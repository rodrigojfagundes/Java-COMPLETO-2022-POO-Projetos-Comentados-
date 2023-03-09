//
//OS COMENTARIOS DESSE CODIGO/CLASSE ESTAO NA SESSAO 22... POIS O PROF NELIO
//NAO ESCREVEU O CODIGO/CLASSE DO ZERO... ELE APENAS COPIOU DA SESSAO 22


package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
