package all;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class Tablemaker {

    String path; //path da pasta onde est�o os arquivos no computador local ou vulcano
    String pastaSetor; // nome da pasta dos artefatos na wiki
    String dtAtualizacao;
    boolean ListaDeServicosDisponivel;

    static String PATH_DOCUMENTOS_APOIO = "http://nqi.ufcspa.edu.br/wiki/documentos_de_apoio/m3p/";
    static String LINE_BREAK = "\n";
    static String BULLET_PADRAO_PAR = ""; // DEFINIR
    static String BULLET_PADRAO_IMPAR = ""; //DEFINIR
    static String PREFIXO_LABEL_NOME_ARQUIVO = "Template para ";
    static String ESPACAMENTO_NIVEL_TABELA = "&thinsp; &thinsp; &thinsp; ";
    static String ESPACAMENTO_SIMPLES = "&thinsp; ";
    static String BULLET_ESTRELA_IMPAR = "&#10030; ";
    static String BULLET_ESTRELA_PAR = "&#10025; ";
    static String IMAGEM_DONWLOAD = "";
    static String IMAGEM_NO_DONWLOAD = "";
    static String STYLE_TEXTO = " style=\"text-align: center; background-color:#e8e8e8;\" ";

    public Tablemaker(String path, String pastaArquivos, String dtAtualizacao) {
        super();
        this.path = path;
        this.pastaSetor = pastaArquivos;
        this.dtAtualizacao = dtAtualizacao;
    }

    ArrayList<Arquivo> geraListaArtefatos(ArrayList<String> listaFicheiros) {

        ArrayList<Arquivo> listaArtefatos = new ArrayList<Arquivo>();
        for (String nomeFicheiro : listaFicheiros) {
            String[] nomeLabelArquivo = nomeFicheiro.split("\\.");
            
            String[] arrayNomeArquivo = nomeLabelArquivo[0].split("]");
            if (arrayNomeArquivo.length > 1) {
                nomeLabelArquivo[0] = PREFIXO_LABEL_NOME_ARQUIVO + arrayNomeArquivo[1].trim();
            }
            listaArtefatos.add(new Arquivo(nomeLabelArquivo[0], nomeFicheiro, null));

        }
        return listaArtefatos;
    }

    public ArrayList<String> buscaNomesArquivos(String pathFile) {

        File folder = new File(pathFile);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> listaNomesFicheiros = new ArrayList<String>(); // n�o h� m�ximo de arquivos

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                listaNomesFicheiros.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                listaNomesFicheiros.add(listOfFiles[i].getName());
            }
        }
        System.out.println(listaNomesFicheiros);
        return listaNomesFicheiros;
    }

    //Encoda a URL para formato URI (reconhecido pela wiki)
    //Copiado do github
    private static String encodeNomeFicheiro(String s) {
        String result;

        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    public String gerarCodigo(DefaultTableModel modelTable, ArrayList<Arquivo> listaObjetosArquivos) {

        String tabela = "";
        String cabecalho = "\n"
                + "! style=\"text-align: center; font-family:Tahoma, Geneva, sans-serif !important;; background-color:#eeaa42; color:#ffffff; height: 50px;\" | " + modelTable.getColumnName(0) + " \n"
                + "! style=\"text-align: center; font-family:Tahoma, Geneva, sans-serif !important;; background-color:#486d9f; color:#ffffff; width: 60px;\" | Nível 0\n"
                + "! style=\"text-align: center; font-family:Tahoma, Geneva, sans-serif !important;; background-color:#486d9f; color:#ffffff; width: 60px;\" | Nível 1\n"
                + "! style=\"text-align: center; font-family:Tahoma, Geneva, sans-serif !important;; background-color:#486d9f; color:#ffffff; width: 60px;\" | Nível 2\n"
                + "! style=\"text-align: center; font-family:Tahoma, Geneva, sans-serif !important;; background-color:#486d9f; color:#ffffff; width: 60px;\" | Nível 3\n"
                + "! style=\"text-align: center; font-family:Tahoma, Geneva, sans-serif !important;; background-color:#486d9f; color:#ffffff; width: 60px;\" | Nível 4\n"
                + "! style=\"text-align: center; font-family:Tahoma, Geneva, sans-serif !important;; background-color:#486d9f; color:#ffffff; width: 60px;\" | Nível 5\n"
                + "! style=\"text-align: center; font-family:Tahoma, Geneva, sans-serif !important;; background-color:#486d9f; color:#ffffff; width: 60px;\" | Nível 6\n"
                + "! style=\"text-align: center; font-family:Tahoma, Geneva, sans-serif !important;; background-color:#486d9f; color:#ffffff; width: 60px;\" | Nível 7\n"
                + "|-" + LINE_BREAK;

        tabela += cabecalho;

        for (int row = 0; row < modelTable.getRowCount(); row++) {
            String linha = "";

            linha += "|" + STYLE_TEXTO + "|" + modelTable.getValueAt(row, 0) + LINE_BREAK;

            for (int col = 1; col < modelTable.getColumnCount(); col++) {

                if (modelTable.getValueAt(row, col) == null) {
                    linha += "|" + STYLE_TEXTO + "| [" + "[File:No icon.png|12px|link=|center]" + "]" + LINE_BREAK;
                } else {
                    linha += "|" + STYLE_TEXTO + "| [" + "[File:Donwload-button.png|15px|link=" + PATH_DOCUMENTOS_APOIO + this.pastaSetor + "/" + encodeNomeFicheiro(procurarNomeFicheiroByNomeArquivo((String) modelTable.getValueAt(row, 0), listaObjetosArquivos)) + "|center]" + "]" + LINE_BREAK;
                }
            }
            linha += "|-" + LINE_BREAK;
            tabela += linha;
        }

        return tabela;

    }

    public String procurarNomeFicheiroByNomeArquivo(String nomeArquivo, ArrayList<Arquivo> listaObjetosArquivos) {
        System.out.println(listaObjetosArquivos);
        for (Arquivo objetoArquivo : listaObjetosArquivos) {
            if (objetoArquivo.nomeArquivo.equals(nomeArquivo)) {
                return objetoArquivo.getNomeFicheiro();
            }
        }
        return null;
    }

}
