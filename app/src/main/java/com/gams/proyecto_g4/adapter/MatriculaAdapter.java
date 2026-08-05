package com.gams.proyecto_g4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gams.proyecto_g4.R;
import com.gams.proyecto_g4.model.Matricula;
import java.util.List;

public class MatriculaAdapter extends RecyclerView.Adapter<MatriculaAdapter.MatriculaViewHolder> {
    private List<Matricula> listaMatriculas;

    public MatriculaAdapter(List<Matricula> listaMatriculas) {
        this.listaMatriculas = listaMatriculas;
    }

    @NonNull
    @Override
    public MatriculaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matricula, parent, false);
        return new MatriculaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MatriculaViewHolder holder, int position) {
        Matricula m = listaMatriculas.get(position);
        holder.txtEstudiante.setText(m.getNombreEstudiante() + " (" + m.getNumeroCuenta() + ")");
        holder.txtPeriodo.setText("Período: " + m.getNombrePeriodo());
        holder.txtFecha.setText("Fecha: " + m.getFechaMatricula());
        holder.txtEstado.setText("Estado: " + m.getEstado());
    }

    @Override
    public int getItemCount() { return listaMatriculas != null ? listaMatriculas.size() : 0; }

    public void actualizarLista(List<Matricula> nuevaLista) {
        listaMatriculas = nuevaLista;
        notifyDataSetChanged();
    }

    public static class MatriculaViewHolder extends RecyclerView.ViewHolder {
        TextView txtEstudiante, txtPeriodo, txtFecha, txtEstado;
        public MatriculaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEstudiante = itemView.findViewById(R.id.txtEstudianteMatriculaItem);
            txtPeriodo = itemView.findViewById(R.id.txtPeriodoMatriculaItem);
            txtFecha = itemView.findViewById(R.id.txtFechaMatriculaItem);
            txtEstado = itemView.findViewById(R.id.txtEstadoMatriculaItem);
        }
    }
}