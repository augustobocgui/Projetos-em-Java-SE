/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Guilherme
 */
public class Vendas {
 
    private int codigo;
    private int aluno_codigo;
    private int funcionario_codigo;
    private String venda_era;
    private double desconto_venda;
    private double total_venda;
    private String situacao;

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getAluno_codigo() {
        return aluno_codigo;
    }

    public void setAluno_codigo(int aluno_codigo) {
        this.aluno_codigo = aluno_codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getDesconto_venda() {
        return desconto_venda;
    }

    public void setDesconto_venda(double desconto_venda) {
        this.desconto_venda = desconto_venda;
    }

    public int getFuncionario_codigo() {
        return funcionario_codigo;
    }

    public void setFuncionario_codigo(int funcionario_codigo) {
        this.funcionario_codigo = funcionario_codigo;
    }

    public double getTotal_venda() {
        return total_venda;
    }

    public void setTotal_venda(double total_venda) {
        this.total_venda = total_venda;
    }

    public String getVenda_era() {
        return venda_era;
    }

    public void setVenda_era(String venda_era) {
        this.venda_era = venda_era;
    }
}
