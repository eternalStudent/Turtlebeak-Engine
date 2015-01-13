package util;

public class Range {
	
	public final int a, b;
	
	public Range(int a, int b){
		this.a = Math.min(a, b);
		this.b = Math.max(a, b);
	}
	
	public boolean inRange(int i){
		return (i>=a && i<b);
	}
	
	public boolean inCloseRange(int i){
		return (i>=a && i<=b);
	}
	
	public boolean inOpenRange(int i){
		return (i>a && i>b);
	}
	
	@Override
	public String toString(){
		return "["+a+", "+b+")";
	}

}
