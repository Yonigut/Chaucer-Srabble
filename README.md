# Chaucer-Srabble
This is a digital humanities project that functions as a modified version of the Scrabble Game 
in which the user can only use words from Chaucer's famous work "The Wife of Bath's Tale."

In addition to the typical interface, this implementation also has an audial feature:
Every early middle english word that is submitted is read out loud. Additionally, several
of the instructions are also read out loud.

There are several text files here that contain the letters, the letter values and the dictionary values. Additionally, there are many audio files with the boice recordings of all the lines from the Chaucer tale. The actual code is organized into the following classes:

# Bag.java
This class represents the bag of letter tiles used when playing Scrabble. Each game has exactly one bag that starts off full and, at each turn players can take random tiles from the bag or swap their own tiles for new random ones from the bag.

# Board.java
This class uses a 2D array to represent the actual board that is used during game play.

# Dictionary.java
This class contains methods for checking whether or not a word is in the dictionary by initially reading and storing a dictionary text file.

# Game.java
This class models the actual game play by dealing with all the checks at each turn and by simulating each player’s turn.

# GameScreen.java
This class simply starts the program and runs the Game class.

# Player.java
This class models each of the two players in the game, containing all the relevant field information of each player, including their current set of tiles, their points so far and whether or not it is their turn.

# ScrabbleRules.java
This class has many methods that are used to check whether or not a player’s input is valid.

# Tile.java
This class represents each Tile in the game. Every tile has a letter and a corresponding point value.

# TokenScanner.java
This class is used to read in text files.

