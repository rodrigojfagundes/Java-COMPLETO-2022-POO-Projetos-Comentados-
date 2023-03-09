//
//	A BASE DESTA CLASSE É A COPIA DA CLASSE DEPARTAMENTFORMCONTROLLER
//

package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

//class SellerFormController q IMPLEMENTA uma INTERFACE initializable
public class SellerFormController implements Initializable {

	// crianod uma dependencia para o vendedor(classe SELLER) chamada de ENTITY
	private Seller entity;

	// criando uma depedencia de SellertService, chamada de SERVICE
	private SellerService service;

	// definindo um atributo/variavel q vai RECEBER um DEPARTMENT q vai
	// ser o DEPARTMENT q o SELLER/vendedor TRABALHA
	private DepartmentService departmentService;

	// criando uma LISTA da INTERFACE DataChangeListener...
	// q permite q outros obj se inscrevam na lista e recebam o evento
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	// declarando os componentes da TELA de mensagem... Aquela q quando clica
	// no botao NEW chama ela... a TELA para CADASTRAR departamento
	// declarando os atributos...
	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private DatePicker dpBirthDate;

	@FXML
	private TextField txtBaseSalary;

	// criando um atributo do tipo comboBox
	// para escolhermos os DEPARTAMENTOS em q o SELLER pode TRABALHAR
	@FXML
	private ComboBox<Department> comboBoxDepartment;

	@FXML
	private Label labelErrorName;

	@FXML
	private Label labelErrorEmail;

	@FXML
	private Label labelErrorBirthDate;

	@FXML
	private Label labelErrorBaseSalary;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	// declarando a nossa LISTA de COISAS OBSERVAVEIS e passando para ela
	// os departamentos
	private ObservableList<Department> obsList;

	// implementando o SET para fazer a INJECAO de DEPENCIAS do ENTITIY do tipo
	// department
	// quando chamarmos o METODO SETSELLER nos vamos passar um SELLER
	// para ele q será ARMAZENADO na VARIAVEL/dependencia ENTITY q criamos la
	// em cima... Nao passarmos o valor direto para ela, pois ela ta em PRIVATE
	public void setSeller(Seller entity) {
		this.entity = entity;
	}

	// criando um metodo SETSERVICES, pois agora ele vai INJETAR os 2 valores
	// q ele receber nos ATRIBUTOS DECLARADOS LA EM CIMA, o SELLERSERVICE e o
	// DEPARTMENTSERVICE... esses valores vao ser injetados quando nos chamarmos
	// o SETSERVICE a partir de outra classe e passar esses 2 valores :)
	public void setServices(SellerService service, DepartmentService departmentService) {
		// passando o VALOR recebido em SERVICE que é do TIPO SELLERSERVICE(classe)
		// e passando esse valor para a DEPENDENCIA/variavel (service)q criamos la em
		// cima
		this.service = service;
		// fazendo o mesmo processo mas injetando o service do Department
		// ou seja injetando o nome do DEPARTMENTO q recebemos ali como argumento
		// e passando para o ATRIBUTO/variavel la de cima
		this.departmentService = departmentService;
	}

	// disponibilizam o metodo para q os objetos possam se inscrever no no
	// DataChangeListener... e ASSIM vamos ADD o LISTENER recebido na LISTA
	public void subscribeDataChangeListener(DataChangeListener listener) {
		// ou seja vamos add ele na lista
		dataChangeListeners.add(listener);
	}

	// metodo para realizar a acao do botao salvar
	@FXML
	public void ontBtSaveAction(ActionEvent event) {
		// testando se o ENTITY(seller) esta valendo NULL
		if (entity == null) {
			throw new IllegalStateException("entity wall null");
		}
		// verificando se o SERVICE esta valendo null... Ou seja se esqueceu
		// de injetar o service...
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		try {
			// salvando o vendedor no BD
			// instanciar um vendedor e salvar esse vendedor no BD
			// entity recebe um getFormData q é
			entity = getFormData();
			// chamando o metodo SaveOrUpdate e passando o OBJ que é um SELLER
			// para salvar ou ATUALIZAR no BANCO
			service.SaveOrUpdate(entity);
			// chamando o metodo NOTIFYDATACHANGELISTENERS... q irá notificar o
			// software q foi ADD um novo SELLER no BANCO...
			// e entao deve ATUALIZAR/recarregar a LISTA de vendedores
			// cadastrados no banco q aparece no SOFTWARE
			notifyDataChangeListeners();

			// pedindo para fechar a janela dps q add o vendedor do BD
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErros());
		}

		// caso de erro ao tentar add os dados no BD
		catch (DbException e) {
			Alerts.showAlert("error saving objet", null, e.getMessage(), AlertType.ERROR);
		}
	}

	// metodo notifyDataChangeListeners... Q ira notificar o software
	// quando foi ADD um novo vendedor no BANCO
	private void notifyDataChangeListeners() {
		// executar o metodo onDataChanged (metodo q é acionado quando algo e alterado)
		// for para CADA DataChangeListener (q vamos chamar de Listener) na LISTA
		// dataChangeListeners, nos vamos add esse DataChanged (dado alterado)
		// na nosso LISTENER
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	// um metodo responsavel por pegar os dados
	// q foram digitados no camp de CAD VENDEDOR e instanciar um vendedor
	private Seller getFormData() {
		// instanciando um novo objeto do tipo SELLER
		Seller obj = new Seller();

		// ValidationException para caso de error, ela exibir a mensagem
		ValidationException exception = new ValidationException("validation error");

		// pegando o q ta no TXTID q é o ID q foi "digitado" pelo usuario
		// o ID na vdd e AUTOINCREMENTE... E pegando esse ID e passando ele para
		// o OBJ atraves do comando obj.setId
		obj.setId(Utils.tryParseToInt(txtId.getText()));

		// verificando SE o campo NAME é IGUAL a NULL
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			// chamando o OBJETO/variavel EXCEPTION que é do tipo da CLASSE
			// VALIDATIONEXCEPTION e chamando o METODO ADDERROR e passando
			// os valores, CAMPO NAME e o nome do ERRO
			exception.addError("name", "field can't be empty");
		}
		// pegando o nome q foi digitado e esta no TXTNAME e passando esse nome
		// para o OBJETO (OBJ) atraves do comando SETNAME
		obj.setName(txtName.getText());

		// fazendo o mesmo processo com o email, idade salario

		// verificando SE o campo Email é IGUAL a NULL
		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			// chamando o OBJETO/variavel EXCEPTION que é do tipo da CLASSE
			// VALIDATIONEXCEPTION e chamando o METODO ADDERROR e passando
			// os valores, CAMPO NAME e o nome do ERRO
			exception.addError("email", "field can't be empty");
		}
		// pegando o email q foi digitado e esta no TXTEMAIL e passando esse email
		// para o OBJETO (OBJ) atraves do comando SETEMAIL
		obj.setEmail(txtEmail.getText());

		// verificando SE a data de NASCIMENTO NAO é NULL
		if (dpBirthDate.getValue() == null) {
			exception.addError("birthDate", "field can't be empity");
		}
		// SE a data de nascimento NAO for NULL...
		else {
			// pegando a data de nascimento
			Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			// convertndo a data de nasc q ta na var INSTANT para DATE
			// e passando esse valor para o OBJETO OBJ
			obj.setBirthDate(Date.from(instant));
		}

		// verificando SE o campo BASESALARY foi preenchido pelo usuairo
		// ou seja... SE é diferente de NULL
		if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")) {
			exception.addError("baseSalary", "field can't be empty");
		}
		// SE o BaseSalary NAO for NULL... Dai nos vamos
		// pegar o salary q foi digitado pelo usuario no campo base salary e vamos
		// passar esse salary para o OBJ... com o comando obj.setBaseSalary
		// passando o salary
		// OBS: chamamos o metodo TryParseToDouble para converte o valor q
		// ta em TEXT para um valor DOUBLE... pois o SALARY e DOUBLE
		obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));
		//
		//pegando o valor do DEPARTMENT q ta no nosso COMBOBOXDEPARTMENT
		//valor esse q foi digitado pelo usuario na hora de cadastrar um
		//SELLER, e passando esse valor para o nosso OBJ do tipo SELLER
		obj.setDepartment(comboBoxDepartment.getValue());
		
		// verificando SE a qtde de error e MAIOR q 0
		if (exception.getErros().size() > 0) {
			// entao lancamos a excessao
			throw exception;
		}

		// retornando um OBJ que é um SELLER com os DADOS pegos dos q foram
		// digitados pelo o usuario dps esse OBJ vai ser enviado para o BANCO
		// pois nele temos as info dos vendedores cadastrado
		return obj;
	}

	// metodo para realizar a acao do botao cancelar
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// chamando as restricoes feitas no metodo INITIALIZENODES
		initializeNodes();
	}

	//
	// colocando algumas restricoes na inicialização dos campos
	private void initializeNodes() {
		// o ID so aceita numero INTEIRO
		Constraints.setTextFieldInteger(txtId);
		// qtde MAX de caracter no nome
		Constraints.setTextFieldMaxLength(txtName, 70);
		// o campo do BASESALARY é um campo do tipo DOUBLE
		// para isso vamos chamar o metodo setTextFieldDouble
		Constraints.setTextFieldDouble(txtBaseSalary);
		// definindo o tamanho max do email...
		// para isso vamos chamar o metodo setTextFieldMaxLength
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		// organizando a data em um padrao
		// para isso vamos chamar o metodo formatDatePicker e pass valor da data
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		//
		// chamando o metodo q cria o comboBox para termos o campinho para escolher
		// o departamento
		initializeComboBoxDepartment();
	}

	// o metodo UPDATEFORM DATA e responsavel por pegar os dados do
	// ENTITY e "POPULAR/escrever" as caixinhas do formulario, para
	// na hora q for ATUALIZAR saber os dados q tavam escritos antes
	public void updateFormData() {
		// testar se o ENTITY/SELLER q foi passado, verificando SE ele
		// ta null
		if (entity == null) {
			throw new IllegalStateException("entity wass null");
		}

		// pegando o ID e o NOME q ta no ENTITY q é um SELLER q foi passado
		// ali em cima... e Fazendo o mesmo processo com os outros campos
		// do SELLER, para quando formos EDITAR mostrar o q ja ta escrito
		// como nome, email, nascimento, para saber o q tamos editando
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		// verificando SE o valor da DATE nao é NULL
		if (entity.getBirthDate() != null) {
			// jogando o valor da data no metodo DatePiker (para quando formos
			// editar algo e ele carregar a data no campo de edicao, a data ficar
			// organizada certinho)
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}

		// verificando SE o departamento do VENDEDOR é NULL
		if (entity.getDepartment() == null) {
			// se for NULL significa que é um vendedor NOVO q eu to cadastrando
			// entao vamos por para q o COMBOBOX (caixa dos departamento) esteja
			// SELECIONADO no primeiro departamento da listinha
			comboBoxDepartment.getSelectionModel().selectFirst();
		}
		// SE ja tiver com um departamento cadastrado, entao vamos selecionar ele
		// no COMBOBOX na hora de
		else {
			comboBoxDepartment.setValue(entity.getDepartment());
		}
	}

	// metodo LOADASSOCIATEDOBJECTS para carregar os objetos associados
	// metodo q sera responsavel por CHAMAR o DEPARTMENTSERVICE e carregar os
	// departamentos cadastrados no BANCO, e PREENCHER a LISTA
	// OBSERVABLELIST<DEPARTMENT> com esses DEPARTAMENTOS q foram buscados no BANCO
	public void loadAssociatedObjects() {

		// verificando SE o departamentSERVICE esta NULL (programacao defensiva)
		if (departmentService == null) {
			throw new IllegalStateException("DepartamentService was null");
		}

		// criando uma LIST de DEPARTMENT q recebe os DEPARTMENT do BD
		// com o metodo findAll da class DepartmentService
		List<Department> list = departmentService.findAll();
		// passando esses departamentos para a LISTA OBSERVAVEL
		obsList = FXCollections.observableArrayList(list);
		// agora vamos pegar esses valores q estao na lista observavel
		// e vamos passar para o COMBOBOX para quando o usuario clicar no local
		// ele possa ver todos os DEPARTAMENTOS q estao CAD no BD
		comboBoxDepartment.setItems(obsList);
	}

	// criando o metodo q vai pegar a mensagem de erro/excessao
	// e mostrar na tela... na parte grafica do software, q recebe como
	// argumento o MAP de STRING q tme o NOME do ERRO e a mensagem
	// exemplo "NAME" "name can't be null"
	private void setErrorMessages(Map<String, String> errors) {
		// percorrendo o map com o CONJUNTO / SET
		Set<String> fields = errors.keySet();


			//o processo a baixo e para NAME, EMAIL, NASCIMENTO, SALARY
		// vamos verificar se o SET/CONJUNTO de FIELDS tem o valor NAME
		// e dps a idade, dps baseSalary, etc...
		// dps chamando o atributo/variavel LabelErrorName q esta declarado la
		// em cima e se o FIELDS tiver o NAME vamos por no LABELERRORNAME um ""
		//(msg de erro vazio) SE nao tiver o NAME no conjunto FIELDS vamos
		//por a msg de ERROR NAME... 
		//usando um OPERADOR TERNARIO, para VERIFICAR se uma CONDICAO foi atendida
		//EXEMPLO, se estava faltando digitar o NOME e a IDADE, dai o usuario
		//digitou o NOME, mas falta a IDADE, entao a MSG de ERRO no NOME nao
		//precisa mais aparecer...
			//SE no OPERADOR TERNARIO a baixo, estamos verificando SE o NAME
			//foi PREENCHIDO, se SIM, vamos remover o error dele, colocando na msg
			//ela vazia com o ""
		labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
		labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
		labelErrorBirthDate.setText((fields.contains("birthDate") ? errors.get("birthDate") : ""));
		labelErrorBaseSalary.setText((fields.contains("baseSalary") ? errors.get("baseSalary") : ""));

		
	}

	// o prof Nelio nao explicou esse codigo, mas EU ACHO q ele serve para
	// INICIALIZAR o COMBOBOX(campo q vai ter os DEPARTAMENTS para selecionar)
	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxDepartment.setCellFactory(factory);
		comboBoxDepartment.setButtonCell(factory.call(null));
	}

}
