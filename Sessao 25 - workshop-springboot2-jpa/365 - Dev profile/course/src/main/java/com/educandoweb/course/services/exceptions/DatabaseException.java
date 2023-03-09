package com.educandoweb.course.services.exceptions;
//
//classe para excessoes no BANCO... tipo caso alguem tente deletar um USUARIO
//q tenha PEDIDOS e PRODUTOS associados a esse USUARIO, nao sera possivel deletar
//o usuario...
//
public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DatabaseException(String msg) {
		super(msg);
	}
}