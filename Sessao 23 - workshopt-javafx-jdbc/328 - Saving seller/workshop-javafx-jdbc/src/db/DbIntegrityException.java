//
//OS COMENTARIOS DESSE CODIGO/CLASSE ESTAO NA SESSAO 22... POIS O PROF NELIO
//NAO ESCREVEU O CODIGO/CLASSE DO ZERO... ELE APENAS COPIOU DA SESSAO 22


package db;

public class DbIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DbIntegrityException(String msg) {
		super(msg);
	}
}
