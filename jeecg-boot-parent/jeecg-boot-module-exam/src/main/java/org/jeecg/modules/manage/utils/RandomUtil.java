package org.jeecg.modules.manage.utils;

import java.util.ArrayList;
import java.util.List;

public class RandomUtil {
    public static List<Integer> randomNum(int Num, int LimitNum) {
        List<Integer> l = new ArrayList<Integer>();
        int i = 0;
        while (l.size() < Num) {
            i = (int) (Math.random() * LimitNum - 0 + 1) + 0;
            if (!l.contains(i))
                l.add(i);
        }
        for (int j : l)
            System.out.println(j);
        return l;
    }
}
