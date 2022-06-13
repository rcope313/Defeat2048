# Defeat2048 🎮

## Project Description
This version of the popular game 2048 has an extensible interface that 
allows custom Game Heuristics to reach the 2048 tile (and beyond). 
A Game Heuristic defines how the program will evaluate and score 
each left, up, down or right move based on the current game state.

## Design Principles
When implementing Defeat2048 with the use of Game Heuristics, a game-search 
algorithm is running behind the scenes. 2048 is nondeterministic; the random-tile
generator is the opponent. For each move, Defeat2048 builds a game tree of
predefined size, with each node having at most four permissible children 
(by key event left, right, up, or down).

It seems that looking ahead 3 to 6 moves is optimized for time complexity 
and “good-enough” evaluation, seeing as random tile output is not accounted for.

A Game Heuristic defines the evaluation script to judge the best game state of the nth 
move ahead (for implementation details, see **Contributing to Defeat2048**). 
A game-state is defined by the ```KeyEventHandler``` class. By traversing
back up the tree, the program resolves its immediate next move, being left, right, up, or down.


## Installing Defeat2048
Javalib.jar was the sole image library used to illustrate the game, courtesy
of Northeastern University. However, this library is not available in Maven’s 
central repository; on each personal machine, the library must be downloaded to 
its local repository. [Download the .jar file](https://course.ccs.neu.edu/cs2510h/files/javalib.jar)
and input into the command line:

```mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file -Dfile=”pathname”```

From there, as long as the user has a 14.0 or greater JRE downloaded locally, 
the user can interface with Defeat2048, as defined in **Using Defeat2048**.

## Using Defeat2048
Defeat2048 has two entrance methods with their respective executable jars; 
the first allows the user to play 2048 via a GUI and see the given
Game Heuristic at work visually; the second "plays" the game with the given Game Heuristic
behind the scenes for a specified amount of times and game tree
depth.

### Play Game
The user can decide whether to play the game manually via the GUI (left) or see 
their defined Game Heuristic at work visually (right).


<p align="center">
<img src="https://media.giphy.com/media/GwHMFAU01SbizEtsaU/giphy.gif" width="350"/>
  <img src="https://media.giphy.com/media/jVbDijrTQkObSweeWI/giphy.gif" width="340"/>

</p>


- Jar File: Game2048Player/target/PlayGame.jar   
- Command: ```java -jar <pathToJar>```
- Arguments: If the user doesn’t input any arguments, they will be able
to play 2048 manually. If the user inputs a string Game Heuristic, they will see the output 
of their Game Heuristic in real-time. Please see **Available Game Heuristics** for current
available Game Heuristics and their applicable spellings.

### Compare Heuristic

In order to test the efficacy of a given Game Heuristic, the user can supply 
the given Game Heuristic, the game tree depth, and the amount of
times they want the application to run the game with the aforementioned qualifiers.

<p align="center">
  <img src="https://i.postimg.cc/7Z6hqL53/Compare-Heuristic.png" width="275"/>
</p>

- Jar File: Game2048Intelligence/target/CompareHeuristics.jar
- Command: ```java -jar <pathToJar>```
- Arguments: The main methods takes three arguments, in a specific order:
    - *Tree Depth* - how many moves the program looks ahead.
    - *Times to Complete* - how many times the program will bring the game to completion.
    - *Game Heuristic* - the given evaluation script.

  The method will throw an IllegalStateException if these arguments are supplied in another order or all three 
  aren't given. **Design Principles** reviews information on Tree Depth, and see **Available Game
  Heuristics** for current available heuristics and their applicable spellings.)

## Create New Game Heuristics
Contributing to Defeat2048 calls for creating new Game Heuristics.

To create a new Game Heuristic, extend the [GameHeuristic](Game2048Intelligence/src/main/java/heuristic/GameHeuristic.java) abstract class. 
Override methods ```evaluateHeuristicScore(KeyEventHandler handler);```
and ```getHeuristicName();```.

Note that initializing a [GameStateTree](Game2048Intelligence/src/main/java/models/GameStateTree.java)
object requires a [GameHeuristic](Game2048Intelligence/src/main/java/heuristic/GameHeuristic.java), which evaluates the nth row of the tree, and each game state 
is handled by a [KeyEventHandler](Game2048Base/src/main/java/models/game/KeyEventHandler.java) object.
The 2048 grid cannot be accessed directly through the handler; you may 
iterate through the primitive rid using ```getSquareByCoordinates(int posnX, int posnY)```.

## Available Game Heuristics
### Prefer Up Heuristic
This Game Heuristic is almost totally deterministic: the heuristic prioritizes 
“up” as its next move; if it cannot, it will randomly assign “left” or “right” 
as its next move; if it cannot, it will assign “down”. This is the only Game 
Heuristic that does not have an evaluation script–and thus does not use a game
tree–due to its pre-destined nature. As such, this Game Heuristic does not perform
incredibly well: when run 50 times, it garners the highest score of 7784 (with the 512 
tile on the board) and has an average score of 4156.

### Snake Heuristic
This Game Heuristic scores each tile by its placement on the grid multiplied
by its value. It prioritizes highest-value tiles in the top left corner, 
snaking downwards thereafter. Starting from the top, the tiles of each row are an order
of magnitude lower in value than their predecessor row. When run 50 times with a game
tree depth of 3, the highest score is 26528 (with the 2048 tile on the board)
and has average score of 11761.


### Snake and Worst Case Heuristic
Like its sibling Snake Heuristic, this Game Heuristic evaluates each game
state by snaking values and devalues a “Worst Case” game state. 
A “Worst Case” game state is when the addition of a random tile would have to 
yield an ensuing down move, as shown below:

<p align="center">
  <img src="https://i.postimg.cc/c4T6q3L1/Worst-Case.png" width="400"/>
</p>

This is the best Game Heuristic built thus far: when run 50 times with 
a 3-depth tree, its highest score is 36244 (with the 2048 tile on the board)
and has an average score of 12313. However, note that the greater the tree 
depth, the more likely “Worst Case” will compromise the evaluation of 
snaking tiles.

## For the future
While Snake Heuristic and Snake and Worst Case Heuristic often defeat the
game, the doors for reaching the 4096 and 8192 tiles are still open.
Previously built Game Heuristics are unable to correct themselves;
i.e, if the highest-value tile is no longer in the top-left corner,
it quickly becomes a dead-end game state. Unlocking this self-correction
might be the route towards higher scores.

## Credits
[Northeastern University Image Library](https://course.ccs.neu.edu/cs2510h/image-doc.html)
