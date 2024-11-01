package Modelos;

public class PedProdFunc {
    // Atributos
    private int idProdPed;    
    // Composição
    private Funcionario funcionario;  
    private Produto produto;
    private Pedido pedido;
    private int quantidade; 
    private double valorUnitario; 
    private double valorTotal;   

    // Construtor
    public PedProdFunc(int idProdPed, Funcionario funcionario, Produto produto, Pedido pedido, int quantidade, double valorUnitario) {
        this.idProdPed = idProdPed;
        this.funcionario = funcionario;
        this.produto = produto;
        this.pedido = pedido;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = quantidade * valorUnitario;  
    }

    // Métodos Getter & Setter
    public int getIdProdPed() {
        return idProdPed;
    }

    public void setIdProdPed(int idProdPed) {
        this.idProdPed = idProdPed;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.valorTotal = quantidade * valorUnitario;  // Recalcula o valor total quando a quantidade muda
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
        this.valorTotal = quantidade * valorUnitario;  // Recalcula o valor total quando o valor unitário muda
    }

    public double getValorTotal() {
        return valorTotal;
    }

 // Sobrescrever o método toString para exibir as informações de maneira legível
    @Override
    public String toString() {
        return "PedProdFunc {" +
                "ID Produto-Pedido: " + idProdPed +
                ", Nome do Produto: " + (produto != null ? produto.getNomeProd() : "N/A") + 
                ", ID do Produto: " + (produto != null ? produto.getIdProd() : "N/A") +
                ", ID do Pedido: " + (pedido != null ? pedido.getIdPedido() : "N/A") +
                ", Nome do Funcionário: " + (funcionario != null ? funcionario.getNome() : "N/A") + 
                ", ID do Funcionário: " + (funcionario != null ? funcionario.getIdFunc() : "N/A") +
                ", Quantidade: " + quantidade +
                ", Valor Unitário: R$ " + String.format("%.2f", valorUnitario) +
                ", Valor Total: R$ " + String.format("%.2f", getValorTotal()) +
                "}";
    }

    // Método para exibir informações de PedProdFunc
    public void exibirInformacoes() {
        System.out.println(this.toString());
    }
}