package org.irma.httpd.config;

public interface Element {
	
	public ElementWriter<? extends Element> geElementWriter();
}
