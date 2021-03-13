package ru.geekbrains.spring.springdata.exceptions;


import lombok.Data;

import java.util.Date;

@Data
public class CommonException extends RuntimeException{
    private String message;
    private Date timestamp;

    public CommonException(String message) {
        this.message = message;
        this.timestamp = new Date();
    }
}
