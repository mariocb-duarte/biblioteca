package br.com.biblioteca.dao;

import br.com.biblioteca.model.Emprestimo;


import java.util.List;
import java.util.Optional;

public interface IEmprestimoDAO {
    Emprestimo save(Emprestimo emprestimo);
    Emprestimo update(Emprestimo emprestimo);
    void delete(Long idEmprestimo);
    List<Emprestimo> findAll();
    Optional<Emprestimo> findByIdEmprestimo(Long idEmprestimo);
    List<Emprestimo> findByIdUsuario(Long idUsuario);
    List<Emprestimo> findByIdLivro(Long idLivro);
    List<Emprestimo> findByStatus(String status);
    List<Emprestimo> findBySituacaoPrazo(String situacaoPrazo);
}
