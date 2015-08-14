package util;

import java.awt.image.BufferedImage;

public abstract class Filter implements Function<BufferedImage, BufferedImage>{
	
	@Override
	public boolean equals(Object obj){
		if (obj == null)
			return false;
		if (!(obj instanceof Filter))
			return false;
		Filter other = (Filter)obj;
		return toString().equals(other.toString());
	}
	
	@Override
	public int hashCode(){
		return toString().hashCode();
	}
	
	@Override
	public abstract String toString();

}
