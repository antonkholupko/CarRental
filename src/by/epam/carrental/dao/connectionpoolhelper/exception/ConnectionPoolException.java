package by.epam.carrental.dao.connectionpoolhelper.exception;

public class ConnectionPoolException extends Exception{

    private static final long serialVersionUID = 1L;

    public ConnectionPoolException(String message, Exception ex) {
        super(message, ex);
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Exception ex) {
        super(ex);
    }
}
