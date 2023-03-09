//pacote BOARDGAME é referente a camada de tabuleiro
package boardgame;

//classe com a posicao das peças do xadrez
public class Position {
	
	//declarando as variaveis
	private int row;
	private int column;
	
	//criando o contrutor da classe
	
	public Position(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	
	
	//criando os get e set para podermos alterar os valores das variaveis Q SAO PRIVATE
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	
	public void setValue(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	//metodo para converter para STRING os dados, pq se nao eles ficam tudo estranho tipo mostra o
	//endereco de memoria onde ta o conteudo da variavel, em vez do conteudo...
	@Override
	public String toString() {
		return row + ", " + column;
	}

	
}
