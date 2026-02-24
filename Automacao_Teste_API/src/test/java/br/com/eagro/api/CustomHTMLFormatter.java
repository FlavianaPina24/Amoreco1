package br.com.eagro.api;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.core.plugin.HtmlFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomHTMLFormatter implements EventListener {

    private final HtmlFormatter delegate;

    public CustomHTMLFormatter(String output) throws IOException {
        new File("Evidencias").mkdirs();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
        File reportFile = new File("Evidencias/report-" + timestamp + ".html");
        this.delegate = new HtmlFormatter(new FileOutputStream(reportFile));
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        // A única coisa que precisamos fazer é delegar TUDO para o formatador padrão.
        // Ele já sabe como lidar com todos os eventos.
        delegate.setEventPublisher(publisher);
    }
}
