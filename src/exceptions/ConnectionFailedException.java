package exceptions;

public class ConnectionFailedException extends Exception {
    ConnectionFailedException() {

    }

    ConnectionFailedException(String message) {
        super(message);
    }
}
