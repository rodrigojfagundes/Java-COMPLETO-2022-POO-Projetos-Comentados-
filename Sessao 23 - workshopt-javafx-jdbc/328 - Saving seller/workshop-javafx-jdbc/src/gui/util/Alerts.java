package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

//criando a classe alerts
public class Alerts {
	
	//metodo show alerts q recebe alguns atributos como title, header, contentet
	//etc..
	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
	//metodo SHOWCONFIRMATION, vai perguntar se o USUARIO realmente quer DELETAR o DEPARTAMENTO
	//com um botao OPICIONAL de SIM e NAO
	public static Optional<ButtonType> showConfirmation(String title, String content) { 
	 Alert alert = new Alert(AlertType.CONFIRMATION); 
	alert.setTitle(title); 
	alert.setHeaderText(null); 
	alert.setContentText(content); 
	return alert.showAndWait(); 
	}
	
	
}