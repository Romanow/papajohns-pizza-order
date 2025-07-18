package ru.romanow.playwright.tests

import com.microsoft.playwright.Page
import com.microsoft.playwright.junit.UsePlaywright
import io.qameta.allure.Feature
import org.junit.jupiter.api.Test
import ru.romanow.playwright.utils.BrowserOptions

@UsePlaywright(BrowserOptions::class)
class GoogleSearchScenarioTest {

    @Test
    @Feature("Поиск сайта Papa Johns")
    fun `when Search Papa Johns site with Google then Success`(page: Page) {
        page.navigate("https://google.com/")
        page.locator("[name = 'q']").fill("Papa Johns Москва")
        page.locator("input[name = 'btnI']").first().click()

        Thread.sleep(2000)
    }
}
