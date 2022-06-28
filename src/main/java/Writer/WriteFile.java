package Writer;

import CGson.CGson;
import Model.Trendyol.*;

import javax.management.StringValueExp;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import Util.*;
import com.google.errorprone.annotations.Var;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.apache.xmlbeans.impl.xb.xsdschema.NamedGroup;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

public class WriteFile implements Serializable {
    WriteExcelFile writeExcelFile = new WriteExcelFile();
    GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
    FileNameGenerator fileNameGenerator = new FileNameGenerator();
    CGson gsonTrendyol = new CGson();
    public void writeAFile(Product product, JFileChooser getSelectedFile, List<String> getColors, String enterUrlGetText){
        File file = fileNameGenerator.writeFileSelectedLocation(enterUrlGetText+".xlsx" , getSelectedFile);
        if(!file.exists()) file.exists();
        Map<String , Object[]> stringList = writeExcelFile.readExcelFile(file);
        if(stringList.isEmpty()){
            stringList.put("0", new ExcelTitles().TRENDYOL_LARGE_TITLE);
            int size = writeExcelFile.getExcelFileRowSize(file) ;
            int totalSizes = product.attributes.size() + product.allVariants.size() + product.contentDescriptions.size();
            for (int i = 0; i <= totalSizes; i++) {
                size++;
                Object[] objects = writeExcelFileProductDetail(product, i);
                if(objects != null) stringList.put(String.valueOf(size) , objects); writeExcelFile.fastestExcelLibrary(file , enterUrlGetText , stringList);
            }
        }else{
            stringList.put("0", new ExcelTitles().TRENDYOL_LARGE_TITLE);
            int size = writeExcelFile.getExcelFileRowSize(file) ;
            int totalSizes = product.attributes.size() + product.allVariants.size() + product.contentDescriptions.size();
            for (int i = 0; i <= totalSizes; i++) {
                size++;
                Object[] objects = writeExcelFileProductDetail(product, i);
                if(objects != null) stringList.put(String.valueOf(size) , objects); writeExcelFile.fastestExcelLibrary(file , enterUrlGetText , stringList);
            }
        }
    }


    private void writeFileInDirectory(File file , Product product ) throws InterruptedException {
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
        if(product.allVariants.get(0).value.equals("") || product.allVariants.get(0).value == null){
           productDetail = smallWriteExcelFileAMap(product);
        }else{
            productDetail = largeWriteExcelFileAMap(product);
        }
        new WriteExcelFile().writeExcelFile(file , "product" , productDetail);
    }
    private Map<String , Object[]> smallWriteExcelFileAMap(Product product ) throws InterruptedException {
        int count = 2;
        int totalVariable = product.allVariants.size() + product.variants.size() + product.contentDescriptions.size();
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
        productDetail.put("1", new ExcelTitles().TRENDYOL_SMALL_TITLE);
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
    private Map<String , Object[]> largeWriteExcelFileAMap(Product product ) throws InterruptedException {
        int count = 3;
        Map<String, Object[]> productDetail = new TreeMap<String, Object[]>();
        productDetail.put("1", new ExcelTitles().TRENDYOL_LARGE_TITLE);
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
            whichListBig = product.allVariants.size();
        }else if(product.attributes.size() > product.allVariants.size() && product.attributes.size() > product.contentDescriptions.size()){
            whichListBig = product.attributes.size();
        }else{
            whichListBig = product.contentDescriptions.size();
        }

        for (int i = 0; i <= whichListBig; i++) {
            //productDetail.put(String.valueOf(count) , writeExcelFileProductDetail(product.allVariants , product.attributes , product.contentDescriptions , i));
            count++;
        }

        return productDetail;
    }
    private Object[] writeExcelFileProductDetail(Product product, int whichSizeBig){

        Object[] objects = new Object[]{};
        String value = "";
        String price = "";
        String inStock = "";
        String itemNumber = "";
        String body_barcode = "";

        //Single
        String barcode = product.allVariants.get(0).barcode;
        String name = product.name;
        String productCode = product.productCode;
        String sellingPrice = product.price.sellingPrice.text;
        String originalPrice = product.price.originalPrice.text;
        String discountedPrice = product.price.discountedPrice.text;
        String color = product.color;

        String keyName = "";
        String valueName = "";

        String contentDescription = "";

        if(whichSizeBig < product.allVariants.size() ){
            value = checkNullVariable(String.valueOf(product.allVariants.get(whichSizeBig).value));
            price = checkNullVariable(String.valueOf(product.allVariants.get(whichSizeBig).price));
            inStock = checkNullVariable(String.valueOf(product.allVariants.get(whichSizeBig).inStock));
            System.out.println("STOCK STATUS : " + inStock);
            body_barcode = checkNullVariable(String.valueOf(product.allVariants.get(whichSizeBig).barcode));
            itemNumber = checkNullVariable(String.valueOf(product.allVariants.get(whichSizeBig).itemNumber));
        }
        if(whichSizeBig < product.attributes.size()){
            keyName = checkNullVariable(product.attributes.get(whichSizeBig).key.name);
            valueName = checkNullVariable(product.attributes.get(whichSizeBig).value.name);
        }
        if(whichSizeBig < product.contentDescriptions.size()){
            contentDescription = checkNullVariable(product.contentDescriptions.get(whichSizeBig).description);
        }
        if(!body_barcode.isEmpty()){
          objects = new Object[]{name,productCode,barcode,sellingPrice,discountedPrice , originalPrice, color ,String.valueOf(value) , String.valueOf(body_barcode) , String.valueOf(itemNumber) , String.valueOf(price), String.valueOf(inStock)  , String.valueOf(contentDescription) , String.valueOf(keyName) , String.valueOf(valueName)};
        }else{
            objects = null;
        }

        return objects;
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
    /* Area of colored products (down) */
    public void coloredProducts(List<String> getColors, JFileChooser getSelectedFile, TrendyolModel trendyolModel) {
        List<TrendyolModel> trendyolModelList = new ArrayList<TrendyolModel>();
        AtomicInteger count = new AtomicInteger();
        File file = fileNameGenerator.writeFileSelectedLocation(trendyolModel.product.name , getSelectedFile);
        if(!file.exists()) file.mkdirs();
        getColors.forEach(s -> {
            try {
                Document document = new UJsoup().getDiv(s);
                document.body().getElementsByTag("script").dataNodes().forEach(dataNode -> {
                    String[] a = dataNode.getWholeData().trim().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__");
                    if(a[0].isEmpty()){
                        JSONObject jsonObject = new JSONObject(a[1].trim().split("=" , 2)[1].trim().split("window\\.TYPageName=")[0]);
                        trendyolModelList.add(gsonTrendyol.convertTrendyolModel(jsonObject));
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        trendyolModelList.forEach(trendyolModel1 -> {
           File underFile =  fileNameGenerator.writeFileInFile(String.valueOf(trendyolModel1.product.name + trendyolModel1.product.allVariants.get(0).barcode) , file);
           if(!underFile.exists()) underFile.mkdirs();
            try {
                writeFileInDirectory(underFile , trendyolModel1.product);
                trendyolModel1.product.images.forEach(getImages -> {
                    getImageFromUrl.getLinkFromUrlAndWriteFolder("https://cdn.dsmcdn.com/"+getImages , underFile , count.getAndIncrement());
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    private File checkFile(String enterUrlGetText , JFileChooser getSelectedFile){
        File file = new FileNameGenerator().writeFileSelectedLocation(enterUrlGetText+".xlsx" , getSelectedFile);
        if(!file.exists()) file.exists();
        return file;
    }
}