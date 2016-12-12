package com.mancj.materialsearchbar;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mancj on 19.07.2016.
 */
public class MaterialSearchBar extends RelativeLayout implements View.OnClickListener,
        Animation.AnimationListener,SuggestionsAdapter.OnItemViewClickListener,
        View.OnFocusChangeListener, TextView.OnEditorActionListener {
    public static final int BUTTON_SPEECH = 1;
    public static final int BUTTON_NAVIGATION = 2;

    private LinearLayout inputContainer;
    private ImageView searchIcon;
    private ImageView arrowIcon;
    private EditText searchEdit;
    private ImageView navIcon;
    private OnSearchActionListener onSearchActionListener;
    private boolean searchEnabled;
    private boolean suggestionsVisible;
    public static final int VIEW_VISIBLE = 1;
    public static final int VIEW_INVISIBLE = 0;
    private SuggestionsAdapter adapter;
    private float destiny;

    private int searchIconRes;
    private int navIconResId;
    private CharSequence hint;
    private int maxSuggestionCount;
    private boolean speechMode;

    private int menuResource;
    private PopupMenu popupMenu;

    private int textColor;
    private int hintColor;

    private boolean navButtonEnabled;

    public MaterialSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MaterialSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialSearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        inflate(getContext(), R.layout.searchbar, this);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialSearchBar);
        searchIconRes = array.getResourceId(R.styleable.MaterialSearchBar_searchIconDrawable, -1);
        navIconResId = array.getResourceId(R.styleable.MaterialSearchBar_navIconDrawable, -1);
        hint = array.getString(R.styleable.MaterialSearchBar_hint);
        maxSuggestionCount = array.getInt(R.styleable.MaterialSearchBar_maxSuggestionsCount, 3);
        speechMode = array.getBoolean(R.styleable.MaterialSearchBar_speechMode, false);

        hintColor = array.getColor(R.styleable.MaterialSearchBar_hintColor, -1);
        textColor = array.getColor(R.styleable.MaterialSearchBar_textColor, -1);
        navButtonEnabled = array.getBoolean(R.styleable.MaterialSearchBar_navIconEnabled, false);

        destiny = getResources().getDisplayMetrics().density;
        adapter = new SuggestionsAdapter(LayoutInflater.from(getContext()));
        adapter.setListener(this);
        adapter.maxSuggestionsCount = maxSuggestionCount;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mt_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        array.recycle();

        searchIcon = (ImageView) findViewById(R.id.mt_search);
        arrowIcon = (ImageView) findViewById(R.id.mt_arrow);
        searchEdit = (EditText) findViewById(R.id.mt_editText);
        inputContainer = (LinearLayout) findViewById(R.id.inputContainer);
        navIcon = (ImageView) findViewById(R.id.mt_nav);
        findViewById(R.id.mt_clear).setOnClickListener(this);

        setOnClickListener(this);
        arrowIcon.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        searchEdit.setOnFocusChangeListener(this);
        searchEdit.setOnEditorActionListener(this);
        navIcon.setOnClickListener(this);
        postSetup();
    }

    /**
     * Inflate menu for searchBar
     * @param menuResource - menu resource
     */
    public void inflateMenu(int menuResource){
        this.menuResource = menuResource;
        if (menuResource > 0){
            ImageView menuIcon = (ImageView) findViewById(R.id.mt_menu);
            RelativeLayout.LayoutParams params = (LayoutParams) searchIcon.getLayoutParams();
            params.rightMargin = (int) (36*destiny);
            searchIcon.setLayoutParams(params);
            menuIcon.setVisibility(VISIBLE);
            menuIcon.setOnClickListener(this);
            popupMenu = new PopupMenu(getContext(), menuIcon);
            popupMenu.inflate(menuResource);
            popupMenu.setGravity(Gravity.RIGHT);
        }
    }

    /**
     * Get popup menu
     * @return PopupMenu
     */
    public PopupMenu getMenu(){
        return this.popupMenu;
    }

    private void postSetup(){
        if (searchIconRes < 0)
            searchIconRes = R.drawable.ic_magnify_black_48dp;
        setSpeechMode(speechMode);
        if (navIconResId < 0)
            navIconResId = R.drawable.ic_menu_black_24dp;
        setNavigationIcon(navIconResId);
        if (hint != null)
            searchEdit.setHint(hint);
        setupTextColors();
        setNavButtonEnabled(navButtonEnabled);
        if (popupMenu == null)
            findViewById(R.id.mt_menu).setVisibility(GONE);
    }

    private void setupTextColors(){
        if (hintColor != -1)
            searchEdit.setHintTextColor(ContextCompat.getColor(getContext(), hintColor));
        if (textColor != -1)
            searchEdit.setTextColor(ContextCompat.getColor(getContext(), textColor));
    }

    /**
     * Register listener for search bar callbacks.
     * @param onSearchActionListener the callback listener
     */
    public void setOnSearchActionListener(OnSearchActionListener onSearchActionListener) {
        this.onSearchActionListener = onSearchActionListener;
    }

    /**
     * Hides search input and close arrow
     */
    public void disableSearch(){
        searchEnabled = false;
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_right);
        out.setAnimationListener(this);
        searchIcon.setVisibility(VISIBLE);
        inputContainer.startAnimation(out);
        searchIcon.startAnimation(in);

        if (listenerExists())
            onSearchActionListener.onSearchStateChanged(false);
        if (suggestionsVisible) animateLastRequests(getListHeight(false), 0);
    }

    /**
     * Shows search input and close arrow
     */
    public void enableSearch(){
        adapter.notifyDataSetChanged();
        searchEnabled = true;
        Animation left_in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_left);
        Animation left_out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_left);
        left_in.setAnimationListener(this);
        inputContainer.setVisibility(VISIBLE);
        inputContainer.startAnimation(left_in);
        if (listenerExists()) {
            onSearchActionListener.onSearchStateChanged(true);
        }
        searchIcon.startAnimation(left_out);
    }

    private void animateLastRequests(int from, int to){
        suggestionsVisible = to > 0;
        final RelativeLayout last = (RelativeLayout) findViewById(R.id.last);
        final ViewGroup.LayoutParams lp = last.getLayoutParams();
        if (to == 0 && lp.height == 0)
            return;
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.height = (int) animation.getAnimatedValue();
                last.setLayoutParams(lp);
            }
        });
        if (adapter.getItemCount() > 0)
            animator.start();
    }

    /**
     * Set search icon drawable resource
     * @param searchIconResId icon resource id
     */
    public void setSearchIcon(int searchIconResId) {
        this.searchIconRes = searchIconResId;
        this.searchIcon.setImageResource(searchIconResId);
    }

    /**
     * Set navigation icon drawable resource
     * @param navigationIconResId icon resource id
     */
    public void setNavigationIcon(int navigationIconResId) {
        this.navIconResId = navigationIconResId;
        this.navIcon.setImageResource(navigationIconResId);
    }

    /**
     * Sets search bar hint
     * @param hint
     */
    public void setHint(CharSequence hint) {
        this.hint = hint;
        searchEdit.setHint(hint);
    }

    /**
     *sets the speechMode for the search bar.
     * If set to true, microphone icon will display instead of the search icon.
     * Also clicking on this icon will trigger the callback method onButtonClicked()
     * @see #BUTTON_SPEECH
     * @see OnSearchActionListener#onButtonClicked(int)
     * @param speechMode
     */
    public void setSpeechMode(boolean speechMode){
        this.speechMode = speechMode;
        if (speechMode){
            searchIcon.setImageResource(R.drawable.ic_microphone_black_48dp);
            searchIcon.setClickable(true);
        }else {
            searchIcon.setImageResource(searchIconRes);
            searchIcon.setClickable(false);
        }
    }

    /**
     * True if MaterialSearchBar is in speech mode
     * @return speech mode
     */
    public boolean isSpeechModeEnabled(){
        return speechMode;
    }

    /**
     * Check if search bar is in edit mode
     * @return true if search bar is in edit mode
     */
    public boolean isSearchEnabled() {
        return searchEnabled;
    }

    /**
     * Specifies the maximum number of search queries stored until the activity is destroyed
     * @param maxQuery maximum queries
     */
    public void setMaxSuggestionCount(int maxQuery){
        this.maxSuggestionCount = maxQuery;
        adapter.maxSuggestionsCount = maxQuery;
    }

    /**
     * Returns the last search queries.
     * The queries are stored only for the duration of one activity session.
     * When the activity is destroyed, the queries will be deleted.
     * To save queries, use the method getLastSuggestions().
     * To recover the queries use the method setLastSuggestions().
     * @return array with the latest search queries
     * @see #setLastSuggestions(List)
     * @see #setMaxSuggestionCount(int)
     */
    public List<String> getLastSuggestions(){
        return adapter.getSuggestions();
    }

    /**
     * Sets the array of recent search queries.
     * It is advisable to save the queries when the activity is destroyed
     * and call this method when creating the activity.
     * @param suggestions an array of queries
     * @see #getLastSuggestions()
     * @see #setMaxSuggestionCount(int)
     */
    public void setLastSuggestions(List<String> suggestions) {
        adapter.setSuggestions(suggestions);
    }

    /**
     * Set search input text color
     * @param textColor text color
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        setupTextColors();
    }

    /**
     * Set text input hint color
     * @param hintColor text hint color
     */
    public void setTextHintColor(int hintColor) {
        this.hintColor = hintColor;
        setupTextColors();
    }

    /**
     * Set navigation drawer menu icon enabled
     * @param navButtonEnabled icon enabled
     */
    public void setNavButtonEnabled(boolean navButtonEnabled) {
        this.navButtonEnabled = navButtonEnabled;
        if (navButtonEnabled){
            navIcon.setVisibility(VISIBLE);
            navIcon.setClickable(true);
            LayoutParams lp = (LayoutParams) inputContainer.getLayoutParams();
            lp.leftMargin = (int) (50 * destiny);
            inputContainer.setLayoutParams(lp);
            arrowIcon.setVisibility(GONE);
        }else {
            navIcon.setVisibility(GONE);
            navIcon.setClickable(false);
        }
    }

    /**
     * Set search text
     * @param text text
     */
    public void setText(String text){
        searchEdit.setText(text);
    }

    /**
     * Get search text
     * @return text
     */
    public String getText(){
        return searchEdit.getText().toString();
    }

    /**
     * Set CardView elevation
     * @param elevation
     */
    public void setCardViewElevation(int elevation){
        CardView cardView = (CardView) findViewById(R.id.mt_container);
        cardView.setCardElevation(elevation);
    }

    /**
     * Add text watcher to searchbar's EditText
     * @param textWatcher
     */
    public void addTextChangeListener(TextWatcher textWatcher){
        searchEdit.addTextChangedListener(textWatcher);
    }

    private boolean listenerExists(){
        return onSearchActionListener != null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == getId()){
            if (!searchEnabled)
            {
                enableSearch();
            }
        }else if (id == R.id.mt_arrow){
            disableSearch();
        }else if (id == R.id.mt_search){
            if (listenerExists())
                onSearchActionListener.onButtonClicked(BUTTON_SPEECH);
        }else if (id == R.id.mt_clear){
            searchEdit.setText("");
        }else if (id == R.id.mt_menu){
            popupMenu.show();
        }else if (id == R.id.mt_nav)
            if (listenerExists())
                onSearchActionListener.onButtonClicked(BUTTON_NAVIGATION);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (!searchEnabled){
            inputContainer.setVisibility(GONE);
            searchEdit.setText("");
        }else {
            searchIcon.setVisibility(GONE);
            searchEdit.requestFocus();
            animateLastRequests(0, getListHeight(false));
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (hasFocus)
        {
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        }
        else
        {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (listenerExists())
            onSearchActionListener.onSearchConfirmed(searchEdit.getText());
        if (suggestionsVisible)
            animateLastRequests(getListHeight(false), 0);
        adapter.addSuggestion(searchEdit.getText().toString());
        return true;
    }

    /**
     * For calculate the height change when item delete or add animation
     * false is retrurn the full height of item,
     * true is return the height of postion subtraction one
     * @param isSubtraction
     */
    private int getListHeight(boolean isSubtraction){
        if(!isSubtraction)
        return (int) ((adapter.getItemCount()*50)*destiny);
        return (int) (((adapter.getItemCount()-1)*50)*destiny);
    }

    @Override
    public void OnItemClickListener(int position,View v) {
        if(v.getTag() instanceof String) {
            searchEdit.setText((String)v.getTag());
        }
    }

    @Override
    public void OnItemDeleteListener(int position,View v) {
        if (v.getTag() instanceof String) {
            /*Order of two line should't be change,
            because sholud calculate the height of item first*/
            animateLastRequests(getListHeight(false), getListHeight(true));
            adapter.deleteSuggestion(position, (String) v.getTag());
        }
    }

    /**
     * Interface definition for MaterialSearchBar callbacks.
     */
    public interface OnSearchActionListener {
        /**
         * Invoked when SearchBar opened or closed
         * @param enabled
         */
        void onSearchStateChanged(boolean enabled);

        /**
         * Invoked when search confirmed and "search" button is clicked on the soft keyboard
         * @param text search input
         */
        void onSearchConfirmed(CharSequence text);

        /**
         * Invoked when "speech" or "navigation" buttons clicked.
         * @param buttonCode {@link #BUTTON_NAVIGATION} or {@link #BUTTON_SPEECH} will be passed
         */
        void onButtonClicked(int buttonCode);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isSearchBarVisible = searchEnabled ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.suggestionsVisible = suggestionsVisible ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.speechMode = speechMode ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.navIconResId = navIconResId;
        savedState.searchIconRes = searchIconRes;
        savedState.suggestions = getLastSuggestions();
        savedState.maxSuggestions = maxSuggestionCount;
        if (hint != null) savedState.hint = hint.toString();
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        searchEnabled = savedState.isSearchBarVisible == VIEW_VISIBLE;
        suggestionsVisible = savedState.suggestionsVisible == VIEW_VISIBLE;
        setLastSuggestions(savedState.suggestions);
        if (suggestionsVisible)
            animateLastRequests(0, getListHeight(false));
        if (searchEnabled)
        {
            inputContainer.setVisibility(VISIBLE);
            searchIcon.setVisibility(GONE);
        }
//        speechMode = savedState.speechMode == VIEW_VISIBLE;
//        navIconResId = savedState.navIconResId;
//        searchIconRes = savedState.searchIconRes;
//        hint = savedState.hint;
//        maxSuggestionCount = savedState.maxSuggestions > 0 ? maxSuggestionCount  = savedState.maxSuggestions : maxSuggestionCount;
//        postSetup();
    }

    private static class SavedState extends BaseSavedState{
        private int isSearchBarVisible;
        private int suggestionsVisible;
        private int speechMode;
        private int searchIconRes;
        private int navIconResId;
        private String hint;
        private List<String> suggestions;
        private int maxSuggestions;

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(isSearchBarVisible);
            out.writeInt(suggestionsVisible);
            out.writeInt(speechMode);

            out.writeInt(searchIconRes);
            out.writeInt(navIconResId);
            out.writeString(hint);
            out.writeList(suggestions);
            out.writeInt(maxSuggestions);
        }

        public SavedState(Parcel source) {
            super(source);
            isSearchBarVisible = source.readInt();
            suggestionsVisible = source.readInt();
            speechMode = source.readInt();

            navIconResId = source.readInt();
            searchIconRes = source.readInt();
            hint = source.readString();
            suggestions = source.readArrayList(null);
            maxSuggestions = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode()==KeyEvent.KEYCODE_BACK&&searchEnabled) {
            animateLastRequests(getListHeight(false), 0);
            disableSearch();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
