package com.gams.proyecto_g4;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gams.proyecto_g4.dao.AsignaturaDAO;
import com.gams.proyecto_g4.dao.CarreraDAO;
import com.gams.proyecto_g4.dao.DocenteDAO;
import com.gams.proyecto_g4.model.Asignatura;
import com.gams.proyecto_g4.model.Carrera;
import com.gams.proyecto_g4.model.Docente;

import java.util.ArrayList;
import java.util.List;

public class FormularioAsignaturaActivity extends AppCompatActivity {

    private TextView txtTitulo;
    private Spinner spnCarrera;
    private Spinner spnDocente;

    private EditText edtCodigo;
    private EditText edtNombre;
    private EditText edtDescripcion;
    private EditText edtUnidades;

    private Button btnGuardar;
    private Button btnCancelar;

    private AsignaturaDAO asignaturaDAO;
    private CarreraDAO carreraDAO;
    private DocenteDAO docenteDAO;

    private List<Carrera> listaCarreras;
    private List<Docente> listaDocentes;

    private Asignatura asignaturaActual;
    private int idAsignatura = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_asignatura);

        inicializarComponentes();

        asignaturaDAO = new AsignaturaDAO(this);
        carreraDAO = new CarreraDAO(this);
        docenteDAO = new DocenteDAO(this);

        cargarSpinnerCarreras();
        cargarSpinnerDocentes();

        idAsignatura = getIntent().getIntExtra(
                "id_asignatura",
                0
        );

        if (idAsignatura > 0) {
            cargarAsignatura();
        }

        btnGuardar.setOnClickListener(
                view -> guardarAsignatura()
        );

        btnCancelar.setOnClickListener(
                view -> finish()
        );
    }

    private void inicializarComponentes() {
        txtTitulo = findViewById(
                R.id.txtTituloFormularioAsignatura
        );

        spnCarrera = findViewById(
                R.id.spnCarreraAsignatura
        );

        spnDocente = findViewById(
                R.id.spnDocenteAsignatura
        );

        edtCodigo = findViewById(
                R.id.edtCodigoAsignatura
        );

        edtNombre = findViewById(
                R.id.edtNombreAsignatura
        );

        edtDescripcion = findViewById(
                R.id.edtDescripcionAsignatura
        );

        edtUnidades = findViewById(
                R.id.edtUnidadesAsignatura
        );

        btnGuardar = findViewById(
                R.id.btnGuardarAsignatura
        );

        btnCancelar = findViewById(
                R.id.btnCancelarAsignatura
        );
    }

    private void cargarSpinnerCarreras() {
        listaCarreras = carreraDAO.listar();

        List<String> nombres = new ArrayList<>();

        for (Carrera carrera : listaCarreras) {
            if (carrera.isEstado()) {
                nombres.add(carrera.getNombre());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nombres
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spnCarrera.setAdapter(adapter);
    }

    private void cargarSpinnerDocentes() {
        listaDocentes = docenteDAO.listar();

        List<String> nombres = new ArrayList<>();
        nombres.add("Sin docente");

        for (Docente docente : listaDocentes) {
            if ("ACTIVO".equals(docente.getEstadoLaboral())) {
                nombres.add(docente.getNombreCompleto());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nombres
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spnDocente.setAdapter(adapter);
    }

    private void cargarAsignatura() {
        asignaturaActual =
                asignaturaDAO.obtenerPorId(idAsignatura);

        if (asignaturaActual == null) {
            Toast.makeText(
                    this,
                    "No se encontró la asignatura",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        txtTitulo.setText("Editar asignatura");

        edtCodigo.setText(
                asignaturaActual.getCodigoAsignatura()
        );

        edtNombre.setText(
                asignaturaActual.getNombre()
        );

        edtDescripcion.setText(
                asignaturaActual.getDescripcion()
        );

        edtUnidades.setText(
                String.valueOf(
                        asignaturaActual.getUnidadesValorativas()
                )
        );

        seleccionarCarrera(
                asignaturaActual.getIdCarrera()
        );

        seleccionarDocente(
                asignaturaActual.getIdDocente()
        );
    }

    private void seleccionarCarrera(int idCarrera) {
        int posicionVisible = 0;

        for (Carrera carrera : listaCarreras) {
            if (!carrera.isEstado()) {
                continue;
            }

            if (carrera.getIdCarrera() == idCarrera) {
                spnCarrera.setSelection(posicionVisible);
                return;
            }

            posicionVisible++;
        }
    }

    private void seleccionarDocente(Integer idDocente) {
        if (idDocente == null) {
            spnDocente.setSelection(0);
            return;
        }

        int posicionVisible = 1;

        for (Docente docente : listaDocentes) {
            if (!"ACTIVO".equals(docente.getEstadoLaboral())) {
                continue;
            }

            if (docente.getIdDocente() == idDocente) {
                spnDocente.setSelection(posicionVisible);
                return;
            }

            posicionVisible++;
        }
    }

    private void guardarAsignatura() {
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

        String unidadesTexto = edtUnidades
                .getText()
                .toString()
                .trim();

        if (!validarDatos(codigo, nombre, unidadesTexto)) {
            return;
        }

        Carrera carreraSeleccionada =
                obtenerCarreraSeleccionada();

        if (carreraSeleccionada == null) {
            Toast.makeText(
                    this,
                    "Debe registrar una carrera activa primero",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        Integer idDocente =
                obtenerIdDocenteSeleccionado();

        int unidades = Integer.parseInt(
                unidadesTexto
        );

        try {
            if (idAsignatura == 0) {
                registrarAsignatura(
                        carreraSeleccionada.getIdCarrera(),
                        idDocente,
                        codigo,
                        nombre,
                        descripcion,
                        unidades
                );
            } else {
                actualizarAsignatura(
                        carreraSeleccionada.getIdCarrera(),
                        idDocente,
                        codigo,
                        nombre,
                        descripcion,
                        unidades
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
                    "Ocurrió un error al guardar la asignatura",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private boolean validarDatos(
            String codigo,
            String nombre,
            String unidadesTexto
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

        if (unidadesTexto.isEmpty()) {
            edtUnidades.setError(
                    "Las unidades valorativas son obligatorias"
            );

            edtUnidades.requestFocus();
            return false;
        }

        int unidades;

        try {
            unidades = Integer.parseInt(
                    unidadesTexto
            );
        } catch (NumberFormatException e) {
            edtUnidades.setError(
                    "Ingrese un número válido"
            );

            edtUnidades.requestFocus();
            return false;
        }

        if (unidades <= 0) {
            edtUnidades.setError(
                    "Las unidades deben ser mayores que cero"
            );

            edtUnidades.requestFocus();
            return false;
        }

        return true;
    }

    private Carrera obtenerCarreraSeleccionada() {
        int posicionBuscada =
                spnCarrera.getSelectedItemPosition();

        int posicionVisible = 0;

        for (Carrera carrera : listaCarreras) {
            if (!carrera.isEstado()) {
                continue;
            }

            if (posicionVisible == posicionBuscada) {
                return carrera;
            }

            posicionVisible++;
        }

        return null;
    }

    private Integer obtenerIdDocenteSeleccionado() {
        int posicionBuscada =
                spnDocente.getSelectedItemPosition();

        if (posicionBuscada == 0) {
            return null;
        }

        int posicionVisible = 1;

        for (Docente docente : listaDocentes) {
            if (!"ACTIVO".equals(docente.getEstadoLaboral())) {
                continue;
            }

            if (posicionVisible == posicionBuscada) {
                return docente.getIdDocente();
            }

            posicionVisible++;
        }

        return null;
    }

    private void registrarAsignatura(
            int idCarrera,
            Integer idDocente,
            String codigo,
            String nombre,
            String descripcion,
            int unidades
    ) {
        if (asignaturaDAO.existeCodigo(codigo)) {
            edtCodigo.setError(
                    "Ese código ya existe"
            );

            edtCodigo.requestFocus();
            return;
        }

        if (asignaturaDAO.existeNombreEnCarrera(
                nombre,
                idCarrera
        )) {
            edtNombre.setError(
                    "Ese nombre ya existe en la carrera seleccionada"
            );

            edtNombre.requestFocus();
            return;
        }

        Asignatura asignatura = new Asignatura(
                idCarrera,
                idDocente,
                codigo,
                nombre,
                descripcion,
                unidades
        );

        long resultado = asignaturaDAO.insertar(
                asignatura
        );

        if (resultado != -1) {
            Toast.makeText(
                    this,
                    "Asignatura registrada correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo registrar la asignatura",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void actualizarAsignatura(
            int idCarrera,
            Integer idDocente,
            String codigo,
            String nombre,
            String descripcion,
            int unidades
    ) {
        if (asignaturaActual == null) {
            return;
        }

        asignaturaActual.setIdCarrera(idCarrera);
        asignaturaActual.setIdDocente(idDocente);
        asignaturaActual.setCodigoAsignatura(codigo);
        asignaturaActual.setNombre(nombre);
        asignaturaActual.setDescripcion(descripcion);
        asignaturaActual.setUnidadesValorativas(unidades);

        boolean resultado = asignaturaDAO.actualizar(
                asignaturaActual
        );

        if (resultado) {
            Toast.makeText(
                    this,
                    "Asignatura actualizada correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo actualizar la asignatura",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}