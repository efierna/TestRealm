package com.example.testrealm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testrealm.Model.DataModel;

import java.util.List;

import io.realm.Realm;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_insert;
    private Button button_update;
    private Button button_read;
    private Button button_delete;

    private TextView output_text;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_insert = findViewById(R.id.btn_insert);
        button_update = findViewById(R.id.btn_update);
        button_read = findViewById(R.id.btn_read);
        button_delete = findViewById(R.id.btn_delete);
        output_text = findViewById(R.id.show_data);

        button_insert.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_read.setOnClickListener(this);
        button_delete.setOnClickListener(this);

        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_insert){
            Log.d("inser", "onClick: insert");
            ShowInsertDialog();
        }

        if(view.getId() == R.id.btn_update){
            Log.d("inser", "onClick: insert");
        }

        if(view.getId() == R.id.btn_read){
            Log.d("inser", "onClick: insert");
            ShowData();
        }

        if(view.getId() == R.id.btn_delete){
            Log.d("inser", "onClick: insert");
        }
    }

    private void ShowInsertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
        View view = getLayoutInflater().inflate(R.layout.data_input_dialog, null);
        alert.setView(view);

        final EditText name = view.findViewById(R.id.input_name);
        final EditText age = view.findViewById(R.id.input_name);
        final Spinner gender = view.findViewById(R.id.input_gender);
        Button btn_save = view.findViewById(R.id.btn_save);

        final AlertDialog alertDialog = alert.show();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                Number current_id = realm.where(DataModel.class).max("id");
                long nextId;
                if (current_id == null) {
                    nextId = 1;
                }else {
                    nextId = current_id.intValue() + 1;
                }

                final DataModel dataModel = new DataModel();
                dataModel.setId(nextId);
                dataModel.setAge(Integer.parseInt(age.getText().toString()));
                dataModel.setName(name.getText().toString());
                dataModel.setGender(gender.getSelectedItem().toString());

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(dataModel);
                    }
                });

            }
        });

    }

    private void ShowData(){
        List<DataModel> dataModels = realm.where(DataModel.class).findAll();
        output_text.setText("");
        for (int i = 0; i < dataModels.size(); i++){
            output_text.append("ID : "+dataModels.get(i).getId() + "Name : "+dataModels.get(i).getName() + "Gender : "+dataModels.get(i).getGender() + " \n");
        }
    }
}