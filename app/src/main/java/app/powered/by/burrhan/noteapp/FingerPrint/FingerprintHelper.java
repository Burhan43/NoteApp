package app.powered.by.burrhan.noteapp.FingerPrint;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

import app.powered.by.burrhan.noteapp.Activities.LoginActivity;
import app.powered.by.burrhan.noteapp.Activities.MainActivity;


public class FingerprintHelper {

    public static String checker = "Not_Authenticated";

    public FingerprintHelper(LoginActivity loginActivity) {
    }

    public void m1(Activity activity, Executor executor, BiometricPrompt biometricPrompt, BiometricPrompt.PromptInfo promptInfo, CardView biometricLoginButton) {


        executor = ContextCompat.getMainExecutor(activity);
        biometricPrompt = new BiometricPrompt((FragmentActivity) activity,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(activity,
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("RestrictedApi")
            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(activity,
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                Toast.makeText(activity, "Authentication failed", Toast.LENGTH_SHORT).show();

            }

        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Secure Login To NoteApp")
                .setSubtitle("Using Phone Biometric Credentials")
//                .setNegativeButtonText("Use account password")
                .setDeviceCredentialAllowed(true)
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

        BiometricPrompt finalBiometricPrompt = biometricPrompt;
        BiometricPrompt.PromptInfo finalPromptInfo = promptInfo;


        //  onClickToLogin  //

        BiometricPrompt finalBiometricPrompt1 = biometricPrompt;
        BiometricPrompt.PromptInfo finalPromptInfo1 = promptInfo;
        BiometricPrompt finalBiometricPrompt2 = biometricPrompt;
        BiometricPrompt.PromptInfo finalPromptInfo2 = promptInfo;
        biometricLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    finalBiometricPrompt.authenticate(finalPromptInfo);
                    checker = "Authenticated";

                    if (!checker.equals("Authenticated")) {

                        FingerPrintKeyHandler authenticator = FingerPrintKeyHandler.getInstance();
                        new FingerPrintKeyHandler((LoginActivity) activity);
                        String a = FingerPrintKeyHandler.possibility.toUpperCase();
//                    Toast.makeText(activity, "lo g:"+ a, Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (authenticator.initCypher(finalBiometricPrompt2, finalPromptInfo2)) {

//                                Toast.makeText(activity, "msg:" + "true", Toast.LENGTH_SHORT).show();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    authenticator.initCypher(finalBiometricPrompt1, finalPromptInfo1);

                                    BiometricPrompt.CryptoObject cryptoObject = new BiometricPrompt.CryptoObject(authenticator.getCipher());


                                    finalBiometricPrompt.authenticate(finalPromptInfo, cryptoObject);

                                    Toast.makeText(activity, "pin", Toast.LENGTH_SHORT).show();


                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(activity, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    checker = "Not_Authenticated";
                }


            }
        });

        ///////////////**************///////////////////////////
    }


}

