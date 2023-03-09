package com.educandoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.course.entities.Order;

//o OrderRepository é o repositorio/INTERACE responsavel por fazer OPERACOES com a
//ENTIDADE/CLASSE ORDER
	//q herda o JPAREPOSITORY q pede um TIPO de ENTIDADE que é ORDER... e o tipo de
	//IDENTIFICADOR/ID é do tipo LONG (os ID chave primaria é LONG do ORDER)
public interface OrderRepository extends JpaRepository<Order, Long>{

}
