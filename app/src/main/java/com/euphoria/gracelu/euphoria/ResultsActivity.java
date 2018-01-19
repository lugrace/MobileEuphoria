package com.euphoria.gracelu.euphoria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;

import java.net.URI;
import java.net.URL;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.utils.URIBuilder;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.util.EntityUtils;
import retrofit2.Call;


import com.microsoft.cognitive.textanalytics.model.request.RequestDoc;
import com.microsoft.cognitive.textanalytics.model.request.keyphrases_sentiment.TextRequest;
import com.microsoft.cognitive.textanalytics.model.request.RequestDocIncludeLanguage;
import com.microsoft.cognitive.textanalytics.model.request.language.LanguageRequest;
import com.microsoft.cognitive.textanalytics.model.response.keyphrases.KeyPhrasesResponse;
import com.microsoft.cognitive.textanalytics.model.response.language.LanguageResponse;
import com.microsoft.cognitive.textanalytics.model.response.sentiment.SentimentResponse;
import com.microsoft.cognitive.textanalytics.retrofit.ServiceCall;
import com.microsoft.cognitive.textanalytics.retrofit.ServiceCallback;
import com.microsoft.cognitive.textanalytics.retrofit.ServiceRequestClient;

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
//        AsyncHttpClient client = new AsyncHttpClient();
//        String endpoint = "https://westcentralus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";
//        String accessKey = "90fa1d8c5db046429b4db9b51ba39f56";
//        client.setBasicAuth("Ocp-Apim-Subscription-Key",accessKey);
//        client.get(endpoint, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onStart() {
//                // called before request is started
//                resultsName.setText("start");
//            }
//
////            @Override
////            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
////                // called when response HTTP status is "200 OK"
////                resultsName.setText("grace!");
////            }
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//                resultsName.setText("grace!");
//            }
//
////            @Override
////            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
////                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
////                resultsName.setText("rip!");
////            }
//
//            @Override
//            public void onRetry(int retryNo) {
//                // called when request is retried
//                resultsName.setText("retry!");
//            }
//        });
        resultsName.setText("GRACE");
        String response = GetSentiment("lol");
        resultsName.setText(prettify (response));
        return 0;
    }

    public String GetSentiment(String tweetText) throws Exception {
//        String text = new Gson().toJson(tweetText);
//        byte[] encoded_text = text.getBytes("UTF-8");
        String accessKey = "90fa1d8c5db046429b4db9b51ba39f56";
//
        URL url = new URL("https://westcentralus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment");
//        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Content-Type", "text/json");
//        connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
//        connection.setDoOutput(true);
//
//        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//        wr.write(encoded_text, 0, encoded_text.length);
//        wr.flush();
//        wr.close();
//
//        StringBuilder response = new StringBuilder ();
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(connection.getInputStream()));
//        String line;
//        while ((line = in.readLine()) != null) {
//            response.append(line);
//        }
//        in.close();
//
//        return response.toString();
        final TextView resultsName = (TextView) findViewById(R.id.resultsName);

        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment");


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", accessKey);


            // Request body
            StringEntity reqEntity = new StringEntity("{body}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                System.out.println(EntityUtils.toString(entity));
                resultsName.setText(EntityUtils.toString(entity));
            }
            resultsName.setText("oops");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            resultsName.setText("EXTRA OOPS");
        }
        return "gracegrace";
    }

    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(json_text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

}
