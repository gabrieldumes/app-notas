package com.gabriel.notas12.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabriel.notas12.ArmazenamentoPreferencias;
import com.gabriel.notas12.Nota;
import com.gabriel.notas12.R;
import com.gabriel.notas12.RecyclerItemClickListener;
import com.gabriel.notas12.adapter.Adapter;

import java.util.ArrayList;
import java.util.List;

public class LixeiraFragment extends Fragment {

    private RecyclerView recyclerViewLixeira;
    private List<Nota> listaLixeira = new ArrayList<>();
    private ArmazenamentoPreferencias preferencias;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lixeira, container, false);

        recyclerViewLixeira = view.findViewById(R.id.recyclerViewLixeira);

        preferencias = new ArmazenamentoPreferencias(getActivity());

        recyclerViewLixeira.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerViewLixeira,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(
                                        getActivity(),
                                        "Pressione para restaurar a nota",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onLongItemClick(View view, final int position) {
                                AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());

                                dialogo.setTitle("Quer restaurar esta nota?");

                                dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Nota nota = listaLixeira.get(position);
                                        preferencias.setStatus(true, nota.getId());
                                        carregarListaLixeira();
                                    }
                                });

                                dialogo.setNegativeButton("Não", null);

                                dialogo.create().show();
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
        carregarListaLixeira();
    }

    public void carregarListaLixeira() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewLixeira.setLayoutManager(layoutManager);

        criarLista();
        Adapter adapter = new Adapter(this.listaLixeira);
        recyclerViewLixeira.setAdapter(adapter);
    }

    public void criarLista() {
        listaLixeira.clear();

        for (int n = preferencias.getIndice(); n >= 1; n--) {
            if (!preferencias.getStatus(n)) {
                String titulo = preferencias.getNota("titulo", n);
                String textoNota = preferencias.getNota("nota", n);
                boolean lembreteAtivado = preferencias.isLembreteAtivado(n);

                Nota nota = new Nota(titulo, textoNota, lembreteAtivado, n);
                listaLixeira.add(nota);
            }
        }
    }
}
