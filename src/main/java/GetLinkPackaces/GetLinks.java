package GetLinkPackaces;

import Util.UJsoup;

import javax.swing.*;

public class GetLinks {
    GetInformationInTheLink getInformationInTheLink = new GetInformationInTheLink();

    public void getLinkInSites(String siteName, String enterBrand, DefaultListModel<String> listModel, JScrollPane main_scroll_pane){

        switch (siteName){
            case "Trendyol":
                getTrendyolProductLinks(enterBrand , listModel, main_scroll_pane);
            break;

            default:break;
        }
    }

    private void getTrendyolProductLinks(String getBrand, DefaultListModel<String> listModel, JScrollPane scrollPane){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1 ; i < 9999; i++){
                    try{
                        new UJsoup().getDiv("https://www.trendyol.com/sr?q="+getBrand+"&qt="+getBrand+"&st="+getBrand+"&os=2&pi="+i)
                                .select(".p-card-chldrn-cntnr > a")
                                .forEach(element -> {
                                    String getHref = element.attr("href");
                                    String[] getBrandSplit = getHref.split(getBrand);
                                    if(getBrandSplit[0].equals("/")){
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        listModel.addElement("https://www.trendyol.com"+getHref);
                                        getInformationInTheLink.getInformationInTheLink("https://www.trendyol.com"+getHref , "Trendyol");
                                        JScrollBar scrollBar =  scrollPane.getVerticalScrollBar();
                                        scrollBar.setValue(listModel.size() * 100);
                                    }
                                });
                    }catch (Exception e){
                        System.out.println(e.getLocalizedMessage());
                    }
                }
            }
        });
        thread.start();
    }




    /* private void getURLS(String link){
        for(int i = 1 ; i <= 9999; i++){
            try{
                var jsoupGet = Jsoup.connect("https://www.trendyol.com/sr?q=kom&qt=kom&st=kom&os=1&pi="+i).get()
                        .select(".p-card-chldrn-cntnr > a");

                jsoupGet.forEach(element -> {
                    String getHref = element.attr("href");
                    String[] getHrefSplite = getHref.split("kom");
                    if(getHrefSplite[0].equals("/")){
                        System.out.println(getHref);
                    }

                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }*/
}
