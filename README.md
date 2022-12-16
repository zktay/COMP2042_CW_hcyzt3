# Software Maintenence Nottingham COMP2042
<ul>
<li>Title: Software Maintenance Assignment  </li>
<li>Module: COMP2042 coursework</li>
<li>Name: Tay Ze Kang  </li>
<li>Student ID: 20511540  </li>
</ul>

## How to compile the code to produce the application.
### 1. Pull the code from the link provided below.
```
https://github.com/zktay/COMP2042_CW_hcyzt3.git
```
### 2. Download Java IDE to run the program.
[IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) or [Eclipse](https://www.eclipse.org/downloads/)
### 3. Download JavaJDK and JavaFx (JavaFx already provided in the source file)
[Java JDK 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
or
[Java SDK 19](https://gluonhq.com/products/javafx/) 
### 4. Running the program
Open project in on of the IDE above and locate and run the Main.java.

## Where your Javadoc documentation is stored (the path to the directory).
```
COMP2042TayZe/javadocs
```


## A list of features that are implemented and are working properly.
### New Features
<ul>
<li> Sorted Leaderboard with searching and filter system.  </li>
<li> Podium that shows top 3 for respective game board.  </li>
<li> Color picker that allows user to change background color including gamescene.  </li>
<li> Level selection that consists of "Easy", "Normal", "Hard", "Extreme", "3072", and "Color" that comes with different target cell number to reach. While 3072 will randomly spawn 3 or 6 instead of 2 or 4. Color will spawn color instead of cells with numbers.</li>
<li> Board selection that consists of "3x3, 4x4, 5x5, 6x6" which have different target cell's number to reach.  </li>
<li> Help Button to show who to contact if met any problem.  </li>
<li> Menu Bar that redirect user to 3rd party website about how to play 2048.  </li>
<li> User will be prompt and asked wheter to continue the game or not if any of their cell reach targeted number accordingly to their board size and difficulty.  </li>
<li> User are required to enter their username before starting the game (input will be validate), username are allowed to be duplicated.  </li>
<li> Every buttons comes with hover effects to improve UX.  </li>
<li> Background music and sound effect while two tiles added together (can be toggle off by user manually in setting page, both default are on).  </li>
</ul>

### Improvements to exisitng scenes.
<ul>
<li> New Buttons added to EndGameScene with respective function (EXIT, RESTART, NEW GAME).  </li>
<li> EndGame will have respective sounds effect accordinly to the result.</li>
<li> GameScene will show user's username and their score above their game board.  </li>
<li> A home button added to game board to let user back to main menu during the game, prompt will show to double confirm.  </li>
<li> Border Radius for every cells to improve UX.  </li>
<li> Reallocate the game board, Title, Username and Score to the middle of the screen to improve the UX.  </li>
</ul>

## A list of features, if any, that are not implemented with an explanation of why they were not implemented.

### Time Limit function that limit the user to reach certain cell's value under time given.
<ul>
<li>Due date incoming, time not given enough as adding time limit means need to change the data structure of my database(textfile). That will also affect how the leaderboard and the podium works as both of it need addition filter and show out those result.  </li>
</ul>

### Animation between cells and cells when both cells are adding up together.
<ul>
<li> Technical knowledge limitation and time constraint.  </li>
</ul>

## A list of new Java classes that you introduced for the assignment
<ul>
<li> leaderboard.java  </li>
<li> Scoring.java  </li>
<li> Setting.java  </li>
<li> controller.java </li>
<li> spawn.java </li>
</ul>

## A list of Java classes that you modified from the given code base
<ul>
<li> Cell.java  </li>
<li> Endgame.java  </li>
<li> GameScene.java  </li>
<li> Main.java  </li>
<li> TextMaker.java  </li>
</ul>
