package com.example.cadastrodeprodutos;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroProduto extends AppCompatActivity {

    private EditText nome;
    private EditText serial;
    private EditText categoria;
    private ProdutoDao dao;
    private Produto produto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        nome = findViewById(R.id.edtNome);
        serial = findViewById(R.id.edtSerial);
        categoria = findViewById(R.id.edtCategoria);
        dao = new ProdutoDao(this);

        Intent it = getIntent();
        if(it.hasExtra("produto")){
            produto = (Produto) it.getSerializableExtra("produto");
            nome.setText(produto.getNome());
            serial.setText(produto.getSerial());
            categoria.setText(produto.getCategoria());
        }
    }

    public void salvar (View view){
        if(produto == null) {
            produto = new Produto();
            produto.setNome(nome.getText().toString());
            produto.setSerial(serial.getText().toString());
            produto.setCategoria(categoria.getText().toString());
            long id = dao.inserir(produto);

            Toast.makeText(this, "Produto inserido com id" + id, Toast.LENGTH_LONG).show();
            finish();
        }else {
            produto.setNome(nome.getText().toString());
            produto.setSerial(serial.getText().toString());
            produto.setCategoria(categoria.getText().toString());
            dao.atualizar(produto);
            Toast.makeText(this, "Produto foi alterado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}