/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CaixaInterface.java
 *
 * Created on 21/11/2011, 15:52:36
 */
package br.com.seeds;

import Classes.*;
import ClassesDAO.*;
import Excessoes.BancoException;
import emailaplicativoseeds.Carteiro;
import emailaplicativoseeds.Mensagem;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.EmailException;
import seeds.Main;

/**
 *
 * @author Guilherme
 */
public class CaixaInterface extends javax.swing.JFrame {

    /**
     * Creates new form CaixaInterface
     */
    boolean aberto = false;
    String s, f;
    float TotalEmCaixa = 0, TotalEntrada = 0, TotalSaida = 0;
    DefaultTableModel modelo;
    CaixaDAO dao;
    int Focar = 0;
    int CodigoProdutoExcluido;
    private Mensagem mensagens;
    String destinatario;
    String assunto;
    String msg;
    int local = 0;

    public CaixaInterface() throws BancoException, ParseException, SQLException {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/br/com/imagens/CaixaFrame.png")).getImage());
        jPanel4.setVisible(false);
        jBVenda.requestFocus();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);//Abri o Frame Maximizado
        dao = new CaixaDAO();
        AoIniciar();
        jTextField3.setDocument(new LimitadorMoeda());
        jTextField4.setDocument(new LimitadorMoeda());
        jTextField5.setDocument(new LimitadorMoeda());
        jLabel7.setVisible(false);
        jButton3.requestFocus();
        if (!jButton2.isEnabled()) {
            jBVenda.setEnabled(false);
            jBExcluirOperacao.setEnabled(false);
        }
        NowString();
        try {
            LocalidadeDAO dao = new LocalidadeDAO();
            local = dao.gerarCodigoLocalidade();
            local--;
            dao.desconectar();
        } catch (BancoException f) {
            f.printStackTrace();
        }
    }

    public void NowString() {
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        DateFormat dg = DateFormat.getTimeInstance();
        s = df.format(now);
        f = dg.format(now);
        jBVenda.requestFocus();
    }

    public void Tabela() throws ParseException {
        jBVenda.requestFocus();
        int ii = 0;
        modelo = new DefaultTableModel() {

            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        String a[][];

        a = new String[7000][5];

        modelo.addColumn("Código");
        modelo.addColumn("Ação");
        modelo.addColumn("Hora");
        modelo.addColumn("Valor");
        modelo.addColumn("Quantidade");

        int pos = 1;
        // testa se a posi??o ? v?lida

        if (pos > (modelo.getRowCount() - 1)) {
            pos = 0;
        }

        try {

            ResultSet gui1 = null;

            gui1 = dao.TabelaEntrada(s);
            while (gui1.next()) {
                a[ii][0] = (gui1.getString("Codigo"));
                a[ii][1] = (gui1.getString("Operacao"));
                a[ii][2] = (gui1.getString("Hora"));
                a[ii][3] = (gui1.getString("Valor"));
                a[ii][4] = (gui1.getString("Quantidade"));
                ii++;
            }
            gui1.close();

            gui1 = dao.TabelaSaida(s);
            while (gui1.next()) {
                a[ii][0] = (gui1.getString("Codigo"));
                a[ii][1] = (gui1.getString("Operacao"));
                a[ii][2] = (gui1.getString("Hora"));
                a[ii][3] = (gui1.getString("Valor"));
                a[ii][4] = ("" + 1);
                ii++;
            }
            gui1.close();

            dao.TabelaEntrada(s).close();
        } catch (SQLException ex) {
            Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < ii; i++) {
            modelo.insertRow(pos, new Object[]{a[i][0], a[i][1], a[i][2], a[i][3], a[i][4]});
            jTable1.setModel(modelo);
        }
        jTable1.setDefaultRenderer(Object.class, new CellRenderer());

    }

    public void AoIniciar() throws ParseException, BancoException, SQLException {
        TotalEmCaixa = 0;
        TotalSaida = 0;
        TotalEntrada = 0;
        try {
            CaixaDAO Caixa = new CaixaDAO();
            NowString();
            ResultSet rs = Caixa.VerificaAbertura(s, f);
            float ValorInicial = 0;
            boolean Verificando = false;
            while (rs.next()) {
                Verificando = true;
                aberto = true;
            }
            rs.close();
            Tabela();
            //Caso Verificando foir verdade, o caixa está aberto no dia corrente
            if (Verificando) {
                jLabel4.setForeground(Color.BLUE);
                jLabel5.setForeground(Color.BLUE);
                jLabel6.setForeground(Color.BLUE);
                jTextField3.setEnabled(true);
                jTextField4.setEnabled(true);
                jTextField5.setEnabled(true);
                jTextField3.setText("0000");
                jTextField4.setText("0000");
                jTextField5.setText("0000");
                jLabel8.setEnabled(true);
                jLabel9.setEnabled(true);
                jLabel10.setEnabled(true);
                ResultSet PuxandoValorInicial = Caixa.SelecaoValorInicial(s);
                while (PuxandoValorInicial.next()) {
                    ValorInicial = PuxandoValorInicial.getFloat("Valor");
                }
                PuxandoValorInicial.close();

                ResultSet PuxandoEntrada = Caixa.SelecaoEntrada(s);
                while (PuxandoEntrada.next()) {
                    TotalEntrada = TotalEntrada + (PuxandoEntrada.getFloat("Valor") * PuxandoEntrada.getInt("Quantidade"));
                }
                PuxandoEntrada.close();

                ResultSet PuxandoSaida = Caixa.SelecaoSaida(s);
                while (PuxandoSaida.next()) {
                    TotalSaida = TotalSaida + (PuxandoSaida.getFloat("Valor"));
                }
                PuxandoSaida.close();

                TotalEmCaixa = (ValorInicial + TotalEntrada) - TotalSaida;
                String Valor = new DecimalFormat("0.00").format(TotalEmCaixa);
                Valor = Valor.replace(".", "");
                Valor = Valor.replace(",", "");

                String ValorEntrada = new DecimalFormat("0.00").format(TotalEntrada);
                ValorEntrada = ValorEntrada.replace(".", "");
                ValorEntrada = ValorEntrada.replace(",", "");

                String ValorSaida = new DecimalFormat("0.00").format(TotalSaida);
                ValorSaida = ValorSaida.replace(".", "");
                ValorSaida = ValorSaida.replace(",", "");

                jTextField3.setText(Valor);
                jTextField4.setText(ValorEntrada);
                jTextField5.setText(ValorSaida);

                //Verificando se caixa está fechado
                ResultSet rs1 = Caixa.VerificaFechamento(s);
                while (rs1.next()) {
                    Verificando = true;
                    jLabel4.setEnabled(false);
                    jLabel5.setEnabled(false);
                    jLabel6.setEnabled(false);
                    jLabel8.setEnabled(false);
                    jLabel9.setEnabled(false);
                    jLabel10.setEnabled(false);
                    jLabel7.setVisible(true);
                    jTable1.setEnabled(false);
                    jButton2.setEnabled(false);
                    jButton1.setEnabled(false);
                    jButton3.setEnabled(false);
                    jButton4.setEnabled(false);
                    jTextField4.setEnabled(false);
                    jTextField5.setEnabled(false);
                    jTextField3.setEnabled(false);
                }
                Tabela();
                rs1.close();

            }
            Caixa.desconectar();
        } catch (BancoException ex) {
            Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        jBVenda.requestFocus();
    }

    public Mensagem lerMensagem() {
        Mensagem mensagem = new Mensagem();
        mensagem.setDestinatario(destinatario);
        mensagem.setAssunto(assunto);
        mensagem.setMensagem(msg);
        return mensagem;
    }

    public void exibirMsgSucesso() {
        //System.out.println("\n===============================");
        //System.out.println("Mensagem enviada com sucesso!!!");
        //System.out.println("===============================");
    }

    public void exibirMsgErro(String msg) {
        //System.out.println("\n*** Ops! Não foi possível enviar a mensagem ***");
        //System.out.println("\nMotivo: \n" + msg);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jBExcluirOperacao = new javax.swing.JButton();
        jBVenda = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtUsuario = new javax.swing.JTextField();
        txtSenha = new javax.swing.JPasswordField();
        jBAutentificarExcluir = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setTitle("Caixa");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setForeground(new java.awt.Color(153, 153, 153));

        jLabel2.setFont(new java.awt.Font("Bitstream Vera Sans Mono", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("                              Caixa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Ação", "Valor", "Hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel4.setText("Entrada:  ");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel5.setText("Saída:");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel6.setText("Total em caixa:");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Caixa.png"))); // NOI18N
        jButton3.setText("    Abrir caixa");
        jButton3.setToolTipText("");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/FecharCaixa.png"))); // NOI18N
        jButton4.setText("    Fechar caixa");
        jButton4.setToolTipText("");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Suprimento.png"))); // NOI18N
        jButton2.setText("    Entrada de caixa");
        jButton2.setToolTipText("Suprimento");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Sangria.png"))); // NOI18N
        jButton1.setText("    Saída de caixa");
        jButton1.setToolTipText("Sangria");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/ReabrirCaixa.png"))); // NOI18N
        jButton5.setText("    Reabrir caixa");
        jButton5.setToolTipText("");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jBExcluirOperacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/CaixaRemoveOperacao.png"))); // NOI18N
        jBExcluirOperacao.setText("    Excluir Operação");
        jBExcluirOperacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBExcluirOperacao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBExcluirOperacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExcluirOperacaoActionPerformed(evt);
            }
        });

        jBVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/venda2.png"))); // NOI18N
        jBVenda.setText("    Venda");
        jBVenda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBVenda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBExcluirOperacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBVenda, jButton3});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBExcluirOperacao, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBVenda, jButton3});

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel8.setText("R$");
        jLabel8.setEnabled(false);

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel9.setText("R$");
        jLabel9.setEnabled(false);

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel10.setText("R$");
        jLabel10.setEnabled(false);

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/FecharCaixa.png"))); // NOI18N
        jLabel7.setText("Caixa Fechado!");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(null);

        txtUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });
        jPanel4.add(txtUsuario);
        txtUsuario.setBounds(10, 190, 173, 30);

        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });
        jPanel4.add(txtSenha);
        txtSenha.setBounds(10, 230, 173, 30);

        jBAutentificarExcluir.setBackground(new java.awt.Color(255, 255, 255));
        jBAutentificarExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/set.PNG"))); // NOI18N
        jBAutentificarExcluir.setToolTipText("Autentica e reabre o caixa");
        jBAutentificarExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAutentificarExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAutentificarExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(jBAutentificarExcluir);
        jBAutentificarExcluir.setBounds(183, 230, 30, 30);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jButton7.setContentAreaFilled(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7);
        jButton7.setBounds(0, 0, 50, 30);

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/LoginMetro3.png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel4.add(jLabel1);
        jLabel1.setBounds(0, 0, 430, 320);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 7, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1073)/2, (screenSize.height-711)/2, 1073, 711);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Clicou em abrir
        if (!aberto) {
            AberturaCaixa Abrindo = new AberturaCaixa();
            this.dispose();
            Abrindo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Caixa já está aberto! ");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Clicou em entrada em caixa
        if (aberto) {
            Main.Caixa.setVisible(false);
            CaixaEntrada Entrada;
            try {
                Entrada = new CaixaEntrada(0);
                Entrada.setVisible(aberto);
            } catch (BancoException ex) {
                Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // apertou enter
        int Evento = evt.getKeyCode();
        if (Evento == 10) {
            if (aberto) {
                Main.Caixa.setVisible(false);
                CaixaEntrada Entrada;
                try {
                    Entrada = new CaixaEntrada(0);
                    Entrada.setVisible(aberto);
                } catch (BancoException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Clicou em saida de caixa
        if (aberto) {
            Main.Caixa.setVisible(false);
            CaixaSaida Saida;
            try {
                Saida = new CaixaSaida();
                Saida.setVisible(aberto);
            } catch (BancoException ex) {
                Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Clicou em fechar o caixa
        if (aberto) {
            Object[] options = {"Sim", "Não"};
            int n = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja FECHAR o caixa?",
                    "Confirmação", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (n == 0) {
                Caixa caixa = new Caixa();
                caixa.setData(s);
                caixa.setHora(f);
                caixa.setOperacao("Fechamento");

                try {
                    dao.OperandoCaixaFechamento(caixa);
                } catch (BancoException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.dispose();
                try {
                    CaixaInterface Caixa = new CaixaInterface();
                    Main.Caixa.AoIniciar();
                    Caixa.setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                }

                int selection = JOptionPane.showConfirmDialog(null,
                        "Fechamento do caixa realizado com sucesso!\n"
                        + "Deseja realizar Backup agora?",
                        "Caixa Fechado!", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    new RotinasDeSeguranca().setVisible(true);
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "O caixa não está aberto!");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // Reabrir caixa
        SolicitaçãoSenha sol = new SolicitaçãoSenha();
        sol.setVisible(true);
        this.dispose();
        Main.Caixa.setVisible(false);

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jBExcluirOperacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExcluirOperacaoActionPerformed
        jPanel4.setVisible(true);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);//Abri o Frame Maximizado
    }//GEN-LAST:event_jBExcluirOperacaoActionPerformed

    private void jBVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVendaActionPerformed
        CaixaInterface.this.dispose();
        // Clicou em entrada em caixa
        if (aberto) {
            CaixaEntradaVenda Entrada;
            try {
                try {
                    Entrada = new CaixaEntradaVenda(0);
                    Entrada.setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (BancoException ex) {
                Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jBVendaActionPerformed

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        if (Focar == 0) {
            jBVenda.requestFocus();
            Focar = 2;
        }
    }//GEN-LAST:event_jPanel1MouseEntered

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            jPanel4.setVisible(false);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // autenticar
            NowString();
            if (txtUsuario.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Usuário tem de ser preenchido!");
                txtUsuario.requestFocus();
            } else if (txtSenha.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Senha tem de ser preenchida!");
                txtSenha.requestFocus();
            } else {
                String senhaMD5 = md5(txtSenha.getText());
                try {
                    UsuarioDAO daos = new UsuarioDAO();
                    Usuario Verificando;
                    CaixaDAO caixa = new CaixaDAO();
                    boolean Entrou = false;

                    Verificando = daos.carregarUsuario(txtUsuario.getText());
                    if (Verificando.getSenha().equals(senhaMD5) && Verificando.getTipo().equals("Administrador")) {

                        // Excluir Operação
                        int x = jTable1.getSelectedRow();

                        String Operacao = "" + jTable1.getValueAt(x, 1);
                        String Valor = "" + jTable1.getValueAt(x, 3);
                        int Codigo = Integer.parseInt("" + jTable1.getValueAt(x, 0));

                        Object[] options = {"Sim", "Não"};
                        int n = JOptionPane.showOptionDialog(this,
                                "Tem certeza que deseja Excluir a operação: \n    Operação:    " + Operacao + "\n    Valor:    " + Valor + "    ??",
                                "Confirmação", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (n == 0) {
                            if (!(Operacao.equals("Mesas - Entrada") || Operacao.equals("Abertura"))) {

                                EntradaCaixa cliente = new EntradaCaixa();

                                CaixaDAO daoss = new CaixaDAO(); // estou instanciando a conexão
                                cliente = daoss.SelecionandoSubProdutosDeletarCaixa(Codigo);

                                CodigoProdutoExcluido = cliente.getCodigoProduto();

                                String mesalidade = "" + jTable1.getValueAt(x, 1);
                                //JOptionPane.showMessageDialog(rootPane, CodigoProdutoExcluido);
                                //JOptionPane.showMessageDialog(rootPane, Codigo);
                                //JOptionPane.showMessageDialog(rootPane, mesalidade);
                                //INICIO ATUALIZAR CONTAS RECEBER

                                if (mesalidade.equals("Mensalidade")) {
                                    //JOptionPane.showMessageDialog(rootPane, mesalidade);
                                    try {
                                        ContasRDAO daosssr = new ContasRDAO(); // estou instanciando a conexão
                                        ContasR clientess = new ContasR();

                                        //daosssr = dao.carregarContasContratoPeloCodigo(CodigoProdutoExcluido);

                                        //EntradaCaixa cliente = new EntradaCaixa();
                                        //CaixaDAO daoss = new CaixaDAO(); // estou instanciando a conexão
                                        //cliente = daoss.SelecionandoSubProdutosDeletarCaixa(Codigo);
                                        //CodigoProdutoExcluido = cliente.getCodigoProduto();

                                        clientess.setCodigo(CodigoProdutoExcluido);
                                        clientess.setValor_pago(Double.parseDouble("0"));

                                        daosssr.atualizaDados(clientess);
                                        daosssr.desconectar();
                                    } catch (Exception ex) {
                                        Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    JOptionPane.showMessageDialog(this,
                                            "Cancelamento da mensaldade efetuada com sucesso!");

                                }
                                //FIM ATUALIZAR CONTAS RECEBER

                                //FIM ATUALIZAR PRODUTOS
                                if (mesalidade.equals("Venda")) {
                                    int quantidade = Integer.parseInt("" + jTable1.getValueAt(x, 4));
                                    dao.carregarEntradaCaixa("" + jTable1.getValueAt(x, 0));
                                    int codigoProduto = cliente.getCodigoProduto();
                                    int Quantidade = 0;
                                    ResultSet Verificando1 = dao.SelecionandoEstoque(codigoProduto);

                                    try {
                                        while (Verificando1.next()) {
                                            Quantidade = Verificando1.getInt("Quantidade");
                                            Quantidade = Quantidade + quantidade;
                                        }
                                        dao.DevolucaoEstoque(codigoProduto, Quantidade);
                                        Verificando1.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    JOptionPane.showMessageDialog(this,
                                            "Cancelamento da Venda efetuada com sucesso!");
                                }
                                //FIM ATUALIZAR PRODUTOS

                                String qtde = "" + jTable1.getValueAt(x, 4);
                                dao.InserindoEntradaExcluida(Operacao, CodigoProdutoExcluido,
                                        Float.parseFloat(Valor), Codigo,
                                        Integer.parseInt(qtde));

                                dao.ExcluirSaida(Operacao, Codigo);
                                dao.ExcluirEntrada(Operacao, Codigo);

                                //JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
                                Main.Caixa.dispose();
                                try {
                                    Main.Caixa = new CaixaInterface();
                                    Main.Caixa.AoIniciar();
                                    Main.Caixa.setVisible(true);
                                } catch (BancoException ex) {
                                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ParseException ex) {
                                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Operação não realizada!");
                            }
                        }
                        txtUsuario.setText("");
                        txtSenha.setText("");
                        jPanel4.setVisible(false);


                        AcessoDAO daou = new AcessoDAO();
                        Acesso okay = new Acesso();
                        //Verificando = daos.carregarUsuario(txtUsuario.getText());
                        NowString();
                        okay.setUsuario(Verificando.getCodigo());
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Acesso a exclusão efetuado [Interface - Caixa]\nCódigo " + Codigo + " Operação " + Operacao + " Valor " + Valor + " ");
                        daou.adicionarAcesso(okay);
                        daou.desconectar();
                        //IMFORMAR POR EMAIL
                        destinatario = "seedsescola@gmail.com";
                        assunto = "Financeiro";
                        msg = "Acesso a exclusão efetuado [Interface - Caixa]\n"
                                + "\nCódigo abertura: " + Codigo + "\nOperação: " + Operacao + "\nValor: " + Valor + " "
                                + "\nNome do usuário: " + Verificando.getNome().trim() + "\n"
                                + "Data: " + s + "\n"
                                + "Hora: " + f + "\n"
                                + "Código usuário: " + Verificando.getCodigo() + "\n"
                                + "Local: " + local + "";
                        mensagens = new Mensagem();
                        mensagens.setDestinatario(destinatario);
                        mensagens.setAssunto(assunto);
                        mensagens.setMensagem(msg);
                        Mensagem mensagem = mensagens;
                        Carteiro carteiro = new Carteiro();
                        try {
                            carteiro.enviarMensagem(mensagem);
                            exibirMsgSucesso();
                        } catch (EmailException e) {
                            exibirMsgErro(e.getMessage());
                        }
                        //EMAIL FIM

                    } else {
                        AcessoDAO daou = new AcessoDAO();
                        Acesso okay = new Acesso();
                        Verificando = daos.carregarUsuario(txtUsuario.getText());
                        NowString();
                        okay.setUsuario(Verificando.getCodigo());
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Acesso a exclusão não efetuado [Interface - Caixa]\nAcesso não concedido!");
                        daou.adicionarAcesso(okay);
                        daou.desconectar();
                        JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                        txtUsuario.setText("");
                        txtSenha.setText("");
                    }
                    daos.desconectar();
                } catch (BancoException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            jPanel4.setVisible(false);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // autenticar
            NowString();
            if (txtUsuario.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Usuário tem de ser preenchido!");
                txtUsuario.requestFocus();
            } else if (txtSenha.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Senha tem de ser preenchida!");
                txtSenha.requestFocus();
            } else {
                String senhaMD5 = md5(txtSenha.getText());
                try {
                    UsuarioDAO daos = new UsuarioDAO();
                    Usuario Verificando;
                    CaixaDAO caixa = new CaixaDAO();
                    boolean Entrou = false;

                    Verificando = daos.carregarUsuario(txtUsuario.getText());
                    if (Verificando.getSenha().equals(senhaMD5) && Verificando.getTipo().equals("Administrador")) {

                        // Excluir Operação
                        int x = jTable1.getSelectedRow();

                        String Operacao = "" + jTable1.getValueAt(x, 1);
                        String Valor = "" + jTable1.getValueAt(x, 3);
                        int Codigo = Integer.parseInt("" + jTable1.getValueAt(x, 0));

                        Object[] options = {"Sim", "Não"};
                        int n = JOptionPane.showOptionDialog(this,
                                "Tem certeza que deseja Excluir a operação: \n    Operação:    " + Operacao + "\n    Valor:    " + Valor + "    ??",
                                "Confirmação", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (n == 0) {
                            if (!(Operacao.equals("Mesas - Entrada") || Operacao.equals("Abertura"))) {

                                EntradaCaixa cliente = new EntradaCaixa();

                                CaixaDAO daoss = new CaixaDAO(); // estou instanciando a conexão
                                cliente = daoss.SelecionandoSubProdutosDeletarCaixa(Codigo);

                                CodigoProdutoExcluido = cliente.getCodigoProduto();

                                String mesalidade = "" + jTable1.getValueAt(x, 1);
                                //JOptionPane.showMessageDialog(rootPane, CodigoProdutoExcluido);
                                //JOptionPane.showMessageDialog(rootPane, Codigo);
                                //JOptionPane.showMessageDialog(rootPane, mesalidade);
                                //INICIO ATUALIZAR CONTAS RECEBER

                                if (mesalidade.equals("Mensalidade")) {
                                    //JOptionPane.showMessageDialog(rootPane, mesalidade);
                                    try {
                                        ContasRDAO daosssr = new ContasRDAO(); // estou instanciando a conexão
                                        ContasR clientess = new ContasR();

                                        //daosssr = dao.carregarContasContratoPeloCodigo(CodigoProdutoExcluido);

                                        //EntradaCaixa cliente = new EntradaCaixa();
                                        //CaixaDAO daoss = new CaixaDAO(); // estou instanciando a conexão
                                        //cliente = daoss.SelecionandoSubProdutosDeletarCaixa(Codigo);
                                        //CodigoProdutoExcluido = cliente.getCodigoProduto();

                                        clientess.setCodigo(CodigoProdutoExcluido);
                                        clientess.setValor_pago(Double.parseDouble("0"));

                                        daosssr.atualizaDados(clientess);
                                    } catch (Exception ex) {
                                        Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    JOptionPane.showMessageDialog(this,
                                            "Cancelamento da mensaldade efetuada com sucesso!");

                                }
                                //FIM ATUALIZAR CONTAS RECEBER

                                //FIM ATUALIZAR PRODUTOS
                                if (mesalidade.equals("Venda")) {
                                    int quantidade = Integer.parseInt("" + jTable1.getValueAt(x, 4));
                                    dao.carregarEntradaCaixa("" + jTable1.getValueAt(x, 0));
                                    int codigoProduto = cliente.getCodigoProduto();
                                    int Quantidade = 0;
                                    ResultSet Verificando1 = dao.SelecionandoEstoque(codigoProduto);

                                    try {
                                        while (Verificando1.next()) {
                                            Quantidade = Verificando1.getInt("Quantidade");
                                            Quantidade = Quantidade + quantidade;
                                        }
                                        dao.DevolucaoEstoque(codigoProduto, Quantidade);
                                        Verificando1.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    JOptionPane.showMessageDialog(this,
                                            "Cancelamento da Venda efetuada com sucesso!");
                                }
                                //FIM ATUALIZAR PRODUTOS

                                String qtde = "" + jTable1.getValueAt(x, 4);
                                dao.InserindoEntradaExcluida(Operacao, CodigoProdutoExcluido,
                                        Float.parseFloat(Valor), Codigo,
                                        Integer.parseInt(qtde));

                                dao.ExcluirSaida(Operacao, Codigo);
                                dao.ExcluirEntrada(Operacao, Codigo);

                                //JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
                                Main.Caixa.dispose();
                                try {
                                    Main.Caixa = new CaixaInterface();
                                    Main.Caixa.AoIniciar();
                                    Main.Caixa.setVisible(true);
                                } catch (BancoException ex) {
                                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ParseException ex) {
                                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Operação não realizada!");
                            }
                        }
                        txtUsuario.setText("");
                        txtSenha.setText("");
                        jPanel4.setVisible(false);


                        AcessoDAO daou = new AcessoDAO();
                        Acesso okay = new Acesso();
                        //Verificando = daos.carregarUsuario(txtUsuario.getText());
                        NowString();
                        okay.setUsuario(Verificando.getCodigo());
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Acesso a exclusão efetuado [Interface - Caixa]\nCódigo " + Codigo + " Operação " + Operacao + " Valor " + Valor + " ");
                        daou.adicionarAcesso(okay);
                        daou.desconectar();
                        //IMFORMAR POR EMAIL
                        destinatario = "seedsescola@gmail.com";
                        assunto = "Financeiro";
                        msg = "Acesso a exclusão efetuado [Interface - Caixa]\n"
                                + "\nCódigo abertura: " + Codigo + "\nOperação: " + Operacao + "\nValor: " + Valor + " "
                                + "\nNome do usuário: " + Verificando.getNome().trim() + "\n"
                                + "Data: " + s + "\n"
                                + "Hora: " + f + "\n"
                                + "Código usuário: " + Verificando.getCodigo() + "\n"
                                + "Local: " + local + "";
                        mensagens = new Mensagem();
                        mensagens.setDestinatario(destinatario);
                        mensagens.setAssunto(assunto);
                        mensagens.setMensagem(msg);
                        Mensagem mensagem = mensagens;
                        Carteiro carteiro = new Carteiro();
                        try {
                            carteiro.enviarMensagem(mensagem);
                            exibirMsgSucesso();
                        } catch (EmailException e) {
                            exibirMsgErro(e.getMessage());
                        }
                        //EMAIL FIM

                    } else {
                        AcessoDAO daou = new AcessoDAO();
                        Acesso okay = new Acesso();
                        Verificando = daos.carregarUsuario(txtUsuario.getText());
                        NowString();
                        okay.setUsuario(Verificando.getCodigo());
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Acesso a exclusão não efetuado [Interface - Caixa]\nAcesso não concedido!");
                        daou.adicionarAcesso(okay);
                        daou.desconectar();
                        JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                        txtUsuario.setText("");
                        txtSenha.setText("");
                    }
                    daos.desconectar();
                } catch (BancoException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void jBAutentificarExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAutentificarExcluirActionPerformed
        // autenticar
        NowString();
        if (txtUsuario.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Usuário tem de ser preenchido!");
            txtUsuario.requestFocus();
        } else if (txtSenha.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Senha tem de ser preenchida!");
            txtSenha.requestFocus();
        } else {
            String senhaMD5 = md5(txtSenha.getText());
            try {
                UsuarioDAO daos = new UsuarioDAO();
                Usuario Verificando;
                CaixaDAO caixa = new CaixaDAO();
                boolean Entrou = false;


                Verificando = daos.carregarUsuario(txtUsuario.getText());
                if (Verificando.getSenha().equals(senhaMD5) && Verificando.getTipo().equals("Administrador")) {



                    // Excluir Operação
                    int x = jTable1.getSelectedRow();

                    String Operacao = "" + jTable1.getValueAt(x, 1);
                    String Valor = "" + jTable1.getValueAt(x, 3);
                    int Codigo = Integer.parseInt("" + jTable1.getValueAt(x, 0));

                    Object[] options = {"Sim", "Não"};
                    int n = JOptionPane.showOptionDialog(this,
                            "Tem certeza que deseja Excluir a operação: \n    Operação:    " + Operacao + "\n    Valor:    " + Valor + "    ??",
                            "Confirmação", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    if (n == 0) {
                        if (!(Operacao.equals("Mesas - Entrada") || Operacao.equals("Abertura"))) {

                            EntradaCaixa cliente = new EntradaCaixa();

                            CaixaDAO daoss = new CaixaDAO(); // estou instanciando a conexão
                            cliente = daoss.SelecionandoSubProdutosDeletarCaixa(Codigo);

                            CodigoProdutoExcluido = cliente.getCodigoProduto();

                            String mesalidade = "" + jTable1.getValueAt(x, 1);
                            //JOptionPane.showMessageDialog(rootPane, CodigoProdutoExcluido);
                            //JOptionPane.showMessageDialog(rootPane, Codigo);
                            //JOptionPane.showMessageDialog(rootPane, mesalidade);
                            //INICIO ATUALIZAR CONTAS RECEBER

                            if (mesalidade.equals("Mensalidade")) {
                                //JOptionPane.showMessageDialog(rootPane, mesalidade);
                                try {
                                    ContasRDAO daosssr = new ContasRDAO(); // estou instanciando a conexão
                                    ContasR clientess = new ContasR();

                                    //daosssr = dao.carregarContasContratoPeloCodigo(CodigoProdutoExcluido);

                                    //EntradaCaixa cliente = new EntradaCaixa();
                                    //CaixaDAO daoss = new CaixaDAO(); // estou instanciando a conexão
                                    //cliente = daoss.SelecionandoSubProdutosDeletarCaixa(Codigo);
                                    //CodigoProdutoExcluido = cliente.getCodigoProduto();

                                    clientess.setCodigo(CodigoProdutoExcluido);
                                    clientess.setValor_pago(Double.parseDouble("0"));

                                    daosssr.atualizaDados(clientess);
                                    daosssr.desconectar();
                                } catch (Exception ex) {
                                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                JOptionPane.showMessageDialog(this,
                                        "Cancelamento da mensaldade efetuada com sucesso!");

                            }
                            //FIM ATUALIZAR CONTAS RECEBER

                            //FIM ATUALIZAR PRODUTOS
                            if (mesalidade.equals("Venda")) {
                                int quantidade = Integer.parseInt("" + jTable1.getValueAt(x, 4));
                                dao.carregarEntradaCaixa("" + jTable1.getValueAt(x, 0));
                                int codigoProduto = cliente.getCodigoProduto();
                                int Quantidade = 0;
                                ResultSet Verificando1 = dao.SelecionandoEstoque(codigoProduto);

                                try {
                                    while (Verificando1.next()) {
                                        Quantidade = Verificando1.getInt("Quantidade");
                                        Quantidade = Quantidade + quantidade;
                                    }
                                    dao.DevolucaoEstoque(codigoProduto, Quantidade);
                                    Verificando1.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                JOptionPane.showMessageDialog(this,
                                        "Cancelamento da Venda efetuada com sucesso!");
                            }
                            //FIM ATUALIZAR PRODUTOS

                            String qtde = "" + jTable1.getValueAt(x, 4);
                            dao.InserindoEntradaExcluida(Operacao, CodigoProdutoExcluido,
                                    Float.parseFloat(Valor), Codigo,
                                    Integer.parseInt(qtde));

                            dao.ExcluirSaida(Operacao, Codigo);
                            dao.ExcluirEntrada(Operacao, Codigo);

                            //JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
                            Main.Caixa.dispose();
                            try {
                                Main.Caixa = new CaixaInterface();
                                Main.Caixa.AoIniciar();
                                Main.Caixa.setVisible(true);
                            } catch (BancoException ex) {
                                Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ParseException ex) {
                                Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Operação não realizada!");
                        }
                    }
                    txtUsuario.setText("");
                    txtSenha.setText("");
                    jPanel4.setVisible(false);

                    AcessoDAO daou = new AcessoDAO();
                    Acesso okay = new Acesso();
                    //Verificando = daos.carregarUsuario(txtUsuario.getText());
                    NowString();
                    okay.setUsuario(Verificando.getCodigo());
                    okay.setData(s);
                    okay.setHora(f);
                    okay.setDescricao("Acesso a exclusão efetuado [Interface - Caixa]\nCódigo " + Codigo + " Operação " + Operacao + " Valor " + Valor + " ");
                    daou.adicionarAcesso(okay);
                    daou.desconectar();
                    //IMFORMAR POR EMAIL
                    destinatario = "seedsescola@gmail.com";
                    assunto = "Financeiro";
                    msg = "Acesso a exclusão efetuado [Interface - Caixa]\n"
                            + "\nCódigo abertura: " + Codigo + "\nOperação: " + Operacao + "\nValor: " + Valor + " "
                            + "\nNome do usuário: " + Verificando.getNome().trim() + "\n"
                            + "Data: " + s + "\n"
                            + "Hora: " + f + "\n"
                            + "Código usuário: " + Verificando.getCodigo() + "\n"
                            + "Local: " + local + "";
                    mensagens = new Mensagem();
                    mensagens.setDestinatario(destinatario);
                    mensagens.setAssunto(assunto);
                    mensagens.setMensagem(msg);
                    Mensagem mensagem = mensagens;
                    Carteiro carteiro = new Carteiro();
                    try {
                        carteiro.enviarMensagem(mensagem);
                        exibirMsgSucesso();
                    } catch (EmailException e) {
                        exibirMsgErro(e.getMessage());
                    }
                    //EMAIL FIM


                } else {
                    AcessoDAO daou = new AcessoDAO();
                    Acesso okay = new Acesso();
                    Verificando = daos.carregarUsuario(txtUsuario.getText());
                    NowString();
                    okay.setUsuario(Verificando.getCodigo());
                    okay.setData(s);
                    okay.setHora(f);
                    okay.setDescricao("Acesso a exclusão não efetuado [Interface - Caixa]\nAcesso não concedido!");
                    daou.adicionarAcesso(okay);
                    daou.desconectar();
                    JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                    txtUsuario.setText("");
                    txtSenha.setText("");
                }
                daos.desconectar();
            } catch (BancoException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jBAutentificarExcluirActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Sair
        txtUsuario.setText("");
        txtSenha.setText("");
        jPanel4.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAutentificarExcluir;
    private javax.swing.JButton jBExcluirOperacao;
    private javax.swing.JButton jBVenda;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    public static String md5(String senha) {
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        sen = hash.toString(16);
        return sen;
    }
}
