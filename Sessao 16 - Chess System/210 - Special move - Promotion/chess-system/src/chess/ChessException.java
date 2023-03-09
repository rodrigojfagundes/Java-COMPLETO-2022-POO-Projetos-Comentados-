package chess;

import boardgame.BoardException;

//criando a classe ChessException... para erros/excessoes na camada de xadrez
//ela HERDA/EXTENDS de BoardException
public class ChessException extends BoardException {
	//nao sei para q serve a linha  a baixo
	private static final long serialVersionUID = 1L;
	
	//criando o metodo/funcao CHESSEXCEPTION q recebe a mensagem de erro/excessao
	public ChessException(String msg) {
		//e vai passar para classe MAE/SUPER CLASSE no caso o RuntimeException a mensagem de erro
		super(msg);
	}
}
