package com.MDstudios.mdsandbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Jawad Nasser on 8/10/2014.
 */
public class QuotesEnActivityEdit extends Activity {
    private EditText mQuoteText;
    private Long mRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotesen_activity_edit);

        mQuoteText = (EditText) findViewById(R.id.title);

        Button confirmButton = (Button) findViewById(R.id.confirm);

        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(QuotesEnDBAdapter.KEY_QUOTES);
            mRowId = extras.getLong(QuotesEnDBAdapter.KEY_ROWID);

            if (title != null) {
                mQuoteText.setText(title);
            }
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString(QuotesEnDBAdapter.KEY_QUOTES, mQuoteText.getText().toString());
                if (mRowId != null) {
                    bundle.putLong(QuotesEnDBAdapter.KEY_ROWID, mRowId);
                }

                Intent mIntent = new Intent();
                mIntent.putExtras(bundle);
                setResult(RESULT_OK, mIntent);
                finish();
            }

        });
    }
}
