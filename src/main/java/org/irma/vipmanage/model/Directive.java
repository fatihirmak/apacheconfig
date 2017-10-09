package org.irma.vipmanage.model;

import java.util.List;

public class Directive {
    private String name;
    private List<String> arguments;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getArguments() {
        return arguments;
    }
    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }
}
