package android.training.demoapp.ui.activities;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.training.demoapp.R;
import android.training.demoapp.adapters.PagerAdapter;
import android.training.demoapp.ui.activities.baseMenu.BaseNavDrawerActivity;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

public class TabsListadoActivity
        extends BaseNavDrawerActivity
        implements SetUpAlertDialog.SetUpAlertDialogListener
{
    private FloatingActionButton fab;
    Context context;


    private String sharedPrefFile = "ajustes_share_pref";
    private SharedPreferences mPreferences;


    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus
        // (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // The activity is about to be restarted.
    }
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
//https://guides.codepath.com/android/fragment-navigation-drawer
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_tabs);
        context = this;
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        this.setTabs();
    }



    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        Toast.makeText(context, String.valueOf(dialog.getView()), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(context, String.valueOf(dialog), Toast.LENGTH_SHORT).show();

    }


//private void mostrarAjustes(){
//    fab = findViewById(R.id.add_fab);
//    fab.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            DialogFragment newFragment = new SetUpAlertDialog();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            newFragment.show(fragmentManager, "Ajustes");
//
//        }
//    });
//}


    private void setTabs(){

//        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1).setIcon(R.drawable.ic_location));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2).setIcon(R.drawable.ic_euros));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3).setIcon(R.drawable.ic_euros));


        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
    }





}


