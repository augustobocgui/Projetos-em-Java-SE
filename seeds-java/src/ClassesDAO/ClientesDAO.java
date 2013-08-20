/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Cliente;
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
 * @author Guilherme Augusto
 */
public class ClientesDAO {

    Connection conexao;
    private String sql;

    public ClientesDAO() throws BancoException {
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

    public int gerarCodigoCliente() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM CLIENTE";

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

    public int gerarCodigoClienteExcluido() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM CLIENTE_EXCLUIDO";

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

    public void adicionarCliente(Cliente cliente) throws BancoException {
        sql = "INSERT INTO CLIENTE (NOME, NASCIMENTO, CPF, TELEFONE,"
                + "ENDERECO, NUMERO, BAIRRO, UF, CIDADE,"
                + "  EMAIL, DESCONTO, SEXO, CELULAR, CEP, LOCALIDADE, CODIGO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cliente.getNome());
            gui.setString(2, cliente.getNascimento());
            gui.setString(3, cliente.getCpf());
            gui.setString(4, cliente.getTelefone());
            gui.setString(5, cliente.getEndereco());
            gui.setString(6, cliente.getNumero());
            gui.setString(7, cliente.getBairro());
            gui.setString(8, cliente.getUf());
            gui.setString(9, cliente.getCidade());
            gui.setString(10, cliente.getEmail());
            gui.setFloat(11, cliente.getDesconto());
            gui.setString(12, cliente.getSexo());
            gui.setString(13, cliente.getCelular());
            gui.setString(14, cliente.getCep());
            gui.setInt(15, cliente.getLocalidade());
            gui.setInt(16, cliente.getCodigo());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void adicionarClienteExcluido(Cliente cliente) throws BancoException {
        sql = "INSERT INTO CLIENTE_EXCLUIDO (NOME, NASCIMENTO, CPF, TELEFONE,"
                + "ENDERECO, NUMERO, BAIRRO, UF, CIDADE,"
                + "  EMAIL, DESCONTO, SEXO, CELULAR, CEP, LOCALIDADE, CODIGO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);

            gui.setString(1, cliente.getNome());
            gui.setString(2, cliente.getNascimento());
            gui.setString(3, cliente.getCpf());
            gui.setString(4, cliente.getTelefone());
            gui.setString(5, cliente.getEndereco());
            gui.setString(6, cliente.getNumero());
            gui.setString(7, cliente.getBairro());
            gui.setString(8, cliente.getUf());
            gui.setString(9, cliente.getCidade());
            gui.setString(10, cliente.getEmail());
            gui.setFloat(11, cliente.getDesconto());
            gui.setString(12, cliente.getSexo());
            gui.setString(13, cliente.getCelular());
            gui.setString(14, cliente.getCep());
            gui.setInt(15, cliente.getLocalidade());
            gui.setInt(16, cliente.getCodigo());

            gui.execute(); // se não precisar resgatar dados
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDados(Cliente cliente) throws BancoException {
        sql = "UPDATE CLIENTE SET NOME = ?, NASCIMENTO = ?, CPF = ?, TELEFONE = ?,"
                + " ENDERECO = ?, NUMERO = ?, BAIRRO = ?, UF = ?, "
                + " CIDADE = ?,"
                + " EMAIL = ?, DESCONTO = ?, SEXO = ?, CELULAR = ?, CEP = ?, LOCALIDADE = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setString(1, cliente.getNome());
            guiAlterar.setString(2, cliente.getNascimento());
            guiAlterar.setString(3, cliente.getCpf());
            guiAlterar.setString(4, cliente.getTelefone());
            guiAlterar.setString(5, cliente.getEndereco());
            guiAlterar.setString(6, cliente.getNumero());
            guiAlterar.setString(7, cliente.getBairro());
            guiAlterar.setString(8, cliente.getUf());
            guiAlterar.setString(9, cliente.getCidade());
            guiAlterar.setString(10, cliente.getEmail());
            guiAlterar.setFloat(11, cliente.getDesconto());
            guiAlterar.setString(12, cliente.getSexo());
            guiAlterar.setString(13, cliente.getCelular());
            guiAlterar.setString(14, cliente.getCep());
            guiAlterar.setInt(15, cliente.getLocalidade());
            guiAlterar.setInt(16, cliente.getCodigo());

            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void deletarCliente(Cliente controle) throws BancoException {
        sql = "DELETE FROM CLIENTE where CODIGO = ? AND LOCALIDADE = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());
            control.setInt(2, controle.getLocalidade());

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void deletarClienteExcluido(Cliente controle) throws BancoException {
        sql = "DELETE FROM CLIENTE_EXCLUIDO where CODIGO = ? AND LOCALIDADE = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());
            control.setInt(2, controle.getLocalidade());

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Cliente carregarCliente(StringBuffer nome) throws BancoException {
        Cliente fornecedor = new Cliente();
        fornecedor.setNome("nulo");
        sql = "SELECT * FROM CLIENTE WHERE NOME = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornecedor.setCodigo(banco.getInt("codigo"));
                fornecedor.setLocalidade(banco.getInt("localidade"));
                fornecedor.setNome(banco.getString("nome"));
                fornecedor.setNascimento(banco.getString("nascimento"));
                fornecedor.setCpf(banco.getString("cpf"));
                fornecedor.setTelefone(banco.getString("telefone"));
                fornecedor.setEndereco(banco.getString("endereco"));
                fornecedor.setNumero(banco.getString("numero"));
                fornecedor.setBairro(banco.getString("bairro"));
                fornecedor.setUf(banco.getString("uf"));
                fornecedor.setCidade(banco.getString("cidade"));
                fornecedor.setEmail(banco.getString("email"));
                fornecedor.setDesconto(banco.getFloat("desconto"));
                fornecedor.setSexo(banco.getString("sexo"));
                fornecedor.setCelular(banco.getString("celular"));
                fornecedor.setCep(banco.getString("cep"));
            }
            banco.close();
            gui.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Cliente carregarClientePeloCodigo(StringBuffer nome, StringBuffer nomes) throws BancoException {
        Cliente fornecedor = new Cliente();
        fornecedor.setNome("nulo");
        sql = "SELECT * FROM CLIENTE WHERE CODIGO = ? AND LOCALIDADE = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            gui.setString(2, nomes.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornecedor.setCodigo(banco.getInt("codigo"));
                fornecedor.setLocalidade(banco.getInt("localidade"));
                fornecedor.setNome(banco.getString("nome"));
                fornecedor.setNascimento(banco.getString("nascimento"));
                fornecedor.setCpf(banco.getString("cpf"));
                fornecedor.setTelefone(banco.getString("telefone"));
                fornecedor.setEndereco(banco.getString("endereco"));
                fornecedor.setNumero(banco.getString("numero"));
                fornecedor.setBairro(banco.getString("bairro"));
                fornecedor.setUf(banco.getString("uf"));
                fornecedor.setCidade(banco.getString("cidade"));
                fornecedor.setEmail(banco.getString("email"));
                fornecedor.setDesconto(banco.getFloat("desconto"));
                fornecedor.setSexo(banco.getString("sexo"));
                fornecedor.setCelular(banco.getString("celular"));
                fornecedor.setCep(banco.getString("cep"));
            }
            gui.close();
            banco.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Cliente carregarClienteInativoPeloCodigo(StringBuffer nome, StringBuffer nomes) throws BancoException {
        Cliente fornecedor = new Cliente();
        fornecedor.setNome("nulo");
        sql = "SELECT * FROM CLIENTE_EXCLUIDO WHERE CODIGO = ? AND LOCALIDADE = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            gui.setString(2, nomes.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornecedor.setCodigo(banco.getInt("codigo"));
                fornecedor.setLocalidade(banco.getInt("localidade"));
                fornecedor.setNome(banco.getString("nome"));
                fornecedor.setNascimento(banco.getString("nascimento"));
                fornecedor.setCpf(banco.getString("cpf"));
                fornecedor.setTelefone(banco.getString("telefone"));
                fornecedor.setEndereco(banco.getString("endereco"));
                fornecedor.setNumero(banco.getString("numero"));
                fornecedor.setBairro(banco.getString("bairro"));
                fornecedor.setUf(banco.getString("uf"));
                fornecedor.setCidade(banco.getString("cidade"));
                fornecedor.setEmail(banco.getString("email"));
                fornecedor.setDesconto(banco.getFloat("desconto"));
                fornecedor.setSexo(banco.getString("sexo"));
                fornecedor.setCelular(banco.getString("celular"));
                fornecedor.setCep(banco.getString("cep"));
            }
            gui.close();
            banco.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Cliente carregarClienteComb(StringBuffer cod) throws BancoException {
        Cliente cliente = new Cliente();
        cliente.setNome("nulo");
        sql = "SELECT * FROM CLIENTE WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cod.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                cliente.setCodigo(banco.getInt("codigo"));
                cliente.setLocalidade(banco.getInt("localidade"));
                cliente.setNome(banco.getString("nome"));
            }
            banco.close();
            gui.close();
            return cliente;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Cliente carregarClienteN(StringBuffer fornecer) throws BancoException {
        Cliente fornecedor = new Cliente();
        fornecedor.setNome("nulo");
        sql = "SELECT * FROM CLIENTE WHERE NOME = ? *~";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, fornecer.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {

                fornecedor.setCodigo(banco.getInt("codigo"));
                fornecedor.setLocalidade(banco.getInt("localidade"));
                fornecedor.setNome(banco.getString("nome"));
                fornecedor.setNascimento(banco.getString("nascimento"));
                fornecedor.setCpf(banco.getString("cpf"));
                fornecedor.setTelefone(banco.getString("telefone"));
                fornecedor.setEndereco(banco.getString("endereco"));
                fornecedor.setNumero(banco.getString("numero"));
                fornecedor.setBairro(banco.getString("bairro"));
                fornecedor.setUf(banco.getString("uf"));
                fornecedor.setCidade(banco.getString("cidade"));
                fornecedor.setEmail(banco.getString("email"));
                fornecedor.setDesconto(banco.getFloat("desconto"));
                fornecedor.setSexo(banco.getString("sexo"));
                fornecedor.setCelular(banco.getString("celular"));
                fornecedor.setCep(banco.getString("cep"));
            }
            gui.close();
            banco.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Cliente carregarClienteCPF(StringBuffer cpf) throws BancoException {
        Cliente fornecedor = new Cliente();
        fornecedor.setNome("nulo");
        sql = "SELECT * FROM CLIENTE WHERE CPF = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, cpf.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornecedor.setCodigo(banco.getInt("codigo"));
                fornecedor.setLocalidade(banco.getInt("localidade"));
                fornecedor.setNome(banco.getString("nome"));
                fornecedor.setNascimento(banco.getString("nascimento"));
                fornecedor.setCpf(banco.getString("cpf"));
                fornecedor.setTelefone(banco.getString("telefone"));
                fornecedor.setEndereco(banco.getString("endereco"));
                fornecedor.setNumero(banco.getString("numero"));
                fornecedor.setBairro(banco.getString("bairro"));
                fornecedor.setUf(banco.getString("uf"));
                fornecedor.setCidade(banco.getString("cidade"));
                fornecedor.setEmail(banco.getString("email"));
                fornecedor.setDesconto(banco.getFloat("desconto"));
                fornecedor.setSexo(banco.getString("sexo"));
                fornecedor.setCelular(banco.getString("celular"));
                fornecedor.setCep(banco.getString("cep"));
            }
            banco.close();
            gui.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Cliente> pesquisaCliente(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from cliente";
        } else {
            sql = "Select * from cliente where nome like " + aspas + "%" + pes + "%" + aspas + "and cpf"
                    + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Cliente> lista = new ArrayList<Cliente>();
            while (banco.next()) // só entra se existir 
            {
                Cliente fornecer = new Cliente();
                fornecer.setCodigo(banco.getInt("codigo"));
                fornecer.setLocalidade(banco.getInt("localidade"));
                fornecer.setNome(banco.getString("nome"));
                fornecer.setCpf(banco.getString("cpf"));
                fornecer.setTelefone(banco.getString("telefone"));

                lista.add(fornecer);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Cliente> pesquisaClienteExcluido(String pes, String pesCpf) throws BancoException {
        char aspas = '"';
        String sql;
        if (pes.equals("") || pes == null) {// || pesCpf.equals("")) {
            sql = "Select * from cliente_excluido";
        } else {
            sql = "Select * from cliente_excluido where nome like " + aspas + "%" + pes + "%" + aspas + "and cpf"
                    + " like" + aspas + "%" + pesCpf + "%" + aspas;
        }

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            ResultSet banco = gui.executeQuery();

            List<Cliente> lista = new ArrayList<Cliente>();
            while (banco.next()) // só entra se existir 
            {
                Cliente fornecer = new Cliente();
                fornecer.setCodigo(banco.getInt("codigo"));
                fornecer.setLocalidade(banco.getInt("localidade"));
                fornecer.setNome(banco.getString("nome"));
                fornecer.setCpf(banco.getString("cpf"));
                fornecer.setTelefone(banco.getString("telefone"));

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
