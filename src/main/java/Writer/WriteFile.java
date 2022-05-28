package Writer;

import Model.Trendyol.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
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
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
        if(product.allVariants.get(0).value.equals("") || product.allVariants.get(0).value == null){
           productDetail = smallWriteExcelFileAMap(product);
        }else{
            productDetail = largeWriteExcelFileAMap(product);
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
    private Map<String , Object[]> smallWriteExcelFileAMap(Product product) throws InterruptedException {
        int count = 3;
        AtomicReference<String> contentDescriptionCombine = new AtomicReference<>();
        product.contentDescriptions.forEach(contentDescriptions -> {
            contentDescriptionCombine.set(contentDescriptions.description);
        });
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
        productDetail.put("1",new Object[] { "Ürün adı", "Ürün Kodu", "Ürün Barkodu" , "Fiyat" , "Renk" , "Ürün Açıklaması" , "" ,"Öznitellikler"});
        productDetail.put("2",new Object[] { product.name , product.productCode , product.variants.get(0).barcode , product.price.originalPrice.text , product.color, String.valueOf(contentDescriptionCombine) , "" , product.attributes.get(0).key.name , product.attributes.get(0).value.name});
        Thread.sleep(1000);
        for (int i = 1; i < product.attributes.size(); i++) {
            productDetail.put(String.valueOf(count), new Object[]{"", "", "", "", "", "", "", product.attributes.get(i).key.name, product.attributes.get(i).value.name});
            count++;
        }
        return productDetail;
    }
    private Map<String , Object[]> largeWriteExcelFileAMap(Product product) throws InterruptedException {
        int count = 3;
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
        productDetail.put("1",new Object[] {
                "Ürün adı",
                "Ürün Kodu",
                "Ürün Barkodu" ,
                "Fiyat" ,
                "Renk" ,
                "Beden" ,
                "Beden barkod" ,
                "Beden ürün kodu" ,
                "Beden fiyatı" ,
                "Stok durumu",
                "Ürün Açıklaması" ,
                "" ,
                "Öznitellikler"});

        productDetail.put("2",new Object[] {
                product.name ,
                product.productCode ,
                product.variants.get(0).barcode ,
                product.price.originalPrice.text ,
                product.color,
                product.allVariants.get(0).value ,
                String.valueOf(product.allVariants.get(0).barcode) ,
                String.valueOf(product.allVariants.get(0).itemNumber) ,
                String.valueOf(product.allVariants.get(0).price) ,
                String.valueOf(product.allVariants.get(0).inStock),
                String.valueOf(product.contentDescriptions.get(0).description),
                "",
                product.attributes.get(0).key.name ,
                product.attributes.get(0).value.name});

        int whichSizeBig = 0;
        if(product.allVariants.size() > product.attributes.size()){
            whichSizeBig = product.allVariants.size();
        }else{
            whichSizeBig = product.attributes.size();
        }


        putOnMap(3 ,new Object[]{"sa" , "sa" ,"sa" , "sa"} , productDetail);

        System.out.println(productDetail.get(3));

        writeExcelFileAllVariantsAndAttributes(product);
        return productDetail;
    }
   /* private<T> Object[] autoLoop(List<T> productList){
        int count = 3;
        Object[] t = {};
        for(int i = 1; i < productList.size(); i++){
            t = new Object[]{count++, productList.get(i)};
        }
        return t;
    }
    */

    private void writeExcelFileAllVariantsAndAttributes(Product product){
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
    }

    private Object[] putOnMap(int size , Object[] variable , Map<String , Object[]> getMap){
        return getMap.put(String.valueOf(size) , new Object[]{variable});
    }

}