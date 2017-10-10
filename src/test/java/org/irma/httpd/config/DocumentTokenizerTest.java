package org.irma.httpd.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DocumentTokenizerTest {
	
	@Test
    public void testEmptyDocument() {
	    DocumentTokenizer tokenizer = new DocumentTokenizer("");
	    assertEquals(tokenizer.nextToken().getType(), TokenType.END_OF_FILE);
	    
	    tokenizer = new DocumentTokenizer("  ");
	    assertEquals(tokenizer.nextToken().getType(), TokenType.END_OF_FILE);
        
        tokenizer = new DocumentTokenizer("  \r\n");
        assertEquals(TokenType.NEWLINE, tokenizer.nextToken().getType());
	}
	
	@Test
    public void testComment() {
        DocumentTokenizer tokenizer = new DocumentTokenizer("#Test Comment\r\n#Second Comment");
        assertEquals(TokenType.COMMENT, tokenizer.nextToken().getType());
        assertEquals(TokenType.COMMENT, tokenizer.nextToken().getType());
    }
	
	@Test
    public void testComplex() {
        DocumentTokenizer tokenizer = new DocumentTokenizer("#Test Comment\r\nDirective 120\r\nNewDirective");
        assertEquals(TokenType.COMMENT, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
        assertEquals(TokenType.NEWLINE, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
    }
	
	@Test
    public void testStrings() {
        DocumentTokenizer tokenizer = new DocumentTokenizer("#Test Comment\r\nDirective Argument 'String argument' \"Double Quote\"");
        assertEquals(TokenType.COMMENT, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
        assertEquals(TokenType.STRING, tokenizer.nextToken().getType());
        assertEquals(TokenType.STRING, tokenizer.nextToken().getType());
    }
	
	@Test
    public void testSections() {
        DocumentTokenizer tokenizer = new DocumentTokenizer("<Section Argument>\r\nDirective Argument\r\n</Section>");
        assertEquals(TokenType.SECTION_START_OPEN, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
        assertEquals(TokenType.SECTION_CLOSE, tokenizer.nextToken().getType());
        assertEquals(TokenType.NEWLINE, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
        assertEquals(TokenType.NEWLINE, tokenizer.nextToken().getType());
        assertEquals(TokenType.SECTION_END_OPEN, tokenizer.nextToken().getType());
        assertEquals(TokenType.KEYWORD, tokenizer.nextToken().getType());
        assertEquals(TokenType.SECTION_CLOSE, tokenizer.nextToken().getType());
    }
    
}
