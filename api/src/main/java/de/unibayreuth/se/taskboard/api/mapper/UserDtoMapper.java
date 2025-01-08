package de.unibayreuth.se.taskboard.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.User;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
@ConditionalOnMissingBean // prevent IntelliJ warning about duplicate beans
@NoArgsConstructor

public abstract class UserDtoMapper {

    public abstract UserDto fromBusiness(User source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name", defaultValue = "Unknown")
    public abstract User toBusiness(UserDto source);
    
}
