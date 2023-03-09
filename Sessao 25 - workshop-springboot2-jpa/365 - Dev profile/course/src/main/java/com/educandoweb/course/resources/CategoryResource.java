package com.educandoweb.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.services.CategoryService;

//criando o CategoryRESOURCE que disponibiliza um RECURSO WEB 
//correspondente a ENTIDADE/class Category 
//que vai disponibilizar 2 ENDPOINTS para nos PEGAR/RECUPERAR as categorias CADASTRADAS
//e tbm a categoris informando a ID dela...
@RestController
//como o recurso q vamos requerer e relacionado a CATEGORY(CategoryResource) entao 
//o caminho é /category... Ou seja quando DIGITARMOS localhost:8080/CATEGORIES no 
//navegador vai chamar esse metodo
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	//criando uma DEPEDENCIA para a CLASSE CATEGORYSERVICE, q vamos chamar de SERVICE
	@Autowired
	private CategoryService service;
	
	//metodo FINDALL do tipo ResponseEntity que é um ENDPOINT para acessar as categorias... 
	 //esse  METODO ResponseEntity, VAI RETORNAR um VALOR do tipo LIST de CATEGORY
			//o GETMAPPING e para dizer q responde a REQUISICAO GET DE HTTP
	@GetMapping
	public ResponseEntity<List<Category>> findAll(){		
		//declarando uma LISTA de CATEGORIA, q RECEBE o SERVICO do nosso FINDALL
		//e ESSA LISTA vai receber o OBJETO SERVICE(q é do tipo CATEGORYSERVICE) e vamos chamar
		//o metodo q ela tem q é o FINDALL... Para pegar os CATEGORY
		List<Category> list = service.findAll();
		
		//retornando... o RESPONSE ENTITY OK. e no CORPO/BODY passando a LISTA q criamos ali
		//em cima
		return ResponseEntity.ok().body(list);
	}
	
	//criando um ENDPOIT/METODO para Buscar o Usuario por ID
		//o @GETMAPPING e por causa q vai ser para uma REQUISICAO do tipo GET
		//e o VALUE e para dizer q na URL no navegador vai ter o ID do usuario q quermos
	@GetMapping(value = "/{id}")
	//criando o METODO FINDBYID... do tipo RESPONSEENTITY q vai RETORNAR um USER
	//esse metodo FINDBYID vai receber o ID em formato de LONG ID
		//o @PathVariable é uma ANNOTATION do Spring
	public ResponseEntity<Category> findById(@PathVariable Long id){
		//criando uma VARIAVEL OBJ do TIPO USER q recebe o METODO FINDBYID q ta no
		//JPA que é HERDADO pelo OBJETO SERVICE que é um OBJETO do tipo CategoryService(declarado
		//ali em cima)
		Category obj = service.findById(id);
		//retornando o OBJETO/USUARIO encontrado pelo o ID :)
		return ResponseEntity.ok().body(obj);
		
	}
	
}
