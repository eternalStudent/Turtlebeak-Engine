package util;

public class Rectangle {
	
	public final int x0, y0, x1, y1, width, height;
	private final java.awt.Rectangle r;
	
	public Rectangle(Point p0, Point p1){
		x0 = Math.min(p0.x, p1.x);
		y0 = Math.min(p0.y, p1.y);
		x1 = Math.max(p0.x, p1.x);
		y1 = Math.max(p0.y, p1.y);
		width = x1-x0+1;
		height = y1-y0+1;
		r = new java.awt.Rectangle(x0, y0, width, height);
	}
	
	public Rectangle(Point p, int width, int height){
		x0 = p.x;
		y0 = p.y;
		x1 = x0+width-1;
		y1 = y0+height-1;
		this.width = width;
		this.height = height;
		r = new java.awt.Rectangle(x0, y0, width, height);
	}
	
	public int volume(){
		return width*height;
	}
	
	public boolean isIntersects(Rectangle other){
		return ( (x0>=other.x0 && x0<=other.x1) || (x1>=other.x0 && x1<=other.x1)
				|| (other.x0>=x0 && other.x0<=x1) || (other.x1>=x0 && other.x1<=x1) )
				&& ( (y0>=other.y0 && y0<=other.y1) || (y1>=other.y0 && y1<=other.y1)
				|| (other.y0>=y0 && other.y0<=y1) || (other.y1>=y0 && other.y1<=y1) );
	}
	
	public Rectangle intersection(Rectangle other){
		if (!isIntersects(other))
			return null;
		return new Rectangle(new Point(Math.max(x0, other.x0),Math.max(y0, other.y0)),
				new Point(Math.min(x1, other.x1),Math.min(y1, other.y1)));
	}
	
	public boolean includes(Point p){
		return p.x>=x0 && p.x<=x1 && p.y>=y0 && p.y<=y1;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null)
			return false;
		if (!(obj instanceof Rectangle))
			return false;
		Rectangle other = (Rectangle)obj;
		return x0 == other.x0 && y0 == other.y0 && width == other.width && height == other.height;
	}
	
	@Override
	public int hashCode(){
		return r.hashCode();
	}
	
}
