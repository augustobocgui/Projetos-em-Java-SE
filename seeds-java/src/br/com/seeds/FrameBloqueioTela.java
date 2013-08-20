/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import Classes.Acesso;
import Classes.Usuario;
import ClassesDAO.AcessoDAO;
import ClassesDAO.UsuarioDAO;
import Excessoes.BancoException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author Guilherme
 */
public class FrameBloqueioTela extends javax.swing.JDialog {

    String s, f;
    StringBuffer nome = new StringBuffer();
    String tipoFuncionario = "";
    int apagar = 0;

    /**
     * Creates new form FramePaiCadastros
     */
    public FrameBloqueioTela(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setUndecorated(true);//retira a barra de título do JFrame  

        initComponents();
        usuarioLogado();
        setLocationRelativeTo(null);//faz o frame ser exibido no meio da tela  

        PainelTrasnparenteVendas pt = new PainelTrasnparenteVendas(this);//instancia o painel  

        add(pt);//adiciona ao JFrame 
        //Para 
        jLLoginMetro6.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                //Verificando se o botão direito do mouse foi clicado  
                if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                    JPopupMenu menus = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Clique-me para checar o usuario - atualizar.");
                    menus.add(item);

                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            try {
                                usuarioLogado();
                            } catch (Exception e) {
                            }
                        }
                    });
                    menus.show(jLLoginMetro6, me.getX(), me.getY());
                }
            }
        });
    }

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

    public void NowString() {
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        DateFormat dg = DateFormat.getTimeInstance();
        s = df.format(now);
        f = dg.format(now);
    }

    public void usuarioLogado() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            AcessoDAO daos = new AcessoDAO();
            Acesso user = new Acesso();
            Usuario users = new Usuario();
            tipoFuncionario = "" + dao.gerarCodigoUsuarioUltimoLogado();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(tipoFuncionario);

            if (tipoFuncionario.equals("0")) {
                do {
                    int remove = Integer.parseInt(nome + "");
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    nome.append(remove - 1);
                    user = daos.carregarAcessoPeloCodigo(nome);

                    txtUsuario.setText("" + user.getUsuario());

                    users = dao.carregarUsuarioPeloCodigo(user.getUsuario());
                    tipoFuncionario = users.getFuncao();
                    txtUsuario.setText("" + users.getLogin());

                } while (nome.equals("0"));

                txtUsuario.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Opss! :("
                        + "\nNão foi possível identificar o usuário *logado no sistema!\n"
                        + "Isso não prejudica outras funções executadas.\n"
                        + "Tente novamente!\n\n"
                        + "*Que está identificado no sistema computacional.\n"
                        + "", "", JOptionPane.ERROR_MESSAGE);
                this.dispose();

            } else {
                user = daos.carregarAcessoPeloCodigo(nome);
                txtUsuario.setText("" + user.getUsuario());

                users = dao.carregarUsuarioPeloCodigo(user.getUsuario());
                tipoFuncionario = users.getFuncao();
                txtUsuario.setText("" + users.getLogin());

            }
            if (txtUsuario.getText().trim().contains("null")) {
                JOptionPane.showMessageDialog(null, "Opss! :("
                        + "\nNão foi possível identificar o usuário *logado no sistema!\n"
                        + "Isso não prejudica outras funções executadas.\n"
                        + "Tente novamente!\n\n"
                        + "*Que está identificado no sistema computacional.\n"
                        + "", "", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
            user = null;
            users = null;
            dao.desconectar();
            daos.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        txtUsuario = new javax.swing.JTextField();
        jLLoginMetro6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/barralateral.png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setFocusable(false);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/barralateralInferior - Cópia.png"))); // NOI18N
        jLabel2.setFocusable(false);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/barralateralInferiorE.png"))); // NOI18N
        jLabel3.setFocusable(false);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/system-log-out.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/set.PNG"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName(""); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Loading.gif"))); // NOI18N
        jLabel5.setFocusable(false);

        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });

        txtUsuario.setEnabled(false);
        txtUsuario.setFocusable(false);
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

        jLLoginMetro6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLLoginMetro6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/LoginMetro1.png"))); // NOI18N
        jLLoginMetro6.setFocusable(false);
        jLLoginMetro6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLLoginMetro6MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(450, 450, 450)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(460, 460, 460)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLLoginMetro6, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jButton1)
                .addGap(29, 29, 29)
                .addComponent(jLabel3))
            .addComponent(jLabel4)
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLLoginMetro6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        try {
            AcessoDAO daos = new AcessoDAO();
            Acesso okay = new Acesso();
            NowString();
            okay.setUsuario(0);
            okay.setData(s);
            okay.setHora(f);
            okay.setDescricao("Logout  [Interface - Menu]\nTérmino de comunicação entre o usuário e o sistema, encerrando a seção dentro da máquina.");
            daos.adicionarAcesso(okay);
            okay = null;
            daos.desconectar();
        } catch (Exception e) {
        }
        System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Entrar
        if (txtUsuario.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Usuário tem de ser preenchido!");
            txtUsuario.requestFocus();
        } else if (txtSenha.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Senha tem de ser preenchida!");
            txtSenha.requestFocus();
        } else {
            String senhaMD5 = md5(txtSenha.getText());
            try {
                UsuarioDAO dao;
                dao = new UsuarioDAO();
                Usuario Verificando;
                Verificando = dao.carregarUsuario(txtUsuario.getText());

                if (Verificando.getSenha().equals(senhaMD5)) {
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                    txtSenha.setText("");
                    txtSenha.requestFocus();
                }
                Verificando = null;
                dao.desconectar();
            } catch (BancoException ex) {
                Logger.getLogger(FrameBloqueioTela.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            // Entrar
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
                    if (Verificando.getSenha().equals(senhaMD5)) {
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                        txtSenha.setText("");
                        txtSenha.requestFocus();
                    }
                    Verificando = null;
                    dao.desconectar();
                } catch (Exception e) {
                }
            }
        }
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        txtUsuario.setText(txtUsuario.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtUsuarioFocusLost

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtUsuario.setText(txtUsuario.getText().toString().toLowerCase());
            // Entrar
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
                    if (Verificando.getSenha().equals(senhaMD5)) {
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                        txtSenha.setText("");
                        txtSenha.requestFocus();
                    }
                    Verificando = null;
                    dao.desconectar();
                } catch (Exception e) {
                }
            }
        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        txtUsuario.setText(txtUsuario.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void jLLoginMetro6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLLoginMetro6MouseEntered
        if (txtUsuario.getText().equals("")) {
            usuarioLogado();
        }
    }//GEN-LAST:event_jLLoginMetro6MouseEntered

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
            java.util.logging.Logger.getLogger(FrameBloqueioTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameBloqueioTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameBloqueioTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameBloqueioTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrameBloqueioTela dialog = new FrameBloqueioTela(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLLoginMetro6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
