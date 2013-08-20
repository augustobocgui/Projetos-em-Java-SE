package LimiteCampos;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Guilherme
 */
import javax.swing.text.*;

public class FixedLengthDocument extends PlainDocument {

    private int iMaxLength;

    public FixedLengthDocument(int maxlen) {
        super();
        iMaxLength = maxlen;
    }

    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        //if (s == null) return;  

        if (iMaxLength <= 0) // aceitara qualquer no. de caracteres  
        {
            super.insertString(offset, str, attr);
            return;
        }

        int ilen = (getLength() + str.length());
        if (ilen <= iMaxLength) // se o comprimento final for menor...  
        {
            super.insertString(offset, str, attr);   // ...aceita str  
        }
    }
}
