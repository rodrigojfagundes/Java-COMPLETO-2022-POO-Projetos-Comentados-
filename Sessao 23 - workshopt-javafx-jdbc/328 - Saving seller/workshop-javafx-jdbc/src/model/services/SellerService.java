//ESTA CLASSE SELLERSERVICE � UMA COPIA DA CLASSE DEPARTAMENTSERVICE
//O PROFESSOR NELIO PEDIU PARA FAZER ISSO E TROCAR OS NOMES DE DEPARTAMENT PARA
//SELLER

package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

//criando a classe SELLER SERVICE
public class SellerService {
	
	//declarando uma depedencia de SellertDAO (q � uma INTERFACE q serve para
	//INSERIR ATUALIZAR e DELETAR dados do BD)
	private SellerDao dao = DaoFactory.createSellerDao();
	
	//criando um METODO chamado FINDALL q IRA retornar uma LIST com todos os
	//SELLER do BANCO DE DADOS :)
	public List<Seller> findAll(){
		//chamando o DAO (objeto/variavel) q ciramos ali em CIMA q � do tipo
		//SELLERDAO... E chamando o METODO FINDALL que ir� retornar uma LISTA
		//com os VENDEDORES CADASTRADOS... no BANCO DE DADOS
		return dao.findAll();
	}
	
	//crianod uma opera��o/metodo q vai receber um SELLER como OBJETO/VARIAVEL
	//ATRIBUTO... e vai verificar SE � para INSERIR ou ATUALIZAR O SELLER
	//existente
	public void SaveOrUpdate(Seller obj) {
		//se o ID do objeto(obj)/seller for NULL significa q � um seller
		//novo... POIS o ID � autoincremente
		if(obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	//metodo para remover um vendedor, q RECEBE um OBJ do tipo VENDEDOR
	public void remove (Seller obj) {
		dao.deleteById(obj.getId());
	}
}