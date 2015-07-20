package webreader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.net.*;
import java.awt.Color;
import java.io.*;

public class WebReader {
	
	public static void main(String[] args) throws Exception {
		WebReader wr = new WebReader();
		wr.print_map("http://www.reddit.com/");
	}
	
	// get tokens from a webpage
	public List<String> fetch() throws Exception {
		return fetch("https://en.wikipedia.org/wiki/Special:Random");
	}
	
	public List<String> fetch(String url) throws Exception {
		List<String> toReturn = new ArrayList<String>();
		URL read = new URL(url);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(read.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
        	String[] splits = inputLine.split("</.*>");
        	System.out.println("split into "+splits.length+" lines");
        	for(String s : splits) {
	            System.out.println(s);
	            toReturn.add(s.trim().toLowerCase());
        	}
        }
        in.close();
        return toReturn;
	}
	
	
	public Tile[][] mapify(List<String> input) {
		int size = (int) Math.floor(Math.sqrt(input.size()));
		Tile[][] map = new Tile[size][size];
		Iterator<String> it = input.iterator();
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				map[i][j] = intifiy(it.next());
			}
		}
		System.out.println("made an array");
		map = verify_paths(map);
		
		return map;
	}
	
	public Tile intifiy(String input) {
		/*
		 * < 10 = walkable
		 * 
		 * 5: Red - header/teleport
		 * 3: Yellow - key/bonus
		 * 4: Blue - link/door
		 * 9: Orange - Player
		 * Purple - Title
		 * 12: Green - Monster
		 * 
		 * 1: White - Win
		 * 0: Light Gray - Walkable
		 * 10: Dark Gray - Wall
		 * 11: Black - non-border wall (all 4 neighbors are impassible)
		 * 
		 * 
		 * 10 = wall
		 * 11 = wall
		 * 12 = monster
		 */
		// Doors
		if(input.substring(1, 2).equals("a")) {
			if(find_url(input).equals("https://en.wikipedia.org/wiki/Dungeon_crawl")) {
				return new Tile(1);
			}
			return new Tile(4, find_url(input));
		}
		// Keys
		if(input.contains("key")) {
			return new Tile(3);
		}
		// header teleport
		if(input.substring(1, 2).equals("h")) {
			return new Tile(5);
		}
		// if the line is a img -> monster
		if(!input.substring(1, 4).equals("img")) {
			return new Tile(12);
		}
		// if the line is a div -> wall
		if(!input.substring(1, 4).equals("div")) {
			return new Tile(10);
		}
		// if the line isn't started by a tag
		if(!input.substring(0, 1).equals("<")) {
			return new Tile(0);
		}
		return new Tile(10);
	}
	
	public Tile[][] verify_paths(Tile[][] initial_map) {
		System.out.println("verify paths");
		Tile[][] new_map = initial_map;
		
		Random r = new Random();
		
		for(int i = 0; i < new_map.length; i++) {
			for(int j = 0; j < new_map.length; j++) {
				if(new_map[i][j].v() == 5 || i == 0 && j == 0) {
					new_map = verify(new_map, i, j, r);
				}
			}
		}
		
		return new_map;
	}
	
	public Tile[][] verify(Tile[][] initial_map, int start_x, int start_y, Random r) {
		ArrayList<Pair> frontier = new ArrayList<Pair>();
		ArrayList<Pair> visited = new ArrayList<Pair>();
		ArrayList<Pair> blocked = new ArrayList<Pair>();
		
		frontier.add(new Pair(start_x, start_y));
		
		while(frontier.size() > 0) {
			Pair current = frontier.remove(r.nextInt(frontier.size()));
			Pair next;
			for(int i = 0; i < 4; i++) {
				if(current.x+(-2+i)/2 >= 0 && current.x+(-2+i)/2 < initial_map.length) {
					if(current.y+(-2+i)%2 >= 0 && current.y+(-2+i)%2 < initial_map.length) {
						// next is in bounds
						next = new Pair(current.x+(-2+i)/2, current.y+(-2+i)%2);
						if(!visited.contains(next) && !blocked.contains(next) && !frontier.contains(next)) {
							// then it hasn't been seen yet
							Tile t = initial_map[next.x][next.y];
							if(t.val >= 10) {
								blocked.add(next);
							} else if (t.val == 4) {
								// door
								return initial_map;
							} else {
								frontier.add(next);
							}
						}
					}
				}
			}
			visited.add(current);
			if(frontier.isEmpty() && !blocked.isEmpty()) {
				Pair toClear = blocked.remove(r.nextInt(blocked.size()));
				initial_map[toClear.x][toClear.y] = new Tile(0);
			}
		}
		System.out.println("No paths found.");
		// generate a clear map with just links
		// then return that
		return initial_map;
	}
	
	public String find_url(String input) {
		int start = input.indexOf("href=\"") + "href=\"".length();
		input = input.substring(start);
		int end = input.indexOf("\"");
		String short_form = input.substring(0, end);
		// TODO check to see if it is /wiki/... and ignore others that are not
		return "https://en.wikipedia.org"+short_form; // TODO
	}
	
	public void print_map(String url) throws Exception {
		Tile[][] map = mapify(fetch(url));
		for(int i = 0; i < map.length; i++) {
			System.out.print("\n[");
			for(int j = 0; j < map.length; j++) {
				String intermediate = ("  "+map[i][j].val);
				System.out.print(intermediate.substring(intermediate.length()-2)+"] [");
			}
			System.out.print("]");
		}
		System.out.print("\n");
	}
	
	public class Tile {
		int val;
		String url;
		Color c;
		
		/* 
		 * 0: Light Gray - Walkable 
		 * 1: White - Win
		 * 3: Yellow - key/bonus
		 * 4: Blue - link/door
		 * 5: Red - header/teleport
		 * 9: Orange - Player
		 * 10: Dark Gray - Wall
		 * 11: Black - non-border wall (all 4 neighbors are impassible)
		 * 12: Green - Monster
		 * Purple - Title
		 */
		
		public Tile(int value, String url) {
			this.val = value;
			if(val != 4) {
				this.url = "";
			} else {
				this.url = url;
			}
			if(url.equals("") && val == 4) {
				val = 0;
			}
			switch(this.val) {
				case 0: c = Color.LIGHT_GRAY;
            	break;
				case 1: c = Color.WHITE;
        		break;
				case 3: c = Color.YELLOW;
        		break;
				case 4: c = Color.BLUE;
        		break;
				case 5: c = Color.RED;
        		break;
				case 9: c = Color.ORANGE;
        		break;
				case 10: c = Color.DARK_GRAY;
        		break;
				case 11: c = Color.BLACK;
        		break;
				case 12: c = Color.GREEN;
        		break;
				default: c = Color.PINK;
				break;
			}
		}
		
		public Tile(int value) {
			this(value, "");
		}
		
		public int v() {
			return val;
		}
	}
	public class Pair {
		public int x, y;
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}

