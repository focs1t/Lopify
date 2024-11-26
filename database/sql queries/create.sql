CREATE TABLE roles (
    roleId BIGSERIAL PRIMARY KEY,
    roleName VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE countries (
    countryId BIGSERIAL PRIMARY KEY,
    countryName VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE artists (
    artistId BIGSERIAL PRIMARY KEY,
    artistName VARCHAR(255) NOT NULL UNIQUE,
	artistBio VARCHAR(255),
	artistCountryId BIGINT,
    FOREIGN KEY (artistCountryId) REFERENCES countries(countryId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE genres (
    genreId BIGSERIAL PRIMARY KEY,
    genreName VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE albums (
    albumId BIGSERIAL PRIMARY KEY,
    albumName VARCHAR(255) NOT NULL,
	albumDescription VARCHAR(255) NOT NULL,
	albumImageUrl VARCHAR(255) NOT NULL,
    albumReleaseDate DATE,
	albumDuration TIME,
	albumArtistId BIGINT,
	albumGenreId BIGINT,
    FOREIGN KEY (albumArtistId) REFERENCES artists(artistId) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (albumGenreId) REFERENCES genres(genreId) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (albumName, albumArtistId)
);

CREATE TABLE tours (
    tourId BIGSERIAL PRIMARY KEY,
    tourName VARCHAR(255) NOT NULL,
    tourArtistId BIGINT,
    FOREIGN KEY (tourArtistId) REFERENCES artists(artistId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE concerts (
    concertId BIGSERIAL PRIMARY KEY,
    concertPlace VARCHAR(255) NOT NULL,
	concertCity VARCHAR(255) NOT NULL,
	concertTicketUrl VARCHAR(255) NOT NULL,
    concertDate TIMESTAMP,
    concertCountryId BIGINT,
	concertTourId BIGINT,
    FOREIGN KEY (concertCountryId) REFERENCES countries(countryId) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (concertTourId) REFERENCES tours(tourId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tracks (
    trackId BIGSERIAL PRIMARY KEY,
    trackName VARCHAR(255) NOT NULL,
	trackDate DATE,
	trackImageUrl VARCHAR(255) NOT NULL,
	trackSongUrl VARCHAR(255) NOT NULL,
	trackDuration TIME,
    trackAlbumId BIGINT,
    trackGenreId BIGINT,
    FOREIGN KEY (trackAlbumId) REFERENCES albums(albumId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (trackGenreId) REFERENCES genres(genreId) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE users (
    userId BIGSERIAL PRIMARY KEY,
    userLogin VARCHAR(255) NOT NULL UNIQUE,
    userPassword VARCHAR(255) NOT NULL,
    userEmail VARCHAR(255) NOT NULL UNIQUE,
    userRoleId BIGINT,
	userCountryId BIGINT,
    FOREIGN KEY (userRoleId) REFERENCES roles(roleId) ON DELETE SET NULL ON UPDATE CASCADE,
	FOREIGN KEY (userCountryId) REFERENCES countries(countryId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE comments (
    commentId BIGSERIAL PRIMARY KEY,
    commentUserId BIGINT,
    commentAlbumId BIGINT,
    commentText VARCHAR(255) NOT NULL,
    commentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (commentUserId) REFERENCES users(userId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (commentAlbumId) REFERENCES albums(albumId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE playlists (
    playlistId BIGSERIAL PRIMARY KEY,
    playlistName VARCHAR(255) NOT NULL,
	playlistDescription VARCHAR(255) NOT NULL,
	playlistImageUrl VARCHAR(255) NOT NULL,
    playlistUserId BIGINT,
    FOREIGN KEY (playlistUserId) REFERENCES users(userId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE playliststracks (
    playlistId BIGINT,
    trackId BIGINT,
    PRIMARY KEY (playlistId, trackId),
    FOREIGN KEY (playlistId) REFERENCES playlists(playlistId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (trackId) REFERENCES tracks(trackId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE artiststracks (
    artistId BIGINT,
    trackId BIGINT,
    PRIMARY KEY (artistId, trackId),
    FOREIGN KEY (artistId) REFERENCES artists(artistId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (trackId) REFERENCES tracks(trackId) ON DELETE CASCADE ON UPDATE CASCADE
);