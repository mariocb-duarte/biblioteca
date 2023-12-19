package br.com.biblioteca.model;

public class Livro {
    private Long idLivro;
    private String tituloLivro;
    private String autorLivro;
    private int paginasLivro;
    private String categoriaLivro;
    private String statusLivro;


    public Livro(Long idLivro, String tituloLivro, String autorLivro, int paginasLivro, String categoriaLivro) {
        this.idLivro = idLivro;
        this.tituloLivro = tituloLivro;
        this.autorLivro = autorLivro;
        this.paginasLivro = paginasLivro;
        this.categoriaLivro = categoriaLivro;
        this.statusLivro = "Disponivel";
    }

    public Livro(Long idLivro, String tituloLivro, String autorLivro, int paginasLivro, String categoriaLivro, String status) {
        this.idLivro = idLivro;
        this.tituloLivro = tituloLivro;
        this.autorLivro = autorLivro;
        this.paginasLivro = paginasLivro;
        this.categoriaLivro = categoriaLivro;
        this.statusLivro = status;
    }
    public Livro(){

    }


    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(String autorLivro) {
        this.autorLivro = autorLivro;
    }

    public Integer getPaginasLivro() {
        return paginasLivro;
    }

    public void setPaginasLivro(int paginasLivro) {
        this.paginasLivro = paginasLivro;
    }

    public String getCategoriaLivro() {
        return categoriaLivro;
    }

    public void setCategoriaLivro(String categoriaLivro) {
        this.categoriaLivro = categoriaLivro;
    }

    public String getStatusLivro() {
        return statusLivro;
    }

    public void setStatusLivro() {
        if (this.statusLivro.equalsIgnoreCase("Disponivel")){
            this.statusLivro = "Indisponivel";
        }else{
            this.statusLivro = "Disponivel";
        }
    }

    public void setStatusLivro(String value){
        this.statusLivro = value;
    }
}
