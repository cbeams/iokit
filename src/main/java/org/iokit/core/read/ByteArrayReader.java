package org.iokit.core.read;

import org.iokit.lang.Try;

import java.io.InputStream;

public class ByteArrayReader implements FixedLengthReader<byte[]> {

    private final InputStream input;

    public ByteArrayReader(InputStream input) {
        this.input = input;
    }

    @Override
    public byte[] read(int length) throws ReaderException {
        byte[] array = new byte[length];

        int actual = Try.toCall(() -> input.read(array, 0, length));

        if (actual != length)
            throw new ReaderException("expected to read %d bytes, but read only %d", length, actual);

        return array;
    }
}
