

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;

/**
 * This class models each player in the game. Thus, it stores the name, points and letters of each
 * player as well as several other static fields shared among all Players. There are also methods
 * provided which handles the Player's interactions with other classes created.
 */
public class Player {
	private String name;
	private String[] myLetters;
	private static final int NUM_TILES = 7;
	private static boolean firstPlayer = true;
	private static JButton[] tileButtons = new JButton[NUM_TILES];
	private static Bag tileBag;
	private JLabel pointDisplay;
	private static JLabel playerDisplay;
	private ScrabbleRules gameRules;
	
    private int myPoints;
    
    /**
     * This method constructs the Player by selecting random letters from the Bag.
     * 
     * @param tileBag, the shared bag of letters
     * @param rules, the rules that each player has to follow
     * @return none
     */
    public Player(Bag tileBag, ScrabbleRules rules) {
    	name = "Sam";
    	myLetters = new String[NUM_TILES];
    	this.tileBag = tileBag;
        if (firstPlayer) {
            myLetters[0] = "G";
            myLetters[1] = "T";
            myLetters[2] = "K";
            myLetters[3] = "Y";
            myLetters[4] = "N";
            myLetters[5] = "H";
            myLetters[6] = "P";
            
        } else {
            myLetters[0] = "E";
            myLetters[1] = "E";
            myLetters[2] = "D";
            myLetters[3] = "N";
            myLetters[4] = "T";
            myLetters[5] = "W";
            myLetters[6] = "S";
        }
    	myPoints = 0;
    	pointDisplay = new JLabel(name + ": " + myPoints);
    	pointDisplay.setFont(new Font("Helectiva", Font.BOLD, 18));
    	pointDisplay.setForeground(Color.WHITE);
    	playerDisplay = new JLabel("");
    	gameRules = rules;
    	firstPlayer = false;
    }
    
    /**
     * This method gets the points of the player
     * 
     * @return an int of the current points the player has
     */
    public int getPoints() {
    	return this.myPoints;
    }
    
    /**
     * This method gets the total points of all the letters the Player has but hasn't used yet
     * 
     * @return an int of the total points of the Player's current hand
     */
    public int getUnplayedLetterPoints() {
    	int total = 0;
    	for (int i = 0; i < NUM_TILES; i++) {
    		total += gameRules.getValue(myLetters[i]);
    	}
    	return total;
    }
    
    /**
     * This method sets the name of the Player and updates the display accordingly
     * 
     * @param name, a String that represents the new name of the Player
     * @return none
     */
    public void setName(String name) {
    	this.name = name;
    	pointDisplay = new JLabel(name + ": " + myPoints);
    	pointDisplay.setFont(new Font("Helectiva", Font.BOLD, 18));
    	pointDisplay.setForeground(Color.WHITE);
    }
    
    /**
     * This method sets the first player's turn by updating the display accordingly
     * 
     * @return none
     */
    public void setFirstTurn() {
    	playerDisplay.setFont(new Font("Helectiva", Font.BOLD, 30));
    	playerDisplay.setForeground(Color.WHITE);
    	playerDisplay.setText("It's " + name + "'s turn!");
    }
    
    /**
     * This method draws the letters of the current Player
     * 
     * @return none
     */
    public void drawMyTiles() {
    	for (int i = 0; i < NUM_TILES; i++) {
    		tileButtons[i].setText(myLetters[i]);
     	}
    }
    
    /**
     * This method returns the array of JButtonst that Display the current player's Tiles
     * 
     * @return an array of the JButtons of the Displayed Player Tiles
     */
    public static JButton[] getButtons() {
    	return tileButtons;
    }
    
    /**
     * This method updates the display according to the player who is starting their turn
     * 
     * @return none
     */
    public void makeCurrentPlayer() {
    	playerDisplay.setText("It's " + name + "'s turn!");
    	this.drawMyTiles();
    }
    
    /**
     * This method exhanged the current letter of a Player for a new one from the Bag
     * 
     * @param a JButton that is the current JButton whose letter is being exchanged
     * @return none
     */
    public void exchangeTile(JButton button) {
    	int index = Player.getButtonIndex(button); 
    	String oldLetter = myLetters[index];
    	String newLetter = tileBag.exchange(oldLetter);
    	myLetters[index] = newLetter;
    	button.setText(newLetter);
    	this.drawMyTiles();
    }
    
    /**
     * This method determines if the Player is currently eligible to exchange a tile, based on whether
     * or not they have already put tiles on the board/
     * 
     * @return a boolean determining whether or not the Player is allowed to exchange Tiles
     */
    public boolean allowedToExchange() {
    	for (int i = 0; i < NUM_TILES; i++) {
    		if (myLetters[i].equals("")) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * This method fills up the "hand" of the current player with letters after they have finished
     * their turn.
     * 
     * @return none
     */
    public void fillLetters() {
    	for (int i = 0; i < NUM_TILES; i++) {
    		if (myLetters[i].equals("")) {
    			myLetters[i] = tileBag.take();
    		}
    	}
    }
    
    /**
     * This method returns the JButton stored at a specific index in tileButtons[]
     * 
     * @param index, the index in the array of JButtons
     * @return the JButton stored at that index
     */
    public static JButton getButton(int index) {
    	return tileButtons[index];
    }
    
    /**
     * This method returns the specific index at which a JButton is stored in tileButtons[]
     * 
     * @return an int that is the index in the array of a certain JButton, returns -1 if it is
     * not stored in the array
     */
    public static int getButtonIndex(JButton button) {
    	for (int i = 0; i < NUM_TILES; i++) {
    		if (tileButtons[i] == button) {
    			return i;
    		}
    	}
		return -1;
    }
    
    /**
     * This method brings a letter back from the grid into the "hand" of the current Player
     * 
     * @param index, an int which represents the index of a stored Tile Button
     * @param letter, a String that is the letter being returned back to the Button
     * @return none
     */
    public void returnLetter(int index, String letter) {
    	this.setIndex(index, letter);
    	tileButtons[index].setText(letter);
    }
    
    /**
     * This method gets the letter stored at a specific index in myLetters[]
     * 
     * @param index, the index of the letter in the array
     * @return an String that is the letter stored at the index
     */
    public String getLetter(int index) {
    	String letter = myLetters[index];
    	return letter;
    }
    
    /**
     * This method adds a letters to myLetters[] at a specific index
     * 
     * @param index, the index of the letter in the array
     * @param letter, the letter being stored at that index
     * @return none
     */
    private void setIndex(int index, String letter) {
    	myLetters[index] = letter;
	}

    /**
     * This method adds points to the Players points
     * 
     * @param newPoints, an int of the points being added
     * @return none
     */
	public void addPoints(int newPoints) {
    	this.myPoints += newPoints;
    	if (this.myPoints < 0) {
    		this.myPoints = 0;
    	}
		pointDisplay.setText(this.name + ": " + this.myPoints);
    }
    
    /**
     * This method returns the player's current points
     * 
     * @return an int, the player's current points
     */
    public int getScore() {
    	return myPoints;
    }
    
    /**
     * This method removes a letter from myLetters at a specific index
     * 
     * @param index, the index of the letter in the array
     * @return none
     */
    public void removeLetter(int index) {
    	myLetters[index] = "";
    	tileButtons[index].setText("");
    }

    /**
     * This method returns the Point Display
     * 
     * @return Container, the JLabel Displaying the Score
     */
	public Container getPointDisplay() {
		return pointDisplay;
	}

    /**
     * This method returns the Player Display
     * 
     * @return Container, the JLabel displaying the current Player
     */
	public static Component getPlayerDisplay() {
		JLabel playerSign = new JLabel();
        playerSign.setBackground(new Color(100, 0, 13));
        playerSign.setLayout(new FlowLayout());
        playerSign.add(playerDisplay);
		return playerDisplay;
	}

    /**
     * This method returns whether or not all the letters of the Player have been used in one turn
     * 
     * @return a boolean, whether the player has used all the letters in their "hand"
     */
	public boolean usedAllLetters() {
		for (int i = 0; i < myLetters.length; i++) {
			if (!myLetters[i].equals("")) {
				return false;
			}
		}
		return true;
	}

    /**
     * This method gets the name of the player
     * 
     * @return a String, that is the name of the player
     */
	public String getName() {
		return this.name;
	}

}
