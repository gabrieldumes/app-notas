package com.gabriel.notas12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gabriel.notas12.ArmazenamentoPreferencias;
import com.gabriel.notas12.Nota;
import com.gabriel.notas12.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Nota> listaNotas;
    private Context context;
    private ArmazenamentoPreferencias preferencias;

    public Adapter(List<Nota> listaNotas) {
        this.listaNotas = listaNotas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_adapter_nota, parent, false);
        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        preferencias = new ArmazenamentoPreferencias(context);
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(preferencias.getCorNota()));
        holder.textTitulo.setTextSize(preferencias.getTamanhoFonte("titulo"));
        holder.textNota.setTextSize(preferencias.getTamanhoFonte("texto"));

        if (position == listaNotas.size() - 1) {
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
            layoutParams.setMargins(
                    dpToPixel(10),
                    dpToPixel(13),
                    dpToPixel(10),
                    dpToPixel(13)
            );
            holder.cardView.requestLayout();
        } else {
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
            layoutParams.setMargins(
                    dpToPixel(10),
                    dpToPixel(13),
                    dpToPixel(10),
                    dpToPixel(0)
            );
            holder.cardView.requestLayout();
        }

        Nota nota = listaNotas.get(position);
        holder.textTitulo.setText(nota.getTitulo());
        holder.textNota.setText(nota.getNota());
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTitulo, textNota;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitulo = itemView.findViewById(R.id.textTitulo);
            textNota = itemView.findViewById(R.id.textNota);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    public int dpToPixel(int valorEmDp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (valorEmDp * density);
    }
}
