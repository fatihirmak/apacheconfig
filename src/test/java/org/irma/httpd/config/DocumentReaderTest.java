package org.irma.httpd.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class DocumentReaderTest {
    
    @Test
    public void testDefault() throws IOException, ParseException {
        DocumentReader reader = newDocumentReader("#Comment\r\nDirective 1 2\r\n<section arg>\r\nDirective 3 4\r\n</section>");
        Document document = reader.getDocument();
        Iterator<Element> iterator = document.getElements().iterator();
        assertComment("Comment", iterator.next());
        assertDirective(new Directive("Directive", argumentsOf("1", "2")), iterator.next());
        Section section = new Section("section", argumentsOf("arg"), 
        		argumentsOf((Element) new Directive("Directive", argumentsOf("3", "4"))));
        assertSection(section, iterator.next());
    }
    
    @Test(expected=ParseException.class)
    public void testException() throws IOException, ParseException {
    	DocumentReader reader = newDocumentReader("#Comment\r\nDirective 1 2\r\n<section arg>\r\nDirective 3 4\r\n");
        reader.getDocument();
    }
    
    @Test(expected=ParseException.class)
    public void testExceptionCloseTag() throws IOException, ParseException {
    	DocumentReader reader = newDocumentReader("<section arg>\r\nDirective 3 4\r\n</>");
        reader.getDocument();
    }
    
    @Test(expected=ParseException.class)
    public void testExceptionCloseTagNoMatch() throws IOException, ParseException {
    	DocumentReader reader = newDocumentReader("Directive 3 4\r\n</section>");
        reader.getDocument();
    }
    
    @Test(expected=ParseException.class)
    public void testExceptionMixedTags() throws IOException, ParseException {
    	DocumentReader reader = newDocumentReader("<section>\r\nDirective 3 4\r\n<section2></section>");
        reader.getDocument();
    }
    
    private DocumentReader newDocumentReader(String content) throws IOException {
    	return new DocumentReader(new ByteArrayInputStream(content.getBytes()));
    }
    
    private void assertComment(String comment, Element element) {
    	assertTrue(element.getClass() == Comment.class);
    	assertEquals(comment, ((Comment) element).getContent());
    }
    
    private void assertDirective(Directive directive, Element element) {
    	assertTrue(element.getClass() == Directive.class);
    	Directive actual = (Directive) element;
    	assertEquals(directive.getName(), actual.getName());
    	assertEquals(directive.getArguments(), actual.getArguments());
    }
    
    private void assertSection(Section section, Element element) {
    	assertTrue(element.getClass() == Section.class);
    	Section actual = (Section) element;
    	assertEquals(section.getName(), actual.getName());
    	assertEquals(section.getArguments(), actual.getArguments());
    	assertEquals(section.getElements(), actual.getElements());
    }
    
    private <T> List<T> argumentsOf(@SuppressWarnings("unchecked") T ... arguments) {
    	return Arrays.asList(arguments);
    }
}
