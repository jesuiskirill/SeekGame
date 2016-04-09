package com.iwuvhugs.seekgame;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class GameFragment extends Fragment {

    private static final String TAG = GameFragment.class.getSimpleName();

    private TextView mImageDetails;


    public static GameFragment newInstance() {
        return new GameFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        mImageDetails = (TextView) view.findViewById(R.id.image_details);
        return view;
    }


    public void setImageDetailsText(String s) {
        mImageDetails.setText(s);
    }

    public void setGame(GameObjects objects) {


    }
}
