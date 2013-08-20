
/*
 * Usuarios.java
 *
 * Created on 03/01/2009, 23:08:44
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
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.mail.EmailException;

public class Usuarios extends javax.swing.JFrame {

    int teste = 0;
    int entrada = 0;
    int codigo = 0;
    StringBuffer nome = new StringBuffer();
    int apagar = 0;
    String s, f;
    String destinatario;
    String assunto;
    String msg;
    private Mensagem mensagens;
    int local = 0;
    String tipoFuncionario = "";

    /**
     * Creates new form Usuarios
     */
    public Usuarios() {
        initComponents();
        setLocationRelativeTo(this);
        //Para trocar função do Tab por Enter
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);
        txtNome.requestFocus();
        //Para limitar os campos
        txtNome.setDocument(new LimiteCampos.FixedLengthDocument(100));
        txtLogin.setDocument(new LimiteCampos.FixedLengthDocument(30));
        txtSenha.setDocument(new LimiteCampos.FixedLengthDocument(30));
        txtConfirmaSenha.setDocument(new LimiteCampos.FixedLengthDocument(30));
        //Para esconder os campos
        jLSenhaFraca.setVisible(false);
        jLSenhaRazoavel.setVisible(false);
        jLSenhaForte.setVisible(false);
        jBSalvar.setEnabled(false);
        jBAlterar.setEnabled(false);
        jBExcluir.setEnabled(false);

        try {
            UsuarioDAO dao = new UsuarioDAO();
            codigo = dao.gerarCodigoUsuario();
            txtCodigo.setText("" + codigo);
            if (txtCodigo.getText().equals("1")) {
                jCBFuncao.setVisible(false);
                jBExcluir.setEnabled(false);
            } else {
                jCBFuncao.setVisible(true);
            }
            dao.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                    txtLogin.setText("" + user.getUsuario());

                    users = dao.carregarUsuarioPeloCodigo(user.getUsuario());
                    //txtUsuarioNome.setText("" + users.getNome());
                    tipoFuncionario = users.getFuncao();
                    txtLogin.setText("" + users.getLogin());
                    if (tipoFuncionario.equals("Administrador")) {
                    } else {
                    }

                } while (nome.equals("0"));

                txtLogin.setBackground(Color.red);
                txtLogin.setToolTipText("Não foi possível identificar o usuário *logado no sistema! "
                        + "Isso não prejudica outras funções executadas.\n"
                        + "*Que está identificado no sistema computacional.");
                //txtUsuarioUserName.setVisible(false);

            } else {
                user = daos.carregarAcessoPeloCodigo(nome);
                txtLogin.setText("" + user.getUsuario());


                users = dao.carregarUsuarioPeloCodigo(user.getUsuario());
                //txtUsuarioNome.setText("" + users.getNome());
                tipoFuncionario = users.getFuncao();
                txtLogin.setText("" + users.getLogin());
                if (tipoFuncionario.equals("Administrador")) {
                } else {
                }

            }
            user = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jCBFuncao = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtConfirmaSenha = new javax.swing.JPasswordField();
        txtLogin = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        jLSenhaFraca = new javax.swing.JLabel();
        jLSenhaRazoavel = new javax.swing.JLabel();
        jLSenhaForte = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jBSalvar = new javax.swing.JButton();
        jBExcluir = new javax.swing.JButton();
        jBAlterar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Novo usuário");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(249, 249, 249));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("* Dados pessoais"));

        jLabel3.setText("Código: ");

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setText("Nome: ");

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel16.setText("Função:");

        jCBFuncao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vendedor (a)", "Secretário (a)", "Serviços Gerais", "Administrador", "Outra. Editar" }));
        jCBFuncao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBFuncaoActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jCBFuncao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jCBFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(163, 163, 163))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 20, 530, 100);

        jPanel3.setBackground(new java.awt.Color(249, 249, 249));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("* Dados de acesso ao sistema"));
        jPanel3.setLayout(null);

        txtConfirmaSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txtConfirmaSenha);
        txtConfirmaSenha.setBounds(124, 106, 250, 23);

        txtLogin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLogin.setToolTipText("Nome de usuário");
        txtLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLoginFocusLost(evt);
            }
        });
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLoginKeyTyped(evt);
            }
        });
        jPanel3.add(txtLogin);
        txtLogin.setBounds(124, 27, 250, 23);

        jLabel13.setText("Login:");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(20, 30, 29, 14);

        jLabel14.setText("Senha:");
        jPanel3.add(jLabel14);
        jLabel14.setBounds(20, 70, 34, 14);

        jLabel15.setText("Confirmar senha:");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(16, 106, 94, 14);

        txtSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSenha.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSenhaCaretUpdate(evt);
            }
        });
        jPanel3.add(txtSenha);
        txtSenha.setBounds(124, 65, 250, 23);

        jLSenhaFraca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLSenhaFraca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/senhaFraca.png"))); // NOI18N
        jLSenhaFraca.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLSenhaFraca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLSenhaFraca);
        jLSenhaFraca.setBounds(390, 10, 40, 100);

        jLSenhaRazoavel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/senhaRazoavel.png"))); // NOI18N
        jLSenhaRazoavel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(jLSenhaRazoavel);
        jLSenhaRazoavel.setBounds(390, 10, 40, 100);

        jLSenhaForte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/senhaForte.png"))); // NOI18N
        jLSenhaForte.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(jLSenhaForte);
        jLSenhaForte.setBounds(390, 10, 40, 100);

        jLabel5.setText("Força da senha.");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(400, 120, 80, 14);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(10, 120, 530, 150);

        jBSalvar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jBSalvar.setText("Concluir");
        jBSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarActionPerformed(evt);
            }
        });
        jPanel1.add(jBSalvar);
        jBSalvar.setBounds(10, 280, 107, 40);

        jBExcluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/edit-delete.png"))); // NOI18N
        jBExcluir.setText("Excluir");
        jBExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExcluirActionPerformed(evt);
            }
        });
        jPanel1.add(jBExcluir);
        jBExcluir.setBounds(250, 280, 110, 40);

        jBAlterar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/view-refresh.png"))); // NOI18N
        jBAlterar.setText("Alterar");
        jBAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarActionPerformed(evt);
            }
        });
        jPanel1.add(jBAlterar);
        jBAlterar.setBounds(130, 280, 110, 40);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel12.setText("* CAMPOS OBRIGATÓRIOS.");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 0, 129, 12);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSenhaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSenhaCaretUpdate
        if (txtSenha.getText().contains("/") || txtSenha.getText().contains("-") || txtSenha.getText().contains(".")
                || txtSenha.getText().contains("@")) {
            //JOptionPane.showConfirmDialog(rootPane, forca);
            jLSenhaFraca.setVisible(true);
            jLSenhaRazoavel.setVisible(false);
            jLSenhaForte.setVisible(false);
            jBSalvar.setEnabled(false);
        } else {
            teste = txtSenha.getText().length(); // Verifica quantidade de caracteres
            if (teste < 7) {
                entrada = entrada - 1;
            }

            if (txtSenha.getText().equals("verde")) {
                jLSenhaFraca.setVisible(false);
                jLSenhaRazoavel.setVisible(false);
                jLSenhaForte.setVisible(true);
                if (jBAlterar.isEnabled() == true) {
                    jBSalvar.setEnabled(false);
                } else {
                    jBSalvar.setEnabled(true);
                }
            } else {
                if (txtSenha.getText().equals("") || txtSenha.getText().equals("123456789")
                        || txtSenha.getText().equals("987654321") || txtSenha.getText().equals("abcdefghi")
                        || txtSenha.getText().equals(txtLogin.getText()) || txtSenha.getText().equals("9876543210")
                        || txtSenha.getText().equals("0123456789") || txtSenha.getText().equals(txtNome.getText())
                        || txtSenha.getText().equals("12345678910") || teste < 7 || txtSenha.getText().equals("seeds")
                        || txtSenha.getText().equals("Depi Luz") || txtSenha.getText().equals("seeds")) {
                    jLSenhaFraca.setVisible(true);
                    jLSenhaRazoavel.setVisible(false);
                    jLSenhaForte.setVisible(false);
                    jBSalvar.setEnabled(false);
                } else {
                    if (teste >= 7) {
                        jLSenhaFraca.setVisible(false);
                        jLSenhaForte.setVisible(false);
                        jLSenhaRazoavel.setVisible(true);
                        if (jBAlterar.isEnabled() == true) {
                            jBSalvar.setEnabled(false);
                        } else {
                            jBSalvar.setEnabled(true);
                        }
                        if (teste > 8) {
                            jLSenhaFraca.setVisible(false);
                            jLSenhaForte.setVisible(true);
                            if (jBAlterar.isEnabled() == true) {
                                jBSalvar.setEnabled(false);
                            } else {
                                jBSalvar.setEnabled(true);
                            }
                            jLSenhaRazoavel.setVisible(false);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_txtSenhaCaretUpdate

private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarActionPerformed
    if (txtCodigo.getText().equals("1")) {
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append("Administrador");
    } else {
        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
        nome.delete(0, apagar);
        nome.append(jCBFuncao.getSelectedItem().toString());
    }

    if (!txtSenha.getText().equals(txtConfirmaSenha.getText())) {
        JOptionPane.showMessageDialog(rootPane, "Campos Senha e Confirmação de senha são diferentes!",
                "Atenção!", JOptionPane.INFORMATION_MESSAGE);
    } else {
        if (txtSenha.getText().equals("") || txtConfirmaSenha.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe a Senha e confirme!",
                    "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (txtNome.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Campo Nome tem que ser informado!",
                        "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (txtLogin.getText().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Campo Login tem que ser informado!",
                            "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Usuario user = new Usuario();
                    jBSalvar.setEnabled(true);
                    txtNome.setText(txtNome.getText().trim());
                    user.setNome(txtNome.getText());
                    user.setFuncao("" + nome);
                    user.setLogin(txtLogin.getText());
                    String senhaMD5 = md5(txtSenha.getText());
                    user.setSenha(senhaMD5);

                    try {
                        AcessoDAO daos = new AcessoDAO();
                        Acesso okay = new Acesso();
                        NowString();
                        okay.setUsuario(Integer.parseInt(txtCodigo.getText()));
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Novo cadastro de usuário efetuado[Interface - Usuários]\n"
                                + "Nome informado: " + txtNome.getText().trim() + "");
                        daos.adicionarAcesso(okay);
                        okay = null;

                        //IMFORMAR POR EMAIL
                        String destinatario = "seedsescola@gmail.com";
                        String assunto = "Novo cadastro de usuário";
                        String msg = "Novo cadastro de usuário efetuado[Interface - Usuários]\n"
                                + "Nome informado: " + txtNome.getText().trim() + "\n"
                                + "Data: " + s + "\n"
                                + "Hora: " + f + "\n"
                                + "Função: " + jCBFuncao.getSelectedItem().toString() + "\n"
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
                        } catch (EmailException e) {
                            exibirMsgErro(e.getMessage());
                        }
                        //EMAIL FIM


                        UsuarioDAO dao = new UsuarioDAO();
                        dao.adicionarUsuario(user);
                        JOptionPane.showMessageDialog(this, "Cadastro efetuado com sucesso!");

                        if (txtCodigo.getText().equals("1")) {
                            Usuarios.this.dispose();
                            new Login().setVisible(true);
                        } else {
                            txtCodigo.setText("");
                            txtNome.setText("");
                            txtLogin.setText("");
                            txtSenha.setText("");
                            txtConfirmaSenha.setText("");
                            //jCBFuncao.setSelectedItem("Administrador (a)");
                            try {
                                txtCodigo.setText("" + dao.gerarCodigoUsuario());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        user = null;
                        daos.desconectar();
                    } catch (BancoException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    if (nome.equals("Administrador")) {
        Usuarios.this.dispose();
        new Login().setVisible(true);
    }
}//GEN-LAST:event_jBSalvarActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nome.delete(0, apagar);
    nome.append(JOptionPane.showInputDialog(this, "Informe o Login!",
            "Buscar",
            JOptionPane.QUESTION_MESSAGE));
    //if (!nome.equals(null)) {
    Usuario user = new Usuario();
    try {
        UsuarioDAO dao = new UsuarioDAO(); // estou instanciando a conexão
        user = dao.carregarUsuarioPeloNomeUser(nome);
        if (user.getNome().equals("nulo")) {
            JOptionPane.showMessageDialog(this, "O Usuário informado não consta no banco!");
        } else {
            usuarioLogado();
            if (user.getLogin().equals(txtLogin.getText())) {
                txtCodigo.setText(String.valueOf(user.getCodigo()));
                txtNome.setText(user.getNome());
                jCBFuncao.setSelectedItem(user.getFuncao());
                txtLogin.setText(user.getLogin());
                jBAlterar.setEnabled(true);
                jBExcluir.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Você não é o usuário que está usando o sistema.\n"
                        + "Para alterar a sua senha feche o sistema e entre com o seu usuário.",
                        "", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new Usuarios().setVisible(true);
            }
        }
        user = null;
        dao.desconectar();
    } catch (BancoException e) {
        e.printStackTrace();
    }
    if (txtCodigo.getText().equals("1")) {
        jCBFuncao.setVisible(false);
        jBExcluir.setEnabled(false);
        jBSalvar.setEnabled(false);
        //jBSalvar.setToolTipText("Criar novo Usuário Administrador");
    } else {
        jCBFuncao.setVisible(true);
        jBExcluir.setEnabled(true);
    }
    //}
}//GEN-LAST:event_jButton2ActionPerformed

private void jBExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExcluirActionPerformed
    if (txtCodigo.getText().equals(codigo)) {
        JOptionPane.showMessageDialog(this, "Não é possível excluir o usuário.\n"
                + "Usuário não existe ou não foi encontrado.\n"
                + "Certifique-se se você selecionou o usuário antes de exclui-lo",
                "seeds",
                JOptionPane.ERROR_MESSAGE);
    } else {
        if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Campo Código não pode ser vazio",
                    "seeds",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            if (txtCodigo.getText().equals("1")) {
                JOptionPane.showMessageDialog(this, "O Administrador não pode ser excluído!",
                        "seeds",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                nome.delete(0, apagar);
                nome.append(jCBFuncao.getSelectedItem());
                if (txtCodigo.getText().equals("1")) {
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    nome.append("Administrador");
                }
                //Usuario user = new Usuario();

                Usuario UsuarioDeletarRegistro = new Usuario();
                UsuarioDeletarRegistro.setCodigo(Integer.parseInt(txtCodigo.getText()));
                try {
                    UsuarioDAO dao = new UsuarioDAO();
                    dao.deletarUsuario(UsuarioDeletarRegistro);
                    if (txtCodigo.getText().equals("1")) {
                        JOptionPane.showMessageDialog(this, "Atualização efetuada com sucesso!\n"
                                + "O Sistema será encerrado...",
                                "seeds",
                                JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    } else {
                        AcessoDAO daos = new AcessoDAO();
                        Acesso okay = new Acesso();
                        NowString();
                        okay.setUsuario(Integer.parseInt(txtCodigo.getText()));
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Exclusão de dados do usuário efetuado[Interface - Usuários]\n"
                                + "Nome informado: " + txtNome.getText().trim() + "");
                        daos.adicionarAcesso(okay);
                        okay = null;
                        daos.desconectar();
                        JOptionPane.showMessageDialog(this, "Atualização efetuada com sucesso!\n",
                                "seeds", JOptionPane.INFORMATION_MESSAGE);
                    }
                    UsuarioDeletarRegistro = null;
                    dao.desconectar();
                } catch (BancoException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}//GEN-LAST:event_jBExcluirActionPerformed

private void jBAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarActionPerformed
    if (!txtSenha.getText().equals(txtConfirmaSenha.getText())) {
        JOptionPane.showMessageDialog(rootPane, "Campos Senha e Confirmação de senha são diferentes!",
                "Atenção!", JOptionPane.INFORMATION_MESSAGE);
    } else {
        if (txtSenha.getText().equals("") || txtConfirmaSenha.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Informe a Senha e confirme!",
                    "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (txtNome.getText().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Campo Nome tem que ser informado!",
                        "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (txtLogin.getText().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Campo Login tem que ser informado!",
                            "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Usuario user = new Usuario();
                    jBSalvar.setEnabled(true);
                    user.setCodigo(Integer.parseInt(txtCodigo.getText()));
                    txtNome.setText(txtNome.getText().trim());
                    user.setNome(txtNome.getText());

                    if (txtCodigo.getText().equals("1")) {
                        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                        nome.delete(0, apagar);
                        nome.append("Administrador");
                    } else {
                        apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                        nome.delete(0, apagar);
                        nome.append(jCBFuncao.getSelectedItem().toString());
                    }
                    user.setFuncao("" + nome);
                    user.setLogin(txtLogin.getText());
                    String senhaMD5 = md5(txtSenha.getText());
                    user.setSenha(senhaMD5);
                    try {
                        UsuarioDAO dao = new UsuarioDAO();
                        dao.atualizaDados(user);
                        JOptionPane.showMessageDialog(this, "Efetuado com sucesso!");

                        AcessoDAO daos = new AcessoDAO();
                        Acesso okay = new Acesso();
                        NowString();
                        okay.setUsuario(Integer.parseInt(txtCodigo.getText()));
                        okay.setData(s);
                        okay.setHora(f);
                        okay.setDescricao("Alteração de dados do usuário efetuado[Interface - Usuários]\n"
                                + "Nome informado: " + txtNome.getText().trim() + "");
                        daos.adicionarAcesso(okay);
                        okay = null;
                        txtCodigo.setText("");
                        txtNome.setText("");
                        txtLogin.setText("");
                        txtSenha.setText("");
                        txtConfirmaSenha.setText("");
                        user = null;
                        dao.desconectar();
                        daos.desconectar();
                    } catch (BancoException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}//GEN-LAST:event_jBAlterarActionPerformed

private void txtLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusLost
    txtLogin.setText(txtLogin.getText().toString().toLowerCase());
    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
    nome.delete(0, apagar);
    nome.append(txtLogin.getText());
    if (txtLogin.getText().contains("null")) {
        txtLogin.setText(txtLogin.getText().trim().replace("null", ""));
        txtLogin.setToolTipText("A palavra [null] não disponível.");
        txtLogin.setBackground(Color.red);
    } else {
        txtLogin.setBackground(Color.white);
    }
    Usuario usu = new Usuario();
    usu.setLogin("" + nome);
    try {
        UsuarioDAO dao = new UsuarioDAO();
        boolean Encontrou = dao.verificaDisponibilidadeUsuario(usu);
        if (!Encontrou) {
        } else {
            JOptionPane.showMessageDialog(this, "Nome de usuário não disponivel!",
                    "seeds",
                    JOptionPane.INFORMATION_MESSAGE);
            txtLogin.setText("");
            txtLogin.requestFocus();
        }
        usu = null;
        dao.desconectar();
    } catch (BancoException ex) {
        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_txtLoginFocusLost

private void jCBFuncaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBFuncaoActionPerformed
    if (jCBFuncao.getSelectedItem().toString().equals("Administrador")) {
        int selection = JOptionPane.showConfirmDialog(this,
                "Acesso restrito!\n"
                + "Após confirmação não será possível o cancelamento desta operação\n"
                + " sem que isso implique o fechamento do sistema.\n"
                + "Somente Administradores podem criar um novo usuário Administrador.\n ",
                "", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) {
            new SolicitacaoSenhaNovoAdministrador(null, rootPaneCheckingEnabled).setVisible(true);
        } else {
            this.dispose();
            new Usuarios().setVisible(true);
        }
    }
    if (jCBFuncao.getSelectedItem().toString().equals("Outra. Editar")) {
        jCBFuncao.setEditable(true);
        jCBFuncao.setSelectedItem("");
    } else {
        jCBFuncao.setEditable(false);
    }
}//GEN-LAST:event_jCBFuncaoActionPerformed

    private void txtLoginKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyTyped
        txtLogin.setText(txtLogin.getText().toString().toLowerCase());
    }//GEN-LAST:event_txtLoginKeyTyped
//Função para criar hash da senha informada  

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
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuarios().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAlterar;
    private javax.swing.JButton jBExcluir;
    private javax.swing.JButton jBSalvar;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jCBFuncao;
    private javax.swing.JLabel jLSenhaForte;
    private javax.swing.JLabel jLSenhaFraca;
    private javax.swing.JLabel jLSenhaRazoavel;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JPasswordField txtConfirmaSenha;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
