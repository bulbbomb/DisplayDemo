package com.ymm.displaydemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymm.displaydemo.model.Repo;
import com.ymm.lib.loader.ImageLoader;

/**
 * Created by SHI on 16/10/25
 */

public class RepoHolder extends RecyclerView.ViewHolder {

    ImageView mAvaterView;
    TextView mAvatarNameView;
    TextView mNameView;
    TextView mDescView;

    public RepoHolder(ViewGroup parent) {
        this(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false));
    }

    public RepoHolder(View itemView) {
        super(itemView);
        mAvaterView = (ImageView) itemView.findViewById(R.id.avatar);
        mAvatarNameView = (TextView) itemView.findViewById(R.id.avatar_name);
        mNameView = (TextView) itemView.findViewById(R.id.name);
        mDescView = (TextView) itemView.findViewById(R.id.desc);
    }

    public void setData(Repo data) {

        ImageLoader.with(itemView.getContext()).load(data.owner.avatar_url).into(mAvaterView);
        mAvatarNameView.setText(data.owner.login);
        mNameView.setText(data.full_name);
        mDescView.setText(data.description);
    }
}
