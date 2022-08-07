package com.irrehaare.socialnetworkpostservice.socialnetworkpost.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table
public class SocialNetworkPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime postDate;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0")
    private int viewCount;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    public SocialNetworkPost(String author, String content) {
        this.author = author;
        this.content = content;
        this.postDate = LocalDateTime.now();
        this.viewCount = 0;
        this.isDeleted = false;
    }
}


