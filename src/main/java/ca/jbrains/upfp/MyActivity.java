package ca.jbrains.upfp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView transactionsCountView = (TextView) findViewById(R.id.transactionsCount);
        transactionsCountView.setText(String.valueOf(1));
    }
}