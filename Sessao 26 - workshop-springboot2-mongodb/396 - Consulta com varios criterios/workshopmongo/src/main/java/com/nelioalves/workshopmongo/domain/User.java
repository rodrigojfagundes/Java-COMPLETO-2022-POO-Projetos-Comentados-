//
//criar o SUBPACOTE DOMAIN, q significa DOMINIO, DOMINIO do 
//NEGOCIO, ou seja as entidades do negocio vao estar nessa 
//classe... e dentro do subpacote DOMAIN criar a classe USER

package com.nelioalves.workshopmongo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


	//colocando @DOCUMENT e @ID para dizer q essa CLASS é 
	//uma COLEÇÂO/TABELA do banco MONGODB
	//o collection = "USER" e para dizer q a colecao no MONGO o nome e 
	//user
@Document
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	

	//criando os atributo
	//o @ID e por causa do ID para o MONGO DB como chave primary
	@Id
	private String id;
	private String name;
	private String email;
	
	
	//List/LISTA de POSTS
	//fazendo o USUARIO conhecer os POST dele... Ou seja a partir do 
	//USUARIO/AUTHOR vamos ver quais os POST q foram feitos por ele... 
	//dentro do  USUARIO vai ter APENAS AS REFERENCIAS para o ID dos POSTS
		//
		//no SPRINGDATA para dizermos q um ATRIBUTO esta REFERENCIANDO 
		//outra COLECAO do MONDODB e so colocar o @DBREF
	@DBRef(lazy = true)
	private List<Post> posts = new ArrayList<>();
	
	//criando o metodo construtor vazio
	public User() {
	}
	
	
	//criando o metodo construtor com argumentos
	public User(String id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	
	
	//criando os metodos GET e SET
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
	
	
	
	public List<Post> getPosts() {
		return posts;
	}


	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	
	//criando os metodos HASHCODE e EQUALS para q possamos
	//comparar os objetos pelo o valor do ID
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
