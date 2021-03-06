package GetLinkPackaces;

import CGson.CGson;
import Model.Trendyol.TrendyolModel;
import Writer.WriteFile;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class GetInformationInTheLink implements Serializable {
    CGson gsonTrendyol = new CGson();
    WriteFile writeFile = new WriteFile();
    public void getInformationTrendyolProducts(WebDriver driver, JFileChooser getSelectedFile, String enterUrlGetText, ExecutorService executorService, String getProductLink)  {
        Document jsoup = Jsoup.parse(driver.getPageSource());
        Element elements1 = jsoup.body();
        AtomicReference<JSONObject> jsonObjects = new AtomicReference<JSONObject>();
        @Nullable List<String> getColorsLink = new ArrayList<String>();
        @Nullable List<String> getProductColorsLink = new ArrayList<>();
        getColorsLink.clear();
        elements1.getElementsByTag("script").forEach(element -> {
            element.dataNodes().forEach(dataNode -> {
                String[] s = dataNode.getWholeData().trim().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__");
                if(s[0].isEmpty()){
                    JSONObject jsonObject = new JSONObject(s[1].trim().split("=" , 2)[1].trim().split("window\\.TYPageName=")[0]);
                    jsonObjects.set(jsonObject);
                }
            });
        });

        Elements document = elements1.getElementsByClass("slc-img");
        document.forEach(element -> {
            getColorsLink.add(element.attr("title"));
            getProductColorsLink.add(element.attr("href"));
        });
        writeAFileFuncPushed(getSelectedFile , jsonObjects.get() , getColorsLink , enterUrlGetText , executorService , getProductLink , getProductColorsLink);
    }
    private void writeAFileFuncPushed(JFileChooser getSelectedFile, JSONObject jsonObject, List<String> getColors, String enterUrlGetText, ExecutorService executorService, String getProductLink , List<String> getProductColorsLink) {
        if(getProductColorsLink.isEmpty()){
            TrendyolModel trendyolModel = gsonTrendyol.convertTrendyolModel(jsonObject);
            writeFile.writeAFile(trendyolModel.product, getSelectedFile, getColors , enterUrlGetText , executorService , getProductLink);
        }else{
            writeFile.coloredProducts(getProductColorsLink , getColors ,  enterUrlGetText , getSelectedFile , getProductLink);
        }
    }
}
