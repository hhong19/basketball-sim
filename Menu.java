/*CSE 205: Class #11333 / Tuesday Thursday 4:30
 *Assignment: 6
 *Author(s): Harrison Hong / 1217745542
 *Description: Driver for the program. Displays all information 
 *to the user and receives inputs. Prevents the user from
 *entering unpermitted inputs to the program. Calls the other classes
 *to fulfill responsibilities.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
	private static Scanner scan = new Scanner(System.in);
	//stores all Teams in one ArrayList
	private static ArrayList<Team> league = new ArrayList<Team>();
	//stores all Players into one ArrayList for easy access when ranking
	private static ArrayList<Player> all_players = new ArrayList<Player>();
	
	public static void main(String[] args) {
		int menuChoice = 0;
		
		while(menuChoice != 9) {
			displayMainMenu();
			menuChoice = getMenuChoice();
			
			switch(menuChoice) {
				case 1:
					createTeam();
					break;
				case 2:
					createPlayer();
					break;
				case 3:
					removeFromTeam();
					break;
				case 4:
					tradePlayer();
					break;
				case 5:
					adjustStat();
					break;
				case 6:
					viewRoster();
					break;
				case 7:
					playGame();
					break;
				case 8:
					rankPlayers();
					break;
				case 9:
					quit();
					break;
				default:
					handleMenuError();
					break;
			}
		}
	}
	
	//prints out the main menu
	private static void displayMainMenu() {
		System.out.println("--------------------------------------");
		System.out.println("Basketball Simulator 2020");
		System.out.println("- Start by creating a team and adding players.");
		System.out.println("- You need at least 2 teams to play a game or trade.\n");
		System.out.println("1) Create a new team");
		System.out.println("2) Add a player");
		System.out.println("3) Remove a player");
		System.out.println("4) Trade a player");
		System.out.println("5) Adjust player stats");
		System.out.println("6) View team roster");
		System.out.println("7) Play a game");
		System.out.println("8) Rank all players");
		System.out.println("9) Quit");
		System.out.println("Enter a menu option:");
	}
	
	//receives the user menu choice and returns it
	private static int getMenuChoice() {
		System.out.print("---> ");
		while(! scan.hasNextInt() )
		{
			scan.nextLine();
			System.out.print("---> ");
		}
		
		int menuChoice = scan.nextInt();
		scan.nextLine();
		return menuChoice;
	}
	
	//creates a new Team with a name specified by the user
	private static void createTeam() {
		System.out.println("Enter a new team name (0 to go back): ");
		String name = scan.nextLine();
		if(name.equals("0")) {return;}
		
		Team myTeam = new Team(name);
		league.add(myTeam);
		System.out.println("Congrats! The team (" + name + ") has been created!");
	}
	
	//creates a new Player using user inputs
	private static void createPlayer() {
		if(league.size() == 0) {
			System.out.println("*Denied* You need to create a team before adding players");
			return;
		}
		
		System.out.println("Enter the player's name (0 to go back): ");
		String player_name = scan.nextLine();
		if(player_name.equals("0")) {return;}
		
		int off_rating = scanOffRating();
		int def_rating = scanDefRating();
		
		Player myPlayer = new Player(player_name, off_rating, def_rating); //new Player object is created
		addToTeam(myPlayer);
	}
	
	//adds a Player to a Team
	private static void addToTeam(Player p) {
		System.out.println("Enter the team to add to: ");
		int iTeam = teamSearch();
		if(league.get(iTeam).size() >= 5) {
			System.out.println("*Player not added* This team is full");
			return;
		}
		league.get(iTeam).add(p);
		all_players.add(p);
		System.out.println("Player has been added");
	}
	
	//asks the user which Team a Player is being removed from
	private static void removeFromTeam() {
		if(all_players.size() == 0) {
			System.out.println("*Denied* There are no players to remove");
			return;
		}
		
		System.out.println("Enter a team to remove a player from (0 to go back): ");
		int iTeam = teamSearch();
		if(iTeam == -1) {return;}
		if(isEmpty(iTeam)) { System.out.println("This team is empty"); return; }
		removePlayer(iTeam);
	}
	
	//removes a Player from a Team
	private static void removePlayer(int iTeam) {
		league.get(iTeam).printRoster();
		
		System.out.println("Enter the index of the player to remove: ");
		int iPlayer = playerSearch(iTeam);
		
		Player p = league.get(iTeam).get(iPlayer);
		all_players.remove(p);
		league.get(iTeam).remove(iPlayer);
		
		System.out.println("Player has been removed");
	}
	
	//asks the user for two Teams and a Player from each Team and then trades them
	private static void tradePlayer() {
		if(league.size() < 2) {
			System.out.println("*Denied* Not enough teams for a trade");
			return;
		}
		
		System.out.println("Enter a team (0 to go back): "); //choosing the first team
		int iTeam1 = teamSearch();
		if(iTeam1 == -1) { System.out.println("*Trade canceled*"); return; }
		if(isEmpty(iTeam1)) { System.out.println("This team is empty"); return; }
		
		league.get(iTeam1).printRoster();
		System.out.println("Enter the index of a player to trade: "); //choosing the first player
		int iPlayer1 = playerSearch(iTeam1);
		
		System.out.println("Enter a team to trade with (0 to go back): "); //choosing the second team
		int iTeam2 = teamSearch();
		if(iTeam2 == -1) { System.out.println("*Trade canceled*"); return; }
		if(isEmpty(iTeam2)) { System.out.println("This team is empty"); return; }
		
		league.get(iTeam2).printRoster();
		System.out.println("Enter the index of a player to trade: "); //choosing the second player
		int iPlayer2 = playerSearch(iTeam2);
		
		if(iTeam1 == iTeam2) {
			System.out.println("*Trade failed* Trades must be between two different teams");
			return;
		}
		
		league.get(iTeam1).trade(iPlayer1, league.get(iTeam2), iPlayer2); //doing the trading
		System.out.println("Players have been successfully traded");
	}
	
	//asks the user for a Team and Player, lets them adjust the off and def rating of the Player
	private static void adjustStat() {
		if(league.size() == 0) {
			System.out.println("*Denied* There are no players");
			return;
		}
		
		System.out.println("Enter a team (0 to go back): ");
		int iTeam = teamSearch();
		if(iTeam == -1) {return;}
		if(isEmpty(iTeam)) { System.out.println("This team is empty"); return; }
		
		league.get(iTeam).printRoster();
		
		System.out.println("Enter player index: ");
		int iPlayer = playerSearch(iTeam);
		
		int off_rating = scanOffRating();
		int def_rating = scanDefRating();
		
		league.get(iTeam).get(iPlayer).setOffRating(off_rating);
		league.get(iTeam).get(iPlayer).setDefRating(def_rating);
		System.out.println("Player stats have been updated");
	}
	
	//asks the user for a Team and prints the roster of that Team
	private static void viewRoster() {
		if(league.size() == 0) {
			System.out.println("*Denied* There are no team rosters to view");
			return;
		}
		
		System.out.println("Enter the team roster to view (0 to go back): ");
		int iTeam = teamSearch();
		if(iTeam == -1) {return;}
		league.get(iTeam).printRoster();
	}
	
	//asks the user for two Teams and plays a game between them
	private static void playGame() {
		if(league.size() < 2) {
			System.out.println("*Denied* Not enough teams to play a game");
			return;
		}
		
		System.out.println("Enter a team (0 to go back):");
		int iTeam1 = teamSearch();
		if(iTeam1 == -1) { System.out.println("*Game canceled*"); return; }
		System.out.println("Enter another team:");
		int iTeam2 = teamSearch();
		if(iTeam2 == -1) { System.out.println("*Game canceled*"); return; }
		
		if(isEmpty(iTeam1) || isEmpty(iTeam2)) {
			System.out.println("*Game failed* One or more teams are empty");
			return;
		}
		
		league.get(iTeam1).play(league.get(iTeam2));
	}
	
	//calls quicksort() in the Sort class to rank all Players from highest overall rating to lowest
	private static void rankPlayers() {
		if(all_players.size() == 0) {
			System.out.println("*Denied* There are no players to rank");
			return;
		}
		Sort.quicksort(all_players);
	}
	
	//prompts the user for an input integer that is the off_rating for the Player and returns it
	private static int scanOffRating() {
		int off_rating = 0;
		do {
			try {
				System.out.println("Enter the player's new offensive rating (integer 1-10): ");
				off_rating = scan.nextInt();
				scan.nextLine();
			}catch(Exception e){ //catches exception if user input is not an integer
				System.out.println("-- You must enter an integer --");
				scan.nextLine();
			}
		}while(!validRating(off_rating)); //loops runs until the user enters values within the specified range
		return off_rating;
	}
	
	//prompts the user for an input integer that is the def_rating for the Player and returns it
	private static int scanDefRating() {
		int def_rating = 0;
		do {
			try {
				System.out.println("Enter the player's new defensive rating (integer 1-10): ");
				def_rating = scan.nextInt();
				scan.nextLine();
			}catch(Exception e) { //catches exception if user input is not an integer
				System.out.println("-- You must enter an integer --");
				scan.nextLine();
			}
		}while(!validRating(def_rating)); //loops runs until the user enters values within the specified range
		return def_rating;
	}
	
	//checks if off and def inputs are between 1 and 10
	private static boolean validRating(int num) {
		if(num < 1 || num > 10) {
			System.out.println("-- Inputs must be within range --");
			return false;
		}
		else {
			return true;
		}
	}
	
	//takes an input of Team name and returns the index of that team in the league ArrayList
	private static int teamSearch() {
		boolean team_found = false;
		int iTeam = 0;
		do{
			//the method calling teamSearch() has its own prompt for requesting a Team name from the user
			String team_name = scan.nextLine();
			if(team_name.equals("0")) {return -1;}
			for(int i = 0; i < league.size(); i++) {
				if(league.get(i).getName().equals(team_name)) { //the Team name has been found
					iTeam = i;
					team_found = true;
					break;
				}
			}
			if(!team_found) { //the Team name was not found
				System.out.println("-- You must enter a valid team --");
			}
		}while(!team_found);
		return iTeam; //returns the index of the Team in the league
	}
	
	//returns the index of a Player within a Team
	private static int playerSearch(int iTeam) {
		boolean player_found = false;
		int result = 0;
		do {
			//the method calling playerSearch() has its own prompt for requesting a Player index
			try {
				int iPlayer = scan.nextInt();
				scan.nextLine();
				if(iPlayer < league.get(iTeam).size() && iPlayer >= 0) { //checks for a valid index
					result = iPlayer;
					player_found = true;
				}
				else {
					System.out.println("-- You must enter a valid index --");
				}
			}catch(Exception e){ //catches exception if user input is not an integer
				System.out.println("-- You must enter a valid index --");
				scan.nextLine();
			}
		}while(!player_found);
		return result; //returns the index of a Player
	}
	
	//checks if a Team is empty
	private static boolean isEmpty(int iTeam) {
		return league.get(iTeam).size() == 0;
	}
	
	//prints the quit message for the program
	private static void quit() {
		System.out.println("-------------- Goodbye! --------------");
	}
	
	//prints the error message for invalid menu input
	private static void handleMenuError() {
		System.out.println("*Invalid menu choice*");
	}
}
