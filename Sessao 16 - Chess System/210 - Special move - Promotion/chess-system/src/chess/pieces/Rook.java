package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

//classe da peca torre... ela vai HERDAR/EXTENDS da classe CHESSPIECE...
public class Rook extends ChessPiece{

	
	//criando o construtor... LEMBRANDO o BOARD, e COLOR são atributos q vão ser passados para a
	//SUPER CLASSE ou CLASSE MAE... no caso a ChessPiece
	public Rook(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	//criando o toString
	@Override
	public String toString() {
		//o R vai aparecer no tabuleiro... R pq TORRE é ROOK em INGLES
		return "R"; 
	}
	
	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		//posicao auxiliar
		Position p = new Position(0,0);
		//veerificando as posicoes ACIMA
		//above
		//chamando o position da class PIECE q tem a posicao da PECA para pegar a LINHA e COLUNA
		//a posicao da LINHA getRow é -1 POIS é para vermos UMA LINHA ACIMA... SE a LINHA DE CIMA TA VAGA
		p.setValue(position.getRow() -1 , position.getColumn());
		//testando, enquanto a posicao P existir e NÂO tiver uma PECA la (a posicao estiver VAGA)
		//vamos marcar essa posicao como VERDADEIRA... Ou seja que é possivel por a PIECE la, pois ta VAZIO
		//o local no TABULEIRO
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			//desta forma estamos marcando o valor e a linha q esta em P como verdadeiro...
			//ou seja POSSIVEL fazer movimento para essa linha
			mat[p.getRow()][p.getColumn()] = true;
			//fazendo a linha desta posicao andar + UMA LINHA para CIMA
			p.setRow(p.getRow() -1);
		}
		//quando a repeticao acima terminar, vamos ver SE ainda existem CASAS(lugar para por PECA)
		//e TESTAR se essa casa EXISTE uma peca adversaria, se SIM vamos marcar essa casa como verdadeiro
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
				//veerificando as pecas nas posicoes a esquerda
				//left
				//chamando o position da class PIECE q tem a posicao da PECA para pegar a LINHA e COLUNA
				//a posicao da COLUNA getColumn é -1 POIS é para vermos a COLUNA A ESQUERDA... SE a COLUNA DA ESQUERDA TA VAGA
				p.setValue(position.getRow() , position.getColumn() -1);
				//testando, enquanto a posicao P existir e NÂO tiver uma PECA la (a posicao estiver VAGA)
				//vamos marcar essa posicao como VERDADEIRA... Ou seja que é possivel por a PIECE la, pois ta VAZIO
				//o local no TABULEIRO
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					//desta forma estamos marcando o valor e a linha q esta em P como verdadeiro...
					//ou seja POSSIVEL fazer movimento para essa linha
					mat[p.getRow()][p.getColumn()] = true;
					//fazendo a COLUNA desta posicao andar +(mais) UMA LINHA para ESQUERDA com o -1
					p.setColumn(p.getColumn() -1);
				}
				//quando a repeticao acima terminar, vamos ver SE ainda existem CASAS(lugar para por PECA)
				//e TESTAR se essa casa EXISTE uma peca adversaria, se SIM vamos marcar essa casa como verdadeiro
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
		
				
				
				
				//veerificando as pecas nas posicoes a direita
				//right
				//chamando o position da class PIECE q tem a posicao da PECA para pegar a LINHA e COLUNA
				//a posicao da COLUNA getColumn é +1 POIS é para vermos a COLUNA A DIREITA... SE a COLUNA DA DIREITA TA VAGA
				p.setValue(position.getRow() , position.getColumn() +1);
				//testando, enquanto a posicao P existir e NÂO tiver uma PECA la (a posicao estiver VAGA)
				//vamos marcar essa posicao como VERDADEIRA... Ou seja que é possivel por a PIECE la, pois ta VAZIO
				//o local no TABULEIRO
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					//desta forma estamos marcando o valor e a linha q esta em P como verdadeiro...
					//ou seja POSSIVEL fazer movimento para essa linha
					mat[p.getRow()][p.getColumn()] = true;
					//fazendo a COLUNA desta posicao andar +(mais) UMA LINHA para DIREITA com o +1
					p.setColumn(p.getColumn() +1);
				}
				//quando a repeticao acima terminar, vamos ver SE ainda existem CASAS(lugar para por PECA)
				//e TESTAR se essa casa EXISTE uma peca adversaria, se SIM vamos marcar essa casa como verdadeiro
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				
				
				
				//veerificando as posicoes BAIXO
				//below
				//chamando o position da class PIECE q tem a posicao da PECA para pegar a LINHA e COLUNA
				//a posicao da LINHA getRow é +1 POIS é para vermos UMA LINHA ABAIXO... SE a LINHA DE BAIXO TA VAGA
				p.setValue(position.getRow() +1 , position.getColumn());
				//testando, enquanto a posicao P existir e NÂO tiver uma PECA la (a posicao estiver VAGA)
				//vamos marcar essa posicao como VERDADEIRA... Ou seja que é possivel por a PIECE la, pois ta VAZIO
				//o local no TABULEIRO
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					//desta forma estamos marcando o valor e a linha q esta em P como verdadeiro...
					//ou seja POSSIVEL fazer movimento para essa linha
					mat[p.getRow()][p.getColumn()] = true;
					//fazendo a linha desta posicao andar + UMA LINHA para BAIXO
					p.setRow(p.getRow() +1);
				}
				//quando a repeticao acima terminar, vamos ver SE ainda existem CASAS(lugar para por PECA)
				//e TESTAR se essa casa EXISTE uma peca adversaria, se SIM vamos marcar essa casa como verdadeiro
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				
				
		
		
		return mat;
	}
}
