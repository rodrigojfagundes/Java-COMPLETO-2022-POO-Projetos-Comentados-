package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

//classe da peca do tabuleiro REI... ela HERDA/EXTENDS do ChessPiece
public class King extends ChessPiece{

	//criando uma dependencia para a partida
	private ChessMatch chessMatch;
	
	//criando o construtor... LEMBRANDO o BOARD, e COLOR são atributos q vão ser passados para a
	//SUPER CLASSE ou CLASSE MAE... no caso a ChessPiece, e incluindo a referencia da partida ChesMatch no
	//construtor
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
		// TODO Auto-generated constructor stub
	}
	
	
	//crindo o TOSTRING
	@Override
	public String toString() {
		//o K vai aparecer no tabuleiro... K pq REI é KING em INGLES
		return "K";
	}
	
	
	//criando o metodo pode mover, fala se o REI pode mover para uma determinada posicao
	private boolean canMove(Position position) {
		//pegando a peca P q ta na posicao
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		//verificando SE a peca NAO é NULA e se É UMA PECA ADVERSARIA
		return p == null || p.getColor() != getColor();
	}
	
	//metodo auxiliar para ajudar a testar a condicao de roque (roque é uma jogada do xadrez)
	//para testar se a torre esta apta para fazer a jogada roque
	private boolean testRookCastling(Position position) {
		//verificando se a qtde de movimento e zero
		//pegando a piece q ta na position
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		//testando se a piece e diferente de nulo, e verificando se ela e uma torre, e se a cor é
		//da mesma cor do rei, e testar se ela ainda nao se moveu com o P.GETMOVECOUNT
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;	
	}


	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//testando SE o REI pd se mover para cima
		//above
		//posicao da linha -1 ou seja da LINHA ACIMA DO REI
		p.setValue(position.getRow() -1, position.getColumn());
		//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
				//testando SE o REI pd se mover para baixo
				//below
				//posicao da linha +1 ou seja da LINHA BAIXO DO REI
				p.setValue(position.getRow() +1, position.getColumn());
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}		
		
		
				//testando SE o REI pd se mover para esquerda
				//left
				//posicao da COLUNA -1 ou seja da COLUNA ESQUERDA DO REI
				p.setValue(position.getRow(), position.getColumn() -1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
		
				//testando SE o REI pd se mover para direita
				//right
				//posicao da COLUNA +1 ou seja da COLUNA DIREITA DO REI
				p.setValue(position.getRow(), position.getColumn() +1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				
				//testando SE o REI pd se mover para NOROESTE
				//NW
				//posicao da LINHA -1 e COLUNA -1
				p.setValue(position.getRow() -1 , position.getColumn() -1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//assim sucessivamente...
				
				//testando SE o REI pd se mover para NORDESTE
				//NE
				//posicao da LINHA -1 e COLUNA -1
				p.setValue(position.getRow() -1 , position.getColumn() +1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//testando SE o REI pd se mover para SUDOESTE
				//SW
				//posicao da LINHA -1 e COLUNA -1
				p.setValue(position.getRow() +1 , position.getColumn() -1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				
				//testando SE o REI pd se mover para SUDESTE
				//SE
				//posicao da LINHA -1 e COLUNA -1
				p.setValue(position.getRow() +1 , position.getColumn() +1);
				//se a posicao P existe, e SE o retorno de canMove for TRUE, entao pode se mover
				if (getBoard().positionExists(p) && canMove(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//#SpecialMove Castling (verificando se o REI pd se mover para DIREITA e ESQUERDA)
				
				//verificando se nao houve movimentos com o rei, e se ele nao ta em cheque
				if(getMoveCount() == 0 && !chessMatch.getCheck()) {
					//testar se as 2 posicoes DIREITA e ESQUERDA tao vaga e se a TORRE ainda nao se moveu
					
					//#specialmove castling kingside rook (rook do lado do rei) rook pequeno
					
					//posicao da torre do rei é  alinha e coluna do REI + 3 casas
					Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
					//testar se nessa posicao realmente tem uma torre la... POIS SE NAO TIVER nao de
					//fazer o ROOK
					if (testRookCastling(posT1)) {
						//vamos testar as 2 casas entre o rei e a torre se elas tao vazia
						//1 e 2 casas
						Position p1 = new Position(position.getRow(), position.getColumn() + 1);
						Position p2 = new Position(position.getRow(), position.getColumn() + 2);
						//verificando se a casa 1 e 2 entre o rei e a torre tao vazia
						if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
							//entao podemos fazer o rook
							//incluindo na matriz de movimentos possiveis o rook
							mat[position.getRow()][position.getColumn() + 2] = true;
						}
					}
					
					//fazendo o rook grande
					//#specialmove castling queenside rook (rook do lado do rei) rook pequeno
					
					//posicao da torre do rei é  alinha e coluna do QUEEN - 4 casas
					Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
					//testar se nessa posicao realmente tem uma torre la... POIS SE NAO TIVER nao de
					//fazer o ROOK
					if (testRookCastling(posT2)) {
						//verificando se a casa 1,2 e 3 estao vagas a esquerda
						Position p1 = new Position(position.getRow(), position.getColumn() - 1);
						Position p2 = new Position(position.getRow(), position.getColumn() - 2);
						Position p3 = new Position(position.getRow(), position.getColumn() - 3);
						//verificando se a casa 1, 2 e 3 entre o rei e a torre tao vazia
						if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
							//entao podemos fazer o rook
							//incluindo na matriz de movimentos possiveis o rook
							mat[position.getRow()][position.getColumn() -2] = true;
						}
					}
					
					
				}
		
		return mat;
	}
	

}
