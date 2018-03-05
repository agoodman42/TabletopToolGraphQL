package com.example.aaron.tabletoptoolgraphql;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.EditText;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String n1,loc1x, loc1y, n2, loc2x, loc2y;

    private TextView character1Name, character1X, character1Y, character2Name, character2X,
        character2Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        character1Name = (TextView) findViewById(R.id.Name1);
        character1X = (TextView) findViewById(R.id.Char1LocX);
        character1Y = (TextView) findViewById(R.id.Char1LocY);


        getCharacterLoc();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

    private void getCharacterLoc() {

        MyApolloClient.getMyApolloClient().query(
                AllCharacterLocQuery.builder().build()).enqueue(new ApolloCall.Callback<AllCharacterLocQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<AllCharacterLocQuery.Data> response) {
                Log.d(TAG, "onResponse: " + response.data().allCharacters().get(0).name());

                n1 = response.data().allCharacters().get(0).name();
                loc1x = Long.toString(response.data().allCharacters().get(0)
                        .location().xCoordinate());
                loc1y = Long.toString(response.data().allCharacters().get(0)
                        .location().yCoordinate());


                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        character1Name.setText(n1);
                        character1X.setText(loc1x);
                        character1Y.setText(loc1y);
                    }
                });
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

            }
        });

    }

    public void createNewCharacter(){
        EditText mCharacterNameText = (EditText) findViewById(R.id.newCharNameEditText);
        EditText newCharacterXLoc = (EditText) findViewById(R.id.newCharXCoord);
        EditText newCharacterYLoc = (EditText) findViewById(R.id.newCharYCoord);

//        MyApolloClient.getMyApolloClient().mutate(
//                createCharacterMutation.builder()
//                .name()
//                .location()
//                .xcoord()
//        );

    }
}
