package com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class SocialNetworkPostDto {
    private final long id;
    private final LocalDateTime postDate;
    private final String author;
    private final String content;
    private final int viewCount;

    public SocialNetworkPostDto(long id, LocalDateTime postDate, String author, String content, int viewCount) {
        this.id = id;
        this.postDate = Objects.requireNonNull(postDate);
        this.author = Objects.requireNonNull(author);
        this.content = Objects.requireNonNull(content);
        this.viewCount = viewCount;
    }

    public SocialNetworkPostDto(SocialNetworkPost socialNetworkPost){
        this.id = socialNetworkPost.getId();
        this.postDate = socialNetworkPost.getPostDate();
        this.author = socialNetworkPost.getAuthor();
        this.content = socialNetworkPost.getContent();
        this.viewCount = socialNetworkPost.getViewCount();
    }
}
