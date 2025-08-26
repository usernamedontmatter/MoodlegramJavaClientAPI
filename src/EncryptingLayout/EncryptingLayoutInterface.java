package EncryptingLayout;

import IOStream.IOStreamInterface;

public interface EncryptingLayoutInterface extends IOStreamInterface {
    short getEncryptingNumber();

    void setIOStream(IOStreamInterface iostream);
}
