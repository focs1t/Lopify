package ru.focsit.backend.pojo;

import java.io.Serializable;
import java.util.Objects;

public class ArtistTrackId implements Serializable {

    private Long artist;
    private Long track;

    public ArtistTrackId() {}

    public ArtistTrackId(Long artist, Long track) {
        this.artist = artist;
        this.track = track;
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, track);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistTrackId that = (ArtistTrackId) o;
        return Objects.equals(artist, that.artist) && Objects.equals(track, that.track);
    }
}
