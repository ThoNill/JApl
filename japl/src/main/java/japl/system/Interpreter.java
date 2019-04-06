package japl.system;

import java.util.Deque;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import apl.AplReaderParser.AplContext;
import apl.AplReaderParser.BwordsContext;
import apl.AplReaderParser.ConstantContext;
import apl.AplReaderParser.CwordsContext;
import apl.AplReaderParser.OperationContext;
import apl.AplReaderParser.PwordsContext;
import apl.AplReaderParser.VariableContext;
import apl.AplReaderParser.WordContext;
import apl.AplReaderParser.WordsContext;
import japl.basis.AplRuntimeException;
import japl.basis.BinaryFunctionDictonary;
import japl.basis.BinaryStackOperation;
import japl.basis.ConstantCreator;
import japl.basis.Creator;
import japl.basis.CreatorDictonary;
import japl.basis.CreatorStackOperation;
import japl.basis.ManyStackOperation;
import japl.basis.SingleConstantCreator;
import japl.basis.StackOperation;
import japl.basis.UnaryFunctionDictonary;
import japl.basis.UnaryStackOperation;
import japl.basis.UserDefinedFunction;
import japl.basis.Variable;
import japl.basis.VariableCreator;
import japl.basis.VariableDictonary;

class Interpreter {
    private VariableDictonary variables;
    private UnaryFunctionDictonary unary;
    private BinaryFunctionDictonary binary;
    private CreatorDictonary creators;
    private Deque<Object> alphaStack;
    private Deque<Object> omegaStack;
    private boolean aplMode;
    

   
    Interpreter(VariableDictonary variables,
            UnaryFunctionDictonary unary, BinaryFunctionDictonary binary,
            CreatorDictonary creators, Deque<Object> alphaStack,
            Deque<Object> omegaStack,boolean aplMode) {
        super();
        this.variables = variables;
        this.unary = unary;
        this.binary = binary;
        this.creators = creators;
        this.alphaStack = alphaStack;
        this.omegaStack = omegaStack;
        this.aplMode = aplMode;
    }

    StackOperation createOperation(ParseTree tree) {
        return createOp(new ManyStackOperation(aplMode), 0, tree);
    }

    private StackOperation createOp(ManyStackOperation many, int position,
            ParseTree tree) {
        if (tree instanceof AplContext) {
            return createManyOperation(tree);
        }
        if (tree instanceof WordsContext) {
            return addWordsOperations(many, tree);
        }
        if (tree instanceof WordContext) {
            return addWordOperation(many, position, (WordContext) tree);
        }
        return new ManyStackOperation(aplMode);
    }

    private StackOperation addWordOperation(ManyStackOperation many,
            int position, WordContext tree) {
        addConstant(many, tree);
        addVariable(many, tree);
        addOperation(many, position, tree);
        addBWords(many, tree);
        addCWords(many, tree);
        addPWords(many, tree);

        return many;
    }

    private void addPWords(ManyStackOperation many, WordContext tree) {
        PwordsContext pwords = tree.pwords();
        if (pwords != null) {
            many.add(createManyOperation(pwords.words()));
        }
    }

    private void addCWords(ManyStackOperation many, WordContext tree) {
        CwordsContext cwords = tree.cwords();
        if (cwords != null) {
            int oldAlphaCount = creators.getCount(getBinaryFunctionSign());
            StackOperation op = createManyOperation(cwords.words());
            int newAlphaCount = creators.getCount(getBinaryFunctionSign());
            UserDefinedFunction f = new UserDefinedFunction(op, alphaStack, omegaStack,oldAlphaCount == newAlphaCount,aplMode);
            
            SingleConstantCreator creator = new SingleConstantCreator();
            creator.add(f);
            many.add(new CreatorStackOperation(creator));
        }
    }

    private String getBinaryFunctionSign() {
        return aplMode ? "⍺" : "⍹";
    }

    private void addBWords(ManyStackOperation many, WordContext tree) {
        BwordsContext bwords = tree.bwords();
        if (bwords != null) {
                addBinaryFunction(many,"⌷");
            many.add(createManyOperation(bwords.words()));
        }
    }

    private void addOperation(ManyStackOperation many, int position,
            WordContext tree) {
        OperationContext op = tree.operation();
        if (op != null) {
            String opName = op.APL().getSymbol().getText();
            boolean isCreator = creators.containsKey(opName);
            boolean isUnary = unary.containsKey(opName);
            boolean isBinary = binary.containsKey(opName);
            if (isCreator) {
                many.add(new CreatorStackOperation(creators.getFunction(opName)));    
            } else {
                if (position > 0 && isBinary) {
                    addBinaryFunction(many, opName);
                } else {
                    if (isUnary) {
                        many.add(new UnaryStackOperation(unary
                                .getFunction(opName)));
                    } else {
                        throw new AplRuntimeException("No Unary function "
                                + opName);
                    }
                }
            }

        }
    }

    private void addBinaryFunction(ManyStackOperation many, String opName) {
        BinaryStackOperation bOp = new BinaryStackOperation(
                binary.getFunction(opName));
        many.add(bOp);
    }

    private void addVariable(ManyStackOperation many, WordContext tree) {
        VariableContext v = tree.variable();
        if (v != null) {
            Variable variable = variables.getVariable(v.getText());
            many.add(new CreatorStackOperation(new VariableCreator(variable)));
        }
    }

    private void addConstant(ManyStackOperation many, WordContext tree) {
        ConstantContext c = tree.constant();
        if (c != null) {
            StackOperation lastOp = many.lastOp();
            ConstantCreator creator = null;
            if (lastOp != null && lastOp instanceof CreatorStackOperation) {
                CreatorStackOperation cop = (CreatorStackOperation) lastOp;
                Creator pc = cop.getCreator();
                if (pc instanceof ConstantCreator) {
                    creator = (ConstantCreator) pc;
                }
            }
            if (creator == null) {
                creator = new ConstantCreator();
                many.add(new CreatorStackOperation(creator));
            }

            TerminalNode text = c.Text();
            TerminalNode i = c.Int();
            TerminalNode d = c.Double();
            if (text != null) {
                creator.add(entfeneHochkomma(text.toString()));
            }
            if (i != null) {
                creator.add(Integer.parseInt(i.getText()));
            }
            if (d != null) {
                creator.add(Double.parseDouble(d.getText()));
            }
        }
    }

    private String entfeneHochkomma(String text) {
       return text.substring(1, text.length()-1);
    }

    private StackOperation createManyOperation(ParseTree tree) {
        ManyStackOperation many = new ManyStackOperation(aplMode);
        return addWordsOperations(many, tree);
    }

    private StackOperation addWordsOperations(ManyStackOperation many,
            ParseTree tree) {
        int anz = tree.getChildCount();
        for (int i = 0; i < anz; i++) {
            createOp(many, i, tree.getChild(i));
        }
        return many;
    }

}
