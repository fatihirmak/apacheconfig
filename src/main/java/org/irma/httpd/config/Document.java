package org.irma.httpd.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Document implements ElementContainer, Element {
    private static final ElementContainerHelper HELPER = new ElementContainerHelper();
    
    private List<Element> elements;
    
    public Document() {
		elements = new ArrayList<>();
	}
	
    public List<Element> getElements() {
    	return elements;
    }

    @Override
    public ElementWriter<Document> geElementWriter() {
        return new ElementWriter<Document>() {
            
            @Override
            public void write(Document document, TextWriter writer) throws IOException {
            	HELPER.write(Document.this, writer);
            }
        };
    }
    
    @Override
    public <T extends Element> List<T> getElements(Class<T> cls) {
        return HELPER.getElements(getElements(), cls);
    }
}
