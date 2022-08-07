package com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class NewSocialNetworkPostDto {
    private final String author;
    private final String content;

    public NewSocialNetworkPostDto(String author, String content) {
        this.author = Objects.requireNonNull(author);
        this.content = Objects.requireNonNull(content);
    }
}
