package org.irma.vipmanage.parser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class TextScanner {
    private static final short INDEX_BEGINNING = 0;
    private static final char LINE_FEED = 10;
    private static final char CARRIAGE_RETURN = 13;
    private char[] text;
    private int index;
    
    public TextScanner(String text) {
        this.text = text.toCharArray();
        this.index = 0;
    }
    
    public char next() {
        return isEnd() ? '\0' : text[index];
    }
    
    public char previous() {
        return isBeginning() ? '\0' : text[index - 1];
    }
    
    public char forward() {
        if (isEnd()) {
            throw new IndexOutOfBoundsException();
        }
        return text[index++];
    }
    
    public char retreat() {
        if (isBeginning()) {
            throw new IndexOutOfBoundsException();
        }
        return text[--index];
    }
    
    public boolean isEnd() {
        return index == text.length;
    }
    
    public boolean isBeginning() {
        return index == INDEX_BEGINNING;
    }
    
    public String nextLine() {
        StringBuffer buffer = new StringBuffer();
        while (!isEnd() && !isNextNewLineCharacter()) {
            buffer.append(forward());
        }
        // push the pointer to the next line
        while (next() == LINE_FEED || next() == CARRIAGE_RETURN) {
            forward();
        }
        return buffer.toString();
    }
    
    private boolean isNextNewLineCharacter() {
        if (isEnd()) {
            return false;
        }
        boolean isNextNewLineCharacter = false;
        char ch = forward();
        if (ch == LINE_FEED) {
            isNextNewLineCharacter = true;
        }
        else if (ch == CARRIAGE_RETURN && next() == LINE_FEED) {
            isNextNewLineCharacter = true;
        }
        retreat();
        return isNextNewLineCharacter;
    }
}
