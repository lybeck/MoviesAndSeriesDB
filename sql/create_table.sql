
-- drop all tables

-- select 'DROP TABLE IF EXISTS ' || tablename || ' cascade;' 
--   from pg_tables
--  where schemaname = 'mosedb';

DROP TABLE IF EXISTS mosedb.episodeformat cascade;
DROP TABLE IF EXISTS mosedb.episode cascade;
DROP TABLE IF EXISTS mosedb.season cascade;
DROP TABLE IF EXISTS mosedb.seriesgenre cascade;
DROP TABLE IF EXISTS mosedb.seriesname cascade;
DROP TABLE IF EXISTS mosedb.series cascade;
DROP TABLE IF EXISTS mosedb.movieformat cascade;
DROP TABLE IF EXISTS mosedb.moviename cascade;
DROP TABLE IF EXISTS mosedb.moviegenre cascade;
DROP TABLE IF EXISTS mosedb.movie cascade;
DROP TABLE IF EXISTS mosedb.format cascade;
DROP TABLE IF EXISTS mosedb.genre cascade;
DROP TABLE IF EXISTS mosedb.users cascade;
DROP TYPE IF EXISTS mosedb.langid cascade;
DROP TYPE IF EXISTS mosedb.mediaformat cascade;

CREATE TYPE mosedb.langid AS ENUM ('fi','swe','eng','other');
CREATE TYPE mosedb.mediaformat AS ENUM ('vhs','dvd','bd','dc');

-- users
CREATE TABLE mosedb.users (
    username varchar(20) NOT NULL,
    password varchar(20) NOT NULL,
    firstname varchar(20) NOT NULL,
    lastname varchar(30) NOT NULL,
    admin boolean DEFAULT false  NOT NULL,
    PRIMARY KEY (username)
);
INSERT INTO mosedb.users (username, password, firstname, lastname, admin)
    VALUES ('lasse-admin', 'salasana', 'Lasse', 'Lybeck', true);
INSERT INTO mosedb.users (username, password, firstname, lastname, admin)
    VALUES ('roope-admin', 'koira', 'Robert', 'Sirviö', true);

-- genre
CREATE TABLE mosedb.genre (
    genrename varchar(20) NOT NULL,
    PRIMARY KEY (genrename)
);
INSERT INTO mosedb.genre (genrename) VALUES ('Action');
INSERT INTO mosedb.genre (genrename) VALUES ('Adventure');
INSERT INTO mosedb.genre (genrename) VALUES ('Animation');
INSERT INTO mosedb.genre (genrename) VALUES ('Biography');
INSERT INTO mosedb.genre (genrename) VALUES ('Comedy');
INSERT INTO mosedb.genre (genrename) VALUES ('Crime');
INSERT INTO mosedb.genre (genrename) VALUES ('Documentary');
INSERT INTO mosedb.genre (genrename) VALUES ('Drama');
INSERT INTO mosedb.genre (genrename) VALUES ('Family');
INSERT INTO mosedb.genre (genrename) VALUES ('Fantasy');
INSERT INTO mosedb.genre (genrename) VALUES ('Film-Noir');
INSERT INTO mosedb.genre (genrename) VALUES ('Game-Show');
INSERT INTO mosedb.genre (genrename) VALUES ('History');
INSERT INTO mosedb.genre (genrename) VALUES ('Horror');
INSERT INTO mosedb.genre (genrename) VALUES ('Music');
INSERT INTO mosedb.genre (genrename) VALUES ('Musical');
INSERT INTO mosedb.genre (genrename) VALUES ('Mystery');
INSERT INTO mosedb.genre (genrename) VALUES ('News');
INSERT INTO mosedb.genre (genrename) VALUES ('Reality-TV');
INSERT INTO mosedb.genre (genrename) VALUES ('Romance');
INSERT INTO mosedb.genre (genrename) VALUES ('Sci-Fi');
INSERT INTO mosedb.genre (genrename) VALUES ('Sport');
INSERT INTO mosedb.genre (genrename) VALUES ('Talk-Show');
INSERT INTO mosedb.genre (genrename) VALUES ('Thriller');
INSERT INTO mosedb.genre (genrename) VALUES ('War');
INSERT INTO mosedb.genre (genrename) VALUES ('Western');

-- format
CREATE TABLE mosedb.format (
    formatid serial NOT NULL,
    mediaformat mosedb.mediaformat NOT NULL,
    filetype varchar(10),
    resox int,
    resoy int,
    PRIMARY KEY (formatid)
);

-- movie
CREATE TABLE mosedb.movie (
    movieid serial NOT NULL,
    owner varchar(20) NOT NULL,
    movieyear int,
    seen boolean DEFAULT true NOT  NULL,
    PRIMARY KEY (movieid),
    FOREIGN KEY (owner) REFERENCES mosedb.users ON DELETE CASCADE
);
 
-- moviename
CREATE TABLE mosedb.moviename (
    movieid int NOT NULL,
    langid mosedb.langid NOT NULL,
    moviename varchar(50) NOT NULL,
    PRIMARY KEY (movieid, langid),
    FOREIGN KEY (movieid) REFERENCES mosedb.movie ON DELETE CASCADE
);

-- moviegenre
CREATE TABLE mosedb.moviegenre (
    movieid int NOT NULL,
    genrename varchar(20) NOT NULL,
    PRIMARY KEY (movieid, genrename),
    FOREIGN KEY (movieid) REFERENCES mosedb.movie ON DELETE CASCADE,
    FOREIGN KEY (genrename) REFERENCES mosedb.genre ON DELETE CASCADE
);

-- movieformat
CREATE TABLE mosedb.movieformat (
    movieid int NOT NULL,
    formatid int NOT NULL,
    PRIMARY KEY (movieid, formatid),
    FOREIGN KEY (movieid) REFERENCES mosedb.movie ON DELETE CASCADE,
    FOREIGN KEY (formatid) REFERENCES mosedb.format ON DELETE CASCADE
);

-- series
CREATE TABLE mosedb.series (
    seriesid serial NOT NULL,
    owner varchar(20) NOT NULL,
    PRIMARY KEY (seriesid),
    FOREIGN KEY (owner) REFERENCES mosedb.users ON DELETE CASCADE
);

-- seriesname
CREATE TABLE mosedb.seriesname (
    seriesid int NOT NULL,
    langid mosedb.langid NOT NULL,
    seriesname varchar(50) NOT NULL,
    PRIMARY KEY (seriesid, langid),
    FOREIGN KEY (seriesid) REFERENCES mosedb.series ON DELETE CASCADE
);

-- seriesgenre
CREATE TABLE mosedb.seriesgenre (
    seriesid int NOT NULL,
    genrename varchar(20) NOT NULL,
    PRIMARY KEY (seriesid, genrename),
    FOREIGN KEY (seriesid) REFERENCES mosedb.series ON DELETE CASCADE,
    FOREIGN KEY (genrename) REFERENCES mosedb.genre
);

-- season
CREATE TABLE mosedb.season (
    seriesid int NOT NULL,
    seasonnumber int NOT NULL,
    PRIMARY KEY (seriesid, seasonnumber),
    FOREIGN KEY (seriesid) REFERENCES mosedb.series ON DELETE CASCADE
);

-- episode
CREATE TABLE mosedb.episode (
    seriesid int NOT NULL,
    seasonnumber int NOT NULL,
    episodenumber int NOT NULL,
    episodename varchar(50),
    episodeyear int,
    seen boolean DEFAULT true  NOT NULL,
    PRIMARY KEY (seriesid, seasonnumber, episodenumber),
    FOREIGN KEY (seriesid,seasonnumber) REFERENCES mosedb.season ON DELETE CASCADE
);

-- episodeformat
CREATE TABLE mosedb.episodeformat (
    seriesid int NOT NULL,
    seasonnumber int NOT NULL,
    episodenumber int NOT NULL,
    formatid int NOT NULL,
    PRIMARY KEY (seriesid, seasonnumber, episodenumber, formatid),
    FOREIGN KEY (seriesid,seasonnumber,episodenumber) REFERENCES mosedb.episode ON DELETE CASCADE,
    FOREIGN KEY (formatid) REFERENCES mosedb.format ON DELETE CASCADE
);

