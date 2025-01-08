package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.repository.PlaylistRepository;
import ru.focsit.backend.repository.SongRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    private final JdbcTemplate jdbcTemplate;

    public SongService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SongDto toDto(Song song) {
        return new SongDto(
                song.getId(),
                song.getName(),
                song.getGenre(),
                song.getArtist(),
                song.getAlbum(),
                song.getDuration()
        );
    }

    public Song fromDto(SongDto songDto) {
        return Song.builder()
                .id(songDto.getId())
                .name(songDto.getName())
                .genre(songDto.getGenre())
                .artist(songDto.getArtist())
                .album(songDto.getAlbum())
                .duration(songDto.getDuration())
                .build();
    }

    public Song createSong(SongDto songDto) {
        Song song = fromDto(songDto);
        return songRepository.save(song);
    }

    public List<SongDto> getAllSongs() {
        return songRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /*
    public Song getSongById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found"));
    }

     */

    public Song getSongById(Long id) {
        String query = "SELECT id, name, genre, artist, album, duration FROM songs WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, new SongRowMapper());
    }

    /*
    public Song updateSong(Long id, Song updatedSong) {
        Song song = getSongById(id);
        List.of(
                (Runnable) () -> song.setName(updatedSong.getName()),
                () -> song.setDuration(updatedSong.getDuration()),
                () -> song.setGenre(updatedSong.getGenre()),
                () -> song.setArtist(updatedSong.getArtist()),
                () -> song.setAlbum(updatedSong.getAlbum())
        ).forEach(Runnable::run);
        return songRepository.save(song);
    }

     */

    public Song updateSong(Long id, Song updatedSong) {
        String query = """
            UPDATE songs
            SET name = ?, duration = ?, genre = ?, artist = ?, album = ?
            WHERE id = ?
        """;
        jdbcTemplate.update(query,
                updatedSong.getName(),
                updatedSong.getDuration(),
                updatedSong.getGenre(),
                updatedSong.getArtist(),
                updatedSong.getAlbum(),
                id);
        return getSongById(id);
    }

    private static class SongRowMapper implements RowMapper<Song> {
        @Override
        public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Song.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .genre(rs.getString("genre"))
                    .artist(rs.getString("artist"))
                    .album(rs.getString("album"))
                    .duration(rs.getInt("duration"))
                    .build();
        }
    }

    public void deleteSong(Long id) {
        Song song = getSongById(id);
        playlistRepository.removeSongFromAllPlaylists(song);

        songRepository.deleteById(id);
    }

    public List<Song> getSongsByCriteria(String album, String artist, String name) {
        return songRepository.findAll().stream()
                .filter(song -> album == null || song.getAlbum().equalsIgnoreCase(album))
                .filter(song -> artist == null || song.getArtist().equalsIgnoreCase(artist))
                .filter(song -> name == null || song.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public List<SongDto> getTop10SongsByFavorites() {
        String query = """
            SELECT s.id, s.name, s.genre, s.artist, s.album, s.duration
            FROM songs s
            JOIN playlist_songs ps ON s.id = ps.song_id
            GROUP BY s.id
            ORDER BY COUNT(ps.song_id) DESC
            LIMIT 10
        """;

        List<Song> songs = jdbcTemplate.query(query, (rs, rowNum) -> Song.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .genre(rs.getString("genre"))
                .artist(rs.getString("artist"))
                .album(rs.getString("album"))
                .duration(rs.getInt("duration"))
                .build());

        return songs.stream()
                .map(this::toDto)
                .toList();
    }
}