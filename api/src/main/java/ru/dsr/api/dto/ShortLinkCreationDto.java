package ru.dsr.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ShortLinkCreationDto {
    @NotNull
    private String url;
}
