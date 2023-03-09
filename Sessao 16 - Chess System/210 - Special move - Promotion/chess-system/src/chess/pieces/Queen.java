package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

//CLASSE da RAINHA/QUEEN
public class Queen extends ChessPiece{

	
	//
	//NAO COMENTEI TODOS OS MOVIMENTOS, PQ ELES SÃO IGUAIS OS DAS OUTRAS PIECES/PECAS
	//A LOGICA... SÓ MUDA ALGUNS VALORES... ENTAO OS COMENTARIOS ESTAO NAS OUTRAS PIECES/PECAS :)
	//
	

	public Queen(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	//criando o toString
	@Override
	public String toString() {
		//o Q vai aparecer no tabuleiro... Q pq RAINHA é QUEEN em INGLES
		return "Q"; 
	}
	
	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		//posicao auxiliar
		Position p = new Position(0,0);

		
		
		p.setValue(position.getRow() -1 , position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;	
			p.setRow(p.getRow() -1);
		}
		
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
			
				p.setValue(position.getRow() , position.getColumn() -1);
				
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
					
					p.setColumn(p.getColumn() -1);
				}
				
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
						
				p.setValue(position.getRow() , position.getColumn() +1);
				
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;				
					p.setColumn(p.getColumn() +1);
				}
				
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
					
				p.setValue(position.getRow() +1 , position.getColumn());			
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;				
					p.setRow(p.getRow() +1);
				}
				
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				p.setValue(position.getRow() -1 , position.getColumn()-1);			
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {					
					mat[p.getRow()][p.getColumn()] = true;
					//movimento LINHA -1 e COLUNA -1
					p.setValue(p.getRow() -1, p.getColumn() -1);
				}				
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}

						//veerificando as pecas nas posicoes DIAGONAL NORDESTE
						//NE
						
						p.setValue(position.getRow() -1, position.getColumn() +1);						
						while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {						
							mat[p.getRow()][p.getColumn()] = true;						
							p.setValue(p.getRow() -1, p.getColumn() + 1);
						}
						
						if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
							mat[p.getRow()][p.getColumn()] = true;
						}


						p.setValue(position.getRow() +1 , position.getColumn() +1);
						
						while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
							
							mat[p.getRow()][p.getColumn()] = true;
							
							p.setValue(p.getRow() +1, p.getColumn() +1);
						}
						
						if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
							mat[p.getRow()][p.getColumn()] = true;
						}

						
						p.setValue(position.getRow() +1 , position.getColumn() -1);
						
						while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
							
							mat[p.getRow()][p.getColumn()] = true;
							
							p.setValue(p.getRow() + 1, p.getColumn() -1);
						}
						
						if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
							mat[p.getRow()][p.getColumn()] = true;
						}

		return mat;
	}
}
