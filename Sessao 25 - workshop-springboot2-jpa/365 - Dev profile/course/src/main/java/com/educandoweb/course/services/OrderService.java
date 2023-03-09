package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.repositories.OrderRepository;

//
//classe orderService
//teremos duas OPERAÇÔES/METODOS basicos... Buscar TODOS PEDIDOS, e BUSCAR PEDIDOS por
//ID.......... o @Service e para dizer q essa classe e um SERVICE do SRING
//e assim ela podera ser injetada com o AUTOWIRED...
@Service
public class OrderService {
	
	//Depedencia para a INTERFACE ORDERREPOSITORY, q iremos chamar de REPOSITORY
	@Autowired
	private OrderRepository repository;
	
	//metodo para RETORNAR TODOS PEDIDOS DO BANCO
	public List<Order> findAll(){
		//chamando o nosso OBJETO REPOSITORY do tipo ORDERREPOSITORY que HERDA a CLASSE
		//JPAREPOSITORY... E essa CLASSE JPAREPOSITORY tem um METODO que é o FINDALL
		//Dai nos CHAMAMOS o METODO FINDALL q esta no JPAREPOSITORY e pedimos para trazer
		//todos ORDERS cadastrados no BANCO
		return repository.findAll();
	}
	
	//criando o metodo FINDBYID para retornar um PEDIDO/ORDER q corresponde a ID passada
	public Order findById(Long id) {
		//chamando o nosso OBJETO REPOSITORY do tipo USERREPOSITORY que HERDA a CLASSE
		//JPAREPOSITORY... E essa CLASSE JPAREPOSITORY tem um METODO que é o FINDBYID
		//Dai nos CHAMAMOS o METODO FINDBYID q esta no JPAREPOSITORY e pedimos para trazer
		//o ODER da ID q foi passada pelo USUARIO...
			//e ele(ORDER) vai ser armazenado dentro do OBJETO OBJ que é um OBJETO q recebe
			//um ORDER e é do tipo OPTIONAL
		Optional<Order> obj = repository.findById(id);
		//retornando o OBJ
		return obj.get();
	}
}
