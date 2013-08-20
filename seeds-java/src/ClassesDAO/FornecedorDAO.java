/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Fornecedor;
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
public class FornecedorDAO {

    Connection conexao;
    private String sql;

    public FornecedorDAO() throws BancoException {
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

    public int gerarCodigoFornecedor() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM FORNECEDOR";

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

    public void adicionarFornecedor(Fornecedor fornecedor) throws BancoException {
        sql = "INSERT INTO FORNECEDOR ( NOME, ENDERECO,"
                + "NUMERO, COMPLEMENTO,"
                + "BAIRRO, CIDADE, CEP, PABX, FAX, CPF, CNPJ, HOME_PAGE,"
                + "  EMAIL) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, fornecedor.getNome());
            gui.setString(2, fornecedor.getEndereco());
            gui.setString(3, fornecedor.getNumero());
            gui.setString(4, fornecedor.getComplemento());
            gui.setString(5, fornecedor.getBairro());
            gui.setString(6, fornecedor.getCidade());
            gui.setString(7, fornecedor.getCep());
            gui.setString(8, fornecedor.getTelefone1());
            gui.setString(9, fornecedor.getTelefone2());
            gui.setString(10, fornecedor.getCpf());
            gui.setString(11, fornecedor.getCnpj());
            gui.setString(12, fornecedor.getContato());
            gui.setString(13, fornecedor.getEmail());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void adicionarFornecedorExcluido(Fornecedor fornecedor) throws BancoException {
        sql = "INSERT INTO FORNECEDOR_EXCLUIDO ( CODIGO,"
                + " NOME, ENDERECO, NUMERO, COMPLEMENTO,"
                + "BAIRRO, CIDADE, CEP, PABX, FAX, CPF, CNPJ, HOME_PAGE,"
                + "  EMAIL) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, fornecedor.getCodigo());
            gui.setString(2, fornecedor.getNome());
            gui.setString(3, fornecedor.getEndereco());
            gui.setString(4, fornecedor.getNumero());
            gui.setString(5, fornecedor.getComplemento());
            gui.setString(6, fornecedor.getBairro());
            gui.setString(7, fornecedor.getCidade());
            gui.setString(8, fornecedor.getCep());
            gui.setString(9, fornecedor.getTelefone1());
            gui.setString(10, fornecedor.getTelefone2());
            gui.setString(11, fornecedor.getCpf());
            gui.setString(12, fornecedor.getCnpj());
            gui.setString(13, fornecedor.getContato());
            gui.setString(14, fornecedor.getEmail());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDados(Fornecedor fornecedor) throws BancoException {
        sql = "UPDATE FORNECEDOR SET NOME = ?,"
                + "  ENDERECO = ?, NUMERO = ?, COMPLEMENTO = ?,"
                + " BAIRRO = ?, CIDADE = ?, CEP = ?, PABX = ?, "
                + " FAX = ?, CPF = ?, CNPJ = ?, HOME_PAGE = ?,"
                + " EMAIL = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setString(1, fornecedor.getNome());
            guiAlterar.setString(2, fornecedor.getEndereco());
            guiAlterar.setString(3, fornecedor.getNumero());
            guiAlterar.setString(4, fornecedor.getComplemento());
            guiAlterar.setString(5, fornecedor.getBairro());
            guiAlterar.setString(6, fornecedor.getCidade());
            guiAlterar.setString(7, fornecedor.getCep());
            guiAlterar.setString(8, fornecedor.getTelefone1());
            guiAlterar.setString(9, fornecedor.getTelefone2());
            guiAlterar.setString(10, fornecedor.getCpf());
            guiAlterar.setString(11, fornecedor.getCnpj());
            guiAlterar.setString(12, fornecedor.getContato());
            guiAlterar.setString(13, fornecedor.getEmail());
            guiAlterar.setInt(14, fornecedor.getCodigo());


            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void deletarFornecedor(Fornecedor controle) throws BancoException {
        sql = "DELETE FROM FORNECEDOR where CODIGO = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Fornecedor carregarFornecedor(StringBuffer nome) throws BancoException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("nulo");
        sql = "SELECT * FROM FORNECEDOR WHERE NOME = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornecedor.setCodigo(banco.getInt("codigo"));
                fornecedor.setNome(banco.getString("nome"));
                fornecedor.setEndereco(banco.getString("endereco"));
                fornecedor.setNumero(banco.getString("numero"));
                fornecedor.setComplemento(banco.getString("complemento"));
                fornecedor.setBairro(banco.getString("bairro"));
                fornecedor.setCidade(banco.getString("cidade"));
                fornecedor.setCep(banco.getString("cep"));
                fornecedor.setTelefone1(banco.getString("pabx"));
                fornecedor.setTelefone2(banco.getString("fax"));
                fornecedor.setCpf(banco.getString("cpf"));
                fornecedor.setCnpj(banco.getString("cnpj"));
                fornecedor.setContato(banco.getString("home_page"));
                fornecedor.setEmail(banco.getString("email"));
            }
            banco.close();
            gui.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Fornecedor carregarFornecedorPeloCodigo(StringBuffer nome) throws BancoException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("nulo");
        sql = "SELECT * FROM FORNECEDOR WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornecedor.setCodigo(banco.getInt("codigo"));
                fornecedor.setNome(banco.getString("nome"));
                fornecedor.setEndereco(banco.getString("endereco"));
                fornecedor.setNumero(banco.getString("numero"));
                fornecedor.setComplemento(banco.getString("complemento"));
                fornecedor.setBairro(banco.getString("bairro"));
                fornecedor.setCidade(banco.getString("cidade"));
                fornecedor.setCep(banco.getString("cep"));
                fornecedor.setTelefone1(banco.getString("pabx"));
                fornecedor.setTelefone2(banco.getString("fax"));
                fornecedor.setCpf(banco.getString("cpf"));
                fornecedor.setCnpj(banco.getString("cnpj"));
                fornecedor.setContato(banco.getString("home_page"));
                fornecedor.setEmail(banco.getString("email"));
            }
            gui.close();
            banco.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Fornecedor carregarFornecedorComb(StringBuffer cod) throws BancoException {
        Fornecedor fornece = new Fornecedor();
        fornece.setNome("nulo");
        sql = "SELECT * FROM FORNECEDOR WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cod.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornece.setCodigo(banco.getInt("codigo"));
                fornece.setNome(banco.getString("nome"));
            }
            banco.close();
            gui.close();
            return fornece;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Fornecedor carregarFornecedorN(StringBuffer fornecer) throws BancoException {
        Fornecedor curso = new Fornecedor();
        curso.setNome("nulo");
        sql = "SELECT * FROM FORNECEDOR WHERE NOME = ? *~";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, fornecer.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                curso.setCodigo(banco.getInt("codigo"));
                curso.setNome(banco.getString("nome"));
            }
            gui.close();
            banco.close();
            return curso;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Fornecedor carregarFornecedorCPF(String cpf) throws BancoException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("nulo");
        sql = "SELECT * FROM FORNECEDOR WHERE CPF = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cpf);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornecedor.setCodigo(banco.getInt("codigo"));
                fornecedor.setNome(banco.getString("nome"));
                fornecedor.setEndereco(banco.getString("endereco"));
                fornecedor.setNumero(banco.getString("numero"));
                fornecedor.setComplemento(banco.getString("complemento"));
                fornecedor.setBairro(banco.getString("bairro"));
                fornecedor.setCidade(banco.getString("cidade"));
                fornecedor.setCep(banco.getString("cep"));
                fornecedor.setTelefone1(banco.getString("pabx"));
                fornecedor.setTelefone2(banco.getString("fax"));
                fornecedor.setCpf(banco.getString("cpf"));
                fornecedor.setCnpj(banco.getString("cnpj"));
                fornecedor.setContato(banco.getString("home_page"));
                fornecedor.setEmail(banco.getString("email"));
            }
            banco.close();
            gui.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Fornecedor carregarFornecedorCNPJ(String cnpj) throws BancoException {
        Fornecedor fornece = new Fornecedor();
        fornece.setNome("nulo");
        sql = "SELECT * FROM FORNECEDOR WHERE CNPJ = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cnpj);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornece.setCodigo(banco.getInt("codigo"));
                fornece.setNome(banco.getString("nome"));
                fornece.setCpf(banco.getString("cnpj"));
            }
            gui.close();
            banco.close();
            return fornece;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Fornecedor> pesquisaFornecedor(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from fornecedor";
        } else {
            sql = "Select * from fornecedor where nome like " + aspas + "%" + pes + "%" + aspas + "and cpf"
                    + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Fornecedor> lista = new ArrayList<Fornecedor>();
            while (banco.next()) // só entra se existir 
            {
                Fornecedor fornecer = new Fornecedor();
                fornecer.setCodigo(banco.getInt("codigo"));
                fornecer.setNome(banco.getString("nome"));
                fornecer.setCpf(banco.getString("cpf"));
                fornecer.setCnpj(banco.getString("cnpj"));

                lista.add(fornecer);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
