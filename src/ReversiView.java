import java.util.Observer;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.layout.TilePane;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

/**
 * This class is implement the view of the game, it mainly use the javafx to
 * complete the function and show the game board, it also show the step of
 * player's move and computer's move also it show the important information
 * 
 * @author Wenkai Zheng
 */
public class ReversiView extends Application implements Observer {

  private TilePane gridTitlePane = new TilePane();
  ReversiModel model = new ReversiModel();
  Label label = new Label("white: 0,black: 0");
  ReversiController controller = new ReversiController(model);
  ReversiBoard UIBoard = new ReversiBoard();
  ObjectOutputStream output;
  // ObjectInputStream input;
  Socket clientConnection;
  char SC = '?'; // 'S' means server, 'C' means client '?' means local game
  boolean HorC = true; // true means human false means computer
  Thread socketAccepter;
  boolean killThread = true;
  Socket socket;
  ServerSocket serverSocket;

  /**
	 * simply constructor of ReversiView
	 */
  public ReversiView() {
    model.ReversiModelReset();
    model.addObserver(this);
  }

  /**
	 * main function purpose: to lunch the javafx and the stage
	 * 
	 * @param args form the input string
	 */
  public static void main(String[] args) {

    launch(args);
  }

  /**
	 * method name: remove_all purpose: remove all of the event handle we set in the
	 * pane
	 * 
	 */
  public void remove_all() {
    for (int i = 0; i < gridTitlePane.getChildren().size(); i++) {
      StackPane border = (StackPane) gridTitlePane.getChildren().get(i);
      border.setOnMouseClicked(null);
    }
  }

  /**
	 * method name: update purpose: Observe the change in the model, once it
	 * changed, show the change in the board
	 * 
	 * @param o   the Observable pass from the model
	 * @param arg the information object
	 * 
	 */
  @Override
  public void update(Observable o, Object arg) {

    information i = (information) arg;
    System.out.println("receive from model " + i.name);
    StackPane clicked_one = (StackPane)(gridTitlePane.lookup("#" + i.name));
    System.out.println(clicked_one.getId());
    Circle child = (Circle) clicked_one.getChildren().get(0);
    if (i.color == 0) child.setFill(Color.WHITE);
    else if (i.color == 1) child.setFill(Color.BLACK);
  }

  /**
	 * method name: display purpose: once the game is end, show the important
	 * message if human win, print "You Won" else print "You Lost"
	 * 
	 */
  public void display() {

    System.out.println("The game is over");

    File file = new File("save_game.dat");
    try {
      file = file.getCanonicalFile();
      file.delete();
    } catch(IOException e) {}
    if (controller.white_score() > controller.black_score()) {
      if (SC == '?' || SC == 'S') new Alert(AlertType.INFORMATION, "You Won").showAndWait();

      else new Alert(AlertType.INFORMATION, "You Lost").showAndWait();

    } else {
      if (SC == '?' || SC == 'S') new Alert(AlertType.INFORMATION, "You Lost").showAndWait();

      else new Alert(AlertType.INFORMATION, "You Won").showAndWait();

    }

  }

  /**
	 * method name:set_pane purpose: after receive the board information from other
	 * side reset the pane show in the UI
	 */
  public void set_pane() {
    int count = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        StackPane border = (StackPane) gridTitlePane.getChildren().get(count);
        Circle temp = (Circle) border.getChildren().get(0);
        if (model.get_chess(i, j + 1) == 'W') {
          temp.setFill(Color.WHITE);
        } else if (model.get_chess(i, j + 1) == 'B') {
          temp.setFill(Color.BLACK);
        } else temp.setFill(Color.TRANSPARENT);
        count++;
      }
    }
  }

  /**
	 * method name: sendModel use the network to send the board information
	 * 
	 */
  public void sendModel() {
    try {

      if (output != null) {
        System.out.println("Server send in here");
        UIBoard.set(model.getPicture());
        output.writeObject(UIBoard);
        gridTitlePane.setDisable(true);
      } else {
        System.out.println("Server send in here not");
      }

    } catch(IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
	 * method name: set_handle purpose: set the 64 StackPane inside of title pane
	 * and each StackPane has a child called circle and each StackPane set a
	 * EventHandler of mouse, once it clicked, make the correspond move if human
	 * win, print "You Won" else print "You Lost"
	 * 
	 */
  public void set_handle() {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {

        Circle circle = new Circle(i, j, 20);
        StackPane border = new StackPane();

        if (i == 3 && j == 3 || i == 4 && j == 4) circle.setFill(Color.WHITE);
        else if (i == 3 && j == 4 || i == 4 && j == 3) circle.setFill(Color.BLACK);
        else circle.setFill(Color.TRANSPARENT);
        border.setPadding(new Insets(2, 2, 2, 2));
        border.setMinWidth(20);
        border.setMinHeight(20);
        border.setStyle("-fx-border-color: black;");
        border.getChildren().add(circle);

        String coordinate = new String("");
        char x = (char)('a' + j - 0);
        char y = (char)('1' + i - 0);
        coordinate += x;
        coordinate += y;
        border.setId(coordinate);

        border.setOnMouseClicked(new EventHandler < MouseEvent > () {
          public void handle(MouseEvent me) {
            System.out.println(border.getId());
            try {

              controller.check_input(border.getId());
              sendModel();
              if (SC == '?') controller.computerTurn();

              label.setText("The score for white " + controller.white_score() + " The score for black " + controller.black_score());
              if (controller.checkAllPossible(0) == 1 && controller.checkAllPossible(1) == 1) {

                display();
                System.out.println("moseclick message display");
                if (SC == '?') remove_all();
                else {
                  gridTitlePane.setDisable(false);
                  //killThread = true;
                }

              }
            } catch(InvalidMoveException e) {
              int color = 0; // 0 means white, 1 means white
              if (SC == 'C') color = 1; // client color is black
              System.out.println("This is not valid move");
              if (controller.checkAllPossible(color) == 1) {
                if (SC == '?') controller.computerTurn();
                label.setText("The score for white " + controller.white_score() + " The score for black " + controller.black_score());
                if (controller.checkAllPossible(0) == 1 && controller.checkAllPossible(1) == 1) {
                  display();
                  System.out.println("invalid move message display");
                  remove_all();

                  // return;
                }

                if (SC != '?') sendModel();
              }
            } catch(InvalidInputException e) {
              System.out.println("This is not valid move");
            }
          }
        });
        gridTitlePane.getChildren().add(border);
      }
    }
  }

  /**
	 * method name: start purpose: the javafx stage and set the mainly use parameter
	 * and set each handle initialize most of function and parameter. also add a
	 * Handler which new the game, delete the saved data also when close the window,
	 * save the game data and when run it again, load the data and continue in
	 * project6, we add the Networked Reversi. inside the UI add a new menu item
	 * which can make one become the server Another become the client so they can
	 * play against each other server ro client could be both computer or human
	 */
  @Override
  // override the start and stop methods in application and add shaperepl
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Reversi-Server");
    gridTitlePane.setPadding(new Insets(8, 8, 8, 8));
    gridTitlePane.setStyle("-fx-background-color: #008000");
    Menu file = new Menu("File");
    MenuBar menuBar = new MenuBar();
    MenuItem item1 = new MenuItem("new game");
    MenuItem item2 = new MenuItem("Networked game");
    item2.setOnAction(new EventHandler < ActionEvent > () {
      public void handle(ActionEvent me) {
        dialog d = new dialog();
        d.initModality(Modality.APPLICATION_MODAL);
        d.make_stage(new EventHandler < MouseEvent > () {@SuppressWarnings({
            "resource",
            "unused"
          })@Override
          public void handle(MouseEvent arg) {
            Node source = (Node) arg.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            Scene parent = ((Button) arg.getSource()).getScene();
            // System.out.println((TextField) parent.lookup("ServerAddrTextBox"));
            String serverAddr = ((TextField) parent.lookup("#ServerAddrTextBox")).getText();
            String serverPort = ((TextField) parent.lookup("#ServerPortTextBox")).getText();
            RadioButton rb1 = (RadioButton)(parent.lookup("#Serverbutton"));
            RadioButton rb2 = (RadioButton)(parent.lookup("#Clientbutton"));
            RadioButton rb3 = (RadioButton)(parent.lookup("#Humanbutton"));
            RadioButton rb4 = (RadioButton)(parent.lookup("#Computerbutton"));
            stage.close();
            if (rb1.isSelected()) SC = 'S';
            if (rb2.isSelected()) SC = 'C';
            if (rb3.isSelected()) HorC = true;
            if (rb4.isSelected()) HorC = false;
            if (SC == 'S' && HorC) controller.HumC = 'W';
            if (SC == 'C' && HorC) controller.HumC = 'B';
            if (SC == 'S' && !HorC) controller.ComC = 'W';
            if (SC == 'C' && !HorC) controller.ComC = 'B';
            socketAccepter = new Thread() {
              public void run() {
                // Socket socket;
                // ServerSocket serverSocket;
                try {
                  if (SC == 'S') {
                    serverSocket = new ServerSocket(Integer.parseInt(serverPort));
                    socket = serverSocket.accept();
                  } else if (SC == 'C') {
                    socket = new Socket(serverAddr, Integer.parseInt(serverPort));
                    serverSocket = null;
                    gridTitlePane.setDisable(true);

                  } else {
                    System.out.println("Hi");
                    socket = null;
                    serverSocket = null;
                  }
                  Platform.runLater(() ->{
                    System.out.println(serverAddr + " " + serverPort + " " + SC + " " + HorC);
                  });

                  ObjectInputStream input;

                  if (SC == 'S') {
                    input = new ObjectInputStream(socket.getInputStream());
                    Platform.runLater(() ->{
                      try {
                        output = new ObjectOutputStream(socket.getOutputStream());
                      } catch(IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                      }
                      if (!HorC) // Horc true
                      {
                        controller.computerTurn();
                        sendModel();
                        System.out.println("server out put has been set");
                      }
                    });
                  } else if (SC == 'C') {
                    Platform.runLater(() ->{
                      try {
                        output = new ObjectOutputStream(socket.getOutputStream());
                      } catch(IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                      }
                      // System.out.println("out put has been set");
                    });
                    input = new ObjectInputStream(socket.getInputStream());
                  } else input = null;
                  while (killThread) {

                    UIBoard = (ReversiBoard) input.readObject();
                    // System.out.println("read successful");
                    Platform.runLater(() ->{
                      gridTitlePane.setDisable(false);
                      model.setPicture(UIBoard.get());
                      set_pane();
                      label.setText("The score for white " + controller.white_score() + " The score for black " + controller.black_score());
                      // if (controller.checkAllPossible(0) == 1) {
                      // white
                      if (SC == 'S' && controller.checkAllPossible(0) == 1 && controller.checkAllPossible(1) != 1) {
                        sendModel();
                        return;

                      }
                      if (SC == 'C' && controller.checkAllPossible(1) == 1 && controller.checkAllPossible(0) != 1) {

                        sendModel();
                        return;

                      }
                      if (controller.checkAllPossible(0) == 1 && controller.checkAllPossible(1) == 1) {
                        System.out.println("get input game over");
                        // no necessary
                        if (killThread) {
                          display();
                          remove_all();
                          // sendModel();
                        }
                        killThread = false;
                        // System.exit(0);
                        // socketAccepter.stop();
                        if (serverSocket != null) try {
                          serverSocket.close();
                        } catch(IOException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                        }
                        if (socket != null) try {
                          socket.close();
                        } catch(IOException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                        }
                        Platform.runLater(() ->output = null);

                        //killThread = true;
                        gridTitlePane.setDisable(false);
                        set_pane();

                        return;

                      }

                      if (!HorC && killThread) // Horc true
                      {
                        controller.computerTurn();
                        label.setText("The score for white " + controller.white_score() + " The score for black " + controller.black_score());
                        //	sendModel();
                        // no necessary
                        if (killThread) sendModel();
                        if (controller.checkAllPossible(0) == 1 && controller.checkAllPossible(1) == 1) {
                          System.out.println("computer send game over");
                          if (killThread) {
                            display();
                            remove_all();
                          }
                          killThread = false;

                          if (serverSocket != null) try {
                            serverSocket.close();
                          } catch(IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                          }
                          if (socket != null) try {
                            socket.close();
                          } catch(IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                          }
                          Platform.runLater(() ->output = null);

                          //killThread = true;
                          gridTitlePane.setDisable(false);
                          set_pane();
                          return;
                        }

                      }

                    });
                  }
                  // killThread=true;
                  // gridTitlePane.setDisable(false);
                } catch(NumberFormatException | IOException | ClassNotFoundException e) {
                  if (serverSocket != null) try {
                    serverSocket.close();
                  } catch(IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                  }
                  if (socket != null) try {
                    socket.close();
                  } catch(IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                  }
                  Platform.runLater(() ->output = null);

                  //killThread = true;
                  gridTitlePane.setDisable(false);
                  set_pane();
                  return;
                  // e.printStackTrace();
                }
              }

            };
            socketAccepter.start();
          }
        });
      }
    });
    item1.setOnAction(new EventHandler < ActionEvent > () {

      public void handle(ActionEvent me) {
        File file = new File("save_game.dat");
        killThread = true;
        SC = '?';
        HorC = true;
        controller.HumC = '?';
        controller.ComC = '?';
        try {
          file = file.getCanonicalFile();
          System.out.println("We delete in here " + file.delete());
        } catch(IOException e) {}
        model.ReversiModelReset();
        label.setText("The score for white " + controller.white_score() + " The score for black " + controller.black_score());
        gridTitlePane.getChildren().clear();
        set_handle();
      }
    });
    file.getItems().add(item1);
    file.getItems().add(item2);
    menuBar.getMenus().add(file);
    set_handle();

    /*
		 * 
		 * load the data
		 * 
		 */
    if (model.load("save_game.dat")) {
      set_pane();
      label.setText("The score for white " + controller.white_score() + " The score for black " + controller.black_score());
    }
    primaryStage.setOnCloseRequest(new EventHandler < WindowEvent > () {
      public void handle(WindowEvent event) {
        if (controller.checkAllPossible(0) == 1 && controller.checkAllPossible(1) == 1);
        else model.save("save_game.dat");
      }
    });
    VBox vbox = new VBox(menuBar);
    vbox.getChildren().add(gridTitlePane);
    vbox.getChildren().add(label);
    // vbox.(label);
    Scene scene = new Scene(vbox, 374, 416);
    primaryStage.setScene(scene);
    label.setText("The score for white " + controller.white_score() + " The score for black " + controller.black_score());
    primaryStage.setResizable(false);
    primaryStage.show();

  }
}
