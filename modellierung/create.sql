CREATE TABLE `GroupRight` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
) COLLATE 'latin1_german2_ci';

CREATE TABLE `Group` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
    `isActive` tinyint(1) unsigned NOT NULL
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
   `description` varchar(128) COLLATE 'latin1_german2_ci' NULL,
   `minimumStock` int(10) unsigned NULL,
   `quotaStock` int(10) unsigned NULL,
   `currentStock` int(10) unsigned NULL,
   `batchSize` int(10) unsigned NULL,
   `mtkIntervall` int(10) unsigned NULL,
   `stkIntervall` int(10) unsigned NULL,
   `creation` timestamp NOT NULL,
   `silenceWarning` tinyint(1) unsigned NOT NULL
) COLLATE 'latin1_german2_ci';

CREATE TABLE `Stock` (
   `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
   `volume` int(10) unsigned NOT NULL,
   `date` date NULL,
   `mtk` date NULL,
   `stk` date NULL,
   `inventarNo` varchar(128) COLLATE 'latin1_german2_ci' NULL,
   `serialNo` varchar(128) COLLATE 'latin1_german2_ci' NULL,
   `umdns` varchar(128) COLLATE 'latin1_german2_ci' NULL,
   `batchNo` varchar(128) COLLATE 'latin1_german2_ci' NULL,
   `creation` timestamp NOT NULL,
   `escalationAck` int(10) unsigned NOT NULL,
   `material_id` int(10) unsigned NOT NULL,
   `location_id` int(10) unsigned NOT NULL,
   `type_id` int(10) unsigned NOT NULL,
   `message_id` int(10) unsigned NULL
   CONSTRAINT `Constr_Stock_Material`
       FOREIGN KEY `material_fk` (`material_id`) REFERENCES `Material` (`id`)
       ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `Constr_Stock_Location`
       FOREIGN KEY `location_fk` (`location_id`) REFERENCES `Location` (`id`)
       ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `Constr_Stock_Message`
       FOREIGN KEY `message_fk` (`message_id`) REFERENCES `Message` (`id`)
       ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `Constr_Stock_Type`
       FOREIGN KEY `type_fk` (`type_id`) REFERENCES `Type` (`id`)
       ON DELETE CASCADE ON UPDATE CASCADE
) COLLATE 'latin1_german2_ci';

CREATE TABLE `Operation` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
) COLLATE 'latin1_german2_ci';

CREATE TABLE `Logbook` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
    `date` TIMESTAMP NOT NULL,
    `material_id` int(10) unsigned NULL,
    `operation_id` int(10) unsigned NOT NULL,
    `user_id` int(10) unsigned NOT NULL
    CONSTRAINT `Constr_Logbook_User`
        FOREIGN KEY `user_fk` (`user_id`) REFERENCES `User` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_Logbook_Material`
        FOREIGN KEY `material_fk` (`material_id`) REFERENCES `Material` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_Logbook_Operation`
        FOREIGN KEY `operation_fk` (`operation_id`) REFERENCES `Operation` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) COLLATE 'latin1_german2_ci';
