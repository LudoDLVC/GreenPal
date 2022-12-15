package com.example.test2.ui.gallery;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test2.R;

public class ViewEventsFragment extends Fragment implements AdapterView.OnItemLongClickListener{


    private SQLiteDatabase db;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_view_events, container, false);
        // Inflate the layout for this fragment

        listView = (ListView) rootView.findViewById(R.id.ltvListaEventos);
        listView.setOnItemLongClickListener(this);

        int dia, mes, anio;
        dia = mes = anio = 0;

        dia = (int) getArguments().getSerializable("dia");
        mes = (int) getArguments().getSerializable("mes");
        anio = (int) getArguments().getSerializable("anio");
        String cadena = dia + " - " + mes + " - " + anio;

        BDSQLite bd = new BDSQLite(getContext(), "Agenda", null, 1);
        db = bd.getReadableDatabase();

        String sql = "select * from eventos where fechadesde='" + cadena + "'";
        Cursor c;

        String nombre, fechadesde, fechahasta, descripcion, ubicacion;
        try {
            c = db.rawQuery(sql, null);
            arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
            if (c.moveToFirst()) {
                do {
                    nombre = c.getString(1);
                    ubicacion = c.getString(2);
                    fechadesde = c.getString(3);
                    fechahasta = c.getString(5);
                    descripcion = c.getString(7);
                    arrayAdapter.add(nombre + ", " + ubicacion + ", " + fechadesde + ", " + fechahasta + ", " + descripcion);
                } while (c.moveToNext());
                listView.setAdapter(arrayAdapter);
            } else {
                getFragmentManager().beginTransaction().remove(this).commit();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error"+ex.getMessage(),Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction().remove(this).commit();
        }
        return rootView;
    }

    private void eliminar(String dato) {
        String[] datos = dato.split(", ");

        String sql = "delete from eventos where nombreEvento='" + datos[0] + "' and" +
                " ubicacion='" + datos[1] + "' and fechadesde='" + datos[2] + "' and " +
                "fechahasta='" + datos[3] + "' and descripcion='" + datos[4] + "'";

        try {
            arrayAdapter.remove(dato);
            listView.setAdapter(arrayAdapter);
            db.execSQL(sql);
            Toast.makeText(getContext(), "Evento eliminado", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        CharSequence[] items = new CharSequence[2];
        items[0] = "Eliminar evento";
        items[1] = "Cancelar";
        builder.setTitle("Eliminar evento")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            eliminar(adapterView.getItemAtPosition(i).toString());

                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

}