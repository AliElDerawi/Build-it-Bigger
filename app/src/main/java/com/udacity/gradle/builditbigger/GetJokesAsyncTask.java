package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nanodegree.displayjoking.DisplayJokingActivity;
import com.nanodegree.jokingsource.JokesTellerClass;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import static com.udacity.gradle.builditbigger.MainActivity.mSimpleIdlingResource;

public class GetJokesAsyncTask extends AsyncTask<Context, Void, String> {
    public static MyApi myApiService = null;
    private Context mContext;
    public static final String EXTRA_JOKE = "com.udacity.gradle.builditbigger.EXTRA_JOKE";

//    @Override
//    protected String doInBackground(Context... contexts) {
//        if (myApiService == null) {  // Only do this once
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    // options for running against local devappserver
//                    // - 10.0.2.2 is localhost's IP address in Android emulator
//                    // - turn off compression when running against local devappserver
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });
//
//            myApiService = builder.build();
//        }
//
//        mContext = contexts[0];
//
//        try {
//            return myApiService.getRandomJoke().execute().getData();
//        } catch (IOException e){
//            return e.getMessage();
//        }
//    }

    @Override
    protected String doInBackground(Context... params) {
        if (mSimpleIdlingResource != null){
            mSimpleIdlingResource.setIdleState(false);
        }
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        mContext = params[0];
        String name = JokesTellerClass.getRandomJoke();

        try {
            return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (mSimpleIdlingResource != null){
            mSimpleIdlingResource.setIdleState(true);
        }
        Intent intent = new Intent(mContext, DisplayJokingActivity.class);
        intent.putExtra(EXTRA_JOKE,result);
        mContext.startActivity(intent);
    }
}
