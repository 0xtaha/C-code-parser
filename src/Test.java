import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String InputFile = "test.c";
        FileInputStream is = new FileInputStream(InputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);
        CLexer lexer = new CLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser( tokens);
        ParseTree tree = parser.compilationUnit();

        CStandard CheckNames = new CStandard();
        CheckNames.visit(tree);
    }

}
