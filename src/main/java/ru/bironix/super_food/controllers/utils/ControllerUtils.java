package ru.bironix.super_food.controllers.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.bironix.super_food.security.SecurityUser;

public class ControllerUtils {

//    public static String getUsernameFromSecurityContext() {
//        var user = (MySecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return user.getUsername();
//    }

    public static Integer getPersonIdFromSecurityContext() {
        var user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    public static String toPrettyJsonForHtml(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object)
                .replace("\n", "<br>");
    }
}
