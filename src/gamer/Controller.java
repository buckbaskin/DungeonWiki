package gamer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import webreader.WebReader;

public class Controller {
	
	WebReader wr;
	WebReader.Tile[][] map;
	
	int u_x, u_y;
	
	KeyListener listener;
	
	JPanel gameView;
	
	public Controller(JPanel gameView) {
		this.gameView = gameView;
		wr = new WebReader();
		try {
			map = wr.build_map("https://en.wikipedia.org/wiki/Special:Random", "https://en.wikipedia.org/wiki/Special:Random");
		} catch (Exception e) {
			System.out.println("Error building game. Exiting...");
			System.exit(0);
		}
		u_x = 0;
		u_y = 0;
		
		listener = new GameKeyListener();
		this.gameView.addKeyListener(listener);
		this.gameView.setFocusable(true);
	}
	
	public class GameKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP ) {
				System.out.println("UP ARROW!");
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN ) {
				System.out.println("DOWN ARROW!");
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT ) {
				System.out.println("LEFT ARROW!");
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT ) {
				System.out.println("RIGHT ARROW!");
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Mini Tennis");
		JPanel example = new JPanel();
		Controller c = new Controller(example);
		frame.add(example);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
