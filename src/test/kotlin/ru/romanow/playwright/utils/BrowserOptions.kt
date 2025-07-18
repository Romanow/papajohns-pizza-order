package ru.romanow.playwright.utils

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType.LaunchOptions
import com.microsoft.playwright.junit.Options
import com.microsoft.playwright.junit.OptionsFactory
import ru.romanow.playwright.utils.PropertiesHelper.Companion.get

class BrowserOptions : OptionsFactory {
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
