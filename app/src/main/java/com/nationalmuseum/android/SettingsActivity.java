package com.nationalmuseum.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.LocaleList;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private Spinner languageSpinner, themeSpinner, fontSizeSpinner;
    private Switch switchUpdates;
    private Button btnReportProblem, saveSettings;
    private TextView tvAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applySavedFontScale();
        applySavedLanguage(); // apply language BEFORE super.onCreate
        super.onCreate(savedInstanceState);
        applySavedTheme();

        setContentView(R.layout.activity_settings);

        languageSpinner = findViewById(R.id.languageSpinner);
        themeSpinner = findViewById(R.id.themeSpinner);
        fontSizeSpinner = findViewById(R.id.fontSizeSpinner);
        switchUpdates = findViewById(R.id.switchUpdates);
        btnReportProblem = findViewById(R.id.btnReportProblem);
        saveSettings = findViewById(R.id.saveSettings);
        tvAppVersion = findViewById(R.id.tvAppVersion);

        setupLanguageSpinner();
        setupThemeSpinner();
        setupFontSizeSpinner();
        loadAppVersion();
        setupButtons();

        SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
        setSpinnerSelection(languageSpinner, prefs.getString("Language", "English"));
        setSpinnerSelection(themeSpinner, prefs.getString("Theme", "Light"));
        setSpinnerSelection(fontSizeSpinner, prefs.getString("FontSize", "Medium"));
        switchUpdates.setChecked(prefs.getBoolean("Updates", true));
    }

    private void applySavedFontScale() {
        SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
        String fontSize = prefs.getString("FontSize", "Medium");
        float scale;
        switch (fontSize) {
            case "Small": scale = 0.85f; break;
            case "Large": scale = 1.25f; break;
            default: scale = 1.0f;
        }

        Configuration config = getResources().getConfiguration();
        config.fontScale = scale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void applySavedTheme() {
        SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
        String theme = prefs.getString("Theme", "Light");
        switch (theme) {
            case "Light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "Dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "System Default":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    private void applySavedLanguage() {
        SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
        String language = prefs.getString("Language", "English");
        String langCode = getLanguageCode(language);

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        config.setLocales(new LocaleList(locale));
        getApplicationContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private String getLanguageCode(String language) {
        switch (language) {
            case "Spanish": return "es";
            case "French": return "fr";
            case "German": return "de";
            case "Bengali": return "bn";
            default: return "en";
        }
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            if (position >= 0) {
                spinner.setSelection(position);
            }
        }
    }

    private void setupLanguageSpinner() {
        String[] languages = {"English", "Spanish", "French", "German", "Bengali"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
    }

    private void setupThemeSpinner() {
        String[] themes = {"Light", "Dark", "System Default"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, themes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);
    }

    private void setupFontSizeSpinner() {
        String[] sizes = {"Small", "Medium", "Large"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSizeSpinner.setAdapter(adapter);
    }

    private void loadAppVersion() {
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            tvAppVersion.setText("App Version: " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setupButtons() {
        saveSettings.setOnClickListener(v -> {
            String selectedLanguage = languageSpinner.getSelectedItem().toString();
            String selectedTheme = themeSpinner.getSelectedItem().toString();
            String selectedFontSize = fontSizeSpinner.getSelectedItem().toString();
            boolean updatesEnabled = switchUpdates.isChecked();

            SharedPreferences prefs = getSharedPreferences("UserSettings", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Language", selectedLanguage);
            editor.putString("Theme", selectedTheme);
            editor.putString("FontSize", selectedFontSize);
            editor.putBoolean("Updates", updatesEnabled);
            editor.apply();

            Toast.makeText(this, "Settings Saved! Restarting app to apply language...", Toast.LENGTH_SHORT).show();
            restartApp();
        });

        btnReportProblem.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822"); // Ensures only email apps handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"shouvikpaul943@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Problem Report");
            intent.putExtra(Intent.EXTRA_TEXT, "Describe your issue here...");

            try {
                startActivity(Intent.createChooser(intent, "Send email using..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "No email app found to send report", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class); // or SplashActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
