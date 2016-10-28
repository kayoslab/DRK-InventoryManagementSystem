# Anforderungen

## Funktional

Da es sich um ein umfangreiches Softwareprojekt handeln wird, werden die funktionalen Anforderungen wie folgt in ihre Anwendungsbereiche unterteilt.

### Adminmenü

- Anlegen/Löschen neuer Benutzer
- Zurücksetzen von Kennwörtern
- Anlegen/Löschen neuer Gruppen
- Zuweisung von Benutzern zu Gruppen
- Festlegen/Ändern von Gruppenrichtlinien
- Anlegen/Löschen von Materialien und Geräten
- Anlegen/Löschen von Lagerorten
- Festlegen/Ändern von Mindestbeständen

### Login

- Anmeldung mit Nutzername und Kennwort
- Kennwort kann vom Nutzer geändert werden
- Kennwort wird auf Passwortkonvention geprüft

### Logdateien

- Änderungen in den Beständen werden im Log eingetragen
- Ein Administrator muss das Erstellen eines Logs aktivieren

### Materialien und Geräte

- Änderungen des Bestands in Form von Mehrung/Minderung
- Korrektur bei Fehlbeständen


## Nicht-funktional

- Aussehen und Handhabung
    - Möglichst intuitiv, damit auch wenig erfahrene Nutzer die Bedienung schnell beherrschen
    - Corporate Design des DRKs (Logo)
- Zuverlässigkeit
    - Fehlertoleranz bei der Eingabe von Werten
    - Backups?
- Leistung und Effizienz
    - Schonender Umgang mit den Ressourcen (insbesondere Datenbankverbindungen)
- Wartbarkeit, Änderbarkeit
    - Die Möglichkeit der Weiterentwicklung muss gegeben sein
- Sicherheitsanforderungen
    - Gilt zu prüfen: Erstellung von Logdateien
