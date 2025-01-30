package ru.dsr.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShortLinkDto {
    private Integer id;
    @NotNull
    private String url;
    @NotNull
    private String shortCode;

    @NotNull
    private String generatedLink;
}
