package org.iokit.gzip;

import org.iokit.core.IOKitInputStream;
import org.iokit.core.LineTerminator;
import org.iokit.core.Try;

import java.util.zip.GZIPInputStream;

import java.io.InputStream;

import java.util.EnumSet;

public class MultiMemberGzipInputStream extends IOKitInputStream {

    public static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;

    public MultiMemberGzipInputStream(GZIPInputStream in, InputStream raw, EnumSet<LineTerminator> terminators) {
        super(in, raw, terminators);
    }


    public static class Adapter implements IOKitInputStream.Adapter {

        private final int size;

        public Adapter() {
            this(DEFAULT_BUFFER_SIZE);
        }

        public Adapter(int size) {
            this.size = size;
        }

        @Override
        public boolean canAdapt(byte[] magic) {
            return magic.length >= 2 && magic[0] == (byte) 0x1f && magic[1] == (byte) 0x8b;
        }

        @Override
        public MultiMemberGzipInputStream adapt(InputStream raw, EnumSet<LineTerminator> terminators) {
            GZIPInputStream in =
                (raw instanceof GZIPInputStream) ?
                    (GZIPInputStream) raw :
                    Try.toCall(() -> new GZIPInputStream(raw, size));

            return new MultiMemberGzipInputStream(in, raw, terminators);
        }
    }
}
