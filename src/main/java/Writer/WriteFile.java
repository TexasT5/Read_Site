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
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


        for (int j = 1; j <= whichListBig; j++) {
            String value = "";
            String price = "";
            String inStock = "";
            String barcode = "";
            String itemNumber = "";

            String keyName = "";
            String valueName = "";

            String contentDescription = "";
            if(j < product.allVariants.size() ){
                Thread.sleep(1000);
                value = String.valueOf(product.allVariants.get(j).value);
                price = String.valueOf(product.allVariants.get(j).price);
                inStock = String.valueOf(product.allVariants.get(j).inStock);
                barcode = String.valueOf(product.allVariants.get(j).barcode);
                itemNumber = String.valueOf(product.allVariants.get(j).itemNumber);
            }
            if(j < product.attributes.size()){
                keyName = product.attributes.get(j).key.name;
                valueName = product.attributes.get(j).value.name;
            }
            if(j < product.contentDescriptions.size()){
                contentDescription = product.contentDescriptions.get(j).description;
            }
            productDetail.put(String.valueOf(count) , new Object[]{"","","","","", value , barcode , itemNumber , price, inStock  , contentDescription , "" , keyName , valueName});
            count++;
        }
        return productDetail;
    }
}