package br.com.seeds;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Guilherme
 */
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * Restringe a digitação de apenas numeros em um componentes de texto como o
 * JTextField Uso: setDocument(new OnlyNumberField());
 *
 * @author Eduardo Costa - www.dimensaotech.com
 * 
*/
public class OnlyNumberField extends PlainDocument {

    private int maxlength;
    boolean Virgula, UmaVez;

    public OnlyNumberField() {
    }

    public OnlyNumberField(int maxlength) {
        super();
        this.maxlength = maxlength;
    }

    public void insertString(int offs, String str, AttributeSet a) {
        try {
            if (str.equals(",") && !UmaVez) {
                Virgula = true;
                str = ",";
                UmaVez = true;
            }
            Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            if (!Virgula) {
                str = "";
                Toolkit.getDefaultToolkit().beep();
            }
            Virgula = false;
        }
        try {
            boolean fixedLengh = (!((getLength() + str.length()) > maxlength));
            if (maxlength == 0 || fixedLengh) {
                super.insertString(offs, str, a);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
