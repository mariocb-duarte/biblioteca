package br.com.biblioteca.dao;

import br.com.biblioteca.infra.ConnectionFactory;
import br.com.biblioteca.model.Livro;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivroDAO implements ILivroDAO{
    @Override
    public Livro save(Livro livro) {
        try (Connection connection = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO Livros (titulo, autor, paginas, categoria, status) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, livro.getTituloLivro());
            preparedStatement.setString(2, livro.getAutorLivro());
            preparedStatement.setInt(3, livro.getPaginasLivro());
            preparedStatement.setString(4, livro.getCategoriaLivro());
            preparedStatement.setString(5, livro.getStatusLivro());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            Long generatedId = resultSet.getLong("id");
            livro.setIdLivro(generatedId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livro;
    }

    @Override
    public Livro update(Livro livro) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "UPDATE Livros SET titulo = ?, autor = ?, paginas = ?, categoria = ?, status = ? WHERE id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, livro.getTituloLivro());
            preparedStatement.setString(2, livro.getAutorLivro());
            preparedStatement.setInt(3, livro.getPaginasLivro());
            preparedStatement.setString(4, livro.getCategoriaLivro());
            preparedStatement.setString(5, livro.getStatusLivro());
            preparedStatement.setLong(6, livro.getIdLivro());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return livro;
    }

    @Override
    public void delete(Long idLivro) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM Livros WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idLivro);


            preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public List<Livro> findAll() {
        String sql = "SELECT id, titulo, autor, paginas, categoria, status FROM Livros";
        List<Livro> livros = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id  = resultSet.getLong("id");
                String titulo = resultSet.getString("titulo");
                String autor = resultSet.getString("autor");
                int paginas = resultSet.getInt("paginas");
                String categoria = resultSet.getString("categoria");
                String status = resultSet.getString("status");

                Livro livro = new Livro(id, titulo, autor, paginas, categoria, status);
                livros.add(livro);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return livros;
    }

    @Override
    public Optional<Livro> findById(Long id) {
        String sql = "SELECT id, titulo, autor, paginas, categoria, status FROM Livros WHERE id = ?";

        Livro livro = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long pKey  = rs.getLong("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int paginas = rs.getInt("paginas");
                String categoria = rs.getString("categoria");
                String status = rs.getString("status");

                livro = new Livro(pKey, titulo, autor, paginas, categoria, status);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return Optional.ofNullable(livro);
    }

    @Override
    public Optional<Livro> findByTitulo(String titulo) {
        String sql = "SELECT id, titulo, autor, paginas, categoria, status FROM Livros WHERE titulo = ?";
        Livro livro = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titulo);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id  = rs.getLong("id");
                String tituloLivro = rs.getString("titulo");
                String autor = rs.getString("autor");
                int paginas = rs.getInt("paginas");
                String categoria = rs.getString("categoria");
                String status = rs.getString("status");

                livro = new Livro(id, tituloLivro, autor, paginas, categoria, status);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return Optional.ofNullable(livro);
    }

    @Override
    public Optional<Livro> findByAutor(String autor) {
        String sql = "SELECT id, titulo, autor, paginas, categoria, status FROM Livros WHERE autor = ?";
        Livro livro = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, autor);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id  = rs.getLong("id");
                String titulo = rs.getString("titulo");
                String autorLivro = rs.getString("autor");
                int paginas = rs.getInt("paginas");
                String categoria = rs.getString("categoria");
                String status = rs.getString("status");

                livro = new Livro(id, titulo, autorLivro, paginas, categoria, status);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return Optional.ofNullable(livro);
    }

    @Override
    public Optional<Livro> findByCategoria(String categoria) {
        String sql = "SELECT id, titulo, autor, paginas, categoria, status FROM Livros WHERE categoria = ?";
        Livro livro = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, categoria);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id  = rs.getLong("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int paginas = rs.getInt("paginas");
                String categoriaLivro = rs.getString("categoria");
                String status = rs.getString("status");

                livro = new Livro(id, titulo, autor, paginas, categoriaLivro, status);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return Optional.ofNullable(livro);
    }

    @Override
    public Optional<Livro> findByStatus(String status) {
        String sql = "SELECT id, titulo, autor, paginas, categoria, status FROM Livros WHERE categoria = ?";
        Livro livro = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id  = rs.getLong("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                int paginas = rs.getInt("paginas");
                String categoria = rs.getString("categoria");
                String statusLivro = rs.getString("status");

                livro = new Livro(id, titulo, autor, paginas, categoria, statusLivro);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return Optional.ofNullable(livro);
    }
}
