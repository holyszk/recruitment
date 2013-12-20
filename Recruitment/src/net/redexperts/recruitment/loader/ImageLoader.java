package net.redexperts.recruitment.loader;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageLoader extends BasicLoader<Bitmap> {

	@Override
	protected Bitmap parseData(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

}
