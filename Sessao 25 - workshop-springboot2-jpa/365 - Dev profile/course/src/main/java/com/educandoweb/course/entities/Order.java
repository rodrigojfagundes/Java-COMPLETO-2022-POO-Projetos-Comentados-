package com.educandoweb.course.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.educandoweb.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

//criando a nossa CLASSE/ENTIDADE PEDIDOS/ORDER
	//EU ACHO Q O @ENTITY diz q vai ser CRIADO UMA TABLE NO BANCO COM O MESMO NOME Q A CLASS
	//NO CASO ORDER... mas nas proximas linha a gente modifica para TB_ORDER para
	//nao dar conflito com a palavra ORDER reservada do BANCO
@Entity
//para tabela ORDER nao dar CONFLITO com a palavra RESERVADA ORDER do BANCO, vamos modificar
//para tb_order
@Table(name = "tb_order")
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;

	
	//implementando os ATRIBUTOS/VARIAVEIS basicas... do PEDIDO
	//colocando o @ID para dizer q o ID e a chave primeria
	//@GENERATEDVALUE IDENTITY... e para dizer q a chave é AUTOINCREMENT pelo BANCO
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		//momento em q foi pedido
			//@JsonFormat e para a data ficar no formato 8601 (dia, mes, ano...)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant moment;
	
	//criando uma VARIAVEL ORDERSTATUS do tipo INTEGER... Q vai receber um valor
	//numerico e sera passado para a CLASS ORDERSTATUS
		//é INTEGER pois na TABELA ORDER no BANCO vai ser do tipo INTEGER a coluna
		//ORDERSTATUS
	private Integer orderStatus;
	
		//declarando uma VARIAVEL CLIENT do tipo USER... Para sabermos qual é o 
		//USUARIO/CLIENTE q esta fazendo o PEDIDO
	//colocando a ANNOTATION MANYTOONE... Pois é MUITOS (ORDER/PEDIDO) para UM CLIENTE/USER
	@ManyToOne
	//Annotation JoinColumn recebe a CHAVE ESTRANGEIRA q tera no BANCO q é o ID do cliente
	//q fez o PEDIDO
	@JoinColumn(name = "client_id")
	private User client;
	
	
	//chamando o ANNOTATION OneToMany/Um para Muitos
	//e nele nos vamos chamar o ID(que é um OBJETO do tipo ORDERITEMPK) 
	//e esta declarado dentro da CLASSE ORDERITEM e nele chamamos o ORDER
	@OneToMany(mappedBy = "id.order")
	//criando uma COLECAO/SET do TIPO ORDERITEM chamada de ITEMS, e iniciando com
	//um NEW HASHSET, pois o SET é uma INTERFACE e o HASHSET é a CLASSE q
	//implementa esse INTERFACE
	private Set<OrderItem> items = new HashSet<>();
	
	//UM PEDIDO/ORDER tem um PAGAMENTO...
	//colocando q a CLASSE INDEPENDETE é o ORDER(pois para ter um PAGAMENTO é OBRIGATORIO
	//ter um PEDIDO)... E colocando q a classe DEPENDENTE é o PAYMENT, pois para ter
	//um PAGAMENTO tem q ter um PEDIDO
	//		o CASCADETYPE é para dizer q o ORDER e PAYMENT tem o mesmo ID...
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private Payment payment;
	
	//criando o construtor sem argumentos
	public Order() {}

	
	//criando o metodo construtor com argumentos
	public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
		super();
		this.id = id;
		this.moment = moment;
		setOrderStatus(orderStatus);
		this.client = client;
	}
	
	//criando os GET e SET para podermos VER e ALTERAR os valores dos ATRIBUTOS/VARIAVEIS
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Instant getMoment() {
		return moment;
	}


	public void setMoment(Instant moment) {
		this.moment = moment;
	}
	
	public OrderStatus getOrderStatus() {
		//chamando o METODO VALUEOF q ta no ENUM/CLASS ORDERSTATUS... E esse metodo
		//VALUEOF vai transformar o NUMERO(INTEGER) q ta na VAR ORDERSTATUS e vamos
		//converter ELE para um VALOR em ORDERSTATUS
		return OrderStatus.valueOf(orderStatus);
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		if(orderStatus != null) {
		//aqui nos vamos RECEBER um valor em ORDERSTATUS, e temos q guardar ele
		//em formato de NUMERO INTEIRO
			this.orderStatus = orderStatus.getCode();
		}
	}


	public User getClient() {
		return client;
	}


	public void setClient(User client) {
		this.client = client;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Set<OrderItem> getItems(){
		return items;
	}
	
	//metodo q vai retornar a SOMA dos SUBTOTAIS dos ITENS de PEDIDO(ORDERITEM)
	public Double getTotal() {
		//pegando a SOMA DOS SUBTOTAIS dos ORDERITEM
		double sum = 0.0;
		for(OrderItem x : items) {
			sum +=  x.getsubTotal();
		}
		return sum;
	}
	
	//metodo HASH CODE e EQUALS para comparar OBJETOS/pedidos pelo ID
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
