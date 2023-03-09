package com.educandoweb.course.entities.enums;

//tipo ENUMERADO ORDER STATUS
public enum OrderStatus {
	
	//vamos DEFINIR MANUALMENTE um VALOR numerico para CADA OPCAO do ENUM
	WAITING_PAYMENT(1),
	PAID(2),
	SHIPPED(3),
	DELIVERED(4),
	CANCELED(5);
	
	//variavel q IRA receber o tipo NUMERADO
	private int code;
	
	//construtor q recebe esse valor
	private OrderStatus(int code) {
		this.code = code;
	}
	
	//fazendo um GET para podermos acessar esse valor de for
	public int getCode() {
	return code;
	}
	
	//convertendo um vlor numerico para o tipo ENUMERADO
	//vamos passar um valor para O CODE, e o metodo a baixo vai verificar
	//qual é o ORDERSTATUS para o valor (INT) passado para a variavel CODE
	public static OrderStatus valueOf(int code) {
		//percorrendo o ENUM ORDERSTATUS, e verificando qual é o ORDERSTATUS
		//para o VALOR passado para var int CODE
		for (OrderStatus value : OrderStatus.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		//caso seja passado um valor invalido
		throw new IllegalArgumentException("Invalid OrderStatus code");
	}
}
