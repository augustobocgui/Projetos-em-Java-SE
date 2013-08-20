/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seeds;

import Excessoes.BancoException;
import br.com.seeds.CaixaInterface;
import br.com.seeds.Login;
import br.com.seeds.Menu;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme Augusto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    /*
     * public static void main(String[] args) { try { fazerConexao conect = new
     * fazerConexao(); conect.fazerConexaoBanco();
     *
     * Login loginObject = new Login(); loginObject.setVisible(true);
     *
     * } catch (Exception e) { JOptionPane.showMessageDialog(null, "Erro ao
     * conectar ao banco!" + e); } }
     *
     */
    //static CaixaInterface Caixa;
    
    public static CaixaInterface Caixa;
    static Menu Menu;
    
    public static void main(String args[]) throws ParseException, SQLException {
        
        new Login().setVisible(true);
        
        //Login TelaLogin = new Login();
        try {
         Caixa = new CaixaInterface();
         } catch (BancoException ex) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
         }
         
        //TelaLogin.setVisible(true);
        
    }
}
