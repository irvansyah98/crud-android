package com.example.iervan.crudbaru;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TampilPegawai extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextDesg;
    private EditText editTextSalary;

    private Button buttonUpdate;
    private Button buttonDelete;


    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_pegawai);

        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.EMP_ID);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDesg = (EditText) findViewById(R.id.editTextDesg);
        editTextSalary = (EditText) findViewById(R.id.editTextSalary);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextId.setText(id);

        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(TampilPegawai.this,"Fetching...","Wait...",false,false);

            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params){
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String name = c.getString(konfigurasi.TAG_NAMA);
            String desg = c.getString(konfigurasi.TAG_POSISI);
            String sal = c.getString(konfigurasi.TAG_GAJIH);

            editTextName.setText(name);
            editTextDesg.setText(desg);
            editTextSalary.setText(sal);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void updateEmployee(){
        final String name = editTextName.getText().toString().trim();
        final String desg = editTextDesg.getText().toString().trim();
        final String salary = editTextSalary.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(TampilPegawai.this,"Updating...","Please Wait...",false,false);
                
            }
            
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilPegawai.this,"Success",Toast.LENGTH_LONG).show();
            }
            
            @Override
            protected String doInBackground(Void... params){
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_ID,id);
                hashMap.put(konfigurasi.KEY_EMP_NAMA,name);
                hashMap.put(konfigurasi.KEY_EMP_POSISI,desg);
                hashMap.put(konfigurasi.KEY_EMP_GAJIH,salary);
                
                RequestHandler rh = new RequestHandler();
                
                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP,hashMap);
                
                return s;
            }
        }
        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }
    
    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(TampilPegawai.this, "Updating...","Please Wait...",false,false);
                
            }
            
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilPegawai.this, "Success", Toast.LENGTH_LONG).show();
            }
            
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_EMP,id);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah anda yakin ingin menghapus ?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEmployee();
                        startActivity(new Intent(TampilPegawai.this,TampilSemuaPgw.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonUpdate){
            updateEmployee();
        }

        if (view == buttonDelete){
            confirmDeleteEmployee();
        }
    }
}
