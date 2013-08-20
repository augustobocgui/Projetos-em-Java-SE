/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import seeds.Main;
import Classes.LimitadorMoeda;
import Classes.SaidaCaixa;
import ClassesDAO.CaixaDAO;
import Excessoes.BancoException;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme
 */
public class CaixaSaida extends javax.swing.JFrame {

    /**
     * Creates new form CaixaSaida
     */
    String s, f;
    CaixaDAO dao;

    public CaixaSaida() throws BancoException {
        initComponents();
        setLocationRelativeTo(null);
        dao = new CaixaDAO();
        txtValor.setDocument(new LimitadorMoeda());
        NowString();
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

        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        txtDescricao = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sangria");
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Valor:");

        txtValor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorKeyPressed(evt);
            }
        });

        txtDescricao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescricaoKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Descrição:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(261, 261, 261))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel3});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel3});

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel12.setText("* Campos obrigatórios!");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/entrar.png"))); // NOI18N
        jButton2.setText("Finalizar");
        jButton2.setToolTipText("Concluir um pagamento e Inativar o funcionário");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cancelar.png"))); // NOI18N
        jButton4.setText("Sair");
        jButton4.setToolTipText("Fechará a tela funcionários e voltará ao menu principal");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-675)/2, (screenSize.height-242)/2, 675, 242);
    }// </editor-fold>//GEN-END:initComponents

    private void txtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Clicou em Finalizar
            boolean Verificandonegativo = false;
            try {
                Verificandonegativo = dao.VerificandoValor(Float.parseFloat(txtValor.getText().replace(".", "").replace(",", ".")));
            } catch (BancoException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (txtValor.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Valor é um campo de preenchimento obrigatório!");
                txtValor.requestFocus();
            } else if (Verificandonegativo) {
                JOptionPane.showMessageDialog(this, "O Caixa não pode ficar negativo!");
            } else {
                SaidaCaixa Saida = new SaidaCaixa();

                NowString();
                int CodigoAbertura = 0;



                try {
                    ResultSet Verificando = dao.VerificaAbertura(s, f);
                    while (Verificando.next()) {
                        CodigoAbertura = Verificando.getInt("Codigo");
                    }
                    Verificando.close();

                } catch (SQLException ex) {
                    Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
                }

                Saida.setCodigoAbertura(CodigoAbertura);
                Saida.setData(s);
                Saida.setHora(f);
                if (txtDescricao.getText().equals("")) {
                    Saida.setOperacao("Saída");
                } else {
                    Saida.setOperacao(txtDescricao.getText().trim());
                }
                String Valor = txtValor.getText();
                Valor = Valor.replace(".", "");
                Valor = Valor.replace(",", ".");
                Saida.setValor(Float.parseFloat(Valor));

                try {
                    dao.InserindoSaida(Saida);
                } catch (BancoException ex) {
                    Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                }

                JOptionPane.showMessageDialog(this, "Sucesso!");
                try {
                    try {
                        Main.Caixa.AoIniciar();
                    } catch (BancoException ex) {
                        Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.dispose();

                Main.Caixa.setVisible(true);
            }
        }
    }//GEN-LAST:event_txtValorKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Clicou em Finalizar
        boolean Verificandonegativo = false;
        try {
            Verificandonegativo = dao.VerificandoValor(Float.parseFloat(txtValor.getText().replace(".", "").replace(",", ".")));
        } catch (BancoException ex) {
            Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (txtValor.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Valor é um campo de preenchimento obrigatório!");
            txtValor.requestFocus();
        } else if (Verificandonegativo) {
            JOptionPane.showMessageDialog(this, "O Caixa não pode ficar negativo!");
        } else {
            SaidaCaixa Saida = new SaidaCaixa();

            NowString();
            int CodigoAbertura = 0;



            try {
                ResultSet Verificando = dao.VerificaAbertura(s, f);
                while (Verificando.next()) {
                    CodigoAbertura = Verificando.getInt("Codigo");
                }
                Verificando.close();

            } catch (SQLException ex) {
                Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }

            Saida.setCodigoAbertura(CodigoAbertura);
            Saida.setData(s);
            Saida.setHora(f);
            if (txtDescricao.getText().equals("")) {
                Saida.setOperacao("Saída");
            } else {
                Saida.setOperacao(txtDescricao.getText().trim());
            }
            String Valor = txtValor.getText();
            Valor = Valor.replace(".", "");
            Valor = Valor.replace(",", ".");
            Saida.setValor(Float.parseFloat(Valor));

            try {
                dao.InserindoSaida(Saida);
            } catch (BancoException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            }

            JOptionPane.showMessageDialog(this, "Sucesso!");
            try {
                try {
                    Main.Caixa.AoIniciar();
                } catch (BancoException ex) {
                    Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ParseException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();

            Main.Caixa.setVisible(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // apetar enter em finalizar simples
    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Clicou em sair
        this.dispose();
        try {
            try {
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.Caixa.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescricaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Clicou em Finalizar
            boolean Verificandonegativo = false;
            try {
                Verificandonegativo = dao.VerificandoValor(Float.parseFloat(txtValor.getText().replace(".", "").replace(",", ".")));
            } catch (BancoException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (txtValor.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Valor é um campo de preenchimento obrigatório!");
                txtValor.requestFocus();
            } else if (Verificandonegativo) {
                JOptionPane.showMessageDialog(this, "O Caixa não pode ficar negativo!");
            } else {
                SaidaCaixa Saida = new SaidaCaixa();

                NowString();
                int CodigoAbertura = 0;



                try {
                    ResultSet Verificando = dao.VerificaAbertura(s, f);
                    while (Verificando.next()) {
                        CodigoAbertura = Verificando.getInt("Codigo");
                    }
                    Verificando.close();

                } catch (SQLException ex) {
                    Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(CaixaEntrada.class.getName()).log(Level.SEVERE, null, ex);
                }

                Saida.setCodigoAbertura(CodigoAbertura);
                Saida.setData(s);
                Saida.setHora(f);
                if (txtDescricao.getText().equals("")) {
                    Saida.setOperacao("Saída");
                } else {
                    Saida.setOperacao(txtDescricao.getText().trim());
                }
                String Valor = txtValor.getText();
                Valor = Valor.replace(".", "");
                Valor = Valor.replace(",", ".");
                Saida.setValor(Float.parseFloat(Valor));

                try {
                    dao.InserindoSaida(Saida);
                } catch (BancoException ex) {
                    Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                }

                JOptionPane.showMessageDialog(this, "Sucesso!");
                try {
                    try {
                        Main.Caixa.AoIniciar();
                    } catch (BancoException ex) {
                        Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(CaixaSaida.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.dispose();

                Main.Caixa.setVisible(true);
            }
        }
    }//GEN-LAST:event_txtDescricaoKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}