package com.ymm.displaydemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ymm.displaydemo.model.GitHubService;
import com.ymm.displaydemo.model.Repo;
import com.ymm.lib.network.core.Call;
import com.ymm.lib.network.core.Callback;
import com.ymm.lib.network.core.Response;
import com.ymm.lib.network.core.ServiceManager;

import java.util.ArrayList;
import java.util.List;

public class SearchRepoFragment extends Fragment {
    EditText mUserEt;
    RecyclerView mRecyclerView;
    RepoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_repo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUserEt = (EditText) view.findViewById(R.id.user_name_et);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        view.findViewById(R.id.search_repo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRepo();
            }
        });
    }

    public void searchRepo() {

        if (mUserEt.getText() == null) return;
        final String name = mUserEt.getText().toString();


        //read service implementation
        GitHubService githubService = ServiceManager.getService(ApiConstants.BASE_URL, GitHubService.class);
        Call<List<Repo>> call = githubService.listRepos(name);
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "搜索中...", "", true);
        //request the server
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                progressDialog.dismiss();
                List<Repo> repos = response.body();

                if (repos != null) {
//                    final String repo = "Repositories of " + name + ":" + repos;
//                    System.out.println(repo);
//                    Toast.makeText(getContext(), repo, Toast.LENGTH_SHORT).show();

                    setData(repos);
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Search failed", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void setData(List<Repo> list) {
        if (mAdapter == null) {
            mAdapter = new RepoAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();
    }

    private class RepoAdapter extends RecyclerView.Adapter<RepoHolder> {

        private List<Repo> mData = new ArrayList<>();

        public void setData(List<Repo> data) {
            mData.clear();
            mData.addAll(data);
        }

        @Override
        public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RepoHolder(parent);
        }

        @Override
        public void onBindViewHolder(RepoHolder holder, int position) {
            holder.setData(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }
}
