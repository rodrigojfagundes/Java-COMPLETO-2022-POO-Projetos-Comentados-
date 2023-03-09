package boardgame;
//
//classe tabuleiro
public class Board {
	//criando as variaveis do tabuleiro
	private int rows;
	private int columns;
	//criando uma matriz de PECAS
	private Piece[][] pieces;
	
	
	//esse construtor de TABULEIRO vai RECEBER apenas a LINHA e a COLUNA...
	public Board(int rows, int columns) {
		//a qtde de linhas e colunas do tabuleiro deve ser no minimo 1
		if(rows < 1 || columns < 1 ) {
			throw new BoardException("error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		//aqui nos estamos INSTANCIANDO um OBJETO chamado PIECES q vai passar o valor de LINHA e COLUNA
		//para classe Piece
		pieces = new Piece[rows][columns];
	}

	
	//criando o GET de COLUMN e de ROW... o SET nao pois NAO queremos ALTERAR esses valores dps q o jogo
	//comeca

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	//criando um metodo/funcao do tipo chamado de Piece que é do tipo PIECE, e esse metodo vai RECEBER
	//uma LINHA e COLUNA q vai ser passada para a classe PIECE (por isso q ele e do tipo PIECE)
	public Piece piece(int row, int column) {
		//testar se a posicao NAO EXISTE...
		if (!positionExists(row, column)) {
			//se a position q foi testada no IF acima nao existir... dai vai exibir a mensagem de erro
			//a baixo
			throw new BoardException("position not on the board");
		}
		//o metodo vai retornar a matriz PIECES na linha ROW e coluna COLUMN (q foram passadas acima)
		return pieces[row][column];
	}
	//agora vamos fazer uma SOBRECARGA do metodo PIECE, e agora ele vai RETORNAR a PEÇA/PIECE pela
	//posicao
	public Piece piece(Position position) {
		//testar se a posicao NAO EXISTE...
		if (!positionExists(position)) {
			//se a position q foi testada no IF acima nao existir... dai vai exibir a mensagem de erro
			//a baixo
			throw new BoardException("position not on the board");
		}
		//retornar o pieces na posicao e na coluna
		return pieces[position.getRow()][position.getColumn()];
	}
	//metodo para colcoar as PECAS no TABULEIRO... q vai receber uma PECA e uma POSICAO
		// OBS: o PIECE é a MATRIZ q ta DECLARADA dentro DESSA CLASSE BOARD... no TOPO DESSE ARQUIVO
			//NAO É A CLASSE PIECE
	public void placePiece(Piece piece, Position position) {
		//testar se ja existe uma peca/pice na posicao
		if (thereIsAPiece(position)) {
			throw new BoardException("there is already a piece on position " + position);
		}
		
		//o metodo vai na matriz de peça na linha position.getRow e na coluna position.get.Column e
		//add a essa posicao da matriz de pecas a peca PIECE q veio como argumento ali na funcao
		pieces[position.getRow()][position.getColumn()] = piece;
		//agora vamos dizer q PIECE NÂO esta mais na posicao NULA
		//chamando a CLASSE agora é a CLASSE PIECE e dps passando o valor da posicao atual da PECA/PIECE
		//para o atributo/variavel POSITION da class PIECE... mesmo nao tendo o GET e SET :)
		piece.position = position;
	}
	//criando metodo para mover as pecas
		//metodo public q retorna uma PIECE, o metodo se chama REMOVEPICE, e ele vai receber
		//como argumento uma Position do tipo Position
	public Piece removePiece(Position position) {
		//verificando se a posicao nao existe
		if (!positionExists(position)) {
			throw new BoardException("position not on the board");
		}
		//vamos verificar se NAO existe PECA na POSITION q foi passada
		if (piece(position) == null){
			return null;
		}
		//SE o IF acima nao acontecer, entao vamos retirar a peca do tabuleiro
		//a variavel AUX do tipo PIECE vai receber a peca q esta em POSITION
		Piece aux = piece(position);
		//a peça q esta em AUX vai receber a posicao NULL... ou seja NAO tem mais posicao
		aux.position = null;
		//acessando a MATRIZ de PECAS/PIECE... na linha POSITION.GETROW e na COLUNA POSITION.GETCOLUMN
		//vai receber o valor NULL... dessa forma indicamos q NAO tem mais peca nessa posicao da matriz
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	
	//criando um metodo auxiiar PRIVADO q recebe uma linha e coluna
	//e verifica se essa LINHA e COLUNA existe dentro do tabuleiro...
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	//se o metodo/funcao ali de cima for TRUE o PositionExists... Entao o positionExists aqui de baixo
	//vai retornar TRUE
	public boolean positionExists(Position position) {
	return positionExists(position.getRow(), position.getColumn());
	}
	
	//criando metodo thereIsAPiece... Q serve para testar se tem PECA na posicao
	public boolean thereIsAPiece(Position position) {
		//testar se a posicao NAO EXISTE...
		if (!positionExists(position)) {
	    //se a position q foi testada no IF acima nao existir... dai vai exibir a mensagem de erro
		//a baixo
		throw new BoardException("position not on the board");
		}
		//CHAMANDO o metodo/funcao PIECE q esta NESSA CLASSE BOARD, e passando o valor de POSITION
		//para esse metodo... e verificando se o o resultado e diferente de nulo
		return piece(position) != null;
	}
	
}
