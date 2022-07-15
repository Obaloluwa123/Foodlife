package com.example.fooding.clients;

import android.app.Application;

import com.example.fooding.models.Ingredient;
import com.example.fooding.models.Search;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Search.class);
        ParseObject.registerSubclass(Ingredient.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("U9T87rKc7d58hGzkhwKpEjY8GQNJZjCGXWuQInz1")
                .clientKey("GmtnA5oNFejtgv56eLDnIgKRfulkRbZYw60N8JXJ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
