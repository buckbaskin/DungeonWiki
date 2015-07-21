package gamer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Wiki Dungeon");
		JPanel example = new View(20);
		Controller c = new Controller(example);
		((View) example).controller(c);
		((View) example).setTitle(c.current_url);
		frame.add(example);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
