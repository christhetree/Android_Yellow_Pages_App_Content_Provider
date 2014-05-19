package com.tddrampup.databases;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.tddrampup.contentProviders.YellowContentProvider;
import com.tddrampup.models.Address;
import com.tddrampup.models.GeoCode;
import com.tddrampup.models.Listing;

import java.util.List;

/**
 * Created by WX009-PC on 2/27/14.
 */
public class SearchTableHelper {

    public static final String[] searchTableAdapterProjection = {
            SearchTable.COLUMN_ID,
            SearchTable.COLUMN_NAME,
            SearchTable.COLUMN_STREET,
            SearchTable.COLUMN_CITY
    };

    public static final String[] searchTableMapProjection = {
            SearchTable.COLUMN_NAME,
            SearchTable.COLUMN_LATITUDE,
            SearchTable.COLUMN_LONGITUDE
    };

    public static final String[] searchTableItemClickProjection = {
            SearchTable.COLUMN_LISTING_ID,
            SearchTable.COLUMN_MERCHANT_URL,
            SearchTable.COLUMN_LONGITUDE
    };

    public static final String[] searchTableDetailProjection = {
            SearchTable.COLUMN_NAME,
            SearchTable.COLUMN_STREET,
            SearchTable.COLUMN_CITY,
            SearchTable.COLUMN_MERCHANT_URL,
    };

    public static void addListings(List<Listing> listings, Context context) {
        Uri uri = YellowContentProvider.CONTENT_URI_SEARCH_LISTINGS;
        context.getContentResolver().delete(uri, null, null);

        for (Listing listing : listings) {
            addListing(listing, context);
        }
    }

    private static void addListing(Listing listing, Context context) { // TODO: do with SQL?
        ContentValues values = new ContentValues();
        String id = listing.getId();
        if (id != null) {
            values.put(SearchTable.COLUMN_LISTING_ID, id);
        } else {
            values.put(SearchTable.COLUMN_LISTING_ID, "");
        }

        String name = listing.getName();
        if (name != null) {
            values.put(SearchTable.COLUMN_NAME, name);
        } else {
            values.put(SearchTable.COLUMN_NAME, "");
        }

        final Address address = listing.getAddress();
        if (address != null) {
            String street = address.getStreet();
            if (street != null) {
                values.put(SearchTable.COLUMN_STREET, street);
            } else {
                values.put(SearchTable.COLUMN_STREET, "");
            }

            String city = address.getCity();
            if (city != null) {
                values.put(SearchTable.COLUMN_CITY, city);
            } else {
                values.put(SearchTable.COLUMN_CITY, "");
            }
        } else {
            values.put(SearchTable.COLUMN_STREET, "");
            values.put(SearchTable.COLUMN_CITY, "");
        }

        String url = listing.getMerchantUrl(); // Will be null
        if (url != null) {
            values.put(SearchTable.COLUMN_MERCHANT_URL, url);
        } else {
            values.put(SearchTable.COLUMN_MERCHANT_URL, "");
        }

        final GeoCode geoCode = listing.getGeoCode();
        if (geoCode != null) {
            String latitude = geoCode.getLatitude();
            if (latitude != null) {
                values.put(SearchTable.COLUMN_LATITUDE, latitude);
            } else {
                values.put(SearchTable.COLUMN_LATITUDE, "");
            }

            String longitude = geoCode.getLongitude();
            if (longitude != null) {
                values.put(SearchTable.COLUMN_LONGITUDE, longitude);
            } else {
                values.put(SearchTable.COLUMN_LONGITUDE, "");
            }
        } else {
            values.put(SearchTable.COLUMN_LATITUDE, "");
            values.put(SearchTable.COLUMN_LONGITUDE, "");
        }

        context.getContentResolver().insert(YellowContentProvider.CONTENT_URI_SEARCH_LISTINGS, values);
    }

    public static void updateListing(Listing listing, Context context) {
        String listingId = listing.getId();
        String merchantUrl = listing.getMerchantUrl();

        ContentValues values = new ContentValues();
        if (merchantUrl != null) {
            values.put(SearchTable.COLUMN_MERCHANT_URL, merchantUrl);
        } else {
            values.put(SearchTable.COLUMN_MERCHANT_URL, "");
        }
        context.getContentResolver().update(YellowContentProvider.CONTENT_URI_SEARCH_LISTINGS, values, SearchTable.COLUMN_LISTING_ID + "=" + listingId, null);
    }
}
