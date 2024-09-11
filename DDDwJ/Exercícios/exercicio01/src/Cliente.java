public class Cliente {
    private String nome;
    private double saldo;


    public Cliente(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
    }

    public void adicionarSaldo(double valor) {
        this.saldo += valor;
    }

    public int comprarProduto(double valor) {
        if (this.saldo >= valor) {
            this.saldo -= valor;
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "\nSaldo do cliente é:" + saldo;
    }
}
