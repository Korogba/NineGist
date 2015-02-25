package zumma.com.ninegistapp.service.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.parse.ParseUser;

import java.util.HashMap;

import zumma.com.ninegistapp.ParseConstants;
import zumma.com.ninegistapp.database.table.FriendTable;

/**
 * Created by Okafor on 29/12/2014.
 */
public class FriendsSearch {

    public HashMap<String, String> allUserContacts(Context context) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor people = context.getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        HashMap<String, String> contacts = new HashMap<String, String>();

        int n = 0;
        if (people != null && people.getCount() > 0) {
            people.moveToFirst();
            do {
                n++;
                String name = people.getString(indexName);
                String number = people.getString(indexNumber);
                if (number.length() > 10) {
                    number = number.replace("-", "").replace("+", "").replace(" ", "").trim();
                    String formated = getFullMobileNumber("234", number);
//                    Log.d(TAG, n+"  name : " + name + "  number : " + formated);
                    contacts.put(name, formated);
                }
            } while (people.moveToNext());
        }
        people.close();

        return contacts;
    }


    public boolean insertFriend(Context context, ContentValues values) {
        Uri uri = context.getContentResolver().insert(FriendTable.CONTENT_URI, values);
        if (uri != null) {
            return true;
        }
        return false;
    }

    public ContentValues toContentValues(ParseUser user, String name, ParseUser currentUser) {
        ContentValues values = new ContentValues();
        values.put(FriendTable.COLUMN_ID, user.getObjectId());
        values.put(FriendTable.COLUMN_USERNAME, name);
        values.put(FriendTable.COLUMN_PHONE_NUMBER, user.getUsername());
        values.put(FriendTable.COLUMN_COUNTRY_NAME, user.getString(ParseConstants.KEY_COUNTRY_NAME));
        values.put(FriendTable.COLUMN_COUNTRY_CODE, user.getString(ParseConstants.KEY_COUNTRY_CODE));
        values.put(FriendTable.COLUMN_UPDATED_AT, user.getUpdatedAt().getTime());
        values.put(FriendTable.COLUMN_USER_ID, currentUser.getObjectId());
        return values;
    }

    private String getFullMobileNumber(String code, String phone_number) {
        if (phone_number.subSequence(0, 1).equals("0")) {
            phone_number = code + phone_number.substring(1);
        }
        return phone_number;
    }

}
