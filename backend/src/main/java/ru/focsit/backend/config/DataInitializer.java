package ru.focsit.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.service.SongService;
import ru.focsit.backend.service.UserService;
import ru.focsit.backend.service.PlaylistService;

import java.util.stream.IntStream;

@Component
public class DataInitializer {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private SongService songService;

    private boolean songsInitialized = false;  // Флаг, чтобы инициализация песен выполнялась один раз

    @PostConstruct
    public void init() {
        initAdminUser();
        initUsersWithPlaylists();
        initSongsIfNotExist();  // Теперь инициализация песен только если они не добавлены
    }

    private void initAdminUser() {
        if (userService.getAllUsers().stream().noneMatch(u -> u.getUsername().equals("admin"))) {
            User adminUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .email("admin@music.com")
                    .role(Role.ROLE_ADMIN)
                    .build();

            userService.create(adminUser);
        }
    }

    private void initUsersWithPlaylists() {
        // Проверим, если нет пользователей с ролью ROLE_USER, то добавим
        createUserWithPlaylistIfNotExist("user1", "user1@music.com", "user1");
        createUserWithPlaylistIfNotExist("user2", "user2@music.com", "user2");
        createUserWithPlaylistIfNotExist("user3", "user3@music.com", "user3");
    }

    private void createUserWithPlaylistIfNotExist(String username, String email, String password) {
        // Проверяем, существует ли уже пользователь с таким именем
        if (userService.getAllUsers().stream().anyMatch(u -> u.getUsername().equals(username))) {
            System.out.println("Пользователь с именем " + username + " уже существует");
            return;
        }

        // Если нет, создаем нового пользователя
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        // Создаем плейлист "Избранное" для пользователя
        Playlist playlist = Playlist.builder()
                .user(user)
                .name("Избранное")
                .build();

        playlistService.createPlaylist(playlist);
    }

    private void initSongsIfNotExist() {
        if (!songsInitialized) {  // Проверяем, была ли уже инициализация песен
            initSongs();
            songsInitialized = true;  // Помечаем, что инициализация песен выполнена
        }
    }

    private void initSongs() {
        String[] artists = {
                "The Weeknd", "Ed Sheeran", "Adele", "Drake", "Billie Eilish",
                "Taylor Swift", "Post Malone", "Kanye West", "Harry Styles", "Dua Lipa"
        };

        String[] albums = {
                "After Hours", "Divide", "25", "Scorpion", "Happier Than Ever",
                "Red", "Hollywood's Bleeding", "Donda", "Fine Line", "Future Nostalgia"
        };

        String[] songNames = {
                "Blinding Lights", "Shape of You", "Hello", "God's Plan", "Bad Guy",
                "Love Story", "Circles", "Stronger", "Watermelon Sugar", "Don't Start Now",
                "Starboy", "Castle on the Hill", "Someone Like You", "Hotline Bling", "Everything I Wanted",
                "All Too Well", "Rockstar", "Praise God", "Adore You", "Levitating",
                "Save Your Tears", "Perfect", "When We Were Young", "In My Feelings", "No Time to Die",
                "Red", "Wow", "I Am a God", "Sign of the Times", "Physical", "Peaches"
        };

        String[] genres = {
                "Pop", "Rock", "Hip-Hop", "R&B", "Indie", "Electronic", "Country"
        };

        IntStream.range(0, 30).forEach(i -> {
            Song song = Song.builder()
                    .name(songNames[i])
                    .artist(artists[i % artists.length])
                    .album(albums[i % albums.length])
                    .duration(180 + (i % 5) * 10)
                    .genre(genres[i % genres.length])
                    .build();

            SongDto songDto = new SongDto(
                    song.getId(),
                    song.getName(),
                    song.getGenre(),
                    song.getArtist(),
                    song.getAlbum(),
                    song.getDuration()
            );

            songService.createSong(songDto);
        });
    }
}