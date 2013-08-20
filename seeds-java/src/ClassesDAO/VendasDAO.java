/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Vendas;
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
public class VendasDAO {

    Connection conexao;
    private String sql;

    public VendasDAO() throws Exception {
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

    public int gerarCodigoVendas() throws Exception {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM VENDAS";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet gui = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (gui.next()) {
                codigo = gui.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            stm.close();
            gui.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
        return codigo + 1;
    }

    public void adicionarVenda(Vendas dinheiro) throws Exception {
        sql = "INSERT INTO VENDAS (CODIGO, ALUNO_CODIGO, FUNCIONARIO_CODIGO, VENDA_ERA, DESCONTO_VENDA,"
                + " TOTAL_VENDA, SITUACAO) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, dinheiro.getCodigo());
            gui.setInt(2, dinheiro.getAluno_codigo());
            gui.setInt(3, dinheiro.getFuncionario_codigo());
            gui.setString(4, dinheiro.getVenda_era());
            gui.setDouble(5, dinheiro.getDesconto_venda());
            gui.setDouble(6, dinheiro.getTotal_venda());
            gui.setString(7, dinheiro.getSituacao());
            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new Exception("" + e);
        }
    }
}
