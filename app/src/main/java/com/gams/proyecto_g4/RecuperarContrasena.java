package com.gams.proyecto_g4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gams.proyecto_g4.dao.RecuperacionDAO;

public class RecuperarContrasena extends AppCompatActivity {

    EditText edtCorreoRecuperar;
    Button btnSolicitarRecuperacion;
    TextView txtVolverLogin;

    RecuperacionDAO recuperacionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        edtCorreoRecuperar = findViewById(R.id.edtCorreoRecuperar);
        btnSolicitarRecuperacion = findViewById(R.id.btnSolicitarRecuperacion);
        txtVolverLogin = findViewById(R.id.txtVolverLogin);

        recuperacionDAO = new RecuperacionDAO(this);

        btnSolicitarRecuperacion.setOnClickListener(view -> solicitarRecuperacion());

        txtVolverLogin.setOnClickListener(view -> {
            Intent intent = new Intent(
                    RecuperarContrasena.this,
                    MainActivity.class
            );

            startActivity(intent);
            finish();
        });
    }

    private void solicitarRecuperacion() {

        String correo = edtCorreoRecuperar.getText()
                .toString()
                .trim();

        if (correo.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese su correo electrónico",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        boolean registrado = recuperacionDAO.solicitarRecuperacion(correo);

        if (registrado) {

            Toast.makeText(
                    this,
                    "Solicitud de recuperación creada",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "El correo no está registrado",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}