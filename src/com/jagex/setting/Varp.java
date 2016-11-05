package com.jagex.setting;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.jagex.cache.Archive;
import com.jagex.io.Stream;

public final class  Varp {

    public static void unpackConfig(Archive archive)
    {
        Stream stream = new Stream(archive.getEntry("varp.dat"));
        anInt702 = 0;
        int cacheSize = stream.readUnsignedWord();
        if(cache == null)
            cache = new Varp[cacheSize];
        if(anIntArray703 == null)
            anIntArray703 = new int[cacheSize];
        for(int j = 0; j < cacheSize; j++)
        {
            if(cache[j] == null)
                cache[j] = new Varp();
            cache[j].readValues(stream, j);
        }
        if(stream.currentOffset != stream.buffer.length)
            System.out.println("varptype load mismatch");
    }

    private void readValues(Stream stream, int i)
    {
        do
        {
            int j = stream.readUnsignedByte();
            if(j == 0)
                return;
            int dummy;
            if(j == 1)
                 stream.readUnsignedByte();
            else
            if(j == 2)
                stream.readUnsignedByte();
            else
            if(j == 3)
                anIntArray703[anInt702++] = i;
            else
            if(j == 4)
                dummy = 2;
            else
            if(j == 5)
                anInt709 = stream.readUnsignedWord();
            else
            if(j == 6)
                dummy = 2;
            else
            if(j == 7)
                stream.readDWord();
            else
            if(j == 8)
                aBoolean713 = true;
             else
            if(j == 10)
                 stream.readString();
            else
            if(j == 11)
                aBoolean713 = true;
            else
            if(j == 12)
                stream.readDWord();
            else
            if(j == 13)
                dummy = 2;
            else
                System.out.println("Error unrecognised config code: " + j);
        } while(true);
    }

    private Varp()
    {
        aBoolean713 = false;
    }

    public static Varp cache[];
    private static int anInt702;
    private static int[] anIntArray703;
    public int anInt709;
    public boolean aBoolean713;

}