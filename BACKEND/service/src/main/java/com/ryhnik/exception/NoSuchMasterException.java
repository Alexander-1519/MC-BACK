package com.ryhnik.exception;

public class NoSuchMasterException extends EntityNotFoundException {

    public NoSuchMasterException(Long id) {
        super(String.format("Master not found: %s", id), Code.MASTER_NOT_FOUND);
    }

    public NoSuchMasterException(String username) {
        super(String.format("Master not found: %s", username), Code.MASTER_NOT_FOUND);
    }
}
