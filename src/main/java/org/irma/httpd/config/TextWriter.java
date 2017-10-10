package org.irma.httpd.config;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class TextWriter extends BufferedWriter {
    private int indent;
    private DocumentFormatOptions formatOptions;
    
    TextWriter(Writer out, DocumentFormatOptions formatter) {
        super(out);
        this.formatOptions = formatter; 
        indent = 0;
    }

    public String getCurrentIndentString() {
        StringBuffer buffer = new StringBuffer(); 
        for (int i = 0; i < indent; i++) {
            buffer.append(getFormatOptions().getIndentPolicy().getCharacter());
        }
        return buffer.toString();
    }
    
    protected void increaseIndent() {
        indent += getFormatOptions().getIndentSize();
    }
    
    protected void decreaseIndent() {
        indent -= getFormatOptions().getIndentSize();
    }
    
    protected DocumentFormatOptions getFormatOptions() {
        return formatOptions;
    }
    
    public TextWriter appendCurrentIndent() throws IOException {
        append(getCurrentIndentString());
        return this;
    }
    
    public void write(String... strings) throws IOException {
    	for (int i = 0; i < strings.length; i++) {
    		write(strings[i]);
    		write(getFormatOptions().getArgumentSeparatorPolicy().getCharacter());
    	}
    }
}
