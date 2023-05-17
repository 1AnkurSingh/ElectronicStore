package com.electronicstore.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("Resource Not Found Exception !!");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
