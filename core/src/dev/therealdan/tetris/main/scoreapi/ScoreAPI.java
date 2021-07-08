package dev.therealdan.tetris.main.scoreapi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpRequestHeader;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreAPI implements Net.HttpResponseListener {

    private Json json;

    private String scoreboard;
    private String publicKey;
    private String endpoint;

    private List<Score> scores = new ArrayList<>();

    public ScoreAPI(Preferences preferences) {
        json = new Json();
        json.setIgnoreUnknownFields(true);

        scoreboard = preferences.getString("ScoreAPI.Scoreboard", "tetris");
        publicKey = preferences.getString("ScoreAPI.PublicKey", "scbadver");
        endpoint = preferences.getString("ScoreAPI.Endpoint", "https://therealdan.dev/umbraco/surface/score");
    }

    public void retrieveScores() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendRequest(Net.HttpMethods.GET, endpoint + "/getscores",
                        "scoreboard=" + scoreboard +
                                "&publicKey=" + publicKey);
            }
        }).start();
    }

    public void postScore(final String name, final int score) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendRequest(Net.HttpMethods.POST, endpoint + "/addscore",
                        "scoreboard=" + scoreboard +
                                "&publicKey=" + publicKey +
                                "&score=" + score +
                                "&name=" + name);
            }
        }).start();
    }

    private void sendRequest(String httpMethod, String url, String content) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(httpMethod).url(url).content(content).build();
        httpRequest.setHeader(HttpRequestHeader.UserAgent, "java-libgdx-snake-client");
        Gdx.net.sendHttpRequest(httpRequest, this);
    }

    @Override
    public void cancelled() {

    }

    @Override
    public void failed(Throwable t) {

    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        try {
            ScoresResponse scoresResponse = json.fromJson(ScoresResponse.class, httpResponse.getResultAsString());
            scores.clear();
            scores.addAll(Arrays.asList(scoresResponse.Scores));
        } catch (Exception e) {
            //
        }
    }

    public List<Score> getScores() {
        return scores;
    }
}