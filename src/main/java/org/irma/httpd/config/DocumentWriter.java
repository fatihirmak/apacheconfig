package org.irma.httpd.config;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class DocumentWriter {
    
    private TextWriter writer;
    
    public DocumentWriter(OutputStream stream) {
        writer = new TextWriter(new OutputStreamWriter(stream), new DocumentFormatOptions());
    }
    
    public DocumentWriter(OutputStream stream, DocumentFormatOptions formatter) {
        writer = new TextWriter(new OutputStreamWriter(stream), formatter);
    }
    
    protected TextWriter getWriter() {
        return writer;
    }
    
    @SuppressWarnings("unchecked")
	public void write(Element element) throws IOException {
		ElementWriter writer = element.geElementWriter();
        writer.write(element, getWriter());
        getWriter().flush();
    }
}
