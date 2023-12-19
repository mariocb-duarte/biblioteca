package br.com.biblioteca;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.model.Usuario;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUsuario {
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    Scanner scanner = new Scanner(System.in);

    public void gerenciarUsuario() {
        AppUsuario appUsuario = new AppUsuario();
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("================================== GERENCIAR USUÁRIO ==================================");
            System.out.println("Escolha um opção:");
            System.out.println("[1] - Cadastrar Novo Usuário");
            System.out.println("[2] - Editar Usuário");
            System.out.println("[3] - Remover Usuário");
            System.out.println("[4] - Buscar Usuário");
            System.out.println("[/] - Voltar");

            String leitura = scanner.nextLine();

            if (leitura.equals("1")){
                appUsuario.addUsuario();
            } else if (leitura.equals("2")) {
                appUsuario.editarUsuario();
            }else if (leitura.equals("3")) {
                appUsuario.deletarUsuario();
            }else if (leitura.equals("4")) {
                appUsuario.findUsuario();
            }else if (leitura.equals("/")) {
                break;
            }else{
                System.out.println("Comando Inválido!");
                System.out.println("Escolha um opção válida: ");
            }
        }
    }

    public void addUsuario() {
        String nome, cpf, telefone;
        //checar se usuario ja existe
        //cpf deve ser unico, não pode ter dois usuarios com o mesmo cpf
        //usuario deve ter obrigatoriamente nome, cpf e numero de telefone
        System.out.println("================================== CADASTRAR USUÁRIO ==================================");
        System.out.println("Digite o nome do novo usuário: ");
        while (true) {
            nome = scanner.nextLine();
            if (nome.isBlank()) {
                System.out.println("Preenchimento Obrigatório. Insira o nome:");
            } else if (nome.equalsIgnoreCase("/")) {
                System.out.println("Cadastro de Novo Usuário Não Realizado!!");
                return;
            } else {
                System.out.println("Nome inserido!");
                break;
            }
        }

        System.out.println("Digite o cpf do novo usuário (apenas números): ");
        while (true) {
            cpf = scanner.nextLine();

            if (cpf.isBlank()) {
                System.out.println("Preenchimento Obrigatório. Insira o cpf:");
            } else if (cpf.equalsIgnoreCase("/")) {
                System.out.println("Cadastro de Novo Usuário Não Realizado!!");
                return;
            } else if (isCpf(cpf)) {
                if (usuarioDAO.findByCpf(cpf).isPresent()) {
                    System.out.println("Já existe usuário cadastrado com este CPF.");
                    System.out.println("Insira outro CPF: ");
                } else {
                    System.out.println("CPF inserido!");
                    break;
                }
            } else if (!isCpf(cpf)) {
                System.out.println("CPF deve conter apenas números e exatamente 11 caracteres.");
                System.out.println("Insira o cpf novamente: ");
            }
        }

        System.out.println("Digite o telefone do novo usuário (apenas números): ");
        while (true) {
            telefone = scanner.nextLine();

            if (telefone.isBlank()) {
                System.out.println("Preenchimento Obrigatório. Insira o telefone:");
            } else if (telefone.equalsIgnoreCase("/")) {
                System.out.println("Cadastro de Novo Usuário Não Realizado!!");
                return;
            } else if (isTelefone(telefone)) {
                if (usuarioDAO.findByTelefone(telefone).isPresent()) {
                    System.out.println("Já existe usuário cadastrado com este número de telefone.");
                    System.out.println("Insira outro número de telefone:");
                } else {
                    System.out.println("Telefone inserido!");
                    break;
                }
            } else if (!isTelefone(telefone)) {
                System.out.println("Telefone deve conter apenas números e exatamente 11 caracteres.");
                System.out.println("Insira o telefone novamente: ");
            }
        }


        Usuario novoUsuario = new Usuario();
        novoUsuario.setNomeUsuario(nome);
        novoUsuario.setCpfUsuario(cpf);
        novoUsuario.setTelefoneUsuario(telefone);
        usuarioDAO.save(novoUsuario);
        System.out.println("Novo Usuário Cadastrado com Sucesso.");
    }

    public void editarUsuario() {
        String nome, cpf, telefone, input;
        long id;
        System.out.println("================================== EDITAR USUÁRIO =====================================");
        System.out.println("Digite o ID do Usuário: ");
        while (true){
            input = scanner.nextLine();
            try{
                id = Long.parseLong(input);
                if (usuarioDAO.findById(id).isPresent()){
                    nome = usuarioDAO.findById(id).get().getNomeUsuario();
                    cpf = usuarioDAO.findById(id).get().getCpfUsuario();
                    telefone = usuarioDAO.findById(id).get().getTelefoneUsuario();

                    System.out.println("Usuário ID: " + id);
                    System.out.println("Nome: " + nome);
                    System.out.println("CPF: " + cpf);
                    System.out.println("Telefone: " + telefone);
                    System.out.println("Confirmar o usuário: [s/n]");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("s")){
                        break;
                    } else if (input.equalsIgnoreCase("n")) {
                        System.out.println("Digite outro ID: ");;
                    }
                } else if (usuarioDAO.findById(id).isEmpty()) {
                    System.out.println("ID Inválido");
                }
            } catch (Exception e) {
                if (input.equals("/")){
                    return;
                }else {
                    System.out.println("ID inválido");
                }

            }
        }

        Usuario usuario = new Usuario(id, nome, cpf, telefone);

        while (true){
            System.out.println("Escolha uma opção:");
            System.out.println("[1] - Editar NOME");
            System.out.println("[2] - Editar CPF");
            System.out.println("[3] - Editar TELEFONE");
            System.out.println("[/] - Voltar");
            input = scanner.nextLine();
            if (input.equals("1")){
                System.out.println("Digite o novo NOME: ");
                while (true){
                    input = scanner.nextLine();
                    if (input.isBlank()) {
                        System.out.println("Preenchimento Obrigatório. Insira o nome:");
                    } else if (input.equalsIgnoreCase("/")) {
                        System.out.println("Alteração de Nome de Usuário Não Realizada!!");
                        break;
                    } else {
                        usuario.setNomeUsuario(input);
                        usuarioDAO.update(usuario);
                        System.out.println("Nome Alterado!");
                        break;
                    }
                }
            } else if (input.equals("2")) {
                System.out.println("Digite o novo CPF (apenas números): ");
                while (true) {
                    input = scanner.nextLine();

                    if (input.isBlank()) {
                        System.out.println("Preenchimento Obrigatório. Insira o cpf:");
                    } else if (input.equalsIgnoreCase("/")) {
                        System.out.println("Alteração de CPF de Usuário Não Realizada!!");
                        break;
                    } else if (isCpf(input)) {
                        if (usuarioDAO.findByCpf(input).isPresent()) {
                            System.out.println("Já existe usuário cadastrado com este CPF.");
                            System.out.println("Insira outro CPF: ");
                        } else {
                            usuario.setCpfUsuario(input);
                            usuarioDAO.update(usuario);
                            System.out.println("CPF Alterado!");
                            break;
                        }
                    } else if (!isCpf(input)) {
                        System.out.println("CPF deve conter apenas números e exatamente 11 caracteres.");
                        System.out.println("Insira o cpf novamente: ");
                    }
                }
            } else if (input.equals("3")) {
                System.out.println("Digite o novo número de telefone (apenas números): ");
                while (true) {
                    input = scanner.nextLine();

                    if (input.isBlank()) {
                        System.out.println("Preenchimento Obrigatório. Insira o telefone:");
                    } else if (input.equalsIgnoreCase("/")) {
                        System.out.println("Alteração de Telefone de Usuário Não Realizada!!");
                        break;
                    } else if (isTelefone(input)) {
                        if (usuarioDAO.findByTelefone(input).isPresent()) {
                            System.out.println("Já existe usuário cadastrado com este número de telefone.");
                            System.out.println("Insira outro número de telefone:");
                        } else {
                            usuario.setTelefoneUsuario(input);
                            usuarioDAO.update(usuario);
                            System.out.println("Telefone inserido!");
                            break;
                        }
                    } else if (!isTelefone(input)) {
                        System.out.println("Telefone deve conter apenas números e exatamente 11 caracteres.");
                        System.out.println("Insira o telefone novamente: ");
                    }
                }

            } else if (input.equals("/")) {
                break;
            }else {
                System.out.println("Comando Inválido!");
            }
        }
    }

    public void deletarUsuario(){
        String nome, cpf, telefone, input;
        long id;
        System.out.println("================================== REMOVER USUÁRIO =====================================");
        System.out.println("Digite o ID do Usuário: ");
        while (true){
            input = scanner.nextLine();
            try{
                id = Long.parseLong(input);
                if (usuarioDAO.findById(id).isPresent()){
                    nome = usuarioDAO.findById(id).get().getNomeUsuario();
                    cpf = usuarioDAO.findById(id).get().getCpfUsuario();
                    telefone = usuarioDAO.findById(id).get().getTelefoneUsuario();

                    System.out.println("Usuário ID: " + id);
                    System.out.println("Nome: " + nome);
                    System.out.println("CPF: " + cpf);
                    System.out.println("Telefone: " + telefone);
                    System.out.println("Confirmar o usuário: [s/n]");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("s")){
                        break;
                    } else if (input.equalsIgnoreCase("n")) {
                        System.out.println("Digite outro ID: ");;
                    }
                } else if (usuarioDAO.findById(id).isEmpty()) {
                    System.out.println("ID Inválido");
                }
            } catch (Exception e) {
                if (input.equals("/")){
                    return;
                }else {
                    System.out.println("ID inválido");
                }

            }
        }

        //checar se usuario tem emprestimo aberto

        for (int i = 0; i < emprestimoDAO.findByIdUsuario(id).size();i++){
            if (emprestimoDAO.findByIdUsuario(id).get(i).getStatus().equalsIgnoreCase("Aberto")){
                System.out.println("Usuário possui empréstimo em aberto.");
                System.out.println("Não pode ser removido!!");
                return;
            }
        }

        usuarioDAO.delete(id);
        System.out.println("Usuário Removido!");

    }

    public void findUsuario(){
        String nome, cpf, telefone, input;
        long id;
        System.out.println("================================== USUÁRIOS =====================================");

        while (true){
            System.out.println("Escolha uma opção:");
            System.out.println("[1] - Por ID do Usuário");
            System.out.println("[2] - Por NOME do Usuário");
            System.out.println("[3] - Por CPF do Usuário");
            System.out.println("[4] - Por TELEFONE do Usuário");
            System.out.println("[/] - Voltar");
            input = scanner.nextLine();
            if (input.equals("1")){

                while (true){
                    System.out.println("Digite o ID do Usuário: ");
                    input = scanner.nextLine();
                    try{
                        id = Long.parseLong(input);
                        if (usuarioDAO.findById(id).isPresent()){
                            nome = usuarioDAO.findById(id).get().getNomeUsuario();
                            cpf = usuarioDAO.findById(id).get().getCpfUsuario();
                            telefone = usuarioDAO.findById(id).get().getTelefoneUsuario();
                            System.out.println("---------------------------------------------------------------------------------");
                            System.out.println("Usuário ID: " + id);
                            System.out.println("Nome: " + nome);
                            System.out.println("CPF: " + cpf);
                            System.out.println("Telefone: " + telefone);
                            System.out.println("---------------------------------------------------------------------------------");
                            break;
                        } else if (usuarioDAO.findById(id).isEmpty()) {
                            System.out.println("ID Inválido");
                        }
                    } catch (Exception e) {
                        if (input.equals("/")){
                            break;
                        }else {
                            System.out.println("ID inválido");
                        }

                    }
                }

            } else if (input.equals("2")) {
                while (true){

                    System.out.println("Digite o NOME do Usuário: ");
                    input = scanner.nextLine();
                    System.out.println("---------------------------------------------------------------------------------");
                    int cont = 0;
                    for (int i = 0; i < usuarioDAO.findAll().size();i++){
                        if (!input.isBlank() && usuarioDAO.findAll().get(i).getNomeUsuario().toLowerCase().contains(input.toLowerCase())){
                            System.out.println("Usuário ID: " + usuarioDAO.findAll().get(i).getIdUsuario());
                            System.out.println("Nome: " + usuarioDAO.findAll().get(i).getNomeUsuario());
                            System.out.println("CPF: " + usuarioDAO.findAll().get(i).getCpfUsuario());
                            System.out.println("Telefone: " + usuarioDAO.findAll().get(i).getTelefoneUsuario());
                            System.out.println("---------------------------------------------------------------------------------");
                            cont+=1;
                        }
                    }

                    if (cont < 1) {
                        System.out.println("Não foi encontrado usuário com este nome.");
                    }else {
                        break;
                    }
                }

            }else if (input.equals("3")) {

                    System.out.println("Digite o CPF do Usuário (apenas números): ");
                    input = scanner.nextLine();

                    if (isCpf(input)){
                        if (usuarioDAO.findByCpf(input).isPresent()){
                            System.out.println("---------------------------------------------------------------------------------");
                            System.out.println("Usuário ID: " + usuarioDAO.findByCpf(input).get().getIdUsuario());
                            System.out.println("Nome: " + usuarioDAO.findByCpf(input).get().getNomeUsuario());
                            System.out.println("CPF: " + usuarioDAO.findByCpf(input).get().getCpfUsuario());
                            System.out.println("Telefone: " + usuarioDAO.findByCpf(input).get().getTelefoneUsuario());
                            System.out.println("---------------------------------------------------------------------------------");
                        } else {
                            System.out.println("CPF não encontrado!");
                        }
                    }else {
                        System.out.println("CPF inválido!");
                    }

            }else if (input.equals("4")) {

                    System.out.println("Digite o TELEFONE do Usuário (apenas números): ");
                    input = scanner.nextLine();

                    if (isTelefone(input)){
                        if (usuarioDAO.findByTelefone(input).isPresent()){
                            System.out.println("---------------------------------------------------------------------------------");
                            System.out.println("Usuário ID: " + usuarioDAO.findByTelefone(input).get().getIdUsuario());
                            System.out.println("Nome: " + usuarioDAO.findByTelefone(input).get().getNomeUsuario());
                            System.out.println("CPF: " + usuarioDAO.findByTelefone(input).get().getCpfUsuario());
                            System.out.println("Telefone: " + usuarioDAO.findByTelefone(input).get().getTelefoneUsuario());
                            System.out.println("---------------------------------------------------------------------------------");

                        }else {
                            System.out.println("TELEFONE não encontrado!");
                        }
                    }else {
                        System.out.println("TELEFONE inválido!");
                    }


            }else if (input.equals("/")) {
                break;

            }else{
                System.out.println("Comando Inválido!");
            }


        }
    }

    public boolean isCpf(String value) {

        Pattern pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        boolean matchFound = matcher.find();
        if (matchFound && value.length() == 11) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTelefone(String value) {

        Pattern pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        boolean matchFound = matcher.find();
        if (matchFound && value.length() == 11) {
            return true;
        } else {
            return false;
        }
    }
}
