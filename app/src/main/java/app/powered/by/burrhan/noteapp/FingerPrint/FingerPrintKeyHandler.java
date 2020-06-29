package app.powered.by.burrhan.noteapp.FingerPrint;

import android.app.Activity;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricPrompt;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import app.powered.by.burrhan.noteapp.Activities.LoginActivity;


public class FingerPrintKeyHandler {
    private static FingerPrintKeyHandler fingerPrintKeyHandler;
    private KeyStore keyStore;
    private Cipher cipher;
    private KeyGenerator keyGenerator;
    private static final String KEY_NAME = "Burhan";
    private SecretKey key;
    public static String possibility = "false";
    public Activity activity;

    public FingerPrintKeyHandler() {
        initAuthentication();
    }

    public FingerPrintKeyHandler(LoginActivity loginActivity) {
        this.activity = loginActivity;
    }

    public void initAuthentication() {
        try {
            keyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }


        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateSecretKeyMethod();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean initCypher(BiometricPrompt biometricPrompt, BiometricPrompt.PromptInfo promptInfo) {
        Cipher cipher1 = null;

        {
            cipher1 = getCipher();
        }

        SecretKey secretKey = getSecretKey();

        try {
            cipher1.init(Cipher.ENCRYPT_MODE, secretKey);
            return true;
        } catch (UserNotAuthenticatedException e) {
            Log.d("MY_APP_TAG", "The key's validity timed out.");
            biometricPrompt.authenticate(promptInfo);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return false;


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Cipher getCipher() {
        try {
            cipher = Cipher.getInstance
                    (KeyProperties
                            .KEY_ALGORITHM_AES
                            + "/"
                            + KeyProperties
                            .BLOCK_MODE_CBC
                            + "/"
                            + KeyProperties
                            .ENCRYPTION_PADDING_PKCS7);
            possibility = "true";
        } catch (NoSuchAlgorithmException e) {
            possibility = "false";

            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            possibility = "false";

            e.printStackTrace();
        }
        return cipher;


    }

    public SecretKey getSecretKey() {

        try {
            keyStore.load(null);
            key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            possibility = "true";


        } catch (CertificateException e) {
            possibility = "false";

            e.printStackTrace();
        } catch (IOException e) {
            possibility = "false";

            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            possibility = "false";

            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            possibility = "false";

            e.printStackTrace();
        } catch (KeyStoreException e) {
            possibility = "false";

            e.printStackTrace();
        }

        return key;


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateSecretKeyMethod() {


        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
        } catch (CertificateException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        }


        KeyGenParameterSpec keyGenerator1 = (new KeyGenParameterSpec.Builder(
                KEY_NAME,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(true)
                .setUserAuthenticationValidityDurationSeconds(5000)
                .build());

        try {
            keyGenerator.init(keyGenerator1);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        keyGenerator.generateKey();

    }

    public static FingerPrintKeyHandler getInstance() {
        if (fingerPrintKeyHandler == null) {
            fingerPrintKeyHandler = new FingerPrintKeyHandler();

        }
        return fingerPrintKeyHandler;
    }


}

