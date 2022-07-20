package Writer;

import CGson.CGson;
import Model.Trendyol.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import Util.*;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

public class WriteFile implements Serializable {
    WriteExcelFile writeExcelFile = new WriteExcelFile();
    GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
    FileNameGenerator fileNameGenerator = new FileNameGenerator();
    CGson gsonTrendyol = new CGson();

    public void  writeAFile(Product product, JFileChooser getSelectedFile, List<String> getColors, String enterUrlGetText, ExecutorService executorServices, String getProductLink){
        File file = fileNameGenerator.writeFileSelectedLocation(enterUrlGetText+".xlsx" , getSelectedFile);
        Map<String , Object[]> stringList = writeExcelFile.readFastestExcelFile(file);
        int size = writeExcelFile.getExcelFileRowSize(file);
        int totalSize = product.allVariants.size() + product.contentDescriptions.size() + product.variants.size();
        stringList.put("0" , new ExcelTitles().TRENDYOL_LARGE_TITLE);
        for (int j = 0; j < totalSize; j++) {
            Object[] objects = writeExcelFileProductDetail(product , Arrays.asList() ,j , getProductLink);
            if(objects != null) {
                stringList.put(String.valueOf(size) , objects);
                size++;
            }
        }
        writeExcelFile.fastestExcelLibrary(file , enterUrlGetText , stringList);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    private Object[] writeExcelFileProductDetail(Product product, List<String> getColors, int whichSizeBig, String getProductLink){
        Object[] objects = new Object[]{};
        String value = "";
        String price = "";
        String inStock = "";
        String itemNumber = "";
        String body_barcode = "";
        String imageURL = "";

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
            body_barcode = checkNullVariable(String.valueOf(product.allVariants.get(whichSizeBig).barcode));
            itemNumber = checkNullVariable(String.valueOf(product.allVariants.get(whichSizeBig).itemNumber));
        }else{
            value = "";
            price = "";
            inStock = "";
            body_barcode = "";
            itemNumber = "";
        }


        if(whichSizeBig < product.images.size()){
            imageURL = "https://cdn.dsmcdn.com"+product.images.get(whichSizeBig);
        }else{
            imageURL = "";
        }

        if(whichSizeBig < product.attributes.size()){
            keyName = checkNullVariable(product.attributes.get(whichSizeBig).key.name);
            valueName = checkNullVariable(product.attributes.get(whichSizeBig).value.name);
        }else{
            keyName = "";
            valueName = "";
        }

        if(whichSizeBig < product.contentDescriptions.size()){
            contentDescription = checkNullVariable(product.contentDescriptions.get(whichSizeBig).description);
        }else{
            contentDescription = "";
        }

        if(product.category.hierarchy.split("Giyim")[0].isEmpty()){
            if(!body_barcode.isEmpty()){
                objects = new Object[]{name, getProductLink,productCode,barcode,sellingPrice,discountedPrice , originalPrice, color ,String.valueOf(value) , String.valueOf(body_barcode) , String.valueOf(itemNumber) , String.valueOf(price), String.valueOf(inStock)  , String.valueOf(contentDescription) , String.valueOf(keyName) , String.valueOf(valueName) , String.valueOf(imageURL)};
            }
        }else{
            objects = new Object[]{name, getProductLink,productCode,barcode,sellingPrice,discountedPrice , originalPrice, color ,String.valueOf("") , String.valueOf("") , String.valueOf("") , String.valueOf(""), String.valueOf(inStock)  , String.valueOf(contentDescription) , String.valueOf(keyName) , String.valueOf(valueName) , String.valueOf(imageURL)};
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
    public void coloredProducts(List<String> getProductColorsLink, List<String> getColors, String enterUrlGetText, JFileChooser getSelectedFile, String getProductLink) {
        File file = fileNameGenerator.writeFileSelectedLocation(enterUrlGetText+".xlsx" , getSelectedFile);
        AtomicReference<Map<String, Object[]>> stringList = new AtomicReference<>(writeExcelFile.readFastestExcelFile(file));
        AtomicInteger size = new AtomicInteger(writeExcelFile.getExcelFileRowSize(file));
        List<TrendyolModel> colorsBarcode = new ArrayList<>();
        stringList.get().put("0" , new ExcelTitles().TRENDYOL_LARGE_TITLE);
        getProductColorsLink.forEach(s -> {
            try {
                Document document = new UJsoup().getDiv("https://www.trendyol.com"+s);
                document.body().getElementsByTag("script").dataNodes().forEach(dataNode -> {
                    String[] a = dataNode.getWholeData().trim().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__");
                    if(a[0].isEmpty()){
                        JSONObject jsonObject = new JSONObject(a[1].trim().split("=" , 2)[1].trim().split("window\\.TYPageName=")[0]);
                        TrendyolModel trendyolModel = gsonTrendyol.convertTrendyolModel(jsonObject);
                        colorsBarcode.add(trendyolModel);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        colorsBarcode.forEach(trendyolModel -> {
            List<Object[]> objects = new ArrayList<>();
            int totalCount = trendyolModel.product.allVariants.size() + trendyolModel.product.contentDescriptions.size() + trendyolModel.product.attributes.size() + trendyolModel.product.images.size();
            for (int i = 0; i < totalCount; i++) {
                objects.add(writeExcelFileProductDetail(trendyolModel.product , Arrays.asList() , i , getProductLink));
                objects.forEach(objects1 -> {
                    try{
                        if (objects1 != null && objects1[0] != ""){
                            stringList.get().put(String.valueOf(size.get()) , objects1);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                size.getAndIncrement();
            }
            writeExcelFile.fastestExcelLibrary(file , enterUrlGetText , stringList.get());
        });
    }
}