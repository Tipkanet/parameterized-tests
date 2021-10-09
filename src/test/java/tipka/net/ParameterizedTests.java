package tipka.net;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import tipka.net.domain.MenuItems;
import tipka.net.pages.QuestlabMainPage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ParameterizedTests {

    private QuestlabMainPage page = new QuestlabMainPage();

    @BeforeAll
    static void beforeAll() {
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub/"; // set comment mark for local build
        Configuration.startMaximized = true;
    }

    @ValueSource(strings = {
            "spelu-noteikumi",
            "rezervacija",
            "-shop-product-podarochnaia-karta-",
            "atsauksmes",
            "contacts"
    })
    @ParameterizedTest(name = "Сlick-through Test")
    void testWithValueSource(String menuItem) {
        page.clickMenuItem(menuItem);
    }

    @CsvSource({
            "spelu-noteikumi, Правила, Testing the 'Rules'-page",
            "rezervacija, Эксперимент, Testing the 'Reservation'-page",
            "-shop-product-podarochnaia-karta-, Подарочная карта, Testing the 'Gift card'-page",
            "atsauksmes, Отзывы, Testing the 'Feedback'-page",
            "contacts, Контакты, Testing the 'Contacts'-page"
            }
    )
    @ParameterizedTest(name = "{2}")
    void testWithCsvSource (String menuItems, String headline) {
        page.assertThatPageContainsTheHeadline(menuItems, headline);
    }

    @EnumSource(value = MenuItems.class, names = {"RESERVATION"}, mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest(name = "{0}")
    void testWithEnumSource (MenuItems menuItems) {
        page.assertThatPageContainsTheHeadline(menuItems);
    }

    @MethodSource("dataForMethodSource")
    @ParameterizedTest
    void fillMessageFormTest (int caseNumber, List<String> stringArray, int cardNumber) {
        switch (caseNumber) {
            case 1 : {
                page.fillMessageForm(stringArray);
            }
            break;
            case 2 : {
                page.fillPurchaseCardForm(stringArray, cardNumber);
            }
            break;
            default:
                throw new IllegalStateException("Incorrect case number: " + caseNumber);
        }
    }

    static Stream<Arguments> dataForMethodSource() {
        return Stream.of(
                Arguments.of(
                        1,
                        Arrays.asList(
                                "contacts/",
                                "Name",
                                "+371 222 333 444",
                                "realmail@gmail.com",
                                "Is it possible to organize a beer-party here?"),
                        0
                ),
                Arguments.of(
                        2,
                        Arrays.asList(
                                "shop/product/podarochnaia-karta/",
                                "Name",
                                "+371 222 333 444",
                                "realmail@gmail.com",
                                "I will pick up the card tomorrow afternoon"),
                        999999999
                )
        );
    }
}
