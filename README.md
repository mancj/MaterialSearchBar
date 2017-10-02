
#Material SearchBar Android
Material Design Search Bar for Android
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MaterialSearchBar-orange.svg?style=flat)](http://android-arsenal.com/details/1/4158)
----------
This beautiful and easy to use library will help to add Lollipop Material Design SearchView in your project.

<img src="/art/preview.gif" width="400">
<img src="/art/pv1.png" width="400">
<img src="/art/pv2.png" width="400">
<img src="/art/pv3.png" width="400">
<img src="/art/pv4.png" width="400">
<img src="/art/pv5.png" width="400">

----------

# See our [Wiki](https://github.com/mancj/MaterialSearchBar/wiki)


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
	compile 'com.github.mancj:MaterialSearchBar:0.7.5'
}

```

then add SearchBar to your activity:

```xml
<com.mancj.materialsearchbar.MaterialSearchBar
    style="@style/MaterialSearchBarLight"
    app:mt_speechMode="true"
    app:mt_hint="Custom hint"
    app:mt_maxSuggestionsCount="10"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:id="@+id/searchBar" />
```

----------

**MaterialSearchBar has the following xml attributes:**

| Attribute           | Description                                                                           |
|---------------------|---------------------------------------------------------------------------------------|
| mt_speechMode          | if set to true, microphone icon will be displayed instead of search icon    	 	 |
| mt_maxSuggestionsCount | specifies the max number of search queries stored                                     |
| mt_navIconEnabled      | set navigation icon enabled                                                           |
| mt_roundedSearchBarEnabled | use capsule shaped searchbar on v21+ and revert to default on lower               |
| mt_dividerColor        | set the colors of the suggestion and menu dividers   				 |
| mt_searchBarColor      | set the main color of the searchbar                                                   |
| mt_menuIconDrawable    | set drawable of the menu icon                                                         |
| mt_searchIconDrawable  | set drawable of the search icon when speech mode is false                             |
| mt_speechIconDrawable  | set drawable of the speech icon when speech mode is true                              |
| mt_backIconDrawable    | set drawable of the back arrow icon                                                   |
| mt_clearIconDrawable   | set drawable of the clear icon                                                        |
| mt_navIconTint         | set tint color of nav/back animated icon                                              |
| mt_menuIconTint        | set tint color of the menu icon                                                       |
| mt_searchIconTint      | set tint color search/speech icon                                                     |
| mt_backIconTint        | set tint color of the back arrow icon                                                 |
| mt_clearIconTint       | set tint color of the clear icon                                                      |
| mt_navIconUseTint      | should the animated nav icon use tint color                                           |
| mt_menuIconUseTint     | should the menu icon use the tint color                                               |
| mt_searchIconUseTint   | should the search/speech icon use the tint color                                      |
| mt_backIconUseTint     | should the back icon use the tint color                                               |
| mt_clearIconUseTint    | should the clear icon use the tint color                                              |
| mt_hint                | set the text of the hint when the searchbar is focused and search query is empty      |
| mt_placeholder         | set the placeholder text when the MaterialSearchBar is not focused                    |
| mt_textColor           | set text color                                                                        |
| mt_hintColor           | set hint color                                                                        | 
| mt_placeholderColor    | set placeholder color                                                                 |
| mt_textCursorTint      | set text cursors tint                                                                 |
| mt_leftTextSelectorDrawable | set left text selector drawable                                                  |
| mt_middleTextSelectorDrawable | set middle text selector drawable                                              |
| mt_rightTextSelectorDrawable | set right text selector drawable                                                |
| mt_leftTextSelectorTint | set left text selector tint color                                                    |
| mt_middleTextSelectorTint | set middle text selector tint color                                                |
| mt_rightTextSelectorTint | set right text selector tint color                                                  |
| mt_handlesTintEnabled  | should text selectors use tint color                                                  |
| mt_highlightedTextColor | set the text highlight tint color                                                    |

----------
**public methods:**

 - `addTextChangeListener(TextWatcher textWatcher)`
 - `clearSuggestions()`
 - `disableSearch()`
 - `enableSearch()`
 - `getLastSuggestions()`
 - `getMenu()`
 - `getText()`
 - `hideSuggestionList()`
 - `inflateMenu(int menuResource)`
 - `inflateMenu(int menuResource, int icon)`
 - `isSearchEnabled()`
 - `isSpeechModeEnabled()`
 - `isSuggestionsVisible()`
 - `setArrowIcon(int arrowIconResId)`
 - `setArrowIconTint(int arrowIconTint)`
 - `setCardViewElevation(int elevation)`
 - `setClearIcon(int clearIconResId)`
 - `setClearIconTint(int clearIconTint)`
 - `setCustomSuggestionAdapter(SuggestionsAdapter suggestionAdapter)`
 - `setDividerColor(int dividerColor)`
 - `setHint(CharSequence hintText)`
 - `setIconRippleStyle(boolean borderlessRippleEnabled)`
 - `setLastSuggestions(List suggestions)`
 - `setMaxSuggestionCount(int maxSuggestionsCount)`
 - `setMenuDividerEnabled(boolean menuDividerEnabled)`
 - `setMenuIcon(int menuIconResId)`
 - `setMenuIconTint(int menuIconTint)`
 - `setNavButtonEnabled(boolean navButtonEnabled)`
 - `setNavIconTint(int navIconTint)`
 - `setOnSearchActionListener(OnSearchActionListener onSearchActionListener)`
 - `setPlaceHolder(CharSequence placeholder)`
 - `setPlaceHolderColor(int placeholderColor)`
 - `setRoundedSearchBarEnabled(boolean roundedSearchBarEnabled)`
 - `setSearchIcon(int searchIconResId)`
 - `setSearchIconTint(int searchIconTint)`
 - `setSpeechModeEnabled(boolean speechMode)`
 - `setSuggestionsClickListener(SuggestionsAdapter.OnItemViewClickListener listener)`
 - `setText(String text)`
 - `setTextColor(int textColor)`
 - `setTextHighlightColor(int highlightedTextColor)`
 - `setTextHintColor(int hintColor)`
 - `showSuggestions()`
 - `updateLastSuggestions(List suggestions)`

----------
**Styling Material SearchBar**

**Custom Style - styles.xml**
Create a custom style and use one of the provided styles as the parent.

```xml
Provided Styles are: MaterialSearchBarLight and MaterialSearchBarDark

Example:
<style name="MyCustomTheme" parent="MaterialSearchBarLight">
    <item name="mt_searchBarColor">@color/searchBarPrimaryColor</item>
        <item name="mt_dividerColor">@color/searchBarDividerColor</item>
        <item name="mt_navIconTint">@color/searchBarNavIconTintColor</item>
        <item name="mt_searchIconTint">@color/searchBarSearchIconTintColor</item>
        <item name="mt_clearIconTint">@color/searchBarClearIconTintColor</item>
        <item name="mt_menuIconTint">@color/searchBarMenuIconTintColor</item>
        <item name="mt_backIconTint">@color/searchBarBackIconTintColor</item>
        <item name="mt_textCursorTint">@color/searchBarCursorColor</item>
        <item name="mt_textColor">@color/searchBarTextColor</item>
        <item name="mt_hintColor">@color/searchBarHintColor</item>
        <item name="mt_placeholderColor">@color/searchBarPlaceholderColor</item>
        <item name="mt_highlightedTextColor">@color/searchBarTextHighlightColor</item>
        <item name="mt_leftTextSelectorTint">@color/leftTextSelectorColor</item>
        <item name="mt_middleTextSelectorTint">@color/middleTextSelectorColor</item>
        <item name="mt_rightTextSelectorTint">@color/rightTextSelectorColor</item>
        <item name="mt_leftTextSelectorDrawable">@drawable/text_select_handle_left_mtrl_alpha_mtrlsearch</item>
        <item name="mt_middleTextSelectorDrawable">@drawable/text_select_handle_middle_mtrl_alpha_mtrlsearch</item>
        <item name="mt_rightTextSelectorDrawable">@drawable/text_select_handle_right_mtrl_alpha_mtrlsearch</item>
        <item name="mt_handlesTintEnabled">true</item>
    </style>
```
**OR**

**Custom Colors - colors.xml** 
Simply set/change these colors(or some) and you have your custom style.
```xml
    //Material SearchBar Light Theme Colors
    <color name="searchBarIconColor">#3a3a3a</color>
    //Base
    <color name="searchBarPrimaryColor">#FFFFFF</color>
    <color name="searchBarCursorColor">#8000a1ff</color>
    <color name="searchBarDividerColor">#1F000000</color>

    //Icons
    <color name="searchBarNavIconTintColor">@color/searchBarIconColor</color>
    <color name="searchBarMenuIconTintColor">@color/searchBarIconColor</color>
    <color name="searchBarSearchIconTintColor">@color/searchBarIconColor</color>
    <color name="searchBarClearIconTintColor">@color/searchBarIconColor</color>
    <color name="searchBarBackIconTintColor">@color/searchBarIconColor</color>

    //Text
    <color name="searchBarTextColor">#DE000000</color>
    <color name="searchBarHintColor">#42000000</color>
    <color name="searchBarPlaceholderColor">#8A000000</color>
    <color name="searchBarTextHighlightColor">#8000a1ff</color>
    <color name="leftTextSelectorColor">#00a1ff</color>
    <color name="middleTextSelectorColor">#00a1ff</color>
    <color name="rightTextSelectorColor">#00a1ff</color>

    //Material SearchBar Dark Theme Colors
    <color name="searchBarIconColorDark">#00a1ff</color>
    //Base
    <color name="searchBarPrimaryColorDark">#303030</color>
    <color name="searchBarCursorColorDark">@color/searchBarIconColorDark</color>
    <color name="searchBarDividerColorDark">#1FFFFFFF</color>

    //Icons
    <color name="searchBarNavIconTintColorDark">@color/searchBarIconColorDark</color>
    <color name="searchBarMenuIconTintColorDark">@color/searchBarIconColorDark</color>
    <color name="searchBarSearchIconTintColorDark">@color/searchBarIconColorDark</color>
    <color name="searchBarClearIconTintColorDark">@color/searchBarIconColorDark</color>
    <color name="searchBarBackIconTintColorDark">@color/searchBarIconColorDark</color>

    //Text
    <color name="searchBarTextColorDark">#DEFFFFFF</color>
    <color name="searchBarHintColorDark">#42FFFFFF</color>
    <color name="searchBarPlaceholderColorDark">#8AFFFFFF</color>
    <color name="searchBarTextHighlightColorDark">#BF00a1ff</color>
    <color name="leftTextSelectorColorDark">@color/searchBarIconColorDark</color>
    <color name="middleTextSelectorColorDark">@color/searchBarIconColorDark</color>
    <color name="rightTextSelectorColorDark">@color/searchBarIconColorDark</color>
```
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
    lastSearches = loadSearchSuggestionFromDisk();
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


More [Examples](https://github.com/mancj/MaterialSearchBar/tree/master/app/src/main/java/com/mancj/example)
