package com.arapeak.adkya.ui.activites;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.arapeak.adkya.listener.IOnBackPressed;
import com.arapeak.adkya.ui.fragments.ChalengeFragment;
import com.arapeak.adkya.ui.fragments.ChangePasswordFragment;
import com.arapeak.adkya.ui.fragments.EditPersonalFragment;
import com.arapeak.adkya.ui.fragments.HomeFragment;
import com.arapeak.adkya.ui.fragments.PersonalFragment;
import com.arapeak.adkya.R;
import com.arapeak.adkya.model.CurrentUserSaved;
import com.arapeak.adkya.utils.Common;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
, HomeFragment.OnFragmentInteractionListener {
    private static final String TAG = "visitor";

    //this is init fragment to replace between them
  Fragment fragment;
  static FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: activity ");




        //init fragment
        fragment= new HomeFragment();
//        fragment= HomeFragment.getInstance();
        //i make this because there is  aproblem if the user sign as visitor then return to login and sign as user will apear empty fragment
//        and not call onCreateView>> so i want be sure that every time i want new fragment to call onCreateView
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //update header data with image and title from saved user

        View  mHeaderView = navigationView.getHeaderView(0);
        TextView  mDrawerHeaderTitle =  mHeaderView.findViewById(R.id.textView13);
        CircleImageView profile_image_header =  mHeaderView.findViewById(R.id.profile_image_header);

        TextView  mDrawerHeaderClass =  mHeaderView.findViewById(R.id.textView134);


        if (Common.IS_VISITOR){


            mDrawerHeaderTitle.setText("زائر");

            Picasso.get().load(R.drawable.boy).into(profile_image_header);


            mDrawerHeaderClass.setVisibility(View.INVISIBLE);

            //show the menu for the visitor ..by default is in visibile

            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_sign_in).setVisible(true);
            nav_Menu.findItem(R.id.nav_register).setVisible(true);
            nav_Menu.findItem(R.id.nav_sgin_out).setVisible(false);

        }else {

            mDrawerHeaderTitle.setText(Common.retrieveUserDataPreferance(this).getName());

            Picasso.get().load(Common.retrieveUserDataPreferance(this).getImage()).into(profile_image_header);


            mDrawerHeaderClass.setVisibility(View.VISIBLE);
            mDrawerHeaderClass.setText(getClassStudentName(Common.retrieveUserDataPreferance(this).getClassId()));

            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_sign_in).setVisible(false);
            nav_Menu.findItem(R.id.nav_register).setVisible(false);
            nav_Menu.findItem(R.id.nav_sgin_out).setVisible(true);

        }


    }

    private String getClassStudentName(Integer  classId) {


        if (classId==null) return null;

        String classStudentName=null;

        switch (classId) {
            case 1:
                classStudentName = getResources().getString(R.string.class1);
                break;

            case 2:
                classStudentName = getResources().getString(R.string.class2);

                break;

            case 3:
                classStudentName = getResources().getString(R.string.class3);


                break;

            case 4:
                classStudentName = getResources().getString(R.string.class4);


                break;

            case 5:
                classStudentName = getResources().getString(R.string.class5);


                break;

            case 6:
                classStudentName = getResources().getString(R.string.class6);

                break;

            default:
                return classStudentName;

        }
        return classStudentName;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //handel on challenge fragment
            IOnBackPressed fragmentListner=((IOnBackPressed) ChalengeFragment.getInstance());



            if (Common.IS_VISITOR){
                startActivity(new Intent(getApplicationContext(), Login1Activity.class));
                finish();

            }else if (ChalengeFragment.isInChalange) {
                fragmentListner.onMyBackPressed();


            }else if (ChangePasswordFragment.getInstance().isInChangePasswordFragment){
                //go to home framgemnt
                HomeActivity.replaceFragmentFromActivity(new HomeFragment() );
            }else {

                startActivity(new Intent(getApplicationContext(), Login1Activity.class));
                finish();

            }

        }

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (Common.IS_VISITOR){
            if (id == R.id.nav_personal_file) {
                showReqiuredRegisterVisitorDailog();

            } else if (id == R.id.nav_home) {


                fragment= HomeFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();

            } else if (id == R.id.nav_settings) {

                showReqiuredRegisterVisitorDailog();

            } else if (id == R.id.nav_contact_us) {

                showReqiuredRegisterVisitorDailog();

            } else if (id == R.id.nav_who_us) {


                startActivity(new Intent(getApplicationContext(), WhoAreWeActivity.class));


            } else if (id == R.id.nav_sign_in) {
                //todo must sign out from google and facebook here>> because i check in login if google sign in
                signOutFromGoogle();
                signOutFromFacebook();
                Intent intent= new Intent(this,LoginActivity.class);
                intent.putExtra("is_from_txt_sign_in_account",true);
                 startActivity(intent);

            }else if (id == R.id.nav_register) {
                startActivity(new Intent(this,RegisterActivity.class));
            }

        }else {
            if (id == R.id.nav_personal_file) {

                fragment= PersonalFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();

            } else if (id == R.id.nav_home) {


                fragment= HomeFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();

            } else if (id == R.id.nav_settings) {

                fragment= EditPersonalFragment.getInstance();
                fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();

            } else if (id == R.id.nav_contact_us) {

                startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));


            } else if (id == R.id.nav_who_us) {


                startActivity(new Intent(getApplicationContext(), WhoAreWeActivity.class));


            } else if (id == R.id.nav_sgin_out) {
                //clear token and data
                CurrentUserSaved userSaved= new CurrentUserSaved();
                userSaved.setIsLogin(false);

               signOutFromFacebook();


                //logout from google
                signOutFromGoogle();



                Common.saveUserDataPreferance(getApplicationContext(),userSaved);

                startActivity(new Intent(getApplicationContext(), Login1Activity.class));
                finish();
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOutFromFacebook() {
        //logout from facebook
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null){
            LoginManager.getInstance().logOut();
        }
    }

    private void signOutFromGoogle() {


        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(this,gso);
        if (googleSignInClient!=null){

            googleSignInClient.signOut();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static void replaceFragmentFromActivity(Fragment fragment){
       fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
    }

    private void showReqiuredRegisterVisitorDailog() {

        final AlertDialog dialog;
        AlertDialog.Builder mBuilder;
        mBuilder = new AlertDialog.Builder(this );
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = li.inflate(R.layout.dialog_require_register_visitor, null);


        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();

        final Button btn_ok =  mView.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });

    }


}
