package com.ryhnik.exception;

public class NoSuchMaintenanceDateException extends EntityNotFoundException {

    public NoSuchMaintenanceDateException(Long id) {
        super(String.format("Maintenance date not found: %s", id), Code.MAINTENANCE_DATE_NOT_FOUND);
    }
}
