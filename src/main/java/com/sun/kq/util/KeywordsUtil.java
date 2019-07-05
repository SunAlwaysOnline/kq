package com.sun.kq.util;

import com.sun.kq.entity.Keyword;

import java.util.HashMap;
import java.util.List;

public class KeywordsUtil {
    public static String[] keywords = new String[]{"电瓶车", "二手车", "二手电瓶车"};

    /**
     * 将列表格式的关键字组成字符串格式
     *
     * @param keywordList
     * @return
     */
    public static String getWordStringFromList(List<String> keywordList) {
        StringBuilder sb = new StringBuilder();
        if (keywordList.size() == 0) {
            sb.append("暂未拥有任何关键词，快去增加关键词吧!" + "\n");
            sb.append("输入 增加关键词+关键词 即可" + "\n");
            sb.append("例如 增加关键词+二手车");
            return sb.toString();
        }
        sb.append("目前已有关键词列表如下:" + "\n");
        for (int i = 0; i < keywordList.size(); i++) {
            if (i != keywordList.size() - 1) {
                sb.append(keywordList.get(i) + "\n");
            } else {
                sb.append(keywordList.get(i));
            }
        }
        return sb.toString();
    }

}
