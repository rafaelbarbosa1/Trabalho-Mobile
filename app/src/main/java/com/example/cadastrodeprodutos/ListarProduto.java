package com.example.cadastrodeprodutos;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarProduto extends AppCompatActivity {


    private ListView listView;
    private ProdutoDao dao;
    private List<Produto> produtos;
    private List<Produto> produtosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produto);

        listView = findViewById(R.id.lstProduto);
        dao = new ProdutoDao(this);

        produtos = dao.obterTodos();
        produtosFiltrados.addAll(produtos);
        ProdutoAdapter adaptador = new ProdutoAdapter(this, produtosFiltrados);
        listView.setAdapter(adaptador);

        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal,menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                pesquisaProduto(s);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void pesquisaProduto(String nome){
        produtosFiltrados.clear();
        for (Produto p : produtos){
            if(p.getNome().toLowerCase().contains(nome.toLowerCase())){
                produtosFiltrados.add(p);
            }
        }
        listView.invalidateViews();
    }

    public void deletar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Produto produtoDeleta = produtosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Atenção").setMessage("Deseja realmente deletar esse produto ?").setNegativeButton("Não", null).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                produtosFiltrados.remove(produtoDeleta);
                produtos.remove(produtoDeleta);
                dao.deletar(produtoDeleta);
                listView.invalidateViews();
            }
        }).create();
        dialog.show();
    }

    public void cadastrar (MenuItem item){

        Intent it = new Intent(this, CadastroProduto.class);
        startActivity(it);
    }

    public  void atualizar (MenuItem item){

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Produto produtoAtualizar = produtosFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, CadastroProduto.class);
        it.putExtra("produto", produtoAtualizar);
        startActivity(it);
    }

    public void onResume(){
        super.onResume();
        produtos = dao.obterTodos();
        produtosFiltrados.clear();
        produtosFiltrados.addAll(produtos);
        listView.invalidateViews();
    }
}