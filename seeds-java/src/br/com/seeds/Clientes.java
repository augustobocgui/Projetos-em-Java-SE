/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Clientes.java
 *
 * Created on 02/09/2004, 01:18:18
 */
package br.com.seeds;

import Classes.Acesso;
import Classes.Cliente;
import Classes.verificar_CPF;
import ClassesDAO.*;
import Excessoes.BancoException;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import seeds.Main;

/**
 *
 * @author Guilherme Augusto
 */
public class Clientes extends javax.swing.JFrame {

    StringBuffer nome = new StringBuffer();
    StringBuffer nomes = new StringBuffer();
    String data = "";
    String s, f;
    int apagar = 0;
    int codigoUsuarioLogado = 0;
    boolean VerificaFinal = false;
    boolean aberto = false;

    /**
     * Creates new form Clientes
     */
    public Clientes() throws BancoException, ParseException, SQLException {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/br/com/imagens/ClienteFrame.png")).getImage());
        setLocationRelativeTo(null);

        txtNome.requestFocus();

        txtNome.setDocument(new LimiteCampos.FixedLengthDocument(100));
        txtCodigo.setDocument(new LimiteCampos.FixedLengthDocument(11));
        txtCodigoLocalidade.setDocument(new LimiteCampos.FixedLengthDocument(11));
        txtEndereco.setDocument(new LimiteCampos.FixedLengthDocument(100));
        txtNumero.setDocument(new LimiteCampos.FixedLengthDocument(10));
        txtBairro.setDocument(new LimiteCampos.FixedLengthDocument(100));
        txtCidade.setDocument(new LimiteCampos.FixedLengthDocument(100));
        txtEmail.setDocument(new LimiteCampos.FixedLengthDocument(100));
        txtTelefone.setDocument(new LimiteCampos.FixedLengthDocument(15));
        txtCelular.setDocument(new LimiteCampos.FixedLengthDocument(15));
        txtCodigo.setDocument(new OnlyNumberField(11));

        try {
            ClientesDAO dao = new ClientesDAO();
            apagar = dao.gerarCodigoCliente();
            if (apagar > dao.gerarCodigoClienteExcluido()) {
                txtCodigo.setText("" + apagar);
            } else {
                txtCodigo.setText("" + dao.gerarCodigoClienteExcluido());
            }
            apagar = 0;
            dao.desconectar();
        } catch (BancoException f) {
            f.printStackTrace();
        }

        try {
            LocalidadeDAO dao = new LocalidadeDAO();
            txtCodigoLocalidade.setText(String.valueOf(dao.gerarCodigoLocalidade() - 1));
            dao.desconectar();
        } catch (BancoException f) {
            f.printStackTrace();
        }


        try {
            UsuarioDAO dao = new UsuarioDAO();
            AcessoDAO daos = new AcessoDAO();
            Acesso user = new Acesso();
            codigoUsuarioLogado = dao.gerarCodigoUsuarioUltimoLogado();

            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(codigoUsuarioLogado);
            //JOptionPane.showMessageDialog(rootPane, nome);

            if (codigoUsuarioLogado == 0) {
                do {
                    int remove = Integer.parseInt(nome + "");
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    nome.append(remove - 1);
                    user = daos.carregarAcessoPeloCodigo(nome);
                    codigoUsuarioLogado = user.getUsuario();
                } while (nome.equals("0"));


                JOptionPane.showMessageDialog(null,
                        "Não foi possível identificar o usuário *logado no sistema!\n "
                        + "Isso não prejudica outras funções executadas.\n"
                        + "*Que está identificado no sistema computacional.", "", JOptionPane.ERROR_MESSAGE);

            } else {
                user = daos.carregarAcessoPeloCodigo(nome);
                codigoUsuarioLogado = user.getUsuario();
            }
            dao.desconectar();
            daos.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CaixaDAO Caixa = new CaixaDAO();
        NowString();
        VerificaFinal = VerificaAbetura();
        ResultSet rs = Caixa.VerificaAbertura(s, f);
        while (rs.next()) {
            aberto = true;
        }

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    try {
                        try {

                            Fechando();

                        } catch (ParseException ex) {
                            Logger.getLogger(BuscarSubprodutosCaixaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(BuscarSubprodutosCaixaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (BancoException ex) {
                    Logger.getLogger(BuscarSubprodutosCaixaVenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void Fechando() throws BancoException, SQLException, ParseException {
        this.dispose();
    }

    public void FechandoAbrindoMatricula() throws BancoException, SQLException, ParseException {
        this.dispose();
        new Matriculas().setVisible(true);
    }

    public void DataAlterando(String data, int Componente) {

        Date date = null;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (data == null || data.equals("")) {
            data = "01/01/0001";
        }
        try {
            date = (java.util.Date) formatter.parse(data);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);

        }

        if (Componente == 1) {
            txtDataNascimento.setDate(date);
        }

    }

    public void NowString() {
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        DateFormat dg = DateFormat.getTimeInstance();
        s = df.format(now);
        f = dg.format(now);
    }

    public boolean VerificaAbetura() throws ParseException, SQLException, BancoException {
        CaixaDAO dao = new CaixaDAO();
        ResultSet Verifica = dao.VerificaAbertura(s, f);
        boolean Aberto = false;
        while (Verifica.next()) {
            Aberto = true;
        }
        if (Aberto) {
            ResultSet rs = dao.VerificaFechamento(s, f);
            boolean Ver = false;
            while (rs.next()) {
                Ver = true;
            }


            if (!Ver) {
                return true;
            } else {
                return false;
            }

        } else if (!Aberto) {
            return false;
        } else {
            return Aberto;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PainelFisico = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jCBUf = new javax.swing.JComboBox();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jFTVisorCPF1 = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        txtCidade = new javax.swing.JTextField();
        txtDataNascimento = new com.toedter.calendar.JDateChooser();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCep = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jCBSexo = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        jBSalvar = new javax.swing.JButton();
        jBAlterar = new javax.swing.JButton();
        jBExcluir = new javax.swing.JButton();
        jBLimparCampos = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBBuscarCodigo = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        txtCodigoLocalidade = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Alunos");
        setResizable(false);

        PainelFisico.setBackground(new java.awt.Color(249, 249, 249));
        PainelFisico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 214, 214)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel39.setText(" CPF:");

        jLabel40.setText("* Data Nasc.");

        jLabel41.setText(" Endereço:");

        txtEndereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel42.setText("Número:");

        txtNumero.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel43.setText(" Bairro:");

        txtBairro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel44.setText("UF:");

        jCBUf.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBUf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MG", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" }));
        jCBUf.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBUfItemStateChanged(evt);
            }
        });

        jLabel45.setText(" Cidade:");

        jLabel46.setText("Telefone:");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel47.setText("E-mail:");

        try {
            jFTVisorCPF1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFTVisorCPF1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jFTVisorCPF1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTVisorCPF1FocusLost(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cpf.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtCidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDataNascimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jSpinner1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        jSpinner1.setToolTipText("Taxa de desconto na compra de produtos.");
        jSpinner1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jSpinner1FocusLost(evt);
            }
        });

        jLabel2.setText("Desconto (%)");
        jLabel2.setToolTipText("%");

        jLabel3.setText("CEP:");

        try {
            txtCep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setText("Sexo:");

        jCBSexo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masculino", "Feminino", "Outro" }));

        jLabel5.setText("Celular:");

        txtCelular.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCelular.setToolTipText("(##) ####-####");

        txtTelefone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTelefone.setToolTipText("(##) ####-####");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel40))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(txtCelular))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addGap(18, 18, 18)
                                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFTVisorCPF1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addComponent(jCBSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel42)
                        .addGap(78, 78, 78))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtBairro, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                                    .addComponent(txtCidade))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCep)
                                    .addComponent(jCBUf, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNumero, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(txtEmail))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel40, jLabel41, jLabel43, jLabel45});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jFTVisorCPF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel39))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jCBSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDataNascimento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel41)
                        .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel42))
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(jCBUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel41, jLabel43, jLabel45, jLabel47});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jFTVisorCPF1, txtBairro, txtEmail, txtEndereco, txtNumero});

        jBSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jBSalvar.setText("Salvar");
        jBSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });

        jBAlterar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/view-refresh.png"))); // NOI18N
        jBAlterar.setText("Alterar");
        jBAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarActionPerformed(evt);
            }
        });

        jBExcluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/edit-delete.png"))); // NOI18N
        jBExcluir.setText("Excluir");
        jBExcluir.setToolTipText("Exclusão lógica dos registros.");
        jBExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExcluirActionPerformed(evt);
            }
        });

        jBLimparCampos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBLimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/draw-eraser (2).png"))); // NOI18N
        jBLimparCampos.setText("Limpar");
        jBLimparCampos.setToolTipText("Carregar dados do Banco de Dados");
        jBLimparCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimparCamposActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel10.setText("* CAMPOS OBRIGATÓRIOS.");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel2.setForeground(new java.awt.Color(102, 102, 102));

        jLabel38.setText("Nome *");

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Código");

        jBBuscarCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jBBuscarCodigo.setBorderPainted(false);
        jBBuscarCodigo.setContentAreaFilled(false);
        jBBuscarCodigo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscarCodigo.setFocusable(false);
        jBBuscarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarCodigoActionPerformed(evt);
            }
        });

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });

        txtCodigoLocalidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigoLocalidade.setToolTipText("Código da Localidade/Prédio");

        jLabel6.setText("/");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jBBuscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBBuscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/revert.png"))); // NOI18N
        jButton3.setText("Inativos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/matriculas.png"))); // NOI18N
        jButton4.setText("Matrícula");
        jButton4.setToolTipText("Ir para Interface Matrícula.");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelFisicoLayout = new javax.swing.GroupLayout(PainelFisico);
        PainelFisico.setLayout(PainelFisicoLayout);
        PainelFisicoLayout.setHorizontalGroup(
            PainelFisicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelFisicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelFisicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PainelFisicoLayout.createSequentialGroup()
                        .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jBExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBLimparCampos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PainelFisicoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBAlterar, jBExcluir, jBLimparCampos, jBSalvar});

        PainelFisicoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton3, jButton4});

        PainelFisicoLayout.setVerticalGroup(
            PainelFisicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelFisicoLayout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(PainelFisicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLimparCampos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PainelFisicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addComponent(jBSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        PainelFisicoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBAlterar, jBExcluir, jBLimparCampos, jBSalvar});

        PainelFisicoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton3, jButton4});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelFisico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelFisico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jCBUfItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBUfItemStateChanged
    // TODO add your handling code here:
}//GEN-LAST:event_jCBUfItemStateChanged

private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
    txtCodigo.setText(txtCodigo.getText().trim());
    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nome.delete(0, apagar);
    nome.append(txtCodigo.getText());
    apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nomes.delete(0, apagar);
    nomes.append(txtCodigoLocalidade.getText());
    try {
        ClientesDAO dao = new ClientesDAO();
        Cliente cliente = new Cliente();
        cliente = dao.carregarClientePeloCodigo(nome, nomes);
        if (cliente.getNome().equals("nulo")) {
            if (txtNome.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Campo Nome tem que ser informado!",
                        "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                txtNome.requestFocus();
            } else {
                jBSalvar.setEnabled(true);
                cliente.setCodigo(Integer.parseInt(txtCodigo.getText()));
                cliente.setLocalidade(Integer.parseInt(txtCodigoLocalidade.getText()));
                txtNome.setText(txtNome.getText().trim());
                cliente.setNome(txtNome.getText());
                if (txtDataNascimento.getDate() == null) {
                    cliente.setNascimento("");
                } else {
                    data = new SimpleDateFormat("dd/MM/yyyy").format(txtDataNascimento.getDate());
                    cliente.setNascimento(data);
                }
                cliente.setCpf(jFTVisorCPF1.getText());

                cliente.setTelefone(txtTelefone.getText());
                cliente.setCelular(txtCelular.getText());

                if (txtCep.getText().equals("     -   ")) {
                    cliente.setCep(null);
                } else {
                    cliente.setCep(txtCep.getText());
                }
                txtEndereco.setText(txtEndereco.getText().trim());
                cliente.setEndereco(txtEndereco.getText());

                if (txtNumero.getText().equals("")) {
                    cliente.setNumero(null);
                } else {
                    txtNumero.setText(txtNumero.getText().trim());
                    cliente.setNumero(txtNumero.getText());
                }
                txtBairro.setText(txtBairro.getText().trim());
                cliente.setBairro(txtBairro.getText());
                cliente.setUf(jCBUf.getSelectedItem().toString());
                cliente.setSexo(jCBSexo.getSelectedItem().toString());
                cliente.setCidade(txtCidade.getText());

                if (txtEmail.getText().equals("")) {
                    cliente.setEmail(null);
                } else {
                    txtEmail.setText(txtEmail.getText().trim());
                    cliente.setEmail(txtEmail.getText());
                }
                cliente.setDesconto(Float.parseFloat(jSpinner1.getValue().toString()));
                try {
                    //ClientesDAO dao = new ClientesDAO();
                    dao.adicionarCliente(cliente);
                    dao.deletarClienteExcluido(cliente);
                    JOptionPane.showMessageDialog(this,
                            "Cadastro efetuado com sucesso!",
                            "Seeds",
                            JOptionPane.INFORMATION_MESSAGE);

                    txtNome.setText("");
                    txtDataNascimento.setDate(null);
                    jFTVisorCPF1.setText("");
                    txtTelefone.setText("");
                    txtCelular.setText("");
                    txtCep.setText("");
                    txtEndereco.setText("");
                    txtNumero.setText("");
                    txtBairro.setText("");
                    jCBUf.setSelectedItem("MG");
                    jCBSexo.setSelectedItem("Masculino");
                    txtCidade.setText("");
                    txtEmail.setText("");
                    jSpinner1.setValue(0);
                    try {
                        apagar = dao.gerarCodigoCliente();
                        if (apagar > dao.gerarCodigoClienteExcluido()) {
                            txtCodigo.setText("" + apagar);
                        } else {
                            txtCodigo.setText("" + dao.gerarCodigoClienteExcluido());
                        }
                        apagar = 0;
                    } catch (BancoException f) {
                        f.printStackTrace();
                    }
                } catch (BancoException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "O cliente informado consta no banco!");
        }
        cliente = null;
        dao.desconectar();
    } catch (BancoException e) {
        e.printStackTrace();
    }

}//GEN-LAST:event_jBSalvarActionPerformed

private void jBAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarActionPerformed
    txtCodigo.setText(txtCodigo.getText().trim());
    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nome.delete(0, apagar);
    nome.append(txtCodigo.getText());
    apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nomes.delete(0, apagar);
    nomes.append(txtCodigoLocalidade.getText());
    try {
        ClientesDAO dao = new ClientesDAO();
        Cliente cliente = new Cliente();
        cliente = dao.carregarClientePeloCodigo(nome, nomes);
        if (cliente.getNome().equals("nulo")) {
            JOptionPane.showMessageDialog(this,
                    "O cliente informado não consta no banco!");
        } else {
            if (txtNome.getText().equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Campo Nome tem que ser informado!",
                        "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                txtNome.requestFocus();
            } else {
                jBSalvar.setEnabled(true);
                cliente.setCodigo(Integer.parseInt(txtCodigo.getText()));
                cliente.setLocalidade(Integer.parseInt(txtCodigoLocalidade.getText()));
                txtNome.setText(txtNome.getText().trim());
                cliente.setNome(txtNome.getText());
                if (txtDataNascimento.getDate() == null) {
                    cliente.setNascimento("");
                } else {
                    data = new SimpleDateFormat("dd/MM/yyyy").format(txtDataNascimento.getDate());
                    cliente.setNascimento(data);
                }
                cliente.setCpf(jFTVisorCPF1.getText());

                cliente.setTelefone(txtTelefone.getText());
                cliente.setCelular(txtCelular.getText());

                if (txtCep.getText().equals("     -   ")) {
                    cliente.setCep(null);
                } else {
                    cliente.setCep(txtCep.getText());
                }
                txtEndereco.setText(txtEndereco.getText().trim());
                cliente.setEndereco(txtEndereco.getText());

                if (txtNumero.getText().equals("")) {
                    cliente.setNumero(null);
                } else {
                    txtNumero.setText(txtNumero.getText().trim());
                    cliente.setNumero(txtNumero.getText());
                }
                txtBairro.setText(txtBairro.getText().trim());
                cliente.setBairro(txtBairro.getText());
                cliente.setUf(jCBUf.getSelectedItem().toString());
                cliente.setSexo(jCBSexo.getSelectedItem().toString());
                cliente.setCidade(txtCidade.getText());

                if (txtEmail.getText().equals("")) {
                    cliente.setEmail(null);
                } else {
                    txtEmail.setText(txtEmail.getText().trim());
                    cliente.setEmail(txtEmail.getText());
                }
                cliente.setDesconto(Float.parseFloat(jSpinner1.getValue().toString()));
                try {
                    //ClientesDAO dao = new ClientesDAO();
                    dao.atualizaDados(cliente);
                    JOptionPane.showMessageDialog(null, "Efetuado com sucesso!");

                    txtNome.setText("");
                    txtDataNascimento.setDate(null);
                    jFTVisorCPF1.setText("");
                    txtTelefone.setText("");
                    txtCelular.setText("");
                    txtCep.setText("");
                    txtEndereco.setText("");
                    txtNumero.setText("");
                    txtBairro.setText("");
                    jCBUf.setSelectedItem("MG");
                    jCBSexo.setSelectedItem("Masculino");
                    txtCidade.setText("");
                    txtEmail.setText("");
                    try {
                        apagar = dao.gerarCodigoCliente();
                        if (apagar > dao.gerarCodigoClienteExcluido()) {
                            txtCodigo.setText("" + apagar);
                        } else {
                            txtCodigo.setText("" + dao.gerarCodigoClienteExcluido());
                        }
                        apagar = 0;
                    } catch (BancoException f) {
                        f.printStackTrace();
                    }
                } catch (BancoException e) {
                    e.printStackTrace();
                }
            }
        }
        cliente = null;
        dao.desconectar();
    } catch (BancoException e) {
        e.printStackTrace();
    }

}//GEN-LAST:event_jBAlterarActionPerformed

private void jBExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExcluirActionPerformed
    txtCodigo.setText(txtCodigo.getText().trim());
    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nome.delete(0, apagar);
    nome.append(txtCodigo.getText());
    apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nomes.delete(0, apagar);
    nomes.append(txtCodigoLocalidade.getText());
    try {
        ClientesDAO dao = new ClientesDAO();
        Cliente cliente = new Cliente();
        cliente = dao.carregarClientePeloCodigo(nome, nomes);
        if (cliente.getNome().equals("nulo")) {
            JOptionPane.showMessageDialog(this,
                    "O cliente informado não consta no banco!");
        } else {
            int selection = JOptionPane.showConfirmDialog(this,
                    "Deseja excluir o registro?",
                    "Seeds", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.OK_OPTION) {
                if (txtCodigo.equals("")) {
                    JOptionPane.showMessageDialog(this, "Campo Código não pode ser vazio\n"
                            + "Clique Incluir");
                } else {
                    //Cliente cliente = new Cliente();
                }

                //Gerar a declaração INÍCIO
                int escolha = JOptionPane.showConfirmDialog(this,
                        "Deseja gerar declaração de trancamento?",
                        "Seeds", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (escolha == JOptionPane.OK_OPTION) {
                    try {
                        Connection con = new Conexao().getConnection();
                        HashMap parametros = new HashMap();
                        parametros.put("NOME", txtNome.getText().trim());
                        parametros.put("CURSO", JOptionPane.showInputDialog(this, "Insira o nome do curso."));
                        parametros.put("MES", JOptionPane.showInputDialog(this, "Insira o mês em que parou de frequentar."));
                        parametros.put("APOSTILA", JOptionPane.showInputDialog(this, "Parou na apostila?"));

                        // JasperPrint jp = JasperFillManager.fillReport("C:/Users/Guilherme/Documents/NetBeansProjects/shiftsoftlight/src/relatorios/Alunos.jasper", parametros, con);
                        //JasperPrint jp = JasperFillManager.fillReport("C:/Program Files/shiftsoftlight/relatorios/Alunos.jasper", parametros, con);
                        JasperPrint jp = JasperFillManager.fillReport("./relatorios/Trancamento.jasper", parametros, con);

                        JasperViewer jrv = new JasperViewer(jp, false);
                        jrv.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (VerificaFinal) {
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
                    } else {
                        JOptionPane.showMessageDialog(null, "O caixa está fechado!");
                        //----Venda.this.dispose();
                        try {
                            try {
                                // clicou em caixa
                                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados

                                Main.Caixa.dispose();
                                Main.Caixa = new CaixaInterface();
                                Main.Caixa.AoIniciar();
                            } catch (BancoException ex) {
                                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Main.Caixa.setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } else {
                    //Registrar que optou por não gerar declaração 10reias
                    AcessoDAO daos = new AcessoDAO();
                    Acesso okay = new Acesso();
                    NowString();
                    okay.setUsuario(codigoUsuarioLogado);
                    okay.setData(s);
                    okay.setHora(f);
                    okay.setDescricao("Declaração de trancamento [Interface - Clientes ]\n"
                            + "Registro: " + txtCodigo.getText() + " \n"
                            + "Observação: O usuário Optou por não gerar a Declaração.\n");
                    daos.adicionarAcesso(okay);
                    daos.desconectar();
                }
                //Gerar a declaração FIN

                // Cliente ClienteDeletarRegistro = new Cliente();
                cliente.setCodigo(Integer.parseInt(txtCodigo.getText()));
                try {
                    //ClientesDAO dao = new ClientesDAO();
                    cliente.setCodigo(Integer.parseInt(txtCodigo.getText()));
                    cliente.setLocalidade(Integer.parseInt(txtCodigoLocalidade.getText()));
                    txtNome.setText(txtNome.getText().trim());
                    cliente.setNome(txtNome.getText());
                    if (txtDataNascimento.getDate() == null) {
                        cliente.setNascimento("");
                    } else {
                        data = new SimpleDateFormat("dd/MM/yyyy").format(txtDataNascimento.getDate());
                        cliente.setNascimento(data);
                    }
                    cliente.setCpf(jFTVisorCPF1.getText());

                    cliente.setTelefone(txtTelefone.getText());
                    cliente.setCelular(txtCelular.getText());

                    if (txtCep.getText().equals("     -   ")) {
                        cliente.setCep(null);
                    } else {
                        cliente.setCep(txtCep.getText());
                    }
                    txtEndereco.setText(txtEndereco.getText().trim());
                    cliente.setEndereco(txtEndereco.getText());

                    if (txtNumero.getText().equals("")) {
                        cliente.setNumero(null);
                    } else {
                        txtNumero.setText(txtNumero.getText().trim());
                        cliente.setNumero(txtNumero.getText());
                    }
                    txtBairro.setText(txtBairro.getText().trim());
                    cliente.setBairro(txtBairro.getText());
                    cliente.setUf(jCBUf.getSelectedItem().toString());
                    cliente.setSexo(jCBSexo.getSelectedItem().toString());
                    cliente.setCidade(txtCidade.getText());

                    if (txtEmail.getText().equals("")) {
                        cliente.setEmail(null);
                    } else {
                        txtEmail.setText(txtEmail.getText().trim());
                        cliente.setEmail(txtEmail.getText());
                    }
                    cliente.setDesconto(Float.parseFloat(jSpinner1.getValue().toString()));

                    dao.adicionarClienteExcluido(cliente);
                    dao.deletarCliente(cliente);
                    JOptionPane.showMessageDialog(this, "Atualização efetuada com sucesso!",
                            "seeds",
                            JOptionPane.INFORMATION_MESSAGE);
                    txtNome.setText("");
                    txtDataNascimento.setDate(null);
                    txtTelefone.setText("");
                    txtCelular.setText("");
                    txtCep.setText("");
                    txtEndereco.setText("");
                    txtNumero.setText("");
                    txtBairro.setText("");
                    txtCidade.setText("");
                    jCBSexo.setSelectedItem("Masculino");
                    jCBUf.setSelectedItem("MG");
                    jFTVisorCPF1.setText("");
                    txtEmail.setText("");
                    try {
                        apagar = dao.gerarCodigoCliente();
                        if (apagar > dao.gerarCodigoClienteExcluido()) {
                            txtCodigo.setText("" + apagar);
                        } else {
                            txtCodigo.setText("" + dao.gerarCodigoClienteExcluido());
                        }
                        apagar = 0;
                    } catch (BancoException f) {
                        f.printStackTrace();
                    }
                } catch (BancoException e) {
                    e.printStackTrace();
                }

            }
        }
        cliente = null;
        dao.desconectar();
    } catch (BancoException e) {
        e.printStackTrace();
    }

}//GEN-LAST:event_jBExcluirActionPerformed

private void jBLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimparCamposActionPerformed
    txtCodigo.setText("");
    txtNome.setText("");
    jFTVisorCPF1.setText("");
    txtTelefone.setText("");
    txtCelular.setText("");
    txtCep.setText("");
    txtEndereco.setText("");
    txtNumero.setText("");
    txtBairro.setText("");
    jCBUf.setSelectedItem("MG");
    jCBSexo.setSelectedItem("Masculino");
    txtCidade.setText("");
    txtEmail.setText("");
    try {
        ClientesDAO dao = new ClientesDAO();
        apagar = dao.gerarCodigoCliente();
        if (apagar > dao.gerarCodigoClienteExcluido()) {
            txtCodigo.setText("" + apagar);
        } else {
            txtCodigo.setText("" + dao.gerarCodigoClienteExcluido());
        }
        apagar = 0;
        dao.desconectar();
    } catch (BancoException f) {
        f.printStackTrace();
    }
    txtDataNascimento.setDate(null);
}//GEN-LAST:event_jBLimparCamposActionPerformed

private void jBBuscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarCodigoActionPerformed
    txtNome.requestFocus();
    try {
        ClientesDAO dao = new ClientesDAO();
        Cliente cliente = new Cliente();
        if (dao.gerarCodigoCliente() == 1) {
            JOptionPane.showMessageDialog(this,
                    "O sistema não possuí um cliente cadastrado.",
                    "seeds",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {

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
                        ClientesDAO dao = new ClientesDAO();
                        Cliente cliente = new Cliente();
                        cliente = dao.carregarClientePeloCodigo(codigoAluno, nomes);
                        txtCodigo.setText(String.valueOf(cliente.getCodigo()));
                        txtNome.setText(cliente.getNome());
                        if (txtNome.getText().equals("nulo")) {
                            JOptionPane.showMessageDialog(rootPane,
                                    "O cliente informado não consta no banco!\n"
                                    + "Verifique se o código do Local: " + txtCodigoLocalidade.getText() + " é \n"
                                    + "o mesmo do Aluno selecionado.",
                                    "Seeds", JOptionPane.ERROR_MESSAGE);
                            jFTVisorCPF1.setText("");
                            txtTelefone.setText("");
                            txtCelular.setText("");
                            txtCep.setText("");
                            txtEndereco.setText("");
                            txtNumero.setText("");
                            txtBairro.setText("");
                            jCBUf.setSelectedItem("MG");
                            jCBSexo.setSelectedItem("Masculino");
                            txtCidade.setText("");
                            txtEmail.setText("");
                            txtCodigoLocalidade.requestFocus();
                        } else {
                            data = cliente.getNascimento();
                            if (data == null) {
                                data = null;
                            } else {
                                data = null;
                                data = cliente.getNascimento();
                                DataAlterando(data, 1);

                            }
                            jFTVisorCPF1.setText(cliente.getCpf());
                            txtTelefone.setText(cliente.getTelefone());
                            txtEndereco.setText(cliente.getEndereco());
                            txtCelular.setText(cliente.getCelular());
                            txtCep.setText(cliente.getCep());
                            txtNumero.setText(cliente.getNumero());
                            txtBairro.setText(cliente.getBairro());
                            jCBUf.setSelectedItem(cliente.getUf());
                            jCBSexo.setSelectedItem(cliente.getSexo());
                            txtCidade.setText(cliente.getCidade());
                            txtEmail.setText(cliente.getEmail());
                            jSpinner1.setValue(cliente.getDesconto());
                        }
                        cliente = null;
                        dao.desconectar();
                    } catch (BancoException b) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                    pesq.dispose();
                }
            };
            pesq.setAcao(acaoOk);
        }
        dao.desconectar();
    } catch (BancoException f) {
        f.printStackTrace();
    }

}//GEN-LAST:event_jBBuscarCodigoActionPerformed
private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    //Já que eu salvo os dados com a função Trin() devo então facilitar a busca por nome
    //Removendo os espaços desnecessários; assim melhoro a resposta a essa busca
    txtNome.setText(txtNome.getText().trim());

    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nome.delete(0, apagar);
    nome.append(txtNome.getText());
    try {
        ClientesDAO dao = new ClientesDAO();
        Cliente cliente = new Cliente();
        cliente = dao.carregarCliente(nome);
        if (cliente.getNome().equals("nulo")) {
            JOptionPane.showMessageDialog(rootPane, "O Cliente informado não consta no banco!");
        } else {
            txtCodigo.setText(String.valueOf(cliente.getCodigo()));
            txtNome.setText(cliente.getNome());
            data = cliente.getNascimento();
            if (data == null) {
            } else {
                data = null;
                data = cliente.getNascimento();
                DataAlterando(data, 1);

            }
            jFTVisorCPF1.setText(cliente.getCpf());
            txtTelefone.setText(cliente.getTelefone());
            txtCelular.setText(cliente.getCelular());
            txtCep.setText(cliente.getCep());
            txtEndereco.setText(cliente.getEndereco());
            txtNumero.setText(cliente.getNumero());
            txtBairro.setText(cliente.getBairro());
            jCBUf.setSelectedItem(cliente.getUf());
            jCBSexo.setSelectedItem(cliente.getSexo());
            txtCidade.setText(cliente.getCidade());
            txtEmail.setText(cliente.getEmail());
        }
        cliente = null;
        dao.desconectar();
    } catch (BancoException e) {
        e.printStackTrace();
    }
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    if (jFTVisorCPF1.getText().equals("000.000.000-00")
            || jFTVisorCPF1.getText().equals("111.111.111-11")
            || jFTVisorCPF1.getText().equals("222.222.222-22")
            || jFTVisorCPF1.getText().equals("333.333.333-33")
            || jFTVisorCPF1.getText().equals("444.444.444-44")
            || jFTVisorCPF1.getText().equals("555.555.555-55")
            || jFTVisorCPF1.getText().equals("666.666.666-66")
            || jFTVisorCPF1.getText().equals("777.777.777-77")
            || jFTVisorCPF1.getText().equals("888.888.888-88")
            || jFTVisorCPF1.getText().equals("999.999.999-99")) {
        JOptionPane.showMessageDialog(this,
                "CPF invalido!",
                "ERRO",
                JOptionPane.ERROR_MESSAGE);
    } else {
        verificarCPF();
    }
}//GEN-LAST:event_jButton1ActionPerformed

private void jFTVisorCPF1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTVisorCPF1FocusLost
    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nome.delete(0, apagar);
    nome.append(jFTVisorCPF1.getText());
    try {
        ClientesDAO dao = new ClientesDAO();
        Cliente cliente = new Cliente();
        cliente = dao.carregarClienteCPF(nome);
        if (cliente.getNome().equals("nulo")) {
            //JOptionPane.showMessageDialog(rootPane, "O Cliente informado não consta no banco!");
        } else {
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(cliente.getNome());
            int selection = JOptionPane.showConfirmDialog(this,
                    "Deseja carregar os dados?\n"
                    + "" + nome + "",
                    "Consta na base de dados!\n"
                    + "", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (selection == JOptionPane.OK_OPTION) {
                txtCodigo.setText(String.valueOf(cliente.getCodigo()));
                txtNome.setText(cliente.getNome());
                jFTVisorCPF1.setText(cliente.getCpf());
                txtTelefone.setText(cliente.getTelefone());
                txtCelular.setText(cliente.getCelular());
                txtCep.setText(cliente.getCep());
                txtEndereco.setText(cliente.getEndereco());
                txtNumero.setText(cliente.getNumero());
                txtBairro.setText(cliente.getBairro());
                jCBUf.setSelectedItem(cliente.getUf());
                jCBSexo.setSelectedItem(cliente.getSexo());
                txtCidade.setText(cliente.getCidade());
                txtEmail.setText(cliente.getEmail());
                data = cliente.getNascimento();
                if (data == null) {
                } else {
                    data = null;
                    data = cliente.getNascimento();
                    DataAlterando(data, 1);
                }
            }
        }
        cliente = null;
        dao.desconectar();
    } catch (BancoException e) {
        e.printStackTrace();
    }
}//GEN-LAST:event_jFTVisorCPF1FocusLost

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        if (txtCodigo.getText().equals("")) {
            //SubProdutos clientes = new SubProdutos();
            try {
                ClientesDAO dao = new ClientesDAO();
                apagar = dao.gerarCodigoCliente();
                if (apagar > dao.gerarCodigoClienteExcluido()) {
                    txtCodigo.setText("" + apagar);
                } else {
                    txtCodigo.setText("" + dao.gerarCodigoClienteExcluido());
                }
                apagar = 0;
                dao.desconectar();
            } catch (BancoException f) {
                f.printStackTrace();
            }
        }
        txtCodigo.setText(txtCodigo.getText().trim());
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtCodigo.getText());
        apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nomes.delete(0, apagar);
        nomes.append(txtCodigoLocalidade.getText());
        //String testNomeCase = txtNome.getText();
        Cliente cliente = new Cliente();
        try {
            ClientesDAO dao = new ClientesDAO(); // estou instanciando a conexão
            cliente = dao.carregarClientePeloCodigo(nome, nomes);
            if (cliente.getNome().equals("nulo")) {
                //JOptionPane.showMessageDialog(rootPane, "O Produto informado não consta no banco!");
            } else {
                int selection = JOptionPane.showConfirmDialog(this,
                        "O Cliente informado consta no banco!\n"
                        + "Deseja carregar os dados do cliente?",
                        "seeds", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    txtCodigo.setText(String.valueOf(cliente.getCodigo()));
                    txtNome.setText(cliente.getNome());
                    data = cliente.getNascimento();
                    if (data == null) {
                    } else {
                        data = null;
                        data = cliente.getNascimento();
                        DataAlterando(data, 1);
                    }
                    jFTVisorCPF1.setText(cliente.getCpf());
                    txtTelefone.setText(cliente.getTelefone());
                    txtCelular.setText(cliente.getCelular());
                    txtCep.setText(cliente.getCep());
                    txtEndereco.setText(cliente.getEndereco());
                    txtNumero.setText(cliente.getNumero());
                    txtBairro.setText(cliente.getBairro());
                    jCBUf.setSelectedItem(cliente.getUf());
                    jCBSexo.setSelectedItem(cliente.getSexo());
                    txtCidade.setText(cliente.getCidade());
                    txtEmail.setText(cliente.getEmail());

                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    txtNome.requestFocus();
                } else {
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    try {
                        apagar = dao.gerarCodigoCliente();
                        if (apagar > dao.gerarCodigoClienteExcluido()) {
                            txtCodigo.setText("" + apagar);
                        } else {
                            txtCodigo.setText("" + dao.gerarCodigoClienteExcluido());
                        }
                        apagar = 0;
                        cliente = null;
                    } catch (BancoException f) {
                        f.printStackTrace();
                    }
                }
            }
            dao.desconectar();
        } catch (BancoException e) {
            e.printStackTrace();
        }
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
    }//GEN-LAST:event_txtCodigoFocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        txtNome.requestFocus();
        try {
            ClientesDAO dao = new ClientesDAO();
            Cliente cliente = new Cliente();
            if (dao.gerarCodigoClienteExcluido() == 1) {
                JOptionPane.showMessageDialog(this,
                        "O sistema não possuí cliente inativo.",
                        "seeds",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {

                final BuscarClientesExcluidos pesq = new BuscarClientesExcluidos("Clientes Inativos");
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
                            ClientesDAO dao = new ClientesDAO();
                            Cliente cliente = new Cliente();
                            cliente = dao.carregarClienteInativoPeloCodigo(codigoAluno, nomes);
                            txtCodigo.setText(String.valueOf(cliente.getCodigo()));
                            txtNome.setText(cliente.getNome());
                            if (txtNome.getText().equals("nulo")) {
                                JOptionPane.showMessageDialog(rootPane,
                                        "O cliente informado não consta no banco!\n"
                                        + "Verifique se o código do Local: " + txtCodigoLocalidade.getText() + " é \n"
                                        + "o mesmo do Aluno selecionado.",
                                        "Seeds", JOptionPane.ERROR_MESSAGE);
                                jFTVisorCPF1.setText("");
                                txtTelefone.setText("");
                                txtCelular.setText("");
                                txtCep.setText("");
                                txtEndereco.setText("");
                                txtNumero.setText("");
                                txtBairro.setText("");
                                jCBUf.setSelectedItem("MG");
                                jCBSexo.setSelectedItem("Masculino");
                                txtCidade.setText("");
                                txtEmail.setText("");
                                txtCodigoLocalidade.requestFocus();
                            } else {
                                data = null;
                                data = cliente.getNascimento();
                                DataAlterando(data, Integer.parseInt("1"));
                                jFTVisorCPF1.setText(cliente.getCpf());
                                txtTelefone.setText(cliente.getTelefone());
                                txtEndereco.setText(cliente.getEndereco());
                                txtCelular.setText(cliente.getCelular());
                                txtCep.setText(cliente.getCep());
                                txtNumero.setText(cliente.getNumero());
                                txtBairro.setText(cliente.getBairro());
                                jCBUf.setSelectedItem(cliente.getUf());
                                jCBSexo.setSelectedItem(cliente.getSexo());
                                txtCidade.setText(cliente.getCidade());
                                txtEmail.setText(cliente.getEmail());
                                jSpinner1.setValue(cliente.getDesconto());
                            }
                            dao.desconectar();
                        } catch (BancoException b) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        pesq.dispose();
                    }
                };
                pesq.setAcao(acaoOk);
            }
            cliente = null;
            dao.desconectar();
        } catch (BancoException f) {
            f.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //Já que eu salvo os dados com a função Trin() devo então facilitar a busca por nome
        //Removendo os espaços desnecessários; assim melhoro a resposta a essa busca
        txtNome.setText(txtNome.getText().trim());

        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtNome.getText());
        Cliente cliente = new Cliente();
        try {
            ClientesDAO dao = new ClientesDAO();
            cliente = dao.carregarCliente(nome);


            if (cliente.getNome().equals("nulo") && !txtNome.getText().equals("")) {
                int selection = JOptionPane.showConfirmDialog(this,
                        "Deseja sair sem salvar o registro?",
                        "Seeds", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {

                    try {
                        FechandoAbrindoMatricula();
                    } catch (BancoException ex) {
                        Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else {
                try {
                    FechandoAbrindoMatricula();
                } catch (BancoException ex) {
                    Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            dao.desconectar();
        } catch (BancoException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jSpinner1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jSpinner1FocusLost
        if (Integer.parseInt(jSpinner1.getValue().toString()) > 100) {
            jSpinner1.setValue("" + 101);
        }
    }//GEN-LAST:event_jSpinner1FocusLost

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_txtNomeKeyPressed
    public void verificarCPF() {
        try {
            String cpf = jFTVisorCPF1.getText();
            verificar_CPF documento = new verificar_CPF();
            documento.setCpf(cpf);
            documento.verificando();
            jFTVisorCPF1.requestFocus();
        } catch (Exception e) {
            e.getMessage();

        }
    }
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelFisico;
    private javax.swing.JButton jBAlterar;
    private javax.swing.JButton jBBuscarCodigo;
    private javax.swing.JButton jBExcluir;
    private javax.swing.JButton jBLimparCampos;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jCBSexo;
    private javax.swing.JComboBox jCBUf;
    private javax.swing.JFormattedTextField jFTVisorCPF1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoLocalidade;
    private com.toedter.calendar.JDateChooser txtDataNascimento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
