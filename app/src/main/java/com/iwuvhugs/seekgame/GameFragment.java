package com.iwuvhugs.seekgame;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


public class GameFragment extends Fragment {

    private static final String TAG = GameFragment.class.getSimpleName();

    private TextView scoreTextView;
    private GridView gridView;
    private GameObjectsAdapter gameObjectsAdapter;
    private GameObjects objects;


    public static GameFragment newInstance() {
        return new GameFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameLevels levels = new GameLevels(true);
        objects = levels.getGames().get(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        scoreTextView = (TextView) view.findViewById(R.id.score_textView);
        gridView = (GridView) view.findViewById(R.id.game_items_view);
        if (objects != null) {
            gameObjectsAdapter = new GameObjectsAdapter(getActivity(), this.objects);
            gridView.setAdapter(gameObjectsAdapter);
            gameObjectsAdapter.notifyDataSetChanged();
        }
        return view;
    }

    public void sendObjectsFound(String result) {
        Gson gson = new Gson();
        String[] itemsFound = gson.fromJson(result, String[].class);
        boolean found = false;
        if (objects != null) {
            for (int i = 0; i < objects.getSize(); i++) {
                for (String item : itemsFound) {
                    if (item.toLowerCase().equals(objects.getGameObjectAtPosition(i).getObject().toLowerCase())) {
                        found = true;
                        objects.getGameObjectAtPosition(i).setFound(true);
                        objects.score();
                        scoreTextView.setText("" + objects.getCurrentScore());
                        if (gameObjectsAdapter != null) {
                            gameObjectsAdapter.setNewUpdatedGameObjects(objects);
                        }
                    }
                }
            }

            if (!found) {
                showToast("Object is not found");
            }

            if (objects.win()) {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getActivity().sendBroadcast(new Intent(MainActivity.GAME_WON));
                        }
                    });
                }
            }
        }
    }

    /**
     * Shows a {@link Toast} on the UI thread.
     *
     * @param text The message to show
     */
    private void showToast(final String text) {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
