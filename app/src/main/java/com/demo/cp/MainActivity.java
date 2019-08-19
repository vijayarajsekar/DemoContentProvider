package com.demo.cp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQ_CODE = 100;
    private Context mContext;
    private TextView mContactName;
    private Cursor mContactCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mContactName = findViewById(R.id.contactview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkPermission()) {
            requestPermission();
        } else {
            getContacts();
        }
    }


    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(mContext, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ? true : false;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS}, PERMISSION_REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT).show();
                getContacts();
            } else {
                Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_SHORT).show();
                mContactName.setText("No Contacts Available");
            }
        }
    }

    private void getContacts() {

        Uri mContactUri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME};

        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
                + ("1") + "'";

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";

        mContactCursor = getContentResolver().query(mContactUri, projection, selection, selectionArgs, sortOrder);

        if (null != mContactCursor) {
            while (mContactCursor.moveToNext()) {

                String displayName = mContactCursor.getString(mContactCursor
                        .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                mContactName.append("Name : ");
                mContactName.append(displayName);
                mContactName.append("\n");
            }

            // Closing the cursor
            mContactCursor.close();
        } else {
            mContactName.setText("No Contacts Available");
        }
    }
}
