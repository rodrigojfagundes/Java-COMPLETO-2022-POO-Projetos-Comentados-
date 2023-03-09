//
//usando o padrao DTO para retornar USUARIO

//DTO é uma classe q sera usada COMO OBJ de transferencia de DADOS, 
//ou seja vamos supor q eu tenho um OBJ do tipo USUARIO q tem NOME,
//IDADE, EMAIL, CIDADE, etc...mas eu quero transferir(USAR) so o 
//NOME e IDADE, dai eu crio um DTO para passar isso
//pois eu passo para os atributos do USERDTO apenas os ATRIBUTOS
//NOME e IDADE do OBJ USER verdadeiro
package com.nelioalves.workshopmongo.dto;

import java.io.Serializable;

import com.nelioalves.workshopmongo.domain.User;

//classe USERDTO
public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	//declarando os atributos
	private String id;
	private String name;
	private String email;
	
	//criando um construtor vazio
	public UserDTO() {
	}
	
	
	//criando um CONSTRUTOR de USERDTO q recebe um OBJ do tipo
	//USER
	public UserDTO(User obj) {
	//o campo ID/NAME/EMAIL do USERDTO, vai receber os valores 
	//do USER q foi passado ali em cima como OBJ
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
}
