
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

package com.nelioalves.workshopmongo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.nelioalves.workshopmongo.domain.Post;

//
//interface POSTREPOSITORY
//REPOSITORY... os repository fazem OPERACOES basicas de ACESSO AO BANCO
//
//implementando o REPOSITORY com o SPRING DATA usando o ANNOTATION
//				@REPOSITORY
@Repository
//a INTERFACE POSTREPOSITORY vai HERDAR da INTERFACE ou CLASSE nao sei
//MongoRepository q ja existe no SPRINGDATA... e passando q sera recebido
//dados do TIPO POST e q o TIPO do ID(do post) é do tipo STRING
public interface PostRepository extends MongoRepository<Post, String>{
	
	
	//ACHO Q é um metodo para criar uma forma de BUSCA de palavras 
	//personalizada algo assim
		
	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	//basicamente recebe um TEXT em formato de STRING e retorna
	//uma LISTA de POSTS
	List<Post> searchTitle(String text);

	//fazer uma consulta para BUSCAR POST q tenham STRING no TITULO
	//chamando o metodo de BUSCA FINDBYTITLECONTAINIG(String text)
		//o metodo q RETORNA uma LISTA de POST
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	
	//criando o metodo de CONSULTA q vamos chamar de FULLSEARCH
		//buscar uma DETERMINADA palavra em QUALQUER LUGAR, 
		//seja no POST, COMENTARIOS, CORPO, e com INTERVALO de DATAS
		//
		//o @QUERY vai especificar a CONSULTA... com uns comando/QUERY q
		//sao do propio MONGO ... esse link +ou- https://www.mongodb.com/docs/manual/reference/operator/query/or/
	@Query("{ $and: [ { date: { $gte: ?1 } }, { date: { $lte: ?2 } } ,  { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } },  { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Post> fullSearch(String text, Date minDate, Date maxDate);
}
