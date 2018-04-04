package com.fairytale.fortunetarot.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.fairytale.fortunetarot.comm.App;

import java.util.Random;

/**
 * Created by lizhen on 2018/1/23.
 */

public class CardUtil {
    private String[] cards = new String[]{
            "TheFool#愚人", "TheMagician#魔术师", "TheHighPriestess#女祭祀", "TheEmpress#女皇", "TheEmperor#皇帝", "TheHierophant#教皇", "Thelovers#恋人",
            "TheChariot#战车", "Strength#力量", "TheHermit#隐士", "TheWheelofFortune#命运之轮", "Justice#正义", "TheHangedMan#倒吊人", "Death#死神", "Temperance#节制", "TheDevil#魔鬼", "TheTower#高塔",
            "TheStars#星星", "TheMoon#月亮", "TheSun#太阳", "Judgement#审判", "TheWorld#世界", "AceOfCups#圣杯王牌", "TwoOfCups#圣杯二", "ThreeOfCups#圣杯三", "FourOfCups#圣杯四",
            "FiveOfCups#圣杯五", "SixOfCups#圣杯六", "SevenOfCups#圣杯七", "EightOfCups#圣杯八", "NineOfCups#圣杯九", "TenOfCups#圣杯十", "PageOfCups#圣杯侍从", "KnightOfCups#圣杯骑士",
            "QueenOfCups#圣杯皇后", "KingOfCups#圣杯国王", "AceOfWands#权杖王牌", "TwoOfWands#权杖二", "ThreeOfWands#权杖三", "FourOfWands#权杖四", "FiveOfWands#权杖五", "SixOfWands#权杖六",
            "SevenOfWands#权杖七", "EightOfWands#权杖八", "NineOfWands#权杖九", "TenOfWands#权杖十", "PageOfWands#权杖侍从", "KnightOfWands#权杖骑士", "QueenOfWands#权杖皇后", "" +
            "KingOfWands#权杖国王", "AceOfPentacles#星币王牌", "TwoOfPentacles#星币二", "ThreeOfPentacles#星币三", "FourOfPentacles#星币四", "FiveOfPentacles#星币五", "SixOfPentacles#星币六",
            "SevenOfPentacles#星币七", "EightOfPentacles#星币八", "NineOfPentacles#星币九", "TenOfPentacles#星币十", "PageOfPentacles#星币侍从", "KnightOfPentacles#星币骑士",
            "QueenOfPentacles#星币皇后", "KingOfPentacles#星币国王", "AceOfSwords#宝剑王牌", "TwoOfSwords#宝剑二", "ThreeOfSwords#宝剑三", "FourOfSwords#宝剑四", "FiveOfSwords#宝剑五",
            "SixOfSwords#宝剑六", "SevenOfSwords#宝剑七", "EightOfSwords#宝剑八", "NineOfSwords#宝剑九", "TenOfSwords#宝剑十", "PageOfSwords#宝剑侍从", "KnightOfSwords#宝剑骑士",
            "QueenOfSwords#宝剑皇后", "KingOfSwords#宝剑国王"};


    public String[] getDailyCardInfo() {
        String[] cardInfo;

        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("DailyCard", Activity.MODE_PRIVATE);
        String savedCardInfo = sharedPreferences.getString("cardInfo", null);
        String date = sharedPreferences.getString("cardDate", null);

        if (savedCardInfo != null && DateUtil.GetNowDate().equals(date)) {
            cardInfo = savedCardInfo.split("\\|");
        } else {
            int pos = new Random().nextInt(78);
            String[] temple = cards[pos].split("#");
            String isReverse = new Random().nextBoolean() ? "Y" : "N";
            cardInfo = new String[3];
            String path;
            if (pos < 22) { // 大阿卡拉牌
                path = "majorarcanaimgs/" + temple[0] + ".jpg";
            } else {
                path = "imgs/" + temple[0] + ".jpg";
            }
            cardInfo[0] = path;
            cardInfo[1] = temple[1];
            cardInfo[2] = isReverse;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cardInfo", path + "|" + temple[1] + "|"+ isReverse);
            editor.putString("cardDate", DateUtil.GetNowDate());
            editor.commit();
        }
        return cardInfo;
    }


}
