package com.euphoria.gracelu.euphoria;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.microsoft.cognitive.textanalytics.model.request.RequestDoc;
import com.microsoft.cognitive.textanalytics.model.request.RequestDocIncludeLanguage;
import com.microsoft.cognitive.textanalytics.model.request.keyphrases_sentiment.TextRequest;
import com.microsoft.cognitive.textanalytics.model.request.language.LanguageRequest;
import com.microsoft.cognitive.textanalytics.retrofit.ServiceCall;
import com.microsoft.cognitive.textanalytics.retrofit.ServiceCallback;
import com.microsoft.cognitive.textanalytics.retrofit.ServiceRequestClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;


/**
 * Created by gracelu on 1/10/18.
 */


public class ResultsActivity extends AppCompatActivity{


    String[] tweets = new String[50];
    private ServiceCall mSentimentCall;
    private ServiceCallback mSentimentCallback;
    private ServiceRequestClient mRequest;
    private RequestDoc mDocument;
    private LanguageRequest mLanguageRequest;       // request for language detection
    private RequestDocIncludeLanguage mDocIncludeLanguage;
    private TextRequest mTextIncludeLanguageRequest;
    private String mSubscriptionKey;
    private String URLHost = "https://westcentralus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";
    static String accessKey = "67e66e4df8154d1d80c7f4816eff3cae";
    static String host = "https://westcentralus.api.cognitive.microsoft.com";

    static String path = "/text/analytics/v2.0/sentiment";

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
    }

    public String[] retrieveTweets(String collegeName){

        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        String[] temp = new String[50];
        SearchService searchService = twitterApiClient.getSearchService();
        Call<Search> call2 = searchService.tweets(collegeName, null, "en", null, null, 50, null, null, null, null);
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
                try {
                    int happy = findHappinessLevel(tweets);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            public void failure(TwitterException exception) {
                //Do something on failure
            }
        });
        return tweets;
    }

    public int findHappinessLevel(String[] tweets) throws Exception {
        final TextView resultsName = (TextView) findViewById(R.id.resultsName);
        try {
            Documents documents = new Documents ();
            for(int i = 1; i < tweets.length; i++){
                documents.add(""+i, "en", tweets[i]);
            }

            String response = GetSentiment (documents);
            int actualPercent = calculateHappiness(response);
//            resultsName.setText(actualPercent+"");
            TextView happinessIndex = (TextView) findViewById(R.id.happinessIndex);
            happinessIndex.setText(actualPercent+"%");
        }
        catch (Exception e) {
            resultsName.setText("Microsoft, we have a problem.");
        }
        return 0;
    }

    private int calculateHappiness(String response) throws JSONException {
        final TextView resultsName = (TextView) findViewById(R.id.resultsName);
        double overallTotal = 0.0;
        double[] allPercents = new double[50];
        int actualPercent = 0;
        JSONObject everything = new JSONObject(response);
        JSONArray docs = everything.getJSONArray("documents");
        int numDocs = docs.length();
        for(int i = 0; i < numDocs; i++){
            JSONObject temp = docs.getJSONObject(i);
            allPercents[i] = Double.parseDouble(temp.getString("score"));
            overallTotal += allPercents[i];
        }
        double avTotal = overallTotal/numDocs;
        actualPercent = (int)(avTotal*100);

        return actualPercent;
    }


    public static String GetSentiment (Documents documents) throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String text = new Gson().toJson(documents);
        byte[] encoded_text = text.getBytes("UTF-8");

        URL url = new URL(host+path);
        HttpsURLConnection connection;
        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/json");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(encoded_text, 0, encoded_text.length);
        wr.flush();
        wr.close();

        StringBuilder response = new StringBuilder ();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return response.toString();
    }

    class Document {
        public String id, language, text;

        public Document(String id, String language, String text){
            this.id = id;
            this.language = language;
            this.text = text;
        }
    }

    class Documents {
        public List<Document> documents;

        public Documents() {
            this.documents = new ArrayList<Document>();
        }
        public void add(String id, String language, String text) {
            this.documents.add (new Document (id, language, text));
        }
    }


}
