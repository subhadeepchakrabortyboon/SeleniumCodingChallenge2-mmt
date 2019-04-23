package challenge2.mmt.WebPages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import challenge.mmt.Browser.Driver;
import challenge.mmt.util.ElementVisibility;
import challenge.mmt.util.PageScroll;

public class FlightResultPage extends HomePage{

	@FindBy(xpath="//span[text()='Non Stop']/preceding-sibling::span")
	WebElement NonStopCheckbox;
	
	@FindBy(xpath="//span[text()='1 Stop']/preceding-sibling::span")
	WebElement oneStopCheckbox;
	
	@FindBy(xpath="//a[@class='fli-clear inlineB']")
	WebElement clearFilter;
	
	@FindBy(xpath="//div[@class='splitVw-sctn pull-left']/child::div[2]/child::div")
	List<WebElement> departureFilghts;
	
	@FindBy(xpath="//div[@class='splitVw-sctn pull-right']/child::div[2]/child::div")
	List<WebElement> ReturnFilghts;
	
	@FindBy(xpath="//div[@class='pull-right marL5 text-right']/p/span")
	List<WebElement> FlightPrices;
	
	@FindBy(xpath="//span[@class='splitVw-total-fare']")
	WebElement totalFlightPrice;
	
	@FindBy(xpath="//span[@class='slashed-price']")
	WebElement shlashedTotalPrice;
	
	By SelectedFlightPrice=By.xpath("//p[@class='actual-price']");
	public FlightResultPage()
	{
		PageFactory.initElements(Driver.driver, this);
	}
	public int departureFilightCount()
	{
		PageScroll.toBottomOfPage();
		return departureFilghts.size();
	}
	
	public int returnFilightCount()
	{
		PageScroll.toUP();
		return ReturnFilghts.size();
	}
	
	public int[] NoFilterFlightCount()
	{
		
		clearFilter();
		int[] count=new int[2];
		count[0]=departureFilightCount();
		count[1]=returnFilightCount();
		return count;
	}
	
	public int[] NoStopFlightCount()
	{
		clearFilter();
		NonStopCheckbox.click();
		int[] count=new int[2];
		count[0]=departureFilightCount();
		count[1]=returnFilightCount();
		return count;
	}
	
	public int[] oneStopFlightCount()
	{
		clearFilter();
		oneStopCheckbox.click();
		int[] count=new int[2];
		count[0]=departureFilightCount();
		count[1]=returnFilightCount();
		return count;
	}
	
	public Map<String, String> selectRandomFlight(int dep,int ret)
	{
		Map<String, String> Prices=new HashMap<String, String>();
		
		JavascriptExecutor js=(JavascriptExecutor)Driver.driver;
		js.executeScript("arguments[0].click();", departureFilghts.get(dep));
			
		js.executeScript("arguments[0].click();", ReturnFilghts.get(ret));
		
		String[] Flightdetails=departureFilghts.get(dep).getAttribute("innerText").split("\\r?\\n");
		Prices.put("Dep Flight", Flightdetails[13]);
		Flightdetails=ReturnFilghts.get(ret).getAttribute("innerText").split("\\r?\\n");
		Prices.put("Ret Flight", Flightdetails[13]);
		Prices.put("Dep Bottom Price", FlightPrices.get(0).getText());
		Prices.put("Ret Bottom Price", FlightPrices.get(1).getText());
		if(ElementVisibility.isVisble(shlashedTotalPrice))
		{
			Prices.put("total Price", shlashedTotalPrice.getText());
		}
		else
		{
		Prices.put("total Price", totalFlightPrice.getText());
		}
		return Prices;
	}
	
	public void clearFilter()
	{
		if(ElementVisibility.isVisble(clearFilter))
		{
			clearFilter.click();
		}
	}
}