package br.com.biblioteca;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.model.Livro;
import java.util.Scanner;


public class AppLivro {
    Scanner scanner = new Scanner(System.in);
    LivroDAO livroDAO = new LivroDAO();
    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    String leitura;

    public void gerenciarLivro() {
        AppLivro appLivro = new AppLivro();
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("================================== GERENCIAR LIVRO ==================================");
            System.out.println("Escolha um opção:");
            System.out.println("[1] - Cadastrar Novo Livro");
            System.out.println("[2] - Editar Livro");
            System.out.println("[3] - Remover Livro");
            System.out.println("[4] - Buscar Livro");
            System.out.println("[/] - Voltar");

            String leitura = scanner.nextLine();

            if (leitura.equals("1")){
                appLivro.addLivro();
            } else if (leitura.equals("2")) {
                appLivro.editarLivro();
            }else if (leitura.equals("3")) {
                appLivro.deletarLivro();
            }else if (leitura.equals("4")) {
                appLivro.findLivro();
            }else if (leitura.equals("/")) {
                break;
            }else{
                System.out.println("Comando Inválido!");
                System.out.println("Escolha um opção válida: ");
            }
        }
    }


    public void addLivro() {
        String titulo, autor, categoria;
        int paginas;
        System.out.println("================================== CADASTRAR LIVRO ==================================");
        System.out.println("Digite o título do novo Livro: ");
        while (true) {
            titulo = scanner.nextLine();
            if (titulo.isBlank()) {
                System.out.println("Preenchimento Obrigatório. Insira o título:");
            } else if (titulo.equalsIgnoreCase("/")) {
                System.out.println("Cadastro de Novo Livro Não Realizado!!");
                return;
            } else {
                if (titulo.length() <= 150) {
                    System.out.println("Título inserido!");
                    break;
                } else {
                    System.out.println("Título muito grande, deve ter no máximo 150 caracteres.");
                }
            }
        }

        System.out.println("Digite o nome do autor do novo Livro: ");
        while (true) {
            autor = scanner.nextLine();
            if (autor.isBlank()) {
                System.out.println("Preenchimento Obrigatório. Insira o nome do autor:");
            } else if (autor.equalsIgnoreCase("/")) {
                System.out.println("Cadastro de Novo Livro Não Realizado!!");
                return;
            } else {
                if (autor.length() <= 100) {
                    System.out.println("Nome do autor inserido!");
                    break;
                } else {
                    System.out.println("Nome do autor deve conter no máximo 100 caracteres.");
                }
            }
        }

        System.out.println("Digite o número de páginas do novo Livro: ");
        while (true) {
            leitura = scanner.nextLine();
            try {
                paginas = Integer.parseInt(leitura);
                if (paginas > 0) {
                    break;
                } else {
                    System.out.println("O número de páginas não pode ser nulo. Insira um número de páginas válido: ");
                }
            } catch (Exception e) {
                if (leitura.equals("/")) {
                    System.out.println("Cadastro de Novo Livro Não Realizado!!");
                    return;
                } else {
                    System.out.println("O número de páginas inválido. Insira um número de páginas válido: ");
                }
            }
        }

        System.out.println("Digite a categoria do novo Livro: ");
        while (true) {
            categoria = scanner.nextLine();
            if (categoria.isBlank()) {
                System.out.println("Preenchimento Obrigatório. Insira a categoria:");
            } else if (categoria.equalsIgnoreCase("/")) {
                System.out.println("Cadastro de Novo Livro Não Realizado!!");
                return;
            } else {
                if (categoria.length() <= 100) {
                    System.out.println("Categoria inserida!");
                    break;
                } else {
                    System.out.println("Categoria deve conter no máximo 100 caracteres.");
                }
            }
        }

        Livro novoLivro = new Livro();
        novoLivro.setTituloLivro(titulo);
        novoLivro.setAutorLivro(autor);
        novoLivro.setPaginasLivro(paginas);
        novoLivro.setCategoriaLivro(categoria);
        novoLivro.setStatusLivro("Disponível");

        livroDAO.save(novoLivro);
        System.out.println("Novo Livro Cadastrado com Sucesso.");

    }

    public void editarLivro() {
        String titulo, autor, categoria, status;
        int paginas;
        long id;

        System.out.println("================================== EDITAR LIVRO =====================================");
        System.out.println("Digite o ID do Livro: ");

        while (true) {
            leitura = scanner.nextLine();
            try {
                id = Long.parseLong(leitura);
                if (livroDAO.findById(id).isPresent()) {
                    titulo = livroDAO.findById(id).get().getTituloLivro();
                    autor = livroDAO.findById(id).get().getAutorLivro();
                    paginas = livroDAO.findById(id).get().getPaginasLivro();
                    categoria = livroDAO.findById(id).get().getCategoriaLivro();
                    status = livroDAO.findById(id).get().getStatusLivro();

                    System.out.println("Livro ID: " + id);
                    System.out.println("Título: " + titulo);
                    System.out.println("Autor: " + autor);
                    System.out.println("Paginas: " + paginas);
                    System.out.println("Categoria: " + categoria);
                    System.out.println("Status: " + status);
                    System.out.println("Confirmar o Livro: [s/n]");
                    leitura = scanner.nextLine();
                    if (leitura.equalsIgnoreCase("s")) {
                        break;
                    } else if (leitura.equalsIgnoreCase("n")) {
                        System.out.println("Digite outro ID: ");
                    }
                } else if (livroDAO.findById(id).isEmpty()) {
                    System.out.println("ID Inválido");
                }
            } catch (Exception e) {
                if (leitura.equals("/")) {
                    return;
                } else {
                    System.out.println("ID inválido");
                }

            }
        }

        Livro livro = new Livro(id, titulo, autor, paginas, categoria, status);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("[1] - Editar TITULO");
            System.out.println("[2] - Editar AUTOR");
            System.out.println("[3] - Editar PAGINAS");
            System.out.println("[4] - Editar CATEGORIA");
            System.out.println("[/] - Voltar");
            leitura = scanner.nextLine();
            if (leitura.equals("1")) {
                System.out.println("Digite o novo TITULO: ");
                while (true) {
                    leitura = scanner.nextLine();
                    if (leitura.isBlank()) {
                        System.out.println("Preenchimento Obrigatório. Insira o Titulo:");
                    } else if (leitura.equalsIgnoreCase("/")) {
                        System.out.println("Alteração de Titulo de Livro Não Realizada!!");
                        break;
                    } else {
                        livro.setTituloLivro(leitura);
                        livroDAO.update(livro);
                        System.out.println("Titulo Alterado!");
                        break;
                    }
                }
            } else if (leitura.equals("2")) {
                System.out.println("Digite o nome do novo Autor: ");
                while (true) {
                    leitura = scanner.nextLine();
                    if (leitura.isBlank()) {
                        System.out.println("Preenchimento Obrigatório. Insira o Nomde do Autor:");
                    } else if (leitura.equalsIgnoreCase("/")) {
                        System.out.println("Alteração de Autor de Livro Não Realizada!!");
                        break;
                    } else {
                        livro.setAutorLivro(leitura);
                        livroDAO.update(livro);
                        System.out.println("Autor Alterado!");
                        break;
                    }
                }
            } else if (leitura.equals("3")) {
                System.out.println("Digite o novo número de Páginas: ");
                while (true) {
                    leitura = scanner.nextLine();
                    try {
                        paginas = Integer.parseInt(leitura);
                        if (paginas > 0) {
                            livro.setPaginasLivro(paginas);
                            livroDAO.update(livro);
                            System.out.println("Número de Páginas Alterado!");
                            break;
                        } else {
                            System.out.println("O número de páginas não pode ser nulo. Insira um número de páginas válido: ");
                        }
                    } catch (Exception e) {
                        if (leitura.equals("/")) {
                            System.out.println("Alteração de Número de Páginas de Livro Não Realizada!!");
                            break;
                        } else {
                            System.out.println("Número de páginas inválido. Insira um número de páginas válido: ");
                        }
                    }
                }

            } else if (leitura.equals("4")) {
                System.out.println("Digite a nova Categoria do Livro: ");
                while (true) {
                    leitura = scanner.nextLine();
                    if (leitura.isBlank()) {
                        System.out.println("Preenchimento Obrigatório. Insira a Categoria:");
                    } else if (leitura.equalsIgnoreCase("/")) {
                        System.out.println("Alteração de Categoria de Livro Não Realizada!!");
                        break;
                    } else {
                        livro.setCategoriaLivro(leitura);
                        livroDAO.update(livro);
                        System.out.println("Categoria Alterada!");
                        break;
                    }
                }

            } else if (leitura.equals("/")) {
                break;
            } else {
                System.out.println("Comando Inválido!");
            }
        }
    }


    public void deletarLivro() {
        String titulo, autor, categoria, status;
        int paginas;
        long id;

        System.out.println("================================== REMOVER LIVRO =====================================");
        System.out.println("Digite o ID do Livro: ");
        while (true) {
            leitura = scanner.nextLine();
            try {
                id = Long.parseLong(leitura);
                if (livroDAO.findById(id).isPresent()) {
                    titulo = livroDAO.findById(id).get().getTituloLivro();
                    autor = livroDAO.findById(id).get().getAutorLivro();
                    paginas = livroDAO.findById(id).get().getPaginasLivro();
                    categoria = livroDAO.findById(id).get().getCategoriaLivro();
                    status = livroDAO.findById(id).get().getStatusLivro();

                    System.out.println("Livro ID: " + id);
                    System.out.println("Título: " + titulo);
                    System.out.println("Autor: " + autor);
                    System.out.println("Páginas: " + paginas);
                    System.out.println("Categoria: " + categoria);
                    System.out.println("Status: " + status);
                    System.out.println("Confirmar o Livro: [s/n]");
                    leitura = scanner.nextLine();
                    if (leitura.equalsIgnoreCase("s")) {
                        break;
                    } else if (leitura.equalsIgnoreCase("n")) {
                        System.out.println("Digite outro ID: ");
                    }
                } else if (livroDAO.findById(id).isEmpty()) {
                    System.out.println("ID Inválido");
                }
            } catch (Exception e) {
                if (leitura.equals("/")) {
                    return;
                } else {
                    System.out.println("ID inválido");
                }

            }
        }

        //checar se livro tem emprestimo aberto

        for (int i = 0; i < emprestimoDAO.findByIdLivro(id).size(); i++) {
            if (emprestimoDAO.findByIdLivro(id).get(i).getStatus().equalsIgnoreCase("aberto")) {
                System.out.println("Livro possui empréstimo em aberto.");
                System.out.println("Não pode ser removido!!");
                return;
            }
        }

        livroDAO.delete(id);
        System.out.println("Livro Removido");

    }

    public void findLivro() {
        String titulo, autor, categoria, status;
        int paginas;
        long id;

        System.out.println("================================== LIVROS =====================================");

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("[1] - Por ID do Livro");
            System.out.println("[2] - Por TITULO do Livro");
            System.out.println("[3] - Por AUTOR do Livro");
            System.out.println("[4] - Por CATEGORIA do Livro");
            System.out.println("[5] - Por STATUS do Livro");
            System.out.println("[/] - Voltar");
            leitura = scanner.nextLine();
            if (leitura.equals("1")) {

                while (true) {
                    System.out.println("Digite o ID do Livro: ");
                    leitura = scanner.nextLine();
                    try {
                        id = Long.parseLong(leitura);
                        if (livroDAO.findById(id).isPresent()) {
                            titulo = livroDAO.findById(id).get().getTituloLivro();
                            autor = livroDAO.findById(id).get().getAutorLivro();
                            categoria = livroDAO.findById(id).get().getCategoriaLivro();
                            paginas = livroDAO.findById(id).get().getPaginasLivro();
                            status = livroDAO.findById(id).get().getStatusLivro();

                            System.out.println("---------------------------------------------------------------------------------");
                            System.out.println("Livro ID: " + id);
                            System.out.println("Título: " + titulo);
                            System.out.println("Autor: " + autor);
                            System.out.println("Paginas: " + paginas);
                            System.out.println("Categoria: " + categoria);
                            System.out.println("Status: " + status);
                            System.out.println("---------------------------------------------------------------------------------");
                            break;
                        } else if (livroDAO.findById(id).isEmpty()) {
                            System.out.println("ID Inválido");
                        }
                    } catch (Exception e) {
                        if (leitura.equals("/")) {
                            break;
                        } else {
                            System.out.println("ID inválido");
                        }

                    }
                }

            } else if (leitura.equals("2")) {
                while (true) {

                    System.out.println("Digite o TITULO do Livro: ");
                    leitura = scanner.nextLine();
                    if (leitura.equals("/")){
                        break;
                    }
                    int cont = 0;
                    for (int i = 0; i < livroDAO.findAll().size(); i++) {
                        if (!leitura.isBlank() && livroDAO.findAll().get(i).getTituloLivro().toLowerCase().contains(leitura.toLowerCase())) {
                            System.out.println("---------------------------------------------------------------------------------");
                            System.out.println("Livro ID: " + livroDAO.findAll().get(i).getIdLivro());
                            System.out.println("Tírtulo: " + livroDAO.findAll().get(i).getTituloLivro());
                            System.out.println("Autor: " + livroDAO.findAll().get(i).getAutorLivro());
                            System.out.println("Páginas: " + livroDAO.findAll().get(i).getPaginasLivro());
                            System.out.println("Categoria: " + livroDAO.findAll().get(i).getCategoriaLivro());
                            System.out.println("Status: " + livroDAO.findAll().get(i).getStatusLivro());
                            cont += 1;
                        }
                    }

                    if (cont < 1 && leitura.isBlank()) {
                        System.out.println("Você Precisa Digitar um Título.");
                    } else if (cont<1) {
                        System.out.println("Não foi encontrado Livro com o Titulo Digitado.");
                    } else {
                        System.out.println("---------------------------------------------------------------------------------");
                        break;
                    }
                }

            } else if (leitura.equals("3")) {

                while (true) {

                    System.out.println("Digite o nome do Autor do Livro: ");
                    leitura = scanner.nextLine();
                    if (leitura.equals("/")){
                        break;
                    }
                    int cont = 0;
                    for (int i = 0; i < livroDAO.findAll().size(); i++) {
                        if (!leitura.isBlank() && livroDAO.findAll().get(i).getAutorLivro().toLowerCase().contains(leitura.toLowerCase())) {
                            System.out.println("---------------------------------------------------------------------------------");
                            System.out.println("Livro ID: " + livroDAO.findAll().get(i).getIdLivro());
                            System.out.println("Tírtulo: " + livroDAO.findAll().get(i).getTituloLivro());
                            System.out.println("Autor: " + livroDAO.findAll().get(i).getAutorLivro());
                            System.out.println("Páginas: " + livroDAO.findAll().get(i).getPaginasLivro());
                            System.out.println("Categoria: " + livroDAO.findAll().get(i).getCategoriaLivro());
                            System.out.println("Status: " + livroDAO.findAll().get(i).getStatusLivro());
                            cont += 1;
                        }
                    }

                    if (cont < 1 && leitura.isBlank()) {
                        System.out.println("Você Precisa Digitar um Autor.");
                    } else if (cont<1) {
                        System.out.println("Não foi encontrado Livro com o Autor Digitado.");
                    } else {
                        System.out.println("---------------------------------------------------------------------------------");
                        break;
                    }
                }


            } else if (leitura.equals("4")) {

                while (true) {

                    System.out.println("Digite a Categoria: ");
                    leitura = scanner.nextLine();
                    if (leitura.equals("/")){
                        break;
                    }
                    int cont = 0;
                    for (int i = 0; i < livroDAO.findAll().size(); i++) {
                        if (!leitura.isBlank() && livroDAO.findAll().get(i).getCategoriaLivro().toLowerCase().contains(leitura.toLowerCase())) {
                            System.out.println("---------------------------------------------------------------------------------");
                            System.out.println("Livro ID: " + livroDAO.findAll().get(i).getIdLivro());
                            System.out.println("Tírtulo: " + livroDAO.findAll().get(i).getTituloLivro());
                            System.out.println("Autor: " + livroDAO.findAll().get(i).getAutorLivro());
                            System.out.println("Páginas: " + livroDAO.findAll().get(i).getPaginasLivro());
                            System.out.println("Categoria: " + livroDAO.findAll().get(i).getCategoriaLivro());
                            System.out.println("Status: " + livroDAO.findAll().get(i).getStatusLivro());
                            cont += 1;
                        }
                    }

                    if (cont < 1 && leitura.isBlank()) {
                        System.out.println("Você Precisa Digitar uma Categoria.");
                    } else if (cont<1) {
                        System.out.println("Não foi encontrado Livro com a Categoria Digitada.");
                    } else {
                        System.out.println("---------------------------------------------------------------------------------");
                        break;
                    }
                }


            }else if (leitura.equals("5")){
                while (true) {

                    System.out.println("Digite o Status (Disponivel ou Indisponivel): ");
                    leitura = scanner.nextLine();
                    if (leitura.equals("/")){
                        break;
                    }
                    int cont = 0;
                    for (int i = 0; i < livroDAO.findAll().size(); i++) {
                        if (!leitura.isBlank() && livroDAO.findAll().get(i).getStatusLivro().toLowerCase().contains(leitura.toLowerCase())) {
                            System.out.println("---------------------------------------------------------------------------------");
                            System.out.println("Livro ID: " + livroDAO.findAll().get(i).getIdLivro());
                            System.out.println("Tírtulo: " + livroDAO.findAll().get(i).getTituloLivro());
                            System.out.println("Autor: " + livroDAO.findAll().get(i).getAutorLivro());
                            System.out.println("Páginas: " + livroDAO.findAll().get(i).getPaginasLivro());
                            System.out.println("Categoria: " + livroDAO.findAll().get(i).getCategoriaLivro());
                            System.out.println("Status: " + livroDAO.findAll().get(i).getStatusLivro());
                            cont += 1;
                        }
                    }

                    if (cont < 1 && leitura.isBlank()) {
                        System.out.println("Você Precisa Digitar um Status.");
                    } else if (cont<1) {
                        System.out.println("Não foi encontrado Livro com o Status Digitado.");
                    } else {
                        System.out.println("---------------------------------------------------------------------------------");
                        break;
                    }
                }


            }else if (leitura.equals("/")) {
                break;

            } else {
                System.out.println("Comando Inválido!");
            }


        }
    }

}




