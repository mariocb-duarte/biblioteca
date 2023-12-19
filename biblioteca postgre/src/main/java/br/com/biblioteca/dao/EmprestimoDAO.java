package br.com.biblioteca.dao;

import br.com.biblioteca.infra.ConnectionFactory;
import br.com.biblioteca.model.Emprestimo;
import br.com.biblioteca.model.Livro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

public class EmprestimoDAO implements IEmprestimoDAO {

    @Override
    public Emprestimo save(Emprestimo emprestimo) {
        try (Connection connection = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO Emprestimos (idUsuario, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacao, valor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, emprestimo.getIdUsuario());
            preparedStatement.setLong(2, emprestimo.getIdLivro());
            preparedStatement.setDate(3, java.sql.Date.valueOf(emprestimo.getDiaEmprestimo()));
            preparedStatement.setInt(4, emprestimo.getTempoEmprestimo());
            preparedStatement.setDate(5, java.sql.Date.valueOf(emprestimo.getDiaDevolucao()));
            preparedStatement.setString(6, emprestimo.getStatus());
            preparedStatement.setString(7, emprestimo.getSituacaoPrazo());
            preparedStatement.setDouble(8, emprestimo.getValor());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            Long generatedId = resultSet.getLong("id");
            emprestimo.setIdLivro(generatedId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return emprestimo;
    }

    @Override
    public Emprestimo update(Emprestimo emprestimo) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "UPDATE Emprestimos SET idUsuario = ?, idLivro = ?, diaEmprestimo = ?, tempoEmprestimo = ?, diaDevolucao = ?, status = ?, situacao = ?, valor = ?  WHERE id = ?;";


            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, emprestimo.getIdUsuario());
            preparedStatement.setLong(2, emprestimo.getIdLivro());
            preparedStatement.setDate(3, java.sql.Date.valueOf(emprestimo.getDiaEmprestimo()));
            preparedStatement.setInt(4, emprestimo.getTempoEmprestimo());
            preparedStatement.setDate(5, java.sql.Date.valueOf(emprestimo.getDiaDevolucao()));
            preparedStatement.setString(6, emprestimo.getStatus());
            preparedStatement.setString(7, emprestimo.getSituacaoPrazo());
            preparedStatement.setDouble(8, emprestimo.getValor());
            preparedStatement.setDouble(9, emprestimo.getIdEmprestimo());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return emprestimo;
    }

    @Override
    public void delete(Long idEmprestimo) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM Emprestimos WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idEmprestimo);


            preparedStatement.executeUpdate();


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public List<Emprestimo> findAll() {
        String sql = "SELECT id, idUsuario, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacao, valor FROM Emprestimos";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id  = resultSet.getLong("id");
                Long idUsuario = resultSet.getLong("idUsuario");
                Long idLivro = resultSet.getLong("idLivro");
                LocalDate diaEmprestimo = resultSet.getDate("diaEmprestimo").toLocalDate();
                int tempoEmprestimo = resultSet.getInt("tempoEmprestimo");
                LocalDate diaDevolucao = resultSet.getDate("diaDevolucao").toLocalDate();
                String status = resultSet.getString("status");
                String situacaoPrazo = resultSet.getString("situacao");
                double valor = resultSet.getDouble("valor");


                Emprestimo emprestimo = new Emprestimo(id, idUsuario, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacaoPrazo, valor );
                emprestimos.add(emprestimo);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        return emprestimos;
    }

    @Override
    public Optional<Emprestimo> findByIdEmprestimo(Long idEmprestimo) {
        String sql = "SELECT id, idUsuario, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacao, valor FROM Emprestimos WHERE id = ?";
        Emprestimo emprestimo = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idEmprestimo);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id  = resultSet.getLong("id");
                Long idUsuario = resultSet.getLong("idUsuario");
                Long idLivro = resultSet.getLong("idLivro");
                LocalDate diaEmprestimo = resultSet.getDate("diaEmprestimo").toLocalDate();
                int tempoEmprestimo = resultSet.getInt("tempoEmprestimo");
                LocalDate diaDevolucao = resultSet.getDate("diaDevolucao").toLocalDate();
                String status = resultSet.getString("status");
                String situacaoPrazo = resultSet.getString("situacao");
                double valor = resultSet.getDouble("valor");


                emprestimo = new Emprestimo(id, idUsuario, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacaoPrazo, valor );
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(emprestimo);
    }

    @Override
    public List<Emprestimo> findByIdUsuario(Long idUsuario) {
        String sql = "SELECT id, idUsuario, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacao, valor FROM Emprestimos WHERE idUsuario = ?";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idUsuario);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id  = resultSet.getLong("id");
                Long idUser = resultSet.getLong("idUsuario");
                Long idLivro = resultSet.getLong("idLivro");
                LocalDate diaEmprestimo = resultSet.getDate("diaEmprestimo").toLocalDate();
                int tempoEmprestimo = resultSet.getInt("tempoEmprestimo");
                LocalDate diaDevolucao = resultSet.getDate("diaDevolucao").toLocalDate();
                String status = resultSet.getString("status");
                String situacaoPrazo = resultSet.getString("situacao");
                double valor = resultSet.getDouble("valor");


                Emprestimo emprestimo = new Emprestimo(id, idUser, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacaoPrazo, valor );
                emprestimos.add(emprestimo);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return emprestimos;
    }

    @Override
    public List<Emprestimo> findByIdLivro(Long idLivro) {
        String sql = "SELECT id, idUsuario, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacao, valor FROM Emprestimos WHERE idLivro = ?";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idLivro);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id  = resultSet.getLong("id");
                Long idUser = resultSet.getLong("idUsuario");
                Long idBook = resultSet.getLong("idLivro");
                LocalDate diaEmprestimo = resultSet.getDate("diaEmprestimo").toLocalDate();
                int tempoEmprestimo = resultSet.getInt("tempoEmprestimo");
                LocalDate diaDevolucao = resultSet.getDate("diaDevolucao").toLocalDate();
                String status = resultSet.getString("status");
                String situacaoPrazo = resultSet.getString("situacao");
                double valor = resultSet.getDouble("valor");


                Emprestimo emprestimo = new Emprestimo(id, idUser, idBook, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacaoPrazo, valor );
                emprestimos.add(emprestimo);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return emprestimos;
    }

    @Override
    public List<Emprestimo> findByStatus(String status) {
        String sql = "SELECT id, idUsuario, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacao, valor FROM Emprestimos WHERE status = ?";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id  = resultSet.getLong("id");
                Long idUser = resultSet.getLong("idUsuario");
                Long idBook = resultSet.getLong("idLivro");
                LocalDate diaEmprestimo = resultSet.getDate("diaEmprestimo").toLocalDate();
                int tempoEmprestimo = resultSet.getInt("tempoEmprestimo");
                LocalDate diaDevolucao = resultSet.getDate("diaDevolucao").toLocalDate();
                String statusEmprestimo = resultSet.getString("status");
                String situacaoPrazo = resultSet.getString("situacao");
                double valor = resultSet.getDouble("valor");


                Emprestimo emprestimo = new Emprestimo(id, idUser, idBook, diaEmprestimo, tempoEmprestimo, diaDevolucao, statusEmprestimo, situacaoPrazo, valor );
                emprestimos.add(emprestimo);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return emprestimos;
    }

    @Override
    public List<Emprestimo> findBySituacaoPrazo(String situacao) {
        String sql = "SELECT id, idUsuario, idLivro, diaEmprestimo, tempoEmprestimo, diaDevolucao, status, situacao, valor FROM Emprestimos WHERE situacao = ?";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, situacao);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id  = resultSet.getLong("id");
                Long idUser = resultSet.getLong("idUsuario");
                Long idBook = resultSet.getLong("idLivro");
                LocalDate diaEmprestimo = resultSet.getDate("diaEmprestimo").toLocalDate();
                int tempoEmprestimo = resultSet.getInt("tempoEmprestimo");
                LocalDate diaDevolucao = resultSet.getDate("diaDevolucao").toLocalDate();
                String statusEmprestimo = resultSet.getString("status");
                String situacaoPrazo = resultSet.getString("situacao");
                double valor = resultSet.getDouble("valor");


                Emprestimo emprestimo = new Emprestimo(id, idUser, idBook, diaEmprestimo, tempoEmprestimo, diaDevolucao, statusEmprestimo, situacaoPrazo, valor );
                emprestimos.add(emprestimo);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return emprestimos;
    }
}
