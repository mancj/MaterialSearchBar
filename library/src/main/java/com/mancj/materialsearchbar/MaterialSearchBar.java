package com.mancj.materialsearchbar;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
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
        Animation.AnimationListener,
        View.OnFocusChangeListener, TextView.OnEditorActionListener {
    private LinearLayout inputContainer;
    private ImageView searchIcon;
    private ImageView arrowIcon;
    private EditText searchEdit;
    private OnSearchActionListener onSearchActionListener;
    private boolean searchEnabled;
    private boolean suggestionsVisible;
    public static final int VIEW_VISIBLE = 1;
    public static final int VIEW_INVISIBLE = 0;
    private SuggestionsAdapter adapter;
    private float destiny;

    private int iconRightResId;
    private int iconLefttResId;
    private CharSequence hint;
    private int maxSuggestionCount;
    private boolean speechMode;

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
        iconRightResId = array.getResourceId(R.styleable.MaterialSearchBar_iconRight, -1);
        iconLefttResId = array.getResourceId(R.styleable.MaterialSearchBar_iconLeft, -1);
        hint = array.getString(R.styleable.MaterialSearchBar_hint);
        maxSuggestionCount = array.getInt(R.styleable.MaterialSearchBar_maxSuggestionsCount, 3);
        speechMode = array.getBoolean(R.styleable.MaterialSearchBar_speechMode, false);

        destiny = getResources().getDisplayMetrics().density;
        adapter = new SuggestionsAdapter(LayoutInflater.from(getContext()));
        adapter.setOnClickListener(this);
        adapter.maxSuggestionsCount = maxSuggestionCount;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mt_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        array.recycle();

        searchIcon = (ImageView) findViewById(R.id.mt_search);
        arrowIcon = (ImageView) findViewById(R.id.mt_arrow);
        searchEdit = (EditText) findViewById(R.id.mt_editText);
        inputContainer = (LinearLayout) findViewById(R.id.inputContainer);
        findViewById(R.id.mt_clear).setOnClickListener(this);

        arrowIcon.setOnClickListener(this);
        searchEdit.setOnFocusChangeListener(this);
        setOnClickListener(this);
        searchEdit.setOnEditorActionListener(this);
        if (speechMode) searchIcon.setOnClickListener(this);

        setupIcons();
    }

    private void setupIcons(){
        if (speechMode)
            searchIcon.setImageResource(R.drawable.ic_microphone_black_48dp);
        if (iconRightResId > 0)
            searchIcon.setImageResource(iconRightResId);
        if (iconLefttResId > 0)
            arrowIcon.setImageResource(iconLefttResId);
        if (hint != null)
            searchEdit.setHint(hint);
    }

    //=======================================
    //          Public methods

    /**
     * Register listener for search bar callbacks.
     * @param onSearchActionListener the callback listener
     */
    public void setOnSearchActionListener(OnSearchActionListener onSearchActionListener) {
        this.onSearchActionListener = onSearchActionListener;
    }

    /**
     * Shows search input and close arrow
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
            onSearchActionListener.onSearchDisabled();
        if (suggestionsVisible) animateLastRequests(getListHeight(), 0);
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
        animateLastRequests(0, getListHeight());
        if (listenerExists()) {
            onSearchActionListener.onSearchEnabled();
        }
        searchIcon.startAnimation(left_out);
    }

    private void animateLastRequests(int from, int to){
        suggestionsVisible = to > 0 ? true : false;
        final RelativeLayout last = (RelativeLayout) findViewById(R.id.last);
        final ViewGroup.LayoutParams lp = last.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.height = (int) animation.getAnimatedValue();
                last.setLayoutParams(lp);
            }
        });
        if (adapter.getItemCount() > 0) animator.start();
    }

    /**
     * sets the icon instead of the back arrow
     * @param iconRightResId icon resource id
     */
    public void setIconRight(int iconRightResId) {
        this.iconRightResId = iconRightResId;
        searchIcon.setImageResource(iconRightResId);
    }

    /**
     * sets the icon instead of the search arrow
     * @param iconLefttResId icon resource id
     */
    public void setIconLeft(int iconLefttResId) {
        this.iconLefttResId = iconLefttResId;
        arrowIcon.setImageResource(iconLefttResId);
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
     * Also clicking on this icon will trigger the callback method onSpeechSelected()
     * @see OnSearchActionListener#onSpeechSelected()
     * @param speechMode
     */
    public void setSpeechMode(boolean speechMode){
        this.speechMode = speechMode;
        if (speechMode) searchIcon.setImageResource(R.drawable.ic_microphone_black_48dp);
    }

    /**
     * Check if search bar is in edit mode
     * @return true if search bar is in edit mode
     */
    public boolean isSearchEnabled() {
        return searchEnabled;
    }

    /**
     * Sets the maximum value of the last search queries
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

    private boolean listenerExists(){
        return onSearchActionListener != null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        final int thisId = getId();
        if (id == getId()){
            if (!searchEnabled)
            {
                enableSearch();
                if (listenerExists())
                    onSearchActionListener.onSearchBarClicked();
            }
        }else if (id == R.id.mt_arrow){
            disableSearch();
        }else if (id == R.id.requestItem){
            if (v.getTag() instanceof String)
                searchEdit.setText(v.getTag().toString());
        }else if (id == R.id.mt_search){
            if (listenerExists())
                onSearchActionListener.onSpeechSelected();
        }else if (id == R.id.mt_clear){
            searchEdit.setText("");
        }
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
            onSearchActionListener.onSearch(searchEdit.getText());
        if (suggestionsVisible)
            animateLastRequests(getListHeight(), 0);
        adapter.addSuggestion(searchEdit.getText().toString());
        return false;
    }

    private int getListHeight(){
        return (int) ((adapter.getItemCount()*50)*destiny);
    }

    interface OnSearchActionListener {
        void onSearchBarClicked();
        void onSearchEnabled();
        void onSearchDisabled();
        void onSearch(CharSequence text);
        void onSpeechSelected();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isSearchBarVisible = searchEnabled ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.suggestionsVisible = suggestionsVisible ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.speechMode = speechMode ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.iconLefttResId = iconLefttResId;
        savedState.iconRightResId = iconRightResId;
        savedState.suggestions = getLastSuggestions();
        savedState.maxSuggestions = maxSuggestionCount;
        if (hint != null) savedState.hint = hint.toString();
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        searchEnabled = savedState.isSearchBarVisible == VIEW_VISIBLE ? true : false;
        suggestionsVisible = savedState.suggestionsVisible == VIEW_VISIBLE ? true : false;
        speechMode = savedState.speechMode == VIEW_VISIBLE ? true : false;
        setLastSuggestions(savedState.suggestions);
        if (suggestionsVisible)
            animateLastRequests(0, getListHeight());
        if (searchEnabled)
        {
            inputContainer.setVisibility(VISIBLE);
            searchIcon.setVisibility(GONE);
        }
        iconLefttResId = savedState.iconLefttResId;
        iconRightResId = savedState.iconRightResId;
        hint = savedState.hint;
        maxSuggestionCount = savedState.maxSuggestions > 0 ? maxSuggestionCount  = savedState.maxSuggestions : maxSuggestionCount;
        Log.d("LOG_TAG", getClass().getSimpleName() + ": max " + maxSuggestionCount);
        setupIcons();
    }

    private static class SavedState extends BaseSavedState{
        private int isSearchBarVisible;
        private int suggestionsVisible;
        private int speechMode;
        private int iconRightResId;
        private int iconLefttResId;
        private String hint;
        private List<String> suggestions;
        private int maxSuggestions;

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(isSearchBarVisible);
            out.writeInt(suggestionsVisible);
            out.writeInt(speechMode);

            out.writeInt(iconRightResId);
            out.writeInt(iconLefttResId);
            out.writeString(hint);
            out.writeList(suggestions);
            out.writeInt(maxSuggestions);
        }

        public SavedState(Parcel source) {
            super(source);
            isSearchBarVisible = source.readInt();
            suggestionsVisible = source.readInt();
            speechMode = source.readInt();

            iconLefttResId = source.readInt();
            iconRightResId = source.readInt();
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
}
