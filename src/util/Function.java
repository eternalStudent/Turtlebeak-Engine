package util;

public interface Function<A, R> extends Cloneable{
	
	public R eval(A argument);
	
}