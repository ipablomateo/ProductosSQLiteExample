package greenway.com.gt.productos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 1;
    private String filePath;

    private DB db;
    private ProductoAdapter adapter;

    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private EditText etBuscarProducto;
    private TextView tvCount;
    private ListView listView;

    private ArrayList<Producto> productos;
    private MenuItem menuItem;

    private void load(String query) {
        if (query.length() > 0) {
            this.productos = db.getProductos(query);
            if (this.productos.size() == 0) {
                this.tvCount.setText("No hay productos :(");
            }
            if (this.productos.size() == 1) {
                this.tvCount.setText("1 producto encontrado");
            }
            if (this.productos.size() > 1) {
                this.tvCount.setText(this.productos.size() + " productos encontrados");
            }
        } else {
            this.productos = db.getProductos();
            this.tvCount.setText("");
        }
        this.adapter = new ProductoAdapter(getApplicationContext(), productos);
        this.listView.setAdapter(adapter);
    }

    private void setupToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setup(){
        this.coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coodirnador_productos_lista);
        this.listView = (ListView) findViewById(R.id.lvProductos);
        this.tvCount = (TextView) findViewById(R.id.tvProductosCount);
        this.etBuscarProducto = (EditText) findViewById(R.id.etBuscarProducto);
        this.etBuscarProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                load(String.valueOf(query));
                if (query.length() == 0) {
                    tvCount.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.db = new DB(getApplicationContext());

        this.setupToolbar();
        this.setup();

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.load("");
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Selecciona un archivo CSV"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    this.filePath = uri.getPath();
                    if (this.filePath.length() > 0) {
                        try {
                            addProductos(parseProductos(readCSV(this.filePath)));
                        }catch (Exception e){
                            Log.i("pablo", e.toString());
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String readCSV(String path) {
        File rFile = new File(path);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rFile), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {

        }

        return text.toString();
    }

    private ArrayList<Producto> parseProductos(String texto) {

        try{
            ArrayList<Producto> productos = new ArrayList<>();
            String tmp = texto.toString();
            String lines[] = tmp.split("\n");

            for (int i = 0; i <= lines.length - 1; i++) {
                String objeto[] = lines[i].split(",");
                productos.add(new Producto(
                        objeto[0],
                        objeto[1],
                        Float.valueOf(objeto[2])
                ));
            }
        }catch (Exception e){
            Log.i("pablo", e.toString());
            return null;
        }

        return productos;
    }

    private void addProductos(ArrayList<Producto> productos) {
        for (int i = 0; i <= productos.size() - 1; i++) {
            this.db.addProducto(productos.get(i));
        }
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Se han añadido " + productos.size() + " productos correctamente", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_productos_lista, menu);
        this.menuItem = (MenuItem) findViewById(R.id.action_clear_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_importar_productos_lista:
                this.showFileChooser();
                break;
            case R.id.action_clear_search:
                this.etBuscarProducto.setText("");
                //item.setVisible(false);
                break;
        }
        return false;
    }
}
