package id.sch.smktelkom_mlg.project2.xirpl30717233235.expinder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {
    DataHelper dbHelper;
    Button ton1;
    EditText text1, text2, text3;


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        dbHelper = new DataHelper(this);
        text1 = (EditText) findViewById(R.id.edtPurdate);
        text2 = (EditText) findViewById(R.id.edtItem);
        text3 = (EditText) findViewById(R.id.edtExpdate);
        ton1 = (Button) findViewById(R.id.btnSave);

        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("INSERT INTO item (purdate, item, expdate) VALUES('" +
                        text1.getText().toString() + "','" +
                        text2.getText().toString() + "','" +
                        text3.getText().toString() + "')");
                Toast.makeText(getApplicationContext(), "Items Added", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

}






