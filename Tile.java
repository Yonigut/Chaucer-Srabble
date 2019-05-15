

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Tile extends JButton {
	private String letter;
	private int value;
	private boolean isEmpty;
	private int letterMultiplier;
	private int wordMultiplier;
	private boolean finalized;
	private int buttonIndex;
	private ScrabbleRules rules;

	/**
	 * This class models each Tile on the Board in the game. Thus, it stores the letter and value
	 * of each Tile, as well as its letterMultiplier, wordMultiplier and several other fields in
	 * its state. The Tile Models each of the spaces on a physical Scrabble board. All the Tiles
	 * here are contained within the Board object. There are methods here that are provided for
	 * the interaction between Tile and other classes.
	 */
	Tile(int letterMultiplier, int wordMultiplier, ScrabbleRules rules) {
		super(" ");
		letter = " ";
		value = 0;
		isEmpty = true;
		this.letterMultiplier = letterMultiplier;
		this.wordMultiplier = wordMultiplier;
		this.finalized = false;
		this.buttonIndex = -1;
		this.rules = rules;
	}
	
    /**
     * This method makes a Copy of the Tile with the same exact state
     * 
     * @return an Tile that is a copy of the current Tile
     */
	public Tile makeCopy() {
		Tile duplicate = new Tile(this.letterMultiplier, this.wordMultiplier, rules);
		if (this.letter != " ") {
			duplicate.setTile(this.letter, this.value);
			duplicate.finalize();
		} 
		duplicate.setText(this.getText());
		duplicate.setFont(this.getFont());
		return duplicate; 
	}
	
    /**
     * This method gets the letter of the current Tile
     * 
     * @return an String that is the letter of the current Tile
     */
	public String getLetter() {
		String returnedLetter = this.letter;
		return returnedLetter;
	}
	
    /**
     * This method sets the Tiles to a specific letter and value
     * 
     * @param letter, a String that is the new letter that the Tile is getting
     * @param vlaue, an int that is the corresponding value to the new letter
     * @return an int of the current points the player has
     */
	public void setTile(String letter, int value) {
		this.letter = letter;
		this.value = value;
		if (letter.equals(" ")) {
			this.value = 0;
			this.isEmpty = true;
		} else {
			this.value = rules.getValue(letter);
			this.isEmpty = false;
		}
	}
	
    /**
     * This method handles the placing of a letter onto a tile on the Board. If there is already
     * a letter, then the old letter is removed and the new one is aded. Otherwise, the new one 
     * is just added.
     * 
     * @param tileIndex, the index of the JButton from which the new letter is being added
     * @param currentPlayer, the player adding this letter
     * @return none
     */
	public void putLetterOnTile(int tileIndex, Player currentPlayer) {
		String newLetter = currentPlayer.getLetter(tileIndex);
		if (this.letter != " ") {
			String oldLetter = this.letter;
			currentPlayer.returnLetter(buttonIndex, oldLetter);
		} else {
			currentPlayer.removeLetter(tileIndex);
		}
		currentPlayer.removeLetter(tileIndex);
		this.setTile(newLetter, 5);
		this.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        this.setText(newLetter);
		this.buttonIndex = tileIndex;
	}
	
    /**
     * This method handles the removing of a letter onto a tile on the Board.
     * 
     * @param tileIndex, the index of the JButton to which the letter is being returned
     * @param currentPlayer, the player removing this letter
     * @return none
     */
	public void removeLetterFromTile(int tileIndex, Player currentPlayer) {
		if (this.letter != " ") {
			String oldLetter = this.letter;
			this.setTile(" ", 0);
			this.initialDraw();
			currentPlayer.returnLetter(this.buttonIndex, oldLetter);
			this.buttonIndex = -1;
		}
	}
	
    /**
     * This method "finalizes" a Tile by updating its state. This essentially prevents the 
     * Tile's letter from ever being changed in the future.
     * 
     * @return none
     */
	public void finalize() {
		this.finalized = true;
		this.buttonIndex = -1;
		this.isEmpty = false;
		this.letterMultiplier = 1;
		this.wordMultiplier = 1;
		this.setBackground(new Color(245, 245, 220));
	}
	
    /**
     * This method determines whether or not the current Tile is finalized
     * 
     * @return a boolean, whether or not the Tile is finalized
     */
	public boolean isFinalized() {
		return finalized;
	}
	
    /**
     * This method gets the Value of the Tile by multiplying its letterMultiplier with the letter
     * value
     * 
     * @return an int, that is the total Tile Value
     */
	public int getValue() {
		int oldMultiplier = letterMultiplier;
		return oldMultiplier * value;
	}
	
    /**
     * This method gets letterMultiplier
     * 
     * @return an int, that is the letterMultiplier of the current Tile
     */
	public int getLetterMultiplier() {
		return this.letterMultiplier;
	}
	
    /**
     * This method gets wordMultiplier
     * 
     * @return an int, that is the wordMultiplier of the current Tile
     */
	public int getWordMultiplier() {
		return this.wordMultiplier;
	}

    /**
     * This method draws the original state of the Tile
     * 
     * @return none
     */
	public void initialDraw() {
		if (this.getLetterMultiplier() == 2) {
			this.setBackground(new Color(51, 153, 255));
            this.setOpaque(true);
            this.setBorderPainted(true);
            this.setFont(new Font("Times New Roman", Font.BOLD, 7));
            this.setText("2X  Letter");
    	} else if (this.getLetterMultiplier() == 3) {
    		this.setBackground(new Color(20, 120, 255));
    		this.setOpaque(true);
    		this.setBorderPainted(true);
    		this.setFont(new Font("Times New Roman", Font.BOLD, 7));
    		this.setText("3X  Letter");	
    	} else if (this.getWordMultiplier() == 2) {
    		this.setBackground(new Color(255, 102, 102));
    		this.setOpaque(true);
    		this.setBorderPainted(true);
    		this.setFont(new Font("Times New Roman", Font.BOLD, 7));
    		this.setText("2X  Letter");
    	} else if (this.getWordMultiplier() == 3) {
    		this.setBackground(Color.RED);
    		this.setOpaque(true);
    		this.setBorderPainted(true);
    		this.setFont(new Font("Times New Roman", Font.BOLD, 7));
    		this.setText("3X  Word");	
    	} else {
    		this.setBackground(new Color(245, 245, 220));
    		this.setOpaque(true);
    		this.setBorderPainted(true);
    		this.setText(" ");
    	}
	}

    /**
     * This method returns whether or not a Tile has a letter
     * 
     * @return a boolean, whether or not the Tile is empty
     */
	public boolean isEmpty() {
		return this.isEmpty;
	}
}
