package org.irma.vipmanage.model;

import java.util.List;

public class Section extends Directive {
    private List<Directive> directives;

    public List<Directive> getDirectives() {
        return directives;
    }

    public void setDirectives(List<Directive> directives) {
        this.directives = directives;
    }

    public boolean addDirective(Directive directive) {
        return directives.add(directive);
    }
}
