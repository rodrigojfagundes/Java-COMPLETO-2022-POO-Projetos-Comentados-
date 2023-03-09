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

package com.nelioalves.workshopmongo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.workshopmongo.domain.Post;
import com.nelioalves.workshopmongo.repository.PostRepository;
import com.nelioalves.workshopmongo.services.exception.ObjectNotFoundException;

//
//classe PostService, ou seja vai ser um SERVICO responsavel por
//trabalhar com os POSTS
//
//colocando o @SERVICE para dizer q essa CLASSE e um SERVICO e pd ser
//INJETADA em outras CLASSES
@Service
public class PostService {
	
	//o @AUTOWIRED e para instanciar um OBJ automaticamente... 
	//no caso sera um OBJ do tipo POSTREPOSITORY, q vamos chamar de REPO
	@Autowired
	private PostRepository repo;

	
	//OBS OBS OBS:
	
	//O NOME DO OBJ O CORRETO SERIA OBJ OU PST (POST)
	//MAS EU ACHO Q POR ENGANO DO PROFESSOR, ELE DEIXOU COMO
	//USER NAS LINHAS
		//Optional<Post> user = repo.findById(id);
		//return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));

	//criando um metodo para buscar um POST pelo o ID
	public Post findById(String id) {
		//criando um VAR optional do tipo POST q recebe o REPO q é um OBJ do
		//tipo POSTREPOSITORY, e pelo REPO chamamos o metodo HERDADO
		//FINDONE e passamos o ID
		Optional<Post> user = repo.findById(id);
		//no RETURN, nos chamamos o nosso OBJ e nele chamamos o METODO
		//ORELSETHROW e para esse metodo passamos a classe OBJECTNOTFOUND
		//EXCEPTION com a msg objeto nao encontrado
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	//criando o metodo de BUSCA... Metodo q vamos chamar de FINDBYTITLE
	//o metodo retorna uma LISTA de POST
	public List<Post>findByTitle(String text){
		//e esse metodo vai CHAMAMAR o nosso REPO (que é um OBJ do tipo
		//POSTREPOSITORY) e dps chamar o metodo SEARCH TITLE
		return repo.searchTitle(text);
	}
	
	//METODO FULLSEARCH é um metodo de CONSULTA q sera para 
	//encontrar uma PALAVRA seja em POST, COMENTARIO, etc...
		//esse metodo RETORNA uma LISTA de POST
	public List<Post> fullSearch(String text, Date minDate, Date maxDate){
		//acresentando UM DIA ao MAXDATE... Pois se nao NAO fica MENOR q MAXDATE
		//pois tem q ser o MAXDATE(data) + 23h59min
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		//e vamos RETORNAR o valor q VOLTAR do metodo FULLSEARCH
		//q ta na class POSTREPOSITORY
		return repo.fullSearch(text, minDate, maxDate);
	}
}
