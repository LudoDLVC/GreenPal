package com.example.test2.ui.gallery;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.test2.R;

public class AddFragment extends Fragment implements View.OnClickListener{

    View rootView;
    private LinearLayout add_addFragment;

    private EditText nombreEvento, ubicacion, fechadesde, horadesde, fechahasta, horahasta;
    private EditText descripcion;

    private Button guardar, cancelar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add, container, false);
        // Inflate the layout for this fragment

        add_addFragment = rootView.findViewById(R.id.add_addFragment);


        nombreEvento=rootView.findViewById(R.id.edtNombreEvento);
        ubicacion= rootView.findViewById(R.id.edtUbicacion);
        fechadesde=rootView.findViewById(R.id.edtFechaDesde);
        fechahasta= rootView.findViewById(R.id.edtFechaHasta);
        horadesde= rootView.findViewById(R.id.edtHoraInicio);
        horahasta= rootView.findViewById(R.id.edtHoraHasta);
        descripcion= rootView.findViewById(R.id.edtDescripcion);

        int dia=0, mes=0, anio=0;

        dia=(int)getArguments().getSerializable("dia");
        mes=(int)getArguments().getSerializable("mes");
        anio=(int)getArguments().getSerializable("anio");

        fechadesde.setText(dia+" - " + mes +" - " + anio);
        fechahasta.setText(dia+" - " + mes +" - " + anio);

        guardar=(Button) rootView.findViewById(R.id.btnGuardar);    guardar.setOnClickListener(this);
        cancelar=(Button) rootView.findViewById(R.id.btnCancelar);  cancelar.setOnClickListener(this);


        return rootView;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGuardar:
                BDSQLite bd = new BDSQLite(getContext(), "Agenda", null,1);
                SQLiteDatabase db = bd.getWritableDatabase();

                String sql = "insert into eventos" +
                        "(nombreEvento, ubicacion, fechadesde, horadesde, fechahasta, horahasta," +
                        "descripcion) values('" +
                        nombreEvento.getText() +
                        "','" + ubicacion.getText() +
                        "','" + fechadesde.getText() +
                        "','" + horadesde.getText() +
                        "','" + fechahasta.getText() +
                        "','" + horahasta.getText() +
                        "','" + descripcion.getText() + "')";

                try {
                    db.execSQL(sql);
                    nombreEvento.setText("");
                    ubicacion.setText("");
                    fechadesde.setText("");
                    horadesde.setText("");
                    fechahasta.setText("");
                    horahasta.setText("");
                    descripcion.setText("");

                    getFragmentManager().beginTransaction().remove(this).commit();
                    return;
                }catch (Exception e){
                    Toast.makeText(getContext(), "Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancelar:
                getFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }
}