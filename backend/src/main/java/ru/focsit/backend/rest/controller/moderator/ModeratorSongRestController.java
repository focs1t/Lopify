package ru.focsit.backend.rest.controller.moderator;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.dto.CommentDto;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.service.CommentService;
import ru.focsit.backend.service.SongService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/moderator/songs")
public class ModeratorSongRestController {

    @Autowired
    private SongService songService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<SongDto>> getAllSongs() {
        List<SongDto> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SongDto>> getSongs(
            @RequestParam(required = false) String album,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String name) {

        List<Song> songs = songService.getSongsByCriteria(album, artist, name);

        List<SongDto> songDtos = songs.stream()
                .map(songService::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(songDtos);
    }

    @PostMapping
    public ResponseEntity<SongDto> createSong(@RequestBody @Valid SongDto songDto) {
        Song song = songService.createSong(songDto);
        return ResponseEntity.ok(songService.toDto(song));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDto> updateSong(@PathVariable Long id, @RequestBody @Valid SongDto songDto) {
        Song song = songService.getSongById(id);
        song.setName(songDto.getName());
        song.setGenre(songDto.getGenre());
        song.setArtist(songDto.getArtist());
        song.setAlbum(songDto.getAlbum());
        song.setDuration(songDto.getDuration());
        Song updatedSong = songService.updateSong(id, song);
        return ResponseEntity.ok(songService.toDto(updatedSong));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{songId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsBySong(@PathVariable Long songId) {
        List<CommentDto> comments = commentService.getCommentsBySongId(songId)
                .stream()
                .map(commentService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{songId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long songId, @PathVariable Long commentId) {
        boolean commentExists = commentService.getCommentsBySongId(songId).stream()
                .anyMatch(comment -> comment.getId().equals(commentId));
        if (!commentExists) {
            return ResponseEntity.notFound().build();
        }
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}