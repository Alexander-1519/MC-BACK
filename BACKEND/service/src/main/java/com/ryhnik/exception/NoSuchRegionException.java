package com.ryhnik.exception;

public class NoSuchRegionException extends EntityNotFoundException {

    public NoSuchRegionException(Long id) {
        super(String.format("Region not found: %s", id), Code.REGION_NOT_FOUND);
    }
}
