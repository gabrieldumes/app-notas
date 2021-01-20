package com.gabriel.notas12.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gabriel.notas12.ArmazenamentoPreferencias;
import com.gabriel.notas12.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracoesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfiguracoesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SeekBar seekTamanhoFonte;
    private RadioGroup radioGroupCorFundo;
    private RadioButton radioAmarelo, radioVerde, radioBranco;
    private Button buttonExportar;
    private TextView textDeletarDados, textRestaurar;

    private ArmazenamentoPreferencias preferencias;

    public ConfiguracoesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfiguracoesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfiguracoesFragment newInstance(String param1, String param2) {
        ConfiguracoesFragment fragment = new ConfiguracoesFragment();
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
        View view = inflater.inflate(R.layout.fragment_configuracoes, container, false);

        preferencias = new ArmazenamentoPreferencias(getActivity());

        seekTamanhoFonte = view.findViewById(R.id.seekTamanhoFonte);
        radioGroupCorFundo = view.findViewById(R.id.radioGroupCorFundo);
        radioAmarelo = view.findViewById(R.id.radioAmarelo);
        radioVerde = view.findViewById(R.id.radioVerde);
        radioBranco = view.findViewById(R.id.radioBranco);
        buttonExportar = view.findViewById(R.id.buttonExportarNotas);
        textDeletarDados = view.findViewById(R.id.textDeletarDados);
        textRestaurar = view.findViewById(R.id.textRestaurar);

        buttonExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionalidadeIndisponivel();
            }
        });

        textDeletarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionalidadeIndisponivel();
            }
        });

        textRestaurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionalidadeIndisponivel();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        radioGroupCorFundo.check(preferencias.getRadioCheckedId());
        seekTamanhoFonte.setProgress((int) preferencias.getTamanhoFonte("progress"));
    }

    @Override
    public void onPause() {
        super.onPause();
        preferencias.setCorNota(radioGroupCorFundo.getCheckedRadioButtonId());
        switch (seekTamanhoFonte.getProgress()) {
            case 0:
                preferencias.setTamanhoFonte(16, 13, 0);
                break;
            case 1:
                preferencias.setTamanhoFonte(20, 17, 1);
                break;
            case 2:
                preferencias.setTamanhoFonte(24, 20, 2);
        }
    }

    public void funcionalidadeIndisponivel() {
        Toast.makeText(getActivity(), "Funcionalidade indisponível", Toast.LENGTH_SHORT).show();
    }
}