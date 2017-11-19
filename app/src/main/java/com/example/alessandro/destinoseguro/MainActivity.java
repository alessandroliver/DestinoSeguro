package com.example.alessandro.destinoseguro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    //gerenciar os fragmentos da aplicaçao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        //recuperando o objeto p/ ultilizar

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //iniciar uma transaçao

        transaction.add(R.id.container, new MapsFragment(), "MapsFragment" );

        transaction.commitAllowingStateLoss();
        //confirmar a ateraçao

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar o menu; Isso adiciona itens à barra de ação se estiver presente.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showFragment(Fragment fragment, String name)
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //iniciar uma transaçao

        transaction.replace(R.id.container, fragment, name );
        //substituir por um fragmento novo que for adicionar

        transaction.commit();
        //confirmar a substituiçao do fragment
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_dentinoseguro:

                showFragment(new MapsFragment(), "MapsFragment");

                break;

            case R.id.nav_dentinoproviderv1:

                showFragment(new ExemploProviderFragmentV1(), "ExemploProviderFragmentV1");

                break;

            case R.id.nav_dentinoproviderv2_gps:

                showFragment(new ExemploProviderFragmentV2GPS(), "ExemploProviderFragmentV2GPS");

                break;

            case R.id.nav_placeautocompleteactivity:

                Intent intent = new Intent(this, PlaceAutoCompleteActivity.class);
                startActivity(intent);

                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
