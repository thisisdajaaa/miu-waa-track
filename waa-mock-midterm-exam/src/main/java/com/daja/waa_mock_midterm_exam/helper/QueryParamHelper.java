package com.daja.waa_mock_midterm_exam.helper;

import java.util.HashMap;
import java.util.Map;

public class QueryParamHelper {
    public static Map<String, String> transformedFilter(String filter) {
        Map<String, String> filterMap = new HashMap<>();

        if (filter != null && !filter.isEmpty()) {
            String[] filters = filter.split(",");
            for (String f : filters) {
                String[] keyValue = f.split(":");
                if (keyValue.length == 2) filterMap.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        return filterMap;
    }
}
