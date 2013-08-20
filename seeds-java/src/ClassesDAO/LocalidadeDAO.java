/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Localidade;
import Excessoes.BancoException;
import br.com.seeds.FazerConexao;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme
 */
public class LocalidadeDAO {

    Connection conexao;
    private String sql;

    public LocalidadeDAO() throws BancoException {
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

    public int gerarCodigoLocalidade() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM LOCALIDADE";
        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet gui = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (gui.next()) {
                codigo = gui.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            stm.close();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return codigo + 1;
    }

    public void adicionarLocalidade(Localidade localidade) throws BancoException {
        sql = "INSERT INTO LOCALIDADE (CODIGO, BAIRRO, CIDADE, ENDERECO, NUMERO,"
                + "COMPLEMENTO, CEP, SITUACAO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, localidade.getCodigo());
            gui.setString(2, localidade.getBairro());
            gui.setString(3, localidade.getCidade());
            gui.setString(4, localidade.getEndereco());
            gui.setString(5, localidade.getNumero());
            gui.setString(6, localidade.getComplemento());
            gui.setString(7, localidade.getCep());
            gui.setString(8, localidade.getSituacao());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Localidade carregarLocalidadePeloCodigo(String nome) throws BancoException {
        Localidade localidade = new Localidade();
        localidade.setBairro("nulo");
        sql = "SELECT * FROM LOCALIDADE WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                localidade.setCodigo(banco.getInt("codigo"));
                localidade.setBairro(banco.getString("bairro"));
                localidade.setCidade(banco.getString("cidade"));
                localidade.setEndereco(banco.getString("endereco"));
                localidade.setNumero(banco.getString("numero"));
                localidade.setComplemento(banco.getString("complemento"));
                localidade.setCep(banco.getString("cep"));
                localidade.setSituacao(banco.getString("situacao"));

            }
            banco.close();
            gui.close();
            return localidade;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Localidade carregarLocalidade(StringBuffer cursos) throws BancoException {
        Localidade regiao = new Localidade();
        regiao.setSituacao("nulo");
        sql = "SELECT * FROM LOCALIDADE WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cursos.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                regiao.setCodigo(banco.getInt("codigo"));
                regiao.setSituacao(banco.getString("situacao"));
            }
            gui.close();
            banco.close();
            return regiao;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDados(Localidade local) throws BancoException {
        sql = "UPDATE LOCALIDADE SET BAIRRO = ? , CIDADE = ?, ENDERECO = ?, NUMERO = ?,"
                + " COMPLEMENTO = ?, CEP = ?, SITUACAO = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setString(1, local.getBairro());
            guiAlterar.setString(2, local.getCidade());
            guiAlterar.setString(3, local.getEndereco());
            guiAlterar.setString(4, local.getNumero());
            guiAlterar.setString(5, local.getComplemento());
            guiAlterar.setString(6, local.getCep());
            guiAlterar.setString(7, local.getSituacao());
            guiAlterar.setInt(8, local.getCodigo());

            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
