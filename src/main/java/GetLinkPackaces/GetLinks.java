package GetLinkPackaces;

import Util.FileNameGenerator;
import Util.UJsoup;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.compress.utils.OsgiUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class GetLinks implements Serializable {
    GetInformationInTheLink getInformationInTheLink = new GetInformationInTheLink();
    public void getLinkInSites(String siteName, String enterBrand, DefaultListModel<String> listModel, JScrollPane main_scroll_pane, JFileChooser getSelectFile, String enterUrlGetText){
        switch (siteName){
            case "Trendyol":
                getTrendyolProductLinks(enterBrand , listModel, main_scroll_pane , getSelectFile , enterUrlGetText);
            break;

            default:break;
        }
    }

    private void getTrendyolProductLinks(String getBrand, DefaultListModel<String> listModel, JScrollPane scrollPane, JFileChooser getSelectedFile, String enterUrlGetText){
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        UJsoup uJsoup = new UJsoup();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                File files = new FileNameGenerator().writeFileSelectedLocation(enterUrlGetText+".xlsx" , getSelectedFile);
                if(!files.exists()) files.exists();
                AtomicBoolean threadEnding = new AtomicBoolean(true);
                int i = 1;
                while(threadEnding.get()){
                    try {
                        Document document = uJsoup.getDiv("https://www.trendyol.com/sr?q="+getBrand+"&qt="+getBrand+"&st="+getBrand+"&os=2&pi="+i);
                        Elements getLink = document.select(".p-card-chldrn-cntnr > a");
                        getLink.forEach(element -> {
                            String getHref = element.attr("href");
                            listModel.addElement("https://www.trendyol.com"+getHref);
                            if(!getHref.equals("")){
                                driver.get("https://www.trendyol.com"+getHref);
                            }
                            getInformationInTheLink.getInformationTrendyolProducts(driver , getSelectedFile , enterUrlGetText);
                            JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
                            scrollBar.setValue(scrollBar.getMaximum());
                        });
                        driver.quit();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    private void test(){
        /*
        while(threadEnding.get()){
            try {
                Document document = uJsoup.getDiv("https://www.trendyol.com/sr?q="+getBrand+"&qt="+getBrand+"&st="+getBrand+"&os=2&pi="+i);
                Elements getLink = document.select(".p-card-chldrn-cntnr > a");
                if(getLink.isEmpty()){
                    System.out.println("boş");
                    threadEnding.set(false);
                    executorService.shutdown();
                    try {
                        if (!executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                            executorService.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        executorService.shutdownNow();
                    }
                }else{
                    getLink.forEach(element -> {
                        String getHref = element.attr("href");
                        String[] getBrandSplit = getHref.split(getBrand);
                        listModel.addElement("https://www.trendyol.com"+getHref);
                        getInformationInTheLink.getInformationTrendyolProducts("https://www.trendyol.com"+getHref , getSelectedFile);
                        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
                        scrollBar.setValue(scrollBar.getMaximum());
                    });
                }
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}
