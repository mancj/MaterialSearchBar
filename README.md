
#Material SearchBar Android
Material Design Search Bar for Android

----------
This beautiful and easy to use library will help to add Lollipop Material Desing SearchView in your project.





![Material Design SearchBar Android gif](https://github.com/mancj/MaterialSearchBar/blob/master/art/ezgif-838715850.gif)
![Gif](https://github.com/mancj/MaterialSearchBar/blob/master/art/device-2016-07-20-170335.png)

----------


**to include SearchBar to your project:**

 add this to the the project level build.gradle file

    allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}

**then add SearchBar to your activity:**

    <com.mancj.materialsearchbar.MaterialSearchBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>


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