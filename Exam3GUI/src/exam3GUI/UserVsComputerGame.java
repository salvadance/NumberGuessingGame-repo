package exam3GUI;

import java.util.Random;

/**
 * UserVsComputerGame is a class that implements a number guessing game between a user and the computer.
 * 
 * The computer generates a random number within a specified range, and the user attempts to guess it.
 * The class provides hints to guide the user towards the correct number and tracks the number of guesses.
 * Additionally, it includes a cheat mode that can be activated with a specific code to reveal the target number.
 * Resets game mode
 * 
 * @author [Salvador Lugo]
 */
public class UserVsComputerGame {
	private int numToGuess; //Variable for number to guess
	private int currentGuess; //currentGuess keeps track of number of tries
	private boolean isCheatOn; // Boolean value for cheat
	
	
	/**
	 * Constructs a UserVsComputerGame with a random number within the specified range.
	 * 
	 * @param lower the lower bound of the range (inclusive)
	 * @param upper the upper bound of the range (inclusive)
	 */
	public UserVsComputerGame(int lower, int upper) {
		Random randGen = new Random();
		
		numToGuess = randGen.nextInt(upper - lower + 1) + lower;
		currentGuess = 1;
		isCheatOn = false;
	}
	
	
	/**
	 * Provides a hint by comparing the user's guess to the target number.
	 *
	 * @param num the user's guessed number
	 * @return "Correct" if the guess matches the target number,
	 *         "high" if the guess is greater than the target number,
	 *         "low" if the guess is less than the target number
	 */
	public String getHints(int num) {
		if (num == numToGuess) {
			return "Correct"; // if value matches return correct
		}
		else if (num > numToGuess) { // if user value is lower return higher
			++currentGuess;// increment currentGuess
			return "high"; 
		}
		else { // else the user's value is higher so returns lower
			++currentGuess; //increment currentGuess
			return "low"; 
		}
	}
	
	//Get method that returns the currentGuess count
	public int getCurrentGuessNumber() { 
		return currentGuess;
	}
	
	/**
	 * Checks if the provided code matches the cheat code and activates cheat mode if it does.
	 * Is case sensitive.
	 * Sets isCheatOn to true so cheat is ready to use
	 * 
	 * @param userCode the code string to verify against the cheat code
	 * @return true if the code matches the cheat code and cheat mode is activated, false otherwise
	 */
	public boolean enterCheatCode(String userCode) {
		if (userCode.equals("DataBreach")) {
			isCheatOn = true;
			return true;
		}
		return false;
	}
	
	
	/**
	 * Retrieves the number to guess if cheat mode is enabled.
	 * 
	 * If cheat mode is currently active, this method disables it and returns
	 * the number that needs to be guessed. If cheat mode is not active, returns null.
	 * 
	 *
	 * @return the number to guess if cheat mode is on, otherwise null
	 */
	public Integer getNumberCheat() {
		if (isCheatOn) {
			isCheatOn = false;
			return numToGuess;
		}
		return null;
	}
	
	
	/**
	 * Gets the current state of the cheat mode.
	 *
	 * @return true if cheat mode is enabled, false otherwise
	 */
	public boolean getIsCheatOn() {
		return isCheatOn;
	}
	
}
