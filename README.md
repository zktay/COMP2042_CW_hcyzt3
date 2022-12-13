# Software Maintenence Nottingham
<ul>
<li>Software Maintenance Assignment  </li>
<li>Name: Tay Ze Kang  </li>
<li>Student ID: 20511540  </li>
</ul>

## How to compile the code to produce the application.
> placeholder  


## Where your Javadoc documentation is stored (the path to the directory).
> placeholder  


## A list of features that are implemented and are working properly.
### New Features
<ul>
<li> Sorted Leaderboard with searching and filter system.  </li>
<li> Podium that shows top 3 for respective game board.  </li>
<li> Color picker that allows user to change background color including gamescene.  </li>
<li> Level selection that consists of "Easy", "Normal", "Hard", "Extreme", "3072", and "Color" that comes with different target cell number to reach. While 3072 will randomly spawn 3 or 6 instead of 2 or 4. Color will spawn color instead of cells with numbers</li>
<li> Board selection that consists of "3x3, 4x4, 5x5, 6x6" which have different target cell's number to reach.  </li>
<li> Help Button to show who to contact if met any problem.  </li>
<li> Menu Bar that redirect user to 3rd party website (youtube) about how to play 2048.  </li>
<li> User will be prompt and asked wheter to continue the game or not if any of their cell reach targeted number accordingly to their board size and difficulty.  </li>
<li> User are required to enter their username before starting the game (input will be validate), username are allowed to be duplicated.  </li>
<li> Every buttons comes with hover effects to improve UX.  </li>
<li> Background music and sound effect while two tiles added together (can be toggle off by user manually in setting page, both default are on)  </li>
<li> endGame and winGame will have their respective sounds effect to celebrate</li>
</ul>

### Improvements to exisitng scenes.
<ul>
<li> New Buttons added to EndGameScene and NewGameScene with respective fucntion (EXIT, RESTART, NEW GAME).  </li>
<li> GameScene will show user's username and their score above their game board.  </li>
<li> A home button added to game board to let user back to main menu during the game, prompt will show to double confirm.  </li>
<li> Border Radius for every cells to improve UX.  </li>
<li> Reallocate the game board, Title, Username and Score to the middle of the screen to improve the UX.  </li>
</ul>

## A list of features that are implemented and are not working properly.
> None  

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
<li> WinGame.java  </li>
</ul>

## A list of Java classes that you modified from the given code base
<ul>
<li> Cell.java  </li>
<li> Endgame.java  </li>
<li> GameScene.java  </li>
<li> Main.java  </li>
<li> TextMaker.java  </li>
</ul>