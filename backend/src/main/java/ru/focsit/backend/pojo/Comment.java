package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commentUserId")
    @JsonIdentityReference
    private User commentUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commentAlbumId")
    @JsonIdentityReference
    private Album commentAlbum;

    @NotBlank(message = "Текст комментария обязателен")
    @Size(max = 500, message = "Текст комментария должен быть меньше 500 символов")
    @Column(name = "commentText", nullable = false)
    private String commentText;

    @Column(name = "commentDate", nullable = false, updatable = false)
    private LocalDateTime commentDate = LocalDateTime.now();
}