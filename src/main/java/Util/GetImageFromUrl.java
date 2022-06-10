package Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GetImageFromUrl {
    public void getLinkFromUrlAndWriteFolder(String getLink , File file , int getCount){
        if(!getLink.equals("")){
            try{
                URL url = new URL(getLink);
                BufferedImage bufferedImage = ImageIO.read(url);
                ImageIO.write(bufferedImage , "png" , new File(file , "image "+getCount+".png"));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
