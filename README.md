Vorlagen
=====

Generelles Vorgehen, aus Vorlagen (Templates) Dateien zu erzeugen.

Aufgrund einesvorgegebenen  Modells sollen Dateien erzeugt werden.

Dazu werden Vorlagen benutzt. Die Vorlagen benutzen aber nicht direkt
das Modell, sondern VorlagenModelle, die von VorlagenModellFabriken 
aus dem Model erzeugt werden. Die Vorlagen selbst werden durch 
VorlagenFabriken erzeugt.

Jede Vorlage steuert die Erzeugung einer Datei aus dem VorlagenModell.
Die Vorlage gibt auch den Dateinamen und Verzeichnis an, wohin die 
Datei geschrieben werden soll.

Die Modelle und Templates werden von einer  VorlagenMaschine erzeugt.

Diese legt drei Verzeichnisse fest, von denen die anderen Verzeichnisse ausgehen.

BasisModelVerzeichnis, das Oberverzeichnis in dem die Modelbeschreibungen liegen.
BasisVorlagenVerzeichnis, das Oberverzeichnis, in den die Vorlagenbeschreibungen liegen.
BasisZielVerzeichnis, das Oberverzeichnis in das die erzeugten Dateien gestellt werden.

Die VorlagenMaschine besteht weiter aus einer VorlagenFabrik und einer ModelFabrik.

Es kann mehrere Vorlagen geben. Die Vorlagen und Zuordnungen zu Dateien werden
in einer VorlagenDefinition verwaltet.

Diese Vorlagendefinition umfasst jeweils eine VorlagenBeschreibung, anhand derer eine
Vorlage erzeugt werden kann. Meist ein Dateiname. 

Weiter umfasst diese Struktur 
ein ModelVerzeichnis, als Zwischenverzeichnis  für die VorlagenModelle
ein VorlageVerzeichnis, als Zwischenverzeichnis  für die Vorlagen
ein Zielverzeichnis, als Zwischenverzeichnis  für die Ausgabedateien
und eine VorlagenModellFabrik.


