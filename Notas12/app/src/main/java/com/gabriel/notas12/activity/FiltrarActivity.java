package com.gabriel.notas12.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gabriel.notas12.ArmazenamentoPreferencias;
import com.gabriel.notas12.R;

public class FiltrarActivity extends AppCompatActivity {

    private RadioGroup radioGroupOrdenar;
    private RadioButton radioMaisRecentes, radioMaisAntigas;
    private EditText editPesquisa;
    private CheckBox checkNotas, checkLembretes;
    private Button buttonAplicarFiltros;

    private ArmazenamentoPreferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar);

        radioGroupOrdenar = findViewById(R.id.radioGroupOrdenar);
        radioMaisRecentes = findViewById(R.id.radioMaisRecentes);
        radioMaisAntigas = findViewById(R.id.radioMaisAntigas);
        editPesquisa = findViewById(R.id.editPesquisa);
        checkNotas = findViewById(R.id.checkNotas);
        checkLembretes = findViewById(R.id.checkLembretes);
        buttonAplicarFiltros = findViewById(R.id.buttonAplicarFiltros);

        preferencias = new ArmazenamentoPreferencias(getApplicationContext());

        recuperarFiltrosAplicados();

        buttonAplicarFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNotas.isChecked() || checkLembretes.isChecked()) {

                    preferencias.setOrdenacao(radioGroupOrdenar.getCheckedRadioButtonId());
                    preferencias.setPesquisa(editPesquisa.getText().toString());
                    preferencias.setFiltroEmNotas(checkNotas.isChecked());
                    preferencias.setFiltroEmLembretes(checkLembretes.isChecked());

                    Toast.makeText(getApplicationContext(), "Filtros aplicados", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Selecione onde aplicar os filtros", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void recuperarFiltrosAplicados() {
        radioGroupOrdenar.check(preferencias.getOrdenacao());
        editPesquisa.setText(preferencias.getPesquisa());
        checkNotas.setChecked(preferencias.isFiltroEmNotas());
        checkLembretes.setChecked(preferencias.isFiltroEmLembretes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sair, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}