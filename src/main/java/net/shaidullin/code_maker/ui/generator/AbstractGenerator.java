package net.shaidullin.code_maker.ui.generator;

import freemarker.core.PlainTextOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.StringWriter;
import java.io.Writer;

public abstract class AbstractGenerator implements Generator {
    protected static final String IMPORTED_PACKAGES = "imported_packages";
    protected static final String MODEL = "model";
    protected static final String PACKAGE = "package";

    private Configuration cfg;

    public AbstractGenerator() {
        this.initialize();
    }

    private void initialize() {
        cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setClassForTemplateLoading(getClass(), "/templates");

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setOutputFormat(PlainTextOutputFormat.INSTANCE);
        cfg.setAutoEscapingPolicy(Configuration.DISABLE_AUTO_ESCAPING_POLICY);
    }


    protected String renderTemplate(Object model, String templateName) {
        try {
            Template template = cfg.getTemplate(templateName);
            Writer writer = new StringWriter();
            template.process(model, writer);

            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
