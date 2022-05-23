package Writer;

import Model.Trendyol.Product;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WriteFile {

    public void writeAFile(Product product){
        try{
            File file = new File("/Volumes/HDD/IdeaProjects/Read_Site/"+product.name);
            if(!file.exists()) {
                file.mkdirs();
            }else{
                System.out.println("Dosya var");
            }
            getImageFromURL(product.images , file);
            writeFileInDirectory(file , product);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getImageFromURL(List<String> getImage , File file){
        AtomicInteger count = new AtomicInteger();
        getImage.forEach(s -> {
            try {
                URL url = new URL("https://cdn.dsmcdn.com" + s);
                BufferedImage bufferedImage = ImageIO.read(url);
                ImageIO.write(bufferedImage , "png" , new File(file , "image"+(count.getAndIncrement())+".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void writeFileInDirectory(File file , Product product){
        File getDirectory = new File(file , "productDetails.txt");
        try{
            FileWriter fileWriter = new FileWriter(getDirectory);
            fileWriter.write("Urun adi: "+product.name+"\n Urun kategorisi: "+product.businessUnit+"\n Urun kodu: "+ product.productCode +"\n Fiyat: "+product.price.originalPrice.text+"\n Ürün Barkodu: "+product.variants.get(0).barcode+"\n Degerlendirme: "+String.valueOf(product.ratingScore.totalRatingCount));
            fileWriter.write("\n");
            product.allVariants.forEach(allVariants -> {
                if(!allVariants.value.equals("")) {
                    try {
                        fileWriter.write("\n"+("Beden: "+allVariants.value)+"\n"+("Urun kodu: "+allVariants.itemNumber)+"\n"+("Barkod: "+allVariants.barcode)+"\n"+("Fiyat: "+allVariants.price));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            fileWriter.write("\n");
            fileWriter.write("Urun aciklamasi");
            fileWriter.write("\n");
            product.contentDescriptions.forEach(contentDescriptions -> {
                try {
                    if(contentDescriptions != null) fileWriter.write(contentDescriptions.description);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });

            fileWriter.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
