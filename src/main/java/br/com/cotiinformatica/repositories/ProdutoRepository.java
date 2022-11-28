package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.cotiinformatica.entities.Produto;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ProdutoRepository {

	public void create(Produto produto) throws Exception {

		Connection connection = ConnectionFactory.createConnection();
		
		String query = "insert into produto(nome, descricao, preco, quantidade, datacadastro) values(?,?,?,?,?)";
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, produto.getNome());
		statement.setString(2, produto.getDescricao());
		statement.setDouble(3, produto.getPreco());
		statement.setInt(4, produto.getQuantidade());
		statement.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(produto.getDataCadastro()));
		statement.execute();
		
		connection.close();
	}
	
	public void update(Produto produto) throws Exception {
		
		Connection connection = ConnectionFactory.createConnection();
		
		String query = "update produto set nome=?, descricao=?, preco=?, quantidade=? where idproduto=?";
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, produto.getNome());
		statement.setString(2, produto.getDescricao());
		statement.setDouble(3, produto.getPreco());
		statement.setInt(4, produto.getQuantidade());
		statement.setInt(5, produto.getIdProduto());
		statement.execute();
		
		connection.close();
	}
	
	public void delete(Produto produto) throws Exception {
		
		Connection connection = ConnectionFactory.createConnection();
		
		String query = "delete from produto where idproduto=?";
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, produto.getIdProduto());
		statement.execute();
		
		connection.close();
	}
	
	public List<Produto> findAll() throws Exception {
		
		Connection connection = ConnectionFactory.createConnection();
		
		String query = "select * from produto order by nome";
		
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		
		List<Produto> lista = new ArrayList<Produto>();
		
		while(resultSet.next()) {
			
			Produto produto = new Produto();
			
			produto.setIdProduto(resultSet.getInt("idproduto"));
			produto.setNome(resultSet.getString("nome"));
			produto.setDescricao(resultSet.getString("descricao"));
			produto.setPreco(resultSet.getDouble("preco"));
			produto.setQuantidade(resultSet.getInt("quantidade"));
			produto.setDataCadastro(new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("datacadastro")));
			
			lista.add(produto);
		}
		
		connection.close();
		return lista;
	}
	
	public Produto findById(Integer idProduto) throws Exception {
		
		Connection connection = ConnectionFactory.createConnection();
		
		String query = "select * from produto where idproduto=?";
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, idProduto);
		ResultSet resultSet = statement.executeQuery();
		
		Produto produto = null;
		
		if(resultSet.next()) {
			
			produto = new Produto();
			
			produto.setIdProduto(resultSet.getInt("idproduto"));
			produto.setNome(resultSet.getString("nome"));
			produto.setDescricao(resultSet.getString("descricao"));
			produto.setPreco(resultSet.getDouble("preco"));
			produto.setQuantidade(resultSet.getInt("quantidade"));
			produto.setDataCadastro(new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("datacadastro")));
		}
		
		connection.close();
		return produto;
	}	
}











