package by.epam.carrental.dao.impl;

import by.epam.carrental.dao.IConnectionPool;
import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;
import by.epam.carrental.dao.connectionpoolhelper.util.DBResourceManager;
import by.epam.carrental.resource.DBConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class ConnectionPooldb implements IConnectionPool {

    private static final Logger LOG = LogManager.getLogger(ConnectionPooldb.class.getName());

    private static final String CLOSE_CON_EXC = "SQL Exception in closeConnection. ConnectionPool";
    private static final String CLEAR_CON_EXC = "SQL Exception in clearConnectionQueue. ConnectionPool";
    private static final String TAKE_CON_EXC = "Error connection to the data source. ConnectionPool";
    private static final String CLASS_NOT_FOUND_EXC = "Can't find database driver class. ConnectionPool";
    private static final String IS_CLOSED_EXC = "Attempting to close closed connection. ConnectionPool";
    private static final String REMOVE_EXC = "Error deleting connection from the given away connections pool. ConnectionPool";
    private static final String OFFER_EXC = "Error allocation connection in the pool. ConnectionPool";
    private static final String NUMB_FORM_EXC = "NumberFormatException in ConnectionPool, poolSize=50";
    private static final String CON_POOL_EXC = "Error in connection pool. ConnectionPoolException";

    private static final String INIT_STARTS_MSG = "ConnectionPooldb : initPoolData : starts";
    private static final String DISPOSE_STARTS_MSG = "ConnectionPool : dispose : starts";
    private static final String CLEAR_STARTS_MSG = "ConnectionPool : clearConnectionQueue : starts";
    private static final String TAKE_CONNECTION_STARTS_MSG = "ConnectionPooldb : takeConnection : starts";
    private static final String CLOSE_CONNECTION_MSG = "ConnectionPooldb : closeConnection";
    private static final String CLOSE_CONNECTION_QUERY_STARTS_MSG = "ConnectionPooldb : closeConnectionsQueue : starts";
    private static final String INIT_ENDS_MSG = "ConnectionPooldb : initPoolData : ends";
    private static final String DISPOSE_ENDS_MSG = "ConnectionPool : dispose : ends";
    private static final String CLEAR_ENDS_MSG = "ConnectionPool : clearConnectionQueue : ends";
    private static final String TAKE_CONNECTION_ENDS_MSG = "ConnectionPooldb : takeConnection : ends";
    private static final String CLOSE_CONNECTION_QUERY_ENDS_MSG = "ConnectionPooldb : closeConnectionsQueue : ends";

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    private static final ConnectionPooldb instance = new ConnectionPooldb();

    private ConnectionPooldb() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBConfiguration.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBConfiguration.DB_URL);
        this.user = dbResourceManager.getValue(DBConfiguration.DB_USER);
        this.password = dbResourceManager.getValue(DBConfiguration.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBConfiguration.DB_POOL_SIZE));
        } catch (NumberFormatException ex) {
            LOG.warn(NUMB_FORM_EXC);
            poolSize = 50;
        }
    }

    public static ConnectionPooldb getInstance() {
        return instance;
    }

    public void initPoolData() throws ConnectionPoolException {
        LOG.debug(INIT_STARTS_MSG);
        try {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
            connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
                LOG.debug(INIT_ENDS_MSG);
            }
        } catch (SQLException ex) {
            LOG.error(CON_POOL_EXC);
            throw new ConnectionPoolException(CON_POOL_EXC, ex);
        } catch (ClassNotFoundException ex) {
            LOG.error(CLASS_NOT_FOUND_EXC);
            throw new ConnectionPoolException(CLASS_NOT_FOUND_EXC, ex);
        }
    }

    public void dispose() throws ConnectionPoolException {
        LOG.debug(DISPOSE_STARTS_MSG);
        clearConnectionQueue();
        LOG.debug(DISPOSE_ENDS_MSG);
    }

    private void clearConnectionQueue() throws ConnectionPoolException {
        LOG.debug(CLEAR_STARTS_MSG);
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
            LOG.debug(CLEAR_ENDS_MSG);
        } catch (SQLException ex) {
            LOG.error(CLEAR_CON_EXC);
            throw new ConnectionPoolException(CLEAR_CON_EXC, ex);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        LOG.debug(TAKE_CONNECTION_STARTS_MSG);
        Connection connection = null;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
            LOG.debug(TAKE_CONNECTION_ENDS_MSG);
        } catch (InterruptedException ex) {
            LOG.error(TAKE_CON_EXC);
            throw new ConnectionPoolException(TAKE_CON_EXC, ex);
        }
        return connection;
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs) throws ConnectionPoolException {
        LOG.debug(CLOSE_CONNECTION_MSG);
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            LOG.error(CLOSE_CON_EXC);
            throw new ConnectionPoolException(CLOSE_CON_EXC, ex);
        }
        try {
            if (st != null)
                st.close();
        } catch (SQLException ex) {
            LOG.error(CLOSE_CON_EXC);
            throw new ConnectionPoolException(CLOSE_CON_EXC, ex);
        }
        try {
            if (con != null)
                con.close();
        } catch (SQLException ex) {
            LOG.error(CLOSE_CON_EXC);
            throw new ConnectionPoolException(CLOSE_CON_EXC, ex);
        }

    }


    public void closeConnection(Connection con, Statement st) throws ConnectionPoolException {
        LOG.debug(CLOSE_CONNECTION_MSG);
        try {
            con.close();
        } catch (SQLException ex) {
            LOG.error(CLOSE_CON_EXC);
            throw new ConnectionPoolException(CLOSE_CON_EXC, ex);
        }
        try {
            st.close();
        } catch (SQLException ex) {
            LOG.error(CLOSE_CON_EXC);
            throw new ConnectionPoolException(CLOSE_CON_EXC, ex);
        }
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        LOG.debug(CLOSE_CONNECTION_QUERY_STARTS_MSG);
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
        LOG.debug(CLOSE_CONNECTION_QUERY_ENDS_MSG);
    }

    private class PooledConnection implements Connection {
        private Connection connection;

        public PooledConnection(Connection con) throws SQLException {
            this.connection = con;
            this.connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                LOG.error(IS_CLOSED_EXC);
                throw new SQLException(IS_CLOSED_EXC);
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!givenAwayConQueue.remove(this)) {
                LOG.error(REMOVE_EXC);
                throw new SQLException(REMOVE_EXC);
            }
            if (!connectionQueue.offer(this)) {
                LOG.error(OFFER_EXC);
                throw new SQLException(OFFER_EXC);
            }
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public Statement createStatement(int resultSetType, int setConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, setConcurrency, resultSetHoldability);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties arg0) throws SQLClientInfoException {
            connection.setClientInfo(arg0);
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public void releaseSavepoint(Savepoint args0) throws SQLException {
            connection.releaseSavepoint(args0);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
            connection.setNetworkTimeout(arg0, arg1);
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public void rollback(Savepoint arg0) throws SQLException {
            connection.rollback(arg0);
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public Statement createStatement(int resultSetType, int setConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, setConcurrency);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public void abort(Executor arg0) throws SQLException {
            connection.abort(arg0);
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
            connection.setTypeMap(arg0);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

    }

}
