package com.bazaraki.steps;

import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.is;
import static org.junit.Assume.assumeThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import com.bazaraki.models.Advertisement;
import com.bazaraki.pages.*;
import static org.assertj.core.api.Assertions.*;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.matchers.BeanMatcher;
import net.thucydides.core.steps.ScenarioSteps;
import static net.thucydides.core.matchers.BeanMatcherAsserts.filterElements;
import static net.thucydides.core.matchers.BeanMatcherAsserts.shouldMatch;

public class UISteps extends ScenarioSteps {

    MainPage mainPage;
    LocationsPopup locationsPopup;
    FiltersPopup filtersPopup;
    LoginPage loginPage;
    AdvertisementPage adPage;

    @Step
    public void opens_main_page() {
        mainPage.open();
    }

    @Step
    public void searches_for_query_in_main_page(String keyword) {
        mainPage.keywordInput.typeAndEnter(keyword);
    }

    @Step
    public void opens_location_popup_in_main_page() {
        mainPage.clickChooseRegionBtn();
    }

    @Step
    public void chooses_district_as(String districtName) {
        locationsPopup.getDistrictCheckboxByName(districtName).click();
        locationsPopup.submitBtn.click();
        assertThat(mainPage.getChosenRegionName()).isEqualTo(districtName);
    }

    @Step
    public void opens_filters_popup_in_main_page() {
        mainPage.filtersBtn.click();
    }

    @Step
    public void sets_min_price_as(String minPrice) {
        filtersPopup.openMinPriceMenu();
        filtersPopup.clickOnPriceItemByValue(minPrice);
        assertThat(filtersPopup.getSelectedMinPriceValue()).isEqualTo(minPrice);
    }

    @Step
    public void sets_max_price_as(String maxPrice) {
        filtersPopup.openMaxPriceMenu();
        filtersPopup.clickOnPriceItemByValue(maxPrice);
        assertThat(filtersPopup.getSelectedMaxPriceValue()).isEqualTo(maxPrice);
    }

    @Step
    public void clicks_search_button_in_filters_popup() {
        filtersPopup.clickSearchBtn();
    }

    @Step
    public void should_see_search_results_where(BeanMatcher... matchers) {
        shouldMatch(mainPage.getAdSearchResults(), matchers);
    }

    @Step
    public List<Advertisement> chooses_advertisements_in_search_results_where(BeanMatcher... matchers) {
        List<Advertisement> ads = mainPage.getAdSearchResults();
        assumeThat(ads, is(not(empty())));
        List<Advertisement> filteredAds = filterElements(ads, matchers);
        assertThat(filteredAds).isNotEmpty();
        Serenity.recordReportData().withTitle("Chosen advertisements").andContents(filteredAds.toString());
        return filteredAds;
    }

    @Step
    public List<Advertisement> chooses_advertisements_with_min_price_among_of(List<Advertisement> ads) {
        assumeThat(ads, is(not(empty())));
        if (ads.size() == 1) { return ads; }
        List<Advertisement> ads_with_min_price = ads.stream()
                        .filter(el -> el.getPrice() == ads.stream().map(Advertisement::getPrice)
                        .min(Double::compareTo).get()).collect(Collectors.toList());
        Serenity.recordReportData().withTitle("Chosen advertisements").andContents(ads_with_min_price.toString());
        return ads_with_min_price;
    }

    @Step
    public Advertisement chooses_the_oldest_advertisement_among_of(List<Advertisement> ads) {
        assumeThat(ads, is(not(empty())));
        Advertisement ad;
        if (ads.size() == 1) { ad = ads.get(0); }
        ad = ads.stream().reduce((x, y) -> x.getPublishedDate().before(y.getPublishedDate()) ? x : y).get();
        Serenity.recordReportData().withTitle("Chosen advertisement").andContents(ad.toString());
        return ad;
    }

    @Step
    public void clicks_on_advertisement_in_search_results(Advertisement ad) {
        mainPage.getAdvertisementDivById(ad.getId()).click();
    }

    @Step
    public void adds_advertisement_to_favourites() {
        adPage.addToFavoritesBtn.click();
    }

    @Step
    public void should_see_url_contain(String id) {
        assertThat(adPage.getDriver().getCurrentUrl()).contains(id);
    }

    @Step
    public void accepts_terms_on_login_page() {
        loginPage.confirmTermsCheckbox.click();
    }

    @Step
    public void clicks_continue_button_on_login_page() {
        loginPage.clickContinueBtn();
    }

    @Step
    public void should_see_error_message_on_login_page_as(String errorMsg) {
        assertThat(loginPage.validationErrorMsgDiv.getText()).isEqualTo(errorMsg);
    }

}
