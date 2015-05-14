package view;

import java.awt.image.BufferedImage;

import util.Point;
import util.Rectangle;

public class Widget {
	
	public Rectangle rec;
	public BufferedImage image;
	public BufferedImage click;
	public BufferedImage disable;
	
	public Widget(BufferedImage image, Point p){
		this.image = image;
		rec = new Rectangle(p, image.getWidth(), image.getHeight());
	}

}
