package com.gams.proyecto_g4;

import com.gams.proyecto_g4.dao.UsuarioDAO;
import com.gams.proyecto_g4.dao.SesionDAO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText edtUsuario, edtContrasena;
    Button btnIniciarSesion;
    TextView txtRecuperar;

    UsuarioDAO usuarioDAO;
    SesionDAO sesionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuarioDAO = new UsuarioDAO(this);
        sesionDAO = new SesionDAO(this);

        if (sesionDAO.existeSesionActiva()) {

            Intent intent = new Intent(
                    MainActivity.this,
                    DashboardActivity.class
            );

            startActivity(intent);
            finish();

            return;
        }

        setContentView(R.layout.activity_main);

        edtUsuario = findViewById(R.id.edtUsuario);
        edtContrasena = findViewById(R.id.edtContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        txtRecuperar = findViewById(R.id.txtRecuperar);


        btnIniciarSesion.setOnClickListener(view -> validarLogin());


        txtRecuperar.setOnClickListener(view -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    RecuperarContrasena.class
            );

            startActivity(intent);

        });

    }


    private void validarLogin() {

        String usuario = edtUsuario.getText()
                .toString()
                .trim();

        String contrasena = edtContrasena.getText()
                .toString()
                .trim();


        if (usuario.isEmpty() || contrasena.isEmpty()) {

            Toast.makeText(
                    this,
                    "Debe ingresar usuario y contraseña",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        boolean valido = usuarioDAO.validarLogin(
                usuario,
                contrasena
        );


        if (valido) {

            int idUsuario = usuarioDAO.obtenerIdUsuario(usuario);

            if (idUsuario != -1) {

                sesionDAO.crearSesion(idUsuario);

            }

            String rol = usuarioDAO.obtenerRol(usuario);

            Intent intent = new Intent(
                    MainActivity.this,
                    DashboardActivity.class
            );

            intent.putExtra("usuario", usuario);
            intent.putExtra("nivel", rol);

            startActivity(intent);
            finish();


        } else {

            Toast.makeText(
                    this,
                    "Credenciales inválidas",
                    Toast.LENGTH_SHORT
            ).show();

        }

    }
}