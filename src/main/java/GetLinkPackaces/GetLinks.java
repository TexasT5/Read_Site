package GetLinkPackaces;

import Util.FileNameGenerator;
import Util.UJsoup;
import com.github.dockerjava.api.command.AuthCmd;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.compress.utils.OsgiUtils;
import org.checkerframework.checker.units.qual.C;
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
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    public void getLinkInSites(String siteName, String enterBrand, DefaultListModel<String> listModel, JScrollPane main_scroll_pane, JFileChooser getSelectFile, String enterUrlGetText, JLabel counter){
        switch (siteName){
            case "Trendyol":
                getTrendyolProductLinks(enterBrand , listModel, main_scroll_pane , getSelectFile , enterUrlGetText , counter);
                break;

            default:break;
        }
    }

    private void getTrendyolProductLinks(String getBrand, DefaultListModel<String> listModel, JScrollPane scrollPane, JFileChooser getSelectedFile, String enterUrlGetText, JLabel counter){
        UJsoup uJsoup = new UJsoup();
        WebDriver driver = new ChromeDriver();
        AtomicBoolean threadEnding = new AtomicBoolean(true);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while(threadEnding.get()){
                    try {
                        Document document = uJsoup.getDiv("https://www.trendyol.com/sr?q="+getBrand+"&qt="+getBrand+"&st="+getBrand+"&os=2&pi="+i);
                        Elements getLink = document.select(".p-card-chldrn-cntnr > a");
                        getLink.forEach(element -> {
                            String getHref = element.attr("href");
                            if(getHref.equals("")){
                                threadEnding.set(false);
                                executorService.shutdown();
                                try {
                                    if (!executorService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                                        executorService.shutdownNow();
                                    }
                                } catch (InterruptedException e) {
                                    executorService.shutdownNow();
                                }
                                driver.quit();
                            }
                            listModel.addElement("https://www.trendyol.com"+getHref);
                            try{
                                driver.get("https://www.trendyol.com"+getHref);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            int getCount = Integer.parseInt(counter.getText());
                            getCount++;
                            counter.setText(String.valueOf(getCount));

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            getInformationInTheLink.getInformationTrendyolProducts(driver, getSelectedFile , enterUrlGetText , executorService);
                            JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
                            scrollBar.setValue(scrollBar.getMaximum());
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        });
    }
}
