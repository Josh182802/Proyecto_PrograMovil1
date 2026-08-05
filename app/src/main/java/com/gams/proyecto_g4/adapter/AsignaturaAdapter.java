package com.gams.proyecto_g4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gams.proyecto_g4.R;
import com.gams.proyecto_g4.model.Asignatura;

import java.util.List;

public class AsignaturaAdapter
        extends RecyclerView.Adapter<AsignaturaAdapter.AsignaturaViewHolder> {

    public interface OnAsignaturaAccionListener {
        void onEditar(Asignatura asignatura);
        void onCambiarEstado(Asignatura asignatura);
    }

    private List<Asignatura> listaAsignaturas;
    private final OnAsignaturaAccionListener listener;

    public AsignaturaAdapter(
            List<Asignatura> listaAsignaturas,
            OnAsignaturaAccionListener listener
    ) {
        this.listaAsignaturas = listaAsignaturas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AsignaturaViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View vista = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_asignatura,
                        parent,
                        false
                );

        return new AsignaturaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull AsignaturaViewHolder holder,
            int position
    ) {
        Asignatura asignatura = listaAsignaturas.get(position);

        holder.txtNombre.setText(
                asignatura.getNombre()
        );

        holder.txtCodigo.setText(
                "Código: " + asignatura.getCodigoAsignatura()
        );

        String descripcion = asignatura.getDescripcion();

        if (descripcion == null || descripcion.trim().isEmpty()) {
            descripcion = "Sin descripción";
        }

        holder.txtDescripcion.setText(
                "Descripción: " + descripcion
        );

        holder.txtCarrera.setText(
                "Carrera: " + asignatura.getNombreCarrera()
        );

        String docente = asignatura.getNombreDocente();

        if (docente == null || docente.trim().isEmpty()) {
            docente = "Sin docente";
        }

        holder.txtDocente.setText(
                "Docente: " + docente
        );

        holder.txtUnidades.setText(
                "Unidades valorativas: "
                        + asignatura.getUnidadesValorativas()
        );

        holder.txtEstado.setText(
                "Estado: "
                        + (asignatura.isEstado()
                        ? "Activa"
                        : "Inactiva")
        );

        holder.btnEstado.setText(
                asignatura.isEstado()
                        ? "Desactivar"
                        : "Activar"
        );

        holder.btnEditar.setOnClickListener(
                view -> listener.onEditar(asignatura)
        );

        holder.btnEstado.setOnClickListener(
                view -> listener.onCambiarEstado(asignatura)
        );
    }

    @Override
    public int getItemCount() {
        return listaAsignaturas == null
                ? 0
                : listaAsignaturas.size();
    }

    public void actualizarLista(
            List<Asignatura> nuevaLista
    ) {
        listaAsignaturas = nuevaLista;
        notifyDataSetChanged();
    }

    public static class AsignaturaViewHolder
            extends RecyclerView.ViewHolder {

        private final TextView txtNombre;
        private final TextView txtCodigo;
        private final TextView txtDescripcion;
        private final TextView txtCarrera;
        private final TextView txtDocente;
        private final TextView txtUnidades;
        private final TextView txtEstado;

        private final Button btnEditar;
        private final Button btnEstado;

        public AsignaturaViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            txtNombre = itemView.findViewById(
                    R.id.txtNombreAsignaturaItem
            );

            txtCodigo = itemView.findViewById(
                    R.id.txtCodigoAsignaturaItem
            );

            txtDescripcion = itemView.findViewById(
                    R.id.txtDescripcionAsignaturaItem
            );

            txtCarrera = itemView.findViewById(
                    R.id.txtCarreraAsignaturaItem
            );

            txtDocente = itemView.findViewById(
                    R.id.txtDocenteAsignaturaItem
            );

            txtUnidades = itemView.findViewById(
                    R.id.txtUnidadesAsignaturaItem
            );

            txtEstado = itemView.findViewById(
                    R.id.txtEstadoAsignaturaItem
            );

            btnEditar = itemView.findViewById(
                    R.id.btnEditarAsignaturaItem
            );

            btnEstado = itemView.findViewById(
                    R.id.btnEstadoAsignaturaItem
            );
        }
    }
}