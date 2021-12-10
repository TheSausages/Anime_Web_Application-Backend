Insert into Users values
    ('467a809a-d893-48c2-85e2-82f9ce4b1560', 'UserWithData', 2, 10, 25),
    ('eabc11d0-e6da-49f8-a6db-6bceb84a06bc', 'SecondForumUser', 0, 0, 0),
    ('51d75b0a-9d45-4fe8-bf0e-01f7abd07c0b', 'UserWithNoData', 0, 0, 0);


Insert into Achievements values
    (1, 'First Post!', 'The first is never the last', 'achievements/NrOfPosts-1.png', 15),
    (2, '10 Posts!', 'Getting the hang of it', 'achievements/NrOfPosts-10.png', 15),
    (3, '50 posts!', 'Seasoned forum veteran', 'achievements/NrOfPosts-50.png', 25),
    (4, 'First Review!', 'Getting started', 'achievements/NrOfReviews-1.png', 10),
    (5, 'Anime Enthusiast', 'Seen some', 'achievements/NrOfReviews-10.png', 15),
    (6, 'Anime Connoisseur', 'Professional', 'achievements/NrOfReviews-50.png', 20);

Insert into UserAchievements values
    (1, '467a809a-d893-48c2-85e2-82f9ce4b1560'),
    (4, '467a809a-d893-48c2-85e2-82f9ce4b1560');

Insert into Reviews values
    (1, 'First Test Review', 'First Test Review Text', 8, 3, 2),
    (2, 'Second Test Review', 'Second Test Review Text', 0, 8, 1);

Insert Into Anime value (1, 9.0, 1, 0, 1, 25), (2, 5.0, 1, 1, 1, 10), (3, 0.0, 0, 0, 0, 22);

Insert into AnimeUserInfos value
    ('467a809a-d893-48c2-85e2-82f9ce4b1560', 1, 'WATCHING', '2021-10-10',
        '2021-10-11', 0, false, '2021-10-11', true, 9, 1),
    ('467a809a-d893-48c2-85e2-82f9ce4b1560', 2, 'COMPLETED', '2021-10-01',
        '2021-10-10', 1, true, '2021-10-10', true, 5, 2);

Insert into Tags value
    (1, 'Episode Discussion', 'LOW', 'rgb(0, 183, 255)'),
    (2, 'New Studio', 'MEDIUM', 'rgb(255, 112, 112)'),
    (3, 'Best Girl', 'HIGH', 'rgb(255, 180, 112)'),
    (4, 'Best Boy', 'LOW', 'rgb(112, 180, 79)');

Insert into ForumCategories values
        (1, 'Suggestions', 'Suggestions for enhancing the site and service'),
        (2, 'News', 'Talks about news from the industry');

Insert into Threads values
    (1, 'First Thread on the forum!', 'Text of First Thead!', 'CLOSED', 1, '2021-09-1 12:11:32',
        '2021-09-2 15:45:40',
        'eabc11d0-e6da-49f8-a6db-6bceb84a06bc', 2),
    (2, 'Second Thread on the forum!', 'Text of Second Thread!', 'OPEN', 3, '2021-09-2 14:05:04',
        '2021-09-4 12:12:12',
        '467a809a-d893-48c2-85e2-82f9ce4b1560', 1);

Insert into ThreadTags value (1, 1), (1, 2), (1, 3), (1, 4), (2, 2), (2, 3);

Insert into ThreadUserStatuses value
    ('eabc11d0-e6da-49f8-a6db-6bceb84a06bc', 2, true, false),
    ('467a809a-d893-48c2-85e2-82f9ce4b1560', 1, false, false);

Insert into Posts value
    (1, 'First Post on the forum!', 'Text of the first post on the forum', false, 13, 16, 0,
        '2021-09-1 14:10:12', '2021-09-2 18:30:59',
        '467a809a-d893-48c2-85e2-82f9ce4b1560', 1),
    (2, 'Second Post on the forum!', 'Text of the second post on the forum', false, 2, 1, 0,
        '2021-09-2 15:34:21', '2021-09-2 15:34:21',
        'eabc11d0-e6da-49f8-a6db-6bceb84a06bc', 1),
    (3, 'Third Post on the forum!', 'Text of the third post on the forum', true, 0, 35, 0,
        '2021-09-3 16:45:01', '2021-09-3 16:45:01',
        '467a809a-d893-48c2-85e2-82f9ce4b1560', 1),
    (4, 'Fourth Post on the forum!', 'Text of the fourth post on the forum', false, 10, 5, 2,
        '2021-09-4 16:45:01', '2021-09-5 16:45:01',
        'eabc11d0-e6da-49f8-a6db-6bceb84a06bc', 2);

Insert Into PostUserStatuses values
    ('eabc11d0-e6da-49f8-a6db-6bceb84a06bc', 1, true, false, false),
    ('eabc11d0-e6da-49f8-a6db-6bceb84a06bc', 3, false, true, false),
    ('467a809a-d893-48c2-85e2-82f9ce4b1560', 4, false, false, true);