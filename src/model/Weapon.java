package model;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Distribution;
import util.Parser;
import util.Random;
import util.Range;

import com.google.common.io.Resources;

public class Weapon implements Cloneable{
	
	public static Map<String, Weapon> dict = new HashMap<>();
	
	public String name, type;
	public Range damage;
	public int criticalChance, range, timeCost, reloadCost, criticalFactor;
	public Distribution damageDistribution = new Distribution(){
		public int produce(Range damage){
			return Random.normal(damage.a, damage.b);
		}
	};
	
	public Weapon(String name){
		this.name = name;
	}
	
	public static void fromText(String text){
		Pattern pattern = Pattern.compile("\\{([^//}])*\\}");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()){
			String elem = matcher.group();
			Weapon w = new Weapon("");
			w.name = Parser.read(elem, "name", "");
			w.type = Parser.read(elem, "type", "");
			dict.put(w.name, w);
		}
	}
	
	public static Weapon fromDict(String name){
		return dict.get(name).clone();
	}
	
	public static void fromResource(URL url) throws IOException{
		fromText(Resources.toString(url, StandardCharsets.UTF_8));
	}
	
	public int damage(){
		return damageDistribution.produce(damage);
	}
	
	public int criticalDamage(){
		return (criticalFactor*damage())/120;
	}
	
	public boolean isCritical(){
		return Random.nextInt(120)<criticalChance;
	}
	
	@Override
	public Weapon clone(){
		Weapon w = new Weapon(name);
		try {
			w = (Weapon) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		w.damage = new Range(damage.a, damage.b);
		w.damageDistribution = damageDistribution.clone();
		return w;
	}

}