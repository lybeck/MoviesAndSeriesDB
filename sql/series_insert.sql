
DROP TABLE IF EXISTS mosedb.episodeformat CASCADE;
DROP TABLE IF EXISTS mosedb.episode CASCADE;
DROP TABLE IF EXISTS mosedb.seriesgenre CASCADE;
DROP TABLE IF EXISTS mosedb.seriesname CASCADE;
DROP TABLE IF EXISTS mosedb.series CASCADE;


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

-- episode
CREATE TABLE mosedb.episode (
    seriesid int NOT NULL,
    seasonnumber int NOT NULL,
    episodenumber int NOT NULL,
    episodename varchar(50),
    episodeyear int,
    seen boolean DEFAULT true  NOT NULL,
    PRIMARY KEY (seriesid, seasonnumber, episodenumber),
    FOREIGN KEY (seriesid) REFERENCES mosedb.series ON DELETE CASCADE
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



-- inserts

INSERT INTO mosedb.series (owner) VALUES ('lasse');
DELETE FROM mosedb.series WHERE seriesid=1;
INSERT INTO mosedb.series (owner) VALUES ('lasse');
INSERT INTO mosedb.series (owner) VALUES ('lasse');
DELETE FROM mosedb.series WHERE seriesid=3;
INSERT INTO mosedb.series (owner) VALUES ('lasse');
INSERT INTO mosedb.series (owner) VALUES ('lasse');
INSERT INTO mosedb.series (owner) VALUES ('lasse');
INSERT INTO mosedb.series (owner) VALUES ('lasse');
DELETE FROM mosedb.series WHERE seriesid=7;
INSERT INTO mosedb.series (owner) VALUES ('lasse');
DELETE FROM mosedb.series WHERE seriesid=8;
INSERT INTO mosedb.series (owner) VALUES ('lasse');
INSERT INTO mosedb.series (owner) VALUES ('lasse');
INSERT INTO mosedb.series (owner) VALUES ('lasse');
INSERT INTO mosedb.series (owner) VALUES ('lasse');
INSERT INTO mosedb.series (owner) VALUES ('roope');
INSERT INTO mosedb.series (owner) VALUES ('roope');
INSERT INTO mosedb.series (owner) VALUES ('roope');
INSERT INTO mosedb.series (owner) VALUES ('roope');


INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (2, 'eng', 'How I Met Your Mother');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (2, 'fi', 'Ensisilmäyksellä');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (4, 'eng', 'The Simpsons');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (5, 'eng', 'Sex and The City');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (5, 'fi', 'Sinkkuelämää');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (6, 'eng', 'Rome');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (6, 'fi', 'Rooma');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (9, 'eng', 'The Big Bang Theory');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (9, 'fi', 'Rillit Huurussa');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (10, 'eng', 'Elementary');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (10, 'fi', 'Holmes NYC');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (11, 'eng', 'Two and a Half Men');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (11, 'fi', 'Miehen Puolikkaat');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (12, 'eng', 'Game of Thrones');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (13, 'eng', 'Desperate Housewives');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (13, 'fi', 'Täydelliset Naiset');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (14, 'eng', 'Ally McBeal');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (15, 'eng', 'Teletubbies');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (15, 'fi', 'Teletapit');
INSERT INTO mosedb.seriesname (seriesid, langid, seriesname) VALUES (16, 'eng', 'Dexter');

INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (2, 'Comedy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (2, 'Romance');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (4, 'Animation');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (4, 'Comedy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (4, 'Family');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (5, 'Comedy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (5, 'Romance');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (6, 'Action');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (6, 'Drama');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (6, 'History');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (9, 'Comedy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (10, 'Crime');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (10, 'Drama');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (10, 'Mystery');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (11, 'Comedy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (12, 'Adventure');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (12, 'Drama');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (12, 'Fantasy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (13, 'Comedy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (13, 'Drama');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (13, 'Romance');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (14, 'Comedy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (14, 'Drama');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (14, 'Fantasy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (15, 'Family');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (15, 'Fantasy');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (15, 'Musical');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (16, 'Crime');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (16, 'Drama');
INSERT INTO mosedb.seriesgenre (seriesid, genrename) VALUES (16, 'Mystery');

INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 1, 'Winter Is Coming', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 2, 'The Kingsroad', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 3, 'Lord Snow', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 4, 'Cripples, Bastards, and Broken Things', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 5, 'The Wolf and the Lion', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 6, 'A Golden Crown', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 7, 'You Win or You Die', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 8, 'The Pointy End', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 9, 'Baelor', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 1, 10, 'Fire and Blood', 2011, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 1, 'The North Remembers', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 2, 'The Night Lands', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 3, 'What Is Dead May Never Die', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 4, 'Garden of Bones', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 5, 'The Ghost of Harrenhal', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 6, 'The Old Gods and the New', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 7, 'A Man Without Honor', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 8, 'The Prince of Winterfell', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 9, 'Blackwater', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 2, 10, 'Valar Morghulis', 2012, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 1, 'Valar Dohaeris', 2013, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 2, 'Dark Wings, Dark Words', 2013, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 3, 'Walk of Punishment', 2013, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 4, 'And Now His Watch Is Ended', 2013, true);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 5, 'Kissed by Fire', 2013, false);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 6, 'The Climb', 2013, false);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 7, 'The Bear and the Maiden Fair', 2013, false);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 8, 'Second Sons', 2013, false);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 9, 'The Rains of Castamere', 2013, false);
INSERT INTO mosedb.episode (seriesid, seasonnumber, episodenumber, episodename, episodeyear, seen) VALUES (12, 3, 10, 'Mhysa', 2013, false);



