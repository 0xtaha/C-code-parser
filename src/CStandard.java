public class CStandard extends CBaseVisitor<String> {
    boolean InsideScope;

    private class CListenerAndCvistor extends CBaseListener {

        @Override public void enterCompoundStatement(CParser.CompoundStatementContext ctx) {
            InsideScope = true ;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation does nothing.</p>
         */

        @Override public void exitCompoundStatement(CParser.CompoundStatementContext ctx) {
            InsideScope = false ;
        }
        /**
         * {@inheritDoc}
         *
         * <p>The default implementation does nothing.</p>
         */

    }

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

