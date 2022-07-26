# FoodLife

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
FooodLife provides tasty dish recipe that can be made from specific ingredients available

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category: Food**
- **Mobile: Users can search for ingredients in their fridge and see recipes with those ingredients**
- **Story: This answers the question of "what can I cook with the ingredients I have? Users will be able to know the dish to cook with their available ingrediemts "**
- **Market: A lot of people have something at home in their fridge, but don't know what to cook**
- **Habit:this app will be addictive because a lot of people want to know what recipe they can cook with the ingredients they have.**
- **Scope: The app most basic feature is that it allows user to know what to cook and**

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
* [] User can see custom launch screen
* [] User can sign up for a new account
* [] User can log into their account
* [] User can log out of their account
* [] User can search for recipes based on ingredients
* [] User can see dish recipe that can be made
* [] User can see steps on how to prepare the recipe
* [] User can add ingredients to their Ingredient list
* [] User ingredients can be used to get suggested recipes


**Optional Nice-to-have Stories(Stretch)**
* [] User can save favorite recipes
* [] User can take picture of recipe
* [] User can filter recipes based on diet and omitting allergies.
* [] User can share pictures of recipe taken with their friends on Facebook
* [] Recipe search autocompletes
* [] API call information is cached and can be seen offline
* [] Food/Barcode scanner for ingredients to see list of recipes that contain the scanned ingredients.

### 2. Screen Archetypes

* Home 
   * User can see recipes they prepared  
* Ingredient List
   * User can add ingredients they have 
* Search
   * This will allow user to search for recipes
* Favorite
   * This will allow users to save their profile 
* Profile
   * This will allow users to see their profile 
    * ...
### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home screen - Dish recipe screen
*Ingredients List Screen
* Search screen - Recipe Screen
* Favorite recipe screen
* Profile Screen

**Flow Navigation** (Screen to Screen)

* Login  Screen
   * User Feed
   * ...
* Register Screen
   * User Feed
   * ...
   
*Ingredients List Screen
   *User can add ingredients
   *User can remove ingredients
   
 * * Search Screen 
   *user can search for recipe based on available ingredients
 * * Recipe Screen
   * Shows recipe based on available ingredients in the fridge
   
 * * Favorite Screen 
   *user can save favorite recipe
   
* Profile Screen
   * User Feed
   * ...

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="Sketch.jpg" width=600>

### [] Tools that will be used:
* Back4App
* Spoonacular


### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models

### User
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user|
   | username      | STRING   | unique username for the user |
   | password      | String   | password for user login |
   | profilePic    | File     | image for user profile |
   
### checkingredients/getIngredientsFromApi
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | ingredientId  |    INT   | unique id for predefined ingredients|
   | ingredientName| STRING   | name of predefined ingredient |
   | isChecked     | BOOL    | boolean value to check if ingredients has been selected |
   
   
### chooseIngredients
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | ingredientId  | INT     | unique identifier for my list of predefined ingredient |
   | ingredientName| STRING  | name for pre-defined ingredients |
   
### getIngredientsFomApi
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | ingredientId  | INT     | unique identifier for my list of predefined ingredient |
   | ingredientName| STRING  | name for pre-defined ingredients |
   
   
### getRecipeFromAPI
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectid      | STRING   | unique identifier for the recipe|
   | recipeid      | Integer  | get id in spoonacular database  |
   | title         | STRING   | title of recipe|
   | imageUrl      | String   | url to recipe database |
   | Summary       | STRING   | description of recipe|
   | createdAt     | DateTime | date recipe was created |
   | updatedAt     | DateTime | date when recipe was last updated|
   
### Networking
#### List of network requests by screen
   - Home Feed Screen
      - (Read/GET) Query logged in user object
         ```swift
         ```
      - (Create/POST) Create a new user
   - Create Post Screen
      - (Create/POST) Create a new post object
   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile image
#### [OPTIONAL:] Existing API Endpoints
##### Spoonacular
- Base URL - (https://spoonacular.com/recipe-api/docs)
   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | /recipes/complexSearch?includeIngredients={includeIngredients} | gets all recipes that include certain ingredients
    `GET`    | /recipes/{id}/information | gets recipe details
    
