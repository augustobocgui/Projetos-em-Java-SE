/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import Excessoes.BancoException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Guilherme Meira
 */
public class FazerConexao {

    String linha;
    String linha2;
    String linha3;
    String basededados;
    String ip;
    String porta;

    public Connection fazerConexaoBanco() throws BancoException {

        try {
            FileReader arq = new FileReader("../seeds-java/basededados.txt");
            FileReader arq2 = new FileReader("../seeds-java/ip.txt");
            FileReader arq3 = new FileReader("../seeds-java/porta.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            BufferedReader lerArq2 = new BufferedReader(arq2);
            BufferedReader lerArq3 = new BufferedReader(arq3);
            linha = lerArq.readLine(); // lê a primeira linha
            while (linha != null) {
                basededados = linha;
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            linha2 = lerArq2.readLine(); // lê a primeira linha
            while (linha2 != null) {
                ip = linha2;
                linha2 = lerArq2.readLine(); // lê da segunda até a última linha
            }
            linha3 = lerArq3.readLine(); // lê a primeira linha
            while (linha3 != null) {
                porta = linha3;
                linha3 = lerArq3.readLine(); // lê da segunda até a última linha
            }
            arq.close();
            arq2.close();
            arq3.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo.\n",
                    e.getMessage());
        }

        try {
            return DriverManager.getConnection("jdbc:mysql://" + ip + ":" + porta + "/" + basededados + "", "root", "nuubpmuf");
        } catch (SQLException e) {

            int selection = JOptionPane.showConfirmDialog(null,
                    "Não foi possível estabelecer conexão com o Mysql.\n"
                    + "Deseja configurar a conexão?",
                    "Erro de Conexão", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.OK_OPTION) {
                new ConfiguracoesServidor(null, true).setVisible(true);
            }

            throw new BancoException("Erro ao fazer conexão: " + e);
        }
    }
}
