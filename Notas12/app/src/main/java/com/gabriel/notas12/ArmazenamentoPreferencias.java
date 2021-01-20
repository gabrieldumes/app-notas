package com.gabriel.notas12;

import android.content.Context;
import android.content.SharedPreferences;

public class ArmazenamentoPreferencias {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final String NOME_ARQUIVO = "armazenamento.preferencias";

    //Notas
    private final String CHAVE_INDICE = "indice";
    private final String CHAVE_TITULO = "titulo";
    private final String CHAVE_NOTA = "nota";
    private final String CHAVE_LEMBRETE = "lembrete";
    private final String CHAVE_STATUS = "status";

    //Filtros
    private final String CHAVE_ORDENACAO = "ordenacao";
    private final String CHAVE_PESQUISA = "termoDePesquisa";
    private final String CHAVE_FILTRO_EM_NOTAS = "filtroEmNotas";
    private final String CHAVE_FILTRO_EM_LEMBRETES = "filtroEmLembretes";

    //Configurações
    private final String CHAVE_COR_NOTA = "corNota";
    private final String CHAVE_TAMANHO_TITULO = "tamanhoTitulo";
    private final String CHAVE_TAMANHO_TEXTO = "tamanhoTexto";
    private final String CHAVE_SEEK_PROGRESS = "seekProgress";

    public ArmazenamentoPreferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, 0);
        editor = preferences.edit();
        if (getIndice() == -43) {
            setIndice(0);
        }
    }

    public void setNota(String titulo, String nota, boolean lembreteAtivado, Integer i) {

        if (i == null) {
            i = getIndice() + 1;
            setIndice(i);
        }

        editor.putString(CHAVE_TITULO + i, titulo);
        editor.putString(CHAVE_NOTA + i, nota);
        editor.putBoolean(CHAVE_LEMBRETE + i, lembreteAtivado);
        editor.putBoolean(CHAVE_STATUS + i, true);
        editor.commit();
    }

    public String getNota(String opcao, int i) {
        String retorno;

        switch (opcao) {
            case "titulo":
                retorno = preferences.getString(CHAVE_TITULO + i, "Erro ao recuperar título");
                break;
            case "nota":
                retorno = preferences.getString(CHAVE_NOTA + i, "Erro ao recuperar nota");
                break;
            default:
                retorno = "Opção inválida";
        }
        return retorno;
    }

    public boolean isLembreteAtivado(int i) {
        return preferences.getBoolean(CHAVE_LEMBRETE + i, false);
    }

    private void setIndice(int i) {
        editor.putInt(CHAVE_INDICE, i);
        editor.commit();
    }

    public int getIndice() {
        return preferences.getInt(CHAVE_INDICE, -43);
    }

    public void setStatus(boolean status, int i) {
        editor.putBoolean(CHAVE_STATUS + i, status);
        editor.commit();
    }

    public boolean getStatus(int i) {
        return preferences.getBoolean(CHAVE_STATUS + i, true);
    }

    public void setOrdenacao(int ordenacao) {
        editor.putInt(CHAVE_ORDENACAO, ordenacao);
        editor.commit();
    }

    public int getOrdenacao() {
        return preferences.getInt(CHAVE_ORDENACAO, R.id.radioMaisRecentes);
    }

    public void setPesquisa(String termoDePesquisa) {
        editor.putString(CHAVE_PESQUISA, termoDePesquisa);
        editor.commit();
    }

    public String getPesquisa() {
        return preferences.getString(CHAVE_PESQUISA, "fJK_odI8=jf*Mx");
    }

    public void setFiltroEmNotas(boolean filtroEmNotas) {
        editor.putBoolean(CHAVE_FILTRO_EM_NOTAS, filtroEmNotas).commit();
    }

    public boolean isFiltroEmNotas() {
        return preferences.getBoolean(CHAVE_FILTRO_EM_NOTAS, true);
    }

    public void setFiltroEmLembretes(boolean filtroEmLembretes) {
        editor.putBoolean(CHAVE_FILTRO_EM_LEMBRETES, filtroEmLembretes).commit();
    }

    public boolean isFiltroEmLembretes() {
        return preferences.getBoolean(CHAVE_FILTRO_EM_LEMBRETES, false);
    }

    public void setCorNota(int radioId) {
        editor.putInt(CHAVE_COR_NOTA, radioId).commit();
    }

    public int getRadioCheckedId() {
        return preferences.getInt(CHAVE_COR_NOTA, R.id.radioAmarelo);
    }

    public int getCorNota() {
        switch (preferences.getInt(CHAVE_COR_NOTA, R.id.radioAmarelo)) {
            case R.id.radioVerde:
                return R.color.cor_nota_verde;
            case R.id.radioBranco:
                return R.color.cor_nota_branco;
            default:
                return R.color.cor_nota_amarelo;
        }
    }

    public void setTamanhoFonte(float titulo, float texto, float progress) {
        editor.putFloat(CHAVE_TAMANHO_TITULO, titulo);
        editor.putFloat(CHAVE_TAMANHO_TEXTO, texto);
        editor.putFloat(CHAVE_SEEK_PROGRESS, progress);
        editor.commit();
    }

    public float getTamanhoFonte(String opcao) {
        switch (opcao) {
            case "titulo":
                return preferences.getFloat(CHAVE_TAMANHO_TITULO, 20);
            case "texto":
                return preferences.getFloat(CHAVE_TAMANHO_TEXTO, 17);
            case "progress":
                return preferences.getFloat(CHAVE_SEEK_PROGRESS, 1);
            default:
                return 5;
        }
    }
}
