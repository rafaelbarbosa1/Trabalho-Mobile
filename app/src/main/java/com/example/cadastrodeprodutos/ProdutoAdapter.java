package com.example.cadastrodeprodutos;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProdutoAdapter extends BaseAdapter {

    private List<Produto> produtos;
    private Activity activity;

    public ProdutoAdapter(Activity activity, List<Produto> produtos) {
        this.activity = activity;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int i) {
        return produtos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return produtos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = activity.getLayoutInflater().inflate(R.layout.item, viewGroup,false );
        TextView nome = v.findViewById(R.id.txtNome);
        TextView serial = v.findViewById(R.id.txtSerial);
        TextView categoria = v.findViewById(R.id.txtCategoria);
        Produto p = produtos.get(i);
        nome.setText(p.getNome());
        serial.setText(p.getSerial());
        categoria.setText(p.getCategoria());

        return v;
    }
}