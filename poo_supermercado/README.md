# POO Supermercado — E1 (PUCRS)

Projeto exemplo para o exercício E1 de Programação Orientada a Objetos.

## Estrutura
```
poo_supermercado/
 ├── src/
 │    ├── App.java
 │    └── Produto.java
 ├── dados/
 │    ├── produtos1.txt
 │    └── produtos2.txt
 ├── uml/
 │    └── produto_uml.png
 ├── doc/  (pasta para saída do javadoc)
 └── README.md
```

## Compilar e Executar

No terminal, dentro da pasta `poo_supermercado`:

```bash
# Compilar
javac -d out src/*.java

# Executar com o primeiro arquivo
java -cp out App dados/produtos1.txt

# Executar com o segundo arquivo
java -cp out App dados/produtos2.txt

# Se não passar arquivo, usa dados/produtos1.txt como padrão
java -cp out App
```
> Opcional: gerar JAR executável
```bash
jar --create --file dist/poo_supermercado.jar -C out .
java -cp dist/poo_supermercado.jar App dados/produtos1.txt
```

## Gerar JavaDoc
```bash
javadoc -d doc -sourcepath src App Produto
```

## Formato do arquivo de dados
Cada linha:
```
nome;categoria;preco;quantidade;validade(yyyy-MM-dd)
```
