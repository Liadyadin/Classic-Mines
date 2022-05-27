package mines;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MinesFX extends Application {
	@Override
	public void start(Stage stage) {
		HBox hbox; //creating hbox- like in scene Builder 
		mycontroller cntrl=new mycontroller();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("MinesFX.fxml")); //MinesFX --> name of fxml file in scene builder 
			hbox = loader.load();
			
			Scene scene = new Scene(hbox); //creating scene
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("The Amazing Mines sweeper");
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}