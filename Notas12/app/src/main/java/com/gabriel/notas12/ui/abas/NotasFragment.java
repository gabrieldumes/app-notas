package com.gabriel.notas12.ui.abas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.gabriel.notas12.ArmazenamentoPreferencias;
import com.gabriel.notas12.Nota;
import com.gabriel.notas12.R;
import com.gabriel.notas12.RecyclerItemClickListener;
import com.gabriel.notas12.activity.NovaNotaActivity;
import com.gabriel.notas12.adapter.Adapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArmazenamentoPreferencias preferencias;
    private RecyclerView recyclerViewNotas;
    private List<Nota> listaNotas = new ArrayList<>();

    public NotasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotasFragment newInstance(String param1, String param2) {
        NotasFragment fragment = new NotasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        recyclerViewNotas = view.findViewById(R.id.recyclerViewNotas);

        preferencias = new ArmazenamentoPreferencias(getActivity());

        recyclerViewNotas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerViewNotas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Nota nota = listaNotas.get(position);
                                Intent intent = new Intent(getActivity(), NovaNotaActivity.class);
                                intent.putExtra("notaSelecionada", nota);

                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, final int position) {

                                AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());

                                dialogo.setTitle("Deseja excluir esta nota?");

                                dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Nota nota = listaNotas.get(position);

                                        preferencias.setStatus(false, nota.getId());
                                        carregarListaNotas();
                                        Toast.makeText(getActivity(), "Nota excluída", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                dialogo.setNegativeButton("Não", null);

                                dialogo.create();
                                dialogo.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        carregarListaNotas();
    }

    public void carregarListaNotas() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNotas.setLayoutManager(layoutManager);

        criarLista();
        Adapter adapter = new Adapter(listaNotas);
        recyclerViewNotas.setAdapter(adapter);
    }

    public void criarLista() {
        listaNotas.clear();

        //recuperar os filtros
        int ordenarPor = preferencias.getOrdenacao();
        String pesquisa = preferencias.getPesquisa().toLowerCase();

        if (preferencias.isFiltroEmNotas()) {
            switch (ordenarPor) {
                case R.id.radioMaisRecentes:
                    for (int n = preferencias.getIndice(); n >= 1; n--) {
                        if (preferencias.getStatus(n)) {
                            String titulo = preferencias.getNota("titulo", n);
                            String textoNota = preferencias.getNota("nota", n);
                            boolean lembreteAtivado = preferencias.isLembreteAtivado(n);

                            if (!pesquisa.equals("")) {
                                if (titulo.toLowerCase().contains(pesquisa) ||
                                        textoNota.toLowerCase().contains(pesquisa)) {
                                    Nota nota = new Nota(titulo, textoNota, lembreteAtivado, n);
                                    listaNotas.add(nota);
                                }
                            } else {
                                Nota nota = new Nota(titulo, textoNota, lembreteAtivado, n);
                                listaNotas.add(nota);
                            }
                        }
                    }
                    break;
                case R.id.radioMaisAntigas:
                    for (int n = 1; n <= preferencias.getIndice(); n++) {
                        if (preferencias.getStatus(n)) {
                            String titulo = preferencias.getNota("titulo", n);
                            String textoNota = preferencias.getNota("nota", n);
                            boolean lembreteAtivado = preferencias.isLembreteAtivado(n);

                            if (!pesquisa.equals("")) {
                                if (titulo.toLowerCase().contains(pesquisa.toLowerCase()) ||
                                        textoNota.toLowerCase().contains(pesquisa.toLowerCase())) {
                                    Nota nota = new Nota(titulo, textoNota, lembreteAtivado, n);
                                    listaNotas.add(nota);
                                }
                            } else {
                                Nota nota = new Nota(titulo, textoNota, lembreteAtivado, n);
                                listaNotas.add(nota);
                            }
                        }
                    }
            }
        } else { //Exibição PADRÃO
            for (int n = preferencias.getIndice(); n >= 1; n--) {

                if (preferencias.getStatus(n)) {
                    String titulo = preferencias.getNota("titulo", n);
                    String textoNota = preferencias.getNota("nota", n);
                    boolean lembreteAtivado = preferencias.isLembreteAtivado(n);

                    Nota nota = new Nota(titulo, textoNota, lembreteAtivado, n);
                    listaNotas.add(nota);
                }
            }
        }
    }
}