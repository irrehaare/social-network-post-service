package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import lombok.Getter;

import java.util.Objects;

@Getter
public class NewSocialNetworkPostDto {
    String author;
    String content;

    public NewSocialNetworkPostDto(String author, String content) {
        this.author = Objects.requireNonNull(author);
        this.content = Objects.requireNonNull(content);
    }
}
