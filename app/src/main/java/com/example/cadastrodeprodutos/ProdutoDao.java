package com.example.cadastrodeprodutos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ProdutoDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Produto produto){
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("serial", produto.getSerial());
        values.put("categoria", produto.getCategoria());

        return banco.insert("produto", null, values);
    }
    public List<Produto> obterTodos(){
        List<Produto> produtos = new ArrayList<>();
        Cursor cursor = banco.query("produto", new String[]{"id", "nome", "serial", "categoria"}, null, null, null,
                null,null);
        while (cursor.moveToNext()){
            Produto p = new Produto();
            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setSerial(cursor.getString(2));
            p.setCategoria(cursor.getString(3));
            produtos.add(p);
        }
        return produtos;
    }

    public void deletar(Produto produto){
        banco.delete("produto", "id = ?", new String[]{String.valueOf(produto.getId())});
    }

    public void atualizar(Produto produto){
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("serial", produto.getSerial());
        values.put("categoria", produto.getCategoria());
        banco.update("produto", values, "id = ?", new String[]{String.valueOf(produto.getId())});

    }
}