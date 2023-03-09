package com.educandoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.course.entities.Product;

//o ProductRepository é o repositorio/INTERACE responsavel por fazer OPERACOES com a
//ENTIDADE/CLASSE PRODUCT
	//q herda o JPAREPOSITORY q pede um TIPO de ENTIDADE/CLASSE que é CATEGORY... e o tipo de
	//IDENTIFICADOR/ID é do tipo LONG (os ID chave primaria é LONG do CATEGORY)
public interface ProductRepository extends JpaRepository<Product, Long>{

}