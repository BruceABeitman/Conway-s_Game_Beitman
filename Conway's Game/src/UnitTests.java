import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * Tests grid functionality
 * 1. Conway's game mechanics when performing a step to the default grid
 * 2. Conway's game mechanics when performing a step to the stepped grid
 * 3. Tests behavior when loading a grid with disproportional dimensions (uneven rows)
 * 4. Tests behavior when loading a grid with unsupported token types
 */
public class UnitTests {
	
	// NOTE Please alter file locations according to your filesystem!!
	private String default_step = "E:\\TestSuiteFiles\\DefaultGrid.txt";
	private String _1st_step = "E:\\TestSuiteFiles\\DefaultGrid_1step.txt";
	private String disp_dims = "E:\\TestSuiteFiles\\ExtraCharGrid.txt";
	private String bad_token = "E:\\TestSuiteFiles\\WrongCharGrid.txt";

	@Test
	public void test_default_step() {
		
		// Create the default grid, load from file
		Grid defaultGrid = new Grid(); 
		try {
			defaultGrid.createFromFile(new File(default_step));
		} catch (TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create the stepped grid, load from file
		Grid defaultSteppedGrid = new Grid(); 
		try {
			defaultSteppedGrid.createFromFile(new File(_1st_step));
		} catch (TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Have default grid step forward once
		try {
			defaultGrid.conwayStep();
		} catch (TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("Single stepping the default grid should yield the hard-coded stepped grid.", true, compareGrids(defaultSteppedGrid, defaultGrid));
	}
	
	@Test
	public void test_reverse_step() {
		
		// Create the default grid, load from file
		Grid defaultGrid = new Grid();
		try {
			defaultGrid.createFromFile(new File(default_step));
		} catch (TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create the stepped grid, load from file
		Grid defaultSteppedGrid = new Grid(); 
		try {
			defaultSteppedGrid.createFromFile(new File(_1st_step));
		} catch (TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Have stepped grid step forward once
		try {
			defaultSteppedGrid.conwayStep();
		} catch (TokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("Single stepping the hard-coded grid should yield the default grid.", true, compareGrids(defaultSteppedGrid, defaultGrid));
	}
	
	@Test
	public void test_disproportional_dims() {
		
		// Create the disproportional grid, load from file
		Grid defaultGrid = new Grid();
		String return_statement = "";
		try {
			defaultGrid.createFromFile(new File(disp_dims));
		} catch (TokenException e) {
			return_statement = e.toString();
		}
		
		// Verify that the TokenException was raised
		if (return_statement.equals("")) {
			assertEquals("Extra char valid NOT found.", false, true);
		} else {
			assertEquals(return_statement, true, true);
		}
	}
	
	@Test
	public void bad_token() {
		
		// Create the grid with the bad Token, load from file
		Grid defaultGrid = new Grid();
		String return_statement = "";
		try {
			defaultGrid.createFromFile(new File(bad_token));
		} catch (TokenException e) {
			return_statement = e.toString();
		}
		
		// Verify that the TokenException was raised
		if (return_statement.equals("")) {
			assertEquals("Wrong char valid NOT found.", false, true);
		} else {
			assertEquals(return_statement, true, true);
		}
	}
	
	/*
	 * Function to compare 2 grids, deeply (one element at a time)
	 * This function might be better moved within the Grid object?
	 */
	private boolean compareGrids(Grid A, Grid B) {
		
		// Check that both grids have non-zero dimensions, and dimensions match
		if (A.getGridHeight() > 0 && A.getGridWidth() > 0 && B.getGridHeight() > 0 && B.getGridWidth() > 0 && A.getGridHeight() == B.getGridHeight() && A.getGridWidth() == B.getGridWidth()) {
			// For each element in 2-D grid, compare it's value
			for (int vert_iter=0; vert_iter < A.getGridHeight(); vert_iter++) {
				for (int horiz_iter=0; horiz_iter < A.getGridWidth(); horiz_iter++) {
					try {
						// If any coordinate in grid is different, return False
						if (!(A.getCell(horiz_iter, vert_iter) == B.getCell(horiz_iter, vert_iter))){
							return false;
						}
					} catch (TokenException e) {
						return false;
					}
				}
			}
			// If all grid coordinates were checked, return True
			return true;
		}
		else {
			return false;
		}

	}

}
