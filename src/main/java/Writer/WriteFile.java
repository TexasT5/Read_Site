package Writer;

import Model.Trendyol.*;

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
            String fileName = "";
            String regex = ":+|/+|\"+|<+|>+|\\*+";
            fileName = product.name.replaceAll(regex , "").trim();
            File file = null;
            if (System.getProperty("os.name").startsWith("Mac")){
                file = new File(getSelectedFile.getSelectedFile()+"/"+fileName);
            }else{
                file = new File(getSelectedFile.getSelectedFile()+"\\"+fileName);
            }

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
        XSSFSheet spreadsheet = workbook.createSheet(product.productCode);
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
        int count = 2;
        int totalVariable = product.allVariants.size() + product.variants.size() + product.contentDescriptions.size();
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();

        productDetail.put("1",new Object[] { "Product name", "Product Code", "Product Barcode" , "Selling Price" , "Discounted Price" , "Original Price" , "Color" , "Product Description" , "" ,"Attributes" , "Original Categories"});
        Thread.sleep(1000);
        for (int i = 0; i < totalVariable; i++) {
            String name = null;
            String productCode = null;
            String productBarcode = null;
            String sellingPrice = null;
            String discountedPrice = null;
            String originalPrice = null;
            String color = null;
            String keyValue = null;
            String valueName = null;
            String contentDescriptionCombine = null;
            String originalCategories = null;
            if(i <= 0){
                name = product.name;
                productCode = product.productCode;
                sellingPrice = product.price.sellingPrice.text.toString();
                discountedPrice = product.price.discountedPrice.text.toString();
                originalPrice = product.price.originalPrice.text.toString();
                color = product.color;
                originalCategories = product.originalCategory.hierarchy;
            }

            if(i < product.attributes.size()){
                keyValue = product.attributes.get(i).key.getName();
                valueName = product.attributes.get(i).value.name;
            }
            if(i < product.variants.size()){
                productBarcode = product.variants.get(i).barcode;
            }
            if(i < product.contentDescriptions.size()){
                contentDescriptionCombine = product.contentDescriptions.get(i).description;
            }
            productDetail.put(String.valueOf(count), new Object[]{checkNullVariable(name), checkNullVariable(productCode), checkNullVariable(productBarcode), checkNullVariable(sellingPrice),checkNullVariable(discountedPrice) , checkNullVariable(originalPrice) , checkNullVariable(color), checkNullVariable(contentDescriptionCombine), "", checkNullVariable(keyValue), checkNullVariable(valueName) , checkNullVariable(originalCategories)});
            count++;
        }
        return productDetail;
    }
    private Map<String , Object[]> largeWriteExcelFileAMap(Product product) throws InterruptedException {
        int count = 3;
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
        productDetail.put("1",new Object[] {
                "Product Name",
                "Product Code",
                "Product Barcode" ,
                "Selling Price" , "Discounted Price" , "Original Price",
                "Color" ,
                "Body" ,
                "Body Barcode" ,
                "Body Product Code" ,
                "Body Price" ,
                "Stock Status",
                "Product Description" ,
                "" ,
                "Attributes"});

        productDetail.put("2",new Object[] {
                checkNullVariable(product.name),
                checkNullVariable(product.productCode) ,
                checkNullVariable(product.variants.get(0).barcode ),
                checkNullVariable(product.price.sellingPrice.text) ,
                checkNullVariable(product.color),
                checkNullVariable(product.allVariants.get(0).value) ,
                checkNullVariable(String.valueOf(product.allVariants.get(0).barcode)) ,
                checkNullVariable(String.valueOf(product.allVariants.get(0).itemNumber)) ,
                checkNullVariable(String.valueOf(product.allVariants.get(0).price)) ,
                checkNullVariable(String.valueOf(product.allVariants.get(0).inStock)),
                checkNullVariable(String.valueOf(product.contentDescriptions.get(0).description)),
                "",
                checkNullVariable(product.attributes.get(0).key.name) ,
                checkNullVariable(product.attributes.get(0).value.name)});

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
            value = checkNullVariable(String.valueOf(getAllVariants.get(whichSizeBig).value));
            price = checkNullVariable(String.valueOf(getAllVariants.get(whichSizeBig).price));
            inStock = checkNullVariable(String.valueOf(getAllVariants.get(whichSizeBig).inStock));
            barcode = checkNullVariable(String.valueOf(getAllVariants.get(whichSizeBig).barcode));
            itemNumber = checkNullVariable(String.valueOf(getAllVariants.get(whichSizeBig).itemNumber));
        }
        if(whichSizeBig < attributesList.size()){
            keyName = checkNullVariable(attributesList.get(whichSizeBig).key.name);
            valueName = checkNullVariable(attributesList.get(whichSizeBig).value.name);
        }
        if(whichSizeBig < getContentDescription.size()){
            contentDescription = checkNullVariable(getContentDescription.get(whichSizeBig).description);
        }
        return new Object[]{"","","","","", String.valueOf(value) , String.valueOf(barcode) , String.valueOf(itemNumber) , String.valueOf(price), String.valueOf(inStock)  , String.valueOf(contentDescription) , "" , String.valueOf(keyName) , String.valueOf(valueName)};
    }
    private String checkNullVariable(String getString){
        String returnedSafeString = "";
        if(getString == null){
            returnedSafeString = "";
        }else if(getString.equals("")){
            returnedSafeString = "";
        }else{
            returnedSafeString = getString;
        }
        return returnedSafeString;
    }
}