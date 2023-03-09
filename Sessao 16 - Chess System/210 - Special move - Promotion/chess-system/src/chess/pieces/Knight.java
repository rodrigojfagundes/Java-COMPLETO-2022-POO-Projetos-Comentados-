package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

//classe da peca do tabuleiro KNIGHT/CAVALO, e ela é uma subclasse da SUPER CLASSE / CLASSE MAE
//ChessPiece
public class Knight extends ChessPiece{

	
	
	//criando o construtor... LEMBRANDO o BOARD, e COLOR são atributos q vão ser passados para a
	//SUPER CLASSE ou CLASSE MAE... no caso a ChessPiece
	public Knight(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	
	//crindo o TOSTRING
	@Override
	public String toString() {
		//cavalo é KNIGHT, mas para nao ficar IGUAL o REI que é com K tbm ... Vamos por a letra N
		return "N";
	}
	
	
	//criando o metodo pode mover, fala se o REI pode mover para uma determinada posicao
	private boolean canMove(Position position) {
		//pegando a peca P q ta na posicao
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		//verificando SE a peca NAO é NULA e se É UMA PECA ADVERSARIA
		return p == null || p.getColor() != getColor();
	}


	//
	//NAO COMENTEI TODOS OS MOVIMENTOS, PQ ELES SÃO IGUAIS OS DAS OUTRAS PIECES/PECAS
	//A LOGICA... SÓ MUDA ALGUNS VALORES... ENTAO OS COMENTARIOS ESTAO NAS OUTRAS PIECES/PECAS :)
	//
	
	
	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
				
				//testando SE o KNIGHT
				//PARA A POSICAO 1
				p.setValue(position.getRow() -1, position.getColumn() -2);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}		
		
				//POSICAO 2
				p.setValue(position.getRow() -2 , position.getColumn() -1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
		
				//POSICAO 3
				p.setValue(position.getRow() -2, position.getColumn() +1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//POSICAO 4
				p.setValue(position.getRow() -1 , position.getColumn() +2);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//POSICAO 5
				p.setValue(position.getRow() +1 , position.getColumn() +2);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//POSICAO 6
				p.setValue(position.getRow() +2 , position.getColumn() +1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//POSICAO 7
				p.setValue(position.getRow() +2 , position.getColumn() -1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//POSICAO 8
				p.setValue(position.getRow() +1 , position.getColumn() -2);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				
		
		return mat;
	}
	

}
