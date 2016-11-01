# Architekturmuster

Nachfolgend soll ein grober Überblick über die industriell gängigen Architekturmuster gegeben werden um die Erstellung der Projektarchitektur vorzubereiten.

- Model View Controller(MVC)
- Model View Presenter(MVP)
- Three-tier architecture (auch Schichtenmodell)

## Model View Controller

Dieses Architekturmuster sieht vor, dass die Software in die drei Einheiten **Datenmodell** (engl. model), **Präsentation** (engl. view) und **Programmsteuerung** (engl. controller) unterteilt wird. Ziel ist es, die Einheiten beim Programmentwurf autonom zu betrachten, so dass eine Änderung oder Erweiterung leichter zu verwirklichen ist und die Wiederverwendbarkeit der einzelnen Komponenten gegeben ist.

Die drei Einheiten führen je nach Implementierung die folgenden Aufgaben aus:

### Model

Das **Model** enthält die Daten, welche später zur Darstellung bereitgestellt werden. Es soll vollkommen unabhängig von *View* und *Controller* sein. Die Bekanntgabe von Änderungen im Datenmodell geschieht über das *Observer-Pattern* (engl. Beobachter, das zu beobachtende Objekt bietet einen Mechanismus, um Beobachter an- und abzumelden und diese über Änderungen zu informieren) oder das *Delegation-Pattern* (engl. Abordnung, dynamische Methodenbindung zur Programmlaufzeit um methoden anderer Objekte auszuführen).

### View

Der **View** ist für die grafische Darstellung von Daten und die Entgegennahme von Nutzerinteraktionen zuständig. Die Verarbeitung der eingegebenen Daten wird jedoch nicht von der *View* durchgeführt, sondern über die bereits erwähnten Entwurfsmuster wie Observer-Pattern oder Delegation-Pattern an den *Controller* weitergeleitet. Häufig wird für den *View* das *Composite pattern* (engl. Kompositum, zur Abbildung von Hierarchischen Strukturen in der Softwareentwicklung) verwendet um View-Hierarchien abzubilden. Gängige Attribute dafür sind beispielsweise `View subviews[]` und `View parentView`.

### Controller

Im **Controller** werden ein oder mehrerer *Views* verwaltet und sämtliche Nutzereingaben entgegen genommen. An dieser Stelle werden Nutzereingaben ausgewertet und dem *Model* übergeben. Abhängig der Vorgaben bzw. der für die Programmiersprache typischen Konzepte findet sich hier auch eine Validierung von Eingaben oder eine Internationalisierung der vom *View* angezeigten Texte wieder. In einigen Implementierungen beinhaltet dieser Teil der Architektur einen Observer der *Model*-Daten, so wie die Möglichkeit die angezeigten *Views* zu aktualisieren.


## Model View Presenter

Dieses Architekturmuster ging aus dem bereits vorher erwähnten **MVP-Pattern** hervor. Dabei legt es jedoch großen Wert darauf, dass *Model* und *View* vollständig von einander getrennt und über den *Presenter* verbunden sind. Der *Presenter* weiß als einziger Teil der Programmlogik sowohl vom *Model* als auch vom *View* bescheid (Umgekehrt ist der Presenter den beiden jedoch jeweils unbekannt).

Zur Implementierung des **MVP-Pattern** werden für *Model* und *View* jeweils Interfaces (engl. Schnittstelle, formale Deklaration der vorhandenen Funktionen und wie diese angesprochen werden können) definiert, welche jeweils den dahinterliegenden Aufbau definieren, aber auch von einander entkoppeln. So können auf einfachste Weise Funktionen der jeweiligen Einheiten ausgetauscht werden, ohne dass davon die Projektarchitektur betroffen ist.


Die von Martin Fowler vorgenommene Unterscheidung in **Supervising Controller** und **Passive View** soll soweit außer Acht gelassen werden, da diese für die Impelemtierung des Projekts voraussichtlich nicht von Interesse sein wird. Weitere Informationen dazu können in der Quelle [2.] nachgelesen werden.

## Three-tier architecture



# Projektarchitektur



# Quellen
1. MVC, http://wiki.c2.com/?ModelViewController
2. MVC/MVP, http://martinfowler.com/eaaDev/uiArchs.html
