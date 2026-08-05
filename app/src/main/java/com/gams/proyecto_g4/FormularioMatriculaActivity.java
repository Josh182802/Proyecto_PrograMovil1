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

    private void cargarSpinners() {
        // 1. Estudiantes
        listaEstudiantes = estudianteDAO.listar();
        List<String> nombresEst = new ArrayList<>();
        for (Estudiante e : listaEstudiantes) {
            if ("ACTIVO".equals(e.getEstadoAcademico())) {
                nombresEst.add(e.getNombreCompleto() + " (" + e.getNumeroCuenta() + ")");
            }
        }
        spnEstudiantes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresEst));

        // 2. Períodos Académicos
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursorP = db.rawQuery("SELECT id_periodo, nombre, anio_academico FROM periodo_academico WHERE estado = 1", null);
        List<String> nombresP = new ArrayList<>();
        if (cursorP.moveToFirst()) {
            do {
                listaIdsPeriodos.add(cursorP.getInt(0));
                nombresP.add(cursorP.getString(1) + " - " + cursorP.getInt(2));
            } while (cursorP.moveToNext());
        }
        cursorP.close();
        spnPeriodos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresP));

        // 3. Asignaturas
        Cursor cursorA = db.rawQuery("SELECT id_asignatura, nombre, codigo_asignatura FROM asignatura WHERE estado = 1", null);
        List<String> nombresA = new ArrayList<>();
        if (cursorA.moveToFirst()) {
            do {
                listaIdsAsignaturas.add(cursorA.getInt(0));
                nombresA.add(cursorA.getString(2) + " - " + cursorA.getString(1));
            } while (cursorA.moveToNext());
        }
        cursorA.close();
        db.close();
        spnAsignaturas.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresA));
    }

    private void guardarMatricula() {
        if (spnEstudiantes.getSelectedItemPosition() < 0 || listaIdsPeriodos.isEmpty() || listaIdsAsignaturas.isEmpty()) {
            Toast.makeText(this, "Asegúrese de contar con estudiantes, períodos y asignaturas activas", Toast.LENGTH_LONG).show();
            return;
        }

        Estudiante est = listaEstudiantes.get(spnEstudiantes.getSelectedItemPosition());
        int idPeriodo = listaIdsPeriodos.get(spnPeriodos.getSelectedItemPosition());
        int idAsignatura = listaIdsAsignaturas.get(spnAsignaturas.getSelectedItemPosition());

        if (matriculaDAO.existeMatriculaEnPeriodo(est.getIdEstudiante(), idPeriodo)) {
            Toast.makeText(this, "El estudiante ya cuenta con una matrícula en este período", Toast.LENGTH_LONG).show();
            return;
        }

        Matricula matricula = new Matricula(est.getIdEstudiante(), idPeriodo, edtObservaciones.getText().toString().trim());
        List<Integer> asignaturas = new ArrayList<>();
        asignaturas.add(idAsignatura);

        long res = matriculaDAO.registrarMatriculaConAsignaturas(matricula, asignaturas);
        if (res != -1) {
            Toast.makeText(this, "Matrícula registrada exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al guardar matrícula", Toast.LENGTH_SHORT).show();
        }
    }
}