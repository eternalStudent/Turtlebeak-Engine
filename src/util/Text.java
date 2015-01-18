package util;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.common.io.Resources;

public class Text {
	
	private List<String> list = new ArrayList<>();
	public int currentLine = 0;
	
	public Text(URL url){
		String text;
		try {
			text = Resources.toString(url,  StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scanner sc = new Scanner(text);
		while (sc.hasNextLine()){
			list.add(sc.nextLine());
		}
		sc.close();
	}
	
	public String getLine(int i){
		if (i<0 || i>=list.size())
			return "";
		return list.get(i);
	}
	
}
