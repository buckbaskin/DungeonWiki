package gamer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import webreader.WebReader.Tile;

public class View extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7854117709890594584L;
	
	Controller c;
	
	int size;
	
	public View(int size) {
		this.size = size;
	}
	
	public void controller(Controller c) {
		this.c = c;
	}
	public Controller controller() {
		return this.c;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int height = (int) this.getSize().getHeight();
		int width = (int) this.getSize().getWidth();
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, width, height);
		paint_map(g2);
		paint_user(g2);
	}
	
	public void paint_map(Graphics2D g) {
		int height = (int) this.getSize().getHeight();
		int width = (int) this.getSize().getWidth();
		int x1 = width/2 - size/2;
		int y1 = height/2 - size/2;
		
		
		int start_x = x1-controller().u_x*size;
		int start_y = y1-controller().u_y*size;
		Tile[][] map = controller().map;
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map.length; y++) {
				g.setColor(map[x][y].c);
				g.fillRect(start_x+x*size, start_y+y*size, size, size);
			}
		}
	}
	public void paint_user(Graphics2D g){
		int height = (int) this.getSize().getHeight();
		int width = (int) this.getSize().getWidth();
		int x = width/2 - size/2;
		int y = height/2 - size/2;
		g.setColor(Color.ORANGE);
		g.fillOval(x+5, y+5, size-10, size-10);
	}
}
