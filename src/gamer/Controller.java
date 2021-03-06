package gamer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;

import webreader.WebReader;
import webreader.WebReader.Tile;

public class Controller {
	
	WebReader wr;
	Tile[][] map;
	
	int u_x, u_y;
	
	KeyListener listener;
	
	JPanel gameView;
	
	String current_url, last_url;
	
	File f;
	
	public Controller(JPanel gameView) {
		this.gameView = gameView;
		wr = new WebReader();
		//System.out.println(System.getProperty("user.dir"));
		f = new File(".\\src\\save_game");
		current_url = "https://en.wikipedia.org/wiki/Special:Random";
		last_url = "https://en.wikipedia.org/wiki/Special:Random";
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			current_url = br.readLine();
		//	System.out.println("read url "+current_url+" from file "+f.getAbsolutePath());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			map = wr.build_map(current_url, last_url);
			// wr.print_map(map);
		} catch (Exception e) {
			System.out.println("Error building game. Exiting...");
			System.exit(0);
		}
		u_x = map.length/2;
		u_y = map.length/2;
		
		listener = new GameKeyListener(this);
		this.gameView.addKeyListener(listener);
		this.gameView.setFocusable(true);
		
		f = new File("./save_game");
	}
	
	public void left() {
		// System.out.println("left");
		if(u_x > 0) {
			u_x -= 1;
			Tile t = map[u_x][u_y];
			switch(t.v()) {
			case 1: //win
				System.out.println("You Won!!! You found the magical wikipedia page.");
				System.exit(0);
			case 3: //bonus
				System.out.println("You found a key.");
				break;
			case 4: // door/link
				System.out.println("You found a door to "+t.u());
				door(t);
				break;
			case 5: // teleport
				System.out.println("You found a teleport...");
				break;
			case 10: // wall
				System.out.println("You bumped into a wall...");
				u_x += 1;
				break;
			case 11: // dark wall
				System.out.println("You bumped into an unreachable wall... You go glen cocoa");
				u_x += 1;
				break;
			case 12: //monster
				System.out.println("You fought a monster and lost...");
				System.exit(0);
			default: //default, nothing
				break;
			}
			
		} else {
			// Take no action because on left border
		}
		// System.out.println("(x,y) ("+u_x+","+u_y+")");
	}
	public void right() {
		// System.out.println("right");
		if(u_x < map.length-1) {
			u_x += 1;
			Tile t = map[u_x][u_y];
			switch(t.v()) {
			case 1: //win
				System.out.println("You Won!!! You found the magical wikipedia page.");
				System.exit(0);
			case 3: //bonus
				System.out.println("You found a key.");
				break;
			case 4: // door/link
				System.out.println("You found a door to "+t.u());
				door(t);
				break;
			case 5: // teleport
				System.out.println("You found a teleport...");
				break;
			case 10: // wall
				System.out.println("You bumped into a wall...");
				u_x -= 1;
				break;
			case 11: // dark wall
				System.out.println("You bumped into an unreachable wall... You go glen cocoa");
				u_x -= 1;
				break;
			case 12: //monster
				System.out.println("You fought a monster and lost...");
				System.exit(0);
			default: //default, nothing
				break;
			}
			
		} else {
			// Take no action because on left border
		}
		// System.out.println("(x,y) ("+u_x+","+u_y+")");
	}
	public void up() {
		// System.out.println("up");
		if(u_y > 0) {
			u_y -= 1;
			Tile t = map[u_x][u_y];
			switch(t.v()) {
			case 1: //win
				System.out.println("You Won!!! You found the magical wikipedia page.");
				System.exit(0);
			case 3: //bonus
				System.out.println("You found a key.");
				break;
			case 4: // door/link
				System.out.println("You found a door to "+t.u());
				door(t);
				break;
			case 5: // teleport
				System.out.println("You found a teleport...");
				break;
			case 10: // wall
				System.out.println("You bumped into a wall...");
				u_y += 1;
				break;
			case 11: // dark wall
				System.out.println("You bumped into an unreachable wall... You go glen cocoa");
				u_y += 1;
				break;
			case 12: //monster
				System.out.println("You fought a monster and lost...");
				System.exit(0);
			default: //default, nothing
				break;
			}
			
		} else {
			// Take no action because on left border
		}
		// System.out.println("(x,y) ("+u_x+","+u_y+")");
	}
	public void down() {
		// System.out.println("down");
		if(u_y < map.length-1) {
			u_y += 1;
			Tile t = map[u_x][u_y];
			switch(t.v()) {
			case 1: //win
				System.out.println("You Won!!! You found the magical wikipedia page.");
				System.exit(0);
			case 3: //bonus
				System.out.println("You found a key.");
				break;
			case 4: // door/link
				System.out.println("You found a door to "+t.u());
				door(t);
				break;
			case 5: // teleport
				System.out.println("You found a teleport...");
				break;
			case 10: // wall
				System.out.println("You bumped into a wall...");
				u_y -= 1;
				break;
			case 11: // dark wall
				System.out.println("You bumped into an unreachable wall... You go glen cocoa");
				u_y -= 1;
				break;
			case 12: //monster
				System.out.println("You fought a monster and lost...");
				System.exit(0);
			default: //default, nothing
				break;
			}
			
		} else {
			// Take no action because on left border
		}
		// System.out.println("(x,y) ("+u_x+","+u_y+")");
	}
	
	public void door(Tile t) {
		String url = t.u();
		last_url = current_url;
		current_url = url;
		try {
			map = wr.build_map(current_url, last_url);
			u_x = map.length/2;
			u_y = map.length/2;
		} catch (Exception e) {
			System.out.println(" Error building new map. Please try again later");
			System.exit(0);
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(".\\src\\save_game"));
			writer.write(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			writer = new BufferedWriter(new FileWriter(".\\src\\visit_list", true));
			writer.write(url+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		((View) gameView).setTitle(url);
	}
	
	public class GameKeyListener implements KeyListener {
		
		Controller c;
		public GameKeyListener(Controller c) {
			this.c = c;
		}
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				// System.out.println("UP ARROW! or W!");
				c.up();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				// System.out.println("DOWN ARROW! or S!");
				c.down();
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				// System.out.println("LEFT ARROW! or A!");
				c.left();
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				// System.out.println("RIGHT ARROW! or D!");
				c.right();
			}
			c.gameView.repaint();
		}
	}

}
