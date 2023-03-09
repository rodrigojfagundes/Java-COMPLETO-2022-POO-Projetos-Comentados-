package com.educandoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.course.entities.User;

//o UserRepository é o repositorio/INTERACE responsavel por fazer OPERACOES com a
//ENTIDADE/CLASSE USER
	//q herda o JPAREPOSITORY q pede um TIPO de ENTIDADE que é USER... e o tipo de
	//IDENTIFICADOR/ID é do tipo LONG (os ID chave primaria é LONG do USER)
public interface UserRepository extends JpaRepository<User, Long>{

}
