package com.nelioalves.workshopmongo.dto;

import java.io.Serializable;
import java.util.Date;

//classe COMMENTDTO... Pois com ele nos vamos PEGAR TODOS OS DADOS
//do OBJ COMMENT e vamos retornar apenas alguns dados principais
//o TEXTO, o DATE e AUTHOR
public class CommentDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	//declarando atributos
	private String text;
	private Date date;
	private AuthorDTO author;
	
	//criando o construtor vazio
	public CommentDTO() {
	}
	
	//criando o construtor com todos os atributos
	public CommentDTO(String text, Date date, AuthorDTO author) {
		super();
		this.text = text;
		this.date = date;
		this.author = author;
	}
	
	//criando os GET e SET
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
	
	
}
