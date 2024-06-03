package com.tymipawi.labirynt.utils;

import java.io.DataInputStream;
import java.io.IOException;

public class Binary {
   public Binary() {
   }

   public static int readUnsignedShortLittleEndian(DataInputStream dis) throws IOException {
      int b1 = dis.readUnsignedByte();
      int b2 = dis.readUnsignedByte();
      return b2 << 8 | b1;
   }

   public static long readUnsignedIntLittleEndian(DataInputStream dis) throws IOException {
      int b1 = dis.readUnsignedByte();
      int b2 = dis.readUnsignedByte();
      int b3 = dis.readUnsignedByte();
      int b4 = dis.readUnsignedByte();
      return (long)b4 << 24 | (long)b3 << 16 | (long)(b2 << 8) | (long)b1;
   }
}
