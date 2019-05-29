package maze_runner;

import java.util.Scanner;
import java.util.Random;

public class battle_ships {
	public static char[][] OceanMap = new char[10][10];
	public static char[][] ComputerOceanMap = new char[10][10]; 
    public static int player_ships = 5;
    public static int computer_ships = 5;
	
    public static void main(String[] args) {
    	// Initialization
    	System.out.print("**** Welcome to the Battle Ships game ****\n");
    	System.out.print("\nRight now, the sea is empty\n\n");
    	printMap(OceanMap);
    	
    	// Placing players ships
    	boolean placed = false;
    	for (int i = 0; i < player_ships; i++) {
    		while (!placed) {
    			
    			placed = place_ship(OceanMap);
    			
    			if (!placed) {
    				System.out.print("\nCan't put your ship there\n\n");
    			}
    		}
    		System.out.print("\nPlaced " + (i+1) + " ships, " + (player_ships-(i+1)) + " to go\n\n");
    		printMap(OceanMap);
    		placed = false;
    	}
    	
    	// Placing computers ships
    	System.out.print("\nComputer is deploying ships\n");
    	int to_place = computer_ships;
    		while (to_place > 0) {
    			//pick random X
    			Random rand = new Random();
    			int x = rand.nextInt(10);
    			
    			//pick random Y
    			int y = rand.nextInt(10);
    			
    			// check if placable
		        if (ComputerOceanMap[x][y] == 0)  {
		        	to_place = to_place - 1;
		        	ComputerOceanMap[x][y] = '@';
		        	System.out.print("Ship " + (computer_ships-to_place) + " deployed\n");
		        }
    			//printMap(ComputerOceanMap);
    		}
    		System.out.println("-------------------------");
    		
    		
    		// Start the fight!
    		int nb_turn = 1;
    		while (check_victory(player_ships, computer_ships).equals("continue")) {
    			
    			war_turn(OceanMap,ComputerOceanMap, nb_turn);
    			nb_turn = nb_turn + 1;
    		}
    		String message = check_victory(player_ships, computer_ships);
    		System.out.println(message + "in " + nb_turn + " turns");		
    	}
    
    // Fight logic (Changed it a bit to avoid that computer backfires himself)
    public static void war_turn(char[][] map, char[][] map2, int nb_turn) {
    	boolean done = false;
    	boolean already = false;
    	while (!done) {
    		already = false;
	    	Scanner input = new Scanner(System.in);
	    	System.out.print("\n   **** TURN " + nb_turn + " ****\n");
	    	System.out.print("PLAYER'S TURN\n");
	    	System.out.print("Enter X coordinate for firing: ");
	        int x = input.nextInt();
	        System.out.print("Enter Y coordinate for firing: ");
	        int y = input.nextInt();
	        
	        // if coordinates not good
	        if (y>=map.length && x>=map[0].length && x >= 0 && y >= 0) {
	        	done = true;
	        	System.out.print("Trying to shot outside of the sea ? Bad move, aim properly next time!\n");
	        } else {
	        		
	        
		        // if player shot already there
		        if (map[x][y] == '-') {
		        	done = false;
		        	already = true;
		        	System.out.print("You already shot there, please chose another target!\n");
		        }
		        
		        if (!done && !already) {
		  
		        	done = true;
		        	
		        	// Hit enemies ship
		        	if (map2[x][y] == '@') {
		        		map[x][y] = '!';
		        		System.out.print("Boom! You sunk the ship!\n");
		        		computer_ships = computer_ships - 1;
		        	} else if (map[x][y] == 0) {
		        	// Hit the sea
		        		map[x][y] = '-';
		        		System.out.print("Sorry, you missed\n");
		        	} else {
		        	// Hit his ship
		        	//if (map[x][y] == '@') {
		        		map[x][y] = 'x';
		        		System.out.print("Oh no, you sunk your own ship :(\n");
		        		player_ships = player_ships - 1;
		        	}
   	
		        }
        
        	}
	      
    	}
    	// Computer turn
    	System.out.print("\nCOMPUTER'S TURN\n");
    	boolean computer_done = false;
    	while (!computer_done) {
			//pick random X
			Random rand = new Random();
			int x = rand.nextInt(10);
			//pick random Y
			int y = rand.nextInt(10);
			System.out.print(x);
			System.out.print(y);
			// check if not one of our ships (no need to be dumb like in the exercise)
	        if (map2[x][y] == '@')  {
	        	computer_done = false;
	        }
	        
	        // check if not already shot 
	        if (map2[x][y] == '-')  {
	        	computer_done = false;
	        }
	        
	        // check if not already sunked 
	        if (map[x][y] == 'x')  {
	        	computer_done = false;
	        }
	        
	        if (!computer_done)  {
	        	// Did it hit the player ?
	        	if (map[x][y] == '@')  {
	        		map[x][y] = 'x';
	        		System.out.print("\nThe Computer sunk one of your ships!\n");
	        		player_ships = player_ships - 1;
	        		computer_done = true;
		        }
	        	// Did it missed
	        	if (map[x][y] == 0)  {
	        		map2[x][y] = '-';
	        		System.out.print("\nComputer missed\n");
	        		computer_done = true;
		        }
	        }
    	}
    	
	    System.out.print("\nYour ships : " + player_ships + " | Computer ships : " + computer_ships + "\n");
	    printMap(map);
	    printMap(map2);
    }
    
    // To check if victory and give back message
    public static String check_victory(int player, int computer) {	
    	if (player == 0) {
    		return "Computer won!";
    	}
    	if (computer == 0) {
    		return "You won!";
    	}
    	return "continue";
    }
	
    // Function to place the ship for user
    public static boolean place_ship(char[][] map) {
    	// ask coordinates
    	Scanner input = new Scanner(System.in);
        System.out.print("Enter X coordinate for your ship: ");
        int x = input.nextInt();
        System.out.print("Enter Y coordinate for your ship: ");
        int y = input.nextInt();
        // Check if filled
        char val = map[x][y];
        // if coordinates not good
        if (y>=map.length && x>=map[0].length && x >= 0 && y >= 0) {
        	return false;
        }
        // if not empty
        if (map[x][y] != 0)  {
        	return false;
        }
        // else assign it
        
        map[x][y] = '@';
        return true;
    }
	
	//Print the battleship
    public static void printMap(char[][] map) {
    	// Top axis
    	System.out.println("   0123456789   ");
    	
        for (int i = 0; i < map.length; i++) {
        	
        	//print vertical left bars 
            System.out.print(i + " |");
        	
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
            }
            // prints vertical right bars
            System.out.println("| " + i);
        }
        // prints bottom axis
        System.out.println("   0123456789   ");
        System.out.println();
        System.out.println("-------------------------");
    }
	
}
