package org.irma.httpd.config;

public class DocumentFormatOptions {
    
    private int indentSize = 2;
    private WhiteSpaceType indentCharacterPolicy = WhiteSpaceType.SPACE;
    private WhiteSpaceType argumentSeparatorPolicy = WhiteSpaceType.SPACE;
    
    public int getIndentSize() {
        return indentSize;
    }
    public void setIndentSize(int indentSize) {
        this.indentSize = indentSize;
    }
    public WhiteSpaceType getIndentPolicy() {
        return indentCharacterPolicy;
    }
    public void setIndentPolicy(WhiteSpaceType indentCharacter) {
        this.indentCharacterPolicy = indentCharacter;
    }
    public WhiteSpaceType getArgumentSeparatorPolicy() {
        return argumentSeparatorPolicy;
    }
    public void setArgumentSeparatorPolicy(WhiteSpaceType argumentSeparatorCharacter) {
        this.argumentSeparatorPolicy = argumentSeparatorCharacter;
    }
}
