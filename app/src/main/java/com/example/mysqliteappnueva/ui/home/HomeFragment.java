package com.example.mysqliteappnueva.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mysqliteappnueva.AdminSQLiteOpenHelper;
import com.example.mysqliteappnueva.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    ListView listado;
    ArrayList<String> list;
    ArrayAdapter adaptador;



    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visualizar, container, false);




        listado=(ListView) root.findViewById(R.id.lista);
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(getActivity(),"administracion", null, 1);





        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);









            }
        });
        return root;
    }
}