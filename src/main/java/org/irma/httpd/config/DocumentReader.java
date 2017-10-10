package org.irma.httpd.config;

import static org.irma.httpd.config.TokenType.END_OF_FILE;
import static org.irma.httpd.config.TokenType.KEYWORD;
import static org.irma.httpd.config.TokenType.NEWLINE;
import static org.irma.httpd.config.TokenType.SECTION_CLOSE;
import static org.irma.httpd.config.TokenType.STRING;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DocumentReader {
    private static final int BUFFER_SIZE = 1024;
    
    private String configContent;
    private DocumentTokenizer tokenizer;
    private Stack<ElementContainer> containers;
    
    public DocumentReader(File configFile) throws IOException, FileNotFoundException {
        this(new FileInputStream(configFile));
    }
    
    public DocumentReader(InputStream stream) throws IOException {
        configContent = readStreamContent(stream);
        tokenizer = new DocumentTokenizer(configContent);
        containers = new Stack<>();
        containers.push(createDocument());
    }
    
    protected String getConfigContent() {
        return configContent;
    }
    
    protected Stack<ElementContainer> getContainers() {
        return containers;
    }
    
    protected String readStreamContent(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer content = new StringBuffer(); 
        char[] buffer = new char[BUFFER_SIZE];
        int length = -1;
        while ((length = reader.read(buffer, 0, BUFFER_SIZE)) > -1) {
            content.append(buffer, 0, length);
        }
        return content.toString();
    }
    
    public Document getDocument() throws ParseException {
        DocumentTokenizer tokenizer = getDocumentTokenizer();
        Token token = tokenizer.nextToken();
        while (token.getType() != END_OF_FILE) {
        	handleToken(token);
        	token = tokenizer.nextToken();
        }
        ElementContainer container = containers.pop();
        if (container instanceof Document) {
            return (Document) container;
        } else {
            throw new ParseException(String.format("Section left unclosed: %s", container.toString()), 0);
        }
    }
    
    protected void handleToken(Token token) throws ParseException {
        ElementContainer container = getContainers().peek();
        List<Element> elements = container.getElements();
        String content = getTokenString(token);
        switch (token.getType()) {
        case COMMENT:
            elements.add(createComment(content));
            break;
            
        case KEYWORD:
            elements.add(createDirective(content));
            break;
            
        case SECTION_START_OPEN:
            Section section = createSection();
            elements.add(section);
            getContainers().push(section);
            break;
            
        case SECTION_END_OPEN:
            token = tokenizer.nextToken();
            String tagContent = getTokenString(token);
            if (token.getType() != KEYWORD) {
                throw newParseException(token, "Expecting close tag label.");
            }
            if (container instanceof Section) {
                Section currentSection = (Section) container;
                if (currentSection.getName().equals(tagContent)) {
                    getContainers().pop();
                } else {
                    throw new ParseException(String.format("Section '%s' is not the current open section.", tagContent), token.getRegion().getStart());
                }
            } else {
                throw new ParseException(String.format("No opening tag for section '%s'", getTokenString(token)), token.getRegion().getStart());
            }
            
            break;
            
        default:
            
        }
    }
    
    protected Comment createComment(String content) {
        Comment comment = new Comment(content);
        return comment;
    }
    
    protected Directive createDirective(String content) throws ParseException {
        List<String> arguments = new ArrayList<>(); 
        Token token = tokenizer.nextToken();
        while (token.getType() != END_OF_FILE && token.getType() != NEWLINE) {
            if (token.getType() != KEYWORD && token.getType() != STRING) {
                throw newParseException(token);
            }
            arguments.add(getTokenString(token));
            token = tokenizer.nextToken();
        }
        return new Directive(content, arguments);
    }
    
    protected Section createSection() throws ParseException {
        Token token = tokenizer.nextToken();
        if (token.getType() != KEYWORD) {
            throw newParseException(token, "Expecting a section tag.");
        }
        String name = getTokenString(token);
        
        List<String> arguments = new ArrayList<>();
        token = tokenizer.nextToken();
        while (token.getType() != SECTION_CLOSE) {
            if (token.getType() == END_OF_FILE) {
                throw newParseException(token, "End of file is not expected here.");
            }
            if (token.getType() != KEYWORD && token.getType() != TokenType.STRING) {
                throw newParseException(token);
            }
            arguments.add(getTokenString(token));
            token = tokenizer.nextToken();
        }
        return new Section(name, arguments, new ArrayList<Element>());
    }
    
    protected ParseException newParseException(Token token, String customMessage) {
        return new ParseException(String.format("Unexpected token '%s'. %s", 
                                                getTokenString(token), customMessage), 
                                  token.getRegion().getStart());
    }
    
    protected ParseException newParseException(Token token) {
        return new ParseException(String.format("Unexpected token '%s'. %s", 
                                                getTokenString(token), ""), 
                                  token.getRegion().getStart());
    }
    
    protected Document createDocument() {
        return new Document();
    }
    
    protected DocumentTokenizer getDocumentTokenizer() {
        return tokenizer;
    }
    
    protected String getTokenString(Token token) {
        Region region = token.getRegion();
        return getConfigContent().substring(region.getStart(), region.getEnd());
    }
}
