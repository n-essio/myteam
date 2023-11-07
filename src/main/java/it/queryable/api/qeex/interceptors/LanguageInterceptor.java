package it.queryable.api.qeex.interceptors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@ApplicationScoped
public class LanguageInterceptor implements ContainerRequestFilter {

    private String language;


    public String getLanguage() {
        return language;
    }

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        if (context.getHeaders().containsKey("language")) {
            var language = context.getHeaders().getFirst("language");
            this.language = language;
        }
    }
}