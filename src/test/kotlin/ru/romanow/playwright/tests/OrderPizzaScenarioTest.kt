package ru.romanow.playwright.tests

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Locator.FilterOptions
import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.GetByRoleOptions
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import com.microsoft.playwright.junit.UsePlaywright
import com.microsoft.playwright.options.AriaRole
import com.microsoft.playwright.options.AriaRole.BUTTON
import io.qameta.allure.Feature
import org.junit.jupiter.api.Test
import ru.romanow.playwright.utils.BrowserOptions
import ru.romanow.playwright.utils.PropertiesHelper.Companion.get

@UsePlaywright(BrowserOptions::class)
class OrderPizzaScenarioTest {

    @Test
    @Feature("Заказ пиццы на сайте Papa Johns")
    fun `when Order Italian Pizza then Success`(page: Page) {
        page.navigate("https://papajohns.ru/moscow")

        closeLocationDialog(page)
        singIn(page, get("papajohns.login"), get("papajohns.password"))
        selectPizza(page, "Итальянская с моцареллой и пепперони", 35, "Сырный борт")
        makeOrder(page)

        Thread.sleep(2000)
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

}
