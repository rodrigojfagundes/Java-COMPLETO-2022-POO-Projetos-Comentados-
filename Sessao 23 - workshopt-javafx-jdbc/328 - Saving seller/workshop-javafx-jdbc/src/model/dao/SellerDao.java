//
//OS COMENTARIOS DESSE CODIGO/CLASSE ESTAO NA SESSAO 22... POIS O PROF NELIO
//NAO ESCREVEU O CODIGO/CLASSE DO ZERO... ELE APENAS COPIOU DA SESSAO 22

package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {

	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
}
