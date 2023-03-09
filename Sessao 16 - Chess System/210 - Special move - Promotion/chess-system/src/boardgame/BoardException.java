package boardgame;


//criando a classe BOARDEXCEPTION... Q EXTEND/HERDA de RuntimeException
public class BoardException extends RuntimeException{
	//nao sei para que ser o serial version uid ali de baixo...
	private static final long serialVersionUID = 1L;
	
	//criando o metodo/funcao BoardException q vai receber uma msg de excessao
	public BoardException(String msg) {
		//e vai re passar essa mensagem para a CLASSE MAE/SUPER CLASSE no caso o RuntimeException
		super(msg);
	}
}
