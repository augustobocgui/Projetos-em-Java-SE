/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import Classes.Professor;
import Classes.Turmas;
import ClassesDAO.ProfessorDAO;
import ClassesDAO.TurmasDAO;
import Excessoes.BancoException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class Turma extends javax.swing.JFrame {

    StringBuffer nome = new StringBuffer();
    int apagar = 0;
    int codigo = 0;

    /**
     * Creates new form Turma
     */
    public Turma() throws BancoException {
        initComponents();

        //Para trocar função do Tab por Enter
        //HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        //conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        //this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);

        setLocationRelativeTo(null);
        txtNomeProfessor.setDocument(new LimiteCampos.FixedLengthDocument(100));
        txtCodigo.setDocument(new OnlyNumberField(11));
        txtCodigoProfessor.setDocument(new OnlyNumberField(11));

        try {
            TurmasDAO dao = new TurmasDAO();
            txtCodigo.setText("" + dao.gerarCodigoTurma());
            dao.desconectar();
        } catch (BancoException f) {
            f.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHI = new javax.swing.JFormattedTextField();
        txtHF = new javax.swing.JFormattedTextField();
        jCBDia = new javax.swing.JComboBox();
        txtNomeProfessor = new javax.swing.JTextField();
        jBuscarFuncionario = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoProfessor = new javax.swing.JTextField();
        txtCurso = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jBSalvar = new javax.swing.JButton();
        jBAlterar = new javax.swing.JButton();
        jBLimpar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jBBuscarTurma = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Turma");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel2.setText("Dia:");
        jLabel2.setToolTipText("Dia da semana.");

        jLabel3.setText("Horário: ");
        jLabel3.setToolTipText("Hora do início.");

        jLabel4.setText("Responsável: ");

        jLabel5.setText("às");

        try {
            txtHI.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        try {
            txtHF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jCBDia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBDia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo" }));
        jCBDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBDiaActionPerformed(evt);
            }
        });

        txtNomeProfessor.setEditable(false);
        txtNomeProfessor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jBuscarFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jBuscarFuncionario.setToolTipText("Buscar professor.");
        jBuscarFuncionario.setBorderPainted(false);
        jBuscarFuncionario.setContentAreaFilled(false);
        jBuscarFuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBuscarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuscarFuncionarioActionPerformed(evt);
            }
        });

        jLabel6.setText("Código:");

        txtCodigoProfessor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigoProfessor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoProfessor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoProfessorFocusLost(evt);
            }
        });
        txtCodigoProfessor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoProfessorKeyPressed(evt);
            }
        });

        txtCurso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setText("Curso: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomeProfessor))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(txtCodigoProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBuscarFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCBDia, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtHI, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtHF, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtCurso))))))
                .addGap(13, 13, 13))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtHF, txtHI});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCBDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtHI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtHF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigoProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jBuscarFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNomeProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtHF, txtHI});

        jBSalvar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jBSalvar.setText("Salvar");
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });

        jBAlterar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/view-refresh.png"))); // NOI18N
        jBAlterar.setText("Alterar");
        jBAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarActionPerformed(evt);
            }
        });

        jBLimpar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/draw-eraser (2).png"))); // NOI18N
        jBLimpar.setText("Limpar");
        jBLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimparActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel10.setText("* CAMPOS OBRIGATÓRIOS.");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel1.setText("Código:");
        jLabel1.setToolTipText("Turma.");

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jBBuscarTurma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jBBuscarTurma.setBorderPainted(false);
        jBBuscarTurma.setContentAreaFilled(false);
        jBBuscarTurma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscarTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarTurmaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBBuscarTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBBuscarTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBAlterar, jBLimpar, jBSalvar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBAlterar, jBLimpar, jBSalvar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCBDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBDiaActionPerformed

    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
        txtCodigo.setText(txtCodigo.getText().trim());
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtCodigo.getText());
        Professor professor = new Professor();
        Turmas turmass = new Turmas();
        try {
            TurmasDAO dao = new TurmasDAO(); // estou instanciando a conexão
            turmass = dao.carregarTurmaPeloCodigo(nome);
            dao.desconectar();
        } catch (BancoException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (turmass.getDia().equals("nulo")) {
            if (txtCodigo.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Campo Código tem que ser informado!\n"
                        + "Clique Incluir",
                        "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (txtHF.getText().equals("  :  ") || txtHI.getText().equals("  :  ")) {
                    JOptionPane.showMessageDialog(this, "Os campos HI e HF tem que ser informados!\n"
                            + "",
                            "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (txtNomeProfessor.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "Campo Responsável tem que ser informado!\n"
                                + "",
                                "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (txtCurso.getText().equals("")) {
                            JOptionPane.showMessageDialog(this, "Campo Curso tem que ser informado!\n"
                                    + "",
                                    "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                        } else {

                            txtCodigoProfessor.setText(txtCodigoProfessor.getText().trim());
                            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                            nome.delete(0, apagar);
                            nome.append(txtCodigoProfessor.getText());
                            try {
                                ProfessorDAO daos = new ProfessorDAO(); // estou instanciando a conexão
                                professor = daos.carregarProfessorPeloCodigo(nome);

                                if (professor.getNome().equals("nulo")) {
                                    JOptionPane.showMessageDialog(this,
                                            "O professor informado [" + nome + "] não consta no banco!");
                                    txtCodigoProfessor.setText("");
                                } else {

                                    //Turmas turmass = new Turmas();

                                    turmass.setCodigo(Integer.parseInt(txtCodigo.getText()));
                                    turmass.setDia(jCBDia.getSelectedItem().toString());
                                    turmass.setHi(txtHI.getText());
                                    turmass.setHf(txtHF.getText());
                                    turmass.setCurso(txtCurso.getText().trim());
                                    turmass.setFuncionario(Integer.parseInt(txtCodigoProfessor.getText()));
                                    //turma.setSituacao(jCBSituacao.getSelectedItem().toString());

                                    try {
                                        TurmasDAO dao = new TurmasDAO();
                                        dao.adicionarTurmas(turmass);
                                        JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");

                                        jCBDia.setSelectedItem("Segunda");
                                        txtHF.setText("");
                                        txtHI.setText("");
                                        txtNomeProfessor.setText("");
                                        txtCurso.setText("");
                                        try {
                                            txtCodigo.setText("" + dao.gerarCodigoTurma());
                                        } catch (Exception e) {
                                        }
                                        jCBDia.requestFocus();
                                        turmass = null;
                                        professor = null;
                                        dao.desconectar();
                                    } catch (BancoException e) {
                                        e.printStackTrace();
                                    }
                                }
                                daos.desconectar();
                            } catch (BancoException ex) {
                                Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "O sistema possuí já turma a cadastrada.",
                    "seeds",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBSalvarActionPerformed

    private void jBBuscarTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarTurmaActionPerformed
        jCBDia.requestFocus();
        try {
            TurmasDAO dao = new TurmasDAO(); // estou instanciando a conexão
            if (dao.gerarCodigoTurma() == 1) {
                JOptionPane.showMessageDialog(this,
                        "O sistema não possuí turma cadastrada.",
                        "seeds",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {

                final BuscarTurmas pesq = new BuscarTurmas("Turmas");
                pesq.setVisible(true);

                ActionListener acaoOk = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        StringBuffer codigoTurma = new StringBuffer();
                        int codigoCliente = 0;
                        codigoCliente = pesq.getCodigoCliente();
                        codigoTurma.append(codigoCliente);
                        try {
                            TurmasDAO dao = new TurmasDAO();
                            Turmas turmass = new Turmas();
                            turmass = dao.carregarTurmaPeloCodigo(codigoTurma);
                            txtCodigo.setText(String.valueOf(turmass.getCodigo()));
                            jCBDia.setSelectedItem(turmass.getDia());
                            txtHI.setText(turmass.getHi());
                            txtHF.setText(turmass.getHf());
                            txtCurso.setText(turmass.getCurso());
                            txtCodigoProfessor.setText(turmass.getFuncionario() + "");

                            txtCodigoProfessor.setText(txtCodigoProfessor.getText().trim());
                            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                            nome.delete(0, apagar);
                            nome.append(txtCodigoProfessor.getText());

                            ProfessorDAO daos = new ProfessorDAO(); // estou instanciando a conexão
                            Professor professor = new Professor();
                            professor = daos.carregarProfessorPeloCodigo(nome);
                            if (professor.getNome().equals("nulo")) {
                            } else {

                                txtCodigoProfessor.setText(String.valueOf(professor.getCodigo()));
                                txtNomeProfessor.setText(professor.getNome());

                            }
                            turmass = null;
                            professor = null;
                            dao.desconectar();
                            daos.desconectar();
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
    }//GEN-LAST:event_jBBuscarTurmaActionPerformed

    private void jBLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimparActionPerformed
        jCBDia.setSelectedItem("Segunda");
        txtHF.setText("");
        txtHI.setText("");
        txtCurso.setText("");
        txtNomeProfessor.setText("");
        txtCodigoProfessor.setText("");
        try {
            TurmasDAO dao = new TurmasDAO(); // estou instanciando a conexão
            txtCodigo.setText("" + dao.gerarCodigoTurma());
            dao.desconectar();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jBLimparActionPerformed

    private void jBAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarActionPerformed
        txtCodigo.setText(txtCodigo.getText().trim());
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtCodigo.getText());
        Professor professor = new Professor();
        Turmas turmass = new Turmas();
        try {
            ProfessorDAO daos = new ProfessorDAO(); // estou instanciando a conexão
            TurmasDAO dao = new TurmasDAO(); // estou instanciando a conexão
            turmass = dao.carregarTurmaPeloCodigo(nome);

            if (turmass.getDia().equals("nulo")) {
                JOptionPane.showMessageDialog(this,
                        "A Turma informada não consta no banco!");
            } else {

                txtCodigoProfessor.setText(txtCodigoProfessor.getText().trim());
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(txtCodigoProfessor.getText());
                try {
                    professor = daos.carregarProfessorPeloCodigo(nome);
                } catch (BancoException ex) {
                    Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (professor.getNome().equals("nulo")) {
                    JOptionPane.showMessageDialog(this,
                            "O professor informado [" + nome + "] não consta no banco!");
                    txtCodigoProfessor.setText("");
                } else {
                    turmass.setCodigo(Integer.parseInt(txtCodigo.getText()));
                    turmass.setDia(jCBDia.getSelectedItem().toString());
                    turmass.setHi(txtHI.getText());
                    turmass.setHf(txtHF.getText());
                    turmass.setCurso(txtCurso.getText().trim());
                    turmass.setFuncionario(Integer.parseInt(txtCodigoProfessor.getText()));
                    try {
                        dao.atualizaDados(turmass);
                        JOptionPane.showMessageDialog(this,
                                "Atualização efetuada com sucesso!");
                        turmass = null;
                        professor = null;
                    } catch (BancoException e) {
                        e.printStackTrace();
                    }

                }
            }
            daos.desconectar();
            dao.desconectar();
        } catch (BancoException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBAlterarActionPerformed

    private void jBuscarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuscarFuncionarioActionPerformed
        try {
            ProfessorDAO daos = new ProfessorDAO(); // estou instanciando a conexão
            if (daos.gerarCodigoProfessor() == 1) {
                JOptionPane.showMessageDialog(this,
                        "O sistema não possuí um professor cadastrado.",
                        "seeds",
                        JOptionPane.QUESTION_MESSAGE);
                txtNomeProfessor.requestFocus();
            } else {
                final BuscarProfessores pesq = new BuscarProfessores("Professores");
                pesq.setVisible(true);

                ActionListener acaoOk = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        StringBuffer codigoProfessor = new StringBuffer();
                        codigoProfessor.append(pesq.getCodigoProfessor());
                        try {
                            ProfessorDAO daos = new ProfessorDAO();
                            Professor professor = new Professor();
                            professor = daos.carregarProfessorPeloCodigo(codigoProfessor);
                            txtCodigoProfessor.setText(String.valueOf(professor.getCodigo()));
                            txtNomeProfessor.setText(professor.getNome());
                            professor = null;
                            daos.desconectar();
                        } catch (BancoException b) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        pesq.dispose();
                    }
                };
                pesq.setAcao(acaoOk);
            }
            daos.desconectar();
        } catch (BancoException ex) {
            Logger.getLogger(Professores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jBuscarFuncionarioActionPerformed

    private void txtCodigoProfessorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProfessorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCodigoProfessor.setText(txtCodigoProfessor.getText().trim());
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(txtCodigoProfessor.getText());
            try {
                Professor professor = new Professor();
                ProfessorDAO daos = new ProfessorDAO(); // estou instanciando a conexão
                professor = daos.carregarProfessorPeloCodigo(nome);
                if (professor.getNome().equals("nulo")) {
                    JOptionPane.showMessageDialog(this,
                            "O professor informado não consta no banco!");
                    txtCodigoProfessor.requestFocus();
                    txtNomeProfessor.setText("");
                    txtCodigoProfessor.setText("");
                } else {
                    txtCodigoProfessor.setText(String.valueOf(professor.getCodigo()));
                    txtNomeProfessor.setText(professor.getNome());
                }
                professor = null;
                daos.desconectar();
            } catch (BancoException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_txtCodigoProfessorKeyPressed

    private void txtCodigoProfessorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoProfessorFocusLost
        txtCodigoProfessor.setText(txtCodigoProfessor.getText().trim());
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(txtCodigoProfessor.getText());
        try {
            ProfessorDAO daos = new ProfessorDAO(); // estou instanciando a conexão
            Professor professor = new Professor();
            professor = daos.carregarProfessorPeloCodigo(nome);
            if (professor.getNome().equals("nulo")) {
                JOptionPane.showMessageDialog(this,
                        "O professor informado não consta no banco!");
                txtCodigoProfessor.requestFocus();
                txtNomeProfessor.setText("");
                txtCodigoProfessor.setText("");
            } else {
                txtCodigoProfessor.setText(String.valueOf(professor.getCodigo()));
                txtNomeProfessor.setText(professor.getNome());
            }
            professor = null;
            daos.desconectar();
        } catch (BancoException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtCodigoProfessorFocusLost
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAlterar;
    private javax.swing.JButton jBBuscarTurma;
    private javax.swing.JButton jBLimpar;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JButton jBuscarFuncionario;
    private javax.swing.JComboBox jCBDia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoProfessor;
    private javax.swing.JTextField txtCurso;
    private javax.swing.JFormattedTextField txtHF;
    private javax.swing.JFormattedTextField txtHI;
    private javax.swing.JTextField txtNomeProfessor;
    // End of variables declaration//GEN-END:variables
}
