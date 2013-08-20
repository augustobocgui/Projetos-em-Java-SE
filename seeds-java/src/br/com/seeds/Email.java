/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import Classes.Cliente;
import Classes.Emails;
import ClassesDAO.ClientesDAO;
import ClassesDAO.EmailsDAO;
import ClassesDAO.LocalidadeDAO;
import Excessoes.BancoException;
import java.awt.Desktop;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Guilherme
 */
public class Email extends javax.swing.JFrame {

    StringBuffer nome = new StringBuffer();
    StringBuffer nomes = new StringBuffer();
    StringBuffer nomeEmail = new StringBuffer();
    String data = "";
    String separador = "";
    int apagar = 0;
    int MaxEmails = 0;
    Emails ind = new Emails();
    List<Emails> populacaoOriginal = new ArrayList<Emails>();
    private List<Emails> populacao = new ArrayList();
    public ClientesDAO dao;
    public Cliente cliente = new Cliente();
    public EmailsDAO daos;
    public Emails email = new Emails();
    int localidades = 0;

    /**
     * Creates new form Email
     */
    public Email() throws BancoException {
        initComponents();
        txtCodigoLocalidade.setDocument(new LimiteCampos.FixedLengthDocument(11));
        setLocationRelativeTo(null);
        dao = new ClientesDAO();
        daos = new EmailsDAO();

        MaxEmails = daos.gerarCodigoEmails();
        try {
            LocalidadeDAO dao = new LocalidadeDAO();
            txtCodigoLocalidade.setText(String.valueOf(dao.gerarCodigoLocalidade() - 1));
            dao.desconectar();
        } catch (BancoException f) {
            f.printStackTrace();
        }
    }

    public void geraEmails() throws BancoException {//preencher com inteiros cada posições do vetor (Emails)


        int tp = MaxEmails;
        String valor;
        for (int i = 0; i < tp; i++) {
            Emails indiv = new Emails();


            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(MaxEmails);
            localidades = Integer.parseInt(txtCodigoLocalidade.getText());
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(localidades);

            cliente = dao.carregarClientePeloCodigo(nome, nomes);
            MaxEmails = MaxEmails - 1;
            if (cliente.getNome().equals("nulo")) {
                //JOptionPane.showMessageDialog(rootPane, "O Email informado não consta no banco!");
            } else {
                apagar = nomeEmail.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nomeEmail.delete(0, apagar);
                nomeEmail.append(cliente.getEmail());


                int l = 0;
                while (l < 1) {
                    String ind = String.valueOf(nomeEmail);
                    valor = ind;
                    if (!indiv.getGen().contains(valor)) {
                        indiv.getGen().add(valor);
                    } else {
                        l--;
                    }
                    l++;
                }

                populacao.add(indiv);
                populacaoOriginal = populacao;
            }
        }
    }

    public DefaultTableModel getEmails() {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("Emails.txt"));
        } catch (IOException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        int numeroLinhas = populacaoOriginal.size();
        int fim = populacaoOriginal.size();
        int numeroColunas = 1;

        Object[][] dados = new Object[numeroLinhas][numeroColunas];

        for (int i = 0; i < populacaoOriginal.size(); i++) {
            dados[i][0] = populacaoOriginal.get(i).getGen().toString();
            try {
                String teste = populacaoOriginal.get(i).getGen().toString();
                teste = teste.replace("[", "").replace("]", "").replace("[null]", "");
                if (teste.equals("null")) {
                    teste = "";
                } else {
                    out.write(separador + teste); // "" = quebra de linha no Windows
                }


                fim--;
                if (fim == 0) {
                    //System.out.println("Acabei de escrever no arquivo");
                    out.close();

                }
            } catch (IOException e) {
                // possiveis erros são tratados aqui  
            }
        }
        String[] nomeColunas = {"EMAILs"};
        return new DefaultTableModel(dados, nomeColunas);
    }

    public void limpar() {
        populacao.clear();
        populacaoOriginal.clear();
        txtTabela1.setModel(getEmails());
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTabela1 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jBOutLook = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtCodigoLocalidade = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Exportar E-mail");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTabela1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtTabela1.setModel(getEmails());
        txtTabela1.setToolTipText("");
        jScrollPane1.setViewportView(txtTabela1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 360, 390));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/gnome-reboot (2).png"))); // NOI18N
        jButton7.setToolTipText("Atualizar interface.");
        jButton7.setBorderPainted(false);
        jButton7.setContentAreaFilled(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 35, 33));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setToolTipText("Gerar lista dos e-mails.");
        jPanel2.setOpaque(false);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Novo.png"))); // NOI18N
        jButton5.setText("Gerar com [ ]");
        jButton5.setToolTipText("Dados indisponíveis para está versão.");
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Novo.png"))); // NOI18N
        jButton4.setText("Gerar com [;]");
        jButton4.setToolTipText("Dados indisponíveis para está versão.");
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Novo.png"))); // NOI18N
        jButton1.setText("Gerar com [,]");
        jButton1.setToolTipText("Dados indisponíveis para está versão.");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton4, jButton5});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton4, jButton5});

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 400, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setOpaque(false);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/icone-gmail.png"))); // NOI18N
        jButton8.setToolTipText("Abrir o navegador com URL: http://www.gmail.com.br");
        jButton8.setBorderPainted(false);
        jButton8.setContentAreaFilled(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/msn_password.png"))); // NOI18N
        jButton9.setToolTipText("Abrir o navegador com URL: http://www.msn.com.br");
        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Yahoo-Messenger.png"))); // NOI18N
        jButton10.setToolTipText("Abrir navegador com URL: http://br.yahoo.com/");
        jButton10.setBorderPainted(false);
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/google-chrome.png"))); // NOI18N
        jButton11.setToolTipText("Abre o navegador.");
        jButton11.setBorderPainted(false);
        jButton11.setContentAreaFilled(false);
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jBOutLook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/outlook.png"))); // NOI18N
        jBOutLook.setBorderPainted(false);
        jBOutLook.setContentAreaFilled(false);
        jBOutLook.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBOutLook.setEnabled(false);
        jBOutLook.setFocusable(false);
        jBOutLook.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBOutLook.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBOutLook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOutLookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBOutLook, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBOutLook, jButton10, jButton11, jButton8, jButton9});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBOutLook, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBOutLook, jButton10, jButton11, jButton8, jButton9});

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 400, 90));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setOpaque(false);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/document-print-preview.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/document-print-direct.png"))); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setEnabled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/document-print.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/gmail.png"))); // NOI18N
        jButton12.setToolTipText("Informa os dados da conta de e-mail de Administração.");
        jButton12.setBorderPainted(false);
        jButton12.setContentAreaFilled(false);
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3, jButton6});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton12)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3, jButton6});

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 400, 80));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/mailing.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 360, 300));
        jPanel1.add(txtCodigoLocalidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 120, -1));

        jLabel2.setText("Código do Local:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jBOutLook.setEnabled(false);
        JOptionPane.showMessageDialog(rootPane, "Está operação pode demorar.\n"
                + "Caso isso ocorra clique em Atualizar interface.\n"
                + "Carregando todos os emails.",
                "Seeds", JOptionPane.WARNING_MESSAGE);
        String caminho = "../seeds-java/";
        /*
         * mudei aqui para que o arquivo fosse composto de diretório + separador
         * (que pode ser / ou \) + nome do arquivo
         */
        File file = new File(caminho + File.separator + "Emails.txt");
        if (!file.exists()) {
            //System.out.println("arquivo não existe");
            //System.out.println("criando arquivo File.txt em..." + caminho);
            try {
                //Aqui é o que falta  
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println("arquivo Emails.txt, criado em" + "caminho");
        }
        try {
            separador = ",";
            geraEmails();
            txtTabela1.setVisible(true);
            txtTabela1.setModel(getEmails());
        } catch (BancoException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        PrinterMatrix printer = new PrinterMatrix();
        //printer.printTextLinCol(10, 8, String.valueOf("teste"));
        printer.toFile("Emails.txt");
        String arg[] = {""};
        ImpressaoView.main(arg);
        //new ImpressaoView().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String caminho = "../seeds-java/";
        File file = new File(caminho + File.separator + "Emails.txt");
        if (!file.exists()) {
            //System.out.println("arquivo não existe");
            //System.out.println("criando arquivo File.txt em..." + caminho);
            try {
                //Aqui é o que falta  
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println("arquivo Emails.txt, criado em" + "caminho");
        }
        //---- out.write(Final); // "" = quebra de linha no Windows
        //System.out.println("Acabei de escrever no arquivo");
        //out.close();
        File Emails = new File("Emails.txt");
        try {
            Desktop.getDesktop().print(Emails);
        } catch (IOException ex) {
            Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
         * File Emails = new File("Emails.txt"); try {
         * Desktop.getDesktop().print(Emails); } catch (IOException ex) {
         * Logger.getLogger(ContasReceberContratos.class.getName()).log(Level.SEVERE,
         * null, ex); }
         *
         */
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jBOutLook.setEnabled(true);
        JOptionPane.showMessageDialog(rootPane, "Está operação pode demorar.\n"
                + "Caso isso ocorra clique em Atualizar interface.\n"
                + "Carregando todos os emails.",
                "Seeds", JOptionPane.WARNING_MESSAGE);
        String caminho = "../seeds-java/";
        /*
         * mudei aqui para que o arquivo fosse composto de diretório + separador
         * (que pode ser / ou \) + nome do arquivo
         */
        File file = new File(caminho + File.separator + "Emails.txt");
        if (!file.exists()) {
            //System.out.println("arquivo não existe");
            //System.out.println("criando arquivo File.txt em..." + caminho);
            try {
                //Aqui é o que falta  
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println("arquivo Emails.txt, criado em" + "caminho");
        }
        try {
            separador = ";";
            geraEmails();
            txtTabela1.setVisible(true);
            txtTabela1.setModel(getEmails());
        } catch (BancoException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jBOutLook.setEnabled(false);
        JOptionPane.showMessageDialog(rootPane, "Está operação pode demorar.\n"
                + "Caso isso ocorra clique em Atualizar interface.\n"
                + "Carregando todos os emails.",
                "Seeds", JOptionPane.WARNING_MESSAGE);
        String caminho = "../seeds-java/";
        /*
         * mudei aqui para que o arquivo fosse composto de diretório + separador
         * (que pode ser / ou \) + nome do arquivo
         */
        File file = new File(caminho + File.separator + "Emails.txt");
        if (!file.exists()) {
            //System.out.println("arquivo não existe");
            //System.out.println("criando arquivo File.txt em..." + caminho);
            try {
                //Aqui é o que falta  
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println("arquivo Emails.txt, criado em" + "caminho");
        }
        try {
            separador = " ";
            geraEmails();
            txtTabela1.setVisible(true);
            txtTabela1.setModel(getEmails());
        } catch (BancoException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Email.this.dispose();
        Email Novo;
        try {
            Novo = new Email();
            Novo.setVisible(true);
        } catch (BancoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        BareBonesBrowserLaunch.openURL("http://www.gmail.com.br");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        BareBonesBrowserLaunch.openURL("http://www.msn.com.br");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        BareBonesBrowserLaunch.openURL("http://br.yahoo.com/");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        BareBonesBrowserLaunch.openURL("http://");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jBOutLookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOutLookActionPerformed
        try {
            String contatos = carregaArquivo("Emails.txt");
            // Runtime.getRuntime().exec("cmd.exe /c start Outlook.exe /c ipm.note /m usuario@gmail.com");
            Runtime.getRuntime().exec("cmd.exe /c start Outlook.exe /c ipm.note /m " + contatos + "");
            //Runtime.getRuntime().exec("cmd.exe /c start Outlook.exe /c ipm.note /m usuario@dominio");
        } catch (IOException ex) {
            Logger.getLogger(ImpressaoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBOutLookActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        new SolicitacaoSenhaAdministradorEmail(null, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed

    private static String carregaArquivo(String arquivo)
            throws FileNotFoundException, IOException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return null;
        }
        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        StringBuffer bufSaida = new StringBuffer();
        String linha;
        while ((linha = br.readLine()) != null) {
            bufSaida.append(linha + "\n");
        }
        br.close();
        return bufSaida.toString();
    }

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBOutLook;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCodigoLocalidade;
    private javax.swing.JTable txtTabela1;
    // End of variables declaration//GEN-END:variables

}
