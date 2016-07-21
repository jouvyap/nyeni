package bravostudio.nyeni.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bravostudio.nyeni.Adapter.GridAdapter;
import bravostudio.nyeni.Custom.NyeniConstant;
import bravostudio.nyeni.Custom.SquareImageView;
import bravostudio.nyeni.MainActivity;
import bravostudio.nyeni.R;

/**
 * Created by jouvyap on 7/20/16.
 */
public class UploadFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    static final int REQUEST_AUDIO_CAPTURE = 3;
    static final int REQUEST_IMAGE_LIBRARY = 4;

    private View uploadFragmentView;
    private Uri fileUri; // to save image/video/audio location

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        uploadFragmentView =  inflater.inflate(R.layout.fragment_upload, container, false);

        LinearLayout uploadPicture = (LinearLayout) uploadFragmentView.findViewById(R.id.picture_layout);
        LinearLayout uploadVideo = (LinearLayout) uploadFragmentView.findViewById(R.id.video_layout);
        LinearLayout uploadAudio = (LinearLayout) uploadFragmentView.findViewById(R.id.audio_layout);
        LinearLayout uploadLibrary = (LinearLayout) uploadFragmentView.findViewById(R.id.library_layout);

        uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideo();
            }
        });

        uploadAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordAudio();
            }
        });

        uploadLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotos();
            }
        });

        return uploadFragmentView;
    }

    private void takePicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(REQUEST_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void recordVideo(){
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getOutputMediaFileUri(REQUEST_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void recordAudio(){
        Intent takeAudioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        fileUri = getOutputMediaFileUri(REQUEST_AUDIO_CAPTURE);
        takeAudioIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (takeAudioIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeAudioIntent, REQUEST_AUDIO_CAPTURE);
        } else {
            Toast.makeText(getActivity(), "Device tidak support", Toast.LENGTH_SHORT).show();
        }
    }

    private void choosePhotos(){
        Intent chooseImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseImageIntent.setType("image/*");
        startActivityForResult(chooseImageIntent, REQUEST_IMAGE_LIBRARY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.changeFragment(NyeniConstant.MENU_TAB.UPLOAD_PHOTO, fileUri.getPath());
        } else if(requestCode == REQUEST_VIDEO_CAPTURE && resultCode == getActivity().RESULT_OK) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.changeFragment(NyeniConstant.MENU_TAB.UPLOAD_VIDEO, fileUri.getPath());
        } else if(requestCode == REQUEST_AUDIO_CAPTURE && resultCode == getActivity().RESULT_OK){
            Log.d("JOUVY", fileUri.getPath());
        } else if(requestCode == REQUEST_IMAGE_LIBRARY && resultCode == getActivity().RESULT_OK){
            Uri selectedImage = data.getData();
            Log.d("JOUVY", selectedImage.getPath());
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.changeFragment(NyeniConstant.MENU_TAB.UPLOAD_PHOTO, selectedImage.getPath());
        }
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                NyeniConstant.SAVE_DIRECTORY);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("JOUVY", "Oops! Failed create "
                        + NyeniConstant.SAVE_DIRECTORY + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == REQUEST_IMAGE_CAPTURE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == REQUEST_VIDEO_CAPTURE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else if (type == REQUEST_AUDIO_CAPTURE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "AUD_" + timeStamp + ".mp3");
        } else{
            return null;
        }

        return mediaFile;
    }
}
