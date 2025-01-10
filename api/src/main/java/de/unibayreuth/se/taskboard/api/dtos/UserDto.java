package de.unibayreuth.se.taskboard.api.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.annotation.Nullable;
import lombok.Data;

//public record UserDto(
//    @Nullable  UUID id,
//    @NotNull  String name,
//    @Nullable LocalDateTime createdAt
//) { }



@Data
public class UserDto {
    @Nullable
    private final UUID id;
    @NotNull
    private final String name;
    @Nullable
    private final LocalDateTime createdAt;
}
