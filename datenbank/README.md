# ER-Model

![ER-Model](./../modellierung/UML/ER/ER-Model.png)
Finales ER-Models.

# Allgemein

Zunächst wird für das Projekt die Collation `latin1_german2_ci` verwendet, da diese am ehesten der "Telefonbuchsortierung" im deutschen entspricht. Alternativ wäre es auch denkbar gewesen `utf8_general_ci` zu verwenden, diese kann jedoch zu unerwarteten Sortierungen beim Aufkommen von Umlauten führen.

Für die Implementierung der *BOOLEAN* Werte wie `passwordChanged` und `silencedWarnings` wird auf den Datentyp *TINYINT(1)* zurückgegriffen. Zum Thema *BOOLEAN* sagt die MySQL Dokumentation folgendes:

- Bool, Boolean: These types are synonyms for TINYINT(1). A value of zero is considered false. Non-zero values are considered true.
- As of MySQL 5.0.3, the BIT data type is used to store bit-field values. A type of BIT(M) enables storage of M-bit values. M can range from 1 to 64.
- We intend to implement full boolean type handling, in accordance with standard SQL, in a future MySQL release.

Die Constraints der Tabellen `GroupHasRights` und `UserIsMemberOfGroup` nutzen als *ON DELETE* und *ON UPDATE* Verhalten die Einstellung *CASCADE*. Diese sorgt dafür, dass beim löschen von Nutzern respektive von Gruppen die dazugehörigen Fremdschlüssel-Beziehungen ebenfalls entfernt werden und wir uns im Laufe der Softwareentwicklung keine Sorgen um das Pflegen von nicht länger relevanten Daten (sog. Datenleichen) machen müssen.

# EER-Model

![EER-Model](./../modellierung/UML/EER/EER-Model.png)
Finales EER-Models.

# SQL-Aufruf

## Group has Rights

Die Tabelle `GroupRights` beinhaltet einen festen Datensatz von zu vergebenden Gruppenrechten, welche vom Datenbankadministrator (respektive vom Entwickler) vorgegeben werden müssen und im Programmcode fest verdrahtet sind. Diese wird durch ein Insert Skript für die Auslieferung an den Kunden vorausgefüllt. Die Tabelle `Group` beinhaltet eine vorgegebene Auswahl von Gruppen, welche für den üblichen Betrieb ausreichen sollten. Sie kann jedoch von einem Gruppenadministrator weiter gefüllt werden. Über eine *Many-To-Many Relationship* werden jeder Gruppe mehre Rechte zugeordnet.

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

Die Gruppe der User wird zwar im direkten Anwendungsfall mit Daten vorausgefüllt, bis auf den Administrator ist dies jedoch an sich nicht notwendig. Alle weiteren Nutzer können auch über das Userinterface angelegt und gepflegt werden. Zwischen `User` und `Group` besteht eine *Many-To-Many Relationship*.

```sql
CREATE TABLE `User` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
    `firstname` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL,
    `name` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL,
    `mail` varchar(128) COLLATE 'latin1_german2_ci' NULL,
    `creation` datetime NOT NULL,
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

## Lagerobjekt

Die Datenbank-Tabelle `Type` beinhaltet eine feste Anzahl von möglichen Typen zwischen denen unterschieden werden kann. Zum jetzigen Zeitpunkt umfasst diese Tabelle die Typen *Gerät*, *Medizinisches Material* und *Betreuungsmaterial*. In der Zukunft soll die Software um den Typ *Fahrzeug* erweitert werden. Die Tabelle `Location` beinhaltet alle möglichen Lagerorte, welche ebenfalls über das Userinterface gepflegt werden können.

Die Tatsächlichen zu lagernden Objekte werden durch einen Eintrag in der Tabelle `StockObject` repräsentiert. Diese können von den entsprechenden Geräte-, Material-, Betreuungs- Verantwortlichen angelegt werden, sollten diese in der Entsprechenden `Group` mit dem jeweiligen Recht eingetragen sein.

```sql
CREATE TABLE `Type` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
) COLLATE 'latin1_german2_ci';

CREATE TABLE `Location` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
) COLLATE 'latin1_german2_ci';


CREATE TABLE `StockObject` (
   `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
   `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
   `description` varchar(128) COLLATE 'latin1_german2_ci' NULL,
   `batchSize` int(10) unsigned NULL,
   `totalVolume` int(10) unsigned NULL,
   `mtkIntervall` int(10) unsigned NULL,
   `stkIntervall` int(10) unsigned NULL,
   `creation` datetime NOT NULL,
   `typeId` int(10) unsigned NOT NULL ,
   CONSTRAINT `Constr_Stock_Type`
        FOREIGN KEY `type_fk` (`typeId`) REFERENCES `Type` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) COLLATE 'latin1_german2_ci';
```

## Lagerbestand

Die Representation eines Lagerbestands wird wiederum durch einen Eintrag in der `StockValue` Tabelle dargestellt. Dieser beinhaltet eine Referenz zu einem Lagerobjekt (_StockObject_) so wie Referenzen auf `Location` und `Message`. Durch das gesonderte Speichern von Mindest- und Soll-Beständen pro Verbindung zwischen `StockObject` und `Location` kann für jedes Lager ein gesonderter Mindestbestand an Objekten festgelegt und explizit für diesen gewarnt werden.

``` sql
CREATE TABLE `Message` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
) COLLATE 'latin1_german2_ci';

CREATE TABLE `StockValue` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `volume` int(10) unsigned NOT NULL,
    `date` DATETIME NULL,
    `mtkDate` DATETIME NULL,
    `stkDate` DATETIME NULL,
    `inventoryNumber` varchar(128) COLLATE 'latin1_german2_ci' NULL,
    `serialNumber` varchar(128) COLLATE 'latin1_german2_ci' NULL,
    `umdns` varchar(128) COLLATE 'latin1_german2_ci' NULL,
    `batchNumber` varchar(128) COLLATE 'latin1_german2_ci' NULL,
    `minimumStock` int(10) unsigned NULL,
    `quotaStock` int(10) unsigned NULL,
    `silencedWarnings` tinyint(1) unsigned NOT NULL,
    `creation` datetime NOT NULL,
    `stockObjectId` int(10) unsigned NOT NULL,
    `locationId` int(10) unsigned NOT NULL,
    `messageId` int(10) unsigned NOT NULL,
    CONSTRAINT `Constr_StockValue_StockObject`
         FOREIGN KEY `stockObject_fk` (`stockObjectId`) REFERENCES `StockObject` (`id`)
         ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_StockValue_Location`
         FOREIGN KEY `location_fk` (`locationId`) REFERENCES `Location` (`id`)
         ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_StockValue_Message`
         FOREIGN KEY `message_fk` (`messageId`) REFERENCES `Message` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) COLLATE 'latin1_german2_ci';
```

## Logbook

Die `Operation` Tabelle wird mit einem Datensatz möglicher vom User durchzuführender Operationen gefüllt und über eine *To-One Relationships* mit einem automatisch generierten Eintrag in der `Logbook` Tabelle in Verbindung gesetzt. Auf diesem Weg bietet unser Softwaresystem die Möglichkeit, sollte dies gänzlich ausgereizt werden wollen, jede Bestandsveränderung pro Nutzer festzuhalten und so die Ursache für Fehlbestände oder Fehlbuchungen auszumachen. Zum derzeitigen Standpunkt sollen nach Rücksprache jedoch nur rudimentäre Log-Funktionen verwendet werden.

```sql
CREATE TABLE `Operation` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE
) COLLATE 'latin1_german2_ci';

CREATE TABLE `Logbook` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` varchar(128) COLLATE 'latin1_german2_ci' NOT NULL UNIQUE,
    `date` TIMESTAMP NOT NULL,
    `userId` int(10) unsigned NOT NULL,
    `stockObjectId` int(10) unsigned NOT NULL,
    `operationId` int(10) unsigned NULL,
    CONSTRAINT `Constr_Logbook_User`
        FOREIGN KEY `user_fk` (`userId`) REFERENCES `User` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_Logbook_StockObject`
        FOREIGN KEY `stockObject_fk` (`stockObjectId`) REFERENCES `StockObject` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_Logbook_Operation`
        FOREIGN KEY `operation_fk` (`operationId`) REFERENCES `Operation` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) COLLATE 'latin1_german2_ci';
```
