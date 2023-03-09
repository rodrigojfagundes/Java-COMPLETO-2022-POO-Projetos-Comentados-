package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.repositories.CategoryRepository;

//
//classe CategoryService
//teremos duas OPERAÇÔES/METODOS basicos... Buscar TODAS CATEGORIAS, e BUSCAR CATEGORIAS por
//ID.......... o @Service e para dizer q essa classe e um SERVICE do SRING
//e assim ela podera ser injetada com o AUTOWIRED...
@Service
public class CategoryService {
	
	//Depedencia para a INTERFACE CATEGORYREPOSITORY, q iremos chamar de REPOSITORY
	@Autowired
	private CategoryRepository repository;
	
	//metodo para RETORNAR TODAS CATEGORIAS DO BANCO
	public List<Category> findAll(){
		//chamando o nosso OBJETO REPOSITORY do tipo CATEGORYREPOSITORY que HERDA a CLASSE
		//JPAREPOSITORY... E essa CLASSE JPAREPOSITORY tem um METODO que é o FINDALL
		//Dai nos CHAMAMOS o METODO FINDALL q esta no JPAREPOSITORY e pedimos para trazer
		//todos CATEGORY cadastrados no BANCO
		return repository.findAll();
	}
	
	//criando o metodo FINDBYID para retornar um USUARIO/USER q corresponde a ID passada
	public Category findById(Long id) {
		//chamando o nosso OBJETO REPOSITORY do tipo CATEGORYEPOSITORY que HERDA a CLASSE
		//JPAREPOSITORY... E essa CLASSE JPAREPOSITORY tem um METODO que é o FINDBYID
		//Dai nos CHAMAMOS o METODO FINDBYID q esta no JPAREPOSITORY e pedimos para trazer
		//o CATEGORY da ID q foi passada pelo USUARIO...
			//e ele(CATEGORY) vai ser armazenado dentro do OBJETO OBJ que é um OBJETO q recebe
			//um CATEGORY e é do tipo OPTIONAL
		Optional<Category> obj = repository.findById(id);
		//retornando o OBJ
		return obj.get();
	}
}
