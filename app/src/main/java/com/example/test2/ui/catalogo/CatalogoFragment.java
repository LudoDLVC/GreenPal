package com.example.test2.ui.catalogo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test2.R;

import java.util.ArrayList;
import java.util.List;

public class CatalogoFragment extends Fragment {
    View rootView;
    List<ListElementFragment> elements;
    private RecyclerView recyclerView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=  inflater.inflate(R.layout.fragment_catalogo, container, false);
        init();
        return rootView;
    }


    public void init(){
        elements = new ArrayList<>();
        elements.add(new ListElementFragment("#000000", "Jacaranda", "Riego: 2-3 veces por semana", "Exteriores"));
        elements.add(new ListElementFragment("#000000", "Ocotillo", "Riego: 1 vez por semana", "Exteriores"));
        elements.add(new ListElementFragment("#000000", "Garambullo", "Riego: Cada 15 días", "Exteriores"));
        elements.add(new ListElementFragment("#000000", "Suculenta", "Riego: 1 vez por semana", "Interiores y Exteriores"));
        elements.add(new ListElementFragment("#000000", "Nopal", "Riego: Cada 20 días", "Exteriores"));

        ListAdapter listAdapter = new ListAdapter(elements, this);

        recyclerView =(RecyclerView)rootView.findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(listAdapter);
    }
}