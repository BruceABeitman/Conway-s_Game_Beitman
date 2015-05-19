import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Conway's Game
 * This class handles creating the console interface, and manipulates the game grid
 * corresponding to the user's input.
 */
public class Game {
	
	private static boolean DEBUG = false;
	private static Grid gameGrid = null;
	private static String input = "";

	/*
	 * Main function loops to receive user input
	 */
	public static void main(String[] args) {
		
		if (args.length == 0) {
			// Loop until the user exits the console
			while (!input.equals("exit")) {
				
				// Console print
				System.out.println("Conway's game>>>");
				
				// Try to read user input in, and split on whitespace
				try{
					
				    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				    input = bufferRead.readLine();
				    
				    String[] in_args = input.split("\\s+");
				    
				    // If multiple arguments passed, pass to File or Test command
				    // (Currently only f and t support arguments)
				    if (in_args.length > 1) {
				    	
		  		    	if (DEBUG) {
		  			    	System.out.println("Received command with argument.");
		  			    }
				    	
				    	// Check if command is either File or Test, if not print manual
				    	if (in_args[0].trim().toLowerCase().equals("f")) {
				    		
				    		fillGridFromFile(in_args[1].trim());
			    		} else if (in_args[0].trim().toLowerCase().equals("t")) {
				    		
				    		checkNeighbors(in_args[1].trim(), in_args[2].trim());
				    	} else {
				    		
				    		print_man();
				    	}
			    	// If only a command is passed, call appropriate functions (Print, Step, Clear, Manual) for command
			    	} else {
					    switch(input) {
					    // Print command
					    case "p":
					        try {
					        	gameGrid.printGrid();
					        }
					        catch (NullPointerException e) {
					        	System.out.println("No grid to print.");
					        }
					        break;
					    // Step command
					    case "s":
					    	try {
					    		gameGrid.conwayStep();
					    		gameGrid.printGrid();
					    	}
					        catch (NullPointerException e) {
					        	System.out.println("No grid to print.");
					        }
					        break;
					    // Clear command
					    case "c":
					    	try {
					    		gameGrid.clearGrid();
					    	}
					        catch (NullPointerException e) {
					        	System.out.println("No grid to print.");
					        }
					        break;
					     // Manual command
					    case "man":
					    	print_man();
					        break;
						// If none of the above, then print manual
					    default:
					        print_man();
					    }
		
			    	}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				} catch (TokenException e) {
					// TODO Auto-generated catch block
					if (DEBUG) {
						e.printStackTrace();
					} else {
						System.out.println(e);
					}
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					if (DEBUG) {
						e.printStackTrace();
					} else {
						System.out.println("Bad input, please try again.");
					}
					
				}
			}
		}
	}
	
	/*
	 * Passes the grid coordinate to 
	 */
	public static void checkNeighbors(String x_coord, String y_coord) {
		try {
        	boolean result = gameGrid.check_neighbors(Integer.parseInt(x_coord), Integer.parseInt(y_coord));
        	if (result) {
	        	System.out.println("Next step coordinate will be: ALIVE");
        	} else {
        		System.out.println("Next step coordinate will be: DEAD");
        	}

        }
        catch (NullPointerException e) {
        	if (DEBUG) {
				e.printStackTrace();
			} else {
	        	System.out.println("No grid to print.");
			}
        }
	    catch (TokenException e) {
	    	if (DEBUG) {
				e.printStackTrace();
			} else {
				System.out.println(e);
			}
	    }
	}
	
	public static void fillGridFromFile(String filename) {
		BufferedReader br = null;
		
		if (DEBUG) {
	    	System.out.println("Reading file: " + filename);
	    }
		
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(filename));
			
			if (DEBUG) 
				while ((sCurrentLine = br.readLine()) != null) {
				{
					System.out.println(sCurrentLine);
				}
			}
			
			gameGrid = new Grid(); 
			gameGrid.createFromFile(new File(filename));
 
		} catch (IOException d) {
			System.out.println("No file found: " + filename);
		} catch (TokenException e) {
			if (DEBUG) {
				e.printStackTrace();
			} else {
				System.out.println(e);
			}
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/*
	 * Funciton to print the manual for the Conway's game console.
	 */
	private static void print_man() {
		System.out.println("Commands: \n\t p - print grid \n\t s - step conway \n\t f <file> - load file");
	}
}
