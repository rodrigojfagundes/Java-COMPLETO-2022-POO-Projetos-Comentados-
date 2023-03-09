package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

//
//classe UI = User Interface
public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	//cores do texto... acho q das pecas do tabuleiro
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	//cores do fundo do tabuleiro... eu acho
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	
	//funcao simples para limpar a tela...
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
		public static void clearScreen() {
			System.out.print("\033[H\033[2J");
			System.out.flush();
		}	
	
	
	
	//metodo para ler uma posicao do usuario... usando o Scanner SC do Program.java para ler a posicao
	public static ChessPosition readChessPosition(Scanner sc) {
		//bloco TRY para excecoes
		try {
		//mandando ler uma string S com a posicao
		String s = sc.nextLine();
		//sei q ali é 0 pq comeca na linha 0 a coluna...
		char column = s.charAt(0);
		//lendo a linha
		int row = Integer.parseInt(s.substring(1));
		//aqui vamos retornar para a class ChessPosition a COLUNA e a LINHA
		return new ChessPosition(column, row);
		}
		//caso ocorra algm erro, vamos armazenar na var E
		catch (RuntimeException e) {
			//e vamos passar o erro padrao do java q e o erro de entrada de dados
			throw new InputMismatchException("erro reading chessposition valid values are from a1 to h8 ");
		}
	}
	//print match... imprimir a partida e a lista de pecas capturadas
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		//imprimindo o tabuleiro
		printBoard(chessMatch.getPieces());
		//imprimindo a lista de pieca capturadas
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		//imprimindo o turno(vez de qual jogador)
		System.out.println("Turn :" + chessMatch.getTurn());
		//testando SE NAO ta em checkmate, dai vamos esperar a proxima jogada
		if(!chessMatch.getCheckMate()) {
		System.out.println("waiting player " + chessMatch.getCurrentPlayer());
		//testar se esta em check, se sim avisar o jogador...
		//para isso vamos chamar a class chessMatch e o metodo getCheck
		if (chessMatch.getCheck()) {
			System.out.println("check!");
		}
		}
		//Se tiver em checkmate, vai exiir a mensagem na tela
		else {
			System.out.println("check");
			System.out.println("winner " + chessMatch.getCurrentPlayer());
		}
	}

	// criando o metodo PRINT BOARD para imprimir o tabuleiro...
	public static void printBoard(ChessPiece[][] pieces) {
		// for para percorrer as linhas
		for (int i = 0; i < pieces.length; i++) {
			// esse codigo a baixo 8 - i é pq a MATRIZ do PC comeca com 0... e a nossa nos
			// queremos q
			// comece com o numero 1...
			System.out.print((8 - i) + " ");
			// for para percorrer as colunas do tabuleiro
			for (int j = 0; j < pieces.length; j++) {
				// imprimindo a PEÇA ... chamando o metodo/funcao a baixo o PRINTPIECE... false pq PECE nao tem
				//fundo colorido
				printPiece(pieces[i][j], false);
			}
			// para fazer quebra de linha
			System.out.println();
		}
		// imprimindo as letras da ultima linha
		System.out.println("  a b c d e f g h");
	}

	
	// FAZENDO UMA SOBRECARGA  do metodo PRINT BOARD para imprimir o tabuleiro...
	//e desta vez, vai IMPRIMIR OS MOVIMENTOS POSSIVEIS DE QUANDO UMA PECA E SELECIONADA
		public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
			// for para percorrer as linhas
			for (int i = 0; i < pieces.length; i++) {
				// esse codigo a baixo 8 - i é pq a MATRIZ do PC comeca com 0... e a nossa nos
				// queremos q
				// comece com o numero 1...
				System.out.print((8 - i) + " ");
				// for para percorrer as colunas do tabuleiro
				for (int j = 0; j < pieces.length; j++) {
					// imprimindo a PEÇA ... chamando o metodo/funcao a baixo o PRINTPIECE
					//SE POSSIBLEMOVES for TRUE, vamos pintar o fundo de colorido (e indicar onde pd se mover)
					printPiece(pieces[i][j], possibleMoves[i][j]);
				}
				// para fazer quebra de linha
				System.out.println();
			}
			// imprimindo as letras da ultima linha
			System.out.println("  a b c d e f g h");
		}

	// metodo/funcao para imprimir as peca
	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
	// se NAO tiver peca no local... dai vai imprimir um -
	    	if (piece == null) {
	            System.out.print("-" + ANSI_RESET);
	        }
	// Se tiver peca... dai vai imprimir a peca q ta em PIECE
	        else {
	//se a PIECE/PECA for BRANCA... dai vamos chamar o ANSI_WHITE q fizemos la em cima
	            if (piece.getColor() == Color.WHITE) {
	                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
	            }
	            else {
	//se a PIECE/PECA for PRETA dai vamos chamar o ANSI_YELLOW
	                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
	            }
	        }
	// imprimindo um espaco entre as pecas
	        System.out.print(" ");
		}
	//metodo q recebe uma LISTA de peças de XADRES com as pecas capturadas
	//e imprimir elas na tela
	private static void printCapturedPieces(List<ChessPiece> captured) {
		//crianod ua lista com as pecas branca capturadas
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		//crianod ua lista com as pecas preta capturadas
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
	//imprimindo as listas na tela
		System.out.println("captured pieces: ");
		System.out.print("white: ");
		//imprimind na cor branca
		System.out.print(ANSI_WHITE);
		//imprimindo um array de valores
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		
		//imprimindo a lista de pecas preta
		System.out.print("black: ");
		//imprimind na cor preta
		System.out.print(ANSI_BLACK);
		//imprimindo um array de valores
		System.out.println(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
	}
	
	
}