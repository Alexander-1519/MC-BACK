package com.ryhnik.exception;

public class NoSuchMasterReviewException extends EntityNotFoundException {

    public NoSuchMasterReviewException(Long id) {
        super(String.format("Master review not found: %s", id), Code.MASTER_REVIEW_NOT_FOUND);
    }
}
