/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

/**
 *
 * @author Guilherme
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.print.*;
  
// Essa classe é a responsavel pela impressao. Ela detecta a impressora  
// instalada, recebe o texto e o imprime.  
public class Impressao{  
  
    // variavel estatica porque será utilizada por inumeras threads  
    private static PrintService impressora;  
  
    public Impressao() {  
  
        detectaImpressoras();  
  
    }  
  
    // O metodo verifica se existe impressora conectada e a  
    // define como padrao.  
    public void detectaImpressoras() {  
  
        try {  
  
            DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;  
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);  
            for (PrintService p: ps) {  
  
                System.out.println("Impressora encontrada: " + p.getName());  
  
                //if (p.getName().contains("Text") || p.getName().contains("HP Officejet 4500 G510a-f"))  {  
                // if (p.getName().contains("Text") || p.getName().contains("PSC-1500-series")) {
                if (p.getName().contains("Text") || p.getName().contains("Epson LX-300"))  {  
  
                    System.out.println("Impressora Selecionada: " + p.getName());  
                    impressora = p;  
                    break;  
  
                }  
  
            }  
  
        } catch (Exception e) {  
  
            e.printStackTrace();  
  
        }  
  
    }  
  
    public synchronized boolean imprime(String texto) {  
  
        // se nao existir impressora, entao avisa usuario  
        // senao imprime texto  
        if (impressora == null) {  
  
            String msg = "Nennhuma impressora foi encontrada. Instale uma impressora padrão \r\n(Generic Text Only) e reinicie o programa.";  
            System.out.println(msg);  
  
        } else {  
  
            try {  
                DocPrintJob dpj = impressora.createPrintJob();  
                //PageFormat pf = dpj.pageDialog(dpj.defaultPage());
                InputStream stream = new ByteArrayInputStream(texto.getBytes());  
                        
               
                
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;  
                Doc doc = new SimpleDoc(stream, flavor, null);  
                dpj.print(doc, null);  
  
                return true;  
  
            } catch (PrintException e) {  
  
                e.printStackTrace();  
  
            }  
  
        }  
  
        return false;  
  
    }  
  
}
