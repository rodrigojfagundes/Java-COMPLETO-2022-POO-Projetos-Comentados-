package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

//essa classe vai ter as REGRAS da partida de XADREZ
public class ChessMatch {
	

	
	//turn para mudar o turno entre o jogador 1 e jogador 2
	private int turn;
	private Color currentPlayer;
	//declarando um Board, pois precisamos ter um tabuleiro
	private Board board;
	//propiedade boolean para saber se ta em CHECK ou NAO
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	//lista de pecas q estao no tabuleiro
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	//matriz de pecas q foram pegas
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		//passando as dimencoes para o tabuleiro de xadrez
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		//chamando a funcao initialSetup para iniciar a partida
		initialSetup();
	}
	
	//metodo GET de turn e current player
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer(){
		return currentPlayer;
	}
	
	//criando um get para termos acesso para saber se ta em CHECK ou NAO
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return check;
	}
	
	
	
	//get para ver o valor da enPassantVulnerable
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	//
	//criando o metodo ChessPiece q vai retornar uma MATRIZ de pecas de XADREZ correspondente
	//aos valores q foram passados no BOARD ali de cima
	public ChessPiece[][] getPieces (){
		//chesspiece vai ser uma matriz q vai receber os valores de LINHA e de COLUMN da class BOARD
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getRows(); j++) {
				//fazendo um DOWNCASTING (ChessPiece) para interpretar como uma PECA de XADREZ
				//e nao como uma peca comum
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		//apos fazer os 2 for ali de cima para criar as LINHAS e COLUNAS conforme os valores q estao
		//na class BOARD... vamos retornar a matriz MAT
		return mat;
	}
	
	//funcao/metodo como indicador de para QUAIS lado a piece pode se mover
	//imprimir as possicoes possiveis a partir de uma posicao de origem
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		//validando a posicao
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	
	//metodo/funcao PERFORMCHESSMOVE q serve para retornar uma posicao capturada
	//metodo publico q vai RETORNAR um CHESSPIECE (do tipo CHESSPIECE)
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		//convertendo o ChessPosition e sourcePosition para posicoes da matriz
			//no SOURCE temos a POSICAO INICIAL da PECA
		Position source = sourcePosition.toPosition();
		//no TARGET temos a posicao de DESTINO da PECA
		Position target = targetPosition.toPosition();
		//verificar se na poosicao de origem tem alguma peca
		validateSourcePosition(source);
		//validando a posicao de destino
		validateTargetPosition(source, target);
		//variavel CAPTUREDPIECE q vai receber o resultado do movimento de PIECE/PECA pela funcao
		//MakeMove q recebe uma ORIGEM da PECA e o DESTINO da PIECE
		Piece capturedPiece = makeMove(source, target);
		
		//testar SE o movimento realizado não colocou o propio jogador em check
		if (testCheck(currentPlayer)) {
			//caso sim, teremos q desfazer o movimento feito... pois o jogador nao pode se colocar em check
			undoMove(source, target, capturedPiece);
			throw new ChessException("you can't put yourself in check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		
		// #specialmove PROMOTION... ocorre quando o PEAO atravessa todo o tabuleiro e chega no outro canto
		//do tabuleiro
		
		promoted = null;
		if(movedPiece instanceof Pawn) {
			//se a peca PEAO for BRANCA e ela tiver na LINHA de comeco das PECAS PRETAS...
			//significa q o PEAO BRANCO ATRAVESOU todo O TABULEIRO
			if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece)board.piece(target);
				//o promoted q e a peca promovida no caso e um peao... e vai poder se transformar em outra
				//peca... POR PADRAO ESTAMOS TROCANDO PELA LETRA Q OU SEJA PELA RAINHA
				promoted = replacePromotedPiece("Q");
				
			}
		}
		
		//verificando se o OPONENTE ficou em CHECK apos fazer a jogada
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		//testando SE a jogada q foi feita deiou o jogador em checkmate, caso sim o jogo termina
		//metodo q faz isso
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		//caso nao seja checkmate, vamos chamar a proxima jogada
		else {
		//chamando o metodo/funcao q faz a mudanca de jogador
		nextTurn();
		}
		//testar se a peca movida foi um peao q moveu 2 casas... se FOR
		//ele e possivel de ENPASSANT
		//#specialmove enpassant
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			//entao vamos avisar q essa piece/peca pode levar um ENPASSANT
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}
		
		//retornando a peca capturada
		return (ChessPiece) capturedPiece;
	}
	
	//metodo q vai receber a LETRA da PECA q queremos trocar pelo PEAO Q FOI PROMOVIDO
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null) {
			throw new IllegalStateException("there is no piece to be promoted");
		}
		//verificando se a letra q foi recebida no metodo e valida
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
			throw new InvalidParameterException("invalid type for promotion");
		}
		//pegamos a posicao do PEAO e removemos ele do tabuleiro... Pois ele ira sair e no lugar
		//ira ficar a PECA nova q o usuario escolheu
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		//vamos add a nova peca q foi escolhida no tabuleiro
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
		
		
	}
	
	//conforme a letra q o usuario passou, nos vamos substituir o PEAO PROMOVIDO por uma das opcoes
	//a baixo
	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("N")) return new Knight(board, color);
		if (type.equals("Q")) return new Queen(board, color);
		return new Bishop(board, color);
	}
	
	//criando o metodo MakeMove... Recebendo uma posicao LINHA e COLUNA de Origem e de Destino
	private Piece makeMove(Position source, Position target) {
		//estamos REMOVENDO a PIECE P da posicao de ORIGEM
		ChessPiece p = (ChessPiece) board.removePiece(source);
		//estamos chamando o metodo INCREASE MOVE COUNT q ta na class ChessPiece e ADD + 1 MOVIMENTO
		p.increaseMoveCount();
		//e estamos removendo a POSSIVEL peca q esteja na posicao de destino
		Piece capturedPiece = board.removePiece(target);
		//agora vamos por a PECA P q estava na POSICAO de ORIGEM... Na sua posicao de DESTINO(target)
		board.placePiece(p, target);		
		//se a PEICE capturada for diferente de NULL entao
		if(capturedPiece != null) {
			//vamos pegar a PIECE capturada q TA ARMAZENADA NA VAR CAPTUREDPIECE e vamos remover ela da
			//LISTA PIECEONTHEBOARD
			piecesOnTheBoard.remove(capturedPiece);
			//e vamos ADD essa PIECE q foi capturada na LISTA de PECAS CAPTURADA
			capturedPieces.add(capturedPiece);
		}
		//metodo/funcao para testar se o jogador moveu 2 casas para direita é um rook pequeno
		//se o jogador moveu o rei 2 casas para a esquerda e um rook grande, entao tbm tem q mover a torre
		
		// #specialmove castrling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			//SE caiu nesse IF entao e um ROOK pequeno
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() +1);
			//acho q no processo ele move a torre tbm, q no casso e o ROOK ai
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook,  targetT);
			rook.increaseMoveCount();
		}
		
		// #specialmove castrling queenside rook
		//rook grande
		//se a piece movida for um rei e ele moveu 2 casas paa a ESQUERDA
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			//pegando a posicao de origem da torre
			Position sourceT = new Position(source.getRow(), source.getColumn() -4);
			//pegando a posicao de destino dessa torre
			Position targetT = new Position(source.getRow(), source.getColumn() -1 );
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook,  targetT);
			rook.increaseMoveCount();
		}
		
		// # especialMove EN PASSANT
		//vamos testar se a peca q foi movida e uma INSTANCIA de PEAO PAWN
		if (p instanceof Pawn) {
			//significa q mudou de coluna, entao andou na diagonal... o PEAO so ANDA NA DIAGONAL quando
			//ele CAPTURA UMA PECA ADVERSARIA... MAS SE ELE (PEAO) ANDOU NA DIAGONAL E NAO CAPTUROU PECA
			//SIGNIFICA Q FEZ UM EN PASSANT
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				//descobrir se foi capturado um peao a direita ou a esquerda
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				//removendo a piece q foi capturada do tabuleiro
				capturedPiece = board.removePiece(pawnPosition);
				//add o peao a lista de pecas capturadas
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		
		
		
		return capturedPiece;
	}
	//metodo para DESFAZER o movimento POIS O JOGADOR NAO PODE SE AUTO-FAZER CHECK-MATE
	private void undoMove (Position source, Position target, Piece capturedPiece) {
		//tirando a piece q removeu do destino
		ChessPiece p = (ChessPiece)board.removePiece(target);
		//quando um movimento de uma PIECE for DESFEITO, vamos ter q REDUZIR/TIRAR 1 movimento da nossa
		//variavel MOVIMENTO, com o P.DECREASEMOVECOUNT
		p.decreaseMoveCount();
		//e add ela na posicao de ORIGEM/SOURCE novamente
		board.placePiece(p, source);
		
		//se foi capturada uma peca? vamos voltar a peca captuda para posicao de destino
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			//tirando ela da lista de pecas capturada, e colocando na lista de pecas no tabuleiro
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// DESFAZENDO O MOVIMENTO DE ROOK 
		//
		// #specialmove castrling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			//SE caiu nesse IF entao e um ROOK pequeno
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() +1);
			//acho q no processo ele move a torre tbm, q no casso e o ROOK ai
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook,  sourceT);
			rook.decreaseMoveCount();
		}
		
		// #specialmove castrling queenside rook
		//rook grande
		//se a piece movida for um rei e ele moveu 2 casas paa a ESQUERDA
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			//pegando a posicao de origem da torre
			Position sourceT = new Position(source.getRow(), source.getColumn() -4);
			//pegando a posicao de destino dessa torre
			Position targetT = new Position(source.getRow(), source.getColumn() -1 );
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook,  sourceT);
			rook.decreaseMoveCount();
		}
		
		
		//
		//	DESFAZENDO O EN PASSANT
		//
		// # especialMove EN PASSANT
		//vamos testar se a peca q foi movida e uma INSTANCIA de PEAO PAWN
		if (p instanceof Pawn) {

			if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				//descobrir se foi capturado um peao a direita ou a esquerda
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}				
				board.placePiece(pawn, pawnPosition);
			}
		}
		
		
		
	}
	
	//metodo/funcao validate source position, q recebe uma Posicao do tipo Position
	private void validateSourcePosition(Position position) {
		//se nao existir uma peca nessa posicao
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("there is no piece on source position");
		}
		//pegando a peca de tabuleiro na posicao e testando a cor dela
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			//excessao caso o jogador esteja tentando mover uma peca do oponente
			throw new ChessException("the chosen piece is not yours");
		}
		
		//IF para verificar SE a posicao de ORIGEM é valida...
			//SE nao tiver movimento possivel com a verificacao a baixo... vamos lancar uma excessao
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("there is no possible moves for the chosen piece");
			
		}
	}
	
	//funcoa/metodo para verificar se a POSICAO e VALIDA
	private void validateTargetPosition(Position source, Position target) {
		//verificando SE a posicao de DESTINO/TARGET é uma posicao POSSIVEL em relacao a POSICAO de ORIGEM/SOURCE
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("the chosen piece can't move to target position");
		}
	}
	
	//metodo q vai fazer a troca de turnos... Ou seja alterações entre JOGADOR 1 e JOGADOR 2...
	private void nextTurn() {
		turn++;
		//se o jogador tava com a peca preta agora e o jogador com a peca branca... e vise versa
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	//se a piece e preta o oponente da piece e branco e vise versa
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	//varrer a piece do jogo encontrando o REI daquela cor
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
	//para cada piece P na LISTA LIST
		for (Piece p : list) {
			//e verificando SE é a PIECE KING/REI
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		//SE por acaso o REI NAO FOR ENCONTRADO...
		throw new IllegalStateException("There is no " + color + "king on the board");
	}
	
	//metodo q vai varrer os possiveis movimentos de todas as pieces advesarias e ver se tem alguma
	//dessas pecas q podem atingir o rei, desta forma colocando o rei em check
	private boolean testCheck(Color color) {
		//pegando a posicao do rei... chamando o metodo rei, passando cor e pegando a posicao dele
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			//pegando a matriz de possibilidade de movimento das pecas
			boolean[][] mat = p.possibleMoves();
			//SE nessa matriz o resultado for TRUE, entao o rei ta em check
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				//entao o rei ta em check
				return true;
			}
		}
		//se percorrer o for e retornar false, entao o rei NAO ta em check
	return false;
	}
	//metodo para verificar se esta tendo checkmate
	private boolean testCheckMate(Color color) {
		//testar para ver se ele nao ta em check, pois se ele nao esta em CHECK, logo ele nao em checkmate
		if(!testCheck(color)) {
			return false;
		}
		//Lista do tipo PIECE, filtrando todas as pecas da mesma cor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			//pegando os movimentos possiveis
			boolean[][] mat = p.possibleMoves();
			//pegando as linhas da matriz
			for(int i=0; i<board.getRows(); i++) {
				//percorrendo as colunas da matriz
				for (int j=0; j<board.getColumns(); j++) {
					//verificando se esses valores de linha e coluna q estao percorrendo
					//da para fazer algum movimento...
					if(mat[i][j]) {
						//e se esses movimentos tiram do check
						//se tira do CHECK, retorna falso... ou seja NAO ta em check mate
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						//movendo a piece p da origem para o destino
						Piece capturedPiece = makeMove(source, target);
						//vamos testar se ainda ta em check
						//testando se o rei da mesma cor ta em check
						boolean testCheck = testCheck(color);
						//apos fazer o teste vamos desfaszer as operações para elas nao irem para o tabuleiro
						//POIS ESTAMOS APENAS TESTANDO SE NAO TEM SOLUCAO PARA O CHECK
						undoMove(source, target, capturedPiece);
						//se nao estava em check, significa q esse movimento tirou do checkmate,
						//e q tem solucao para nao ser checkmate :)
						if(!testCheck) {
							//false, entao NAO ESTA EM CHECKMATE
							return false;
						}
					}
				}
			}
		}
		//se apos percorrer a lista nao houver nenhuma peca da mesma cor q pode tirar o REI do checkmate
		//entao e true
		return true;
	}
	
	//neste metodo nos vamos passar as coordenadas do xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		//chamando a classe Board e o metodo PlacePiece e passando a PECA q ta em PIECE
		//chamando a class ChessPosition com o valor da posicao q vamos movimentar passando a linha e
		//coluna
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		//colocando a peca q foi instanciada dentro da lista de PECAS NO TABULEIRO
		piecesOnTheBoard.add(piece);
	}
	//criando um metodo que e responsavel por iniciar a partida de xadrez... Colocando as pecas
	//no tabuleiro
	private void initialSetup() {
		//instanciando as pecas...
		//chamando a classe BOARD e o metodo PLACEPIECE e passando o NOME da PECA e o LOCAL
		//
		//o metodo PLACENEWPIECE, esta recebendo a COLUNA c e LINHA 1 com um objeto 
		//do tipo ROOK/TORRE... e essa TORRE
		//vai com 2 valores, o primeiro é o TABULEIRO e o segundo VALOR é a COR da PECA...
		//dps nos instanciamos o valor da POSITION... Ou eja a LINHA e COLUNA para ONDE vai a PECA


		
		 	placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		 	placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		 	placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		 	placeNewPiece('d', 1, new Queen(board, Color.WHITE));
	        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
	        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
	        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
	        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
	        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
	        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

	        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
	        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
	        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
	        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
	        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
	        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
	        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
	        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
