package pe.servosa.android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.util.MyPreferences;
import pe.servosa.android.util.internet.Connection;
import pe.servosa.android.util.internet.CustomJsonObjectRequest;
import pe.servosa.android.util.internet.MyVolley;
import pe.servosa.android.util.internet.TaskPing;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.txtUserName) EditText txtUserName;
    @Bind(R.id.txtPassword) EditText txtPassword;
    @Bind(R.id.imgBackgroundLogin) ImageView imgBackgroundLogin;
    @Bind(R.id.imgLogoServosa) ImageView imgLogoServosa;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        MyPreferences.getInstance().init(this, "UserProfile");
        if (MyPreferences.getInstance().getString("email", "").length() > 0) {
            initMainApp();
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Glide.with(this)
                .load(R.drawable.background_servosa)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            .crossFade()
            .into(imgBackgroundLogin);

        Glide.with(this)
                .load(R.drawable.logo_servosa)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imgLogoServosa);

        new TaskPing().execute(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Iniciando sesion");
        progressDialog.setMessage("Espere un momento");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && txtPassword.hasFocus()) {
            if (Connection.hasNetworkConnectivity(this)) {
                if (validateUserAndPassword()) {
                    progressDialog.show();
                    initSession();
                }
            } else {
                Connection.showMessageNotConnectedToNetwork(this);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void initSession() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", txtUserName.getText().toString().trim());
        params.put("password", txtPassword.getText().toString().trim());

        CustomJsonObjectRequest request = new CustomJsonObjectRequest
            (Request.Method.POST, MyVolley.URL_API_REST + "signIn", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    Log.d("RESPONSE", response + "");
                    try {
                        if (response.getBoolean("status")) {
                            saveUserProfile(response.getJSONObject("data"));
                            initMainApp();
                        } else {
                            Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException ex) {
                        Toast.makeText(LoginActivity.this, getString(R.string.json_object_exception), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    progressDialog.dismiss();
                    Log.e("VolleyError", error.getMessage() + "");
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle(getString(R.string.volley_error_title))
                            .setMessage(getString(R.string.volley_error_message))
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                }
            });

        MyVolley.getInstance(this).addToRequestQueue(request);
    }

    private boolean validateUserAndPassword() {
        if (txtUserName.getText().toString().trim().length() == 0 || txtPassword.getText().toString().trim().length() == 0) {
            Toast.makeText(LoginActivity.this, getString(R.string.act_login_error_datos_usuario), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void saveUserProfile(JSONObject usuario) throws JSONException{
        MyPreferences.getInstance().edit()
                .putString("id", usuario.getString("id_usuario"))
                .putString("email", usuario.getString("email"))
                .putString("username", txtUserName.getText().toString().trim())
                .putString("nombre", usuario.getString("nombre"))
                .putString("apellidos", usuario.getString("apellidos"))
                .putString("tipo_usuario", usuario.getString("nombre_tipo_usuario"))
                .putString("usuario", usuario.getString("usuario"))
                .putString("id_tipo_usuario", usuario.getString("id_tipo_usuario"))
                .commit();
    }

    private void initMainApp() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
