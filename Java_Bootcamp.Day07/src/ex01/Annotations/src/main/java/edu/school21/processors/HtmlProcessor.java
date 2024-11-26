package edu.school21.processors;

import com.google.auto.service.AutoService;
import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            TypeElement typeElement = (TypeElement) element;
            HtmlForm htmlForm = typeElement.getAnnotation(HtmlForm.class);

            try {
                FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", htmlForm.fileName());
                PrintWriter writer = new PrintWriter(fileObject.openWriter());

                // Generate HTML form code
                writer.println("<form action=\"" + htmlForm.action() + "\" method=\"" + htmlForm.method() + "\">");

                // Process @HtmlInput annotations
                for (Element enclosedElement : element.getEnclosedElements()) {
                    HtmlInput htmlInput = enclosedElement.getAnnotation(HtmlInput.class);
                    if (htmlInput != null) {
                        writer.println("<input type=\"" + htmlInput.type() + "\" name=\"" + htmlInput.name() + "\" placeholder=\"" + htmlInput.placeholder() + "\">");
                    }
                }

                writer.println("<input type=\"submit\" value=\"Send\">");
                writer.println("</form>");

                writer.close();

            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error generating HTML form: " + e.getMessage());
            }
        }

        return true;
    }

}