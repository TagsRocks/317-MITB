package com.jagex.entity;
// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.jagex.Client;
import com.jagex.cache.anim.Animation;
import com.jagex.cache.anim.Frame;
import com.jagex.cache.anim.Graphic;
import com.jagex.cache.def.EntityDef;
import com.jagex.cache.def.ItemDef;
import com.jagex.entity.model.IDK;
import com.jagex.entity.model.Model;
import com.jagex.io.Buffer;
import com.jagex.link.Cache;
import com.jagex.util.TextClass;

public final class Player extends Entity
{

    public Model getRotatedModel()
    {
        if(!visible)
            return null;
        Model model = method452();
        if(model == null)
            return null;
        super.height = model.modelHeight;
        model.aBoolean1659 = true;
        if(aBoolean1699)
            return model;
        if(super.anInt1520 != -1 && super.anInt1521 != -1)
        {
            Graphic graphic = Graphic.graphics[super.anInt1520];
            Model model_2 = SpotAnimModel.getModel(graphic);
            if(model_2 != null)
            {
                Model model_3 = new Model(true, Frame.isInvalid(super.anInt1521), false, model_2);
                model_3.method475(0, -super.anInt1524, 0);
                model_3.method469();
                model_3.method470(graphic.animation.anIntArray353[super.anInt1521]);
                model_3.anIntArrayArray1658 = null;
                model_3.anIntArrayArray1657 = null;
                if(graphic.breadthScale != 128 || graphic.depthScale != 128)
                    model_3.method478(graphic.breadthScale, graphic.breadthScale, graphic.depthScale);
                model_3.method479(64 + graphic.ambience, 850 + graphic.modelShadow, -30, -50, -30, true);
                Model aclass30_sub2_sub4_sub6_1s[] = {
                        model, model_3
                };
                model = new Model(aclass30_sub2_sub4_sub6_1s);
            }
        }
        if(aModel_1714 != null)
        {
            if(Client.loopCycle >= anInt1708)
                aModel_1714 = null;
            if(Client.loopCycle >= anInt1707 && Client.loopCycle < anInt1708)
            {
                Model model_1 = aModel_1714;
                model_1.method475(anInt1711 - super.x, anInt1712 - anInt1709, anInt1713 - super.y);
                if(super.turnDirection == 512)
                {
                    model_1.method473();
                    model_1.method473();
                    model_1.method473();
                } else
                if(super.turnDirection == 1024)
                {
                    model_1.method473();
                    model_1.method473();
                } else
                if(super.turnDirection == 1536)
                    model_1.method473();
                Model aclass30_sub2_sub4_sub6s[] = {
                        model, model_1
                };
                model = new Model(aclass30_sub2_sub4_sub6s);
                if(super.turnDirection == 512)
                    model_1.method473();
                else
                if(super.turnDirection == 1024)
                {
                    model_1.method473();
                    model_1.method473();
                } else
                if(super.turnDirection == 1536)
                {
                    model_1.method473();
                    model_1.method473();
                    model_1.method473();
                }
                model_1.method475(super.x - anInt1711, anInt1709 - anInt1712, super.y - anInt1713);
            }
        }
        model.aBoolean1659 = true;
        return model;
    }

    public void updatePlayer(Buffer buffer)
    {
        buffer.position = 0;
        anInt1702 = buffer.readUByte();
        headIcon = buffer.readUByte();
        desc = null;
        team = 0;
        for(int j = 0; j < 12; j++)
        {
            int k = buffer.readUByte();
            if(k == 0)
            {
                equipment[j] = 0;
                continue;
            }
            int i1 = buffer.readUByte();
            equipment[j] = (k << 8) + i1;
            if(j == 0 && equipment[0] == 65535)
            {
                desc = EntityDef.forID(buffer.readUShort());
                break;
            }
            if(equipment[j] >= 512 && equipment[j] - 512 < ItemDef.totalItems)
            {
                int l1 = ItemDef.forID(equipment[j] - 512).team;
                if(l1 != 0)
                    team = l1;
            }
        }

        for(int l = 0; l < 5; l++)
        {
            int j1 = buffer.readUByte();
            if(j1 < 0 || j1 >= Client.anIntArrayArray1003[l].length)
                j1 = 0;
            anIntArray1700[l] = j1;
        }

        super.anInt1511 = buffer.readUShort();
        if(super.anInt1511 == 65535)
            super.anInt1511 = -1;
        super.anInt1512 = buffer.readUShort();
        if(super.anInt1512 == 65535)
            super.anInt1512 = -1;
        super.anInt1554 = buffer.readUShort();
        if(super.anInt1554 == 65535)
            super.anInt1554 = -1;
        super.anInt1555 = buffer.readUShort();
        if(super.anInt1555 == 65535)
            super.anInt1555 = -1;
        super.anInt1556 = buffer.readUShort();
        if(super.anInt1556 == 65535)
            super.anInt1556 = -1;
        super.anInt1557 = buffer.readUShort();
        if(super.anInt1557 == 65535)
            super.anInt1557 = -1;
        super.anInt1505 = buffer.readUShort();
        if(super.anInt1505 == 65535)
            super.anInt1505 = -1;
        name = TextClass.fixName(TextClass.nameForLong(buffer.readLong()));
        combatLevel = buffer.readUByte();
        skill = buffer.readUShort();
        visible = true;
        aLong1718 = 0L;
        for(int k1 = 0; k1 < 12; k1++)
        {
            aLong1718 <<= 4;
            if(equipment[k1] >= 256)
                aLong1718 += equipment[k1] - 256;
        }

        if(equipment[0] >= 256)
            aLong1718 += equipment[0] - 256 >> 4;
        if(equipment[1] >= 256)
            aLong1718 += equipment[1] - 256 >> 8;
        for(int i2 = 0; i2 < 5; i2++)
        {
            aLong1718 <<= 3;
            aLong1718 += anIntArray1700[i2];
        }

        aLong1718 <<= 1;
        aLong1718 += anInt1702;
    }

    private Model method452()
    {
        if(desc != null)
        {
            int j = -1;
            if(super.anim >= 0 && super.anInt1529 == 0)
                j = Animation.animations[super.anim].anIntArray353[super.anInt1527];
            else
            if(super.anInt1517 >= 0)
                j = Animation.animations[super.anInt1517].anIntArray353[super.anInt1518];
            Model model = desc.method164(-1, j, null);
            return model;
        }
        long l = aLong1718;
        int k = -1;
        int i1 = -1;
        int j1 = -1;
        int k1 = -1;
        if(super.anim >= 0 && super.anInt1529 == 0)
        {
            Animation animation = Animation.animations[super.anim];
            k = animation.anIntArray353[super.anInt1527];
            if(super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511)
                i1 = Animation.animations[super.anInt1517].anIntArray353[super.anInt1518];
            if(animation.anInt360 >= 0)
            {
                j1 = animation.anInt360;
                l += j1 - equipment[5] << 40;
            }
            if(animation.anInt361 >= 0)
            {
                k1 = animation.anInt361;
                l += k1 - equipment[3] << 48;
            }
        } else
        if(super.anInt1517 >= 0)
            k = Animation.animations[super.anInt1517].anIntArray353[super.anInt1518];
        Model model_1 = (Model) mruNodes.get(l);
        if(model_1 == null)
        {
            boolean flag = false;
            for(int i2 = 0; i2 < 12; i2++)
            {
                int k2 = equipment[i2];
                if(k1 >= 0 && i2 == 3)
                    k2 = k1;
                if(j1 >= 0 && i2 == 5)
                    k2 = j1;
                if(k2 >= 256 && k2 < 512 && !IDK.cache[k2 - 256].method537())
                    flag = true;
                if(k2 >= 512 && !ItemDef.forID(k2 - 512).method195(anInt1702))
                    flag = true;
            }

            if(flag)
            {
                if(aLong1697 != -1L)
                    model_1 = (Model) mruNodes.get(aLong1697);
                if(model_1 == null)
                    return null;
            }
        }
        if(model_1 == null)
        {
            Model aclass30_sub2_sub4_sub6s[] = new Model[12];
            int j2 = 0;
            for(int l2 = 0; l2 < 12; l2++)
            {
                int i3 = equipment[l2];
                if(k1 >= 0 && l2 == 3)
                    i3 = k1;
                if(j1 >= 0 && l2 == 5)
                    i3 = j1;
                if(i3 >= 256 && i3 < 512)
                {
                    Model model_3 = IDK.cache[i3 - 256].method538();
                    if(model_3 != null)
                        aclass30_sub2_sub4_sub6s[j2++] = model_3;
                }
                if(i3 >= 512)
                {
                    Model model_4 = ItemDef.forID(i3 - 512).method196(anInt1702);
                    if(model_4 != null)
                        aclass30_sub2_sub4_sub6s[j2++] = model_4;
                }
            }

            model_1 = new Model(j2, aclass30_sub2_sub4_sub6s);
            for(int j3 = 0; j3 < 5; j3++)
                if(anIntArray1700[j3] != 0)
                {
                    model_1.method476(Client.anIntArrayArray1003[j3][0], Client.anIntArrayArray1003[j3][anIntArray1700[j3]]);
                    if(j3 == 1)
                        model_1.method476(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j3]]);
                }

            model_1.method469();
            model_1.method479(64, 850, -30, -50, -30, true);
            mruNodes.put(l, model_1);
            aLong1697 = l;
        }
        if(aBoolean1699)
            return model_1;
        Model model_2 = Model.aModel_1621;
        model_2.method464(model_1, Frame.isInvalid(k) & Frame.isInvalid(i1));
        if(k != -1 && i1 != -1)
            model_2.method471(Animation.animations[super.anim].anIntArray357, i1, k);
        else
        if(k != -1)
            model_2.method470(k);
        model_2.method466();
        model_2.anIntArrayArray1658 = null;
        model_2.anIntArrayArray1657 = null;
        return model_2;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public int privelage;
	public Model method453()
    {
        if(!visible)
            return null;
        if(desc != null)
            return desc.method160();
        boolean flag = false;
        for(int i = 0; i < 12; i++)
        {
            int j = equipment[i];
            if(j >= 256 && j < 512 && !IDK.cache[j - 256].method539())
                flag = true;
            if(j >= 512 && !ItemDef.forID(j - 512).method192(anInt1702))
                flag = true;
        }

        if(flag)
            return null;
        Model aclass30_sub2_sub4_sub6s[] = new Model[12];
        int k = 0;
        for(int l = 0; l < 12; l++)
        {
            int i1 = equipment[l];
            if(i1 >= 256 && i1 < 512)
            {
                Model model_1 = IDK.cache[i1 - 256].method540();
                if(model_1 != null)
                    aclass30_sub2_sub4_sub6s[k++] = model_1;
            }
            if(i1 >= 512)
            {
                Model model_2 = ItemDef.forID(i1 - 512).method194(anInt1702);
                if(model_2 != null)
                    aclass30_sub2_sub4_sub6s[k++] = model_2;
            }
        }

        Model model = new Model(k, aclass30_sub2_sub4_sub6s);
        for(int j1 = 0; j1 < 5; j1++)
            if(anIntArray1700[j1] != 0)
            {
                model.method476(Client.anIntArrayArray1003[j1][0], Client.anIntArrayArray1003[j1][anIntArray1700[j1]]);
                if(j1 == 1)
                    model.method476(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j1]]);
            }

        return model;
    }

    public Player()
    {
        aLong1697 = -1L;
        aBoolean1699 = false;
        anIntArray1700 = new int[5];
        visible = false;
        anInt1715 = 9;
        equipment = new int[12];
    }

    private long aLong1697;
    public EntityDef desc;
    public boolean aBoolean1699;
    public final int[] anIntArray1700;
    public int team;
    private int anInt1702;
    public String name;
    public static Cache mruNodes = new Cache(260);
    public int combatLevel;
    public int headIcon;
    public int anInt1707;
    public int anInt1708;
    public int anInt1709;
    public boolean visible;
    public int anInt1711;
    public int anInt1712;
    public int anInt1713;
    public Model aModel_1714;
    private int anInt1715;
    public final int[] equipment;
    private long aLong1718;
    public int anInt1719;
    public int anInt1720;
    public int anInt1721;
    public int anInt1722;
    public int skill;

}
