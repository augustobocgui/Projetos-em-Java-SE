package emailaplicativoseeds;

import java.io.File;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Carteiro {

    private String hostName = "smtp.gmail.com";
    private String usuario = "seedsescola"; // Seu login do Gmail
    private String senha = "emailseeds"; // Sua senha do Gmail
    private String email = "seedsescola@gmail.com"; // Seu e-mail do Gmail
    private SimpleEmail simpleEmail;

    
    int count;
    private String mailSMTPServer;     
    private String mailSMTPServerPort;     
    private File arquivo=null;   
    //private String from="mailto:seedsescola@gmail.com";    
   //private String from="seedsescola@gmail.com"; 
   
   private String from="mailto:seedsescola@gmail.com"; 
   
    private String senhas="emailseeds";  

    
    
    
    public Carteiro() {
        this.simpleEmail = new SimpleEmail();
    }

    public void enviarMensagem(Mensagem mensagem) throws EmailException {
        simpleEmail.setHostName(hostName);
        simpleEmail.setAuthentication(usuario, senha);
        simpleEmail.setSSL(true);
        simpleEmail.setFrom(email);
        simpleEmail.addTo(mensagem.getDestinatario());
        simpleEmail.setSubject(mensagem.getAssunto());
        simpleEmail.setMsg(mensagem.getMensagem());
        simpleEmail.send();

    }


    public String recebe() throws NoSuchProviderException, MessagingException {

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties props = System.getProperties();
        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.port", "995");
        props.setProperty("mail.pop3.socketFactory.port", "995");

        Session session = Session.getDefaultInstance(props, null);

        URLName urln = new URLName("pop3","smtp.gmail.com",465,null,     
    "username@gmail.com", "senha");

        Store store = session.getStore(urln);  // poderia ser imap também  

// conectar a caixa postal  
        store.connect(mailSMTPServer, from, senhas);

        Folder fldr = store.getFolder("INBOX");
        fldr.open(Folder.READ_WRITE);
        count = fldr.getMessageCount();

        //JOptionPane.showMessageDialog(null, count + "  Menssage Inbox", "total messages", JOptionPane.INFORMATION_MESSAGE);

         // Get  a message by its sequence number  
            Message m = fldr.getMessage(count);

            // Get some headers  
            Date date = m.getSentDate();
            Address[] from = m.getFrom();
            String subj = m.getSubject();
            String mimeType = m.getContentType();
String msn;
            msn = "REMETENTE: " + from[0].toString() + "\n ASSUNTO: " + subj;

            //System.out.println(date + "\t" + from[0] + "\t"
              //      + subj + "\t" + mimeType);
            

/*        for (int i = 1; i <= count; i++) {
            // Get  a message by its sequence number  
            Message m = fldr.getMessage(i);

            // Get some headers  
            Date date = m.getSentDate();
            Address[] from = m.getFrom();
            String subj = m.getSubject();
            String mimeType = m.getContentType();

            m.setText("REMETENTE: " + from[0].toString() + "     ASSUNTO: " + subj);

            System.out.println(date + "\t" + from[0] + "\t"
                    + subj + "\t" + mimeType);
        }
        * 
        */       
         return msn;
    }
    
     public int recebeQMSN()throws NoSuchProviderException, MessagingException {

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties props = System.getProperties();
        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.port", "995");
        props.setProperty("mail.pop3.socketFactory.port", "995");

        Session session = Session.getDefaultInstance(props, null);

 URLName urln = new URLName("pop3","smtp.gmail.com",465,null,     
    "username@gmail.com", "senha");

        Store store = session.getStore(urln);  // poderia ser imap também  
        // conectar a caixa postal  
        store.connect(mailSMTPServer, from, senhas);
        Folder fldr = store.getFolder("INBOX");
        fldr.open(Folder.READ_WRITE);
        count = fldr.getMessageCount();

         return count;
     }
}
/*
 public void recebe() throws NoSuchProviderException, MessagingException {     
    
  
         Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());     
  final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";     
    
  Properties props = System.getProperties();     
  props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);     
  props.setProperty("mail.pop3.socketFactory.fallback", "false");     
  props.setProperty("mail.pop3.port", "995");     
  props.setProperty("mail.pop3.socketFactory.port", "995");     
    
  Session session = Session.getDefaultInstance(props,null);     
    
 URLName urln = new URLName("pop3","smtp.gmail.com",465,null,     
    "username@gmail.com", "senha");   
   
Store store = session.getStore(urln);  // poderia ser imap também  
  
// conectar a caixa postal  
 store.connect(mailSMTPServer, from, senha);  
  
   Folder fldr = store.getFolder("INBOX");  
            fldr.open(Folder.READ_WRITE);  
            int count = fldr.getMessageCount();  
              
 JOptionPane.showMessageDialog(this, count+"  Menssage Inbox", "total messages", JOptionPane.INFORMATION_MESSAGE);      
           
              
      for(int i = 1; i <= count; i++) {  
   // Get  a message by its sequence number  
                Message m = fldr.getMessage(i);  
  
                // Get some headers  
                Date date = m.getSentDate();  
                Address [] from = m.getFrom();  
                String subj = m.getSubject();  
                String mimeType = m.getContentType();  
                  
                message.setText("REMETENTE: "+from[0].toString()+ "     ASSUNTO: "+subj  );  
                                  
                  
                System.out.println(date + "\t" + from[0] + "\t" +  
                                    subj + "\t" + mimeType);  
            }  
  
}  
           
  
ai funcionou beleza valeu ai pela ajuda  


 */