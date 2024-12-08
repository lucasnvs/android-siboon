package com.lucasnvs.siboon.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lucasnvs.siboon.data.repository.SectionRepository;
import com.lucasnvs.siboon.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SectionAdapter sectionAdapter;
    private HomeViewModel homeViewModel;

    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SectionRepository sectionRepository = new SectionRepository(requireContext());
        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                //noinspection unchecked
                return (T) new HomeViewModel(sectionRepository);
            }
        }).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recyclerViewSections.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sectionAdapter = new SectionAdapter(getContext(), new ArrayList<>());
        binding.recyclerViewSections.setAdapter(sectionAdapter);

        homeViewModel.sections.observe(getViewLifecycleOwner(), sections -> {
            sectionAdapter.setSections(sections);
            sectionAdapter.notifyDataSetChanged();
        });

        homeViewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Log.e("HomeFragment", "Erro recebido: " + error);
                Toast.makeText(getContext(), "Erro: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        homeViewModel.loadSections();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
