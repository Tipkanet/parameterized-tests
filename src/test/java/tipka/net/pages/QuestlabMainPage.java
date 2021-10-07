package tipka.net.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import tipka.net.domain.MenuItems;
import java.util.List;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class QuestlabMainPage {

    public static final String URL = "https://ru.questlab.lv/";
    private SelenideElement title =  $(".title");
    private SelenideElement nameOne = $("#id_field_5");
    private SelenideElement mailOne = $("#id_field_6");
    private SelenideElement phoneOne = $("#id_field_7");
    private SelenideElement messageOne = $("#id_field_8");
    private SelenideElement submitOne = $("#submitted");
    private SelenideElement submitPurchase = $(".submit");
    private SelenideElement giftCard = $(".media-body");
    private SelenideElement nameTwo = $("#id_billing_detail_first_name");
    private SelenideElement phoneTwo = $("#id_billing_detail_phone");
    private SelenideElement mailTwo = $("#id_billing_detail_email");
    private SelenideElement messageTwo = $("#id_additional_instructions");
    private SelenideElement cash = $("#id_pmt_1");
    private SelenideElement discount = $("#id_discount_code");
    private SelenideElement agree = $("#id_i_agree");
    private SelenideElement submitTwo = $(".form-actions");

    public void clickMenuItem(String menuItem) {
        Selenide.open(URL);
        $("#" + menuItem).click();
    }

    public void assertThatPageContainsTheHeadline(String menuItem, String headline) {
        clickMenuItem(menuItem);
        title.shouldHave(text(headline));
    }

    public void assertThatPageContainsTheHeadline(MenuItems menuItem) {
        clickMenuItem(menuItem.getDescription());
        String textForSearching = getTextForSearching(menuItem);
        if (textForSearching == null) {
            throw new IllegalArgumentException("Incorrect argument: " + menuItem.name());
        } else {
            title.shouldHave(text(textForSearching));
        }
    }

    private String getTextForSearching(MenuItems menuItem) {
        String textForSearching = null;
        switch (menuItem.name()) {
            case "RULES":
                textForSearching = "Правила";
                break;
            case "RESERVATION":
                textForSearching = "Эксперимент";
                break;
            case "GIFT":
                textForSearching = "Подарочная карта";
                break;
            case "FEEDBACK":
                textForSearching = "Отзывы";
                break;
            case "CONTACTS":
                textForSearching = "Контакты";
                break;
        }
        return textForSearching;
    }

    public void fillMessageForm(List<String> stringArray) {
        Selenide.open(URL + stringArray.get(0));
        nameOne.setValue(stringArray.get(1));
        mailOne.setValue(stringArray.get(3));
        phoneOne.setValue(stringArray.get(2));
        messageOne.setValue(stringArray.get(4));
        submitOne.hover();
        sleep(5000);
    }


    public void fillPurchaseCardForm(List<String> stringArray, int cardNumber) {
        Selenide.open(URL + stringArray.get(0));
        submitPurchase.click();
        giftCard.shouldHave(text("1 x Подарочная карта Квест: 35 eiro karte "));
        nameTwo.setValue(stringArray.get(1));
        phoneTwo.setValue(stringArray.get(2));
        mailTwo.setValue(stringArray.get(3));
        cash.click();
        discount.setValue(cardNumber + "");
        messageTwo.setValue(stringArray.get(4));
        agree.click();
        submitTwo.hover();
        sleep(5000);
    }

}
