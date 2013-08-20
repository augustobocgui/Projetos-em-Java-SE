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

import Classes.Cliente;
import Classes.ContasR;
import Classes.Contrato;
import ClassesDAO.ClientesDAO;
import ClassesDAO.ContasRDAO;
import ClassesDAO.ContratoDAO;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Guilherme
 */
public class Vencimentos extends javax.swing.JFrame {

    String s, f, dataFormatada;
    int apagar = 0;
    double totalAReceber = 0.00;
    StringBuffer nome = new StringBuffer();
    StringBuffer nomes = new StringBuffer();

    /**
     * Creates new form JFPesquisaFornecedores
     */
    public Vencimentos(String Codigo) {
        initComponents();
        txtDataPagamento.setDocument(new LimiteCampos.FixedLengthDocument(10));
        //=-------=-=-=-txtLocalizar.setText(Codigo + "");
        txtTotalParcelasAPagar.setText(apagar + "");
        txtTotalAReceber.setText(totalAReceber + "");
        valorPorExtenso(totalAReceber);
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
    }

    public void NowStringInversa() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        dataFormatada = df.format(c.getTime());
        //return df.parse(dataFormatada);  
    }
    //Calcula a Idade baseado em String. Exemplo: calculaIdade("20/08/1977","dd/MM/yyyy");

    public static int calculaIdade(String dataNasc, String pattern) {

        DateFormat sdf = new SimpleDateFormat(pattern);
        Date dataNascInput = null;
        try {
            dataNascInput = sdf.parse(dataNasc);
        } catch (Exception e) {
        }
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNascInput);
// Cria um objeto calendar com a data atual
        Calendar today = Calendar.getInstance();
// Obtém a idade baseado no ano
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        dateOfBirth.add(Calendar.YEAR, age);
        if (today.before(dateOfBirth)) {
            age--;
        }
        return age;
    }

    public void valorPorExtenso(double vlr) {
        if (vlr == 0) //return("zero");
        {
            txtTotalAReceber.setToolTipText("Zero");
        }

        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15) //return("Erro: valor superior a 999 trilhões.");
        {
            txtTotalAReceber.setToolTipText("Erro: valor superior a 999 trilhões.");
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
        txtTotalAReceber.setToolTipText(svalor);
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtLocalizar = new javax.swing.JTextField();
        jBCancelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtParcela = new javax.swing.JTextField();
        txtDataEmissao = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDataPagamento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtValorDaParcela = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNomeDoCliente = new javax.swing.JTextField();
        txtCelular = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoCliente = new javax.swing.JTextField();
        txtCodigoLocalidade = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTotalParcelasAPagar = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTotalAReceber = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtParcelaUm = new javax.swing.JTextField();

        rowSorterToStringConverter1.setTable(masterTable);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vencimentos");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        masterTable.setModel(getDadosTabelaClientePesquisa());
        masterTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                masterTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(masterTable);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Data da busca:");

        txtLocalizar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${rowSorter}"), txtLocalizar, org.jdesktop.beansbinding.BeanProperty.create("text"), "");
        binding.setConverter(rowSorterToStringConverter1);
        bindingGroup.addBinding(binding);

        txtLocalizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLocalizarKeyPressed(evt);
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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/gnome-reboot (2).png"))); // NOI18N
        jButton1.setToolTipText("Atualizar interface.");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addComponent(txtLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        jPanel3.setBackground(new java.awt.Color(249, 249, 249));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel6.setText("Emissão: ");

        txtParcela.setEditable(false);
        txtParcela.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDataEmissao.setEditable(false);
        txtDataEmissao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setText("Pagamento: ");
        jLabel7.setToolTipText("Data do Pagamento/Vencimento");

        jLabel5.setText("Parcela nº. ");

        jLabel2.setText("Código:");

        txtDataPagamento.setEditable(false);
        txtDataPagamento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDataPagamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataPagamentoFocusLost(evt);
            }
        });

        jLabel9.setText("Valor da parcela: ");

        txtValorDaParcela.setEditable(false);
        txtValorDaParcela.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValorDaParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtValorDaParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel7)
                        .addComponent(txtDataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel3.setText("Cliente: ");

        txtNomeDoCliente.setEditable(false);
        txtNomeDoCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomeDoCliente.setToolTipText("Background Vermelho: Aluno Inativo.");

        txtCelular.setEditable(false);
        try {
            txtCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCelular.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setText("Celular:");

        txtCodigoCliente.setEditable(false);
        txtCodigoCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCodigoLocalidade.setEditable(false);
        txtCodigoLocalidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel15.setText("/");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoLocalidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeDoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel3, jLabel8});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomeDoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoLocalidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel8});

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Total de parcelas");

        txtTotalParcelasAPagar.setEditable(false);
        txtTotalParcelasAPagar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTotalParcelasAPagar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalParcelasAPagar.setToolTipText("Total de parcelas vencidas hoje.");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Total a Receber");

        txtTotalAReceber.setEditable(false);
        txtTotalAReceber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTotalAReceber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalAReceber.setToolTipText("Resultado pode corresponder mais ou menos à média dos dados [Estimativa não precisa].");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Vencimento da 1ª parcela: ");
        jLabel10.setToolTipText("");

        txtParcelaUm.setEditable(false);
        txtParcelaUm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtParcelaUm.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtParcelaUm.setToolTipText("Data do vencimento da 1ª parcela do contrato selecionado.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotalParcelasAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalAReceber, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtParcelaUm))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTotalParcelasAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtTotalAReceber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtParcelaUm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
    this.dispose();
}//GEN-LAST:event_jBCancelarActionPerformed

private void txtLocalizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLocalizarKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        Vencimentos.this.dispose();
    }
}//GEN-LAST:event_txtLocalizarKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        Vencimentos Entrada;
        try {
            Entrada = new Vencimentos(s);
            Entrada.setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtDataPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataPagamentoFocusLost
        if (txtDataPagamento.getText().contains("-")) {
            txtDataPagamento.setText(txtDataPagamento.getText().replace("-", "/"));
        }
        if (txtDataPagamento.getText().contains(" ")) {
            txtDataPagamento.setText(txtDataPagamento.getText().replace(" ", ""));
        }
        if (txtDataPagamento.getText().length() < 10 || txtDataPagamento.getText().length() > 10) {
            JOptionPane.showMessageDialog(rootPane, "Não é um formato de data válido!\n"
                    + "Exemplo: 2012/01/01\n"
                    + "yyyy/MM/dd", "", JOptionPane.ERROR_MESSAGE);
            txtDataPagamento.requestFocus();
        }
    }//GEN-LAST:event_txtDataPagamentoFocusLost

    private void masterTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_masterTableMouseReleased
        txtNomeDoCliente.setBackground(Color.lightGray);
        try {
            ContasRDAO daos = new ContasRDAO(); // estou instanciando a conexão
            ContasR cliente = new ContasR();
            ContasR clientes = new ContasR();
            ClientesDAO dao = new ClientesDAO(); // estou instanciando a conexão
            Cliente aluno = new Cliente();
            ContratoDAO daoc = new ContratoDAO(); // estou instanciando a conexão
            Contrato contract = new Contrato();

            //int linha = masterTable.getSelectedRow();
            codigoFornecedor = masterTable.getValueAt(masterTable.getSelectedRow(), 8).hashCode();
            txtCodigo.setText(codigoFornecedor + "");
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigo.getText());
            cliente = daos.carregarContasContratoPeloCodigo(nome);
            txtCodigoCliente.setText("" + cliente.getCodigo_cliente());
            txtCodigoLocalidade.setText("" + cliente.getLocalidade());
            txtParcela.setText("" + cliente.getParcela());
            txtDataEmissao.setText("" + cliente.getData_emissao());
            txtDataEmissao.setText(txtDataEmissao.getText().replace("-", "/"));
            txtDataPagamento.setText("" + cliente.getData_pagamento());
            txtDataPagamento.setText(txtDataPagamento.getText().replace("/1/", "/01/"));

            String valorParcela = "" + cliente.getValor();
            //valorParcela = valorParcela.replace(".", "0");
            valorParcela = valorParcela.replace(",", ".");
            txtValorDaParcela.setText(valorParcela);

            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigoCliente.getText());
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(txtCodigoLocalidade.getText());
            aluno = dao.carregarClientePeloCodigo(nome, nomes);
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);

            if (!txtCodigoCliente.getText().equals("")) {
                txtNomeDoCliente.setText(aluno.getNome());
                txtCodigoLocalidade.setText(aluno.getLocalidade() + "");

                txtCelular.setText(aluno.getCelular());
                txtCodigoLocalidade.setText("" + aluno.getLocalidade());
            }

            if (txtNomeDoCliente.getText().equals("nulo")) {
                int codigoContrato = masterTable.getValueAt(masterTable.getSelectedRow(), 1).hashCode();
                contract = daoc.carregarContratoPeloCodigo("" + codigoContrato);
                txtCodigoCliente.setText("" + contract.getCodigo_aluno());
                txtCodigoLocalidade.setText("" + contract.getLocalidade());

                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigoCliente.getText());
                apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nomes.delete(0, apagar);
                nomes.append(txtCodigoLocalidade.getText());
                aluno = dao.carregarClientePeloCodigo(nome, nomes);
                txtNomeDoCliente.setText(aluno.getNome());
                txtCodigoLocalidade.setText(aluno.getLocalidade() + "");
                if (aluno.getLocalidade() == 0) {
                    txtCodigoLocalidade.setText("" + masterTable.getValueAt(masterTable.getSelectedRow(), 9).hashCode());
                    txtCodigoLocalidade.setEditable(true);
                }

                txtCelular.setText(aluno.getCelular());


                if (!txtNomeDoCliente.getText().equals("nulo")) {
                    cliente.setCodigo_cliente(Integer.parseInt(txtCodigoCliente.getText()));
                    cliente.setLocalidade(Integer.parseInt(txtCodigoLocalidade.getText()));
                    cliente.setCodigo_contrato(codigoContrato);
                    daos.atualizaDadosCliente(cliente);
                } else {

                    txtNomeDoCliente.setBackground(Color.red);

                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    nome.append(txtCodigoCliente.getText());
                    apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nomes.delete(0, apagar);
                    nomes.append(txtCodigoLocalidade.getText());
                    aluno = dao.carregarClienteInativoPeloCodigo(nome, nomes);
                    txtNomeDoCliente.setText(aluno.getNome());
                    txtCodigoLocalidade.setText(aluno.getLocalidade() + "");
                    if (aluno.getLocalidade() == 0) {
                        txtCodigoLocalidade.setText("" + masterTable.getValueAt(masterTable.getSelectedRow(), 9).hashCode());
                        txtCodigoLocalidade.setEditable(true);
                    }

                    txtCelular.setText(aluno.getCelular());

                    if (!txtNomeDoCliente.getText().equals("nulo")) {
                        cliente.setCodigo_cliente(Integer.parseInt(txtCodigoCliente.getText()));
                        cliente.setLocalidade(Integer.parseInt(txtCodigoLocalidade.getText()));
                        cliente.setCodigo_contrato(codigoContrato);
                        daos.atualizaDadosCliente(cliente);
                    }

                }
            }
            codigoFornecedor = masterTable.getValueAt(masterTable.getSelectedRow(), 1).hashCode();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(codigoFornecedor);
            clientes = daos.carregarContasContratoPeloCodigoParcelaUm(nome);
            txtParcelaUm.setText(clientes.getData_pagamento() + "");
            //IDADE
            int age = calculaIdade(txtParcelaUm.getText().trim(), "dd/MM/yyyy");
            txtParcelaUm.setText(txtParcelaUm.getText() + " - " + age + " Anos.");

            cliente = null;
            clientes = null;
            contract = null;
            aluno = null;
            dao.desconectar();
            daos.desconectar();
            daoc.desconectar();
        } catch (Exception b) {
            JOptionPane.showMessageDialog(null, b);
        }
    }//GEN-LAST:event_masterTableMouseReleased
    /**
     * @param args the command line arguments
     */
    public DefaultTableModel getDadosTabelaClientePesquisa() {
        NowString();
        NowStringInversa();
        try {
            ContasRDAO dao = new ContasRDAO();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(dataFormatada);
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(s);
           
            List<ContasR> lista = dao.pesquisacontasRe(nome, nomes);

            apagar = 0;
            Object[][] dados = new Object[lista.size()][10];

            for (int us = 0; us < lista.size(); us++) {

                if (lista.get(us).getTotal() != 1.00) {
                  if (lista.get(us).getData_pagamento().equals(s) || lista.get(us).getData_pagamento().equals(dataFormatada)) {

                    if (lista.get(us).getValor_pago() < lista.get(us).getValor()) {
                      totalAReceber += lista.get(us).getValor();
                }

                apagar++;
                dados[us][0] = lista.get(us).getCodigo_cliente();
                dados[us][1] = lista.get(us).getCodigo_contrato();
                dados[us][2] = lista.get(us).getParcela();
                dados[us][3] = lista.get(us).getData_emissao();
                dados[us][4] = lista.get(us).getData_pagamento();
                dados[us][5] = lista.get(us).getCodigo_turma();
                dados[us][6] = lista.get(us).getValor();
                dados[us][7] = lista.get(us).getValor_pago();
                dados[us][8] = lista.get(us).getCodigo();
                dados[us][9] = lista.get(us).getLocalidade();

            }
            }
            }
            lista = null;
            dao.desconectar();
            String[] nomeColunas = {"Cliente", "CONTRATO", "Parcela", "Emissão", "Vencimento", "Turma", "Valor", "Valor Pago", "Código", "Local"};
            return new DefaultTableModel(dados, nomeColunas);
        } catch (Exception e) {
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable masterTable;
    private converter.RowSorterToStringConverter rowSorterToStringConverter1;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoCliente;
    private javax.swing.JTextField txtCodigoLocalidade;
    private javax.swing.JTextField txtDataEmissao;
    private javax.swing.JTextField txtDataPagamento;
    private javax.swing.JTextField txtLocalizar;
    private javax.swing.JTextField txtNomeDoCliente;
    private javax.swing.JTextField txtParcela;
    private javax.swing.JTextField txtParcelaUm;
    private javax.swing.JTextField txtTotalAReceber;
    private javax.swing.JTextField txtTotalParcelasAPagar;
    private javax.swing.JTextField txtValorDaParcela;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
