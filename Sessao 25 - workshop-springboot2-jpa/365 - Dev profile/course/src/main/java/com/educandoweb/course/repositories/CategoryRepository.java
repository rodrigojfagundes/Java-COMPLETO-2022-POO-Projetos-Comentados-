package com.educandoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.course.entities.Category;

//o CategoryRepository é o repositorio/INTERACE responsavel por fazer OPERACOES com a
//ENTIDADE/CLASSE CATEGORY
	//q herda o JPAREPOSITORY q pede um TIPO de ENTIDADE/CLASSE que é CATEGORY... e o tipo de
	//IDENTIFICADOR/ID é do tipo LONG (os ID chave primaria é LONG do CATEGORY)
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
