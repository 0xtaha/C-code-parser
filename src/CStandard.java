public class CStandard extends CBaseVisitor<String> {
    public boolean InsideScope  ;

    public class CListener extends CBaseListener {


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
        boolean is = InsideScope ;

        String structRegex ;
        String UnionRegex ;
        if(is){
            System.out.println("inside Scope");
            structRegex = "L_([A-Z][a-z0-9]*_)+s";
            UnionRegex = "L_([A-Z][a-z0-9_]*_)+u";
        }
        else{
            System.out.println("outside Scope");
            structRegex = "G_([A-Z][a-z0-9]*_)+s";
            UnionRegex = "G_([A-Z][a-z0-9_]*_)+u";
        }

        if (ctx.structOrUnion().getText().matches("struct")) {
            if ((ctx.Identifier().getText().matches(structRegex)) == false)
                System.out.println("Violation, STRUCT");
        } else {
            if ((ctx.Identifier().getText().matches((UnionRegex)) == false)){
                System.out.println("Violation, UNION");

            }
        }
        return val;
    }

    @Override
    public String visitEnumSpecifier(CParser.EnumSpecifierContext ctx) {
        String val = String.valueOf(ctx.Identifier());
        String EnumRegex ;
        boolean is = InsideScope ;
        if(is){
            System.out.println("inside Scope");
            EnumRegex = "L_([A-Z][a-z0-9_]*_)+e";
        }
        else{
            System.out.println("outside Scope");
            EnumRegex = "G_([A-Z][a-z0-9_]*_)+e";
        }

        if ((ctx.Identifier().getText().matches(EnumRegex)) == false) {
            System.out.println("Violation, ENUM");
        }
        return val;
    }

    @Override
    public String visitFunctionSpecifier(CParser.FunctionSpecifierContext ctx) {
        String val = String.valueOf(ctx.Identifier());

        String FuncRegex = "([A-Z][a-z0-9]*_)+f" ;
        boolean is = InsideScope ;
        if(is){
            System.out.println("inside Scope");
        }
        else{
            System.out.println("outside Scope");
        }

        if ((ctx.Identifier().getText().matches(FuncRegex)) == false) {
            System.out.println("Violation, function");
        }
        return val;
    }
}

