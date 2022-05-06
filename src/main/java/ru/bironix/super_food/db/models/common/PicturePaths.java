package ru.bironix.super_food.db.models.common;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PicturePaths {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    String small;

    String medium;

    @Column(nullable = false)
    String large;
}