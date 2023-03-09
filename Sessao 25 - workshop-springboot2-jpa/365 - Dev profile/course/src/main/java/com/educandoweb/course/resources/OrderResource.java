package com.educandoweb.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.services.OrderService;

//criando o OrderRESOURCE que disponibiliza um RECURSO WEB correspondente a ENTIDADE/class ORDER 
//que vai disponibilizar 2 ENDPOINTS para nos PEGAR/RECUPERAR os pedidos CADASTRADOS
//e tbm o pedido informando o ID dele...
@RestController
//como o recurso q vamos requerer e relacionado a ORDER(OrderResource) entao o caminho é
// /orders... Ou seja quando DIGITARMOS localhost:8080/ORDERS no navegador vai chamar esse metodo
@RequestMapping(value = "/orders")
public class OrderResource {
	
	//criando uma DEPEDENCIA para a CLASSE ORDERSERVICE, q vamos chamar de SERVICE
	@Autowired
	private OrderService service;
	
	//metodo FINDALL do tipo ResponseEntity que é um ENDPOINT para acessar os pedidos... 
	 //esse  METODO ResponseEntity, VAI RETORNAR um VALOR do tipo LIST de ORDER
			//o GETMAPPING e para dizer q responde a REQUISICAO GET DE HTTP
	@GetMapping
	public ResponseEntity<List<Order>> findAll(){		
		//declarando uma LISTA de PEDIDOS, q RECEBE o SERVICO do nosso FINDALL
		//e ESSA LISTA vai receber o OBJETO SERVICE(q é do tipo ORDERSERVICE) e vamos chamar
		//o metodo q ela tem q é o FINDALL... Para pegar os ORDERS
		List<Order> list = service.findAll();
		
		//retornando... o RESPONSE ENTITY OK. e no CORPO/BODY passando a LISTA q criamos ali
		//em cima
		return ResponseEntity.ok().body(list);
	}
	
	//criando um ENDPOIT/METODO para Buscar o PEDIDO por ID
		//o @GETMAPPING e por causa q vai ser para uma REQUISICAO do tipo GET
		//e o VALUE e para dizer q na URL no navegador vai ter o ID do pedido q quermos
	@GetMapping(value = "/{id}")
	//criando o METODO FINDBYID... do tipo RESPONSEENTITY q vai RETORNAR um ORDER
	//esse metodo FINDBYID vai receber o ID em formato de LONG ID
		//o @PathVariable é uma ANNOTATION do Spring
	public ResponseEntity<Order> findById(@PathVariable Long id){
		//criando uma VARIAVEL OBJ do TIPO USER q recebe o METODO FINDBYID q ta no
		//JPA que é HERDADO pelo OBJETO SERVICE que é um OBJETO do tipo OrderService(declarado
		//ali em cima)
		Order obj = service.findById(id);
		//retornando o OBJETO/USUARIO encontrado pelo o ID :)
		return ResponseEntity.ok().body(obj);
		
	}
	
}
