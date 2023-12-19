package br.com.biblioteca;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;

import java.time.LocalDate;
import java.util.Scanner;

public class AppEmprestimo {
    Scanner scanner = new Scanner(System.in);
    long idUsuario, idLivro, id;
    String leitura;
    int tempo;

    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    LivroDAO livroDAO = new LivroDAO();

    public void gerenciarEmprestimo() {
        AppEmprestimo appEmprestimo = new AppEmprestimo();

        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("================================== GERENCIAR EMPRÉSTIMO ==================================");
            System.out.println("Escolha um opção:");
            System.out.println("[1] - Novo Empréstimo");
            System.out.println("[2] - Devolução");
            System.out.println("[/] - Voltar");

            String leitura = scanner.nextLine();

            if (leitura.equals("1")){
                appEmprestimo.addEmprestimo();
            } else if (leitura.equals("2")) {
                appEmprestimo.devolucao();
            }else if (leitura.equals("/")) {
                break;
            }else{
                System.out.println("Comando Inválido!");
                System.out.println("Escolha um opção válida: ");
            }
        }

    }

    public void addEmprestimo() {
        String nome, cpf, telefone;
        String titulo, autor, categoria, status;
        float valor;
        int paginas;


        System.out.println("Informe o ID do Usuário: ");
        while (true) {
            leitura = scanner.nextLine();
            try {
                idUsuario = Long.parseLong(leitura);
                usuarioDAO.findById(idUsuario);
                if (usuarioDAO.findById(idUsuario).isPresent()) {
                    nome = usuarioDAO.findById(idUsuario).get().getNomeUsuario();
                    cpf = usuarioDAO.findById(idUsuario).get().getCpfUsuario();
                    telefone = usuarioDAO.findById(idUsuario).get().getTelefoneUsuario();

                    System.out.println("---------------------------------------------------------------------------------");
                    System.out.println("Usuário ID: " + idUsuario);
                    System.out.println("Nome: " + nome);
                    System.out.println("CPF: " + cpf);
                    System.out.println("Telefone: " + telefone);
                    System.out.println("---------------------------------------------------------------------------------");
                    System.out.println("Confirmar o usuário: [s/n]");
                    leitura = scanner.nextLine();
                    if (leitura.equalsIgnoreCase("s")) {
                        break;
                    } else if (leitura.equalsIgnoreCase("n")) {
                        System.out.println("Digite outro ID: ");

                    }
                } else if (usuarioDAO.findById(idUsuario).isEmpty()) {
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

        System.out.println("Digite o ID do Livro: ");
        while (true) {
            leitura = scanner.nextLine();
            try {
                idLivro = Long.parseLong(leitura);
                if (livroDAO.findById(idLivro).isPresent()) {
                    titulo = livroDAO.findById(idLivro).get().getTituloLivro();
                    autor = livroDAO.findById(idLivro).get().getAutorLivro();
                    paginas = livroDAO.findById(idLivro).get().getPaginasLivro();
                    categoria = livroDAO.findById(idLivro).get().getCategoriaLivro();
                    status = livroDAO.findById(idLivro).get().getStatusLivro();

                    if (status.equalsIgnoreCase("indisponivel")) {
                        System.out.println("Este livro esta indisponivel.");
                        System.out.println("Vamos tentar outro.");
                        System.out.println("Digite outro ID de Livro: ");
                    } else {
                        System.out.println("---------------------------------------------------------------------------------");
                        System.out.println("Livro ID: " + idLivro);
                        System.out.println("Título: " + titulo);
                        System.out.println("Autor: " + autor);
                        System.out.println("Paginas: " + paginas);
                        System.out.println("Categoria: " + categoria);
                        System.out.println("Status: " + status);
                        System.out.println("---------------------------------------------------------------------------------");
                        System.out.println("Confirmar o Livro: [s/n]");
                        leitura = scanner.nextLine();
                        if (leitura.equalsIgnoreCase("s")) {
                            break;
                        } else if (leitura.equalsIgnoreCase("n")) {
                            System.out.println("Digite outro ID de Livro: ");
                        }
                    }

                } else if (livroDAO.findById(idLivro).isEmpty()) {
                    System.out.println("ID de Livro Inválido");
                    System.out.println("Digite outro ID de Livro: ");
                }
            } catch (Exception e) {
                if (leitura.equals("/")) {
                    return;
                } else {
                    System.out.println("ID de Livro inválido.");
                    System.out.println("Digite outro ID de Livro: ");
                }

            }
        }

        System.out.println("Digite a quantidade de dias do empréstimo: ");
        while (true) {
            leitura = scanner.nextLine();
            try {
                tempo = Integer.parseInt(leitura);
                break;
            } catch (Exception e) {
                System.out.println("A quantidade de dia deve conter apenas números.");
                System.out.println("Vamos tentar novamente.");
                System.out.println("Digite a quantidade de dias: ");
            }
        }

        valor = tempo * 3;
        System.out.printf("O emprestimo vai custar R$ %.2f \n", valor);
        System.out.println("Confirme o emprestimo [s/n]:");

        while (true) {
            leitura = scanner.nextLine();

            if (leitura.equalsIgnoreCase("s")) {
                Emprestimo emprestimo = new Emprestimo(idUsuario, idLivro, LocalDate.now(), tempo);
                emprestimoDAO.save(emprestimo);
                System.out.println("Emprestimo realizado com sucesso!");
                Livro livro = new Livro();
                livro.setIdLivro(idLivro);
                livro.setTituloLivro(titulo);
                livro.setAutorLivro(autor);
                livro.setPaginasLivro(paginas);
                livro.setCategoriaLivro(categoria);
                livro.setStatusLivro("Indisponivel");

                livroDAO.update(livro);
                break;
            } else if (leitura.equalsIgnoreCase("n") || leitura.equals("/")) {
                System.out.println("Empréstimo não realizado.");
                return;
            } else {
                System.out.println("Comando inválido. Digite [s] para sim ou [n] para não.");
            }
        }
    }

    public void devolucao() {
        System.out.println("Digite o ID do Livro a ser Devolvido:");
        while(true){
            leitura = scanner.nextLine();
            try{
                idLivro = Long.parseLong(leitura);
                if (livroDAO.findById(idLivro).isPresent()){
                    if (livroDAO.findById(idLivro).get().getStatusLivro().equals("Disponivel")){
                        System.out.println("O livro não tem empréstimo ativo.");
                        System.out.println("Insira outro ID de Livro:");
                    }else{
                        Livro livro = new Livro(
                                livroDAO.findById(idLivro).get().getIdLivro(),
                                livroDAO.findById(idLivro).get().getTituloLivro(),
                                livroDAO.findById(idLivro).get().getAutorLivro(),
                                livroDAO.findById(idLivro).get().getPaginasLivro(),
                                livroDAO.findById(idLivro).get().getCategoriaLivro(),
                                livroDAO.findById(idLivro).get().getStatusLivro()
                        );
                        livro.setStatusLivro("Disponivel");
                        livroDAO.update(livro);

                        Emprestimo emprestimo = new Emprestimo(
                                emprestimoDAO.findByIdLivro(idLivro).getFirst().getIdEmprestimo(),
                                emprestimoDAO.findByIdLivro(idLivro).getFirst().getIdUsuario(),
                                emprestimoDAO.findByIdLivro(idLivro).getFirst().getIdLivro(),
                                emprestimoDAO.findByIdLivro(idLivro).getFirst().getDiaEmprestimo(),
                                emprestimoDAO.findByIdLivro(idLivro).getFirst().getTempoEmprestimo(),
                                emprestimoDAO.findByIdLivro(idLivro).getFirst().getDiaDevolucao(),
                                emprestimoDAO.findByIdLivro(idLivro).getFirst().getStatus(),
                                emprestimoDAO.findByIdLivro(idLivro).getFirst().getSituacaoPrazo(),
                                emprestimoDAO.findByIdLivro(idLivro).getFirst().getValor()
                        );

                        emprestimo.setStatus("finalizado");
                        emprestimo.setSituacaoPrazo("NA");
                        emprestimoDAO.update(emprestimo);
                        System.out.println("Livro devolvido com sucesso!");
                        break;
                    }
                }
            } catch (Exception e) {
                if (leitura.equals("/")){
                    break;
                }else {
                    System.out.println("ID de Livro inválido ou não posui empréstimo ativo.");
                    System.out.println("Tente outro ID de Livro: ");
                }

            }

        }
    }
}
