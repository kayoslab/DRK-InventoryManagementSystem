# SQL-Aufruf

## Group has Rights

Es handelt sich um eine **Many-To-Many Relationship** zwischen den Tabellen Group und GroupRight.

    CREATE TABLE `GroupRight` (
        `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
        `groups` int(10) unsigned NOT NULL
    ) COLLATE 'latin1_german2_ci';

    CREATE TABLE `Group` (
        `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
    ) COLLATE 'latin1_german2_ci';

    CREATE TABLE `GroupHasRights` (
        `group` int(10) unsigned NOT NULL,
        `right` int(10) unsigned NOT NULL,
    CONSTRAINT `Constr_GroupHasRights`
        FOREIGN KEY `group_fk` (`group`) REFERENCES `Group` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_RightHasGroups`
        FOREIGN KEY `right_fk` (`right`) REFERENCES `GroupRight` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
    ) COLLATE 'latin1_german2_ci';

## User is Member of Group

Es handelt sich um eine **Many-To-Many Relationship** zwischen den Tabellen User und Group.

    CREATE TABLE `User` (
        `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `username` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
        `firstname` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL,
        `name` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL,
        `creation` timestamp NOT NULL,
        `password` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL,
        `passwordChanged` tinyint(1) unsigned NOT NULL
    ) COLLATE 'latin1_german2_ci';

    CREATE TABLE `UserIsMemberOfGroup` (
        `user` int(10) unsigned NOT NULL,
        `group` int(10) unsigned NOT NULL,
    CONSTRAINT `Constr_UserHasGroup`
        FOREIGN KEY `user_fk` (`user`) REFERENCES `User` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_GroupHasUser`
        FOREIGN KEY `group_fk` (`group`) REFERENCES `Group` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
    ) COLLATE 'latin1_german2_ci';

## Material

Beinhaltet verschiedene **To-One Relationships** zwischen der Material Tabelle und den Location, Message und Type Tabellen.

    CREATE TABLE `Location` (
        `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
    ) COLLATE 'latin1_german2_ci';

    CREATE TABLE `Message` (
        `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
        `escalation` int(10) unsigned NOT NULL UNIQUE
    ) COLLATE 'latin1_german2_ci';

    CREATE TABLE `Type` (
        `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
    ) COLLATE 'latin1_german2_ci';

    CREATE TABLE `Material` (
       `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
       `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
       `minimumStock` int(10) unsigned NULL,
       `currentStock` int(10) unsigned NULL,
       `batchSize` int(10) unsigned NULL,
       `date` date NULL,
       `mtk` date NULL,
       `stk` date NULL,
       `mtkIntervall` int(10) unsigned NULL,
       `stkIntervall` int(10) unsigned NULL,
       `creation` timestamp NOT NULL,
       `inventarNo` varchar(128) COLLATE 'latin1_german2_ci' NULL,
       `serialNo` varchar(128) COLLATE 'latin1_german2_ci' NULL,
       `umdns` varchar(128) COLLATE 'latin1_german2_ci' NULL,
       `silenceWarning` tinyint(1) unsigned NOT NULL,
       `location_id` int(10) unsigned NOT NULL,
       `type_id` int(10) unsigned NOT NULL,
       `message_id` int(10) unsigned NULL
    ) COLLATE 'latin1_german2_ci';

## Logbook

Beinhaltet verschiedene **To-One Relationships** zwischen der Logbook Tabelle und den Material und Operation Tabellen.

    CREATE TABLE `Operation` (
        `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
    ) COLLATE 'latin1_german2_ci';

    CREATE TABLE `Logbook` (
        `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
        `material_id` int(10) unsigned NOT NULL,
        `operation_id` int(10) unsigned NOT NULL,
        `user_id` int(10) unsigned NOT NULL
    ) COLLATE 'latin1_german2_ci';
