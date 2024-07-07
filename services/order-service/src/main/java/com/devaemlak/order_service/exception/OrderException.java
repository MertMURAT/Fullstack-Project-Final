package com.devaemlak.order_service.exception;

public class OrderException extends RuntimeException{

    OrderException(String message){
        super(message);
    }
}
