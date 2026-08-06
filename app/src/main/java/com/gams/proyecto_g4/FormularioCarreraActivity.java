package com.gams.proyecto_g4;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gams.proyecto_g4.dao.CarreraDAO;
import com.gams.proyecto_g4.model.Carrera;

public class FormularioCarreraActivity extends AppCompatActivity {

    private TextView txtTitulo;
    private EditText edtCodigo;
    private EditText edtNombre;
    private EditText edtDescripcion;
    private EditText edtDuracion;
    private Button btnGuardar;
    private Button btnCancelar;

    private CarreraDAO carreraDAO;
    private Carrera carreraActual;

    private int idCarrera = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_carrera);

        inicializarComponentes();

        carreraDAO = new CarreraDAO(this);

        idCarrera = getIntent().getIntExtra(
                "id_carrera",
                0
        );

        if (idCarrera > 0) {
            cargarCarrera();
        }

        btnGuardar.setOnClickListener(
                view -> guardarCarrera()
        );

        btnCancelar.setOnClickListener(
                view -> finish()
        );
    }

    private void inicializarComponentes() {
        txtTitulo = findViewById(
                R.id.txtTituloFormularioCarrera
        );

        edtCodigo = findViewById(
                R.id.edtCodigoCarrera
        );

        edtNombre = findViewById(
                R.id.edtNombreCarrera
        );

        edtDescripcion = findViewById(
                R.id.edtDescripcionCarrera
        );

        edtDuracion = findViewById(
                R.id.edtDuracionCarrera
        );

        btnGuardar = findViewById(
                R.id.btnGuardarCarrera
        );

        btnCancelar = findViewById(
                R.id.btnCancelarCarrera
        );
    }

    private void cargarCarrera() {
        carreraActual = carreraDAO.obtenerPorId(idCarrera);

        if (carreraActual == null) {
            Toast.makeText(
                    this,
                    "No se encontró la carrera",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        txtTitulo.setText("Editar carrera");

        edtCodigo.setText(
                carreraActual.getCodigoCarrera()
        );

        edtNombre.setText(
                carreraActual.getNombre()
        );

        edtDescripcion.setText(
                carreraActual.getDescripcion()
        );

        edtDuracion.setText(
                String.valueOf(
                        carreraActual.getDuracionAnios()
                )
        );
    }

    private void guardarCarrera() {
        String codigo = edtCodigo
                .getText()
                .toString()
                .trim()
                .toUpperCase();

        String nombre = edtNombre
                .getText()
                .toString()
                .trim();

        String descripcion = edtDescripcion
                .getText()
                .toString()
                .trim();

        String duracionTexto = edtDuracion
                .getText()
                .toString()
                .trim();

        if (!validarDatos(
                codigo,
                nombre,
                duracionTexto
        )) {
            return;
        }

        int duracion = Integer.parseInt(
                duracionTexto
        );

        try {
            if (idCarrera == 0) {
                registrarCarrera(
                        codigo,
                        nombre,
                        descripcion,
                        duracion
                );
            } else {
                actualizarCarrera(
                        codigo,
                        nombre,
                        descripcion,
                        duracion
                );
            }

        } catch (SQLiteConstraintException e) {
            Toast.makeText(
                    this,
                    "El código o el nombre ya están registrados",
                    Toast.LENGTH_LONG
            ).show();

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Ocurrió un error al guardar la carrera",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private boolean validarDatos(
            String codigo,
            String nombre,
            String duracionTexto
    ) {
        if (codigo.isEmpty()) {
            edtCodigo.setError(
                    "El código es obligatorio"
            );

            edtCodigo.requestFocus();
            return false;
        }

        if (nombre.isEmpty()) {
            edtNombre.setError(
                    "El nombre es obligatorio"
            );

            edtNombre.requestFocus();
            return false;
        }

        if (duracionTexto.isEmpty()) {
            edtDuracion.setError(
                    "La duración es obligatoria"
            );

            edtDuracion.requestFocus();
            return false;
        }

        int duracion;

        try {
            duracion = Integer.parseInt(
                    duracionTexto
            );
        } catch (NumberFormatException e) {
            edtDuracion.setError(
                    "Ingrese una duración válida"
            );

            edtDuracion.requestFocus();
            return false;
        }

        if (duracion <= 0) {
            edtDuracion.setError(
                    "La duración debe ser mayor que cero"
            );

            edtDuracion.requestFocus();
            return false;
        }

        return true;
    }

    private void registrarCarrera(
            String codigo,
            String nombre,
            String descripcion,
            int duracion
    ) {
        if (carreraDAO.existeCodigo(codigo)) {
            edtCodigo.setError(
                    "Ese código ya está registrado"
            );

            edtCodigo.requestFocus();
            return;
        }

        if (carreraDAO.existeNombre(nombre)) {
            edtNombre.setError(
                    "Ese nombre ya está registrado"
            );

            edtNombre.requestFocus();
            return;
        }

        Carrera carrera = new Carrera(
                codigo,
                nombre,
                descripcion,
                duracion
        );

        long resultado = carreraDAO.insertar(
                carrera
        );

        if (resultado != -1) {
            Toast.makeText(
                    this,
                    "Carrera registrada correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo registrar la carrera",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void actualizarCarrera(
            String codigo,
            String nombre,
            String descripcion,
            int duracion
    ) {
        if (carreraActual == null) {
            Toast.makeText(
                    this,
                    "No se encontró la carrera",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        carreraActual.setCodigoCarrera(codigo);
        carreraActual.setNombre(nombre);
        carreraActual.setDescripcion(descripcion);
        carreraActual.setDuracionAnios(duracion);

        boolean resultado = carreraDAO.actualizar(
                carreraActual
        );

        if (resultado) {
            Toast.makeText(
                    this,
                    "Carrera actualizada correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo actualizar la carrera",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}