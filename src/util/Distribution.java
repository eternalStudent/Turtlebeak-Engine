package util;

public abstract class Distribution implements Cloneable{
	
	public abstract int produce(Range range);
	
	@Override
	public Distribution clone(){
		try {
			return (Distribution) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
