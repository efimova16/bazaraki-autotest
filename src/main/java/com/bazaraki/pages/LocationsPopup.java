package com.bazaraki.pages;

import java.util.List;
import java.util.stream.Collectors;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class LocationsPopup extends PageObject {

    @FindBy(css=".js-search-cities-regions")
    public WebElementFacade keywordInput;

    @FindBy(css=".cities-regions__button")
    public WebElementFacade submitBtn;

    @FindBy(css = ".js-cities-wrapper-cities-regions > li")
    List<WebElementFacade> districtsList;

    public WebElementFacade getDistrictItemByName(String districtName) {
        List<WebElementFacade> targetDistrictsList = districtsList.stream()
                .filter(el -> el.getText().contentEquals(districtName))
                .collect(Collectors.toList());
        if (targetDistrictsList.size() == 0) {
            throw new AssertionError("District with name '" + districtName
                    + "' isn't presented in menu");
        } else if (targetDistrictsList.size() > 1) {
            throw new AssertionError("Districts with name '" + districtName
                    + "' are more than one in menu");
        }
        return targetDistrictsList.get(0);
    }

    public WebElementFacade getDistrictCheckboxByName(String districtName) {
        WebElementFacade districtCheckbox = getDistrictItemByName(districtName).findBy(".//span");
        evaluateJavascript("arguments[0].scrollIntoView();", districtCheckbox);
        return districtCheckbox;
    }

}
