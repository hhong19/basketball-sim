/*CSE 205: Class #11333 / Tuesday Thursday 4:30
 *Assignment: 6
 *Author(s): Harrison Hong / 1217745542
 *Description: An instance of Team is a LinkedList of Nodes. Each Node holds the 
 *data of a Player and the next Node in the list. Every Team has a name, head Node,
 *and size. The offensive and defensive ratings for a Team are calculated for
 *game-play and are not field variables. A Team can add a Player, remove a Player,
 *get a Player, get the size of the team, play another Team, calculate score for a
 *game, trade Players with another Team, get and set name, calculate offensive and
 *defensive rating, and print the Team roster.
 */

import java.util.NoSuchElementException;
import java.util.Random;

public class Team{
	private String name;
	private Node head = null;
	private int size = 0;
	
	//constructor for a new Team, takes a name argument
	Team(String name){
		setName(name);
	}
	
	//adds a new Node to a Team LinkedList, thus adding a Player
	public void add(Player p) {
		if(this.size() >= 5) { //the Team is full
			return;
		}
		
		if(head == null) { //the Node being added is the first one in the list
		    head = new Node();
			head.player = p;
			size++;
			return;
		}
		
		Node newNode = new Node(); //a new Node is created to store the new data
		newNode.player = p;

		Node current = head;
		while(current.next != null) {
			current = current.next;
		} //current now points to the tail

		current.next = newNode; //newNode gets added on to the end of the list
		
		size++;
	}
	
	//removes a Node from a Team LinkedList, thus removing a Player
	public void remove(int index) {
		if(size == 0) { //handles teams with no players
			System.out.println("This team is empty");
			return;
		}
		
		if(index == 0) { 
			head = head.next; //removes the head Node by setting it to head.next
			size--;
			return;
		}
		
		Node current = head;
		int count = 0;
		while(current != null){
			if(count == index-1) {
				current.next = current.next.next; //removes the Node at the index by setting the previous Node's next to the one after the index
			}
			current = current.next;
			count++;
		}
		size--;
	}
	
	//returns a Player at the specified index, used when searching for a specific Player on a Team
	public Player get(int index) {
		if(index < 0 || index >= size) { //the index does not exist
			throw new NoSuchElementException("invalid index");
		}
		
	    Node current = head;
		int count = 0;

		while(current != null) {
			if(count == index) { //returns the Node's player at the index
				return current.player;
			}
			current = current.next;
			count++;
		}
		
		return null; //this return statement lets the method compile
	}
	
	//returns how many Players are on a Team
	public int size() {
		return this.size;
	}
	
	//one Team plays another Team in a game
	//Team scores are randomly generated from a range that is determined by Team off and def ratings
	public void play(Team other) {
		//calculating the stats for the teams
		int team1_off = this.getOffRating();
		int team1_def = this.getDefRating();
		int team2_off = other.getOffRating();
		int team2_def = other.getDefRating();
		
		//calculating each team's score
		int team1_score = getScore(team1_off, team2_def);
		int team2_score = getScore(team2_off, team1_def);
		
		//prints the game results
		System.out.println("\nResults");
		System.out.println(this.getName() + ": " + team1_score);
		System.out.println(other.getName() + ": " + team2_score);
		
		//prints the winner of the game
		if(team1_score > team2_score) {
			System.out.println(this.getName() + " win!");
		}
		else if(team1_score == team2_score) { //recursively calls the play method in the case of a tie
			this.play(other);
		}
		else {
			System.out.println(other.getName() + " win!");
		}
	}
	
	//helper method for play(), calculates how many points a Team scores during a game
	private static int getScore(int off, int def) { 
		//score is calculated by choosing a random value between one team's off_rating and the other team's def_rating
		//essentially finds a random value between max score possible and min score possible
		int score;
		Random r = new Random();
		//handles either case of off_rating or def_rating being greater
		if(off > def) {
			score = r.nextInt((off - def) + 1) + def; //setting the random score
		}
		else {
			score = r.nextInt((def - off) + 1) + off; //setting the random score
		}
		return score;
	}
	
	//used to trade a Player on one Team for a Player on another Team
	public void trade(int iPlayer1, Team other, int iPlayer2) {
		Player temp1 = this.get(iPlayer1); //stores the player of this team to be traded
		Player temp2 = other.get(iPlayer2); //stores the player of the other team to be traded
		
		//players get removed from their teams to make room for the trade
		this.remove(iPlayer1);
		other.remove(iPlayer2);
		
		//players get added to their new team via the temps
		this.add(temp2);
		other.add(temp1);
	}
	
	//returns the name of the Team
	public String getName() {
		return this.name;
	}
	
	//sets the name of the Team
	public void setName(String name) {
		this.name = name;
	}
	
	//calculates the offensive rating for a team (essentially max points possible for the team)
	//this is done using the offensive ratings of Players on the Team
	//used to get a Team's score for a game
	public int getOffRating() { 
		int total = 0;
		Node current = head;
		while(current != null) {
			total += current.player.getOffRating(); //adds up the total of offensive player ratings
			current = current.next;
		}
		return total * 2; //multiplies the total by 2 to make the max score possible 100 points
	}
	
	//calculates the defensive rating for a team (essentially min points that the other team can score)
	//this is done using the defensive ratings of Players on the Team
	//used to get a Team's score for a game
	public int getDefRating() { 
		int total = 0;
		Node current = head;
		while(current != null) {
			total += current.player.getDefRating(); //adds up the total of defensive player ratings
			current = current.next;
		}
		return 100 - (total * 2); //subtracts the total from the max possible points (100) to get points allowed
		//this inverts the value so that higher player def ratings still mean better team def rating
		//this detail means that scores don't necessarily make sense based on team size but it keeps the game balanced
	}
	
	//prints the team roster with index, name, and stats
	//used for viewing which players are on a Team and also for finding Player index for removal
	public void printRoster() {
		System.out.println("\n" + this.name + " Roster");
		Node current = head;
		int index = 0;
		System.out.println("Index Name              Off Def");
		while(current != null) {
			System.out.printf("%-6d", index);
			System.out.printf("%-18s", current.player.getName());
			System.out.printf("%-4d", current.player.getOffRating());
			System.out.printf("%-3d\n", current.player.getDefRating());
			index++;
			current = current.next;
		}
	}
	
	//Nodes each hold the data of a player and the next Node in the list
	class Node{
		public Player player = null;
		public Node next = null;
	}
}