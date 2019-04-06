package japl.system;

import java.util.ArrayDeque;
import java.util.Deque;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.log4j.Logger;

import apl.AplReaderLexer;
import apl.AplReaderParser;
import japl.basis.BinaryFunctionDictonary;
import japl.basis.CreatorDictonary;
import japl.basis.StackOperation;
import japl.basis.UnaryFunctionDictonary;
import japl.basis.VariableDictonary;
import japl.basis.WorkingStack;

public class BasisSystem {
    private static Logger logger = Logger.getLogger(BasisSystem.class);
    
    protected UnaryFunctionDictonary unary = new UnaryFunctionDictonary();
    protected BinaryFunctionDictonary binary = new BinaryFunctionDictonary();
    protected CreatorDictonary creator = new CreatorDictonary();
    protected VariableDictonary variables = new VariableDictonary();
    protected Deque<Object> alphaStack = new ArrayDeque<>();
    protected Deque<Object> omegaStack = new ArrayDeque<>();
    protected Interpreter interpreter;
    protected WorkingStack stack;

    BasisSystem(boolean aplSystem) {
        super();
        stack = new WorkingStack(aplSystem);
    }

    public void run(String aplProgram) {
        AplReaderLexer lexer = new AplReaderLexer(new ANTLRInputStream(aplProgram));
        AplReaderParser parser = new AplReaderParser(new CommonTokenStream(
                lexer));
        ParseTree root = parser.apl();
        StackOperation op = interpreter.createOperation(root);
        logger.debug(op.toString());
        stack.apply(op);
        
    }

    public Object getVariableValue(String name) {
        return variables.getVariable(name).getValue();
    }

    public Object[] getWorkingStackArray() {
        return stack.toArray();
    }

}