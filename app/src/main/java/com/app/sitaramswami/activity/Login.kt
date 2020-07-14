package com.app.sitaramswami.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.sitaramswami.*
import com.app.sitaramswami.apiservice.RequestCall
import com.app.sitaramswami.interfaces.RetrofitListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.util.Log
import com.app.sitaramswami.R
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class Login : AppCompatActivity(), View.OnClickListener, RetrofitListener {
    val TAG = "GoogleActivity";
    val RC_SIGN_IN = 9001;

    lateinit var mAuth: FirebaseAuth
    lateinit var accessTokenTracker: AccessTokenTracker
    lateinit var profileTracker :ProfileTracker
    // [END declare_auth]
    lateinit var mCallbackManager: CallbackManager
    lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        login.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
        mCallbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().logOut();
        textSkip.setOnClickListener(this)
        gLogin.setOnClickListener(this)
        fbLogin.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
        btnForgotPass.setOnClickListener(this)

    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.login -> {
                if (validateForm()) {
                    login.isEnabled = false
                    progressBar.visibility = View.VISIBLE
                    var loginData = LoginData(email.text.toString(), password.text.toString())
                    var enquirySting = Gson().toJsonTree(loginData, LoginData::class.java)
                    RequestCall().post(Constants.LOGIN, enquirySting, this, 1)
                }

            }
            R.id.gLogin -> signIn()
            R.id.fbLogin -> fbLogin()
            R.id.textSkip->{
                var intent = CommonUtil.createIntent(this, MyShop::class.java, true)
                startActivity(intent)
            }
            R.id.btnSignUp -> {
                var intent = CommonUtil.createIntent(this, SignUp::class.java, false)
                startActivity(intent)
            }
            R.id.btnForgotPass -> {
                var intent = CommonUtil.createIntent(this, ForgotPassword::class.java, false)
                startActivity(intent)
            }
        }
    }


    fun validateForm(): Boolean {
        return when {
            !CommonUtil.isValidEmail(email.text.toString()) -> {
                email.setError("Invaild email")
                false
            }
            password.text.length <= 0 -> {
                password.setError("password must not be empty")
                false
            }
            else -> true
        }

    }

    override fun onResponse(response: JsonElement, fromCalling: Int) {
        login.isEnabled = true
        progressBar.visibility = View.GONE
        if (response != null) {
            var json = response.asJsonObject
            if (json.get("status").asInt > 0) {
                SessionManager(this).saveUser(json.get("data").toString())
                Log.d("Id", json.get("data").toString())
                var intent = CommonUtil.createIntent(this, MyShop::class.java, true)
                startActivity(intent)
                finish()
            } else {
                CommonUtil.showDialog(this, json.get("message").asString, false)
            }
        }

    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestServerAuthCode(getString(R.string.web_client_id))
                .requestEmail()
                .build()


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onError(message: String, fromCalling: Int) {

        login.isEnabled = true
        progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }

        } else {

            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }


    fun handleFacebookAccessToken(token: AccessToken) {

        Log.d(TAG, "handleFacebookAccessToken:$token")

        var credential = FacebookAuthProvider.getCredential(token.token);
        Log.d(TAG, "handleFacebookAccessTokn:$credential")
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {


                    override fun onComplete(task: Task<AuthResult>) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success")
                            var user = mAuth.currentUser;
                            Log.d(TAG, "signInWithCredential:success" + user?.displayName + user?.email + user?.photoUrl + user?.phoneNumber);
//                            Log.d(TAG, "signInWithCredential:success"+user);
                            var phn = user?.phoneNumber;

                            if (phn == null) {
                                var socialData = SocialData(user?.displayName, user?.email, "", user?.photoUrl.toString(), "facebook")
                                var enquirySting = Gson().toJsonTree(socialData, SocialData::class.java)
                                RequestCall().post(Constants.SOCIAL_LOGIN, enquirySting, this@Login, 1)
                            } else if (user?.email == null) {
                                var socialData = SocialData(user?.displayName, "", phn, user?.photoUrl.toString(), "facebook")
                                var enquirySting = Gson().toJsonTree(socialData, SocialData::class.java)
                                RequestCall().post(Constants.SOCIAL_LOGIN, enquirySting, this@Login, 1)
                            } else {
                                var socialData = SocialData(user?.displayName, user?.email, user?.phoneNumber, user?.photoUrl.toString(), "facebook")
                                var enquirySting = Gson().toJsonTree(socialData, SocialData::class.java)
                                RequestCall().post(Constants.SOCIAL_LOGIN, enquirySting, this@Login, 1)
                            }

                            progressBar.visibility = View.VISIBLE


                        } else {
                            Log.d(TAG, "signInWithCredential:failed");

                        }


                    }
                });
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this@Login, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful()) {
                            val user = mAuth.getCurrentUser()
                            Log.d(TAG, "signInWithCredential:success" + user?.photoUrl + user!!.phoneNumber + user?.displayName + user?.email);
                            progressBar.visibility = View.VISIBLE

                            var socialData = SocialData(user?.displayName, user?.email, "", user?.photoUrl.toString(), "gmail")
                            var enquirySting = Gson().toJsonTree(socialData, SocialData::class.java)
                            RequestCall().post(Constants.SOCIAL_LOGIN, enquirySting, this@Login, 1)
                        } else {

                        }
                    }
                })
    }


    private fun updateUI() {
        Log.d("Login", "You are logged in")
        var user = mAuth.getCurrentUser();
        Log.d(TAG, "signInWithCredential:success" + user?.displayName + user?.email + user?.photoUrl + user?.phoneNumber);
//                            Log.d(TAG, "signInWithCredential:success"+user);
        var phn = user?.phoneNumber;

        if (phn == null) {
            var socialData = SocialData(user?.displayName, user?.email, "", user?.photoUrl.toString(), "facebook")
            var enquirySting = Gson().toJsonTree(socialData, SocialData::class.java)
            RequestCall().post(Constants.SOCIAL_LOGIN, enquirySting, this@Login, 1)
        } else if (user?.email == null) {
            var socialData = SocialData(user?.displayName, "", phn, user?.photoUrl.toString(), "facebook")
            var enquirySting = Gson().toJsonTree(socialData, SocialData::class.java)
            RequestCall().post(Constants.SOCIAL_LOGIN, enquirySting, this@Login, 1)
        } else {
            var socialData = SocialData(user?.displayName, user?.email, user?.phoneNumber, user?.photoUrl.toString(), "facebook")
            var enquirySting = Gson().toJsonTree(socialData, SocialData::class.java)
            RequestCall().post(Constants.SOCIAL_LOGIN, enquirySting, this@Login, 1)
        }

        progressBar.visibility = View.VISIBLE

    }


    private fun fbLogin() {
        val currentUser = mAuth.currentUser;
        if (currentUser != null) {
            Log.d("Login", "hii" + currentUser.email + currentUser.photoUrl + currentUser.phoneNumber);
            updateUI()
        } else {
          //  mCallbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

            LoginManager.getInstance().registerCallback(mCallbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(result: LoginResult) {
                            handleFacebookAccessToken(result.accessToken);
                            Log.d(TAG, "facebook:onSuccess:$result")
                            Log.d(TAG, "Success" + result.accessToken);

                        }

                        override fun onCancel() {

                            Log.d(TAG, "FacebookExceptionCancel");
                        }

                        override fun onError(error: FacebookException?) {
                            Log.d(TAG, "FacebookException");

                        }

                    });
        }
    }


}
