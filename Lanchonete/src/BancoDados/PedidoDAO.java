package BancoDados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Modelos.Pedido;

public class PedidoDAO {
    private Connection conexao;
    
    // Construtor - Inicializa a conexão com o banco de dados
    public PedidoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirPedido(Pedido pedido) {
        String sql = "INSERT INTO Pedido (idPedido, totalPedido, dataPedido, totalValor) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getIdPedido());
            stmt.setInt(2, pedido.getTotalPedido());

            // Converte o formato da data para java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            java.util.Date utilDate = sdf.parse(pedido.getDataPedido()); 
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 

            stmt.setDate(3, sqlDate);  // Agora usa setDate para o tipo DATE
            stmt.setDouble(4, pedido.getTotalValor());

            stmt.executeUpdate();
        } catch (SQLException | ParseException e) {
            System.err.println("Erro ao inserir pedido: " + e.getMessage());
        }
    }

    public void atualizarPedido(Pedido pedido) {
        String sql = "UPDATE Pedido SET totalPedido = ?, dataPedido = ?, totalValor = ? WHERE idPedido = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getTotalPedido());

            // Converte o formato da data de String para java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            java.util.Date utilDate = sdf.parse(pedido.getDataPedido()); 
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 

            stmt.setDate(2, sqlDate);  
            stmt.setDouble(3, pedido.getTotalValor());
            stmt.setInt(4, pedido.getIdPedido());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pedido: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Erro ao converter a data: " + e.getMessage());
        }
    }

    // Método para deletar um pedido
    public void deletarPedido(int idPedido) {
        String sql = "DELETE FROM Pedido WHERE idPedido = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar pedido: " + e.getMessage());
        }
    }
    
    // Método para buscar um pedido por ID
    public Pedido buscarPedidoPorId(int idPedido) {
        String sql = "SELECT * FROM Pedido WHERE idPedido = ?";
        Pedido pedido = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pedido = new Pedido(
                    rs.getInt("idPedido"),
                    rs.getInt("totalPedido"),
                    rs.getString("dataPedido"),
                    rs.getDouble("totalValor")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido: " + e.getMessage());
        }

        return pedido;
    }

    // Método para listar todos os pedidos
    public List<Pedido> listarPedidos() {
        String sql = "SELECT * FROM Pedido";
        List<Pedido> pedidos = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Pedido pedido = new Pedido(
                    rs.getInt("idPedido"),
                    rs.getInt("totalPedido"),
                    rs.getString("dataPedido"),
                    rs.getDouble("totalValor")
                );
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }
}
