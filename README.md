
#Material SearchBar Android
Material Design Search Bar for Android

----------
This beautiful and easy to use library will help to add Lollipop Material Desing SearchView in your project.

![Material Design SearchBar Android gif](https://github.com/mancj/MaterialSearchBar/blob/master/art/ezgif-838715850.gif)
![Gif](https://github.com/mancj/MaterialSearchBar/blob/master/art/device-2016-07-20-170335.png)

----------
#How to use

**to include SearchBar to your project:**

 add this to the the project level build.gradle file

    allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}

add the dependency to the the app level build.gradle file 

    compile 'com.github.mancj:MaterialSearchBar:0.1.1'


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
| iconLeft            | Change left icon                                                                      |
| iconRight           | Change right icon                                                                     |
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
 - `setIconLeft(int iconLefttResId)`
 - `setIconRight(int iconRightResId)`
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

//called when searchbar enabled or disabled
@Override
public void onSearchStateChanged(boolean enabled) {
    String s = enabled ? "enabled" : "disabled";
    Toast.makeText(MainActivity.this, "Search " + s, Toast.LENGTH_SHORT).show();
}

//called when user confirms request
@Override
public void onSearchConfirmed(CharSequence text) {
    startSearch(text.toString(), true, null, true);
}

//called when microphone icon clicked
@Override
public void onSpeechIconSelected() {
    openVoiceRecognizer();
}
```


#TODO 
- first of all I'll add background dim behing SearchBar
