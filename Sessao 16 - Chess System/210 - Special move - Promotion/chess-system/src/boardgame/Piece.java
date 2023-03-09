package boardgame;

//classe PECA para as peças do tabuleiro
public abstract class Piece {

	//posicao da PEÇA na MATRIZ do jogo... na MATRIZ
	protected Position position;
	private Board board;
	
	//criano o construtor da classe... So passando/recebendo o BOARD, pois inicialmente a posicao de uma
	//peça no tabuleiro é NULA... Pois as peças comecam sempre no mesmo lugar no tabuleiro
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	
	//criando APENAS o GET do BOARD/TABULEIRO... pois o SET e para alterar... e nao queremos alterar
	//o lugar das pecas no tabuleiro
	protected Board getBoard() {
		return board;
	}
	//OPERACAO de MOVIMENTOS POSSIVEIS para as PIECE
	//q retorna uma matriz boleana
	public abstract boolean[][] possibleMoves();
	//metodo POSSIBLEMOVE q recebe uma posicao
	public boolean possibleMove(Position position) {
		//testar se a peca pode se mover para uma posicao
		//retornando o posiblemoves... O metodo retornando uma Matriz
		//na
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	//para verificar SE existe pelo menos 1 movimento possivel para a piece
	public boolean isThereAnyPossibleMove() {
		//chamando o metodo abstrato POSSIBLEMOVES q vai retornar uma matriz em booleano
		//e varre a matriz para verificar SE existe UMA possicao da MATRIZ q é VERDADEIRA...
		//SE NAO TIVER... Entao a peca nao pode se MOVER
		boolean[][] mat = possibleMoves();
		for(int i = 0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				//verificando SE tem algum movimento possivel SE o BOOLEAN for TRUE
				if(mat[i][j]) {
					return true;
				}
			}
		}
		//SE nao...
		return false;
	}
}
