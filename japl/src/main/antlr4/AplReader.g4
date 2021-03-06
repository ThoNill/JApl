grammar AplReader;

@header {
    package apl;
}				

@Lexer::header {
    package apl;
}				

apl: words;

words: word+;

word: (variable | constant | operation  | stop | pwords |  bwords |  cwords);

pwords : LP words RP;
bwords : LB words RB;
cwords : LC words RC; 

variable : Name;
constant : Text | Int | Double;  
operation: APL;
stop : NL;                  

Name: ('A'..'Z' | 'a'..'z')+ ;
Text: '\'' (~[\'] | ' ')+ '\'' ;
Int:  '-'?('0'..'9')+;
Double: '-'?('0'..'9')+ '.' ('0'..'9')+;

WS: (' ' | '\t' )+  -> skip ;

NL:  '\r' ? '\n' | '⋄';

AMP: '\'';

LP: '(';
RP:  ')';
LB: '[';
RB:  ']';
LC: '{';
RC:  '}';


APL : '.' | '+' | '×' | ':' | '⍒' | '⍳' | '⍹'| '⍺' | '⌷' | '←' | '→' | '~⍳' | '⍳~' | '→TV' | 'TV←' | '~A' | 'A~' | '~T~' 
      | '?i' | '?d' | '~?i' | '~?d' | '?i~' | '?d~' | '~[]' | '[]~' | '[~]zip~'
      | '¬' | '∧' | '∨' | '=' | '≠' | '<' | '>' | '≤' | '≥'        
      |'~cache~' |'@class' | '@connection' | '~CSV' | 'CSV~' | 'CSV←' | '→CSV' | '~I~' |  '~U~' |  '~D~' | 'XML~' |  '→XML' 
      | '|' | '!' | '⌈' | '⌊' | ',' | '⊣' | '⊢' ;

/*
REST:  '÷' | '←' | '→' | '↓' | '↑' | '¯' | 'ω' | 'α' | '∘' | '¨' 
|'⌸'|'⌹'|'⌺'|'⌻'|'⌼'|'⌽'|'⌾'|'⌿'|'⍀'|'⍁'|'⍂'
|'⍃'|'⍄'|'⍅'|'⍆'|'⍇'|'⍈'|'⍉'|'⍊'|'⍋'|'⍌'|'⍍'
|'⍎'|'⍏'|'⍐'
|'⍑'
|'⍒'
|'⍓'
|'⍔'
|'⍕'
|'⍖'
|'⍗'
|'⍘'
|'⍙'
|'⍚'
|'⍛'
|'⍜'
|'⍝'
|'⍞'
|'⍟'
|'⍠'
|'⍡'
|'⍢'
|'⍣'
|'⍤'
|'⍥'
|'⍦'
|'⍧'
|'⍨'
|'⍩'
|'⍪'
|'⍫'
|'⍬'
|'⍭'
|'⍮'
|'⍯'
|'⍰'
|'⍱'
|'⍲'
|'⍳'
|'⍴'
|'⍵'
|'⍶'
|'⍷'
|'⍸'
|;
*/