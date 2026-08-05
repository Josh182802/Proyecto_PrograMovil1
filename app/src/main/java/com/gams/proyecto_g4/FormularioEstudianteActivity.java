package com.gams.proyecto_g4;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gams.proyecto_g4.dao.CarreraDAO;
import com.gams.proyecto_g4.dao.EstudianteDAO;
import com.gams.proyecto_g4.dao.UsuarioDAO;
import com.gams.proyecto_g4.model.Carrera;
import com.gams.proyecto_g4.model.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class FormularioEstudianteActivity extends AppCompatActivity {

    private TextView txtTitulo;

    private EditText edtUsuario;
    private EditText edtCorreo;
    private EditText edtContrasena;
    private EditText edtCuenta;
    private EditText edtNombres;
    private EditText edtApellidos;
    private EditText edtIdentidad;
    private EditText edtFechaNacimiento;
    private EditText edtDireccion;
    private EditText edtTelefono;

    private Spinner spnCarrera;
    private Spinner spnEstado;

    private Button btnGuardar;
    private Button btnCancelar;

    private EstudianteDAO estudianteDAO;
    private UsuarioDAO usuarioDAO;
    private CarreraDAO carreraDAO;

    private List<Carrera> listaCarrerasActivas;

    private Estudiante estudianteActual;
    private int idEstudiante = 0;

    private final String[] estados = {
            "ACTIVO",
            "INACTIVO",
            "GRADUADO",
            "SUSPENDIDO",
            "RETIRADO"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_estudiante);

        inicializarComponentes();

        estudianteDAO = new EstudianteDAO(this);
        usuarioDAO = new UsuarioDAO(this);
        carreraDAO = new CarreraDAO(this);

        configurarSpinnerEstados();
        cargarSpinnerCarreras();

        idEstudiante = getIntent().getIntExtra(
                "id_estudiante",
                0
        );

        if (idEstudiante > 0) {
            cargarEstudiante();
        }

        btnGuardar.setOnClickListener(
                view -> guardarEstudiante()
        );

        btnCancelar.setOnClickListener(
                view -> finish()
        );
    }

    private void inicializarComponentes() {
        txtTitulo = findViewById(
                R.id.txtTituloFormularioEstudiante
        );

        edtUsuario = findViewById(
                R.id.edtUsuarioEstudiante
        );

        edtCorreo = findViewById(
                R.id.edtCorreoEstudiante
        );

        edtContrasena = findViewById(
                R.id.edtContrasenaEstudiante
        );

        spnCarrera = findViewById(
                R.id.spnCarreraEstudiante
        );

        edtCuenta = findViewById(
                R.id.edtCuentaEstudiante
        );

        edtNombres = findViewById(
                R.id.edtNombresEstudiante
        );

        edtApellidos = findViewById(
                R.id.edtApellidosEstudiante
        );

        edtIdentidad = findViewById(
                R.id.edtIdentidadEstudiante
        );

        edtFechaNacimiento = findViewById(
                R.id.edtFechaNacimientoEstudiante
        );

        edtDireccion = findViewById(
                R.id.edtDireccionEstudiante
        );

        edtTelefono = findViewById(
                R.id.edtTelefonoEstudiante
        );

        spnEstado = findViewById(
                R.id.spnEstadoEstudiante
        );

        btnGuardar = findViewById(
                R.id.btnGuardarEstudiante
        );

        btnCancelar = findViewById(
                R.id.btnCancelarEstudiante
        );
    }

    private void configurarSpinnerEstados() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                estados
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spnEstado.setAdapter(adapter);
    }

    private void cargarSpinnerCarreras() {
        List<Carrera> todasLasCarreras = carreraDAO.listar();

        listaCarrerasActivas = new ArrayList<>();
        List<String> nombresCarreras = new ArrayList<>();

        for (Carrera carrera : todasLasCarreras) {
            if (carrera.isEstado()) {
                listaCarrerasActivas.add(carrera);
                nombresCarreras.add(carrera.getNombre());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nombresCarreras
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spnCarrera.setAdapter(adapter);
    }

    private void cargarEstudiante() {
        estudianteActual = estudianteDAO.obtenerPorId(
                idEstudiante
        );

        if (estudianteActual == null) {
            Toast.makeText(
                    this,
                    "No se encontró el estudiante",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        txtTitulo.setText("Editar estudiante");

        // Los datos del usuario no se editan en este formulario.
        edtUsuario.setVisibility(View.GONE);
        edtCorreo.setVisibility(View.GONE);
        edtContrasena.setVisibility(View.GONE);

        edtCuenta.setText(
                estudianteActual.getNumeroCuenta()
        );

        edtNombres.setText(
                estudianteActual.getNombres()
        );

        edtApellidos.setText(
                estudianteActual.getApellidos()
        );

        edtIdentidad.setText(
                estudianteActual.getNumeroIdentidad()
        );

        edtFechaNacimiento.setText(
                estudianteActual.getFechaNacimiento()
        );

        edtDireccion.setText(
                estudianteActual.getDireccion()
        );

        edtTelefono.setText(
                estudianteActual.getTelefono()
        );

        seleccionarCarrera(
                estudianteActual.getIdCarrera()
        );

        seleccionarEstado(
                estudianteActual.getEstadoAcademico()
        );
    }

    private void seleccionarCarrera(int idCarrera) {
        for (int i = 0; i < listaCarrerasActivas.size(); i++) {
            Carrera carrera = listaCarrerasActivas.get(i);

            if (carrera.getIdCarrera() == idCarrera) {
                spnCarrera.setSelection(i);
                return;
            }
        }
    }

    private void seleccionarEstado(String estado) {
        for (int i = 0; i < estados.length; i++) {
            if (estados[i].equals(estado)) {
                spnEstado.setSelection(i);
                return;
            }
        }
    }

    private void guardarEstudiante() {
        String usuario = edtUsuario
                .getText()
                .toString()
                .trim();

        String correo = edtCorreo
                .getText()
                .toString()
                .trim();

        String contrasena = edtContrasena
                .getText()
                .toString()
                .trim();

        String cuenta = edtCuenta
                .getText()
                .toString()
                .trim()
                .toUpperCase();

        String nombres = edtNombres
                .getText()
                .toString()
                .trim();

        String apellidos = edtApellidos
                .getText()
                .toString()
                .trim();

        String identidad = edtIdentidad
                .getText()
                .toString()
                .trim();

        String fechaNacimiento = edtFechaNacimiento
                .getText()
                .toString()
                .trim();

        String direccion = edtDireccion
                .getText()
                .toString()
                .trim();

        String telefono = edtTelefono
                .getText()
                .toString()
                .trim();

        String estado = spnEstado
                .getSelectedItem()
                .toString();

        if (!validarDatos(
                usuario,
                correo,
                contrasena,
                cuenta,
                nombres,
                apellidos,
                identidad,
                fechaNacimiento,
                telefono
        )) {
            return;
        }

        if (listaCarrerasActivas.isEmpty()) {
            Toast.makeText(
                    this,
                    "Debe registrar una carrera activa primero",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        int posicionCarrera =
                spnCarrera.getSelectedItemPosition();

        Carrera carreraSeleccionada =
                listaCarrerasActivas.get(posicionCarrera);

        try {
            if (idEstudiante == 0) {
                registrarEstudiante(
                        usuario,
                        correo,
                        contrasena,
                        carreraSeleccionada.getIdCarrera(),
                        cuenta,
                        nombres,
                        apellidos,
                        identidad,
                        fechaNacimiento,
                        direccion,
                        telefono,
                        estado
                );
            } else {
                actualizarEstudiante(
                        carreraSeleccionada.getIdCarrera(),
                        cuenta,
                        nombres,
                        apellidos,
                        identidad,
                        fechaNacimiento,
                        direccion,
                        telefono,
                        estado
                );
            }

        } catch (SQLiteConstraintException e) {
            Toast.makeText(
                    this,
                    "Ya existe un registro con esos datos",
                    Toast.LENGTH_LONG
            ).show();

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Ocurrió un error al guardar el estudiante",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private boolean validarDatos(
            String usuario,
            String correo,
            String contrasena,
            String cuenta,
            String nombres,
            String apellidos,
            String identidad,
            String fechaNacimiento,
            String telefono
    ) {
        if (idEstudiante == 0) {
            if (usuario.isEmpty()) {
                edtUsuario.setError(
                        "El usuario es obligatorio"
                );
                edtUsuario.requestFocus();
                return false;
            }

            if (correo.isEmpty()) {
                edtCorreo.setError(
                        "El correo es obligatorio"
                );
                edtCorreo.requestFocus();
                return false;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                edtCorreo.setError(
                        "Ingrese un correo válido"
                );
                edtCorreo.requestFocus();
                return false;
            }

            if (contrasena.isEmpty()) {
                edtContrasena.setError(
                        "La contraseña es obligatoria"
                );
                edtContrasena.requestFocus();
                return false;
            }

            if (contrasena.length() < 4) {
                edtContrasena.setError(
                        "La contraseña debe tener al menos 4 caracteres"
                );
                edtContrasena.requestFocus();
                return false;
            }
        }

        if (cuenta.isEmpty()) {
            edtCuenta.setError(
                    "El número de cuenta es obligatorio"
            );
            edtCuenta.requestFocus();
            return false;
        }

        if (nombres.isEmpty()) {
            edtNombres.setError(
                    "Los nombres son obligatorios"
            );
            edtNombres.requestFocus();
            return false;
        }

        if (apellidos.isEmpty()) {
            edtApellidos.setError(
                    "Los apellidos son obligatorios"
            );
            edtApellidos.requestFocus();
            return false;
        }

        if (identidad.isEmpty()) {
            edtIdentidad.setError(
                    "La identidad es obligatoria"
            );
            edtIdentidad.requestFocus();
            return false;
        }

        if (identidad.length() != 13) {
            edtIdentidad.setError(
                    "La identidad debe tener 13 dígitos"
            );
            edtIdentidad.requestFocus();
            return false;
        }

        if (!fechaNacimiento.isEmpty()
                && !fechaNacimiento.matches(
                "\\d{4}-\\d{2}-\\d{2}"
        )) {
            edtFechaNacimiento.setError(
                    "Use el formato AAAA-MM-DD"
            );
            edtFechaNacimiento.requestFocus();
            return false;
        }

        if (!telefono.isEmpty() && telefono.length() != 8) {
            edtTelefono.setError(
                    "El teléfono debe tener 8 dígitos"
            );
            edtTelefono.requestFocus();
            return false;
        }

        return true;
    }

    private void registrarEstudiante(
            String usuario,
            String correo,
            String contrasena,
            int idCarrera,
            String cuenta,
            String nombres,
            String apellidos,
            String identidad,
            String fechaNacimiento,
            String direccion,
            String telefono,
            String estado
    ) {
        if (usuarioDAO.existeNombreUsuario(usuario)) {
            edtUsuario.setError(
                    "Ese usuario ya existe"
            );
            edtUsuario.requestFocus();
            return;
        }

        if (usuarioDAO.existeCorreo(correo)) {
            edtCorreo.setError(
                    "Ese correo ya existe"
            );
            edtCorreo.requestFocus();
            return;
        }

        if (estudianteDAO.existeNumeroCuenta(cuenta)) {
            edtCuenta.setError(
                    "Ese número de cuenta ya existe"
            );
            edtCuenta.requestFocus();
            return;
        }

        if (estudianteDAO.existeIdentidad(identidad)) {
            edtIdentidad.setError(
                    "Esa identidad ya existe"
            );
            edtIdentidad.requestFocus();
            return;
        }

        Estudiante estudiante = new Estudiante(
                idCarrera,
                cuenta,
                nombres,
                apellidos,
                identidad,
                fechaNacimiento,
                direccion,
                telefono,
                estado
        );

        long resultado = estudianteDAO.insertar(
                estudiante,
                usuario,
                correo,
                contrasena
        );

        if (resultado != -1) {
            Toast.makeText(
                    this,
                    "Estudiante registrado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo registrar el estudiante",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void actualizarEstudiante(
            int idCarrera,
            String cuenta,
            String nombres,
            String apellidos,
            String identidad,
            String fechaNacimiento,
            String direccion,
            String telefono,
            String estado
    ) {
        if (estudianteActual == null) {
            return;
        }

        estudianteActual.setIdCarrera(idCarrera);
        estudianteActual.setNumeroCuenta(cuenta);
        estudianteActual.setNombres(nombres);
        estudianteActual.setApellidos(apellidos);
        estudianteActual.setNumeroIdentidad(identidad);
        estudianteActual.setFechaNacimiento(fechaNacimiento);
        estudianteActual.setDireccion(direccion);
        estudianteActual.setTelefono(telefono);
        estudianteActual.setEstadoAcademico(estado);

        boolean resultado = estudianteDAO.actualizar(
                estudianteActual
        );

        if (resultado) {
            Toast.makeText(
                    this,
                    "Estudiante actualizado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo actualizar el estudiante",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}