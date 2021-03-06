package com.example.whiteball.fragments;

import android.graphics.Color;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whiteball.utility.Constants;
import com.example.whiteball.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private FragmentManager manager;

    private FrameLayout menuLayout;

    private AnimationDrawable animation;
    private Button startButton;
    private Button settingsButton;
    private Button scoreButton;

    public MenuFragment(FragmentManager manager) {
        this.manager = manager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        this.menuLayout = root.findViewById(R.id.fragment_menu);
        this.menuLayout.setBackgroundColor(Color.BLACK);
        this.menuLayout.setBackgroundResource(R.drawable.animation_background);
        this.animation = (AnimationDrawable) this.menuLayout.getBackground();
        this.animation.start();

        this.startButton = root.findViewById(R.id.start_button);
        this.startButton.setOnClickListener(v -> {
            this.animation.stop();
            FragmentTransaction t = this.manager.beginTransaction();
            GameFragment gameFragment = new GameFragment(this.manager);
            t.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            t.replace(R.id.fragment_container, gameFragment);
            t.commit();
            Constants.CURRENT_FRAGMENT = gameFragment;
        });

        this.settingsButton = root.findViewById(R.id.settings_button);
        this.settingsButton.setOnClickListener(v -> {
            this.animation.stop();
            FragmentTransaction t = this.manager.beginTransaction();
            SettingsFragment settingsFragment = new SettingsFragment(this.manager);
            t.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            t.replace(R.id.fragment_container, settingsFragment);
            t.commit();
            Constants.CURRENT_FRAGMENT = settingsFragment;
        });

        this.scoreButton = root.findViewById(R.id.score_button);
        this.scoreButton.setOnClickListener(v -> {
            this.animation.stop();
            FragmentTransaction t = this.manager.beginTransaction();
            ScoreFragment scoreFragment = new ScoreFragment(this.manager);
            t.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            t.replace(R.id.fragment_container, scoreFragment);
            t.commit();
            Constants.CURRENT_FRAGMENT = scoreFragment;
        });

        return root;
    }

    @Override
    public void onDestroy() {
        this.animation.stop();
        super.onDestroy();
    }
}
