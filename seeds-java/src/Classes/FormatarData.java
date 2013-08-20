
package Classes;


public class FormatarData
{
    // normal = 05-06-2011
    // banco == 2011-06-05
    
    public String dataBanco(String data) // vai converter para a data no formato do banco de dados
    {
        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6);
        return data = ano + "-" + mes + "-" + dia;
    }
 
    public String dataNormal(String data) // vai converter para a data no formato normal
    {
        String dia = data.substring(8);
        String mes = data.substring(5, 7);
        String ano = data.substring(0, 4);
        return data = dia + "-" + mes + "-" + ano;
    }
}
