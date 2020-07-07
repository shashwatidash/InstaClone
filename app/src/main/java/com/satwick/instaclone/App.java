package com.satwick.instaclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("xnevvmsQcWgAq0EB3rA5rUZl5d9PXzcKZUxJtxYh")
                .clientKey("GjHSeRnbqYCZrszfAG4OR19UGguMJrUWvMWFg2yy")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
