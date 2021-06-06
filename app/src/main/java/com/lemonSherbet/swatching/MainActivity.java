package com.lemonSherbet.swatching;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.palette.graphics.Target;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    Bitmap bitmap;
    ImageView imageView;
    LinearLayout LLVibrant;
    LinearLayout LLDarkVibrant;
    LinearLayout LLLightVibrant;
    LinearLayout LLMuted;
    LinearLayout LLDarkMuted;
    LinearLayout LLLightMuted;

    TextView VibrantTitle;
    TextView VibrantBody;
    TextView DarkVibrantTitle;
    TextView DarkVibrantBody;
    TextView LightVibrantTitle;
    TextView LightVibrantBody;
    TextView MutedTitle;
    TextView MutedBody;
    TextView DarkMutedTitle;
    TextView DarkMutedBody;
    TextView LightMutedTitle;
    TextView LightMutedBody;

    TextView TVVibrantHexCode;
    TextView TVDarkVibrantHexCode;
    TextView TVLightVibrantHexCode;
    TextView TVMutedHexCode;
    TextView TVDarkMutedHexCode;
    TextView TVLightMutedHexCode;

    int INTVibrantIntCode;
    int INTDarkVibrantIntCode;
    int INTLightVibrantIntCode;
    int INTMutedIntCode;
    int INTDarkMutedIntCode;
    int INTLightMutedIntCode;

    int INTVibrantTitleIntCode;
    int INTDarkVibrantTitleIntCode;
    int INTLightVibrantTitleIntCode;
    int INTMutedTitleIntCode;
    int INTDarkMutedTitleIntCode;
    int INTLightMutedTitleIntCode;

    int INTVibrantBodyIntCode;
    int INTDarkVibrantBodyIntCode;
    int INTLightVibrantBodyIntCode;
    int INTMutedBodyIntCode;
    int INTDarkMutedBodyIntCode;
    int INTLightMutedBodyIntCode;

    ActionBar actionBar;

    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;

    private AdView mAdView;
    int rotateCount;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    imageView.setBackgroundColor(Color.parseColor("#ffffff"));
                    sendPicture(data.getData());
                    break;
                case CAMERA_CODE:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassfindViewById();
        rotateCount = 0;

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdClicked() {
//                super.onAdClicked();
//                Toast.makeText(getApplicationContext(), "Thanks!", Toast.LENGTH_SHORT).show();
//            }
//        }


        actionBar = getSupportActionBar();
        actionBar.hide();

        AutoPermissions.Companion.loadAllPermissions(this, 101);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        Swatching(bitmap);
        tapMessage();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
            }
        });

        Button rotateBT = findViewById(R.id.BTrotate);
        rotateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateBitmap(bitmap);
            }
        });

        TVVibrantHexCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (TVVibrantHexCode.getText().toString() != "Tap↓") {
                    if (action == MotionEvent.ACTION_DOWN) {
                        clipboardCopy(TVVibrantHexCode.getText().toString());
                        Toast.makeText(getApplicationContext(), TVVibrantHexCode.getText().toString() + "가 클립보드에 복사됨", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Tap color box or text!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        TVDarkVibrantHexCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (TVDarkVibrantHexCode.getText().toString() != "Tap↓") {
                    if (action == MotionEvent.ACTION_DOWN) {
                        clipboardCopy(TVDarkVibrantHexCode.getText().toString());
                        Toast.makeText(getApplicationContext(), TVDarkVibrantHexCode.getText().toString() + "가 클립보드에 복사됨", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Tap color box or text!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        TVLightVibrantHexCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (TVLightVibrantHexCode.getText().toString() != "Tap↓") {
                    if (action == MotionEvent.ACTION_DOWN) {
                        clipboardCopy(TVLightVibrantHexCode.getText().toString());
                        Toast.makeText(getApplicationContext(), TVLightVibrantHexCode.getText().toString() + "가 클립보드에 복사됨", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Tap color box or text!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        TVMutedHexCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (TVMutedHexCode.getText().toString() != "Tap↓") {
                    if (action == MotionEvent.ACTION_DOWN) {
                        clipboardCopy(TVMutedHexCode.getText().toString());
                        Toast.makeText(getApplicationContext(), TVMutedHexCode.getText().toString() + "가 클립보드에 복사됨", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Tap color box or text!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        TVDarkMutedHexCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (TVDarkMutedHexCode.getText().toString() != "Tap↓") {
                    if (action == MotionEvent.ACTION_DOWN) {
                        clipboardCopy(TVDarkMutedHexCode.getText().toString());
                        Toast.makeText(getApplicationContext(), TVDarkMutedHexCode.getText().toString() + "가 클립보드에 복사됨", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Tap color box or text!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        TVLightMutedHexCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (TVLightMutedHexCode.getText().toString() != "Tap↓") {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        clipboardCopy(TVLightMutedHexCode.getText().toString());
                        Toast.makeText(getApplicationContext(), TVLightMutedHexCode.getText().toString() + "가 클립보드에 복사됨", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Tap color box or text!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        LLVibrant.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTVibrantIntCode, TVVibrantHexCode);
                    imageView.setBackgroundColor(INTVibrantIntCode);
                }
                return true;
            }
        });
        LLDarkVibrant.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTDarkVibrantIntCode, TVDarkVibrantHexCode);
                    imageView.setBackgroundColor(INTDarkVibrantIntCode);
                }
                return true;
            }
        });
        LLLightVibrant.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTLightVibrantIntCode, TVLightVibrantHexCode);
                    imageView.setBackgroundColor(INTLightVibrantIntCode);
                }
                return true;
            }
        });
        LLMuted.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTMutedIntCode, TVMutedHexCode);
                    imageView.setBackgroundColor(INTMutedIntCode);
                }
                return true;
            }
        });
        LLDarkMuted.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTDarkMutedIntCode, TVDarkMutedHexCode);
                    imageView.setBackgroundColor(INTDarkMutedIntCode);
                }
                return true;
            }
        });
        LLLightMuted.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTLightMutedIntCode, TVLightMutedHexCode);
                    imageView.setBackgroundColor(INTLightMutedIntCode);
                }
                return true;
            }
        });

        VibrantTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTVibrantTitleIntCode, TVVibrantHexCode);
                }
                return true;
            }
        });
        VibrantBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTVibrantBodyIntCode, TVVibrantHexCode);
                }
                return true;
            }
        });
        DarkVibrantTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTDarkVibrantTitleIntCode, TVDarkVibrantHexCode);
                }
                return true;
            }
        });
        DarkVibrantBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTDarkVibrantBodyIntCode, TVDarkVibrantHexCode);
                }
                return true;
            }
        });
        LightVibrantTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTLightVibrantTitleIntCode, TVLightVibrantHexCode);
                }
                return true;
            }
        });
        LightVibrantBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTLightVibrantBodyIntCode, TVLightVibrantHexCode);
                }
                return true;
            }
        });
        MutedTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTMutedTitleIntCode, TVMutedHexCode);
                }
                return true;
            }
        });
        MutedBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTMutedBodyIntCode, TVMutedHexCode);
                }
                return true;
            }
        });
        DarkMutedTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTDarkMutedTitleIntCode, TVDarkMutedHexCode);
                }
                return true;
            }
        });
        DarkMutedBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTDarkMutedBodyIntCode, TVDarkMutedHexCode);
                }
                return true;
            }
        });
        LightMutedTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTLightMutedTitleIntCode, TVLightMutedHexCode);
                }
                return true;
            }
        });
        LightMutedBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    setHexCode(INTLightMutedBodyIntCode, TVLightMutedHexCode);
                }
                return true;
            }
        });
    }

    private void sendPicture(Uri imgUri) {
        try {
            InputStream in = getContentResolver().openInputStream(imgUri);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null) {
                Toast.makeText(getApplicationContext(), "What happened!", Toast.LENGTH_SHORT).show();
            }
            imageView.setImageBitmap(bitmap);
            in.close();
            tapMessage();
            Swatching(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Swatching(Bitmap bitmap) {
        Palette.from(bitmap).maximumColorCount(128).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                if (palette == null) {
                    Toast.makeText(getApplicationContext(), "Where's my palette?", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Palette.Swatch f = palette.getDominantSwatch();
//                if(f!=null){
//                    VibrantTitle.setText("Vibrant Title");
//                    VibrantBody.setText("Vibrant Body");
//                    LLVibrant.setBackgroundColor(f.getRgb());
//                    VibrantTitle.setTextColor(f.getTitleTextColor());
//                    VibrantBody.setTextColor(f.getBodyTextColor());
//                    INTVibrantIntCode = f.getRgb();
//                    INTVibrantTitleIntCode = f.getTitleTextColor();
//                    INTVibrantBodyIntCode = f.getBodyTextColor();
//                }
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                if (vibrantSwatch != null) {
                    VibrantTitle.setText("Vibrant Title");
                    VibrantBody.setText("Vibrant Body");
                    LLVibrant.setBackgroundColor(vibrantSwatch.getRgb());
                    VibrantTitle.setTextColor(vibrantSwatch.getTitleTextColor());
                    VibrantBody.setTextColor(vibrantSwatch.getBodyTextColor());
                    INTVibrantIntCode = vibrantSwatch.getRgb();
                    INTVibrantTitleIntCode = vibrantSwatch.getTitleTextColor();
                    INTVibrantBodyIntCode = vibrantSwatch.getBodyTextColor();
                } else {
                    LLVibrant.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.likeazebra));
                    VibrantTitle.setTextColor(Color.parseColor("#000000"));
                    VibrantBody.setTextColor(Color.parseColor("#000000"));
                    VibrantTitle.setText("Null");
                    VibrantBody.setText("Null");
                    INTVibrantIntCode = 0;
                    INTVibrantTitleIntCode = 0;
                    INTVibrantBodyIntCode = 0;
                }
                Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                if (darkVibrantSwatch != null) {
                    DarkVibrantTitle.setText("DarkVibrant Title");
                    DarkVibrantBody.setText("DarkVibrant Body");
                    LLDarkVibrant.setBackgroundColor(darkVibrantSwatch.getRgb());
                    DarkVibrantTitle.setTextColor(darkVibrantSwatch.getTitleTextColor());
                    DarkVibrantBody.setTextColor(darkVibrantSwatch.getBodyTextColor());
                    INTDarkVibrantIntCode = darkVibrantSwatch.getRgb();
                    INTDarkVibrantTitleIntCode = darkVibrantSwatch.getTitleTextColor();
                    INTDarkVibrantBodyIntCode = darkVibrantSwatch.getBodyTextColor();
                } else {
                    LLDarkVibrant.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.likeazebra));
                    DarkVibrantTitle.setTextColor(Color.parseColor("#000000"));
                    DarkVibrantBody.setTextColor(Color.parseColor("#000000"));
                    DarkVibrantTitle.setText("Null");
                    DarkVibrantBody.setText("Null");
                    INTDarkVibrantIntCode = 0;
                    INTDarkVibrantTitleIntCode = 0;
                    INTDarkVibrantBodyIntCode = 0;
                }
                Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                if (lightVibrantSwatch != null) {
                    LightVibrantBody.setText("LightVibrant Body");
                    LightVibrantTitle.setText("LightVibrant Title");
                    LLLightVibrant.setBackgroundColor(lightVibrantSwatch.getRgb());
                    LightVibrantTitle.setTextColor(lightVibrantSwatch.getTitleTextColor());
                    LightVibrantBody.setTextColor(lightVibrantSwatch.getBodyTextColor());
                    INTLightVibrantIntCode = lightVibrantSwatch.getRgb();
                    INTLightVibrantTitleIntCode = lightVibrantSwatch.getTitleTextColor();
                    INTLightVibrantBodyIntCode = lightVibrantSwatch.getBodyTextColor();
                } else {
                    LLLightVibrant.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.likeazebra));
                    LightVibrantTitle.setTextColor(Color.parseColor("#000000"));
                    LightVibrantBody.setTextColor(Color.parseColor("#000000"));
                    LightVibrantTitle.setText("Null");
                    LightVibrantBody.setText("Null");
                    INTLightVibrantIntCode = 0;
                    INTLightVibrantTitleIntCode = 0;
                    INTLightVibrantBodyIntCode = 0;
                }
                Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                if (mutedSwatch != null) {
                    MutedTitle.setText("Muted Title");
                    MutedBody.setText("Muted Body");
                    LLMuted.setBackgroundColor(mutedSwatch.getRgb());
                    MutedBody.setTextColor(mutedSwatch.getBodyTextColor());
                    MutedTitle.setTextColor(mutedSwatch.getTitleTextColor());
                    INTMutedIntCode = mutedSwatch.getRgb();
                    INTMutedTitleIntCode = mutedSwatch.getTitleTextColor();
                    INTMutedBodyIntCode = mutedSwatch.getBodyTextColor();
                } else {
                    LLMuted.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.likeazebra));
                    MutedTitle.setTextColor(Color.parseColor("#000000"));
                    MutedBody.setTextColor(Color.parseColor("#000000"));
                    MutedTitle.setText("Null");
                    MutedBody.setText("Null");
                    INTMutedIntCode = 0;
                    INTMutedTitleIntCode = 0;
                    INTMutedBodyIntCode = 0;
                }
                Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                if (darkMutedSwatch != null) {
                    DarkMutedTitle.setText("DarkMuted Title");
                    DarkMutedBody.setText("DarkMuted Body");
                    LLDarkMuted.setBackgroundColor(darkMutedSwatch.getRgb());
                    DarkMutedTitle.setTextColor(darkMutedSwatch.getTitleTextColor());
                    DarkMutedBody.setTextColor(darkMutedSwatch.getBodyTextColor());
                    INTDarkMutedIntCode = darkMutedSwatch.getRgb();
                    INTDarkMutedTitleIntCode = darkMutedSwatch.getTitleTextColor();
                    INTDarkMutedBodyIntCode = darkMutedSwatch.getBodyTextColor();
                } else {
                    LLDarkMuted.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.likeazebra));
                    DarkMutedTitle.setTextColor(Color.parseColor("#000000"));
                    DarkMutedBody.setTextColor(Color.parseColor("#000000"));
                    DarkMutedTitle.setText("Null");
                    DarkMutedBody.setText("Null");
                    INTDarkMutedIntCode = 0;
                    INTDarkMutedTitleIntCode = 0;
                    INTDarkMutedBodyIntCode = 0;
                }
                Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();
                if (lightMutedSwatch != null) {
                    LightMutedTitle.setText("LightMuted Title");
                    LightMutedBody.setText("LightMuted Body");
                    LLLightMuted.setBackgroundColor(lightMutedSwatch.getRgb());
                    LightMutedTitle.setTextColor(lightMutedSwatch.getTitleTextColor());
                    LightMutedBody.setTextColor(lightMutedSwatch.getBodyTextColor());
                    INTLightMutedIntCode = lightMutedSwatch.getRgb();
                    INTLightMutedTitleIntCode = lightMutedSwatch.getTitleTextColor();
                    INTLightMutedBodyIntCode = lightMutedSwatch.getBodyTextColor();
                } else {
                    LLLightMuted.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.likeazebra));
                    LightMutedTitle.setTextColor(Color.parseColor("#000000"));
                    LightMutedBody.setTextColor(Color.parseColor("#000000"));
                    LightMutedTitle.setText("Null");
                    LightMutedBody.setText("Null");
                    INTLightMutedIntCode = 0;
                    INTLightMutedTitleIntCode = 0;
                    INTLightMutedBodyIntCode = 0;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int i, String[] strings) {
    }

    @Override
    public void onGranted(int i, String[] strings) {
    }

    public void ClassfindViewById() {
//        mAdView = findViewById(R.id.adView);

        imageView = findViewById(R.id.imageView);

        TVVibrantHexCode = findViewById(R.id.vibrantHexCode);
        TVDarkVibrantHexCode = findViewById(R.id.darkVibrantHexCode);
        TVLightVibrantHexCode = findViewById(R.id.lightVibrantHexCode);
        TVMutedHexCode = findViewById(R.id.mutedHexCode);
        TVDarkMutedHexCode = findViewById(R.id.darkMutedHexCode);
        TVLightMutedHexCode = findViewById(R.id.lightMutedHexCode);

        LLVibrant = findViewById(R.id.viewVibrant);
        LLDarkVibrant = findViewById(R.id.viewDarkVibrant);
        LLLightVibrant = findViewById(R.id.viewLightVibrant);
        LLMuted = findViewById(R.id.viewMuted);
        LLDarkMuted = findViewById(R.id.viewDarkMuted);
        LLLightMuted = findViewById(R.id.viewLightMuted);

        VibrantTitle = findViewById(R.id.tvVibrantTitle);
        VibrantBody = findViewById(R.id.tvVibrantBody);
        DarkVibrantTitle = findViewById(R.id.tvDarkVibrantTitle);
        DarkVibrantBody = findViewById(R.id.tvDarkVibrantBody);
        LightVibrantTitle = findViewById(R.id.tvLightVibrantTitle);
        LightVibrantBody = findViewById(R.id.tvLightVibrantBody);
        MutedTitle = findViewById(R.id.tvMutedTitle);
        MutedBody = findViewById(R.id.tvMutedBody);
        DarkMutedTitle = findViewById(R.id.tvDarkMutedTitle);
        DarkMutedBody = findViewById(R.id.tvDarkMutedBody);
        LightMutedTitle = findViewById(R.id.tvLightMutedTitle);
        LightMutedBody = findViewById(R.id.tvLightMutedBody);
    }

    public void setHexCode(int IntCode, TextView TV) {
        if (IntCode != 0) {
            String StrHexCode = String.format("#%08X", (0xFFFFFFFF & IntCode));
            TV.setText(StrHexCode);
        } else {
            TV.setText("Null");
        }
    }

    public void tapMessage() {
        TVVibrantHexCode.setText("Tap↓");
        TVDarkVibrantHexCode.setText("Tap↓");
        TVLightVibrantHexCode.setText("Tap↓");
        TVMutedHexCode.setText("Tap↓");
        TVDarkMutedHexCode.setText("Tap↓");
        TVLightMutedHexCode.setText("Tap↓");
    }

    public void rotateBitmap(Bitmap bitmap) {
        rotateCount++;
        Matrix rotateMatrix = new Matrix();
        switch (rotateCount) {
            case 0:
                rotateMatrix.postRotate(0);
                break;
            case 1:
                rotateMatrix.postRotate(90);
                break;
            case 2:
                rotateMatrix.postRotate(180);
                break;
            case 3:
                rotateMatrix.postRotate(270);
                rotateCount = -1;
                break;
        }
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), rotateMatrix, false);

        imageView.setImageBitmap(rotatedBitmap);
    }

    public void clipboardCopy(String hexCode) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("HexCode", hexCode);
        clipboardManager.setPrimaryClip(clipData);
    }
}