
//ESTA CLASSE SELLERLISTCONTROLLER ERA A DEPARTMENTLISTCONTROLLER
//E O PROFESSOR NELIO PEDIU PARA RENOMEAR DEPARTMENT PARA SELLER

package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.DepartmentService;
import model.services.SellerService;

//classe seller list controller q implementa a INTERFACE INITIALIZABLE e
//a DataChangeListener (q fica ouvindo quando algum vendedor e ADD no BD)
public class SellerListController implements Initializable, DataChangeListener {

	// fazendo uma DEPENDENCIA do SELLER SERVICE, para carregar os DADOS
	// q estao cadastrados no SELLER ID NOME... e vamos chamar essa dependencia
	// de SERVICE
	private SellerService service;

	// referencias para a tela de SELLER LIST...
	// temos um BOTAO na TOOLBAR e temos um TABLEVIEW q tem um ID e NAME, etc...
	// referencia para tableview
	@FXML
	private TableView<Seller> tableViewSeller;

	// referencia/atributo para a TABLEVIEW ID
	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	// referencia/ATRIBUTO para a TABLEVIEW NAME
	@FXML
	private TableColumn<Seller, String> tableColumnName;
	
	// referencia/ATRIBUTO para a TABLEVIEW EMAIL
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;
		
	// referencia/ATRIBUTO para a TABLEVIEW BIRTH DATE
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;
		
		
	// referencia/ATRIBUTO para a TABLEVIEW BASE SALARY
	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;
	

	//referencia/ATRIBUTO para a OPERACAO de ATUALIZAR vendedor
	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	// REFERENCIA ou ATRIBUTO para a OPERACAO de DELETAR vendedor
	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

	// referencia para o BOTAO
	@FXML
	private Button btNew;

	// pegar os DADOS Q TA NO SERVICE e mostrar dentro do tableVIEW
	// criando uma LIST do TIPO OBSERVABLE q sera uma LISTA DE SELLER
	// e ira se chamar OBSLIST... dps o OBSERVABLELIST vai ser associado
	// ao tableview...
	private ObservableList<Seller> obsList;

	// metodo de tratamento do botao... Para QUANDO CLICAR NO BOTAO
	// carregar a telinha com os campos para cadastrar um novo produto
	// o actionEvent serve para dizer em QUAL janela foi a q usamos para clicar
	// no botao para CAD um novo departamento
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		// iniciando um formuario de DEPARTMENT vazio, sem NAME e ID preenchidos
		Seller obj = new Seller();
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
	}

	// NAO ENTENDI o codigo a baixo... SEI Q E INJECAO DE DEPENDENCIA...
	// ACHO Q
	public void setSellerService(SellerService service) {
		this.service = service;
	}

	// sobreescrevendo o METODO INITIALIZE q fica dentro da INTERFACE INITIALIZABLE
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// chamando o metodo initializeNodes
		initializeNodes();

	}

	// metodo initialize nodes... para iniciar o comportamento das colunas
	private void initializeNodes() {
		// q inicia apropiadamente as COLUMNS da TABELA... o ID e NAME
		// acessando a TABLECOLUMNID e Pegando os DADOS q estao em ID
		//
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

		// acessando o tableCOLUMNNAME e pegando os dados q estao dentro de NAME
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		// acessando o tableCOLUMNEMAIL e pegando os dados q estao dentro de EMAIL
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
// acessando o tableCOLUMNBIRTHDATE e pegando os dados q estao dentro de birthDate
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		//chamando o metodo formatTableColumnDate e e informando q a data deve
		//ser formatada
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		
		// acessando o tableCOLUMNBASESALARY e pegando os dados q estao dentro de BASESALARY
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		//chamando o metodo formatTableColumnDouble e formantando o salario em
		//2 casas decimais
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
		
		// comando para q a tela fique em tela cheia...
		Stage stage = (Stage) Main.getMainScene().getWindow();
		// comando para a tableviewdepartment acompanhar a janela... e ficar
		// em tela cheia
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
	}
	// metodo q sera responsavel por ACESSAR O SERVICE, carregar os SELLER
	// ou seja o NOME e o ID e jogar esses SELLER na OBSERVABLELIST
	public void updateTableView() {
		// se o service for NULL...
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		// declarando uma LIST de SELLER, q recebe o OBJETO/VARIAVEL SERVICE
		// q � do tipo SELLERSERVICE (o service) dai vamos chamar o METODO
		// FIND ALL para pegar todos os dados q estao na LISTA q ta dentro da class
		// SellerService
		List<Seller> list = service.findAll();
		// chamando o OBLIST e passando para ele a nossa LIST q fizemos ali na
		// linha de cima
		obsList = FXCollections.observableArrayList(list);
		// chamando o TABLEVIEW, e dps chamando o SETITEMS para carregar os
		// views na tela, e passando o OBSLIST
		tableViewSeller.setItems(obsList);
		// chamando o METODO initEditButtons, metodo esse q vai por um botao
		// de EDITAR em cada linha de VENDEDOR no software
		initEditButtons();
		//chamando os botoes de remover para aparecer junto com os de EDITAR
		//na hora de carregar os vendedores
		initRemoveButtons();
	}

	// metodo create dialog form, q recebe uma referencia para o STAGE
	// da JANELA q chamou a JANELINHA DE DIALOGO (aquela para cadastrar os
	// vendedores)
	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
		try {
			// implementando a logica para abrir a JANELINHA de formulario
			// para CAD as pessoas
			//
			// chamando o FXMLLoader para abrir uma tela em FXML
			// e para o FXMLLOADER nos vamos passar o CAMINHO da tela FXML q ta
			// na VAR/atributo AbsoluteName (que � o NOME DA VIEW/TELA) q sera
			// carregada
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			// chamando o painel PANE para carregar a tela (no caso � a caixinha
			// para digitar os novos departamentos)
			Pane pane = loader.load();

			// pegando uma referencia para o CONTROLLADOR, o controllador da tela
			// q carregamos acima q � o formuladio
			SellerFormController controller = loader.getController();
			// INJETANDO no controlador o VENDEDOR, chamando o METODO
			// SETSELLER q ta na class SellerFormController e passando
			// o SELLER/OBJ como valor do argumento ENTITY
			controller.setSeller(obj);
			// injetando o seller service, passando um novo VENDEDOR/sellerService
			//e passando um novo DEPARTMENT em q esse VENDEDOR esta associado
			//em q esse vededor trabalha
			controller.setServices(new SellerService(), new DepartmentService());
			//chamando o metodo loadAssociatedObjects para q ao clicar no 
			//CAMPO vamos carregar os departamentos q estao cadastrado no BANCO
			controller.loadAssociatedObjects();

			// colocando ESTA CLASSE SELLERLISTCONTROLLER para receber a
			// notificacao de quando FOR feita UMA ATUALIZACAO/modificacao no
			// BANCO... Dai dessa forma vamos acionar o METODO ONDATACHANGED
			// q ta ali em baixo e pedir para ele RECARREGAR a PAG de VENDEDORES
			controller.subscribeDataChangeListener(this);

			// carregando os dados do objeto/departamento no formulario
			controller.updateFormData();

			// criando um cenario/stage novo
			Stage dialogStage = new Stage();
			// informando q vai ser um DIALOG STAGE ou seja... q vai ser uma
			// janela pequena q ira aparecer o formulario para add um novo
			// departamento
			dialogStage.setTitle("Enter Seller data");
			// informando q o PANE/PAINEL vai ser o elemento RAIZ da CENA
			dialogStage.setScene(new Scene(pane));
			// informando q nao podemos mudar o tamanho da janela
			dialogStage.setResizable(false);
			// informando qual o stage/cenario � o PAI da janela
			dialogStage.initOwner(parentStage);
			// informando q a janela vai ser MODAL ou SEJA vai ficar TRAVADA
			// e enuanto nao fechar ela nao pode mexer na janela q ta de baixo
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		// em caso de erro...
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	// esse metodo e executado quando recebe uma NOTIFICAO q os dados foram
	// alterados... ou SEJA q foi ADD um departamento novo no BANCO
	@Override
	public void onDataChanged() {
		// e ele ATUALIZA a PAG de VENDEDORES no SOFTWARE :)
		updateTableView();
	}

	// criando o metodo INITEDITBUTTONS q serve para criar um BOTAO de EDICAO em
	// cada LINHA da tabela... quando carregar os VENDEDORES do lado
	// de cada um tem um botao editar :)

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		// criando um OBJ q vai instanciar os BOTOES(editar) e configurar o
		// eventos do botao
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				// desenhando o botao, chamando um evento no CreateDialogForm para criar a
				// janelinha
				// chamando o OBJ q � o vendedor da LINHA q ta o botao de EDITAR q eu clicar
				// e quando clicar para editar chama a telinha de cadastro para podermos editar
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	// criando o metodo initRemoveButtons, q SERVE para CRIAR um BOTAO DELETAR do
	// LADO do
	// botao EDIT... Esse botao vai ficar na LINHA de CADA vendedor para
	// podermos
	// deletar e editar
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				// para CADA botao... Se for abertado vamos chamar o METODO REMOVEENTITY e
				// passar
				// o OBJ que � o ID do vendedor q queremos deletar
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		//chamando o metodo SHOWCONFIRMATION q ta na class ALERTS
		//e passando a informacao de pergunta se quer deletar
		//o resultado do SIM ou NAO vamos salvar na VAR RESULT
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
	
		//verificnado SE o valor q ta na VAR RESULT � um OK ou um CANCEL
		if(result.get() == ButtonType.OK) {
			//TRATAMENTO DE EXCESSAO... para caso o service esteja como null
			if(service ==  null) {
				throw new IllegalStateException("service was null");
			}
			try {
			//SE a pessoa clicou em OK entao pedindo para deletar
			service.remove(obj);
			//chamando o metodo updateTableView para atualizar os dados
			//da TABELA/LISTA de vendedor no software
			updateTableView();
			}
			//SE der erro na hora de deletar
			catch(DbIntegrityException e) {
				e.printStackTrace();
				//chamando um alerts
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
			}
	}
}
