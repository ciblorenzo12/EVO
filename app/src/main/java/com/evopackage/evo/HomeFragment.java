package com.evopackage.evo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.activity_main_windows_create_join_event, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        final MainWindows_Create_Join_Event ma = (MainWindows_Create_Join_Event) getActivity();
        SharedPreferences sp = ma.getSharedPreferences("SP", ma.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
//        final Switch swi = root.findViewById(R.id.switch1);
//
//        int theme = sp.getInt("Theme", 1);
//        if(theme==1){
//            swi.setChecked(false);
//        }
//        else{
//            swi.setChecked(true);
//        }
//
//        swi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(swi.isChecked()){
//                    editor.putInt("Theme", 0);
//                }
//                else{
//                    editor.putInt("Theme", 1);
//                }
//                editor.commit();
//                ma.setDayNight();
//            }
//        });

        return root;
    }
}