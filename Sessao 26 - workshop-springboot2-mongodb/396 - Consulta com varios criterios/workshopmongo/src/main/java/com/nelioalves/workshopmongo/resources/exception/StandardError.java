package com.nelioalves.workshopmongo.resources.exception;

import java.io.Serializable;

//
//classe StandardError é uma classe que te uma
//ESTRUTURA de ERRO ou seja vai ter os CAMPOS para 
//serem PREENCHIDOS, tipo Timestamp, Status,
//Error, Exception, etc...
//
public class StandardError implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//declarando os atributos
	private Long timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
	
	//criando um construtor vazio
	public StandardError() {
	}
	
	
	//criando o construtor com argumentos
	public StandardError(Long timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
	
	
	//criando os GET e SET
	public Long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	
	
}
