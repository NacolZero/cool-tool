package org.nacol.cooltool.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.ExampleMatcher;

public enum MatchFieldEnum {

    CONTAIS("contains", matcher -> matcher.contains()),
    START_WITH("startsWith", matcher -> matcher.startsWith()),
    END_WITH("endsWith", matcher -> matcher.endsWith()),
    IGNORE_CASE("ignoreCase", matcher -> matcher.ignoreCase()),
    EXACT("exact", matcher -> matcher.exact());


    private String fieldName;
    private ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher> matcherConfigurer;

    MatchFieldEnum(String fieldName, ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher> matcherConfigurer) {
        this.fieldName = fieldName;
        this.matcherConfigurer = matcherConfigurer;
    }

    public static ExampleMatcher.MatcherConfigurer<ExampleMatcher.GenericPropertyMatcher> getMatcherConfigurer(String fieldName){
        for (MatchFieldEnum mf : MatchFieldEnum.values()) {
            if (StringUtils.equals(mf.fieldName, fieldName)) {
                return mf.matcherConfigurer;
            }
        }
        //默认模糊匹配
        return matcher -> matcher.contains();
    }
}
