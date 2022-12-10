

 BUGS FIXED
 Cell still spawning even though user already in illegal move.
 score adding up while user hold down any control key.
 Any keyboard key will trigger cell to spawn.
 2,2,4,4 should return 0,0,4,8 but game return 0,2,2,8 instead.
 Resizing the window will cause the gamescene to misalign.
 Fixed the cell's number will out of the cell's block when the cell's value is over 3 digits.
 
 NEW FEATURE
 Sorted Leaderboard with searching and filter system.
 Podium that shows top 3 for respective game board.
 Color picker that allows user to change background color including gamescene.
 Level selection that consists of "Easy, Normal, Hard, Extreme" that comes with different target cell number to reach.
 Board selection that consists of "3x3, 4x4, 5x5, 6x6" which have different target cell's number to reach.
 Help Button to show who to contact if met any problem.
 Menu Bar that redirect user to 3rd party website (youtube) about how to play 2048.
 User will be prompt and asked wheter to continue the game or not if any of their cell reach targeted number accordingly to their board size and difficulty.
 User are required to enter their username before starting the game (input will be validate).
 Every buttons comes with hover effects to imporve UX.
 
 
 ADDED NEW FUNCTION TO EXISITING SCENE
 New Buttons added to EndGameScene and NewGameScene with respective fucntion (EXIT, RESTART, NEW GAME).
 GameScene will show user's username and their score above their game board.
 A home button added to game board to let user back to main menu during the game, prompt will show to double confirm.
 Border Radius for every cells to improve UX.
 Reallocate the game board, Title, Username and Score to the middle of the screen to improve the UX.