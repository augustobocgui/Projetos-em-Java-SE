/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Professor;
import Excessoes.BancoException;
import br.com.seeds.FazerConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme
 */
public class ProfessorDAO {

    Connection conexao;
    private String sql;

    public ProfessorDAO() throws BancoException {
        conexao = (Connection) new FazerConexao().fazerConexaoBanco();
    }

    public void desconectar() {
        try {
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(TurmasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexao = null;
    }

    public int gerarCodigoProfessor() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM PROFESSOR";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet gui = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (gui.next()) {
                codigo = gui.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            gui.close();
            stm.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return codigo + 1;
    }

    public int gerarCodigoProfessorExcluido() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM PROFESSOR_EXCLUIDO";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet gui = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (gui.next()) {
                codigo = gui.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            gui.close();
            stm.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return codigo + 1;
    }

    public void adicionarProfessor(Professor professor) throws BancoException {
        sql = "INSERT INTO PROFESSOR (CODIGO, NOME, ENDERECO,"
                + "NUMERO, COMPLEMENTO,"
                + "BAIRRO, CIDADE, CEP, PABX, FAX, CPF, CNPJ, HOME_PAGE,"
                + " EMAIL) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, professor.getCodigo());
            gui.setString(2, professor.getNome());
            gui.setString(3, professor.getEndereco());
            gui.setString(4, professor.getNumero());
            gui.setString(5, professor.getComplemento());
            gui.setString(6, professor.getBairro());
            gui.setString(7, professor.getCidade());
            gui.setString(8, professor.getCep());
            gui.setString(9, professor.getTelefone1());
            gui.setString(10, professor.getTelefone2());
            gui.setString(11, professor.getCpf());
            gui.setString(12, professor.getCnpj());
            gui.setString(13, professor.getContato());
            gui.setString(14, professor.getEmail());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void adicionarProfessorExcluido(Professor professor) throws BancoException {
        sql = "INSERT INTO PROFESSOR_EXCLUIDO ( CODIGO,"
                + " NOME, ENDERECO, NUMERO, COMPLEMENTO,"
                + "BAIRRO, CIDADE, CEP, PABX, FAX, CPF, CNPJ, HOME_PAGE,"
                + "  EMAIL) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, professor.getCodigo());
            gui.setString(2, professor.getNome());
            gui.setString(3, professor.getEndereco());
            gui.setString(4, professor.getNumero());
            gui.setString(5, professor.getComplemento());
            gui.setString(6, professor.getBairro());
            gui.setString(7, professor.getCidade());
            gui.setString(8, professor.getCep());
            gui.setString(9, professor.getTelefone1());
            gui.setString(10, professor.getTelefone2());
            gui.setString(11, professor.getCpf());
            gui.setString(12, professor.getCnpj());
            gui.setString(13, professor.getContato());
            gui.setString(14, professor.getEmail());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDados(Professor professor) throws BancoException {
        sql = "UPDATE PROFESSOR SET NOME = ?,"
                + "  ENDERECO = ?, NUMERO = ?, COMPLEMENTO = ?,"
                + " BAIRRO = ?, CIDADE = ?, CEP = ?, PABX = ?, "
                + " FAX = ?, CPF = ?, CNPJ = ?, HOME_PAGE = ?,"
                + " EMAIL = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setString(1, professor.getNome());
            guiAlterar.setString(2, professor.getEndereco());
            guiAlterar.setString(3, professor.getNumero());
            guiAlterar.setString(4, professor.getComplemento());
            guiAlterar.setString(5, professor.getBairro());
            guiAlterar.setString(6, professor.getCidade());
            guiAlterar.setString(7, professor.getCep());
            guiAlterar.setString(8, professor.getTelefone1());
            guiAlterar.setString(9, professor.getTelefone2());
            guiAlterar.setString(10, professor.getCpf());
            guiAlterar.setString(11, professor.getCnpj());
            guiAlterar.setString(12, professor.getContato());
            guiAlterar.setString(13, professor.getEmail());
            guiAlterar.setInt(14, professor.getCodigo());


            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void deletarProfessor(Professor controle) throws BancoException {
        sql = "DELETE FROM PROFESSOR where CODIGO = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void deletarProfessorExcluido(Professor controle) throws BancoException {
        sql = "DELETE FROM PROFESSOR_EXCLUIDO where CODIGO = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Professor carregarProfessor(StringBuffer nome) throws BancoException {
        Professor professor = new Professor();
        professor.setNome("nulo");
        sql = "SELECT * FROM PROFESSOR WHERE NOME = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                professor.setCodigo(banco.getInt("codigo"));
                professor.setNome(banco.getString("nome"));
                professor.setEndereco(banco.getString("endereco"));
                professor.setNumero(banco.getString("numero"));
                professor.setComplemento(banco.getString("complemento"));
                professor.setBairro(banco.getString("bairro"));
                professor.setCidade(banco.getString("cidade"));
                professor.setCep(banco.getString("cep"));
                professor.setTelefone1(banco.getString("pabx"));
                professor.setTelefone2(banco.getString("fax"));
                professor.setCpf(banco.getString("cpf"));
                professor.setCnpj(banco.getString("cnpj"));
                professor.setContato(banco.getString("home_page"));
                professor.setEmail(banco.getString("email"));
            }
            banco.close();
            gui.close();
            return professor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Professor carregarProfessorPeloCodigo(StringBuffer nome) throws BancoException {
        Professor professor = new Professor();
        professor.setNome("nulo");
        sql = "SELECT * FROM PROFESSOR WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                professor.setCodigo(banco.getInt("codigo"));
                professor.setNome(banco.getString("nome"));
                professor.setEndereco(banco.getString("endereco"));
                professor.setNumero(banco.getString("numero"));
                professor.setComplemento(banco.getString("complemento"));
                professor.setBairro(banco.getString("bairro"));
                professor.setCidade(banco.getString("cidade"));
                professor.setCep(banco.getString("cep"));
                professor.setTelefone1(banco.getString("pabx"));
                professor.setTelefone2(banco.getString("fax"));
                professor.setCpf(banco.getString("cpf"));
                professor.setCnpj(banco.getString("cnpj"));
                professor.setContato(banco.getString("home_page"));
                professor.setEmail(banco.getString("email"));
            }
            gui.close();
            banco.close();
            return professor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Professor carregarProfessorExcluidoPeloCodigo(StringBuffer nome) throws BancoException {
        Professor professor = new Professor();
        professor.setNome("nulo");
        sql = "SELECT * FROM PROFESSOR_EXCLUIDO WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                professor.setCodigo(banco.getInt("codigo"));
                professor.setNome(banco.getString("nome"));
                professor.setEndereco(banco.getString("endereco"));
                professor.setNumero(banco.getString("numero"));
                professor.setComplemento(banco.getString("complemento"));
                professor.setBairro(banco.getString("bairro"));
                professor.setCidade(banco.getString("cidade"));
                professor.setCep(banco.getString("cep"));
                professor.setTelefone1(banco.getString("pabx"));
                professor.setTelefone2(banco.getString("fax"));
                professor.setCpf(banco.getString("cpf"));
                professor.setCnpj(banco.getString("cnpj"));
                professor.setContato(banco.getString("home_page"));
                professor.setEmail(banco.getString("email"));
            }
            gui.close();
            banco.close();
            return professor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Professor carregarProfessorComb(StringBuffer cod) throws BancoException {
        Professor professor = new Professor();
        professor.setNome("nulo");
        sql = "SELECT * FROM PROFESSOR WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cod.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                professor.setCodigo(banco.getInt("codigo"));
                professor.setNome(banco.getString("nome"));
            }
            banco.close();
            gui.close();
            return professor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Professor carregarProfessorN(StringBuffer fornecer) throws BancoException {
        Professor professor = new Professor();
        professor.setNome("nulo");
        sql = "SELECT * FROM PROFESSOR WHERE NOME = ? *~";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, fornecer.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                professor.setCodigo(banco.getInt("codigo"));
                professor.setNome(banco.getString("nome"));
            }
            gui.close();
            banco.close();
            return professor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Professor carregarProfessorCPF(String cpf) throws BancoException {
        Professor professor = new Professor();
        professor.setNome("nulo");
        sql = "SELECT * FROM PROFESSOR WHERE CPF = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cpf);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                professor.setCodigo(banco.getInt("codigo"));
                professor.setNome(banco.getString("nome"));
                professor.setEndereco(banco.getString("endereco"));
                professor.setNumero(banco.getString("numero"));
                professor.setComplemento(banco.getString("complemento"));
                professor.setBairro(banco.getString("bairro"));
                professor.setCidade(banco.getString("cidade"));
                professor.setCep(banco.getString("cep"));
                professor.setTelefone1(banco.getString("pabx"));
                professor.setTelefone2(banco.getString("fax"));
                professor.setCpf(banco.getString("cpf"));
                professor.setCnpj(banco.getString("cnpj"));
                professor.setContato(banco.getString("home_page"));
                professor.setEmail(banco.getString("email"));
            }
            banco.close();
            gui.close();
            return professor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Professor carregarProfessorCNPJ(String cnpj) throws BancoException {
        Professor professor = new Professor();
        professor.setNome("nulo");
        sql = "SELECT * FROM PROFESSOR WHERE CNPJ = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cnpj);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                professor.setCodigo(banco.getInt("codigo"));
                professor.setNome(banco.getString("nome"));
                professor.setCpf(banco.getString("cnpj"));
            }
            gui.close();
            banco.close();
            return professor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Professor> pesquisaProfessor(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from professor";
        } else {
            sql = "Select * from professor where nome like " + aspas + "%" + pes + "%" + aspas + "and cpf"
                    + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Professor> lista = new ArrayList<Professor>();
            while (banco.next()) // só entra se existir 
            {
                Professor professor = new Professor();
                professor.setCodigo(banco.getInt("codigo"));
                professor.setNome(banco.getString("nome"));
                professor.setCpf(banco.getString("cpf"));
                professor.setCnpj(banco.getString("cnpj"));

                lista.add(professor);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Professor> pesquisaProfessorExcluido(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from professor_excluido";
        } else {
            sql = "Select * from professor_excluido where nome like " + aspas + "%" + pes + "%" + aspas + "and cpf"
                    + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Professor> lista = new ArrayList<Professor>();
            while (banco.next()) // só entra se existir 
            {
                Professor professor = new Professor();
                professor.setCodigo(banco.getInt("codigo"));
                professor.setNome(banco.getString("nome"));
                professor.setCpf(banco.getString("cpf"));
                professor.setCnpj(banco.getString("cnpj"));

                lista.add(professor);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
