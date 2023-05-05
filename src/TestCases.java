import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

public class TestCases {
	WebDriver driver = new ChromeDriver();
	SoftAssert myAssert = new SoftAssert();
	String BASE_URL = "https://ecom-pet-store.myshopify.com/";
	String contact_page = "https://ecom-pet-store.myshopify.com/pages/contact";
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
	@BeforeTest()
	public void before() {
		driver.get(BASE_URL);
	}

	@Test(enabled = false)
	public void test1() {
		WebElement searchIcon = driver.findElement(By.cssSelector("#AccessibleNav > li:nth-child(4) > a"));
		searchIcon.click();
		WebElement searchBar = driver.findElement(By.className("input-group-field"));
		searchBar.sendKeys("cat" + Keys.ENTER);
		List<WebElement> searchResulTitles = driver.findElements(By.className("grid-product__title"));
		boolean flagIfTitleContainsCat = false;
		for (int i = 0; i < searchResulTitles.size(); i++) {
			String title = searchResulTitles.get(i).toString().toLowerCase();
			if (title.contains("cat")) {
				flagIfTitleContainsCat = true;
			} else {
				flagIfTitleContainsCat = false;
				break;
			}
		}
		myAssert.assertEquals(flagIfTitleContainsCat, true);

	}

	@Test()
	public void test2() {
		driver.get("https://ecom-pet-store.myshopify.com/collections/frontpage");
		List<WebElement> Products = driver.findElements(By.className("grid-product__meta"));
		Random rand = new Random();
		int productRandInd = rand.nextInt(Products.size());
		Products.get(productRandInd).click();

		List<WebElement> colors = driver.findElements(By.cssSelector("#ProductSelect-option-0 label"));
		List<WebElement> sizes = driver.findElements(By.cssSelector("#ProductSelect-option-1 label"));
		int colorRandInd = rand.nextInt(colors.size());
		int sizeRandInd = rand.nextInt(sizes.size());
		colors.get(colorRandInd).click();
		sizes.get(sizeRandInd).click();
		WebElement addToCart = driver.findElement(By.id("AddToCart--product-template"));
		addToCart.click();
	}
	
	@Test ()
	public void test5() throws IOException, InterruptedException {
		String cartPage = "https://ecom-pet-store.myshopify.com/cart";
		driver.get(cartPage);
		WebElement checkoutBtn = driver.findElement(By.className("cart__checkout"));
		checkoutBtn.click();
		
		WebElement emailField = driver.findElement(By.id("email"));
		WebElement lastNamelField = driver.findElement(By.name("lastName"));
		WebElement addressField = driver.findElement(By.name("address1"));
		WebElement cityField = driver.findElement(By.name("city"));
		WebElement pcodeField = driver.findElement(By.name("postalCode"));
		WebElement sbmt = driver.findElement(By.cssSelector("#Form0 > div:nth-child(1) > div > div.VheJw > div.oQEAZ.WD4IV > div:nth-child(1) > button"));

		emailField.sendKeys("aa@gmail.com");
		lastNamelField.sendKeys("testUser");
		addressField.sendKeys("Amman");
		cityField.sendKeys("amman");
		pcodeField.sendKeys("13181");
		sbmt.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#Form1 > div:nth-child(1) > div > div > div.oQEAZ.WD4IV > div:nth-child(1) > button")));
		WebElement btn = driver.findElement(By.cssSelector("#Form1 > div:nth-child(1) > div > div > div.oQEAZ.WD4IV > div:nth-child(1) > button"));
		btn.click();
		
		Thread.sleep(1000);
		LocalTime datex = java.time.LocalTime.now();
		String Sdate =  datex.toString();
		String dateFixed = Sdate.replace(":", "_");
		driver.manage().window().maximize();
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("window.scrollTo(0,0)", "");
		TakesScreenshot pic = ((TakesScreenshot) driver);
		File src_files = pic.getScreenshotAs(OutputType.FILE);
		File destFile = new File(".//pic//"+dateFixed+".png");
		FileUtils.copyFile(src_files,destFile);
	}
	
	@Test(enabled=false)
	public void test3() {
		driver.get(BASE_URL);
		List<WebElement> pet_type = driver.findElement(By.cssSelector("div.grid:nth-child(2)"))
				.findElements(By.className("collection-grid__item-link"));
		Random random = new Random();
		int random_pet_type = random.nextInt(0, pet_type.size() - 2);
		pet_type.get(random_pet_type).click();
		WebElement items_grid = driver.findElement(By.className("grid-collage"));
		List<WebElement> pet_items = items_grid.findElements(By.className("product--image"));
		myAssert.assertTrue(pet_items.size() == 10 || pet_items.size() == 12,
				"The actual number of items is not as expected");
	}

	@Test(enabled=false)
	public void test4() throws InterruptedException {
//go to contact form and fill information then submit 
		driver.get(contact_page);
		driver.findElement(By.id("ContactFormName")).sendKeys("Neveen");
		driver.findElement(By.id("ContactFormEmail")).sendKeys("Neveenjarrar@htu.com");
		driver.findElement(By.id("ContactFormPhone")).sendKeys("55292525635");
		driver.findElement(By.id("ContactFormMessage")).sendKeys("I hope to receive the order as soon as possible");
		Thread.sleep(2000);
		driver.findElement(By.className("btn")).click();
		Thread.sleep(2000);
//driver.findElement(By.id("g-recaptcha")).click();
		Thread.sleep(2000);
//    driver.findElement(By.className("shopify-challenge__button btn")).click();
		String Success_msg = driver.findElement(By.className("form-success")).getText();
//		myAssert.assertEquals(Success_msg, expected_Success__msg);
	}

	@AfterTest()
	public void after() {
		myAssert.assertAll();
	}
}
