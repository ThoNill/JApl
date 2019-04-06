package tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import apl.AplReaderLexer;
import apl.AplReaderParser;
import japl.data.ArrayBuilder;
import japl.system.AplSystem;
import japl.system.BasisSystem;

public class BasisTest {
    
    public void testSystem(BasisSystem aplSystem,String aplProgram,Object[] stackExpected) {
        aplSystem.run(aplProgram);
        Object[] stack = aplSystem.getWorkingStackArray();
        assertArrayEquals(stackExpected,stack);
    }
    
    public void testSystem(String aplProgram,Object[] stack) {
        BasisSystem aplSystem = new AplSystem();
        testSystem(aplSystem, aplProgram, stack);
    }

    private Object[] topOfStack(Object ...objs) {
        return new Object[] {objs};
    }


    private void testSystemStackDepth(String aplProgram, int depth) {
        BasisSystem aplSystem = new AplSystem();
        aplSystem.run(aplProgram);
        Object[] stack = aplSystem.getWorkingStackArray();
        assertEquals(depth,stack.length);   }



    @Test
    public void test() {
        String apl = "  ⍒ ⍳ 100 \n";

        AplReaderLexer lexer = new AplReaderLexer(new ANTLRInputStream(apl));
        AplReaderParser parser = new AplReaderParser(new CommonTokenStream(
                lexer));
        ParseTree root = parser.apl();

        assertEquals("(apl (words (word (operation ⍒)) (word (operation ⍳)) (word (constant 100)) (word (stop \\n))))",root.toStringTree(parser));
    }

    @Test
    public void test1() {
        String apl = "  ⍒ ⍳ (1 2 3) 100 \n";

        AplReaderLexer lexer = new AplReaderLexer(new ANTLRInputStream(apl));
        AplReaderParser parser = new AplReaderParser(new CommonTokenStream(
                lexer));
        ParseTree root = parser.apl();
        assertEquals("(apl (words (word (operation ⍒)) (word (operation ⍳)) (word (pwords ( (words (word (constant 1)) (word (constant 2)) (word (constant 3))) ))) (word (constant 100)) (word (stop \\n))))",root.toStringTree(parser));
    }

    public void showTree(StringBuilder buffer, ParseTree tree) {
        append(buffer, "Class Name", tree.getClass().getCanonicalName());
        Object payload = tree.getPayload();
        append(buffer, "Payload Class", (payload==null) ? "No class" : payload.getClass().getCanonicalName());
        append(buffer, "Inhalt", payload);
        append(buffer, "Text",tree.getText() );
        int anz = tree.getChildCount();
        for (int i = 0; i < anz; i++) {
            showTree(buffer, tree.getChild(i));
        }
    }

    private void append(StringBuilder buffer, String name, Object value) {
        buffer.append(name);
        buffer.append(": ");
        buffer.append((value == null) ? "null" : value.toString());
        buffer.append("\n");
    }

    @Test
    public void tesIndex() {
        testSystem(" ( 1 2 3) [1] ",new Integer[]{2});
    }
    
    
    @Test
    public void testSystem() {
        testSystem("  1 2 3 ",topOfStack(1,2,3));
        testSystem("  1 2 ⍳ 3 ",topOfStack(new Integer[]{1,2},new Integer[]{0,1,2,3}));
        testSystem("  4 ( 1 2 ) ⍳ 3 ",topOfStack(4,new Integer[]{1,2},new Integer[]{0,1,2,3}));
        
        testSystem("  4 ( 1 2 ( 1 2 3 4 5 6 ) ) ⍳ 3 ",topOfStack( 4,new Object[] {new Integer[]{1,2},new Integer[]{1, 2, 3, 4, 5, 6}},new Integer[]{0,1,2,3}));
        testSystem("  2 + 3  ",new Integer[]{5});
        testSystem(" ( (1 2) + 3 4 ) ",topOfStack(4,6));
        testSystem(" ( 1 2 3) [1] ",new Integer[]{2});

        BasisSystem aplSystem = new AplSystem();
        testSystem(aplSystem," testvar ← 4  ",new Object[]{});
        assertEquals(4,aplSystem.getVariableValue("testvar"));
        testSystem(aplSystem," testvar ",new Integer[]{4});
        

        aplSystem = new AplSystem();
        testSystem(aplSystem," testvar ← ( 1 8 4 ) ",new Object[]{});
        Object[] objs = (Object[])aplSystem.getVariableValue("testvar");
        assertArrayEquals(new Object[]{1,8,4},objs);
        testSystem(aplSystem," testvar [1] ",new Integer[]{8});
   
        aplSystem = new AplSystem();
        testSystem(aplSystem," testvar ← ( 1 8 4 ) ",new Object[]{});
        testSystem(aplSystem," testvar [0 1] ",topOfStack(1,8));
       
        
        testSystemStackDepth(" TV←  ( 'a' 'b' ) ~A ~⍳ 6  ",1);
    }
    
    @Test
    public void testUserDefinedUnary() { 
        testSystem(" { 2 + ⍹ } . 1 2 3 4 5 6  ",topOfStack(3,4,5,6,7,8));
    }
    
    @Test
    public void testUserDefinedBinary() { 
        testSystem(" 8 { ⍺ + ⍹ } . 1 2 3 4 5 6  ",topOfStack(9,10,11,12,13,14));
    }
    
    @Test
    public void testSystem1() {
        BasisSystem aplSystem = new AplSystem();
        aplSystem.run(" @class 'org.h2.Driver' ");
        aplSystem.run(" DB ← @connection 'jdbc:h2:./test' ");
        aplSystem.run(" DB . 'create table if not exists test ( id int) ' ");
        aplSystem.run(" DB . 'insert into test values (1) ' ");
        aplSystem.run(" DB . 'insert into test values (2) ' ");
        aplSystem.run(" RESULTCACHE ← ~cache~ DB . 'select id, id +1 from test ' ");
        aplSystem.run(" RESULT ← DB . 'select id, id +1 from test ' ");
        aplSystem.run(" TV← RESULT ");
        aplSystem.run(" TV← RESULT ");
        aplSystem.run(" TV← RESULTCACHE ");
        aplSystem.run(" TV← RESULTCACHE ");
        aplSystem.run(" 'csv.erg' CSV← RESULT ");
    }
    
    @Test
    public void testTemplates() {
        BasisSystem aplSystem = new AplSystem();
        aplSystem.run(" TV←  ' show $a$ ' ~T~ ( 'a' ) ~A ~⍳ 6  ");   
    }

    
    @Test
    public void array() {
        Object a = ArrayBuilder.createDoubleArray(new int[]{2,3,4});
        double[] x = new double[2 * 3 * 4];
        for(int i=0;i<x.length;i++) {
            x[i] = i;
        }
        ArrayBuilder.initDoubleArray(a, x);
    }
    
    @Test
    public void testRandom() {
        BasisSystem aplSystem = new AplSystem();
        aplSystem.run(" TV←  ~[] ?i 100  ");
        aplSystem.run(" TV←  ~[] ?d 100  ");
    }

}
