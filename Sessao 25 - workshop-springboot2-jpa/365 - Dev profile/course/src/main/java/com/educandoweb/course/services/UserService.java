package com.educandoweb.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

//
//classe UserService
//teremos duas OPERAÇÔES/METODOS basicos... Buscar TODOS USUARIOS, e BUSCAR USUARIOS por
//ID.......... o @Service e para dizer q essa classe e um SERVICE do SRING
//e assim ela podera ser injetada com o AUTOWIRED...
@Service
public class UserService {
	
	//Depedencia para a INTERFACE USERREPOSITORY, q iremos chamar de REPOSITORY
	@Autowired
	private UserRepository repository;
	
	//metodo para RETORNAR TODOS USUARIOS DO BANCO
	public List<User> findAll(){
		//chamando o nosso OBJETO REPOSITORY do tipo USERREPOSITORY que HERDA a CLASSE
		//JPAREPOSITORY... E essa CLASSE JPAREPOSITORY tem um METODO que é o FINDALL
		//Dai nos CHAMAMOS o METODO FINDALL q esta no JPAREPOSITORY e pedimos para trazer
		//todos USERS cadastrados no BANCO
		return repository.findAll();
	}
	
	//criando o metodo FINDBYID para retornar um USUARIO/USER q corresponde a ID passada
	public User findById(Long id) {
		//chamando o nosso OBJETO REPOSITORY do tipo USERREPOSITORY que HERDA a CLASSE
		//JPAREPOSITORY... E essa CLASSE JPAREPOSITORY tem um METODO que é o FINDBYID
		//Dai nos CHAMAMOS o METODO FINDBYID q esta no JPAREPOSITORY e pedimos para trazer
		//o USERS da ID q foi passada pelo USUARIO...
			//e ele(USER) vai ser armazenado dentro do OBJETO OBJ que é um OBJETO q recebe
			//um USER e é do tipo OPTIONAL
		Optional<User> obj = repository.findById(id);
		//retornando o OBJ, o ORELSETHROW, ele tenta dar o GET(pegar o OBJETO)
		//mas SE esse OBJ nao existir (a ID passada nao existir) dai ele chama
		//a mensagem de error
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	//operacao para salvar no BANCO um USER
	//q vai receber um UBJ do tipo USER
	public User insert(User obj) {
			//salvando o OBJ do tipo USER no BANCO
		return repository.save(obj);
	}
	
	//criando um metodo CHAMADO DELETE q vai RECEBER um ID
	//de USER q sera deletado
	public void delete(Long id) {
		//colocando um bloco TRY
		try {
		
		//chamando o REPOSITORY que é uma INJECAO DE DEPENDENCIA do USERREPOSITORY
		//e dele chamando o metodo DELETEBYID e passando um valor de ID
		//metodo esse q faz parte do JPA q foi HERDADO pelo USERREPOSITORY
		repository.deleteById(id);
		}
		//catch capturando a EXCECCAO EMPTYRESULTDATAACCESSEXCEPTION
		catch(EmptyResultDataAccessException e) {
			//chamando a excessao result not found exception
			throw new ResourceNotFoundException(id);
		}
		//capturando a DataIntegrityViolationException q estara armazenada na
		//variavel E
		catch(DataIntegrityViolationException e) {
			//chamando a CLASSE DATABASEEXCEPTION e passando para ela
			//a excessao q esta acontecendo
			throw new DatabaseException(e.getMessage());
		}
	}
	
	//funcao/metodo para atualizar um usuario
	//metodo do tipo USER pois vai retornar o usuario atualizado
	//e o ID e para dizer qual vai ser o ID do usuario a ser atualizado
	//e um OBJETO USER contendo os dados para serem atualizados
	public User update(Long id, User obj) {
		//criando um bloco TRY para excessoes
		try {		
		//criando um ENTITY(entidade monitorada pelo JPA) do tipo USER
		//q recebe o REPOSITORY.getOne(id).... o GETONE instancia o USUARIO mas
		//nao vai no BANCO ainda
		User entity = repository.getOne(id);
		//chamando a funcao UPDATEDATA, q recebe o ENTITY do tipo USER
		//e o OBJ q é os dados novos
		updateData(entity, obj);
		return repository.save(entity);
		}//pegando a EXCECAO do tipo EntityNotFoundException
		catch (EntityNotFoundException e) {
			//chamando a classe ResoourceNotFound e passando o ID q no caso é o
			//usuario q queremos deletar
			throw new ResourceNotFoundException(id);
		}
	}

	//criando o metodo UPDATEDATA q pega o OBJETO ENTITY e atualizar ele com os
	//dados q vieram no OBJ USER ali no metodo UPDATE
	private void updateData(User entity, User obj) {
		//estamos pegando os valores q estao no ENTITY (BANCO) e passando para ele
		//os valores q estao no OBJ do tipo USER... Ou seja os valores (nome, email
		//etc...) atualizados
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}
