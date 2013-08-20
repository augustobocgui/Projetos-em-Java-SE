/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class TestBackup extends javax.swing.JFrame {

    File bck;
    File bcks;
    int numerodobackup = 1;

    /**
     * Creates new form TestBackup
     */
    public TestBackup() {
        initComponents();
        setLocationRelativeTo(null);
        baseDeDadosEmUso();
        jButton1.requestFocus();
    }

    public void baseDeDadosEmUso() {
        String linha, linha2, linha3;
        try {
            FileReader arq = new FileReader("../seeds-java/basededados.txt");
            FileReader arq2 = new FileReader("../seeds-java/ip.txt");
            FileReader arq3 = new FileReader("../seeds-java/porta.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            BufferedReader lerArq2 = new BufferedReader(arq2);
            BufferedReader lerArq3 = new BufferedReader(arq3);
            linha = lerArq.readLine(); // lê a primeira linha
            linha2 = lerArq2.readLine(); // lê a primeira linha
            linha3 = lerArq3.readLine(); // lê a primeira linha
            while (linha != null) {
                txtNomeDaBaseDeDados.setText(linha);
                txtEnderecoIPv4.setText(linha2);
                txtPorta.setText(linha3);
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            arq.close();
            arq2.close();
            arq3.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo.\n",
                    e.getMessage());
        }

        String nomeDoArquivo = txtNomeDoArquivo.getText().trim();
        File diretorio = new File("C:/backup-seeds/");
        bck = new File("C:/backup-seeds/" + nomeDoArquivo + ".zip");
        bcks = new File("C:/backup-seeds/" + nomeDoArquivo + ".zip");
        if (!diretorio.isDirectory()) {
            new File("C:/backup-seeds/").mkdir();
            JOptionPane.showMessageDialog(rootPane, "Os arquivos de Backup seram salvos em:\n"
                    + "C:/backup-seeds/");
        } else {
        }
    }

    public void executar() {
        if (!bcks.isFile()) {
            String nomeBkp = "backup.zip";
            Backup b = new Backup();
            try {
                byte[] data = b.getData("" + txtEnderecoIPv4.getText().toString() + "", "" + txtPorta.getText().toString() + "", "" + txtUsuario.getText().toString() + "", "" + txtSenha.getText().toString() + "", "" + txtNomeDaBaseDeDados.getText().toString() + "").getBytes();
                //byte[] routine = b.getRoutine("" + txtEnderecoIPv4.getText().toString() + "", "" + txtPorta.getText().toString() + "", "" + txtUsuario.getText().toString() + "", "" + txtSenha.getText().toString() + "", "" + txtNomeDaBaseDeDados.getText().toString() + "").getBytes();
                File filedst = new File("C:/backup-seeds/" + nomeBkp);

                FileOutputStream dest = new FileOutputStream(filedst);
                ZipOutputStream zip = new ZipOutputStream(
                        new BufferedOutputStream(dest));
                zip.setMethod(ZipOutputStream.DEFLATED);
                zip.setLevel(Deflater.BEST_COMPRESSION);

                zip.putNextEntry(new ZipEntry("basededados.sql"));
                zip.write(data);

                //zip.putNextEntry(new ZipEntry("copia.sql"));
                //zip.write(routine);

                zip.close();
                dest.close();



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            while (bck.isFile()) {
                numerodobackup++;
                bck = new File("" + "C:/backup-seeds/" + "backup" + numerodobackup + ".zip");
            }
            Backup b = new Backup();
            try {
                byte[] data = b.getData("" + txtEnderecoIPv4.getText().toString() + "", "" + txtPorta.getText().toString() + "", "" + txtUsuario.getText().toString() + "", "" + txtSenha.getText().toString() + "", "" + txtNomeDaBaseDeDados.getText().toString() + "").getBytes();
                //byte[] routine = b.getRoutine("" + txtEnderecoIPv4.getText().toString() + "", "" + txtPorta.getText().toString() + "", "" + txtUsuario.getText().toString() + "", "" + txtSenha.getText().toString() + "", "" + txtNomeDaBaseDeDados.getText().toString() + "").getBytes();
                File filedst = new File("C:/backup-seeds/backup" + numerodobackup + ".zip");

                FileOutputStream dest = new FileOutputStream(filedst);
                ZipOutputStream zip = new ZipOutputStream(
                        new BufferedOutputStream(dest));
                zip.setMethod(ZipOutputStream.DEFLATED);
                zip.setLevel(Deflater.BEST_COMPRESSION);

                zip.putNextEntry(new ZipEntry("basededados.sql"));
                zip.write(data);

                //zip.putNextEntry(new ZipEntry("copia.sql"));
                //zip.write(routine);

                zip.close();
                dest.close();



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(rootPane, "Backup realizado com sucesso");
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
        txtUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        txtEnderecoIPv4 = new javax.swing.JTextField();
        txtPorta = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNomeDaBaseDeDados = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNomeDoArquivo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerar backup remoto");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Configuração - Banco de dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(null);

        txtUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtUsuario.setText("root");
        jPanel2.add(txtUsuario);
        txtUsuario.setBounds(88, 27, 102, 23);

        jLabel3.setText("Usuário:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(16, 33, 53, 14);

        jLabel4.setText("Senha:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(16, 74, 68, 14);

        txtSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSenha.setText("nuubpmuf");
        jPanel2.add(txtSenha);
        txtSenha.setBounds(88, 68, 102, 23);

        txtEnderecoIPv4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(txtEnderecoIPv4);
        txtEnderecoIPv4.setBounds(330, 27, 149, 23);

        txtPorta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(txtPorta);
        txtPorta.setBounds(88, 109, 102, 23);

        jLabel6.setText("Endereço IPv4:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(217, 33, 95, 14);

        jLabel7.setText("Porta:");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(16, 115, 68, 14);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cloud.png"))); // NOI18N
        jLabel5.setText("Backup remoto da base de dados");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(jLabel5);
        jLabel5.setBounds(200, 50, 299, 128);

        jLabel2.setText("Base de dados: ");

        txtNomeDaBaseDeDados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomeDaBaseDeDados.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomeDaBaseDeDadosFocusLost(evt);
            }
        });
        txtNomeDaBaseDeDados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeDaBaseDeDadosKeyTyped(evt);
            }
        });

        jLabel1.setText("Nome do arquivo: ");

        txtNomeDoArquivo.setEditable(false);
        txtNomeDoArquivo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomeDoArquivo.setText("backup");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeDoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeDaBaseDeDados, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNomeDoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNomeDaBaseDeDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Novo.png"))); // NOI18N
        jButton1.setText("Executar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/mysql-database-view.png"))); // NOI18N
        jButton3.setText("Gerenciador Mysql-Front");
        jButton3.setToolTipText("O MySQL-Front é programa gráfico para gerenciamento de banco de dados MySQL.");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/darkblue-folder-script.png"))); // NOI18N
        jButton4.setText("Diretório");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addContainerGap(14, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton3});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeDaBaseDeDadosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeDaBaseDeDadosFocusLost
        txtNomeDaBaseDeDados.setText(txtNomeDaBaseDeDados.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtNomeDaBaseDeDadosFocusLost

    private void txtNomeDaBaseDeDadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeDaBaseDeDadosKeyTyped
        txtNomeDaBaseDeDados.setText(txtNomeDaBaseDeDados.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtNomeDaBaseDeDadosKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        executar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            Runtime.getRuntime().exec("C:/Arquivos de Programas/MySQL-Front/MySQL-Front.exe"); //e assim
        } catch (Exception e) {
            int selection = JOptionPane.showConfirmDialog(this,
                    "Erro ao abrir Mysql-Front.\n"
                    + "Aplicação não encontrada!"
                    + "Deseja fazer o Download do aplicativo?",
                    "Seeds", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.OK_OPTION) {
                BareBonesBrowserLaunch.openURL("http://www.mysqlfront.de/");
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        File diretorio = new File("C:/backup-seeds/");
        if (!diretorio.isDirectory()) {
            new File("C:/backup-seeds/").mkdir();
            try {
                Runtime.getRuntime().exec("explorer C:\\backup-seeds");
            } catch (IOException ex) {
                Logger.getLogger(TestBackup.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Runtime.getRuntime().exec("explorer C:\\backup-seeds");
            } catch (IOException ex) {
                Logger.getLogger(TestBackup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_jButton1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RotinasDeSeguranca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RotinasDeSeguranca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RotinasDeSeguranca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RotinasDeSeguranca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestBackup().setVisible(true);
            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtEnderecoIPv4;
    private javax.swing.JTextField txtNomeDaBaseDeDados;
    private javax.swing.JTextField txtNomeDoArquivo;
    private javax.swing.JFormattedTextField txtPorta;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
