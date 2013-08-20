/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.seeds;

import Classes.*;
import ClassesDAO.*;
import Excessoes.BancoException;
import emailaplicativoseeds.Carteiro;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import seeds.Main;

/**
 *
 * @author Guilherme Augusto
 */
public class Menu extends javax.swing.JFrame {

    String f, s, dataFormatada;
    boolean aberto = false;
    boolean VerificaFinal = false;
    boolean fecharLacoConexao = true;
    int apagar = 0, inout = 0, inoutsair = 0;
    double totalAReceber = 0.00;
    String parametroPesquisa = "";
    String campo;
    float TotalEmCaixa = 0, TotalEntrada = 0, TotalSaida = 0;
    StringBuffer nome = new StringBuffer();
    StringBuffer nomes = new StringBuffer();
    String tipoFuncionario = "";
    String linha, linha2, linha3, basededados, ip, porta;
    Connection conn = null; //pro compilador ficar feliz
    int erroTurmas = 1;
    int erroVencimentos = 1;
    int erroMatriculas = 1;
    int codigoTurma = 0;
    String status = "Não conectou...";
    int metro = 0;
    int total = 0;

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/br/com/imagens/turma.png")).getImage());
        NowString();
        NowStringInversa();

        txtBuscar.requestFocus();
        Random generator = new Random();
        int randomIndex = generator.nextInt(10) - 1;
        if (randomIndex == -1) {
            jPanel1.setBackground(Color.black);
            txtBuscar.setBackground(Color.black);
        } else if (randomIndex == -1) {
            jPanel1.setBackground(Color.gray);
            txtBuscar.setBackground(Color.gray);
            txtBuscar.setForeground(Color.black);
        } else if (randomIndex == 0) {
            jPanel1.setBackground(Color.white);
            txtBuscar.setBackground(Color.white);
            txtBuscar.setForeground(Color.black);
            jBSTART.setForeground(Color.black);
            txtUsuarioNome.setForeground(Color.black);
            txtUsuarioUserName.setForeground(Color.black);
        } else if (randomIndex == 1) {
            jPanel1.setBackground(Color.lightGray);
            txtBuscar.setBackground(Color.lightGray);
            txtBuscar.setForeground(Color.black);
        }

        try {
            ConectarBanco();
        } catch (Exception e) {
        }
        jBBloquearTela.setBorder(null);
        jBBloquearTela1.setBorder(null);
        txtDataSO.setBorder(null);
        txtNomeDoEmail.setBorder(null);
        txtDataSO12.setBorder(null);
        txtDataSO13.setBorder(null);
        txtDataSO14.setBorder(null);
        txtDataSO15.setBorder(null);
        txtDataSO7.setBorder(null);
        txtTurma.setBorder(null);
        txtTotalDeTurmaHoje.setBorder(null);
        txtTurmaHorario.setBorder(null);
        txtTotalDeAlunos.setBorder(null);
        txtEnderecoIPv4.setBorder(null);
        txtEntradaCaixa.setBorder(null);
        txtEstoqueBaixo1.setBorder(null);
        txtEstoqueBaixo2.setBorder(null);
        txtEstoqueBaixo3.setBorder(null);
        txtMensagemDeEmailSeeds.setBorder(null);
        txtNomedaBasedeDados.setBorder(null);
        txtPorta.setBorder(null);
        txtSaidaCaixa.setBorder(null);
        txtTotalAReceber.setBorder(null);
        txtTotalCaixa.setBorder(null);
        txtTotalDeEmails.setBorder(null);
        txtTotalDeVencimentos.setBorder(null);
        txtUsuarioNome.setBorder(null);
        txtUsuarioUserName.setBorder(null);
        jScrollPane1.setBorder(null);
        jScrollPane2.setBorder(null);
        jLabel1.setBorder(null);
        jLabel2.setBorder(null);
        jLabel3.setBorder(null);
        jLabel5.setBorder(null);
        jLEntradaCaixa.setBorder(null);
        jLMenuLateral.setBorder(null);
        jLMenuLateralAbrirFechar.setBorder(null);
        jLSaidaCaixa.setBorder(null);
        jLSeedsAdministrador.setBorder(null);
        jLSeedsDefaul.setBorder(null);
        jLSeedsSecretaria.setBorder(null);
        jLSeedsServicosGeral.setBorder(null);
        jLStore.setBorder(null);
        jLSeedsVendedores.setBorder(null);
        jBHabilitarMsnErros.setBorder(null);
        jBHabilitarMsnErros.setVisible(false);
        jBHabilitarMsnErros1.setBorder(null);
        jBHabilitarMsnErros1.setVisible(false);
        jBNaoHabilitdo.setBorder(null);
        jBNaoHabilitdo1.setBorder(null);
        jBNaoHabilitdo2.setBorder(null);
        jBNaoHabilitdo3.setBorder(null);
        jBNaoHabilitdo4.setBorder(null);
        jBBuscarPagamento.setVisible(false);
        jBBuscarContrato.setVisible(false);
        jBBuscarMatricula.setVisible(false);
        jBBuscarAluno.setVisible(false);
        jBBuscarRelatorio.setVisible(false);
        jBBloquearTela.setVisible(false);
        jBBloquearTela1.setVisible(false);
        jBEmailInBox.setVisible(false);
        jBCaixaAberto.setVisible(false);
        jBCaixaFechado.setVisible(false);
        jLSeedsAdministrador.setVisible(false);
        jLSeedsSecretaria.setVisible(false);
        jLSeedsServicosGeral.setVisible(false);
        jLSeedsVendedores.setVisible(false);
        jPMenuLateral.setVisible(false);
        jLMenuLateral.setVisible(false);
        jBConfigurar.setVisible(false);
        jBCadastro.setVisible(false);
        jBFinancas.setVisible(false);
        jBEstoques.setVisible(false);
        jBVendas.setVisible(false);
        jBNaoConectado.setVisible(false);
        jBConectado.setVisible(false);
        jBConectadoIn.setVisible(false);
        jBConectadoOut.setVisible(false);
        txtTotalCaixa.setDocument(new LimitadorMoeda());
        txtEntradaCaixa.setDocument(new LimitadorMoeda());
        txtSaidaCaixa.setDocument(new LimitadorMoeda());


        usuarioLogado();


        try {
            VerificaFinal = VerificaAbetura();
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BancoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            try {
                AoIniciar();//CAIXA
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBConectado.setVisible(false);
                jBNaoConectado.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBConectado.setVisible(false);
                jBNaoConectado.setVisible(true);
            }
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            jBConectado.setVisible(false);
            jBNaoConectado.setVisible(true);
        }

        new Thread() {
            public void run() {
                while (true) {
                    Date date = new Date();
                    txtDataSO.setText(date + "");

                    try {
                        Thread.sleep(1000); // espera um segundo
                    } catch (InterruptedException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        }.start();


        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);//Abri o Frame Maximizado
        //this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                try {
                    try {
                        try {
                            int selection = JOptionPane.showConfirmDialog(rootPane,
                                    "Deseja sair do sistema?",
                                    "Finalizar o sistema", JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (selection == JOptionPane.OK_OPTION) {
                                Fechando();
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(BuscarSubprodutosCaixaVenda.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(BuscarSubprodutosCaixaVenda.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (BancoException ex) {
                    Logger.getLogger(BuscarSubprodutosCaixaVenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Para 
        jBConectado.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                //Verificando se o botão direito do mouse foi clicado  
                if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                    JPopupMenu menus = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Clique-me para checar a conexão.");
                    menus.add(item);

                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            try {
                                inoutsair = 0;
                                fecharLacoConexao = true;
                                ConectarBanco();
                            } catch (Exception e) {
                            }
                        }
                    });
                    menus.show(jBConectado, me.getX(), me.getY());
                }
            }
        });

        //Para 
        jBNaoConectado.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                //Verificando se o botão direito do mouse foi clicado  
                if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                    JPopupMenu menus = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Clique-me para checar a conexão.");
                    menus.add(item);

                    item.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            try {
                                inoutsair = 0;
                                fecharLacoConexao = true;
                                ConectarBanco();
                            } catch (Exception e) {
                            }
                        }
                    });
                    menus.show(jBNaoConectado, me.getX(), me.getY());
                }
            }
        });
    }

    public void Fechando() throws BancoException, SQLException, ParseException {
        System.exit(0);
    }

    public void metro() {
        if (metro == 0) {
            turmaFuncionando();//Turma NÃO PODE ESTAR EM THREAD
            estoqueEmFalta();//ESTOQUE
            vencimentos();
            txtTotalAReceber.setText("Total a receber " + totalAReceber + "");
            txtTotalDeVencimentos.setText("Total de vencimentos " + apagar + "");
        }
    }

    public void turmaFuncionando() {

        try {
            TurmasDAO dao = new TurmasDAO();
            List<Turmas> listaTurmas = dao.pesquisaTurma(parametroPesquisa, campo);
            apagar = 0;

            Date date = new Date();

            String dia = (pesquisarDiaSemana(date + ""));
            String hora = getHora() + "";

            for (int us = 0; us < listaTurmas.size(); us++) {

                if (listaTurmas.get(us).getDia().contains(dia)) {
                    if (apagar == 0) {
                        txtTurma.setText(listaTurmas.get(us).getCurso() + "");
                        txtTurmaHorario.setText("Horário às " + listaTurmas.get(us).getHi() + " e termina às " + listaTurmas.get(us).getHf());
                    }
                    if (listaTurmas.get(us).getHf().contains(hora) && !txtTurmaHorario.getText().contains("Começa")) {
                        txtTurma.setText(listaTurmas.get(us).getCurso() + "");
                        txtTurmaHorario.setText("Começa às " + listaTurmas.get(us).getHi() + " e termina às " + listaTurmas.get(us).getHf());
                        codigoTurma = listaTurmas.get(us).getCodigo();
                        contarMatriculas();

                    } else {
                        if (listaTurmas.get(us).getHi().contains(hora)) {
                            txtTurma.setText(listaTurmas.get(us).getCurso() + "");
                            txtTurmaHorario.setText("Começa às " + listaTurmas.get(us).getHi() + " e termina às " + listaTurmas.get(us).getHf());
                            codigoTurma = listaTurmas.get(us).getCodigo();
                            contarMatriculas();
                        }
                    }
                    apagar++;
                }
                txtTotalDeTurmaHoje.setText("Total de hoje é " + apagar + " turmas");
            }
            if(txtTurma.getText().equals("null")){
                txtTurma.setText("Minhas Turmas");
            }
            listaTurmas = null;
            dao.desconectar();
        } catch (Exception e) {
            if (erroTurmas < 1) {
                JOptionPane.showMessageDialog(null,
                        "\nVocê possuí campos vazios nos registros da tabela de Turmas.\n"
                        + "Todos os campos da tabela de cadastro de turma são obrigatórios.\n"
                        + "Favor corrigir os dados ou excluír os registros que não estão em conformidade ou \n"
                        + "algum erro na conexão pode ter comprometido a busca dos dados.",
                        "" + e, JOptionPane.ERROR_MESSAGE);
            }
            erroTurmas = 1;
        }

    }

    public void contarMatriculas() {
        int contarAlunos = 0;
        try {
            MatriculaDAO dao = new MatriculaDAO();
            List<Matricula> lista = dao.pesquisaQuantosMatriculados(parametroPesquisa, campo);

            contarAlunos = 0;
            NowString();
            NowStringInversa();

            for (int us = 0; us < lista.size(); us++) {

                if (lista.get(us).getCodigoTurma() == codigoTurma) {

                    contarAlunos++;

                }
            }

            txtTotalDeAlunos.setText("Total de alunos é " + contarAlunos);
            lista = null;
            dao.desconectar();
        } catch (Exception e) {
            if (erroMatriculas < 1) {
                JOptionPane.showMessageDialog(null,
                        "\nVocê possuí campos vazios nos registros da tabela de Matrículas.\n"
                        + "Todos os campos da tabela de matrículas são obrigatórios.\n"
                        + "Favor corrigir os dados ou excluír os registros que não estão em conformidade ou \n"
                        + "algum erro na conexão pode ter comprometido a busca dos dados.",
                        "" + e, JOptionPane.ERROR_MESSAGE);
            }
            erroMatriculas = 1;
        }

    }

    public String getHora() {
        // cria um StringBuilder
        StringBuilder sb = new StringBuilder();
        // cria um GregorianCalendar que vai conter a hora atual
        GregorianCalendar d = new GregorianCalendar();
        // anexa do StringBuilder os dados da hora
        sb.append(d.get(GregorianCalendar.HOUR_OF_DAY));
        sb.append(":");
        //sb.append(d.get(GregorianCalendar.MINUTE));
        //sb.append( ":" );
        //sb.append( d.get( GregorianCalendar.SECOND ) );
        // retorna a String do StringBuilder
        return sb.toString();
    }

    public void NowString() {
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        DateFormat dg = DateFormat.getTimeInstance();
        s = df.format(now);
        f = dg.format(now);
    }

    public void NowStringInversa() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        dataFormatada = df.format(c.getTime());
    }

    public boolean VerificaAbetura() throws ParseException, SQLException, BancoException {
        CaixaDAO dao = new CaixaDAO();
        ResultSet Verifica = dao.VerificaAbertura(s, f);
        boolean Aberto = false;
        while (Verifica.next()) {
            Aberto = true;
        }
        Verifica.close();
        if (Aberto) {
            ResultSet rs = dao.VerificaFechamento(s, f);
            boolean Ver = false;
            while (rs.next()) {
                Ver = true;
            }
            rs.close();
            if (!Ver) {
                return true;
            } else {
                return false;
            }
        } else if (!Aberto) {
            return false;
        } else {
            return Aberto;
        }
    }

    public void estoqueEmFalta() {
        try {
            SubProdutosDAO dao = new SubProdutosDAO();
            List<SubProdutos> lista = dao.pesquisaProduto(parametroPesquisa, campo);

            Object[][] dados = new Object[lista.size()][4];

            txtEstoqueBaixo1.setText("");
            txtEstoqueBaixo2.setText("");
            txtEstoqueBaixo3.setText("");
            for (int us = 0; us < lista.size(); us++) {

                if (lista.get(us).getQuantidade() <= lista.get(us).getQuantidadeMin()) {
                    dados[us][0] = lista.get(us).getCodigo();
                    dados[us][1] = lista.get(us).getNome();
                    dados[us][2] = lista.get(us).getQuantidade();
                    dados[us][3] = lista.get(us).getQuantidadeMin();
                    if (txtEstoqueBaixo1.getText().equals("")
                            && !txtEstoqueBaixo1.getText().equals(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3])
                            && !txtEstoqueBaixo2.getText().equals(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3])
                            && !txtEstoqueBaixo3.getText().equals(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3])) {
                        txtEstoqueBaixo1.setText(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3]);
                    } else if (txtEstoqueBaixo2.getText().equals("")
                            && !txtEstoqueBaixo1.getText().equals(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3])
                            && !txtEstoqueBaixo2.getText().equals(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3])
                            && !txtEstoqueBaixo3.getText().equals(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3])) {
                        txtEstoqueBaixo2.setText(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3]);
                    } else if (txtEstoqueBaixo3.getText().equals("")
                            && !txtEstoqueBaixo1.getText().equals(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3])
                            && !txtEstoqueBaixo2.getText().equals(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3])
                            && !txtEstoqueBaixo3.getText().equals(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3])) {
                        txtEstoqueBaixo3.setText(dados[us][1] + ": " + dados[us][2] + " - " + dados[us][3]);
                    }
                }
            }
            lista = null;
            dao.desconectar();
        } catch (BancoException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void vencimentos() {
        try {
            ContasRDAO dao = new ContasRDAO();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(dataFormatada);
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(s);
            List<ContasR> lista = dao.pesquisacontasRe(nome, nomes);
            apagar = 0;

            for (int us = 0; us < lista.size(); us++) {

                if (lista.get(us).getTotal() != 1.00) {
                    if (lista.get(us).getData_pagamento().equals(s) || lista.get(us).getData_pagamento().equals(dataFormatada)) {

                        if (lista.get(us).getValor_pago() < lista.get(us).getValor()) {
                            totalAReceber += lista.get(us).getValor();
                        }
                        apagar++;
                    }
                }
            }
            lista = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
            dao.desconectar();
        } catch (Exception e) {
            if (erroVencimentos < 1) {
                JOptionPane.showMessageDialog(null,
                        "\nVocê possuí campos vazios nos registros da tabela de Contas a receber [Pagamentos].\n"
                        + "Varios campos da tabela de contas receber contratos são obrigatórios.\n"
                        + "Favor corrigir os dados ou excluír os registros que não estão em conformidade ou \n"
                        + "algum erro na conexão pode ter comprometido a busca dos dados.",
                        "" + e, JOptionPane.ERROR_MESSAGE);
            }
            erroVencimentos = 1;
        }
    }

    //faz a pesquisa, dado um inteiro de 1 a 7  
    public String pesquisarDiaSemana(String diaSemana) {

        if (diaSemana.contains("Sun")) {
            diaSemana = "Domingo";
        } else if (diaSemana.contains("Mon")) {
            diaSemana = "Segunda";
        } else if (diaSemana.contains("Tue")) {
            diaSemana = "Terça";
        } else if (diaSemana.contains("Wed")) {
            diaSemana = "Quarta";
        } else if (diaSemana.contains("Thu")) {
            diaSemana = "Quinta";
        } else if (diaSemana.contains("Fri")) {
            diaSemana = "Sexta";
        } else {
            //if (diaSemana.contains("Sat")) {
            diaSemana = "Sábado";
        }

        return diaSemana;

    }

    public void getConectar() {

        conn = null; //pro compilador ficar feliz
        try {

            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);

            String serverName = ip;
            String mydatabase = basededados;
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String user = "root";
            String key = "nuubpmuf";
            conn = DriverManager.getConnection(url, user, key);

            //Testa sua conexão// 
            if (conn != null) {
                status = ("STATUS--->Conectado com sucesso!");
                jBConectado.setVisible(true);
                jBNaoConectado.setVisible(false);
                jBConectadoIn.setVisible(false);
                jBConectadoOut.setVisible(false);
            } else {
                status = ("STATUS--->Não foi possivel realizar conexão");
                jBConectado.setVisible(false);
                jBNaoConectado.setVisible(true);
                jBConectadoIn.setVisible(false);
                jBConectadoOut.setVisible(false);
            }
            try {
                conn.close();
                DriverManager.getConnection(url, user, key).close();
            } catch (SQLException ex) {
                //Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBConectado.setVisible(false);
                jBNaoConectado.setVisible(true);
                jBConectadoIn.setVisible(false);
                jBConectadoOut.setVisible(false);
                status = ("STATUS--->Não foi possivel realizar conexão");
            }

        } catch (ClassNotFoundException e) {  //Driver não encontrado 
            System.out.println("O driver expecificado nao foi encontrado.");
            status = ("STATUS--->O driver expecificado nao foi encontrado.");
            jBConectado.setVisible(false);
            jBNaoConectado.setVisible(true);
            jBConectadoIn.setVisible(false);
            jBConectadoOut.setVisible(false);
        } catch (SQLException e) {
            //Não conseguindo se conectar ao banco
            status = ("STATUS--->Não conseguindo se conectar ao banco.");
            jBConectado.setVisible(false);
            jBNaoConectado.setVisible(true);
            jBConectadoIn.setVisible(false);
            jBConectadoOut.setVisible(false);
            //System.out.println("Nao foi possivel conectar ao Banco de Dados.");
        }
    }

    public Connection ConectarBanco() throws BancoException {
        try {
            FileReader arq = new FileReader("../seeds-java/basededados.txt");
            FileReader arq2 = new FileReader("../seeds-java/ip.txt");
            FileReader arq3 = new FileReader("../seeds-java/porta.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            BufferedReader lerArq2 = new BufferedReader(arq2);
            BufferedReader lerArq3 = new BufferedReader(arq3);
            linha = lerArq.readLine(); // lê a primeira linha
            txtNomedaBasedeDados.setText("Base de dados " + linha);
            while (linha != null) {
                basededados = linha;
                linha = lerArq.readLine(); // lê da segunda até a última linha
            }
            linha2 = lerArq2.readLine(); // lê a primeira linha 
            if (linha2.equals("localhost")) {
                metro();//Inicia o Metro
                metro = 1;//Permite que inicie apenas uma vez
                erroTurmas = 0;
                erroVencimentos = 0;
                erroMatriculas = 0;
                jBHabilitarMsnErros1.setToolTipText("Habilitado!");
                jBHabilitarMsnErros.setVisible(false);
                jBHabilitarMsnErros1.setVisible(true);
                jBNaoHabilitdo.setVisible(false);
                jBNaoHabilitdo1.setVisible(false);
                jBNaoHabilitdo2.setVisible(false);
                jBNaoHabilitdo3.setVisible(false);
            }
            txtEnderecoIPv4.setText("IPv4 " + linha2);
            while (linha2 != null) {
                ip = linha2;
                linha2 = lerArq2.readLine(); // lê da segunda até a última linha
            }
            linha3 = lerArq3.readLine(); // lê a primeira linha
            txtPorta.setText("Porta " + linha3);
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

            new Thread() {
                public void run() {
                    while (fecharLacoConexao) {

                        jBConectado.setVisible(false);
                        jBNaoConectado.setVisible(false);
                        if (inout == 0) {
                            jBConectadoIn.setVisible(true);
                            jBConectadoOut.setVisible(false);
                            inout = 1;
                            inoutsair++;
                        } else {
                            jBConectadoIn.setVisible(false);
                            jBConectadoOut.setVisible(true);
                            inout = 0;
                        }
                        try {
                            Thread.sleep(2000); // espera um segundo
                        } catch (InterruptedException exc) {
                            exc.printStackTrace();
                        }
                        if (inoutsair > 1) {
                            if (status.equals("STATUS--->Conectado com sucesso!")) {
                                jBConectadoIn.setVisible(false);
                                jBConectadoOut.setVisible(false);
                                jBConectado.setVisible(true);
                                jBNaoConectado.setVisible(false);
                            } else {
                                jBConectadoIn.setVisible(false);
                                jBConectadoOut.setVisible(false);
                                jBConectado.setVisible(false);
                                jBNaoConectado.setVisible(true);
                            }
                            fecharLacoConexao = false;
                        }
                    }
                }
            }.start();

            inoutsair = 0;
            linha = "";
            linha2 = "";
            linha3 = "";

            getConectar();
            //Concexão in out in out com tread
            return DriverManager.getConnection("jdbc:mysql://" + ip + ":" + porta + "/" + basededados + "", "root", "nuubpmuf");

        } catch (SQLException e) {
            status = ("STATUS--->Não conseguindo se conectar ao banco.");
            if (status.equals("STATUS--->Conectado com sucesso!")) {
                jBConectadoIn.setVisible(false);
                jBConectadoOut.setVisible(false);
                jBConectado.setVisible(true);
                jBNaoConectado.setVisible(false);
            } else {
                jBConectadoIn.setVisible(false);
                jBConectadoOut.setVisible(false);
                jBConectado.setVisible(false);
                jBNaoConectado.setVisible(true);
            }
            txtNomedaBasedeDados.setText("Base de dados ");
            txtEnderecoIPv4.setText("IPv4 ");
            txtPorta.setText("Porta ");
            throw new BancoException("Erro ao fazer conexão: " + e);

        }
    }

    public void AoIniciar() throws ParseException, BancoException, SQLException {
        TotalEmCaixa = 0;
        TotalSaida = 0;
        TotalEntrada = 0;
        try {
            CaixaDAO Caixa = new CaixaDAO();
            NowString();
            ResultSet rs = Caixa.VerificaAbertura(s, f);
            float ValorInicial = 0;
            boolean Verificando = false;
            while (rs.next()) {
                Verificando = true;
                aberto = true;
            }
            rs.close();
            if (Verificando) {

                txtTotalCaixa.setEnabled(true);
                txtEntradaCaixa.setEnabled(true);
                txtSaidaCaixa.setEnabled(true);
                txtTotalCaixa.setText("0000");
                txtEntradaCaixa.setText("0000");
                txtSaidaCaixa.setText("0000");
                jBCaixaAberto.setVisible(true);
                jBCaixaFechado.setVisible(false);

                ResultSet PuxandoValorInicial = Caixa.SelecaoValorInicial(s);
                while (PuxandoValorInicial.next()) {
                    ValorInicial = PuxandoValorInicial.getFloat("Valor");
                }
                PuxandoValorInicial.close();

                ResultSet PuxandoEntrada = Caixa.SelecaoEntrada(s);
                while (PuxandoEntrada.next()) {
                    TotalEntrada = TotalEntrada + (PuxandoEntrada.getFloat("Valor") * PuxandoEntrada.getInt("Quantidade"));
                }
                PuxandoEntrada.close();

                ResultSet PuxandoSaida = Caixa.SelecaoSaida(s);
                while (PuxandoSaida.next()) {
                    TotalSaida = TotalSaida + (PuxandoSaida.getFloat("Valor"));
                }
                PuxandoSaida.close();

                TotalEmCaixa = (ValorInicial + TotalEntrada) - TotalSaida;
                String Valor = new DecimalFormat("0.00").format(TotalEmCaixa);
                Valor = Valor.replace(".", "");
                Valor = Valor.replace(",", "");

                String ValorEntrada = new DecimalFormat("0.00").format(TotalEntrada);
                ValorEntrada = ValorEntrada.replace(".", "");
                ValorEntrada = ValorEntrada.replace(",", "");

                String ValorSaida = new DecimalFormat("0.00").format(TotalSaida);
                ValorSaida = ValorSaida.replace(".", "");
                ValorSaida = ValorSaida.replace(",", "");

                txtTotalCaixa.setText(Valor);
                txtEntradaCaixa.setText(ValorEntrada);
                txtSaidaCaixa.setText(ValorSaida);

                //Verificando se caixa está fechado
                ResultSet rs1 = Caixa.VerificaFechamento(s);
                while (rs1.next()) {
                    Verificando = true;
                    jBCaixaFechado.setVisible(true);
                    jBCaixaAberto.setVisible(false);

                }
                rs1.close();
            }
            Caixa = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
        } catch (BancoException ex) {
            Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
            jBConectado.setVisible(false);
            jBNaoConectado.setVisible(true);
            jBConectadoIn.setVisible(false);
            jBConectadoOut.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
            jBConectado.setVisible(false);
            jBNaoConectado.setVisible(true);
            jBConectadoIn.setVisible(false);
            jBConectadoOut.setVisible(false);
        }

    }

    public void usuarioLogado() {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            AcessoDAO daos = new AcessoDAO();
            Acesso user = new Acesso();
            Usuario users = new Usuario();
            tipoFuncionario = "" + dao.gerarCodigoUsuarioUltimoLogado();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(tipoFuncionario);

            if (tipoFuncionario.equals("0")) {
                do {
                    int remove = Integer.parseInt(nome + "");
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    nome.append(remove - 1);
                    user = daos.carregarAcessoPeloCodigo(nome);

                    txtUsuarioUserName.setText("" + user.getUsuario());

                    users = dao.carregarUsuarioPeloCodigo(user.getUsuario());
                    txtUsuarioNome.setText("" + users.getNome());
                    tipoFuncionario = users.getFuncao();
                    txtUsuarioUserName.setText("" + users.getLogin());

                    if (tipoFuncionario.equals("Administrador")) {
                        jLSeedsAdministrador.setVisible(true);
                        jLSeedsDefaul.setVisible(false);
                        jLSeedsSecretaria.setVisible(false);
                        jLSeedsServicosGeral.setVisible(false);
                        jLSeedsVendedores.setVisible(false);

                    }
                    if (tipoFuncionario.equals("Vendedor (a)")) {
                        jLSeedsAdministrador.setVisible(false);
                        jLSeedsDefaul.setVisible(false);
                        jLSeedsSecretaria.setVisible(false);
                        jLSeedsServicosGeral.setVisible(false);
                        jLSeedsVendedores.setVisible(true);
                        jBEmailInBox.setVisible(false);
                        jBEmailOutBox.setVisible(false);
                        jBXEmail.setVisible(false);
                    }
                    if (tipoFuncionario.equals("Secretário (a)")) {
                        jLSeedsAdministrador.setVisible(false);
                        jLSeedsDefaul.setVisible(false);
                        jLSeedsSecretaria.setVisible(true);
                        jLSeedsServicosGeral.setVisible(false);
                        jLSeedsVendedores.setVisible(false);
                        jBEmailInBox.setVisible(false);
                        jBEmailOutBox.setVisible(false);
                        jBXEmail.setVisible(false);
                    }
                    if (tipoFuncionario.equals("Serviços Gerais")) {
                        jLSeedsAdministrador.setVisible(false);
                        jLSeedsDefaul.setVisible(false);
                        jLSeedsSecretaria.setVisible(false);
                        jLSeedsServicosGeral.setVisible(true);
                        jLSeedsVendedores.setVisible(false);
                        jBEmailInBox.setVisible(false);
                        jBEmailOutBox.setVisible(false);
                        jBXEmail.setVisible(false);
                    }


                } while (nome.equals("0"));

                txtUsuarioNome.setBackground(Color.red);
                txtUsuarioNome.setToolTipText("Não foi possível identificar o usuário *logado no sistema! "
                        + "Isso não prejudica outras funções executadas.\n"
                        + "*Que está identificado no sistema computacional.");
                txtUsuarioUserName.setVisible(false);
            } else {
                user = daos.carregarAcessoPeloCodigo(nome);
                txtUsuarioUserName.setText("" + user.getUsuario());


                users = dao.carregarUsuarioPeloCodigo(user.getUsuario());
                txtUsuarioNome.setText("" + users.getNome());
                tipoFuncionario = users.getFuncao();
                txtUsuarioUserName.setText("" + users.getLogin());
                if (tipoFuncionario.equals("Administrador")) {
                    jLSeedsAdministrador.setVisible(true);
                    jLSeedsDefaul.setVisible(false);
                    jLSeedsSecretaria.setVisible(false);
                    jLSeedsServicosGeral.setVisible(false);
                    jLSeedsVendedores.setVisible(false);

                }
                if (tipoFuncionario.equals("Vendedor (a)")) {
                    jLSeedsAdministrador.setVisible(false);
                    jLSeedsDefaul.setVisible(false);
                    jLSeedsSecretaria.setVisible(false);
                    jLSeedsServicosGeral.setVisible(false);
                    jLSeedsVendedores.setVisible(true);
                    jBEmailInBox.setVisible(false);
                    jBEmailOutBox.setVisible(false);
                    jBXEmail.setVisible(false);
                }
                if (tipoFuncionario.equals("Secretário (a)")) {
                    jLSeedsAdministrador.setVisible(false);
                    jLSeedsDefaul.setVisible(false);
                    jLSeedsSecretaria.setVisible(true);
                    jLSeedsServicosGeral.setVisible(false);
                    jLSeedsVendedores.setVisible(false);
                    jBEmailInBox.setVisible(false);
                    jBEmailOutBox.setVisible(false);
                    jBXEmail.setVisible(false);
                }
                if (tipoFuncionario.equals("Serviços Gerais")) {
                    jLSeedsAdministrador.setVisible(false);
                    jLSeedsDefaul.setVisible(false);
                    jLSeedsSecretaria.setVisible(false);
                    jLSeedsServicosGeral.setVisible(true);
                    jLSeedsVendedores.setVisible(false);
                    jBEmailInBox.setVisible(false);
                    jBEmailOutBox.setVisible(false);
                    jBXEmail.setVisible(false);
                }
            }
            user = null;//Liberar objetos obsoletos logo após o uso atribuindo null.
            users = null;
            dao.desconectar();
            daos.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPMenuLateral = new javax.swing.JPanel();
        jBConfigurar = new javax.swing.JButton();
        jBCadastro = new javax.swing.JButton();
        jBFinancas = new javax.swing.JButton();
        jBEstoques = new javax.swing.JButton();
        jBVendas = new javax.swing.JButton();
        jLMenuLateral = new javax.swing.JLabel();
        jLMenuLateralAbrirFechar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDataSO = new javax.swing.JTextArea();
        jBXMeusVencimentos1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtNomeDoEmail = new javax.swing.JTextField();
        jBEmailOutBox = new javax.swing.JButton();
        jBXEmail = new javax.swing.JButton();
        txtTotalDeEmails = new javax.swing.JTextField();
        jBEmailInBox = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMensagemDeEmailSeeds = new javax.swing.JTextArea();
        jBNaoHabilitdo4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtTotalCaixa = new javax.swing.JTextField();
        txtEntradaCaixa = new javax.swing.JTextField();
        txtSaidaCaixa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jBCaixaAberto = new javax.swing.JButton();
        jLEntradaCaixa = new javax.swing.JLabel();
        jLSaidaCaixa = new javax.swing.JLabel();
        jBCaixaFechado = new javax.swing.JButton();
        jBNaoHabilitdo = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtDataSO7 = new javax.swing.JTextField();
        jLStore = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtDataSO12 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtTurma = new javax.swing.JTextField();
        txtTotalDeTurmaHoje = new javax.swing.JTextField();
        txtDataSO13 = new javax.swing.JTextField();
        txtTurmaHorario = new javax.swing.JTextField();
        txtTotalDeAlunos = new javax.swing.JTextField();
        jBNaoHabilitdo1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtDataSO15 = new javax.swing.JTextField();
        txtEstoqueBaixo1 = new javax.swing.JTextField();
        txtEstoqueBaixo2 = new javax.swing.JTextField();
        txtEstoqueBaixo3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jBNaoHabilitdo2 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtDataSO14 = new javax.swing.JTextField();
        txtTotalDeVencimentos = new javax.swing.JTextField();
        txtTotalAReceber = new javax.swing.JTextField();
        jBVencimentos = new javax.swing.JButton();
        jBNaoHabilitdo3 = new javax.swing.JButton();
        jBXMeusVencimentos = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtUsuarioNome = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        txtPorta = new javax.swing.JTextField();
        txtEnderecoIPv4 = new javax.swing.JTextField();
        txtNomedaBasedeDados = new javax.swing.JTextField();
        jBConectado = new javax.swing.JButton();
        jBNaoConectado = new javax.swing.JButton();
        jBConectadoOut = new javax.swing.JButton();
        jBConectadoIn = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLSeedsDefaul = new javax.swing.JLabel();
        jLSeedsAdministrador = new javax.swing.JLabel();
        jLSeedsServicosGeral = new javax.swing.JLabel();
        jLSeedsSecretaria = new javax.swing.JLabel();
        jLSeedsVendedores = new javax.swing.JLabel();
        txtUsuarioUserName = new javax.swing.JTextField();
        jBSTART = new javax.swing.JButton();
        jBBloquearTela = new javax.swing.JButton();
        jBBloquearTela1 = new javax.swing.JButton();
        jBHabilitarMsnErros = new javax.swing.JButton();
        jBHabilitarMsnErros1 = new javax.swing.JButton();
        jBBuscarIcones = new javax.swing.JButton();
        jBBuscarPagamento = new javax.swing.JButton();
        jBBuscarContrato = new javax.swing.JButton();
        jBBuscarMatricula = new javax.swing.JButton();
        jBBuscarAluno = new javax.swing.JButton();
        jBBuscarRelatorio = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();

        jMenuItem14.setText("jMenuItem14");

        jMenuItem15.setText("jMenuItem15");

        jMenuItem16.setText("jMenuItem16");

        jMenuItem17.setText("jMenuItem17");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu - Seeds Software Java 2.0");

        jPanel1.setBackground(new java.awt.Color(51, 0, 51));
        jPanel1.setFocusable(false);
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(null);

        jPMenuLateral.setOpaque(false);
        jPMenuLateral.setLayout(null);
        jPanel1.add(jPMenuLateral);
        jPMenuLateral.setBounds(1180, 0, 0, 720);

        jBConfigurar.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jBConfigurar.setForeground(new java.awt.Color(204, 204, 204));
        jBConfigurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Configuração.png"))); // NOI18N
        jBConfigurar.setText("Configura");
        jBConfigurar.setToolTipText("");
        jBConfigurar.setBorderPainted(false);
        jBConfigurar.setContentAreaFilled(false);
        jBConfigurar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBConfigurar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jBConfigurar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBConfigurar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBConfigurarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBConfigurarMouseExited(evt);
            }
        });
        jBConfigurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConfigurarActionPerformed(evt);
            }
        });
        jPanel1.add(jBConfigurar);
        jBConfigurar.setBounds(1190, 510, 90, 90);

        jBCadastro.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jBCadastro.setForeground(new java.awt.Color(204, 204, 204));
        jBCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/cadastro.png"))); // NOI18N
        jBCadastro.setText("Cadastro");
        jBCadastro.setBorderPainted(false);
        jBCadastro.setContentAreaFilled(false);
        jBCadastro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBCadastro.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jBCadastro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBCadastro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCadastroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCadastroMouseExited(evt);
            }
        });
        jBCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCadastroActionPerformed(evt);
            }
        });
        jPanel1.add(jBCadastro);
        jBCadastro.setBounds(1190, 390, 90, 90);

        jBFinancas.setForeground(new java.awt.Color(204, 204, 204));
        jBFinancas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Finanças.png"))); // NOI18N
        jBFinancas.setText("Financeiro");
        jBFinancas.setBorderPainted(false);
        jBFinancas.setContentAreaFilled(false);
        jBFinancas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBFinancas.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jBFinancas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBFinancas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBFinancasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBFinancasMouseExited(evt);
            }
        });
        jBFinancas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFinancasActionPerformed(evt);
            }
        });
        jPanel1.add(jBFinancas);
        jBFinancas.setBounds(1190, 270, 90, 90);

        jBEstoques.setForeground(new java.awt.Color(204, 204, 204));
        jBEstoques.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Estoques.png"))); // NOI18N
        jBEstoques.setText("Estoque");
        jBEstoques.setBorderPainted(false);
        jBEstoques.setContentAreaFilled(false);
        jBEstoques.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBEstoques.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jBEstoques.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBEstoques.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEstoquesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEstoquesMouseExited(evt);
            }
        });
        jBEstoques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEstoquesActionPerformed(evt);
            }
        });
        jPanel1.add(jBEstoques);
        jBEstoques.setBounds(1190, 150, 80, 90);

        jBVendas.setForeground(new java.awt.Color(204, 204, 204));
        jBVendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/vendas.png"))); // NOI18N
        jBVendas.setText("Vendas");
        jBVendas.setBorderPainted(false);
        jBVendas.setContentAreaFilled(false);
        jBVendas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBVendas.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jBVendas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBVendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVendasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVendasMouseExited(evt);
            }
        });
        jBVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVendasActionPerformed(evt);
            }
        });
        jPanel1.add(jBVendas);
        jBVendas.setBounds(1190, 30, 80, 90);

        jLMenuLateral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/barra_lateral_config.png"))); // NOI18N
        jLMenuLateral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLMenuLateralMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLMenuLateralMouseExited(evt);
            }
        });
        jPanel1.add(jLMenuLateral);
        jLMenuLateral.setBounds(1190, -10, 90, 770);

        jLMenuLateralAbrirFechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLMenuLateralAbrirFecharMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLMenuLateralAbrirFecharMouseExited(evt);
            }
        });
        jPanel1.add(jLMenuLateralAbrirFechar);
        jLMenuLateralAbrirFechar.setBounds(1250, -10, 30, 720);

        jPanel2.setBackground(new java.awt.Color(255, 200, 0));
        jPanel2.setFocusable(false);
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(null);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Calendar");
        jLabel1.setFocusable(false);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 124, 96, 15);

        jScrollPane2.setBackground(new java.awt.Color(235, 151, 26));
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setFocusable(false);

        txtDataSO.setEditable(false);
        txtDataSO.setBackground(new java.awt.Color(255, 200, 0));
        txtDataSO.setColumns(20);
        txtDataSO.setFont(new java.awt.Font("Segoe UI Light", 0, 35)); // NOI18N
        txtDataSO.setForeground(new java.awt.Color(255, 255, 255));
        txtDataSO.setLineWrap(true);
        txtDataSO.setRows(5);
        txtDataSO.setAutoscrolls(false);
        txtDataSO.setFocusable(false);
        txtDataSO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDataSOMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(txtDataSO);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(5, 11, 290, 107);

        jBXMeusVencimentos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/x.png"))); // NOI18N
        jBXMeusVencimentos1.setBorderPainted(false);
        jBXMeusVencimentos1.setContentAreaFilled(false);
        jBXMeusVencimentos1.setFocusable(false);
        jBXMeusVencimentos1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBXMeusVencimentos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBXMeusVencimentos1ActionPerformed(evt);
            }
        });
        jPanel2.add(jBXMeusVencimentos1);
        jBXMeusVencimentos1.setBounds(260, 120, 40, 29);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(650, 90, 300, 150);

        jPanel3.setBackground(new java.awt.Color(63, 151, 151));
        jPanel3.setFocusable(false);
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(null);

        txtNomeDoEmail.setEditable(false);
        txtNomeDoEmail.setBackground(new java.awt.Color(63, 151, 151));
        txtNomeDoEmail.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        txtNomeDoEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtNomeDoEmail.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomeDoEmail.setText("Meus emails");
        txtNomeDoEmail.setFocusable(false);
        txtNomeDoEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNomeDoEmailMouseClicked(evt);
            }
        });
        jPanel3.add(txtNomeDoEmail);
        txtNomeDoEmail.setBounds(10, 5, 270, 38);

        jBEmailOutBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Emails.png"))); // NOI18N
        jBEmailOutBox.setBorderPainted(false);
        jBEmailOutBox.setContentAreaFilled(false);
        jBEmailOutBox.setFocusable(false);
        jBEmailOutBox.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBEmailOutBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEmailOutBoxActionPerformed(evt);
            }
        });
        jPanel3.add(jBEmailOutBox);
        jBEmailOutBox.setBounds(10, 110, 41, 33);

        jBXEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/x.png"))); // NOI18N
        jBXEmail.setBorderPainted(false);
        jBXEmail.setContentAreaFilled(false);
        jBXEmail.setFocusable(false);
        jBXEmail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBXEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBXEmailActionPerformed(evt);
            }
        });
        jPanel3.add(jBXEmail);
        jBXEmail.setBounds(270, 120, 30, 26);

        txtTotalDeEmails.setEditable(false);
        txtTotalDeEmails.setBackground(new java.awt.Color(63, 151, 151));
        txtTotalDeEmails.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTotalDeEmails.setForeground(new java.awt.Color(255, 255, 255));
        txtTotalDeEmails.setFocusable(false);
        jPanel3.add(txtTotalDeEmails);
        txtTotalDeEmails.setBounds(210, 120, 50, 21);

        jBEmailInBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Emails1.png"))); // NOI18N
        jBEmailInBox.setBorderPainted(false);
        jBEmailInBox.setContentAreaFilled(false);
        jBEmailInBox.setFocusPainted(false);
        jBEmailInBox.setFocusable(false);
        jBEmailInBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEmailInBoxActionPerformed(evt);
            }
        });
        jPanel3.add(jBEmailInBox);
        jBEmailInBox.setBounds(10, 110, 40, 30);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtMensagemDeEmailSeeds.setEditable(false);
        txtMensagemDeEmailSeeds.setBackground(new java.awt.Color(63, 151, 151));
        txtMensagemDeEmailSeeds.setColumns(20);
        txtMensagemDeEmailSeeds.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        txtMensagemDeEmailSeeds.setForeground(new java.awt.Color(255, 255, 255));
        txtMensagemDeEmailSeeds.setLineWrap(true);
        txtMensagemDeEmailSeeds.setRows(5);
        txtMensagemDeEmailSeeds.setFocusable(false);
        txtMensagemDeEmailSeeds.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMensagemDeEmailSeedsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtMensagemDeEmailSeeds);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(10, 50, 270, 60);

        jBNaoHabilitdo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/naoHabilitado.png"))); // NOI18N
        jBNaoHabilitdo4.setToolTipText("Serviço não habilitado!");
        jBNaoHabilitdo4.setBorderPainted(false);
        jBNaoHabilitdo4.setContentAreaFilled(false);
        jBNaoHabilitdo4.setFocusable(false);
        jPanel3.add(jBNaoHabilitdo4);
        jBNaoHabilitdo4.setBounds(50, 110, 41, 40);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(10, 90, 300, 150);

        jPanel4.setBackground(new java.awt.Color(115, 103, 255));
        jPanel4.setFocusable(false);
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });
        jPanel4.setLayout(null);

        txtTotalCaixa.setEditable(false);
        txtTotalCaixa.setBackground(new java.awt.Color(115, 103, 255));
        txtTotalCaixa.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        txtTotalCaixa.setForeground(new java.awt.Color(255, 255, 255));
        txtTotalCaixa.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalCaixa.setFocusable(false);
        txtTotalCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTotalCaixaMouseClicked(evt);
            }
        });
        jPanel4.add(txtTotalCaixa);
        txtTotalCaixa.setBounds(80, 10, 80, 38);

        txtEntradaCaixa.setBackground(new java.awt.Color(115, 103, 255));
        txtEntradaCaixa.setEditable(false);
        txtEntradaCaixa.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtEntradaCaixa.setForeground(new java.awt.Color(255, 255, 255));
        txtEntradaCaixa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEntradaCaixa.setFocusable(false);
        txtEntradaCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEntradaCaixaMouseClicked(evt);
            }
        });
        jPanel4.add(txtEntradaCaixa);
        txtEntradaCaixa.setBounds(80, 50, 80, 31);

        txtSaidaCaixa.setBackground(new java.awt.Color(115, 103, 255));
        txtSaidaCaixa.setEditable(false);
        txtSaidaCaixa.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtSaidaCaixa.setForeground(new java.awt.Color(255, 255, 255));
        txtSaidaCaixa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSaidaCaixa.setFocusable(false);
        txtSaidaCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSaidaCaixaMouseClicked(evt);
            }
        });
        jPanel4.add(txtSaidaCaixa);
        txtSaidaCaixa.setBounds(80, 80, 80, 31);

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Caixa");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel5);
        jLabel5.setBounds(10, 10, 70, 32);

        jBCaixaAberto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Caixas.png"))); // NOI18N
        jBCaixaAberto.setBorderPainted(false);
        jBCaixaAberto.setContentAreaFilled(false);
        jBCaixaAberto.setFocusable(false);
        jBCaixaAberto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCaixaAbertoActionPerformed(evt);
            }
        });
        jPanel4.add(jBCaixaAberto);
        jBCaixaAberto.setBounds(0, 115, 43, 31);

        jLEntradaCaixa.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLEntradaCaixa.setForeground(new java.awt.Color(255, 255, 255));
        jLEntradaCaixa.setText("Entrada");
        jLEntradaCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLEntradaCaixaMouseClicked(evt);
            }
        });
        jPanel4.add(jLEntradaCaixa);
        jLEntradaCaixa.setBounds(10, 50, 70, 25);

        jLSaidaCaixa.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLSaidaCaixa.setForeground(new java.awt.Color(255, 255, 255));
        jLSaidaCaixa.setText("Saída");
        jLSaidaCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLSaidaCaixaMouseClicked(evt);
            }
        });
        jPanel4.add(jLSaidaCaixa);
        jLSaidaCaixa.setBounds(10, 80, 70, 25);

        jBCaixaFechado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/FecharCaixas.png"))); // NOI18N
        jBCaixaFechado.setBorderPainted(false);
        jBCaixaFechado.setContentAreaFilled(false);
        jBCaixaFechado.setFocusable(false);
        jBCaixaFechado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCaixaFechadoActionPerformed(evt);
            }
        });
        jPanel4.add(jBCaixaFechado);
        jBCaixaFechado.setBounds(0, 115, 40, 30);

        jBNaoHabilitdo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/naoHabilitado.png"))); // NOI18N
        jBNaoHabilitdo.setToolTipText("Serviço não habilitado!");
        jBNaoHabilitdo.setBorderPainted(false);
        jBNaoHabilitdo.setContentAreaFilled(false);
        jBNaoHabilitdo.setFocusable(false);
        jPanel4.add(jBNaoHabilitdo);
        jBNaoHabilitdo.setBounds(130, 110, 41, 40);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(10, 260, 170, 150);

        jPanel5.setBackground(new java.awt.Color(102, 0, 102));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        txtDataSO7.setEditable(false);
        txtDataSO7.setBackground(new java.awt.Color(102, 0, 102));
        txtDataSO7.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        txtDataSO7.setForeground(new java.awt.Color(255, 255, 255));
        txtDataSO7.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDataSO7.setText("Vendas");
        txtDataSO7.setFocusable(false);
        txtDataSO7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDataSO7MouseClicked(evt);
            }
        });

        jLStore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Store.png"))); // NOI18N
        jLStore.setFocusable(false);
        jLStore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLStoreMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLStore, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtDataSO7, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtDataSO7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLStore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.add(jPanel5);
        jPanel5.setBounds(10, 430, 300, 150);

        jPanel6.setBackground(new java.awt.Color(218, 218, 101));
        jPanel6.setFocusable(false);
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        txtDataSO12.setEditable(false);
        txtDataSO12.setBackground(new java.awt.Color(218, 218, 101));
        txtDataSO12.setForeground(new java.awt.Color(255, 255, 255));
        txtDataSO12.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDataSO12.setText("Google Chrome");
        txtDataSO12.setFocusable(false);
        txtDataSO12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDataSO12MouseClicked(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/google-chrome.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataSO12, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDataSO12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel6);
        jPanel6.setBounds(190, 260, 120, 150);

        jPanel7.setBackground(new java.awt.Color(212, 105, 78));
        jPanel7.setFocusable(false);
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });
        jPanel7.setLayout(null);

        txtTurma.setEditable(false);
        txtTurma.setBackground(new java.awt.Color(212, 105, 78));
        txtTurma.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        txtTurma.setForeground(new java.awt.Color(255, 255, 255));
        txtTurma.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTurma.setText("Minhas Turmas");
        txtTurma.setFocusable(false);
        txtTurma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTurmaMouseClicked(evt);
            }
        });
        jPanel7.add(txtTurma);
        txtTurma.setBounds(10, 6, 250, 38);

        txtTotalDeTurmaHoje.setBackground(new java.awt.Color(212, 105, 78));
        txtTotalDeTurmaHoje.setEditable(false);
        txtTotalDeTurmaHoje.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtTotalDeTurmaHoje.setForeground(new java.awt.Color(255, 255, 255));
        txtTotalDeTurmaHoje.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalDeTurmaHoje.setFocusable(false);
        txtTotalDeTurmaHoje.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTotalDeTurmaHojeMouseClicked(evt);
            }
        });
        jPanel7.add(txtTotalDeTurmaHoje);
        txtTotalDeTurmaHoje.setBounds(10, 50, 280, 31);

        txtDataSO13.setBackground(new java.awt.Color(212, 105, 78));
        txtDataSO13.setEditable(false);
        txtDataSO13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtDataSO13.setForeground(new java.awt.Color(255, 255, 255));
        txtDataSO13.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDataSO13.setText("Base de dados");
        txtDataSO13.setFocusable(false);
        jPanel7.add(txtDataSO13);
        txtDataSO13.setBounds(36, 573, 314, 31);

        txtTurmaHorario.setBackground(new java.awt.Color(212, 105, 78));
        txtTurmaHorario.setEditable(false);
        txtTurmaHorario.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtTurmaHorario.setForeground(new java.awt.Color(255, 255, 255));
        txtTurmaHorario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTurmaHorario.setFocusable(false);
        txtTurmaHorario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTurmaHorarioMouseClicked(evt);
            }
        });
        jPanel7.add(txtTurmaHorario);
        txtTurmaHorario.setBounds(10, 80, 280, 31);

        txtTotalDeAlunos.setBackground(new java.awt.Color(212, 105, 78));
        txtTotalDeAlunos.setEditable(false);
        txtTotalDeAlunos.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtTotalDeAlunos.setForeground(new java.awt.Color(255, 255, 255));
        txtTotalDeAlunos.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalDeAlunos.setFocusable(false);
        txtTotalDeAlunos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTotalDeAlunosMouseClicked(evt);
            }
        });
        jPanel7.add(txtTotalDeAlunos);
        txtTotalDeAlunos.setBounds(10, 110, 280, 31);

        jBNaoHabilitdo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/naoHabilitado.png"))); // NOI18N
        jBNaoHabilitdo1.setToolTipText("Serviço não habilitado!");
        jBNaoHabilitdo1.setBorderPainted(false);
        jBNaoHabilitdo1.setContentAreaFilled(false);
        jBNaoHabilitdo1.setFocusable(false);
        jPanel7.add(jBNaoHabilitdo1);
        jBNaoHabilitdo1.setBounds(260, 0, 41, 40);

        jPanel1.add(jPanel7);
        jPanel7.setBounds(330, 90, 300, 150);

        jPanel8.setBackground(new java.awt.Color(51, 153, 255));
        jPanel8.setFocusable(false);
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });
        jPanel8.setLayout(null);

        txtDataSO15.setBackground(new java.awt.Color(51, 153, 255));
        txtDataSO15.setEditable(false);
        txtDataSO15.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        txtDataSO15.setForeground(new java.awt.Color(255, 255, 255));
        txtDataSO15.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDataSO15.setText("Meus Estoques");
        txtDataSO15.setFocusable(false);
        txtDataSO15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDataSO15MouseClicked(evt);
            }
        });
        jPanel8.add(txtDataSO15);
        txtDataSO15.setBounds(10, 12, 280, 38);

        txtEstoqueBaixo1.setEditable(false);
        txtEstoqueBaixo1.setBackground(new java.awt.Color(51, 153, 255));
        txtEstoqueBaixo1.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtEstoqueBaixo1.setForeground(new java.awt.Color(255, 255, 255));
        txtEstoqueBaixo1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEstoqueBaixo1.setFocusable(false);
        txtEstoqueBaixo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEstoqueBaixo1MouseClicked(evt);
            }
        });
        txtEstoqueBaixo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstoqueBaixo1ActionPerformed(evt);
            }
        });
        jPanel8.add(txtEstoqueBaixo1);
        txtEstoqueBaixo1.setBounds(40, 50, 250, 31);

        txtEstoqueBaixo2.setEditable(false);
        txtEstoqueBaixo2.setBackground(new java.awt.Color(51, 153, 255));
        txtEstoqueBaixo2.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtEstoqueBaixo2.setForeground(new java.awt.Color(255, 255, 255));
        txtEstoqueBaixo2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEstoqueBaixo2.setFocusable(false);
        txtEstoqueBaixo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEstoqueBaixo2MouseClicked(evt);
            }
        });
        jPanel8.add(txtEstoqueBaixo2);
        txtEstoqueBaixo2.setBounds(40, 80, 250, 31);

        txtEstoqueBaixo3.setEditable(false);
        txtEstoqueBaixo3.setBackground(new java.awt.Color(51, 153, 255));
        txtEstoqueBaixo3.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtEstoqueBaixo3.setForeground(new java.awt.Color(255, 255, 255));
        txtEstoqueBaixo3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEstoqueBaixo3.setFocusable(false);
        txtEstoqueBaixo3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEstoqueBaixo3MouseClicked(evt);
            }
        });
        jPanel8.add(txtEstoqueBaixo3);
        txtEstoqueBaixo3.setBounds(40, 110, 220, 31);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Estoquess.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton1);
        jButton1.setBounds(0, 110, 41, 40);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/x.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton2);
        jButton2.setBounds(260, 120, 40, 29);

        jBNaoHabilitdo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/naoHabilitado.png"))); // NOI18N
        jBNaoHabilitdo2.setToolTipText("Serviço não habilitado!");
        jBNaoHabilitdo2.setBorderPainted(false);
        jBNaoHabilitdo2.setContentAreaFilled(false);
        jBNaoHabilitdo2.setFocusable(false);
        jPanel8.add(jBNaoHabilitdo2);
        jBNaoHabilitdo2.setBounds(0, 50, 41, 40);

        jPanel1.add(jPanel8);
        jPanel8.setBounds(330, 430, 300, 150);

        jPanel9.setBackground(new java.awt.Color(167, 195, 44));
        jPanel9.setFocusable(false);
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(null);

        txtDataSO14.setEditable(false);
        txtDataSO14.setBackground(new java.awt.Color(167, 195, 44));
        txtDataSO14.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        txtDataSO14.setForeground(new java.awt.Color(255, 255, 255));
        txtDataSO14.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDataSO14.setText("Meus Vencimentos");
        txtDataSO14.setFocusable(false);
        txtDataSO14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDataSO14MouseClicked(evt);
            }
        });
        jPanel9.add(txtDataSO14);
        txtDataSO14.setBounds(10, 12, 280, 38);

        txtTotalDeVencimentos.setEditable(false);
        txtTotalDeVencimentos.setBackground(new java.awt.Color(167, 195, 44));
        txtTotalDeVencimentos.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtTotalDeVencimentos.setForeground(new java.awt.Color(255, 255, 255));
        txtTotalDeVencimentos.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalDeVencimentos.setFocusable(false);
        txtTotalDeVencimentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTotalDeVencimentosMouseClicked(evt);
            }
        });
        txtTotalDeVencimentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalDeVencimentosActionPerformed(evt);
            }
        });
        jPanel9.add(txtTotalDeVencimentos);
        txtTotalDeVencimentos.setBounds(40, 50, 250, 31);

        txtTotalAReceber.setEditable(false);
        txtTotalAReceber.setBackground(new java.awt.Color(167, 195, 44));
        txtTotalAReceber.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtTotalAReceber.setForeground(new java.awt.Color(255, 255, 255));
        txtTotalAReceber.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalAReceber.setFocusable(false);
        txtTotalAReceber.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTotalAReceberMouseClicked(evt);
            }
        });
        jPanel9.add(txtTotalAReceber);
        txtTotalAReceber.setBounds(40, 80, 250, 31);

        jBVencimentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Vencimentos.png"))); // NOI18N
        jBVencimentos.setBorderPainted(false);
        jBVencimentos.setContentAreaFilled(false);
        jBVencimentos.setFocusable(false);
        jBVencimentos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBVencimentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVencimentosActionPerformed(evt);
            }
        });
        jPanel9.add(jBVencimentos);
        jBVencimentos.setBounds(0, 110, 41, 40);

        jBNaoHabilitdo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/naoHabilitado.png"))); // NOI18N
        jBNaoHabilitdo3.setToolTipText("Serviço não habilitado!");
        jBNaoHabilitdo3.setBorderPainted(false);
        jBNaoHabilitdo3.setContentAreaFilled(false);
        jBNaoHabilitdo3.setFocusable(false);
        jPanel9.add(jBNaoHabilitdo3);
        jBNaoHabilitdo3.setBounds(0, 50, 41, 40);

        jBXMeusVencimentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/x.png"))); // NOI18N
        jBXMeusVencimentos.setBorderPainted(false);
        jBXMeusVencimentos.setContentAreaFilled(false);
        jBXMeusVencimentos.setFocusable(false);
        jBXMeusVencimentos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBXMeusVencimentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBXMeusVencimentosActionPerformed(evt);
            }
        });
        jPanel9.add(jBXMeusVencimentos);
        jBXMeusVencimentos.setBounds(260, 120, 40, 29);

        jPanel1.add(jPanel9);
        jPanel9.setBounds(330, 260, 300, 150);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/usuario.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel2);
        jLabel2.setBounds(880, 20, 50, 60);

        txtUsuarioNome.setFont(new java.awt.Font("Segoe UI Light", 0, 11)); // NOI18N
        txtUsuarioNome.setForeground(new java.awt.Color(255, 255, 255));
        txtUsuarioNome.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtUsuarioNome.setText("Nome");
        txtUsuarioNome.setFocusable(false);
        txtUsuarioNome.setOpaque(false);
        jPanel1.add(txtUsuarioNome);
        txtUsuarioNome.setBounds(600, 60, 270, 22);

        jPanel10.setBackground(new java.awt.Color(63, 151, 151));
        jPanel10.setFocusable(false);
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });
        jPanel10.setLayout(null);

        txtPorta.setBackground(new java.awt.Color(63, 151, 151));
        txtPorta.setEditable(false);
        txtPorta.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtPorta.setForeground(new java.awt.Color(255, 255, 255));
        txtPorta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPorta.setText("Porta");
        txtPorta.setFocusable(false);
        txtPorta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPortaMouseClicked(evt);
            }
        });
        jPanel10.add(txtPorta);
        txtPorta.setBounds(40, 90, 250, 31);

        txtEnderecoIPv4.setBackground(new java.awt.Color(63, 151, 151));
        txtEnderecoIPv4.setEditable(false);
        txtEnderecoIPv4.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        txtEnderecoIPv4.setForeground(new java.awt.Color(255, 255, 255));
        txtEnderecoIPv4.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEnderecoIPv4.setText("IPv4");
        txtEnderecoIPv4.setFocusable(false);
        txtEnderecoIPv4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEnderecoIPv4MouseClicked(evt);
            }
        });
        jPanel10.add(txtEnderecoIPv4);
        txtEnderecoIPv4.setBounds(40, 60, 250, 31);

        txtNomedaBasedeDados.setEditable(false);
        txtNomedaBasedeDados.setBackground(new java.awt.Color(63, 151, 151));
        txtNomedaBasedeDados.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        txtNomedaBasedeDados.setForeground(new java.awt.Color(255, 255, 255));
        txtNomedaBasedeDados.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomedaBasedeDados.setText("Conexões");
        txtNomedaBasedeDados.setFocusable(false);
        txtNomedaBasedeDados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNomedaBasedeDadosMouseClicked(evt);
            }
        });
        jPanel10.add(txtNomedaBasedeDados);
        txtNomedaBasedeDados.setBounds(10, 22, 280, 38);

        jBConectado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/network-transmit-receive.png"))); // NOI18N
        jBConectado.setBorderPainted(false);
        jBConectado.setContentAreaFilled(false);
        jBConectado.setFocusable(false);
        jBConectado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConectadoActionPerformed(evt);
            }
        });
        jPanel10.add(jBConectado);
        jBConectado.setBounds(0, 120, 39, 28);

        jBNaoConectado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/network-idle.png"))); // NOI18N
        jBNaoConectado.setBorderPainted(false);
        jBNaoConectado.setContentAreaFilled(false);
        jBNaoConectado.setFocusable(false);
        jBNaoConectado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNaoConectadoActionPerformed(evt);
            }
        });
        jPanel10.add(jBNaoConectado);
        jBNaoConectado.setBounds(0, 120, 39, 28);

        jBConectadoOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/network-transmit.png"))); // NOI18N
        jBConectadoOut.setToolTipText("Acesso ao banco de dados. Aguarde.");
        jBConectadoOut.setBorderPainted(false);
        jBConectadoOut.setContentAreaFilled(false);
        jBConectadoOut.setFocusable(false);
        jPanel10.add(jBConectadoOut);
        jBConectadoOut.setBounds(0, 120, 40, 30);

        jBConectadoIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/network-receive.png"))); // NOI18N
        jBConectadoIn.setToolTipText("Acesso ao banco de dados. Aguarde.");
        jBConectadoIn.setBorderPainted(false);
        jBConectadoIn.setContentAreaFilled(false);
        jBConectadoIn.setFocusable(false);
        jPanel10.add(jBConectadoIn);
        jBConectadoIn.setBounds(0, 120, 40, 30);

        jPanel1.add(jPanel10);
        jPanel10.setBounds(650, 430, 300, 150);

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel11.setFocusable(false);
        jPanel11.setLayout(null);

        jLSeedsDefaul.setBackground(new java.awt.Color(0, 0, 0));
        jLSeedsDefaul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLSeedsDefaul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/seedsLogoMenu.jpg"))); // NOI18N
        jPanel11.add(jLSeedsDefaul);
        jLSeedsDefaul.setBounds(0, 0, 300, 150);

        jLSeedsAdministrador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Administrador.png"))); // NOI18N
        jLSeedsAdministrador.setFocusable(false);
        jPanel11.add(jLSeedsAdministrador);
        jLSeedsAdministrador.setBounds(0, 0, 300, 150);

        jLSeedsServicosGeral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Servicos Gerais.png"))); // NOI18N
        jLSeedsServicosGeral.setFocusable(false);
        jPanel11.add(jLSeedsServicosGeral);
        jLSeedsServicosGeral.setBounds(0, 0, 300, 150);

        jLSeedsSecretaria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Secretaria.png"))); // NOI18N
        jLSeedsSecretaria.setFocusable(false);
        jPanel11.add(jLSeedsSecretaria);
        jLSeedsSecretaria.setBounds(0, 0, 300, 150);

        jLSeedsVendedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Vendedores.png"))); // NOI18N
        jLSeedsVendedores.setFocusable(false);
        jPanel11.add(jLSeedsVendedores);
        jLSeedsVendedores.setBounds(0, 0, 300, 150);

        jPanel1.add(jPanel11);
        jPanel11.setBounds(650, 260, 300, 150);

        txtUsuarioUserName.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        txtUsuarioUserName.setForeground(new java.awt.Color(255, 255, 255));
        txtUsuarioUserName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtUsuarioUserName.setText("Nome");
        txtUsuarioUserName.setFocusable(false);
        txtUsuarioUserName.setOpaque(false);
        jPanel1.add(txtUsuarioUserName);
        txtUsuarioUserName.setBounds(600, 20, 270, 30);

        jBSTART.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jBSTART.setForeground(new java.awt.Color(255, 255, 255));
        jBSTART.setText("Start");
        jBSTART.setBorderPainted(false);
        jBSTART.setContentAreaFilled(false);
        jBSTART.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBSTART.setFocusable(false);
        jBSTART.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSTART.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBSTART.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSTARTActionPerformed(evt);
            }
        });
        jPanel1.add(jBSTART);
        jBSTART.setBounds(20, 20, 110, 50);

        jBBloquearTela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Full_Cup.png"))); // NOI18N
        jBBloquearTela.setToolTipText("Bloquear tela.");
        jBBloquearTela.setBorderPainted(false);
        jBBloquearTela.setContentAreaFilled(false);
        jBBloquearTela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBloquearTela.setFocusable(false);
        jBBloquearTela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBloquearTelaActionPerformed(evt);
            }
        });
        jPanel1.add(jBBloquearTela);
        jBBloquearTela.setBounds(33, 590, 60, 45);

        jBBloquearTela1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/Empty_Cup.png"))); // NOI18N
        jBBloquearTela1.setToolTipText("Bloquear tela.");
        jBBloquearTela1.setBorderPainted(false);
        jBBloquearTela1.setContentAreaFilled(false);
        jBBloquearTela1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBloquearTela1.setFocusable(false);
        jBBloquearTela1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBloquearTela1ActionPerformed(evt);
            }
        });
        jPanel1.add(jBBloquearTela1);
        jBBloquearTela1.setBounds(33, 590, 60, 45);

        jBHabilitarMsnErros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/bonobo-browser.png"))); // NOI18N
        jBHabilitarMsnErros.setToolTipText("Habilitar verificações do sistema e demais serviços.");
        jBHabilitarMsnErros.setBorderPainted(false);
        jBHabilitarMsnErros.setContentAreaFilled(false);
        jBHabilitarMsnErros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBHabilitarMsnErros.setFocusable(false);
        jBHabilitarMsnErros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBHabilitarMsnErrosActionPerformed(evt);
            }
        });
        jPanel1.add(jBHabilitarMsnErros);
        jBHabilitarMsnErros.setBounds(100, 590, 60, 50);

        jBHabilitarMsnErros1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/bonobo-browserh.png"))); // NOI18N
        jBHabilitarMsnErros1.setToolTipText("Habilitado.");
        jBHabilitarMsnErros1.setBorderPainted(false);
        jBHabilitarMsnErros1.setContentAreaFilled(false);
        jBHabilitarMsnErros1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBHabilitarMsnErros1.setFocusable(false);
        jBHabilitarMsnErros1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBHabilitarMsnErros1ActionPerformed(evt);
            }
        });
        jPanel1.add(jBHabilitarMsnErros1);
        jBHabilitarMsnErros1.setBounds(100, 590, 60, 50);

        jBBuscarIcones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/buscar.png"))); // NOI18N
        jBBuscarIcones.setToolTipText("Mais usados.");
        jBBuscarIcones.setBorderPainted(false);
        jBBuscarIcones.setContentAreaFilled(false);
        jBBuscarIcones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscarIcones.setFocusable(false);
        jBBuscarIcones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarIconesActionPerformed(evt);
            }
        });
        jPanel1.add(jBBuscarIcones);
        jBBuscarIcones.setBounds(880, 600, 30, 30);

        jBBuscarPagamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/pagamentoBuscar.png"))); // NOI18N
        jBBuscarPagamento.setToolTipText("Pagamentos");
        jBBuscarPagamento.setBorderPainted(false);
        jBBuscarPagamento.setContentAreaFilled(false);
        jBBuscarPagamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscarPagamento.setFocusable(false);
        jBBuscarPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarPagamentoActionPerformed(evt);
            }
        });
        jPanel1.add(jBBuscarPagamento);
        jBBuscarPagamento.setBounds(730, 600, 30, 30);

        jBBuscarContrato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/contratoBuscar.png"))); // NOI18N
        jBBuscarContrato.setToolTipText("Contrato");
        jBBuscarContrato.setBorderPainted(false);
        jBBuscarContrato.setContentAreaFilled(false);
        jBBuscarContrato.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscarContrato.setFocusable(false);
        jBBuscarContrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarContratoActionPerformed(evt);
            }
        });
        jPanel1.add(jBBuscarContrato);
        jBBuscarContrato.setBounds(850, 600, 30, 30);

        jBBuscarMatricula.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/matriculaBuscar.png"))); // NOI18N
        jBBuscarMatricula.setToolTipText("Matrícula");
        jBBuscarMatricula.setBorderPainted(false);
        jBBuscarMatricula.setContentAreaFilled(false);
        jBBuscarMatricula.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscarMatricula.setFocusable(false);
        jBBuscarMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarMatriculaActionPerformed(evt);
            }
        });
        jPanel1.add(jBBuscarMatricula);
        jBBuscarMatricula.setBounds(820, 600, 30, 30);

        jBBuscarAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/alunoBuscar.png"))); // NOI18N
        jBBuscarAluno.setToolTipText("Aluno");
        jBBuscarAluno.setBorderPainted(false);
        jBBuscarAluno.setContentAreaFilled(false);
        jBBuscarAluno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscarAluno.setFocusable(false);
        jBBuscarAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarAlunoActionPerformed(evt);
            }
        });
        jPanel1.add(jBBuscarAluno);
        jBBuscarAluno.setBounds(790, 600, 30, 30);

        jBBuscarRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/imagens/relatorioBuscar.png"))); // NOI18N
        jBBuscarRelatorio.setToolTipText("Relatórios");
        jBBuscarRelatorio.setBorderPainted(false);
        jBBuscarRelatorio.setContentAreaFilled(false);
        jBBuscarRelatorio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscarRelatorio.setFocusable(false);
        jBBuscarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarRelatorioActionPerformed(evt);
            }
        });
        jPanel1.add(jBBuscarRelatorio);
        jBBuscarRelatorio.setBounds(760, 600, 30, 30);

        txtBuscar.setBackground(new java.awt.Color(51, 0, 51));
        txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(204, 204, 204));
        txtBuscar.setText("Buscar");
        txtBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        txtBuscar.setCaretColor(new java.awt.Color(204, 204, 204));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });
        jPanel1.add(txtBuscar);
        txtBuscar.setBounds(280, 600, 630, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1381, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLMenuLateralAbrirFecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLMenuLateralAbrirFecharMouseEntered
        jPMenuLateral.setVisible(true);
        jLMenuLateral.setVisible(true);
        jBConfigurar.setVisible(true);
        jBCadastro.setVisible(true);
        jBFinancas.setVisible(true);
        jBEstoques.setVisible(true);
        jBVendas.setVisible(true);
    }//GEN-LAST:event_jLMenuLateralAbrirFecharMouseEntered

    private void jLMenuLateralAbrirFecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLMenuLateralAbrirFecharMouseExited
        jPMenuLateral.setVisible(false);
        jLMenuLateral.setVisible(false);
        jBConfigurar.setVisible(false);
        jBCadastro.setVisible(false);
        jBFinancas.setVisible(false);
        jBEstoques.setVisible(false);
        jBVendas.setVisible(false);
    }//GEN-LAST:event_jLMenuLateralAbrirFecharMouseExited

    private void jLMenuLateralMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLMenuLateralMouseEntered
        jPMenuLateral.setVisible(true);
        jLMenuLateral.setVisible(true);
        jBConfigurar.setVisible(true);
        jBCadastro.setVisible(true);
        jBFinancas.setVisible(true);
        jBEstoques.setVisible(true);
        jBVendas.setVisible(true);
    }//GEN-LAST:event_jLMenuLateralMouseEntered

    private void jLMenuLateralMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLMenuLateralMouseExited
        jPMenuLateral.setVisible(false);
        jLMenuLateral.setVisible(false);
        jBConfigurar.setVisible(false);
        jBCadastro.setVisible(false);
        jBFinancas.setVisible(false);
        jBEstoques.setVisible(false);
        jBVendas.setVisible(false);
    }//GEN-LAST:event_jLMenuLateralMouseExited

    private void jBEstoquesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEstoquesActionPerformed
        if (tipoFuncionario.equals("Administrador")) {
            new FramePaiEstoques(this, rootPaneCheckingEnabled).setVisible(true);
        } else {
            new SolicitacaoSenhaEstoque(null, rootPaneCheckingEnabled).setVisible(true);
        }
    }//GEN-LAST:event_jBEstoquesActionPerformed

    private void jBConfigurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConfigurarActionPerformed
        new FramePaiSistemas(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jBConfigurarActionPerformed

    private void jBVendasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVendasMouseEntered
        jPMenuLateral.setVisible(true);
        jLMenuLateral.setVisible(true);
        jBConfigurar.setVisible(true);
        jBCadastro.setVisible(true);
        jBFinancas.setVisible(true);
        jBEstoques.setVisible(true);
        jBVendas.setVisible(true);
    }//GEN-LAST:event_jBVendasMouseEntered

    private void jBVendasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVendasMouseExited
        jPMenuLateral.setVisible(false);
        jLMenuLateral.setVisible(false);
        jBConfigurar.setVisible(false);
        jBCadastro.setVisible(false);
        jBFinancas.setVisible(false);
        jBEstoques.setVisible(false);
        jBVendas.setVisible(false);
    }//GEN-LAST:event_jBVendasMouseExited

    private void jBEstoquesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEstoquesMouseEntered
        jPMenuLateral.setVisible(true);
        jLMenuLateral.setVisible(true);
        jBConfigurar.setVisible(true);
        jBCadastro.setVisible(true);
        jBFinancas.setVisible(true);
        jBEstoques.setVisible(true);
        jBVendas.setVisible(true);
    }//GEN-LAST:event_jBEstoquesMouseEntered

    private void jBEstoquesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEstoquesMouseExited
        jPMenuLateral.setVisible(false);
        jLMenuLateral.setVisible(false);
        jBConfigurar.setVisible(false);
        jBCadastro.setVisible(false);
        jBFinancas.setVisible(false);
        jBEstoques.setVisible(false);
        jBVendas.setVisible(false);
    }//GEN-LAST:event_jBEstoquesMouseExited

    private void jBFinancasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFinancasMouseEntered
        jPMenuLateral.setVisible(true);
        jLMenuLateral.setVisible(true);
        jBConfigurar.setVisible(true);
        jBCadastro.setVisible(true);
        jBFinancas.setVisible(true);
        jBEstoques.setVisible(true);
        jBVendas.setVisible(true);
    }//GEN-LAST:event_jBFinancasMouseEntered

    private void jBFinancasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFinancasMouseExited
        jPMenuLateral.setVisible(false);
        jLMenuLateral.setVisible(false);
        jBConfigurar.setVisible(false);
        jBCadastro.setVisible(false);
        jBFinancas.setVisible(false);
        jBEstoques.setVisible(false);
        jBVendas.setVisible(false);
    }//GEN-LAST:event_jBFinancasMouseExited

    private void jBCadastroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCadastroMouseEntered
        jPMenuLateral.setVisible(true);
        jLMenuLateral.setVisible(true);
        jBConfigurar.setVisible(true);
        jBCadastro.setVisible(true);
        jBFinancas.setVisible(true);
        jBEstoques.setVisible(true);
        jBVendas.setVisible(true);
    }//GEN-LAST:event_jBCadastroMouseEntered

    private void jBCadastroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCadastroMouseExited
        jPMenuLateral.setVisible(false);
        jLMenuLateral.setVisible(false);
        jBConfigurar.setVisible(false);
        jBCadastro.setVisible(false);
        jBFinancas.setVisible(false);
        jBEstoques.setVisible(false);
        jBVendas.setVisible(false);
    }//GEN-LAST:event_jBCadastroMouseExited

    private void jBConfigurarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConfigurarMouseEntered
        jPMenuLateral.setVisible(true);
        jLMenuLateral.setVisible(true);
        jBConfigurar.setVisible(true);
        jBCadastro.setVisible(true);
        jBFinancas.setVisible(true);
        jBEstoques.setVisible(true);
        jBVendas.setVisible(true);
    }//GEN-LAST:event_jBConfigurarMouseEntered

    private void jBConfigurarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConfigurarMouseExited
        jPMenuLateral.setVisible(false);
        jLMenuLateral.setVisible(false);
        jBConfigurar.setVisible(false);
        jBCadastro.setVisible(false);
        jBFinancas.setVisible(false);
        jBEstoques.setVisible(false);
        jBVendas.setVisible(false);
    }//GEN-LAST:event_jBConfigurarMouseExited

    private void jBCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCadastroActionPerformed
        new FramePaiCadastros(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jBCadastroActionPerformed

    private void jBFinancasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFinancasActionPerformed
        new FramePaiFinanceiros(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jBFinancasActionPerformed

    private void jBVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVendasActionPerformed
        new FramePaiVendas(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jBVendasActionPerformed

    private void jBEmailOutBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEmailOutBoxActionPerformed
        if (!jBEmailInBox.isVisible() == false) {
            new SolicitacaoSenhaAdministradorEmail(null, rootPaneCheckingEnabled).setVisible(true);
            //BareBonesBrowserLaunch.openURL("http://www.gmail.com.br/");
        }
        if (tipoFuncionario.equals("Administrador")) {
            Carteiro carteiro = new Carteiro();
            try {
                txtTotalDeEmails.setText(carteiro.recebeQMSN() + "");
                txtMensagemDeEmailSeeds.setText(carteiro.recebe() + "");
                txtNomeDoEmail.setText("seedsescola@gmail.com");
                jBEmailOutBox.setVisible(false);
                jBEmailInBox.setVisible(true);
                jBNaoHabilitdo4.setVisible(false);
                carteiro = null;
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(false);
                jBNaoHabilitdo4.setVisible(true);
            } catch (MessagingException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(false);
                jBNaoHabilitdo4.setVisible(true);
            }
        }
    }//GEN-LAST:event_jBEmailOutBoxActionPerformed

    private void jBXEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBXEmailActionPerformed
        txtMensagemDeEmailSeeds.setText("");
        txtTotalDeEmails.setText("");
        txtNomeDoEmail.setText("Meus emails");
        jBEmailOutBox.setVisible(true);
        jBEmailInBox.setVisible(false);
        jBNaoHabilitdo4.setVisible(true);
    }//GEN-LAST:event_jBXEmailActionPerformed

    private void jLStoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLStoreMouseClicked
        if (VerificaFinal) {
            // Clicou em entrada em caixa
            if (aberto) {
                CaixaEntradaVenda Entrada;
                try {
                    try {
                        Entrada = new CaixaEntradaVenda(0);
                        Entrada.setVisible(true);
                    } catch (ParseException ex) {
                        Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (BancoException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "O caixa está fechado!");
            try {
                try {
                    // clicou em caixa
                    // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados

                    Main.Caixa.dispose();
                    Main.Caixa = new CaixaInterface();
                    Main.Caixa.AoIniciar();
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.Caixa.setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLStoreMouseClicked

    private void txtDataSO7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataSO7MouseClicked
        if (VerificaFinal) {
            // Clicou em entrada em caixa
            if (aberto) {
                CaixaEntradaVenda Entrada;
                try {
                    try {
                        Entrada = new CaixaEntradaVenda(0);
                        Entrada.setVisible(true);
                    } catch (ParseException ex) {
                        Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (BancoException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "O caixa está fechado!");
            try {
                try {
                    // clicou em caixa
                    // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados

                    Main.Caixa.dispose();
                    Main.Caixa = new CaixaInterface();
                    Main.Caixa.AoIniciar();
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.Caixa.setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtDataSO7MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        if (VerificaFinal) {
            // Clicou em entrada em caixa
            if (aberto) {
                CaixaEntradaVenda Entrada;
                try {
                    try {
                        Entrada = new CaixaEntradaVenda(0);
                        Entrada.setVisible(true);
                    } catch (ParseException ex) {
                        Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (BancoException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "O caixa está fechado!");
            try {
                try {
                    // clicou em caixa
                    // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados

                    Main.Caixa.dispose();
                    Main.Caixa = new CaixaInterface();
                    Main.Caixa.AoIniciar();
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.Caixa.setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jBVencimentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVencimentosActionPerformed
        txtTotalDeVencimentos.setVisible(true);
        txtTotalAReceber.setVisible(true);
        vencimentos();
        txtTotalAReceber.setText("Total a receber " + totalAReceber + "");
        txtTotalDeVencimentos.setText("Total de vencimentos " + apagar + "");
        try {
            ContasRDAO dao = new ContasRDAO();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(dataFormatada);
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(s);
            List<ContasR> lista = dao.pesquisacontasRe(nome, nomes);
            new SplahVencimentos(lista.size()).setVisible(true);
            lista = null;
            dao.desconectar();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jBVencimentosActionPerformed

    private void txtTotalDeVencimentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalDeVencimentosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalDeVencimentosActionPerformed

    private void jBXMeusVencimentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBXMeusVencimentosActionPerformed
        txtTotalAReceber.setVisible(false);
        txtTotalDeVencimentos.setVisible(false);
    }//GEN-LAST:event_jBXMeusVencimentosActionPerformed

    private void jBCaixaFechadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCaixaFechadoActionPerformed
        try {
            try {
                // clicou em caixa
                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados
                Main.Caixa.dispose();
                Main.Caixa = new CaixaInterface();
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Caixa.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBCaixaFechadoActionPerformed

    private void jBCaixaAbertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCaixaAbertoActionPerformed
        try {
            try {
                AoIniciar();//CAIXA
                // clicou em caixa
                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados
                Main.Caixa.dispose();
                Main.Caixa = new CaixaInterface();
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Caixa.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBCaixaAbertoActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        new Usuarios().setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void txtEstoqueBaixo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstoqueBaixo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstoqueBaixo1ActionPerformed

    private void jBSTARTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSTARTActionPerformed
        new FramePai(tipoFuncionario).setVisible(true);
    }//GEN-LAST:event_jBSTARTActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        if (!jBEmailInBox.isVisible() == false) {
            new SolicitacaoSenhaAdministradorEmail(null, rootPaneCheckingEnabled).setVisible(true);
            //BareBonesBrowserLaunch.openURL("http://www.gmail.com.br/");
        }
        if (tipoFuncionario.equals("Administrador")) {
            Carteiro carteiro = new Carteiro();
            try {
                txtTotalDeEmails.setText(carteiro.recebeQMSN() + "");
                txtMensagemDeEmailSeeds.setText(carteiro.recebe() + "");
                txtNomeDoEmail.setText("seedsescola@gmail.com");
                jBEmailOutBox.setVisible(false);
                jBEmailInBox.setVisible(true);
                jBNaoHabilitdo4.setVisible(false);
                carteiro = null;
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(false);
                jBNaoHabilitdo4.setVisible(true);
            } catch (MessagingException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(true);
            }
        }
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jBEmailInBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEmailInBoxActionPerformed
        if (!jBEmailInBox.isVisible() == false) {
            new SolicitacaoSenhaAdministradorEmail(null, rootPaneCheckingEnabled).setVisible(true);
            //BareBonesBrowserLaunch.openURL("http://www.gmail.com.br/");
        }
        if (tipoFuncionario.equals("Administrador")) {
            Carteiro carteiro = new Carteiro();
            try {
                txtTotalDeEmails.setText(carteiro.recebeQMSN() + "");
                txtMensagemDeEmailSeeds.setText(carteiro.recebe() + "");
                txtNomeDoEmail.setText("seedsescola@gmail.com");
                jBEmailOutBox.setVisible(false);
                jBEmailInBox.setVisible(true);
                jBNaoHabilitdo4.setVisible(false);
                carteiro = null;
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(false);
                jBNaoHabilitdo4.setVisible(true);
            } catch (MessagingException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(false);
                jBNaoHabilitdo4.setVisible(true);
            }
        }
    }//GEN-LAST:event_jBEmailInBoxActionPerformed

    private void txtNomeDoEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomeDoEmailMouseClicked
        if (!jBEmailInBox.isVisible() == false) {
            new SolicitacaoSenhaAdministradorEmail(null, rootPaneCheckingEnabled).setVisible(true);
            //BareBonesBrowserLaunch.openURL("http://www.gmail.com.br/");
        }
        if (tipoFuncionario.equals("Administrador")) {
            Carteiro carteiro = new Carteiro();
            try {
                txtTotalDeEmails.setText(carteiro.recebeQMSN() + "");
                txtMensagemDeEmailSeeds.setText(carteiro.recebe() + "");
                txtNomeDoEmail.setText("seedsescola@gmail.com");
                jBEmailOutBox.setVisible(false);
                jBEmailInBox.setVisible(true);
                jBNaoHabilitdo4.setVisible(false);
                carteiro = null;
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(false);
                jBNaoHabilitdo4.setVisible(true);
            } catch (MessagingException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(false);
                jBNaoHabilitdo4.setVisible(true);
            }
        }
    }//GEN-LAST:event_txtNomeDoEmailMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        BareBonesBrowserLaunch.openURL("http://");
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        BareBonesBrowserLaunch.openURL("http://");
    }//GEN-LAST:event_jPanel6MouseClicked

    private void txtDataSO12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataSO12MouseClicked
        BareBonesBrowserLaunch.openURL("http://");
    }//GEN-LAST:event_txtDataSO12MouseClicked

    private void txtMensagemDeEmailSeedsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMensagemDeEmailSeedsMouseClicked
        if (!jBEmailInBox.isVisible() == false) {
            new SolicitacaoSenhaAdministradorEmail(null, rootPaneCheckingEnabled).setVisible(true);
            //BareBonesBrowserLaunch.openURL("http://www.gmail.com.br/");
        }
        if (tipoFuncionario.equals("Administrador")) {
            Carteiro carteiro = new Carteiro();
            try {
                txtTotalDeEmails.setText(carteiro.recebeQMSN() + "");
                txtMensagemDeEmailSeeds.setText(carteiro.recebe() + "");
                txtNomeDoEmail.setText("seedsescola@gmail.com");
                jBEmailOutBox.setVisible(false);
                jBEmailInBox.setVisible(true);
                jBNaoHabilitdo4.setVisible(false);
                carteiro = null;
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(false);
                jBNaoHabilitdo4.setVisible(true);
            } catch (MessagingException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                jBEmailOutBox.setVisible(true);
                jBEmailInBox.setVisible(false);
                jBNaoHabilitdo4.setVisible(true);
            }
        }
    }//GEN-LAST:event_txtMensagemDeEmailSeedsMouseClicked

    private void jBConectadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConectadoActionPerformed
        new ConfiguracoesServidor(null, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jBConectadoActionPerformed

    private void jBNaoConectadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNaoConectadoActionPerformed
        new ConfiguracoesServidor(null, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jBNaoConectadoActionPerformed

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        try {
            try {
                // clicou em caixa
                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados
                Main.Caixa.dispose();
                Main.Caixa = new CaixaInterface();
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Caixa.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jLEntradaCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLEntradaCaixaMouseClicked
        try {
            try {
                // clicou em caixa
                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados
                Main.Caixa.dispose();
                Main.Caixa = new CaixaInterface();
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Caixa.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLEntradaCaixaMouseClicked

    private void jLSaidaCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLSaidaCaixaMouseClicked
        try {
            try {
                // clicou em caixa
                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados
                Main.Caixa.dispose();
                Main.Caixa = new CaixaInterface();
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Caixa.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLSaidaCaixaMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        try {
            try {
                // clicou em caixa
                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados
                Main.Caixa.dispose();
                Main.Caixa = new CaixaInterface();
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Caixa.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void txtTotalCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTotalCaixaMouseClicked
        try {
            try {
                // clicou em caixa
                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados
                Main.Caixa.dispose();
                Main.Caixa = new CaixaInterface();
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Caixa.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtTotalCaixaMouseClicked

    private void txtEntradaCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEntradaCaixaMouseClicked
        try {
            try {
                // clicou em caixa
                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados
                Main.Caixa.dispose();
                Main.Caixa = new CaixaInterface();
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Caixa.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtEntradaCaixaMouseClicked

    private void txtSaidaCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSaidaCaixaMouseClicked
        try {
            try {
                // clicou em caixa
                // Toda operação feita de abertura, fechamento e reabertura será armazenada no Banco de dados
                Main.Caixa.dispose();
                Main.Caixa = new CaixaInterface();
                Main.Caixa.AoIniciar();
            } catch (BancoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            Main.Caixa.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtSaidaCaixaMouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        try {
            ContasRDAO dao = new ContasRDAO();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(dataFormatada);
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(s);
            List<ContasR> lista = dao.pesquisacontasRe(nome, nomes);
            new SplahVencimentos(lista.size()).setVisible(true);
            lista = null;
            dao.desconectar();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jPanel9MouseClicked

    private void txtDataSO14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataSO14MouseClicked
        try {
            ContasRDAO dao = new ContasRDAO();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(dataFormatada);
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(s);
            List<ContasR> lista = dao.pesquisacontasRe(nome, nomes);
            new SplahVencimentos(lista.size()).setVisible(true);
            lista = null;
            dao.desconectar();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtDataSO14MouseClicked

    private void txtTotalDeVencimentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTotalDeVencimentosMouseClicked
        try {
            ContasRDAO dao = new ContasRDAO();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(dataFormatada);
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(s);
            List<ContasR> lista = dao.pesquisacontasRe(nome, nomes);
            new SplahVencimentos(lista.size()).setVisible(true);
            lista = null;
            dao.desconectar();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtTotalDeVencimentosMouseClicked

    private void txtTotalAReceberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTotalAReceberMouseClicked
        try {
            ContasRDAO dao = new ContasRDAO();
            apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nome.delete(0, apagar);
            nome.append(dataFormatada);
            apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
            nomes.delete(0, apagar);
            nomes.append(s);
            List<ContasR> lista = dao.pesquisacontasRe(nome, nomes);
            new SplahVencimentos(lista.size()).setVisible(true);
            lista = null;
            dao.desconectar();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtTotalAReceberMouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        new ConfiguracoesServidor(null, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jPanel10MouseClicked

    private void txtNomedaBasedeDadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomedaBasedeDadosMouseClicked
        new ConfiguracoesServidor(null, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_txtNomedaBasedeDadosMouseClicked

    private void txtEnderecoIPv4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEnderecoIPv4MouseClicked
        new ConfiguracoesServidor(null, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_txtEnderecoIPv4MouseClicked

    private void txtPortaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPortaMouseClicked
        new ConfiguracoesServidor(null, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_txtPortaMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        if (evt.getClickCount() == 1) {
            jBBloquearTela.setVisible(false);
            jBBloquearTela1.setVisible(true);
            if (metro == 1) {
                jBHabilitarMsnErros.setVisible(false);
                jBHabilitarMsnErros1.setVisible(true);
            } else {
                jBHabilitarMsnErros1.setVisible(false);
                jBHabilitarMsnErros.setVisible(true);
            }
        }
        if (evt.getClickCount() == 2) {
            jBBloquearTela.setVisible(true);
            jBBloquearTela1.setVisible(false);
            jBHabilitarMsnErros.setVisible(false);
            jBHabilitarMsnErros1.setVisible(false);
            NowString();
            try {
                UsuarioDAO dao = new UsuarioDAO();
                Usuario Verificando;
                Verificando = dao.carregarUsuario(txtUsuarioUserName.getText());
                AcessoDAO daos = new AcessoDAO();
                Acesso okay = new Acesso();
                Verificando = dao.carregarUsuario(txtUsuarioUserName.getText());
                NowString();
                okay.setUsuario(Verificando.getCodigo());
                okay.setData(s);
                okay.setHora(f);
                okay.setDescricao("Bloqueio do sistema efetuado [Interface - Menu]");
                daos.adicionarAcesso(okay);
                okay = null;
                Verificando = null;
                daos.desconectar();
                dao.desconectar();
            } catch (Exception e) {
            }
            new FrameBloqueioTela(this, rootPaneCheckingEnabled).setVisible(true);
            jBBloquearTela.setVisible(false);
            jBBloquearTela1.setVisible(false);
            jBHabilitarMsnErros.setVisible(false);
            jBHabilitarMsnErros1.setVisible(false);
        }
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jBBloquearTelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBloquearTelaActionPerformed
    }//GEN-LAST:event_jBBloquearTelaActionPerformed

    private void jBBloquearTela1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBloquearTela1ActionPerformed
        jBBloquearTela.setVisible(true);
        jBBloquearTela1.setVisible(false);
        jBHabilitarMsnErros.setVisible(false);
        jBHabilitarMsnErros1.setVisible(false);
        NowString();
        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario Verificando;
            Verificando = dao.carregarUsuario(txtUsuarioUserName.getText());
            AcessoDAO daos = new AcessoDAO();
            Acesso okay = new Acesso();
            Verificando = dao.carregarUsuario(txtUsuarioUserName.getText());
            NowString();
            okay.setUsuario(Verificando.getCodigo());
            okay.setData(s);
            okay.setHora(f);
            okay.setDescricao("Bloqueio do sistema efetuado [Interface - Menu]");
            daos.adicionarAcesso(okay);
            okay = null;
            Verificando = null;
            daos.desconectar();
            dao.desconectar();
        } catch (Exception e) {
        }
        new FrameBloqueioTela(this, rootPaneCheckingEnabled).setVisible(true);
        jBBloquearTela.setVisible(false);
        jBBloquearTela1.setVisible(false);
        jBHabilitarMsnErros.setVisible(false);
        jBHabilitarMsnErros1.setVisible(false);
    }//GEN-LAST:event_jBBloquearTela1ActionPerformed

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        Turma Novo;
        try {
            Novo = new Turma();
            Novo.setVisible(true);
        } catch (BancoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jPanel7MouseClicked

    private void txtTurmaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTurmaMouseClicked
        Turma Novo;
        try {
            Novo = new Turma();
            Novo.setVisible(true);
        } catch (BancoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtTurmaMouseClicked

    private void txtTotalDeTurmaHojeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTotalDeTurmaHojeMouseClicked
        Turma Novo;
        try {
            Novo = new Turma();
            Novo.setVisible(true);
        } catch (BancoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtTotalDeTurmaHojeMouseClicked

    private void txtTurmaHorarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTurmaHorarioMouseClicked
        Turma Novo;
        try {
            Novo = new Turma();
            Novo.setVisible(true);
        } catch (BancoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtTurmaHorarioMouseClicked

    private void txtTotalDeAlunosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTotalDeAlunosMouseClicked
        Turma Novo;
        try {
            Novo = new Turma();
            Novo.setVisible(true);
        } catch (BancoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtTotalDeAlunosMouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        final EstoqueProdutos pesq = new EstoqueProdutos("Estoque dos Produtos");
        pesq.setVisible(true);

        ActionListener acaoOk = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pesq.dispose();
            }
        };
        pesq.setAcao(acaoOk);
    }//GEN-LAST:event_jPanel8MouseClicked

    private void txtDataSO15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataSO15MouseClicked
        final EstoqueProdutos pesq = new EstoqueProdutos("Estoque dos Produtos");
        pesq.setVisible(true);

        ActionListener acaoOk = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pesq.dispose();
            }
        };
        pesq.setAcao(acaoOk);
    }//GEN-LAST:event_txtDataSO15MouseClicked

    private void txtEstoqueBaixo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEstoqueBaixo1MouseClicked
        final EstoqueProdutos pesq = new EstoqueProdutos("Estoque dos Produtos");
        pesq.setVisible(true);

        ActionListener acaoOk = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pesq.dispose();
            }
        };
        pesq.setAcao(acaoOk);
    }//GEN-LAST:event_txtEstoqueBaixo1MouseClicked

    private void txtEstoqueBaixo2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEstoqueBaixo2MouseClicked
        final EstoqueProdutos pesq = new EstoqueProdutos("Estoque dos Produtos");
        pesq.setVisible(true);

        ActionListener acaoOk = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pesq.dispose();
            }
        };
        pesq.setAcao(acaoOk);
    }//GEN-LAST:event_txtEstoqueBaixo2MouseClicked

    private void txtEstoqueBaixo3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEstoqueBaixo3MouseClicked
        final EstoqueProdutos pesq = new EstoqueProdutos("Estoque dos Produtos");
        pesq.setVisible(true);

        ActionListener acaoOk = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pesq.dispose();
            }
        };
        pesq.setAcao(acaoOk);
    }//GEN-LAST:event_txtEstoqueBaixo3MouseClicked

    private void jBHabilitarMsnErrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBHabilitarMsnErrosActionPerformed
        metro();//Inicia o Metro
        metro = 1;//Permite que inicie apenas uma vez
        erroTurmas = 0;
        erroVencimentos = 0;
        erroMatriculas = 0;
        jBHabilitarMsnErros1.setToolTipText("Habilitado!");
        jBHabilitarMsnErros.setVisible(false);
        jBHabilitarMsnErros1.setVisible(true);
        jBNaoHabilitdo.setVisible(false);
        jBNaoHabilitdo1.setVisible(false);
        jBNaoHabilitdo2.setVisible(false);
        jBNaoHabilitdo3.setVisible(false);
        JOptionPane.showMessageDialog(rootPane, "Verificações do sistema e demais serviços.", "Habilitado!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jBHabilitarMsnErrosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txtEstoqueBaixo1.setVisible(true);
        txtEstoqueBaixo2.setVisible(true);
        txtEstoqueBaixo3.setVisible(true);
        estoqueEmFalta();//ESTOQUE
        final EstoqueProdutos pesq = new EstoqueProdutos("Estoque dos Produtos");
        pesq.setVisible(true);

        ActionListener acaoOk = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pesq.dispose();
            }
        };
        pesq.setAcao(acaoOk);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        txtEstoqueBaixo1.setVisible(false);
        txtEstoqueBaixo2.setVisible(false);
        txtEstoqueBaixo3.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jBHabilitarMsnErros1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBHabilitarMsnErros1ActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Verificações do sistema e demais serviços.\n"
                + "Status: Habilitado",
                "Seeds", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jBHabilitarMsnErros1ActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            if (txtBuscar.getText().toLowerCase().contains("asdf")
                    || txtBuscar.getText().toLowerCase().contains("asdf")) {
                //JOptionPane.showMessageDialog(rootPane, "teste");
                txtBuscar.setText("");
            }
            if (txtBuscar.getText().toLowerCase().contains("google")) {
                BareBonesBrowserLaunch.openURL("http://www.google.com.br");
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("configura")
                    || txtBuscar.getText().toLowerCase().contains("configurar")
                    || txtBuscar.getText().toLowerCase().contains("sistema")) {
                new FramePaiSistemas(this, rootPaneCheckingEnabled).setVisible(true);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("relatório")
                    || txtBuscar.getText().toLowerCase().contains("relatorio")) {
                new Relatorios(this, rootPaneCheckingEnabled).setVisible(true);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("manual")
                    || txtBuscar.getText().toLowerCase().contains("manual da seeds")) {
                File doc = new File("seedsManual.doc");
                try {
                    Desktop.getDesktop().open(doc);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao executar o Manual da Seeds: " + ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("teamviewer")
                    || txtBuscar.getText().toLowerCase().contains("manual teamviewer")
                    || txtBuscar.getText().toLowerCase().contains("teamview")) {
                File doc = new File("TeamViewer7-Manual-RemoteControl-pt.pdf");
                try {
                    Desktop.getDesktop().open(doc);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao abrir manual do TeamViewer7: " + ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("mysql")
                    || txtBuscar.getText().toLowerCase().contains("manual banco de dados")) {
                BareBonesBrowserLaunch.openURL("http://dev.mysql.com/doc/refman/5.1/en/");
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("backup")
                    || txtBuscar.getText().toLowerCase().contains("copia de segurança")
                    || txtBuscar.getText().toLowerCase().contains("cópia de segurança")
                    || txtBuscar.getText().toLowerCase().contains("rotina de segurança")
                    || txtBuscar.getText().toLowerCase().contains("segurança")) {
                new RotinasDeSeguranca().setVisible(true);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("conexao")
                    || txtBuscar.getText().toLowerCase().contains("conexão")
                    || txtBuscar.getText().toLowerCase().contains("configurar conexão")
                    || txtBuscar.getText().toLowerCase().contains("base de dado")
                    || txtBuscar.getText().toLowerCase().contains("configurar conexao")
                    || txtBuscar.getText().toLowerCase().contains("servidor")) {
                new ConfiguracoesServidor(null, rootPaneCheckingEnabled).setVisible(true);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("mandar email")
                    || txtBuscar.getText().toLowerCase().contains("mailing")) {
                Email Novo;
                try {
                    Novo = new Email();
                    Novo.setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("sobre")
                    || txtBuscar.getText().toLowerCase().contains("guilherme")
                    || txtBuscar.getText().toLowerCase().contains("desenvolvedor")) {
                new Sobre(null, rootPaneCheckingEnabled).setVisible(rootPaneCheckingEnabled);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("impressora")
                    || txtBuscar.getText().toLowerCase().contains("epson")
                    || txtBuscar.getText().toLowerCase().contains("lx 300")) {
                new ImpressoraLXEpson().setVisible(true);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("calculadora")
                    || txtBuscar.getText().toLowerCase().contains("calcular")
                    || txtBuscar.getText().toLowerCase().contains("calc")) {
                {
                    try {
                        Runtime.getRuntime().exec("calc"); //assim  
                        //Runtime.getRuntime().exec("C:\\Windows\\System32\\calc.exe"); //e assim  
                    } catch (Exception e) {
                        System.err.println("Erro ao abrir calculadora!");
                    }
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("usuário")
                    || txtBuscar.getText().toLowerCase().contains("usuario")
                    || txtBuscar.getText().toLowerCase().contains("senha")
                    || txtBuscar.getText().toLowerCase().contains("mudar senha")
                    || txtBuscar.getText().toLowerCase().contains("trocar senha")
                    || txtBuscar.getText().toLowerCase().contains("alterar senha")) {
                new Usuarios().setVisible(true);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("matricula")
                    || txtBuscar.getText().toLowerCase().contains("matrícula")) {
                Matriculas Novo;
                try {
                    Novo = new Matriculas();
                    Novo.setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("local")
                    || txtBuscar.getText().toLowerCase().contains("predio")
                    || txtBuscar.getText().toLowerCase().contains("prédio")
                    || txtBuscar.getText().toLowerCase().contains("escola")) {
                Localidades Novo;
                try {
                    Novo = new Localidades();
                    Novo.setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("professor")
                    || txtBuscar.getText().toLowerCase().contains("mestre")
                    || txtBuscar.getText().toLowerCase().contains("docente")) {
                Professores Novo;
                try {
                    Novo = new Professores();
                    Novo.setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("turma")
                    || txtBuscar.getText().toLowerCase().contains("classe")
                    || txtBuscar.getText().toLowerCase().contains("aula")) {
                Turma Novo;
                try {
                    Novo = new Turma();
                    Novo.setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("recibo")
                    || txtBuscar.getText().toLowerCase().contains("curso de férias")
                    || txtBuscar.getText().toLowerCase().contains("curso de ferias")
                    || txtBuscar.getText().toLowerCase().contains("comprovante")) {
                final ReciboCursoFerias pesq = new ReciboCursoFerias("Recibo de Curso - sem entrada no Caixa Diário");
                pesq.setVisible(true);
                ActionListener acaoOk = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        pesq.dispose();
                    }
                };
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("conta")
                    || txtBuscar.getText().toLowerCase().contains("conta receber")
                    || txtBuscar.getText().toLowerCase().contains("contas a receber")
                    || txtBuscar.getText().toLowerCase().contains("conta a receber")
                    || txtBuscar.getText().toLowerCase().contains("despesa")
                    || txtBuscar.getText().toLowerCase().contains("gasto")) {
                ContasRecebar conta;
                try {
                    conta = new ContasRecebar();
                    conta.setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("contrato")) {
                Contratos cont;
                try {
                    cont = new Contratos();
                    cont.setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("pagamento")) {
                if (VerificaFinal || !txtTotalCaixa.getText().equals("")) {
                    if (aberto || !txtTotalCaixa.getText().equals("")) {
                        try {
                            new PagamentosDetalhes().setVisible(true);
                        } catch (BancoException ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "O caixa está fechado!");
                    try {
                        try {
                            Main.Caixa.dispose();
                            Main.Caixa = new CaixaInterface();
                            Main.Caixa.AoIniciar();
                        } catch (BancoException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Main.Caixa.setVisible(true);
                    } catch (ParseException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("vencimento")) {
                try {
                    ContasRDAO dao = new ContasRDAO();
                    apagar = nome.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nome.delete(0, apagar);
                    nome.append(dataFormatada);
                    apagar = nomes.length();//Devolve o número de caracteres do objeto Stringbuffer apagar
                    nomes.delete(0, apagar);
                    nomes.append(s);
                    List<ContasR> lista = dao.pesquisacontasRe(nome, nomes);
                    new SplahVencimentos(lista.size()).setVisible(true);
                    lista = null;
                    dao.desconectar();
                } catch (Exception e) {
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("preço")
                    || txtBuscar.getText().toLowerCase().contains("valor")
                    || txtBuscar.getText().toLowerCase().contains("custo")) {
                final PrecosProdutos pesq = new PrecosProdutos("Preços dos Produtos");
                pesq.setVisible(true);
                ActionListener acaoOk = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        pesq.dispose();
                    }
                };
                pesq.setAcao(acaoOk);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("estoque do material")
                    || txtBuscar.getText().toLowerCase().contains("estoque dos materiais")
                    || txtBuscar.getText().toLowerCase().contains("estoque dos produto")
                    || txtBuscar.getText().toLowerCase().contains("estoque do produto")
                    || txtBuscar.getText().toLowerCase().contains("estoque material")
                    || txtBuscar.getText().toLowerCase().contains("estoque produto")
                    || txtBuscar.getText().toLowerCase().contains("quanto tenho em estoque")
                    || txtBuscar.getText().toLowerCase().contains("quanto tem em estoque")
                    || txtBuscar.getText().toLowerCase().contains("quanto tem no estoque")
                    || txtBuscar.getText().toLowerCase().contains("quantidade de material")
                    || txtBuscar.getText().toLowerCase().contains("quantidade de materiais")
                    || txtBuscar.getText().toLowerCase().contains("quantidade de produto")) {
                final EstoqueProdutos pesq = new EstoqueProdutos("Estoque dos Produtos");
                pesq.setVisible(true);
                ActionListener acaoOk = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        pesq.dispose();
                    }
                };
                pesq.setAcao(acaoOk);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("material")
                    && !txtBuscar.getText().toLowerCase().contains("estoque")
                    || txtBuscar.getText().toLowerCase().contains("produto")
                    && !txtBuscar.getText().toLowerCase().contains("estoque")) {
                if (tipoFuncionario.equals("Administrador")) {
                    new FramePaiEstoques(this, rootPaneCheckingEnabled).setVisible(true);
                } else {
                    new SolicitacaoSenhaEstoque(null, rootPaneCheckingEnabled).setVisible(true);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("fornecedor")) {
                if (tipoFuncionario.equals("Administrador")) {
                    Fornecedores Novo;
                    try {
                        Novo = new Fornecedores();
                        Novo.setVisible(true);
                    } catch (BancoException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    new SolicitacaoSenhaEstoque(null, rootPaneCheckingEnabled).setVisible(true);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("venda")) {
                if (VerificaFinal || !txtTotalCaixa.getText().equals("")) {
                    if (aberto || !txtTotalCaixa.getText().equals("")) {
                        CaixaEntradaVenda Entrada;
                        try {
                            try {
                                Entrada = new CaixaEntradaVenda(0);
                                Entrada.setVisible(true);
                            } catch (ParseException ex) {
                                Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (BancoException ex) {
                            Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(CaixaInterface.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "O caixa está fechado!");
                    try {
                        try {
                            Main.Caixa.dispose();
                            Main.Caixa = new CaixaInterface();
                            Main.Caixa.AoIniciar();
                        } catch (BancoException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Main.Caixa.setVisible(true);
                    } catch (ParseException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("caixa")) {
                try {
                    try {
                        Main.Caixa.dispose();
                        Main.Caixa = new CaixaInterface();
                        Main.Caixa.AoIniciar();
                    } catch (BancoException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Main.Caixa.setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("email")
                    || txtBuscar.getText().toLowerCase().contains("e-mail")
                    || txtBuscar.getText().toLowerCase().contains("conta e-mail")
                    || txtBuscar.getText().toLowerCase().contains("conta email")) {
                if (!jBEmailInBox.isVisible() == false) {
                    new SolicitacaoSenhaAdministradorEmail(null, rootPaneCheckingEnabled).setVisible(true);
                }
                if (tipoFuncionario.equals("Administrador")) {
                    Carteiro carteiro = new Carteiro();
                    try {
                        txtTotalDeEmails.setText(carteiro.recebeQMSN() + "");
                        txtMensagemDeEmailSeeds.setText(carteiro.recebe() + "");
                        jBEmailOutBox.setVisible(false);
                        jBEmailInBox.setVisible(true);
                        carteiro = null;
                    } catch (NoSuchProviderException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        jBEmailOutBox.setVisible(true);
                        jBEmailInBox.setVisible(false);
                    } catch (MessagingException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        jBEmailOutBox.setVisible(true);
                        jBEmailInBox.setVisible(false);
                    }
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("base de dados em uso")
                    || txtBuscar.getText().toLowerCase().contains("base de dados sendo usada")
                    || txtBuscar.getText().toLowerCase().contains("base de dado sendo usada")
                    || txtBuscar.getText().toLowerCase().contains("base de dado em uso")) {
                String linha;
                try {
                    FileReader arq = new FileReader("../seeds-java/basededados.txt");
                    BufferedReader lerArq = new BufferedReader(arq);
                    linha = lerArq.readLine(); // lê a primeira linha
                    while (linha != null) {
                        JOptionPane.showMessageDialog(rootPane,
                                "Base de dados Mysql em uso: \n"
                                + " " + linha + " ",
                                "", JOptionPane.INFORMATION_MESSAGE);
                        linha = lerArq.readLine(); // lê da segunda até a última linha
                    }
                    arq.close();
                } catch (IOException e) {
                    System.err.printf("Erro na abertura do arquivo.\n",
                            e.getMessage());
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("aluno")
                    && !txtBuscar.getText().toLowerCase().contains("contrato")
                    || txtBuscar.getText().toLowerCase().contains("cliente")
                    && !txtBuscar.getText().toLowerCase().contains("contrato")) {
                int CodigoLocalidade;
                try {
                    LocalidadeDAO dao = new LocalidadeDAO();
                    CodigoLocalidade = dao.gerarCodigoLocalidade() - 1;
                    if (CodigoLocalidade == 0) {
                        int selection = JOptionPane.showConfirmDialog(this,
                                "Não há Local cadastrado!\n"
                                + "Aconselhável cadastrar um local antes de cadastrar aluno.\n"
                                + "Deseja abrir o cadastro de Local?",
                                "Seeds", JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                        if (selection == JOptionPane.OK_OPTION) {
                            Localidades Novo;
                            try {
                                Novo = new Localidades();
                                Novo.setVisible(true);
                            } catch (BancoException ex) {
                                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            Clientes Novo;
                            try {
                                Novo = new Clientes();
                                Novo.setVisible(true);
                            } catch (ParseException ex) {
                                Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else {
                        Clientes Novo;
                        try {
                            Novo = new Clientes();
                            Novo.setVisible(true);
                        } catch (ParseException ex) {
                            Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    dao.desconectar();
                } catch (BancoException f) {
                    f.printStackTrace();
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("financeiro")) {
                new FramePaiFinanceiros(this, rootPaneCheckingEnabled).setVisible(true);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("estoque")) {
                if (tipoFuncionario.equals("Administrador")) {
                    new FramePaiEstoques(this, rootPaneCheckingEnabled).setVisible(true);
                } else {
                    new SolicitacaoSenhaEstoque(null, rootPaneCheckingEnabled).setVisible(true);
                }
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("cadastro")) {
                new FramePaiCadastros(this, rootPaneCheckingEnabled).setVisible(true);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("vendas")
                    || txtBuscar.getText().toLowerCase().contains("venda")) {
                new FramePaiVendas(this, rootPaneCheckingEnabled).setVisible(true);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("start")
                    || txtBuscar.getText().toLowerCase().contains("menu")) {
                new FramePai(tipoFuncionario).setVisible(true);
                txtBuscar.setText("");
            }
            if (txtBuscar.getText().toLowerCase().contains("habilitar todos os serviço")
                    || txtBuscar.getText().toLowerCase().contains("habilitar os serviço")
                    || txtBuscar.getText().toLowerCase().contains("habilitar serviço")
                    || txtBuscar.getText().toLowerCase().contains("iniciar serviço")
                    || txtBuscar.getText().toLowerCase().contains("iniciar os serviço")
                    || txtBuscar.getText().toLowerCase().contains("iniciar todos serviço")
                    || txtBuscar.getText().toLowerCase().contains("iniciar todos os serviço")
                    || txtBuscar.getText().toLowerCase().contains("habilitar")) {
                metro();//Inicia o Metro
                metro = 1;//Permite que inicie apenas uma vez
                erroTurmas = 0;
                erroVencimentos = 0;
                erroMatriculas = 0;
                jBHabilitarMsnErros1.setToolTipText("Habilitado!");
                jBHabilitarMsnErros.setVisible(false);
                jBHabilitarMsnErros1.setVisible(true);
                jBNaoHabilitdo.setVisible(false);
                jBNaoHabilitdo1.setVisible(false);
                jBNaoHabilitdo2.setVisible(false);
                jBNaoHabilitdo3.setVisible(false);
                JOptionPane.showMessageDialog(rootPane, "Verificações do sistema e demais serviços.",
                        "Habilitado!", JOptionPane.INFORMATION_MESSAGE);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("bloquear")
                    || txtBuscar.getText().toLowerCase().contains("café")
                    || txtBuscar.getText().toLowerCase().contains("cafe")
                    || txtBuscar.getText().toLowerCase().contains("pause")
                    || txtBuscar.getText().toLowerCase().contains("parar o sistema")
                    || txtBuscar.getText().toLowerCase().contains("parar")
                    || txtBuscar.getText().toLowerCase().contains("stop")) {
                jBBloquearTela.setVisible(true);
                jBBloquearTela1.setVisible(false);
                jBHabilitarMsnErros.setVisible(false);
                jBHabilitarMsnErros1.setVisible(false);
                NowString();
                try {
                    UsuarioDAO dao = new UsuarioDAO();
                    Usuario Verificando;
                    Verificando = dao.carregarUsuario(txtUsuarioUserName.getText());
                    AcessoDAO daos = new AcessoDAO();
                    Acesso okay = new Acesso();
                    Verificando = dao.carregarUsuario(txtUsuarioUserName.getText());
                    NowString();
                    okay.setUsuario(Verificando.getCodigo());
                    okay.setData(s);
                    okay.setHora(f);
                    okay.setDescricao("Bloqueio do sistema efetuado [Interface - Menu]");
                    daos.adicionarAcesso(okay);
                    okay = null;
                    Verificando = null;
                    daos.desconectar();
                    dao.desconectar();
                } catch (Exception e) {
                }
                new FrameBloqueioTela(this, rootPaneCheckingEnabled).setVisible(true);
                jBBloquearTela.setVisible(false);
                jBBloquearTela1.setVisible(false);
                jBHabilitarMsnErros.setVisible(false);
                jBHabilitarMsnErros1.setVisible(false);
                txtBuscar.setText("");
            }

            if (txtBuscar.getText().toLowerCase().contains("fecha")
                    || txtBuscar.getText().toLowerCase().contains("fechar programa")
                    || txtBuscar.getText().toLowerCase().contains("finalizar programa")
                    || txtBuscar.getText().toLowerCase().contains("sair do programa")
                    || txtBuscar.getText().toLowerCase().contains("sair")
                    || txtBuscar.getText().toLowerCase().contains("finalizar")
                    || txtBuscar.getText().toLowerCase().contains("desligar")
                    || txtBuscar.getText().toLowerCase().contains("bye")
                    || txtBuscar.getText().toLowerCase().contains("exit")) {
                int selection = JOptionPane.showConfirmDialog(rootPane,
                        "Deseja sair do sistema?",
                        "Finalizar o sistema", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    try {
                        Fechando();
                    } catch (Exception e) {
                    }
                }
                //txtBuscar.setText("");
            }
            if (txtBuscar.getText().toLowerCase().contains("recarrega")
                    || txtBuscar.getText().toLowerCase().contains("recarregar menu")
                    || txtBuscar.getText().toLowerCase().contains("refazer")
                    || txtBuscar.getText().toLowerCase().contains("recarregar o menu")
                    || txtBuscar.getText().toLowerCase().contains("reinicia")
                    || txtBuscar.getText().toLowerCase().contains("recomeça")) {
                Menu.this.dispose();
                new Menu().setVisible(true);
                //txtBuscar.setText("");
            }
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        txtBuscar.setText(txtBuscar.getText().replace("Buscar", ""));
        jBBuscarPagamento.setVisible(false);
        jBBuscarContrato.setVisible(false);
        jBBuscarMatricula.setVisible(false);
        jBBuscarAluno.setVisible(false);
        jBBuscarRelatorio.setVisible(false);
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void jBBuscarIconesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarIconesActionPerformed
        jBBuscarPagamento.setVisible(true);
        jBBuscarContrato.setVisible(true);
        jBBuscarMatricula.setVisible(true);
        jBBuscarAluno.setVisible(true);
        jBBuscarRelatorio.setVisible(true);
    }//GEN-LAST:event_jBBuscarIconesActionPerformed

    private void jBBuscarPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarPagamentoActionPerformed
        if (VerificaFinal) {
            if (aberto || !txtTotalCaixa.getText().equals("")) {
                try {
                    new PagamentosDetalhes().setVisible(true);
                } catch (BancoException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "O caixa está fechado!");
            try {
                try {
                    Main.Caixa.dispose();
                    Main.Caixa = new CaixaInterface();
                    Main.Caixa.AoIniciar();
                } catch (BancoException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.Caixa.setVisible(true);
            } catch (ParseException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jBBuscarPagamentoActionPerformed

    private void jBBuscarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarRelatorioActionPerformed
        new Relatorios(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jBBuscarRelatorioActionPerformed

    private void jBBuscarAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarAlunoActionPerformed
        int CodigoLocalidade;
        try {
            LocalidadeDAO dao = new LocalidadeDAO();
            CodigoLocalidade = dao.gerarCodigoLocalidade() - 1;
            if (CodigoLocalidade == 0) {
                int selection = JOptionPane.showConfirmDialog(this,
                        "Não há Local cadastrado!\n"
                        + "Aconselhável cadastrar um local antes de cadastrar aluno.\n"
                        + "Deseja abrir o cadastro de Local?",
                        "Seeds", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    Localidades Novo;
                    try {
                        Novo = new Localidades();
                        Novo.setVisible(true);
                    } catch (BancoException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Clientes Novo;
                    try {
                        Novo = new Clientes();
                        Novo.setVisible(true);
                    } catch (ParseException ex) {
                        Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                Clientes Novo;
                try {
                    Novo = new Clientes();
                    Novo.setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(FramePai.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            dao.desconectar();
        } catch (BancoException f) {
            f.printStackTrace();
        }
    }//GEN-LAST:event_jBBuscarAlunoActionPerformed

    private void jBBuscarMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarMatriculaActionPerformed
        Matriculas Novo;
        try {
            Novo = new Matriculas();
            Novo.setVisible(true);
        } catch (BancoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBBuscarMatriculaActionPerformed

    private void jBBuscarContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarContratoActionPerformed
        Contratos cont;
        try {
            cont = new Contratos();
            cont.setVisible(true);
        } catch (BancoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBBuscarContratoActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        txtDataSO.setForeground(Color.white);
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jBXMeusVencimentos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBXMeusVencimentos1ActionPerformed
        txtDataSO.setForeground(Color.orange);
    }//GEN-LAST:event_jBXMeusVencimentos1ActionPerformed

    private void txtDataSOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataSOMouseClicked
        txtDataSO.setForeground(Color.white);
    }//GEN-LAST:event_txtDataSOMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        txtDataSO.setForeground(Color.white);
    }//GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;




                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBloquearTela;
    private javax.swing.JButton jBBloquearTela1;
    private javax.swing.JButton jBBuscarAluno;
    private javax.swing.JButton jBBuscarContrato;
    private javax.swing.JButton jBBuscarIcones;
    private javax.swing.JButton jBBuscarMatricula;
    private javax.swing.JButton jBBuscarPagamento;
    private javax.swing.JButton jBBuscarRelatorio;
    private javax.swing.JButton jBCadastro;
    private javax.swing.JButton jBCaixaAberto;
    private javax.swing.JButton jBCaixaFechado;
    private javax.swing.JButton jBConectado;
    private javax.swing.JButton jBConectadoIn;
    private javax.swing.JButton jBConectadoOut;
    private javax.swing.JButton jBConfigurar;
    private javax.swing.JButton jBEmailInBox;
    private javax.swing.JButton jBEmailOutBox;
    private javax.swing.JButton jBEstoques;
    private javax.swing.JButton jBFinancas;
    private javax.swing.JButton jBHabilitarMsnErros;
    private javax.swing.JButton jBHabilitarMsnErros1;
    private javax.swing.JButton jBNaoConectado;
    private javax.swing.JButton jBNaoHabilitdo;
    private javax.swing.JButton jBNaoHabilitdo1;
    private javax.swing.JButton jBNaoHabilitdo2;
    private javax.swing.JButton jBNaoHabilitdo3;
    private javax.swing.JButton jBNaoHabilitdo4;
    private javax.swing.JButton jBSTART;
    private javax.swing.JButton jBVencimentos;
    private javax.swing.JButton jBVendas;
    private javax.swing.JButton jBXEmail;
    private javax.swing.JButton jBXMeusVencimentos;
    private javax.swing.JButton jBXMeusVencimentos1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLEntradaCaixa;
    private javax.swing.JLabel jLMenuLateral;
    private javax.swing.JLabel jLMenuLateralAbrirFechar;
    private javax.swing.JLabel jLSaidaCaixa;
    private javax.swing.JLabel jLSeedsAdministrador;
    private javax.swing.JLabel jLSeedsDefaul;
    private javax.swing.JLabel jLSeedsSecretaria;
    private javax.swing.JLabel jLSeedsServicosGeral;
    private javax.swing.JLabel jLSeedsVendedores;
    private javax.swing.JLabel jLStore;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JPanel jPMenuLateral;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtDataSO;
    private javax.swing.JTextField txtDataSO12;
    private javax.swing.JTextField txtDataSO13;
    private javax.swing.JTextField txtDataSO14;
    private javax.swing.JTextField txtDataSO15;
    private javax.swing.JTextField txtDataSO7;
    private javax.swing.JTextField txtEnderecoIPv4;
    private javax.swing.JTextField txtEntradaCaixa;
    private javax.swing.JTextField txtEstoqueBaixo1;
    private javax.swing.JTextField txtEstoqueBaixo2;
    private javax.swing.JTextField txtEstoqueBaixo3;
    private javax.swing.JTextArea txtMensagemDeEmailSeeds;
    private javax.swing.JTextField txtNomeDoEmail;
    private javax.swing.JTextField txtNomedaBasedeDados;
    private javax.swing.JTextField txtPorta;
    private javax.swing.JTextField txtSaidaCaixa;
    private javax.swing.JTextField txtTotalAReceber;
    private javax.swing.JTextField txtTotalCaixa;
    private javax.swing.JTextField txtTotalDeAlunos;
    private javax.swing.JTextField txtTotalDeEmails;
    private javax.swing.JTextField txtTotalDeTurmaHoje;
    private javax.swing.JTextField txtTotalDeVencimentos;
    private javax.swing.JTextField txtTurma;
    private javax.swing.JTextField txtTurmaHorario;
    private javax.swing.JTextField txtUsuarioNome;
    private javax.swing.JTextField txtUsuarioUserName;
    // End of variables declaration//GEN-END:variables
}
