package Writer;

import Model.Trendyol.AllVariants;
import Model.Trendyol.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.All;

public class WriteFile {
    public void writeAFile(Product product , JFileChooser getSelectedFile){
        try{
            File file = new File(getSelectedFile.getSelectedFile()+"\\"+product.nameWithProductCode);
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
                ImageIO.write(bufferedImage , "png" , new File(file , "image "+(count.getAndIncrement())+".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void writeFileInDirectory(File file , Product product) throws InterruptedException {
        int count = 3;

        AtomicReference<String> contentDescriptionCombine = new AtomicReference<>();
        product.contentDescriptions.forEach(contentDescriptions -> {
            contentDescriptionCombine.set(contentDescriptions.description);
        });

        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
        if(product.allVariants.get(0).value.equals("") || product.allVariants.get(0).value == null){
            productDetail.put("1",new Object[] { "Ürün adı", "Ürün Kodu", "Ürün Barkodu" , "Fiyat" , "Renk" , "Ürün Açıklaması"});
            productDetail.put("2",new Object[] { product.name , product.productCode , product.variants.get(0).barcode , product.price.originalPrice.text , product.color,String.valueOf(contentDescriptionCombine)});
        }else{
            boolean isFinish = false;
            System.out.println("Bitti mi ? ;" + isFinish);
            productDetail.put("1",new Object[] { "Ürün adı", "Ürün Kodu", "Ürün Barkodu" , "Fiyat" , "Renk" , "Beden" , "Beden barkod" , "Beden ürün kodu" , "Beden fiyatı" , "Ürün Açıklaması"});
            productDetail.put("2",new Object[] { product.name , product.productCode , product.variants.get(0).barcode , product.price.originalPrice.text , product.color, product.allVariants.get(0).value , String.valueOf(product.allVariants.get(0).barcode) , String.valueOf(product.allVariants.get(0).itemNumber) , String.valueOf(product.allVariants.get(0).price) ,String.valueOf(contentDescriptionCombine)});

            int getListSize = product.allVariants.size();

            for(int i = 1; i <= product.allVariants.size(); i++) {
                productDetail.put(String.valueOf(count) , new Object[]{"" , "" , "" , "" ,"" , product.allVariants.get(i).value , product.allVariants.get(i).barcode.toString() , String.valueOf(product.allVariants.get(i).itemNumber) , String.valueOf(product.allVariants.get(i).price)});
                count++;
                System.out.println("Get i : " + i + "get allvariants size : "+ product.allVariants.size());
            }

            System.out.println("Bitti mi ? ;" + isFinish);
        }
        writeExcelFile(file , product , productDetail);
    }
    private void writeExcelFile(File file , Product product , Map<String , Object[]> map) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(product.name);
        XSSFRow row;
        File getDirectory = new File(file , "productDetail.xlsx");

        Set<String> keyId = map.keySet();
        int rowId = 0;
        try {
            for (String key : keyId) {
                row = spreadsheet.createRow(rowId++);
                Object[] objectArr = map.get(key);
                int cellid = 0;
                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue((String) obj);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream(getDirectory);
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}