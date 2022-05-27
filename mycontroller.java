package mines;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

//mycontroller is the skeleton of scene builder 
public class mycontroller {
	private int set; // for checking win/lost
	@FXML
	private HBox hb;

	@FXML
	private Button Reset;

	@FXML
	private Label width;

	@FXML
	private Label height;

	@FXML
	private Label mines;

	@FXML
	private TextField widthLabel;

	@FXML
	private TextField heightLabel;

	@FXML
	private TextField minesLabel;

	@FXML
	private StackPane stack;

	@FXML
	private GridPane grid;

	@FXML
	void ButtonReset(ActionEvent event) { // when we select reset button
		Mines m; // creating instance of Mine
		set = 0;
		int width, height, mines;
		stack.getChildren().clear(); // for board clean - a new game
		width = Integer.parseInt(widthLabel.getText()); // get text from TextField and convert to Int
		height = Integer.parseInt(heightLabel.getText()); // get text from TextField and convert to Int
		mines = Integer.parseInt(minesLabel.getText());// get text from TextField and convert to Int
		Button[][] butt = new Button[width][height]; // creating new array Buttons

		GridPane gridNew = new GridPane();
		gridNew.setPrefSize(width, height);
		gridNew.setPrefWidth(width);
		gridNew.setVgap(2);
		m = new Mines(height, width, mines);

		for (int i = 0; i < height; i++) // for creating board
			for (int j = 0; j < width; j++) {
				butt[i][j] = new Button();

				butt[i][j].setMinWidth(60);
				butt[i][j].setMinHeight(60);
				butt[i][j].setText(".");
				butt[i][j].setStyle("-fx-base:black;");
				butt[i][j].setStyle("-fx-font-size: 14;");
				butt[i][j].setStyle("-fx-font-weight: bolder;");
				butt[i][j].prefWidth(20);
				butt[i][j].setPadding(new Insets(20));
				gridNew.add(butt[i][j], j, i); // adding button to a gridPane
			}
		NewMine Newbutton = new NewMine(height, width);
		Newbutton.init(butt);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Button b = butt[i][j];
				butt[i][j].setOnMouseClicked(e -> { //when we clicked on mouse
					if (e.getButton() == MouseButton.PRIMARY) // holding left mouse button
					{
						for (int x = 0; x < height; x++)
							for (int y = 0; y < width; y++) {
								if (b.equals(Newbutton.newbutton[x][y])) { //check if this is the button we clicked
									if (!m.open(x, y)) { //if this mine isn't open
										m.setShowAll(true);
										for (int k = 0; k < height; k++)
											for (int h = 0; h < width; h++) {
												m.open(k, h); //open m
												Newbutton.newbutton[k][h].setText(m.get(k, h)); //show text on board buttons
											}
									}
									for (int k = 0; k < height; k++)
										for (int h = 0; h < width; h++)
											if (m.board[k][h].openS) //if this mine is already open
												Newbutton.newbutton[k][h].setText(m.get(k, h)); //show text on board buttons

									if (m.board[x][y].bomb && set == 0) { //for show message in the end of the game
										showMsg.Display("End Game", "Sorry you lost");
										set++;
									}
									if (m.isDone() && set != 1)
										showMsg.Display("End Game", "Omg, You Just Won!");
								}
							}
					} else { // holding right mouse button
						if (b.getText().equals(".")) { //create a flag
							b.setText("F");
						}

					}

				});
			}
		}
		hb.prefWidth(width);
		stack.getChildren().add(gridNew); 
		stack.getScene().getWindow().sizeToScene();

	}

	private class NewMine { //creating new class for new mine 
		private int Newheight;
		private int Newwidth;
		Button[][] newbutton;

		public NewMine(int height, int width) {
			this.Newheight = height;
			this.Newwidth = width;
		}

		public void init(Button[][] button) {
			this.newbutton = button;
		}
	}

	private static class showMsg { //create new class for window that say if we are winning or lost
		public static void Display(String title, String msg) {
			Stage Window = new Stage();
			;
			Window.initModality(Modality.APPLICATION_MODAL);
			Window.setTitle(title);
			Window.setWidth(250);
			Label label = new Label();
			label.setText(msg);
			label.setAlignment(Pos.CENTER);
			Button button = new Button("Close");
			label.setMaxWidth(Double.MAX_VALUE);
			label.setAlignment(Pos.CENTER);
			button.setPadding(new Insets(10));
			button.setOnMouseClicked(e_ -> {
				Window.hide();
			});
			button.setAlignment(Pos.CENTER);
			GridPane g = new GridPane();
			g.setPadding(new Insets(10));
			g.add(label, 1, 1);
			g.add(button, 2, 10, 2, 1);

			Scene scene = new Scene(g, 70, 70);
			Window.setScene(scene);
			Window.showAndWait();

		}
	}
}