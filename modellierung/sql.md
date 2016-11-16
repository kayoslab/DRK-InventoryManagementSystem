# ER-Model

![ER-Model](./UML/ER/ER-Model.png)
Vorläufiger Entwurf des ER-Models, **request-for-comments**.

# SQL-Aufruf

Zunächst wird für das Projekt die Collation `latin1_german2_ci` verwendet, da diese am ehesten der "Telefonbuchsortierung" im deutschen entspricht. Alternativ wäre es auch denkbar gewesen `utf8_general_ci` zu verwenden, diese kann jedoch zu unerwarteten Sortierungen beim Aufkommen von Umlauten führen.

Für die Implementierung der `BOOLEAN` Werte wie passwordChanged und silenceWarning wird auf den Datentyp `TINYINT(1)` zurückgegriffen. Zum Thema BOOLEAN sagt die Dokumentation von MySQL folgendes:

- Bool, Boolean: These types are synonyms for TINYINT(1). A value of zero is considered false. Non-zero values are considered true.
- As of MySQL 5.0.3, the BIT data type is used to store bit-field values. A type of BIT(M) enables storage of M-bit values. M can range from 1 to 64.
- We intend to implement full boolean type handling, in accordance with standard SQL, in a future MySQL release.

Die Constraints der Tabellen `GroupHasRights` und `UserIsMemberOfGroup` müssen bezüglich ihrer `ON DELETE` und `ON UPDATE` Funktionen geprüft und besprochen werden. CASCADE gilt daher nur als Platzhalter für ein sinnvolles Verhalten, welches in den Anforderungen festgehalten werden sollte.

## Group has Rights

Es handelt sich um eine **Many-To-Many Relationship** zwischen den Tabellen `Group` und `GroupRight`.

```sql
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
```
## User is Member of Group

Es handelt sich um eine **Many-To-Many Relationship** zwischen den Tabellen `User` und `Group`.

```sql
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
```

## Material

Beinhaltet verschiedene **To-One Relationships** zwischen der `Material` Tabelle und den `Location`, `Message` und `Type` Tabellen.

```sql
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

CREATE TABLE `StockObject` (
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
   `silenceWarning` tinyint(1) unsigned NOT NULL,
   CONSTRAINT `Constr_Stock_Type`
       FOREIGN KEY `type_fk` (`type_id`) REFERENCES `Type` (`id`)
       ON DELETE CASCADE ON UPDATE CASCADE
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
       ON DELETE CASCADE ON UPDATE CASCADE
) COLLATE 'latin1_german2_ci';
```

## Logbook

Beinhaltet verschiedene **To-One Relationships** zwischen der `Logbook` Tabelle und den `Material` und `Operation` Tabellen.

```sql
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
```


## Insert

```sql
INSERT INTO `Operation` (title)
VALUES
    ('create-user'),
    ('delete-user'),
    ('modify-user'),
    ('user-login'),
    ('user-logout'),
    ('create-userRight'),
    ('delete-userRight'),
    ('modify-userRight'),
    ('create-material'),
    ('delete-material'),
    ('modify-material'),
    ('material-increase'),
    ('material-decrease'),
    ('material-correction');

INSERT INTO `GroupRight` (title)
VALUES
    ('create-user'),
    ('delete-user'),
    ('modify-user'),
    ('create-userRight'),
    ('delete-userRight'),
    ('modify-userRight'),
    ('create-material'),
    ('delete-material'),
    ('modify-material'),
    ('material-increase'),
    ('material-decrease'),
    ('material-correction'),
    ('consumable-material'),
    ('medical-material'),
    ('devices');

INSERT INTO `Group` (title)
VALUES
    ('admin'),
    ('user');

INSERT INTO `GroupHasRights`
VALUES
    (1,1),
    (1,2),
    (1,3),
    (1,4),
    (1,5),
    (1,6),
    (1,7),
    (1,8),
    (1,9),
    (1,10),
    (1,11),
    (1,12);

/* pw = md5(test) */
INSERT INTO `User` (username, firstname, name, password, passwordChanged)
VALUES ('root', 'test', 'test', '098f6bcd4621d373cade4e832627b4f6', 0);

INSERT INTO `UserIsMemberOfGroup`
VALUES (1,1);

INSERT INTO `Logbook` (title, material_id, operation_id, user_id)
VALUES ('Initialisation', NULL, 6, 1);
```

## Select

Alle Rechte, welche der Gruppe ``admin`` gewährt wurden:

```sql
SELECT
    `GroupRight`.*
FROM
    `GroupRight`
JOIN
    `GroupHasRights`
    ON `GroupRight`.`id` = `GroupHasRights`.`right`
WHERE
    `GroupHasRights`.`group` = 1
```


Alle User, die das Recht haben neue Benutzer anzulegen:

```sql
SELECT
    `Group`.*
FROM
    `Group`
JOIN
    `GroupHasRights`
    ON `Group`.`id` = `GroupHasRights`.`group`
WHERE
    `GroupHasRights`.`right` = 1
```


Alle User, die der Gruppe ``admin`` angehören:

```sql
SELECT
    `User`.*
FROM
    `User`
JOIN
    `UserIsMemberOfGroup`
    ON `User`.`id` = `UserIsMemberOfGroup`.`user`
WHERE
    `UserIsMemberOfGroup`.`group` = 1
```


Alle Gruppen, in denen der Nutzer ``root`` ist:

```sql
SELECT
    `Group`.*
FROM
    `Group`
JOIN
    `UserIsMemberOfGroup`
    ON `Group`.`id` = `UserIsMemberOfGroup`.`group`
WHERE
    `UserIsMemberOfGroup`.`user` = 1
```
