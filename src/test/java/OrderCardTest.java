import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class OrderCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldOrderCard() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldOrderCardIfNameWithHyphen() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья-Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldOrderCardIfDoubleName() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldOrderCardIfNameWithJ() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошин Алексей");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameInEnglishWord() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Timoshina Natalia");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithYo() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошин Семён");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithHieroglyphs() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("愛愛愛愛愛 愛愛愛");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithArabicLigature() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("العلم نور والجهل ظ");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithSpecialSymbols() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Мари%я Ти;ошина");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithNumbers() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Наталия 123");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithSpaceBar() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("  ");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameIsNotNotFilled() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfFieldsIsNotFilled() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(By.cssSelector(".button")).click();

        String nameNotFilled = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", nameNotFilled.trim());
        String phoneNotFilled = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", phoneNotFilled.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhone10Letters() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7933333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhone3Letters() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+733");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneWithRussianLetters() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Наталья");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneWithEnglishLetters() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Natalia");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneWithHieroglyphs() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("愛愛愛愛");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneWithArabianLigature() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("العلم نور والجهل ظلام");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneWithSpecialSymbols() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7$325452");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneWithSpaceBar() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("  ");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneIsNotFilled() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneIsWrong() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79533333333");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNoClickAgreement() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Тимошина Наталья");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79533333333");

        form.findElement(By.cssSelector(".button")).click();
        WebElement agree = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid"));
        assertTrue(agree.isDisplayed(), "Сообщение об ошибке");
    }
}