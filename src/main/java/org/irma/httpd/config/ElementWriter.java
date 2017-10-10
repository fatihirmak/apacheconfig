package org.irma.httpd.config;

import java.io.IOException;

public interface ElementWriter<T> {
    public void write(T element, TextWriter writer) throws IOException;
}
