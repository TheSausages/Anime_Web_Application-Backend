/*Drop the Database*/
drop database data;

/*Create the database again*/
create database data;
use data;

/*Start creating tables*/

/*Create tables connected to the User*/
CREATE TABLE `Users`
(
    `UserID`            varchar(36) NOT NULL,
    `Username`          varchar(45) NOT NULL UNIQUE,
    `NrOfPosts`         smallint    NOT NULL DEFAULT 0,
    `WatchTime`         int         NOT NULL DEFAULT 0,
    `AchievementPoints` mediumint   NOT NULL DEFAULT 0,

    PRIMARY KEY (`UserID`)
) COMMENT ='Class containing a bit of information about a User - the ID a the ID inside keycloak Authorization Server';

CREATE TABLE `Achievements`
(
    `AchievementID`     int          NOT NULL auto_increment,
    `Name`              varchar(45)  NOT NULL,
    `Description`       varchar(600) NOT NULL DEFAULT 'No Description given',
    `IconPath`          varchar(45)  NOT NULL,
    `AchievementPoints` int          NOT NULL DEFAULT 10,

    PRIMARY KEY (`AchievementID`)
) COMMENT='Table that contains achievements that can be earned by Players. How these achievements can be earned is located inside the Backend';

CREATE TABLE `UserAchievements`
(
    `AchievementID` int         NOT NULL,
    `UserID`        varchar(45) NOT NULL,

    PRIMARY KEY (`UserID`, `AchievementID`),
    KEY `FK_UserAchievements_Achievements` (`AchievementID`),
    CONSTRAINT `FK_1` FOREIGN KEY `FK_UserAchievements_Achievements` (`AchievementID`) REFERENCES `Achievements` (`AchievementID`),
    KEY `FK_UserAchievements_User` (`UserID`),
    CONSTRAINT `FK_2` FOREIGN KEY `FK_UserAchievements_User` (`UserID`) REFERENCES `Users` (`UserID`)
) COMMENT='Table that contains what Achievements a given User has';


/*Create Tables connected to the Forum*/
CREATE TABLE `ForumCategories`
(
    `CategoryID`          int         NOT NULL auto_increment,
    `CategoryName`        varchar(45) NOT NULL,
    `CategoryDescription` varchar(150) DEFAULT 'No Description Given',

    PRIMARY KEY (`CategoryID`)
) COMMENT ='Table Containing the Categories a given Tread can Have';

CREATE TABLE `Tags`
(
    `TagID`         int                                  NOT NULL auto_increment,
    `TagName`       varchar(45)                          NOT NULL,
    `TagImportance` enum ('LOW','MEDIUM','HIGH','ADMIN') NOT NULL DEFAULT 'LOW',
    `TagColor`      varchar(18)                                   DEFAULT 'rgb(0, 255, 0)',

    PRIMARY KEY (`TagID`)
) COMMENT='Table of Tags that a Thread on the forum can have';

CREATE TABLE `Threads`
(
    `ThreadID`     int                    NOT NULL auto_increment,
    `Title`        varchar(80)            NOT NULL,
    `ThreadText`   varchar(600)           NOT NULL DEFAULT 'No Text',
    `Status`       enum ('OPEN','CLOSED') NOT NULL DEFAULT 'OPEN',
    `NrOfPosts`    int                    NOT NULL DEFAULT 0,
    `creation`     datetime                        DEFAULT CURRENT_TIMESTAMP,
    `modification` datetime ON UPDATE CURRENT_TIMESTAMP,
    `CreatorID`    varchar(45)            NOT NULL,
    `CategoryID`   int                    NOT NULL,

    PRIMARY KEY (`ThreadID`),
    KEY `FK_Thread_ForumCategory` (`CategoryID`),
    CONSTRAINT `FK_3` FOREIGN KEY `FK_Thread_ForumCategory` (`CategoryID`) REFERENCES ForumCategories (`CategoryID`)
) COMMENT='Table containing information about a Single Thread on the Forum';

CREATE TABLE `ThreadTags`
(
    `ThreadID` int NOT NULL,
    `TagID`    int NOT NULL,

    PRIMARY KEY (`ThreadID`, `TagID`),
    KEY `FK_ThreadTags_Tags` (`TagID`),
    CONSTRAINT `FK_4` FOREIGN KEY `FK_ThreadTags_Tags` (`TagID`) REFERENCES `Tags` (`TagID`),
    KEY `FK_ThreadTags_Thread` (`ThreadID`),
    CONSTRAINT `FK_5` FOREIGN KEY `FK_ThreadTags_Thread` (`ThreadID`) REFERENCES `Threads` (`ThreadID`)
) COMMENT='Table that contains information what tags a thread has';

CREATE TABLE `ThreadUserStatus`
(
    `UserID`     varchar(45) NOT NULL,
    `ThreadID`   int         NOT NULL,
    `IsWatching` boolean     NOT NULL DEFAULT false,
    `IsBlocked`  boolean     NOT NULL DEFAULT false,

    PRIMARY KEY (`UserID`, `ThreadID`),
    KEY `FK_ThreadUserStatus_thread` (`ThreadID`),
    CONSTRAINT `FK_6` FOREIGN KEY `FK_ThreadUserStatus_thread` (`ThreadID`) REFERENCES `Threads` (`ThreadID`),
    KEY `FK_ThreadUserStatus_User` (`UserID`),
    CONSTRAINT `FK_7` FOREIGN KEY `FK_ThreadUserStatus_User` (`UserID`) REFERENCES `Users` (`UserID`)
) COMMENT='Table that contains a users settings on a thread';

CREATE TABLE `Posts`
(
    `PostID`       int          NOT NULL auto_increment,
    `Title`        varchar(80)  NOT NULL,
    `PostText`     varchar(600) NOT NULL,
    `IsBlocked`    boolean      NOT NULL DEFAULT false,
    `NrOfPlus`     int          NOT NULL DEFAULT 0,
    `NrOfMinus`    int          NOT NULL DEFAULT 0,
    `NrOfReports`  int          NOT NULL DEFAULT 0,
    `creation`     datetime              DEFAULT CURRENT_TIMESTAMP,
    `modification` datetime ON UPDATE CURRENT_TIMESTAMP,
    `UserID`       varchar(45)  NOT NULL,
    `ThreadID`     int          NOT NULL,

    PRIMARY KEY (`PostID`),
    KEY `FK_Post_Thread` (`ThreadID`),
    CONSTRAINT `FK_8` FOREIGN KEY `FK_Post_Thread` (`ThreadID`) REFERENCES `Threads` (`ThreadID`),
    KEY `FK_Post_User` (`UserID`),
    CONSTRAINT `FK_9` FOREIGN KEY `FK_Post_User` (`UserID`) REFERENCES `Users` (`UserID`)
) COMMENT ='Table containing Information about a single Post on the Forum';

CREATE TABLE `PostUserStatus`
(
    `UserID`     varchar(45) NOT NULL,
    `PostID`     int         NOT NULL,
    `IsLiked`    boolean     NOT NULL DEFAULT false,
    `IsDisliked` boolean     NOT NULL DEFAULT false,
    `IsReported` boolean     NOT NULL DEFAULT false,

    PRIMARY KEY (`UserID`, `PostID`),
    KEY `FK_PostUserStatus_User` (`UserID`),
    CONSTRAINT `FK_10` FOREIGN KEY `FK_PostUserStatus_User` (`UserID`) REFERENCES `Users` (`UserID`),
    KEY `FK_PostUserStatus_Post` (`PostID`),
    CONSTRAINT `FK_11` FOREIGN KEY `FK_PostUserStatus_Post` (`PostID`) REFERENCES `Posts` (`PostID`)
) COMMENT ='Table containing status of a User on a Post';

/*Create tables connected to Anime Info*/

CREATE TABLE `Reviews`
(
    `ReviewID`    int          NOT NULL auto_increment,
    `ReviewTitle` varchar(100) NOT NULL DEFAULT 'No Title Written',
    `ReviewText`  varchar(300),
    `NrOfHelpful` int          NOT NULL DEFAULT 0,
    `NrOfPlus`    int          NOT NULL DEFAULT 0,
    `NrOfMinus`   int          NOT NULL DEFAULT 0,

    PRIMARY KEY (`ReviewID`)
) COMMENT='Table containing information about the review of a User';

CREATE TABLE `AnimeUserInfos`
(
    `UserID`           varchar(45)                                                          NOT NULL,
    `AnimeID`          int                                                                  NOT NULL,
    `Status`           enum ('NO_STATUS','WATCHING', 'COMPLETED','DROPPED','PLAN_TO_WATCH') NOT NULL DEFAULT 'NO_STATUS',
    `WatchStartDate`   date                                                                 NULL,
    `WatchEndDate`     date                                                                 NULL,
    `NrOfEpisodesSeen` smallint                                                             NOT NULL DEFAULT 0,
    `IsFavourite`      boolean                                                              NOT NULL DEFAULT false,
    `modification`     datetime on update CURRENT_TIMESTAMP,
    `DidReview`        boolean                                                              NOT NULL DEFAULT false,
    `Grade`            int                                                                  NULL     DEFAULT 5,
    `ReviewID`         int                                                                  NULL,

    PRIMARY KEY (`UserID`, `AnimeID`),
    KEY `FK_AnimeUserInfo_Review` (`ReviewID`),
    CONSTRAINT `FK_177` FOREIGN KEY `FK_AnimeUserInfo_Review` (`ReviewID`) REFERENCES `Reviews` (`ReviewID`),
    KEY `FK_AnimeUserInfo_User` (`UserID`),
    CONSTRAINT `FK_51` FOREIGN KEY `FK_AnimeUserInfo_User` (`UserID`) REFERENCES `Users` (`UserID`)
) COMMENT='Table that contains Data about a users information on an Anime. Uses a composite primary key consisting of a UserID and AnimeID - the second one is received from AniList API.';

