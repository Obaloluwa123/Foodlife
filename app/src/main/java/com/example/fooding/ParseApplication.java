package com.example.fooding;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("U9T87rKc7d58hGzkhwKpEjY8GQNJZjCGXWuQInz1")
                .clientKey("GmtnA5oNFejtgv56eLDnIgKRfulkRbZYw60N8JXJ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
