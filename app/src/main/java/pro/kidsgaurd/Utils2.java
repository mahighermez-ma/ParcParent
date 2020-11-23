package pro.kidsgaurd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

class Utils2
{
    /**
     * Convert encoded image string to bitmap.
     *
     * @param  encodedImage     String that you want to convert to bitmap.
     * @return bitmap form encoded image.
     */
    public static Bitmap getBitmap(String encodedImage)
    {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return (BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
    }
}