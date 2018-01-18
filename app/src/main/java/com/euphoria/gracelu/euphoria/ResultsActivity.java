package com.euphoria.gracelu.euphoria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;
import com.twitter.sdk.android.core.services.StatusesService;

import org.w3c.dom.Text;

import retrofit2.Call;

/**
 * Created by gracelu on 1/10/18.
 */


public class ResultsActivity extends AppCompatActivity{

    TextView resultsName = (TextView) findViewById(R.id.resultsName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Twitter.initialize(this);


        TextView happinessIndex = (TextView) findViewById(R.id.happinessIndex);
        String collegeName = getIntent().getStringExtra("collegeName");

        resultsName.setText(collegeName);
        String[] tweets = retrieveTweets(collegeName);
    }

    public String[] retrieveTweets(String collegeName){
        String[] tweets = new String[100];
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
//        StatusesService statusesService = twitterApiClient.getStatusesService();
//        Call<Tweet> call = statusesService.show(524971209851543553L, null, null, null);
//        call.enqueue(new Callback<Tweet>() {
//            @Override
//            public void success(Result<Tweet> result) {
//                //Do something with result
//            }
//
//            public void failure(TwitterException exception) {
//                //Do something on failure
//            }
//        });

        //testing
        SearchService searchService = twitterApiClient.getSearchService();
        Call<Search> call2 = searchService.tweets("Cornell", null, "en", null, null, 50, null, null, null, null);
        call2.enqueue(new Callback<Search>() {
            @Override
            public void success(Result<Search> result) {
                //do something with result
                resultsName.setText("GRACE");
            }
            public void failure(TwitterException exception) {
                //Do something on failure
            }
        });

        return tweets;
    }

    public int findHappinessLevel(String[] tweets){
        return 0;
    }

}
