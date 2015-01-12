package util;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import util.Lambda;
import util.Point;

public class Tileset {

	private BufferedImage image = null;
	public int cols, tw, th;
	public final String path;
	public Lambda<Point, Point> toScreen;
	public boolean ASCII = false;
	private int[] palette = {-16777216, -16777088, -16744448, -16744320, -8388608, -8388480, -8355840, -4144960, -8355712, -16776961, -16711936, -16711681, -65536, -65281, -256, -1};
	private Map<ASCII, BufferedImage> ASCIIdict = new HashMap<>();
	private Map<Tile, BufferedImage> TileDict = new HashMap<>();
	private BufferedImage[] subImage;
	
	public Tileset(String pathname, int tw, int th, Lambda<Point, Point> toScreen){
		try { image = ImageIO.read(new File(pathname)); } 
		catch (IOException e) { e.printStackTrace(); }	
		path=pathname;
		this.tw=tw;
		this.th=th;
		cols = image.getWidth()/tw;
		subImage = new BufferedImage[cols*image.getHeight()/th];
		for (int i=0; i<subImage.length; i++)
			subImage[i] = image.getSubimage((i%cols)*tw, (i/cols)*th, tw, th);
		this.toScreen = toScreen;
	}
	
	public Tileset(String pathname, int tw, int th,Lambda<Point, Point> toScreen, int[] palette){
		this(pathname, tw, th, toScreen);
		this.palette = palette;
	}
	
	public void setTransparentBackground(int rgb){
		for (int x=0; x<image.getWidth(); x++)
			for (int y=0; y<image.getHeight(); y++)
				if (image.getRGB(x,  y) == rgb)
					image.setRGB(x, y, 0);
	}
	
	private BufferedImage duplicate(BufferedImage image) {
		BufferedImage j = new BufferedImage(tw, th, BufferedImage.TYPE_INT_ARGB);
		for (int x=0; x<tw; x++)
			for (int y=0; y<th; y++)
				j.setRGB(x,y,image.getRGB(x,y));
		return j;
	}
	
	public BufferedImage getTileImage(int ch, int cl, int bg){
		BufferedImage img = duplicate(getSubImage(ch));
		for (int x=0; x<tw; x++)
			for(int y=0; y<th; y++)
				if (img.getRGB(x, y)!=palette[0])
					img.setRGB(x, y, palette[cl]);
				else if (bg!=palette[0])
					img.setRGB(x, y, palette[bg]);
		return img;
	}
	
	public BufferedImage getTileImage(int i, List<Filter> filters){
		BufferedImage img = duplicate(getSubImage(i));
		for (Filter filter: filters){
			filter.produce(img);
		}
		return img;
	}
	
	public BufferedImage getTileImage(ASCII ascii){
		if (ascii == null)
			return getTileImage(0, 0, 0);
		if (!ASCIIdict.containsKey(ascii))
			ASCIIdict.put(ascii, getTileImage(ascii.ch, ascii.cl,ascii.bg));
		return ASCIIdict.get(ascii);
	}
	
	public BufferedImage getTileImage(Tile tile){
		if (!TileDict.containsKey(tile))
			TileDict.put(tile, getTileImage(tile.number, tile.filters));
		return TileDict.get(tile);
	}
	
	public BufferedImage getSubImage(int i){
		return subImage[i];
	}
	
	public Color color(int i){
		return new Color(palette[i]);
	}
	
	public String toString(){
		return path+" "+tw+" "+th;
	}
	
}
