/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Guilherme
 */
public class SaidaCaixa {
    private String Data;
    private String Hora;
    private String Operacao;
    private float Valor;
    private int CodigoAbertura;

    public int getCodigoAbertura() {
        return CodigoAbertura;
    }

    public void setCodigoAbertura(int CodigoAbertura) {
        this.CodigoAbertura = CodigoAbertura;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public String getOperacao() {
        return Operacao;
    }

    public void setOperacao(String Operacao) {
        this.Operacao = Operacao;
    }

    public float getValor() {
        return Valor;
    }

    public void setValor(float Valor) {
        this.Valor = Valor;
    }
    
}
