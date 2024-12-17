package ru.focsit.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteSongsDto {
    private Long userId;
    private String username;
    private Set<SongDto> favoriteSongs;
}