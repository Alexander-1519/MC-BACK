package com.ryhnik.exception;

public class NoSuchMaintenanceException extends EntityNotFoundException {

    public NoSuchMaintenanceException(Long id) {
        super(String.format("Maintenance not found: %s", id), Code.MAINTENANCE_NOT_FOUND);
    }
}
