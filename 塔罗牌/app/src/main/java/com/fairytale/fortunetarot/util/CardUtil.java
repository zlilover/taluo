package com.fairytale.fortunetarot.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.fairytale.fortunetarot.comm.App;
import com.fairytale.fortunetarot.entity.ArcanaCardGroup;
import com.fairytale.fortunetarot.entity.ArcanaCards;
import com.fairytale.fortunetarot.entity.BaseConfigEntity;
import com.fairytale.fortunetarot.entity.DivinationItemEntity;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    public String[] getCards() {
        return cards;
    }

    public static ArrayList<ArcanaCards> getCardsInfo(Context context,String path) {
        String cardsInfoJson = SPUtil.get(context,"cardinfos","").toString();
        if (!TextUtils.isEmpty(cardsInfoJson)) {
            Type type = new TypeToken<ArrayList<ArcanaCards>>(){}.getType();
            return JsonUtils.jsonStringToEntity(cardsInfoJson,type);
        }
        else {
            ArrayList<ArcanaCards> cardsList = new ArrayList<>();
            String infoText = Util.getStringfromAssets(context,path);

            if (infoText == null) {
                return null;
            }
            String result = infoText.replace("@",",");
            String[] cards = result.split(",");
            for (String card: cards) {
                String[] cardInfo = card.split("#");
                if (cardInfo.length < 5) {
                    // 文档有误
                    return null;
                }
                ArcanaCards arcanaCards = new ArcanaCards();
                arcanaCards.setName_for_icon(cardInfo[0]);
                arcanaCards.setName(cardInfo[1]);
                arcanaCards.setName_eng(cardInfo[2]);
                arcanaCards.setRoma_num(cardInfo[3]);
                arcanaCards.setConstellation(cardInfo[4]);
                cardsList.add(arcanaCards);
            }
            SPUtil.put(context,"cardinfo",JsonUtils.entityToJsonString(cardsList));
            return cardsList;
        }
    }

    public static ArrayList<ArcanaCardGroup> getCardsInfoByType(Context context, String path) {
        String groupInfoJson = SPUtil.get(context,"groupinfos","").toString();
        if (!TextUtils.isEmpty(groupInfoJson)) {
            Type type = new TypeToken<ArrayList<ArcanaCardGroup>>(){}.getType();
            return JsonUtils.jsonStringToEntity(groupInfoJson,type);
        } else {
            ArrayList<ArcanaCards> cardsList = getCardsInfo(context,path);
            if (cardsList == null) {
                return null;
            }
            ArrayList<ArcanaCardGroup> groups = new ArrayList<>();

            // 天加大阿卡拉牌
            ArcanaCardGroup bigArcanaGroup = new ArcanaCardGroup();
            bigArcanaGroup.setTypeName("大阿卡拉牌");
            ArrayList<ArcanaCards> big_arcana = new ArrayList<>();
            for(int i = 0; i < 22;i++) {
                big_arcana.add(cardsList.get(i));
            }
            bigArcanaGroup.setCards(big_arcana);
            groups.add(bigArcanaGroup);

            int matchCount = 0;
            for (int i = 22; i < cardsList.size();i += matchCount) {
                String tagName = cardsList.get(i).getName().substring(0,2);
                matchCount = 0;
                for (int j = i;j < cardsList.size();j++ ) {
                    if (cardsList.get(j).getName().substring(0,2).equals(tagName)) {
                        matchCount++;
                        if (j != cardsList.size() - 1) {
                            continue;
                        }
                    }
                    if (matchCount != 0) {
                        ArcanaCardGroup arcanaCardGroup = new ArcanaCardGroup();
                        arcanaCardGroup.setTypeName(tagName);
                        arcanaCardGroup.getCards().addAll(cardsList.subList(i,i + matchCount));
                        groups.add(arcanaCardGroup);
                        break;
                    }
                }
            }
            SPUtil.put(context,"groupinfos",JsonUtils.entityToJsonString(groups));
            return groups;
        }

    }

    public static ArrayList<DivinationItemEntity> getDivinationItemEntityFromAssets(Context context) {
        String content = SPUtil.get(context,"DivinationItemEntityList","").toString();
        if (!TextUtils.isEmpty(content)) {
            Type contentType = new TypeToken<ArrayList<DivinationItemEntity>>(){}.getType();
            return JsonUtils.jsonStringToEntity(content,contentType);
        } else {
            ArrayList<DivinationItemEntity> divinationItemEntities = new ArrayList<>();
            String entityContent = Util.getStringfromAssets(context,"cardarrayinfo/info.txt");
            String[] entities = entityContent.split("@");
            for (int i = 0 ; i < entities.length; i++) {
                String groupId = entities[i].split("#")[0];
                String groupName = entities[i].split("#")[1];
                String textContent = Util.getStringfromAssets(context,"cardarrayinfo/" + groupId + ".txt");
                String[] cardinfos = textContent.split("@");
                for (int j = 0; j < cardinfos.length; j++) {
                    DivinationItemEntity entity = new DivinationItemEntity();
                    entity.setGroupName(groupName);
                    entity.setGroupId(groupId);
                    entity.setTitle(cardinfos[j].split("#")[0]);
                    entity.setInfoId(cardinfos[j].split("#")[1]);   // 为了方便此处直接用"XXX.txt"作为id供后续使用
                    divinationItemEntities.add(entity);
                }
            }
            SPUtil.put(context,JsonUtils.entityToJsonString(divinationItemEntities),"");
            return divinationItemEntities;
        }
    }
}
