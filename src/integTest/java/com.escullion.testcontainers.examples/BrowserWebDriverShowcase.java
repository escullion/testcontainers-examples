package com.escullion.testcontainers.examples;


import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

import java.io.File;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

public class BrowserWebDriverShowcase {

  @Rule
  public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
      .withCapabilities(new ChromeOptions())
      .withRecordingMode(RECORD_ALL, new File("./out/"));

  @Test
  public void whenSearchingRickAstleyInChromeExpectMemeFound() {
    RemoteWebDriver driver = chrome.getWebDriver();
    driver.get("https://wikipedia.org");
    WebElement searchInput = driver.findElementByName("search");
    searchInput.sendKeys("Rick Astley");
    searchInput.submit();

    WebElement otherPage = driver.findElementByLinkText("Rickrolling");
    otherPage.click();

    boolean expectedTextFound = driver.findElementsByCssSelector("p")
        .stream()
        .anyMatch(element -> element.getText().contains("meme"));

    assertThat(expectedTextFound).isTrue();
  }
}
