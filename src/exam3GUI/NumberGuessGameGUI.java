package exam3GUI;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* - PROGRAM DOCUMENTATION -
 * CLASS: CS 131 - DATA STRUCTURES.
 * PROJECT DEVELOPERS: Shelten Aguilar, Salvador Lugo
 * DATE: 11/04/2025 || NOVEMBER 4TH, 2025
 * DESCRIPTION: This Java program requires basic understanding of the following concepts:
 * Variables, Data Types, Numerical Operators, Basic Input/Output Logic (if statements, switch statements), Loops (for, while, do-while),
 * Pseudo-Random Numbers, etc. 
 * 
 * This is uses string input in certain areas to display knowledge of working with strings.
 * 		- Which includes a cheat code
 * 		- Handling of incorrect input
 * 
 * COMPLETION DATE: 11/13/2025 || NOVEMBER 13TH, 2025
*/



/*
 * **NOTE: ComputerVersusUser shortened to CVU**
 * **NOTE: UserVersusComputer shortened to UVC**
 */

/**
 * Class for game GUI
 */
public class NumberGuessGameGUI extends JFrame implements ActionListener {
	//Holds best scores
	private Integer computerBestScore;
	private Integer userBestScore;

	//Game mode references/objects
	private ComputerVsUserGame CVUGame;
	private UserVsComputerGame UVCGame;
	
	//GUI Components
	private JLabel computerScoreLabel;
	private JLabel userScoreLabel;
	private JTextField computerScoreField;
	private JTextField userScoreField;
	private JTextField userReplies;
	private JTextField computerAnswersCVU;
	private JTextField userNumbers;
	private JTextField computerAnswersUVC;
	private JTextArea cvuRules;
	private JTextArea uvcRules;
	private JButton computerVsUserButton;
	private JButton userVsComputerButton;
	private JButton quitButton;
	private JButton mainMenuButton;
	private JButton submitButtonCVU;
	private JButton submitButtonUVC;
	private JButton setNameButton;
	private JButton rulesCVUButton;
	private JButton rulesUVCButton;
	private JPanel gameModesCard;
	private JPanel topLeftPanel;
	private JPanel topRightPanel;
	private JPanel compVsUserCard;
	private JPanel userVsCompCard;
	private JPanel centerCards;
	private JPanel bottomExitCards;
	private JPanel bottomLeftCards;
	private JScrollPane cvuRulesPane;
	private JScrollPane uvcRulesPane;
	
	//CardLayout components for middle and bottom group components
	private CardLayout middleCardLayout;
	private CardLayout bottomExitButtonsCardLayout;
	private CardLayout bottomLeftCardLayout;
	
	//Holds string for names of cards for CardLayout
	private static final String CARD_MENU = "menu";
	private static final String CARD_CVU = "compVsUser";
	private static final String CARD_UVC = "UserVsComp";
	private static final String CARD_MAINMENU_BUTTON = "Main Menu";
	private static final String CARD_QUIT = "Quit";
	private static final String CARD_SET_NAME = "Set Name";
	private static final String CARD_CVU_RULES = "CVU Rules";
	private static final String CARD_UVC_RULES = "UVC Rules";
	
	
	/**
	 * Constructs a new NumberGuessGameGUI instance.
	 * Initializes game scores and game mode objects to their default states,
	 * then sets up and arranges all GUI components for display.
	 */
	NumberGuessGameGUI() {
		//Initiliaze scores to zero and game modes to null;
		computerBestScore = 0;
		userBestScore = 0;
		CVUGame = null;
		UVCGame = null;
		
		//Call methods to set and place components
		buildComponents();
		placeComponents();	
	}
	
	/**
	 * Main entry point for the Number Guess Game GUI application.
	 * Creates and initializes the game window with appropriate settings.
	 * Sets the window to close when the user clicks the close button,
	 * sizes it to fit the content, centers it on the screen, and makes it visible.
	 *
	 * @param args Command line arguments (not used)
	 */
	public static void main(String[] args) {
		NumberGuessGameGUI myGameFrame = new NumberGuessGameGUI();
		
		myGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myGameFrame.pack();
		myGameFrame.setLocationRelativeTo(null);
		myGameFrame.setVisible(true);
		
		
	}
	

	/**
	 * Handles action events triggered by button presses in the GUI.
	 * 
	 * Routes the action to the appropriate method based on which button was pressed:
	 * - computerVsUserButton: Initializes a game where the computer generates a number for the user to guess
	 * - userVsComputerButton: Initializes a game where the user generates a number for the computer to guess
	 * - mainMenuButton: Returns to the main menu
	 * - submitButtonCVU: Processes the user's guess in computer vs user mode
	 * - submitButtonUVC: Processes the computer's guess in user vs computer mode
	 * - rulesUVCButton: Displays rules for user vs computer mode
	 * - rulesCVUButton: Displays rules for computer vs user mode
	 * - setNameButton: Allows the user to set their name
	 * - quitButton: Closes the application
	 * 
	 * @param event the ActionEvent triggered by a button press
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		JButton sourceEvent = (JButton) event.getSource();
		
		if (sourceEvent == computerVsUserButton) { // Computer vs User button is pressed
			initializeCVUGame();	    
		}
		else if (sourceEvent == userVsComputerButton) { //Button press for user vs computer
			initializeUVCGame();
		}
		else if (sourceEvent == mainMenuButton) { //Button press for menu button
			reinitializeMainMenu();    
		}
		else if (sourceEvent == submitButtonCVU) { //Submit button in computer vs user mode
			gameCVU();
		}
		else if (sourceEvent == submitButtonUVC) { //Submit button in user vs computer mode
			gameUVC();
		}
		else if (sourceEvent == rulesUVCButton) { // button press to show user vs computer rules
			showUVCRules();
		}
		else if (sourceEvent == rulesCVUButton) { // button press to show computer vs user
			showCVURules();
		}
		else if (sourceEvent == setNameButton) { // button press to set user's name
			setName();
		}
		else if (sourceEvent == quitButton) { // button press to quit and exit game
			dispose();
		}
		
		
		
	}
	
	
	
	/*
	 * Game modes in the GUI
	 * 1. gameUVC = Game mode for user vs computer
	 * 2. gameCVU = Game mode for computer vs user
	 */
	
	
	/**
	 * Handles the user versus computer game logic for the number guessing game.
	 * 
	 * This method retrieves the user's input from the text field, processes it,
	 * and manages game flow including:
	 * - Validating user input as an integer
	 * - Checking for cheat codes
	 * - Retrieving hints from the computer
	 * - Determining win conditions
	 * - Updating the high score when applicable
	 * - Displaying appropriate messages to the user
	 * 
	 * @throws NumberFormatException if the user input cannot be parsed as an integer
	 */
	private void gameUVC() {
		String userNumStr = userNumbers.getText();
		String computerHints;
		int userNum;
		
		try {

			userNumStr = trimString(userNumStr); //Call method to remove ends whitespaces
			
			if (isInteger(userNumStr)) {
				userNum = Integer.parseInt(userNumStr); //Will throw a NumberFormatException String cannot be converted to Integer number format
				
				//Get next computer hint
				computerHints = UVCGame.getHints(userNum);
				
				if (computerHints.equalsIgnoreCase("Correct")) { //If block for when hint is "Correct"
					JOptionPane.showMessageDialog(this, "You win with " + UVCGame.getCurrentGuessNumber() + " tries! The number was " + userNum);
					setUserHighScore(UVCGame.getCurrentGuessNumber());
					reinitializeMainMenu(); // return to main menu
					UVCGame = null;
				}
				else { //Else output Try number and hint
						computerAnswersUVC.setText("Try " + UVCGame.getCurrentGuessNumber() + ": " + userNum + " is too " + computerHints); 
				}
			}

			// Check if user input is cheat code
			else if (UVCGame.enterCheatCode(userNumStr)){ 
				computerAnswersUVC.setText("Try " + UVCGame.getCurrentGuessNumber() + ": the number is " + UVCGame.getNumberCheat());
			}
			// throw number format exception if not an integer or cheat code
			else {
				throw new NumberFormatException(); 
			}
			
		}
		catch (NumberFormatException excpt) { //Catch statement for NumberFormatException
			JOptionPane.showMessageDialog(this, "Please enter an integer", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private boolean isInteger(String str) {
		
		try {

			Integer.valueOf(str);
			return true;
		} 
		catch (NumberFormatException excpt) {
			return false;
		}
	}
	
	/**
	 * Handles the Computer vs User (CVU) game flow by processing user input hints.
	 * 
	 * This method validates user input to ensure it matches expected hint values
	 * ("Correct", "Higher", or "Lower"), then updates the game state accordingly.
	 * 
	 * If the user indicates the correct number has been guessed, a win message is
	 * displayed with the number of attempts, the best score is updated, and the
	 * game is reset to the main menu.
	 * 
	 * If the user provides a valid hint, the game's search range is adjusted using
	 * binary search logic. If valid values remain, the next guess is displayed.
	 * If no valid values remain (due to user error in providing hints), an error
	 * message is shown and the game returns to the main menu.
	 * 
	 * Invalid input (anything other than the three expected hints) triggers an
	 * exception with an error message displayed to the user.
	 * 
	 * @throws Exception if user input does not match the expected hint values
	 *                   ("Correct", "Higher", or "Lower")
	 */
	private void gameCVU() {

		//String variable to handle user input
		String userText = userReplies.getText();	
		
		try {
			
			//If user enters input that is not an expected hint for the computer
			if (!userText.equalsIgnoreCase("Lower") && !userText.equalsIgnoreCase("Higher") && !userText.equalsIgnoreCase("Correct")) { // throw exception if string not expected value
				throw new Exception("Invalid Input: Please try again. (\"Correct\", \"Higher\", \"Lower\")");
			}
			
			else {
			
				if (userText.equalsIgnoreCase("Correct")) { // If block when string is "Correct"
					JOptionPane.showMessageDialog(this, "I win with " + CVUGame.getCurrentGuessCount() + " tries! The number was " + CVUGame.getMidVal());
					setComputerBestScore(CVUGame.getCurrentGuessCount());
					reinitializeMainMenu();
					CVUGame = null;
				}
				else { //Set next values and checks if next values set are valid

					CVUGame.setNextValues(userText);
					
					if (CVUGame.getLowVal() <= CVUGame.getHighVal()) { // When binare search reaches the last number
						computerAnswersCVU.setText("Try " + CVUGame.getCurrentGuessCount() + ": " + "Is the number " + CVUGame.getMidVal() + "?");
					}
					else { //Else statement user logic error no other possible values exist due to user error
						JOptionPane.showMessageDialog(this, "User logic error: Exiting to main menu", "Error", JOptionPane.ERROR_MESSAGE);
						CVUGame = null;
						reinitializeMainMenu(); // return to main menu
					}
					
				}
				
			}
		}
		
		catch (Exception excpt){ //exception if user entered an incorrect string
			JOptionPane.showMessageDialog(this, excpt.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
/*
* 	[Methods to set best scores]
* 	Built in logic to check if score is the best and set it
*/
	
	/**
	 * Sets the computer's best score if the provided score is lower than the current best score
	 * or if no best score has been recorded yet.
	 *
	 * @param score the score to be evaluated and potentially set as the computer's best score
	 */
	public void setComputerBestScore(int score) {
		if (score < computerBestScore || computerBestScore == 0)	{
			computerBestScore = score;
			computerScoreField.setText(computerBestScore.toString());
		}
	}
	
	/**
	 * Sets the user's best score if the provided score is lower than the current best score
	 * or if no best score has been recorded yet.
	 *
	 * @param score the score to be evaluated and potentially set as the users's best score
	 */
	public void setUserHighScore(int score) {
		if (score < userBestScore || userBestScore == 0) {
			userBestScore = score;
			userScoreField.setText(userBestScore.toString());
		}
	}
	
	
	
/*
* [Initialize methods] 
* To initialize game modes and reinitialize menu
*/
	
	/**
	 * Reinitializes the main menu by resetting the displayed cards to their initial states.
	 * This method switches the center card layout to display the main menu card,
	 * the bottom exit buttons layout to display the quit card, and the bottom left
	 * layout to display the set name card.
	 */
	private void reinitializeMainMenu() {
		middleCardLayout.show(centerCards, CARD_MENU); //Switch center cards to main menu center cards
		bottomExitButtonsCardLayout.show(bottomExitCards, CARD_QUIT); // switch Exit button card from main menu to exit
		bottomLeftCardLayout.show(bottomLeftCards, CARD_SET_NAME); // Switch bottom left cards
	}
	
	/**
	 * Initializes the User vs Computer game mode.
	 * 
	 * Creates a new UserVsComputerGame instance with a number range of 1-100,
	 * resets the game counter display, clears any previous user input, and
	 * switches the UI to display the User vs Computer game card layout along
	 * with the corresponding bottom navigation and rules panels.
	 */
	private void initializeUVCGame() {
		UVCGame = new UserVsComputerGame(1, 100); // Create a new game
		computerAnswersUVC.setText("Try " + UVCGame.getCurrentGuessNumber() + ": " + "Guess the number ?"); // Set first prompt
		userNumbers.setText(""); // Clear any remaining text
		middleCardLayout.show(centerCards, CARD_UVC); // Switch cards to play UVC game
	    bottomExitButtonsCardLayout.show(bottomExitCards, CARD_MAINMENU_BUTTON); // Switch exit button to main menu button
	    bottomLeftCardLayout.show(bottomLeftCards, CARD_UVC_RULES); // Switch bottom left card to show rules button  
		
	}
	
	
	/**
	 * Initializes the Computer vs User number guessing game.
	 * 
	 * Creates a new ComputerVsUserGame instance with a range from 1 to 100,
	 * displays the computer's initial guess and prompts the user to respond
	 * with "Correct", "Higher", or "Lower". Switches the display to show
	 * the CVU game panel and the main menu button, along with the game rules.
	 */
	private void initializeCVUGame() {
		CVUGame = new ComputerVsUserGame(1, 100);
		computerAnswersCVU.setText("Try " + CVUGame.getCurrentGuessCount() + ": " + "Is the number " + CVUGame.getMidVal() + "?");
		userReplies.setText("\"Correct\", \"Higher\", \"Lower\"");
		middleCardLayout.show(centerCards, CARD_CVU);
	    bottomExitButtonsCardLayout.show(bottomExitCards, CARD_MAINMENU_BUTTON);
	    bottomLeftCardLayout.show(bottomLeftCards, CARD_CVU_RULES);
	}
	
/*
* [Methods for game rules]
*/
	
	/**
	 * Displays a dialog box containing the rules for the CVU game.
	 * The rules are presented in a formatted pane and shown to the user
	 * in a message dialog with a plain message type.
	 */
	private void showCVURules() {
		JOptionPane.showMessageDialog(this, cvuRulesPane, "Rules", JOptionPane.PLAIN_MESSAGE);
	}
	
	
	/**
	 * Displays the UVC (University Venue Championship) game rules to the user.
	 * 
	 * This method shows a modal dialog window containing the rules for the UVC game.
	 * The dialog displays the content from the {@code uvcRulesPane} component and is
	 * titled "Rules".
	 */
	private void showUVCRules() {
		JOptionPane.showMessageDialog(this, uvcRulesPane, "Rules", JOptionPane.PLAIN_MESSAGE);
	}
	
	
	/**
	 * Returns the rules for the Computer vs. You game mode.
	 * 
	 * In this mode, the player thinks of a number between 1 and 100,
	 * and the computer attempts to guess it. The player must respond to
	 * each guess with 'Higher', 'Lower', or 'Correct' based on whether
	 * their number is higher, lower, or equal to the computer's guess.
	 * The computer has unlimited guesses but will be penalized for
	 * guesses outside the valid range.
	 * 
	 * @return a String containing the formatted rules and instructions
	 *         for the Computer vs. You game mode
	 */
	private String cvuRules() {
		String str = """
                             Rules of Mode: (Computer v. You)
                             1. You'll think of a number from 1 to 100 for me to guess.
                             2. Answer with 'Higher' if your number is higher.
                             3. Answer with 'Lower' if your number is lower.
                             4. Answer with 'Correct' if your number has been guessed correctly.
                             5. You have infinite number of guesses, but you will be penalized if you guess outside the range.""";
				
		return str;
	}
	
	
	/**
	 * Generates the game rules for the "You vs. Computer" mode.
	 * 
	 * This method returns a formatted string containing the rules and instructions
	 * for the "You vs. Computer" game mode, where the player attempts to guess a
	 * number that the computer has selected.
	 *
	 * @return a {@code String} containing the formatted rules for the You vs. Computer mode,
	 *         including:
	 *         
	 *           	- The range of numbers the computer will select from (1 to 100)
	 *           	- Instructions on how the computer will respond to guesses
	 *           	- Feedback indicators: "Higher", "Lower", or "Correct"
	 *         <
	 */
	private String uvcRules() {
		String str = """
                             Rules of Mode: (You v. Computer)
                             1. I'll think of a number from 1 to 100 for you to guess.
                             2. I'll answer 'Higher' if my number is higher than your guess.
                             3. I'll answer 'Lower' if my number is lower than your guess.
                             4. I'll answer 'Correct' if you have sucessfully guessed my number.""";
		return str;
	}
	
	/**
	 * Prompts the user to enter their name and sets it as the label for the user's score.
	 * The input name is trimmed of leading and trailing whitespace. If the user cancels
	 * the input dialog or enters a null value, the method returns without making changes.
	 * After a valid name is entered, the user score label is updated to display the name
	 * followed by "'s score:" and the GUI is packed to resize components appropriately.
	 */
	private void setName() {
		String name;
		
		name = JOptionPane.showInputDialog("Enter name:");
		
		name = trimString(name); //return a trimmed string
		
		if (name == null) { //if string is null return
			return;
		}
		
		userScoreLabel.setText(name + "'s score:");
		pack(); //Makes user and computer's score fields resized to fit to be visible
	}
	
/*
* [Helper Methods]
* 1. Trim string method
*/
	
	/**
	 * Trims leading and trailing whitespace from the given string.
	 * 
	 * @param str the string to trim, can be null
	 * @return a new string with leading and trailing whitespace removed,
	 *         or null if the input string is null or contains only whitespace characters
	 */
	private String trimString(String str) {
		if(str == null) return null; //Base case str is null

		if (str.charAt(0) != ' ' && str.charAt(str.length() - 1) != ' ') return str; //Base case if string has no end white spaces
		
		boolean hasWhiteSpace = true;
		
		
		
		for(int i = 0; i < str.length(); ++i) { // For loop to check for preceding white space 
			if (str.charAt(i) != ' ') { // if loop comes across non-whitespace char get substring and break loop
				hasWhiteSpace = false;
				str = str.substring(i);
				break;
			}
		}
		
		if (hasWhiteSpace) return null; // If hasWhiteSpace comes out as true the string is all white spaces, returns null

		
		for (int i = str.length() - 1; i >= 0; --i) { //For loop for white spaces at end of string
			if (str.charAt(i) != ' ') {
				str = str.substring(0, ++i);
				break;
			}
		}

		
		return str; // return new string without white spaces at the start and ends
	}
	
	
	
/**
 * [Helper Methods for constructor]
 * 1. Build components
 * 2. Place components
 */
	
	
	/**
	 * Builds and initializes all GUI components for the Number Guessing Game.
	 * 
	 * This method sets up the frame layout, creates and configures all labels,
	 * text fields, buttons, and panels used throughout the application. It organizes
	 * components into three main card layouts for switching between the main menu,
	 * Computer vs User game mode, and User vs Computer game mode.
	 * 
	 * Components initialized:
	 * - Score display labels and text fields for both computer and user
	 * - Text fields for game interactions (answers, replies, guesses)
	 * - Text areas and scroll panes for displaying game rules
	 * - Action buttons for game modes, submissions, navigation, and settings
	 * - Multiple panels organized with BoxLayout and CardLayout managers
	 * - Three CardLayout panels for dynamic view switching:
	 *   * Center cards: main menu, Computer vs User game, User vs Computer game
	 *   * Bottom exit cards: quit and main menu options
	 *   * Bottom left cards: set name, Computer vs User rules, User vs Computer rules
	 * 
	 * Prerequisites: Assumes that class fields for all components have been declared
	 * and that string constants (CARD_MENU, CARD_CVU, etc.) are defined.
	 */
	private void buildComponents() {
		// Set frame's title
		setTitle("Number Guessing Game");
		
		//Set GridBagLayout as layout manager
		setLayout(new GridBagLayout());
		
		//Set labels' contents
		computerScoreLabel = new JLabel("Computer's Score:");
		userScoreLabel = new JLabel("User's score:");
		
		//Set JTextField components
			//Set user and computer score fields
		computerScoreField = new JTextField(3);
		computerScoreField.setEditable(false);
		computerScoreField.setText(computerBestScore.toString());
		userScoreField = new JTextField(3);
		userScoreField.setEditable(false);
		userScoreField.setText(userBestScore.toString());
			//Middle components for Computer vs User
		computerAnswersCVU = new JTextField(20);
		computerAnswersCVU.setEditable(false);
		computerAnswersCVU.setText("Pick a number: 1-100 inclusive");
		userReplies = new JTextField(20);
		userReplies.setEditable(true);
		userReplies.setText("Reply here and press enter");
		userReplies.setFocusable(true);
			//Middle components for User vs Computer
		computerAnswersUVC = new JTextField(20);
		computerAnswersUVC.setEditable(false);
		computerAnswersUVC.setText("Guess the number 1-100 inclusive");
		userNumbers = new JTextField(20);
		userNumbers.setEditable(true);
		//Computer vs User rules message dialog components
		cvuRules = new JTextArea(cvuRules()); //Calls cvuRules method to fill JTextArea
	    cvuRulesPane = new JScrollPane(cvuRules);
	    cvuRules.setEditable(false);
	    	//User vs Computer rules message dialog components
	    uvcRules = new JTextArea(uvcRules());
	    uvcRulesPane = new JScrollPane(uvcRules);
	    uvcRules.setEditable(false);
	    
		

		//Create buttons
	    
	    	//Middle main menu buttons
		computerVsUserButton = new JButton("Computer versus You");
		computerVsUserButton.addActionListener(this);
		userVsComputerButton = new JButton("You versus Computer");
		userVsComputerButton.addActionListener(this);
			//Bottom main menu quit button
		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
			// Either game menu bottom buttons
		mainMenuButton = new JButton("Main Menu");
		mainMenuButton.addActionListener(this);
			// Computer vs User game mode middle submit button
		submitButtonCVU = new JButton("Submit");
		submitButtonCVU.addActionListener(this);
			//User vs Computer game mode middle submit button
		submitButtonUVC = new JButton("Submit");
		submitButtonUVC.addActionListener(this);
			//Set name button
		setNameButton = new JButton("Set Name");
		setNameButton.addActionListener(this);
			//Computer vs User rules button
		rulesCVUButton = new JButton("Rules");
		rulesCVUButton.addActionListener(this);
			//User vs Computer rules button
		rulesUVCButton = new JButton("Rules");
		rulesUVCButton.addActionListener(this);
		
		//Create panel for game mode buttons and add said buttons
			//Card for main menu middle
		gameModesCard = new JPanel();
		gameModesCard.add(computerVsUserButton);
		gameModesCard.add(userVsComputerButton);
			//Card for computer vs user
		compVsUserCard = new JPanel();
		compVsUserCard.setLayout(new BoxLayout(compVsUserCard, BoxLayout.Y_AXIS));
		compVsUserCard.add(computerAnswersCVU);
		compVsUserCard.add(userReplies);
		compVsUserCard.add(submitButtonCVU);
		submitButtonCVU.setAlignmentX(Component.LEFT_ALIGNMENT);
			//Card for user vs computer
		userVsCompCard = new JPanel();
		userVsCompCard.setLayout(new BoxLayout(userVsCompCard, BoxLayout.Y_AXIS));
		userVsCompCard.add(computerAnswersUVC);
		userVsCompCard.add(userNumbers);
		userVsCompCard.add(submitButtonUVC);
		submitButtonUVC.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//Create top panels
		topLeftPanel = new JPanel();
		topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.X_AXIS));
		topLeftPanel.add(computerScoreLabel);
		topLeftPanel.add(Box.createHorizontalStrut(10));
		topLeftPanel.add(computerScoreField);
		//topLeftPanel.add(Box.createHorizontalStrut(10));
		topRightPanel = new JPanel();
		topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.X_AXIS));
		topRightPanel.add(userScoreLabel);
		topRightPanel.add(Box.createHorizontalStrut(10));
		topRightPanel.add(userScoreField);
		//topRightPanel.add(Box.createHorizontalStrut(10));
		
		//Initialize cardLayout objects
		middleCardLayout = new CardLayout();
		bottomExitButtonsCardLayout = new CardLayout();
		bottomLeftCardLayout = new CardLayout();
		
		//Create the Game modes panel that contains cards;
			//Center cards
		centerCards = new JPanel(middleCardLayout);
			//centerCardsCVU = new JPanel(new CardLayout());
		centerCards.add(gameModesCard, CARD_MENU);
		centerCards.add(compVsUserCard, CARD_CVU);
		centerCards.add(userVsCompCard, CARD_UVC);
			//Bottom exit cards
		bottomExitCards = new JPanel(bottomExitButtonsCardLayout);
		bottomExitCards.add(quitButton, CARD_QUIT);
		bottomExitCards.add(mainMenuButton, CARD_MAINMENU_BUTTON);
			//Bottom left cards
		bottomLeftCards = new JPanel(bottomLeftCardLayout);
		bottomLeftCards.add(setNameButton, CARD_SET_NAME);
		bottomLeftCards.add(rulesCVUButton, CARD_CVU_RULES);
		bottomLeftCards.add(rulesUVCButton, CARD_UVC_RULES);
		
	}
	
	/**
	 * Places components on the GUI using GridBagLayout constraints.
	 * 
	 * This method configures the layout and positioning of all major panels
	 * in the game window:
	 * - Top left panel positioned at grid (0,0) with left alignment
	 * - Top right panel positioned at grid (4,0) with right alignment
	 * - Center panel spanning columns 0-4 at row 1, centered with weight
	 * - Bottom left panel positioned at grid (0,2) with left alignment
	 * - Bottom right (exit) panel positioned at grid (4,2) with right alignment
	 * 
	 * All components use consistent inset padding for visual spacing.
	 */
	private void placeComponents() {
		//Create a reference to layoutConst;
		GridBagConstraints layoutConst = new GridBagConstraints();
		
		// Specify components' grid location and padding
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		layoutConst.weightx = 0;
		layoutConst.insets = new Insets(10, 10, 10, 5);
		layoutConst.anchor = GridBagConstraints.LINE_START;
		add(topLeftPanel, layoutConst);
		
		layoutConst.gridx = 4;
		layoutConst.gridy = 0;
		layoutConst.weightx = 0;
		layoutConst.insets = new Insets(10, 10, 10, 5);
		layoutConst.anchor = GridBagConstraints.LINE_END;
		layoutConst.fill = GridBagConstraints.NONE;
		add(topRightPanel, layoutConst);	
		
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		layoutConst.gridwidth = 5;
		layoutConst.weightx = 1.0;
		layoutConst.weighty = 1.0;
		layoutConst.anchor = GridBagConstraints.CENTER;
		layoutConst.insets = new Insets(6, 10, 12, 10);
		add(centerCards, layoutConst);		
		
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 2;
		layoutConst.insets = new Insets(6, 10, 12, 10);
		layoutConst.anchor = GridBagConstraints.LINE_START;
		add(bottomLeftCards, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 4;
		layoutConst.gridy = 2;
		layoutConst.insets = new Insets(6, 10, 12, 10);
		layoutConst.anchor = GridBagConstraints.LINE_END;
		add(bottomExitCards, layoutConst);
	}
	
}
