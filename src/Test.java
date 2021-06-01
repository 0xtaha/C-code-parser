import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String InputFile = "test.txt";
        FileInputStream is = new FileInputStream(InputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);
        CLexer lexer = new CLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser( tokens);
        ParseTree tree = parser.prog();

        TypeSpec DefinitionType = new TypeSpec();
        DefinitionType.visit(tree);
    }

}
