/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFPesquisaFornecedores.java
 *
 * Created on 06/01/2009, 18:00:51
 */
package br.com.seeds;

import Classes.*;
import ClassesDAO.*;
import Excessoes.BancoException;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Guilherme
 */
public class ReciboCursoFerias extends javax.swing.JFrame {
    
    String s, f, nomeDoCurso;
    StringBuffer nome = new StringBuffer();
    StringBuffer nomes = new StringBuffer();
    int apagar = 0;
    public ClientesDAO daosss;
    String CodigoInstrutor = "";
    String dataSemFormatacao;

    /**
     * Creates new form JFPesquisaFornecedores
     */
    public ReciboCursoFerias(String telaPai) {
        initComponents();
        jTextField1.requestFocus();
        txtValorPago.setDocument(new LimitadorMoeda());
        txtValorPago.setText("0000");
        txtDataEmQueFoiFeitoPagamento.setDocument(new LimiteCampos.FixedLengthDocument(10));
        try {
            RecibosDAO daor = new RecibosDAO();
            txtCodigo.setText("" + daor.gerarCodigoRecibo());
        } catch (BancoException f) {
            f.printStackTrace();
        }
        NowString();
    }
    String parametroPesquisa = "";
    String campo;
    private String nomeFornecedor = "teste não vai aparecer";
    private int codigoFornecedor;
    
    public int getCodigoFornecedor() {
        return codigoFornecedor;
    }
    
    public void setCodigoFornecdor(int codigoCliente) {
        this.codigoFornecedor = codigoCliente;
    }
    
    public int getCPF() {
        return codigoFornecedor;
    }
    
    public void setCPF(int CPFF) {
        this.codigoFornecedor = CPFF;
    }
    
    public void NowString() {
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        DateFormat dg = DateFormat.getTimeInstance();
        s = df.format(now);
        f = dg.format(now);
        txtDataEmQueFoiFeitoPagamento.setText(s);
    }
    
    public void pegaNomeInstrutor() {
        Professor funcionario = new Professor();
        try {
            ProfessorDAO dao = new ProfessorDAO(); // estou instanciando a conexão
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(CodigoInstrutor);
            funcionario = dao.carregarProfessorPeloCodigo(nome);
            if (funcionario.getNome().equals("nulo")) {
            } else {
                
                txtResponsavel.setText(funcionario.getNome());
                txtValorPago.requestFocus();
            }
            dao.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void valorPorExtenso(double vlr) {
        
        if (vlr == 0) //return("zero");
        {
            jLValorPago.setText("Zero");
        }
        
        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) //return("Erro: valor superior a 999 trilhões.");
        {
            jLValorPago.setText("Erro: valor superior a 999 trilhões.");
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
        jLValorPago.setText(svalor);
    }
    
    public String pesquisarMes(String mes) {
        mes = mes.replace("/1/", "/01/");
        mes = mes.replace("/2/", "/02/");
        mes = mes.replace("/3/", "/03/");
        mes = mes.replace("/4/", "/04/");
        mes = mes.replace("/5/", "/05/");
        mes = mes.replace("/6/", "/06/");
        mes = mes.replace("/7/", "/07/");
        mes = mes.replace("/8/", "/08/");
        mes = mes.replace("/9/", "/09/");
        if (mes.contains("/01/")) {
            mes = "janeiro";
        }
        if (mes.contains("/02/")) {
            mes = "fevereiro";
        }
        
        if (mes.contains("/03/")) {
            mes = "março";
        }
        if (mes.contains("/04/")) {
            mes = "abril";
        }
        if (mes.contains("/05/")) {
            mes = "maio";
        }
        if (mes.contains("/06/")) {
            mes = "junho";
        }
        
        if (mes.contains("/07/")) {
            mes = "julho";
        }
        
        if (mes.contains("/08/")) {
            mes = "agosto";
        }
        
        if (mes.contains("/09/")) {
            mes = "setembro";
        }
        
        if (mes.contains("/10/")) {
            mes = "outubro";
        }
        
        if (mes.contains("/11/")) {
            mes = "novembro";
        }
        
        if (mes.contains("/12/")) {
            mes = "dezembro";
        }
        
        return mes;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        rowSorterToStringConverter1 = new converter.RowSorterToStringConverter();
        buttonGroupImpressoras = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtValorPago = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDataEmQueFoiFeitoPagamento = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        txtResponsavel = new javax.swing.JTextField();
        txtCPF = new javax.swing.JTextField();
        txtCodigoTurma = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtNomeDoCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        txtCelular = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoCliente = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtDiaTurma = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtHI = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        txtHF = new javax.swing.JFormattedTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtCodigoLocalidade = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtNomeDoCurso = new javax.swing.JTextField();
        jRadioBNaoImprimir = new javax.swing.JRadioButton();
        jRadioBJasperIreport = new javax.swing.JRadioButton();
        jRBImpressoraPadrao = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        jBSalvar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jLValorPago = new javax.swing.JLabel();

        rowSorterToStringConverter1.setTable(masterTable);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Recibo de Curso - sem entrada no Caixa Diário");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));

        jPanel3.setBackground(new java.awt.Color(249, 249, 249));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Valor: ");

        txtValorPago.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtValorPago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtValorPago.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorPagoFocusLost(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setText("RECIBO Nº: ");

        jLabel5.setText("Matrícula: ");

        txtMatricula.setEditable(false);
        txtMatricula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMatricula.setText("0");

        jLabel7.setText("Data:");
        jLabel7.setToolTipText("Data do Pagamento/Vencimento");

        txtDataEmQueFoiFeitoPagamento.setEditable(false);
        txtDataEmQueFoiFeitoPagamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Vou informar a data para impressão");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataEmQueFoiFeitoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtDataEmQueFoiFeitoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txtResponsavel.setEditable(false);
        txtResponsavel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCPF.setEditable(false);
        txtCPF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCodigoTurma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigoTurma.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoTurma.setText("0");
        txtCodigoTurma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoTurmaFocusLost(evt);
            }
        });
        txtCodigoTurma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoTurmaKeyPressed(evt);
            }
        });

        jLabel3.setText("Cliente: ");

        jLabel46.setText("Telefone:");

        txtNomeDoCliente.setEditable(false);
        txtNomeDoCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setText("* Turma: ");

        txtTelefone.setEditable(false);
        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCelular.setEditable(false);
        try {
            txtCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCelular.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setText("Celular:");

        txtCodigoCliente.setEditable(false);
        txtCodigoCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel10.setText("CPF:");

        jLabel11.setText("Dia:");

        txtDiaTurma.setEditable(false);
        txtDiaTurma.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel13.setText("Horário: ");
        jLabel13.setToolTipText("Hora do início.");

        txtHI.setEditable(false);
        try {
            txtHI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel14.setText("às");

        txtHF.setEditable(false);
        try {
            txtHF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCodigoLocalidade.setEditable(false);
        txtCodigoLocalidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel15.setText("/");

        txtNomeDoCurso.setEditable(false);
        txtNomeDoCurso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeDoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigoTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeDoCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDiaTurma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHI, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtHF, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel46)
                        .addGap(18, 18, 18)
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator1)
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel8});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomeDoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel46)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCodigoTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtDiaTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtHI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtHF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeDoCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel8});

        jRadioBNaoImprimir.setBackground(new java.awt.Color(249, 249, 249));
        buttonGroupImpressoras.add(jRadioBNaoImprimir);
        jRadioBNaoImprimir.setText("Não imprimir");

        jRadioBJasperIreport.setBackground(new java.awt.Color(249, 249, 249));
        buttonGroupImpressoras.add(jRadioBJasperIreport);
        jRadioBJasperIreport.setText("Jasper");
        jRadioBJasperIreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioBJasperIreportActionPerformed(evt);
            }
        });

        jRBImpressoraPadrao.setBackground(new java.awt.Color(249, 249, 249));
        buttonGroupImpressoras.add(jRBImpressoraPadrao);
        jRBImpressoraPadrao.setSelected(true);
        jRBImpressoraPadrao.setText("Impressora padrão");

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Localizar aluno: ");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${rowSorter}"), jTextField1, org.jdesktop.beansbinding.BeanProperty.create("text"), "");
        binding.setConverter(rowSorterToStringConverter1);
        bindingGroup.addBinding(binding);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        masterTable.setModel(getDadosTabelaPesquisaM());
        masterTable.getTableHeader().setReorderingAllowed(false);
        masterTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                masterTableMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(masterTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jBSalvar.setText("Concluir");
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });

        jBCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jBCancelar.setText("Sair");
        jBCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jLValorPago.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLValorPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jRadioBJasperIreport)
                            .addGap(22, 22, 22)
                            .addComponent(jRBImpressoraPadrao)
                            .addGap(18, 18, 18)
                            .addComponent(jRadioBNaoImprimir)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jBSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBCancelar, jBSalvar});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioBJasperIreport)
                        .addComponent(jRadioBNaoImprimir)
                        .addComponent(jRBImpressoraPadrao))
                    .addComponent(jLValorPago)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBSalvar)
                    .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jRadioBJasperIreport, jRadioBNaoImprimir});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBCancelar, jBSalvar});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLValorPago, txtValorPago});

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
    this.dispose();
}//GEN-LAST:event_jBCancelarActionPerformed
    
private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        ReciboCursoFerias.this.dispose();
    }
}//GEN-LAST:event_jTextField1KeyPressed
    
    private void jRadioBJasperIreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioBJasperIreportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioBJasperIreportActionPerformed
    
    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
        if (jRadioBNaoImprimir.isSelected()) {
            this.dispose();
        } else {
            txtCodigo.setText(txtCodigo.getText().trim());
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigo.getText());
            try {
                
                if (txtValorPago.getText().equals("")) {
                    JOptionPane.showMessageDialog(this,
                            "Campo Valor pago tem que ser informado!",
                            "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    txtValorPago.requestFocus();
                } else {
                    
                    RecibosDAO daor = new RecibosDAO();
                    Recibo recibo = new Recibo();
                    recibo.setCodigo(Integer.parseInt(txtCodigo.getText()));
                    String Valores = txtValorPago.getText();
                    Valores = Valores.replace(".", "");
                    Valores = Valores.replace(",", ".");
                    recibo.setValor(Double.parseDouble(Valores));
                    recibo.setMatricula(Integer.parseInt(txtMatricula.getText()));
                    recibo.setData(txtDataEmQueFoiFeitoPagamento.getText().trim() + " " + f);
                    
                    try {
                        daor.adicionarRecibo(recibo);
                        JOptionPane.showMessageDialog(null, "Processando o recibo!");
                        daor.desconectar();
                    } catch (BancoException e) {
                        e.printStackTrace();
                    }

                    //IMPRESSÃO 
                    if (jRadioBJasperIreport.isSelected()) {
                        
                        try {
                            Connection con = new Conexao().getConnection();
                            HashMap parametros = new HashMap();
                            parametros.put("NOME", txtNomeDoCliente.getText().trim());
                            parametros.put("DINHEIRO", txtValorPago.getText());
                            parametros.put("DINHEIROEX", jLValorPago.getText());
                            String mes = (pesquisarMes(txtDataEmQueFoiFeitoPagamento.getText()));
                            parametros.put("MES", mes);
                            parametros.put("TURMA", txtNomeDoCurso.getText().trim());
                            parametros.put("DIA", txtDiaTurma.getText().trim());
                            parametros.put("HI", txtHI.getText().trim());
                            parametros.put("HF", txtHF.getText().trim());
                            parametros.put("PROFESSOR", txtResponsavel.getText().trim());
                            String ano;
                            ano = txtDataEmQueFoiFeitoPagamento.getText();
                            ano = ano.replace("/1/", "/01/");
                            ano = ano.substring(6);
                            parametros.put("ANO", ano);
                            if (jCheckBox1.isSelected() == true) {
                                parametros.put("DATA", JOptionPane.showInputDialog(this, "Informe a data.\nNão precisa seguir nenhuma formatação"));
                            } else {
                                parametros.put("DATA", txtDataEmQueFoiFeitoPagamento.getText());
                            }
                            
                            JasperPrint jp = JasperFillManager.fillReport("./relatorios/contratosImpresorra.jasper", parametros, con);
                            // Estava usando: JasperPrint jp = JasperFillManager.fillReport("./relatorios/reciboImpressora.jasper", parametros, con);
                            JasperViewer jrv = new JasperViewer(jp, false);
                            jrv.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
             
                    }
                    
                    if (jRBImpressoraPadrao.isSelected()) {
                        if (jCheckBox1.isSelected() == true) {
                            dataSemFormatacao = JOptionPane.showInputDialog(this, "Informe a data sem a necessidade de formatação predifinida.\n"
                                    + "Use a formatação que desejar.");
                        }                        
                        
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
                        String mes = (pesquisarMes(txtDataEmQueFoiFeitoPagamento.getText()));
                        String ano;
                        ano = txtDataEmQueFoiFeitoPagamento.getText();
                        ano = ano.replace("/1/", "/01/");
                        ano = ano.substring(6);
                        String Final = "";
                        Final = "_____________________________________________________________________________________";
                        Final = Final + "\n Associação Filantrópica N.de Amparo Social";
                        Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                        Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                        Final = Final + "\n_____________________________________________________________________________________";
                        Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + jLValorPago.getText() + " ), ";
                        Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                        Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                        Final = Final + "\n   Parceria/Local:________________________________";
                        Final = Final + "\n";
                        Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                        Final = Final + "\n";
                        Final = Final + "\n 		_____________________________________________________";
                        if (jCheckBox1.isSelected() == true) {
                            Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                        } else {
                            Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                        }
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
                        Final = Final + "\n Utilidade Pública Municipal Lei 3.376 de           RECIBO/MENSALIDADE";
                        Final = Final + "\n 22/12/2004 - CNPJ: 07.003.661/0001";
                        Final = Final + "\n_____________________________________________________________________________________";
                        Final = Final + "\n   Recebemos de " + txtNomeDoCliente.getText() + " a quantia de R$ " + txtValorPago.getText() + " ( " + jLValorPago.getText() + " ), ";
                        Final = Final + "\n   Referente a " + mes + " " + ano + " Turma/Dia:" + txtNomeDoCurso.getText() + " - " + txtDiaTurma.getText() + "";
                        Final = Final + "\n   Horário: " + txtHI.getText() + " às " + txtHF.getText() + " Prof.: " + txtResponsavel.getText() + "";
                        Final = Final + "\n   Parceria/Local:________________________________";
                        Final = Final + "\n";
                        Final = Final + "\n 		Por ser verdade, firmo o presente termo.";
                        Final = Final + "\n";
                        Final = Final + "\n 		_____________________________________________________";
                        if (jCheckBox1.isSelected() == true) {
                            Final = Final + "\n 			  Montes Claros - " + dataSemFormatacao + "";
                        } else {
                            Final = Final + "\n 			  Montes Claros - " + txtDataEmQueFoiFeitoPagamento.getText() + "";
                        }                        
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
                        out.close();
                        
                        File Emails = new File("COMPROVANTE DE PAGAMENTO - SEEDS.txt");
                        try {
                            Desktop.getDesktop().print(Emails);
                        } catch (IOException ex) {
                            Logger.getLogger(ReciboCursoFerias.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    this.dispose();
                    final ReciboCursoFerias pesq = new ReciboCursoFerias("Recibo de Curso - sem entrada no Caixa Diário");
                    pesq.setVisible(true);
                    
                    ActionListener acaoOk = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            
                            pesq.dispose();
                        }
                    };
                    
                    txtValorPago.setText("00");
                    masterTable.setVisible(false);
                    getDadosTabelaClientePesquisa();
                    masterTable.setVisible(true);
                    
                }
                
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jBSalvarActionPerformed
    
    private void masterTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_masterTableMouseReleased
        try {
            
            ClientesDAO dao = new ClientesDAO(); // estou instanciando a conexão
            Cliente aluno = new Cliente();
            TurmasDAO daoss = new TurmasDAO(); // estou instanciando a conexão
            Turmas turmas = new Turmas();
            ProfessorDAO daosss = new ProfessorDAO(); // estou instanciando a conexão
            Professor professor = new Professor();
            
            txtMatricula.setText(masterTable.getValueAt(masterTable.getSelectedRow(), 0).hashCode() + "");
            txtCodigoCliente.setText(masterTable.getValueAt(masterTable.getSelectedRow(), 1).hashCode() + "");
            txtCodigoLocalidade.setText(masterTable.getValueAt(masterTable.getSelectedRow(), 2).hashCode() + "");
            txtCodigoTurma.setText(masterTable.getValueAt(masterTable.getSelectedRow(), 3).hashCode() + "");
            
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigoCliente.getText());
            
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(txtCodigoLocalidade.getText());
            aluno = dao.carregarClientePeloCodigo(nome, nomes);
            
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigoTurma.getText());
            turmas = daoss.carregarTurmaPeloCodigo(nome);
            nomeDoCurso = turmas.getCurso();
            
            if (!txtCodigoCliente.getText().equals("")) {
                txtNomeDoCliente.setText(aluno.getNome());
                txtCodigoLocalidade.setText(aluno.getLocalidade() + "");
                txtCPF.setText(aluno.getCpf());
                txtTelefone.setText(aluno.getTelefone());
                txtCelular.setText(aluno.getCelular());
                txtCodigoLocalidade.setText("" + aluno.getLocalidade());
            }
            if (!txtCodigoTurma.getText().equals("")) {
                txtDiaTurma.setText("" + turmas.getDia());
                txtHI.setText("" + turmas.getHi());
                txtHF.setText("" + turmas.getHf());
                txtNomeDoCurso.setText("" + turmas.getCurso());
                
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(turmas.getFuncionario());
                professor = daosss.carregarProfessorPeloCodigo(nome);
                txtResponsavel.setText(professor.getNome());
            }
            aluno = null;
            turmas = null;
            professor = null;
            dao.desconectar();
            daoss.desconectar();
            daosss.desconectar();
        } catch (Exception b) {
            JOptionPane.showMessageDialog(null, b);
        }
    }//GEN-LAST:event_masterTableMouseReleased
    
    private void txtCodigoTurmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoTurmaFocusLost
        if (txtCodigoTurma.getText().equals("") || txtCodigoTurma.getText().equals("0")) {
            txtCodigoTurma.setText("0");
            txtResponsavel.setText("");
            txtDiaTurma.setText("");
            txtHI.setText("");
            txtHF.setText("");
            
        } else {
            try {
                TurmasDAO daop = new TurmasDAO();
                Turmas turmass = new Turmas();
                
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigoTurma.getText() + "");
                turmass = daop.carregarTurmaPeloCodigo(nome);
                txtDiaTurma.setText("" + turmass.getDia());
                txtHI.setText("" + turmass.getHi());
                txtHF.setText("" + turmass.getHf());
                txtNomeDoCurso.setText("" + turmass.getCurso());
                
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(turmass.getFuncionario() + "");
                
                ProfessorDAO daus = new ProfessorDAO(); // estou instanciando a conexão
                Professor professor = new Professor();
                professor = daus.carregarProfessorPeloCodigo(nome);
                if (professor.getNome().equals("nulo")) {
                    
                    txtResponsavel.setText("");
                    txtDiaTurma.setText("");
                    txtHI.setText("");
                    txtHF.setText("");
                    txtNomeDoCurso.setText("");
                    txtCodigoTurma.setText("0");
                    final BuscarTurmas pesq = new BuscarTurmas("Turmas");
                    pesq.setVisible(true);
                    
                    ActionListener acaoOk = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                            nome.delete(0, apagar);
                            nome.append(pesq.getCodigoCliente());
                            
                            try {
                                TurmasDAO dao = new TurmasDAO();
                                Turmas al = new Turmas();
                                al = dao.carregarTurmaPeloCodigo(nome);
                                txtCodigoTurma.setText(String.valueOf(nome));
                                CodigoInstrutor = "" + al.getFuncionario();
                                pegaNomeInstrutor();
                                al = null;
                                dao.desconectar();
                            } catch (Exception b) {
                                JOptionPane.showMessageDialog(null, e);
                            }
                            
                            pesq.dispose();
                        }
                    };
                    pesq.setAcao(acaoOk);
                    
                } else {
                    txtResponsavel.setText(professor.getNome());
                }
                turmass = null;
                professor = null;
                daop.desconectar();
                daosss.desconectar();
                daus.desconectar();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_txtCodigoTurmaFocusLost
    
    private void txtCodigoTurmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTurmaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            
            try {
                TurmasDAO daop = new TurmasDAO();
                Turmas turmass = new Turmas();
                
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigoTurma.getText() + "");
                turmass = daop.carregarTurmaPeloCodigo(nome);
                
                txtDiaTurma.setText("" + turmass.getDia());
                txtHI.setText("" + turmass.getHi());
                txtHF.setText("" + turmass.getHf());
                txtNomeDoCurso.setText("" + turmass.getCurso());
                
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(turmass.getFuncionario() + "");
                
                ProfessorDAO daus = new ProfessorDAO(); // estou instanciando a conexão
                Professor professor = new Professor();
                professor = daus.carregarProfessorPeloCodigo(nome);
                if (professor.getNome().equals("nulo")) {
                    
                    txtResponsavel.setText("");
                    txtCodigoTurma.setText("0");
                    final BuscarTurmas pesq = new BuscarTurmas("Turmas");
                    pesq.setVisible(true);
                    
                    ActionListener acaoOk = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                            nome.delete(0, apagar);
                            nome.append(pesq.getCodigoCliente());
                            
                            try {
                                TurmasDAO dao = new TurmasDAO();
                                Turmas al = new Turmas();
                                al = dao.carregarTurmaPeloCodigo(nome);
                                txtCodigoTurma.setText(String.valueOf(nome));
                                CodigoInstrutor = "" + al.getFuncionario();
                                pegaNomeInstrutor();
                                al = null;
                                dao.desconectar();
                            } catch (Exception b) {
                                JOptionPane.showMessageDialog(null, e);
                            }
                            
                            pesq.dispose();
                        }
                    };
                    pesq.setAcao(acaoOk);
                    
                } else {
                    txtResponsavel.setText(professor.getNome());
                }
                turmass = null;
                professor = null;
                daop.desconectar();
                daus.desconectar();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_txtCodigoTurmaKeyPressed
    
    private void txtValorPagoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorPagoFocusLost
        String ValorExtenso = txtValorPago.getText();
        ValorExtenso = ValorExtenso.replace(".", "");
        ValorExtenso = ValorExtenso.replace(",", ".");
        valorPorExtenso(Double.parseDouble(ValorExtenso));
    }//GEN-LAST:event_txtValorPagoFocusLost
    /**
     * @param args the command line arguments
     */
    public DefaultTableModel getDadosTabelaClientePesquisa() {
        try {
            
            ClientesDAO dao = new ClientesDAO();
            List<Cliente> lista = dao.pesquisaCliente(parametroPesquisa, campo);
            int numeroContatos = lista.size();
            int numeroColunas = 5;
            
            Object[][] dados = new Object[numeroContatos][numeroColunas];
            
            for (int us = 0; us < lista.size(); us++) {
                dados[us][0] = lista.get(us).getCodigo();
                dados[us][1] = lista.get(us).getLocalidade();
                dados[us][2] = lista.get(us).getNome();
                dados[us][3] = lista.get(us).getCpf();
                dados[us][4] = lista.get(us).getTelefone();
            }
            lista = null;
            dao.desconectar();
            String[] nomeColunas = {"Código", "Localidade", "Nome", "CPF / CNPJ", "Telefone"};
            return new DefaultTableModel(dados, nomeColunas);
        } catch (BancoException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    public DefaultTableModel getDadosTabelaPesquisaM() {
        try {
            ClientesDAO daos = new ClientesDAO();
            Cliente cliente = new Cliente();
            MatriculaDAO dao = new MatriculaDAO();
            List<Matricula> lista = dao.pesquisaMatricula(parametroPesquisa, campo);
            
            Object[][] dados = new Object[lista.size()][5];
            
            for (int us = 0; us < lista.size(); us++) {
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(lista.get(us).getCodigoAluno());
                apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nomes.delete(0, apagar);
                nomes.append(lista.get(us).getLocalidade());
                cliente = daos.carregarClienteInativoPeloCodigo(nome, nomes);
                
                if (cliente.getNome().equals("nulo")) {
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    nome.append(lista.get(us).getCodigoAluno());
                    apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nomes.delete(0, apagar);
                    nomes.append(lista.get(us).getLocalidade());
                    cliente = daos.carregarClientePeloCodigo(nome, nomes);
                }
                dados[us][0] = lista.get(us).getCodigo();
                dados[us][1] = lista.get(us).getCodigoAluno();
                dados[us][2] = lista.get(us).getLocalidade();
                dados[us][3] = lista.get(us).getCodigoTurma();
                dados[us][4] = cliente.getNome();
                
            }
            lista = null;
            cliente = null;
            dao.desconectar();
            daos.desconectar();
            String[] nomeColunas = {"Matrícula", "Código do Aluno", "Localidade", "Código da turma", "Nome"};
            return new DefaultTableModel(dados, nomeColunas);
        } catch (BancoException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    public String getNomeCliente() {
        return nomeFornecedor;
    }
    
    public void setNomeCliente(String nomeCliente) {
        this.nomeFornecedor = nomeCliente;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupImpressoras;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLValorPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRBImpressoraPadrao;
    private javax.swing.JRadioButton jRadioBJasperIreport;
    private javax.swing.JRadioButton jRadioBNaoImprimir;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable masterTable;
    private converter.RowSorterToStringConverter rowSorterToStringConverter1;
    private javax.swing.JTextField txtCPF;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoCliente;
    private javax.swing.JTextField txtCodigoLocalidade;
    private javax.swing.JTextField txtCodigoTurma;
    private javax.swing.JTextField txtDataEmQueFoiFeitoPagamento;
    private javax.swing.JTextField txtDiaTurma;
    private javax.swing.JFormattedTextField txtHF;
    private javax.swing.JFormattedTextField txtHI;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNomeDoCliente;
    private javax.swing.JTextField txtNomeDoCurso;
    private javax.swing.JTextField txtResponsavel;
    private javax.swing.JFormattedTextField txtTelefone;
    private javax.swing.JTextField txtValorPago;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
