package com.ryhnik.exception;

public class NoSuchCityException extends EntityNotFoundException {

    public NoSuchCityException(Long id) {
        super(String.format("City not found: %s", id), Code.CITY_NOT_FOUND);
    }
}
