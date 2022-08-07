package com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UpdateSocialNetworkPostDto {
    private final String content;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UpdateSocialNetworkPostDto(String content) {
        this.content = Objects.requireNonNull(content);
    }
}
