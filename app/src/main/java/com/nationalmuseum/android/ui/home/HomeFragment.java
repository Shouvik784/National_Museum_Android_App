package com.nationalmuseum.android.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nationalmuseum.android.AnthroActivity;
import com.nationalmuseum.android.JewelleryActivity;
import com.nationalmuseum.android.ManuscriptActivity;
import com.nationalmuseum.android.PaintActivity;
import com.nationalmuseum.android.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up Facebook icon click
        ImageView facebookIcon = binding.facebookIcon;
        facebookIcon.setOnClickListener(v -> {
            String fbUserName = "your.username";
            Intent intent;

            try {
                // Try to open in Facebook app
                requireActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/share/1AfQLSMpNV/" + fbUserName));
            } catch (PackageManager.NameNotFoundException e) {
                // Fallback to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/share/1AfQLSMpNV/" + fbUserName));
            }

            startActivity(intent);
        });

        // Set up Instagram icon click
        ImageView instagramIcon = binding.instagramIcon;
        instagramIcon.setOnClickListener(v -> {
            String fbUserName = "your.username";
            Intent intent;

            try {
                // Try to open in Instagram app
                requireActivity().getPackageManager().getPackageInfo("com.instagram.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/its_me__shouvik?igsh=MWhuMmlrM2Q0dXk2eg==" + fbUserName));
            } catch (PackageManager.NameNotFoundException e) {
                // Fallback to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/its_me__shouvik?igsh=MWhuMmlrM2Q0dXk2eg==" + fbUserName));
            }

            startActivity(intent);
        });

        // Set up Youtube icon click
        ImageView youtubeIcon = binding.youtubeIcon;
        youtubeIcon.setOnClickListener(v -> {
            String fbUserName = "your.username";
            Intent intent;

            try {
                // Try to open in Youtube app
                requireActivity().getPackageManager().getPackageInfo(" com. google. android. youtube", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/@shouvik05?si=_ILKt0MokHYTueho" + fbUserName));
            } catch (PackageManager.NameNotFoundException e) {
                // Fallback to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/@shouvik05?si=_ILKt0MokHYTueho" + fbUserName));
            }

            startActivity(intent);
        });

        binding.paintingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaintActivity.class);
                startActivity(intent);
            }
        });
        binding.AnthroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnthroActivity.class);
                startActivity(intent);
            }
        });
        binding.JewelleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JewelleryActivity.class);
                startActivity(intent);
            }
        });
        binding.ManuscriptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManuscriptActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
