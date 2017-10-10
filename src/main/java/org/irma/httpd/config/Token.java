package org.irma.httpd.config;

class Token {
    private TokenType type;
	private Region region;
	
    Token(TokenType type, int start, int end) {
        super();
        this.type = type;
        this.region = new Region(start, end);
    }
    public TokenType getType() {
        return type;
    }
    public void setType(TokenType type) {
        this.type = type;
    }
    
    public Region getRegion() {
        return region;
    }
    public void setRegion(Region region) {
        this.region = region;
    }
    @Override
    public String toString() {
        return String.format("%s[%d, %d]", type.name(), region.getStart(), region.getEnd());
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((region == null) ? 0 : region.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Token other = (Token) obj;
        if (region == null) {
            if (other.region != null)
                return false;
        } else if (!region.equals(other.region))
            return false;
        if (type != other.type)
            return false;
        return true;
    }


    
}
