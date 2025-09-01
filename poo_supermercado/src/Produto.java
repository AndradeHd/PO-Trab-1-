import java.time.LocalDate;

/**
 * Representa um produto de supermercado.
 *
 * <p>Campos contemplam pelo menos três tipos de dados diferentes (String, double, int, LocalDate),
 * conforme exigência do trabalho.</p>
 */
public class Produto implements Comparable<Produto> {

    /** Nome do produto. */
    private String nome;

    /** Categoria (ex.: Alimento, Bebida, Higiene). */
    private String categoria;

    /** Preço unitário em reais. */
    private double preco;

    /** Quantidade em estoque. */
    private int quantidade;

    /** Data de validade (ISO-8601: yyyy-MM-dd). */
    private LocalDate validade;

    /**
     * Constrói um produto.
     *
     * @param nome nome do produto
     * @param categoria categoria do produto
     * @param preco preço unitário
     * @param quantidade quantidade em estoque
     * @param validade data de validade
     */
    public Produto(String nome, String categoria, double preco, int quantidade, LocalDate validade) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.validade = validade;
    }

    /** @return nome do produto */
    public String getNome() { return nome; }

    /** @return categoria do produto */
    public String getCategoria() { return categoria; }

    /** @return preço unitário */
    public double getPreco() { return preco; }

    /** @return quantidade em estoque */
    public int getQuantidade() { return quantidade; }

    /** @return data de validade */
    public LocalDate getValidade() { return validade; }

    /** @param preco novo preço unitário */
    public void setPreco(double preco) { this.preco = preco; }

    /** @param quantidade nova quantidade */
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    /**
     * Valor total em estoque deste item (preço * quantidade).
     * @return valor total
     */
    public double valorTotalEstoque() {
        return preco * quantidade;
    }

    /**
     * Ordena por preço crescente, e desempata por nome.
     */
    @Override
    public int compareTo(Produto outro) {
        int cmp = Double.compare(this.preco, outro.preco);
        if (cmp != 0) return cmp;
        return this.nome.compareToIgnoreCase(outro.nome);
    }

    @Override
    public String toString() {
        return nome + " | " + categoria + " | R$" + preco + " | Qtd: " + quantidade + " | Val: " + validade;
    }
}
