package com.gams.proyecto_g4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gams.proyecto_g4.R;
import com.gams.proyecto_g4.model.Calificacion;
import java.util.List;

public class CalificacionAdapter extends RecyclerView.Adapter<CalificacionAdapter.CalificacionViewHolder> {
    public interface OnCalificacionAccionListener {
        void onEditarNotas(Calificacion calificacion);
    }

    private List<Calificacion> listaCalificaciones;
    private final OnCalificacionAccionListener listener;

    public CalificacionAdapter(List<Calificacion> listaCalificaciones, OnCalificacionAccionListener listener) {
        this.listaCalificaciones = listaCalificaciones;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CalificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calificacion, parent, false);
        return new CalificacionViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CalificacionViewHolder holder, int position) {
        Calificacion c = listaCalificaciones.get(position);
        holder.txtEstudiante.setText(c.getEstudianteNombre() + " (" + c.getNumeroCuenta() + ")");
        holder.txtAsignatura.setText("Asignatura: " + c.getAsignaturaNombre());

        String notaFinal = (c.getNotaFinal() != null) ? String.format("%.2f", c.getNotaFinal()) : "N/A";
        holder.txtNotaFinal.setText("Nota Final: " + notaFinal);
        holder.txtResultado.setText("Estado: " + c.getResultado());

        holder.btnEditar.setOnClickListener(v -> listener.onEditarNotas(c));
    }

    @Override
    public int getItemCount() { return listaCalificaciones != null ? listaCalificaciones.size() : 0; }

    public void actualizarLista(List<Calificacion> nuevaLista) {
        listaCalificaciones = nuevaLista;
        notifyDataSetChanged();
    }

    public static class CalificacionViewHolder extends RecyclerView.ViewHolder {
        TextView txtEstudiante, txtAsignatura, txtNotaFinal, txtResultado;
        Button btnEditar;

        public CalificacionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEstudiante = itemView.findViewById(R.id.txtEstudianteCalificacionItem);
            txtAsignatura = itemView.findViewById(R.id.txtAsignaturaCalificacionItem);
            txtNotaFinal = itemView.findViewById(R.id.txtNotaFinalCalificacionItem);
            txtResultado = itemView.findViewById(R.id.txtResultadoCalificacionItem);
            btnEditar = itemView.findViewById(R.id.btnEditarCalificacionItem);
        }
    }
}