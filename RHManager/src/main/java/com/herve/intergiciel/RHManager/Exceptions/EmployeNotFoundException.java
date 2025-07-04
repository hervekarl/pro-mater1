package com.herve.intergiciel.RHManager.Exceptions;


public class EmployeNotFoundException extends RuntimeException {
    public EmployeNotFoundException(String message) {
        super(message);
    }
}