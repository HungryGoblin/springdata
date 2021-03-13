package ru.geekbrains.spring.springdata.exceptions;

public class ResourceNotFoundException extends CommonException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}