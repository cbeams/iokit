package org.iokit.general;

import org.iokit.core.IOKitParser;
import org.iokit.core.IOKitValidator;

public abstract class ValidatingParser<V> implements IOKitParser<V> {

    private final IOKitValidator<String> validator;

    public ValidatingParser(IOKitValidator<String> validator) {
        this.validator = validator;
    }

    @Override
    public V parse(String input) {
        validator.validate(input);
        return parseValidated(input);
    }

    public abstract V parseValidated(String input);
}
