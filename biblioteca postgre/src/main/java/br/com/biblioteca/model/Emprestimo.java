package br.com.biblioteca.model;

import java.time.LocalDate;

public class Emprestimo {
    private Long idEmprestimo;
    private Long idUsuario;
    private Long idLivro;
    private LocalDate diaEmprestimo;
    private int tempoEmprestimo;
    private LocalDate diaDevolucao;
    private String status; //aberto ou finalizado
    private String situacaoPrazo; //no prazo ou fora do prazo
    private double valor;

    public Emprestimo(Long idUsuario, Long idLivro, LocalDate diaEmprestimo, int tempoEmprestimo) {
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.diaEmprestimo = diaEmprestimo;
        this.tempoEmprestimo = tempoEmprestimo;
        this.diaDevolucao = diaEmprestimo.plusDays(this.tempoEmprestimo);
        this.status = "aberto";
        this.situacaoPrazo = "dentro";
        this.valor = this.tempoEmprestimo*3;
    }

    public Emprestimo(Long idUsuario, Long idLivro, LocalDate diaEmprestimo, int tempoEmprestimo, String status, String situacaoPrazo) {
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.diaEmprestimo = diaEmprestimo;
        this.tempoEmprestimo = tempoEmprestimo;
        this.status = status;
        this.situacaoPrazo = situacaoPrazo;

    }

    public Emprestimo(Long idEmprestimo, Long idUsuario, Long idLivro, LocalDate diaEmprestimo, int tempoEmprestimo,LocalDate diaDevolucao, String status, String situacaoPrazo, double valor){
        this.idEmprestimo = idEmprestimo;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.diaEmprestimo = diaEmprestimo;
        this.tempoEmprestimo = tempoEmprestimo;
        this.diaDevolucao = diaDevolucao;
        this.status = status;
        this.situacaoPrazo = situacaoPrazo;
        this.valor = valor;

    }

    public Emprestimo(){
    }

    public Long getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Long idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public LocalDate getDiaEmprestimo() {
        return diaEmprestimo;
    }

    public void setDiaEmprestimo(LocalDate diaEmprestimo) {
        this.diaEmprestimo = diaEmprestimo;
    }

    public int getTempoEmprestimo() {
        return tempoEmprestimo;
    }

    public void setTempoEmprestimo(int tempoEmprestimo) {
        this.tempoEmprestimo = tempoEmprestimo;
    }

    public LocalDate getDiaDevolucao() {
        return diaDevolucao;
    }

    public void setDiaDevolucao() {
        this.diaDevolucao = this.diaEmprestimo.plusDays(this.tempoEmprestimo);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSituacaoPrazo() {
        return situacaoPrazo;
    }

    public void setSituacaoPrazo(String situacao) {
      this.situacaoPrazo = situacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor() {
        this.valor = this.tempoEmprestimo*3;
    }
}
