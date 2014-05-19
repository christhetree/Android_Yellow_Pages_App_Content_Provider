package com.tddrampup.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tddrampup.R;
import com.tddrampup.contentProviders.YellowContentProvider;
import com.tddrampup.databases.ListingsTable;
import com.tddrampup.databases.ListingsTableHelper;
import com.tddrampup.databases.SearchTable;
import com.tddrampup.databases.SearchTableHelper;
import com.tddrampup.fragments.DetailFragment;
import com.tddrampup.fragments.GoogleMapFragment;
import com.tddrampup.fragments.HomeFragment;
import com.tddrampup.fragments.ListFragment;
import com.tddrampup.models.Listing;
import com.tddrampup.serviceLayers.VolleyServiceLayer;
import com.tddrampup.serviceLayers.VolleyServiceLayerCallback;

import java.util.List;

import roboguice.activity.RoboFragmentActivity;

public class MainActivity extends RoboFragmentActivity implements HomeFragment.onItemClickedListener, ListFragment.onListViewItemClickedListener {

    private VolleyServiceLayer volleyServiceLayer;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        volleyServiceLayer = new VolleyServiceLayer(getApplicationContext());
        mProgressDialog = new ProgressDialog(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_activity, new HomeFragment(), "MY_HOME_FRAGMENT").commit();
        }
    }

    @Override
    public void onListButtonClicked(){
        ListFragment listFragment = new ListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity, listFragment, "MY_LIST_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onMapButtonClicked(){
        GoogleMapFragment googleMapFragment = new GoogleMapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity, googleMapFragment, "MY_GOOGLE_MAP_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSearchButtonClicked() {
        EditText whatEditText = (EditText) findViewById(R.id.what_editText);
        EditText whereEditText = (EditText) findViewById(R.id.where_editText);

        String what = whatEditText.getText().toString();
        String where = whereEditText.getText().toString();

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow((whatEditText.isSelected() ? whatEditText : whereEditText).getWindowToken(), 0);

        if(!what.isEmpty() && !where.isEmpty()) {
            ListFragment searchFragment = new ListFragment(what, where);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_activity, searchFragment, "MY_SEARCH_FRAGMENT");
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            Toast.makeText(this, "Please fill in both fields!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListViewItemClicked(int position, boolean isSearchQuery) {
        Cursor cursor;
        int listingIdIndex;
        int websiteUrlIndex;

        if (isSearchQuery) {
            cursor = getContentResolver().query(YellowContentProvider.CONTENT_URI_SEARCH_LISTINGS, SearchTableHelper.searchTableItemClickProjection, null, null, null);
            listingIdIndex = cursor.getColumnIndex(SearchTable.COLUMN_LISTING_ID);
            websiteUrlIndex = cursor.getColumnIndex(SearchTable.COLUMN_MERCHANT_URL);
        } else {
            cursor = getContentResolver().query(YellowContentProvider.CONTENT_URI_LISTINGS, ListingsTableHelper.listingsTableItemClickProjection, null, null, null);
            listingIdIndex = cursor.getColumnIndex(ListingsTable.COLUMN_LISTING_ID);
            websiteUrlIndex = cursor.getColumnIndex(ListingsTable.COLUMN_MERCHANT_URL);
        }
        cursor.moveToPosition(position);
        String listingId = cursor.getString(listingIdIndex);
        String websiteUrl = cursor.getString(websiteUrlIndex);
        cursor.close();

        if (websiteUrl.isEmpty()) {
            showLoading();
            volleyServiceLayer.volleyServiceLayerCallback = new Callback();
            volleyServiceLayer.GetListing(listingId, isSearchQuery);
        } else {
            launchDetailFragment(listingId, isSearchQuery);
        }
    }

    public class Callback implements VolleyServiceLayerCallback {
        public void listCallbackCall(List<Listing> listings) {
            // do nothing
        }

        @Override
        public void itemCallbackCall(Listing listing, Boolean isSearchQuery) {
            hideLoading();
            String listingId = listing.getId();
            if (isSearchQuery) {
                SearchTableHelper.updateListing(listing, getApplicationContext());
            } else {
                ListingsTableHelper.updateListing(listing, getApplicationContext());
            }
            launchDetailFragment(listingId, isSearchQuery);
        }
    }

    private void launchDetailFragment(String listingId, Boolean isSearchQuery) {
        DetailFragment detailFragment = new DetailFragment(listingId, isSearchQuery);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_activity, detailFragment, "MY_DETAIL_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showLoading() {
        mProgressDialog.setTitle("Loading:");
        mProgressDialog.setMessage("Fetching details...");
        mProgressDialog.show();
    }

    private void hideLoading() {
        mProgressDialog.dismiss();
    }

    public boolean isProgressDialogShowing() {
        return mProgressDialog.isShowing();
    }
}
