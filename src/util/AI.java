package util;

import model.MOB;
import model.World;

public class AI implements Cloneable{
	
	protected World world;
	public MOB mob;
	
	public AI(World world){
		this.world = world;
	}
	
	public AI clone(){
		try {
			return (AI) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean act(){return true;}
}
