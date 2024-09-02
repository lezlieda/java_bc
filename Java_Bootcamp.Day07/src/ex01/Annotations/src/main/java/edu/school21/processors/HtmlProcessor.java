package edu.school21.processors;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }
}