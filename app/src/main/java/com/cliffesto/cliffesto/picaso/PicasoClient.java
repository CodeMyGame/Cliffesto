package com.cliffesto.cliffesto.picaso;

import android.content.Context;
import android.widget.ImageView;

import com.cliffesto.cliffesto.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Kapil Malviya on 7/24/2016.
 */
public class PicasoClient {
    public static void downLoadImg(Context c, String url, ImageView imageView){
        if(url!=null && url.length()>0){
            Picasso.with(c).load(url).placeholder(R.drawable.img_gallary).into(imageView);
        }else{
            Picasso.with(c).load(R.drawable.img_gallary).into(imageView);
        }
    }
}
