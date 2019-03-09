package com.example.humberto.firebaseapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText nome, idade;
    Button save_btn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("students");
        nome = findViewById(R.id.edit_nome);
        idade = findViewById(R.id.edit_idade);
        save_btn = findViewById(R.id.save_btn);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
    }

    private void addStudent() {

        String n = nome.getText().toString();
        int i = Integer.parseInt(idade.getText().toString());

        String id = databaseReference.push().getKey();
        Student s = new Student(id, n, i);

        databaseReference.child(id).setValue(s);

        Toast.makeText(getApplicationContext(), "Operação realizada" +
                "com sucesso", Toast.LENGTH_SHORT).show();

        nome.setText("");
        idade.setText("");

    }

    private void retrieveData() {
        databaseReference.child("students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String txt = dataSnapshot1.child("nome").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
