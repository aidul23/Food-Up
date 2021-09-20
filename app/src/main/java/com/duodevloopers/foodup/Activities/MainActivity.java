package com.duodevloopers.foodup.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.duodevloopers.foodup.ViewModel.MainActivityViewModel;
import com.duodevloopers.foodup.Model.PopularItem;
import com.duodevloopers.foodup.R;
import com.duodevloopers.foodup.databinding.ActivityMainBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity  {

    NavController navController;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    AppBarConfiguration appBarConfiguration;
    private BottomSheetBehavior sheetBehavior;
    private ConstraintLayout bottom_sheet;
    private ActivityMainBinding binding;
    private MainActivityViewModel model;
    NavHostFragment navHostFragment;
    Menu myMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        toolbar = findViewById(R.id.toolbarId);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        model = new ViewModelProvider(this).get(MainActivityViewModel.class);


        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.homeFragment);



        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations)
                .setOpenableLayout(drawerLayout)
                .build();


        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.navigation_drawer_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);

        NavigationUI.setupWithNavController(navView, navController);

        //bottomSheet
        sheetBehavior = BottomSheetBehavior.from(binding.getRoot().findViewById(R.id.bottom_sheet));
        sheetBehavior.setPeekHeight(0);
        sheetBehavior.setHideable(true);
        sheetBehavior.addBottomSheetCallback(callback);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


    }

    // listener for the bottom sheet
    private final BottomSheetBehavior.BottomSheetCallback callback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull @NotNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                    break;
                case BottomSheetBehavior.STATE_EXPANDED: {
                }
                break;
                case BottomSheetBehavior.STATE_COLLAPSED: {
                }
                break;
                case BottomSheetBehavior.STATE_DRAGGING:
                    break;
                case BottomSheetBehavior.STATE_SETTLING:
                    break;

            }
        }

        @Override
        public void onSlide(@NonNull @NotNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        //bottomSheet item
        ShapeableImageView foodItemImage = findViewById(R.id.food_item_image);
        TextView foodItemName = findViewById(R.id.food_item_name);
        TextView foodItemPrice = findViewById(R.id.food_item_price);
        TextView foodItemDescription = findViewById(R.id.food_item_description);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);

        foodItemImage.setShapeAppearanceModel(foodItemImage.getShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 30)
                .setTopRightCorner(CornerFamily.ROUNDED, 30)
                .build());

        model.getPopularItemMutableLiveData().observe(this, new Observer<PopularItem>() {
            @Override
            public void onChanged(PopularItem popularItem) {
                if(popularItem != null){
                    if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        sheetBehavior.setHideable(true);

                        foodItemImage.setImageResource(popularItem.getFoodImage());
                        foodItemName.setText(popularItem.getFoodName());
                        foodItemPrice.setText(popularItem.getFoodPrice()+" Tk");
//                        foodItemDescription.setVisibility(View.VISIBLE);
                        foodItemDescription.setText((popularItem.getFoodDes()));
                    } else {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }
        });

    }

    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
            || super.onOptionsItemSelected(item);

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else {
            super.onBackPressed();
        }
    }


    //toolbar back button
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout);
    }

}