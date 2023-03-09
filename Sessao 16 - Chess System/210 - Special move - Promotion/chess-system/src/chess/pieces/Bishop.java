package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

//classe da peca BISPO... ela vai HERDAR/EXTENDS da classe CHESSPIECE...
public class Bishop extends ChessPiece{

	
	//criando o construtor... LEMBRANDO o BOARD, e COLOR são atributos q vão ser passados para a
	//SUPER CLASSE ou CLASSE MAE... no caso a ChessPiece
	public Bishop(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	//criando o toString
	@Override
	public String toString() {
		//o R vai aparecer no tabuleiro... B pq BISPO é BISHOP em INGLES
		return "B"; 
	}
	
	@Override
	public boolean[][] possibleMoves() {
		// TODO Auto-generated method stub
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		//posicao auxiliar
		Position p = new Position(0,0);
		
		//veerificando as posicoes NOROESTE
		//nw
		//chamando o position da class PIECE q tem a posicao da PECA para pegar a LINHA e COLUNA
		//a posicao da LINHA getRow é -1 POIS é para vermos UMA LINHA ACIMA... 
		//coluna é -1 tbm... Pois assim ele vai na diagonal noroeste 
		p.setValue(position.getRow() -1 , position.getColumn()-1);
		//testando, enquanto a posicao P existir e NÂO tiver uma PECA la (a posicao estiver VAGA)
		//vamos marcar essa posicao como VERDADEIRA... Ou seja que é possivel por a PIECE la, pois ta VAZIO
		//o local no TABULEIRO
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			//desta forma estamos marcando o valor e a linha q esta em P como verdadeiro...
			//ou seja POSSIVEL fazer movimento para essa linha
			mat[p.getRow()][p.getColumn()] = true;
			//movimento LINHA -1 e COLUNA -1
			p.setValue(p.getRow() -1, p.getColumn() -1);
		}
		//quando a repeticao acima terminar, vamos ver SE ainda existem CASAS(lugar para por PECA)
		//e TESTAR se essa casa EXISTE uma peca adversaria, se SIM vamos marcar essa casa como verdadeiro
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
				//veerificando as pecas nas posicoes DIAGONAL NORDESTE
				//NE
				//chamando o position da class PIECE q tem a posicao da PECA para pegar a LINHA e COLUNA
				//a posicao da COLUNA getColumn é -1 e LINHA + 1 POIS é para vermos se a a posicao ta vaga
				p.setValue(position.getRow() -1, position.getColumn() +1);
				//testando, enquanto a posicao P existir e NÂO tiver uma PECA la (a posicao estiver VAGA)
				//vamos marcar essa posicao como VERDADEIRA... Ou seja que é possivel por a PIECE la, pois ta VAZIO
				//o local no TABULEIRO
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					//desta forma estamos marcando o valor e a linha q esta em P como verdadeiro...
					//ou seja POSSIVEL fazer movimento para essa linha
					mat[p.getRow()][p.getColumn()] = true;
					//chamando o setValue para alterar a linha e a coluna da piece
					//pois os testes acima falaram q ta vaga
					p.setValue(p.getRow() -1, p.getColumn() + 1);
				}
				//quando a repeticao acima terminar, vamos ver SE ainda existem CASAS(lugar para por PECA)
				//e TESTAR se essa casa EXISTE uma peca adversaria, se SIM vamos marcar essa casa como verdadeiro
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
		
				
				
				
				//veerificando as pecas nas posicoes no SUDESTE
				//SE
				//SEMELHANTE AOS OUTROS DE CIMA... SÓ MUDA O VALOR DA LINHA E COLUNA
				p.setValue(position.getRow() +1 , position.getColumn() +1);
				
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					
					mat[p.getRow()][p.getColumn()] = true;
					//alterando a posicao da piece
					p.setValue(p.getRow() +1, p.getColumn() +1);
				}
				//quando a repeticao acima terminar, vamos ver SE ainda existem CASAS(lugar para por PECA)
				//e TESTAR se essa casa EXISTE uma peca adversaria, se SIM vamos marcar essa casa como verdadeiro
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				
				
				
				//veerificando as posicoes SUDOESTE
				//below
				//SEMELHANTE AOS OUTROS DE CIMA... SÓ MUDA O VALOR DA LINHA E COLUNA
				p.setValue(position.getRow() +1 , position.getColumn() -1);
				
				while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
					
					mat[p.getRow()][p.getColumn()] = true;
					
					p.setValue(p.getRow() + 1, p.getColumn() -1);
				}
				//quando a repeticao acima terminar, vamos ver SE ainda existem CASAS(lugar para por PECA)
				//e TESTAR se essa casa EXISTE uma peca adversaria, se SIM vamos marcar essa casa como verdadeiro
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
					mat[p.getRow()][p.getColumn()] = true;
				}
		
		
		return mat;
	}
}

