package com.example.moriso.logintorailsserver;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;


public class LoginScreen extends ActionBarActivity {

    public static String resultado = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Button btnEnviar = (Button) findViewById(R.id.btnEnviar);
        Button btnListar = (Button) findViewById(R.id.btnListar);


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new enviarParaCadastro().execute();
            }
        });
        btnListar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new receberDeCadastro().execute();
            }
        });

    }
    private class enviarParaCadastro extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            final EditText editDe = (EditText) findViewById(R.id.editDe);
            String de = editDe.getText().toString();
            final EditText editPara = (EditText) findViewById(R.id.editPara);
            String para = editPara.getText().toString();
            final EditText editExSegredo = (EditText) findViewById(R.id.editExSegredo);
            String ex_segredo = editExSegredo.getText().toString();
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://179.106.207.194:3000/segredos");
            List pairs = new ArrayList();
            pairs.add(new BasicNameValuePair("segredo[de]",         de));
            pairs.add(new BasicNameValuePair("segredo[para]",       para));
            pairs.add(new BasicNameValuePair("segredo[ex_segredo]",     ex_segredo));
            try {
                post.setEntity(new UrlEncodedFormEntity(pairs));
                HttpResponse response = client.execute(post);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    private class receberDeCadastro extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("http://179.106.207.194:3000/segredos").get();
                resultado = doc.title();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(LoginScreen.this, ListarSegredos.class);
            intent.putExtra("resultado",resultado);
            startActivity(intent);
        }
    }
}
