/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Guilherme
 */
public class SubProdutos {
    private int Codigo;
    private String Categoria;
    private int Retirada;
    private String Nome;
    private int Quantidade;
    private int QuantidadeMin;
    private float PrecoCompra;
    private float PrecoVenda;

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public float getPrecoCompra() {
        return PrecoCompra;
    }

    public void setPrecoCompra(float PrecoCompra) {
        this.PrecoCompra = PrecoCompra;
    }

    public float getPrecoVenda() {
        return PrecoVenda;
    }

    public void setPrecoVenda(float PrecoVenda) {
        this.PrecoVenda = PrecoVenda;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int Quantidade) {
        this.Quantidade = Quantidade;
    }

    public int getQuantidadeMin() {
        return QuantidadeMin;
    }

    public void setQuantidadeMin(int QuantidadeMin) {
        this.QuantidadeMin = QuantidadeMin;
    }

    public int getRetirada() {
        return Retirada;
    }

    public void setRetirada(int Retirada) {
        this.Retirada = Retirada;
    }
}
