package util;

import model.MOB;
import model.World;

public abstract class AI implements Cloneable{
	
	protected World world;
	public MOB mob;
	
	public AI(World world){
		this.world = world;
	}
	
	public abstract boolean act();
	
	@Override
	public AI clone(){
		try {
			return (AI) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
