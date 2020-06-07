package com.bazaraki.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.WebDriverException;
import com.bazaraki.models.Advertisement;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://www.bazaraki.com")
public class MainPage extends PageObject {

    @FindBy(id="header-form-search-input")
    public WebElementFacade keywordInput;

    @FindBy(css=".js-select-cities-regions")
    WebElementFacade chooseRegionBtn;

    @FindBy(css=".index-search__expanded-search-link")
    public WebElementFacade filtersBtn;

    @FindBy(css = "div.list-announcement-block")
    List<WebElementFacade> adSearchResultDivList;

    public void clickChooseRegionBtn() {
        chooseRegionBtn.click();
    }

    public String getChosenRegionName() {
        return chooseRegionBtn.getText();
    }

    public List<Advertisement> getAdSearchResults() {
        if (adSearchResultDivList.size() == 0) {
            throw new AssertionError(
                    "There are not found any advertisement in serch result block");
        }
        List<Advertisement> searchResults = new ArrayList<Advertisement>();
        for (WebElementFacade adSearchResultElement : adSearchResultDivList) {
            searchResults.add(new Advertisement().withId(parseAdId(adSearchResultElement))
                                                 .withPrice(parseAdPrice(adSearchResultElement))
                                                 .withName(parseAdName(adSearchResultElement))
                                                 .withPublishedDate(parseAdPublishedDate(adSearchResultElement))
                                                 .withLocation(parseAdLocation(adSearchResultElement))
                                                 .withFotoCount(parseAdFotoCount(adSearchResultElement)));
        }
        return searchResults;
    }

    public WebElementFacade getAdvertisementDivById(int adId) {
        WebElementFacade advertisementDiv = adSearchResultDivList
                .stream().filter(el -> Integer.parseInt(el.getAttribute("id")) == adId)
                .findAny().orElseThrow(() -> new AssertionError(
                                "There is not found any advertisement in serch result with id = '" + adId + "'"));
        return advertisementDiv;
    }

    private int parseAdFotoCount(WebElementFacade adSearchResultElement) {
        try {
            return Integer.parseInt(adSearchResultElement.findBy(".//div[@class='photo-commodities']").getText());
        } catch ( WebDriverException ex) {
            return 0;
        }
    }

    private String parseAdLocation(WebElementFacade adSearchResultElement) {
        return adSearchResultElement.findBy(".//span[@class='category-list-item-info-desc']").getText();
    }

    private String parseAdName(WebElementFacade adSearchResultElement) {
        return adSearchResultElement.findBy(".//a[@class='name  ']").getText();
    }

    private int parseAdId(WebElementFacade adSearchResultElement) {
        return Integer.parseInt(adSearchResultElement.getAttribute("id"));
    }

    private double parseAdPrice(WebElementFacade adSearchResultElement) {
        return Double.parseDouble(adSearchResultElement.findBy(".//div[contains(@class, 'price')]")
                                                         .getText().split("€")[1].replace(".", ""));
    }

    private Calendar parseAdPublishedDate(WebElementFacade adSearchResultElement) {
        Calendar today = Calendar.getInstance();
        String dateString = adSearchResultElement.findBy(".time-like").getText();
        if (dateString.contains("Today")) {
            String time = dateString.replace("Today", "");
            today.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0].trim()));
            today.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1].trim()));
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
        } else if (dateString.contains("Yesterday")) {
            String time = dateString.replace("Yesterday", "");
            today.add(Calendar.DAY_OF_MONTH, -1);
            today.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0].trim()));
            today.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1].trim()));
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            try {
                today.setTime(formatter.parse(dateString));
            } catch (ParseException e) {
                throw new AssertionError("Incorrect date format: " + e.getMessage());
            }
        }
        return today;
    }

}
