package com.nelioalves.workshopmongo.dto;

import java.io.Serializable;

import com.nelioalves.workshopmongo.domain.User;

//criando a class AUTHORDTO... e para 
//projetar os dados do AUTOR com DTO, pois com o DTO nos conseguimos
//FILTRAR os dados q vamos pegar, e no caso do AUTHOR de um
//POST ou COMENTARIO, queremos apenas pegar o NOME e o ID do user
//q no caso o USER é o AUTHOR q fez o post ou comentario...

public class AuthorDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//declarando os atributos
	private String id;
	private String name;
	
	//construtor vazio
	public AuthorDTO() {
	}
	
	//criando um construtor q recebe o USER como argumento
	//esse USER nos vamos chamar de OBJ
	public AuthorDTO(User obj) {
		//pegando os atributos ID e NAME do OBJ de tipo USER
		//e passando esses valores para os nossos ATRIBUTOS
		id = obj.getId();
		name = obj.getName();
	}
	
	//criando os GET e SET
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
