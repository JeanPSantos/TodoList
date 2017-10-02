package todolist.cursoandroid.com.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText textoTarefa;
    private Button botaoAdicionar;
    private ListView listaTarefas;
    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            //Recuperar componentes
            textoTarefa = (EditText) findViewById(R.id.textoId);
            botaoAdicionar = (Button) findViewById(R.id.botaoAdicionarId);
            listaTarefas = (ListView) findViewById(R.id.listViewId);

            //Banco de Dados
            bancoDados = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);

            //Tabela de Tarefas
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");

            botaoAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String textoDigitado = textoTarefa.getText().toString();
                    salvarTarefa(textoDigitado);

                }
            });

            //Recupera as Tarefas
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas", null);

            //Recupera os ID's das colunas
            int indiceColunaId = cursor.getColumnIndex("id");
            int indiceColunaTarefa = cursor.getColumnIndex("tarefa");

            //Listar as Tarefas
            cursor.moveToFirst();
            while (cursor != null) {

                Log.i("Resultado - ", "Tarefa: " + cursor.getString(indiceColunaTarefa));
                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void salvarTarefa(String texto) {
        try {
            if (texto.equals("")) {
                Toast.makeText(MainActivity.this,"Digite uma Tarefa", Toast.LENGTH_SHORT).show();
            } else {
                bancoDados.execSQL("INSERT INTO tarefas (tarefa) VALUES ('" + texto + "')");
                Toast.makeText(MainActivity.this,"Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
