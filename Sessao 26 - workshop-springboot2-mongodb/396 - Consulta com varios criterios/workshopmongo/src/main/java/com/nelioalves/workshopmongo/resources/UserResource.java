//		INFORMACAO IMPORTANTE SOBRE TUDO NO PROJETO

//fazendo a CONEXAO com o MONGODB com o REPOSITORY e o SERVICES...
//(pois estamos trabalhando com CAMADAS)... 
//FRONT-END > solicita ao [back-end] >  Controladores REST(RESOURCERS) 
//ex: UserResource
//e os CONTROLADORES REST(resource) vao SOLICITAR 
//OS SERVICES/(Camada de SERVICOS) e esses SERVICOS (q sao metodos q 
//estao na CAMADA DE SERVICO)
//EX: metodos q estao dentro do UserService
//vao SOLICITAR os OBJ q estao na camada de ACESSO a DADOS os REPOSITORY...
//Ex: UserRepository


package com.nelioalves.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.workshopmongo.domain.Post;
import com.nelioalves.workshopmongo.domain.User;
import com.nelioalves.workshopmongo.dto.UserDTO;
import com.nelioalves.workshopmongo.services.UserService;

//classe USERRESOURCE dentro do SUBPACOTE RESOURCES,
//o nome é RESOURCE pois o nome tecnico para referenciar
//RECURSOS REST é RESOURCE... Ou seja são RECURSOS q o BACKEND
//vai DISPONIBILIZAR... tbm pd ser chamado de 
//CONTROLLER/CONTROLADOR

	//colocamos a ANNOTATION @restController para dizer q é um
	//recurso REST
@RestController
	//colocando o @RequestMapping para dizer qual e o caminho
	//do ENDPOINT... Ou seja o caminho q sera solicitado pelo
	//navegador... ex localhost:8080/users
@RequestMapping(value="/users")
public class UserResource {
	
	//o @AUTOWIRED e para instanciar um OBJ automaticamente... 
	//no caso sera um OBJ do tipo USERSERVICE, q vamos chamar de SERVICE
	@Autowired
	private UserService service;
	//
	//
	//crindo um metodo FINDALL q CHAMA o USERSERVICE e pede para
	//ele os USUARIOS cadastrados no BANCO
	//
	//para dizer q esse METODO vai ser o ENDPOINT REST do 
	//CAMINHO /USERS vamos colocar o @RequestMapping e 
	//passar o METHOD informando q o METODO e o GET pois o 
	//GET e para PEGAR DADOS no padrao REST
	//
	@RequestMapping(method=RequestMethod.GET)
		//o ResponseEntity e para retornar RESPOSTAS em HTTP ja com
		//cabecalhos, etc...
	public ResponseEntity< List<UserDTO>> findAll(){
		//criando uma LIST/lista do tipo USER q irá RECEBER o OBJ service
		//chamando o METODO FINDALL, q e o metodo q ta dentro de
		//userService q solicita os dados de USER no BANCO
		List<User> list = service.findAll();
		
		//pegando o q ta na LIST do tipo USER e passando para a LIST do tipo
		//USERDTO... fazendo esse processo com LAMBDA
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		
		//retornando um ResponseEntity OK, para dizer q a resposta
		//ocorreu com sucesso...
		//e no BODY no vamos passar o CORPO da RESPOSTA no caso 
		//a LISTDTO de usuarios feita ali em cima
		return ResponseEntity.ok().body(listDto);
	}
	
	//criando um metodo para retornar o usuario por ID
	//A LOGICA E SEMELHANTE A DO FINDALL... Só q agora e SÓ UM por vez
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	//o ResponseEntity e para retornar RESPOSTAS em HTTP ja com
	//cabecalhos, etc... q retorna APENAS 1 USER e não uma LIST
	//o @PATHVARIABLE e para dizer q o ID do argumento e o ID passado
	//ali em cima no value="/{id}"
public ResponseEntity<UserDTO> findById(@PathVariable String id){
	
	//instanciando um OBJ do tipo USUARIO/USER recebendo o valor
	//do SERVICE.findById, e passando como parametro
	//o STRING ID q recebemos ali no metodo acima String id...
	User obj = service.findById(id);
	
	//retornando um ResponseEntity OK, para dizer q a resposta
	//ocorreu com sucesso...
	//e no BODY no vamos passar o nosso OBJ do tipo USER q sera
	//CONVERTIDO para USERDTO
		//para isso fizemos um NEW USERDTO e passamos como argumento
		//USER (q e algo q o USERDTO espera receber)passamos o OBJ
		//q e do tipo USER
	return ResponseEntity.ok().body(new UserDTO(obj));
}
	
	//criando o metodo /ENDPOIT para inserir usuario...
	//ele recebe a solicitacao do FRONT e passa para a camada
	//de service...
	
	//criando um metodo para retornar o usuario por ID
	//A LOGICA E SEMELHANTE A DO FINDALL...
	//como é para INSERIR é .POST ... .GET e para PEGAR
	@RequestMapping(method=RequestMethod.POST)
	//o ResponseEntity e para retornar RESPOSTAS em HTTP ja com
	//cabecalhos, etc... Mas como vamos INSERIR nao precisa
	//retornar NADA, pois nao ESTAMOS PEGANDO e sim INSERINDO
	//por isso é VOID
	//
	//o ENDPOINT INSERT vai receber como ARGUMENTO o USERDTO
public ResponseEntity<Void> insert(@RequestBody UserDTO objDto){
	
	//instanciando um OBJ do tipo USUARIO/USER recebendo o valor
	//de SERVICE.FROMDTO... Pois assim nos CONVERTEMOS um USERDTO
	//para USUARIO, e esse USERDTO convertido para USUARIO
		//vai ficar armazenado no OBJETO 	OBJ
	User obj = service.fromDTO(objDto);
	//
	//o OBJ do tipo USER vai recebe o retorno do metodo INSERT
	//
	obj = service.insert(obj);
	
	//retornando um RESP VAZIA, mas com um cabecalho com o LINK
	//do novo usuario q foi ADD
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	return ResponseEntity.created(uri).build();
	}
	
	//criando um metodo para DELETAR o usuario por ID
	//A LOGICA E SEMELHANTE A DO FINDBYID...
		//metodo HTTP vai ser o DELETE
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	//o ResponseEntity e para retornar RESPOSTAS em HTTP ja com
	//cabecalhos, etc... a RESPOSTA do metodo vai ser VOID/vazia
	//pois estamos DELETANDO e nao PEGANDO/visualizando
	//o NOME do metodo e DELETE, e o PATHVARIABLE vai receber
	//o ID do usuario a ser deletado... o PATHVARIABLE e para dizer
	//q o STRING ID é o ID q ta ali em cima em VALUE
	public ResponseEntity<Void> delete(@PathVariable String id){
	//
	//chamando o service e chamando o metodo DELETE e passando
	//o ID do usuario q queremos DELETAR
	service.delete(id);
	
	//resposta com o codigo 204, e uma resposta q DEU CERTO mas
	//q nao tem resposta, pois foi deletado o usuario
	return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	//criando um metodo para ATUALIZAR o usuario
	
	//como é para ATUALIZAR o metodo é .PUT...
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	//o ENDPOINT INSERT vai receber como ARGUMENTO o OBJDTO
	//os dados q estao no OBJDTO é o q o USUARIO passou para
	//atualizar ou seja NOME e EMAIL NOVO,
	//e o PathVariable que é o /{ID} passado ali em cima
	//esse e o ID do usuario q queremos atualizar com os dados
	//q vieram no OBJDTO
	public ResponseEntity<Void> update(@RequestBody UserDTO objDto, @PathVariable String id){
	
	//instanciando um OBJ do tipo USUARIO/USER recebendo o valor
	//de SERVICE.FROMDTO... Pois assim nos CONVERTEMOS um USERDTO
	//para USUARIO, e esse USERDTO convertido para USUARIO
		//vai ficar armazenado no OBJETO 	OBJ
	User obj = service.fromDTO(objDto);
	//passando o ID q veio na requisicao para o OBJ
	//o OBJ é os DADOS NOVOS q o usuario q colocar/atualizar
	obj.setId(id);
	//
	//passamos para o metodo UPDATE o OBJ q é os DADOS q queremos
	//atualizar o NOME e EMAIL novo e o ID que é o ID do usuario
	//original... Para sabermos QUAL é o USUARIO antigo q vai
	//receber esses DADOS NOVOS
	obj = service.update(obj);
	
	//retornando
	return ResponseEntity.noContent().build();
	
	
	}
	
	

	//A LOGICA E SEMELHANTE A DO FINDBYID
	//
	//criando um ENDPOINT/METODO para pegarmos os POSTS de um USUARIO
	//pelo navegador/postman
	//sera... caminho USERS/ID_DO_USUARIO/POSTS
	
	@RequestMapping(value="/{id}/posts", method=RequestMethod.GET)
	//o ResponseEntity e para retornar RESPOSTAS em HTTP ja com
	//cabecalhos, etc... q retorna uma LIST de POSTS
	//o @PATHVARIABLE e para dizer q o ID do argumento e o ID passado
	//ali em cima no value="/{id}"
public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
	
	//instanciando um OBJ do tipo USUARIO/USER recebendo o valor
	//do SERVICE.findById, e passando como parametro
	//o STRING ID q recebemos ali no metodo acima String id...
	User obj = service.findById(id);
	
	//retornando um ResponseEntity OK, para dizer q a resposta
	//ocorreu com sucesso...
	//e no BODY no vamos passar o nosso OBJ chamando o metodo
	//GETPOST para pegar os POST... q no caso sera uma LISTA de posts
	return ResponseEntity.ok().body(obj.getPosts());
}
	
	
	
	
	
}
