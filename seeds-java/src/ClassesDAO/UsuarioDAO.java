package ClassesDAO;

import Classes.Usuario;
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

public class UsuarioDAO {

    Connection conexao;
    private String sql;

    public UsuarioDAO() throws BancoException {
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

    public int gerarCodigoUsuario() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM USUARIO";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet imperio = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (imperio.next()) {
                codigo = imperio.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            stm.close();
            imperio.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return codigo + 1;
    }

    public int gerarCodigoUsuarioUltimoLogado() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM ACESSO";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet imperio = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (imperio.next()) {
                codigo = imperio.getInt("CODIGO"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            stm.close();
            imperio.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return codigo;
    }

    public void adicionarUsuario(Usuario user) throws BancoException {
        sql = "INSERT INTO USUARIO (CODIGO, NOME, USUARIO, SENHA,"
                + " TIPO) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement imperio = conexao.prepareStatement(sql);
            imperio.setInt(1, user.getCodigo());
            imperio.setString(2, user.getNome());
            imperio.setString(3, user.getLogin());
            imperio.setString(4, user.getSenha());
            imperio.setString(5, user.getFuncao());

            imperio.execute(); // se não precisar resgatar dados
            imperio.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void adicionarUsuarioExcluido(Usuario user) throws BancoException {
        sql = "INSERT INTO USUARIO_EXCLUIDO (CODIGO, NOME, USUARIO, SENHA,"
                + " TIPO) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement imperio = conexao.prepareStatement(sql);
            imperio.setInt(1, user.getCodigo());
            imperio.setString(2, user.getNome());
            imperio.setString(3, user.getLogin());
            imperio.setString(4, user.getSenha());
            imperio.setString(5, user.getFuncao());

            imperio.execute(); // se não precisar resgatar dados
            imperio.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Usuario carregarUsuario(String nome) throws BancoException {
        Usuario user = new Usuario();
        user.setLogin("nulo");
        user.setSenha("nulo");
        sql = "SELECT * FROM Usuario WHERE Usuario = ?";
        try {
            PreparedStatement imperio = conexao.prepareStatement(sql);
            imperio.setString(1, nome);
            ResultSet banco = imperio.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                user.setLogin(banco.getString("Usuario"));
                user.setSenha(banco.getString("Senha"));
                user.setTipo(banco.getString("Tipo"));
                user.setCodigo(banco.getInt("codigo"));
                user.setNome(banco.getString("nome"));

            }
            imperio.close();
            banco.close();
            return user;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Usuario carregarNovoUsuario(String nome) throws BancoException {
        Usuario user = new Usuario();
        user.setSenha("nulo");
        sql = "SELECT * FROM USUARIO WHERE SENHA = ?";
        try {
            PreparedStatement imperio = conexao.prepareStatement(sql);
            imperio.setString(1, nome);
            ResultSet banco = imperio.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                user.setCodigo(banco.getInt("codigo"));
                user.setLogin(banco.getString("login"));
                user.setSenha(banco.getString("senha"));

            }
            imperio.close();
            banco.close();
            return user;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public boolean verificaUsuario(Usuario usu) throws BancoException {
        boolean Encontrou = false;

        sql = "SELECT * FROM USUARIO WHERE USUARIO=? AND SENHA=?";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);

            stm.setString(1, usu.getLogin());
            stm.setString(2, usu.getSenha());

            ResultSet gui = stm.executeQuery();
            while (gui.next()) {
                Encontrou = true;
            }
            stm.close();
            gui.close();
            return Encontrou;
        } catch (SQLException ex) {
            throw new BancoException("" + ex);
        }
    }

    public boolean verificaDisponibilidadeUsuario(Usuario usu) throws BancoException {
        boolean Encontrou = false;

        sql = "SELECT * FROM USUARIO WHERE USUARIO=?";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);

            stm.setString(1, usu.getLogin());

            ResultSet gui = stm.executeQuery();
            while (gui.next()) {
                Encontrou = true;
            }
            stm.close();
            gui.close();
            return Encontrou;
        } catch (SQLException ex) {
            throw new BancoException("" + ex);
        }
    }

    public Usuario carregarAcesso(StringBuffer nome) throws BancoException {
        Usuario usuario = new Usuario();
        usuario.setNome("nulo");
        sql = "SELECT * FROM CONTROLE_ACESSO WHERE LIST = ?";
        try {
            PreparedStatement user = conexao.prepareStatement(sql);
            user.setString(1, nome.toString());
            ResultSet banco = user.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                usuario.setNome(banco.getString("usuario"));
                usuario.setSenha(banco.getString("data"));
            }
            banco.close();
            user.close();
            return usuario;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public List<Usuario> carregarTodosAcesso(String nome) throws BancoException {

        sql = "SELECT * FROM CONTROLE_ACESSO WHERE LIST = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome);
            ResultSet banco = gui.executeQuery();

            List<Usuario> lista = new ArrayList<Usuario>();
            while (banco.next()) // só entra se existir 
            {
                Usuario user = new Usuario();
                user.setCodigo(banco.getInt("codigo"));
                user.setNome(banco.getString("usuario"));
                user.setAcesso(banco.getString("data"));
                user.setDocumento(banco.getString("documento"));
                lista.add(user);
            }
            banco.close();
            gui.close();
            return lista;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public int buscarCodigoUsuario() throws BancoException {
        int codigo = 0;
        sql = "SELECT MAX(CODIGO) as codigo FROM CONTROLE_ACESSO";

        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            ResultSet imperio = stm.executeQuery(); // se tiver que puxar algum dado do banco
            while (imperio.next()) {
                codigo = imperio.getInt("codigo"); // puxo o valor da tabela alunos codigo do banco de dados
            }
            imperio.close();
            stm.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return codigo;
    }

    public Usuario carregarUsuarioPeloNome(StringBuffer nome) throws BancoException {
        Usuario user = new Usuario();
        user.setNome("nulo");
        sql = "SELECT * FROM USUARIO WHERE NOME = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                user.setCodigo(banco.getInt("codigo"));
                user.setNome(banco.getString("nome"));

            }
            gui.close();
            banco.close();
            return user;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Usuario carregarUsuarioPeloNomeUser(StringBuffer nome) throws BancoException {
        Usuario user = new Usuario();
        user.setNome("nulo");
        sql = "SELECT * FROM USUARIO WHERE USUARIO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                user.setCodigo(banco.getInt("codigo"));
                user.setNome(banco.getString("nome"));
                user.setLogin(banco.getString("usuario"));
                user.setFuncao(banco.getString("tipo"));

            }
            gui.close();
            banco.close();
            return user;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public Usuario carregarUsuarioPeloCodigo(int nome) throws BancoException {
        Usuario user = new Usuario();
        user.setNome("nulo");
        sql = "SELECT * FROM USUARIO WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, nome);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                user.setCodigo(banco.getInt("codigo"));
                user.setNome(banco.getString("nome"));
                user.setLogin(banco.getString("usuario"));
                user.setFuncao(banco.getString("tipo"));

            }
            gui.close();
            banco.close();
            return user;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void deletarUsuario(Usuario controle) throws BancoException {
        sql = "DELETE FROM USUARIO where CODIGO = ?";
        try {
            PreparedStatement control = conexao.prepareStatement(sql);

            control.setInt(1, controle.getCodigo());

            control.execute(); // se não precisar resgatar dados
            control.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void atualizaDados(Usuario cliente) throws BancoException {
        sql = "UPDATE USUARIO SET NOME = ?, USUARIO = ?, SENHA = ?,"
                + " TIPO = ? WHERE CODIGO = ? ";
        try {
            PreparedStatement guiAlterar = conexao.prepareStatement(sql);
            guiAlterar.setString(1, cliente.getNome());
            guiAlterar.setString(2, cliente.getLogin());
            guiAlterar.setString(3, cliente.getSenha());
            guiAlterar.setString(4, cliente.getFuncao());
            guiAlterar.setInt(5, cliente.getCodigo());

            guiAlterar.execute();
            guiAlterar.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ResultSet Autenticando(String Mac) throws BancoException {
        sql = "SELECT * FROM licenciado where Mac = '" + Mac + "'";
        ResultSet rs;
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    /*
     * public void MudandoData(String Data){ sql = "UPDATE licenciado set
     * DataAtual = ? WHERE Senha = ?";
     *
     * try { PreparedStatement gui = conexao.prepareStatement(sql);
     * gui.setString(1, Data); gui.setString(2, "4master2012software01");
     *
     * gui.execute(); } catch (SQLException ex) {
     * Logger.getLogger(FuncionariosDAO.class.getName()).log(Level.SEVERE, null,
     * ex); } }
     *
     */
    /*
     * public int DiasUtilizados(String DataInicio,String DataFim){ sql =
     * "SELECT DATEDIFF('"+DataFim+"','"+DataInicio+"')"; int Dias =0; try {
     * ResultSet Dias1= conexao.createStatement().executeQuery(sql);
     * Dias1.first(); Dias = Dias1.getInt(1); } catch (SQLException ex) {
     * Logger.getLogger(FuncionariosDAO.class.getName()).log(Level.SEVERE, null,
     * ex); } return Dias; }
     *
     */
    /*
     * public void BloqueandoSistema() { sql = "UPDATE licenciado set Senha = ?
     * WHERE Codigo >= ?";
     *
     * try { PreparedStatement gui = conexao.prepareStatement(sql);
     * gui.setString(1, "Bloqueado"); gui.setInt(2, 1);
     *
     * gui.execute(); } catch (SQLException ex) {
     * Logger.getLogger(FuncionariosDAO.class.getName()).log(Level.SEVERE, null,
     * ex); } }
     *
     */
    public void InserindoUsuario(String nome, String DataPagamento, String DataAtual) throws BancoException {
        sql = "INSERT INTO licenciado (Nome,DataPagamento,DataAtual,Senha) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement imperio = conexao.prepareStatement(sql);
            imperio.setString(1, nome);
            imperio.setString(2, DataPagamento);
            imperio.setString(3, DataAtual);
            imperio.setString(4, "4master2012software01");

            imperio.execute(); // se não precisar resgatar dados
            imperio.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    /*
     * public void MudandoDataPagamento(String Data) { sql = "UPDATE licenciado
     * set DataPagamento = ? WHERE Codigo >= '" + 1 + "'";
     *
     * try { PreparedStatement gui = conexao.prepareStatement(sql);
     * gui.setString(1, Data);
     *
     * gui.execute(); } catch (SQLException ex) {
     * Logger.getLogger(FuncionariosDAO.class.getName()).log(Level.SEVERE, null,
     * ex); } }
     *
     */
    public ResultSet Verificando() throws BancoException {
        sql = "SELECT * FROM licenciado";
        ResultSet banco;
        try {
            banco = conexao.createStatement().executeQuery(sql);


        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return banco;
    }

    public ResultSet Autentic() throws BancoException {
        sql = "SELECT * FROM licenciado where Senha = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, "4master2012software01");

            ResultSet banco = gui.executeQuery();


            return banco;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
