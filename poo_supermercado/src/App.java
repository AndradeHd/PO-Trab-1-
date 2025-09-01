import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Aplicação principal do trabalho.
 *
 * Lê um arquivo texto contendo produtos separados por ';' no formato:
 * nome;categoria;preco;quantidade;validade(yyyy-MM-dd)
 */
public class App {

    public static void main(String[] args) {
        // Se não passar argumento, usa produtos1.txt por padrão
        String arquivo = (args.length < 1) ? "dados/produtos1.txt" : args[0];

        List<Produto> produtos = new ArrayList<>();
        try {
            produtos = carregar(arquivo);
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo '" + arquivo + "': " + e.getMessage());
            return;
        } catch (RuntimeException e) {
            System.out.println("Erro de formato nos dados: " + e.getMessage());
            return;
        }

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto carregado.");
            return;
        }

        // Estatísticas
        DoubleSummaryStatistics stats = produtos.stream().mapToDouble(Produto::getPreco).summaryStatistics();
        Produto maisCaro = Collections.max(produtos, Comparator.comparingDouble(Produto::getPreco));
        Produto maisBarato = Collections.min(produtos, Comparator.comparingDouble(Produto::getPreco));

        // Ordenações
        List<Produto> porPreco = new ArrayList<>(produtos);
        Collections.sort(porPreco);

        List<Produto> porValidade = new ArrayList<>(produtos);
        porValidade.sort(Comparator.comparing(Produto::getValidade));

        // Agrupamento por categoria
        Map<String, List<Produto>> porCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getCategoria));

        // Valor total
        double totalEstoque = produtos.stream().mapToDouble(Produto::valorTotalEstoque).sum();

        // Saída
        System.out.println("=== Arquivo: " + arquivo + " ===");

        System.out.println("\n=== Lista de Produtos (original) ===");
        produtos.forEach(System.out::println);

        System.out.println("\n=== Estatísticas de Preço ===");
        System.out.printf("Quantidade: %d%n", stats.getCount());
        System.out.printf("Média: R$%.2f | Mín: R$%.2f | Máx: R$%.2f%n",
                stats.getAverage(), stats.getMin(), stats.getMax());
        System.out.println("Mais caro: " + maisCaro.getNome() + " (R$" + maisCaro.getPreco() + ")");
        System.out.println("Mais barato: " + maisBarato.getNome() + " (R$" + maisBarato.getPreco() + ")");

        System.out.println("\n=== Valor Total em Estoque ===");
        System.out.printf("R$%.2f%n", totalEstoque);

        System.out.println("\n=== Ordenado por Preço ===");
        porPreco.forEach(System.out::println);

        System.out.println("\n=== Ordenado por Validade ===");
        porValidade.forEach(System.out::println);

        System.out.println("\n=== Agrupado por Categoria ===");
        porCategoria.forEach((cat, lista) -> {
            System.out.println("- " + cat + " (" + lista.size() + " itens)");
            lista.forEach(p -> System.out.println("  • " + p));
        });
    }

    /**
     * Carrega produtos de um arquivo texto.
     */
    public static List<Produto> carregar(String arquivo) throws IOException {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int linhaNum = 0;
            while ((linha = br.readLine()) != null) {
                linhaNum++;
                if (linha.isBlank()) continue;

                String[] campos = linha.split(";");
                if (campos.length != 5) {
                    throw new RuntimeException("Linha " + linhaNum +
                            " inválida: esperado 5 campos, veio " + campos.length);
                }

                String nome = campos[0].trim();
                String categoria = campos[1].trim();
                double preco = Double.parseDouble(campos[2].trim());
                int quantidade = Integer.parseInt(campos[3].trim());
                LocalDate validade = LocalDate.parse(campos[4].trim());

                produtos.add(new Produto(nome, categoria, preco, quantidade, validade));
            }
        }
        return produtos;
    }
}
