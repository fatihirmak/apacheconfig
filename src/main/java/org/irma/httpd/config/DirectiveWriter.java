package org.irma.httpd.config;

import java.io.IOException;

class DirectiveWriter implements ElementWriter<Directive> {


    @Override
    public void write(Directive directive, TextWriter writer) throws IOException {
    	writer.appendCurrentIndent();
        writer.write(directive.getName());
        writer.write(writer.getFormatOptions().getArgumentSeparatorPolicy().getCharacter());
        writer.write(directive.getArguments().toArray(new String[directive.getArguments().size()]));
        writer.newLine();
    }

}
