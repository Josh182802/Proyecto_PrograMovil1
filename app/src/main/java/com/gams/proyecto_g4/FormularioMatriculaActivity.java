package com.gams.proyecto_g4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.gams.proyecto_g4.dao.EstudianteDAO;
import com.gams.proyecto_g4.dao.MatriculaDAO;
import com.gams.proyecto_g4.database.DatabaseHelper;
import com.gams.proyecto_g4.model.Estudiante;
import com.gams.proyecto_g4.model.Matricula;

import java.util.ArrayList;
import java.util.List;

public class FormularioMatriculaActivity extends AppCompatActivity {
    private Spinner spnEstudiantes, spnPeriodos, spnAsignaturas;
    private EditText edtObservaciones;
    private Button btnGuardar, btnCancelar;

    private EstudianteDAO estudianteDAO;
    private MatriculaDAO matriculaDAO;
    private DatabaseHelper dbHelper;

    private List<Estudiante> listaEstudiantes;
    private List<Integer> listaIdsPeriodos = new ArrayList<>();
    private List<Integer> listaIdsAsignaturas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_matricula);

        spnEstudiantes = findViewById(R.id.spnEstudiantesMatricula);
        spnPeriodos = findViewById(R.id.spnPeriodoMatricula);
        spnAsignaturas = findViewById(R.id.spnAsignaturaMatricula);
        edtObservaciones = findViewById(R.id.edtObservacionesMatricula);
        btnGuardar = findViewById(R.id.btnGuardarMatricula);
        btnCancelar = findViewById(R.id.btnCancelarMatricula);

        estudianteDAO = new EstudianteDAO(this);
        matriculaDAO = new MatriculaDAO(this);
        dbHelper = new DatabaseHelper(this);

        cargarSpinners();

        btnGuardar.setOnClickListener(v -> guardarMatricula());
        btnCancelar.setOnClickListener(v -> finish());
    }

    // Variables de clase necesarias
    private List<Estudiante> listaEstudiantesFiltrada = new ArrayList<>();

    private void cargarSpinners() {
        // 1. Estudiantes (Solo Activos)
        List<Estudiante> todosEstudiantes = estudianteDAO.listar();
        listaEstudiantesFiltrada.clear();
        List<String> nombresEst = new ArrayList<>();

        for (Estudiante e : todosEstudiantes) {
            if ("ACTIVO".equalsIgnoreCase(e.getEstadoAcademico())) {
                listaEstudiantesFiltrada.add(e);
                nombresEst.add(e.getNombreCompleto() + " (" + e.getNumeroCuenta() + ")");
            }
        }

        if (nombresEst.isEmpty()) {
            nombresEst.add("No hay estudiantes activos");
        }

        ArrayAdapter<String> adapterEst = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nombresEst);
        adapterEst.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEstudiantes.setAdapter(adapterEst);

        // 2. Períodos Académicos (Acepta estado 1 o 'ACTIVO')
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        listaIdsPeriodos.clear();
        List<String> nombresP = new ArrayList<>();

        Cursor cursorP = db.rawQuery(
                "SELECT id_periodo, nombre, anio_academico FROM periodo_academico WHERE estado = 1 OR estado = 'ACTIVO'", null);

        if (cursorP.moveToFirst()) {
            do {
                listaIdsPeriodos.add(cursorP.getInt(0));
                nombresP.add(cursorP.getString(1) + " - " + cursorP.getInt(2));
            } while (cursorP.moveToNext());
        }
        cursorP.close();

        if (nombresP.isEmpty()) {
            nombresP.add("No hay períodos activos");
        }

        ArrayAdapter<String> adapterPeriodo = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nombresP);
        adapterPeriodo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPeriodos.setAdapter(adapterPeriodo);

        // 3. Asignaturas (Acepta estado 1 o 'ACTIVA')
        listaIdsAsignaturas.clear();
        List<String> nombresA = new ArrayList<>();

        Cursor cursorA = db.rawQuery(
                "SELECT id_asignatura, nombre, codigo_asignatura FROM asignatura WHERE estado = 1 OR estado = 'ACTIVA' OR estado = 'ACTIVO'", null);

        if (cursorA.moveToFirst()) {
            do {
                listaIdsAsignaturas.add(cursorA.getInt(0));
                nombresA.add(cursorA.getString(2) + " - " + cursorA.getString(1));
            } while (cursorA.moveToNext());
        }
        cursorA.close();
        db.close();

        if (nombresA.isEmpty()) {
            nombresA.add("No hay asignaturas activas");
        }

        ArrayAdapter<String> adapterAsig = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nombresA);
        adapterAsig.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAsignaturas.setAdapter(adapterAsig);
    }

    private void guardarMatricula() {
        if (listaEstudiantesFiltrada.isEmpty() || listaIdsPeriodos.isEmpty() || listaIdsAsignaturas.isEmpty()) {
            Toast.makeText(this, "Debe registrar y activar estudiantes, períodos y asignaturas primero", Toast.LENGTH_LONG).show();
            return;
        }

        int posEst = spnEstudiantes.getSelectedItemPosition();
        int posPer = spnPeriodos.getSelectedItemPosition();
        int posAsig = spnAsignaturas.getSelectedItemPosition();

        if (posEst < 0 || posPer < 0 || posAsig < 0) {
            Toast.makeText(this, "Selección inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        Estudiante est = listaEstudiantesFiltrada.get(posEst);
        int idPeriodo = listaIdsPeriodos.get(posPer);
        int idAsignatura = listaIdsAsignaturas.get(posAsig);

        if (matriculaDAO.existeMatriculaEnPeriodo(est.getIdEstudiante(), idPeriodo)) {
            Toast.makeText(this, "El estudiante ya está matriculado en este período", Toast.LENGTH_LONG).show();
            return;
        }

        Matricula matricula = new Matricula(est.getIdEstudiante(), idPeriodo, edtObservaciones.getText().toString().trim());
        List<Integer> asignaturas = new ArrayList<>();
        asignaturas.add(idAsignatura);

        long res = matriculaDAO.registrarMatriculaConAsignaturas(matricula, asignaturas);
        if (res != -1) {
            Toast.makeText(this, "Matrícula registrada correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al procesar la matrícula", Toast.LENGTH_SHORT).show();
        }
    }
}