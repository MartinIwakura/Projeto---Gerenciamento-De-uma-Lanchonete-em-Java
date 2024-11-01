package Modelos;

public class Funcionario {
    private int idFunc;
    private String nome;
    private String cargo;
    private double salario;

    // Construtor 
    public Funcionario(int idFunc, String nome, String cargo, double salario) {
        this.idFunc = idFunc;
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    // Getters e Setters
    public int getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(int idFunc) {
        this.idFunc = idFunc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    // Sobrescrever o método toString() para uma representação mais legível
    @Override
    public String toString() {
        return "----------------------------------------------------\n" +
               "ID: " + idFunc + "\n" +
               "Nome: " + nome + "\n" +
               "Cargo: " + cargo + "\n" +
               "Salário: R$ " + salario + "\n" +
               "----------------------------------------------------\n";
    }

    // Método para exibir informações
    public void exibirInformacoes() {
        System.out.println("----------------------------------------");
        System.out.println("ID: " + idFunc);
        System.out.println("Nome: " + nome);
        System.out.println("Cargo: " + cargo);
        System.out.println("Salário: R$ " + salario);
        System.out.println("----------------------------------------");
    }
}
