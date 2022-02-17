package com.ryhnik.exception;

public class NoSuchUserException extends EntityNotFoundException {

    public NoSuchUserException(Long id) {
        super(String.format("User not found: %s", id), Code.USER_NOT_FOUND);
    }

    public NoSuchUserException(String username) {
        super(String.format("User not found: %s", username), Code.USER_NOT_FOUND);
    }
}
