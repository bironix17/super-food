package ru.bironix.super_food.db;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.bironix.super_food.db.models.person.Person;

@Mapper(componentModel = "spring")
public interface UpdateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void map(Person sourcePerson, @MappingTarget Person targetPerson);

}
