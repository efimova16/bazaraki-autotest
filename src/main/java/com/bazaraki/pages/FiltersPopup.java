package com.bazaraki.pages;

import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class FiltersPopup extends PageObject {

    @FindBy(xpath = "//div[@class='filter-popup__paired']/div[1]")
    WebElementFacade fromPriceMenu;

    @FindBy(xpath = "//div[@class='filter-popup__paired']/div[2]")
    WebElementFacade toPriceMenu;

    @FindBy(css = "li.popover__item")
    List<WebElementFacade> pricesList;

    @FindBy(css = ".filter-popup__footer")
    WebElementFacade searchBtn;

    public void clickSearchBtn() {
        searchBtn.click();
    }

    public void openMinPriceMenu() {
        evaluateJavascript("arguments[0].scrollIntoView();", fromPriceMenu);
        fromPriceMenu.click();
    }

    public void openMaxPriceMenu() {
        evaluateJavascript("arguments[0].scrollIntoView();", toPriceMenu);
        toPriceMenu.click();
    }

    public void clickOnPriceItemByValue(String price) {
        try {
            WebElementFacade pricesLi = findBy("//ul[@class='popover__list']/li[contains(.,'" + price + "')]");
            evaluateJavascript("arguments[0].scrollIntoView();", pricesLi);
            pricesLi.click();
        } catch (NoSuchElementException ex) {
            throw new AssertionError("Price with value '" + price + "' isn't presented in menu");
        }
    }

    public String getSelectedMinPriceValue() {
        return fromPriceMenu.getText();
    }

    public String getSelectedMaxPriceValue() {
        return toPriceMenu.getText();
    }

}
