package com.yf.myweather.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yf.myweather.R;
import com.yf.myweather.adapter.ImageAdapter;
import com.yf.myweather.adapter.VPAdapter;
import com.yf.myweather.utils.AppUtils;
import com.yf.myweather.view.MyGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ImageFragment extends Fragment {
    private MyGridView gvLocal;
    private MyGridView gvGlob;
    private View view;
    private ImageAdapter mAdapter;
    private ImageAdapter adapter;
    private ViewPager ivVP;
    private VPAdapter vpAdapter;
    private int pic[] = {1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 9, 0};
    private List<ImageView> mImageViews=new ArrayList<>();
    private FloatingActionButton test_fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ui_image_list, container, false);
        initView();
        initVpData();
        return view;
    }

    private void initVpData() {
        mImageViews.clear();
        for (int i = 0; i < 4; i++) {
            ImageView imageView1=new ImageView(getActivity());
            imageView1.setImageResource(R.drawable.dog);
            mImageViews.add(imageView1);
        }
        vpAdapter.notifyDataSetChanged();
    }

    private void initView() {
        int height = AppUtils.getHeight(getActivity());
        final int width = AppUtils.getWidth(getActivity());
        gvLocal = (MyGridView) view.findViewById(R.id.gvLocal);
        gvGlob = (MyGridView) view.findViewById(R.id.gvGlob);
        test_fab = (FloatingActionButton) view.findViewById(R.id.test_fab);
        ivVP= (ViewPager) view.findViewById(R.id.ivVP);
        vpAdapter=new VPAdapter(mImageViews,getActivity());
        ivVP.setAdapter(vpAdapter);

        mAdapter = new ImageAdapter(getActivity(), pic, width, height);
        gvLocal.setAdapter(mAdapter);
        adapter = new ImageAdapter(getActivity(), pic, width, height);
        gvGlob.setAdapter(adapter);
        test_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                View dialogView=LayoutInflater.from(getActivity()).inflate(R.layout.ui_dialog,null);
                RelativeLayout takePic= (RelativeLayout) dialogView.findViewById(R.id.takePic);
                RelativeLayout chosePic= (RelativeLayout) dialogView.findViewById(R.id.chosePic);
                chosePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        GalleryFinal.openGallerySingle(1, new GalleryFinal.OnHanlderResultCallback() {
                            @Override
                            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                              if(reqeustCode==1){
                                  if(resultList!=null&&resultList.size()>0){
                                      PhotoInfo info = resultList.get(0);
                                      upLoadImage(info.getPhotoPath());
                                  }
                              }
                            }

                            @Override
                            public void onHanlderFailure(int requestCode, String errorMsg) {

                            }
                        });

                    }
                });
                takePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        GalleryFinal.openCamera(2, new GalleryFinal.OnHanlderResultCallback() {
                            @Override
                            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                if(reqeustCode==2){
                                    if(resultList!=null&&resultList.size()>0){
                                        PhotoInfo info = resultList.get(0);
                                        upLoadImage(info.getPhotoPath());
                                    }
                                }
                            }
                            @Override
                            public void onHanlderFailure(int requestCode, String errorMsg) {

                            }
                        });

                    }
                });
                dialog.setContentView(dialogView);
                dialog.show();

            }
        });
    }

    private void upLoadImage(String path) {
        BmobFile file=new BmobFile(new File(path));
        file.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getActivity(), "upload success ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "upload fail:"+e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }
        });
    }
}
