package org.irma.httpd.config;

import java.util.List;

public class Section extends Directive implements ElementContainer {
	private static final SectionWriter WRITER = new SectionWriter();
	
    private List<Element> elements;
    
    public Section(String name, List<String> arguments, List<Element> elements) {
    	super(name, arguments);
    	this.elements = elements;
    }

    @Override
    public List<Element> getElements() {
        return elements;
    }
    
    @Override
    public String toString() {
        return String.format("<%s>", getName());
    }
    
    @Override
    public SectionWriter geElementWriter() {
    	return WRITER;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Section other = (Section) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}
    
}
