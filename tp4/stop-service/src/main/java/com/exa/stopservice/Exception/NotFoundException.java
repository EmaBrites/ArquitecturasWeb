package com.exa.stopservice.Exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String entity, int id) {
        super(String.format("La entidad %s con id %s no existe.", entity, id));
    }
}
