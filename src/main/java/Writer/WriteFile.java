package Writer;

import Model.Trendyol.AllVariants;
import Model.Trendyol.Attributes;
import Model.Trendyol.ContentDescriptions;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteFile {
    public void writeAFile(Product product , JFileChooser getSelectedFile){
        try{
            String fileName = "";
            String regex = ":+/";

            String[] strings = product.name.split("");
            for (String s : strings){
                System.out.println(Pattern.matches(regex ,s));
            }



            File file = new File(getSelectedFile.getSelectedFile()+"\\"+"");

            if(!file.exists()) {
                file.mkdirs();
            }
            getImageFromURL(product.images , file);
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
                product.price.sellingPrice.text ,
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

        int whichListBig = 0;
        if(product.allVariants.size() > product.attributes.size() && product.allVariants.size() > product.contentDescriptions.size()){
            System.out.println("All variants daha büyük");
            whichListBig = product.allVariants.size();
        }else if(product.attributes.size() > product.allVariants.size() && product.attributes.size() > product.contentDescriptions.size()){
            System.out.println("Attributes daha büyük");
            whichListBig = product.attributes.size();
        }else{
            whichListBig = product.contentDescriptions.size();
            System.out.println("Content description daha büyük");
        }

        for (int i = 1; i <= whichListBig; i++) {
            productDetail.put(String.valueOf(count) , writeExcelFileProductDetail(product.allVariants , product.attributes , product.contentDescriptions , i));
            count++;
        }
        return productDetail;
    }
    private Object[] writeExcelFileProductDetail(List<AllVariants> getAllVariants , List<Attributes> attributesList , List<ContentDescriptions> getContentDescription , int whichSizeBig){
        String value = "";
        String price = "";
        String inStock = "";
        String barcode = "";
        String itemNumber = "";

        String keyName = "";
        String valueName = "";

        String contentDescription = "";
        if(whichSizeBig < getAllVariants.size() ){
            value = String.valueOf(getAllVariants.get(whichSizeBig).value);
            price = String.valueOf(getAllVariants.get(whichSizeBig).price);
            inStock = String.valueOf(getAllVariants.get(whichSizeBig).inStock);
            barcode = String.valueOf(getAllVariants.get(whichSizeBig).barcode);
            itemNumber = String.valueOf(getAllVariants.get(whichSizeBig).itemNumber);
        }
        if(whichSizeBig < attributesList.size()){
            keyName = attributesList.get(whichSizeBig).key.name;
            valueName = attributesList.get(whichSizeBig).value.name;
        }
        if(whichSizeBig < getContentDescription.size()){
            contentDescription = getContentDescription.get(whichSizeBig).description;
        }
        return new Object[]{"","","","","", String.valueOf(value) , String.valueOf(barcode) , String.valueOf(itemNumber) , String.valueOf(price), String.valueOf(inStock)  , String.valueOf(contentDescription) , "" , String.valueOf(keyName) , String.valueOf(valueName)};
    }
}