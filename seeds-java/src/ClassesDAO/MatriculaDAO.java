/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Matricula;
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
public class MatriculaDAO {

    Connection conexao;
    private String sql;

    public MatriculaDAO() throws BancoException {
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

    public int gerarCodigoMatricula() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM MATRICULA";

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

    public void adicionarMatriculas(Matricula matricula) throws BancoException {
        sql = "INSERT INTO MATRICULA (ALUNO, TURMA, LOCALIDADE, CODIGO) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, matricula.getCodigoAluno());
            gui.setInt(2, matricula.getCodigoTurma());
            gui.setInt(3, matricula.getLocalidade());
            gui.setInt(4, matricula.getCodigo());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDados(Matricula matricula) throws BancoException {
        sql = "UPDATE MATRICULA SET ALUNO = ?, TURMA = ?, LOCALIDADE = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setInt(1, matricula.getCodigoAluno());
            guiAlterar.setInt(2, matricula.getCodigoTurma());
            guiAlterar.setInt(3, matricula.getLocalidade());
            guiAlterar.setInt(4, matricula.getCodigo());


            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Matricula> pesquisaMatricula(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from matricula";
        } else {
            sql = "Select * from matricula where aluno like " + aspas + "%" + pes + "%" + aspas
                    + "and codigo" + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }


        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Matricula> lista = new ArrayList<Matricula>();
            while (banco.next()) // só entra se existir 
            {
                Matricula matricula = new Matricula();
                matricula.setCodigo(banco.getInt("codigo"));
                matricula.setCodigoAluno(banco.getInt("aluno"));
                matricula.setLocalidade(banco.getInt("localidade"));
                matricula.setCodigoTurma(banco.getInt("turma"));

                lista.add(matricula);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Matricula> pesquisaQuantosMatriculados(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from matricula";
        } else {
            sql = "Select * from matricula where turma like " + aspas + "%" + pes + "%" + aspas
                    + "and codigo" + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }


        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Matricula> lista = new ArrayList<Matricula>();
            while (banco.next()) // só entra se existir 
            {
                Matricula matricula = new Matricula();
                matricula.setCodigo(banco.getInt("codigo"));
                matricula.setCodigoAluno(banco.getInt("aluno"));
                matricula.setLocalidade(banco.getInt("localidade"));
                matricula.setCodigoTurma(banco.getInt("turma"));

                lista.add(matricula);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Matricula carregarMatriculaPeloCodigo(StringBuffer nome) throws BancoException {
        Matricula matricula = new Matricula();
        //--------Matricula.setCodigoAluno("nulo");
        sql = "SELECT * FROM MATRICULA WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                matricula.setCodigo(banco.getInt("codigo"));
                matricula.setCodigoAluno(banco.getInt("aluno"));
                matricula.setLocalidade(banco.getInt("localidade"));
                matricula.setCodigoTurma(banco.getInt("turma"));
            }
            gui.close();
            banco.close();
            return matricula;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
