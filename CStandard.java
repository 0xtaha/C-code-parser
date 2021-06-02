
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.io.FileInputStream;
import  org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
public class CStandard extends CBaseVisitor<String> {
    @Override
    public String visitGlobalORlocal(CParser.GlobalORlocalContext ctx) {

        String val = String.valueOf(ctx.GlobalOrLocal().toString());
        if (ctx.GlobalOrLocal().toString().equals("global")) {
            System.out.println("global");
        } else {
            System.out.println("local");
        }
        return val;

    }

    @Override
    public String visitSTRUCTORUNION(CParser.STRUCTORUNIONContext ctx) {
      String val =String.valueOf(ctx.structOrUnionSpecifier());
      if(!(ctx.structOrUnionSpecifier().toString().matches("([A-Z][a-z0-9]*_)+s")))
      {
          System.out.println("violation");
      }
        return val;
    }


}