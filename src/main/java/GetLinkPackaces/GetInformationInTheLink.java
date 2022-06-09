package GetLinkPackaces;

import Model.Trendyol.TrendyolModel;
import Util.UJsoup;
import Writer.WriteFile;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import com.gargoylesoftware.htmlunit.javascript.host.DateCustom;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.html.HTMLElement;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;


public class GetInformationInTheLink {
    public void getInformationTrendyolProducts(String links , JFileChooser getSelectedFile)  {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(links);
        Document jsoup = Jsoup.parse(driver.getPageSource());
        Elements elements = jsoup.getElementsByTag("script");
        elements.dataNodes().forEach(dataNode -> {
            String[] s = dataNode.getWholeData().trim().split("window\\.__PRODUCT_DETAIL_APP_INITIAL_STATE__");
            if(s[0].isEmpty()){
                JSONObject jsonObject = new JSONObject(s[1].trim().split("=" , 2)[1].trim().split("window\\.TYPageName=")[0]);
                writeAFileFuncPushed(getSelectedFile , jsonObject);
            }
        });
        driver.quit();
    }

    private void writeAFileFuncPushed(JFileChooser getSelectedFile , JSONObject jsonObject){
        Gson gson = new Gson();
        WriteFile writeFile = new WriteFile();
        Type type = new TypeToken<TrendyolModel>(){}.getType();
        TrendyolModel trendyolModel = gson.fromJson(String.valueOf(jsonObject), type);
        writeFile.writeAFile(trendyolModel.product , getSelectedFile);
    }

    private void parseAjax() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.trendyol.com/kom/kadin-barbara-toparlayici-balensiz-minimizer-sutyen-p-51579114?boutiqueId=607728&merchantId=120078");
        System.out.println(webDriver.getPageSource());
        webDriver.quit();
    }
}
