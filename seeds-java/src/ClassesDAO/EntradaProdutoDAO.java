/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.EntradaSaida;
import Excessoes.BancoException;
import br.com.seeds.FazerConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme Augusto
 */
public class EntradaProdutoDAO {

    Connection conexao;
    private String sql;

    public EntradaProdutoDAO() throws BancoException {
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

    public int gerarCodigoProduto() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM ENTRADA_SAIDA";

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

    public void adicionarProduto(EntradaSaida material) throws BancoException {
        sql = "INSERT INTO ENTRADA_SAIDA (PRODUTO_CODIGO, DATA_HORA,"
                + " TIPO, QTDE, PRECO ) VALUES ( ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, material.getProduto_codigo());
            gui.setString(2, material.getData_hora());
            gui.setString(3, material.getTipo());
            gui.setInt(4, material.getQde());
            gui.setDouble(5, material.getPreco());


            gui.execute(); // se não precisar resgatar dados
            gui.close();

        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDadosProduto(EntradaSaida material) throws BancoException {
        sql = "UPDATE ENTRADA_SAIDA SET PRODUTO_CODIGO = ?,"
                + " DATA_HORA = ?, TIPO = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);

            gui.setInt(1, material.getProduto_codigo());
            gui.setString(2, material.getData_hora());
            gui.setString(3, material.getTipo());
            gui.setInt(4, material.getCodigo());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
