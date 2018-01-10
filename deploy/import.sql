connect 'jdbc:derby:/home/ubuntu/derbyDb1;';

DELETE FROM SA.PHOTO_INFO;
DELETE FROM SA.CALENDAR_INFO;

INSERT INTO SA.PHOTO_INFO(NAME, ALBUM_ID) VALUES ('2hamik', '1000000414802924' );
INSERT INTO SA.PHOTO_INFO(NAME, ALBUM_ID, PEOPLE_IN_PHOTOS) VALUES ('Shamik', '1000000426920616', 'Shamik, Anshi, Foram, Shamik, Preesha, Shrvya, Sweta, Geeta Yagnik, Nayan Yagnik, Khushboo Modi, Priyanki Shah, Jatin Bhana, Kaushal, Neeyam, Deepak, Agastya, pradip shah, beach, mountains, bangalore, mumbai' );

INSERT INTO SA.CALENDAR_INFO(NAME, CALENDAR_URL) VALUES ('2hamik', 'https://www.googleapis.com/calendar/v3/calendars/shamik.nfc@gmail.com/events?orderBy=startTime&singleEvents=true');
INSERT INTO SA.CALENDAR_INFO(NAME, CALENDAR_URL) VALUES ('Shamik', 'https://www.googleapis.com/calendar/v3/calendars/0h04lenjqb302dao7lhujenmkk@group.calendar.google.com/events?orderBy=startTime&singleEvents=true');