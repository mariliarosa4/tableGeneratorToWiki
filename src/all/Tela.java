package all;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JTable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.table.DefaultTableModel;

public class Tela extends JFrame {

    private JTable tableArtefatos;
    private JTable tableDocsApoio;
    public DefaultTableModel modelArtefatos;
    public DefaultTableModel modelDocsApoio;
    public Tablemaker tablemaker;

    private final JPanel contentPane;
    private final JPanel contentTablePane;
    private JTextPane txtResultado;
    private JTextField txtDtAtualizacao;
    private JTextField txtPastaWiki;
    private JTextField txtPathArtefatos;
    private JTextField txtPathDocsApoio;
    private ArrayList<Arquivo> arquivosArtefatos;
    private ArrayList<Arquivo> arquivosDocsApoio;
    public Tela This = this;
    private final JPanel resultPanel;
    static String LINE_BREAK = "\n";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Tela frame = new Tela();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private final JScrollPane scrollPaneResultado;
    private Tablemaker tablemakerArtefatos;
    private Tablemaker tablemakerDocsApoio;

    public Tela() {
        setTitle("Gerador Wiki - Artefatos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 1000);
        contentPane = new JPanel(new BorderLayout());
        gerarPaneInicial();

        preprararInputCaminhos();
        preprararInputPastaNaWiki();
        prepararInputDataAtualizacao();
        prepararBotaoGerarTabela();
        prepararRodapeNQI();

        setLayout(new GridLayout(3, 1));
        contentTablePane = new JPanel();

        resultPanel = new JPanel();

        scrollPaneResultado = new JScrollPane();

        add(contentPane);
        add(contentTablePane);
        add(scrollPaneResultado);

        contentPane.setVisible(true);
        setResizable(false);

    }

    private void gerarPaneInicial() {
        JLabel lblGeradorDePgina = new JLabel("Gerador de P\u00E1gina WIKI - Artefatos");
        lblGeradorDePgina.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblGeradorDePgina.setHorizontalAlignment(SwingConstants.CENTER);
        lblGeradorDePgina.setBounds(1, 0, 500, 100);
        contentPane.add(lblGeradorDePgina);

    }

    private void preprararInputCaminhos() {
        JLabel lblCaminhoArt = new JLabel("Caminho Artefatos:");
        lblCaminhoArt.setBounds(10, 100, 200, 20);
        contentPane.add(lblCaminhoArt);

        txtPathArtefatos = new JTextField();
        txtPathArtefatos.setBounds(10, 120, 404, 20);
        txtPathArtefatos.setText("Q:\\qualidade\\M3P\\Artefatos\\M3P_4ed\\Templates");
        contentPane.add(txtPathArtefatos);
        txtPathArtefatos.setColumns(10);

        JLabel lblCaminhoDocs = new JLabel("Caminho documentos de apoio:");
        lblCaminhoDocs.setBounds(10, 140, 200, 30);
        contentPane.add(lblCaminhoDocs);

        txtPathDocsApoio = new JTextField();
        txtPathDocsApoio.setBounds(10, 165, 404, 20);
        txtPathDocsApoio.setText("Q:\\qualidade\\M3P\\Artefatos\\M3P_4ed\\Documentos de apoio");
        contentPane.add(txtPathDocsApoio);
        txtPathDocsApoio.setColumns(10);
    }

    private void preprararInputPastaNaWiki() {
        JLabel lblPastaDoSetor = new JLabel("Pasta na wiki:");
        lblPastaDoSetor.setBounds(450, 100, 87, 14);
        contentPane.add(lblPastaDoSetor);

        txtPastaWiki = new JTextField();
        txtPastaWiki.setText("Artefatos_guia_4_ed");
        txtPastaWiki.setBounds(450, 120, 136, 20);
        contentPane.add(txtPastaWiki);
        txtPastaWiki.setColumns(10);
    }

    private void prepararInputDataAtualizacao() {

        JLabel lblDataAtualizao = new JLabel("Data Atualiza\u00E7\u00E3o:");
        lblDataAtualizao.setBounds(650, 100, 106, 14);
        contentPane.add(lblDataAtualizao);

        txtDtAtualizacao = new JTextField();
        txtDtAtualizacao.setText("28/01/2020");
        txtDtAtualizacao.setBounds(650, 120, 100, 20);
        contentPane.add(txtDtAtualizacao);
        txtDtAtualizacao.setColumns(10);

    }

    private void prepararRodapeNQI() {

        JLabel lblNqiUfcspa = new JLabel("NQI UFCSPA");
        lblNqiUfcspa.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNqiUfcspa.setBounds(150, 200, 1, 14);
        contentPane.add(lblNqiUfcspa);
    }

    private void prepararBotaoGerarTabela() {
        JButton btnGerarPgina = new JButton("Gerar Tabela");
        btnGerarPgina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    gerarTabelaDeArtefatos();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(This, e.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnGerarPgina.setBounds(800, 120, 121, 23);
        contentPane.add(btnGerarPgina);
    }

    private void gerarTabelaDeArtefatos() {
        String caminhoArtefatos = txtPathArtefatos.getText();
        String caminhoDocsApoio = txtPathDocsApoio.getText();

        String pastaWiki = txtPastaWiki.getText();
        String dtAtualizacao = txtDtAtualizacao.getText();

        String[] arrayArtefatos = caminhoArtefatos.split("\\\\");
        System.out.println(arrayArtefatos[arrayArtefatos.length - 1]);
        tablemakerArtefatos = new Tablemaker(caminhoArtefatos, pastaWiki + "/" + arrayArtefatos[arrayArtefatos.length - 1], dtAtualizacao);
        arquivosArtefatos = tablemakerArtefatos.geraListaArtefatos(tablemakerArtefatos.buscaNomesArquivos(caminhoArtefatos));

        String[] arrayDocsApoio = caminhoDocsApoio.split("\\\\");
        tablemakerDocsApoio = new Tablemaker(caminhoDocsApoio, pastaWiki + "/" + arrayDocsApoio[arrayDocsApoio.length - 1], dtAtualizacao);
        arquivosDocsApoio = tablemakerDocsApoio.geraListaArtefatos(tablemakerDocsApoio.buscaNomesArquivos(caminhoDocsApoio));

        modelDocsApoio = gerarModeloTabela("Documentos de Apoio", arquivosDocsApoio, modelDocsApoio);
        modelArtefatos = gerarModeloTabela("Artefatos", arquivosArtefatos, modelArtefatos);

        gerarPanelTabela();
    }

    private DefaultTableModel gerarModeloTabela(String tipoTabela, ArrayList<Arquivo> arquivos, DefaultTableModel model) {
        model = new DefaultTableModel(new Object[]{tipoTabela, "Nível 0", "Nível 1", "Nível 2", "Nível 3", "Nível 4", "Nível 5", "Nível 6", "Nível 7"}, 0) {
            Class[] types = {
                String.class, Boolean.class, Boolean.class, Boolean.class,
                Boolean.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];

            }

        ;
        };
        
        for (Arquivo artefato : arquivos) {
            model.addRow(new Object[]{artefato.nomeArquivo});
        }
        System.out.println(model.getDataVector());
        return model;
    }

    private void gerarCodigoTabela() {
        String tabelaArtefatos = tablemakerArtefatos.gerarCodigo(modelArtefatos, arquivosArtefatos);
        String tabelaDocsApoio = tablemakerDocsApoio.gerarCodigo(modelDocsApoio, arquivosDocsApoio);
        String codigoFinal = "{|" + tabelaArtefatos + tabelaDocsApoio + "|}" + LINE_BREAK + "Atualizado em: " + txtDtAtualizacao.getText();

        System.out.println(codigoFinal);
        mostrarResultado(codigoFinal);

    }

    private void gerarPanelTabela() {
        JLabel lblNqiUfcspa = new JLabel("Assinale os níveis correspondentes aos arquivos listados:");
        lblNqiUfcspa.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNqiUfcspa.setBounds(150, 200, 1, 14);
        contentTablePane.add(lblNqiUfcspa);

        tableArtefatos = new JTable(modelArtefatos);
        tableDocsApoio = new JTable(modelDocsApoio);

        JScrollPane scrollPaneArtefatos = new JScrollPane(tableArtefatos);
        scrollPaneArtefatos.setPreferredSize(new Dimension(1200, 120));
        scrollPaneArtefatos.setBorder(null);
        contentTablePane.add(scrollPaneArtefatos);

        JScrollPane scrollPaneDocsApoio = new JScrollPane(tableDocsApoio);
        scrollPaneDocsApoio.setPreferredSize(new Dimension(1200, 120));
        contentTablePane.add(scrollPaneDocsApoio);
        JButton btnGerarCodigo = new JButton("Gerar código");
        btnGerarCodigo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    gerarCodigoTabela();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    JOptionPane.showMessageDialog(This, e.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        contentTablePane.add(btnGerarCodigo, BorderLayout.PAGE_END);

        revalidate();
        repaint();
        setVisible(true);

    }

    private void mostrarResultado(String textoResultado) {

        txtResultado = new JTextPane();
        scrollPaneResultado.setViewportView(txtResultado);
        scrollPaneResultado.add(resultPanel);
        txtResultado.setText(textoResultado);
        revalidate();
        repaint();
        setVisible(true);

    }

}
