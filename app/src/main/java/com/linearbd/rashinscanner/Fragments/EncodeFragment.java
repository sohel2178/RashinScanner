package com.linearbd.rashinscanner.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linearbd.rashinscanner.Activities.EncodeActivity;
import com.linearbd.rashinscanner.Adapter.EncodeItemAdapter;
import com.linearbd.rashinscanner.Listener.ItemClickListener;
import com.linearbd.rashinscanner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EncodeFragment extends Fragment implements ItemClickListener {
    private RecyclerView rvEncode;
    private EncodeItemAdapter adapter;


    public EncodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new EncodeItemAdapter(getContext());
        adapter.setItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_encode, container, false);
        initView(view);

        return view;

    }

    private void initView(View view) {
        rvEncode = view.findViewById(R.id.rv_encode);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvEncode.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvEncode.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

        startEncodeActivity(position);

    }

    private void startEncodeActivity(int position) {
        Intent intent = new Intent(getContext(), EncodeActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
