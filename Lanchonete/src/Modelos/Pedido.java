package Modelos;


public class Pedido {
    // Atributos
    private int idPedido;
    private int totalPedido;
    private String dataPedido;
    private double totalValor;

    // Construtor
    public Pedido(int idPedido, int totalPedido, String dataPedido, double totalValor) {
        this.idPedido = idPedido;
        this.totalPedido = totalPedido;
        this.dataPedido = dataPedido;
        this.totalValor = totalValor;
    }

    // Métodos Getter & Setter
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(int totalPedido) {
        this.totalPedido = totalPedido;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public double getTotalValor() {
        return totalValor;
    }

    public void setTotalValor(double totalValor) {
        this.totalValor = totalValor;
    }

    // Sobrescrever o método toString para exibir as informações de maneira legível
    @Override
    public String toString() {
        return "---------------------------------------\n" +
               "ID do Pedido: " + idPedido + "\n" +
               "Total de Itens no Pedido: " + totalPedido + "\n" +
               "Data do Pedido: " + dataPedido + "\n" +
               "Valor Total do Pedido: R$ " + totalValor + "\n" +
               "---------------------------------------";
    }

    // Método para exibir informações do pedido
    public void exibirInformacoes() {
        System.out.println(this.toString());
    }
}
