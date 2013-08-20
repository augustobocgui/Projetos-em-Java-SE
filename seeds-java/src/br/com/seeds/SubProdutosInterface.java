/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import Classes.Fornecedor;
import Classes.LimitadorMoeda;
import Classes.SubProdutos;
import ClassesDAO.FornecedorDAO;
import ClassesDAO.SubProdutosDAO;
import Excessoes.BancoException;
import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class SubProdutosInterface extends javax.swing.JFrame {

    /**
     * Creates new form SubProdutosInterface
     */
    StringBuffer nome = new StringBuffer();
    int apagar = 0;
    int Codigo;
    int codigo = 0;

    public SubProdutosInterface(int Codigo) throws SQLException {
        initComponents();

        //Para trocar função do Tab por Enter
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);

        setLocationRelativeTo(null);
        this.Codigo = Codigo;
        txtPrecoC.setDocument(new LimitadorMoeda());
        txtPrecoC.setText("0000");
        txtPrecoV.setDocument(new LimitadorMoeda());
        txtPrecoV.setText("0000");
        txtPrecoCompraGerencia.setDocument(new LimitadorMoeda());
        txtPrecoVendaGerencia.setDocument(new LimitadorMoeda());
        txtQtdeGerencia.setDocument(new OnlyNumberField(6));
        txtCodigoGerencia.setDocument(new OnlyNumberField(11));
        txtQtde.setDocument(new OnlyNumberField(6));
        txtQtdeM.setDocument(new OnlyNumberField(6));
        txtCodigo.setDocument(new OnlyNumberField(11));
        SetandoCodigo();
        if (Codigo > 0) {
            txtCodigoGerencia.setText("" + this.Codigo);
            try {
                SubProdutosDAO dao = new SubProdutosDAO();
                ResultSet PuxandoProduto = dao.SelecionandoSubprodutos(Integer.parseInt(txtCodigoGerencia.getText()));

                while (PuxandoProduto.next()) {
                    jCBUnidadeGerencia.setSelectedItem(PuxandoProduto.getString("Categoria"));
                    txtNomeGerencia.setText(PuxandoProduto.getString("Nome"));
                    if (PuxandoProduto.getInt("Retirada") == 1) {
                        jLabel5.setEnabled(true);
                        jLabel8.setEnabled(true);
                        txtQtdeGerencia.setEnabled(true);
                        txtQtedMGerencia.setEnabled(true);
                        txtQtdeGerencia.setText(PuxandoProduto.getString("Quantidade"));
                        txtQtedMGerencia.setText(PuxandoProduto.getString("QuantidadeMin"));
                    } else {
                        jLabel5.setEnabled(false);
                        jLabel8.setEnabled(false);
                        txtQtdeGerencia.setEnabled(false);
                        txtQtedMGerencia.setEnabled(false);
                        txtQtdeGerencia.setText("");
                        txtQtedMGerencia.setText("");
                    }
                    String ValorTotal1 = new DecimalFormat("0.00").format((PuxandoProduto.getFloat("PrecoCompra")));
                    ValorTotal1 = ValorTotal1.replace(".", "");
                    ValorTotal1 = ValorTotal1.replace(",", "");
                    txtPrecoCompraGerencia.setText(ValorTotal1);
                    String ValorTotal = new DecimalFormat("0.00").format((PuxandoProduto.getFloat("PrecoVenda")));
                    ValorTotal = ValorTotal.replace(".", "");
                    ValorTotal = ValorTotal.replace(",", "");
                    txtPrecoVendaGerencia.setText(ValorTotal);
                }
                PuxandoProduto.close();

            } catch (BancoException ex) {
                Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (Codigo == -1) {
            jTabbedPane1.setSelectedComponent(jPanel4);
        }
    }

    public void SetandoCodigo() throws SQLException {
        try {
            SubProdutosDAO dao = new SubProdutosDAO();
            ResultSet Selecionando = dao.CodigoSubprodutos();
            boolean Entrou = false;
            if (Selecionando.last()) {
                txtCodigo.setText("" + (Selecionando.getInt("Codigo") + 1));
                Entrou = true;
            }
            if (!Entrou) {
                txtCodigo.setText("1");
            }
            Selecionando.close();
        } catch (BancoException ex) {
            Logger.getLogger(SubProdutosInterface.class.getName()).log(Level.SEVERE, null, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        txtCodigoGerencia = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNomeGerencia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jCBUnidadeGerencia = new javax.swing.JComboBox();
        txtQtdeGerencia = new javax.swing.JTextField();
        txtPrecoCompraGerencia = new javax.swing.JTextField();
        txtQtedMGerencia = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPrecoVendaGerencia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtQtde = new javax.swing.JTextField();
        txtQtdeM = new javax.swing.JTextField();
        txtPrecoC = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtPrecoV = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        txtCodigoFornecedor = new javax.swing.JTextField();
        txtNomeFornecedor = new javax.swing.JTextField();
        txtCnpj = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Produtos");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(249, 249, 249));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/view-refresh.png"))); // NOI18N
        jButton1.setText("Aterar");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/edit-delete.png"))); // NOI18N
        jButton4.setText("Excluir");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/draw-eraser (2).png"))); // NOI18N
        jButton5.setText("Limpar");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jButton7.setText("Sair");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtCodigoGerencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigoGerencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoGerenciaKeyPressed(evt);
            }
        });

        jLabel4.setText("* Código ");

        txtNomeGerencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomeGerencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeGerenciaActionPerformed(evt);
            }
        });

        jLabel1.setText("Nome");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtCodigoGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtNomeGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jCBUnidadeGerencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBUnidadeGerencia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UNIDADES", "LITROS", "EDITAR" }));
        jCBUnidadeGerencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBUnidadeGerenciaActionPerformed(evt);
            }
        });

        txtQtdeGerencia.setEditable(false);
        txtQtdeGerencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtPrecoCompraGerencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtQtedMGerencia.setEditable(false);
        txtQtedMGerencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setText("* Preço Venda     R$:");

        jLabel5.setText("Quantidade");
        jLabel5.setEnabled(false);

        txtPrecoVendaGerencia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setText("Quantidade Mínima");
        jLabel8.setEnabled(false);

        jLabel3.setText("Tipo");

        jLabel6.setText("*Preço Compra   R$:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(txtQtdeGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCBUnidadeGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrecoCompraGerencia, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(txtPrecoVendaGerencia)
                            .addComponent(txtQtedMGerencia))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBUnidadeGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtQtdeGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtQtedMGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPrecoCompraGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPrecoVendaGerencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Gerência", jPanel3);

        jPanel4.setBackground(new java.awt.Color(249, 249, 249));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jButton2.setText("Cadastrar");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/draw-eraser (2).png"))); // NOI18N
        jButton6.setText("Limpar");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jButton8.setText("Sair");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel9.setText("Código");

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel11.setText("  Nome");

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel15.setText("Preço Venda     R$");

        jLabel10.setText("Categoria");

        txtQtde.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtQtdeM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtPrecoC.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel12.setText("Quantidade ");

        jLabel13.setText("Quantidade Mínima ");

        txtPrecoV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UNIDADES", "LITROS", "EDITAR" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel14.setText("Preço Compra   R$ ");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Fornecedor"));

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jButton10.setBorderPainted(false);
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        txtCodigoFornecedor.setEditable(false);
        txtCodigoFornecedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtNomeFornecedor.setEditable(false);
        txtNomeFornecedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtCnpj.setEditable(false);
        txtCnpj.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel18.setText("Nome");

        jLabel19.setText("Código");

        jLabel20.setText("CNPJ");

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/draw-eraser (2).png"))); // NOI18N
        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNomeFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(txtCnpj))
                        .addGap(21, 21, 21))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtCodigoFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel18, jLabel19, jLabel20});

        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton10)
                        .addComponent(jButton9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel18, jLabel19, jLabel20});

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecoC, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecoV, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQtde, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(txtQtdeM)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel12, jLabel13});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQtde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQtdeM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtPrecoC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtPrecoV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel13});

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(291, 291, 291))
        );

        jTabbedPane1.addTab("Cadastro", jPanel4);

        jPanel1.add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 580, 350);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // clicou em buscar
        BuscarSubprodutos Busca;
        try {
            Busca = new BuscarSubprodutos();
            Busca.setVisible(true);
            this.dispose();
        } catch (BancoException ex) {
            Logger.getLogger(SubProdutosInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Cadastrar
        if (jComboBox2.getSelectedItem().equals(" ")) {
            JOptionPane.showMessageDialog(this, "Categoria tem de ser preenchida!");
            jComboBox2.requestFocus();
        } else if (txtQtde.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Quantidade tem de ser preenchido!");
            txtQtde.requestFocus();
        } else if (txtQtdeM.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Quantidade Mínima tem de ser preenchido!");
            txtQtdeM.requestFocus();
        } else if (txtNome.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nome tem de ser preenchido!");
            txtNome.requestFocus();
        } else if (txtPrecoC.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Preço de compra tem de ser preenchido!");
            txtPrecoC.requestFocus();
        } else if (txtPrecoV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Preço de venda tem de ser preenchido!");
            txtPrecoV.requestFocus();
        } else if (Float.parseFloat(txtPrecoV.getText().replace(".", "").replace(",", ".")) < Float.parseFloat(txtPrecoC.getText().replace(".", "").replace(",", "."))) {
            JOptionPane.showMessageDialog(this, "O preço de compra não pode ser maior que o de venda!");
            txtPrecoVendaGerencia.requestFocus();
        } else {
            SubProdutos sp = new SubProdutos();
            try {
                SubProdutosDAO Inserindo = new SubProdutosDAO();
                Inserindo.AtualizandoAI(Integer.parseInt(txtCodigo.getText()));
                sp.setCategoria("" + jComboBox2.getSelectedItem());
                sp.setNome(txtNome.getText());
                sp.setRetirada(1);
                sp.setQuantidade(Integer.parseInt(txtQtde.getText()));
                sp.setQuantidadeMin(Integer.parseInt(txtQtdeM.getText()));
                sp.setPrecoCompra(Float.parseFloat(txtPrecoC.getText().replace(".", "").replace(",", ".")));
                sp.setPrecoVenda(Float.parseFloat(txtPrecoV.getText().replace(".", "").replace(",", ".")));

                Inserindo.InserindoSubProduto(sp);
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                txtNome.setText("");
                txtQtde.setText("");
                txtQtdeM.setText("");
                jComboBox2.setSelectedItem(" ");
                txtPrecoC.setText("");
                txtPrecoV.setText("");
                try {
                    SetandoCodigo();
                } catch (SQLException ex) {
                    Logger.getLogger(SubProdutosInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
                Inserindo.desconectar();
            } catch (BancoException ex) {
                Logger.getLogger(SubProdutosInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // Clicou em limpar
        txtNome.setText("");
        txtQtde.setText("");
        txtQtdeM.setText("");
        jComboBox2.setSelectedItem(" ");
        txtPrecoC.setText("");
        txtPrecoV.setText("");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // sair
        SubProdutosInterface.this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtCodigoGerenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoGerenciaKeyPressed
        int co = evt.getKeyCode();

        if (co == 10) {
            boolean Achou = false;
            try {
                SubProdutosDAO dao = new SubProdutosDAO();
                ResultSet PuxandoProduto = dao.SelecionandoSubprodutos(Integer.parseInt(txtCodigoGerencia.getText()));

                while (PuxandoProduto.next()) {
                    jCBUnidadeGerencia.setSelectedItem(PuxandoProduto.getString("Categoria"));
                    txtNomeGerencia.setText(PuxandoProduto.getString("Nome"));
                    if (PuxandoProduto.getInt("Retirada") == 1) {
                        jLabel5.setEnabled(true);
                        jLabel8.setEnabled(true);
                        txtQtdeGerencia.setEnabled(true);
                        txtQtedMGerencia.setEnabled(true);
                        txtQtdeGerencia.setText(PuxandoProduto.getString("Quantidade"));
                        txtQtedMGerencia.setText(PuxandoProduto.getString("QuantidadeMin"));
                    } else {
                        jLabel5.setEnabled(false);
                        jLabel8.setEnabled(false);
                        txtQtdeGerencia.setEnabled(false);
                        txtQtedMGerencia.setEnabled(false);
                        txtQtdeGerencia.setText("");
                        txtQtedMGerencia.setText("");
                    }
                    String ValorTotal1 = new DecimalFormat("0.00").format((PuxandoProduto.getFloat("PrecoCompra")));
                    ValorTotal1 = ValorTotal1.replace(".", "");
                    ValorTotal1 = ValorTotal1.replace(",", "");
                    txtPrecoCompraGerencia.setText(ValorTotal1);
                    String ValorTotal = new DecimalFormat("0.00").format((PuxandoProduto.getFloat("PrecoVenda")));
                    ValorTotal = ValorTotal.replace(".", "");
                    ValorTotal = ValorTotal.replace(",", "");
                    txtPrecoVendaGerencia.setText(ValorTotal);
                    Achou = true;
                }
                PuxandoProduto.close();

                if (!Achou) {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado!");
                }
            } catch (BancoException ex) {
                Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtCodigoGerenciaKeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //LIMPAR
        txtCodigoGerencia.setText("");
        txtNomeGerencia.setText("");
        jCBUnidadeGerencia.setSelectedItem("");
        txtQtdeGerencia.setText("");
        txtQtedMGerencia.setText("");
        txtQtdeGerencia.setEnabled(false);
        txtQtedMGerencia.setEnabled(false);
        txtPrecoCompraGerencia.setText("");
        txtPrecoVendaGerencia.setText("");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // sair
        SubProdutosInterface.this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // ALTERAR
        Object[] options = {"Sim", "Não"};
        int n = JOptionPane.showOptionDialog(null,
                "Tem certeza que deseja alterar?",
                "Confirmação", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (n == 0) {
            if (txtCodigoGerencia.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "É necessário preencher o código!");
                txtCodigoGerencia.requestFocus();
            } else if (jCBUnidadeGerencia.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(this, "É necessário preencher a Categoria!");
                jCBUnidadeGerencia.requestFocus();
            } else if (txtNomeGerencia.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "É necessário preencher o Nome!");
                txtNomeGerencia.requestFocus();
            } else if (txtPrecoCompraGerencia.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "É necessário preencher o Preço de compra!");
                txtPrecoCompraGerencia.requestFocus();
            } else if (txtPrecoVendaGerencia.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "É necessário preencher o Preço de venda!");
                txtPrecoVendaGerencia.requestFocus();
            } else if (Float.parseFloat(txtPrecoVendaGerencia.getText().replace(".", "").replace(",", ".")) < Float.parseFloat(txtPrecoCompraGerencia.getText().replace(".", "").replace(",", "."))) {
                JOptionPane.showMessageDialog(this, "O preço de compra não pode ser maior que o de venda!");
                txtPrecoVendaGerencia.requestFocus();
            } else {
                SubProdutosDAO Inserindo;
                try {
                    Inserindo = new SubProdutosDAO();
                    SubProdutos sp = new SubProdutos();
                    sp.setCategoria("" + jCBUnidadeGerencia.getSelectedItem());
                    sp.setNome(txtNomeGerencia.getText());
                    if (txtQtdeGerencia.isEnabled()) {
                        sp.setRetirada(1);
                        sp.setQuantidade(Integer.parseInt(txtQtdeGerencia.getText()));
                        sp.setQuantidadeMin(Integer.parseInt(txtQtedMGerencia.getText()));
                    } else {
                        sp.setRetirada(0);
                        sp.setQuantidade(0);
                        sp.setQuantidadeMin(0);
                    }
                    sp.setPrecoCompra(Float.parseFloat(txtPrecoCompraGerencia.getText().replace(".", "").replace(",", ".")));
                    sp.setPrecoVenda(Float.parseFloat(txtPrecoVendaGerencia.getText().replace(".", "").replace(",", ".")));
                    sp.setCodigo(Integer.parseInt(txtCodigoGerencia.getText()));

                    Inserindo.AtualizandoSubproduto(sp);
                    JOptionPane.showMessageDialog(this, "Atualização realizada com sucesso!");
                    txtCodigoGerencia.setText("");
                    txtNomeGerencia.setText("");
                    jCBUnidadeGerencia.setSelectedItem("");
                    txtQtdeGerencia.setText("");
                    txtQtedMGerencia.setText("");
                    txtQtdeGerencia.setEnabled(false);
                    txtQtedMGerencia.setEnabled(false);
                    txtPrecoCompraGerencia.setText("");
                    txtPrecoVendaGerencia.setText("");
                    try {
                        SetandoCodigo();
                    } catch (SQLException ex) {
                        Logger.getLogger(SubProdutosInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sp = null;
                    Inserindo.desconectar();
                } catch (BancoException ex) {
                    Logger.getLogger(SubProdutosInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // EXCLUIR
        if (txtCodigoGerencia.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "É necessário preencher o código!");
            txtCodigoGerencia.requestFocus();
        } else {
            Object[] options = {"Sim", "Não"};
            int n = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja excluir?",
                    "Confirmação", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (n == 0) {
                try {
                    SubProdutosDAO dao = new SubProdutosDAO();
                    dao.deletarSubProduto(Integer.parseInt(txtCodigoGerencia.getText()));
                    JOptionPane.showMessageDialog(this, "Produto deletado com sucesso!");
                    txtCodigoGerencia.setText("");
                    txtNomeGerencia.setText("");
                    jCBUnidadeGerencia.setSelectedItem("");
                    txtQtdeGerencia.setText("");
                    txtQtedMGerencia.setText("");
                    txtQtdeGerencia.setEnabled(false);
                    txtQtedMGerencia.setEnabled(false);
                    txtPrecoCompraGerencia.setText("");
                    txtPrecoVendaGerencia.setText("");
                    try {
                        SetandoCodigo();
                    } catch (SQLException ex) {
                        Logger.getLogger(SubProdutosInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dao.desconectar();
                } catch (BancoException ex) {
                    Logger.getLogger(SubProdutosInterface.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtNomeGerenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeGerenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeGerenciaActionPerformed

    private void jCBUnidadeGerenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBUnidadeGerenciaActionPerformed
        if (jCBUnidadeGerencia.getSelectedItem().toString().equals("EDITAR")) {
            jCBUnidadeGerencia.setEditable(true);
            jCBUnidadeGerencia.setSelectedItem("");
        } else {
            jCBUnidadeGerencia.setEditable(false);
        }
    }//GEN-LAST:event_jCBUnidadeGerenciaActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        if (jComboBox2.getSelectedItem().toString().equals("EDITAR")) {
            jComboBox2.setEditable(true);
            jComboBox2.setSelectedItem("");
        } else {
            jComboBox2.setEditable(false);
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        if (txtCodigo.getText().equals("")) {
            //SubProdutos clientes = new SubProdutos();
            try {
                SubProdutosDAO dao = new SubProdutosDAO(); // estou instanciando a conexão
                txtCodigo.setText("" + dao.gerarCodigoSubProduto());
                txtNome.setText("");
                jComboBox2.setSelectedItem("UNIDADES");
                txtQtde.setText("");
                txtQtdeM.setText("");
                txtPrecoC.setText(null);
                txtPrecoV.setText(null);
                dao.desconectar();
            } catch (BancoException e) {
                e.printStackTrace();
            }
        }
        txtCodigo.setText(txtCodigo.getText().trim());
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtCodigo.getText());
        //String testNomeCase = txtNome.getText();
        SubProdutos cliente = new SubProdutos();
        try {
            SubProdutosDAO dao = new SubProdutosDAO(); // estou instanciando a conexão
            cliente = dao.carregarProdutoPeloCodigo(nome + "");
            if (cliente.getNome().equals("nulo")) {
                //JOptionPane.showMessageDialog(rootPane, "O Produto informado não consta no banco!");
            } else {
                int selection = JOptionPane.showConfirmDialog(this,
                        "O Produto informado consta no banco!\n"
                        + "Deseja carregar os dados do produto?",
                        "seeds", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    txtCodigo.setText(String.valueOf(cliente.getCodigo()));
                    txtNome.setText(cliente.getNome());
                    jComboBox2.setSelectedItem(cliente.getCategoria());
                    txtQtde.setText("" + cliente.getQuantidade());
                    txtQtdeM.setText("" + cliente.getQuantidade());
                    String Preco = "" + cliente.getPrecoCompra();
                    Preco = Preco.replace(".", "");
                    txtPrecoC.setText(Preco);
                    Preco = "" + cliente.getPrecoVenda();
                    Preco = Preco.replace(".", "");
                    txtPrecoV.setText(Preco);
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                } else {
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    txtCodigo.setText("" + dao.gerarCodigoSubProduto());
                }
            }
            cliente = null;
            dao.desconectar();
        } catch (BancoException e) {
            e.printStackTrace();
        }
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
    }//GEN-LAST:event_txtCodigoFocusLost

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        try {
            FornecedorDAO daos = new FornecedorDAO();
            codigo = daos.gerarCodigoFornecedor();
            daos.desconectar();
        } catch (BancoException f) {
            f.printStackTrace();
        }

        if (codigo == 1) {
            JOptionPane.showMessageDialog(this,
                    "O sistema não possuí um fornecedor cadastrado.",
                    "seeds",
                    JOptionPane.INFORMATION_MESSAGE);
            txtNome.requestFocus();
        } else {
            final BuscarFornecedores pesq = new BuscarFornecedores("Fornecedores");
            pesq.setVisible(true);

            ActionListener acaoOk = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    StringBuffer codigoFornecedor = new StringBuffer();
                    codigoFornecedor.append(pesq.getCodigoFornecedor());
                    try {
                        FornecedorDAO daos = new FornecedorDAO();
                        Fornecedor fornecedor = new Fornecedor();
                        fornecedor = daos.carregarFornecedorPeloCodigo(codigoFornecedor);
                        txtCodigoFornecedor.setText(String.valueOf(fornecedor.getCodigo()));
                        txtNomeFornecedor.setText(fornecedor.getNome());
                        if (txtNomeFornecedor.getText().equals("nulo")) {
                            txtCodigoFornecedor.setText("");
                            txtNomeFornecedor.setText("");
                            txtCnpj.setText("");
                        }
                        txtCnpj.setText(fornecedor.getCnpj());
                        daos.desconectar();
                    } catch (BancoException b) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                    pesq.dispose();
                }
            };
            pesq.setAcao(acaoOk);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        txtCodigoFornecedor.setText("");
        txtNomeFornecedor.setText("");
        txtCnpj.setText("");
    }//GEN-LAST:event_jButton9ActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jCBUnidadeGerencia;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtCnpj;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoFornecedor;
    private javax.swing.JTextField txtCodigoGerencia;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNomeFornecedor;
    private javax.swing.JTextField txtNomeGerencia;
    private javax.swing.JTextField txtPrecoC;
    private javax.swing.JTextField txtPrecoCompraGerencia;
    private javax.swing.JTextField txtPrecoV;
    private javax.swing.JTextField txtPrecoVendaGerencia;
    private javax.swing.JTextField txtQtde;
    private javax.swing.JTextField txtQtdeGerencia;
    private javax.swing.JTextField txtQtdeM;
    private javax.swing.JTextField txtQtedMGerencia;
    // End of variables declaration//GEN-END:variables
}
