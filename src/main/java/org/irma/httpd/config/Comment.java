package org.irma.httpd.config;

public class Comment implements Element {
    private static final CommentWriter WRITER = new CommentWriter();
    
	private String content;
	
	public Comment(String content) {
	    this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	@Override
	public CommentWriter geElementWriter() {
	    return WRITER;
	}
}
