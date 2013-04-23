
insert into mosedb.users (username, password, firstname, lastname)
    values ('lasse', 'salasana', 'Lasse', 'Lybeck');
insert into mosedb.users (username, password, firstname, lastname)
    values ('roope', 'koira', 'Robert', 'Sirviö');
insert into mosedb.users (username, password, firstname, lastname)
    values ('noadmin', 'asd', 'No', 'Admin');


insert into mosedb.movie (owner, movieyear) values ('noadmin', 1937);
insert into mosedb.movie (owner, movieyear, seen) values ('noadmin', 1991, false);
insert into mosedb.movie (owner, movieyear) values ('roope', 1985);
insert into mosedb.movie (owner, movieyear) values ('lasse', 1977);
insert into mosedb.movie (owner, movieyear) values ('lasse', 1972);
insert into mosedb.movie (owner, movieyear) values ('roope', 1989);

insert into mosedb.moviename (movieid, langid, moviename)
    values (1, 'eng', 'Snow white');
insert into mosedb.moviename (movieid, langid, moviename)
    values (1, 'fi', 'Lumikki');
insert into mosedb.moviename (movieid, langid, moviename)
    values (1, 'swe', 'Snövit');
insert into mosedb.moviename (movieid, langid, moviename)
    values (2, 'eng', 'Terminator 2: Judgement Day');
insert into mosedb.moviename (movieid, langid, moviename)
    values (3, 'eng', 'Back to the Future');
insert into mosedb.moviename (movieid, langid, moviename)
    values (3, 'fi', 'Paluu tulevaisuuteen');
insert into mosedb.moviename (movieid, langid, moviename)
    values (4, 'eng', 'Star Wars');
insert into mosedb.moviename (movieid, langid, moviename)
    values (4, 'fi', 'Tähtien Sota');
insert into mosedb.moviename (movieid, langid, moviename)
    values (5, 'eng', 'The Godfather');
insert into mosedb.moviename (movieid, langid, moviename)
    values (5, 'fi', 'Kummisetä');
insert into mosedb.moviename (movieid, langid, moviename)
    values (5, 'swe', 'Gudfadern');
insert into mosedb.moviename (movieid, langid, moviename)
    values (6, 'eng', 'Batman');
insert into mosedb.moviename (movieid, langid, moviename)
    values (6, 'fi', 'Lepakkomies');
insert into mosedb.moviename (movieid, langid, moviename)
    values (6, 'swe', 'Läderlappen');
insert into mosedb.moviename (movieid, langid, moviename)
    values (6, 'other', 'Nahkhiirmees');

insert into mosedb.moviegenre (movieid, genrename)
    values (1, 'Animation');
insert into mosedb.moviegenre (movieid, genrename)
    values (1, 'Family');
insert into mosedb.moviegenre (movieid, genrename)
    values (1, 'Fantasy');
insert into mosedb.moviegenre (movieid, genrename)
    values (2, 'Action');
insert into mosedb.moviegenre (movieid, genrename)
    values (2, 'Sci-Fi');
insert into mosedb.moviegenre (movieid, genrename)
    values (2, 'Thriller');
insert into mosedb.moviegenre (movieid, genrename)
    values (3, 'Adventure');
insert into mosedb.moviegenre (movieid, genrename)
    values (3, 'Comedy');
insert into mosedb.moviegenre (movieid, genrename)
    values (3, 'Sci-Fi');
insert into mosedb.moviegenre (movieid, genrename)
    values (4, 'Action');
insert into mosedb.moviegenre (movieid, genrename)
    values (4, 'Adventure');
insert into mosedb.moviegenre (movieid, genrename)
    values (4, 'Fantasy');
insert into mosedb.moviegenre (movieid, genrename)
    values (5, 'Crime');
insert into mosedb.moviegenre (movieid, genrename)
    values (5, 'Drama');
insert into mosedb.moviegenre (movieid, genrename)
    values (6, 'Action');
insert into mosedb.moviegenre (movieid, genrename)
    values (6, 'Fantasy');

insert into mosedb.format (mediaformat)
    values ('vhs'); -- 1
insert into mosedb.format (mediaformat)
    values ('dvd'); -- 2
insert into mosedb.format (mediaformat)
    values ('dvd'); -- 3
insert into mosedb.format (mediaformat)
    values ('vhs'); -- 4
insert into mosedb.format (mediaformat)
    values ('dvd'); -- 5
insert into mosedb.format (mediaformat)
    values ('bd'); -- 6
insert into mosedb.format (mediaformat, filetype, resox, resoy)
    values ('dc', 'mkv', 1920, 1080);  -- 7
insert into mosedb.format (mediaformat, filetype, resox, resoy)
    values ('dc', 'avi', 480, 360); -- 8
insert into mosedb.format (mediaformat)
    values ('bd'); -- 9
insert into mosedb.format (mediaformat, filetype)
    values ('dc', 'mkv'); -- 10
insert into mosedb.format (mediaformat)
    values ('vhs'); -- 11

insert into mosedb.movieformat (movieid, formatid)
    values(1,1);
insert into mosedb.movieformat (movieid, formatid)
    values(1,2);
insert into mosedb.movieformat (movieid, formatid)
    values(2,3);
insert into mosedb.movieformat (movieid, formatid)
    values(3,4);
insert into mosedb.movieformat (movieid, formatid)
    values(3,5);
insert into mosedb.movieformat (movieid, formatid)
    values(4,6);
insert into mosedb.movieformat (movieid, formatid)
    values(4,7);
insert into mosedb.movieformat (movieid, formatid)
    values(4,8);
insert into mosedb.movieformat (movieid, formatid)
    values(5,9);
insert into mosedb.movieformat (movieid, formatid)
    values(5,10);
insert into mosedb.movieformat (movieid, formatid)
    values(6,11);
