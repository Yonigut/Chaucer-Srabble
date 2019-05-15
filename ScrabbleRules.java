

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;

public class ScrabbleRules {
	private Map<String, Integer> letterValues;
	private Dictionary scrabbleWords;
	
	/**
	 * This class models the rules of the game. It creates a Dictionary of the scrabble words,
	 * sets up the Instructions, creates a mapping of letters to their values and sets up 
	 * the high score reader, writers. 
	 */
	public ScrabbleRules() {
	    scrabbleWords = null;
		try {
			scrabbleWords = Dictionary.make("files/wifeOfBath.txt", "files/wifeOfBathWithPunctuation.txt");
			letterValues = new TreeMap<String, Integer>();
			Reader in = new FileReader("files/Scrabble_Values.txt"); 
	        BufferedReader br = new BufferedReader(in);
	        boolean hasNext = true;
	        while (hasNext) {
	        	String letter = br.readLine();
	        	if (letter == null) {
	        		hasNext = false;
	        	} else {
	        		int value = Integer.parseInt(br.readLine());
	        		letterValues.put(letter, value);
	        	}
	        }
	        br.close();
			
		} catch (IOException e) {
			// do nothing
		}
	}
	
	   public int getRandomLine(String word) {
	        return scrabbleWords.getLineIndex(word);
	    }
	
	public String getFirstLine(int randomLine) {
	    scrabbleWords.playLine(randomLine);
	    return scrabbleWords.getFirstLine(randomLine);
	}
	
	   public String getSecondLine(int randomLine) {
	        return scrabbleWords.getSecondLine(randomLine);
	    }

    /**
     * This method returns whether or not a given word is in the Scrabble dictionary
     * 
     * @param word, a String that needs to be checked in the dictionary
     * @return a boolean, whether or not it is in the dictionary
     */
	public boolean isWord(String word) {
		return scrabbleWords.isWord(word);
	}
	
    /**
     * This method returns the Scrabble value of a given letter
     * 
     * @param letter, a String whose value we are looking for
     * @return a int that is the corresponding value
     */
	public int getValue(String letter) {
		if (letter == " " || letter == "") {
			return 0; 
		}
		return letterValues.get(letter);
	}
	
    /**
     * This method returns the String of instructions read from a file
     * 
     * @return a String that contains all the instructions
     */
	public static String getInstructions() {
		String instructions = "";
		try {
			Reader in = new FileReader("files/Scrabble_Instructions.txt"); 
	        BufferedReader br = new BufferedReader(in);
	        boolean hasNext = true;
	        while (hasNext) {
	        	String line = br.readLine();
	        	if (line == null) {
	        		hasNext = false;
	        	} else {
	        		instructions += line;
	        		instructions += " \n ";
	        	}
	        }
	        br.close();
		} catch (IOException e) {
			// do nothing
		}
		return instructions;
	}
	
    /**
     * This method reads, updates and write the updated new High Scores into a file. It then
     * proceeds to return the String containing all the high scores.
     * 
     * @param player1Name, a String that is the name of player1
     * @param player1Score, an int that is the score of player1
     * @param player2Name, a String that is the name of player2
     * @param player2Score, an int that is the score of player2
     * @param fileName, a String containing the name of the file that holds the high scores
     * 
     * @return a String that contains all the upated High Scores
     */
	public String updateHighScores(String player1Name, int player1Score, 
								String player2Name, int player2Score, String fileName) {
		
		String[] topNames = new String[5];
		int[] topScores = new int[5];
		
		if (player1Score < player2Score) {
			int tempScore = player1Score;
			String tempName = player1Name;
			 player1Score = player2Score;
			 player1Name = player2Name;
			 player2Score = tempScore;
			 player2Name = tempName;
		}

		try {
			Reader in = new FileReader(fileName); 
	        BufferedReader br = new BufferedReader(in);
	        boolean hasNext = true;
	        for (int i = 0; i < topNames.length; i++) {
	        	String name = br.readLine();
	        	if (name == null) {
	        		break;
	        	} else if (name.equals("")) {
	        		name = br.readLine();
	        	} else {
	        		topNames[i] = name;
	        		String score = br.readLine();
	        		if (score == null) {
	        			topNames[i] = null;
	        		} else if (score.equals("")) { 
	        		score = br.readLine();
	        		} else {
	        		score = score.trim();
	        		topScores[i] = Integer.parseInt(score);
	        		}
	        	}
	        }
	        br.close();
		} catch (IOException e) {
			// do nothing
		}
            boolean firstPlayerWritten = false;
            boolean secondPlayerWritten = false;
            for (int i = 0; i < topNames.length; i++) {
            	if (player1Score > topScores[i] && !firstPlayerWritten) {
            		int prevScore = topScores[i];
            		String prevName = topNames[i];
            		int currScore = 0;
            		String currName = "";
            		for (int j = i + 1; j < topNames.length; j++) {
            			        currScore = topScores[j];
                 				currName = topNames[j];
                				topScores[j] = prevScore;
                				topNames[j] = prevName;
                				prevScore = currScore;
                				prevName = currName;
            		}
            		topScores[i] = player1Score;
            		topNames[i] = player1Name;
            		firstPlayerWritten = true;
            	} else if (player2Score > topScores[i] && !secondPlayerWritten) {
            		int prevScore = topScores[i];
            		String prevName = topNames[i];
            		int currScore = 0;
            		String currName = "";
            		if (i + 1 <= topNames.length) {
            			for (int j = i + 1; j < topNames.length; j++) {
            			        currScore = topScores[j];
                 				currName = topNames[j];
                				topScores[j] = prevScore;
                				topNames[j] = prevName;
                				prevScore = currScore;
                				prevName = currName;
            			}
            		}
    			topScores[i] = player2Score;
    			topNames[i] = player2Name;
    			secondPlayerWritten = true;
    			break;
            	} else if (topNames[i] == null && !firstPlayerWritten) {
            		topScores[i] = player1Score;
            		topNames[i] = player1Name;
            		firstPlayerWritten = true;
            	} else if (topNames[i] == null && firstPlayerWritten && !secondPlayerWritten) {
        			topScores[i] = player2Score;
        			topNames[i] = player2Name;
        			secondPlayerWritten = true;
        			break;
            	}
            }
            
        	try { 
        		Writer out = new BufferedWriter(new FileWriter(fileName)); 
            	for (int i = 0; i < topNames.length; i++) {
            		if (topNames[i] != null) {
            			out.write(topNames[i]);
            			out.write("\n");
            			out.write(Integer.toString(topScores[i]));
            			out.write("\n");
            		}
            	}
        		out.flush();
        		out.close();
        	} catch (IOException e) {
        		// do nothing
        	}
        	
    		String highScores = "";
        	for (int i = 0; i < topScores.length; i++) {
        		if (topNames[i] == null) {
        			break;
        		}
        		highScores += (Integer.toString(i + 1) + ". " + topNames[i] + 
        				" with " + Integer.toString(topScores[i]) + " points \n");
        	}
        	return highScores;
		}
	}
