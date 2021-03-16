package ru.geekbrains.spring.springdata.exceptions;

public class RequestException extends RuntimeException {

    public RequestException(String message) {
        super (message);
    }

}
