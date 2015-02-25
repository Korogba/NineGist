package zumma.com.ninegistapp.ui.fragments;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import zumma.com.ninegistapp.ParseConstants;
import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.custom.CustomFragment;
import zumma.com.ninegistapp.database.table.FriendTable;

/**
 * Created by Okafor on 18/02/2015.
 */
public class FriendListFragment extends CustomFragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private FriendAdapter mAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);
        TextView empty = (TextView) rootView.findViewById(R.id.empty);

        listView.setEmptyView(empty);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FriendAdapter(getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up ListView, assign adapter and set some listeners. The adapter was previously
        // created in onCreate().

        setHasOptionsMenu(true);

        listView.setAdapter(mAdapter);

        // If there's a previously selected search item from a saved state then don't bother
        // initializing the loader as it will be restarted later when the query is populated into
        // the action bar search view (see onQueryTextChange() in onCreateOptionsMenu()).
        getLoaderManager().initLoader(FriendQuery.QUERY_ID, null,this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri contentUri = FriendTable.CONTENT_URI;

        return new CursorLoader(getActivity(),
                contentUri,
                FriendQuery.PROJECTION,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == FriendQuery.QUERY_ID) {
            if (data != null){
                mAdapter.swapCursor(data);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == FriendQuery.QUERY_ID) {
            // When the loader is being reset, clear the cursor from the adapter. This allows the
            // cursor resources to be freed.
            mAdapter.swapCursor(null);
        }
    }


    private class FriendAdapter extends CursorAdapter{

        private LayoutInflater mInflater;

        public FriendAdapter(Context context) {
            super(context, null, true);

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            final View itemLayout =
                    mInflater.inflate(R.layout.chatuser_list_item, parent, false);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.objectId = (TextView) itemLayout.findViewById(R.id.objectId);
            viewHolder.nameLabel = (TextView) itemLayout.findViewById(R.id.nameLabel);

            itemLayout.setTag(viewHolder);

            return itemLayout;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            final ViewHolder viewHolder = (ViewHolder) view.getTag();

            final String id = cursor.getString(FriendQuery.COLUMN_ID);
            final String username = cursor.getString(FriendQuery.COLUMN_USERNAME);

            viewHolder.nameLabel.setText(username);

        }

        private class ViewHolder {
            TextView objectId;
            TextView nameIcon;
            TextView nameLabel;
        }
    }

    public interface FriendQuery {
        // An identifier for the loader
        final static int QUERY_ID = ParseConstants.FRIEND_QUERY_ID;
        final static Uri CONTENT_URI = FriendTable.CONTENT_URI;
        final static String SELECTION = FriendTable.COLUMN_USERNAME + "!=''";

        final static String[] PROJECTION = {
                FriendTable.COLUMN_ID,
                FriendTable.COLUMN_USERNAME,
        };

        // The query column numbers which map to each value in the projection
        final static int COLUMN_ID = 0;
        final static int COLUMN_USERNAME = 1;
        final static int COLUMN_PHONE_NUMBER = 2;
    }
}
