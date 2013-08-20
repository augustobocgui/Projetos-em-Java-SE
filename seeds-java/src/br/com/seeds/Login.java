/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import Classes.Acesso;
import Classes.Usuario;
import ClassesDAO.AcessoDAO;
import ClassesDAO.LocalidadeDAO;
import ClassesDAO.UsuarioDAO;
import Excessoes.BancoException;
import emailaplicativoseeds.Carteiro;
import emailaplicativoseeds.Mensagem;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author Guilherme Augusto
 */
public class Login extends javax.swing.JFrame {

    String s, f;
    int tentativasDeAcesso = 0;
    private Mensagem mensagens;
    String destinatario;
    String assunto;
    String msg;
    int local = 0;
    String parametroPesquisa = "";
    String campo;
    int verificarUserUmaVez = 0;

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/br/com/imagens/acesso.png")).getImage());

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

        //setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage("../seeds-java/shift.ico"));
        setLocationRelativeTo(null);
        txtUsuario.requestFocus();
        //Thema do RunWindows
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Para Fechar Sistema Menu PopUp
        jPanel1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                //Verificando se o botão direito do mouse foi clicado  
                if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                    JPopupMenu menus = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Clique-me para matar o processo no windows do java");
                    menus.add(item);

                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            //JOptionPane.showMessageDialog(null, "Fui clicado !");
                            kill("javaw.exe");
                            //System.exit(0);
                        }
                    });
                    menus.show(jPanel1, me.getX(), me.getY());
                }
            }
        });
    }

    public static boolean kill(String processo) {
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {
                    if (line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(processo)) {
                        Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
                        return true;
                    }
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }

    public void local() {
        try {
            LocalidadeDAO dao = new LocalidadeDAO();
            local = dao.gerarCodigoLocalidade();
            local--;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtUsuario = new javax.swing.JTextField();
        txtSenha = new javax.swing.JPasswordField();
        jLLoginMetro6 = new javax.swing.JLabel();
        jLLoginMetro4 = new javax.swing.JLabel();
        jLLoginMetro3 = new javax.swing.JLabel();
        jLLoginMetro2 = new javax.swing.JLabel();
        jLLoginMetro1 = new javax.swing.JLabel();
        jLLoginMetro5 = new javax.swing.JLabel();

        jMenuItem1.setText("teste 1");
        jPopupMenu1.add(jMenuItem1);

        jMenu1.setText("jMenu1");

        jMenuItem2.setText("Teste");
        jMenu1.add(jMenuItem2);

        jPopupMenu1.add(jMenu1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });
        jPanel1.setLayout(null);

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
        jPanel1.add(jButton1);
        jButton1.setBounds(380, 110, 30, 31);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(420, 270, 50, 40);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/applicationDatabases.png"))); // NOI18N
        jButton4.setToolTipText("Configurações de conexão.");
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(0, 280, 40, 40);

        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Sobre");
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(50, 290, 80, 20);

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

        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });
        jPanel1.add(txtSenha);
        txtSenha.setBounds(230, 110, 150, 31);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                UsuarioDAO dao = new UsuarioDAO();
                Usuario Verificando;
                Verificando = dao.carregarUsuario(txtUsuario.getText());


                if (Verificando.getSenha().equals(senhaMD5)) {
                    AcessoDAO daos = new AcessoDAO();
                    Acesso okay = new Acesso();
                    Verificando = dao.carregarUsuario(txtUsuario.getText());
                    NowString();
                    okay.setUsuario(Verificando.getCodigo());
                    okay.setData(s);
                    okay.setHora(f);
                    okay.setDescricao("Acesso ao sistema efetuado [Interface - Login]");
                    daos.adicionarAcesso(okay);
                    okay = null;
                    Verificando = null;
                    daos.desconectar();
                    dao.desconectar();
                    Login.this.dispose();
                    new Menu().setVisible(true);

                } else {
                    local();
                    AcessoDAO daos = new AcessoDAO();
                    Acesso okay = new Acesso();
                    NowString();
                    okay.setUsuario(Verificando.getCodigo());
                    okay.setData(s);
                    okay.setHora(f);
                    okay.setDescricao("Acesso ao sistema não efetuado [Interface - Login]");
                    daos.adicionarAcesso(okay);
                    okay = null;
                    tentativasDeAcesso++;
                    if (tentativasDeAcesso == 5 || tentativasDeAcesso == 10 || tentativasDeAcesso > 20) {
                        //IMFORMAR POR EMAIL
                        destinatario = "seedsescola@gmail.com";
                        assunto = "Segurança";
                        msg = "Possibilidade de várias tentativas de acessos indevidos por meio de tentativa e erro [Interface - Login]\n"
                                + "\nNome de login informado: " + txtUsuario.getText().trim() + "\n"
                                + "Data: " + s + "\n"
                                + "Hora: " + f + "\n"
                                + "Código usuário: " + Verificando.getCodigo() + "\n"
                                + "Quantidade de tentativas: " + tentativasDeAcesso + "\n"
                                + "Local: " + local + "";
                        mensagens = new Mensagem();
                        mensagens.setDestinatario(destinatario);
                        mensagens.setAssunto(assunto);
                        mensagens.setMensagem(msg);
                        Mensagem mensagem = mensagens;
                        Carteiro carteiro = new Carteiro();
                        try {
                            carteiro.enviarMensagem(mensagem);
                            exibirMsgSucesso();
                            carteiro = null;
                        } catch (EmailException e) {
                            exibirMsgErro(e.getMessage());
                        }
                        //EMAIL FIM
                    }
                    daos.desconectar();
                    dao.desconectar();
                    JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                    txtUsuario.setText("");
                    txtSenha.setText("");
                }
            } catch (BancoException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        txtUsuario.requestFocus();
        new Sobre(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

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

                        AcessoDAO daos = new AcessoDAO();
                        Acesso okay = new Acesso();
                        Verificando = dao.carregarUsuario(txtUsuario.getText());
                        NowString();
                        okay.setUsuario(Verificando.getCodigo());
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Acesso ao sistema efetuado [Interface - Login]");
                        daos.adicionarAcesso(okay);
                        okay = null;
                        Verificando = null;
                        daos.desconectar();
                        dao.desconectar();
                        Login.this.dispose();
                        new Menu().setVisible(true);
                    } else {
                        local();
                        AcessoDAO daos = new AcessoDAO();
                        Acesso okay = new Acesso();
                        NowString();
                        okay.setUsuario(Verificando.getCodigo());
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Acesso ao sistema não efetuado [Interface - Login]");
                        daos.adicionarAcesso(okay);
                        okay = null;
                        tentativasDeAcesso++;
                        if (tentativasDeAcesso == 5 || tentativasDeAcesso == 10 || tentativasDeAcesso > 20) {
                            //IMFORMAR POR EMAIL
                            destinatario = "seedsescola@gmail.com";
                            assunto = "Segurança";
                            msg = "Possibilidade de várias tentativas de acessos indevidos por meio de tentativa e erro [Interface - Login]\n"
                                    + "\nNome de login informado: " + txtUsuario.getText().trim() + "\n"
                                    + "Data: " + s + "\n"
                                    + "Hora: " + f + "\n"
                                    + "Código usuário: " + Verificando.getCodigo() + "\n"
                                    + "Quantidade de tentativas: " + tentativasDeAcesso + "\n"
                                    + "Local: " + local + "";
                            mensagens = new Mensagem();
                            mensagens.setDestinatario(destinatario);
                            mensagens.setAssunto(assunto);
                            mensagens.setMensagem(msg);
                            Mensagem mensagem = mensagens;
                            Carteiro carteiro = new Carteiro();
                            try {
                                carteiro.enviarMensagem(mensagem);
                                exibirMsgSucesso();
                                carteiro = null;
                            } catch (EmailException e) {
                                exibirMsgErro(e.getMessage());
                            }
                            //EMAIL FIM
                        }
                        daos.desconectar();
                        dao.desconectar();
                        JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                        txtUsuario.setText("");
                        txtSenha.setText("");
                        txtUsuario.requestFocus();
                    }
                } catch (BancoException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtSenhaKeyPressed

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

                        AcessoDAO daos = new AcessoDAO();
                        Acesso okay = new Acesso();
                        Verificando = dao.carregarUsuario(txtUsuario.getText());
                        NowString();
                        okay.setUsuario(Verificando.getCodigo());
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Acesso ao sistema efetuado [Interface - Login]");
                        daos.adicionarAcesso(okay);
                        okay = null;
                        Verificando = null;
                        daos.desconectar();
                        dao.desconectar();
                        Login.this.dispose();
                        new Menu().setVisible(true);
                    } else {
                        AcessoDAO daos = new AcessoDAO();
                        Acesso okay = new Acesso();
                        NowString();
                        okay.setUsuario(Verificando.getCodigo());
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Acesso ao sistema não efetuado [Interface - Login]");
                        daos.adicionarAcesso(okay);
                        okay = null;
                        tentativasDeAcesso++;
                        if (tentativasDeAcesso == 5 || tentativasDeAcesso == 10 || tentativasDeAcesso > 20) {
                            //IMFORMAR POR EMAIL
                            local();
                            destinatario = "seedsescola@gmail.com";
                            assunto = "Segurança";
                            msg = "Possibilidade de várias tentativas de acessos indevidos por meio de tentativa e erro [Interface - Login]\n"
                                    + "\nNome de login informado: " + txtUsuario.getText().trim() + "\n"
                                    + "Data: " + s + "\n"
                                    + "Hora: " + f + "\n"
                                    + "Código usuário: " + Verificando.getCodigo() + "\n"
                                    + "Quantidade de tentativas: " + tentativasDeAcesso + "\n"
                                    + "Local: " + local + "";
                            mensagens = new Mensagem();
                            mensagens.setDestinatario(destinatario);
                            mensagens.setAssunto(assunto);
                            mensagens.setMensagem(msg);
                            Mensagem mensagem = mensagens;
                            Carteiro carteiro = new Carteiro();
                            try {
                                carteiro.enviarMensagem(mensagem);
                                exibirMsgSucesso();
                                carteiro = null;
                            } catch (EmailException e) {
                                exibirMsgErro(e.getMessage());
                            }
                            //EMAIL FIM
                        }
                        daos.desconectar();
                        dao.desconectar();
                        JOptionPane.showMessageDialog(this, "Acesso não concedido!");
                        txtUsuario.setText("");
                        txtSenha.setText("");
                        txtUsuario.requestFocus();
                    }
                } catch (BancoException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        if (verificarUserUmaVez == 0) {
            try {
                UsuarioDAO dao = new UsuarioDAO();
                if (dao.gerarCodigoUsuario() == 1) {
                    Login.this.dispose();
                    new Usuarios().setVisible(true);
                }
                dao.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        verificarUserUmaVez = 1;//Permite que inicie apenas uma vez
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        txtUsuario.requestFocus();
        new ConfiguracoesServidor(null, true).setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        txtUsuario.setText(txtUsuario.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        txtUsuario.setText(txtUsuario.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtUsuarioFocusLost

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLLoginMetro1;
    private javax.swing.JLabel jLLoginMetro2;
    private javax.swing.JLabel jLLoginMetro3;
    private javax.swing.JLabel jLLoginMetro4;
    private javax.swing.JLabel jLLoginMetro5;
    private javax.swing.JLabel jLLoginMetro6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
