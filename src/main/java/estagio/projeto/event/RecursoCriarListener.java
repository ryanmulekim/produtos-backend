package estagio.projeto.event;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class RecursoCriarListener implements ApplicationListener<RecursoCriarEvent> {

    @Override
    public void onApplicationEvent(RecursoCriarEvent recursoCriarEvent) {
        HttpServletResponse response = recursoCriarEvent.getResponse();
        Long codigo = recursoCriarEvent.getCodigo();

        adicionarHeaderLocationURI(codigo, response);
    }

    private static void adicionarHeaderLocationURI(Long codigo, HttpServletResponse response) {
        URI uri =  ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
