
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.io.FileInputStream;
import  org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
public class CStandard extends CBaseVisitor<String> {


    @Override
    public String visitStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext ctx) {
        String val = String.valueOf(ctx.structOrUnion());
        if (ctx.structOrUnion().getText().matches("struct")) {
            if ((ctx.Identifier().getText().matches("([A-Z][a-z0-9]*_)+s")) == false)
                System.out.println("Violation, STRUCT");
        } else {
            if ((ctx.Identifier().getText().matches("([A-Z][a-z0-9]*_)+u")) == false) {
                System.out.println("Violation, UNION");

            }
        }
        return val;
    }

    @Override
    public String visitEnumSpecifier(CParser.EnumSpecifierContext ctx) {
        String val = String.valueOf(ctx.Identifier());

        if ((ctx.Identifier().getText().matches("([A-Z][a-z0-9]*_)+e")) == false) {
            System.out.println("Violation, ENUM");
        }
        return val;
    }

    @Override
    public String visitFunctionSpecifier(CParser.FunctionSpecifierContext ctx) {
        String val = String.valueOf(ctx.Identifier());
        if ((ctx.Identifier().getText().matches("([A-Z][a-z0-9]*_)+f")) == false) {
            System.out.println("Violation, function");
        }
        return val;
    }


}