package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

//classe PROGRAM... ela é MEIO Q COMO A TELA INICIAL DO SISTEMA...
public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		
		//instanciando um SELLERDAO, chamado de sellerDao... DAI em VEZ de dar um
		//NEW sellerDao... ..... Nos vamos chamar o METODO CREATESELLERDAO
		//q ta dentro da CLASSE DAOFACTORY... E esse metodo vai ser o responsavel
		//por criar um novo SellerDao :)
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		
		System.out.println("=== TEST 1 : seller findById ===");
		
		
		//declarando um OBJETO do tipo SELLER, q reebe o SELLERDAO e vamos
		//chamar o metodo FINDBYID e buscar o ID de vendedor 3
		Seller seller = sellerDao.findById(3);
		
		//imprimindo o resultado
		System.out.println(seller);
		
		
		System.out.println("\n=== TEST 2 : seller findByDepartment ===");
		//declarando uma variavel/objeto DEPARTMENT q recebe o valor 2, e o nome NULL
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		//for para percorrer a LIST acima
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		
		System.out.println("\n=== TEST 3 : seller findAll ===");
		//reaproveitando a List de SELLER q criamos no TEST 2...
		//e agora ela vai receber um FindAll da INTERFACE SELLERDAO... Interface essa q
		//chama a CLASSE SELLERDAOJDBC
		list = sellerDao.findAll();
		//for para percorrer a LIST acima
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		//teste para INSERIR dados no BANCO
		System.out.println("\n=== TEST 4 : seller insert ===");
		//criando um novo objeto do tipo SELLER chamado NEWSELLER, e para ele
		//nos vamos add os dados q serao INSERIDOS no banco, o PRIMEIRO é NULL pq é o
		//ID... e o ID é o propio BANCO q incrementa e diz qual é o valor...
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		//inserindo no banco de dado... chamando a INTERFACE SELLERDAO...
		sellerDao.insert(newSeller);
		//imprimindo o ID
		System.out.println("inserted New id = " + newSeller.getId());
		
		
		//teste para UPDATE dados no BANCO
		System.out.println("\n=== TEST 5 : seller update ===");
		//reaproveitando a variavel seller, e chamando o metodo FindById da INTERFACE sellerDao
		//e passamos o ID de vendedor 1
		seller = sellerDao.findById(1);
		//passando um NOVO NOME para o OBJETO SELLEr que no caso é o VENDEDOR de ID 3
		seller.setName("martha waine");
		//passando os dados q ESTAO no OBJETO SELLER para o metodo UPDATE... LEMBRANDO Q
		//o nome foi ATUALIZADO PARA MARTHA WAINE
		sellerDao.update(seller);
		System.out.println("update completed");
		
		
		
		//teste para DELETE dados no BANCO
		System.out.println("\n=== TEST 6 : seller delete ===");
		
		//pedindo para DIGITAR o ID do SELLER q queremos deletar
		System.out.print("enter id for delete teste ");
		int id = sc.nextInt();
		//chamando o metodo DELETEBYID e passando o ID do SELLER q queremos deletar
		sellerDao.deleteById(id);
		System.out.println("Delete completed");
		
		sc.close();
		
	}

}
