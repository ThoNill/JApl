package japl.system;

import japl.csv.CSVBinaryFunction;
import japl.csv.CSVUnaryFunction;
import japl.data.MatrixUnary;
import japl.imp.binary.AddAttributes;
import japl.imp.binary.Apply;
import japl.imp.binary.ApplyTemplate;
import japl.imp.binary.BinaryArithFunctions;
import japl.imp.binary.DeleteTemplate;
import japl.imp.binary.InsertTemplate;
import japl.imp.binary.Mult;
import japl.imp.binary.Sum;
import japl.imp.binary.UpdateTemplate;
import japl.imp.oparators.ArrayIndexLeftApl;
import japl.imp.oparators.Setter;
import japl.imp.unary.AlphaOmega;
import japl.imp.unary.ArrayToStream;
import japl.imp.unary.CacheStream;
import japl.imp.unary.ConnectToDatabase;
import japl.imp.unary.Index;
import japl.imp.unary.InstallClass;
import japl.imp.unary.Range;
import japl.imp.unary.StreamToArray;
import japl.imp.unary.TV;
import japl.imp.unary.UnaryArithFunctions;
import japl.imp.unary.ZipStreams;
import japl.predicates.SimplePredicates;
import japl.random.RandomArray;
import japl.random.RandomStream;
import japl.xml.XMLUnaryFunction;

public class LeftAplSystem extends BasisSystem {
    public LeftAplSystem()  {
        super(false);
    
        unary.put("XML~",new XMLUnaryFunction());
        
        binary.put("~I~",new InsertTemplate());
        binary.put("~U~",new UpdateTemplate());
        binary.put("~D~",new DeleteTemplate());
        
        
        unary.put("⍳",new Index());
        unary.put("⍳~",new Range());

        unary.put("?i",new RandomArray(RandomArray.Art.AS_INT));
        unary.put("?d",new RandomArray(RandomArray.Art.AS_DOUBLE));
        unary.put("?i~",new RandomStream(RandomStream.Art.AS_INT));
        unary.put("?d~",new RandomStream(RandomStream.Art.AS_DOUBLE));
        unary.put("[~]zip~",new ZipStreams());
        
        unary.put("~[]", new StreamToArray());
        unary.put("[]~", new ArrayToStream());
        
        unary.put("⌹",new MatrixUnary(MatrixUnary.Art.INV));
        unary.put("⍉",new MatrixUnary(MatrixUnary.Art.TRANS));
        unary.put("⌽",new MatrixUnary(MatrixUnary.Art.VERTICAL));
        unary.put("⊖",new MatrixUnary(MatrixUnary.Art.HORIZONTAL));
        
        unary.put("→TV",new TV());
        unary.put("@class",new InstallClass());
        unary.put("@connection",new ConnectToDatabase());
        unary.put("~cache~",new CacheStream());
        unary.put("CSV~",new CSVUnaryFunction());
        binary.put("→CSV",new CSVBinaryFunction());
        
        
        binary.put(".",new Apply(stack));
        binary.put("+",new Sum());
        binary.put("×",new Mult());
        binary.put("⌷",new ArrayIndexLeftApl());
        binary.put("→",new Setter(variables));
        binary.put("A~",new AddAttributes());
        binary.put("~T~",new ApplyTemplate(variables));
        
        creator.put("⍺",new AlphaOmega(omegaStack));
        creator.put("⍹",new AlphaOmega(alphaStack));
        
        unary.put("¬",new SimplePredicates(SimplePredicates.Art.NOT));
        binary.put("∧",new SimplePredicates(SimplePredicates.Art.AND));
        binary.put("∨",new SimplePredicates(SimplePredicates.Art.OR));
        binary.put("=",new SimplePredicates(SimplePredicates.Art.EQ));
        binary.put("≠",new SimplePredicates(SimplePredicates.Art.UNEQ));
        
        binary.put("<",new SimplePredicates(SimplePredicates.Art.GT));
        binary.put(">",new SimplePredicates(SimplePredicates.Art.LT));
        binary.put("≤",new SimplePredicates(SimplePredicates.Art.GET));
        binary.put("≥",new SimplePredicates(SimplePredicates.Art.LET));
        
        binary.put("|",new BinaryArithFunctions(BinaryArithFunctions.Art.MODULO));
        unary.put("!",new UnaryArithFunctions(UnaryArithFunctions.Art.FAKULTÄT));
        binary.put("⊣",new BinaryArithFunctions(BinaryArithFunctions.Art.LINKS));
        binary.put("⊢",new BinaryArithFunctions(BinaryArithFunctions.Art.RECHTS));
        binary.put(",",new BinaryArithFunctions(BinaryArithFunctions.Art.CONCAT));
        binary.put("⌈",new BinaryArithFunctions(BinaryArithFunctions.Art.MAX));
        binary.put("⌊",new BinaryArithFunctions(BinaryArithFunctions.Art.MIN));
        
        
        interpreter = new Interpreter(variables, unary, binary,creator,alphaStack,omegaStack,false);
    }

}
