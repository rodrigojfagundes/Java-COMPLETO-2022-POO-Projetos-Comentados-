//
// quando procurar um ID de USUARIO q nao existe
//

package com.nelioalves.workshopmongo.services.exception;

//classe OBJECTNOTFOUNDEXCEPTION q HERDA da RUNTIMEEXCEPTION, q e uma
//classe q e para manejar erros
public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//sobrepor o construtor basico, q RECEBE UM STRING com a MSG de ERRO
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
}
