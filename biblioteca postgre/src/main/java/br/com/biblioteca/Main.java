package br.com.biblioteca;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String leitura;
        AppEmprestimo appEmprestimo = new AppEmprestimo();
        AppLivro appLivro = new AppLivro();
        AppUsuario appUsuario = new AppUsuario();


        while (true) {
            System.out.println("================================== BIBLIOTECA ==================================");
            System.out.println("Escolha um opção:");
            System.out.println("[1] - GERENCIAR EMPRÉSTIMO");
            System.out.println("[2] - GERENCIAR LIVRO");
            System.out.println("[3] - GERENCIAR USUÁRIO");
            System.out.println("[/] - SAIR");

            leitura = scanner.nextLine();
            if (leitura.equals("1")) {
                appEmprestimo.gerenciarEmprestimo();

            } else if (leitura.equals("2")) {
                appLivro.gerenciarLivro();

            } else if (leitura.equals("3")) {
                appUsuario.gerenciarUsuario();
            } else if (leitura.equals("/")) {
                break;
            } else {
                System.out.println("Comando Inválido!");
                System.out.println("Escolha um opção válida: ");
            }

        }

    }
}
