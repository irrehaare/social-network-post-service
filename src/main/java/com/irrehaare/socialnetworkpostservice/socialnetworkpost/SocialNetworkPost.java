package com.irrehaare.socialnetworkpostservice.socialnetworkpost;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreatedDate
    private LocalDateTime postDate;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    private int viewCount;
}


