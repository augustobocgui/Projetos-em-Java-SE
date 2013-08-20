/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Turmas;
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
public class TurmasDAO {

    Connection conexao;
    private String sql;

    public TurmasDAO() throws BancoException {
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

    public int gerarCodigoTurma() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM TURMA";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet gui = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (gui.next()) {
                codigo = gui.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            gui.close();
            stm.close();
            // conexao.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return codigo + 1;
    }

    public void adicionarTurmas(Turmas turma) throws BancoException {
        sql = "INSERT INTO TURMA (DIA, HI, HF,"
                + "  FUNCIONARIO, CURSO, CODIGO) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, turma.getDia());
            gui.setString(2, turma.getHi());
            gui.setString(3, turma.getHf());
            gui.setInt(4, turma.getFuncionario());
            gui.setString(5, turma.getCurso());
            gui.setInt(6, turma.getCodigo());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
            // conexao.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDados(Turmas turma) throws BancoException {
        sql = "UPDATE TURMA SET DIA = ?, HI = ?, HF = ?,"
                + "  FUNCIONARIO = ?, CURSO = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setString(1, turma.getDia());
            guiAlterar.setString(2, turma.getHi());
            guiAlterar.setString(3, turma.getHf());
            guiAlterar.setInt(4, turma.getFuncionario());
            guiAlterar.setString(5, turma.getCurso());
            guiAlterar.setInt(6, turma.getCodigo());


            guiAlterar.execute();
            guiAlterar.close();
            //conexao.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Turmas> pesquisaTurma(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from turma";
        } else {
            sql = "Select * from turma where dia like " + aspas + "%" + pes + "%" + aspas
                    + "and hi" + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Turmas> lista = new ArrayList<Turmas>();
            while (banco.next()) // só entra se existir 
            {
                Turmas turmass = new Turmas();
                turmass.setCodigo(banco.getInt("codigo"));
                turmass.setDia(banco.getString("dia"));
                turmass.setHi(banco.getString("hi"));
                turmass.setHf(banco.getString("hf"));
                turmass.setFuncionario(banco.getInt("funcionario"));
                turmass.setCurso(banco.getString("curso"));

                lista.add(turmass);
            }
            banco.close();
            gui.close();
            // conexao.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Turmas carregarTurmaPeloCodigo(StringBuffer nome) throws BancoException {
        Turmas turmass = new Turmas();
        turmass.setDia("nulo");
        sql = "SELECT * FROM TURMA WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                turmass.setCodigo(banco.getInt("codigo"));
                turmass.setDia(banco.getString("dia"));
                turmass.setHi(banco.getString("hi"));
                turmass.setHf(banco.getString("hf"));
                turmass.setFuncionario(banco.getInt("funcionario"));
                turmass.setCurso(banco.getString("curso"));
            }
            gui.close();
            banco.close();
            //
            return turmass;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }

    }
}
