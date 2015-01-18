package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import controller.Keyboard;

@SuppressWarnings("serial")
public class Board extends JFrame {
	
	public Board(Keyboard keyboard, String title){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);
		setVisible(true);
		setResizable(false);		
		addKeyListener(keyboard);
	}
	
	public void addToPane(Screen screen, String layout){
		getContentPane().add(screen, layout);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(new java.awt.Point((screenSize.width-this.getWidth())/3, (screenSize.height-this.getHeight())/3));
	}
	
	public void addToPane(Screen screen){
		addToPane(screen, BorderLayout.CENTER);
	}

}