package net;

import java.io.*;

// package access
abstract class NetworkConnection {
    protected void send(byte[] data) {
        // TODO: 1/17/17
    }

    protected byte[] receive() {
        // TODO: 1/17/17
        return null;
    }

    protected byte[] toByteArray(Serializable data) throws IOException {
        // convert the data (e.g array of shapes) to an array of bytes

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutput oo = new ObjectOutputStream(baos)) {
            oo.writeObject(data);
        }

        return baos.toByteArray();
    }
}
