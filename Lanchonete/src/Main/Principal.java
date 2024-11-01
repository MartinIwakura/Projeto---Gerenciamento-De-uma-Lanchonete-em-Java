package Main;

import java.sql.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;

import BancoDados.FuncionarioDAO;
import BancoDados.PedProdFuncDAO;
import BancoDados.PedidoDAO;
import BancoDados.ProdutoDAO;
import Modelos.Funcionario;
import Modelos.PedProdFunc;
import Modelos.Pedido;
import Modelos.Produto;

public class Principal {

    // Método principal para executar o menu e interagir com o banco de dados
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection conexao = null;

        // Estabelecendo a conexão com o banco de dados
        try {
            conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Lanchonete", "postgres", "1544");

            // Criando as instâncias dos DAOs
            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);
            PedidoDAO pedidoDAO = new PedidoDAO(conexao);
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
            PedProdFuncDAO pedProdFuncDAO = new PedProdFuncDAO(conexao);

            while (true) {
            	// Exibindo o menu
                System.out.println("MENU:");
                // Produto
                System.out.println("1 - Adicionar Produto");
                System.out.println("2 - Remover Produto");
                System.out.println("3 - Atualizar Produto");
                System.out.println("4 - Listar Produtos");
                System.out.println("5 - Buscar Produto por ID");
                // Pedido
                System.out.println("6 - Adicionar Pedido");
                System.out.println("7 - Remover Pedido");
                System.out.println("8 - Atualizar Pedido");
                System.out.println("9 - Listar Pedidos");
                System.out.println("10 - Buscar Pedido por ID");
                // Funcionario
                System.out.println("11 - Adicionar Funcionário");
                System.out.println("12 - Remover Funcionário");
                System.out.println("13 - Atualizar Funcionário");
                System.out.println("14 - Listar Funcionários");
                System.out.println("15 - Buscar Funcionário por ID");
                // PedProdFunc
                System.out.println("16 - Adicionar PedProdFunc");
                System.out.println("17 - Remover PedProdFunc");
                System.out.println("18 - Atualizar PedProdFunc");
                System.out.println("19 - Listar PedProdFunc");
                System.out.println("20 - Buscar PedProdFunc por ID");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();  

                switch (opcao) {
                case 1: {
                    try {
                        // Exibe os IDs e nomes de todos os produtos existentes
                        System.out.println("Produtos já cadastrados:");
                        List<Produto> produtosExistentes = produtoDAO.listarProdutos();
                        if (produtosExistentes.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                        } else {
                            for (Produto p : produtosExistentes) {
                                System.out.println("ID: " + p.getIdProd() + ", Nome: " + p.getNomeProd());
                            }
                            System.out.println(); // Linha em branco para separar
                        }

                        System.out.print("Digite o ID do produto: ");
                        int idProd = scanner.nextInt();

                        // Verifica se o ID já existe
                        boolean idJaExiste = produtosExistentes.stream().anyMatch(p -> p.getIdProd() == idProd);
                        if (idJaExiste) {
                            System.out.println("Erro: Esse ID já existe. Escolha um ID diferente.");
                            break;
                        }

                        scanner.nextLine(); // Consumir o newline
                        System.out.print("Digite o nome do produto: ");
                        String nome = scanner.nextLine();

                        // Verifica se o nome não está vazio
                        if (nome.isEmpty()) {
                            System.out.println("Erro: O nome do produto não pode estar vazio.");
                            break;
                        }

                        System.out.print("Digite a descrição do produto: ");
                        String descricao = scanner.nextLine();

                        // Verifica se a descrição não está vazia
                        if (descricao.isEmpty()) {
                            System.out.println("Erro: A descrição do produto não pode estar vazia.");
                            break;
                        }

                        System.out.print("Digite o valor do produto: ");
                        double valor = scanner.nextDouble();

                        // Valida se o valor é positivo
                        if (valor <= 0) {
                            System.out.println("Erro: O valor do produto deve ser positivo.");
                            break;
                        }

                        // Cria o objeto Produto
                        Produto produto = new Produto(idProd, nome, descricao, valor);

                        // Insere o produto no banco de dados
                        produtoDAO.inserirProduto(produto);
                        System.out.println("Produto adicionado com sucesso!");

                    } catch (InputMismatchException e) {
                        System.out.println("Erro: Entrada inválida. Certifique-se de inserir os dados corretamente.");
                        scanner.nextLine(); // Limpar o buffer do scanner
                    } catch (SQLException e) {
                        System.out.println("Erro ao adicionar o produto: " + e.getMessage());
                    }
                    break;
                }

                case 2: {
                    // Remover Produto
                    System.out.println("IDs de Produtos Existentes:");
                    List<Produto> produtosExistentes = produtoDAO.listarProdutos();
                    if (produtosExistentes.isEmpty()) {
                        System.out.println("Nenhum produto encontrado.");
                        break;
                    }

                    for (Produto p : produtosExistentes) {
                        System.out.println("ID do Produto: " + p.getIdProd() + ", Nome: " + p.getNomeProd() + ", Preço: R$ " + p.getValorProd());
                    }

                    try {
                        System.out.print("\nDigite o ID do produto a ser removido: ");
                        int idProd = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer

                        // Busca o produto para verificar se existe
                        Produto produto = produtoDAO.buscarProdutoPorId(idProd);

                        if (produto != null) {
                            System.out.println("Dados atuais do Produto: " + produto);

                            // Confirma a remoção
                            System.out.print("Tem certeza que deseja remover o produto (s/n)? ");
                            char confirmacao = scanner.next().charAt(0);

                            if (confirmacao == 's' || confirmacao == 'S') {
                                produtoDAO.excluirProduto(idProd);
                                System.out.println("Produto removido com sucesso!");
                            } else {
                                System.out.println("Remoção cancelada.");
                            }
                        } else {
                            System.out.println("Produto não encontrado! Verifique o ID e tente novamente.");
                        }

                    } catch (Exception e) {
                        System.out.println("Erro ao remover produto: " + e.getMessage());
                    }
                    break;
                }

                case 3: {
                    try {
                        // Exibe os IDs e nomes de todos os produtos existentes
                        System.out.println("Produtos cadastrados:");
                        List<Produto> produtosExistentes = produtoDAO.listarProdutos();
                        if (produtosExistentes.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                            break; // Saia do caso se não houver produtos
                        } else {
                            for (Produto p : produtosExistentes) {
                                System.out.println("ID: " + p.getIdProd() + ", Nome: " + p.getNomeProd());
                            }
                            System.out.println(); // Linha em branco para separar
                        }

                        System.out.print("Digite o ID do produto a ser atualizado: ");
                        int idProd = scanner.nextInt();
                        Produto produto = produtoDAO.buscarProdutoPorId(idProd);
                        
                        if (produto != null) {
                            System.out.println("Dados atuais do Produto: " + produto);
                            
                            // Consumir a nova linha após o próximo inteiro
                            scanner.nextLine();

                            System.out.print("Novo nome do produto (ou pressione Enter para manter): ");
                            String nome = scanner.nextLine();
                            System.out.print("Nova descrição do produto (ou pressione Enter para manter): ");
                            String descricao = scanner.nextLine();
                            System.out.print("Novo valor do produto (ou digite -1 para manter): ");
                            double valor = scanner.nextDouble();

                            // Aplicar mudanças apenas se um valor foi fornecido
                            if (!nome.isEmpty()) {
                                produto.setNomeProd(nome);
                            }
                            if (!descricao.isEmpty()) {
                                produto.setDescricao(descricao);
                            }
                            if (valor >= 0) {
                                produto.setValorProd(valor);
                            }

                            // Confirmar atualização
                            System.out.print("Deseja salvar as alterações? (s/n): ");
                            char confirmacao = scanner.next().charAt(0);
                            if (confirmacao == 's' || confirmacao == 'S') {
                                produtoDAO.atualizarProduto(produto);
                                System.out.println("Produto atualizado com sucesso!");
                            } else {
                                System.out.println("Atualização cancelada.");
                            }
                        } else {
                            System.out.println("Produto não encontrado!");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Erro: Entrada inválida. Certifique-se de inserir os dados corretamente.");
                        scanner.nextLine(); // Limpar o buffer do scanner
                    } catch (SQLException e) {
                        System.out.println("Erro ao atualizar o produto: " + e.getMessage());
                    }
                    break;
                }


                    case 4: {
                        // Listar Produtos
                        System.out.println("Lista de produtos:");
                        for (Produto p : produtoDAO.listarProdutos()) {
                            System.out.println(p);
                        }
                        break;
                    }
                    case 5: {
                        // Exibe os IDs e detalhes de todos os produtos existentes
                        System.out.println("Produtos cadastrados:");
                        List<Produto> produtosExistentes = produtoDAO.listarProdutos(); // Método para listar todos os produtos
                        if (produtosExistentes.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                            break; // Sai do caso se não houver produtos
                        } else {
                            for (Produto p : produtosExistentes) {
                                System.out.println("ID: " + p.getIdProd() + ", Nome: " + p.getNomeProd());
                            }
                            System.out.println(); // Linha em branco para separar
                        }

                        // Buscar Produto por ID
                        System.out.print("Digite o ID do produto que deseja buscar: ");
                        try {
                            int idProd = scanner.nextInt();
                            Produto produto = produtoDAO.buscarProdutoPorId(idProd);
                            if (produto != null) {
                                System.out.println(produto);
                            } else {
                                System.out.println("Produto não encontrado!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Erro: Por favor, insira um número válido para o ID do produto.");
                            scanner.nextLine(); // Limpa o buffer do scanner
                        }
                        break;
                    }

                    case 6: {
                        // Adicionar Pedido
                        System.out.println("IDs de Pedidos Existentes:");
                        List<Pedido> pedidosExistentes = pedidoDAO.listarPedidos();
                        for (Pedido p : pedidosExistentes) {
                            System.out.println("ID do Pedido: " + p.getIdPedido());
                        }

                        try {
                            System.out.print("\nDigite um novo ID para o pedido: ");
                            int idPedido = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer
                            
                            // Verifica se o ID do pedido já existe
                            Pedido pedidoExistente = pedidoDAO.buscarPedidoPorId(idPedido);
                            if (pedidoExistente != null) {
                                System.out.println("ID do pedido já existe! Escolha um ID único.");
                                break;
                            }
                            
                            System.out.print("Digite o total de itens no pedido: ");
                            int totalPedido = scanner.nextInt(); 
                            scanner.nextLine(); // Limpar o buffer
                            
                            System.out.print("Digite a data do pedido (yyyy-MM-dd): ");
                            String dataPedido = scanner.nextLine();
                            
                            System.out.print("Digite o valor total do pedido: ");
                            double valorTotal = scanner.nextDouble();
                            
                            Pedido pedido = new Pedido(idPedido, totalPedido, dataPedido, valorTotal);
                            pedidoDAO.inserirPedido(pedido);
                            System.out.println("Pedido adicionado com sucesso!");
                            
                        } catch (Exception e) {
                            System.out.println("Erro ao adicionar pedido: " + e.getMessage());
                        }
                        break;
                    }

                    case 7: {
                        // Remover Pedido
                        System.out.println("IDs de Pedidos Existentes:");
                        List<Pedido> pedidosExistentes = pedidoDAO.listarPedidos();
                        if (pedidosExistentes.isEmpty()) {
                            System.out.println("Nenhum pedido encontrado.");
                            break;
                        }

                        // Lista os pedidos com detalhes
                        for (Pedido p : pedidosExistentes) {
                            System.out.println("ID do Pedido: " + p.getIdPedido() + ", Data: " + p.getDataPedido() + ", Valor Total: R$ " + p.getTotalValor());
                        }

                        try {
                            System.out.print("\nDigite o ID do pedido a ser removido: ");
                            int idPedido = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer

                            // Verifica se o pedido com o ID existe
                            Pedido pedido = pedidoDAO.buscarPedidoPorId(idPedido);

                            if (pedido != null) {
                                System.out.println("Dados atuais do Pedido: " + pedido);

                                // Confirma a remoção
                                System.out.print("Tem certeza que deseja remover o pedido (s/n)? ");
                                char confirmacao = scanner.next().charAt(0);

                                if (confirmacao == 's' || confirmacao == 'S') {
                                    pedidoDAO.deletarPedido(idPedido);
                                    System.out.println("Pedido removido com sucesso!");
                                } else {
                                    System.out.println("Remoção cancelada.");
                                }
                            } else {
                                System.out.println("Pedido com ID " + idPedido + " não encontrado! Verifique o ID e tente novamente.");
                            }

                        } catch (Exception e) {
                            System.out.println("Erro ao remover pedido: " + e.getMessage());
                        }
                        break;
                    }


                    case 8: {
                        try {
                            // Exibe os IDs e detalhes de todos os pedidos existentes
                            System.out.println("Pedidos cadastrados:");
                            List<Pedido> pedidosExistentes = pedidoDAO.listarPedidos();
                            if (pedidosExistentes.isEmpty()) {
                                System.out.println("Nenhum pedido cadastrado.");
                                break; // Saia do caso se não houver pedidos
                            } else {
                                for (Pedido p : pedidosExistentes) {
                                    System.out.println("ID: " + p.getIdPedido() + ", Data: " + p.getDataPedido() + ", Valor Total: " + p.getTotalValor());
                                }
                                System.out.println(); // Linha em branco para separar
                            }

                            System.out.print("Digite o ID do pedido a ser atualizado: ");
                            int idPedido = scanner.nextInt();
                            Pedido pedido = pedidoDAO.buscarPedidoPorId(idPedido);
                            
                            if (pedido != null) {
                                System.out.println("Dados atuais do Pedido: ---------------------------------------");
                                System.out.println("ID do Pedido: " + pedido.getIdPedido());
                                System.out.println("Total de Itens no Pedido: " + pedido.getTotalPedido()); 
                                System.out.println("Data do Pedido: " + pedido.getDataPedido());
                                System.out.println("Valor Total do Pedido: R$ " + pedido.getTotalValor());
                                System.out.println("---------------------------------------");

                                scanner.nextLine(); // Consumir o newline

                                System.out.print("Nova data do pedido (yyyy-MM-dd, ou pressione Enter para manter): ");
                                String dataPedidoInput = scanner.nextLine();

                                // Pergunta sobre a atualização do total de itens
                                System.out.print("Novo total de itens (ou digite -1 para manter): ");
                                int totalItens = scanner.nextInt();

                                System.out.print("Novo valor total do pedido (ou digite -1 para manter): ");
                                double valorTotal = scanner.nextDouble();

                                // Aplicar mudanças apenas se um valor foi fornecido
                                if (!dataPedidoInput.isEmpty()) {
                                    pedido.setDataPedido(dataPedidoInput); // Mantém como String
                                }
                                if (totalItens >= 0) {
                                    pedido.setTotalPedido(totalItens); // Atualiza o total de itens
                                }
                                if (valorTotal >= 0) {
                                    pedido.setTotalValor(valorTotal); // Atualiza o valor total
                                }

                                // Confirmar atualização
                                System.out.print("Deseja salvar as alterações? (s/n): ");
                                char confirmacao = scanner.next().charAt(0);
                                if (confirmacao == 's' || confirmacao == 'S') {
                                    pedidoDAO.atualizarPedido(pedido);
                                    System.out.println("Pedido atualizado com sucesso!");
                                } else {
                                    System.out.println("Atualização cancelada.");
                                }
                            } else {
                                System.out.println("Pedido não encontrado!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Erro: Entrada inválida. Certifique-se de inserir os dados corretamente.");
                            scanner.nextLine(); // Limpar o buffer do scanner
                        } catch (Exception e) {
                            // Captura qualquer outra exceção que possa ocorrer
                            System.out.println("Erro inesperado: " + e.getMessage());
                        }
                        break;
                    }

                    case 9: {
                        // Listar Pedidos
                        System.out.println("Lista de pedidos:");
                        for (Pedido p : pedidoDAO.listarPedidos()) {
                            System.out.println(p);
                        }
                        break;
                    }
                    case 10: {
                    	// Buscar Pedido por ID
                        // Exibe os IDs e detalhes de todos os pedidos existentes
                        System.out.println("Pedidos cadastrados:");
                        List<Pedido> pedidosExistentes = pedidoDAO.listarPedidos(); // Método para listar todos os pedidos
                        if (pedidosExistentes.isEmpty()) {
                            System.out.println("Nenhum pedido cadastrado.");
                            break; // Sai do caso se não houver pedidos
                        } else {
                            for (Pedido p : pedidosExistentes) {
                                System.out.println("ID: " + p.getIdPedido() + ", Data: " + p.getDataPedido());
                            }
                            System.out.println(); // Linha em branco para separar
                        }

            
                        System.out.print("Digite o ID do pedido que deseja buscar: ");
                        try {
                            int idPedido = scanner.nextInt();
                            Pedido pedido = pedidoDAO.buscarPedidoPorId(idPedido);
                            if (pedido != null) {
                                System.out.println(pedido);
                            } else {
                                System.out.println("Pedido não encontrado!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Erro: Por favor, insira um número válido para o ID do pedido.");
                            scanner.nextLine(); // Limpa o buffer do scanner
                        }
                        break;
                    }
                    case 11: {
                    	// Adicionar Funcionário
                        // Exibe os IDs e detalhes de todos os funcionários existentes
                        System.out.println("Funcionários cadastrados:");
                        List<Funcionario> funcionariosExistentes = funcionarioDAO.listarFuncionarios(); // Método para listar todos os funcionários
                        if (funcionariosExistentes.isEmpty()) {
                            System.out.println("Nenhum funcionário cadastrado.");
                        } else {
                            for (Funcionario f : funcionariosExistentes) {
                                System.out.println("ID: " + f.getIdFunc() + ", Nome: " + f.getNome() + ", Cargo: " + f.getCargo() + ", Salário: R$ " + f.getSalario());
                            }
                            System.out.println(); // Linha em branco para separar
                        }

                        // Verificação e Adição de Funcionário
                        try {
                            System.out.print("Digite o Id do funcionário: ");
                            int idFunc = scanner.nextInt();
                            scanner.nextLine();

                            // Verifica se o ID já existe
                            boolean idExistente = funcionariosExistentes.stream()
                                                    .anyMatch(func -> func.getIdFunc() == idFunc);

                            if (idExistente) {
                                System.out.println("Erro: O ID do funcionário já existe. Tente novamente com um ID diferente.");
                            } else {
                                System.out.print("Digite o nome do funcionário: ");
                                String nome = scanner.nextLine();                        
                                System.out.print("Digite o cargo do funcionário: ");
                                String cargo = scanner.nextLine();                        
                                System.out.print("Digite o salário do funcionário: ");
                                double salario = scanner.nextDouble();

                                // Criar o objeto Funcionario
                                Funcionario funcionario = new Funcionario(idFunc, nome, cargo, salario);
                                funcionarioDAO.adicionarFuncionario(funcionario);
                                System.out.println("Funcionário adicionado com sucesso!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Erro: Entrada inválida. Por favor, insira o tipo de dado correto.");
                            scanner.nextLine(); // Limpa o buffer do scanner
                        } catch (Exception e) {
                            System.out.println("Erro inesperado: " + e.getMessage());
                        }
                        break;
                    }


                    case 12: {
                        // Remover Funcionário
                        System.out.println("IDs de Funcionários Existentes:");
                        List<Funcionario> funcionariosExistentes = funcionarioDAO.listarFuncionarios();
                        if (funcionariosExistentes.isEmpty()) {
                            System.out.println("Nenhum funcionário encontrado.");
                            break;
                        }

                        // Lista os funcionários com detalhes
                        for (Funcionario f : funcionariosExistentes) {
                            System.out.println("ID do Funcionário: " + f.getIdFunc() + ", Nome: " + f.getNome() + ", Cargo: " + f.getCargo() + ", Salário: R$ " + f.getSalario());
                        }

                        try {
                            System.out.print("\nDigite o ID do funcionário a ser removido: ");
                            int idFunc = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer

                            // Busca o funcionário para verificar se existe
                            Funcionario funcionario = funcionarioDAO.buscarFuncionarioPorId(idFunc);

                            if (funcionario != null) {
                                System.out.println("Dados atuais do Funcionário: " + funcionario);

                                // Confirma a remoção
                                System.out.print("Tem certeza que deseja remover o funcionário (s/n)? ");
                                char confirmacao = scanner.next().charAt(0);

                                if (confirmacao == 's' || confirmacao == 'S') {
                                    funcionarioDAO.excluirFuncionario(idFunc);
                                    System.out.println("Funcionário removido com sucesso!");
                                } else {
                                    System.out.println("Remoção cancelada.");
                                }
                            } else {
                                System.out.println("Funcionário não encontrado! Verifique o ID e tente novamente.");
                            }

                        } catch (Exception e) {
                            System.out.println("Erro ao remover funcionário: " + e.getMessage());
                        }
                        break;
                    }

                    case 13: {
                        // Atualizar Funcionário
                        System.out.println("IDs de Funcionários Existentes:");

                        List<Funcionario> funcionariosExistentes = funcionarioDAO.listarFuncionarios();
                        if (funcionariosExistentes.isEmpty()) {
                            System.out.println("Nenhum funcionário encontrado.");
                            break;
                        }

                        // Exibe os IDs e nomes dos funcionários para fácil identificação
                        for (Funcionario f : funcionariosExistentes) {
                            System.out.println("ID do Funcionário: " + f.getIdFunc() + ", Nome: " + f.getNome());
                        }

                        try {
                            System.out.print("\nDigite o ID do funcionário a ser atualizado: ");
                            int idFunc = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer

                            Funcionario funcionario = funcionarioDAO.buscarFuncionarioPorId(idFunc);
                            
                            if (funcionario != null) {
                                System.out.println("Dados atuais do Funcionário:\n" + funcionario);

                                // Atualizar o nome
                                System.out.print("Novo nome do funcionário (ou pressione Enter para manter o mesmo): ");
                                String nome = scanner.nextLine();
                                if (!nome.trim().isEmpty()) {
                                    funcionario.setNome(nome);
                                }

                                // Atualizar o cargo
                                System.out.print("Novo cargo do funcionário (ou pressione Enter para manter o mesmo): ");
                                String cargo = scanner.nextLine();
                                if (!cargo.trim().isEmpty()) {
                                    funcionario.setCargo(cargo);
                                }

                                // Atualizar o salário
                                System.out.print("Novo salário do funcionário (ou digite 0 para manter o mesmo): ");
                                double salario = scanner.nextDouble();
                                if (salario > 0) {
                                    funcionario.setSalario(salario);
                                }

                                // Atualizar o funcionário no banco
                                funcionarioDAO.atualizarFuncionario(funcionario);
                                System.out.println("Funcionário atualizado com sucesso!");
                                
                            } else {
                                System.out.println("Funcionário com ID " + idFunc + " não encontrado! Verifique o ID e tente novamente.");
                            }

                        } catch (Exception e) {
                            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
                        }
                        break;
                    }


                    case 14: {
                        // Listar Funcionários
                        System.out.println("Lista de funcionários:");
                        for (Funcionario f : funcionarioDAO.listarFuncionarios()) {
                            System.out.println(f);
                        }
                        break;
                    }
                    case 15: {
                        // Buscar Funcionário por ID
                        System.out.println("IDs de Funcionários Existentes:");
                        
                        // Cria uma nova instância de FuncionarioDAO
                        FuncionarioDAO funcionarioDAOInstance = new FuncionarioDAO(conexao);
                        List<Funcionario> funcionariosExistentes = funcionarioDAOInstance.listarFuncionarios();
                        
                        if (funcionariosExistentes.isEmpty()) {
                            System.out.println("Nenhum funcionário encontrado.");
                            break;
                        }

                        // Exibe os IDs e nomes dos funcionários para fácil identificação
                        for (Funcionario f : funcionariosExistentes) {
                            System.out.println("ID do Funcionário: " + f.getIdFunc() + ", Nome: " + f.getNome());
                        }

                        try {
                            System.out.print("\nDigite o ID do funcionário que deseja buscar: ");
                            int idFunc = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer

                            Funcionario funcionario = funcionarioDAOInstance.buscarFuncionarioPorId(idFunc);

                            if (funcionario != null) {
                                System.out.println("Detalhes do Funcionário:\n" + funcionario);
                            } else {
                                System.out.println("Funcionário com ID " + idFunc + " não encontrado! Verifique o ID e tente novamente.");
                            }

                        } catch (Exception e) {
                            System.out.println("Erro ao buscar funcionário: " + e.getMessage());
                        }
                        break;
                    }
                    
                    case 16: {
                        try {
                            // Listar IDs de PedProdFunc existentes
                            System.out.println("IDs de PedProdFunc Existentes:");
                            List<PedProdFunc> pedProdFuncExistentes = pedProdFuncDAO.listar(); // Método listar
                            if (pedProdFuncExistentes.isEmpty()) {
                                System.out.println("Nenhum PedProdFunc encontrado.");
                            } else {
                                for (PedProdFunc ppf : pedProdFuncExistentes) {
                                    System.out.println("ID do PedProdFunc: " + ppf.getIdProdPed()); // Método getIdProdPed
                                }
                            }

                            // Solicitar o ID do PedProdFunc
                            System.out.print("Digite o ID do PedProdFunc: ");
                            int idPedProdFunc = scanner.nextInt(); // Alterado para idPedProdFunc
                            scanner.nextLine(); // Limpar o buffer

                            // Verificar se o ID do PedProdFunc já existe
                            if (idPedProdFunc != 0) {
                                PedProdFunc existingPedProdFunc = pedProdFuncDAO.buscarPorId(idPedProdFunc);
                                if (existingPedProdFunc != null) {
                                    System.out.println("Erro: PedProdFunc com ID " + idPedProdFunc + " já existe. Não é possível atualizar com um ID existente.");
                                    break; // Saia do case se o ID já existir
                                }
                            }

                            // Listar IDs de Pedidos existentes
                            System.out.println("IDs de Pedidos Existentes:");
                            List<Pedido> pedidosExistentes = pedidoDAO.listarPedidos();
                            for (Pedido p : pedidosExistentes) {
                                System.out.println("ID do Pedido: " + p.getIdPedido());
                            }

                            System.out.print("Digite o ID do Pedido: ");
                            int idPedido = scanner.nextInt();

                            // Validar ID do Pedido
                            Pedido pedido = pedidoDAO.buscarPedidoPorId(idPedido);
                            if (pedido == null) {
                                throw new Exception("Pedido com ID " + idPedido + " não encontrado.");
                            }

                            // Listar IDs de Funcionários existentes
                            System.out.println("IDs de Funcionários Existentes:");
                            List<Funcionario> funcionariosExistentes = funcionarioDAO.listarFuncionarios();
                            for (Funcionario f : funcionariosExistentes) {
                                System.out.println("ID do Funcionário: " + f.getIdFunc());
                            }

                            System.out.print("Digite o ID do Funcionário: ");
                            int idFuncionario = scanner.nextInt();

                            // Validar ID do Funcionário
                            Funcionario funcionario = funcionarioDAO.buscarFuncionarioPorId(idFuncionario);
                            if (funcionario == null) {
                                throw new Exception("Funcionário com ID " + idFuncionario + " não encontrado.");
                            }

                            // Listar IDs de Produtos existentes
                            System.out.println("IDs de Produtos Existentes:");
                            List<Produto> produtosExistentes = produtoDAO.listarProdutos();
                            for (Produto p : produtosExistentes) {
                                System.out.println("ID do Produto: " + p.getIdProd());
                            }

                            System.out.print("Digite o ID do Produto: ");
                            int idProduto = scanner.nextInt();

                            // Validar ID do Produto
                            Produto produto = produtoDAO.buscarProdutoPorId(idProduto);
                            if (produto == null) {
                                throw new Exception("Produto com ID " + idProduto + " não encontrado.");
                            }

                            System.out.print("Digite a quantidade: ");
                            int quantidade = scanner.nextInt();
                            System.out.print("Digite o valor unitário: ");
                            double valorUnitario = scanner.nextDouble();

                            // Cria o objeto PedProdFunc
                            PedProdFunc pedProdFunc = new PedProdFunc(idPedProdFunc, funcionario, produto, pedido, quantidade, valorUnitario);
                            pedProdFuncDAO.inserir(pedProdFunc);
                            System.out.println("PedProdFunc adicionado com sucesso!");

                        } catch (InputMismatchException e) {
                            System.out.println("Erro: Entrada inválida! Por favor, insira números válidos.");
                            scanner.nextLine(); // Limpa o buffer do scanner
                        } catch (SQLException e) {
                            System.out.println("Erro ao inserir PedProdFunc: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                        break;
                    }

                    
                    case 17: {
                        // Listar IDs de PedProdFunc existentes
                        System.out.println("IDs de PedProdFunc Existentes:");
                        List<PedProdFunc> pedProdFuncExistentes = pedProdFuncDAO.listar(); // Assumindo que existe um método listarTodos
                        if (pedProdFuncExistentes.isEmpty()) {
                            System.out.println("Nenhum PedProdFunc encontrado.");
                            break; // Finaliza o case se não houver PedProdFunc
                        } else {
                            for (PedProdFunc ppf : pedProdFuncExistentes) {
                                System.out.println("ID do PedProdFunc: " + ppf.getIdProdPed()); // Assumindo que existe um método getIdProdPed
                            }
                        }

                        // Solicitar o ID do PedProdFunc a ser removido
                        System.out.print("Digite o ID do PedProdFunc a ser removido: ");
                        int idPedProdFunc = scanner.nextInt();

                        // Verificar se o PedProdFunc existe
                        PedProdFunc pedProdFunc = pedProdFuncDAO.buscarPorId(idPedProdFunc);
                        if (pedProdFunc != null) {
                            // PedProdFunc encontrado, solicitar confirmação
                            System.out.println("Você está prestes a remover o seguinte PedProdFunc:");
                            System.out.println(pedProdFunc); // Imprime detalhes do PedProdFunc para confirmação
                            System.out.print("Tem certeza que deseja remover este PedProdFunc (s/n)? ");
                            char confirmacao = scanner.next().charAt(0);

                            if (confirmacao == 's' || confirmacao == 'S') {
                                // PedProdFunc confirmado para remoção
                                pedProdFuncDAO.deletar(idPedProdFunc);
                                System.out.println("PedProdFunc removido com sucesso!");
                            } else {
                                System.out.println("Remoção cancelada.");
                            }
                        } else {
                            System.out.println("PedProdFunc não encontrado!");
                        }
                        break;
                    }
                 
                    case 18: {
                        // Atualizar PedProdFunc
                        System.out.println("IDs de PedProdFunc Existentes:");

                        List<PedProdFunc> pedProdFuncExistentes = pedProdFuncDAO.listar(); // Assumindo que existe um método listarTodos
                        if (pedProdFuncExistentes.isEmpty()) {
                            System.out.println("Nenhum PedProdFunc encontrado.");
                            break;
                        }

                        // Exibe os IDs e informações dos PedProdFunc para fácil identificação
                        for (PedProdFunc ppf : pedProdFuncExistentes) {
                            System.out.println("ID do PedProdFunc: " + ppf.getIdProdPed() + ", Pedido ID: " + ppf.getPedido().getIdPedido() + 
                                               ", Funcionário ID: " + ppf.getFuncionario().getIdFunc() + 
                                               ", Produto ID: " + ppf.getProduto().getIdProd());
                        }

                        try {
                            System.out.print("\nDigite o ID do PedProdFunc a ser atualizado: ");
                            int idPedProdFunc = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer

                            PedProdFunc pedProdFunc = pedProdFuncDAO.buscarPorId(idPedProdFunc);
                            
                            if (pedProdFunc != null) {
                                System.out.println("Dados atuais do PedProdFunc:\n" + pedProdFunc);

                                // Atualizar o ID do Pedido
                                System.out.print("Novo ID do Pedido (ou digite 0 para manter o mesmo): ");
                                int novoIdPedido = scanner.nextInt();
                                if (novoIdPedido > 0) {
                                    Pedido novoPedido = pedidoDAO.buscarPedidoPorId(novoIdPedido);
                                    if (novoPedido != null) {
                                        pedProdFunc.setPedido(novoPedido);
                                    } else {
                                        System.out.println("Pedido não encontrado com ID: " + novoIdPedido);
                                    }
                                }

                                // Atualizar o ID do Funcionário
                                System.out.print("Novo ID do Funcionário (ou digite 0 para manter o mesmo): ");
                                int novoIdFuncionario = scanner.nextInt();
                                if (novoIdFuncionario > 0) {
                                    Funcionario novoFuncionario = funcionarioDAO.buscarFuncionarioPorId(novoIdFuncionario);
                                    if (novoFuncionario != null) {
                                        pedProdFunc.setFuncionario(novoFuncionario);
                                    } else {
                                        System.out.println("Funcionário não encontrado com ID: " + novoIdFuncionario);
                                    }
                                }

                                // Atualizar o ID do Produto
                                System.out.print("Novo ID do Produto (ou digite 0 para manter o mesmo): ");
                                int novoIdProduto = scanner.nextInt();
                                if (novoIdProduto > 0) {
                                    Produto novoProduto = produtoDAO.buscarProdutoPorId(novoIdProduto);
                                    if (novoProduto != null) {
                                        pedProdFunc.setProduto(novoProduto);
                                    } else {
                                        System.out.println("Produto não encontrado com ID: " + novoIdProduto);
                                    }
                                }

                                // Atualizar a quantidade
                                System.out.print("Nova quantidade (ou digite 0 para manter a mesma): ");
                                int novaQuantidade = scanner.nextInt();
                                if (novaQuantidade > 0) {
                                    pedProdFunc.setQuantidade(novaQuantidade);
                                }

                                // Atualizar o valor unitário
                                System.out.print("Novo valor unitário (ou digite 0 para manter o mesmo): ");
                                double novoValorUnitario = scanner.nextDouble();
                                if (novoValorUnitario > 0) {
                                    pedProdFunc.setValorUnitario(novoValorUnitario);
                                }

                                // Atualizar o PedProdFunc no banco
                                pedProdFuncDAO.atualizar(pedProdFunc);
                                System.out.println("PedProdFunc atualizado com sucesso!");
                                
                            } else {
                                System.out.println("PedProdFunc com ID " + idPedProdFunc + " não encontrado! Verifique o ID e tente novamente.");
                            }

                        } catch (Exception e) {
                            System.out.println("Erro ao atualizar PedProdFunc: " + e.getMessage());
                        }
                        break;
                    }



                    case 19: {
                        // Listar PedProdFunc
                        System.out.println("Lista de PedProdFunc:");
                        for (PedProdFunc ppf : pedProdFuncDAO.listar()) {
                            System.out.println(ppf);
                        }
                        break;
                    }
                    
                    case 20: { // Buscar PedProdFunc
                        try {
                            // Listar IDs de PedProdFunc existentes
                            System.out.println("IDs de PedProdFunc Existentes:");
                            List<PedProdFunc> pedProdFuncExistentes = pedProdFuncDAO.listar(); // Assumindo que existe um método listar
                            if (pedProdFuncExistentes.isEmpty()) {
                                System.out.println("Nenhum PedProdFunc encontrado.");
                                break;
                            } else {
                                for (PedProdFunc ppf : pedProdFuncExistentes) {
                                    System.out.println("ID do PedProdFunc: " + ppf.getIdProdPed()); // Assumindo que existe um método getIdProdPed
                                }
                            }

                            // Solicitar o ID do PedProdFunc
                            System.out.print("Digite o ID do PedProdFunc a ser buscado: ");
                            int idPedProdFunc = scanner.nextInt();
                            scanner.nextLine(); // Limpar o buffer

                            // Verificar se o ID é válido
                            if (idPedProdFunc <= 0) {
                                System.out.println("Erro: O ID deve ser um número positivo.");
                                break;
                            }

                            // Buscar o PedProdFunc pelo ID
                            PedProdFunc pedProdFunc = pedProdFuncDAO.buscarPorId(idPedProdFunc);
                            
                            if (pedProdFunc != null) {
                                System.out.println("Detalhes do PedProdFunc encontrado:");
                                System.out.println(pedProdFunc);
                            } else {
                                System.out.println("Erro: PedProdFunc com ID " + idPedProdFunc + " não encontrado!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Erro: Entrada inválida! Por favor, insira um número válido para o ID.");
                            scanner.nextLine(); // Limpa o buffer do scanner
                        }
                        break;
                    }


                    
                    case 0: {
                        System.out.println("Saindo...");
                        return;
                    }
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                    }
                    }

                    } catch (SQLException e) {
                        e.printStackTrace();
                        
                    } finally {
                        // Garantir que o Scanner seja fechado após o uso
                        if (scanner != null) {
                            scanner.close();
                        }
                        // Fechar a conexão com o banco de dados
                        try {
                            if (conexao != null && !conexao.isClosed()) {
                                conexao.close();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
     }
    }
