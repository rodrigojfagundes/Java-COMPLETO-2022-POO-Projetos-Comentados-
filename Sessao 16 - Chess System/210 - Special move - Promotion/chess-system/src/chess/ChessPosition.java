package chess;

import boardgame.Position;

//classe CHessPosition serve para a posição do xadrez...

public class ChessPosition {
	
	//criando as variaveis
	private char column;
	private int row;
	
	
	//criando o construtor
	public ChessPosition(char column, int row) {
		//verificando SE a coluna for MENOR q o caracter A... OU se a Coluna for MAIS q o caracter H
		//ou se a LINHA for Menor q 1 e MAIOR q 8...
		//entao NAO ACEITAR
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("error instantiating chessposition. valid value are from a1 to h8");
		}
		this.column = column;
		this.row = row;
	}

	
	//criando os get para pegar os valores... o SET foi tirado para nao modificar os valores
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	
	//BASICAMENTE, ESSE METODO PEGA OS VALORES DA COLUNA DA MATRIZ NORMAL Q COMECA COM O 
	//NUMERO 0(zero) E VAI ATE O 7 E TRANSFORMA E UM TABULEIRO/BOARD... EM Q COMECA DO 1 E VAI ATE O 8...
	//aula 178... NAO ENTENDI MTO BEM...
	protected Position toPosition() {
		return new Position (8 - row, column - 'a');
	}
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition ((char)('a' + position.getColumn()), 8 - position.getRow());
	}
	
	//covertendo para toString
	@Override
	public String toString() {
		return "" + column + row;
	}
}