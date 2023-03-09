package model.entities;

import java.io.Serializable;
import java.util.Date;

//criando a classe SELLER/vendedor... implementando a INTERFACE SERIALIZABLE
public class Seller implements Serializable {
	
	//implementando o SERIAZABLE... para transformar os OBJETOS em sequencia
	//de BITS... isso e para ser guardado em arquivo/ ou trafegado em rede, etc...
	private static final long serialVersionUID = 1L;
	
	//criando os atributos/variaveis da classe
	private Integer id;
	private String name;
	private String email;
	private Date birthDate;
	private Double baseSalary;
	//fazendo uma ASSOCIACAO... pois o SELER/VENDEDOR tem um DEPARTMENT
	//desta forma declarando uma variavel department do TIPO DEPARTMENT :)
	private Department department;
	
	//criando o construtor VAZIO/padrao
	public Seller() {
	}

	//construtor com os ARGUMENTOS/variaveis/atributos...
	public Seller(Integer id, String name, String email, Date birthDate, Double baseSalary, Department department) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.baseSalary = baseSalary;
		this.department = department;
	}
	
	
	//criando os metodos GET e SET para podermos alterar/manipular os valores
	//das variaveis acima...
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	
	//gerando o HASHCODE para podermos COMPARAR o valor dos OBJETOS pelo CONTEUDO
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	//criando o metodo TOSTRING para q os valores possam ficar de forma
	//LEGIVEL...
	@Override
	public String toString() {
		return "Seller [id=" + id + ", name=" + name + ", email=" + email + ", birthDate=" + birthDate + ", baseSalary="
				+ baseSalary + ", department=" + department + "]";
	}

}
