package com.educandoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.course.entities.OrderItem;

//o OrderItemRepository é o repositorio/INTERACE responsavel por fazer OPERACOES com a
//ENTIDADE/CLASSE OrderItem
	//q herda o JPAREPOSITORY q pede um TIPO de ENTIDADE que é OrderItem... e o tipo de
	//IDENTIFICADOR/ID é do tipo LONG
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
