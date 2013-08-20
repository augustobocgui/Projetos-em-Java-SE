/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excessoes;

/**
 *
 * @author Guilherme Meira
 */
public class BancoException extends Exception {

    public BancoException(String erro) {
        super(erro);
    }
}
