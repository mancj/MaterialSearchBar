
#Material SearchBar Android
Material Design Search Bar for Android
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MaterialSearchBar-orange.svg?style=flat)](http://android-arsenal.com/details/1/4158)
----------
This beautiful and easy to use library will help to add Lollipop Material Design SearchView in your project.

![Material Design SearchBar Android](https://github.com/mancj/MaterialSearchBar/blob/master/art/preview.gif)

----------
#How to use

**to include SearchBar to your project:**

 add this code to the the project level build.gradle file

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

add the dependency to the the app level build.gradle file 

```gradle
dependencies {
	compile 'com.github.mancj:MaterialSearchBar:0.2.1'
}

```

then add SearchBar to your activity:

```xml
<com.mancj.materialsearchbar.MaterialSearchBar
    app:speechMode="true"
    app:hint="Custom hint"
    app:maxSuggestionsCount="10"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:id="@+id/searchBar" />
```

----------

**MaterialSarchBar has the following xml attributes:**

| attribute           | description                                                                           |
|---------------------|---------------------------------------------------------------------------------------|
| hint                | Set custom prompt in the search box                                                   |
| speechMode          | If set to true, microphone icon will display instead of the search icon.              |
| maxSuggestionsCount | Specifies the maximum number of search queries stored until the activity is destroyed |
| searchIconDrawable  | Set search icon drawable resource                                                     |
| navIconDrawable     | Set navigation icon drawable resource                                                 |
| textColor           | Change text color                                                                     |
| hintColor           | Change text hint color                                                                |


----------
**public methods:**

 - `disableSearch()`
 - `enableSearch()`
 - `isSearchEnabled()`
 - `setHint(CharSequence hint)`
 - `setSpeechMode(boolean speechMode)`
 - `setOnSearchActionListener(OnSearchActionListener onSearchActionListener)`
 - `setLastSuggestions(List<String> suggestions)`
 - `getLastSuggestions()`
 - `setMaxSuggestionCount(int maxQuery)`
 - `setSearchIcon(int searchIconResId)`
 - `setNavigationIcon(int navigationIconResId)`
 - `setTextColor(int textColor)`
 - `setTextHintColor(int hintColor)`
 - `inflateMenu(int menuResource)`
 - `getMenu()`
 
----------


To save search queries when the activity is destroyed, use the method `searchBar.getLastSuggestions()` and then, to restore them use `searchBar.setLastSuggestions(List<String>);` as shown in the example below
 
#Example

Here is a simple example of using MaterialSearchBar

```java
private List<String> lastSearches;
private MaterialSearchBar searchBar;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
    searchBar.setHint("Custom hint");
    searchBar.setSpeechMode(true);
    //enable searchbar callbacks
    searchBar.setOnSearchActionListener(this);
    //restore last queries from disk
    lastSearches = loadSearchSuggestionFromDiks();
    searchBar.setLastSuggestions(list);
    //Inflate menu and setup OnMenuItemClickListener
    searchBar.inflateMenu(R.menu.main);
    searchBar.getMenu().setOnMenuItemClickListener(this);
}

@Override
protected void onDestroy() {
    super.onDestroy();
    //save last queries to disk
    saveSearchSuggestionToDisk(searchBar.getLastSuggestions());
}

@Override
public void onSearchStateChanged(boolean enabled) {
    String s = enabled ? "enabled" : "disabled";
    Toast.makeText(MainActivity.this, "Search " + s, Toast.LENGTH_SHORT).show();
}

@Override
public void onSearchConfirmed(CharSequence text) {
    startSearch(text.toString(), true, null, true);
}

@Override
public void onButtonClicked(int buttonCode) {
    switch (buttonCode){
        case MaterialSearchBar.BUTTON_NAVIGATION:
            drawer.openDrawer(Gravity.LEFT);
            break;
        case MaterialSearchBar.BUTTON_SPEECH:
            openVoiceRecognizer();
    }
}
```
