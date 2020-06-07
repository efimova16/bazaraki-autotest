package com.bazaraki.tests;

import org.openqa.selenium.WebDriver;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Concurrent;
import net.thucydides.junit.annotations.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.bazaraki.models.Advertisement;
import com.bazaraki.steps.UISteps;
import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.startsWith;

@RunWith(SerenityParameterizedRunner.class)
@Concurrent(threads="2")
public class SearchingRealEstate {

    @TestData
    public static Collection<Object[]> orientationSwitch() {
        return Arrays.asList(new Object[][] { { "Landscape orientation" }, { "Portrait orientation" } });
    }

    private final String orientation;

    public SearchingRealEstate(String orientation){
        this.orientation =  orientation;
    }

    @Managed
    public WebDriver driver;

    @Steps
    public UISteps user;

    @Test
    public void searching_scenario() {
        user.opens_main_page();
        user.searches_for_query_in_main_page("2 bedrooms flat");
        user.should_see_search_results_where(the("name", startsWith("2 bedrooms flat")));
        user.opens_location_popup_in_main_page();
        user.chooses_district_as("Limassol district");
        user.opens_filters_popup_in_main_page();
        user.sets_min_price_as("200");
        user.sets_max_price_as("1,000");
        user.clicks_search_button_in_filters_popup();
        user.should_see_search_results_where(the("location", startsWith("Limassol district")),
                                             the("price", greaterThanOrEqualTo(200.00)),
                                             the("price", lessThanOrEqualTo(1000.00)));
        List<Advertisement> ads = user.chooses_advertisements_in_search_results_where(
                                             the("fotoCount", greaterThan(5)));
        List<Advertisement> ads_with_min_price = user.chooses_advertisements_with_min_price_among_of(ads);
        Advertisement ad = user.chooses_the_oldest_advertisement_among_of(ads_with_min_price);
        user.clicks_on_advertisement_in_search_results(ad);
        user.should_see_url_contain(String.valueOf(ad.getId()));
        user.adds_advertisement_to_favourites();
        user.accepts_terms_on_login_page();
        user.clicks_continue_button_on_login_page();
        user.should_see_error_message_on_login_page_as("The phone number is empty or not valid");
    }

}
