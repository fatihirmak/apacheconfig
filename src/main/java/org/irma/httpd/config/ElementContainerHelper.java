package org.irma.httpd.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ElementContainerHelper {
    @SuppressWarnings("unchecked")
    public <T extends Element> List<T> getElements(List<Element> list, Class<T> cls) {
        List<T> elements = new ArrayList<T>();
        for (Element element : list) {
            if (cls.isAssignableFrom(element.getClass())) {
                elements.add((T)element);
            }
        }
        return elements;
    }
    
    @SuppressWarnings("unchecked")
    public void write(ElementContainer container, TextWriter writer) throws IOException {
        for (Element element : container.getElements()) {
            @SuppressWarnings("rawtypes")
            ElementWriter elementWriter = element.geElementWriter();
            elementWriter.write(element, writer);
        }
    }
}
