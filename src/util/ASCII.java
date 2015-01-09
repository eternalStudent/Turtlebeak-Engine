package util;

public class ASCII {
	
	public int ch, cl, bg;
	
	public ASCII(int ch, int cl, int bg){
		this.ch = ch;
		this.cl = cl;
		this.bg = bg;
	}
	
	public ASCII(int ch, int cl){
		this(ch, cl, 0);
	}
	
	public void copy(ASCII other){
		ch = other.ch;
		cl = other.cl;
		bg = other.bg;
	}
	
	public ASCII bg(int bg){
		return new ASCII(this.ch, this.cl, bg);
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null)
			return false;
		if (!(obj instanceof ASCII))
			return false;
		ASCII other = (ASCII)obj;
		return (ch == other.ch && cl == other.cl && bg == other.bg);
	}
	
	@Override 
	public int hashCode(){
		return bg*(256*16)+cl*256+ch;
	}
	
	@Override
	public String toString(){
		return "("+ch+", "+cl+", "+bg+")";
	}

}
