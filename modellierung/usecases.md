# Übersicht

1. Anmelden/Abmelden
2. Kennwörter
3. Gruppen
4. Benutzer
5. Geräte, SAN-Materialien, Verbrauchsmaterialien
6. Lagerorte
7. Bestandsänderungen
8. Listen/Alarme
9. Sonstige Funktionen



## 1. Anmelden/Abmelden

| Name: | Anmelden |
| --- | --- |
| Primärer Nutzer: | Admin, User |
| Ziel (des Nutzers): | Im System angemeldet zu sein |
| Vorbedingung: | 1. Der Nutzer besitzt gültige LogIn-Daten<br>2. Das System läuft bereits<br>3. Kein anderer Benutzer ist momentan angemeldet |
| Nachbedingung: | 1. Dem Nutzer stehen die Funktionen zu Verfügung |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer gibt seine Benutzerdaten ein und bestätigt anschließend<br>2. Das System prüft, ob Benutzername vorhanden ist und ob das eingegebene Passwort korrekt ist<br>3. Das System öffnet den Menübereich |
| Erweiterung: | 1. und <br>2. wie Standardablauf<br>3. Das System bricht die Anmeldung mit der Meldung &quot;Passwort oder Benutzername falsch&quot; ab |



| Name: | Abmelden |
| --- | --- |
| Primärer Nutzer: | Admin, User |
| Ziel (des Nutzers): | Vom System abgemeldet zu sein |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet |
| Nachbedingung: | 1. Dem Nutzer stehen die Funktionen nicht mehr zur Verfügung |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer wählt die Funktion &quot;Abmelden&quot; aus<br>2. Das System zeigt dem Nutzer die Meldung &quot;Alle nicht gespeicherten Vorgänge gehen beim Abmelden verloren. Wollen sie sich wirklich abmelden?&quot;<br>3. Der Nutzer bestätigt die Meldung<br>4. Das System schließt die Datenbankverbindung und zeigt wieder den LogIn Screen an |
| Erweiterung: | 1. - 2. wie beim Standardablauf<br>3. Der Nutzer bricht den Vorgang über die Meldung ab<br>4. Das System zeigt wieder den Menübereich an |



## 2. Kennwörter

| Name: | ZurücksetzenKennwort |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Das Zurücksetzen eines Kennworts |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet |
| Nachbedingung: | 1. Der Benutzer kann sich wieder im System anmelden |
| Beteiligte Nutzer: | evtl. Benutzer |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Kennwort zurücksetzen&quot; auf<br>2. Das System zeigt eine Liste mit allen Benutzern an<br>3. Der Nutzer wählt den Benutzer, dessen Kennwort zurückgesetzt werden soll, aus<br>4. Das System öffnet eine Meldung, ob das Kennwort wirklich zurückgesetzt werden soll<br>5. Der Nutzer bestätigt die Meldung<br>6. Das System setzt das Kennwort des Nutzers zurück |
| Erweiterung: | 1. - 4. wie Standardablauf<br>5. Der Nutzer bricht den Vorgang über die Meldung ab |



| Name: | ÄndernKennwort |
| --- | --- |
| Primärer Nutzer: | Administrator, Benutzer |
| Ziel (des Nutzers): | Das alte Kennwort wird ungültig und das neue Kennwort kann beim LogIn benutzt werden |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Der Nutzer kennt sein aktuelles Kennwort |
| Nachbedingung: | 1. Das neue Kennwort kann für den LogIn genutzt werden<br>2. Das alte Kennwort kann nicht länger für den LogIn genutzt werden |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Kennwort ändern&quot; aus<br>2. Der Nutzer wird vom System in einer Eingabemaske aufgefordert sein aktuelles Kennwort einzugeben<br>3. Das System prüft das eingegebene Kennwort<br>4. Das System öffnet eine Eingabemaske für das neue Kennwort<br>5. Der Nutzer gibt das neue Kennwort ein und bestätigt seine Eingabe<br>6. Das System prüft die Eingabe, meldet die erfolgreiche Änderung des Kennworts und ändert das Kennwort in der Datenbank |
| Erweiterung 1: | 1. - 3. wie Standardablauf<br>4. Das System meldet, dass das Passwort nicht korrekt ist und bricht den Vorgang ab |
| Erweiterung <br>2. | 1. - 5. wie Standardablauf<br>6. Das System meldet, dass die beiden Passwortfelder nicht übereinstimmen und bricht den Vorgang ab |



## 3. Gruppen

| Name: | AnlegenGruppe |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Eine neue Gruppe mit entsprechenden Rechten ist verfügbar |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet |
| Nachbedingung: | 1. Die neue Gruppe ist zuweisbar und besitzt ihre Rechte |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Gruppe anlegen&quot; auf<br>2. Das System öffnet die Eingabemaske für den Nutzer<br>3. Der Nutzer gibt die Gruppendaten (wie zB &quot;Name&quot; der Gruppe) in die Eingabemaske ein, wählt ihre Berechtigungen aus und bestätigt seine Eingabe<br>4. Das System prüft die eingegebenen Daten<br>5. Das System meldet die erfolgreiche Anlage der neuen Gruppe und erstellt einen neuen Datensatz in der Datenbank |
| Erweiterung: | 1. - 3. wie Standardablauf<br>4. Das System stellt einen oder mehrere Fehler fest, gibt eine Fehlermeldung aus und zeigt wieder die Eingabemaske mit den bereits vom Nutzer eingegebenen Daten an |



| Name: | LöschenGruppe |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Ein Gruppendatensatz ist gelöscht |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Der Gruppendatensatz befindet sich in der Datenbank |
| Nachbedingung: | 1. Die Gruppe ist gelöscht und nicht mehr zuweisbar |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Gruppe Löschen&quot; auf<br>2. Das System zeigt eine Liste aller vorhandenen Gruppen an<br>3. Der Nutzer wählt die zu löschende Gruppe aus<br>4. Das System fragt über eine Meldung, ob die Gruppe wirklich gelöscht werden soll<br>5. Der Nutzer bestätigt die Meldung<br>6. Das System löscht die Gruppe aus der Datenbank |
| Erweiterung: | 1. - 4. wie Standardablauf<br>5. Der Nutzer bricht den Vorgang über die Meldung ab |



| Name: | EditiereGruppe |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Änderung der Daten einer Gruppe |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Die zu ändernde Gruppe ist im System vorhanden |
| Nachbedingung: | 1. Die Daten wurden erfolgreich geändert<br>2. Die Änderung sind nach Einloggen des nächsten Benutzers aktiv |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die &quot;Gruppe Bearbeiten&quot; Funktion auf<br>2. Das System öffnet eine Liste mit allen Gruppen<br>3. Der Nutzer wählt die gewünschten Gruppe aus der Liste aus<br>4. Das System öffnet eine Eingabemaske mit den bereits existierenden Gruppendaten und Rechten<br>5. Der Nutzer nimmt seine Änderungen vor und bestätigt diese<br>6. Das System zeigt eine Erfolgsmeldung an und springt zurück in die Gruppenliste |
| Erweiterung: | 1. - 4. wie Standardablauf<br>5. Der Nutzer bricht den Vorgang über eine Meldung ab |

Zu Standardablauf <br>6.: Nach Beendigung des Vorgangs eher zurück in das Hauptmenü?



## 4. Benutzer

| Name: | HinzufügenBenutzer |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Ein neuer Benutzer ist angelegt |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet |
| Nachbedingung: | 1. Der angelegte Benutzer kann sich im System einloggen<br>2. Der Benutzer ist in der Benutzerübersicht sichtbar |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Mitarbeiter Hinzufügen&quot; auf<br>2. Das System öffnet die Eingabemaske für den Nutzer<br>3. Der Nutzer gibt die Benutzerdaten in die Eingabemaske ein, wählt dessen Gruppe aus und bestätigt seine Eingabe<br>4. Das System prüft die eingegebenen Daten<br>5. Das System meldet die erfolgreiche Anlage des neuen Benutzer und erstellt einen neuen Datensatz in der Datenbank |
| Erweiterung: | 1. - 4. wie Standardablauf<br>5. Das System meldet, dass das Mitglied nicht angelegt werden konnte (inkl. Fehlermeldung) |



| Name: | LöschenBenutzer |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Ein Benutzerdatensatz ist gelöscht |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Der Benutzerdatensatz befindet sich in der Datenbank |
| Nachbedingung: | 1. Der Benutzerdatensatz ist gelöscht und nicht mehr in der Benutzerübersicht sichtbar |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Mitarbeiter Löschen&quot; auf<br>2. Das System zeigt eine Liste aller vorhandenen Benutzer an<br>3. Der Nutzer wählt den zu löschenden Benutzer aus<br>4. Das System fragt über eine Meldung, ob der Nutzer wirklich gelöscht werden soll<br>5. Der Nutzer bestätigt die Meldung<br>6. Das System löscht den Benutzerdatensatz aus der Datenbank |
| Erweiterung: | 1. - 4. wie Standardablauf<br>5. Der Nutzer bricht den Vorgang über die Meldung ab |



| Name: | EditiereBenutzer |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Änderung der Daten eines Benutzers |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Der zu ändernde Benutzer ist im System vorhanden |
| Nachbedingung: | 1. Die Daten wurden erfolgreich geändert<br>2. Die Änderung sind nach Einloggen des Benutzers aktiv |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die &quot;Mitarbeiter Bearbeiten&quot; Funktion auf<br>2. Das System öffnet eine Liste mit allen Benutzern<br>3. Der Nutzer wählt den gewünschten Benutzer aus der Liste aus<br>4. Das System öffnet eine Eingabemaske mit den bereits existierenden Benutzerdaten und Gruppen<br>5. Der Nutzer nimmt seine Änderungen vor und bestätigt diese<br>6. Das System zeigt eine Erfolgsmeldung an, ändert den Datensatz in der Datenbank und springt zurück in die Benutzerübersicht |
| Erweiterung: | 1. - 5. wie Standardablauf<br>6. Der Nutzer bricht den Vorgang über eine Meldung ab |

Zu Standardablauf <br>6.: Nach Beendigung des Vorgangs eher zurück in das Hauptmenü?



## 5. Geräte, SAN-Materialien, Verbrauchsmaterialien

| Name: | HinzufügenGerät/HinzufügenMaterial |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Das neue Material/Gerät ist im System verfügbar |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Das Gerät/Material ist nicht bereits vorhanden |
| Nachbedingung: | 1. Das neue Gerät/Material ist in den anderen Funktionen des System sichtbar und kann ausgewählt werden |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Gerät Hinzufügen&quot;/&quot;Material Hinzufügen&quot; auf<br>2. Das System öffnet eine Eingabemaske für den Nutzer<br>3. Der Nutzer gibt die Gerätedaten/Materialdaten in die Eingabemaske ein und bestätigt seine Eingabe; im Falle von Materialien muss der Nutzer wählen, ob es sich um ein SAN-Material oder ein Verbrauchsmaterial handelt<br>4. Das System prüft die eingegebenen Daten<br>5. Das System meldet eine erfolgreiche Anlage des neuen Geräts/Materials und erstellt einen neuen Datensatz in der Datenbank |
| Erweiterung 1: | 1. - 2. wie Standardablauf<br>3. Der Nutzer bricht den Vorgang über eine Meldung ab |
| Erweiterung <br>2. | 1. - 4. wie Standardablauf<br>5. Das System meldet, dass das Gerät/Material nicht angelegt werden konnte, da es bereits vorhanden ist und bricht den Vorgang mit einer Meldung ab |



| Name: | LöschenGerät/LöschenMaterial |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Ein Gerät/Material ist gelöscht |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Das Gerät/Material befindet sich in der Datenbank |
| Nachbedingung: | 1. Das Gerät/Material ist gelöscht und wird nicht mehr in den Listen und Funktionen des Systems angezeigt |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Gerät Löschen&quot;/&quot;Material Löschen&quot; auf<br>2. Das System zeigt eine Liste aller vorhandenen Geräte/Materialien an<br>3. Der Nutzer wählt das zu löschenden Gerät/Material aus<br>4. Das System fragt über eine Meldung, ob das Gerät/Material wirklich gelöscht werden soll<br>5. Der Nutzer bestätigt die Meldung<br>6. Das System löscht den Geräte-/Materialdatensatz aus der Datenbank |
| Erweiterung: | 1. - 4. wie Standardablauf<br>5. Der Nutzer bricht den Vorgang über die Meldung ab |



| Name: | EditiereGerät/EditiereMaterial |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Änderung der Daten eines Gerätes/Materials |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Das zu ändernde Gerät/Material ist im System vorhanden |
| Nachbedingung: | 1. Die Daten wurden erfolgreich geändert<br>2. Die Änderung sind in den Geräte-/Materialdetails sichtbar |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die &quot;Gerät Bearbeiten&quot;/&quot;Material Bearbeiten&quot; Funktion auf<br>2. Das System öffnet eine Liste mit allen Geräten/Materialen<br>3. Der Nutzer wählt das gewünschte Gerät/Material aus der Liste aus<br>4. Das System öffnet eine Eingabemaske mit den bereits existierenden Geräte-/Materialdaten<br>5. Der Nutzer nimmt seine Änderungen vor und bestätigt diese<br>6. Das System zeigt eine Erfolgsmeldung an und springt zurück in die Mitgliederliste |
| Erweiterung: | 1. - 5. wie Standardablauf<br>6. Der Nutzer bricht den Vorgang über eine Meldung ab |

Hier könnte zB der Lagerort geändert werden



## 6. Lagerorte

| Name: | HinzufügenLagerort |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Ein neuer Lagerort ist für die Geräte/Materialien verfügbar |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Der Lagerort existiert noch nicht |
| Nachbedingung: | 1. Der Lagerort ist für die Geräte/Materialien verfügbar |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Lagerort Hinzufügen&quot; auf<br>2. Das System öffnet eine Eingabemaske<br>3. Der Nutzer gibt den Namen (und Standort?) ein und bestätigt seine Eingabe<br>4. Das System prüft die eingegebenen Daten<br>5. Das System meldet das erfolgreiche Anlegen des neuen Lagerortes und fügt ihn der Datenbank hinzu |
| Erweiterung 1: | 1. - 2. wie Standardablauf<br>3. Der Nutzer bricht den Vorgang mit einer Meldung ab |
| Erweiterung <br>2. | 1. - 4. wie Standardablauf<br>5. Das System stellt fest, dass der Lagerort bereits existiert und bricht den Vorgang mit einer Meldung ab |



| Name: | LöschenLagerort |
| --- | --- |
| Primärer Nutzer: | Administrator |
| Ziel (des Nutzers): | Ein Lagerort ist gelöscht |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Der Lagerort im System vorhanden |
| Nachbedingung: | 1. Der Lagerort ist nicht länger im System vorhanden und nicht mehr auswählbar |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Lagerort Löschen&quot; auf<br>2. Das System zeigt eine Liste mit allen Lagerorten an<br>3. Der Nutzer wählt den gewünschten Lagerort aus und bestätigt seine Eingabe<br>4. Das System erfragt über eine Meldung, ob alle Geräte/Materialien bereits an andere Lagerorte verteilt worden sind oder ob die Geräte/Materialien alle zusammen an einem neuen Lagerort gebracht werden<br>5. Der Nutzer wählt Option 1<br>6. Das System meldet das erfolgreiche Löschen des Lagerorten und löscht diesen Datensatz aus der Datenbank |
| Erweiterung 1: | 1. - 2. wie Standardablauf<br>3. Der Nutzer bricht den Vorgang über eine Meldung ab |
| Erweiterung <br>2. | 1. - 4. wie Standardablauf<br>5. Der Nutzer wählt Option <br>2.. Das System zeigt eine Liste der Lagerorte an und erfragt an welchem Lagerort sich die Geräte/Materialien nun befinden (werden)<br>7. Der Nutzer wählt einen Lagerort aus und bestätigt seine Auswahl<br>8. Das System meldet das erfolgreiche Löschen des Lagerortes und die Aktualisierung des Lagerortes der Geräte/Materialien, die sich zuvor an diesem Lagerort befanden und ändert die entsprechenden Datensätze |
| Erweiterung 1 von Erweiterung <br>2. | 1. - 6. wie Standardablauf<br>7. Der Nutzer bricht den Vorgang über die Meldung ab |

Daraus werden später <br>2.Use Cases


## 7. Bestandsänderungen

| Name: | FüllenBestand |
| --- | --- |
| Primärer Nutzer: | Administrator, Benutzer |
| Ziel (des Nutzers): | Der Bestand wird um das gewünschte Gerät/Material erweitert |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Das gewünschte Gerät/Material ist im System verfügbar |
| Nachbedingung: | 1. Der Bestand ist um die gewünschte Menge erweitert worden |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer öffnet die Geräte-/Materialliste<br>2. Der Nutzer wählt das gewünschte Material/Gerät aus der Liste aus<br>3. Das System öffnet die Detailansicht des Gerätes/Materials<br>4. Der Nutzer gibt die Anzahl der hinzuzufügenden Geräte/Materialien ein und bestätigt seine Eingabe<br>5. Das System gibt eine Erfolgsmeldung aus und ändert den Datensatz in der Datenbank |
| Erweiterung: | 1. - 3. wie Standardablauf<br>4. Der Nutzer bricht den Vorgang über eine Meldung ab |



| Name: | EntnahmeBestand |
| --- | --- |
| Primärer Nutzer: | Administrator, Benutzer |
| Ziel (des Nutzers): | Der Bestand wird um das gewünschte Gerät/Material gemindert |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Das gewünschte Gerät/Material ist im System verfügbar |
| Nachbedingung: | 1. Der Bestand ist um die gewünschte Menge gemindert worden |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer öffnet die Geräte-/Materialliste<br>2. Der Nutzer wählt das gewünschte Material/Gerät aus der Liste aus<br>3. Das System öffnet die Detailansicht des Gerätes/Materials<br>4. Der Nutzer gibt die Anzahl der entnommenen Geräte/Materialien ein und bestätigt seine Eingabe<br>5. Das System gibt eine Erfolgsmeldung aus und ändert den Datensatz in der Datenbank |
| Erweiterung: | 1. - 3. wie Standardablauf<br>4. Der Nutzer bricht den Vorgang über eine Meldung ab |



| Name: | ÄndernLagerortBestand |
| --- | --- |
| Primärer Nutzer: | Administrator, Benutzer |
| Ziel (des Nutzers): | Der Lagerort des gewünschte Gerät/Material wird geändert |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Das gewünschte Gerät/Material ist im System verfügbar |
| Nachbedingung: | 1. Der Lagerort des gewünschte Gerät/Material ist aktualisiert |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer öffnet die Geräte-/Materialliste<br>2. Der Nutzer wählt das gewünschte Material/Gerät aus der Liste aus<br>3. Das System öffnet die Detailansicht des Gerätes/Materials<br>4. Der Nutzer gibt den neuen Lagerort des Geräts/Materials ein und bestätigt seine Eingabe<br>5. Das System gibt eine Erfolgsmeldung aus und ändert die Datensätze in der Datenbank |
| Erweiterung: | 1. - 3. wie Standardablauf<br>4. Der Nutzer bricht den Vorgang über eine Meldung ab |



## 8. Listen/Alarme

| Name: | ExportierenListe |
| --- | --- |
| Primärer Nutzer: | Administrator, Benutzer |
| Ziel (des Nutzers): | Eine pdf/Textdatei mit dem Inventar wird ausgegeben |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet |
| Nachbedingung: | 1. Die Liste enthält die gefilterten Gegenstände |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer führt die Funktion &quot;Inventarliste Anzeigen&quot; aus<br>2. Der Nutzer wählt seine gewünschten Filter aus<br>3. Der Nutzer wählt die Funktion &quot;Liste Speichern&quot; aus<br>4. Das System fragt den Nutzer in einem entsprechenden Fenster wo die Datei gespeichert werden soll<br>5. Der Nutzer wählt den Ort aus<br>6. Das System speichert die Liste an der entsprechenden Stelle |
| Erweiterung: | 1. - 4. wie Standardablauf<br>5. Der Nutzer bricht den Vorgang mit schließen des Fensters ab und gelangt wieder in die Inventarliste |



| Name: | ZeigeAlarme |
| --- | --- |
| Primärer Nutzer: | Administrator, Benutzer |
| Ziel (des Nutzers): | Eine Liste mit den aktuellen Alarmen wird angezeigt |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet |
| Nachbedingung: | 1. Alle aktuellen Alarme werden angezeigt |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Alarme Anzeigen&quot; auf<br>2. Das System zeigt eine Liste mit allen Alarmen und ihrem Status an<br>3. Der Nutzer ruft die Funktion &quot;Zurück zum Hauptmenü&quot; auf und das Hauptmenü wird wieder angezeigt |
| Erweiterung: | - |



| Name: | VerschickeAlarme |
| --- | --- |
| Primärer Nutzer: | - |
| Ziel (des Nutzers): | - |
| Vorbedingung: | 1. Das System läuft und es gibt neue Alarme |
| Nachbedingung: | 1. Alle neuen Alarme wurden verschickt |
| Beteiligte Nutzer: | Administrator, Benutzer |
| Standardablauf: | 1. Das System prüft, ob es neue Alarme gibt<br>2. Das System verschickt alle neuen Alarme per Email |
| Erweiterung: | 1. wie Standardablauf<br>2. Das System findet keine neuen Alarme |


## 9. Sonstige Funktionen

| Name: | ZeigeHauptmenü |
| --- | --- |
| Primärer Nutzer: | Administrator, Benutzer |
| Ziel (des Nutzers): | Der Nutzer gelangt wieder in das Hauptmenü |
| Vorbedingung: | 1. Der Nutzer ist im System angemeldet<br>2. Der Nutzer befindet sich nicht im Hauptmenü |
| Nachbedingung: | 1. Alle anderen Menüfenster sind geschlossen |
| Beteiligte Nutzer: | - |
| Standardablauf: | 1. Der Nutzer ruft die Funktion &quot;Zurück zum Hauptmenü&quot; auf<br>2. Das System erfragt über eine Meldung, ob der Nutzer zurück in das Hauptmenü möchte und alle nicht gespeicherten Änderungen verworfen werden sollen<br>3. Der Nutzer bestätigt dies<br>4. Das System öffnet die Hauptmenüansicht und schließt alle anderen Ansichten |
| Erweiterung: | 1. - 2. wie Standardablauf<br>3. Der Nutzer bricht den Vorgang über die Meldung ab |
