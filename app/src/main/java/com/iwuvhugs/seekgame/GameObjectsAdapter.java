package com.iwuvhugs.seekgame;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


public class GameObjectsAdapter extends BaseAdapter {

    private static final String TAG = GameObjectsAdapter.class.getSimpleName();

    private Context context;
    private GameObjects objects;
    private LayoutInflater inflater;

    public GameObjectsAdapter(Context context, GameObjects objects) {
        this.context = context;
        this.objects = objects;
        Log.e(TAG, objects.toString());
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.getObjects().length;
    }

    @Override
    public Object getItem(int position) {
        return objects.getObjectArPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.game_object_layout, parent, false);

            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(objects.getObjectArPosition(position));

        return convertView;
    }

    public void nthItemFound(int i) {

    }

    class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }
}
