package ru.geekbrains.spring.springdata.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException (String message) {
        super (message);
    }
}
