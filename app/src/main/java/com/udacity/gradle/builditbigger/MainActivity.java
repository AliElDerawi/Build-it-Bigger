package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nanodegree.displayjoking.DisplayJokingActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.util.WaitingDialog;

import java.io.IOException;

import IdlingResource.SimpleIdlingResource;


public class MainActivity extends AppCompatActivity {

    @Nullable
    public static SimpleIdlingResource mSimpleIdlingResource = null;

    public static MyApi myApiService = null;
    public static final String EXTRA_JOKE = "com.udacity.gradle.builditbigger.EXTRA_JOKE";
    private static WaitingDialog waitingDialog;
    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        getIdlingResources();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        showWaiteDialog();
        new GetJokesAsyncTask().execute();
    }


    @VisibleForTesting
    @Nullable
    public IdlingResource getIdlingResources() {
        if (mSimpleIdlingResource == null) {
            mSimpleIdlingResource = new SimpleIdlingResource();
        }
        return mSimpleIdlingResource;
    }

    public static class GetJokesAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            if (mSimpleIdlingResource != null) {
                mSimpleIdlingResource.setIdleState(false);
            }
            if (myApiService == null) {  // Only do this once
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
            try {
                return myApiService.sayHi().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            hideWaiteDialog();
            if (mSimpleIdlingResource != null) {
                mSimpleIdlingResource.setIdleState(true);
            }
            Intent intent = new Intent(mContext, DisplayJokingActivity.class);
            intent.putExtra(EXTRA_JOKE, result);
            mContext.startActivity(intent);
        }


    }

    private static void showWaiteDialog() {
        if (waitingDialog == null) {
            waitingDialog = new WaitingDialog((Activity) mContext);
            waitingDialog.showDialog();
        }
    }

    private static void hideWaiteDialog() {
        if (waitingDialog != null) {
            waitingDialog.dismissDialog();
        }
    }

}

