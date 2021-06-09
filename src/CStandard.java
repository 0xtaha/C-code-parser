import org.json.simple.JSONObject;

public class CStandard extends CBaseVisitor<String> {
    public JSONObject declarations = new JSONObject();
    public JSONObject global = new JSONObject();
    public JSONObject local = new JSONObject();

    CStandard(JSONObject global, JSONObject local, JSONObject declarations){
        this.global = global;
        this.local = local;
        this.declarations = declarations;

    }

    public boolean InsideScope  ;
    int Line ;
    int DataStructureType ; // 0 --> Struct , 1 --> Union , 2 --> Enum


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
        String struct_Regex;
        String union_Regex;


        String val = String.valueOf(ctx.structOrUnion());


        struct_Regex = declarations.get("structures").toString();
        union_Regex = declarations.get("unions").toString();

        if (ctx.structOrUnion().getText().matches("struct")) {
            DataStructureType = 0 ;
            if ((ctx.Identifier().getText().matches(struct_Regex)) == false) {
                Line = ctx.start.getLine();
                String L = String.valueOf(Line);
                System.out.printf("Violation, STRUCT , Line : %d%n", Line);
            }
        }

        else {
            DataStructureType = 1 ;
            if ((ctx.Identifier().getText().matches((union_Regex)) == false)){
                Line = ctx.start.getLine();
                System.out.printf("Violation, UNION , Line : %d%n", Line);
            }
        }

        return val;
    }



    @Override
    public String visitEnumSpecifier(CParser.EnumSpecifierContext ctx) {
        String Enum_Regex;

        String val = String.valueOf(ctx.Identifier());
        Enum_Regex = declarations.get("enumerations").toString();

        DataStructureType = 2 ;

        if ((ctx.Identifier().getText().matches(Enum_Regex)) == false) {
            Line = ctx.start.getLine();
            System.out.printf("Violation, ENUM , Line : %d%n", Line);
        }
        return val;
    }

    @Override
    public String visitDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
        String func_Regex;

        String val = String.valueOf(ctx.directDeclarator().Identifier());
        func_Regex =declarations.get("functions").toString();

        if( (ctx.directDeclarator().Identifier().getText().matches( func_Regex))){

            System.out.println("inside Scope");
        }
        else{
            System.out.println("outside Scope");
        }
        if ((ctx.directDeclarator().Identifier().getText().matches(func_Regex) == false)) {
            System.out.println("Warning, your variable name violates the correct name for function");
        }
        return val;
    }

    @Override public String visitTypedefName(CParser.TypedefNameContext ctx) {
        String G_struct_Regex;
        String G_union_Regex;
        String G_enumerations_Regex;
        String L_struct_Regex;
        String L_union_Regex;
        String L_enumerations_Regex;

        int Line = -1 ;
        boolean is = InsideScope ;

        G_struct_Regex = global.get("structures").toString();
        G_union_Regex =global.get("unions").toString();
        G_enumerations_Regex = global.get("enumerations").toString();
        L_struct_Regex = local.get("structures").toString();
        L_union_Regex = local.get("unions").toString();
        L_enumerations_Regex = local.get("enumerations").toString();


        if(DataStructureType == 0){
            String structRegex ;
            if(is){
                System.out.println("inside Scope");
                structRegex =  L_struct_Regex;
            }
            else{
                System.out.println("outside Scope");
                structRegex = G_struct_Regex;
            }


            if ((ctx.Identifier().getText().matches(structRegex)) == false) {
                Line = ctx.start.getLine();
                System.out.printf("Violation, STRUCT , Line : %d%n\n", Line);
            }
        }


        if(DataStructureType == 1){
            String UnionRegex ;
            if(is){
                System.out.println("inside Scope");
                UnionRegex = L_union_Regex;

            }
            else{
                System.out.println("outside Scope");
                UnionRegex = G_union_Regex;

            }


            if ((ctx.Identifier().getText().matches(UnionRegex)) == false) {
                Line = ctx.start.getLine();
                System.out.printf("Violation, Union , Line : %d%n\n", Line);
            }

        }
        if(DataStructureType == 2){
            String EnumRegex ;
            if(is){
                EnumRegex = L_enumerations_Regex;

            }
            else{
                EnumRegex = G_enumerations_Regex;
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

