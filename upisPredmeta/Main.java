package upisPredmeta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	Button sacuvaj = new Button("Sacuvaj");
	TextField[] editi = new TextField[4];
	ComboBox<String> cb = new ComboBox<String>();
	BufferedWriter out;
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage stage) {
		BorderPane glavni = new BorderPane();
		glavni.setPadding(new Insets(10));
		
		glavni.setBottom(sacuvaj);
		glavni.setAlignment(sacuvaj, Pos.CENTER);
		
		glavni.setCenter(centralni());
		
		sacuvaj.setOnAction(this::klikni);
		
		Scene scene = new Scene(glavni);
		stage.setScene(scene);
		stage.setTitle("Upis predmeta");
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.show();
	}
	
	@SuppressWarnings("static-access")
	public GridPane centralni() {
		GridPane tabela = new GridPane();
		
		Label[] labele = new Label[5];
		labele[0] = new Label("Naziv predmeta:");
		labele[1] = new Label("Godina:");
		labele[2] = new Label("Bodovi sa predispitnih obaveza:");
		labele[3] = new Label("Broj predispitnih obaveza:");
		labele[4] = new Label("Bodovi sa usmenog:");
		for(Label l : labele) {
			l.setPadding(new Insets(5));
		}
		
		for(int i = 0; i < 4; i++) {
			editi[i] = new TextField();
			editi[i].setPadding(new Insets(5));
			editi[i].setPrefWidth(200);
			tabela.setMargin(editi[i], new Insets(5));
		}
		
		cb.getItems().add("1");
		cb.getItems().add("2");
		cb.getItems().add("3");
		cb.getItems().add("4");
		cb.setPrefWidth(200);
		cb.getSelectionModel().select(0);
		tabela.setMargin(cb, new Insets(5));
		
		tabela.add(labele[0], 0, 0);	tabela.add(editi[0], 1, 0);
		tabela.add(labele[1], 0, 1);	tabela.add(editi[1], 1, 1);
		tabela.add(labele[2], 0, 2); 	tabela.add(editi[2], 1, 2);
		tabela.add(labele[3], 0, 3);	tabela.add(cb, 1, 3);
		tabela.add(labele[4], 0, 4);	tabela.add(editi[3], 1, 4);
		return tabela;
	}

	private void klikni(ActionEvent event) {
		if(prazniEditi()) { 
			alertWarning("Polja ne mogu biti prazna!");
			return;
		}
		try {
			int godina = Integer.parseInt(editi[1].getText()); //proveravanje da li su uneti brojevi...
			int bodoviPre = Integer.parseInt(editi[2].getText());
			int bodoviIspit = Integer.parseInt(editi[3].getText());
			
			File file = new File("D:\\Podaci.txt");//Lokacija negde ge odgovara
			if(!file.exists()) file.createNewFile();
			out = new BufferedWriter(new FileWriter(file, true)); //true for appending
			
			String s = "";
			s += (editi[0].getText() + ", ");
			s += (godina + ", ");
			s += (bodoviPre + ", ");
			s += (cb.getSelectionModel().getSelectedItem() +", ");
			s += (bodoviIspit + "\n");
			out.write(s);

			alertConfirmation("Uspesno uneseno u fajl.");
			
			ocistiPolja();
			
		} catch(Exception e) {
			alertError("Rreska pri upisu podataka u fajl!");
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}
	private void alertWarning(String msg) {
		Alert alert = new Alert(Alert.AlertType.WARNING, msg);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		alert.showAndWait();
	}
	private void alertConfirmation(String msg) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		alert.showAndWait();
	}
	private void alertError(String msg) {
		Alert al = new Alert(Alert.AlertType.WARNING, msg);
		Stage stage2 = (Stage) al.getDialogPane().getScene().getWindow();
		stage2.setAlwaysOnTop(true);
		al.showAndWait();
	}
	private void ocistiPolja() {
		for(TextField e : editi) {
			e.setText("");
		}
		cb.getSelectionModel().select(0);
	}
	
	private boolean prazniEditi() {
		for(TextField e : editi) {
			if(e.getText().equals("")) return true;
		}
		return false;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
