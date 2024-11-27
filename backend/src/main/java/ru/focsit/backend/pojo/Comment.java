package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentUserId")
    private User commentUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentAlbumId")
    private Album commentAlbum;

    @Column(name = "commentText", nullable = false)
    private String commentText;

    @Column(name = "commentDate", nullable = false, updatable = false)
    private LocalDateTime commentDate = LocalDateTime.now();
}

