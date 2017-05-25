package org.iokit.warc;

import org.iokit.line.LineReader;
import org.iokit.line.LineWriter;

import org.iokit.core.validate.ValidatorException;

import org.iokit.core.parse.ValidatingParser;

import java.util.Objects;

public class WarcVersion {

    public static final String WARC_1_0 = "WARC/1.0";
    public static final String WARC_1_1 = "WARC/1.1";

    private final String value;

    public WarcVersion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarcVersion version = (WarcVersion) o;
        return Objects.equals(value, version.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


    public static class Reader extends org.iokit.core.read.Reader<WarcVersion> {

        private final LineReader lineReader;
        private final org.iokit.core.parse.Parser<WarcVersion> parser;

        public Reader(LineReader lineReader) {
            this(lineReader, new Parser());
        }

        public Reader(LineReader lineReader, org.iokit.core.parse.Parser<WarcVersion> parser) {
            super(lineReader.in);
            this.lineReader = lineReader;
            this.parser = parser;
        }

        public WarcVersion read() {
            return parser.parse(lineReader.read());
        }
    }


    public static class Writer extends org.iokit.core.write.Writer<WarcVersion> {

        private final LineWriter lineWriter;

        public Writer(LineWriter lineWriter) {
            super(lineWriter.out);
            this.lineWriter = lineWriter;
        }

        public void write(WarcVersion version) {
            lineWriter.write(version.getValue());
        }
    }


    public static class Parser extends ValidatingParser<WarcVersion> {

        public Parser() {
            this(new WarcVersion.Validator());
        }

        public Parser(org.iokit.core.validate.Validator<String> validator) {
            super(validator);
        }

        @Override
        public WarcVersion parseValidated(String input) {
            return new WarcVersion(input);
        }
    }


    public static class Validator implements org.iokit.core.validate.Validator<String> {

        @Override
        public void validate(String input) {
            if (!WARC_1_0.equals(input) && !WARC_1_1.equals(input))
                throw new ValidatorException("[%s] is an unsupported or otherwise malformed WARC record version", input);
        }
    }
}
