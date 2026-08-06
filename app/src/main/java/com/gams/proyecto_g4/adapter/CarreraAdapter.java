package com.gams.proyecto_g4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gams.proyecto_g4.R;
import com.gams.proyecto_g4.model.Carrera;

import java.util.List;

public class CarreraAdapter
        extends RecyclerView.Adapter<CarreraAdapter.CarreraViewHolder> {

    public interface OnCarreraAccionListener {
        void onEditar(Carrera carrera);
        void onCambiarEstado(Carrera carrera);
    }

    private List<Carrera> listaCarreras;
    private final OnCarreraAccionListener listener;

    public CarreraAdapter(
            List<Carrera> listaCarreras,
            OnCarreraAccionListener listener
    ) {
        this.listaCarreras = listaCarreras;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarreraViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View vista = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_carrera,
                        parent,
                        false
                );

        return new CarreraViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CarreraViewHolder holder,
            int position
    ) {
        Carrera carrera = listaCarreras.get(position);

        holder.txtNombre.setText(carrera.getNombre());

        holder.txtCodigo.setText(
                "Código: " + carrera.getCodigoCarrera()
        );

        String descripcion = carrera.getDescripcion();

        if (descripcion == null || descripcion.trim().isEmpty()) {
            descripcion = "Sin descripción";
        }

        holder.txtDescripcion.setText(
                "Descripción: " + descripcion
        );

        holder.txtDuracion.setText(
                "Duración: "
                        + carrera.getDuracionAnios()
                        + " años"
        );

        holder.txtEstado.setText(
                "Estado: "
                        + (carrera.isEstado()
                        ? "Activa"
                        : "Inactiva")
        );

        holder.btnEstado.setText(
                carrera.isEstado()
                        ? "Desactivar"
                        : "Activar"
        );

        holder.btnEditar.setOnClickListener(
                view -> listener.onEditar(carrera)
        );

        holder.btnEstado.setOnClickListener(
                view -> listener.onCambiarEstado(carrera)
        );
    }

    @Override
    public int getItemCount() {
        return listaCarreras == null
                ? 0
                : listaCarreras.size();
    }

    public void actualizarLista(List<Carrera> nuevaLista) {
        listaCarreras = nuevaLista;
        notifyDataSetChanged();
    }

    public static class CarreraViewHolder
            extends RecyclerView.ViewHolder {

        private final TextView txtNombre;
        private final TextView txtCodigo;
        private final TextView txtDescripcion;
        private final TextView txtDuracion;
        private final TextView txtEstado;
        private final Button btnEditar;
        private final Button btnEstado;

        public CarreraViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            txtNombre = itemView.findViewById(
                    R.id.txtNombreCarreraItem
            );

            txtCodigo = itemView.findViewById(
                    R.id.txtCodigoCarreraItem
            );

            txtDescripcion = itemView.findViewById(
                    R.id.txtDescripcionCarreraItem
            );

            txtDuracion = itemView.findViewById(
                    R.id.txtDuracionCarreraItem
            );

            txtEstado = itemView.findViewById(
                    R.id.txtEstadoCarreraItem
            );

            btnEditar = itemView.findViewById(
                    R.id.btnEditarCarreraItem
            );

            btnEstado = itemView.findViewById(
                    R.id.btnEstadoCarreraItem
            );
        }
    }
}