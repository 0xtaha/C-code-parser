import java.util.ArrayList;
import java.util.List;

public class CStandard extends CBaseVisitor<String> {

    public boolean InsideScope  ;
    int Line ;
    List<String> StructVar = new ArrayList<>();
    List<String> UnionVar = new ArrayList<>();
    List<String> EnumVar = new ArrayList<>();



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

        String Dec_structRegex = "([A-Z][a-z0-9]*_)+s" ;
        String Dec_UnionRegex = "([A-Z][a-z0-9_]*_)+u" ;

        if (ctx.structOrUnion().getText().matches("struct")) {
            if ((ctx.Identifier().getText().matches(Dec_structRegex)) == false) {
                Line = ctx.start.getLine();
                String L = String.valueOf(Line);
                System.out.printf("Violation, STRUCT , Line : %d%n", Line);
            }
            else{
                StructVar.add(ctx.Identifier().getText());
            }
        }

        else {
            if ((ctx.Identifier().getText().matches((Dec_UnionRegex)) == false)){
                Line = ctx.start.getLine();
                System.out.printf("Violation, UNION , Line : %d%n", Line);
            }
            else{
                UnionVar.add(ctx.Identifier().getText());
            }
        }

        return val;
    }



    @Override
    public String visitEnumSpecifier(CParser.EnumSpecifierContext ctx) {
        String val = String.valueOf(ctx.Identifier());
        String EnumRegex = "([A-Z][a-z0-9_]*_)+e";

        if ((ctx.Identifier().getText().matches(EnumRegex)) == false) {
            Line = ctx.start.getLine();
            System.out.printf("Violation, STRUCT , Line : %d%n", Line);
        }
        else{
            EnumVar.add(ctx.Identifier().getText());
        }

        return val;
    }

    @Override
    public String visitDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
        boolean is = InsideScope ;
        String val = String.valueOf(ctx.directDeclarator().Identifier());

        if( (ctx.directDeclarator().Identifier().getText().matches( "([A-Z][A-Za-z0-9]*_)+arg"))){

            System.out.println("inside Scope");
        }
        else{
            System.out.println("outside Scope");
        }
        if ((ctx.directDeclarator().Identifier().getText().matches("([A-Z][a-z0-9]*_)+f")) == false) {
            System.out.println("Warning, your variable name violates the correct name for function");
        }
        return val;
    }

    @Override public String visitTypedefName(CParser.TypedefNameContext ctx) {
        String IdString = ctx.Identifier().getText();
        int Line = -1 ;
        boolean is = InsideScope ;


        if(StructVar.contains(IdString)){
            String structRegex ;
            if(is){
                System.out.println("inside Scope");
                structRegex = "L_([A-Z][a-z0-9]*_)+s";

            }
            else{
                System.out.println("outside Scope");
                structRegex = "G_([A-Z][a-z0-9]*_)+s";
            }


            if ((ctx.Identifier().getText().matches(structRegex)) == false) {
                Line = ctx.start.getLine();
                System.out.printf("Violation, STRUCT , Line : %d%n\n", Line);
            }
        }


        if(UnionVar.contains(IdString)){
            String UnionRegex ;
            if(is){
                System.out.println("inside Scope");
                UnionRegex = "L_([A-Z][a-z0-9_]*_)+u";

            }
            else{
                System.out.println("outside Scope");
                UnionRegex = "G_([A-Z][a-z0-9_]*_)+u";
            }


            if ((ctx.Identifier().getText().matches(UnionRegex)) == false) {
                Line = ctx.start.getLine();
                System.out.printf("Violation, STRUCT , Line : %d%n\n", Line);
            }

        }
        if(EnumVar.contains(IdString)){
            String EnumRegex ;
            if(is){
                System.out.println("inside Scope");
                EnumRegex = "L_([A-Z][a-z0-9_]*_)+e";
            }
            else{
                System.out.println("outside Scope");
                EnumRegex = "G_([A-Z][a-z0-9_]*_)+e";
            }

            if ((ctx.Identifier().getText().matches(EnumRegex)) == false) {
                Line = ctx.start.getLine();
                System.out.printf("Violation, ENUM ,Line : %d%n\n", Line);
            }

        }
        return visitChildren(ctx);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
}

