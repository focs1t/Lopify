package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long COMMENTID;

    @ManyToOne
    @JoinColumn(name = "COMMENT_USER_ID")
    private User USER;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ALBUM_ID")
    private Album ALBUM;

    @Column(nullable = false)
    private String COMMENTTEXT;

    private LocalDateTime COMMENTDATE = LocalDateTime.now();
}

