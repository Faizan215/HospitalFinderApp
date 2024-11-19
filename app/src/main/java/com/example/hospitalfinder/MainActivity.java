package com.example.hospitalfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private static final int REQUEST_PERMISSION_CODE = 1;
    private Button emergencyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emergencyButton = findViewById(R.id.EmergencyButton);

        // Request microphone permission at runtime
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
        }

        // Set up SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new android.speech.RecognitionListener() {

            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {}

            @Override
            public void onError(int error) {}

            @Override
            public void onResults(Bundle results) {
                // Get the recognized speech results
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String recognizedText = matches.get(0);
                    // Check if the word "HELP" is present in the recognized text
                    if (recognizedText != null && recognizedText.toLowerCase().contains("help")) {
                        runOnUiThread(() -> showAlert());
                    }
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        // Set up click listener for the Emergency button
        emergencyButton.setOnClickListener(v -> startListeningForSpeech());
    }

    private void startListeningForSpeech() {
        // Create an intent for speech recognition
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...");

        // Start listening
        speechRecognizer.startListening(intent);
    }

    private void showAlert() {
        // Display an alert when the word "HELP" is detected
        new AlertDialog.Builder(this)
                .setTitle("Emergency")
                .setMessage("Ambulance on your way")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();

        // Optionally, you can show a Toast instead of the AlertDialog
        // Toast.makeText(this, "Ambulance on your way", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Start listening once the permission is granted
                startListeningForSpeech();
            } else {
                Toast.makeText(this, "Permission Denied. Unable to listen for speech.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
