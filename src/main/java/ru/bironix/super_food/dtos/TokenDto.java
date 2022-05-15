package ru.bironix.super_food.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema(description = "Access or Refresh token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto implements Serializable {

    @NotBlank
    String token;
}
