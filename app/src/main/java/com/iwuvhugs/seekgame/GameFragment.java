package com.iwuvhugs.seekgame;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameFragment extends Fragment {

    private static final String TAG = GameFragment.class.getSimpleName();


    private TextView mImageDetails;
    private ImageView mMainImage;

//    private BroadcastReceiver photoReciever;


    public static GameFragment newInstance() {
        return new GameFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        photoReciever = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.e(TAG, "Hey, It's Kirill again from Game Fragment");
////                Toast.makeText(getActivity(), "PHOTO CAPURED", Toast.LENGTH_LONG).show();
//                setPictureIntoImageView();
//            }
//        };
//        IntentFilter intFilt = new IntentFilter(MainActivity.PHOTO_READY);
//        getActivity().registerReceiver(photoReciever, intFilt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
//        view.findViewById(R.id.picture).setOnClickListener(this);
//        mTextureView = (TextureView) view.findViewById(R.id.texture);
//        Uri uri = FileContentProvider.constructUri(file);
        mImageDetails = (TextView) view.findViewById(R.id.image_details);
        mMainImage = (ImageView) view.findViewById(R.id.main_image);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    public void setPictureIntoImageView() {
        if (mMainImage != null && mImageDetails != null) {
//            Uri uri = Uri.fromFile(new File(getActivity().getExternalFilesDir(null), "pic.jpg"));
//
//            if (uri != null) {
//                try {
//                    // scale the image to 800px to save on bandwidth
//                    Bitmap bitmap = Utils.scaleBitmapDown(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri), 1200);
//
////                    getActivity().callCloudVision(bitmap);
//                    mMainImage.setImageBitmap(bitmap);
//
//                } catch (IOException e) {
//                    Log.d(TAG, "Image picking failed because " + e.getMessage());
////                    Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Log.d(TAG, "Image picker gave us a null image.");
////                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
//            }
        }
    }


    public void setImageBitmap(Bitmap imageBitmap) {
        mMainImage.setImageBitmap(imageBitmap);
    }

    public void setImageDetailsText(String s) {
        mImageDetails.setText(s);
    }
}
