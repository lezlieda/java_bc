package edu.school21.preprocessor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String preprocess(String message) {
        return message.toUpperCase();
    }
}
