package com.educandoweb.course.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.OrderItem;
import com.educandoweb.course.entities.Payment;
import com.educandoweb.course.entities.Product;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.entities.enums.OrderStatus;
import com.educandoweb.course.repositories.CategoryRepository;
import com.educandoweb.course.repositories.OrderItemRepository;
import com.educandoweb.course.repositories.OrderRepository;
import com.educandoweb.course.repositories.ProductRepository;
import com.educandoweb.course.repositories.UserRepository;

//classe de configuração é uma classe auxiliar q vai fazer algumas alterações 
//na aplicacao classe de configuração para o perfil de test
	//o @CONFIGURATION serve para dizer q e uma class de CONFIGURACAO
@Configuration
	//no Profile é para dizer que essa CLASSE é ESPECIFICA para o PERFIL de TEST
	//vamos ter q por o PROFILE... TEST pois no APPLICATION.PROPERTIES nos informamos
	//q essa é uma APLICACAO de TEST com a linha spring.profiles.active=test
@Profile("test")
	//com essa CLASSE TESTCONFIG nos vamos ADD ALGUNS DADOS de TEST no BANCO
	//a INTERFACE CommandLineRunner cria o METODO RUN
public class TestConfig implements CommandLineRunner{
	//chamando a classe UserRepository para fazer uma INJECAO de DEPEDENCIA e
	//poder assim ADD dados no BANCO
	
	//declarando a DEPEDENCIA de UserRepository (fazendo uma INJECAO DE DEPEDENCIA)
		//com o AUTOWIRED nos vamos instanciar uma DEPEDENCIA de USERREPOSITORY
		//dentro desta CLASSE TESTCONFIG
	@Autowired
	private UserRepository userRepository;
	
	//declarando uma depedencia de OrderRepository (fazendo uma INJECAO DE DEPEDENCIA)
	@Autowired
	private OrderRepository orderRepository;
	
	//declarando uma depedencia de CategoryRepository (fazendo uma INJECAO DE DEPEDENCIA)
	@Autowired
	private CategoryRepository categoryRepository;
	
	//declarando uma depedencia de ProductRepository (fazendo uma INJECAO DE DEPEDENCIA)
	@Autowired
	private ProductRepository productRepository;

	
	
	//declarando uma depedencia de OrdemItemRepository (fazendo uma INJECAO DE DEPEDENCIA)
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	//tudo q for colocado dentro do METODO RUN, vai RODAR quando a APLICACAO
	//for EXECUTADA
	@Override
	public void run(String... args) throws Exception {
		
		
		//instanciando 3 OBJETOS do tipo CATEGORY, para fazer os testes na aplicacao
		//esses OBJETOS do tipo CATEGORY vao ser CAD no BANCO H2...
		//o ID e NULL... Pois o BANCO AUTOINCREMENTA
		Category cat1 = new Category(null, "Electronics"); 
		Category cat2 = new Category(null, "Books"); 
		Category cat3 = new Category(null, "Computers"); 
		
		//instanciando 5 OBJETOS do tipo PRODUCT para fazer os testes na aplicao
		//esses OBJETOS sao do tipo PRODUCT, e vao ser CADASTRADOS no BANCO H2
		//o ID e NULL, pois o BANCO AUTOINCREMENTA
		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, ""); 
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, ""); 
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, ""); 
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, ""); 
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, ""); 
		
		
		//salvando as (CATEGORY) no BANCO...
		//chamando o OBJETO categoryREPOSITORY que é um OBJETO do TIPO CATEGORYREPOSITORY
		//classe q tem acesso aos DADOS/BANCO (Pois HERDA as CLASSES DO JPA)
		//chamando o metodo SAVEALL no qual nos passamos uma LISTA com os 3 CATEGORY acima
		//o CAT1,2,3... Metodo SAVEALL Faz parte da CLASSE JPA q foi HERDADO
		// no CATEGORYREPOSITORY
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		
		
		//salvando os (PRODUCT) no BANCO...
		//chamando o OBJETO productREPOSITORY que é um OBJETO do TIPO PRODUCTREPOSITORY
		//classe q tem acesso aos DADOS/BANCO (Pois HERDA as CLASSES DO JPA)
		//chamando o metodo SAVEALL no qual nos passamos uma LISTA com os 5 PRODUCT
		//o p1,2,3,4,5... Metodo SAVEALL Faz parte da CLASSE JPA q foi HERDADO
		// no PRODUCTREPOSITORY
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		
		//agora vamos falar QUAL CATEGORIA e de QUAL PRODUTO
			//falando q o PRODUTO P1 é da CATEGORIA CAT1 ... Ou seja
			//vamos dizer q o LIVRO THE LORD ... e da CATEGORIA LIVROS/BOOK
		p1.getCategories().add(cat2);
		//o o P2 PRODUTO2 ele é da CATEGORIA 1 e 3, pois é ELETRONICO e e do tipo
		//COMPUTER tbm
		p2.getCategories().add(cat1);
		p2.getCategories().add(cat3);
		//sucessivamente...
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat3);
		p5.getCategories().add(cat2);
		
		//salvar as associacoe entre OS PRODUTOS (P1, P2, P3, P4, P5) e as CATEGORIAS
		//no BANCO
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		
		
		//aqui nos vamos INSTANCIAR 2 OBJETOS do tipo USUARIO
		//para fazer os TESTES na aplicação...
			//esses USUARIOS vao ser CAD no BANCO DE DADOS H2... o ID deles e NULL
			//pq é o BANCO de vai DEFINIR
		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");
		
		
		
		//Instanciando 3 OBJETOS(o1, o2 e o3) do tipo PEDIDO 
		//o ID do PEDIDO e NULL pois e AUTOINCREMENT pelo BANCO, passando a DATA
		//e o USUARIO q fez o pedido... e o STATUS do pedido... ORDERSTATUS...
		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1); 
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2); 
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);
		
		
		//salvando eles no BANCO...
		//chamando o OBJETO userREPOSITORY que é um OBJETO do TIPO USERREPOSITORY
		//classe q tem acesso aos DADOS/BANCO (Pois HERDA as CLASSES DO JPA)
		//chamando o metodo SAVEALL no qual nos passamos uma LISTA com os 2 USERS acima
		//o U1 e U2... Metodo SAVEALL Faz parte da CLASSE JPA q foi HERDADO no USERREPOSITORY
		userRepository.saveAll(Arrays.asList(u1, u2));
		//chamando o ORDERREPOSITORY para salvar os PEDIDOS no BANCO
		//e passando os PEDIDOS o1, o2 e o3
		//o metodo SAVEALL foi HERDADO do JPA na classe ORDERREPOSITORY
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
		
		
		//instanciando 4 OBJETOS do tipo ORDERITEM (Items de Pedido)
		//e para cada ORDERITEM nos estamos passando o q estamos PEDINDO e o
		//USUARIO q pediu... nos atributos...
//passando q o OI1 é um ITEM do PEDIDO 1, (obj O1) do PRODUTO 
//(obj P1)  de quantidade 2
		
		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice()); 
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice()); 
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice()); 
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice()); 
		
		//chamando o ORDERITEMREPOSITORY para salvar as ITENS DE PEDIDO no BANCO
		//e passando os os ITENS DE PEDIDO OI1, OI2, OI3, OI4
		//o metodo SAVEALL foi HERDADO do JPA na classe ORDERITEMREPOSITORY
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
		
		//criando um objeto do tipo PAYMENT de nome PAY1, em q o ID e NULL e o momento
		//q foi feito pagamento + o PEDIDO/order associado ao PAGAMENTO no caso O1
		Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
		//para salvar no BANCO, vamos
		//chamar o PEDIDO o1, e chamar o metodo SETPAYMENT q esta dentro do PEDIDO
		//o1
		o1.setPayment(pay1);
		//agora vamos pedir para salvar o OBJ do tipo PEDIDO/ORDER O1
		//q dai ja vai ir com o pagamento junto
		orderRepository.save(o1);
		
	}
}
