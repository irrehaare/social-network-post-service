package com.irrehaare.socialnetworkpostservice.posteditaudit;

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
public class PostEditAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long postId;

    @Column(nullable = false)
    private LocalDateTime editDate;

    @Column(nullable = false)
    private String originalContent;

    public PostEditAudit(long postId, String originalContent) {
        this.postId = postId;
        this.editDate = LocalDateTime.now();
        this.originalContent = originalContent;
    }
}
