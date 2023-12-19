package br.com.biblioteca.dao;

import br.com.biblioteca.model.Livro;


import java.util.List;
import java.util.Optional;

public interface ILivroDAO {

    Livro save(Livro livro);
    Livro update(Livro livro);
    void delete(Long idLivro);
    List<Livro> findAll();
    Optional<Livro> findById(Long idLivro);
    Optional<Livro> findByTitulo(String titulo);
    Optional<Livro> findByAutor(String autor);
    Optional<Livro> findByCategoria(String categoria);
    Optional<Livro> findByStatus(String status);

}
