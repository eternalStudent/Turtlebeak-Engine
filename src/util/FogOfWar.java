package util;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class FogOfWar extends Filter {

	@Override
	public BufferedImage produce(BufferedImage image) {
		for (int x=0; x<image.getWidth(); x++)
			for (int y=0; y<image.getHeight(); y++){
				Color cl = new Color(image.getRGB(x, y));
				Color newColor = new Color(cl.getRed()/2, cl.getGreen()/2, cl.getBlue()/2);
				image.setRGB(x, y, newColor.getRGB());
			}
		return image;
	}
	
	@Override
	public String toString(){
		return "fog of war";
	}

}
