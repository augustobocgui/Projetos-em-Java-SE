/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import Classes.Usuario;
import ClassesDAO.UsuarioDAO;
import Excessoes.BancoException;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class SolicitacaoSenhaNovoAdministrador extends javax.swing.JDialog {

    String s, f;

    /**
     * Creates new form SolicitacaoSenhaContrato
     */
    public SolicitacaoSenhaNovoAdministrador(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Random generator = new Random();
        int randomIndex = generator.nextInt(5) - 1;
        if (randomIndex == -1) {
            jLLoginMetro1.setVisible(true);
            jLLoginMetro2.setVisible(false);
            jLLoginMetro3.setVisible(false);
            jLLoginMetro4.setVisible(false);
            jLLoginMetro5.setVisible(false);
            jLLoginMetro6.setVisible(false);
        } else if (randomIndex == 0) {
            jLLoginMetro1.setVisible(false);
            jLLoginMetro2.setVisible(true);
            jLLoginMetro3.setVisible(false);
            jLLoginMetro4.setVisible(false);
            jLLoginMetro5.setVisible(false);
            jLLoginMetro6.setVisible(false);
        } else if (randomIndex == 1) {
            jLLoginMetro1.setVisible(false);
            jLLoginMetro2.setVisible(false);
            jLLoginMetro3.setVisible(true);
            jLLoginMetro4.setVisible(false);
            jLLoginMetro5.setVisible(false);
            jLLoginMetro6.setVisible(false);
        } else if (randomIndex == 2) {
            jLLoginMetro1.setVisible(false);
            jLLoginMetro2.setVisible(false);
            jLLoginMetro3.setVisible(false);
            jLLoginMetro4.setVisible(true);
            jLLoginMetro5.setVisible(false);
            jLLoginMetro6.setVisible(false);
        } else if (randomIndex == 3) {
            jLLoginMetro1.setVisible(false);
            jLLoginMetro2.setVisible(false);
            jLLoginMetro3.setVisible(false);
            jLLoginMetro4.setVisible(false);
            jLLoginMetro5.setVisible(true);
            jLLoginMetro6.setVisible(false);
        } else if (randomIndex == 4) {
            jLLoginMetro1.setVisible(false);
            jLLoginMetro2.setVisible(false);
            jLLoginMetro3.setVisible(false);
            jLLoginMetro4.setVisible(false);
            jLLoginMetro5.setVisible(false);
            jLLoginMetro6.setVisible(true);
        } else {
            jLLoginMetro1.setVisible(true);
            jLLoginMetro2.setVisible(false);
            jLLoginMetro3.setVisible(false);
            jLLoginMetro4.setVisible(false);
            jLLoginMetro5.setVisible(false);
            jLLoginMetro6.setVisible(false);
        }

        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt) {
                try {
                    try {
                        try {
                            int selection = JOptionPane.showConfirmDialog(rootPane,
                                    "Deseja cancelar?",
                                    "Finalizar o sistema", JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (selection == JOptionPane.OK_OPTION) {
                                Fechando();
                            }
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
        System.exit(0);
    }

    public void NowString() {
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        DateFormat dg = DateFormat.getTimeInstance();
        s = df.format(now);
        f = dg.format(now);
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
        jButton3 = new javax.swing.JButton();
        txtSenha = new javax.swing.JPasswordField();
        txtUsuario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLLoginMetro6 = new javax.swing.JLabel();
        jLLoginMetro4 = new javax.swing.JLabel();
        jLLoginMetro3 = new javax.swing.JLabel();
        jLLoginMetro2 = new javax.swing.JLabel();
        jLLoginMetro1 = new javax.swing.JLabel();
        jLLoginMetro5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Verificação de permissão.");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/FinalizarSistema.png"))); // NOI18N
        jButton3.setText("Finalizar");
        jButton3.setToolTipText("Fechar o sistema - Cancelar.");
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(360, 280, 111, 36);

        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });
        jPanel1.add(txtSenha);
        txtSenha.setBounds(230, 110, 150, 31);

        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusLost(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });
        jPanel1.add(txtUsuario);
        txtUsuario.setBounds(230, 74, 180, 31);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/set.PNG"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(380, 110, 30, 31);

        jLLoginMetro6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLLoginMetro6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/LoginMetro6.png"))); // NOI18N
        jPanel1.add(jLLoginMetro6);
        jLLoginMetro6.setBounds(0, 0, 500, 340);

        jLLoginMetro4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLLoginMetro4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/LoginMetro4.png"))); // NOI18N
        jPanel1.add(jLLoginMetro4);
        jLLoginMetro4.setBounds(0, 0, 500, 340);

        jLLoginMetro3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLLoginMetro3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/LoginMetro3.png"))); // NOI18N
        jPanel1.add(jLLoginMetro3);
        jLLoginMetro3.setBounds(0, 0, 520, 330);

        jLLoginMetro2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLLoginMetro2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/LoginMetro2.png"))); // NOI18N
        jPanel1.add(jLLoginMetro2);
        jLLoginMetro2.setBounds(0, 0, 500, 340);

        jLLoginMetro1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLLoginMetro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/LoginMetro1.png"))); // NOI18N
        jPanel1.add(jLLoginMetro1);
        jLLoginMetro1.setBounds(0, 0, 480, 350);

        jLLoginMetro5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLLoginMetro5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/LoginMetro5.png"))); // NOI18N
        jPanel1.add(jLLoginMetro5);
        jLLoginMetro5.setBounds(0, 0, 500, 340);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            Fechando();
        } catch (BancoException ex) {
            Logger.getLogger(SolicitacaoSenhaNovoAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SolicitacaoSenhaNovoAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SolicitacaoSenhaNovoAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            SolicitacaoSenhaNovoAdministrador.this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // autenticar
            if (txtUsuario.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Usuário tem de ser preenchido!");
                txtUsuario.requestFocus();
            } else if (txtSenha.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Senha tem de ser preenchida!");
                txtSenha.requestFocus();
            } else {
                String senhaMD5 = md5(txtSenha.getText());
                try {
                    UsuarioDAO dao = new UsuarioDAO();
                    Usuario Verificando;
                    Verificando = dao.carregarUsuario(txtUsuario.getText());
                    if (Verificando.getSenha().equals(senhaMD5) && Verificando.getTipo().equals("Administrador")) {
                        SolicitacaoSenhaNovoAdministrador.this.dispose();

                    } else {
                     
                        JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                        txtUsuario.setText("");
                        txtSenha.setText("");
                        txtUsuario.requestFocus();
                    }
                    Verificando = null;
                    dao.desconectar();
                } catch (BancoException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        txtUsuario.setText(txtUsuario.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtUsuarioFocusLost

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            SolicitacaoSenhaNovoAdministrador.this.dispose();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // autenticar
            if (txtUsuario.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Usuário tem de ser preenchido!");
                txtUsuario.requestFocus();
            } else if (txtSenha.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Senha tem de ser preenchida!");
                txtSenha.requestFocus();
            } else {
                String senhaMD5 = md5(txtSenha.getText());
                try {
                    UsuarioDAO dao = new UsuarioDAO();
                    Usuario Verificando;
                    Verificando = dao.carregarUsuario(txtUsuario.getText());
                    if (Verificando.getSenha().equals(senhaMD5) && Verificando.getTipo().equals("Administrador")) {

                        SolicitacaoSenhaNovoAdministrador.this.dispose();
                    } else {
                    
                        JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                        txtUsuario.setText("");
                        txtSenha.setText("");
                        txtUsuario.requestFocus();
                    }
                    Verificando = null;
                    dao.desconectar();
                } catch (BancoException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        txtUsuario.setText(txtUsuario.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // autenticar
        if (txtUsuario.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Usuário tem de ser preenchido!");
            txtUsuario.requestFocus();
        } else if (txtSenha.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Senha tem de ser preenchida!");
            txtSenha.requestFocus();
        } else {
            String senhaMD5 = md5(txtSenha.getText());
            try {
                UsuarioDAO dao = new UsuarioDAO();
                Usuario Verificando;

                Verificando = dao.carregarUsuario(txtUsuario.getText());
                if (Verificando.getSenha().equals(senhaMD5) && Verificando.getTipo().equals("Administrador")) {

                    SolicitacaoSenhaNovoAdministrador.this.dispose();

                } else {
  
                    JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                    txtUsuario.setText("");
                    txtSenha.setText("");
                    txtUsuario.requestFocus();
                }
                Verificando = null;
                dao.desconectar();
            } catch (BancoException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(SolicitacaoSenhaNovoAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SolicitacaoSenhaNovoAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SolicitacaoSenhaNovoAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SolicitacaoSenhaNovoAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                SolicitacaoSenhaNovoAdministrador dialog = new SolicitacaoSenhaNovoAdministrador(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLLoginMetro1;
    private javax.swing.JLabel jLLoginMetro2;
    private javax.swing.JLabel jLLoginMetro3;
    private javax.swing.JLabel jLLoginMetro4;
    private javax.swing.JLabel jLLoginMetro5;
    private javax.swing.JLabel jLLoginMetro6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
