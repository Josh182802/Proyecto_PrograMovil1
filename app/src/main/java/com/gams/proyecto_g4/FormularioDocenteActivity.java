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

import com.gams.proyecto_g4.dao.DocenteDAO;
import com.gams.proyecto_g4.dao.UsuarioDAO;
import com.gams.proyecto_g4.model.Docente;

public class FormularioDocenteActivity extends AppCompatActivity {

    private TextView txtTitulo;

    private EditText edtUsuario;
    private EditText edtCorreo;
    private EditText edtContrasena;
    private EditText edtCodigo;
    private EditText edtNombres;
    private EditText edtApellidos;
    private EditText edtIdentidad;
    private EditText edtTelefono;
    private EditText edtEspecialidad;

    private Spinner spnEstado;

    private Button btnGuardar;
    private Button btnCancelar;

    private DocenteDAO docenteDAO;
    private UsuarioDAO usuarioDAO;

    private Docente docenteActual;
    private int idDocente = 0;

    private final String[] estados = {
            "ACTIVO",
            "INACTIVO",
            "SUSPENDIDO",
            "RETIRADO"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_docente);

        inicializarComponentes();
        configurarSpinner();

        docenteDAO = new DocenteDAO(this);
        usuarioDAO = new UsuarioDAO(this);

        idDocente = getIntent().getIntExtra(
                "id_docente",
                0
        );

        if (idDocente > 0) {
            cargarDocente();
        }

        btnGuardar.setOnClickListener(
                view -> guardarDocente()
        );

        btnCancelar.setOnClickListener(
                view -> finish()
        );
    }

    private void inicializarComponentes() {
        txtTitulo = findViewById(
                R.id.txtTituloFormularioDocente
        );

        edtUsuario = findViewById(
                R.id.edtUsuarioDocente
        );

        edtCorreo = findViewById(
                R.id.edtCorreoDocente
        );

        edtContrasena = findViewById(
                R.id.edtContrasenaDocente
        );

        edtCodigo = findViewById(
                R.id.edtCodigoDocente
        );

        edtNombres = findViewById(
                R.id.edtNombresDocente
        );

        edtApellidos = findViewById(
                R.id.edtApellidosDocente
        );

        edtIdentidad = findViewById(
                R.id.edtIdentidadDocente
        );

        edtTelefono = findViewById(
                R.id.edtTelefonoDocente
        );

        edtEspecialidad = findViewById(
                R.id.edtEspecialidadDocente
        );

        spnEstado = findViewById(
                R.id.spnEstadoDocente
        );

        btnGuardar = findViewById(
                R.id.btnGuardarDocente
        );

        btnCancelar = findViewById(
                R.id.btnCancelarDocente
        );
    }

    private void configurarSpinner() {
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

    private void cargarDocente() {
        docenteActual = docenteDAO.obtenerPorId(idDocente);

        if (docenteActual == null) {
            Toast.makeText(
                    this,
                    "No se encontró el docente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        txtTitulo.setText("Editar docente");

        edtCodigo.setText(
                docenteActual.getCodigoDocente()
        );

        edtNombres.setText(
                docenteActual.getNombres()
        );

        edtApellidos.setText(
                docenteActual.getApellidos()
        );

        edtIdentidad.setText(
                docenteActual.getNumeroIdentidad()
        );

        edtTelefono.setText(
                docenteActual.getTelefono()
        );

        edtEspecialidad.setText(
                docenteActual.getEspecialidad()
        );

        seleccionarEstado(
                docenteActual.getEstadoLaboral()
        );

        edtUsuario.setVisibility(View.GONE);
        edtCorreo.setVisibility(View.GONE);
        edtContrasena.setVisibility(View.GONE);
    }

    private void seleccionarEstado(String estado) {
        for (int i = 0; i < estados.length; i++) {
            if (estados[i].equals(estado)) {
                spnEstado.setSelection(i);
                break;
            }
        }
    }

    private void guardarDocente() {
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

        String codigo = edtCodigo
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

        String telefono = edtTelefono
                .getText()
                .toString()
                .trim();

        String especialidad = edtEspecialidad
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
                codigo,
                nombres,
                apellidos,
                identidad,
                telefono
        )) {
            return;
        }

        try {
            if (idDocente == 0) {
                registrarDocente(
                        usuario,
                        correo,
                        contrasena,
                        codigo,
                        nombres,
                        apellidos,
                        identidad,
                        telefono,
                        especialidad,
                        estado
                );
            } else {
                actualizarDocente(
                        codigo,
                        nombres,
                        apellidos,
                        identidad,
                        telefono,
                        especialidad,
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
                    "Ocurrió un error al guardar el docente",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private boolean validarDatos(
            String usuario,
            String correo,
            String contrasena,
            String codigo,
            String nombres,
            String apellidos,
            String identidad,
            String telefono
    ) {
        if (idDocente == 0) {
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

        if (codigo.isEmpty()) {
            edtCodigo.setError(
                    "El código es obligatorio"
            );

            edtCodigo.requestFocus();
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

        if (!telefono.isEmpty() && telefono.length() != 8) {
            edtTelefono.setError(
                    "El teléfono debe tener 8 dígitos"
            );

            edtTelefono.requestFocus();
            return false;
        }

        return true;
    }

    private void registrarDocente(
            String usuario,
            String correo,
            String contrasena,
            String codigo,
            String nombres,
            String apellidos,
            String identidad,
            String telefono,
            String especialidad,
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

        if (docenteDAO.existeCodigo(codigo)) {
            edtCodigo.setError(
                    "Ese código ya existe"
            );

            edtCodigo.requestFocus();
            return;
        }

        if (docenteDAO.existeIdentidad(identidad)) {
            edtIdentidad.setError(
                    "Esa identidad ya existe"
            );

            edtIdentidad.requestFocus();
            return;
        }

        Docente docente = new Docente(
                0,
                codigo,
                nombres,
                apellidos,
                identidad,
                telefono,
                especialidad,
                estado
        );

        long resultado = docenteDAO.insertar(
                docente,
                usuario,
                correo,
                contrasena
        );

        if (resultado != -1) {
            Toast.makeText(
                    this,
                    "Docente registrado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo registrar el docente",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void actualizarDocente(
            String codigo,
            String nombres,
            String apellidos,
            String identidad,
            String telefono,
            String especialidad,
            String estado
    ) {
        if (docenteActual == null) {
            return;
        }

        docenteActual.setCodigoDocente(codigo);
        docenteActual.setNombres(nombres);
        docenteActual.setApellidos(apellidos);
        docenteActual.setNumeroIdentidad(identidad);
        docenteActual.setTelefono(telefono);
        docenteActual.setEspecialidad(especialidad);
        docenteActual.setEstadoLaboral(estado);

        boolean resultado = docenteDAO.actualizar(
                docenteActual
        );

        if (resultado) {
            Toast.makeText(
                    this,
                    "Docente actualizado correctamente",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        } else {
            Toast.makeText(
                    this,
                    "No se pudo actualizar el docente",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}