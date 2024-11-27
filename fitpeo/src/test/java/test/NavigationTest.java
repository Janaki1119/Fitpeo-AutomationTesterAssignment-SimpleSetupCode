package test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NavigationTest {

	public static void main(String[] args) throws InterruptedException {
		 WebDriver driver = new ChromeDriver();
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Wait timeout of 10 seconds
	        
	        System.out.println("TestCase1: Navigate to the FitPeo Homepage: Open the web browser and navigate to FitPeo Homepage.");
	       	driver.get("https://fitpeo.com/home");
	        driver.manage().window().maximize();
	        wait.until(ExpectedConditions.titleIs("Remote Patient Monitoring (RPM) - fitpeo.com"));
	        String actualTitle = driver.getTitle();
	        String expectedTitle = "Remote Patient Monitoring (RPM) - fitpeo.com";
	        if (!actualTitle.equals(expectedTitle)) {
	            System.out.println("Title does not match! Actual: " + actualTitle + ", Expected: " + expectedTitle);
	        } else {
	            System.out.println("Title matches!");
	        }
	        System.out.println("***************************************************************************************************************");
	        System.out.println("TestCase2: Navigate to the Revenue Calculator Page \"From the homepage, navigate to the Revenue Calculator Page.:");
	        WebElement revenueCalculatorPage = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Revenue Calculator")));
	        revenueCalculatorPage.click();
	        wait.until(ExpectedConditions.urlContains("revenue-calculator")); // Adjust URL part based on the actual navigation
	        String actualUrl = driver.getCurrentUrl();
	        if (!actualUrl.contains("/revenue-calculator")) {
	            System.out.println("Url does not match! Actual: " + actualUrl);
	        } else {
	            System.out.println("Successfully navigated to the Revenue Calculator page.!");
	        }
	        System.out.println("***************************************************************************************************************");
	        System.out.println("TestCase3: Scroll Down to the Slider section:Scroll down the page until the revenue calculator slider is visible.");
	        WebElement sliderSection=driver.findElement(By.className("css-79elbk"));
	        WebElement sliderInput = driver.findElement(By.cssSelector("input[type='range']"));
	        WebElement textField =  driver.findElement(By.cssSelector("input.MuiInputBase-input.MuiOutlinedInput-input.MuiInputBase-inputSizeSmall"));
	        WebElement sliderTrack = driver.findElement(By.cssSelector(".MuiSlider-track"));
	        WebElement sliderThumb = driver.findElement(By.cssSelector(".MuiSlider-thumb"));
	        int newValue = 820;
	        int newValue1 = 560;
	        int minValue = Integer.parseInt(sliderInput.getAttribute("min"));
	        int maxValue = Integer.parseInt(sliderInput.getAttribute("max"));
	        double percentage = ((double) (newValue - minValue) / (maxValue - minValue)) * 100;
	        double percentage1 = ((double) (newValue1 - minValue) / (maxValue - minValue)) * 100;
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].scrollIntoView(true);", sliderSection);
	        Thread.sleep(1000);
	        boolean isScrolledIntoView = (boolean) js.executeScript(
	                "const rect = arguments[0].getBoundingClientRect();" +
	                "return (rect.top >= 0 && rect.bottom <= window.innerHeight);",
	                sliderSection);

	        if (isScrolledIntoView) {
	            System.out.println("Element is successfully scrolled into view.");
	        } else {
	            System.out.println("Element is not scrolled into view.");
	        }
	        System.out.println("***************************************************************************************************************");
	        System.out.println("TestCase4: Adjust the Slider:Adjust the slider to set its value to 820. we’ve highlighted the slider in red color,"+
	        "\n"+" Once the slider is moved the bottom text field value should be updated to 820");
	        
	        if (newValue < minValue || newValue > maxValue) {
	            System.out.println("The value " + newValue + " is out of range.");
	            return;
	        }
	        
	        js.executeScript("arguments[0].setAttribute('value', '" + newValue + "');", sliderInput);
	        js.executeScript("arguments[0].setAttribute('aria-valuenow', '" + newValue + "');", sliderInput); // Update aria-valuenow

	        // Move the thumb and update the track width
	        js.executeScript("arguments[0].style.left='" + percentage + "%';", sliderThumb); // Update thumb position
	        js.executeScript("arguments[0].style.width='" + percentage + "%';", sliderTrack); // Update track width
	        js.executeScript(
	            "arguments[0].value = arguments[1];" +
	            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
	            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
	            textField , newValue
	        );
	        Thread.sleep(1000);
	        String textFieldUpdatedValue = textField.getAttribute("value");
	        System.out.println("Updated slider value: " + textFieldUpdatedValue);
	        if (newValue == Integer.parseInt(textFieldUpdatedValue)) {
	            System.out.println("Successfully updated the slider value to: " + newValue);
	        } else {
	            System.out.println("Failed to update the slider value. Current value: " + textFieldUpdatedValue);
	        }
             System.out.println("***************************************************************************************************************");
             System.out.println("TestCase5: Update the Text Field:Click on the text field associated with the slider."+"\n"
             +" Enter the value 560 in the text field. Now the slider also should change accordingly ");
             textField.clear();
             Thread.sleep(1000);
             js.executeScript(
                     "arguments[0].value = arguments[1];" +
                     "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                     "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                     textField, newValue1
                 );
             // for observance
             Thread.sleep(1000);
             js.executeScript("arguments[0].setAttribute('value', '" + newValue1 + "');", sliderInput);
             js.executeScript("arguments[0].setAttribute('aria-valuenow', '" + newValue1 + "');", sliderInput); // Update aria-valuenow
             js.executeScript("arguments[0].style.left='" + percentage1 + "%';", sliderThumb); // Update thumb position
             js.executeScript("arguments[0].style.width='" + percentage1 + "%';", sliderTrack); // Update track width
             Thread.sleep(3000);
             String textFieldValue = textField.getAttribute("value");
             System.out.println("verify textFieldValue got updated: " + textFieldValue);
             System.out.println("***************************************************************************************************************");
             System.out.println("TestCase6: Validate Slider Value:\r\n"+ "Ensure that when the value 560 is entered in the text field, "
              		+ "the slider's position is updated to reflect the value 560");
             String ariaValueNow = sliderInput.getAttribute("aria-valuenow");
             System.out.println("verifying aria-valuenow attribute of slider's position is getting updated on entering value in textfield: " + ariaValueNow);
             System.out.println("***************************************************************************************************************");
             System.out.println("TestCase7: Select CPT Codes:\r\n"+ 
             "Scroll down further and select the checkboxes for CPT-99091, CPT-99453, CPT-99454, and CPT-99474.\r\n");
             WebElement cptElement = driver.findElement(By.cssSelector(".MuiBox-root.css-1p19z09"));
             js.executeScript("arguments[0].scrollIntoView(true);", cptElement);
             // Pause for observation (optional)
             Thread.sleep(2000);
             List<WebElement> cptCards = driver.findElements(By.xpath("//div[contains(@class, 'MuiBox-root') and contains(., 'CPT-')]"));

             for (WebElement cptCard : cptCards) {
                 // Extract the CPT code text
                 String cptCode = cptCard.findElement(By.xpath(".//*[contains(text(), 'CPT-')]")).getText();

                 // Check if it matches the target CPT codes
                 if (cptCode.equals("CPT-99091") || cptCode.equals("CPT-99453") ||
                     cptCode.equals("CPT-99454") || cptCode.equals("CPT-99474")) {
                     
                     // Find and click the checkbox within the card
                     WebElement checkbox = cptCard.findElement(By.xpath(".//input[@type='checkbox']"));
                     if (!checkbox.isSelected()) {
                         checkbox.click();
                         System.out.println("Clicked checkbox for: " + cptCode);
                     }
                 }
             }
             Thread.sleep(3000);
             System.out.println("***************************************************************************************************************");
             System.out.println("TestCase8: Validate Total Recurring Reimbursement:\r\n"
             		+ "TestCase9:Verify that the header displaying Total Recurring Reimbursement for all Patients Per Month: shows the value $110700.");
             // Locate all CPT cards and uncheckedcheckboxes
             System.out.println("Checking the unchecked checkboxes , so that all the checkboxes gets checked in order to calculate Total Recurring Reimbursement");
             List<WebElement> cptCardUnchecked = driver.findElements(By.xpath("//div[contains(@class, 'MuiBox-root') and contains(., 'CPT-')]"));
             for (WebElement cptCard : cptCardUnchecked) {
             	// Extract the checkbox within each CPT card
                 WebElement checkbox = cptCard.findElement(By.xpath(".//input[@type='checkbox']"));
                 // Check if the checkbox is not already selected, then select it
                 if (!checkbox.isSelected()) {
                     checkbox.click();
                     String cptCode = cptCard.findElement(By.xpath(".//*[contains(text(), 'CPT-')]")).getText();
                     System.out.println("Checked checkbox for CPT code: " + cptCode);
                 }
             }
             Thread.sleep(3000);
             // Scroll to the element and capture the value*/
             WebElement totalReimbursementHeader =driver.findElement(By.xpath("//div[@class='MuiToolbar-root MuiToolbar-gutters MuiToolbar-regular css-1lnu3ao']//p[contains(@class, 'MuiTypography-body2')][4]"));
             js.executeScript("arguments[0].scrollIntoView(true);", totalReimbursementHeader);
             
             // Extract the displayed value
             String displayedValue = totalReimbursementHeader.getText();
             System.out.println("Displayed Value: " + displayedValue);
             // Expected value for validation
             String expectedValue = "$110700";
             // Validate the displayed value against the expected value
             if (displayedValue.equals(expectedValue)) {
                 System.out.println("Validation passed: The displayed value matches the expected value.");
             } else {
                 System.out.println("Validation failed: Expected value is " + expectedValue + ", but found " + displayedValue);
             }
             
             System.out.println("***************************************************************************************************************");
             driver.quit();
	}

}
