package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

//criando a classe DEPARTMENT SERVICE
public class DepartmentService {
	
	
	//declarando uma depedencia de departmentDAO (q � uma INTERFACE q serve para
	//INSERIR ATUALIZAR e DELETAR dados do BD)
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	
	
	//criando um METODO chamado FINDALL q IRA retornar uma LIST com todos os
	//DEPARTMENT do BANCO DE DADOS :)
	public List<Department> findAll(){
		//chamando o DAO (objeto/variavel) q ciramos ali em CIMA q � do tipo
		//DEPARTMENTDAO... E chamando o METODO FINDALL que ir� retornar uma LISTA
		//com os DEPARTAMENTOS CADASTRADOS... no BANCO DE DADOS
		return dao.findAll();
	}
	
	//crianod uma opera��o/metodo q vai receber um DEPARTMENT como OBJETO/VARIAVEL
	//ATRIBUTO... e vai verificar SE � para INSERIR ou ATUALIZAR O DEPARTMENT
	//existente
	public void SaveOrUpdate(Department obj) {
		//se o ID do objeto(obj)/department for NULL significa q � um department
		//novo... POIS o ID � autoincremente
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	//metodo para remover um departamento, q RECEBE um OBJ do tipo DEPARTAMENTO
	public void remove (Department obj) {
		dao.deleteById(obj.getId());
	}
}