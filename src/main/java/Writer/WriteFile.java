package Writer;

import Model.Trendyol.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteFile {
    public void writeAFile(Product product , JFileChooser getSelectedFile){
        try{
            File file = new File(getSelectedFile.getSelectedFile()+"\\"+product.name);
            if(!file.exists()) {
                file.mkdirs();
            }
            getImageFromURL(product.images , file);;
            writeFileInDirectory(file , product);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getImageFromURL(List<String> getImage , File file) throws InterruptedException {
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

    private void writeFileInDirectory(File file , Product product) throws InterruptedException {
        AtomicReference<String> contentDescriptionCombine = new AtomicReference<>();
        int count = 3;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(product.name);
        XSSFRow row;
        product.contentDescriptions.forEach(contentDescriptions -> {
            contentDescriptionCombine.set(contentDescriptions.description+"");
        });
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
        File getDirectory = new File(file , "productDetail.xlsx");
        productDetail.put("1",new Object[] { "Ürün adı", "Ürün Kodu", "Ürün Barkodu" , "Fiyat" , "Renk" , "Beden" , "Beden Barkodu" , "Beden Fiyatı" , "Beden Kodu"});
        productDetail.put("2",new Object[] { product.name , product.productCode , product.variants.get(0).barcode , product.price.originalPrice.text , product.color , String.valueOf(product.allVariants.get(0).value) , String.valueOf(product.allVariants.get(0).barcode), String.valueOf(product.allVariants.get(0).price) , String.valueOf(product.allVariants.get(0).itemNumber) });
        for(int i = 1 ; i < product.allVariants.size(); i++){
            productDetail.put(String.valueOf(count), new Object[] {"" , "" , "" , "" , "" , String.valueOf(product.allVariants.get(i).value) , String.valueOf(product.allVariants.get(i).barcode) , String.valueOf(product.allVariants.get(i).price) , String.valueOf(product.allVariants.get(i).itemNumber)});
            count++;
        }
        Set<String> keyId = productDetail.keySet();
        int rowId = 0;
        try{
            for(String key : keyId){
                row = spreadsheet.createRow(rowId++);
                Object[] objectArr = productDetail.get(key);
                int cellid = 0;
                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String)obj);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(getDirectory);
            workbook.write(fileOutputStream);
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
