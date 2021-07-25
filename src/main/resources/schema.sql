--Drop all tables from the database

--tables with Anime Information
drop table if exists AnimeUserInfo;
drop table if exists Grade;
drop table if exists Review;

--tables with Forum Information
drop table if exists ThreadTags;
drop table if exists ThreadUserStatus;
drop table if exists Tags;
drop table if exists Post;
drop table if exists Thread;
drop table if exists ForumCategory;

--tables with User Information
drop table if exists UserAchievements;
drop table if exists Achievements;
drop table if exists Users;



--Create new tables

--tables with User Information
CREATE TABLE `Users`
(
 `UserID`           varchar(45) NOT NULL ,
 `NrOfPosts`        smallint NOT NULL DEFAULT 0,
 `WatchTime`        int NOT NULL DEFAULT 0,
 `AchievementPoints` mediumint NOT NULL DEFAULT 0,

PRIMARY KEY (`UserID`)
) COMMENT='Class contaning a bit of information about a User - the ID a the ID inside keycloak Authorization Server';

CREATE TABLE `Achievements`
(
 `AchievementID`    int NOT NULL ,
 `Name`             varchar(45) NOT NULL ,
 `Description`      varchar(600) NOT NULL DEFAULT "No Description given",
 `IconPath`         varchar(45) NOT NULL ,
 `AchievementPoints` int NOT NULL DEFAULT 10,

PRIMARY KEY (`AchievementID`)
) COMMENT='Table that contains achievements that can be earned by Players. How these achievements can be earned is located inside the Backend';

CREATE TABLE `UserAchievements`
(
 `AchievementID` int NOT NULL ,
 `UserID`        varchar(45) NOT NULL ,

PRIMARY KEY (`UserID`, `AchievementID`),
KEY `FK_UserAchievements_Achievements` (`AchievementID`),
CONSTRAINT `FK_39` FOREIGN KEY `FK_UserAchievements_Achievements` (`AchievementID`) REFERENCES `Achievements` (`AchievementID`),
KEY `FK_UserAchievements_User` (`UserID`),
CONSTRAINT `FK_46` FOREIGN KEY `FK_UserAchievements_User` (`UserID`) REFERENCES `Users` (`UserID`)
) COMMENT='Table that contains what Achievements a given User has';



--tables with Forum Information

CREATE TABLE `ForumCategory`
(
 `CategoryID`          int NOT NULL ,
 `CategoryName`        varchar(45) NOT NULL ,
 `CategoryDescription` varchar(150) NOT NULL DEFAULT "No Description Given",

PRIMARY KEY (`CategoryID`)
) COMMENT='Table Containing the Categories a given Tread can Have';

CREATE TABLE `Tags`
(
 `TagID`         int NOT NULL auto_increment,
 `TagName`       varchar(45) NOT NULL ,
 `TagImportance` enum('low','medium','high','admin') NOT NULL DEFAULT 'low',

PRIMARY KEY (`TagID`)
) COMMENT='Table of Tags that a Thread on the forum can have';

CREATE TABLE `Thread`
(
 `ThreadID`   int NOT NULL auto_increment,
 `Title`      varchar(45) NOT NULL ,
 `Status`     enum('Open','Closed') NOT NULL DEFAULT 'Open',
 `CategoryID` int NOT NULL ,

PRIMARY KEY (`ThreadID`),
KEY `FK_Thread_ForumCategory` (`CategoryID`),
CONSTRAINT `FK_169` FOREIGN KEY `FK_Thread_ForumCategory` (`CategoryID`) REFERENCES `ForumCategory` (`CategoryID`)
) COMMENT='Table containing information about a Single Thread on the Forum';

CREATE TABLE `ThreadTags`
(
 `ThreadID` int NOT NULL ,
 `TagID`    int NOT NULL ,

PRIMARY KEY (`ThreadID`, `TagID`),
KEY `FK_ThreadTags_Tags` (`TagID`),
CONSTRAINT `FK_158` FOREIGN KEY `FK_ThreadTags_Tags` (`TagID`) REFERENCES `Tags` (`TagID`),
KEY `FK_ThreadTags_Thread` (`ThreadID`),
CONSTRAINT `FK_154` FOREIGN KEY `FK_ThreadTags_Thread` (`ThreadID`) REFERENCES `Thread` (`ThreadID`)
) COMMENT='Table that contains information what tags a thread has';

CREATE TABLE `ThreadUserStatus`
(
 `UserID`     varchar(45) NOT NULL ,
 `ThreadID`   int NOT NULL ,
 `IsWatching` boolean NOT NULL DEFAULT false,
 `IsBlocked`  boolean NOT NULL DEFAULT false,

PRIMARY KEY (`UserID`, `ThreadID`),
KEY `FK_ThreadUserStatus_thread` (`ThreadID`),
CONSTRAINT `FK_133` FOREIGN KEY `FK_ThreadUserStatus_thread` (`ThreadID`) REFERENCES `Thread` (`ThreadID`),
KEY `FK_ThreadUserStatus_User` (`UserID`),
CONSTRAINT `FK_129` FOREIGN KEY `FK_ThreadUserStatus_User` (`UserID`) REFERENCES `Users` (`UserID`)
) COMMENT='Table that contains a users settings on a thread';

CREATE TABLE `Post`
(
 `PostID`    int NOT NULL auto_increment,
 `Title`     varchar(45) NOT NULL ,
 `PostText`  varchar(600) NOT NULL ,
 `IsBlocked` boolean NOT NULL DEFAULT false,
 `NrOfPlus`  int NOT NULL DEFAULT 0,
 `NrOfMinus` int NOT NULL DEFAULT 0,
 `UserID`    varchar(45) NOT NULL ,
 `ThreadID`  int NOT NULL ,

PRIMARY KEY (`PostID`),
KEY `FK_Post_Thread` (`ThreadID`),
CONSTRAINT `FK_183` FOREIGN KEY `FK_Post_Thread` (`ThreadID`) REFERENCES `Thread` (`ThreadID`),
KEY `FK_Post_User` (`UserID`),
CONSTRAINT `FK_142` FOREIGN KEY `FK_Post_User` (`UserID`) REFERENCES `Users` (`UserID`)
) COMMENT='Table containing Information about a single Post on the Forum';


--tables with Anime Information

CREATE TABLE `Grade`
(
 `GradeID`   int NOT NULL auto_increment,
 `Scale`     int NOT NULL ,
 `GradeName` varchar(45) NOT NULL ,

PRIMARY KEY (`GradeID`)
) COMMENT='Grades that the User can give to an Anime';

CREATE TABLE `Review`
(
 `ReviewID`     int NOT NULL auto_increment,
 `ReviewText`   varchar(300) NOT NULL ,
 `NrOfHelpful` int NOT NULL DEFAULT 0,
 `NrOfPlus`     int NOT NULL DEFAULT 0,
 `NrOfMinus`    int NOT NULL DEFAULT 0,

PRIMARY KEY (`ReviewID`)
) COMMENT='Table containing information about the review of a User';

CREATE TABLE `AnimeUserInfo`
(
 `UserID`           varchar(45) NOT NULL ,
 `AnimeID`          int NOT NULL ,
 `Status`           enum('no status','watching', 'completed','dropped','plan to watch') NOT NULL DEFAULT 'no status',
 `WatchStartDate`   date NULL ,
 `WatchEndDate`     date NULL ,
 `NrOfEpisodesSeen` smallint NOT NULL DEFAULT 0,
 `IsFavourite`      boolean NOT NULL DEFAULT false,
 `DidReview`        boolean NOT NULL DEFAULT false,
 `DidGrade`         boolean NOT NULL DEFAULT false,
 `ReviewID`         int NULL ,
 `GradeID`          int NULL ,

PRIMARY KEY (`UserID`, `AnimeID`),
KEY `FK_AnimeUserInfo_Grade` (`GradeID`),
CONSTRAINT `FK_180` FOREIGN KEY `FK_AnimeUserInfo_Grade` (`GradeID`) REFERENCES `Grade` (`GradeID`),
KEY `FK_AnimeUserInfo_Review` (`ReviewID`),
CONSTRAINT `FK_177` FOREIGN KEY `FK_AnimeUserInfo_Review` (`ReviewID`) REFERENCES `Review` (`ReviewID`),
KEY `FK_AnimeUserInfo_User` (`UserID`),
CONSTRAINT `FK_51` FOREIGN KEY `FK_AnimeUserInfo_User` (`UserID`) REFERENCES `Users` (`UserID`)
) COMMENT='Table that contains Data about a users information on an Anime. Uses a composite primary key consisting of a UserID and AnimeID - the second one is received from AniList API.';

