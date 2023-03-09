package com.educandoweb.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.services.UserService;

//criando o UserRESOURCE que disponibiliza um RECURSO WEB correspondente a ENTIDADE/class USER 
//que vai disponibilizar 2 ENDPOINTS para nos PEGAR/RECUPERAR os usuarios CADASTRADOS
//e tbm o usuario informando o ID dele...
@RestController
//como o recurso q vamos requerer e relacionado a USERS(UserResource) entao o caminho é
// /users... Ou seja quando DIGITARMOS localhost:8080/USERS no navegador vai chamar esse metodo
@RequestMapping(value = "/users")
public class UserResource {
	
	//criando uma DEPEDENCIA para a CLASSE USERSERVICE, q vamos chamar de SERVICE
	@Autowired
	private UserService service;
	
	//metodo FINDALL do tipo ResponseEntity que é um ENDPOINT para acessar os usuarios... 
	 //esse  METODO ResponseEntity, VAI RETORNAR um VALOR do tipo LIST de USER
			//o GETMAPPING e para dizer q responde a REQUISICAO GET DE HTTP
	@GetMapping
	public ResponseEntity<List<User>> findAll(){		
		//declarando uma LISTA de USUARIO, q RECEBE o SERVICO do nosso FINDALL
		//e ESSA LISTA vai receber o OBJETO SERVICE(q é do tipo USERSERVICE) e vamos chamar
		//o metodo q ela tem q é o FINDALL... Para pegar os USERS
		List<User> list = service.findAll();
		
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
	public ResponseEntity<User> findById(@PathVariable Long id){
		//criando uma VARIAVEL OBJ do TIPO USER q recebe o METODO FINDBYID q ta no
		//JPA que é HERDADO pelo OBJETO SERVICE que é um OBJETO do tipo UserService(declarado
		//ali em cima)
		User obj = service.findById(id);
		//retornando o OBJETO/USUARIO encontrado pelo o ID :)
		return ResponseEntity.ok().body(obj);
	}
	
	//usando o metodo POST para INSERIR um usuario no banco, 
	@PostMapping
	//criando o metodo INSET do TIPO RESPONSEENTITY q RECEBE um USER
	//e vai inserir esse OBJ do tipo USER no BANCO
	public ResponseEntity<User> insert(@RequestBody User obj){
		//o nosso OBJ que é um objeto do TIPO USER vai receber SERVICE que é uma 
		//DEPEDENCIA DO TIPO USERSERVICE (q ta declarado ali em cima) e apartir do SERVICE
		//nos vamos chamar o metodo INSERT e passar o OBJ do tipo USER
		obj = service.insert(obj);
		//pegando o endereco de onde o novo recurso foi inserido... para retornar
		//um valor 201... NAO É MUITO NESCESSARIO O CODIGO A BAIXO...
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	//criando um ENDPOINT para deletar um usuario...
		//no PADRAO REST o metodo HTTP q se usa para DELETAR é o DELETE
	//entao o ANNOTATION no SPRING BOOT é o @DELETEMAPPING
	@DeleteMapping(value = "/{id}")
	//criando o metodo ResponseEntity do tipo VOID (pois nao vai retornar nada)
	//e o nome do metodo vai ser DELETE... e o metodo vai receber um LONG ID
	public ResponseEntity<Void> delete(@PathVariable Long id){
		//chamando o nosso SERVICE que é uma INJECAO DE DEPENDENCIA
		//ou uma VARIAVEL nao sei SHAIHsia... Do tipo USERSERVICE
		//e chamando o metodo DELETE e passando o ID
		service.delete(id);
		//retornando a resposta SEM CORPO, pois é VOID...
		//a resposta no caso é o CODIGO HTTP de delecao que é o 204
		return ResponseEntity.noContent().build();
	}
	
	//criando o metodo para ATUALIZAR os DADOS DO USUARIO
	//no PADRAO REST para ATUALIZAR UM RECURSO o METODO HTTP é o
	//PUT
	@PutMapping(value = "/{id}")
	//criando um metodo RESPONSEENTITY do tipo USER, pois vai RETORNAR UM USUARIO
	//ATUALIZADO, o nome do metodo é UPDATE... os parametros sao o ID do user
	//q queremos atualizar... E um OBJ com os DADOS Q PASSAMOS PARA ATUALIZAR
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj){
		//usando a variavel OBJ para receber o o metodo UPDATE do SERVICE
		//e passando o ID e o OBJ q sera atualizado
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}