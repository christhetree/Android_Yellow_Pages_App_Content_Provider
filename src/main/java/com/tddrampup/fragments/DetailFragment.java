package com.tddrampup.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tddrampup.R;
import com.tddrampup.contentProviders.YellowContentProvider;
import com.tddrampup.databases.ListingsTable;
import com.tddrampup.databases.ListingsTableHelper;
import com.tddrampup.databases.SearchTable;
import com.tddrampup.databases.SearchTableHelper;

import roboguice.fragment.RoboFragment;

/**
 * Created by: WX009-PC
 * on: 2/21/14.
 */
public class DetailFragment extends RoboFragment {

    private final String mListingId;
    private TextView nameTextView;
    private TextView locationTextView;
    private TextView websiteTextView;
    private final boolean mIsSearchQuery;

    public DetailFragment(String listingId, boolean isSearchQuery) {
        mListingId = listingId;
        mIsSearchQuery = isSearchQuery;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        nameTextView = (TextView) rootView.findViewById(R.id.name_detail_fragment);
        locationTextView = (TextView) rootView.findViewById(R.id.location_detail_fragment);
        websiteTextView = (TextView) rootView.findViewById(R.id.website_detail_fragment);
        populateTextViews();
        return rootView;
    }

    private void populateTextViews() {
        Cursor cursor;
        int nameIdIndex;
        int streetIdIndex;
        int cityIdIndex;
        int websiteIdIndex;
        if (mIsSearchQuery) {
            cursor = getActivity().getContentResolver().query(YellowContentProvider.CONTENT_URI_SEARCH_LISTINGS, SearchTableHelper.searchTableDetailProjection, SearchTable.COLUMN_LISTING_ID + "=" + mListingId, null, null);
            nameIdIndex = cursor.getColumnIndex(SearchTable.COLUMN_NAME);
            streetIdIndex = cursor.getColumnIndex(SearchTable.COLUMN_STREET);
            cityIdIndex = cursor.getColumnIndex(SearchTable.COLUMN_CITY);
            websiteIdIndex = cursor.getColumnIndex(SearchTable.COLUMN_MERCHANT_URL);
        } else {
            cursor = getActivity().getContentResolver().query(YellowContentProvider.CONTENT_URI_LISTINGS, ListingsTableHelper.listingsTableDetailProjection, ListingsTable.COLUMN_LISTING_ID + "=" + mListingId, null, null);
            nameIdIndex = cursor.getColumnIndex(ListingsTable.COLUMN_NAME);
            streetIdIndex = cursor.getColumnIndex(ListingsTable.COLUMN_STREET);
            cityIdIndex = cursor.getColumnIndex(ListingsTable.COLUMN_CITY);
            websiteIdIndex = cursor.getColumnIndex(ListingsTable.COLUMN_MERCHANT_URL);
        }
        cursor.moveToFirst();
        String name = cursor.getString(nameIdIndex);
        String street = cursor.getString(streetIdIndex);
        String city = cursor.getString(cityIdIndex);
        String website = cursor.getString(websiteIdIndex);
        cursor.close();
        nameTextView.setText(name);
        locationTextView.setText(street + ", " + city);
        websiteTextView.setText(website);
    }
}
