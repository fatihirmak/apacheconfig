package org.irma.httpd.config;

import java.util.List;

public class Directive implements Element {
    private static final DirectiveWriter WRITER = new DirectiveWriter(); 
    private String name;
    private List<String> arguments;
    
    public Directive(String name, List<String> arguments) {
    	this.name = name;
        this.arguments = arguments;
    }
    
    public String getName() {
		return name;
	}
	
	public List<String> getArguments() {
		return arguments;
	}
	
	@Override
	public DirectiveWriter geElementWriter() {
	    return WRITER;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Directive other = (Directive) obj;
		if (arguments == null) {
			if (other.arguments != null)
				return false;
		} else if (!arguments.equals(other.arguments))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
