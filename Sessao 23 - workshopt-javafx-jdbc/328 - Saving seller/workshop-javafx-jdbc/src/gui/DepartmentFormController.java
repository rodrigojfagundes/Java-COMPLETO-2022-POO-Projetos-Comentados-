package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

//class DepartmentFormController q IMPLEMENTA uma INTERFACE initializable
public class DepartmentFormController implements Initializable {
	
	//crianod uma dependencia para o departamento chamada de ENTITY
	private Department entity;
	
	//criando uma depedencia de DepartmentService, chamada de SERVICE
	private DepartmentService service;
	
	//criando uma LISTA da INTERFACE DataChangeListener...
	//q permite q outros obj se inscrevam na lista e recebam o evento
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	//declarando os componentes da TELA de mensagem... Aquela q quando clica
	//no botao NEW chama ela... a TELA para CADASTRAR departamento
	//declarando os atributos
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	
	//implementando o SET para fazer a INJECAO de DEPENCIAS do ENTITIY do tipo
	//department
	//quando chamarmos o METODO SETDEPARTMENT nos vamos passar um DEPARTMENT
	//para ele q será ARMAZENADO na VARIAVEL/dependencia ENTITY q criamos la
	//em cima... Nao passarmos o valor direto para ela, pois ela ta em PRIVATE
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	//criando um metodo SETDEPARTMENTSERVICE q RECEBE o um SERVICE q sera do tipo
	//DEPARTMENTSERVICE
	public void setDepartmentService(DepartmentService service) {
		//passando o VALOR recebido em SERVICE que é do TIPO DEPARTMENTSERVICE(classe)
		//e passando esse valor para a DEPENDENCIA/variavel (service)q criamos la em
		//cima
		this.service = service;
	}
	
	
	//disponibilizam o metodo para q os objetos possam se inscrever no no
	//DataChangeListener... e ASSIM vamos ADD o LISTENER recebido na LISTA
	public void subscribeDataChangeListener(DataChangeListener listener) {
		//ou seja vamos add ele na lista
		dataChangeListeners.add(listener);
	}

	//metodo para realizar a acao do botao salvar
	@FXML
	public void ontBtSaveAction(ActionEvent event) {
		//testando se o ENTITY(department) esta valendo NULL
		if(entity == null) {
			throw new IllegalStateException("entity wall null");
		}
		//verificando se o SERVICE esta valendo null... Ou seja se esqueceu
		//de injetar o service...
		if (service == null) {
			throw new IllegalStateException("service was null");
		}
		try {
			//salvando o departamento no BD
			//instanciar um departamento e salvar esse departamento no BD
			//entity recebe um getFormData q é 
			entity = getFormData();
			//chamando o metodo SaveOrUpdate e passando o OBJ que é um DEPARTAMENT
			//para salvar ou ATUALIZAR no BANCO
			service.SaveOrUpdate(entity);
			//chamando o metodo NOTIFYDATACHANGELISTENERS... q irá notificar o
			//software q foi ADD um novo DEPARTAMENT no BANCO...
			//e entao deve ATUALIZAR/recarregar a LISTA de departamentos
			//cadastrados no banco q aparece no SOFTWARE
			notifyDataChangeListeners();
			
			//pedindo para fechar a janela dps q add o departamento do BD
			Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErros());		
		}
		
		
		//caso de erro ao tentar add os dados no BD
		catch(DbException e) {
			Alerts.showAlert("error saving objet", null, e.getMessage(), AlertType.ERROR);
		}
	}
		
	//metodo notifyDataChangeListeners... Q ira notificar o software
	//quando foi ADD um novo departamento no BANCO
	private void notifyDataChangeListeners() {
		//executar o metodo onDataChanged (metodo q é acionado quando algo e alterado)
		//for para CADA DataChangeListener (q vamos chamar de Listener) na LISTA
		//dataChangeListeners, nos vamos add esse DataChanged (dado alterado)
		//na nosso LISTENER
		for(DataChangeListener listener: dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	//um metodo responsavel por pegar os dados
	//q foram digitados no camp de CAD DEPARTAMENTO e instanciar um departamento
	private Department getFormData() {
		//instanciando um novo objeto do tipo DEPARTMENT
		Department obj = new Department();

		//ValidationException para caso de error, ela exibir a mensagem
		
		ValidationException exception = new ValidationException("validation error");
		
		
		// pegando o q ta no TXTID q é o ID q foi "digitado" pelo usuario
		//o ID na vdd e AUTOINCREMENTE... E pegando esse ID e passando ele para
		//o OBJ atraves do comando obj.setId
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		//verificando SE o campo NAME é IGUAL a NULL
		if(txtName.getText() == null || txtName.getText().trim().equals(""))
		{
			//chamando o OBJETO/variavel EXCEPTION que é do tipo da CLASSE
			//VALIDATIONEXCEPTION e chamando o METODO ADDERROR e passando
			//os valores, CAMPO NAME e o nome do ERRO
			exception.addError("name", "field can't be empty");
		}
		// pegando o nome q foi digitado e esta no TXTNAME e passando esse nome
		//para o OBJETO (OBJ) atraves do comando SETNAME
		obj.setName(txtName.getText());
		
		//verificando SE a qtde de error e MAIOR q 0
		if(exception.getErros().size() > 0) {
			//entao lancamos a excessao
			throw exception;
		}
		
		//retornando um OBJ que é um DEPARTMENT com os DADOS pegos dos q foram
		//digitados pelo o usuario, dps esse OBJ vai ser enviado para o BANCO
		//pois nele temos as info dos departamento cadastrado
		return obj;
	}

	//metodo para realizar a acao do botao cancelar
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// chamando as restricoes feitas no metodo INITIALIZENODES
		initializeNodes();
	}
	//colocando algumas restricoes na inicialização
	
	private void initializeNodes() {
		//o ID so aceita numero INTEIRO
		Constraints.setTextFieldInteger(txtId);
		//qtde MAX de caracter no nome
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
		//o metodo UPDATEFORM DATA e responsavel por pegar os dados do 
		//ENTITY e "POPULAR/escrever" as caixinhas do formulario, para 
		//na hora q for ATUALIZAR saber os dados q tavam escritos antes
		public void updateFormData() {
			//testar se o ENTITY/DEPARTAMENT q foi passado, verificando SE ele
			//ta null
			if (entity == null) {
				throw new IllegalStateException("entity wass null");
			}
			
			//pegando o ID e o NOME q ta no ENTITY q é um DEPARTMENT q foi passado
			//ali em cima
			txtId.setText(String.valueOf(entity.getId()));
			txtName.setText(entity.getName());
		
	}
		
		//criando o metodo q vai pegar a mensagem de erro/excessao
		//e mostrar na tela... na parte grafica do software, q recebe como
		//argumento o MAP de STRING q tme o NOME do ERRO e a mensagem
		// exemplo "NAME" "name can't be null" 
		private void setErrorMessages(Map<String, String> errors) {
			//percorrendo o map com o CONJUNTO / SET
			Set<String> fields = errors.keySet();
			
			//vamos verificar se o SET/CONJUNTO de FIELDS tem o valor NAME
			if(fields.contains("name"));
			//chamando o atributo/variavel LabelErrorName q esta declarado la
			//em cima e vamos atribuir a ele o valor q esta no conjunto
			labelErrorName.setText(errors.get("name"));
		}
		
}
