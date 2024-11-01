package Modelos;

public class Produto {
    // Atributos
    private int idProd;
    private String nomeProd;
    private String descricao;  
    private double valorProd;

    // Construtor
    public Produto(int idProd, String nomeProd, String descricao, double valorProd) {
        this.idProd = idProd;
        this.nomeProd = nomeProd;
        this.descricao = descricao;
        this.valorProd = valorProd;
    }

    // Métodos Getter & Setter
    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getNomeProd() {
        return nomeProd;
    }

    public void setNomeProd(String nomeProd) {
        this.nomeProd = nomeProd;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorProd() {
        return valorProd;
    }

    public void setValorProd(double valorProd) {
        this.valorProd = valorProd;
    }

    // Sobrescrever o método toString() para uma representação mais legível
    @Override
    public String toString() {
        return "ID: " + idProd + "\n" +
        	   "Produto: " + nomeProd + "\n" +
               "Descrição: " + descricao + "\n" +
               "Preço: R$ " + valorProd + "\n";
    }

    // Método para exibir informações do produto
    public void exibirInformacoes() {
    	System.out.println("ID: " + idProd);
        System.out.println("Produto: " + nomeProd);
        System.out.println("Descrição: " + descricao);
        System.out.println("Preço: R$ " + valorProd);
    }
}
