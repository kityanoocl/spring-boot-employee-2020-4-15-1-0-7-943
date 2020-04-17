package com.thoughtworks.springbootemployee.commonUtil;

import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class CommonUtil {
    public static <E> List<E> returnListInPage(List<E> list, Integer page, Integer pageSize) {
        int listSize = list.size();
        int startIndex = min(listSize, (page - 1) * pageSize);
        startIndex = max(0, startIndex);
        int endIndex = max(1, page * pageSize);
        endIndex = min(endIndex, listSize);
        return list.subList(startIndex, endIndex);
    }
}
