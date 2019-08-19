package com.demo.cp.type_two.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.cp.R;
import com.demo.cp.type_two.datastore.dbConstants;
import com.demo.cp.type_two.provider.UserDetailsProvider;

public class UserDetails extends AppCompatActivity {

    private EditText mUserName;
    private EditText mAge;
    private Button mSubmit;

    private Uri mUserUri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_userdetails);

        mUserName = findViewById(R.id.edit_name);
        mAge = findViewById(R.id.edit_age);
        mSubmit = findViewById(R.id.btn_submit);

        Bundle extras = getIntent().getExtras();

        // Check from the saved Instance
        mUserUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(UserDetailsProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
            mUserUri = extras
                    .getParcelable(UserDetailsProvider.CONTENT_ITEM_TYPE);

            fillData(mUserUri);
        }

        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mUserName.getText().toString())) {
                    makeToast();
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }

        });
    }

    private void fillData(Uri uri) {
        String[] projection = {dbConstants.COLUMN_UNAME,
                dbConstants.COLUMN_AGE};
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String category = cursor.getString(cursor
                    .getColumnIndexOrThrow(dbConstants.COLUMN_AGE));

            mUserName.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(dbConstants.COLUMN_UNAME)));
            mAge.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(dbConstants.COLUMN_AGE)));

            // Always close the cursor
            cursor.close();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(UserDetailsProvider.CONTENT_ITEM_TYPE, mUserUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        String mUsername = mUserName.getText().toString();
        String mUserAge = mAge.getText().toString();

        // Only save if either summary or description
        // is available

        if (mUsername.length() == 0 && mUserAge.length() == 0) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(dbConstants.COLUMN_UNAME, mUsername);
        values.put(dbConstants.COLUMN_AGE, mUserAge);

        if (mUserUri == null) {
            // New todo
            mUserUri = getContentResolver().insert(
                    UserDetailsProvider.CONTENT_URI, values);
        } else {
            // Update todo
            getContentResolver().update(mUserUri, values, null, null);
        }
    }

    private void makeToast() {
        Toast.makeText(this, "Enter Valid Details",
                Toast.LENGTH_LONG).show();
    }
}
