package BancoDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Modelos.Funcionario;
import Modelos.PedProdFunc;
import Modelos.Pedido;
import Modelos.Produto;

public class PedProdFuncDAO {
    private Connection connection;

    // Construtor - Estabelecendo conexão com o banco de dados
    public PedProdFuncDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para inserir um PedProdFunc no banco de dados
    public void inserir(PedProdFunc pedProdFunc) {
        String sql = "INSERT INTO PedProdFunc (idProdPed, idFunc, idProd, idPedido, quantidade, valorUnitario, valorTotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pedProdFunc.getIdProdPed());
            stmt.setInt(2, pedProdFunc.getFuncionario().getIdFunc());
            stmt.setInt(3, pedProdFunc.getProduto().getIdProd());
            stmt.setInt(4, pedProdFunc.getPedido().getIdPedido());
            stmt.setInt(5, pedProdFunc.getQuantidade());
            stmt.setDouble(6, pedProdFunc.getValorUnitario());
            stmt.setDouble(7, pedProdFunc.getValorTotal());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar um PedProdFunc no banco de dados
    public void atualizar(PedProdFunc pedProdFunc) {
        String sql = "UPDATE PedProdFunc SET idFunc = ?, idProd = ?, idPedido = ?, quantidade = ?, valorUnitario = ?, valorTotal = ? WHERE idProdPed = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pedProdFunc.getFuncionario().getIdFunc());
            stmt.setInt(2, pedProdFunc.getProduto().getIdProd());
            stmt.setInt(3, pedProdFunc.getPedido().getIdPedido());
            stmt.setInt(4, pedProdFunc.getQuantidade());
            stmt.setDouble(5, pedProdFunc.getValorUnitario());
            stmt.setDouble(6, pedProdFunc.getValorTotal());
            stmt.setInt(7, pedProdFunc.getIdProdPed());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar um PedProdFunc no banco de dados
    public void deletar(int idProdPed) {
        String sql = "DELETE FROM PedProdFunc WHERE idProdPed = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProdPed);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos os PedProdFunc do banco de dados
    public List<PedProdFunc> listar() {
        List<PedProdFunc> lista = new ArrayList<>();
        String sql = "SELECT * FROM PedProdFunc";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int idProdPed = rs.getInt("idProdPed");
                int idFunc = rs.getInt("idFunc");
                int idProd = rs.getInt("idProd");
                int idPedido = rs.getInt("idPedido");
                int quantidade = rs.getInt("quantidade");
                double valorUnitario = rs.getDouble("valorUnitario");
                
                Funcionario funcionario = new FuncionarioDAO(connection).buscarFuncionarioPorId(idFunc);
                Produto produto = new ProdutoDAO(connection).buscarProdutoPorId(idProd);
                Pedido pedido = new PedidoDAO(connection).buscarPedidoPorId(idPedido);

                PedProdFunc pedProdFunc = new PedProdFunc(idProdPed, funcionario, produto, pedido, quantidade, valorUnitario);

                lista.add(pedProdFunc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    
    // Método para buscar um PedProdFunc por ID
    public PedProdFunc buscarPorId(int idProdPed) {
        PedProdFunc pedProdFunc = null;
        String sql = "SELECT * FROM PedProdFunc WHERE idProdPed = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProdPed);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idFunc = rs.getInt("idFunc");
                    int idProd = rs.getInt("idProd");
                    int idPedido = rs.getInt("idPedido");
                    int quantidade = rs.getInt("quantidade");
                    double valorUnitario = rs.getDouble("valorUnitario");

                    // Aqui você pode criar instâncias das classes Funcionario, Produto e Pedido a partir dos seus IDs
                    Funcionario funcionario = new FuncionarioDAO(connection).buscarFuncionarioPorId(idFunc);
                    Produto produto = new ProdutoDAO(connection).buscarProdutoPorId(idProd);
                    Pedido pedido = new PedidoDAO(connection).buscarPedidoPorId(idPedido);

                    pedProdFunc = new PedProdFunc(idProdPed, funcionario, produto, pedido, quantidade, valorUnitario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedProdFunc;
    }
}
