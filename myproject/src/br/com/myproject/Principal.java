package br.com.myproject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 Inserir todos os funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.73"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 Remover o funcionário “João” da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 Imprimir todos os funcionários com todas suas informações
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Lista de Funcionários:");
        for (Funcionario funcionario : funcionarios) {
            String formattedDate = funcionario.getDataNascimento().format(formatter);
            System.out.println(funcionario.getNome() + 
                               "\nData de Nascimento: " + formattedDate + 
                               "\nSalário: " + String.format("%,.2f", funcionario.getSalario()) + 
                               "\nFunção: " + funcionario.getFuncao() + 
                               "\n--------------------------------");
        }

        // 3.4 Atualizar lista de funcionários com novo salário (10% de aumento)
        funcionarios.forEach(funcionario -> funcionario.setSalario(funcionario.getSalario().multiply(new BigDecimal("1.10"))));

        // 3.5 Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 Imprimir os funcionários, agrupados por função
        System.out.println("\nFuncionários Agrupados por Função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(funcionario -> System.out.println(funcionario.getNome() + 
                                                            "\nData de Nascimento: " + funcionario.getDataNascimento().format(formatter) + 
                                                            "\nSalário: " + String.format("%,.2f", funcionario.getSalario()) + 
                                                            "\nFunção: " + funcionario.getFuncao() + 
                                                            "\n--------------------------------"));
        });

        // 3.8 Imprimir os funcionários que fazem aniversário no mês 10 e 12
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 || funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(funcionario -> System.out.println(funcionario.getNome() + 
                                                           "\nData de Nascimento: " + funcionario.getDataNascimento().format(formatter) + 
                                                           "\nSalário: " + String.format("%,.2f", funcionario.getSalario()) + 
                                                           "\nFunção: " + funcionario.getFuncao() + 
                                                           "\n--------------------------------"));

        // 3.9 Imprimir o funcionário com a maior idade (exibir nome e idade)
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow();
        int idade = LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear();
        System.out.println("\nFuncionário com a maior idade: " + maisVelho.getNome() + ", Idade: " + idade);

        // 3.10 Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\nFuncionários em Ordem Alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario -> System.out.println(funcionario.getNome() + 
                                                           "\nData de Nascimento: " + funcionario.getDataNascimento().format(formatter) + 
                                                           "\nSalário: " + String.format("%,.2f", funcionario.getSalario()) + 
                                                           "\nFunção: " + funcionario.getFuncao() + 
                                                           "\n--------------------------------"));

        // 3.11 Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos Salários: " + String.format("%,.2f", totalSalarios));

        // 3.12 Imprimir quantos salários mínimos ganha cada funcionário (considerando que o salário mínimo é R$1212.00)
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nQuantidade de Salários Mínimos que Cada Funcionário Ganha:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(funcionario.getNome() + ": " + salariosMinimos + " salários mínimos");
        }
    }
}
