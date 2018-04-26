package com.android.bitcoinbip39;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.bitcoinbip39.bip39.MnemonicGenerator;
import com.android.bitcoinbip39.bip39.Words;
import com.android.bitcoinbip39.bip39.wordlists.English;

import java.security.SecureRandom;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringBuilder sb = new StringBuilder();
        StringBuilder out = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        for(int i = 0;i < entropy.length;i++) {
            out.append(entropy[i]);
        }

        new MnemonicGenerator(English.INSTANCE)
                .createMnemonic(entropy, sb);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(sb.toString());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}