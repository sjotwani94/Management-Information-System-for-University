package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    EditText fetchUrl;
    Button submit;
    WebView inbuiltBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        fetchUrl=findViewById(R.id.edt_search);
        submit=findViewById(R.id.submit_url);
        inbuiltBrowser=findViewById(R.id.web_view);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlWeb=fetchUrl.getText().toString();
                if (urlWeb.contains("https://www.") || urlWeb.contains("http://www."))
                {
                    inbuiltBrowser.setWebViewClient(new WebViewClient());
                    inbuiltBrowser.getSettings().setJavaScriptEnabled(true);
                    inbuiltBrowser.loadUrl(urlWeb);
                    inbuiltBrowser.getSettings().setBuiltInZoomControls(true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (inbuiltBrowser.canGoBack()) {
            inbuiltBrowser.goBack();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Exit");
            builder.setMessage("Do you really wanna exit the app?");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(WebViewActivity.this, "Okay then enter a URL above to browse", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNeutralButton("Go Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    WebViewActivity.super.onBackPressed();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
