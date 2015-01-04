package util;
public interface Lambda<A, R> {	
	public R produce(A argument);
}