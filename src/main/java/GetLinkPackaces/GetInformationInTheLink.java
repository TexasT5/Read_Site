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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GetInformationInTheLink {
    CGson gsonTrendyol = new CGson();
    WriteFile writeFile = new WriteFile();
    public void getInformationTrendyolProducts(String links , JFileChooser getSelectedFile)  {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(links);
        Document jsoup = Jsoup.parse(driver.getPageSource());
        Element elements1 = jsoup.body();
        AtomicReference<JSONObject> jsonObjects = new AtomicReference<JSONObject>();
        @Nullable List<String> getColorsLink = new ArrayList<String>();
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
            getColorsLink.add("https://trendyol.com" + element.attr("href"));
        });
        writeAFileFuncPushed(getSelectedFile , jsonObjects.get() , getColorsLink);
        driver.quit();
    }
    private void writeAFileFuncPushed(JFileChooser getSelectedFile , JSONObject jsonObject , List<String> getColors){
        TrendyolModel trendyolModel =  gsonTrendyol.convertTrendyolModel(jsonObject);
        if(getColors.isEmpty()){
            writeFile.writeAFile(trendyolModel.product , getSelectedFile , getColors);
        }else{
            writeFile.coloredProducts(getColors , getSelectedFile , trendyolModel);
        }
    }
}
