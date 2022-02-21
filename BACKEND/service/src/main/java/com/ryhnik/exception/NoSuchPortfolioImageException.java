package com.ryhnik.exception;

public class NoSuchPortfolioImageException extends EntityNotFoundException {

    public NoSuchPortfolioImageException(Long id) {
        super(String.format("Portfolio image not found: %s", id), Code.PORTFOLIO_IMAGE_NOT_FOUND);
    }
}
