package com.gams.proyecto_g4;

import com.gams.proyecto_g4.dao.UsuarioDAO;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {

    EditText edtUsuario, edtContrasena;
    Button btnIniciarSesion;
    TextView txtRecuperar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Si tu XML se llama activity_main.xml, deja esta línea.
        setContentView(R.layout.activity_main);

        edtUsuario = findViewById(R.id.edtUsuario);
        edtContrasena = findViewById(R.id.edtContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        txtRecuperar = findViewById(R.id.txtRecuperar);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarLogin();
            }
        });

        txtRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Función de recuperación de contraseña", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validarLogin() {
        String usuario = edtUsuario.getText().toString().trim();
        String contrasena = edtContrasena.getText().toString().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {

            Toast.makeText(MainActivity.this,
                    "Debe ingresar usuario y contraseña",
                    Toast.LENGTH_SHORT).show();

        } else {

            UsuarioDAO usuarioDAO = new UsuarioDAO(MainActivity.this);

            boolean valido = usuarioDAO.validarLogin(usuario, contrasena);

            if (valido) {

                abrirDashboard(usuario, "Usuario");

            } else {

                Toast.makeText(MainActivity.this,
                        "Credenciales inválidas",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void abrirDashboard(String usuario, String nivel) {
        Toast.makeText(MainActivity.this, "Bienvenido " + nivel, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("nivel", nivel);
        startActivity(intent);
    }
}

///Usuario: admin
/// Contraseña: 1234
/// Nivel: Administrador