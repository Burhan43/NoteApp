package app.powered.by.burrhan.noteapp.Classes;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import app.powered.by.burrhan.noteapp.R;


public class AppRater {
    public static final String APP_TITLE = "SecureNote";
    private static String APP_PACKAGE_NAME;
    private static long launch_count;
    private static long date_firstLaunch;


    public static int DAYS_UNTIL_PROMPT = 2;
    public static int LAUNCH_UNTIL_PROMPT = 2;

    public AppRater(Context context) {

        APP_PACKAGE_NAME = context.getPackageName();
    }

    public static void AppLaunched(Context context) {

        SharedPreferences prefs = context.getSharedPreferences("rate_app", 0);


        if (prefs.getBoolean("dontshowagin", false)) {
            return;
        }
        SharedPreferences.Editor editor = prefs.edit();


        launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);


        date_firstLaunch = prefs.getLong("date_first_launch", 0);


        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_first_launch", date_firstLaunch);


        }

        if (launch_count >= LAUNCH_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(context, editor, prefs);
            }
        }


        editor.apply();


    }

    private static void showRateDialog(final Context context, final SharedPreferences.Editor editor, SharedPreferences prefs) {
        Dialog dialog = new Dialog(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);

        String message = " If you feel secure using " + APP_TITLE + ", Please take a moment to rate me " +
                "Thank you for your support!";

        Dialog finalDialog = dialog;
        Dialog finalDialog1 = dialog;
        Dialog finalDialog2 = dialog;
        builder
                .setMessage(message)
                .setTitle("Rate" + APP_TITLE)

                .setCancelable(false)

                .setPositiveButton("Rate Now", (dialogInterface, i) -> {

                    editor.putBoolean("dontshowagin", true);
                    editor.apply();

                    try {

                        Uri uri = Uri.parse("market://details?id=" + APP_PACKAGE_NAME);
                        context.startActivity(new Intent(Intent.ACTION_VIEW, uri
                                )
                        );
                        Toast.makeText(context, "Thank You, for your valuable time", Toast.LENGTH_SHORT).show();


                    } catch (ActivityNotFoundException e) {
                        Uri uri = Uri.parse("http://play.google.com/store/appsdetails?id=" + APP_PACKAGE_NAME);
                        Toast.makeText(context, "Thank You, for your valuable time", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(Intent.ACTION_VIEW, uri

                        ));
                    }
                    finalDialog.dismiss();


                })
                .setNeutralButton("Later", (dialogInterface, i) -> {

                    if (editor != null) {

                        launch_count = prefs.getLong("launch_count", 0) - LAUNCH_UNTIL_PROMPT;
                        date_firstLaunch = System.currentTimeMillis() + (86400000 * DAYS_UNTIL_PROMPT);
                        editor.putLong("date_first_launch", date_firstLaunch);
                        editor.apply();

                        editor.putLong("launch_count", launch_count);
                        editor.apply();


                        editor.apply();

                    }


                    finalDialog1.dismiss();


                })

                .setNegativeButton("No, Thanks", (dialogInterface, i) -> {
                    if (editor != null) {
                        editor.putBoolean("dontshowagin", true);
                        editor.apply();

                    }
                    Toast.makeText(context, "Thank You, for valuable time", Toast.LENGTH_SHORT).show();
                    finalDialog2.dismiss();
                });


        dialog = builder.create();
        dialog.show();

    }
}
