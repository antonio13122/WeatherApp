package com.example.weatherlive.view;

import com.example.weatherlive.service.WeatherService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Route("")
public class MainView extends VerticalLayout {

    private final WeatherService weatherService;
    private final TextField cityField;
    private final Button getWeatherButton;
    private final Div weatherContainer;

    @Autowired
    public MainView(WeatherService weatherService) {
        this.weatherService = weatherService;

        cityField = new TextField("City");
        getWeatherButton = new Button("Get Weather");
        weatherContainer = new Div();

        getWeatherButton.addClickListener(click -> fetchWeather(cityField.getValue()));

        add(cityField, getWeatherButton, weatherContainer);

        // Load CSS
        loadCss();

        // Load JavaScript
        loadJavaScript();
    }

    @ClientCallable
    public void updateWeather(String weatherData) {
        // Handle the weather data
        weatherContainer.removeAll();
        weatherContainer.add(new Paragraph(weatherData));
    }

    @ClientCallable
    public void showError(String error) {
        Notification.show(error);
    }

    private void fetchWeather(String city) {
        getElement().executeJs("fetchWeather($0)", city);
    }

    private void loadCss() {
        getElement().executeJs(
                "var link = document.createElement('link');" +
                        "link.rel = 'stylesheet';" +
                        "link.href = './styles.css';" +
                        "document.head.appendChild(link);"
        );
    }

    private void loadJavaScript() {
        getElement().executeJs(
                "var script = document.createElement('script');" +
                        "script.src = './js/script.js';" +
                        "document.head.appendChild(script);" +
                        "script.onload = function() { window.loadWeatherJS(); };"
        );
    }
}
