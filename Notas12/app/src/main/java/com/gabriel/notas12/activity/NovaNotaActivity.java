package com.gabriel.notas12.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gabriel.notas12.ArmazenamentoPreferencias;
import com.gabriel.notas12.Nota;
import com.gabriel.notas12.R;

public class NovaNotaActivity extends AppCompatActivity {

    private EditText editTitulo, editNota;
    private ToggleButton toggleLembrete;
    private ArmazenamentoPreferencias preferencias;
    private Nota notaAtual;
    private boolean editar = false;
    private ConstraintLayout constraintLayoutNovaNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_nota);

        editTitulo = findViewById(R.id.editTitulo);
        editNota = findViewById(R.id.editNota);
        toggleLembrete = findViewById(R.id.toggleLembrete);
        constraintLayoutNovaNota = findViewById(R.id.constraintLayoutNovaNota);

        preferencias = new ArmazenamentoPreferencias(getApplicationContext());

        constraintLayoutNovaNota.setBackgroundColor(getResources().getColor(preferencias.getCorNota()));
        editTitulo.setTextSize(preferencias.getTamanhoFonte("titulo"));
        editNota.setTextSize(preferencias.getTamanhoFonte("texto"));

        notaAtual = (Nota) getIntent().getSerializableExtra("notaSelecionada");

        if (notaAtual != null) {
            editTitulo.setText(notaAtual.getTitulo());
            editNota.setText(notaAtual.getNota());
            toggleLembrete.setChecked(notaAtual.isLembreteAtivado());
            editar = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemSalvar) {
            String titulo = editTitulo.getText().toString();
            String nota = editNota.getText().toString();
            boolean lembreteAtivado = toggleLembrete.isChecked();

            if (titulo.equals("") || nota.equals("")) {
                Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                if (editar) {
                    preferencias.setNota(titulo, nota, lembreteAtivado, notaAtual.getId());
                    Toast.makeText(getApplicationContext(), "Nota editada com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    preferencias.setNota(titulo, nota, lembreteAtivado, null);
                    Toast.makeText(getApplicationContext(), "Nota salva com sucesso", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}