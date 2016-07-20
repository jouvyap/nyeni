package bravostudio.nyeni.Fragment;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

import bravostudio.nyeni.Custom.AndroidMultipartEntity;
import bravostudio.nyeni.Custom.NyeniConstant;
import bravostudio.nyeni.Custom.SquareImageView;
import bravostudio.nyeni.R;

/**
 * Created by jouvyap on 7/21/16.
 */
public class UploadVideoFragment extends Fragment {

    private View uploadVideoFragment;
    private ProgressBar mProgressBar;
    private String mFileUri;
    private long totalSize = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        uploadVideoFragment = inflater.inflate(R.layout.fragment_video_upload, container, false);

        Bundle bundle = getArguments();
        mFileUri = bundle.getString(NyeniConstant.FRAGMENT_BUNDLE_FILE_URI);

        final VideoView videoView = (VideoView) uploadVideoFragment.findViewById(R.id.video);
        videoView.setVideoPath(mFileUri);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });
        videoView.start();

        mProgressBar = (ProgressBar) uploadVideoFragment.findViewById(R.id.progressBar);

        Button buttonUpload = (Button) uploadVideoFragment.findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadFileToServer().execute();
            }
        });

        return uploadVideoFragment;
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            mProgressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            mProgressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            mProgressBar.setProgress(progress[0]);

            // updating percentage value
//            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(NyeniConstant.BASE_URL);

            try {
                AndroidMultipartEntity entity = new AndroidMultipartEntity(
                        new AndroidMultipartEntity.ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(mFileUri);

                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));

                // Extra parameters if you want to pass to server
//                entity.addPart("website",
//                        new StringBody("www.androidhive.info"));
//                entity.addPart("email", new StringBody("abc@gmail.com"));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Video berhasil di-upload", Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }

    }
}
