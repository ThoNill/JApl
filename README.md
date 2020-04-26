
JApl ist eine APl ähnliche Sprache, die auf Java basiert.

APL ist eine frühe Programmiersprache, die auf Arrays 
basiert. Die Programme können mit einem eigenen Zeichsatz 
sehr komprimiert ausgedrückt werden.

Ich habe die Idee, die APL zugrunde liegt, hier um reactive Streams
erweitert, d.h.  nicht nur Arrays werden verarbeitet, sondern auch 
Streams zusammengebaut.

Ergänzend dazu habe ich als Parser antlr als Templatesprache stringtemplate 
und Für Matrizen Colt eingesetzt. 
Als reactive Streams Implementierung setze ich Reactor ein.

APL wird normal zeilenweise von rechts nach links abgearbeitet.
Wobei Arrays aber dann doch wieder von links nach rechts gelesen werden.
Ich habe einen Modus hinzugefügt, der von links nach rechts arbeitet.
Die Richtung von Arrays und Funktionen, Operationen ist dann gleich
und die Schreibrichtung enspricht der Abfolge der Abarbeitung.
Das scheint mir konsistenter zu sein.


 mvn clean antlr4:antlr4 compile test install

