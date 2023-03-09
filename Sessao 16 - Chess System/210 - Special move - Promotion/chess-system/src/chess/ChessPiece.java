package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

//classe das PECAS de XADREZ... ela HERDA/Extends a classe PIECE
public abstract class ChessPiece extends Piece{
	//chamando o ENUM COLOR e passando uma cor
	private Color color;
	//add a variavel MOVECOUNT, para contar a qtde de movimento de piece
	private int moveCount;

	//criando o metodo construtor
	public ChessPiece(Board board, Color color) {
		//o BOARD vai ser passado para a SUPER/MAE CLASSE no caso a classe PIECE
		super(board);
		//e o color vai ali para a variavel COLOR ali de cima
		this.color = color;
	}

	
	//criando o GET color para pegarmos a cor... O SET REMOVEMOS para NAO alterar a COR
	public Color getColor() {
		return color;
	}
	
	//get para pegar o valor da qtde de movimentos da var MOVECOUNT
	public int getMoveCount() {
		return moveCount;
	}
	
	//metodo para incrementar o MOVECOUNT
	public void increaseMoveCount() {
		moveCount++;
	}
	
	//metodo para  decrementar o MOVECOUNT
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	
	//criando o getChessPosition q vai passar a posicao
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	

	//operacao para verificar se tem PECA ADVERSARIA em um determinado local di tabuleiro
	protected boolean isThereOpponentPiece(Position position) {
		//vamos pegar a PECA q ta na POSITION q foi passada acima, e armazenar na variavel P
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		//verificando SE P é diferente de NULL e se a COR é diferente 
		return p != null && p.getColor() != color;
		}
	
	
}
