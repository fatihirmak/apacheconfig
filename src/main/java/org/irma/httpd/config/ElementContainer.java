package org.irma.httpd.config;

import java.util.List;

public interface ElementContainer {
	public List<Element> getElements();
	
	public <T extends Element> List<T> getElements(Class<T> cls);
}
