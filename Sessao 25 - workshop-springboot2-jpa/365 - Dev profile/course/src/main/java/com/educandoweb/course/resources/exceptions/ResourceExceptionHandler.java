package com.educandoweb.course.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

//
//classe q vai ter as mensagens de erro personalizadas
//
//o ControllerAdvice vai pegar as exceptions e trazer para q essa classe execute
//um tratamento
@ControllerAdvice
public class ResourceExceptionHandler {
	
	//o primeiro tratamento é o do ResourceNotFoundException
		//criando um ResponseEntity em q o tipo de RESPOSTA é um STANDARDERROR
		//e o nome do metodo vai ser RESOURCENOTFOUND, esse metodo recebe uma
		//exeção de RESOURCENOTFOUNDEXCEPTION (e) e mais um OBJETO do tipo
		//HTTSSERVELEDREQUEST
	//
	//Annotation para a EXCETION capturada cair nesse metodo aqui
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		//String com uma mensagem padrao de erro
		String error = "Resource not found";
		//criando uma VAR do tipo HTTPSTATUS, e vamos pegar o STATUS da RESPOSTA
		//de uma EXCEPTIONNOTFOUNDEXCEPTION... é o 404 (not found)
		HttpStatus status = HttpStatus.NOT_FOUND;
		//instanciando um OBJ do tipo StandardError, de nome ERR
		//e passando o valor dos atributos
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
	//retornando um response entity
		return ResponseEntity.status(status).body(err);
	}
	
	
	//Annotation para a EXCEPTION capturada cair nesse metodo aqui
		@ExceptionHandler(DatabaseException.class)
		public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
			//String com uma mensagem padrao de erro
			String error = "Database error";
			//criando uma VAR do tipo HTTPSTATUS, e vamos pegar o STATUS da RESPOSTA
			//de uma EXCEPTIONNOTFOUNDEXCEPTION... é o 404 (not found)
			HttpStatus status = HttpStatus.BAD_REQUEST;
			//instanciando um OBJ do tipo StandardError, de nome ERR
			//e passando o valor dos atributos
			StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		//retornando um response entity
			return ResponseEntity.status(status).body(err);
		}
		
	
	
	
}
