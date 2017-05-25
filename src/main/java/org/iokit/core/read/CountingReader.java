package org.iokit.core.read;

import java.io.InputStream;

import java.util.Optional;

public abstract class CountingReader<T> extends OptionalReader<T> {

    public static final int DEFAULT_MINIMUM_READ_COUNT = 0;

    private int minimumReadCount = DEFAULT_MINIMUM_READ_COUNT;

    private long readCount;

    public CountingReader(InputStream in) {
        super(in);
    }

    public final Optional<T> readOptional() {
        Optional<T> value = readOptionalBeforeCounting();

        if (value.isPresent())
            readCount++;

        else if (readCount < minimumReadCount)
            throw new ReaderException(
                "Expected to read at least %d value(s), but %d were found", minimumReadCount, readCount);

        return value;
    }

    protected abstract Optional<T> readOptionalBeforeCounting();

    public void setMinimumReadCount(int minimumReadCount) {
        this.minimumReadCount = minimumReadCount;
    }

    public long getReadCount() {
        return readCount;
    }
}
