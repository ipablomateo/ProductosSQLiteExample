package greenway.com.gt.productos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Pablo on 08/04/2016.
 */
public class DB extends SQLiteOpenHelper {
    public DB(Context context) {
        super(context, Parametros.DATABASE_NAME, null, Parametros.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Parametros.CREATE_PRODUCTOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Parametros.TABLE_NAME_PRODUCTOS);
        onCreate(db);
    }

    public void addProducto(Producto producto){
        if(producto!=null){
            ContentValues values = new ContentValues();
            values.put(Parametros.COLUMN_NOMBRE, producto.getNombre());
            values.put(Parametros.COLUMN_DESCRIPCION, producto.getDescripcion());
            values.put(Parametros.COLUMN_PRECIO, producto.getPrecio());

            SQLiteDatabase db = getWritableDatabase();
            db.insert(Parametros.TABLE_NAME_PRODUCTOS, null, values);
            db.close();
        }
    }

    public ArrayList<Producto> getProductos(){
        ArrayList<Producto> tmp = new ArrayList<>();

        String sql = "SELECT * FROM " + Parametros.TABLE_NAME_PRODUCTOS;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            tmp.add(new Producto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getFloat(3)
            ));
        }
        return tmp;
    }

    public ArrayList<Producto> getProductos(String query){
        ArrayList<Producto> tmp = new ArrayList<>();

        String sql = "SELECT * FROM " + Parametros.TABLE_NAME_PRODUCTOS +
                " WHERE " + Parametros.COLUMN_CODIGO  + " LIKE '%" + query + "%'" +
                " OR " + Parametros.COLUMN_NOMBRE + " LIKE '%" + query + "%'" +
                " OR " + Parametros.COLUMN_DESCRIPCION + " LIKE '%" + query + "%'";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            tmp.add(new Producto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getFloat(3)
            ));
        }
        return tmp;
    }

}
