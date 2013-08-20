/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import Classes.Cliente;
import Classes.EntradaCaixa;
import Classes.LimitadorMoeda;
import ClassesDAO.CaixaDAO;
import ClassesDAO.ClientesDAO;
import ClassesDAO.LocalidadeDAO;
import ClassesDAO.SubProdutosDAO;
import Excessoes.BancoException;
import emailaplicativoseeds.Carteiro;
import emailaplicativoseeds.Mensagem;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.mail.EmailException;
import seeds.Main;

/**
 *
 * @author Guilherme
 */
public class CaixaEntradaVenda extends javax.swing.JFrame {

    /**
     * Creates new form CaixaEntrada
     */
    String s, f;
    EntradaCaixa SetandoEntrada = new EntradaCaixa();
    CaixaDAO dao;
    public ClientesDAO daos;
    public Cliente cliente = new Cliente();
    boolean Abertofinal = false;
    StringBuffer nomes = new StringBuffer();
    int apagar = 0;
    private Mensagem mensagens;
    String destinatario;
    String assunto;
    String msg;

    public CaixaEntradaVenda(int Codigo) throws BancoException, SQLException, ParseException {
        initComponents();
        setLocationRelativeTo(null);
        jCheckBox2.setVisible(false);
        dao = new CaixaDAO();
        jPanel3.setVisible(false);
        txtCodigoProduto.setDocument(new OnlyNumberField(6));
        txtQuantidade.setDocument(new OnlyNumberField(6));
        txtValorAPagar.setDocument(new LimitadorMoeda());
        jTextField2.setDocument(new LimitadorMoeda());
        jTextField1.setDocument(new FixedLengthDocumentx(49));
        if (Codigo > 0) {
            SubProdutosDAO Sub = new SubProdutosDAO();
            ResultSet SubP = Sub.SelecionandoSubprodutos(Codigo);

            while (SubP.next()) {
                jCheckBox1.setSelected(true);
                txtNomeProduto.setText(SubP.getString("Nome"));
                txtValorAPagar.setText(SubP.getString("PrecoVenda").replace(".", "").replace(",", ""));
                txtCodigoProduto.setText("" + Codigo);
                txtQuantidade.requestFocus();
            }
            SubP.close();
            txtQuantidade.requestFocus();
        }
        NowString();
        try {
            LocalidadeDAO dao = new LocalidadeDAO();
            txtCodigoLocalidade.setText(String.valueOf(dao.gerarCodigoLocalidade() - 1));
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

    public void valorPorExtenso(double vlr) {

        if (vlr == 0) //return("zero");
        {
            jBFinalizar.setToolTipText("Zero");
        }

        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) //return("Erro: valor superior a 999 trilhões.");
        {
            jBFinalizar.setToolTipText("Erro: valor superior a 999 trilhões.");
        }

        String svalor = "", saux, vlrP;
        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "três", "quatro", "cinco",
            "seis", "sete", "oito", "nove", "dez", "onze",
            "doze", "treze", "quatorze", "quinze", "dezesseis",
            "dezessete", "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
            "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
            "sessenta", "setenta", "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
        String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

// definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
// 1a. parte = 789 (centena)
// 2a. parte = 456 (mil)
// 3a. parte = 123 (milhões)
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else { // última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = "cem";
                } else {
                    n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
                    cent = n / 100;                  // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
                    unid = (n % 100) % 10;           // unid = 1 (unidade um)
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0) {
                            saux = saux + " e " + unidade[n % 100];
                        } else {
                            saux = unidade[n % 100];
                        }
                    } else {
                        if (saux.length() != 0) {
                            saux = saux + " e " + dezena[dez];
                        } else {
                            saux = dezena[dez];
                        }
                        if (unid != 0) {
                            if (saux.length() != 0) {
                                saux = saux + " e " + unidade[unid];
                            } else {
                                saux = unidade[unid];
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (svalor.length() != 0) {
                    svalor = saux + ", " + svalor;
                } else {
                    svalor = saux;
                }
            }
            if (((i == 0) || (i == 1)) && svalor.length() != 0) {
                tem = true; // tem centena ou mil no valor
            }
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
        }

        if (svalor.length() != 0) {
            if (umReal) {
                svalor = svalor + " real";
            } else if (tem) {
                svalor = svalor + " reais";
            } else {
                svalor = svalor + " de reais";
            }
        }

// definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (svalor.length() != 0) // se não é valor somente com centavos
            {
                svalor = svalor + " e ";
            }
            if (centavos.equals("1")) {
                svalor = svalor + "um centavo";
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    svalor = svalor + unidade[n];
                } else {             // para n = 37, tem-se:
                    unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
                    svalor = svalor + dezena[dez];
                    if (unid != 0) {
                        svalor = svalor + " e " + unidade[unid];
                    }
                }
                svalor = svalor + " centavos";
            }
        }

        //return(svalor);
        jBFinalizar.setToolTipText(svalor);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtValorAPagar = new javax.swing.JTextField();
        txtQuantidade = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jBFinalizar = new javax.swing.JButton();
        jBSair = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtValorSugerido = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTotalSemDesconto = new javax.swing.JTextField();
        txtTotalComDesconto = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jBBuscar = new javax.swing.JButton();
        txtNomeDoCliente = new javax.swing.JTextField();
        jSpinnerDescontoPorcentagem = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jRadioButtonSim = new javax.swing.JRadioButton();
        jRadioButtonNao = new javax.swing.JRadioButton();
        txtCodigoLocalidade = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRadioBNaoImprimir = new javax.swing.JRadioButton();
        jRBImpressoraPadrao = new javax.swing.JRadioButton();
        jRadioBJasperIreport = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        jBBuscarProduto = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCodigoProduto = new javax.swing.JTextField();
        txtNomeProduto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Venda");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("* Valor unitário:");

        txtValorAPagar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtValorAPagar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorAPagar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorAPagarFocusLost(evt);
            }
        });

        txtQuantidade.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtQuantidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQuantidadeFocusLost(evt);
            }
        });
        txtQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQuantidadeKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("* Quantidade:");

        jBFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jBFinalizar.setText("Finalizar");
        jBFinalizar.setToolTipText("");
        jBFinalizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFinalizarActionPerformed(evt);
            }
        });
        jBFinalizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBFinalizarKeyPressed(evt);
            }
        });

        jBSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jBSair.setText("Sair");
        jBSair.setToolTipText("Fechará a tela funcionários e voltará ao menu principal");
        jBSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSairActionPerformed(evt);
            }
        });

        jLabel5.setText("Valor Total sem desconto:");
        jLabel5.setFocusable(false);

        jLabel10.setText("Valor Total com Desconto:");
        jLabel10.setFocusable(false);

        txtValorSugerido.setBackground(new java.awt.Color(255, 255, 204));
        txtValorSugerido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtValorSugerido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorSugerido.setToolTipText("Valor sugerido com relação a taxa de desconto.");
        txtValorSugerido.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtValorSugerido.setEnabled(false);
        txtValorSugerido.setFocusable(false);

        jLabel13.setText("Valor unitário com desconto:");
        jLabel13.setFocusable(false);

        txtTotalSemDesconto.setBackground(new java.awt.Color(255, 255, 204));
        txtTotalSemDesconto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTotalSemDesconto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalSemDesconto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalSemDesconto.setEnabled(false);
        txtTotalSemDesconto.setFocusable(false);

        txtTotalComDesconto.setBackground(new java.awt.Color(255, 255, 204));
        txtTotalComDesconto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTotalComDesconto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalComDesconto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotalComDesconto.setEnabled(false);
        txtTotalComDesconto.setFocusable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Ver sugestão de desconto salva para o Clientes"));
        jPanel2.setFocusable(false);

        jBBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jBBuscar.setBorderPainted(false);
        jBBuscar.setContentAreaFilled(false);
        jBBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscar.setFocusable(false);
        jBBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarActionPerformed(evt);
            }
        });

        txtNomeDoCliente.setEditable(false);
        txtNomeDoCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomeDoCliente.setFocusable(false);

        jSpinnerDescontoPorcentagem.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        jSpinnerDescontoPorcentagem.setFocusable(false);

        jLabel2.setText("Desconto (%)");
        jLabel2.setFocusable(false);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Calcular.png"))); // NOI18N
        jButton8.setText("Calcular");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.setFocusable(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel11.setText("Aplicar desconto na venda?");
        jLabel11.setFocusable(false);

        jRadioButtonSim.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(jRadioButtonSim);
        jRadioButtonSim.setText("Sim");
        jRadioButtonSim.setFocusable(false);

        jRadioButtonNao.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(jRadioButtonNao);
        jRadioButtonNao.setText("Não");
        jRadioButtonNao.setFocusable(false);

        txtCodigoLocalidade.setFocusable(false);

        jLabel6.setText("Local :");
        jLabel6.setFocusable(false);

        jRadioBNaoImprimir.setBackground(new java.awt.Color(255, 255, 255));
        jRadioBNaoImprimir.setSelected(true);
        jRadioBNaoImprimir.setText("Não imprimir");

        jRBImpressoraPadrao.setBackground(new java.awt.Color(255, 255, 255));
        jRBImpressoraPadrao.setText("Impressora padrão");

        jRadioBJasperIreport.setBackground(new java.awt.Color(255, 255, 255));
        jRadioBJasperIreport.setText("Jasper");
        jRadioBJasperIreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioBJasperIreportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jBBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(txtNomeDoCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(12, 12, 12)
                        .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerDescontoPorcentagem, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jRadioBJasperIreport, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRBImpressoraPadrao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioBNaoImprimir))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonSim)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonNao)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNomeDoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jBBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jRadioButtonSim)
                            .addComponent(jRadioButtonNao))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioBJasperIreport)
                            .addComponent(jRadioBNaoImprimir)
                            .addComponent(jRBImpressoraPadrao)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSpinnerDescontoPorcentagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jButton8))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jBFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBSair, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(33, 33, 33)
                                .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtValorAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(txtValorSugerido, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTotalComDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTotalSemDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(75, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtTotalComDesconto, txtTotalSemDesconto, txtValorSugerido});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtQuantidade, txtValorAPagar});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel13, jLabel5});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorSugerido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTotalSemDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTotalComDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBFinalizar, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jBSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtTotalComDesconto, txtTotalSemDesconto, txtValorSugerido});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtQuantidade, txtValorAPagar});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel13, jLabel5});

        jPanel1.add(jPanel5);
        jPanel5.setBounds(10, 110, 680, 390);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel1.setText("* Descrição:");

        jLabel3.setText("* Valor:");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField2KeyPressed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jButton2.setText("Finalizar");
        jButton2.setToolTipText("Concluir um pagamento e Inativar o funcionário");
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

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jButton4.setText("Sair");
        jButton4.setToolTipText("Fechará a tela funcionários e voltará ao menu principal");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText("*Quantidade :");

        jTextField3.setText("1");
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(191, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel3, jLabel4});

        jPanel1.add(jPanel3);
        jPanel3.setBounds(10, 110, 670, 170);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar o produto"));

        jCheckBox2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jCheckBox2);
        jCheckBox2.setText("Simples");
        jCheckBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox2ItemStateChanged(evt);
            }
        });
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jCheckBox2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox2KeyPressed(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jCheckBox1);
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Venda de produto");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jCheckBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCheckBox1KeyPressed(evt);
            }
        });

        jBBuscarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Produtos - Verificar.png"))); // NOI18N
        jBBuscarProduto.setBorderPainted(false);
        jBBuscarProduto.setContentAreaFilled(false);
        jBBuscarProduto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscarProduto.setFocusable(false);
        jBBuscarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBBuscarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jBBuscarProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4);
        jPanel4.setBounds(10, 20, 150, 80);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel12.setText("* Campos obrigatórios!");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(0, 0, 129, 11);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("* Produto"));

        jLabel9.setText("* Código");

        txtCodigoProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoProdutoFocusLost(evt);
            }
        });
        txtCodigoProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoProdutoKeyPressed(evt);
            }
        });

        txtNomeProduto.setEditable(false);
        txtNomeProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomeProduto.setFocusable(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(txtNomeProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jPanel1.add(jPanel6);
        jPanel6.setBounds(170, 20, 520, 80);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-716)/2, (screenSize.height-546)/2, 716, 546);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Clicou em Finalizar

        if (jCheckBox2.isSelected()) {

            if (jTextField1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "O campo Descrição precisa ser preenchido!");
                jTextField1.requestFocus();
            } else if (jTextField2.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "O campo Valor precisa ser preenchido!");
                jTextField2.requestFocus();
            } else {
                NowString();
                int CodigoAbertura = 0;

                try {
                    ResultSet Verificando = dao.VerificaAbertura(s, f);
                    while (Verificando.next()) {
                        CodigoAbertura = Verificando.getInt("Codigo");
                    }
                    Verificando.close();

                } catch (SQLException ex) {
                    Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                }


                SetandoEntrada.setSimples(true);
                SetandoEntrada.setQuantidade(Integer.parseInt(jTextField3.getText()));
                String Valor = jTextField2.getText();
                Valor = Valor.replace(".", "");
                Valor = Valor.replace(",", ".");
                SetandoEntrada.setValor(Float.parseFloat(Valor));
                SetandoEntrada.setData(s);
                SetandoEntrada.setHora(f);
                SetandoEntrada.setOperacao(jTextField1.getText());
                SetandoEntrada.setCodigoAbertura(CodigoAbertura);

                try {
                    dao.InserindoEntradaSimples(SetandoEntrada);
                } catch (BancoException ex) {
                    Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                }



                JOptionPane.showMessageDialog(this, "Sucesso!");
                this.dispose();
                try {
                    try {
                        Main.Caixa.AoIniciar();
                    } catch (BancoException ex) {
                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.Caixa.setVisible(true);
            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Clicou em sair
        this.dispose();
        try {
            try {
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.Caixa.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jBFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFinalizarActionPerformed
        if (jRadioButtonSim.isSelected() && txtValorSugerido.getText().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Não foi gerado em valor com desconto!",
                    "",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // Clicou em finalizar
            if (jCheckBox1.isSelected()) {

                if (txtCodigoProduto.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "O campo Código do produto precisa ser preenchido!");
                    txtCodigoProduto.requestFocus();
                } else if (txtValorAPagar.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "O campo Valor precisa ser preenchido!");
                    txtValorAPagar.requestFocus();
                } else if (txtQuantidade.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "O campo Quantidade precisa ser preenchido!");
                    txtQuantidade.requestFocus();
                } else if (txtNomeProduto.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Produto não informado!");

                } else {



                    //IMPRESSÃO 
                    if (jRadioBJasperIreport.isSelected()) {

                        try {
                            Connection con = new Conexao().getConnection();
                            HashMap parametros = new HashMap();
                            parametros.put("NOME", txtNomeProduto.getText().trim());
                            if (jRadioButtonSim.isSelected() == true) {
                                parametros.put("DINHEIRO", txtTotalComDesconto.getText());
                                valorPorExtenso(Double.parseDouble(txtTotalComDesconto.getText()));
                                parametros.put("DINHEIROEX", jBFinalizar.getToolTipText());
                            } else {
                                parametros.put("DINHEIRO", txtTotalSemDesconto.getText());
                                valorPorExtenso(Double.parseDouble(txtTotalSemDesconto.getText()));
                                parametros.put("DINHEIROEX", jBFinalizar.getToolTipText());
                            }

                            parametros.put("VALOR", txtValorAPagar.getText());
                            parametros.put("QUANTIDADE", txtQuantidade.getText());

                            // JasperPrint jp = JasperFillManager.fillReport("C:/Users/Guilherme/Documents/NetBeansProjects/shiftsoftlight/src/relatorios/Alunos.jasper", parametros, con);
                            //JasperPrint jp = JasperFillManager.fillReport("C:/Program Files/shiftsoftlight/relatorios/Alunos.jasper", parametros, con);
                            JasperPrint jp = JasperFillManager.fillReport("./relatorios/VendaImpressora.jasper", parametros, con);
                            JasperViewer jrv = new JasperViewer(jp, false);
                            jrv.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //IMPRESSÃO FIM

                    if (jRBImpressoraPadrao.isSelected()) {
                        if (jRadioButtonSim.isSelected() == true) {
                            valorPorExtenso(Double.parseDouble(txtTotalComDesconto.getText()));
                        } else {
                            valorPorExtenso(Double.parseDouble(txtTotalSemDesconto.getText()));
                        }
                        try {
                            String caminho = "../seeds-java/";
                            File file = new File(caminho + File.separator + "COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                            if (!file.exists()) {
                                System.out.println("arquivo não existe");
                                System.out.println("criando arquivo File.txt em..." + caminho);
                                try {
                                    //Aqui é o que falta  
                                    file.createNewFile();
                                } catch (IOException ex) {
                                    Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                System.out.println("arquivo COMPROVANTE DE PAGAMENTO - SEEDS.txt, criado em" + "caminho");
                            }

                            String Final = "";
                            Final = "_____________________________________________________________________________________";
                            Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                            Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO";
                            Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n   Produto: " + txtNomeProduto.getText() + " Quantidade: " + txtQuantidade.getText() + " Valor: R$ " + txtValorAPagar.getText() + " , ";
                            Final = Final + "\n   Total de " + jBFinalizar.getToolTipText() + ".";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                            Final = Final + "\n";
                            Final = Final + "\n 		_____________________________________________________";
                            Final = Final + "\n 			  Montes Claros - " + s + "";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                            Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n                                            -- 1ª Via --";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                            Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO";
                            Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n   Produto: " + txtNomeProduto.getText() + " Quantidade: " + txtQuantidade.getText() + " Valor: R$ " + txtValorAPagar.getText() + " , ";
                            Final = Final + "\n   Total de " + jBFinalizar.getToolTipText() + ".";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n";
                            Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                            Final = Final + "\n";
                            Final = Final + "\n 		_____________________________________________________";
                            Final = Final + "\n 			  Montes Claros - " + s + "";
                            Final = Final + "\n_____________________________________________________________________________________";
                            Final = Final + "\n Rua Padre Augusto, 335 - 2º Andar - Centro - Montes Claros / MG Fone: (38) 3221-6288";
                            Final = Final + "\n Rua Pires e Albuquerque, Nº. 282 - Centro - Montes Claros / MG Fone: (38) 3221-0828";
                            Final = Final + "\n_____________________________________________________________________________________";


                            BufferedWriter out = null;
                            try {
                                out = new BufferedWriter(new FileWriter("COMPROVANTE DE PAGAMENTO - SEEDS.txt"));
                            } catch (IOException ex) {
                                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            out.write(Final); // "" = quebra de linha no Windows
                            //System.out.println("Acabei de escrever no arquivo");
                            out.close();

                            File Emails = new File("COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                            try {
                                Desktop.getDesktop().print(Emails);
                            } catch (IOException ex) {
                                Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (Exception e) {
                        }
                    }



                    NowString();
                    int CodigoAbertura = 0;
                    int Quantidade = 0;
                    int QuantidadeCritica = 0;
                    boolean Continuar = false;
                    boolean Subtrair = false;

                    try {
                        ResultSet Verificando = dao.VerificaAbertura(s, f);
                        while (Verificando.next()) {
                            CodigoAbertura = Verificando.getInt("Codigo");
                        }
                        Verificando.close();
                        ResultSet Verificando1 = dao.SelecionandoEstoque(Integer.parseInt(txtCodigoProduto.getText()));

                        while (Verificando1.next()) {
                            Quantidade = Verificando1.getInt("Quantidade");
                            QuantidadeCritica = Verificando1.getInt("QuantidadeMin");
                            Subtrair = true;
                        }

                        Verificando1.close();

                        if (Quantidade <= QuantidadeCritica) {
                            JOptionPane.showMessageDialog(this, "A quantidade em estoque está abaixo do recomendado!",
                                    "Seeds", JOptionPane.WARNING_MESSAGE);

                            //IMFORMAR POR EMAIL
                            destinatario = "seedsescola@gmail.com";
                            assunto = "Estoque";
                            msg = "A quantidade em estoque está abaixo do recomendado [Interface - Venda]\n"
                                    + "\nNome de produto informado: " + txtNomeProduto.getText().trim() + "\n"
                                    + "Código do produto: " + txtCodigoProduto.getText().trim() + "\n"
                                    + "Data: " + s + "\n"
                                    + "Hora: " + f + "\n"
                                    + "Quantidade em estoque: " + Quantidade + "\n"
                                    + "Quantidade mínima: " + QuantidadeCritica + "\n"
                                    + "Local: " + txtCodigoLocalidade.getText().trim() + "\n";

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


                        }
                        if (Quantidade >= Integer.parseInt(txtQuantidade.getText()) && Subtrair) {
                            dao.SubtraindoEstoque(Integer.parseInt(txtCodigoProduto.getText()), Integer.parseInt(txtQuantidade.getText()));
                            Continuar = true;
                        } else if (Subtrair) {
                            JOptionPane.showMessageDialog(this, "A quantidade em estoque é menor que a informada!",
                                    "Seeds", JOptionPane.ERROR_MESSAGE);

                            //IMFORMAR POR EMAIL
                            destinatario = "seedsescola@gmail.com";
                            assunto = "Estoque";
                            msg = "A quantidade em estoque é menor que a informada [Interface - Venda]\n"
                                    + "\nNome de produto informado: " + txtNomeProduto.getText().trim() + "\n"
                                    + "Código do produto: " + txtCodigoProduto.getText().trim() + "\n"
                                    + "Data: " + s + "\n"
                                    + "Hora: " + f + "\n"
                                    + "Quantidade em estoque: " + Quantidade + "\n"
                                    + "Quantidade mínima: " + QuantidadeCritica + "\n"
                                    + "Quantidade à vender: " + txtQuantidade.getText().trim() + "\n"
                                    + "Local: " + txtCodigoLocalidade.getText().trim() + "\n";

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
                            Continuar = true;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BancoException ex) {
                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (Continuar) {
                        SetandoEntrada.setCodigoProduto(Integer.parseInt(txtCodigoProduto.getText()));
                        SetandoEntrada.setSimples(false);
                        SetandoEntrada.setProduto(txtNomeProduto.getText());
                        SetandoEntrada.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
                        String Valor = txtValorAPagar.getText();
                        Valor = Valor.replace(".", "");
                        Valor = Valor.replace(",", ".");
                        if (jRadioButtonSim.isSelected()) {
                            SetandoEntrada.setValor(Float.parseFloat(txtValorSugerido.getText()));
                        } else {
                            SetandoEntrada.setValor(Float.parseFloat(Valor));
                        }
                        SetandoEntrada.setData(s);
                        SetandoEntrada.setHora(f);
                        SetandoEntrada.setOperacao("Venda");
                        SetandoEntrada.setCodigoAbertura(CodigoAbertura);

                        try {
                            dao.InserindoEntrada(SetandoEntrada);

                        } catch (BancoException ex) {
                            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }



                        JOptionPane.showMessageDialog(this, "Sucesso!");
                        this.dispose();
                        try {
                            try {
                                Main.Caixa.AoIniciar();
                            } catch (BancoException ex) {
                                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Main.Caixa.setVisible(true);
                    }
                }
            }
        }
    }//GEN-LAST:event_jBFinalizarActionPerformed

    private void jBSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSairActionPerformed
        // Sair
        CaixaEntradaVenda.this.dispose();
    }//GEN-LAST:event_jBSairActionPerformed

    private void txtCodigoProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoProdutoFocusLost
        // Saiu Codigo
    }//GEN-LAST:event_txtCodigoProdutoFocusLost

    private void txtCodigoProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProdutoKeyPressed
        // clicou enter em codigo
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boolean Achou = false;
            try {
                ResultSet PuxandoProduto = dao.SelecionandoSubProdutos(Integer.parseInt(txtCodigoProduto.getText()));

                while (PuxandoProduto.next()) {
                    txtNomeProduto.setText(PuxandoProduto.getString("Nome"));
                    String ValorTotal = new DecimalFormat("0.00").format((PuxandoProduto.getFloat("PrecoVenda")));
                    ValorTotal = ValorTotal.replace(".", "");
                    ValorTotal = ValorTotal.replace(",", "");
                    txtValorAPagar.setText(ValorTotal);
                    txtQuantidade.requestFocus();
                    Achou = true;
                }
                PuxandoProduto.close();

                if (!Achou) {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado!");
                }
            } catch (BancoException ex) {
                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtCodigoProdutoKeyPressed

    private void jBFinalizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBFinalizarKeyPressed
        // Clicou enter Finalizar
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jRadioButtonSim.isSelected() && txtValorSugerido.getText().equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Não foi gerado em valor com desconto!",
                        "",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Clicou em finalizar
                if (jCheckBox1.isSelected()) {

                    if (txtCodigoProduto.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "O campo Código do produto precisa ser preenchido!");
                        txtCodigoProduto.requestFocus();
                    } else if (txtValorAPagar.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "O campo Valor precisa ser preenchido!");
                        txtValorAPagar.requestFocus();
                    } else if (txtQuantidade.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "O campo Quantidade precisa ser preenchido!");
                        txtQuantidade.requestFocus();
                    } else if (txtNomeProduto.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "Produto não informado!");

                    } else {
                        NowString();
                        int CodigoAbertura = 0;
                        int Quantidade = 0;
                        int QuantidadeCritica = 0;
                        boolean Continuar = false;
                        boolean Subtrair = false;

                        try {
                            ResultSet Verificando = dao.VerificaAbertura(s, f);
                            while (Verificando.next()) {
                                CodigoAbertura = Verificando.getInt("Codigo");
                            }
                            Verificando.close();
                            ResultSet Verificando1 = dao.SelecionandoEstoque(Integer.parseInt(txtCodigoProduto.getText()));

                            while (Verificando1.next()) {
                                Quantidade = Verificando1.getInt("Quantidade");
                                QuantidadeCritica = Verificando1.getInt("QuantidadeMin");
                                Subtrair = true;
                            }

                            Verificando1.close();

                            if (Quantidade <= QuantidadeCritica) {
                                JOptionPane.showMessageDialog(this, "A quantidade em estoque está abaixo do recomendado!",
                                        "Seeds", JOptionPane.WARNING_MESSAGE);

                                //IMFORMAR POR EMAIL
                                destinatario = "seedsescola@gmail.com";
                                assunto = "Estoque";
                                msg = "A quantidade em estoque está abaixo do recomendado [Interface - Venda]\n"
                                        + "\nNome de produto informado: " + txtNomeProduto.getText().trim() + "\n"
                                        + "Código do produto: " + txtCodigoProduto.getText().trim() + "\n"
                                        + "Data: " + s + "\n"
                                        + "Hora: " + f + "\n"
                                        + "Quantidade em estoque: " + Quantidade + "\n"
                                        + "Quantidade mínima: " + QuantidadeCritica + "\n"
                                        + "Local: " + txtCodigoLocalidade.getText().trim() + "\n";

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


                            }
                            if (Quantidade >= Integer.parseInt(txtQuantidade.getText()) && Subtrair) {
                                dao.SubtraindoEstoque(Integer.parseInt(txtCodigoProduto.getText()), Integer.parseInt(txtQuantidade.getText()));
                                Continuar = true;
                            } else if (Subtrair) {
                                JOptionPane.showMessageDialog(this, "A quantidade em estoque é menor que a informada!",
                                        "Seeds", JOptionPane.ERROR_MESSAGE);

                                //IMFORMAR POR EMAIL
                                destinatario = "seedsescola@gmail.com";
                                assunto = "Estoque";
                                msg = "A quantidade em estoque é menor que a informada [Interface - Venda]\n"
                                        + "\nNome de produto informado: " + txtNomeProduto.getText().trim() + "\n"
                                        + "Código do produto: " + txtCodigoProduto.getText().trim() + "\n"
                                        + "Data: " + s + "\n"
                                        + "Hora: " + f + "\n"
                                        + "Quantidade em estoque: " + Quantidade + "\n"
                                        + "Quantidade mínima: " + QuantidadeCritica + "\n"
                                        + "Quantidade à vender: " + txtQuantidade.getText().trim() + "\n"
                                        + "Local: " + txtCodigoLocalidade.getText().trim() + "\n";

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
                                Continuar = true;
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BancoException ex) {
                            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (Continuar) {
                            SetandoEntrada.setCodigoProduto(Integer.parseInt(txtCodigoProduto.getText()));
                            SetandoEntrada.setSimples(false);
                            SetandoEntrada.setProduto(txtNomeProduto.getText());
                            SetandoEntrada.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
                            String Valor = txtValorAPagar.getText();
                            Valor = Valor.replace(".", "");
                            Valor = Valor.replace(",", ".");
                            if (jRadioButtonSim.isSelected()) {
                                SetandoEntrada.setValor(Float.parseFloat(txtValorSugerido.getText()));
                            } else {
                                SetandoEntrada.setValor(Float.parseFloat(Valor));
                            }
                            SetandoEntrada.setData(s);
                            SetandoEntrada.setHora(f);
                            SetandoEntrada.setOperacao("Venda");
                            SetandoEntrada.setCodigoAbertura(CodigoAbertura);

                            try {
                                dao.InserindoEntrada(SetandoEntrada);

                            } catch (BancoException ex) {
                                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                            }



                            JOptionPane.showMessageDialog(this, "Sucesso!");
                            this.dispose();
                            try {
                                try {
                                    Main.Caixa.AoIniciar();
                                } catch (BancoException ex) {
                                    Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (ParseException ex) {
                                Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Main.Caixa.setVisible(true);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jBFinalizarKeyPressed

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
        // apertou enter em valor
        int evento;

        evento = evt.getKeyCode();
        if (evento == 10) {
            jTextField3.requestFocus();
        }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // apetar enter em finalizar simples
        int evento;

        evento = evt.getKeyCode();
        if (evento == 10) {
            if (jCheckBox2.isSelected()) {

                if (jTextField1.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "O campo Descrição precisa ser preenchido!");
                    jTextField1.requestFocus();
                } else if (jTextField2.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "O campo Valor precisa ser preenchido!");
                    jTextField2.requestFocus();
                } else {
                    NowString();
                    int CodigoAbertura = 90;



                    try {
                        ResultSet Verificando = dao.VerificaAbertura(s, f);
                        while (Verificando.next()) {
                            CodigoAbertura = Verificando.getInt("Codigo");
                        }
                        Verificando.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }


                    SetandoEntrada.setSimples(true);
                    SetandoEntrada.setQuantidade(1);
                    String Valor = jTextField2.getText();
                    Valor = Valor.replace(".", "");
                    Valor = Valor.replace(",", ".");
                    SetandoEntrada.setValor(Float.parseFloat(Valor));
                    SetandoEntrada.setData(s);
                    SetandoEntrada.setHora(f);
                    SetandoEntrada.setOperacao(jTextField1.getText());
                    SetandoEntrada.setCodigoAbertura(CodigoAbertura);

                    try {
                        dao.InserindoEntradaSimples(SetandoEntrada);
                    } catch (BancoException ex) {
                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }



                    JOptionPane.showMessageDialog(this, "Sucesso!");
                    this.dispose();
                    try {
                        try {
                            Main.Caixa.AoIniciar();
                        } catch (BancoException ex) {
                            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Main.Caixa.setVisible(true);
                }

            }
        }
    }//GEN-LAST:event_jButton2KeyPressed

    private void jTextField3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        // TODO add your handling code here:
        int Evento = evt.getKeyCode();
        if (Evento == 10) {
            jButton2.requestFocus();
        }
    }//GEN-LAST:event_jTextField3KeyPressed

    private void jCheckBox1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox1KeyPressed
        // enter em venda 
        int evento;

        evento = evt.getKeyCode();
        if (evento == 10) {
            jCheckBox2.setSelected(false);
            jCheckBox1.setSelected(true);
        }
    }//GEN-LAST:event_jCheckBox1KeyPressed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // Clicou em venda de produto
        if (jCheckBox1.isSelected()) {
            jPanel5.setVisible(true);
            jPanel3.setVisible(false);
            txtCodigoProduto.requestFocus();
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        // TODO add your handling code here:
        if (jCheckBox1.isSelected()) {
            jPanel5.setVisible(true);
            jPanel3.setVisible(false);
            txtCodigoProduto.requestFocus();
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jCheckBox2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCheckBox2KeyPressed
        // pressionou enter em simples
        int evento;

        evento = evt.getKeyCode();
        if (evento == 10) {
            jCheckBox2.setSelected(true);
            jCheckBox1.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox2KeyPressed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // Clicou em Simples
        if (jCheckBox2.isSelected()) {
            jPanel5.setVisible(false);
            jPanel3.setVisible(true);
            jTextField1.requestFocus();
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox2ItemStateChanged
        // TODO add your handling code here:
        if (jCheckBox2.isSelected()) {
            jPanel5.setVisible(false);
            jPanel3.setVisible(true);
            jTextField1.requestFocus();
        }
    }//GEN-LAST:event_jCheckBox2ItemStateChanged

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed

        final BuscarClientes pesq = new BuscarClientes("Clientes");
        pesq.setVisible(true);

        ActionListener acaoOk = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuffer codigoAluno = new StringBuffer();
                int codigoCliente = 0;
                codigoCliente = pesq.getCodigoCliente();
                codigoAluno.append(codigoCliente);
                apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nomes.delete(0, apagar);
                nomes.append(txtCodigoLocalidade.getText());
                try {
                    ClientesDAO daos = new ClientesDAO();
                    //Cliente cliente = new Cliente();
                    cliente = daos.carregarClientePeloCodigo(codigoAluno, nomes);
                    txtNomeDoCliente.setText(cliente.getNome());
                    jSpinnerDescontoPorcentagem.setValue(cliente.getDesconto());

                } catch (BancoException b) {
                    JOptionPane.showMessageDialog(null, e);
                }
                pesq.dispose();
            }
        };
        pesq.setAcao(acaoOk);

    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (txtValorAPagar.getText().equals("")) {
            //if (txtValorAPagar.getText().equals("") && jSpinner1.getValue().toString().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Valor e Desconto não pode ser vazio!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            float sugestao = 0;
            float ValorS = 0;

            String ValorSugerido = txtValorAPagar.getText();
            ValorSugerido = ValorSugerido.replace(".", "");
            ValorSugerido = ValorSugerido.replace(",", ".");

            ValorS = Float.parseFloat(ValorSugerido);

            sugestao = Float.parseFloat(jSpinnerDescontoPorcentagem.getValue().toString());
            sugestao = sugestao / 100;
            sugestao = sugestao * ValorS;
            ValorS = ValorS - sugestao;
            txtValorSugerido.setText("" + ValorS);

            if (!txtQuantidade.getText().equals("")) {
                ValorS = sugestao = 0;

                sugestao = Float.parseFloat(txtQuantidade.getText());
                ValorS = Float.parseFloat(txtValorSugerido.getText());
                ValorS *= sugestao;
                txtTotalComDesconto.setText(ValorS + "");
            }

        }

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jBBuscarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarProdutoActionPerformed
        try {
            // Clicou lupa
            BuscarSubprodutosCaixaVenda Busca = new BuscarSubprodutosCaixaVenda();
            Busca.setVisible(true);
            this.dispose();
        } catch (BancoException ex) {
            Logger.getLogger(CaixaEntradaVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBBuscarProdutoActionPerformed

    private void txtQuantidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantidadeKeyPressed
        // apertou enter
        int Apertou = evt.getKeyCode();
        if (Apertou == 10) {
            jBFinalizar.requestFocus();
        }
    }//GEN-LAST:event_txtQuantidadeKeyPressed

    private void txtQuantidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantidadeFocusLost
        if (!txtQuantidade.getText().equals("")) {
            String ValorTotal = txtValorAPagar.getText();
            ValorTotal = ValorTotal.replace(".", "");
            ValorTotal = ValorTotal.replace(",", ".");

            float ValorT = Float.parseFloat(ValorTotal);
            float Quantidade = Float.parseFloat(txtQuantidade.getText());
            ValorT *= Quantidade;
            txtTotalSemDesconto.setText(ValorT + "");
        }

        if (txtValorAPagar.getText().equals("")) {
            //if (txtValorAPagar.getText().equals("") && jSpinner1.getValue().toString().equals("")) {
            //JOptionPane.showMessageDialog(this, "Valor e Desconto não pode ser vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            float sugestao = 0;
            float ValorS = 0;

            String ValorSugerido = txtValorAPagar.getText();
            ValorSugerido = ValorSugerido.replace(".", "");
            ValorSugerido = ValorSugerido.replace(",", ".");

            ValorS = Float.parseFloat(ValorSugerido);

            sugestao = Float.parseFloat(jSpinnerDescontoPorcentagem.getValue().toString());
            sugestao = sugestao / 100;
            sugestao = sugestao * ValorS;
            ValorS = ValorS - sugestao;
            txtValorSugerido.setText("" + ValorS);

            if (!txtQuantidade.getText().equals("")) {
                ValorS = sugestao = 0;

                sugestao = Float.parseFloat(txtQuantidade.getText());
                ValorS = Float.parseFloat(txtValorSugerido.getText());
                ValorS *= sugestao;
                txtTotalComDesconto.setText(ValorS + "");
            }

        }
    }//GEN-LAST:event_txtQuantidadeFocusLost

    private void txtValorAPagarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorAPagarFocusLost
        if (txtValorAPagar.getText().equals("")) {
            //if (txtValorAPagar.getText().equals("") && jSpinner1.getValue().toString().equals("")) {
            //JOptionPane.showMessageDialog(this, "Valor e Desconto não pode ser vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            float sugestao = 0;
            float ValorS = 0;

            String ValorSugerido = txtValorAPagar.getText();
            ValorSugerido = ValorSugerido.replace(".", "");
            ValorSugerido = ValorSugerido.replace(",", ".");

            ValorS = Float.parseFloat(ValorSugerido);

            sugestao = Float.parseFloat(jSpinnerDescontoPorcentagem.getValue().toString());
            sugestao = sugestao / 100;
            sugestao = sugestao * ValorS;
            ValorS = ValorS - sugestao;
            txtValorSugerido.setText("" + ValorS);

            if (!txtQuantidade.getText().equals("")) {
                ValorS = sugestao = 0;

                sugestao = Float.parseFloat(txtQuantidade.getText());
                ValorS = Float.parseFloat(txtValorSugerido.getText());
                ValorS *= sugestao;
                txtTotalComDesconto.setText(ValorS + "");
            }

        }
    }//GEN-LAST:event_txtValorAPagarFocusLost

    private void jRadioBJasperIreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioBJasperIreportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioBJasperIreportActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBBuscarProduto;
    private javax.swing.JButton jBFinalizar;
    private javax.swing.JButton jBSair;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRBImpressoraPadrao;
    private javax.swing.JRadioButton jRadioBJasperIreport;
    private javax.swing.JRadioButton jRadioBNaoImprimir;
    private javax.swing.JRadioButton jRadioButtonNao;
    private javax.swing.JRadioButton jRadioButtonSim;
    private javax.swing.JSpinner jSpinnerDescontoPorcentagem;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField txtCodigoLocalidade;
    private javax.swing.JTextField txtCodigoProduto;
    private javax.swing.JTextField txtNomeDoCliente;
    private javax.swing.JTextField txtNomeProduto;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtTotalComDesconto;
    private javax.swing.JTextField txtTotalSemDesconto;
    private javax.swing.JTextField txtValorAPagar;
    private javax.swing.JTextField txtValorSugerido;
    // End of variables declaration//GEN-END:variables
}
