package util;

import java.util.HashSet;
import java.util.Set;

public class BSPDungeon {
	
	private Node root;
	private Set<Point> walls = new HashSet<>();
	
	public BSPDungeon(int width, int height){
		for (int x=0; x<width; x++)
			for (int y=0; y<height; y++)
				walls.add(new Point(x, y));
		root = new Node(0, 0, width-1, height-1);
		root.splitNode();
		createRooms(root);
	}
	
	private void createRooms(Node node){
		if (node.room != null){
			for (int x=node.room.x0; x<=node.room.x1; x++)
				for (int y=node.room.y0; y<=node.room.y1; y++)
					walls.remove(new Point(x, y));
		}
		else{
			int x0 = Math.min(node.left.center.x, node.right.center.x);
			int x1 = Math.max(node.left.center.x, node.right.center.x);
			int y0 = Math.min(node.left.center.y, node.right.center.y);
			int y1 = Math.max(node.left.center.y, node.right.center.y);
			for (int x=x0; x<=x1; x++)
				for (int y=y0; y<=y1; y++)
					walls.remove(new Point(x, y));	
			createRooms(node.left);
			createRooms(node.right);
		}	
	}
	
	public class Node{
		public Node left;
		public Node right;
		public Node parent;
		public final int x0, y0, x1, y1, width, height;
		public final Point center;
		public Room room;
		
		public Node(int x, int y, int width, int height){
			this.x0 = x;
			this.y0 = y;
			this.x1 = x0+width;
			this.y1 = y0+height;
			this.width = width;
			this.height = height;
			center = new Point(x+width/2, y+height/2);
		}
		
		public void splitNode(){
			if (width<15 && height<15){
				room = new Room(Random.nextInt(x0+1, center.x-1), Random.nextInt(center.x+1, x1-1), 
						Random.nextInt(y0+1, center.y-1), Random.nextInt(center.y+1,y1-1));
				return;
			}	
			if (width>height){
				int buffer = Random.normal(x0+3, x1-3);
				left = new Node(x0, y0, buffer-x0, height);
				right = new Node(buffer, y0, x1-buffer, height);
			}
			else{
				int buffer = Random.normal(y0+3, y1-3);
				left = new Node(x0, y0, width, buffer-y0);
				right = new Node(x0, buffer, width, y1-buffer);
			}
			right.parent = this;
			left.parent = this;
			left.splitNode();
			right.splitNode();
		}
		
		public class Room{
			int x0, x1, y0, y1;
			public Room(int x0, int x1, int y0, int y1){
				this.x0 = x0;
				this.x1 = x1;
				this.y0 = y0;
				this.y1 = y1;
			}
		}		
	}
	
	public boolean contains(Point p){
		return walls.contains(p);
	}
	
}