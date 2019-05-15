import java.util.HashSet;
import java.util.Set;

/**
 * This class models the Board of letter tiles that exists in a real Scrabble game. Here,
 * the object stores a 2D array of Tiles, a created class that models tiles on a board. This
 * class provides methods that are used during game-play to make sure that the board follows
 * all the invariants of the rules of the game. This class also stores an instantiation of the
 * ScrabbleRules class.
 */
public class Board {
	private Tile[][] grid;
	private ScrabbleRules rules;
	private Set<String> usedWords;
	
    /**
     * Constructs a Board's initial state by creating the 2D array and setting up the rules.
     * This constructor calls two private helper functions as well to set up the initial state
     * of the premium tiles.
     *
     * @param rules, from the ScrabbleRules class, which is used in some of the methods here
     * @return none
     */
	public Board(ScrabbleRules rules) {
		grid = new Tile[15][15];
		for (int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Tile(getInitialLetterMult(i, j), getInitialWordMult(i, j), rules);
			}
		}
		this.rules = rules;
		this.usedWords = new HashSet<String>();
	}
	
    /**
     * This is a simple getter functions which gets the Tile at a specfic position on the Board
     *
     * @param int x, the "x-coordinate" position of a Tile on the Board
     * @param int y, the "y-coordinate" position of a Tile on the Board
     * @return the Tile at the given position
     */
	public Tile getTile(int x, int y) {
		return grid[x][y];
	}
	
    /**
     * This method returns whether or not any moves have been made since the last turn
     *
     * @return a boolean, representing whether or not any moves have been made in the current turn
     */
	 public boolean movesMade() {
		for (int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
			if (!grid[i][j].isEmpty() && !grid[i][j].isFinalized()	) {
				return true;
				}
			}
			
		}
		return false;
	 }
	 
	/**
	* This is a private helper methods that sets up the initial values of the Tiles' letter 
	* multipliers at given positions in the board. This is called by the constructor.
	*
    * @param int x, the "x-coordinate" position of a Tile on the Board
    * @param int y, the "y-coordinate" position of a Tile on the Board
	* @return an int, representing the letterMultilplier for a specific Tile on the Board 
	*/
	private int getInitialLetterMult(int x, int y) {
		if ((x == 0 && y == 11) || (x == 0 && y == 3) || (x == 2 && y == 6)  ||
			(x == 2 && y == 8)  || (x == 3 && y == 7) || (x == 3 && y == 0)  ||
			(x == 3 && y == 14) || (x == 6 && y == 2) || (x == 6 && y == 6)  ||
			(x == 6 && y == 8) || (x == 6 && y == 12) || (x == 7 && y == 3)  ||
			(x == 7 && y == 11) || (x == 8 && y == 2) || (x == 8 && y == 6)  ||
			(x == 8 && y == 8) || (x == 8 && y == 12) || (x == 11 && y == 7)  ||
			(x == 11 && y == 0) || (x == 11 && y == 14) || (x == 12 && y == 6)  ||
			(x == 12 && y == 8) || (x == 14 && y == 3) || (x == 14 && y == 11)) {
			return 2;
		} else if((x == 1 && y == 5) || (x == 1 && y == 9) || (x == 5 && y == 1)  ||
				(x == 5 && y == 5)  || (x == 5 && y == 9) || (x == 5 && y == 13)  ||
				(x == 9 && y == 1) || (x == 9 && y == 5) || (x == 9 && y == 9)  ||
				(x == 9 && y == 13) || (x == 13 && y == 5) || (x == 13 && y == 9)) {
			return 3;
		}
		return 1;
		}
	
	/**
	* This is a private helper methods that sets up the initial values of the Tiles' word 
	* multipliers at given positions in the board. This is called by the constructor.
	*
    * @param int x, the "x-coordinate" position of a Tile on the Board
    * @param int y, the "y-coordinate" position of a Tile on the Board
	* @return an int, representing the wordMultiplier for a specific Tile on the Board 
	*/
	private int getInitialWordMult(int x, int y) {
		if ((x == 7 && y == 7) || (x == 1 && y == 1) || (x == 2 && y == 2)  ||
			(x == 3 && y == 3)  || (x == 4 && y == 4) || (x == 10 && y == 4)  ||
			(x == 11 && y == 3) || (x == 12 && y == 2) || (x == 13 && y == 1)  ||
			(x == 1 && y == 13) || (x == 2 && y == 12) || (x == 3 && y == 11)  ||
			(x == 4 && y == 10) || (x == 10 && y == 10) || (x == 11 && y == 11)  ||
			(x == 12 && y == 12) || (x == 13 && y == 13)) {
			return 2;
		} else if((x == 0 && y == 0) || (x == 7 && y == 0) || (x == 14 && y == 0)  ||
				(x == 0 && y == 7)  || (x == 0 && y == 14) || (x == 7 && y == 14)  ||
				(x == 14 && y == 7) || (x == 14 && y == 14)) {
			return 3;
		}
		return 1;
		}

	/**
	* This method checks to make sure that all the words on the Board are found
	* within the Scrabble Dictionary, i.e. that all the words are legal in the game.
	*
	* @return a boolean, representing whether or not all the words on the Board are legal
	*/
	public String isOnlyWords() {
	    String newWord = "";
	    String potentialWord = "";
	    
		for (int i = 0; i < grid[0].length; i++) {
			Tile[] tilesColumn = reverseArray(grid[i]);
			String lettersColumn = "";
			for (int j = 0; j < tilesColumn.length; j++) {
				lettersColumn += tilesColumn[j].getLetter();
				boolean currentIsNotEmpty = (!tilesColumn[j].getLetter().equals(" "));
				boolean aboveIsEmpty = true;
				boolean belowIsEmpty = true;
				if (j - 1 >= 0) {
					if (!tilesColumn[j - 1].getLetter().equals(" ")) {
						aboveIsEmpty = false; 
					}
				}
				if (j + 1 < 15) {
					if (!tilesColumn[j + 1].getLetter().equals(" ")) {
						belowIsEmpty = false; 
					}
				}
				if (aboveIsEmpty && belowIsEmpty && currentIsNotEmpty) {
					boolean leftIsEmpty = true;
					boolean rightIsEmpty = true;
					if (i - 1 >= 0) {
						if (!grid[i - 1][grid.length - j - 1].getLetter().equals(" ")) {
							leftIsEmpty = false; 
						}
					}
					if (i + 1 < 15) {
						if (!grid[i + 1][grid.length - j - 1].getLetter().equals(" ")) {
							rightIsEmpty = false; 
						}
					}
					if (rightIsEmpty && leftIsEmpty) {
						return null;
					}
				}
			}
			String[] words = lettersColumn.split(" ");
			for (int k = 0; k < words.length; k++) {
                if (!rules.isWord(words[k]) && !words[k].equals(" ") && words[k].length() >= 2) {
                    return null;
                } else if (isNewWord(words[k]) && words[k].length() >= 2) {
                    System.out.println("FOUND NEW WORD! IT IS: " + words[k]);
                    newWord = words[k];
                } else if (newWord.isEmpty() && words[k].length() >= 2) {
                    potentialWord = words[k];
                }
                
                if (words[k].length() >= 2) {
                    usedWords.add(words[k]);         
                }
			}
		}
		for (int i = 0; i < grid[0].length; i++) {
			String lettersRow = "";
			for (int j = 0; j < grid.length; j++) {
				lettersRow += grid[j][i].getLetter();
			}
			String[] words = lettersRow.trim().split(" ");
//			for (int k = 0; k < words.length; k++) {
//				}
			for (int k = 0; k < words.length; k++) {
				if (!rules.isWord(words[k]) && !words[k].equals(" ") && words[k].length() >= 2) {
					return null;
				} else if (isNewWord(words[k]) && words[k].length() >= 2) {
                    System.out.println("FOUND NEW WORD! IT IS: " + words[k]);
                    newWord = words[k];
                } else if (newWord.isEmpty() && words[k].length() >= 2) {
                    potentialWord = words[k];
                } 
				
				if (words[k].length() >= 2) {
                    usedWords.add(words[k]);         
                }
			}
		     System.out.println("New Word is: " + newWord);
		}
		System.out.println("END New Word is: " + newWord);
		if (newWord.isEmpty()) {
	        System.out.println("DOING THIS!??");
		    return potentialWord;
		}
	      System.out.println("Already used these words:");
	        for (String word : usedWords) {
	            System.out.println(word);

	        }
		return newWord;

	}
	
	private boolean isNewWord(String string) {
        return !usedWords.contains(string);
    }

    /**
	* This method is called to calculate the points gained by a Player after their turn 
	* in the game. It simply calculates how many new words were created during their turn,
	* and then counts up the letter values, the letter Multipliers, the word Multiplers of
	* the letters in those words.
	*
	* @return an int, representing the score for the turn that just ended 
	*/
	public int calculateScore() {
		int tempScore = 0;
		for (int i = 0; i < grid.length; i++) {
			Tile[] column = grid[i];
			int startIndex = 0;
			int endIndex = 0;
			for (int j = column.length - 1; j >= 0; j--) {
				if (column[j].getLetter().equals(" ") && j != column.length - 1 && 
						!column[j + 1].getLetter().equals(" ")) {
					endIndex = j + 1;
					boolean hasNewLetters = false;
					int wordMultiplier = 1;
					int tempWordScore = 0;
					if (startIndex - endIndex >= 1) {
						for (int k = startIndex; k >= endIndex; k--) {
						tempWordScore += column[k].getValue();
						wordMultiplier *= column[k].getWordMultiplier(); 
							if (!column[k].isFinalized()) {
								hasNewLetters = true;
							}
						}
						if (hasNewLetters) {
							tempScore += tempWordScore * wordMultiplier;
						}
					}
				} else if (!column[j].getLetter().equals(" ") && j == 0) {
					endIndex = j;
					boolean hasNewLetters = false;
					int wordMultiplier = 1;
					int tempWordScore = 0;
					if (startIndex - endIndex >= 1) {
						for (int k = startIndex; k >= endIndex; k--) {
							tempWordScore += column[k].getValue();
							wordMultiplier *= column[k].getWordMultiplier(); 
								if (!column[k].isFinalized()) {
									hasNewLetters = true;
								}
						}
						if (hasNewLetters) {
						tempScore += tempWordScore * wordMultiplier;
							}
					}
					
				} else if (!column[j].getLetter().equals(" ") && j != column.length - 1 && 
						column[j + 1].getLetter().equals(" ")) {
					startIndex = j;
				} else if (!column[j].getLetter().equals(" ") && j == column.length - 1) {
					startIndex = j;
				}

			}
		}
		for (int i = 0; i < grid.length; i++) {
			int startIndex = 0;
			int endIndex = 0;
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i].getLetter().equals(" ") && j != 0 && 
					!grid[j - 1][i].getLetter().equals(" ")) { 
					endIndex = j - 1;
					if (endIndex - startIndex >= 1) {
						boolean hasNewLetters = false;
						int wordMultiplier = 1;
						int tempWordScore = 0;
						for (int k = startIndex; k <= endIndex; k++) {
							tempWordScore += grid[k][i].getValue();
							wordMultiplier *= grid[k][i].getWordMultiplier(); 
							if (!grid[k][i].isFinalized()) {
								hasNewLetters = true;
							}
						}
						if (hasNewLetters) {
							tempScore += tempWordScore * wordMultiplier;
						}
					}
				} else if (!grid[j][i].getLetter().equals(" ") && j == grid.length - 1) { 
						endIndex = j - 1;
						boolean hasNewLetters = false;
						int wordMultiplier = 1;
						int tempWordScore = 0;
						if (endIndex - startIndex >= 1) {
							for (int k = startIndex; k <= endIndex; k++) {
								tempWordScore += grid[k][i].getValue();
								wordMultiplier *= grid[k][i].getWordMultiplier(); 
								if (!grid[k][i].isFinalized()) {
									hasNewLetters = true;
								}
							}
							if (hasNewLetters) {
								tempScore += tempWordScore * wordMultiplier;
							}
						}
				} else if (!grid[j][i].getLetter().equals(" ") && j != 0 && 
							grid[j - 1][i].getLetter().equals(" ")) {
						startIndex = j;
				} else if (!grid[j][i].getLetter().equals(" ") && j == 0) {
						startIndex = j;
				}
			}
		}
			return tempScore;
	}

	/**
	* This is a private helper methods that reverses an array.
	*
    * @param Til[] oldArry, an array of Tiles that needs to be reversed
	* @return a Tile[], the final reversed array 
	*/
	private Tile[] reverseArray(Tile[] oldArray) {
		Tile[] newArray = new Tile[oldArray.length];
		for (int i = 0; i < newArray.length; i++) {
			 newArray[i] = oldArray[oldArray.length - i - 1].makeCopy();
			}
		return newArray;
	}
	
	/**
	* This method is called during the game to make sure that all new Tiles placed on the 
	* Board during a round follow the invariant of the game that requires the new Tiles
	* to be somehow touching old Tiles.
	*
	* @return a boolean, representing whether or not the new Tiles are adjacent to the old Tiles
	*/
	public boolean newTilestouchOld() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Tile currentTile = grid[i][j];
				if (!currentTile.isFinalized() && !currentTile.isEmpty()) {
					if (((i - 1 < 0) || (i - 1 >= 0 && grid[i - 1][j].isEmpty())) &&
						((i + 1 >= grid.length) || 
								(i + 1 < grid.length && grid[i + 1][j].isEmpty())) &&
						((j + 1 < 0) || 
								(j - 1 >= 0 && grid[i][j - 1].isEmpty())) &&
						((i + 1 >= grid.length) || 
								(j + 1 < grid[i].length && grid[i][j + 1].isEmpty()))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	* This method is called at the end of a turn, after the score has been calculated, to 
	* finalize all the Tiles on the Board. This prevents those tiles from ever being moved or
	* changed again, an invariant of the game.
	*
	* @return none
	*/
	public void finalizeBoard() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (!grid[i][j].isEmpty()) {
					grid[i][j].finalize();
				}
			}
		}
	}
	
	/**
	* This method makes sure that all the tiles placed on the board during a single turn
	* are all within the same column or all within the same row. This is a mandatory 
	* invariant of the game.
	*
	* @return a boolean representing whether or not this invariant is maintained on the current
	* Board
	*/
	public boolean allMovesInLine() {
		boolean columnMove = false;
		boolean rowMove = false;
		boolean foundMove = false;
		int lineIndex = -1;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (!grid[i][j].isFinalized() && !grid[i][j].isEmpty()) {
					if (!foundMove) {
						for (int k = 0; k < grid.length; k++) {
							if (!grid[k][j].isFinalized() && !grid[k][j].isEmpty() && k!= i) {
								rowMove = true;
								lineIndex = j;
								foundMove = true;
								break;
							}
						}
						if (!foundMove) {
							columnMove = true;
							lineIndex = i;
							foundMove = true;
						}
					} else { 
						if (columnMove && i != lineIndex) {
							return false;
						} else if (rowMove && j != lineIndex) {
							return false;
						}
					}
					
				}
			}
		}
		return true;
	}

	/**
	* This methods is called after the first turn to make sure that the center tile is used,
	* which is a requirement in the rules of the game.
	*
	* @return a boolean, representing whether or not the center Tile was used
	*/
	public boolean middleTileUsed() {
		return !grid[7][7].isEmpty();
	}
}
