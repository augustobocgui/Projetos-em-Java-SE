/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Produto;
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
public class ProdutoDAO {

    Connection conexao;
    private String sql;

    public ProdutoDAO() throws BancoException {
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
        sql = "SELECT MAX(CODIGO) as codigo FROM PRODUTO";

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

    public void adicionarProduto(Produto material) throws BancoException {
        sql = "INSERT INTO PRODUTO (CODIGO, FORNECEDOR_CODIGO, NOME, MARCA, ESTOQUE,"
                + "ESTOQUE_CRITICO, PRECO, PRECO_VENDA) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, material.getCodigo());
            gui.setInt(2, material.getCodigo_fornecedor());
            gui.setString(3, material.getNome());
            gui.setString(4, material.getMarca());
            gui.setInt(5, material.getEstoque());
            gui.setInt(6, material.getEstoque_critico());
            gui.setDouble(7, material.getPreco());
            gui.setDouble(8, material.getPreco_venda());

            gui.execute(); // se não precisar resgatar dados
            gui.close();

        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void adicionarProdutoExcluido(Produto material) throws BancoException {
        sql = "INSERT INTO PRODUTO_EXCLUIDO (CODIGO, FORNECEDOR_CODIGO, NOME, MARCA, ESTOQUE,"
                + "ESTOQUE_CRITICO, PRECO, PRECO_VENDA) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, material.getCodigo());
            gui.setInt(2, material.getCodigo_fornecedor());
            gui.setString(3, material.getNome());
            gui.setString(4, material.getMarca());
            gui.setInt(5, material.getEstoque());
            gui.setInt(6, material.getEstoque_critico());
            gui.setDouble(7, material.getPreco());
            gui.setDouble(8, material.getPreco_venda());

            gui.execute(); // se não precisar resgatar dados
            gui.close();

        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void deletarProduto(Produto controle) throws BancoException {
        sql = "DELETE FROM PRODUTO where CODIGO = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Produto carregarProdutoPeloCodigo(String nome) throws BancoException {
        Produto material = new Produto();
        material.setNome("nulo");
        sql = "SELECT * FROM PRODUTO WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                material.setCodigo(banco.getInt("codigo"));
                material.setCodigo_fornecedor(banco.getInt("fornecedor_codigo"));
                material.setNome(banco.getString("nome"));
                material.setMarca(banco.getString("marca"));
                material.setEstoque(banco.getInt("estoque"));
                material.setEstoque_critico(banco.getInt("estoque_critico"));
                material.setPreco(banco.getDouble("preco"));
                material.setPreco_venda(banco.getDouble("preco_venda"));
            }
            gui.close();
            banco.close();
            return material;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Produto carregarProduto(String nome) throws BancoException {
        Produto material = new Produto();
        material.setNome("nulo");
        sql = "SELECT * FROM PRODUTO WHERE NOME = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                material.setCodigo(banco.getInt("codigo"));
                material.setCodigo_fornecedor(banco.getInt("fornecedor_codigo"));
                material.setNome(banco.getString("nome"));
                material.setMarca(banco.getString("marca"));
                material.setEstoque(banco.getInt("estoque"));
                material.setEstoque_critico(banco.getInt("estoque_critico"));
                material.setPreco(banco.getDouble("preco"));
                material.setPreco_venda(banco.getDouble("preco_venda"));

            }
            gui.close();
            banco.close();
            return material;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDadosProduto(Produto material) throws BancoException {
        sql = "UPDATE PRODUTO SET FORNECEDOR_CODIGO = ?, NOME= ?, MARCA = ?, ESTOQUE = ?,"
                + " ESTOQUE_CRITICO = ?, PRECO = ?, PRECO_VENDA = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);

            gui.setInt(1, material.getCodigo_fornecedor());
            gui.setString(2, material.getNome());
            gui.setString(3, material.getMarca());
            gui.setInt(4, material.getEstoque());
            gui.setInt(5, material.getEstoque_critico());
            gui.setDouble(6, material.getPreco());
            gui.setDouble(7, material.getPreco_venda());
            gui.setInt(8, material.getCodigo());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDadosPreco(Produto prod) throws BancoException {
        sql = "UPDATE PRODUTO SET PRECO = ?, PRECO_VENDA = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);

            guiAlterar.setDouble(1, prod.getPreco());
            guiAlterar.setDouble(2, prod.getPreco_venda());
            guiAlterar.setInt(3, prod.getCodigo());

            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDadosEstoque(Produto prod) throws BancoException {
        sql = "UPDATE PRODUTO SET ESTOQUE = ?, ESTOQUE_CRITICO = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);

            guiAlterar.setInt(1, prod.getEstoque());
            guiAlterar.setInt(2, prod.getEstoque_critico());
            guiAlterar.setInt(3, prod.getCodigo());

            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDadosEstoqueVenda(Produto prod) throws BancoException {
        sql = "UPDATE PRODUTO SET ESTOQUE = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);

            guiAlterar.setInt(1, prod.getEstoque());
            guiAlterar.setInt(2, prod.getCodigo());

            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Produto carregarMaterialComb(StringBuffer cursos) throws BancoException {
        Produto prod = new Produto();
        prod.setNome("nulo");
        sql = "SELECT * FROM PRODUTO WHERE CODIGO = ?";

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cursos.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                prod.setCodigo(banco.getInt("codigo"));
                prod.setNome(banco.getString("nome"));

            }
            gui.close();
            banco.close();
            return prod;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Produto carregarPodutoN(String cursos) throws BancoException {
        Produto mat = new Produto();
        mat.setNome("nulo");
        sql = "SELECT * FROM PRODUTO WHERE NOME = ?";

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cursos);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                mat.setCodigo(banco.getInt("codigo"));
                mat.setCodigo_fornecedor(banco.getInt("fornecedor_codigo"));
                mat.setNome(banco.getString("nome"));
                mat.setMarca(banco.getString("marca"));
                mat.setEstoque(banco.getInt("estoque"));
                mat.setEstoque_critico(banco.getInt("estoque_critico"));
                mat.setPreco(banco.getDouble("preco"));
                mat.setPreco_venda(banco.getDouble("preco_venda"));

            }
            gui.close();
            banco.close();
            return mat;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Produto> pesquisaProduto(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from produto";
        } else {
            sql = "Select * from produto where nome like " + aspas + "%" + pes + "%" + aspas
                    + "and codigo" + " like" + aspas + "%" + pesCpf + "%" + aspas
                    + "and marca" + " like" + aspas + "%" + pesCpf + "%" + aspas
                    + "and preco_venda" + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }


        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Produto> lista = new ArrayList<Produto>();
            while (banco.next()) // só entra se existir 
            {
                Produto produto = new Produto();
                produto.setCodigo(banco.getInt("codigo"));
                produto.setNome(banco.getString("nome"));
                produto.setMarca(banco.getString("marca"));
                produto.setEstoque(banco.getInt("estoque"));
                produto.setEstoque_critico(banco.getInt("estoque_critico"));
                produto.setPreco_venda(banco.getDouble("preco_venda"));

                lista.add(produto);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
