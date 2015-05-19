import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Grid {
	
	private static boolean DEBUG = false;
	
	private ArrayList<ArrayList<Character>> gridArray = null;
	
	private char DEAD_CELL = '.';
	private char LIVE_CELL = 'O';
	
	/*
	 * The grid object exists as an ArrayList of ArrayList
	 * (If we know the dimensions will always be the same, we should change to be Array of Array)
	 */
	public Grid() {
		gridArray = new ArrayList<ArrayList<Character>>();  
	}
	
	/*
	 * Function to fill gridArray with values from file
	 */
	public void createFromFile(File file) throws TokenException {
		    
	    BufferedReader br = null;
	
		try {
			
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
			
			// For each line in the file
			while ((sCurrentLine = br.readLine()) != null) {
				
				// Fill as the row in the gridArray
				ArrayList<Character> row = new ArrayList<Character>();
				for (char ch:sCurrentLine.toCharArray()) {
					if (ch != DEAD_CELL && ch != LIVE_CELL) {
						// Throw bad token exception
						throw new TokenException("Bad token (" + ch + ") in file. Use '.' and 'O'");
					}
					row.add(ch);
				}
				
				if (DEBUG) {
					System.out.println(row);
				}

				// check we are adding same lengths of rows (or disproportionate dims)
				if (gridArray.size() > 0) {
					if (gridArray.get(0).size() == row.size()) {
						gridArray.add(row);
					}
					else {
						throw new TokenException("Grid is not in a standard rectangular form! Please keep all rows the same number of tokens."); 
					}
				} else {
					// First addition into gridArray is not checked
					gridArray.add(row);
				}			
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/*
	 * Function to print all contents of gridArray
	 */
	public void printGrid() {
		
		// Loop through each element of gridArray and print
		for (int vert_iter=0; vert_iter < getGridHeight(); vert_iter++) {
			for (char ch:gridArray.get(vert_iter)) {
				System.out.print(ch);
			}
			// Print a new line at end of each row
			System.out.print("\n");
		}
	}
	
	/*
	 * Perform a step forward in the Conway Game
	 */
	public void conwayStep() throws TokenException {
		
		// Create a copy to step forward without polluting original data
		ArrayList<ArrayList<Character>> gridArrayCopy = new ArrayList<ArrayList<Character>>();
		
		// For each row in the array
		for (int vert_iter=0; vert_iter < getGridHeight(); vert_iter++) {
			
			ArrayList<Character> row = new ArrayList<Character>();
			for (int horiz_iter=0; horiz_iter < getGridWidth(); horiz_iter++) {
				
				// Check if LIVE conditions are met (more than 3 "touching" cells are alive)
				if (check_neighbors(horiz_iter, vert_iter)) {
					row.add(LIVE_CELL);
				} else {
					row.add(DEAD_CELL);
				}
			}
			gridArrayCopy.add(row);
		}
		
		// Swap temporary copy with original gridArray
		gridArray.clear();
		gridArray = gridArrayCopy;
	}

	/*
	 * Check if 3 or mroe neighbors of passed coordinate are alive 
	 */
	public boolean check_neighbors(int x_coord, int y_coord) throws TokenException {

		int alive_neighbors = 0;
		
		// For each touching coordinate
		for (int vert_offset=-1;vert_offset<=1;vert_offset++) {
			for (int horiz_offset=-1;horiz_offset<=1;horiz_offset++) {
				int y_pos = y_coord + vert_offset;
				int x_pos = x_coord + horiz_offset;
				
				// check boundaries of grid
				if (y_pos >= 0 && y_pos < getGridHeight() && x_pos >= 0 && x_pos < getGridWidth()) {
					// don't check yourself
					if (!(y_pos == 0 && x_pos == 0)) {
						// count as alive neighbor
						if (getCell(x_pos, y_pos)) {
							alive_neighbors++;
						}
					}
				}
			}
		}
		
		// If more than 2 alive neighbors, return passed coordinate to be ALIVE YAY!
		if (alive_neighbors > 2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void clearGrid() {
		gridArray.clear();
	}
	
	/*
	 * Function to return coordinate's current status in the grid
	 */
	public boolean getCell(int x_coord, int y_coord) throws TokenException {
		if (gridArray.get(y_coord).get(x_coord) == 'O') {
			return true;
		}
		else if (gridArray.get(y_coord).get(x_coord) == '.') {
			return false;
		}
		else {
			throw new TokenException("Bad token in grid! How did this happen?!"); 
		}
	}
	
	/*
	 * Function to return the grid's width (row size)
	 */
	public int getGridWidth() {
		if (gridArray.size() > 0) {
			return gridArray.get(1).size();
		}
		else {
			return 0;
		}
	}
	
	/*
	 * Function to return the grid's height (number of rows)
	 */
	public int getGridHeight() {
		return gridArray.size();
	}
}
