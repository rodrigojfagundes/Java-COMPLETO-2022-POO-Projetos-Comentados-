package com.educandoweb.course.entities.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.Product;

//Classe AUXILIAR ORDERITEMPK q é a CHAVE PRIMARIA do ITEM DE PEDIDO, essa classe vai
//ter uma referencia para a CLASSE PEDIDO e PRODUTO
	//colocando o EMBEDDABLE para virar um ANNOTATION do JPA
@Embeddable
public class OrderItemPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//criando um ATRIBUTO order do tipo Order e um ATRIBUTO product do tipo PRODUCT
	//
	//
	//declarando q o ORDER vai ser MUITOS para UM (mtos pedido para 1 produto)
	//com o ManyToOne...		E o JOINCOLUMN e para dizer qual vai ser o NOME DA
	//CHAVE ESTRANGEIRA no BANCO
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	//
	//declarando q o PRODUCT vai ser MUITOS para UM (mtos PRODUTOS para 1 PEDIDO)
	//com o ManyToOne...		E o JOINCOLUMN e para dizer qual vai ser o NOME DA
	//CHAVE ESTRANGEIRA no BANCO
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	//declarando os GET e SET
	

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	//metodo HASHCODE e EQUALS para COMPARAR VALORES...
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		OrderItemPK other = (OrderItemPK) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
	
	
	
}