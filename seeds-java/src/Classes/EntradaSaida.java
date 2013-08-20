/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Guilherme Augusto
 */
public class EntradaSaida {

    private int codigo;
    private int produto_codigo;
    private String data_hora;
    private String tipo;
    private int qde;
    private double preco;

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getData_hora() {
        return data_hora;
    }

    public void setData_hora(String data_hora) {
        this.data_hora = data_hora;
    }

    public int getProduto_codigo() {
        return produto_codigo;
    }

    public void setProduto_codigo(int produto_codigo) {
        this.produto_codigo = produto_codigo;
    }

    public int getQde() {
        return qde;
    }

    public void setQde(int qde) {
        this.qde = qde;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
