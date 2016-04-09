package com.iwuvhugs.seekgame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     *
     * Register for free access to Cloud API here
     * https://cloud.google.com/vision/
     * Also create CloudVisionCredentials.java and add
     * public static final String CLOUD_VISION_API_KEY = "<your API key>"
     * inside that class.
     *
     */
    private static final String CLOUD_VISION_API_KEY = CloudVisionCredentials.CLOUD_VISION_API_KEY;

    public static final String PHOTO_READY = "com.iwuvhugs.seekgame.PHOTO_READY";

    private PhotoFragment photoFragment = null;
    private GameFragment gameFragment = null;

    private BroadcastReceiver photoReceiver;
    private IntentFilter intFilt = new IntentFilter(PHOTO_READY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (null == savedInstanceState) {

            gameFragment = GameFragment.newInstance();
            photoFragment = PhotoFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.game_fragment, gameFragment)
                    .commit();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, photoFragment)
                    .commit();


//        }

        photoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "Hey, It's Kirill again from Game Fragment");
//                Toast.makeText(getActivity(), "PHOTO CAPURED", Toast.LENGTH_LONG).show();
                uploadImage();
            }
        };

        registerReceiver(photoReceiver, intFilt);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(photoReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(photoReceiver, intFilt);
    }

    private void uploadImage() {
        Uri uri = Uri.fromFile(new File(this.getExternalFilesDir(null), "pic.jpg"));

            if (uri != null) {
                try {
                    // scale the image to 800px to save on bandwidth
                    Bitmap bitmap = Utils.scaleBitmapDown(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri), 1200);

                    callCloudVision(bitmap);
                    if(gameFragment != null) {
                        gameFragment.setImageBitmap(bitmap);
                    }

                } catch (IOException e) {
                    Log.d(TAG, "Image picking failed because " + e.getMessage());
//                    Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
                }
            } else {
                Log.d(TAG, "Image picker gave us a null image.");
//                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }

    }


    private void callCloudVision(final Bitmap bitmap) throws IOException {
        // Switch text to loading
        //TODO
//        mImageDetails.setText("Wait until photo is recognizing");
        if(gameFragment != null) {
            gameFragment.setImageDetailsText("Wait until photo is recognizing");
        }


        // Do the real work in an async task, because we need to use the network anyway
        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(new
                            VisionRequestInitializer(CLOUD_VISION_API_KEY));
                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        // Add the image
                        Image base64EncodedImage = new Image();
                        // Convert the bitmap to a JPEG
                        // Just in case it's a format that Android understands but Cloud Vision
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();

                        // Base64 encode the JPEG
                        base64EncodedImage.encodeContent(imageBytes);
                        annotateImageRequest.setImage(base64EncodedImage);

                        // add the features we want
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature labelDetection = new Feature();
                            labelDetection.setType("LABEL_DETECTION");
                            labelDetection.setMaxResults(10);
                            add(labelDetection);
                        }});

                        // Add the list of one thing to the request
                        add(annotateImageRequest);
                    }});

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    // Due to a bug: requests to Vision API containing large images fail when GZipped.
                    annotateRequest.setDisableGZipContent(true);
                    Log.d(TAG, "created Cloud Vision request object, sending request");

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response);

                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to make API request because of other IOException " +
                            e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
                if(gameFragment != null) {
                    gameFragment.setImageDetailsText(result);
                }
            }
        }.execute();
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        String message = "I found these things:\n\n";

        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                message += String.format("%.3f: %s", label.getScore(), label.getDescription());
                message += "\n";
            }
        } else {
            message += "nothing";
        }

        return message;
    }


//    public void uploadImage(Uri uri) {
//        if (uri != null) {
//            try {
//                // scale the image to 800px to save on bandwidth
//                Bitmap bitmap = Utils.scaleBitmapDown(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), 1200);
//
//                callCloudVision(bitmap);
//                //TODO
////                mMainImage.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                Log.d(TAG, "Image picking failed because " + e.getMessage());
////                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Log.d(TAG, "Image picker gave us a null image.");
////            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
//        }
//    }
}
