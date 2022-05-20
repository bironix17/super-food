package ru.bironix.super_food.controllers.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.bironix.super_food.security.jwt.SecurityUser;
import ru.bironix.super_food.store.db.models.person.Role;

public class ControllerUtils {

    public static Integer getPersonIdFromSecurityContext() {
        var user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    public static Role getPersonRoleFromSecurityContext() {
        var user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getRole();
    }

    public static String toPrettyJsonForHtml(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                .replace("\n", "<br>");
    }
}
