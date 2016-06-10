package by.epam.carrental.dao;

import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface IConnectionPool {

    public Connection takeConnection() throws ConnectionPoolException;

    public void initPoolData() throws ConnectionPoolException;

    public void dispose() throws ConnectionPoolException;

    public void closeConnection(Connection con, Statement st) throws ConnectionPoolException;

    public void closeConnection(Connection con, Statement st, ResultSet rs) throws ConnectionPoolException;
}
