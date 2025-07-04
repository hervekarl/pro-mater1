package com.herve.pro.intergiciel.Authentification.Exceptions;

public class UsersErrorExceptions extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsersErrorExceptions(String message) {
        super(message);
    }

    public UsersErrorExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public UsersErrorExceptions(Throwable cause) {
        super(cause);
    }

}
