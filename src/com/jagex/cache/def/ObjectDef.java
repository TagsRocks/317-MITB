package com.jagex.cache.def;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.jagex.Client;
import com.jagex.cache.Archive;
import com.jagex.cache.anim.Frame;
import com.jagex.cache.setting.VariableBits;
import com.jagex.entity.model.Model;
import com.jagex.io.Buffer;
import com.jagex.link.Cache;
import com.jagex.net.OnDemandFetcher;

public final class ObjectDef
{

    public static ObjectDef forID(int i)
    {
        for(int j = 0; j < 20; j++)
            if(cache[j].type == i)
                return cache[j];

        cacheIndex = (cacheIndex + 1) % 20;
        ObjectDef class46 = cache[cacheIndex];
        buffer.position = streamIndices[i];
        class46.type = i;
        class46.setDefaults();
        class46.readValues(buffer);
        return class46;
    }

    private void setDefaults()
    {
        anIntArray773 = null;
        anIntArray776 = null;
        name = null;
        description = null;
        modifiedModelColors = null;
        originalModelColors = null;
        anInt744 = 1;
        anInt761 = 1;
        aBoolean767 = true;
        aBoolean757 = true;
        hasActions = false;
        aBoolean762 = false;
        aBoolean769 = false;
        aBoolean764 = false;
        anInt781 = -1;
        anInt775 = 16;
        aByte737 = 0;
        aByte742 = 0;
        actions = null;
        anInt746 = -1;
        anInt758 = -1;
        aBoolean751 = false;
        aBoolean779 = true;
        anInt748 = 128;
        anInt772 = 128;
        anInt740 = 128;
        anInt768 = 0;
        anInt738 = 0;
        anInt745 = 0;
        anInt783 = 0;
        aBoolean736 = false;
        aBoolean766 = false;
        anInt760 = -1;
        anInt774 = -1;
        anInt749 = -1;
        childrenIDs = null;
    }

    public void method574(OnDemandFetcher class42_sub1)
    {
        if(anIntArray773 == null)
            return;
        for(int j = 0; j < anIntArray773.length; j++)
            class42_sub1.method560(anIntArray773[j] & 0xffff, 0);
    }

    public static void nullLoader()
    {
        mruNodes1 = null;
        mruNodes2 = null;
        streamIndices = null;
        cache = null;
buffer = null;
    }

    public static void unpackConfig(Archive archive)
    {
        buffer = new Buffer(archive.getEntry("loc.dat"));
        Buffer buffer = new Buffer(archive.getEntry("loc.idx"));
        int totalObjects = buffer.readUShort();
        streamIndices = new int[totalObjects];
        int i = 2;
        for(int j = 0; j < totalObjects; j++)
        {
            streamIndices[j] = i;
            i += buffer.readUShort();
        }

        cache = new ObjectDef[20];
        for(int k = 0; k < 20; k++)
            cache[k] = new ObjectDef();

    }

    public boolean method577(int i)
    {
        if(anIntArray776 == null)
        {
            if(anIntArray773 == null)
                return true;
            if(i != 10)
                return true;
            boolean flag1 = true;
            for(int k = 0; k < anIntArray773.length; k++)
                flag1 &= Model.method463(anIntArray773[k] & 0xffff);

            return flag1;
        }
        for(int j = 0; j < anIntArray776.length; j++)
            if(anIntArray776[j] == i)
                return Model.method463(anIntArray773[j] & 0xffff);

        return true;
    }

    public Model method578(int i, int j, int k, int l, int i1, int j1, int k1)
    {
        Model model = method581(i, k1, j);
        if(model == null)
            return null;
        if(aBoolean762 || aBoolean769)
            model = new Model(aBoolean762, aBoolean769, model);
        if(aBoolean762)
        {
            int l1 = (k + l + i1 + j1) / 4;
            for(int i2 = 0; i2 < model.anInt1626; i2++)
            {
                int j2 = model.anIntArray1627[i2];
                int k2 = model.anIntArray1629[i2];
                int l2 = k + ((l - k) * (j2 + 64)) / 128;
                int i3 = j1 + ((i1 - j1) * (j2 + 64)) / 128;
                int j3 = l2 + ((i3 - l2) * (k2 + 64)) / 128;
                model.anIntArray1628[i2] += j3 - l1;
            }

            model.method467();
        }
        return model;
    }

    public boolean method579()
    {
        if(anIntArray773 == null)
            return true;
        boolean flag1 = true;
        for(int i = 0; i < anIntArray773.length; i++)
            flag1 &= Model.method463(anIntArray773[i] & 0xffff);
            return flag1;
    }

    public ObjectDef method580()
    {
        int i = -1;
        if(anInt774 != -1)
        {
        	i = VariableBits.get(anInt774, clientInstance.variousSettings);
        } else
        if(anInt749 != -1)
            i = clientInstance.variousSettings[anInt749];
        if(i < 0 || i >= childrenIDs.length || childrenIDs[i] == -1)
            return null;
        else
            return forID(childrenIDs[i]);
    }

    private Model method581(int j, int k, int l)
    {
        Model model = null;
        long l1;
        if(anIntArray776 == null)
        {
            if(j != 10)
                return null;
            l1 = (long)((type << 6) + l) + ((long)(k + 1) << 32);
            Model model_1 = (Model) mruNodes2.get(l1);
            if(model_1 != null)
                return model_1;
            if(anIntArray773 == null)
                return null;
            boolean flag1 = aBoolean751 ^ (l > 3);
            int k1 = anIntArray773.length;
            for(int i2 = 0; i2 < k1; i2++)
            {
                int l2 = anIntArray773[i2];
                if(flag1)
                    l2 += 0x10000;
                model = (Model) mruNodes1.get(l2);
                if(model == null)
                {
                    model = Model.method462(l2 & 0xffff);
                    if(model == null)
                        return null;
                    if(flag1)
                        model.method477();
                    mruNodes1.put(l2, model);
                }
                if(k1 > 1)
                    aModelArray741s[i2] = model;
            }

            if(k1 > 1)
                model = new Model(k1, aModelArray741s);
        } else
        {
            int i1 = -1;
            for(int j1 = 0; j1 < anIntArray776.length; j1++)
            {
                if(anIntArray776[j1] != j)
                    continue;
                i1 = j1;
                break;
            }

            if(i1 == -1)
                return null;
            l1 = (long)((type << 6) + (i1 << 3) + l) + ((long)(k + 1) << 32);
            Model model_2 = (Model) mruNodes2.get(l1);
            if(model_2 != null)
                return model_2;
            int j2 = anIntArray773[i1];
            boolean flag3 = aBoolean751 ^ (l > 3);
            if(flag3)
                j2 += 0x10000;
            model = (Model) mruNodes1.get(j2);
            if(model == null)
            {
                model = Model.method462(j2 & 0xffff);
                if(model == null)
                    return null;
                if(flag3)
                    model.method477();
                mruNodes1.put(j2, model);
            }
        }
        boolean flag;
        flag = anInt748 != 128 || anInt772 != 128 || anInt740 != 128;
        boolean flag2;
        flag2 = anInt738 != 0 || anInt745 != 0 || anInt783 != 0;
        Model model_3 = new Model(modifiedModelColors == null, Frame.isInvalid(k), l == 0 && k == -1 && !flag && !flag2, model);
        if(k != -1)
        {
            model_3.method469();
            model_3.method470(k);
            model_3.anIntArrayArray1658 = null;
            model_3.anIntArrayArray1657 = null;
        }
        while(l-- > 0) 
            model_3.method473();
        if(modifiedModelColors != null)
        {
            for(int k2 = 0; k2 < modifiedModelColors.length; k2++)
                model_3.method476(modifiedModelColors[k2], originalModelColors[k2]);

        }
        if(flag)
            model_3.method478(anInt748, anInt740, anInt772);
        if(flag2)
            model_3.method475(anInt738, anInt745, anInt783);
        model_3.method479(64 + aByte737, 768 + aByte742 * 5, -50, -10, -50, !aBoolean769);
        if(anInt760 == 1)
            model_3.anInt1654 = model_3.modelHeight;
        mruNodes2.put(l1, model_3);
        return model_3;
    }

    private void readValues(Buffer buffer)
    {
        int i = -1;
label0:
        do
        {
            int j;
            do
            {
                j = buffer.readUByte();
                if(j == 0)
                    break label0;
                if(j == 1)
                {
                    int k = buffer.readUByte();
                    if(k > 0)
                        if(anIntArray773 == null || lowMem)
                        {
                            anIntArray776 = new int[k];
                            anIntArray773 = new int[k];
                            for(int k1 = 0; k1 < k; k1++)
                            {
                                anIntArray773[k1] = buffer.readUShort();
                                anIntArray776[k1] = buffer.readUByte();
                            }

                        } else
                        {
                            buffer.position += k * 3;
                        }
                } else
                if(j == 2)
                    name = buffer.readString();
                else
                if(j == 3)
                    description = buffer.readStringBytes();
                else
                if(j == 5)
                {
                    int l = buffer.readUByte();
                    if(l > 0)
                        if(anIntArray773 == null || lowMem)
                        {
                            anIntArray776 = null;
                            anIntArray773 = new int[l];
                            for(int l1 = 0; l1 < l; l1++)
                                anIntArray773[l1] = buffer.readUShort();

                        } else
                        {
                            buffer.position += l * 2;
                        }
                } else
                if(j == 14)
                    anInt744 = buffer.readUByte();
                else
                if(j == 15)
                    anInt761 = buffer.readUByte();
                else
                if(j == 17)
                    aBoolean767 = false;
                else
                if(j == 18)
                    aBoolean757 = false;
                else
                if(j == 19)
                {
                    i = buffer.readUByte();
                    if(i == 1)
                        hasActions = true;
                } else
                if(j == 21)
                    aBoolean762 = true;
                else
                if(j == 22)
                    aBoolean769 = true;
                else
                if(j == 23)
                    aBoolean764 = true;
                else
                if(j == 24)
                {
                    anInt781 = buffer.readUShort();
                    if(anInt781 == 65535)
                        anInt781 = -1;
                } else
                if(j == 28)
                    anInt775 = buffer.readUByte();
                else
                if(j == 29)
                    aByte737 = buffer.readByte();
                else
                if(j == 39)
                    aByte742 = buffer.readByte();
                else
                if(j >= 30 && j < 39)
                {
                    if(actions == null)
                        actions = new String[5];
                    actions[j - 30] = buffer.readString();
                    if(actions[j - 30].equalsIgnoreCase("hidden"))
                        actions[j - 30] = null;
                } else
                if(j == 40)
                {
                    int i1 = buffer.readUByte();
                    modifiedModelColors = new int[i1];
                    originalModelColors = new int[i1];
                    for(int i2 = 0; i2 < i1; i2++)
                    {
                        modifiedModelColors[i2] = buffer.readUShort();
                        originalModelColors[i2] = buffer.readUShort();
                    }

                } else
                if(j == 60)
                    anInt746 = buffer.readUShort();
                else
                if(j == 62)
                    aBoolean751 = true;
                else
                if(j == 64)
                    aBoolean779 = false;
                else
                if(j == 65)
                    anInt748 = buffer.readUShort();
                else
                if(j == 66)
                    anInt772 = buffer.readUShort();
                else
                if(j == 67)
                    anInt740 = buffer.readUShort();
                else
                if(j == 68)
                    anInt758 = buffer.readUShort();
                else
                if(j == 69)
                    anInt768 = buffer.readUByte();
                else
                if(j == 70)
                    anInt738 = buffer.readShort();
                else
                if(j == 71)
                    anInt745 = buffer.readShort();
                else
                if(j == 72)
                    anInt783 = buffer.readShort();
                else
                if(j == 73)
                    aBoolean736 = true;
                else
                if(j == 74)
                {
                    aBoolean766 = true;
                } else
                {
                    if(j != 75)
                        continue;
                    anInt760 = buffer.readUByte();
                }
                continue label0;
            } while(j != 77);
            anInt774 = buffer.readUShort();
            if(anInt774 == 65535)
                anInt774 = -1;
            anInt749 = buffer.readUShort();
            if(anInt749 == 65535)
                anInt749 = -1;
            int j1 = buffer.readUByte();
            childrenIDs = new int[j1 + 1];
            for(int j2 = 0; j2 <= j1; j2++)
            {
                childrenIDs[j2] = buffer.readUShort();
                if(childrenIDs[j2] == 65535)
                    childrenIDs[j2] = -1;
            }

        } while(true);
        if(i == -1)
        {
            hasActions = anIntArray773 != null && (anIntArray776 == null || anIntArray776[0] == 10);
            if(actions != null)
                hasActions = true;
        }
        if(aBoolean766)
        {
            aBoolean767 = false;
            aBoolean757 = false;
        }
        if(anInt760 == -1)
            anInt760 = aBoolean767 ? 1 : 0;
    }

    private ObjectDef()
    {
        type = -1;
    }

    public boolean aBoolean736;
    private byte aByte737;
    private int anInt738;
    public String name;
    private int anInt740;
    private static final Model[] aModelArray741s = new Model[4];
    private byte aByte742;
    public int anInt744;
    private int anInt745;
    public int anInt746;
    private int[] originalModelColors;
    private int anInt748;
    public int anInt749;
    private boolean aBoolean751;
    public static boolean lowMem;
    private static Buffer buffer;
    public int type;
    private static int[] streamIndices;
    public boolean aBoolean757;
    public int anInt758;
    public int childrenIDs[];
    private int anInt760;
    public int anInt761;
    public boolean aBoolean762;
    public boolean aBoolean764;
    public static Client clientInstance;
    private boolean aBoolean766;
    public boolean aBoolean767;
    public int anInt768;
    private boolean aBoolean769;
    private static int cacheIndex;
    private int anInt772;
    private int[] anIntArray773;
    public int anInt774;
    public int anInt775;
    private int[] anIntArray776;
    public byte description[];
    public boolean hasActions;
    public boolean aBoolean779;
    public static Cache mruNodes2 = new Cache(30);
    public int anInt781;
    private static ObjectDef[] cache;
    private int anInt783;
    private int[] modifiedModelColors;
    public static Cache mruNodes1 = new Cache(500);
    public String actions[];

}
