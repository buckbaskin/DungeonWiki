package webreader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.net.*;
import java.io.*;

public class WebReader {
	
	// get tokens from a webpage
	public List<String> fetch(String url) throws Exception {
		List<String> toReturn = new ArrayList<String>();
		URL read = new URL(url);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(read.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            toReturn.add(inputLine.trim().toLowerCase());
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
		
		map = verify_paths(map);
		
		return map;
	}
	
	public Tile intifiy(String input) {
		/*
		 * < 10 = walkable. 0 = start, 1 = path, 2 = bonus, 3 = link
		 */
		// Doors
		if(input.substring(1, 2).equals("a")) {
			return new Tile(3, find_url(input));
		}
		// Keys
		if(input.contains("key")) {
			return new Tile(2);
		}
		if(!input.substring(0, 1).equals("<")) {
			return new Tile(0);
		}
		if(!input.substring(1, 4).equals("div")) {
			return new Tile(10);
		}
		return new Tile(11);
	}
	
	public Tile[][] verify_paths(Tile[][] initial_map) {
		boolean[][] clear = new boolean[initial_map.length][initial_map.length];
		Tile[][] new_map = initial_map;
		for(int i = 0; i < new_map.length; i++) {
			for(int j = 0; j < new_map.length; j++) {
				if(new_map[i][j].v() < 10) {
					clear[i][j] = true;
				}
			}
		}
		
		for(int i = 0; i < new_map.length; i++) {
			for(int j = 0; j < new_map.length; j++) {
				if(new_map[i][j].v() < 10) {
					clear[i][j] = true;
				}
			}
		}
		
		return new_map;
	}
	
	public String find_url(String input) {
		return input; // TODO
	}
	
	public class Tile {
		int val;
		String url;
		
		public Tile(int value, String url) {
			this.val = value;
			if(val != 3) {
				this.url = "";
			} else {
				this.url = url;
			}
		}
		
		public Tile(int value) {
			this(value, "");
		}
		
		public int v() {
			return val;
		}
	}
}

