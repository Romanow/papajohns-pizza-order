package ru.romanow.playwright.tests

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType.LaunchOptions
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Locator.FilterOptions
import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.GetByRoleOptions
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import com.microsoft.playwright.junit.Options
import com.microsoft.playwright.junit.OptionsFactory
import com.microsoft.playwright.junit.UsePlaywright
import com.microsoft.playwright.options.AriaRole
import com.microsoft.playwright.options.AriaRole.BUTTON
import io.qameta.allure.Feature
import org.junit.jupiter.api.Test
import ru.romanow.playwright.utils.PropertiesHelper.Companion.get

@UsePlaywright(PapaJohnsPizzaTest.BrowserOptions::class)
class PapaJohnsPizzaTest {

    @Test
    @Feature("Заказ пиццы на сайте Papa Johns")
    fun `when Order Italian Pizza then Success`(page: Page) {
        page.navigate("https://papajohns.ru/")

        closeLocationDialog(page)
        singIn(page, get("papajohns.login"), get("papajohns.password"))
        selectPizza(page, "Итальянская с моцареллой и пепперони", 35, "Сырный борт")
        makeOrder(page)
    }

    private fun singIn(page: Page, login: String?, password: String?) {
        page.locator("header button:nth-of-type(2)").click()
        page.locator("Вход", BUTTON).click()
        page.locator("input[name='phone']").fill(login)
        page.locator("input[name='password']").fill(password)
        page.locator("Войти", BUTTON).click()
    }

    private fun selectPizza(page: Page, pizza: String, size: Int, border: String) {
        val selectedPizza = page.locator(".ProductCard")
            .filter(FilterOptions().apply { has = page.locator("xpath=.//*[normalize-space() = '$pizza']") })
            .first()
        selectedPizza.locator("picture").click()

        page.locator("#modal div:text('$size')").click()

        page.locator("#modal div[data-test-id='toggle_arrow']").click()
        page.locator("#modal div:text('$border')").click()

        page.locator("#modal .ProductCardModal__button").click()
    }

    private fun makeOrder(page: Page) {
        page.locator("div[data-test-id='cart_actions']").click()
        assertThat(page.locator("button:has-text('Оформить заказ')")).isVisible()
    }

    private fun closeLocationDialog(page: Page) {
        val locationDialog = page.locator("#modal")
        if (locationDialog.isVisible) {
            locationDialog.locator("button:has-text('Да')").click()
        }
    }

    private fun Page.locator(text: String, role: AriaRole): Locator =
        this.getByRole(role, GetByRoleOptions().setName(text))

    internal class BrowserOptions : OptionsFactory {
        override fun getOptions(): Options {
            return Options().apply {
                contextOptions = Browser.NewContextOptions().apply {
                    locale = "ru-RU"
                    userAgent =
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36"
                };

                headless = get("playwright.headless-mode", Boolean::class)
                launchOptions = LaunchOptions().apply {
                    slowMo = if (get("playwright.slow-mode", Boolean::class) == true) 500.0 else 0.0
                }
            }
        }
    }
}
