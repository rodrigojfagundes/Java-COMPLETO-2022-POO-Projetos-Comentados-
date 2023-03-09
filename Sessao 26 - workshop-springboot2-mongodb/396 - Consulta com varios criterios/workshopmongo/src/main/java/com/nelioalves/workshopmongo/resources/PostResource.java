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

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.workshopmongo.domain.Post;
import com.nelioalves.workshopmongo.resources.util.URL;
import com.nelioalves.workshopmongo.services.PostService;

//classe POSTRESOURCE dentro do SUBPACOTE RESOURCES,
//o nome é RESOURCE pois o nome tecnico para referenciar
//RECURSOS REST é RESOURCE... Ou seja são RECURSOS q o BACKEND
//vai DISPONIBILIZAR... tbm pd ser chamado de 
//CONTROLLER/CONTROLADOR

	//colocamos a ANNOTATION @restController para dizer q é um
	//recurso REST
@RestController
	//colocando o @RequestMapping para dizer qual e o caminho
	//do ENDPOINT... Ou seja o caminho q sera solicitado pelo
	//navegador... ex localhost:8080/posts
@RequestMapping(value="/posts")
public class PostResource {
	
	//o @AUTOWIRED e para instanciar um OBJ automaticamente... 
	//no caso sera um OBJ do tipo POSTSERVICE, q vamos chamar de SERVICE
	@Autowired
	private PostService service;
	
	//criando um metodo FINDBYID q chama o POSTSERVICE e pede para
	//para retornar o POST por ID conforme estao CAD no BANCO
	//
		//para dizer q esse METODO vai ser o ENDPOINT REST do 
		//CAMINHO /POSTS vamos colocar o @RequestMapping e 
		//passar o METHOD informando q o METODO e o GET pois o 
		//GET e para PEGAR DADOS no padrao REST
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	//o ResponseEntity e para retornar RESPOSTAS em HTTP ja com
	//cabecalhos, etc... q retorna APENAS 1 POST e NÂO uma LIST
	//o @PATHVARIABLE e para dizer q o ID do argumento e o ID passado
	//ali em cima no value="/{id}"
public ResponseEntity<Post> findById(@PathVariable String id){
	
	//instanciando um OBJ do tipo POST recebendo o valor
	//do SERVICE.findById, e passando como parametro
	//o STRING ID q recebemos ali no metodo acima String id...
	Post obj = service.findById(id);
	
	//retornando um ResponseEntity OK, para dizer q a resposta
	//ocorreu com sucesso...
	//e no BODY no vamos passar o nosso OBJ do tipo POST
	return ResponseEntity.ok().body(obj);
	}	
	
	
	
	//implementando o METODO/ENDPOINT para fazermos a
	//busca por alguma palavra especifica no titulo dos POST
	
		//para dizer q esse METODO vai ser o ENDPOINT REST do 
		//CAMINHO /POSTS vamos colocar o @RequestMapping e 

	//o /titlesearch pois é o LINK por onde vamos acessar
	//esse METODO para fazer as PESQUISA
	@RequestMapping(value="/titlesearch", method=RequestMethod.GET)
	//o ResponseEntity e para retornar RESPOSTAS em HTTP ja com
	//cabecalhos, etc... q retorna uma LISTA com todos os POST
	//q tem a palavra BUSCADA/solicitada no TITULO
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value="text", defaultValue="") String text){
	//pegando URL q ta no TEXT q foi passado acima e decodificando
	text = URL.decodeParam(text);
	//agora q a URL(url e tipo LOCALHOST/titlesearch/BOM  DIA
	//dai dps de DECODIFICAR ele o BOMDIA fica tudo junto e dai da
	//certo...
		//agora q a URL ta decodificada vamos passar o q foi buscado
		//e ta no TEXT para o metodo FINDBYTITLE q ta na classe
		//service... e ARMAZENAR os resultados na LISTA de POST
	List<Post> list = service.findByTitle(text);
	//retornando um ResponseEntity OK, para dizer q a resposta
	//ocorreu com sucesso...
	//e no BODY no vamos passar o nosso OBJ do tipo LIST
	//q ta a LISTA de TITULOS q corresponde ao q foi BUSCADO
	// /pesquisado
	return ResponseEntity.ok().body(list);
	}
	
	
	
	//implementando o METODO/ENDPOINT para fazermos a
		//busca por alguma palavra especifica EM TODOS OS LOCAIS
		//no POST, COMENTARIOS e em DETERMINADA DATA
		
			//para dizer q esse METODO vai ser o ENDPOINT REST do 
			//CAMINHO /POSTS vamos colocar o @RequestMapping e 

		//o /fullsearch pois é o LINK por onde vamos acessar
		//esse METODO para fazer as PESQUISA
		@RequestMapping(value="/fullsearch", method=RequestMethod.GET)
		//o ResponseEntity e para retornar RESPOSTAS em HTTP ja com
		//cabecalhos, etc... q retorna uma LISTA com todos os POST
		//q tem a palavra BUSCADA/solicitada
		public ResponseEntity<List<Post>> fullSearch(
				//passando os 3 parametros...
				//a PALAVRA a ser buscada e a DATA de INICIO e data maxima
				@RequestParam(value="text", defaultValue="") String text,
				@RequestParam(value="minDate", defaultValue="") String minDate,
				@RequestParam(value="maxDate", defaultValue="") String maxDate)
		{
		//pegando URL q ta no TEXT q foi passado acima e decodificando
		text = URL.decodeParam(text);
		//agora q a URL(url e tipo LOCALHOST/titlesearch/BOM  DIA
		//dai dps de DECODIFICAR ele o BOMDIA fica tudo junto e dai da
		//certo...
		
		
		//chamando o metodo CONVERTDATE q ta na class URL para converter a
		//DATA inicial para uma data em FORMATO padrao
			//se der algum problema ara converter nos vamos chamar o NEW DATE
			//e passar o 0L q e uma data min padrao do JAVA(01/01/1970)
		Date min = URL.convertDate(minDate, new Date(0L));
		//semelhante com o DATE MAX
		Date max = URL.convertDate(maxDate, new Date());
			//agora q a URL ta decodificada vamos passar o q foi buscado
			//e ta no TEXT para o metodo FULLSEARCH q ta na classe
			//service... e ARMAZENAR os resultados na LISTA de POST
		List<Post> list = service.fullSearch(text, min, max);
		//retornando um ResponseEntity OK, para dizer q a resposta
		//ocorreu com sucesso...
		//e no BODY no vamos passar o nosso OBJ do tipo LIST
		//q ta a LISTA de TITULOS q corresponde ao q foi BUSCADO
		// /pesquisado
		return ResponseEntity.ok().body(list);
		}
	
	
	
	
}
