package ru.bironix.super_food.dtos.person;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Schema(description = "Сущность избранного")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDto {
    Integer id;
    Integer dishId;
}
