package greenway.com.gt.productos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pablo on 08/04/2016.
 */
public class ProductoAdapter extends BaseAdapter {

    private ArrayList<Producto> productos;
    private LayoutInflater inflater;

    public ProductoAdapter(Context context, ArrayList<Producto> productos) {
        this.productos = productos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.productos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = inflater.inflate(R.layout.producto_row, null);

        TextView tvNombre = (TextView) view.findViewById(R.id.productoNombreLista);
        TextView tvDescripcion = (TextView) view.findViewById(R.id.productoDescripcionLista);
        TextView tvPrecio = (TextView) view.findViewById(R.id.productoPrecioLista);

        tvNombre.setText(productos.get(position).getNombre());
        tvDescripcion.setText(productos.get(position).getDescripcion());
        tvPrecio.setText(String.valueOf(productos.get(position).getPrecio()));


        return view;
    }
}
