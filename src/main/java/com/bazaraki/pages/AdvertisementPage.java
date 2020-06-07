package com.bazaraki.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class AdvertisementPage extends PageObject {

    @FindBy(css = ".menu-item--favorites")
    public WebElementFacade addToFavoritesBtn;

}
