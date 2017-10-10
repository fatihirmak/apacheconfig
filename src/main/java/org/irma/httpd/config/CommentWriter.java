package org.irma.httpd.config;

import java.io.IOException;

class CommentWriter implements ElementWriter<Comment> {

    @Override
    public void write(Comment comment, TextWriter writer) throws IOException {
        writer.appendCurrentIndent()
        	  .append(Constants.NUMBER_SIGN)
              .append(comment.getContent());
        writer.newLine();
    }
}
