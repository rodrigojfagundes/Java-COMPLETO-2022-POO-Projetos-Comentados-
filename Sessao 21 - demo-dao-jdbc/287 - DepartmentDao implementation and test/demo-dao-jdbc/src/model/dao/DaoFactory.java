package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

//fabrica de DAO, essa classe vai ter, OPERACOES ESTATICAS para instanciar
//os DAO
public class DaoFactory {
	
	//metodo para criar um novo SellerDao
	public static SellerDao createSellerDao() {
		//retornando um SellerDaoJDBC, com o GETCONNECTION q tem as INFO
		//para se conectar ao BANCO
		return new SellerDaoJDBC(DB.getConnection());
	}
	
}
