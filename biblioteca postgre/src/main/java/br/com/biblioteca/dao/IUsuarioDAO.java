package br.com.biblioteca.dao;

import br.com.biblioteca.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioDAO {

    Usuario save(Usuario usario);
    Usuario update(Usuario usuario);
    void delete(Long idUsuario);
    List<Usuario> findAll();
    Optional<Usuario> findById(Long idUsuario);
    Optional<Usuario> findByNome(String nome);
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByTelefone(String telefone);
}
