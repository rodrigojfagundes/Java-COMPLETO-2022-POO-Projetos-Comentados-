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

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.workshopmongo.domain.User;
import com.nelioalves.workshopmongo.dto.UserDTO;
import com.nelioalves.workshopmongo.repository.UserRepository;
import com.nelioalves.workshopmongo.services.exception.ObjectNotFoundException;

//
//classe UserService, ou seja vai ser um SERVICO responsavel por
//trabalhar com os USUARIOS/USERS
//
//colocando o @SERVICE para dizer q essa CLASSE e um SERVICO e pd ser
//INJETADA em outras CLASSES
@Service
public class UserService {
	
	//o @AUTOWIRED e para instanciar um OBJ automaticamente... 
	//no caso sera um OBJ do tipo USERREPOSITORY, q vamos chamar de REPO
	@Autowired
	private UserRepository repo;

	
	//criando o METODO q irá retornar os USUARIOS do BANCO
	public List<User> findAll(){
		//chamando o OBJ REPO q é do tipo USERREPOSITORY... E esse
		//USERREPOSITORY ele HERDA varios METODOS da classe 
		//MONGOREPOSITORY e assim se conecta no BANCO MONGO
		//ou seja estamos usando o SPRING DATA
		return repo.findAll();	
	}
	
	//criando um metodo para buscar um USER pelo o ID
	public User findById(String id) {
		//criando um VAR optional do tipo USER q recebe o REPO q é um OBJ do
		//tipo USERREPOSITORY, e pelo REPO chamamos o metodo HERDADO
		//FINDONE e passamos o ID
		Optional<User> obj = repo.findById(id);
		//no RETURN, nos chamamos o nosso OBJ e nele chamamos o METODO
		//ORELSETHROW e para esse metodo passamos a classe OBJECTNOTFOUND
		//EXCEPTION com a msg objeto nao encontrado
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	//metodo para inserir USUARIO com POST
	public User insert(User obj) {
		//dai nos vamos chmar o REPO que é o USERREPOSITORY
		//e pelo REPO nos vamos chamar o metodo INSERT
		//e passar o OBJ do tipo USER q recebemos como argumento
		//e vamos retornar o USER cadastrado...
		return repo.insert(obj);
	}
	
	//implementando o metodo DELETE, q recebe um ID de argumento
	//ID do usuario q queremos deletar
	public void delete(String id) {
		//mas antes de DELETAR o usuario, vamos chamar o metodo
		//FINDBYID e passar o ID q recebemos como argumento
		//POIS nos APENAS podemos deletar um USUARIO q existe
		findById(id);
		//chamando o REPO e chamando o METODO DELETE
		//e passando o ID q vamos deletar
		repo.deleteById(id);
	}
	
	//metodo para ATUALIZAR os dados de um USUARIO
	//recebendo um USUARIO como argumento
	//
	//o user OBJ enviado como argumento, é os DADOS enviados pelo
	//o usuario na requisicao
	public User update(User obj) {
		//chamando o metodo findByID e como ID nos passamos
		//o ID do OBJ (que é os DADOS ATUALIZADOS enviado pelo
		//usuario)
		//e dessa forma nos ENCONTRAMOS o USER original antes
		//de ser MODIFICADO e armazenamos em ((((NEWOBJ)))
		//pois nos pegamos o ID do USER q queremos modificar
		//e encontramos o USER ORIGINAL
		User newObj = findById(obj.getId());
		//chamando o metodo UPDATEDATA para copiar os dados
		//do OBJ(dados novos) para o NEWOBJ(dados antigos)
		updateData(newObj, obj);
		//feito isso, agora vamos chamar o REPO e o metodo SAVE
		//e passar o USUARIO com os dados ATUALIZADOS
		//ou seja o NEWOBJ
		return repo.save(newObj);
	}
	
	
	//metodo q COPIA os dados q estao no OBJ para o NEWOBJ
	//ou seja pegar o q foi passado como argumento pelo
	//sistema e passar como dados para o USUARIO ANTIGO(newobj)
	//e ASSIM DEIXAR O NEWOBJ ATUALIZADO :)
	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	//implementar o metodo FROMDTO... Esse metodo vai pegar um
	//DTO e instanciar um USUARIO
	//
	//OBS: No arquivo USERDTO eu EXPLICO BEM a FUNCAO DO DTO...
	//mas no caso da FROMDTO nos vamos fazer o inverso
		//esse metodo recebe um USERDTO chamado de objDTO
	public User fromDTO(UserDTO objDto) {
		//esse metodo vai retornar um NEWUSER/novo usuario
		//com os DADOS q foram recebidos no USERDTO
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
