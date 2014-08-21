package com.MDstudios.mdsandbox;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jawad Nasser on 8/13/2014.
 */
public class FragmentFlexibleUIB extends Fragment {
    TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flexui_b,container,false);

        text = (TextView) view.findViewById(R.id.textView);

        return view;
    }

    public void changeData(int index){
        String[] descriptions = getResources().getStringArray(R.array.chDescriptions);
        text.setText(descriptions[index]);
    }
}
