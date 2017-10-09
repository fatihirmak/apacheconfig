package org.irma.vipmanage.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TextScannerTest {
    
    @Test
    public void testDefault() {
        TextScanner scanner = new TextScanner("abc");
        assertEquals('a', scanner.next());
        assertEquals('a', scanner.next());
        assertEquals('a', scanner.forward());
        assertEquals('b', scanner.next());
        assertEquals('b', scanner.forward());
        assertEquals('c', scanner.forward());
        assertEquals(true, scanner.isEnd());
        assertEquals('c', scanner.retreat());
        assertEquals('b', scanner.retreat());
        assertEquals('a', scanner.previous());
        assertEquals('a', scanner.previous());
        assertEquals('a', scanner.retreat());
        assertEquals('\0', scanner.previous());
        assertEquals(true, scanner.isBeginning());
    }
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void testEndError() {
        TextScanner scanner = new TextScanner("a");
        scanner.forward();
        scanner.forward();
    }
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void testBeginningError() {
        TextScanner scanner = new TextScanner("a");
        scanner.retreat();
    }
    
    @Test
    public void testNewLine() {
        TextScanner scanner = new TextScanner("abc\ndef\r\nklm");
        assertEquals("abc", scanner.nextLine());
        assertEquals("def", scanner.nextLine());
        assertEquals("klm", scanner.nextLine());
    }
    
    @Test
    public void testNewLinePartial() {
        TextScanner scanner = new TextScanner("abc\ndef");
        scanner.forward();
        assertEquals("bc", scanner.nextLine());
        scanner.forward();
        scanner.forward();
        scanner.forward();
        assertEquals("", scanner.nextLine());
    }
    
    @Test
    public void testNewLineWithInvalidCarriageReturn() {
        TextScanner scanner = new TextScanner("ab\rc\nde\rf\r\nklm");
        assertEquals("ab\rc", scanner.nextLine());
        assertEquals("de\rf", scanner.nextLine());
    }
}
