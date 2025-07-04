package com.herve.intergiciel.PharmacyManager.Exception;


public class MedicamentNotFoundException extends RuntimeException {
    public MedicamentNotFoundException(String message) {
        super(message);
    }
}
