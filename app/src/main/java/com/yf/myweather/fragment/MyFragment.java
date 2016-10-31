package com.yf.myweather.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.yf.myweather.R;
import com.yf.myweather.activity.Login;
import com.yf.myweather.activity.WaitActivity;
import com.yf.myweather.activity.XingZuoActivity;
import com.yf.myweather.model.User;
import com.yf.myweather.utils.AppManager;
import com.yf.myweather.utils.StoreUtils;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/10/27.
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private ImageView userLogo;
    private TextView locationLabe;
    private String uId;
    private TextView MrakLogin;
    private  RelativeLayout collLay;
    private  RelativeLayout upLay;
    private  RelativeLayout remarkLay;
    private  RelativeLayout ysLay;
    private  RelativeLayout tcLay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.ui_my_fg, container, false);
        initView();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        uId = StoreUtils.getUid(getActivity());
        if (uId != null && uId.trim().length() > 0) {
            initUserData(uId);
        } else {
            tcLay.setVisibility(View.INVISIBLE);
            MrakLogin.setText("请 登 录");
            userLogo.setImageResource(R.drawable.touxiang);
        }
    }

    private void initUserData(String id) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", id)
                .findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null && list.size() > 0) {
                            User user = list.get(0);
                            String name = user.getUserName();
                            BmobFile face = user.getFace();
                            MrakLogin.setText(name);
                                Glide.with(getActivity()).load(face.getUrl())
                                        .asBitmap()
                                        .placeholder(R.drawable.touxiang)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into(new BitmapImageViewTarget(userLogo) {
                                            @Override
                                            protected void setResource(Bitmap resource) {
                                                RoundedBitmapDrawable circularBitmapDrawable =
                                                        RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                                                circularBitmapDrawable.setCircular(true);
                                                userLogo.setImageDrawable(circularBitmapDrawable);
                                            }
                                        });
                        } else {

                        }

                    }
                });
        ;

    }

    private void initView() {
        MrakLogin = (TextView) mView.findViewById(R.id.MrakLogin);
        userLogo = (ImageView) mView.findViewById(R.id.userLogo);
        locationLabe = (TextView) mView.findViewById(R.id.locationLabe);
        collLay= (RelativeLayout) mView.findViewById(R.id.collLay);
        upLay= (RelativeLayout) mView.findViewById(R.id.upLay);
        remarkLay= (RelativeLayout) mView.findViewById(R.id.remarkLay);
        ysLay= (RelativeLayout) mView.findViewById(R.id.ysLay);
        tcLay= (RelativeLayout) mView.findViewById(R.id.tcLay);
        tcLay.setOnClickListener(this);
        ysLay.setOnClickListener(this);
        remarkLay.setOnClickListener(this);
        upLay.setOnClickListener(this);
        collLay.setOnClickListener(this);
        locationLabe.setText(StoreUtils.getAddress(getActivity()));
        userLogo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userLogo:
                if (uId.trim().length() > 0) {
                    //个人资料修改
                    createDialog();
                } else {
                    //登录
                    startActivity(new Intent(getActivity(), Login.class));
                }
                break;
            case R.id.collLay:
                if (uId.trim().length() > 0) {
                    //TODO 我的收藏
                    startActivity(new Intent(getActivity(), WaitActivity.class));
                } else {
                    //登录
                    startActivity(new Intent(getActivity(), Login.class));
                }
                break;
            case R.id.upLay:
                if (uId.trim().length() > 0) {
                    //TODO 我的上传
                    startActivity(new Intent(getActivity(), WaitActivity.class));
                } else {
                    //登录
                    startActivity(new Intent(getActivity(), Login.class));
                }
                break;
            case R.id.remarkLay:
                if (uId.trim().length() > 0) {
                    //TODO 我的评论
                    startActivity(new Intent(getActivity(), WaitActivity.class));
                } else {
                    //登录
                    startActivity(new Intent(getActivity(), Login.class));
                }

                break;
            case R.id.ysLay:
                if (uId.trim().length() > 0) {
                    //TODO  我的运势
                    startActivity(new Intent(getActivity(), XingZuoActivity.class));
                } else {
                    //登录
                    startActivity(new Intent(getActivity(), Login.class));
                }

                break;
            case R.id.tcLay:
                //TODO 退出
                StoreUtils.cleanUid(getActivity());
                AppManager.finishActivity();
                break;


        }

    }

    private void createDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.ui_dialog, null);
        RelativeLayout takePic = (RelativeLayout) dialogView.findViewById(R.id.takePic);
        RelativeLayout chosePic = (RelativeLayout) dialogView.findViewById(R.id.chosePic);
        chosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                GalleryFinal.openGallerySingle(1, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        if (reqeustCode == 1) {
                            if (resultList != null && resultList.size() > 0) {
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
                        if (reqeustCode == 2) {
                            if (resultList != null && resultList.size() > 0) {
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

    private void upLoadImage(String path) {
        final BmobFile file = new BmobFile(new File(path));
        file.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    changeLogo(file);
                } else {
                    Toast.makeText(getActivity(), "上传失败" + e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void changeLogo(BmobFile file) {
        User user = new User();
        user.setFace(file);
        user.update(uId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    initUserData(uId);
                    Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "上传失败" + e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


