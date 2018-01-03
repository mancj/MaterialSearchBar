# Material SearchBar Android
[![](https://jitpack.io/v/simonebortolin/MaterialSearchBar.svg)](https://jitpack.io/#simonebortolin/MaterialSearchBar)

Android 3rd party library to make a MaterialSearchBar in a easy mode. 
This beautiful and easy to use library will help to add a Material Design SearchView in your project.

## Screenshots
<img src="https://github.com/simonebortolin/MaterialSearchBar/blob/master/image_1.png" alt="" width="200px"></a>
<img src="https://github.com/simonebortolin/MaterialSearchBar/blob/master/image_2.png" alt="" width="200px"></a>
<img src="https://github.com/simonebortolin/MaterialSearchBar/blob/master/image_3.png" alt="" width="200px"></a>

## Installation

Step 1. Add it in your **root** build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

```gradle
dependencies {
	compile 'com.github.simonebortolin:MaterialSearchBar:0.8.0'
}

```

## How to use this library

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

| Attribute              | Description                                                                           |
|------------------------|---------------------------------------------------------------------------------------|
| mt_speechMode          | if set to true, microphone icon will be displayed instead of search icon    	 	     |
| mt_maxSuggestionsCount | specifies the max number of search queries stored                                     |
| mt_navIconEnabled      | set navigation icon enabled                                                           |
| mt_roundedSearchBarEnabled | use capsule shaped searchbar on v21+ and revert to default on lower               |
| mt_dividerColor        | set the colors of the suggestion and menu dividers   				                 |
| mt_searchBarColor      | set the main color of the searchbar                                                   |
| mt_menuIconDrawable    | set drawable of the menu icon                                                         |
| mt_searchIconDrawable  | set drawable of the search icon when speech mode is false                             |
| mt_speechIconDrawable  | set drawable of the speech icon when speech mode is true                              |
| mt_backIconDrawable    | set drawable of the back arrow icon                                                   |
| mt_clearIconDrawable   | set drawable of the clear icon                                                        |
| mt_navIconDrawable     | set drawable of the nav icon                                                          |
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
 - `setNavIcon(int navIconResId)`
 - `setNavIcon()`
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

To save search queries when the activity is destroyed, use the method `searchBar.getLastSuggestions()` and then, to restore them use `searchBar.setLastSuggestions(List<String>);` as shown in the example below

#Example

Here is a simple example of using MaterialSearchBar


    searchBar = findViewById(R.id.searchBar);
    searchBar.inflateMenu(R.menu.main);
    searchBar.setText("Hello World!");
    searchBar.setCardViewElevation(10);
    searchBar.setUpButtonEnabled(false);
    searchBar.addTextChangeListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    });
    searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
        @Override
        public void onSearchStateChanged(boolean enabled) {

        }

        @Override
        public void onSearchConfirmed(CharSequence text) {

        }

        @Override
        public void onButtonClicked(int buttonCode) {
            switch (buttonCode) {
                case MaterialSearchBar.BUTTON_NAVIGATION:
                    break;
                case MaterialSearchBar.BUTTON_SPEECH:
                    break;
                case MaterialSearchBar.BUTTON_BACK:
                    searchBar.disableSearch();
                    break;
            }
        }
    });
        
However, the library is very customizable, and you can easily change the view of the adapter.


## Credits


I thank all the authors of the various commits that I have included in my fork


## License


The MIT License (MIT)

Copyright (c) 2016 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

Please let me know if you use the library in your applications. 
I want to collect and publish this list.
