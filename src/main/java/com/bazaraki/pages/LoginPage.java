package com.bazaraki.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://bazaraki.com/profile/login")
public class LoginPage extends PageObject {

    @FindBy(name="confirm_terms")
    public WebElementFacade confirmTermsCheckbox;

    @FindBy(name="validate")
    WebElementFacade continueBtn;

    @FindBy(xpath=".//div[contains(@class, 'validation__error')]")
    public WebElementFacade validationErrorMsgDiv;

    public void clickContinueBtn() {
        evaluateJavascript("arguments[0].scrollIntoView();", continueBtn);
        continueBtn.waitUntilEnabled();
        continueBtn.click();
    }

}
