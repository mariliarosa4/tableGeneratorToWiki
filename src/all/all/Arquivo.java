package all;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Arquivo {

    String nomeArquivo;
    String nomeFicheiro;
    ArrayList<String> niveis;

    public Arquivo(String nomeArquivo, String nomeFicheiro, ArrayList<String> niveis) {
        this.nomeArquivo = nomeArquivo;
        this.nomeFicheiro = nomeFicheiro;
        this.niveis = niveis;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public String getNomeFicheiro() {
        return nomeFicheiro;
    }

    public ArrayList<String> getNiveis() {
        return niveis;
    }

    
}
