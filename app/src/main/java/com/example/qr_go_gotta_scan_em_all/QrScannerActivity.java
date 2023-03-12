package com.example.qr_go_gotta_scan_em_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

//referenced from https://github.com/yuriy-budiyev/code-scanner
public class QrScannerActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private ImageView back_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        CodeScannerView scannerView = findViewById(R.id.qr_scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        back_btn = findViewById(R.id.qr_scanner_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(QrScannerActivity.this,"You caught a pokemon" , Toast.LENGTH_SHORT).show();
                        //taking the pokemon Caught back to main activity
                        Intent intent = new Intent();
                        intent.putExtra("PokemonCaught",result.getText());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        Toast.makeText(this, "Scanning for Pokemon", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}