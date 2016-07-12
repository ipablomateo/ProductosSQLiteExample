package greenway.com.gt.productos;

/**
 * Created by Pablo on 08/04/2016.
 */
public class Parametros {

    public static final String DATABASE_NAME = "dbFarmaciaLite";
    public static final int DATABASE_VERSION = 5;

    public static final String TABLE_NAME_PRODUCTOS = "productos";

    public static final String COLUMN_CODIGO = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_PRECIO = "precio";

    public static final String CREATE_PRODUCTOS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME_PRODUCTOS + " (" +
            COLUMN_CODIGO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NOMBRE + " TEXT, " +
            COLUMN_DESCRIPCION + " TEXT, " +
            COLUMN_PRECIO + " REAL)";
}
