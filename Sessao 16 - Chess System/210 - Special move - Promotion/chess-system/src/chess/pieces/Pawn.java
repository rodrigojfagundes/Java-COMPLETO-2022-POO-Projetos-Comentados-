package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

//class PEAO... Extende/HERDA da ChessPiece
public class Pawn extends ChessPiece{
	
	
	//criando uma dependencia para a partida
	private ChessMatch chessMatch;
	
	//criando construtor
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
		// TODO Auto-generated constructor stub
	}
	
	
	//sobrescrita, de PosiblesMoves... Movimentos possiveis de um peao
	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		
		//declaracao da matriz e posicao
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		//posicao auxiliar
		Position p = new Position(0,0);
		
		//verificando se a cor do peao é branco
		if(getColor() == Color.WHITE) {
			//na primeira jogada DO JOGO o PEAO só pode mover para CIMA... 1 ou 2 casas
			p.setValue(position.getRow() -1, position.getColumn());
			//significa q o PEAO pd ir para essa posicao... 1 linha para frente
			//se a posicao de 1 linha acima dele existir e estiver vazia, ele pode andar para la
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			//para frente
			p.setValue(position.getRow() -2, position.getColumn());
			//verificando SE nao tem 1 outro peao na frente, pois se tiver NAO e possivel pular 2 casa
			Position p2 = new Position(position.getRow()-1, position.getColumn());
			//significa q o PEAO pd ir para essa posicao... 2 linha para frente
			//se a posicao de 2 linha acima dele existir e estiver vazia, ele pode andar para la
			//SE o getmoveCount for 0 entao significa q e a primeira jogada, logo pd andar 2 casa
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//peao andando na diagonal esquerda
			p.setValue(position.getRow() -1, position.getColumn()-1);
			//se a posicao existe e se tem UMA PIECE ADVERSARIA
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}	
			//peao andando na diagonal direita
			p.setValue(position.getRow() -1, position.getColumn()+1);
			//se a posicao existe e se tem UMA PIECE ADVERSARIA
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//specialmove en passant white
			//verificando se tem piece branca na linha 3
			if(position.getRow() == 3) {
				//verificando se tem piece adversaria na linha
				Position left = new Position(position.getRow(), position.getColumn() -1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					//movendo para a posicao da matriz
					mat[left.getRow() -1][left.getColumn()] = true;
				}
				//specialmove en passant white
				//verificando se tem piece adversaria na linha direita
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					//movendo para a posicao da matriz
					mat[right.getRow() -1][right.getColumn()] = true;
				}
			}
			
		}
		//piece/peca preta
			else {
				//piece preta
				
				//na primeira jogada DO JOGO o PEAO só pode mover para CIMA... 1 ou 2 casas
				p.setValue(position.getRow() +1, position.getColumn());
				//significa q o PEAO pd ir para essa posicao... 1 linha para frente
				//se a posicao de 1 linha acima dele existir e estiver vazia, ele pode andar para la
				if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				
				//para frente
				p.setValue(position.getRow() +2, position.getColumn());
				//verificando SE nao tem 1 outro peao na frente, pois se tiver NAO e possivel pular 2 casa
				Position p2 = new Position(position.getRow()+1, position.getColumn());
				//significa q o PEAO pd ir para essa posicao... 2 linha para frente
				//se a posicao de 2 linha acima dele existir e estiver vazia, ele pode andar para la
				//SE o getmoveCount for 0 entao significa q e a primeira jogada, logo pd andar 2 casa
				if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//peao andando na diagonal esquerda
				p.setValue(position.getRow() +1, position.getColumn()-1);
				//se a posicao existe e se tem UMA PIECE ADVERSARIA
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}	
				//peao andando na diagonal direita
				p.setValue(position.getRow() +1, position.getColumn()+1);
				//se a posicao existe e se tem UMA PIECE ADVERSARIA
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//specialmove en passant black
				//verificando se tem piece back na linha 4
				if(position.getRow() == 4) {
					//verificando se tem piece adversaria na linha
					Position left = new Position(position.getRow(), position.getColumn() -1);
					if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
						//movendo para a posicao da matriz
						mat[left.getRow() +1][left.getColumn()] = true;
					}
					//specialmove en passant white
					//verificando se tem piece adversaria na linha direita
					Position right = new Position(position.getRow(), position.getColumn() + 1);
					if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
						//movendo para a posicao da matriz
						mat[right.getRow() +1][right.getColumn()] = true;
					}
				}
				
				
				
				
				
				
			}

		return mat;
	}
	
	//tostring
	@Override
	public String toString() {
		return "P";
	}
	
	
}
