

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
/**
 * This Class creates all the JComponents, JButtons on the screen, handles their interactions
 * and maintains the state of the game. It brings all the pieces together. Because of this,
 * it has many fields that each hold on to a different aspect of its state.
 */
@SuppressWarnings("serial")
public class GameScreen {
	
	private Board myBoard;
	private boolean tileSelected = false;
	private JButton currentTile;
	private Player currentPlayer;
	private Player waitingPlayer;
	private JPanel playersTiles;
	private boolean firstRound;
	private Bag tileBag;
	private JButton checkTurn;
	private JButton finishButton;
	private boolean myTilesEnabled = true;
	private int scorelessTurns;
	private JButton resetButton;
	private ScrabbleRules myRules;
	private JLabel wordPanel;
	private JLabel linePanel1;
	private JLabel linePanel2;
	private JLabel linePanel3;
	 
    /**
     * Constructs a GameScreen by calling the start helper function.
     *
     * @param none
     * @return none
     */
    public GameScreen(JFrame frame) {
           start(frame);
   }
    
    /**
     * Displays the instructions of the game before starting the game.
     *
     * @param frame, the JFrame created in Game class
     * @return none
     */
	private void start(JFrame frame) {
        String soundName = "files/media.io_Intro.wav";
        Clip clip = null;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {

        }

        clip.start();
        
	    JOptionPane.showConfirmDialog(null, "Heere biginneth the Tale of the Wif of Bathe", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//		JOptionPane.showConfirmDialog(frame, "Heere biginneth the Tale of the Wif of Bathe",
//    			"", JOptionPane.INFORMATION_MESSAGE);
	    
	    restart(frame);
	}
	
    /**
     * This method actually starts each playing of the game.
     *
     * @param frame, the JFrame created in Game class and used to draw the JComponents
     * @return none
     */
	private void restart(JFrame frame) {
		// set up basic state
		ScrabbleRules rules = new ScrabbleRules();
    	myBoard = new Board(rules);
    	myRules = rules;
    	firstRound = true;
		tileBag = new Bag();
		scorelessTurns = 0;
		
		// set up basic format of the window of the game
        frame.setSize(new Dimension(100, 100));
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        frame.setContentPane(listPane);
        frame.setBackground(new Color(100, 0, 13));
        
        // create basic options buttons for game-play
        JButton exchangeButton = makeExchangeButton();
        JButton endTurnButton = this.makeFinishButton(exchangeButton);
        JButton takeTilesButton = makeTileTakeButton(endTurnButton);
        JButton checkTurnButton = makeCompleteButton(frame, listPane, takeTilesButton);
        checkTurnButton.setEnabled(false);

         // adds the "end" and "reset" buttons to the frame
         JPanel controlPanel = makeControlPanel(frame);
         frame.add(controlPanel, BorderLayout.NORTH);
        
         // adds the grid of Tiles (the Board) to the frame
         JPanel grid = gridLayoutPanel(exchangeButton, checkTurnButton);
         frame.add(grid);
         
         // creates the two players, adds the current player display to the screen
         Player player1 = new Player(tileBag, myRules);
         Player player2 = new Player(tileBag, myRules);
         Icon icon = new ImageIcon("files/wife.gif");
        //  String player1Name = JOptionPane.showInputDialog("Player 1, Please Enter Your Name");
         String player1Name = (String) JOptionPane.showInputDialog(null,"Player 1, Please Enter Your Name", "", JOptionPane.INFORMATION_MESSAGE, icon, null,"");
         String player2Name = (String) JOptionPane.showInputDialog(null,"Player 2, Please Enter Your Name", "", JOptionPane.INFORMATION_MESSAGE, icon, null,"");
         player1.setName(player1Name);
         player2.setName(player2Name);
         this.currentPlayer = player1;
         this.waitingPlayer = player2;
         player1.setFirstTurn();
         playersTiles = makeMyTiles();
         JPanel playerSign = new JPanel();
         playerSign.setLayout(new FlowLayout());
         playerSign.add(Player.getPlayerDisplay());
         playerSign.setBackground(new Color(100, 0, 13));
         frame.add(playerSign);
         
         // adds the options bar to the screen
         frame.add(makeOptionBar(exchangeButton, checkTurnButton, takeTilesButton, endTurnButton));
        
         // adds the players' tiles to the frame
         frame.add(playersTiles); 

         // adds the Score tracker to the screen
         JPanel currentScores = new JPanel();
         currentScores.setLayout(new FlowLayout());
         currentScores.add((player1.getPointDisplay()));
         currentScores.add((player2.getPointDisplay()));
    	 currentScores.setBackground(new Color(100, 0, 13));
         frame.add(currentScores);
         JPanel literaryPanel = new JPanel();
         literaryPanel.setLayout(new FlowLayout());
         wordPanel = makeWordPanel();
         linePanel1 = makeLinePanel();
         linePanel2 = makeLinePanel();
         linePanel3 = makeLinePanel();
         JPanel linesPanel = new JPanel();
         linesPanel.setLayout(new GridLayout(3,1));
         linesPanel.add(linePanel1);
         linesPanel.add(linePanel2);
         linesPanel.add(linePanel3);
         linesPanel.setBackground(new Color(100, 0, 13));
         linesPanel.setOpaque(true);
         literaryPanel.add(wordPanel);
         literaryPanel.add(linesPanel);
         literaryPanel.setBackground(new Color(100, 0, 13));
         literaryPanel.setOpaque(true);
//         wordPanel.setText("HO");
//         wordPanel.setBackground(new Color(100, 0, 13));
//         wordPanel.setOpaque(true);
//         wordPanel.setLayout(new FlowLayout());
//         frame.add(wordPanel);
//         frame.add(linePanel);

		 frame.add(literaryPanel);
	}
    
    /**
     * Constructs the "Exchange" Button to be used during the game, allowing players
     * to exchange a single tile if they have no moves. This method also adds its listeners
     *
     * @param none
     * @return the newly constructed exchange Button
     */
    private JButton makeExchangeButton() {
    	JButton exchangeButton = new JButton("Exchange Tile");
    	exchangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (currentPlayer.allowedToExchange()) {
            	currentPlayer.exchangeTile(currentTile);
            	myTilesEnabled = false;
            	exchangeButton.setEnabled(false);
            	checkTurn.setEnabled(false);
            	finishButton.setEnabled(true);
            }
            }
    	});
    	return exchangeButton;
    }
    
    /**
     * Constructs the "Check Turn" Button to be used during the game, allowing players
     * to check if their turns passed all the rules of the game. This method also adds its Action
     * Listeners which is long because of the different possible outcomes after the program
     * checks if the turn is legitimate and if the game should be ended.
     *
     * @param frame, the frame of the window which the button needs to be added
     * @param listPane, the parent component for the JOptionPanes
     * @param takeTilesButton, the JButton on the screen which players click to take more buttons.
     * @return the newly constructed "Check Turn" Button
     */
    private JButton makeCompleteButton(JFrame frame, Component listPane, JButton takeTilesButton) {
    	JButton newButton = new JButton("Check Turn");
    	checkTurn = newButton;
    	newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newestWord = myBoard.isOnlyWords();
            if (!myBoard.movesMade()) {
            	JOptionPane.showInternalMessageDialog(listPane, "No Moves Made. Please try again.",
            			"Turn Incomplete!", JOptionPane.INFORMATION_MESSAGE);
            } else if (!firstRound && !myBoard.newTilestouchOld()) {
				JOptionPane.showInternalMessageDialog(listPane, "All the Tiles on the Board Must "
						+ "be Connected. Please Try Again.",
            			"Tiles Not Connected", JOptionPane.INFORMATION_MESSAGE);
			} else if (firstRound && !myBoard.middleTileUsed()) {
				JOptionPane.showInternalMessageDialog(listPane, "Center Tile must be used in the "
						+ "first move. Please Try Again.",
            			"Center Tile Not Used", JOptionPane.INFORMATION_MESSAGE);
			} else if (!myBoard.allMovesInLine()) {
				JOptionPane.showInternalMessageDialog(listPane, "All Tiles Placed in one turn Must"
						+ " share a row or column. Please Try Again.",
            			"New Tiles Illegally Places", JOptionPane.INFORMATION_MESSAGE);
			} else if (newestWord == null) {
					JOptionPane.showInternalMessageDialog(listPane, "Board Contains Words not In "
							+ "Dictionary. Please Try Again.",
	            			"Words Not Allowed", JOptionPane.INFORMATION_MESSAGE);
			} else if (currentPlayer.usedAllLetters() && tileBag.noMoreTiles()) {
				Player initWinner = null;
	        	if (currentPlayer.getPoints() > waitingPlayer.getPoints()) {
	        		initWinner = currentPlayer;
	        	} else {
	        		initWinner = waitingPlayer;
	        	}
	        	endGame(frame, initWinner);
				currentPlayer.addPoints(waitingPlayer.getUnplayedLetterPoints());
				endGame(frame, initWinner);
			} else {
				int newPoints = myBoard.calculateScore();
				if (newPoints == 0) {
					scorelessTurns++;
				} else {
					scorelessTurns = 0;
				}
				if (currentPlayer.usedAllLetters()) {
					currentPlayer.addPoints(50);
				}
				if (scorelessTurns >= 6) {
		        	Player initWinner = null;
		        	if (currentPlayer.getPoints() > waitingPlayer.getPoints()) {
		        		initWinner = currentPlayer;
		        	} else {
		        		initWinner = waitingPlayer;
		        	}
		        	endGame(frame, initWinner);
					}
				currentPlayer.addPoints(newPoints);
				myBoard.finalizeBoard();
				firstRound = false;
				takeTilesButton.setEnabled(true);
				takeTilesButton.setEnabled(true);
				newButton.setEnabled(false);
	            myTilesEnabled = false;
	            System.out.println("HAPPENING");
	            System.out.println("newest word is " + newestWord);
	            wordPanel.setText("\"" + newestWord.toLowerCase() + "\": ");
	            int randomLine = myRules.getRandomLine(newestWord);
	            linePanel1.setText("Line " + randomLine + ": ");
	            linePanel2.setText(myRules.getFirstLine(randomLine));
	            linePanel3.setText(myRules.getSecondLine(randomLine));
            }
            }
        });
    	return newButton;
    }
    
    
    private JLabel makeWordPanel() {
        JLabel wordPanel = new JLabel();
        wordPanel.setFont(new Font("Helectiva", Font.BOLD, 30));
        wordPanel.setForeground(Color.WHITE);
        wordPanel.setText(" ");
        wordPanel.setBackground(new Color(100, 0, 13));
        wordPanel.setLayout(new FlowLayout());
        wordPanel.setOpaque(true);
        return wordPanel;
    }
    
    private JLabel makeLinePanel() {
        JLabel linesPanel = new JLabel();
        linesPanel.setFont(new Font("Helectiva", Font.BOLD, 12));
        linesPanel.setForeground(Color.WHITE);
        linesPanel.setText(" ");
        linesPanel.setBackground(new Color(100, 0, 13));
        linesPanel.setLayout(new FlowLayout());
        linesPanel.setOpaque(true);
        return linesPanel;
    }
    
    
    /**
     * Constructs the "Take Tile" Button, which players click on after their turns have been checked
     * to get more letters for the next round.
     *
     * @param endTurnButton, the "End Turn" Button that can only be clicked once the player gets
     * 			more tiles
     * @return the newly constructed "Take Tiles" Button
     */
    private JButton makeTileTakeButton(JButton endTurnButton) {
    	JButton newButton = new JButton("Take Tiles");
		newButton.setEnabled(false);
    	newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				currentPlayer.fillLetters();
				currentPlayer.drawMyTiles();
				endTurnButton.setEnabled(true);
				newButton.setEnabled(false);
            }
            });
    	return newButton;
    }
    
    /**
     * Constructs the Option Bar, the JPanel of the different buttons that can be cliked during a
     * player's turn.
     *
     * @param endTurnButton, the "End Turn" Button that can only be clicked once the player gets
     * 			more tiles
     * @param exchnageButton, the "Exchange Turn" Button that players click on to exchange a single
     * 			Tile for a new one from the bag
     * @param checkTurnButton, the "Check Turn" that players click after their turns
     * @param takeTilesButton, the "Take Tiles" Button that is used to get more tiles at the end
     * 			of turns
     * @return the newly constructed Options Panel
     */
    private JPanel makeOptionBar(JButton exchangeButton, JButton checkTurnButton, 
    	JButton takeTilesButton, JButton endTurnButton) {
        JPanel options = new JPanel();
        options.setLayout(new FlowLayout());
        options.setBackground(new Color(100, 0, 13));
        options.add(exchangeButton);
        options.add(takeTilesButton);
        options.add(checkTurnButton);
        options.add(endTurnButton);
        return options;
    }
    
    /**
     * Constructs the "End Turn" Button, used by players to end their turns after they see their
     * new tiles.
     *
     * @param exchnageButton, the "Exchange Turn" Button that players click on to exchange a single
     * 			Tile for a new one from the bag
     * @return the newly constructed "End Turn" Button
     */
    private JButton makeFinishButton(JButton exchangeButton) {
    	JButton newButton = new JButton("End Turn");
		newButton.setEnabled(false);
		finishButton = newButton;
    	newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
					Player temp = currentPlayer;
					currentPlayer = waitingPlayer;
					waitingPlayer = temp;
					currentPlayer.makeCurrentPlayer();
					// checkTurn.setEnabled(true);
					newButton.setEnabled(false);
	            	myTilesEnabled = true;
	            	exchangeButton.setEnabled(true);
					// currentPlayer.enableTiles();
	            	wordPanel.setText(" ");
	            	linePanel1.setText(" ");
	            	linePanel2.setText(" ");
	            	linePanel3.setText(" ");
            }
            
            });
    	return newButton;
    }
    
    /**
     * Constructs the 7 Buttons represents the current Player's personal Tiles
     *
     * @return the newly constructed JPanel of personal Tiles
     */
    private JPanel makeMyTiles() {
        JPanel tilePanel = new JPanel();
        tilePanel.setBackground(new Color(100, 0, 13));
        JButton[] panelButtons = Player.getButtons();
        for (int i = 0; i < panelButtons.length; i++) {
        	panelButtons[i] = new JButton();
        	JButton current = panelButtons[i];
        	tilePanel.add(current);
        	current.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if (current.getText() != " " && myTilesEnabled) {
                		currentTile = current;
                		tileSelected = true;
                	}
                }
            });
        }
        currentPlayer.drawMyTiles();
        return tilePanel;
    }
    
    /**
     * Constructs the visual Grid of the Game Board, using the 2D array in the Board class. 
     * 
     * @param exchangeButton, the button which allows players to exchange a Tile instead of
     * 			making moves during a turn
     * @param checkTurnButton, the button which allows player to check if their turns were
     * 			legal according to the rules of the game.
     * @return the newly constructed Grid of the Board Tiles
     */
    private JPanel gridLayoutPanel(JButton exchangeButton, JButton checkTurnButton) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(15, 15));
        
        for (int j = 14; j >= 0; j--) {
        	for (int i = 0; i < 15; i++) {
        		Tile current = myBoard.getTile(i, j);
        		panel.add(current);
        		current.initialDraw();
        		current.addActionListener(new ActionListener() {
        			@Override
        			public void actionPerformed(ActionEvent e) {
        				if (!current.isFinalized()) {
        					if (tileSelected) {
        						current.putLetterOnTile(Player.getButtonIndex(currentTile), 
        								currentPlayer);
        						tileSelected = false;
        						exchangeButton.setEnabled(false);
        							if (myTilesEnabled) {
        								checkTurnButton.setEnabled(true);	
        							}

        					} else if (myTilesEnabled) {
        						current.removeLetterFromTile(Player.getButtonIndex(currentTile), 
        								currentPlayer);
        						if (currentPlayer.allowedToExchange()) {
        							exchangeButton.setEnabled(true);
        							checkTurnButton.setEnabled(false);
        						} else {
        							checkTurnButton.setEnabled(true);
        						}
                		
        					}
        				}
        			}
        		});        	
        	}
        }
        return panel;
    }
    
    /**
     * Constructs the Control Panel containing the "Reset" and "End" Buttons for the game
     *
     * @param frame, the JFrame of the entire game window
     * @return the newly constructed JPanel of personal Tiles
     */
    private JPanel makeControlPanel(JFrame frame) {
        final JButton reset = new JButton("Reset Game");
        
        reset.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
             restart(frame);
            }
        });
        reset.setEnabled(false);
        resetButton = reset;
        
        final JButton end = new JButton("End Game");
        end.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		Player initWinner = null;
        		if (currentPlayer.getPoints() > waitingPlayer.getPoints()) {
        			initWinner = currentPlayer;
        		} else {
        			initWinner = waitingPlayer;
        		}
        		endGame(frame, initWinner);
        		System.out.println("Clicked End");
            }
        });
        
        final JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(100, 0, 13));
        controlPanel.add(reset);
        controlPanel.add(end);
		return controlPanel;
	}

    /**
     * This method calculates and displays the final scores, determines the winner and displays
     * the high scores as well.
     * 
     * @param frame, the JFrame of the entire game
     * @param initWinner, the initial Winner before the final calculations
     * @return none
     */
	private void endGame(JFrame frame, Player initWinner) {
		currentPlayer.addPoints(- currentPlayer.getUnplayedLetterPoints());
		waitingPlayer.addPoints(- waitingPlayer.getUnplayedLetterPoints());
		
		Player finalWinner = null;
		if (currentPlayer.getPoints() > waitingPlayer.getPoints()) {
			finalWinner = currentPlayer;
		} else if (currentPlayer.getPoints() == waitingPlayer.getPoints()) {
			finalWinner = initWinner;
		} else {
			finalWinner = waitingPlayer;
		}
		
		String finalMessage = ("The Winner is: " + finalWinner.getName() + ". \n Final Scores: \n " 
		+ currentPlayer.getName() + " with " + currentPlayer.getPoints() + " points. \n "
		+ waitingPlayer.getName() + " with " + waitingPlayer.getPoints() + " points. \n ");
		
	      String soundName = "files/media.io_End.wav";
	        Clip clip = null;
	        try {
	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
	            clip = AudioSystem.getClip();
	            clip.open(audioInputStream);
	        } catch (Exception e) {

	        }

	        clip.start();
	        
	    JOptionPane.showConfirmDialog(null, "Heere endeth the Wives Tale of Bathe", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	      
        Icon icon = new ImageIcon("files/wife.gif");
		
		JOptionPane.showMessageDialog(frame, finalMessage, "GAME OVER!",
				JOptionPane.INFORMATION_MESSAGE, icon);
		String highScores = myRules.updateHighScores(currentPlayer.getName(), 
				currentPlayer.getPoints(), waitingPlayer.getName(), waitingPlayer.getPoints(), 
				"files/Scrabble_High_Scores.txt");
		JOptionPane.showMessageDialog(frame, highScores,
    			"High Scores", JOptionPane.INFORMATION_MESSAGE, icon);
		resetButton.setEnabled(true);
    }
}
