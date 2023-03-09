//
//OS COMENTARIOS DESSE CODIGO/CLASSE ESTAO NA SESSAO 22... POIS O PROF NELIO
//NAO ESCREVEU O CODIGO/CLASSE DO ZERO... ELE APENAS COPIOU DA SESSAO 22

package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
