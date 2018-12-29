package IksOks;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	Button[][] btns = new Button[3][3];
	int br = 0;
	String rez = "", rez2 = "";
	
	@Override
	public void start(Stage stage) {
		GridPane gp = napraviPane();
		Scene scene = new Scene(gp);
		
		stage.setScene(scene);
		stage.setTitle("Iks-Oks");
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
	}
	@SuppressWarnings("static-access")
	private GridPane napraviPane() {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(5));
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				btns[i][j] = new Button("");
				btns[i][j].setPrefSize(70, 70);
				btns[i][j].setOnAction(this::akcija);
				
				gp.add(btns[i][j], j, i);
				gp.setMargin(btns[i][j], new Insets(5));
			}
		}
		return gp;
	}

	private void akcija(ActionEvent event) {
		Button btn = (Button) event.getSource();
		if(br++ % 2 == 0) {
			btn.setText("X");
		} else {
			btn.setText("O");
		}
		btn.setDisable(true);
		for(int i = 0; i < 3; i++) {
			rez = ""; rez2 = "";
			for(int j = 0; j < 3; j++) {
				rez += btns[i][j].getText();
				rez2 += btns[j][i].getText();
				if(rez.toLowerCase().equals("xxx") || rez2.toLowerCase().equals("xxx")) imamoPobednika("X");
				if(rez.toLowerCase().equals("ooo") || rez2.toLowerCase().equals("ooo")) imamoPobednika("O");
			}
		}
		rez = ""; rez2 = "";
		for(int i = 0; i < 3; i++) {
			rez += btns[i][i].getText();
			rez2 += btns[2-i][i].getText();
			if(rez.toLowerCase().equals("xxx") || rez2.toLowerCase().equals("xxx")) imamoPobednika("X");
			if(rez.toLowerCase().equals("ooo") || rez2.toLowerCase().equals("ooo")) imamoPobednika("O");
		}
		if(br == 9) nereseno();
	}
	private void imamoPobednika(String winner) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pobednik je " +winner+"! Nova igra?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Cestitamo!!!");
		alert.setHeaderText("Imamo pobednika!");
		Optional<ButtonType> odg = alert.showAndWait();
		if(odg.isPresent() && odg.get() == ButtonType.YES) resetuj();
		else Platform.exit();
	}
	private void nereseno() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Nereseno...Nova igra?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Nereseno!");
		alert.setHeaderText("Nemamo pobednika!");
		Optional<ButtonType> odg = alert.showAndWait();
		if(odg.isPresent() && odg.get() == ButtonType.YES) resetuj();
		else Platform.exit();
	}
	private void resetuj() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				btns[i][j].setText("");
				btns[i][j].setDisable(false);
			}
		}
		br = 0;
		rez = "";
		rez2 = "";
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
