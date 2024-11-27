package ru.focsit.backend.pojo;

import java.io.Serializable;
import java.util.Objects;

public class PlaylistTrackId implements Serializable {

    private Long playlist;
    private Long track;

    public PlaylistTrackId() {}


    public PlaylistTrackId(Long playlist, Long track) {
        this.playlist = playlist;
        this.track = track;
    }


    @Override
    public int hashCode() {
        return Objects.hash(playlist, track);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistTrackId that = (PlaylistTrackId) o;
        return Objects.equals(playlist, that.playlist) && Objects.equals(track, that.track);
    }
}
