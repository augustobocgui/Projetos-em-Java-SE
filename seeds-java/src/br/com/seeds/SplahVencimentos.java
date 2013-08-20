/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class SplahVencimentos extends JWindow {

    AbsoluteLayout absoluto;
    AbsoluteConstraints absimagem, absbarra, absbutton;
    ImageIcon image;
    JLabel jlabel;
    JCheckBox jbutton;
    JProgressBar barra;
    int fechar;
    int reduzir;
    int progresso;
    String s;

    public SplahVencimentos(int lista) {
        fechar = lista;
        absoluto = new AbsoluteLayout();
        absimagem = new AbsoluteConstraints(0, 0);
        absbarra = new AbsoluteConstraints(145, 195);
        absbutton = new AbsoluteConstraints(10, 260);
        jlabel = new JLabel();
        jbutton = new JCheckBox();
        image = new ImageIcon(this.getClass().getResource("backgroundjava.jpg"));
        barra = new JProgressBar();
        barra.setBackground(Color.white);//
        barra.setPreferredSize(new Dimension(200, 20));

        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        s = df.format(now);

        jlabel.setIcon(image);

        this.getContentPane().setLayout(absoluto);
        this.getContentPane().add(barra, absbarra);
        this.getContentPane().add(jbutton, absbutton);
        this.getContentPane().add(jlabel, absimagem);

        reduzir = Math.round(fechar / 100);

        jbutton.setText("Colocar o processo em segundo plano");
        jbutton.setToolTipText("Clique para continuar o trabalho sem ter que ficar esperando que o processo termine");
        jbutton.setBorderPainted(false);
        jbutton.setBorder(null);
        jbutton.setForeground(Color.white);
        jbutton.setContentAreaFilled(false);
         
        new Thread() {
            public void run() {
                int i = 0;
                while (progresso < 100) {
                    barra.setValue(progresso);
                    if(jbutton.isSelected()==true){
                    SplahVencimentos.this.dispose();
                    }
                    i++;
                    if (reduzir <= i) {
                        progresso++;
                        i = 0;
                    }
                    try {
                        sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SplahVencimentos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    Vencimentos SP;
                    SP = new Vencimentos(s);
                    SP.setVisible(true);
                } catch (Exception e) {
                }
                SplahVencimentos.this.dispose();
            }
        }.start();
        this.pack();
        this.setVisible(true);
        setLocationRelativeTo(null);
    }
}
