package ru.bironix.super_food.exceptions;

import lombok.Getter;
import ru.bironix.super_food.constants.ApiError;

import java.util.List;

@Getter
public class NotFoundSourceException extends RuntimeException {

    private List<Integer> notFoundIds;
    private String entityName;
    private final ApiError apiError = ApiError.RESOURCE_NOT_FOUND;

    public NotFoundSourceException(List<Integer> notFoundIds, String entityName) {
        super(ApiError.RESOURCE_NOT_FOUND.name());
        this.notFoundIds = notFoundIds;
        this.entityName = entityName;
    }

    public NotFoundSourceException(Integer notFoundId, String entityName) {
        super(ApiError.RESOURCE_NOT_FOUND.name());
        this.notFoundIds = List.of(notFoundId);
        this.entityName = entityName;
    }

    public NotFoundSourceException(String entityName) {
        super(ApiError.RESOURCE_NOT_FOUND.name());
        this.entityName = entityName;
    }
}
