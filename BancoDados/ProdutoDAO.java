package BancoDados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Modelos.Produto;

public class ProdutoDAO {
    private Connection conexao;
    
    // Construtor - Inicializa a conexão com o banco de dados
    public ProdutoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (idProd, nomeProd, descricao, valorProd) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, produto.getIdProd());
            stmt.setString(2, produto.getNomeProd());
            stmt.setString(3, produto.getDescricao());
            stmt.setDouble(4, produto.getValorProd());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao inserir produto: " + e.getMessage());
        }
    }

 // Método para atualizar um produto no banco de dados
    public void atualizarProduto(Produto produto) throws SQLException {
        String sql = "UPDATE Produto SET nomeProd = ?, descricao = ?, valorProd = ? WHERE idProd = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produto.getNomeProd());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getValorProd());
            stmt.setInt(4, produto.getIdProd());
            stmt.executeUpdate();
        }
    }

    
    // Método para excluir um produto pelo ID
    public void excluirProduto(int idProd) throws SQLException {
        String sql = "DELETE FROM Produto WHERE idProd = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idProd);
            stmt.executeUpdate();
        }
    }
    
 // Método para buscar um produto pelo ID
    public Produto buscarProdutoPorId(int idProd) throws SQLException {
        String sql = "SELECT * FROM Produto WHERE idProd = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idProd);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                        rs.getInt("idProd"),
                        rs.getString("nomeProd"),
                        rs.getString("descricao"),
                        rs.getDouble("valorProd")
                    );
                }
            }
        }
        return null; // Retorna null se o produto não for encontrado
    }
    
    // Método para listar todos os produtos
    public List<Produto> listarProdutos() throws SQLException {
        String sql = "SELECT * FROM Produto ORDER BY idProd ASC";
        List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("idProd"),
                    rs.getString("nomeProd"),
                    rs.getString("descricao"),
                    rs.getDouble("valorProd")
                );
                produtos.add(produto);
            }
        }
        return produtos;
    }
}
