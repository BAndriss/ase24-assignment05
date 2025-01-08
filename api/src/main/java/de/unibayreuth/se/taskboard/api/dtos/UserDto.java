package de.unibayreuth.se.taskboard.api.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.annotation.Nullable;

public record UserDto(
    @Nullable  UUID id,
    @NotNull  String name,
    @Nullable LocalDateTime createdAt
) { }