package org.irma.httpd.config;

public enum WhiteSpaceType {
    TAB('\t'), SPACE(' ');
    
    private char charecter;
    
    WhiteSpaceType(char ch) {
        this.charecter = ch;
    }
    
    public char getCharacter() {
        return charecter;
    }
};