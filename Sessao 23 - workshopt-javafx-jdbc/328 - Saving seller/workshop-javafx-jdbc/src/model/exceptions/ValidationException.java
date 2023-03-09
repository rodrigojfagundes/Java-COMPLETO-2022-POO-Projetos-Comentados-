package model.exceptions;

import java.util.HashMap;
import java.util.Map;

//classe para excecao personalizada de validacao, q e uma subclasse de
//RUNTIMEEXCEPTION
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//para carregar os erros na excessao vamos chamar um MAP
	//String String... MAP é uma colecao de CHAVE:VALOR
	private Map<String, String> errors = new HashMap<>();
	
	//metodo de validation exception... q é uma excessao para validar
	//um formulario
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErros(){
		return errors;
	}
	//metodo para add elementos(errors) a colecao MAP
	//passando o campo... NAME, ID, CPF, etc... e o NOME do error
	public void addError(String fieldName, String errorMessage) {
		errors.put(fieldName, errorMessage);
	}
}