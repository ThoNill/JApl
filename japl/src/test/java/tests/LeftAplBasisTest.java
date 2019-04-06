package tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import japl.data.ArrayBuilder;
import japl.system.BasisSystem;
import japl.system.LeftAplSystem;

public class LeftAplBasisTest {
    
    public void testSystem(BasisSystem aplSystem,String aplProgram,Object[] stackExpected) {
        aplSystem.run(aplProgram);
        Object[] stack = aplSystem.getWorkingStackArray();
        assertArrayEquals(stackExpected,stack);
    }
    
    public void testSystem(String aplProgram,Object[] stack) {
        BasisSystem aplSystem = new LeftAplSystem();
        testSystem(aplSystem, aplProgram, stack);
    }

    private Object[] topOfStack(Object ...objs) {
        return new Object[] {objs};
    }


    private void testSystemStackDepth(String aplProgram, int depth) {
    	BasisSystem aplSystem = new LeftAplSystem();
        aplSystem.run(aplProgram);
        Object[] stack = aplSystem.getWorkingStackArray();
        assertEquals(depth,stack.length);   }




    @Test
    public void tesIndex() {
        testSystem("  ( 1 2 3) [1] ",new Integer[]{2});
    }
    
    
    @Test
    public void testSystem() {
        
        testSystem("  1 2 3 ",topOfStack(1,2,3));
        
        testSystem("  1 2 ( 3 ⍳ ) ",topOfStack(new Integer[]{1,2},new Integer[]{0,1,2,3}));
        
        
        testSystem("  4 ( 1 2 ) ( 3 ⍳ ) ",topOfStack(4,new Integer[]{1,2},new Integer[]{0,1,2,3}));
        
        testSystem("  4 ( 1 2 ( 1 2 3 4 5 6 ) ) 3 ⍳ ",topOfStack( 4,new Object[] {new Integer[]{1,2},new Integer[]{1, 2, 3, 4, 5, 6}},new Integer[]{0,1,2,3}));
        testSystem("  2 + 3  ",new Integer[]{5});
        testSystem(" ( (1 2) + 3 4 ) ",topOfStack(4,6));
      
        BasisSystem aplSystem = new LeftAplSystem();
        testSystem(aplSystem," 4 → testvar ",new Object[]{});
        assertEquals(4,aplSystem.getVariableValue("testvar"));
        testSystem(aplSystem," testvar ",new Integer[]{4});
        

        aplSystem = new LeftAplSystem();
        testSystem(aplSystem," ( 1 8 4 ) → testvar   ",new Object[]{});
        Object[] objs = (Object[])aplSystem.getVariableValue("testvar");
        assertArrayEquals(new Object[]{1,8,4},objs);
        testSystem(aplSystem," testvar [1] ",new Integer[]{8});
   
        aplSystem = new LeftAplSystem();
        testSystem(aplSystem," ( 1 8 4 ) → testvar   ",new Object[]{});
        testSystem(aplSystem," testvar [0 1]  ",topOfStack(1,8));
       
        
        testSystemStackDepth("     6 ⍳~ A~ ( 'a' 'b' ) →TV ",1);
        
    }
    
    @Test
    public void testUserDefinedUnary() { 
        testSystem("  1 2 3 4 5 6  . { 2 + ⍺ } ",topOfStack(3,4,5,6,7,8));
    }
    
    @Test
    public void testUserDefinedBinary() { 
        testSystem("  1 2 3 4 5 6  . { ⍹ + ⍺ } 8 ",topOfStack(9,10,11,12,13,14));
    }
    
    @Test
    public void testSystem1() {
        BasisSystem aplSystem = new LeftAplSystem();
        aplSystem.run(" 'org.h2.Driver' @class  ");
        aplSystem.run(" 'jdbc:h2:./test'  @connection  → DB  ");
        aplSystem.run(" 'create table if not exists test ( id int) ' . DB  ");
        aplSystem.run(" 'insert into test values (1) '  . DB "  );
        aplSystem.run(" 'insert into test values (2) '  . DB  ");
        aplSystem.run(" 'select id, id +1 from test ' . DB   ~cache~ → RESULTCACHE  ");
        aplSystem.run(" 'select id, id +1 from test ' . DB → RESULT  ");
        aplSystem.run("  RESULT →TV");
        aplSystem.run(" RESULT →TV");
        aplSystem.run(" RESULTCACHE →TV");
        aplSystem.run(" RESULTCACHE →TV ");
        aplSystem.run(" RESULT →CSV  'csv.erg'  ");
    }
    
    @Test
    public void testTemplates() {
        BasisSystem aplSystem = new LeftAplSystem();
        aplSystem.run(" 6 ⍳~ A~ ( 'a' ) ~T~ ' show $a$ '  →TV   ");   
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
        BasisSystem aplSystem = new LeftAplSystem();
        aplSystem.run(" 100   ?i []~ →TV ");
        aplSystem.run(" 100   ?d []~ →TV ");
        aplSystem.run(" 100 ?i~  → zufall ");
        aplSystem.run(" { ⍺ [ zufall ] → ⍹ } → rand ");
        aplSystem.run(" ( 'a' 'b' 'c' ) .rand 'A'  ");
        aplSystem.run(" ( 1 2 3 4 5 6 7 )  .rand 'B'  ");
        aplSystem.run(" ( 'Stuttgart' 'Remseck' )  .rand 'C'  ");
        aplSystem.run(" ( A B C  )  [~]zip~ A~ ( 'Wer' 'Anzahl' 'Ort') A~ ( 'Anzahl' 'Wer' 'Ort' ) ~U~ 'ina' →TV ");
    }

    
    @Test
    public void testXML() {
        BasisSystem aplSystem = new LeftAplSystem();
        aplSystem.run(" 'src/test/resources/kontoauszug.xml' XML~ →TV ");
    }
    
    @Test
    public void testPredicates() {
        testSystem(" 100 = 100 ",new Object[]{true});
        testSystem(" 100 = 80 ",new Object[]{false});
        testSystem(" 100 80 = 80 ",topOfStack(false,true));
        testSystem(" 100 80 ≠ 80 ",topOfStack(true,false));
        testSystem(" 100 80 ≥ 80 ",topOfStack(true,true));
        testSystem(" 100 80 60 > 70 ",topOfStack(true,true,false));
        testSystem(" 10 50 60 < 70 ",topOfStack(true,true,true));
        testSystem(" 10 50 60 ≤ 60 ",topOfStack(true,true,true));
        testSystem(" 10 50 60 < 60 ",topOfStack(true,true,false));
        testSystem(" ( 10 50 60 < 60 ) ¬ ",topOfStack(false,false,true));
        testSystem(" ( 100 80 = 80 ) ∧ ( 100 80 = 100 ) ",topOfStack(false,false));
        testSystem(" ( 100 80 = 80 ) ∨ ( 100 80 = 100 ) ",topOfStack(true,true));
    }

    @Test
    public void testFUnctions() {
        testSystem(" 6 ! ",new Object[]{(long)(1*2*3*4*5*6)});
        testSystem(" 2 5 6 ! ",topOfStack(2L,(long)(1*2*3*4*5),(long)(1*2*3*4*5*6)));
        testSystem(" 6 | 5 ",new Object[]{1});
        testSystem(" 6 10 4 11 12 | 5 ",topOfStack(1,0,4,1,2));
        testSystem(" 6 10 4 11 12 ⌈ 9 ",topOfStack(9,10,9,11,12));
        testSystem(" 6 10 4 11 12 ⌊ 9 ",topOfStack(6,9,4,9,9));
        testSystem(" 6 10 4 11 12 ⊢ 9 ",topOfStack(6,10,4,11,12));
        testSystem(" 6 10 4 11 12 ⊣ 9 ",topOfStack(9,9,9,9,9));
       }

}
