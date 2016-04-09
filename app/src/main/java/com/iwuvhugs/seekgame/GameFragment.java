package com.iwuvhugs.seekgame;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;


public class GameFragment extends Fragment {

    private static final String TAG = GameFragment.class.getSimpleName();

    //    private TextView mImageDetails;
    private GridView gridview;
    private GameObjectsAdapter gameObjectsAdapter;
    private GameObjects objects;


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
//        mImageDetails = (TextView) view.findViewById(R.id.image_details);
        gridview = (GridView) view.findViewById(R.id.game_items_view);
        if (objects != null) {
            gameObjectsAdapter = new GameObjectsAdapter(getActivity(), this.objects);
            gridview.setAdapter(gameObjectsAdapter);
        }
        return view;
    }


//    public void setImageDetailsText(String s) {
//        mImageDetails.setText(s);
//    }

    public void setGame(GameObjects objects) {
        this.objects = objects;
        if (gridview != null) {
            gameObjectsAdapter = new GameObjectsAdapter(getActivity(), this.objects);
            gridview.setAdapter(gameObjectsAdapter);
        }
    }

    public void sendObjectsFound(String result) {
        Gson gson = new Gson();
        String[] itemsFound = gson.fromJson(result, String[].class);
        if (objects != null) {
            for (int i = 0; i < objects.getObjects().length; i++) {
                Log.e(TAG, objects.getObjectArPosition(i));
                for (String item : itemsFound) {
                    if (item.toLowerCase().equals(objects.getObjectArPosition(i).toLowerCase())) {
                        Log.e(TAG, "OBJECT FOUND");
                        if(gameObjectsAdapter != null){
                            gameObjectsAdapter.nthItemFound(i);
                        }
                    }
                }
            }
        }
    }
}
