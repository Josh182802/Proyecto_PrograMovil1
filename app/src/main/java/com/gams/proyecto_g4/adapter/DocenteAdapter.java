package com.gams.proyecto_g4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gams.proyecto_g4.R;
import com.gams.proyecto_g4.model.Docente;

import java.util.List;

public class DocenteAdapter
        extends RecyclerView.Adapter<DocenteAdapter.DocenteViewHolder> {

    public interface OnDocenteAccionListener {
        void onEditar(Docente docente);
        void onCambiarEstado(Docente docente);
    }

    private List<Docente> listaDocentes;
    private final OnDocenteAccionListener listener;

    public DocenteAdapter(
            List<Docente> listaDocentes,
            OnDocenteAccionListener listener
    ) {
        this.listaDocentes = listaDocentes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DocenteViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View vista = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_docente,
                        parent,
                        false
                );

        return new DocenteViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull DocenteViewHolder holder,
            int position
    ) {
        Docente docente = listaDocentes.get(position);

        holder.txtNombre.setText(
                docente.getNombreCompleto()
        );

        holder.txtCodigo.setText(
                "Código: " + docente.getCodigoDocente()
        );

        holder.txtIdentidad.setText(
                "Identidad: " + docente.getNumeroIdentidad()
        );

        String telefono = docente.getTelefono();

        if (telefono == null || telefono.trim().isEmpty()) {
            telefono = "Sin teléfono";
        }

        holder.txtTelefono.setText(
                "Teléfono: " + telefono
        );

        String especialidad = docente.getEspecialidad();

        if (especialidad == null || especialidad.trim().isEmpty()) {
            especialidad = "Sin especialidad";
        }

        holder.txtEspecialidad.setText(
                "Especialidad: " + especialidad
        );

        holder.txtEstado.setText(
                "Estado: " + docente.getEstadoLaboral()
        );

        holder.btnEstado.setText(
                "ACTIVO".equals(docente.getEstadoLaboral())
                        ? "Desactivar"
                        : "Activar"
        );

        holder.btnEditar.setOnClickListener(
                view -> listener.onEditar(docente)
        );

        holder.btnEstado.setOnClickListener(
                view -> listener.onCambiarEstado(docente)
        );
    }

    @Override
    public int getItemCount() {
        return listaDocentes == null
                ? 0
                : listaDocentes.size();
    }

    public void actualizarLista(List<Docente> nuevaLista) {
        listaDocentes = nuevaLista;
        notifyDataSetChanged();
    }

    public static class DocenteViewHolder
            extends RecyclerView.ViewHolder {

        private final TextView txtNombre;
        private final TextView txtCodigo;
        private final TextView txtIdentidad;
        private final TextView txtTelefono;
        private final TextView txtEspecialidad;
        private final TextView txtEstado;

        private final Button btnEditar;
        private final Button btnEstado;

        public DocenteViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            txtNombre = itemView.findViewById(
                    R.id.txtNombreDocenteItem
            );

            txtCodigo = itemView.findViewById(
                    R.id.txtCodigoDocenteItem
            );

            txtIdentidad = itemView.findViewById(
                    R.id.txtIdentidadDocenteItem
            );

            txtTelefono = itemView.findViewById(
                    R.id.txtTelefonoDocenteItem
            );

            txtEspecialidad = itemView.findViewById(
                    R.id.txtEspecialidadDocenteItem
            );

            txtEstado = itemView.findViewById(
                    R.id.txtEstadoDocenteItem
            );

            btnEditar = itemView.findViewById(
                    R.id.btnEditarDocenteItem
            );

            btnEstado = itemView.findViewById(
                    R.id.btnEstadoDocenteItem
            );
        }
    }
}