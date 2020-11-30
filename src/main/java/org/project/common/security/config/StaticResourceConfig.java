package org.project.common.security.config;

import org.project.common.attachment.FileHelper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Configuration for static resources when front end accesses these resources.
 *
 * @author Zon-g
 */
@Configuration
public class StaticResourceConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        /* When a request accesses the static resources, if request maps '/avatar/**',
         * then location of avatar static resource on this server will be added to this mapping.
         * For example, if this application is running on Window, and here is a static resource
         */
        registry.addResourceHandler("/avatar/**")
                .addResourceLocations("file:" + FileHelper.getAvatarRoot());
        registry.addResourceHandler("/mail/**")
                .addResourceLocations("file:" + FileHelper.getMailRoot());
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/doc.html");
        super.addResourceHandlers(registry);
    }

}
