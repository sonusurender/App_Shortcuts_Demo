package com.android_app_shortcuts_demo;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addDynamicShortcuts(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            addDynamicShortcuts();
        }
    }

    //Method to add dynamic shortcuts
    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addDynamicShortcuts() {
        //get Shortcut manager
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        //Add Compose Email Shortcut
        ShortcutInfo composeEmail = new ShortcutInfo.Builder(this, "compose_email")//add unique id
                .setShortLabel("Compose Email")//set short label
                .setLongLabel("Compose email in single tap")//set long label
                .setDisabledMessage("Compose email is disabled")//disabled message shown when shortcut is disabled
                .setIcon(Icon.createWithResource(this, R.drawable.ic_email))//set icon
                .setIntent(new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("mailto:surender@androhub.com")))//set intent : here i am opening Mail app to send email to an Email ID
                .build();

        ShortcutInfo openWebsite = new ShortcutInfo.Builder(this, "open_website")//add unique id
                .setShortLabel("Open Website")//set short label
                .setLongLabel("Click to see my blog")//set long label
                .setDisabledMessage("You cannot access the link as it is disabled")//disabled message shown when shortcut is disabled
                .setIcon(Icon.createWithResource(this, R.drawable.ic_web))//set icon
                .setIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.androhub.com")))//set intent : here i am opening browser app to open provided link
                .build();

        //NOTE : You can add more shortcuts here according to your requirement

        if (shortcutManager != null)
            shortcutManager.setDynamicShortcuts(Arrays.asList(composeEmail, openWebsite));//finally add all the created shortcut

    }

    /**
     * method to update dynamic shortcuts
     * @param view of button
     * here in this method an alert dialog will be shown up and user have to enter shortcut id to update the shortcut if required
     */
    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void updateDynamicShortcuts(View view) {

        //create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Shortcut Id to update");//set dialog title

        final EditText enterShortcutId = new EditText(this);//create edit text
        builder.setView(enterShortcutId);//add edit text to alert dialog

        //set positive button
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //get the entered shortcut id
                String getShortcutId = enterShortcutId.getText().toString().trim();

                //check shortcut id should not be empty
                if (!getShortcutId.equals("")) {
                    switch (getShortcutId) {
                        case "compose_email":
                            //if shortcut id is compose_email update the shortcut
                            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

                            ShortcutInfo composeEmail = new ShortcutInfo.Builder(MainActivity.this, getShortcutId)//pass the same shortcut id
                                    .setShortLabel("Compose Email")
                                    .setLongLabel("Compose email in single tap")
                                    .setIcon(Icon.createWithResource(MainActivity.this, R.drawable.ic_web))//i am only updating icon of the shortcut
                                    .setIntent(new Intent(Intent.ACTION_SENDTO,
                                            Uri.parse("mailto:surender@androhub.com")))
                                    .build();

                            if (shortcutManager != null) {
                                //finally update shortcuts and pass the created shortcut
                                shortcutManager.updateShortcuts(Arrays.asList(composeEmail));

                                Toast.makeText(MainActivity.this, "Shortcut updated.", Toast.LENGTH_SHORT).show();

                                //NOTE : You can update multiple shortcuts also
                            }

                            break;

                        case "open_website":
                            //if shortcut id is open_website update the shortcut
                            ShortcutManager shortcutManager1 = getSystemService(ShortcutManager.class);

                            ShortcutInfo openWebsite = new ShortcutInfo.Builder(MainActivity.this, getShortcutId)
                                    .setShortLabel("Open Website")
                                    .setLongLabel("Click to see my blog")
                                    .setIcon(Icon.createWithResource(MainActivity.this, R.drawable.ic_web))
                                    .setIntent(new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("http://androhub.com")))//I am also changing link
                                    .build();

                            if (shortcutManager1 != null) {

                                //finally update shortcuts and pass the created shortcut
                                shortcutManager1.updateShortcuts(Arrays.asList(openWebsite));

                                Toast.makeText(MainActivity.this, "Shortcut updated.", Toast.LENGTH_SHORT).show();

                                //NOTE : You can update multiple shortcuts also
                            }

                            break;

                        default:
                            //if shortcut id doesn't match show the below toast
                            Toast.makeText(MainActivity.this, "No Shortcut created with " + getShortcutId + " name.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //finally show the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    //method to delete/remove dynamic shortcuts
    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void deleteDynamicShortcuts(View view) {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {

            //NOTE : remove multiple or single shortcuts then uncomment the below line
            //shortcutManager.removeDynamicShortcuts(Arrays.asList("compose_email","open_website"));//here you have to provide created shortcut IDs you want to remove

            //NOTE : if you want to remove all shortcuts then use the below code
            shortcutManager.removeAllDynamicShortcuts();

            Toast.makeText(MainActivity.this, "Shortcut deleted.", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * NOTE : Things to know about PINNED Shortcuts
     * 1. PINNED SHORTCUTS only work in Android 8.0
     * 2. To add Pinned Shortcuts you should be having at least one static and dynamic shortcuts created and enabled or you have to create a new one
     * 3. Previously created static or dynamic shortcut ID will be required to create Pinned shortcuts
     */
    /**
     * method to add PINNED SHORTCUTS for the above created shortcuts
     * @param view of button
     */
    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addPinnedShortcuts(View view) {
        ShortcutManager mShortcutManager =
                getSystemService(ShortcutManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //check if device supports Pin Shortcut or not
            if (mShortcutManager != null && mShortcutManager.isRequestPinShortcutSupported()) {
                // Assumes there's already a shortcut with the ID "open_website".
                // The shortcut must be enabled.
                ShortcutInfo pinShortcutInfo =
                        new ShortcutInfo.Builder(MainActivity.this, "open_website").build();

                // Create the PendingIntent object only if your app needs to be notified
                // that the user allowed the shortcut to be pinned. Note that, if the
                // pinning operation fails, your app isn't notified. We assume here that the
                // app has implemented a method called createShortcutResultIntent() that
                // returns a broadcast intent.
                Intent pinnedShortcutCallbackIntent =
                        mShortcutManager.createShortcutResultIntent(pinShortcutInfo);

                // Configure the intent so that your app's broadcast receiver gets
                // the callback successfully.
                PendingIntent successCallback = PendingIntent.getBroadcast(MainActivity.this, 0,
                        pinnedShortcutCallbackIntent, 0);

                //finally ask user to add the shortcut to home screen
                mShortcutManager.requestPinShortcut(pinShortcutInfo,
                        successCallback.getIntentSender());
            }
        }
    }

    /**
     * method to disable created shortcuts
     */
    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void disableShortcuts(){
        ShortcutManager shortcutManager =
                getSystemService(ShortcutManager.class);
        if (shortcutManager!=null) {
            //you can disable created shortcuts by passing ID of shortcut you want to disable
            shortcutManager.disableShortcuts(Arrays.asList("compose_email"));

        }
    }


    /**
     * method to enable created shortcuts
     */
    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void enableShortcuts(){
        ShortcutManager shortcutManager =
                getSystemService(ShortcutManager.class);
        if (shortcutManager!=null) {
            //you can enable created shortcuts by passing ID of shortcut you want to enable
            shortcutManager.enableShortcuts(Arrays.asList("compose_email"));

        }
    }



}
