package org.example.userservice.common.exceptions;

public class EntityNotFoundException extends AppException {
    public EntityNotFoundException(String entity, Object id) {
        super("Entity " + entity + " with id " + id + " not found");
    }
}
