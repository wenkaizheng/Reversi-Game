#Reversi Game with two players or two computers or 1 user 1 computer
 You can connect with others by using their IP and port as a client
 Server just need open that port before you connect 
Main Board
 Game board will be a TilePane of 8 rows and 8 columns. The Inset will be 8 pixels. Each element of the board will be a StackPane which contains a Circle shape with a 20px radius and initially a transparent fill color. The background of the TilePane will be filled with green. Each StackPane will have a thin black border around it.

Beneath the TilePane, use a Label to display the current score.

Event Handling
You will add a MouseClick handler to the pane. You can click anywhere to place your piece. That means you’ll need to get the X and Y coordinate of the click event and turn it into a row and column number. You will then call into your controller to make the desired move.

MVC and Observer/Observable
When the controller changes the model, we’d like to change the view. The model can do this for us, by notifying us when the model changes if our view is an Observer and the model is an Observable.

Have your model class extend java.util.Observable and have your view class implement java.util.Observer. As a requirement to the Observer interface, you will need to implement the update(Observable o, Object arg) method in the View.

Make a ReversiBoard class to encapsulate the board independent of the underlying Model. Construct an instance of this class in the model when a move is made and pass that to update() via the Object arg parameter. That argument is set by the Observable model calling setChanged(); and notifyObservers(Object arg). Whatever we pass to notifyObservers() will become the arg parameter of update().

Note: As of Java 9, Observer and Observable have been deprecated. The reasons for this deprecation aren’t germane to us, and there is no real “better choice”. So we will stick with it as it is useful. If you’re using Java 10 or later and want to have Eclipse stop yelling at you, you can use @SuppressWarnings("deprecation") above your Model class.

Saving and Loading
The ReversiBoard class (and only the ReversiBoard class) should be Serializable.

When the program loads, look for a file named “save_game.dat”. If it exists, load it into your model and show its state on the view. If it doesn’t exist, assume it is a new game and construct a new default ReversiModel.

When the windows closes (setOnCloseRequest), handle the WindowClosing event and save the current game to “save_game.dat” by writing out the serialized ReversiBoard class. Do NOT use anything other than ObjectInputStream and ObjectOutputStream to implement loading and saving.

Add a menu (pictured above) to the top pane of the application. Your MenuBar should contain a “File” Menu with one MenuItem for “New Game”. When an action is performed on the MenuItem, construct a new model, update the view, and delete the “save_game.dat” file if it exists.

When the game is won or lost, delete “save_game.dat” if it exists.



