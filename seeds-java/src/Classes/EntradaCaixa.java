/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Guilherme
 */
public class EntradaCaixa {
    private String Produto;
    private int Quantidade;
    private float Valor;
    private int CodigoProduto;
    private boolean Simples;
    private String Descricao;
    private String Operacao;
    private String Data;
    private String Hora;
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


    public int getCodigoProduto() {
        return CodigoProduto;
    }

    public void setCodigoProduto(int CodigoProduto) {
        this.CodigoProduto = CodigoProduto;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public String getProduto() {
        return Produto;
    }

    public void setProduto(String Produto) {
        this.Produto = Produto;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int Quantidade) {
        this.Quantidade = Quantidade;
    }

    public boolean isSimples() {
        return Simples;
    }

    public void setSimples(boolean Simples) {
        this.Simples = Simples;
    }

    public float getValor() {
        return Valor;
    }

    public void setValor(float Valor) {
        this.Valor = Valor;
    }
}
