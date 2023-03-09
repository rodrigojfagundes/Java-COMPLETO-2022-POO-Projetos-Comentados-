package com.educandoweb.course.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.educandoweb.course.entities.pk.OrderItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;

//classe ORDERITEM/ITEM DE PEDIDO, q tem quantidade e preco e TBM uma 
//associacao com as classes PRODUTO e PEDIDO

	//fazendo o MAPEAMENTO... o @ENTITY e para dizer q vamos CRIAR UMA TABELA
	//com o NOME dessa CLASSE (ORDERITEM) no BANCO...
@Entity
//chamando o @TABLE para renomear o nome da TABELA q seria ORDERITEM para
//tb_order_item
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 1L;
	// declarando os atributos/variaveis da class

	// atributo IDENTIFICADOR correspondente a CHAVE PRIMARIA, é um do tipo
	// da CLASSE ORDERITEMPK, e vamos chamar de ID
	@EmbeddedId
	private OrderItemPK id = new OrderItemPK();

	// atributos qtde e preco do tipo INTEGER e DOUBLE
	private Integer quantity;
	private Double price;

	// criando o construtor vazio
	public OrderItem() {
	}

	// construtor com argumentos, q recebe uma ORDER do tipo(classe) ORDER
	// e um PRODUCT do tipo(classe) PRODUCT, alem dos valores de quantidade e price
	public OrderItem(Order order, Product product, Integer quantity, Double price) {
		super();
		// chamando o nosso OBJ ID q é um OBJ do tipo ORDERITEMPK, e dps chamando
		// o metodo SETORDER q ta dentro do ORDERITEMPK e passando para esse metodo
		// o valor de ORDER q recebemos acima no nosso construtor
		id.setOrder(order);
		// chamando o nosso OBJ ID q é um OBJ do tipo ORDERITEMPK, e dps chamando
		// o metodo SETPRODUCT q ta dentro do ORDERITEMPK e passando para esse metodo
		// o valor de PRODUCT q recebemos acima no nosso construtor
		id.setProduct(product);

		this.quantity = quantity;
		this.price = price;
	}

	// declarando os GET e SET

	// esse GET de ORDER nos estamos acessando o OBJ ID q é um OBJ do tipo
	// ORDERITEMPK e pedindo para retornar o metodo GETORDER q esta dentro da
	// classe ORDERITEMPK
	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}

	// esse SET de ORDER nos estamos PASSANDO um ORDER/pedido para o METODO
	// SETORDER que esta dentro do OBJ ID, que é um OBJETO do tipo da classe
	// ORDERITEMPK
	public void setOrder(Order order) {
		id.setOrder(order);
	}

	//esse GET de PRODUCT nos estamos acessando o OBJ ID q é um OBJ do tipo
	//ORDERITEMPK e pedindo para retornar o metodo GETPRODUCT q esta dentro da
	//classe ORDERITEMPK
	public Product getProduct() {
		return id.getProduct();
	}

	//esse SET de PRODUCT nos estamos PASSANDO um PRODUCT/produto para o METODO
	//SETPRODUCT que esta dentro do OBJ ID, que é um OBJETO do tipo da classe
	//ORDERITEMPK
	public void setProduct(Product product) {
		id.setProduct(product);
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	//metodo q devolve um valor DOUBLE, e esse metodo é o SUBTOTAL
	public Double getsubTotal() {
		//pegando o PRECO UNITARIO do PRODUTO X a quantidade
		return price * quantity;
	}
	
	// metodo HASHCODE e EQUALS para comparar valores
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
		OrderItem other = (OrderItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
