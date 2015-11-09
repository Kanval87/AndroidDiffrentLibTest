package com.volley.swastik;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.volley.swastik.dummy.DummyContent;
import com.volley.swastik.endPointVolley.EndPointListener;
import com.volley.swastik.endPointVolley.EndPointResult;
import com.volley.swastik.endPointVolley.InstagramAdapter;
import com.volley.swastik.endPointVolley.Media;

import hugo.weaving.DebugLog;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
@DebugLog
public class ItemDetailFragment extends Fragment implements EndPointListener, AbsListView.OnScrollListener {

    private Media media;
    private InstagramAdapter instagramAdapter;
    private ListView listView;
    private ProgressBar progressBar;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        if (getArguments().containsKey(ARG_ITEM_ID)) {
//            // Load the dummy content specified by the fragment
//            // arguments. In a real-world scenario, use a Loader
//            // to load content from a content provider.
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
//        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        getComponents(rootView);
        setAdapter();
        addProgressBar(rootView);

//        // Show the dummy content as text in a TextView.
//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
//        }

        return rootView;
    }


    private void addProgressBar(View rootView) {
        progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setIndeterminate(true);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 24));
        final FrameLayout decorView = (FrameLayout) getActivity().getWindow().getDecorView();
        decorView.addView(progressBar);
        ViewTreeObserver observer = progressBar.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                View contentView = decorView.findViewById(android.R.id.content);
                progressBar.setY(contentView.getY() - 15);
                ViewTreeObserver observer = progressBar.getViewTreeObserver();
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN) {
                    observer.removeOnGlobalLayoutListener(this);
                } else {
                    // do something for phones running an SDK before lollipop
                }
            }
        });
    }

    private void getComponents(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setOnScrollListener(this);
    }

    private void setAdapter() {
        if (instagramAdapter == null) {
            instagramAdapter = new InstagramAdapter(media = new Media("selfie"), getContext());
            media.addListener(this);
        }
        listView.setAdapter(instagramAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instagramAdapter.dispose();
        media.removeListener(this);
    }

    @Override
    public void onNetworkOffline() {
        Toast.makeText(getContext(), "Network offline, check your connection", Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStartLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onLoadMoreDataSuccess(EndPointResult endPointResult) {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadingMoreDataFail(Exception exception) {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onInstagramNotResponding() {
        Toast.makeText(getContext(), "Instagram not responding", Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        if (instagramAdapter == null) return;
        if (i + 1 >= i3 - i2) instagramAdapter.loadMoreData();
    }
}
