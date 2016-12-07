# Source

## stockManagement

Allgemeine implementierung einer Desktop Applikation für den Einsatz am Client Computer nach allen vorher definierten Anforderungen.

## stockManagement-db-worker

Hintergrundprozess, welcher mittels cron-job auf dem Server getriggert wird. Dieser aktualisert die Datenbank und versendet ggf. E-Mails, falls Sollbestände oder kritische Termine unterschritten werden.

## stockManagement-importer

Einfaches Import Script, welches aus gegebenen .csv Dateien einen SQL-Insert Aufruf generiert. Dieser Code soll nicht fest ins Projekt integriert werden, da auf die Textvalidierung keinen großen Wert gelegt wird und es ggf. zu Datenbankinkosistenzen führen könnte.

## stockManagement-online

Testimplementierung einer Webseite zum Login, welcher ggf. eine komplette Inventarliste anzeigen soll.
