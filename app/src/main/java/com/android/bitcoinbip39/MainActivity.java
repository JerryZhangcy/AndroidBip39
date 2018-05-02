package com.android.bitcoinbip39;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.bitcoinbip39.bip39.MnemonicGenerator;
import com.android.bitcoinbip39.bip39.SeedCalculator;
import com.android.bitcoinbip39.bip39.Words;
import com.android.bitcoinbip39.bip39.wordlists.English;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Base64;

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
        byte[] entropy = new byte[Words.TWENTY_FOUR.byteLength()];
        new SecureRandom().nextBytes(entropy);

        String entropyStr = Util.bytesToHexFun2(entropy);

        new MnemonicGenerator(English.INSTANCE)
                .createMnemonic(entropy, sb);

        byte[] seed = new SeedCalculator().calculateSeed(sb.toString(), "");

        String seedStr = Util.bytesToHexFun2(seed);

        // Example of a call to a native method
        ((TextView) findViewById(R.id.entropy)).setText("Entropy:" + "\n" + entropyStr);
        ((TextView) findViewById(R.id.mnemonic)).setText("Mnemonic:" + "\n" + sb.toString());
        ((TextView) findViewById(R.id.seed)).setText("Seed:" + "\n" + seedStr);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();
}
