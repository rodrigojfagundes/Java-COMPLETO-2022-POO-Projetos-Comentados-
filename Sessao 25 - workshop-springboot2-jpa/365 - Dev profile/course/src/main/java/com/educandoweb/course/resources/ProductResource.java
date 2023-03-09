package com.educandoweb.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.Product;
import com.educandoweb.course.services.ProductService;

//criando o ProductRESOURCE que disponibiliza um RECURSO WEB correspondente a ENTIDADE/class PRODUCT 
//que vai disponibilizar 2 ENDPOINTS para nos PEGAR/RECUPERAR os produtos CADASTRADOS
//e tbm o produto informando o ID dele...
@RestController
//como o recurso q vamos requerer e relacionado a PRODUCTS(ProductResource) entao o caminho é
// /products... Ou seja quando DIGITARMOS localhost:8080/PRODUCTS no navegador vai chamar esse metodo
@RequestMapping(value = "/products")
public class ProductResource {
	
	//criando uma DEPEDENCIA para a CLASSE PRODUCTSERVICE, q vamos chamar de SERVICE
	@Autowired
	private ProductService service;
	
	//metodo FINDALL do tipo ResponseEntity que é um ENDPOINT para acessar os produtos... 
	 //esse  METODO ResponseEntity, VAI RETORNAR um VALOR do tipo LIST de PRODUCT
			//o GETMAPPING e para dizer q responde a REQUISICAO GET DE HTTP
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){		
		//declarando uma LISTA de PRODUTOS, q RECEBE o SERVICO do nosso FINDALL
		//e ESSA LISTA vai receber o OBJETO SERVICE(q é do tipo PRODUCTSERVICE) e vamos chamar
		//o metodo q ela tem q é o FINDALL... Para pegar os PRODUCTS
		List<Product> list = service.findAll();
		
		//retornando... o RESPONSE ENTITY OK. e no CORPO/BODY passando a LISTA q criamos ali
		//em cima
		return ResponseEntity.ok().body(list);
	}
	
	//criando um ENDPOIT/METODO para Buscar o Produto por ID
		//o @GETMAPPING e por causa q vai ser para uma REQUISICAO do tipo GET
		//e o VALUE e para dizer q na URL no navegador vai ter o ID do produto q quermos
	@GetMapping(value = "/{id}")
	//criando o METODO FINDBYID... do tipo RESPONSEENTITY q vai RETORNAR um PRODUCT
	//esse metodo FINDBYID vai receber o ID em formato de LONG ID
		//o @PathVariable é uma ANNOTATION do Spring
	public ResponseEntity<Product> findById(@PathVariable Long id){
		//criando uma VARIAVEL OBJ do TIPO PRODUCT q recebe o METODO FINDBYID q ta no
		//JPA que é HERDADO pelo OBJETO SERVICE que é um OBJETO do tipo ProductService(declarado
		//ali em cima)
		Product obj = service.findById(id);
		//retornando o OBJETO/PRODUTOS encontrado pelo o ID :)
		return ResponseEntity.ok().body(obj);
		
	}
	
}
