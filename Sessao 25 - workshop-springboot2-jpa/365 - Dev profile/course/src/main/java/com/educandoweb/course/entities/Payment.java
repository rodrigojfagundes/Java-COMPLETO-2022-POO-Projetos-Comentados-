package com.educandoweb.course.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//classe/entidade de PAYMENT, essa classe vai ser associada ao PEDIDO/order
//
//			MAPEAMENTO OBJETO RELACIONAL JPA
//
//EU ACHO Q O @ENTITY diz q vai ser CRIADO UMA TABLE NO BANCO COM O MESMO NOME Q A CLASS
//NO CASO PAYMENT...
@Entity
//colocando o @TABLE para RENOMEAR o nome da TABELA no BANCO q seria PAYMENT (mesmo nome q
//essa CLASSE)... vamos renomear para TB_PAYMENT
@Table(name = "tb_payment")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	//colocando o @ID para dizer q o ID e a chave primeria
	//@GENERATEDVALUE IDENTITY... e para dizer q a chave é AUTOINCREMENT pelo BANCO
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//atributos/variaveis
	private Long id;
	private Instant moment;
	
	
		//associacoes...
	
	//a classe INDEPENDENTE é o ORDER (pois pd ter um pedido sem o pagamento)
	//MAS NAO existe PAGAMENTO sem pedido
	//classe depedente é o payment
	@JsonIgnore
	@OneToOne
	@MapsId
	private Order order;
	
	//metodos construtores
	public Payment() {
	}

	public Payment(Long id, Instant moment, Order order) {
		super();
		this.id = id;
		this.moment = moment;
		this.order = order;
	}
	
	
	//criando os metodos get e set
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	//metodo hashcode equals para comparar valores
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
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
