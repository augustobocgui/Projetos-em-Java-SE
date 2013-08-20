/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesDAO;

import Classes.Caixa;
import Classes.EntradaCaixa;
import Classes.SaidaCaixa;
import Excessoes.BancoException;
import br.com.seeds.*;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class CaixaDAO {

    Connection conexao;
    private String sql;
    static ResultSet rs;
    String DataFinal, s, f;

    public CaixaDAO() throws BancoException {
        conexao = (Connection) new FazerConexao().fazerConexaoBanco();
        NowString();
    }

    public void desconectar() {
        try {
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(TurmasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexao = null;
    }
    
    public void OperandoCaixa(Caixa caixa) throws BancoException {
        sql = "INSERT INTO AberturaFechamento (Data,Hora,Operacao) VALUES (?,?,?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, caixa.getData());
            gui.setString(2, caixa.getHora());
            gui.setString(3, caixa.getOperacao());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void NowString() {
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        DateFormat dg = DateFormat.getTimeInstance();
        s = df.format(now);
        f = dg.format(now);
    }

    public void ExcluirSaida(String Nome, int Codigo) {
        sql = "delete from saidacaixa WHERE Operacao = ? and Codigo = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, Nome);
            gui.setInt(2, Codigo);

            gui.execute();
            gui.close();
        } catch (SQLException ex) {
            Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean VerificandoValor(float Saida) throws BancoException, SQLException, ParseException {

        VerificaAbertura(s, f);
        ResultSet SelecionandoEntradas = SelecaoEntrada("");
        ResultSet SelecionandoValorInicial = SelecaoValorInicial("");
        ResultSet SelecionandoSaidas = SelecaoSaida("");
        float Entradas = 0;
        float ValorInicial = 0;
        float Saidas = 0;
        while (SelecionandoEntradas.next()) {
            Entradas = Entradas + SelecionandoEntradas.getFloat("Valor");
        }

        while (SelecionandoValorInicial.next()) {
            ValorInicial = SelecionandoValorInicial.getFloat("Valor");
        }

        while (SelecionandoSaidas.next()) {
            Saidas = Saidas + SelecionandoSaidas.getFloat("Valor");
        }

        float TotalCaixa = (ValorInicial + Entradas) - Saidas;
        SelecionandoEntradas.close();
        SelecionandoSaidas.close();
        SelecionandoValorInicial.close();
        if (TotalCaixa - Saida >= 0) {
            return false;
        } else {
            return true;
        }
    }

    public void ExcluirEntrada(String Nome, int Codigo) {
        sql = "delete from entradacaixa WHERE Operacao = ? and Codigo = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, Nome);
            gui.setInt(2, Codigo);

            gui.execute();
            gui.close();
        } catch (SQLException ex) {
            Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet VerificaAbertura(String Data, String Hora) throws ParseException {
        sql = "SELECT * FROM AberturaFechamento where Data='" + Data + "' and Operacao='" + "Abertura" + "' ";

        try {
            boolean entrou = false;
            Date date = null;
            rs = conexao.createStatement().executeQuery(sql);
            while (rs.next()) {
                entrou = true;
            }
            Hora = Hora.replace(":", "");
            int HoraFinal = Integer.parseInt(Hora);
            //System.out.println(HoraFinal);
            if (!entrou && HoraFinal <= 60000) {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(Data);
                String DataFinal = new SimpleDateFormat("yyyy-MM-dd").format(date);
                sql = "SELECT INTERVAL -1 DAY + '" + DataFinal + "'";
                rs = conexao.createStatement().executeQuery(sql);
                rs.first();
                String UltimaData = rs.getString(1);
                //System.out.println(UltimaData);
                date = new SimpleDateFormat("yyyy-MM-dd").parse(UltimaData);
                String Enviar = new SimpleDateFormat("dd/MM/yyyy").format(date);
                //System.out.println(Enviar);
                this.DataFinal = Enviar;
                sql = "SELECT * FROM AberturaFechamento where Data ='" + Enviar + "' and Operacao='" + "Abertura" + "' ";
                rs = conexao.createStatement().executeQuery(sql);

            } else {
                //System.out.println(Data);
                sql = "SELECT * FROM AberturaFechamento where Data='" + Data + "' and Operacao='" + "Abertura" + "' ";
                rs = conexao.createStatement().executeQuery(sql);
                DataFinal = Data;
            }
        } catch (SQLException ex) {
            int selection = JOptionPane.showConfirmDialog(null,
                    "Não foi possível estabelecer conexão com o Mysql.\n"
                    + "Deseja configurar a conexão?",
                    "Erro de Conexão - CAIXA DIÁRIO", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.OK_OPTION) {
                new ConfiguracoesServidor(null, true).setVisible(true);
            }
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    public void ValorInicial(String Data, float Valor, int CodigoAbertura, String Hora) throws BancoException {
        sql = "INSERT INTO ValorInicial (Data,Valor,CodigoFornecedor,Hora) VALUES (?,?,?,?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, Data);
            gui.setFloat(2, Valor);
            gui.setInt(3, CodigoAbertura);
            gui.setString(4, Hora);

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ResultSet SelecaoValorInicial(String Data) throws BancoException {
        sql = "SELECT * FROM ValorInicial where Data='" + DataFinal + "'";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public void InserindoEntrada(EntradaCaixa caixa) throws BancoException {
        sql = "INSERT INTO entradacaixa (Data,Hora,Operacao,CodigoProduto,Valor,CodigoAbertura,Quantidade) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, DataFinal);
            gui.setString(2, caixa.getHora());
            gui.setString(3, caixa.getOperacao());
            if (caixa.getCodigoProduto() != 0) {
                gui.setInt(4, caixa.getCodigoProduto());
            } else {
                gui.setString(4, null);
            }
            gui.setFloat(5, caixa.getValor());
            gui.setInt(6, caixa.getCodigoAbertura());
            gui.setInt(7, caixa.getQuantidade());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void InserindoEntradaContrato(EntradaCaixa caixa) throws BancoException {
        sql = "INSERT INTO entradacaixa (Data,Hora,Operacao,CodigoProduto,Valor,CodigoAbertura,Quantidade) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, DataFinal);
            gui.setString(2, caixa.getHora());
            gui.setString(3, caixa.getOperacao());
            if (caixa.getCodigoProduto() != 0) {
                gui.setInt(4, caixa.getCodigoProduto());
            } else {
                gui.setString(4, null);
            }
            gui.setFloat(5, caixa.getValor());
            gui.setInt(6, caixa.getCodigoAbertura());
            gui.setInt(7, caixa.getQuantidade());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public void InserindoEntradaExcluida(String Operacao, int code, float Valor,
            int CodigoAbertura, int qtde) throws BancoException {
        sql = "INSERT INTO entradacaixa_excluido (Data,Hora,Operacao,"
                + "CodigoProduto,Valor,CodigoAbertura,Quantidade) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, DataFinal);
            gui.setString(2, f);
            gui.setString(3, Operacao);
            gui.setInt(4, code);
            gui.setFloat(5, Valor);
            gui.setInt(6, CodigoAbertura);
            gui.setInt(7, qtde);


            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ResultSet SelecaoEntrada(String Data) throws BancoException {
        sql = "SELECT * FROM entradacaixa where Data='" + DataFinal + "' ";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public ResultSet SelecionandoEstoque(int Codigo) throws BancoException {
        sql = "SELECT * FROM subprodutos where Codigo='" + Codigo + "' and Retirada = '" + 1 + "'";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public void SubtraindoEstoque(int Codigo, int Quantidade) {
        sql = "UPDATE subprodutos set Quantidade = Quantidade - ? WHERE  Codigo =  ?";

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, Quantidade);
            gui.setInt(2, Codigo);

            gui.execute();
            gui.close();
        } catch (SQLException ex) {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DevolucaoEstoque(int Codigo, int Quantidade) {
        sql = "UPDATE subprodutos set Quantidade = ? WHERE  Codigo =  ?";

        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, Quantidade);
            gui.setInt(2, Codigo);

            gui.execute();
            gui.close();
        } catch (SQLException ex) {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void InserindoEntradaSimples(EntradaCaixa caixa) throws BancoException {
        sql = "INSERT INTO entradacaixa (Data,Hora,Operacao,Valor,CodigoAbertura,Quantidade) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, DataFinal);
            gui.setString(2, caixa.getHora());
            gui.setString(3, caixa.getOperacao());
            gui.setFloat(4, caixa.getValor());
            gui.setInt(5, caixa.getCodigoAbertura());
            gui.setInt(6, caixa.getQuantidade());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ResultSet TabelaEntrada(String Data) throws ParseException {
        ResultSet Novo = null;
        NowString();
        VerificaAbertura(Data, f);
        //System.out.println(DataFinal);
        sql = "SELECT * FROM entradacaixa where Data = '" + DataFinal + "' ";
        try {
            Novo = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            try {
                throw new BancoException("" + e);
            } catch (BancoException ex) {
                Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Novo;
    }

    public void InserindoSaida(SaidaCaixa caixa) throws BancoException {
        sql = "INSERT INTO saidacaixa (Data,Hora,Operacao,Valor,CodigoAbertura) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, DataFinal);
            gui.setString(2, caixa.getHora());
            gui.setString(3, caixa.getOperacao());
            gui.setFloat(4, caixa.getValor());
            gui.setInt(5, caixa.getCodigoAbertura());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ResultSet SelecaoSaida(String Data) throws BancoException {
        sql = "SELECT * FROM saidacaixa where Data='" + DataFinal + "'";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public ResultSet TabelaSaida(String Data) {
        ResultSet Novo = null;
        sql = "SELECT * FROM saidacaixa where Data = '" + DataFinal + "' ";
        try {
            Novo = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            try {
                throw new BancoException("" + e);
            } catch (BancoException ex) {
                Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Novo;
    }

    public void OperandoCaixaFechamento(Caixa caixa) throws BancoException {
        sql = "INSERT INTO AberturaFechamento (Data,Operacao,Hora) VALUES (?,?,?)";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, DataFinal);
            gui.setString(2, caixa.getOperacao());
            gui.setString(3, caixa.getHora());

            gui.execute();
            gui.close();
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }

    public ResultSet VerificaFechamento(String Data) {
        sql = "SELECT * FROM AberturaFechamento where Data='" + DataFinal + "' and Operacao='" + "Fechamento" + "' ";

        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    public ResultSet VerificaVenda(String Data) {
        sql = "SELECT * FROM AberturaFechamento where Data='" + DataFinal + "' and Operacao='" + "Fechamento" + "' ";

        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    public ResultSet VerificaFechamento(String Data, String Hora) throws ParseException {
        sql = "SELECT * FROM AberturaFechamento where Data='" + Data + "' and Operacao='" + "Fechamento" + "' ";

        try {
            boolean entrou = false;
            Date date = null;
            rs = conexao.createStatement().executeQuery(sql);
            while (rs.next()) {
                entrou = true;
            }
            Hora = Hora.replace(":", "");
            int HoraFinal = Integer.parseInt(Hora);
            //System.out.println(HoraFinal);
            if (!entrou && HoraFinal <= 60000) {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(Data);
                String DataFinal = new SimpleDateFormat("yyyy-MM-dd").format(date);
                sql = "SELECT INTERVAL -1 DAY + '" + DataFinal + "'";
                rs = conexao.createStatement().executeQuery(sql);
                rs.first();
                String UltimaData = rs.getString(1);
                //System.out.println(UltimaData);
                date = new SimpleDateFormat("yyyy-MM-dd").parse(UltimaData);
                String Enviar = new SimpleDateFormat("dd/MM/yyyy").format(date);
                //System.out.println(Enviar);
                this.DataFinal = Enviar;
                sql = "SELECT * FROM AberturaFechamento where Data ='" + Enviar + "' and Operacao='" + "Fechamento" + "' ";
                rs = conexao.createStatement().executeQuery(sql);

            } else {
                sql = "SELECT * FROM AberturaFechamento where Data='" + Data + "' and Operacao='" + "Fechamento" + "' ";
                rs = conexao.createStatement().executeQuery(sql);
                DataFinal = Data;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    public void deletandoFechamento(String Hora, String Data) throws BancoException, ParseException {
        sql = "SELECT * FROM AberturaFechamento where Data='" + Data + "' and Operacao='" + "Fechamento" + "' ";

        try {
            boolean entrou = false;
            Date date = null;
            rs = conexao.createStatement().executeQuery(sql);
            while (rs.next()) {
                entrou = true;
            }
            Hora = Hora.replace(":", "");
            int HoraFinal = Integer.parseInt(Hora);
            //System.out.println(HoraFinal);
            if (!entrou && HoraFinal <= 60000) {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(Data);
                String DataFinal = new SimpleDateFormat("yyyy-MM-dd").format(date);
                sql = "SELECT INTERVAL -1 DAY + '" + DataFinal + "'";
                rs = conexao.createStatement().executeQuery(sql);
                rs.first();
                String UltimaData = rs.getString(1);
                //System.out.println(UltimaData);
                date = new SimpleDateFormat("yyyy-MM-dd").parse(UltimaData);
                String Enviar = new SimpleDateFormat("dd/MM/yyyy").format(date);
                //System.out.println(Enviar + "xxxx");

                this.DataFinal = Enviar;
                sql = "DELETE FROM AberturaFechamento where Data='" + Enviar + "' and Operacao='" + "Fechamento" + "' ";
                PreparedStatement control = conexao.prepareStatement(sql);

                control.execute();
                control.close();
            } else {
                sql = "DELETE FROM AberturaFechamento where Data='" + Data + "' and Operacao='" + "Fechamento" + "' ";
                PreparedStatement control = conexao.prepareStatement(sql);

                control.execute();
                control.close();

                DataFinal = Data;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet SelecionandoSubProdutos(int Codigo) throws BancoException {
        sql = "SELECT * FROM subprodutos where Codigo='" + Codigo + "'";
        try {
            rs = conexao.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
        return rs;
    }

    public EntradaCaixa SelecionandoSubProdutosDeletarCaixa(int Codigo) throws BancoException {
        EntradaCaixa fornecedor = new EntradaCaixa();
        fornecedor.setData("nulo");
        sql = "SELECT * FROM ENTRADACAIXA WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setInt(1, Codigo);
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornecedor.setCodigoProduto(banco.getInt("CodigoProduto"));

            }
            gui.close();
            banco.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
    /*
     * public EntradaCaixa carregarEntradaCaixaPeloCodigo(int codigo) throws
     * BancoException { EntradaCaixa fornecedor = new EntradaCaixa();
     * fornecedor.setData("nulo"); sql = "SELECT * FROM ENTRADACAIXA WHERE
     * CODIGO = ?"; try { PreparedStatement gui = conexao.prepareStatement(sql);
     * gui.setInt(1, codigo); ResultSet banco = gui.executeQuery(); while
     * (banco.next()) // só entra se existir {
     * fornecedor.setCodigoProduto(banco.getInt("CodigoProduto"));
     *
     * }
     * gui.close(); banco.close(); return fornecedor; } catch (SQLException e) {
     * throw new BancoException("" + e); } }
     *
     */

    public EntradaCaixa carregarEntradaCaixa(String nome) throws BancoException {
        EntradaCaixa fornecedor = new EntradaCaixa();
        fornecedor.setData("nulo");
        sql = "SELECT * FROM ENTRADACAIXA WHERE CODIGO = ?";
        try {
            PreparedStatement gui = conexao.prepareStatement(sql);
            gui.setString(1, nome.toString());
            ResultSet banco = gui.executeQuery();
            while (banco.next()) // só entra se existir 
            {
                fornecedor.setCodigoAbertura(banco.getInt("Codigo"));
                fornecedor.setCodigoProduto(banco.getInt("CodigoProduto"));

            }
            banco.close();
            gui.close();
            return fornecedor;
        } catch (SQLException e) {
            throw new BancoException("" + e);
        }
    }
}
