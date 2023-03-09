package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

//classe SellerDaoJDBC classe responsavel por INSTANCIAR os DAO, SUFIXO JDBC
//ali e para dizer q é uma implementacao do JDBC do SELLERDAO
	//a CLASSE implementa a INTERFACE SELLERDAO
public class SellerDaoJDBC implements SellerDao{
	
	//criando uma depedencia com a conexao (conexao com o BD) de nome CONN
	private Connection conn;
	//fazendo a injecao de depedencia no construtor a baixo
	
	//nesse construtor nos estamos dizendo q o SellerDaoJDBC vai receber um valor
	//de CONN e esse valor de CONN (dados para conexao ao BANCO) sera atribuido
	//a VAR CONN feita ali em cima (private Connection conn)
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	
	
	//a primeiro metodo é o INSERT para INSERIR no BD um OBJETO do tipo
	//SELLER...
	@Override
	public void insert(Seller obj) {
		
		//declarando o valor NULL para a var ST do tipo PreparedStatement
		PreparedStatement st = null;
		try {
			//passando para var ST o valor do nosso CONN (conexao com o DB) e chamando o
			//metodo prepareStatement para dizer quais vao ser os comandos SQL a serem
			//rodados
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", 
					//retornando o ID do vendedor inserido... o ID e gerado automaticamente
					//AUTO INCREMENTE
					Statement.RETURN_GENERATED_KEYS);
			
			//associando um VALOR a cada uma das (???) interrogacoes... Nome, Email, BirthDate
			//etc...
			
			//associando a PRIMEIRA INTERROGAÇÂO (?) o o valor q esta em obj.getName no caso
			//o nome
			st.setString(1, obj.getName());
			
			//o valor da INTERROGACAO(?) 2 é o valor q ta no OBJETO OBJ em GETEMAIL
			st.setString(2, obj.getEmail());
			
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getDate()));
			
			
			st.setDouble(4, obj.getBaseSalary());
			
			//pegando o ID do DEPARTMENT q este SELLER/VENDEDOR trabalha
			//para isso vamos acessar o DEPARTAMENT do OBJ (objeto de VENDEDOR/seller) e dps
			//pegando o DEPARTMENT (getDepartment) q este VENDEDOR trabalha, e dps com o
			//getID pegando o ID do DEPARTAMENT q este vendedor trabalha :)
			st.setInt(5, obj.getDepartment().getId());
			
			//criando uma variavel rowsAffect q ira receber a qtde de LINHAS q foram
			//modificadas no BANCO... E o ST.EXECUTEUPDATE, vai servir para executar
			//os codigos de ADD vendedor feito acima
			int rowsAffected = st.executeUpdate();
			
			//verificando SE a qtde de linhas alteradas foi MAIOR q 0... CASO SIM, entao
			//significa q foi INSERIDO dados no BANCO
			if (rowsAffected > 0) {
				//declarando uma variavel RS do tipo RESULTSET q vai receber o ID do
				//VENDEDOR cadastrado
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					//a VAR ID do tipo INT vai RECEBEEER o VALOR da ID q esta armazenada
					//na VARIAVEL RS(variavel feita ali na linha de cima)
						//o NUMERO é o 1 pois é o PRIMEIRO INT... algo assim
					int id = rs.getInt(1);
					//setando o ID q foi GERADO para cadastrarmos ESSE ID no OBJETO
					//SELLER, pois até entao ele nao tava setado... E EUUU ACHOOO QQQ
					//nao era VISIVEL no BANCO esse ID... EU ACHO... agora vai ficar
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			
			//else para caso de excessao CASO de algum erro...
			else {
			throw new DbException("unexpected error! no rows affected!");
		}
	}
		//catch para caso o BLOCO TRY de erro
	catch(SQLException e) {
		throw new DbException(e.getMessage());
		}
		//fechando os recursos aberto
		finally {
			DB.closeStatement(st);
		}
	}

	//criano a operação UPDATE para atualizr os valores do BD, q recebe um
	//OBJETO do tipo SELLER como argumento
	@Override
	public void update(Seller obj) {
		//declarando o valor NULL para a var ST do tipo PreparedStatement
				PreparedStatement st = null;
				try {
					//passando para var ST o valor do nosso CONN (conexao com o DB) e chamando o
					//metodo prepareStatement para dizer quais vao ser os comandos SQL a serem
					//rodados
					st = conn.prepareStatement(
							"UPDATE seller "
							+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
							+ "WHERE Id = ?");
					
					//os valores viram a PARTIR DO OBJETO Q TA sendo recebido no metodo UPDATE
					//no caso o OBJETO é o SELLER OBJ
					//associando um VALOR a cada uma das (???) interrogacoes... Nome, Email, BirthDate
					//etc... 
					//associando a PRIMEIRA INTERROGAÇÂO (?) o o valor q esta em obj.getName no caso
					//o nome
					st.setString(1, obj.getName());
					
					//o valor da INTERROGACAO(?) 2 é o valor q ta no OBJETO OBJ em GETEMAIL
					st.setString(2, obj.getEmail());
					
					st.setDate(3, new java.sql.Date(obj.getBirthDate().getDate()));
					
					
					st.setDouble(4, obj.getBaseSalary());
					
					//ALTERANDO o ID do DEPARTMENT q este SELLER/VENDEDOR trabalha
					//para isso vamos acessar o DEPARTAMENT do OBJ (objeto de VENDEDOR/seller) e dps
					//pegando o DEPARTMENT (getDepartment) q este VENDEDOR trabalha, e dps com o
					//getID pegando o ID do DEPARTAMENT q este vendedor trabalha :)
					st.setInt(5, obj.getDepartment().getId());
					//e o ID do vendedor
					st.setInt(6, obj.getId());
					
					//o ST.EXECUTEUPDATE, vai servir para executar os codigos de ATUALIZAR
					//vendedor feito acima
					st.executeUpdate();
					
			}
				//catch para caso o BLOCO TRY de erro
			catch(SQLException e) {
				throw new DbException(e.getMessage());
				}
				//fechando os recursos aberto
				finally {
					DB.closeStatement(st);
				}
		
	}
	
	//operacao q recebe uma ID para deletar do BD
	@Override
	public void deleteById(Integer id) {
		//instanciando a variavel ST
		PreparedStatement st = null;
		try {
			//passando o comando para DELETAR a TABELA SELLER onde o ID for igual o ID
			//recebido no ID do metodo
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
			//informando Q a PRIMEIRA INTERROGAÇÂO do comando ali de cima, vai receber
			//o VALOR q ta no ID do metodo DELETEBYID
			st.setInt(1, id);
			
			//executando o comando SQL acima...
			 st.executeUpdate();
			
	
		}
		//caso de excessao
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		//fechando as conexoes
		finally {
			DB.closeStatement(st);
		}
	}

	//operacao q recebe um ID e retorna um SELLER associado a esse ID...
	//vai consutar no DB se tem algum OBJETO do tipo SELLER com o ID
	//passado, se SIM... Vai retornar os dados desse SELLER
		//OVERRIDE pq SOBREESCREVENDO o metodo q ta na INTERFACE SELLERDAO
	@Override
	public Seller findById(Integer id) {
		//declarando uma var ST do tipo PreparedStatement
		PreparedStatement st = null;
		//declarando var RS do tipo ResultSet
		ResultSet rs = null;
		//abrindo o bloco try
		try {
			//falando q a var ST vai receber o nosso CONN.prepare
			st = conn.prepareStatement(
//basicamente buscando todos campos de vendedor + o nome do departamento, q ganha um
//apelido de DepName e dps nos BUSCAMOS(join) os dados de Seller e DEPARTAMENT
//restrigimos a BUSCA onde o ID do SELLER seja IGUAL a... o valor passado
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			
		//definindo qual sera o VALOR do "???" INTERROGACAO de WHERE seller.id=?
		//no caso, sera o VALOR do ID q foi passado la em cima como parametro
		//no metodo FINDBYID
			st.setInt(1, id);
			//Executando o comando de busca acima, e passando o valor de resultado
			//para a variavel RS
			rs = st.executeQuery();
			//agora meio q nos vamos fazer a DIVISAO... Pois foi pego as info de
			//SELLER e DEPARTMENT juntas... Mas nos vamos ter q DIVIDIR as info de
			//SELLER vao para o OBJETO SELLER e as INFO de DEPARTMENT vao para o
			//obj DEPARTMENT
			//			MAIS UMA EXPLICAÇÂO
	//o RESULTSET (RS) traz os dados no formato de TABELA(FORMATO RELACIONAL), 
	//mas estamos programando ORIENTADO a OBJETO... as classes DAO
	//são responsaveis por PEGAR os dados no TIPO RELACIONAL(TABELAS)
	//e transformar em OBJETOS ASSOCIADOS(orientado a objetos) tipo, temos o SELLER
	// e ASSOCIADO a ela tmos o DEPARTMENT... ou SEJA NAO E TUDO JUNTO
			
			//testando SE a consulta TEVE algum RESULTADO... (pois o .next)
			//verifica se tem MAIS de 0 linhas... SE for 0 é NULL
			if (rs.next()) {
				//instanciando um objeto DEP do tipo DEPARTMENT q chama
				//a funcao instantiateDepartment, q passa como argumento o RS
				Department dep = instantiateDepartment(rs);
				//
				//agora vamos pegar os dados do BANCO q estao na TABELA SELLER
				//e vamos ASSOCIAR esses DADOS a um OBJETO do tipo SELLER
				//
				//chamando o METODO InstantiateSeller q vai
				//criar um OBJETO da classe SELLER chamado de OBJ
				//e vamos pegar os dados do BANCO q são de SELLER e passar para
				//esse OBJETO chamado de OBJ q é do tipo SELLER
				Seller obj = instantiateSeller(rs, dep);
				
				//retornando o objeto OBJ(seller com os DADOS + o DEPARTAMENT)
				return obj;
			}
			return null;
		}
		
		//em caso de excessao
		catch(SQLException e) {
			//passando a excessao personalizada
			throw new DbException(e.getMessage());
		}
		//finally para fechar os recursos
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
	
	
	//criando o metodo q vai PEGAR os DADOS do BANCO q sao da tabela SELLER
	//e ADD esses dados ao OBJETO do tipo SELER, no caso o OBJ
	//o throws SQLEXCEPTION e para caso der erro propagar a excessao
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		//criando um OBJETO do tipo SELLER q ira se chamar OBJ
		Seller obj = new Seller();
		//passando o ID q ta em SELLER no BANCO para o nosso OBJETO do tipo
		//SELLER
		obj.setId(rs.getInt("Id"));
		//passando o NOME q ta em SELLER no BANCO para o nosso OBJETO do
		//tipo SELLER
		obj.setName(rs.getString("Name"));
		//passando o EMAIL q ta em SELLER no BANCO para o nosso OBJETO do
		//tipo SELLER
		obj.setEmail(rs.getString("Email"));
		//passando o SALARIO q ta em SELLER no BANCO para o nosso OBJETO
		//do tipo SELLER
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		//passando o NASCIMENTO (birthdate) q ta em SELLER no BANCO para
		//o nosso OBJETO do tipo SELLER
		obj.setBirthDate(rs.getDate("BirthDate"));
		//passando o OBJETO DEP q nos fizemos ali em cima, para o
		//DEPARTMENT do nosso OBJETO (obj) do tipo SELER
		obj.setDepartment(dep);
		
		//return o obj
		return obj;
	}




	//criando o metodo q vai PEGAR os DADOS do BANCO q sao da tabela
	//DEPARTMENT e ADD esses dados ao OBJETO do tipo DEPARTMENT no caso DEP
	//o throws SQLEXCEPTION e para caso der erro propagar a excessao...
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		//a ID do Objeto DEP, vai RECEBER a ID q ESTA no DEPARTMENT do BD
		//e ela é acessada atraves do rs.getInt (pegando o valor INT)
		//da coluna DepartmentId no BANCO
		dep.setId(rs.getInt("DepartmentId"));
		//pegando o nome do department q ta no BANCO e colocando no OBJETO
		//DEP :)
		dep.setName(rs.getString("DepName"));
		
		return dep;
	}




	//operacao para RETORNAR todos os SELLER/VENDEDOR em forma de LISTA :)
	@Override
	public List<Seller> findAll() {
		
		//declarando uma var ST do tipo PreparedStatement
		PreparedStatement st = null;
		//declarando var RS do tipo ResultSet
		ResultSet rs = null;
		//abrindo o bloco try
		try {
			//falando q a var ST vai receber o nosso CONN.prepare
			st = conn.prepareStatement(
		//QUERY para buscar OS VENDEDORES de um DEPARTAMENTO
		//selecionando TODOS os dados de um VENDEDOR + o DEPARTAMENTO e dando um
		//apelido de DEPNAME... Dps vamos JUNTAR(JOIN) os dados de SELLER e DEPARTMENT

		"SELECT seller.*,department.Name as DepName "
		+ "FROM seller INNER JOIN department "
		+ "ON seller.DepartmentId = department.Id "
		+ "ORDER BY Name");

			//Executando o comando de busca acima, e passando o valor de resultado
			//para a variavel RS
			rs = st.executeQuery();
			
			//criando uma LISTA/LIST para armazenar os VENDEDORES do DEPARTMENT
			List<Seller> list = new ArrayList<>();
			//declarando um MAP q a chave dele vai ser um INTEGER o ID do DEPARTMENT
			//e o VALOR de CADA OBJETO vai ser o ID DO DEPARTMENT
				//esse MAP vai RECEBER cada DEPARTMENT Instanciado/iniciado
			Map<Integer, Department> map = new HashMap();
			
			
			// 				OBS: OBJETIVO, TER VARIOS VENDEDORES APONTANDO PARA
			//				O MESMO DEPARTAMENTO... POIS DO CONTRARIO
			//				TERIAMOS O MESMO DEPARTAMENTO ID INSTANCIADO VARIAS VEZES
			
			
			//usando o WHILE para PERCORRER linha por linha com da tabela 
			//DEPARTMENT o RS.NEXT, e PEGANDO todos os SELLERS/vendedor desse
			//DEPARTMENT
			while (rs.next()) {
				
				//testando SE o DEPARTMENT ja existe
				//indo no MAP e CASO ja exista esse DEPARTMENTID instanciado nos vamos
				//utilizar novamente esse DEPARTMENT
				//se NAO existir o map.get vai retornar NULL, dai nos vamos instnaciar
				//pela PRIMEIRA vez o DEPARTMENT com esse ID
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				//SE o retorno do MAP.GET DepartmentId for NULL entao é pq esse
				//DEPARTMENT nao havia sido iniciado/instanciado ainda
				if(dep == null) {
					//instanciando um objeto DEP do tipo DEPARTMENT q chama
					//a funcao instantiateDepartment, q passa como argumento o RS
					dep = instantiateDepartment(rs);
					//agora salvando o DEPARTMENTID dentro do MAP para q na proxima
					//vez no WHILE, ja ver q esse dep(departmentId) esta instanciado
					map.put(rs.getInt("DepartmentId"), dep);
					
				}

				//
				//agora vamos pegar os dados do BANCO q estao na TABELA SELLER
				//e vamos ASSOCIAR esses DADOS a um OBJETO do tipo SELLER
				//
				//chamando o METODO InstantiateSeller q vai
				//criar um OBJETO da classe SELLER chamado de OBJ
				//e vamos pegar os dados do BANCO q são de SELLER e passar para
				//esse OBJETO chamado de OBJ q é do tipo SELLER
				Seller obj = instantiateSeller(rs, dep);
				
				//adicionando esse vendedor(em forma de OBJETO) encontrado na LISTA
				list.add(obj);
			}
			//dps de add todos os VENDEDORES desse DEPARTMENT na LISTA, vamos
			//retornar a LISTA com os nomes desses VENDEDORES
			return list;
		}
		
		//em caso de excessao
		catch(SQLException e) {
			//passando a excessao personalizada
			throw new DbException(e.getMessage());
		}
		//finally para fechar os recursos
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
		
		
	}
	
	//metodo para encontrar um VENDEDOR/SELLER a partir de um DEPARTAMENTO informado
	@Override
	public List<Seller> findByDepartment(Department department) {
		
		//declarando uma var ST do tipo PreparedStatement
				PreparedStatement st = null;
				//declarando var RS do tipo ResultSet
				ResultSet rs = null;
				//abrindo o bloco try
				try {
					//falando q a var ST vai receber o nosso CONN.prepare
					st = conn.prepareStatement(
				//QUERY para buscar OS VENDEDORES de um DEPARTAMENTO
				//selecionando TODOS os dados de um VENDEDOR + o DEPARTAMENTO e dando um
				//apelido de DEPNAME... Dps vamos JUNTAR(JOIN) os dados de SELLER e DEPARTMENT
				//com as RESTRIÇÂO DE, vamos BUSCAR onde o DEPARTMENT ID é "=" um certo valor
				"SELECT seller.*,department.Name as DepName "
				+ "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id "
				+ "WHERE DepartmentId = ? "
				+ "ORDER BY Name");
					
					//definindo qual sera o VALOR do "???" INTERROGACAO de WHERE department.id=?
					//no caso, sera o VALOR do ID do DEPARTMENT foi passado la em cima
					//como parametro no metodo FINDBYDEPARTMENT
					st.setInt(1, department.getId());
					//Executando o comando de busca acima, e passando o valor de resultado
					//para a variavel RS
					rs = st.executeQuery();
					
					//criando uma LISTA/LIST para armazenar os VENDEDORES do DEPARTMENT
					List<Seller> list = new ArrayList<>();
					//declarando um MAP q a chave dele vai ser um INTEGER o ID do DEPARTMENT
					//e o VALOR de CADA OBJETO vai ser o ID DO DEPARTMENT
						//esse MAP vai RECEBER cada DEPARTMENT Instanciado/iniciado
					Map<Integer, Department> map = new HashMap();
					
					
					// 				OBS: OBJETIVO, TER VARIOS VENDEDORES APONTANDO PARA
					//				O MESMO DEPARTAMENTO... POIS DO CONTRARIO
					//				TERIAMOS O MESMO DEPARTAMENTO ID INSTANCIADO VARIAS VEZES
					
					
					//usando o WHILE para PERCORRER linha por linha com da tabela 
					//DEPARTMENT o RS.NEXT, e PEGANDO todos os SELLERS/vendedor desse
					//DEPARTMENT
					while (rs.next()) {
						
						//testando SE o DEPARTMENT ja existe
						//indo no MAP e CASO ja exista esse DEPARTMENTID instanciado nos vamos
						//utilizar novamente esse DEPARTMENT
						//se NAO existir o map.get vai retornar NULL, dai nos vamos instnaciar
						//pela PRIMEIRA vez o DEPARTMENT com esse ID
						Department dep = map.get(rs.getInt("DepartmentId"));
						
						//SE o retorno do MAP.GET DepartmentId for NULL entao é pq esse
						//DEPARTMENT nao havia sido iniciado/instanciado ainda
						if(dep == null) {
							//instanciando um objeto DEP do tipo DEPARTMENT q chama
							//a funcao instantiateDepartment, q passa como argumento o RS
							dep = instantiateDepartment(rs);
							//agora salvando o DEPARTMENTID dentro do MAP para q na proxima
							//vez no WHILE, ja ver q esse dep(departmentId) esta instanciado
							map.put(rs.getInt("DepartmentId"), dep);
							
						}

						//
						//agora vamos pegar os dados do BANCO q estao na TABELA SELLER
						//e vamos ASSOCIAR esses DADOS a um OBJETO do tipo SELLER
						//
						//chamando o METODO InstantiateSeller q vai
						//criar um OBJETO da classe SELLER chamado de OBJ
						//e vamos pegar os dados do BANCO q são de SELLER e passar para
						//esse OBJETO chamado de OBJ q é do tipo SELLER
						Seller obj = instantiateSeller(rs, dep);
						
						//adicionando esse vendedor(em forma de OBJETO) encontrado na LISTA
						list.add(obj);
					}
					//dps de add todos os VENDEDORES desse DEPARTMENT na LISTA, vamos
					//retornar a LISTA com os nomes desses VENDEDORES
					return list;
				}
				
				//em caso de excessao
				catch(SQLException e) {
					//passando a excessao personalizada
					throw new DbException(e.getMessage());
				}
				//finally para fechar os recursos
				finally {
					DB.closeStatement(st);
					DB.closeResultSet(rs);
				}

	}

}
