package BancoDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Modelos.Funcionario;

public class FuncionarioDAO {
    private Connection conexao;

    // Construtor - Inicializa a conexão com o banco de dados
    public FuncionarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

 // Método para adicionar um funcionário 
    public void adicionarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO Funcionario (idFunc, nome, cargo, salario) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        	stmt.setInt(1, funcionario.getIdFunc());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getCargo());
            stmt.setDouble(4, funcionario.getSalario());
            stmt.executeUpdate();
        }
    }

    // Método para atualizar os dados de um funcionário
    public void atualizarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE Funcionario SET Nome = ?, Cargo = ?, Salario = ? WHERE IdFunc = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setDouble(3, funcionario.getSalario());
            stmt.setInt(4, funcionario.getIdFunc());
            stmt.executeUpdate();
        }
    }

    // Método para excluir um funcionário pelo ID
    public void excluirFuncionario(int idFunc) throws SQLException {
        String sql = "DELETE FROM Funcionario WHERE IdFunc = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idFunc);
            stmt.executeUpdate();
        }
    }

    // Método para buscar um funcionário pelo ID
    public Funcionario buscarFuncionarioPorId(int idFunc) throws SQLException {
        String sql = "SELECT * FROM Funcionario WHERE IdFunc = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idFunc);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                        rs.getInt("IdFunc"), 
                        rs.getString("Nome"), 
                        rs.getString("Cargo"), 
                        rs.getDouble("Salario")
                    );
                }
            }
        }
        return null; // Retorna null se não encontrar
    }

    // Método para listar todos os funcionários
    public List<Funcionario> listarFuncionarios() throws SQLException {
        List<Funcionario> listaFuncionarios = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                    rs.getInt("IdFunc"), 
                    rs.getString("Nome"), 
                    rs.getString("Cargo"), 
                    rs.getDouble("Salario")
                );
                listaFuncionarios.add(funcionario);
            }
        }
        return listaFuncionarios;
    }
}
