package org.irma.httpd.config;

import static org.irma.httpd.config.Constants.BACKSLASH;
import static org.irma.httpd.config.Constants.CARRIAGE_RETURN;
import static org.irma.httpd.config.Constants.DOUBLE_QUOTE;
import static org.irma.httpd.config.Constants.GREATER_THAN;
import static org.irma.httpd.config.Constants.LESS_THAN;
import static org.irma.httpd.config.Constants.LINE_FEED;
import static org.irma.httpd.config.Constants.NUMBER_SIGN;
import static org.irma.httpd.config.Constants.SINGLE_QUOTE;
import static org.irma.httpd.config.Constants.SLASH;
import static org.irma.httpd.config.TokenType.COMMENT;
import static org.irma.httpd.config.TokenType.END_OF_FILE;
import static org.irma.httpd.config.TokenType.KEYWORD;
import static org.irma.httpd.config.TokenType.NEWLINE;
import static org.irma.httpd.config.TokenType.SECTION_CLOSE;
import static org.irma.httpd.config.TokenType.SECTION_END_OPEN;
import static org.irma.httpd.config.TokenType.SECTION_START_OPEN;
import static org.irma.httpd.config.TokenType.STRING;

class DocumentTokenizer {
    
    
	private TextScanner scanner;
    public DocumentTokenizer(String document) {
        scanner = new TextScanner(document);
    }
    
    public Token nextToken() {
        while (!scanner.isEnd()) {
            char ch = scanner.getFirstNonSpaceCharacter();
            
            int start = scanner.getCurrentPosition() - 1;

            switch(ch) {
            case CARRIAGE_RETURN:
                break;
                
            case LINE_FEED:
                int end = scanner.getCurrentPosition();
                return new Token(NEWLINE, start, end);
            
            case NUMBER_SIGN:
                String line = scanner.nextLine();
                end = start + line.length() + 1;
                return new Token(COMMENT, start + 1, end);
                
            case SINGLE_QUOTE:
            case DOUBLE_QUOTE:
                start = scanner.getCurrentPosition();
                end = nextStringPosition(ch);
                return new Token(STRING, start, end);
                
            case LESS_THAN:
                char next = scanner.next();
                if (next == SLASH) {
                    scanner.forward();
                    end = scanner.getCurrentPosition();
                    return new Token(SECTION_END_OPEN, start, end);
                } else {
                    end = scanner.getCurrentPosition();
                    return new Token(SECTION_START_OPEN, start, end);
                }
                
            case GREATER_THAN:
                end = scanner.getCurrentPosition();
                return new Token(SECTION_CLOSE, start, end);
                
            default:
                if (ch != '\0') {
                    end = getNextTokenLocation();
                    return new Token(KEYWORD, start, end);
                }
            }
        }
        return new Token(END_OF_FILE, scanner.getCurrentPosition(), scanner.getCurrentPosition());
    }
    
    private int nextStringPosition(char quote) {
        while (!scanner.isEnd()) {
            char next = scanner.next();
            if (next == quote && scanner.previous() != BACKSLASH) {
                int end = scanner.getCurrentPosition();
                scanner.forward();
                return end;
            }
            scanner.forward();
        }
        // probably broken string, no end quote until the end of file
        return scanner.getCurrentPosition();
    }
    
    protected int getNextTokenLocation() {
        while (!scanner.isEnd()) {
            char next = scanner.next();
            if (Character.isWhitespace(next) || isTokenCharacter(next)) {
                return scanner.getCurrentPosition();
            }
            scanner.forward();
        }
        return scanner.getCurrentPosition();
    }
    
    protected boolean isTokenCharacter(char ch) {
        return ch == NUMBER_SIGN || ch == LESS_THAN || ch == GREATER_THAN 
                || ch == SINGLE_QUOTE || ch == DOUBLE_QUOTE;
    }
}
