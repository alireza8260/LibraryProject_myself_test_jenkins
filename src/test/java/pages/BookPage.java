package pages;

import utility.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utility.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookPage extends BasePage {
    @FindBy(xpath = "//*[@id=\"tbl_books\"]/tbody/tr/td[1]/a")
    public WebElement editBtn;

    @FindBy(xpath = "//*[@id=\"tbl_books\"]/tbody/tr/td[3]")
    public WebElement name;

    @FindBy(xpath = "//*[@id=\"tbl_books\"]/tbody/tr/td[2]")
    public WebElement Isbn;

    @FindBy(xpath = "//*[@id=\"tbl_books\"]/tbody/tr/td[4]")
    public WebElement Author;
    @FindBy(xpath = "//*[@id=\"tbl_books\"]/tbody/tr/td[6]")
    public WebElement Year;

    @FindBy(xpath = "//table/tbody/tr")
    public List<WebElement> allRows;

    @FindBy(xpath = "//input[@type='search']")
    public WebElement search;

    @FindBy(id = "book_categories")
    public WebElement mainCategoryElement;

    @FindBy(name = "name")
    public WebElement bookName;


    @FindBy(xpath = "(//input[@type='text'])[4]")
    public WebElement author;

    @FindBy(xpath = "//div[@class='portlet-title']//a")
    public WebElement addBook;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveChanges;

    @FindBy(xpath = "//div[@class='toast-message']")
    public WebElement toastMessage;

    @FindBy(name = "year")
    public WebElement year;

    @FindBy(name = "isbn")
    public WebElement isbn;

    @FindBy(id = "book_group_id")
    public WebElement categoryDropdown;


    @FindBy(id = "description")
    public WebElement description;

    @FindBy(xpath = "//tr/td[position()>=2 and position()<=6]")
    public List<WebElement> bookInfo;


    public WebElement editBook(String book) {
        String xpath = "//td[3][.='" + book + "']/../td/a";
        return Driver.getDriver().findElement(By.xpath(xpath));
    }

    public WebElement borrowBook(String book) {
        String xpath = "//td[3][.='" + book + "']/../td/a";
        return Driver.getDriver().findElement(By.xpath(xpath));
    }
    public  Map<String,Object> getActualBookInfo(){
        Map<String ,Object>actualBookInfo = new HashMap<>();
        BookPage bookPage = new BookPage();
        actualBookInfo.put("name",bookPage.name.getText());
        actualBookInfo.put("isbn", bookPage.Isbn.getText());
        actualBookInfo.put("year", bookPage.Year.getText());
        actualBookInfo.put("author", bookPage.Author.getText());


        return actualBookInfo;
    }



}
