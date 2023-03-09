package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//criando um objeo do tipo CHESSMATH
		ChessMatch chessMatch = new ChessMatch();
		//declarando a lista de pecas... para saber as q foram pegas pelo jogador
		List<ChessPiece> captured = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);
		while(!chessMatch.getCheck()) {
		//bloco try para erros e excecoes
		try {	
		//antes de imprimir o tabuleiro, nos vamos chamar o metodo q limpa a tela
		UI.clearScreen();
		
		//chamando a classe UI e o metodo PrintBoard para imprimir o tabuleiro... UI = User Interface
		//a chessMath e meio q o inicio da partida o tabuleiro e tals... E o Captured e a lista de pecas
		//capturadas
		UI.printMatch(chessMatch, captured);
		System.out.println();
		//posicao de origem
		System.out.print("source");
		//lendo a posicao de origem
		ChessPosition source = UI.readChessPosition(sc);
		
		//criando uma matriz de valores booleano q vai receber os movimentos q sao possiveis de realizar
		//a partir da ORIGEM/SOURCE e TARGET/DESTINO de uma piece
		boolean[][] possibleMoves = chessMatch.possibleMoves(source);
		UI.clearScreen();
		//fazendo uma sobrecarga imprimindo o tabuleiro novamente so q COM AS POSICOES POSSIVEIS DE MOVIMENTO
		UI.printBoard(chessMatch.getPieces(), possibleMoves);
		
		
		System.out.println();
		//posicao de destino
		System.out.println("target");
		ChessPosition target = UI.readChessPosition(sc);
		
		//passando as informacoes q estao na var SOURCE e TARGET para 
		//sempre q fizer um movimento... e o movimento resultar em peca capturada vamos add eles
		//ali na lista capturedPiece
		ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		
		//se a peca capturada FOR DIFERENTE de NULLO, significa q alguma peca foi capturada
		if (capturedPiece != null) {
			//add pieca a lista de capturada
			captured.add(capturedPiece);
		}
		
		//mensagem para o usuario digitar a piece promovida
		if(chessMatch.getPromoted() !=null) {
			System.out.print("enter piece for promotion (B/N/R/Q) ");
			String type = sc.nextLine();
			chessMatch.replacePromotedPiece(type);
		}
		
		}
		//caso ocorra uma excessao vai exibir a mensagem de erro
		catch(ChessException e) {
			System.out.print(e.getMessage());
			sc.nextLine();
		}
		catch(InputMismatchException e) {
			System.out.print(e.getMessage());
			sc.nextLine();
		}
	}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
}

}
