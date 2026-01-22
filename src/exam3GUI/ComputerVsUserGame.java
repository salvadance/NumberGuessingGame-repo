package exam3GUI;


/**
 * ComputerVsUserGame implements a binary search guessing game where the computer
 * makes guesses within a specified range and adjusts its search based on user feedback.
 * 
 * The game uses a binary search algorithm to efficiently narrow down the target number
 * by dividing the search range in half with each guess. The computer tracks the number
 * of guesses made and maintains the current search bounds (lowVal and highVal) along
 * with the midpoint (midVal) for the next guess.
 * 
 * The user provides feedback indicating whether the target number is higher or lower
 * than the computer's current guess, allowing the game to adjust its range accordingly.
 * 
 * @author [Salvador Lugo]
 */

public class ComputerVsUserGame {
	private int lowVal; // lowest value in range
	private int highVal; // highest value in range
	private int midVal; // middle value for range
	private int currentGuessCount; // current guess
	private final static String HIGHER = "Higher";
	private final static String LOWER = "Lower";
	
	
	/**
	 * Constructs a ComputerVsUserGame instance with specified range bounds.
	 * Initializes the game with a low and high value, calculates the midpoint,
	 * and sets the currentGuessCount to track the number of guesses.
	 *
	 * @param lowVal the lower bound of the guessing range (inclusive)
	 * @param highVal the upper bound of the guessing range (inclusive)
	 */
	public ComputerVsUserGame(int lowVal, int highVal) {
		this.lowVal = lowVal;
		this.highVal = highVal;
		
		midVal = (lowVal + highVal) / 2;
		
		currentGuessCount = 1;
	}
		
	
	/**
	 * Updates the search range and midpoint value based on the user's feedback direction.
	 * Implements a binary search algorithm by adjusting the lower or upper bounds and
	 * recalculating the midpoint for the next guess.
	 *
	 * @param direction a String indicating whether the target is "HIGHER" or "LOWER"
	 *                  than the current midpoint value. The comparison is not case-insensitive.
	 *
	 *
	 * @see #HIGHER
	 * @see #LOWER
	 */
	public void setNextValues(String direction) {
		
		if (direction.equalsIgnoreCase(HIGHER)) {
			lowVal = midVal + 1;
			++currentGuessCount;
			midVal = (highVal + lowVal) / 2;
		}
		else if (direction.equalsIgnoreCase(LOWER)) {
			highVal = midVal - 1;
			++currentGuessCount;
			midVal = (highVal + lowVal) / 2;
		}
		
	}
	
	//Get method that returns highVal
	public int getHighVal() {
		return highVal;
	}
	
	//Get method that returns lowVal
	public int getLowVal() {
		return lowVal;
	}
	
	
	/**
	 * Gets the current number of guesses made in the game.
	 *
	 * @return the current guess count
	 */
	public int getCurrentGuessCount() {
		return currentGuessCount;
	}
	
	/**
	 * Gets the middle value.
	 *
	 * @return the middle value stored in midVal
	 */
	public int getMidVal() {
		return midVal;
	}

}
