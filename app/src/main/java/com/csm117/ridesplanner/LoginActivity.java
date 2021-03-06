package com.csm117.ridesplanner;

import com.csm117.ridesplanner.entities.Sheet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.services.script.model.*;
import java.util.Map;

import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    static public GoogleAccountCredential mCredential;
    private TextView mOutputText;
    ProgressDialog mProgress;
    private TextView mSignedInAs;
    private Button mSigninButton;
    private Button mSignoutButton;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {
            "https://www.googleapis.com/auth/drive",
            "https://www.googleapis.com/auth/drive.apps.readonly",
            "https://www.googleapis.com/auth/script.storage",
            "https://www.googleapis.com/auth/spreadsheets"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Signed in as: ");
        // Initialize credentials and service object.
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));

        mSignedInAs = (TextView) this.findViewById(R.id.signed_in_as);
        mSigninButton = (Button) this.findViewById(R.id.sign_in_button);
        if (mCredential.getSelectedAccountName() != null) {
            mSignedInAs.setText(mCredential.getSelectedAccountName());
            mSigninButton.setText("Verify app permissions");
        }

        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGooglePlayServicesAvailable()) {
                    refreshResults();
                } else {
                    Snackbar.make(view, "Google Play Services required: " +
                                    "after installing, close and relaunch this app.",
                            Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });

        mSignoutButton = (Button) this.findViewById(R.id.sign_out_button);
        mSignoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCredential.getSelectedAccountName() != null) {
                    mCredential.setSelectedAccountName(null);
                    mSigninButton.setText("Sign In Or Register");
                    mSignedInAs.setText("n/a");
                }
            }
        });



    }

    /**
     * Extend the given HttpRequestInitializer (usually a credentials object)
     * with additional initialize() instructions.
     *
     * @param requestInitializer the initializer to copy and adjust; typically
     *         a credential object.
     * @return an initializer with an extended read timeout.
     */
    private static HttpRequestInitializer setHttpTimeout(
            final HttpRequestInitializer requestInitializer) {
        return new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest)
                    throws java.io.IOException {
                requestInitializer.initialize(httpRequest);
                // This allows the API to call (and avoid timing out on)
                // functions that take up to 6 minutes to complete (the maximum
                // allowed script run time), plus a little overhead.
                httpRequest.setReadTimeout(380000);
            }
        };
    }
    /**
     * Called whenever this activity is pushed to the foreground, such as after
     * a call to onCreate().
     */
    @Override
    protected void onResume() {
        super.onResume();
        // if (isGooglePlayServicesAvailable()) {
        //     refreshResults();
        // } else {
        //     mOutputText.setText("Google Play Services required: " +
        //             "after installing, close and relaunch this app.");
        // }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    isGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        mCredential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mSignedInAs.setText(accountName);
                        mProgress.setMessage("Signed in as: " + accountName);
                        mSigninButton.setText("Verify app permissions");

                    }
                } else if (resultCode == RESULT_CANCELED) {
                    //mOutputText.setText("Account unspecified.");
                    mSignedInAs.setText("n/a");
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode != RESULT_OK) {
                    chooseAccount();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Attempt to get a set of data from the Google Apps Script Execution API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */
    private void refreshResults() {
        if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            mProgress.setMessage("Signed in as: " +
                    mCredential.getSelectedAccountName() + "\nGetting Sheet " +
                    "Data");
            if (isDeviceOnline()) {
                new MakeRequestTask(mCredential).execute();
            } else {
                //mOutputText.setText("No network connection available.");
            }

            //REDIRECT
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                }
            }, 2000);
        }
    }

    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    private void chooseAccount() {
        startActivityForResult(
                mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date. Will
     * launch an error dialog for the user to update Google Play Services if
     * possible.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        final int connectionStatusCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS ) {
            return false;
        }
        return true;
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                connectionStatusCode,
                LoginActivity.this,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * An asynchronous task that handles the Google Apps Script Execution API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, String> {
        private com.google.api.services.script.Script mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.script.Script.Builder(
                    transport, jsonFactory, setHttpTimeout(credential))
                    .setApplicationName("Google Apps Script Execution API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Apps Script Execution API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected String doInBackground(Void... params) {
            try {
                return getSheetID();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Call the API to run an Apps Script function that returns a list
         * of folders within the user's root directory on Drive.
         *
         * @return list of String folder names and their IDs
         * @throws IOException
         */
        private String getSheetID()
                throws IOException, GoogleAuthException {
            // ID of the script to call. Acquire this from the Apps Script editor,
            // under Publish > Deploy as API executable.
            String scriptId = "MkPlmM8tc25HedhUPYY-U7sw9KAQap61g";
            String sheetID = "";
            List<Object> parameters = new ArrayList<>();
            parameters.add(Sheet.ridesSheetName);
            // Create an execution request object.
            ExecutionRequest request = new ExecutionRequest()
                    .setFunction("getOrCreateRidesSheetID")
                    .setParameters(parameters);

            // Make the request.
            Operation op =
                    mService.scripts().run(scriptId, request).execute();

            // Print results of request.
            if (op.getError() != null) {
                throw new IOException(getScriptError(op));
            }
            if (op.getResponse() != null &&
                    op.getResponse().get("result") != null) {
                // The result provided by the API needs to be cast into
                // the correct type, based upon what types the Apps Script
                // function returns. Here, the function returns an Apps
                // Script Object with String keys and values, so must be
                // cast into a Java Map (folderSet).
                sheetID = (String) (op.getResponse().get("result"));
            }
            return sheetID;
        }

        /**
         * Interpret an error response returned by the API and return a String
         * summary.
         *
         * @param op the Operation returning an error response
         * @return summary of error response, or null if Operation returned no
         *     error
         */
        private String getScriptError(Operation op) {
            if (op.getError() == null) {
                return null;
            }

            // Extract the first (and only) set of error details and cast as a Map.
            // The values of this map are the script's 'errorMessage' and
            // 'errorType', and an array of stack trace elements (which also need to
            // be cast as Maps).
            Map<String, Object> detail = op.getError().getDetails().get(0);
            List<Map<String, Object>> stacktrace =
                    (List<Map<String, Object>>)detail.get("scriptStackTraceElements");

            java.lang.StringBuilder sb =
                    new StringBuilder("\nScript error message: ");
            sb.append(detail.get("errorMessage"));

            if (stacktrace != null) {
                // There may not be a stacktrace if the script didn't start
                // executing.
                sb.append("\nScript error stacktrace:");
                for (Map<String, Object> elem : stacktrace) {
                    sb.append("\n  ");
                    sb.append(elem.get("function"));
                    sb.append(":");
                    sb.append(elem.get("lineNumber"));
                }
            }
            sb.append("\n");
            return sb.toString();
        }


        @Override
        protected void onPreExecute() {
            //mOutputText.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(String output) {
            mProgress.hide();
            if (output == null || output.isEmpty()) {
                Log.e("Sheet", "Unable to grab/create sheetID!");
            } else {
                Sheet.updateSheetID(output);
                // Redirect to new intent
                Intent nextScreen = new Intent(getApplicationContext(), ViewRidesActivity.class);
                startActivity(nextScreen);
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            LoginActivity.REQUEST_AUTHORIZATION);
                } else {
                    //mOutputText.setText("The following error occurred:\n"
                            //+ mLastError.getMessage());
                }
            } else {
                //mOutputText.setText("Request cancelled.");
            }
        }
    }
}

