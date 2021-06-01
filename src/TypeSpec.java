public class TypeSpec extends CBaseVisitor <String> {
    @Override public String visitTypedefName(CParser.TypedefNameContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */

}
