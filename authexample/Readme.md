## Spring-Boot Auth Example

Dieses Spring-Boot Beispielprojekt zeigt die Verwendung von Spring-Security zum Absichern eines Endpunktes, sowie Funktionalität zum Login und Logout. Die Nutzer werden dabei aus der Datenbank (Tabelle `user` abgerufen).

Bereitgestellte Funktionalität:
* Login (`/login`)
* Logout (`/logout`)
* Endpunkt absichern (`/secure`)
* Im Template prüfen, ob Nutzer angemeldet ist (`/`)
* Speicherung der Sessions in der Datenbank

## Voraussetzung

Es muss das Tabellenschema, analog zur Vorlage unter \database\db-schema.sql in die Datenbank importiert werden und auch die zu verwendende Datenbank in der `application.properties` konfiguriert werden.

## Beispielnutzer

Im Beispielschema wurde ein Beispielnutzer `test@test.de` mit dem Passwort `geheim` bereitgestellt.



