package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

//classe q controla o nosso MAINVIEW.FXML... essa classe vai IMPLEMENTAR a
//INTERFACE INITIALIZABLE
public class MainViewController implements Initializable {

	// itens de controle de tela. q correspondem ao menu do GUI...

	// criando uma VARIAVEL do TIPO MENUITEM chamada MENUTIEMSELLER
	@FXML
	private MenuItem menuItemSeller;

	// criando uma VARIAVEL do TIPO MENUITEM chamada MENUTIEMDepartment
	@FXML
	private MenuItem menuItemDepartment;

	// criando uma VARIAVEL do TIPO MENUITEM chamada MENUTIEMAbout
	@FXML
	private MenuItem menuItemAbout;

	// a baixo, vamos declarar OS METODOS para REALIZAR AS ACOES dos ITENS
	// de MENU (MENUITEM) declarados acima... ali como VARIAVEIS...

	// metodo para realizar as acoes do MenuItemSeller
	// OBS O CONTROLLER É O SELLERLISTCONTROLLER.JAVA
	@FXML
	public void onMenuItemSellerAction() {
		// o LOADVIEW chama o DESIGN em SellerList e chama o parametro q
		// q uma FUNCAO para INICIALIZAR o controlador em forma de expressao LAMBDA
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			// expressao lambda q chama o controller dps chamamos
			// o metodo setSellerService e DPS para esse SELLERSERVICE nos
			// passamos um NEW/NOVO SellerService(classe) q inicia uma LISTA
			// com alguns ID e NOME de departamento cadastrados
			controller.setSellerService(new SellerService());
			// atualizando e fazendo as informacoes aparecer na tela
			// chamando o metodo UPDATETableView q ta na CLASS
			// seller List controller
			controller.updateTableView();
		});
	}

	// metodo para realizar as acoes do MenuItemDEPARTMENT
	//
	// OBS O CONTROLLER É O DEPARTMENTLISTCONTROLLER.JAVA
	@FXML
	public void onMenuItemDepartmentAction() {
		// o LOADVIEW chama o DESIGN em DepartmentList e chama o parametro q
		// q uma FUNCAO para INICIALIZAR o controlador em forma de expressao LAMBDA
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			// expressao lambda q chama o controller dps chamamos
			// o metodo setDepartmentService e DPS para esse DEPARTMENTSERVICE nos
			// passamos um NEW/NOVO DepartmentService(classe) q inicia uma LISTA
			// com alguns ID e NOME de departamento cadastrados
			controller.setDepartmentService(new DepartmentService());
			// atualizando e fazendo as informacoes aparecer na tela
			// chamando o metodo UPDATETableView q ta na CLASS
			// deparment List controller
			controller.updateTableView();
		});
	}

	// metodo para realizar as acoes do MenuItemABOUT
	@FXML
	public void onMenuItemAboutAction() {
		// passando para o LOADVIEW o local q esta o DESIGN da tela do ABOUT
		// no caso o ABOUT.FXML e passando uma funcao de INICIALIZACAO
		// q no caso é uma funcao Q NAO VAI LEVAR EM NADA... (prof usou essas
		// palavras...) pois a PAG ABOUT NAO precisa de funcao de inicializacao
		loadView("/gui/About.fxml", x -> {
		});
	}

	// implementando o metodo initialize
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
	}

	// criando uma funcao para abrir outra tela... em q o ABSOLUTNAME
	// vai receber o caminho de onde ta a outra tela, em FXML :)
	// e com o CONSUMER para acresentar a declaracao do parametro LAMBDA
	// a funcao LOADVIEW é uma funcao GENERICA, uma funcao do TIPO T...
	// e EU ACHO Q esse tipo T, pode ser um ABOUT, SELLER, DEPARTMENT, etc...
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {

		// bloco TRY
		try {

			// chamando o FXMLLoader para abrir uma tela em FXML
			// e para o FXMLLOADER nos vamos passar o CAMINHO da tela FXML q ta
			// na VAR/atributo AbsoluteName
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			// a var NEWVBOX do tipo VBOX recebe o FXML q ta na VAR LOADER, feita acima
			VBox newVBox = loader.load();

			// mostrando a VIEW nova q foi carregada dentro da janela principal
			// pegando qual a JANELA q ja ta aparecendo se e seller, about, etc...
			// pegando a referencia da SCENA/cena q a JANELA ta, chamando o METODO
			// getMAINSCENE q ta dentro da class MAIN
			Scene mainScene = Main.getMainScene();
			// pegando a referencia para os FILHOS<children> da JANELA PRINCIPAL
			// o VBOX do MainView.fxml, para saber se ta no registration ou help...
			// o metodo getroot pega o primeiro elemento da VIEW no caso o
			// scrollpane... e dentro do SCROLLPANE no acessamos o .CONTET
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			// guardando uma referencia para o menu... no caso estamos pegando o
			// PRIMEIRO FIlho do VBOX da janela principal no caso e o MAINMENU
			Node mainMenu = mainVBox.getChildren().get(0);
			// chamando o MAINVBOX feito ali em cima, e limpando todos os filhos
			// dele
			mainVBox.getChildren().clear();
			// agora vamos ADD o MAINMENU (eu ACHO q é aquela barrinha de MENUS
			// q fica na parte de cima do software)
			mainVBox.getChildren().add(mainMenu);
			// adicionando os filhos do NEWVBOX... ou seja o filhos da janela q
			// estamos abrindo
			mainVBox.getChildren().addAll(newVBox.getChildren());

			// depois de carregar a janela acima, para ativar a funcao q
			// foi passada em Consumer <T> initializingAction

			T controller = loader.getController();
			// executando a acao q ta em INITIALINGACTIOn
			initializingAction.accept(controller);
		}
		// tratando a excecao
		catch (IOException e) {
			// chamando a classe ALERTS(q é utilizada para caso de excessao)
			Alerts.showAlert("IO Exception", "error loading view", e.getMessage(), AlertType.ERROR);

		}
	}
}