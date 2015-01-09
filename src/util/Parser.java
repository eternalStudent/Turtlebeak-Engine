package util;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	
	public static String read(String text, String field, String initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: \"[^\"]*\"");
		Matcher m = p.matcher(text);			
		if (m.find())try{
			Object[] values = new MessageFormat("["+field+"]: \"{0}\"").parse(m.group());
			return values[0].toString();
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	public static char read(String text, String field, char initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: '[^']'");
		Matcher m = p.matcher(text);			
		if (m.find())try{
			Object[] values = new MessageFormat("["+field+"]: {0}").parse(m.group());
			return values[0].toString().charAt(1);
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	public static int read(String text, String field, int initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: -?\\d+");
		Matcher m = p.matcher(text);			
		if (m.find()) try{
			Object[] values = new MessageFormat("["+field+"]: {0, number, integer}").parse(m.group());
			return Integer.parseInt(values[0].toString());
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	public static double read(String text, String field, double initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: -?\\d+.?\\d+");
		Matcher m = p.matcher(text);			
		if (m.find()) try{
			Object[] values = new MessageFormat("["+field+"]: {0}").parse(m.group());
			return Double.parseDouble(values[0].toString());
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	public static boolean read(String text, String field, boolean initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: ((false)|(true))");
		Matcher m = p.matcher(text);			
		if (m.find()) try{
			Object[] values = new MessageFormat("["+field+"]: {0}").parse(m.group());
			return Boolean.parseBoolean(values[0].toString());
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	public static List<String> readList(String text){
		List<String> list = new ArrayList<>(); 
		Pattern p = Pattern.compile("\"[^\"]*\",? *");
		Matcher m = p.matcher(text);
		while (m.find())
			list.add(read("[]: "+m.group(), "", ""));
		return list;
	}
	
	public static List<String> readList(String text, String field, List<String> initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: \\[(\"[^\"]*\",? *)*\\]");
		Matcher m = p.matcher(text);			
		if (m.find()) try{
			Object[] values = new MessageFormat("["+field+"]: {0}").parse(m.group());
			return readList(values[0].toString());
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	public static List<Integer> readIntList(String text){
		List<Integer> list = new ArrayList<>(); 
		Pattern p = Pattern.compile("-?\\d+,? *,? *");
		Matcher m = p.matcher(text);
		while (m.find())
			list.add(read("[]: "+m.group(), "", 0));
		return list;
	}
	
	public static List<Integer> readIntList(String text, String field, List<Integer> initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: \\[(-?\\d+,? *)*\\]");
		Matcher m = p.matcher(text);			
		if (m.find()) try{
			Object[] values = new MessageFormat("["+field+"]: {0}").parse(m.group());
			return readIntList(values[0].toString());
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	private static int ObjToInt(Object obj){
		return Integer.parseInt(obj.toString());
	}
	
	public static ASCII read(String text, String field, ASCII initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: \\(\\d+, \\d+, \\d+\\)");
		Matcher m = p.matcher(text);
		if (m.find()) try{
			String pattern = "["+field+"]: ({0,number,integer}, {1,number,integer}, {2,number,integer})";
			Object[] values = new MessageFormat(pattern).parse(m.group());
			return new ASCII(ObjToInt(values[0]), ObjToInt(values[1]), ObjToInt(values[2]));
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	public static List<ASCII> readASCIIList(String text){
		List<ASCII> list = new ArrayList<>(); 
		Pattern p = Pattern.compile("\\(\\d+, \\d+, \\d+\\)");
		Matcher m = p.matcher(text);
		while (m.find())
			list.add(read("[]: "+m.group(), "", new ASCII(0, 0, 0)));
		return list;
	}
	
	public static List<ASCII> readASCIIList(String text, String field, List<ASCII> initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: \\[(\\(\\d+, \\d+, \\d+\\),? *)*\\]");
		Matcher m = p.matcher(text);			
		if (m.find()) try{
			Object[] values = new MessageFormat("["+field+"]: {0}").parse(m.group());
			return readASCIIList(values[0].toString());
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	public static Point read(String text, String field, Point initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: \\(-?\\d+, -?\\d+\\)");
		Matcher m = p.matcher(text);
		if (m.find()) try{
			String pattern = "["+field+"]: ({0,number,integer}, {1,number,integer})";
			Object[] values = new MessageFormat(pattern).parse(m.group());
			return new Point(ObjToInt(values[0]), ObjToInt(values[1]));
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
	public static List<Point> readPointList(String text){
		List<Point> list = new ArrayList<>(); 
		Pattern p = Pattern.compile("\\(-?\\d+, -?\\d+\\)");
		Matcher m = p.matcher(text);
		while (m.find())
			list.add(read("[]: "+m.group(), "", new Point(0, 0)));
		return list;
	}
	
	public static List<Point> readPointList(String text, String field, List<Point> initialValue){
		Pattern p = Pattern.compile("\\["+field+"\\]: \\[(\\(-?\\d+, -?\\d+\\),? *)*\\]");
		Matcher m = p.matcher(text);			
		if (m.find()) try{
			Object[] values = new MessageFormat("["+field+"]: {0}").parse(m.group());
			return readPointList(values[0].toString());
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		return initialValue;
	}
	
//	public static void main(String[] args){
//		System.out.println(read("[bloop]: (1, 2, 3)", "bloop", new ASCII(32, 1, 6)));
//	}

}
