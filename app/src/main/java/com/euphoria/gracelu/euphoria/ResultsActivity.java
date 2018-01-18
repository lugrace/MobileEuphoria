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

import retrofit2.Call;

/**
 * Created by gracelu on 1/10/18.
 */


public class ResultsActivity extends AppCompatActivity{


    String[] tweets = new String[50];
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Twitter.initialize(this);

        TextView resultsName = (TextView) findViewById(R.id.resultsName);
        TextView happinessIndex = (TextView) findViewById(R.id.happinessIndex);
        String collegeName = getIntent().getStringExtra("collegeName");

        resultsName.setText(collegeName);
        String[] tweets = retrieveTweets(collegeName);
//        resultsName.setText(tweets[26]);
    }

    public String[] retrieveTweets(String collegeName){

        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        String[] temp = new String[50];
        //testing
        SearchService searchService = twitterApiClient.getSearchService();
        Call<Search> call2 = searchService.tweets("Cornell", null, "en", null, null, 50, null, null, null, null);
        call2.enqueue(new Callback<Search>() {
            @Override
            public void success(Result<Search> result) {
                //do something with result
                TextView resultsName = (TextView) findViewById(R.id.resultsName);
                int i = 0;
                for(Tweet tweet : result.data.tweets){
                    tweets[i] = tweet.text;
                    i++;
                }
                int happy = findHappinessLevel(tweets);
            }
            public void failure(TwitterException exception) {
                //Do something on failure
            }
        });
        return tweets;
    }

    public int findHappinessLevel(String[] tweets){
        TextView resultsName = (TextView) findViewById(R.id.resultsName);
        resultsName.setText(tweets[3]);
        return 0;
    }

}
