package com.fairytale.fortunetarot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.adapter.InfoListAdapter;
import com.fairytale.fortunetarot.entity.InfoEntity;
import com.fairytale.fortunetarot.http.request.RequestCode;
import com.fairytale.fortunetarot.presenter.BasePresenter;
import com.fairytale.fortunetarot.presenter.InfoListPresenter;
import com.fairytale.fortunetarot.view.InfoListView;
import com.fairytale.fortunetarot.widget.CustomFontTextView;
import com.fairytale.fortunetarot.widget.LoadMoreFooterView;
import com.fairytale.fortunetarot.widget.RefreshHeaderView;
import com.fairytale.fortunetarot.widget.spinnerloading.SpinnerLoading;

import java.util.ArrayList;
/**
 * Created by lizhen on 2018/2/8.
 */

public class InfoListFragment extends BaseFragment implements InfoListView {
    private IRecyclerView recyclerView;
    private InfoListPresenter mInfoListPresenter;
    private View rootView;
    private Context context;
    private InfoListAdapter mInfoListAdapter;
    private LoadMoreFooterView loadMoreFooterView;
    private RefreshHeaderView refreshHeaderView;
    private long refreshTime;
    private SpinnerLoading spinnerLoading;
    private LinearLayout ll_loading;
    private CustomFontTextView tv_loading;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void setArguments(Bundle args) {
        mInfoListPresenter = new InfoListPresenter(this);
        mInfoListPresenter.setType(args.getInt("type"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_info_list,container,false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        recyclerView = initViewById(view,R.id.iRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        if (mInfoListAdapter == null) {
            mInfoListAdapter = new InfoListAdapter(context);
        }
        recyclerView.setLoadMoreEnabled(false);
        recyclerView.setRefreshEnabled(true);
        loadMoreFooterView = new LoadMoreFooterView(context);
        recyclerView.setLoadMoreFooterView(loadMoreFooterView);

        refreshHeaderView = new RefreshHeaderView(context);
        recyclerView.setRefreshHeaderView(refreshHeaderView);
        recyclerView.setIAdapter(mInfoListAdapter);

        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mInfoListPresenter.reset();
                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                mInfoListPresenter.startRequestByCode(RequestCode.REQUEST_INFO_LIST);
            }
        });

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (loadMoreFooterView.canLoadMore() && mInfoListAdapter.getItemCount() > 0) {
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                    mInfoListPresenter.startRequestByCode(RequestCode.REQUEST_INFO_LIST);
                }
            }
        });
        spinnerLoading = initViewById(view,R.id.spinnerLoading);
        ll_loading = initViewById(view,R.id.ll_loading);
        tv_loading = initViewById(view,R.id.tv_loading);

        if (System.currentTimeMillis() - refreshTime >= 1000 * 60 * 15) {   // 在滑动的过程中15分钟之内不重新加载数据
            refreshTime = System.currentTimeMillis();
            mInfoListPresenter.reset();
            mInfoListPresenter.startRequestByCode(RequestCode.REQUEST_INFO_LIST);
        }
    }

    @Override
    BasePresenter setPresenter() {
        return mInfoListPresenter;
    }

    @Override
    public void refresh() {
        mInfoListPresenter.reset();
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        mInfoListPresenter.startRequestByCode(RequestCode.REQUEST_INFO_LIST);
    }

    @Override
    public void stopRefresh() {
        recyclerView.setRefreshing(false);
    }

    @Override
    public void showRefreshData(ArrayList<InfoEntity> infoEntities) {
        stopRefresh();
        mInfoListAdapter.setData(infoEntities,false);
        recyclerView.setLoadMoreEnabled(true);
    }

    @Override
    public void showLoadMoreData(final ArrayList<InfoEntity> infoEntities) {
        loadMoreFooterView.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopLoadMore();
                mInfoListAdapter.setData(infoEntities,true);
            }
        },500);
    }

    @Override
    public void stopLoadMore() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
    }

    @Override
    public void showNoMore() {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
        recyclerView.setLoadMoreEnabled(false);
    }

    @Override
    public void showNoContentView() {
        ll_loading.setVisibility(View.VISIBLE);
        tv_loading.setVisibility(View.VISIBLE);
        spinnerLoading.setVisibility(View.GONE);
        tv_loading.setText("没有找到内容啦");
    }

    @Override
    public void hideNoContentView() {
        ll_loading.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        ll_loading.setVisibility(View.VISIBLE);
        tv_loading.setVisibility(View.VISIBLE);
        spinnerLoading.setVisibility(View.VISIBLE);
        tv_loading.setText("加载中");
    }

    @Override
    public void showErrorView() {
        ll_loading.setVisibility(View.VISIBLE);
        tv_loading.setVisibility(View.VISIBLE);
        spinnerLoading.setVisibility(View.GONE);
        tv_loading.setText("点击重新加载");
        tv_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
                refresh();
            }
        });
    }
}
