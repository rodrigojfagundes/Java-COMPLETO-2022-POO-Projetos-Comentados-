package com.nelioalves.workshopmongo.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nelioalves.workshopmongo.services.exception.ObjectNotFoundException;

//classe ResourceExceptionHandler, será um 
//manipulador de excecoes na camada de resource

//colocando o ANNOTATION @ControllerAdvice, serve para dizer q essa
//classe e responsavel por tratar possiveis erros nas requisicoes

@ControllerAdvice
public class ResourceExceptionHandler {
	
	//declarando um metodo ResponseEntity q retorna uma
	//CLASSE STANDARD ERROR, e esse metodo nos vamos chamar
	//de ObjectNotFound, como parametro vamos passar o TIPO
	//de excessao q vamos tratar no caso: ObjectNotFoundEException
	//e o parametro HTTPServeletRequest
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
//vamos ter um tratamento para a excessao ObjectNotFoundException
		
		//aqui e para q a variavel STATUS q vai ser usada ali 
		//em baixo no ERR tenha o STATUS de NOT_FOUND
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		//declarando um OBJ de nome ERR do tipo StandardError
		//passando o MOMENTO do ERRO
		//passando o STATUS do error q é o NOT_FOUND
		//passando uma DESCRICAO CURTA
		//passando a mensagem q pegamos do ObjectNotFoundException (e.getMessage())
		//passando o caminho q gerou a excessao
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		
		//retornando um RESPONSE ENTITY com um codigo de STATUS
		//e no .BODY/CORPO no passando o ERR q é um OBJ do tipo
		//STANDARDERROR
		return ResponseEntity.status(status).body(err);
	}
}
