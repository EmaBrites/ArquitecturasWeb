package com.exa.scooterservice.Exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String entity, String id) {
        super(String.format("La entidad %s con id %s no existe.", entity, id));
    }
}
