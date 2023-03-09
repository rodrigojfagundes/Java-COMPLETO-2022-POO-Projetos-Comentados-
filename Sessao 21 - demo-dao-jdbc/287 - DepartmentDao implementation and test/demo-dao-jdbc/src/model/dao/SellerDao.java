package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

//criando INTERFACE SellerDAO
public interface SellerDao {
	
		//declarando as OPERACOES q tem q ser feitas em uma CLASSE q IMPLEMENTA
		//essa INTERFACE aqui... No caso a Classe SellerDaoJDBC vai ter q ter os
		//metodos a baixo... e executar algo neles...
	
		void insert(Seller obj);
		
		
		void update(Seller obj);
		
		
		void deleteById(Integer id);
		
		
		Seller findById(Integer id);
		
		
		List<Seller> findAll();
		
		
		List<Seller> findByDepartment(Department department);
		
	
	
}
