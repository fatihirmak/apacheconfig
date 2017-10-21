package org.irma.httpd.config;

import java.io.IOException;
import java.text.ParseException;

public class Test {

    public static void main(String[] args) {
        try {
            DocumentReader reader = new DocumentReader(Test.class.getResourceAsStream("httpd.conf"));
            Document document = reader.getDocument();
            for (Directive directive : document.getElements(Directive.class)) {
                System.out.println(String.format("%s%s", directive.getName(), directive.getArguments()));
            }
        } catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
        }
        
    }

}
