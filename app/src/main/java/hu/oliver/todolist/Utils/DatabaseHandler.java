package hu.oliver.todolist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hu.oliver.todolist.Model.ToDoModel;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    private final static String NAME = "toDoListDatabase";
    private final static String TODO_TABLE = "todo";
    private final static String ID = "id";
    private final static String TASK = "task";
    private final static String STATUS = "status";
    private final static String  CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE
        + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    private DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the old, existing table
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);

        // create the table again
        onCreate(db);
    }

    public void openDatabase() {
        // getWritableDatabase: to WRITE and UPDATE aswell
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
    }

    /*public List<ToDoModel> getAllTasks() {
        List<ToDoModel> taskList = new ArrayList<>();

    }*/

}
