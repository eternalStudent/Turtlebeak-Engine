package util;

public interface Lambda<A, R> extends Cloneable{
	
	public R produce(A argument);
	
}