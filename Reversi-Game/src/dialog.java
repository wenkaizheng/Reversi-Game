import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class dialog extends Stage {
	public boolean isServer = false;
	RadioButton rb1;
	RadioButton rb2;

	public void make_stage(EventHandler<MouseEvent> e) {
		this.setTitle("Network Setup");
		HBox hbox0 = new HBox();
		VBox vbox = new VBox();
		vbox.setSpacing(20.0);
		HBox hbox1 = new HBox();
		// hbox1.setSpacing(10.0);
		final ToggleGroup group = new ToggleGroup();
		rb1 = new RadioButton();
		rb2 = new RadioButton();

		Text create = new Text("   Create:  ");
		Text server = new Text("Server  ");
		Text clint = new Text("Client");
		rb1.setId("Serverbutton");
		rb2.setId("Clientbutton");
		rb1.setToggleGroup(group);
		rb2.setToggleGroup(group);
		hbox1.getChildren().add(create);
		hbox1.getChildren().add(rb1);
		hbox1.getChildren().add(server);
		hbox1.getChildren().add(rb2);
		hbox1.getChildren().add(clint);

		HBox hbox2 = new HBox();
		final ToggleGroup group2 = new ToggleGroup();
		RadioButton rb3 = new RadioButton();
		RadioButton rb4 = new RadioButton();
		rb3.setId("Humanbutton");
		rb4.setId("Computerbutton");
		Text play = new Text("   Play as:  ");
		Text human = new Text("Human   ");
		Text computer = new Text("Computer ");
		rb3.setToggleGroup(group2);
		rb4.setToggleGroup(group2);
		hbox2.getChildren().add(play);
		hbox2.getChildren().add(rb3);
		hbox2.getChildren().add(human);
		hbox2.getChildren().add(rb4);
		hbox2.getChildren().add(computer);

		HBox hbox3 = new HBox();
		Text servers = new Text("   Server ");
		Text Port = new Text("  Port ");
		TextField Servers = new TextField();
		Servers.setId("ServerAddrTextBox");
		Servers.setText("localhost");
		TextField Ports = new TextField();
		Ports.setId("ServerPortTextBox");
		Ports.setText("4000");
		// Text computer = new Text("Computer ");

		hbox3.getChildren().add(servers);
		hbox3.getChildren().add(Servers);
		hbox3.getChildren().add(Port);
		hbox3.getChildren().add(Ports);

		HBox hbox4 = new HBox();
		Button ok = new Button("OK");
		Button cancel = new Button("Cancel");
		cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) { 
            	Node  source = (Node)  me.getSource(); 
                Stage stage  = (Stage) source.getScene().getWindow();
                stage.close();
            }
		});
         
		Text space = new Text("  ");
		Text spaces = new Text("  ");
		hbox4.getChildren().add(spaces);
		hbox4.getChildren().add(ok);
		hbox4.getChildren().add(space);
		hbox4.getChildren().add(cancel);

		ok.setOnMouseClicked(e);

		vbox.getChildren().add(hbox0);
		vbox.getChildren().add(hbox1);
		vbox.getChildren().add(hbox2);
		vbox.getChildren().add(hbox3);
		vbox.getChildren().add(hbox4);

		Scene scene = new Scene(vbox, 400, 200);

		this.setScene(scene);
		this.showAndWait();
	}

}