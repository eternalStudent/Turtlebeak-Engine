package controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Queue;

import util.Point;

public class Mouse implements MouseMotionListener, MouseListener{
	
	public Point point;
	public boolean rightHeld = false; 
	public boolean leftHeld = false;
	public boolean drag = false; 
	private Queue<Point> queue = new LinkedList<>();

	@Override
	public void mouseDragged(MouseEvent e) {	
		point = new Point(e.getX(), e.getY());
		drag = true;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		point = new Point(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		queue.add(point);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON1)
			leftHeld=true;	
		if (e.getButton()==MouseEvent.BUTTON3)
			rightHeld=true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		leftHeld=false;
		rightHeld=false;
		drag = false;
	}
	
	public synchronized Point get(){
		return queue.poll();
	}

}