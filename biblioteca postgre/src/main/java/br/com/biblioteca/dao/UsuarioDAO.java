package br.com.biblioteca.dao;

import br.com.biblioteca.infra.ConnectionFactory;
import br.com.biblioteca.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO implements IUsuarioDAO{

    @Override
    public Usuario save(Usuario usuario) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO Usuarios (nome, cpf, telefone) VALUES (?,?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, usuario.getNomeUsuario());
            preparedStatement.setString(2, usuario.getCpfUsuario());
            preparedStatement.setString(3, usuario.getTelefoneUsuario());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            Long generatedId = resultSet.getLong("id");
            usuario.setIdUsuario(generatedId);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "UPDATE Usuarios SET nome = ?, cpf = ?, telefone = ? WHERE id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getNomeUsuario());
            preparedStatement.setString(2, usuario.getCpfUsuario());
            preparedStatement.setString(3, usuario.getTelefoneUsuario());
            preparedStatement.setLong(4, usuario.getIdUsuario());

            preparedStatement.executeUpdate();



        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return usuario;
    }

    public void delete(Long idUsuario) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM Usuarios WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idUsuario);


            preparedStatement.executeUpdate();



        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT id, nome, cpf, telefone FROM Usuarios";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id  = rs.getLong("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");

                Usuario usuario = new Usuario(id, nome, cpf, telefone);
                usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return usuarios;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        String sql = "SELECT id, nome, cpf, telefone FROM Usuarios WHERE id = ?";
        Usuario usuario = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long pKey  = rs.getLong("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");

                usuario = new Usuario(pKey, nome, cpf, telefone);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return Optional.ofNullable(usuario);
    }

    public Optional<Usuario> findByNome(String nome) {
        String sql = "SELECT id, nome, cpf, telefone FROM Usuarios WHERE nome = ?";
        Usuario usuario = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id  = rs.getLong("id");
                String nomeUser = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");

                usuario = new Usuario(id, nomeUser, cpf, telefone);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return Optional.ofNullable(usuario);
    }

    public Optional<Usuario> findByCpf(String cpf) {
        String sql = "SELECT id, nome, cpf, telefone FROM Usuarios WHERE cpf = ?";
        Usuario usuario = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cpf);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id  = rs.getLong("id");
                String nome = rs.getString("nome");
                String cpfUser = rs.getString("cpf");
                String telefone = rs.getString("telefone");

                usuario = new Usuario(id, nome, cpfUser, telefone);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return Optional.ofNullable(usuario);
    }


    public Optional<Usuario> findByTelefone(String telefone) {
        String sql = "SELECT id, nome, cpf, telefone FROM Usuarios WHERE telefone = ?";
        Usuario usuario = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, telefone);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id  = rs.getLong("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefoneUser = rs.getString("telefone");

                usuario = new Usuario(id, nome, cpf, telefoneUser);

            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return Optional.ofNullable(usuario);
    }
}
