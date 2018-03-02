package org.paleozogt.gtestrunner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.mutable.MutableObject;

public class MainActivity extends AppCompatActivity {
    GTestRunner gTestRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gTestRunner = new GTestRunner(this);

        final TextView testOutput = (TextView) findViewById(R.id.test_output);

        final Spinner testListSpinner = (Spinner) findViewById(R.id.test_list);
        testListSpinner.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, gTestRunner.getTests()));

        Button runTestsButton = (Button) findViewById(R.id.run_tests);
        runTestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MutableObject<String> output= new MutableObject<>();
                gTestRunner.run("--gtest_filter=" + testListSpinner.getSelectedItem(), output);
                testOutput.setText(output.getValue());
            }
        });
    }
}
