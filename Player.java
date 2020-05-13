/*CSE 205: Class #11333 / Tuesday Thursday 4:30
 *Assignment: 6
 *Author(s): Harrison Hong / 1217745542
 *Description: A Player has a name, off_rating, and def_rating. A Player can
 *get and set its name, get and set its off_rating, get and set its
 *def_rating, calculate its overall rating for ranking.
 */

public class Player {
	private String name;
	private int off_rating;
	private int def_rating;
	
	//default constructor, creates a place-holder "empty" Player
	Player(){
		this.name = "empty";
		this.off_rating = 0;
		this.def_rating = 0;
	}
	
	//constructor taking name, off_rating, and def_rating arguments to create a new Player
	Player(String name, int off_rating, int def_rating){
		setName(name);
		setOffRating(off_rating);
		setDefRating(def_rating);
	}
	
	//returns the name of a Player
	public String getName() {
		return this.name;
	}
	
	//sets the name of a Player
	public void setName(String name) {
		this.name = name;
	}
	
	//returns a Player's offensive rating
	public int getOffRating() {
		return this.off_rating;
	}
	
	//sets a Player's offensive rating
	public void setOffRating(int off_rating) {
		this.off_rating = off_rating;
	}
	
	//returns a Player's defensive rating
	public int getDefRating() {
		return this.def_rating;
	}
	
	//sets a Player's defensive rating
	public void setDefRating(int def_rating) {
		this.def_rating = def_rating;
	}
	
	//calculates a Player's overall rating
	public double getOverallRating() {
		//overall rating for a Player is for ranking purposes only
		//it is an average of a Player's offensive and defensive rating
		double overall = (this.off_rating + this.def_rating) / 2.0;
		return overall;
	}
}
