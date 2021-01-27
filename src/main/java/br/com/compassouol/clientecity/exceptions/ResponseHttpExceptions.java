package br.com.compassouol.clientecity.exceptions;

public class ResponseHttpExceptions extends RuntimeException {
    private static final long serialVersionID = 1149241039409861914L;

    public ResponseHttpExceptions(String message) {
        super(message);
    }

    public ResponseHttpExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseHttpExceptions(Throwable cause) {
        super(cause);
    }
}
