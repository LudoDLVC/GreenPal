package com.example.test2.ui.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.test2.R;
import com.example.test2.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment implements CalendarView.OnDateChangeListener {

    private FragmentGalleryBinding binding;
    View rootView;
    private CalendarView calendarView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_gallery, container, false);
        // Inflate the layout for this fragment

        calendarView=(CalendarView)rootView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CharSequence []items =  new CharSequence[3];
        items[0]="Agregar evento";
        items[1]="Ver eventos";
        items[2]="Cancelar";

        final int dia, mes, anio;
        dia =i;
        mes =i1+1;
        anio =i2;

        builder.setTitle("Seleccione una tarea")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i==0){
                            //actividad agregar eventos
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("anio", anio);
                            mensajes("funciona");
                            AddFragment addFragment = new AddFragment();
                            addFragment.setArguments(bundle);

                            fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.calendar_base, addFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }else if (i==1){
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("anio", anio);
                            ViewEventsFragment viewEventsFragment = new ViewEventsFragment();
                            viewEventsFragment.setArguments(bundle);

                            fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.calendar_base, viewEventsFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }else if (i==2){
                            return;
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void mensajes (String mensajes){
        Toast.makeText(getContext(), mensajes, Toast.LENGTH_SHORT).show();
    }
}