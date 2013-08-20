/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.SubProdutos;
import Excessoes.BancoException;
import br.com.seeds.FazerConexao;
import com.mysql.jdbc.Connection;
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
public class SubProdutosDAO {

    Connection conexao;
    private String sql;
    static ResultSet rs;

    public SubProdutosDAO() throws BancoException {
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

    public int gerarCodigoSubProduto() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM SUBPRODUTOS";

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

    public void InserindoSubProduto(SubProdutos sp) throws BancoException {
        sql = "INSERT INTO subprodutos (Categoria,Nome,PrecoCompra,PrecoVenda,Retirada,Quantidade,QuantidadeMin) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, sp.getCategoria());
            gui.setString(2, sp.getNome());
            gui.setFloat(3, sp.getPrecoCompra());
            gui.setFloat(4, sp.getPrecoVenda());
            gui.setInt(5, sp.getRetirada());
            gui.setInt(6, sp.getQuantidade());
            gui.setInt(7, sp.getQuantidadeMin());

            gui.execute();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ResultSet SelecionandoSubprodutos(int Codigo) throws BancoException {
        sql = "SELECT * FROM subprodutos where CODIGO='" + Codigo + "'";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public ResultSet TabelaSubprodutos() throws BancoException {
        sql = "SELECT * FROM subprodutos order by Codigo desc";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public void AtualizandoSubproduto(SubProdutos sp) throws BancoException {
        sql = "update subprodutos set Categoria = ?,Nome = ?,PrecoCompra = ?,PrecoVenda= ?,Retirada= ?,Quantidade = ?,QuantidadeMin = ? where Codigo = ?";

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, sp.getCategoria());
            gui.setString(2, sp.getNome());
            gui.setFloat(3, sp.getPrecoCompra());
            gui.setFloat(4, sp.getPrecoVenda());
            gui.setInt(5, sp.getRetirada());
            gui.setInt(6, sp.getQuantidade());
            gui.setInt(7, sp.getQuantidadeMin());
            gui.setInt(8, sp.getCodigo());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void deletarSubProduto(int Codigo) throws BancoException {
        sql = "DELETE FROM subprodutos where Codigo= ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, Codigo);

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ResultSet CodigoSubprodutos() throws BancoException {
        sql = "SELECT * FROM subprodutos order by Codigo asc";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public void AtualizandoAI(int Codigo) throws BancoException {
        sql = "ALTER TABLE subprodutos AUTO_INCREMENT = ?";

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, Codigo);

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ResultSet TabelaSubprodutosCategoria(String Categoria) throws BancoException {
        sql = "SELECT * FROM subprodutos where Categoria ='" + Categoria + "'  order by Codigo desc";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }
    //ALTER TABLE tabela AUTO_INCREMENT = 1;

    public ResultSet SubProdutosFinal() throws BancoException {
        sql = "SELECT * FROM subprodutos where Retirada='" + 1 + "'";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public ResultSet Selecionando(String Nome) throws BancoException {
        sql = "SELECT * FROM subprodutos where Nome='" + Nome + "'";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public void deletar(int Codigo) throws BancoException {
        sql = "DELETE FROM vendamesas where Codigo = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, Codigo);

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void Atualizando(String Nome, int quantidade) throws BancoException {
        sql = "update subprodutos set Quantidade = Quantidade +  ? where Nome = ? and Retirada = ?";

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, quantidade);
            gui.setString(2, Nome);
            gui.setInt(3, 1);

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<SubProdutos> pesquisaProduto(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from subprodutos";
        } else {
            sql = "Select * from subprodutos where nome like " + aspas + "%" + pes + "%" + aspas
                    + "and PrecoVenda" + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }


        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<SubProdutos> lista = new ArrayList<SubProdutos>();
            while (banco.next()) // só entra se existir 
            {
                SubProdutos produto = new SubProdutos();
                produto.setCodigo(banco.getInt("Codigo"));
                produto.setNome(banco.getString("Nome"));
                produto.setCategoria(banco.getString("Categoria"));
                produto.setQuantidade(banco.getInt("Quantidade"));
                produto.setQuantidadeMin(banco.getInt("QuantidadeMin"));
                produto.setPrecoVenda(banco.getFloat("PrecoVenda"));
                produto.setPrecoCompra(banco.getFloat("PrecoCompra"));

                lista.add(produto);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDadosProduto(SubProdutos material) throws BancoException {
        sql = "UPDATE SUBPRODUTOS SET Nome= ?, Categoria = ?, Quantidade = ?,"
                + " QuantidadeMin = ?, PrecoCompra = ?, PrecoVenda = ? WHERE Codigo = ? ";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);

            //gui.setInt(1, material.getCodigo_fornecedor());
            gui.setString(1, material.getNome());
            gui.setString(2, material.getCategoria());
            gui.setInt(3, material.getQuantidade());
            gui.setInt(4, material.getQuantidadeMin());
            gui.setDouble(5, material.getPrecoCompra());
            gui.setDouble(6, material.getPrecoVenda());
            gui.setInt(7, material.getCodigo());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public SubProdutos carregarProdutoPeloCodigo(String nome) throws BancoException {
        SubProdutos material = new SubProdutos();
        material.setNome("nulo");
        sql = "SELECT * FROM SUBPRODUTOS WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                material.setCodigo(banco.getInt("Codigo"));
                //material.setCodigo_fornecedor(banco.getInt("fornecedor_codigo"));
                material.setNome(banco.getString("Nome"));
                material.setCategoria(banco.getString("Categoria"));
                material.setQuantidade(banco.getInt("Quantidade"));
                material.setQuantidadeMin(banco.getInt("QuantidadeMin"));
                material.setPrecoCompra(banco.getFloat("PrecoCompra"));
                material.setPrecoVenda(banco.getFloat("PrecoVenda"));
            }
            gui.close();
            banco.close();
            return material;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
