package com.gams.proyecto_g4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gams.proyecto_g4.R;
import com.gams.proyecto_g4.model.Estudiante;

import java.util.List;

public class EstudianteAdapter
        extends RecyclerView.Adapter<EstudianteAdapter.EstudianteViewHolder> {

    public interface OnEstudianteAccionListener {
        void onEditar(Estudiante estudiante);
        void onCambiarEstado(Estudiante estudiante);
    }

    private List<Estudiante> listaEstudiantes;
    private final OnEstudianteAccionListener listener;

    public EstudianteAdapter(
            List<Estudiante> listaEstudiantes,
            OnEstudianteAccionListener listener
    ) {
        this.listaEstudiantes = listaEstudiantes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EstudianteViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View vista = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_estudiante,
                        parent,
                        false
                );

        return new EstudianteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull EstudianteViewHolder holder,
            int position
    ) {
        Estudiante estudiante = listaEstudiantes.get(position);

        holder.txtNombre.setText(
                estudiante.getNombreCompleto()
        );

        holder.txtCuenta.setText(
                "Cuenta: " + estudiante.getNumeroCuenta()
        );

        holder.txtIdentidad.setText(
                "Identidad: " + estudiante.getNumeroIdentidad()
        );

        holder.txtCarrera.setText(
                "Carrera: " + estudiante.getNombreCarrera()
        );

        String telefono = estudiante.getTelefono();

        if (telefono == null || telefono.trim().isEmpty()) {
            telefono = "Sin teléfono";
        }

        holder.txtTelefono.setText(
                "Teléfono: " + telefono
        );

        String fechaIngreso = estudiante.getFechaIngreso();

        if (fechaIngreso == null || fechaIngreso.trim().isEmpty()) {
            fechaIngreso = "No disponible";
        }

        holder.txtIngreso.setText(
                "Fecha de ingreso: " + fechaIngreso
        );

        holder.txtEstado.setText(
                "Estado: " + estudiante.getEstadoAcademico()
        );

        holder.btnEstado.setText(
                "ACTIVO".equals(estudiante.getEstadoAcademico())
                        ? "Desactivar"
                        : "Activar"
        );

        holder.btnEditar.setOnClickListener(
                view -> listener.onEditar(estudiante)
        );

        holder.btnEstado.setOnClickListener(
                view -> listener.onCambiarEstado(estudiante)
        );
    }

    @Override
    public int getItemCount() {
        return listaEstudiantes == null
                ? 0
                : listaEstudiantes.size();
    }

    public void actualizarLista(
            List<Estudiante> nuevaLista
    ) {
        listaEstudiantes = nuevaLista;
        notifyDataSetChanged();
    }

    public static class EstudianteViewHolder
            extends RecyclerView.ViewHolder {

        private final TextView txtNombre;
        private final TextView txtCuenta;
        private final TextView txtIdentidad;
        private final TextView txtCarrera;
        private final TextView txtTelefono;
        private final TextView txtIngreso;
        private final TextView txtEstado;

        private final Button btnEditar;
        private final Button btnEstado;

        public EstudianteViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            txtNombre = itemView.findViewById(
                    R.id.txtNombreEstudianteItem
            );

            txtCuenta = itemView.findViewById(
                    R.id.txtCuentaEstudianteItem
            );

            txtIdentidad = itemView.findViewById(
                    R.id.txtIdentidadEstudianteItem
            );

            txtCarrera = itemView.findViewById(
                    R.id.txtCarreraEstudianteItem
            );

            txtTelefono = itemView.findViewById(
                    R.id.txtTelefonoEstudianteItem
            );

            txtIngreso = itemView.findViewById(
                    R.id.txtIngresoEstudianteItem
            );

            txtEstado = itemView.findViewById(
                    R.id.txtEstadoEstudianteItem
            );

            btnEditar = itemView.findViewById(
                    R.id.btnEditarEstudianteItem
            );

            btnEstado = itemView.findViewById(
                    R.id.btnEstadoEstudianteItem
            );
        }
    }
}