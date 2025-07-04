package com.herve.intergiciel.PharmacyManager.Exception1;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.herve.intergiciel.PharmacyManager.Exception.MedicamentNotFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MedicamentNotFoundException.class)
    public ResponseEntity<String> handleMedicamentNotFoundException(MedicamentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Une erreur est survenue: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
