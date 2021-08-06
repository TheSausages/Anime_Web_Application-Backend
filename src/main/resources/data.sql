Insert into Users
values ('dc6f1887-3ef1-4811-8ce9-eba78052f0e0', 0, 0, 0);

Insert into Achievements
values (1, 'Welcome onboard!', 'Achievement unlocked after registering', 'noPathForNow', 10);

Insert into UserAchievements
values (1, 'dc6f1887-3ef1-4811-8ce9-eba78052f0e0');

Insert into Grades
values (1, 0, 'Worst thing ever');

Insert into Reviews
values (1, 'Ayyyy thats something', 1, 2, 0);

Insert into AnimeUserInfos value ('dc6f1887-3ef1-4811-8ce9-eba78052f0e0', 35682, 'COMPLETED', '2018-09-26',
                                  '2019-12-12', 2, false, true, true, 1, 1);

Insert into Tags value (1, 'Episode Discussion', 'MEDIUM');

Insert into ForumCategories
values (1, 'Suggestions', 'Category for suggestions for enhancing the site and service');

Insert into Threads
values (1, 'First Thread on the forum!', 'OPEN', 1);

Insert into ThreadTags value (1, 1);

Insert into ThreadUserStatus value ('dc6f1887-3ef1-4811-8ce9-eba78052f0e0', 1, true, false);

Insert into Posts value (1, 'First Post on the forum!', 'Text of the first post on the forum', false, 2, 1,
                         'dc6f1887-3ef1-4811-8ce9-eba78052f0e0', 1);