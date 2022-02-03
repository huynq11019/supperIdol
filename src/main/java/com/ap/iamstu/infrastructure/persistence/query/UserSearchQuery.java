package com.ap.iamstu.infrastructure.persistence.query;

import com.ap.iamstu.infrastructure.support.query.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UserSearchQuery extends PagingQuery {
    private String keyword;
//    private List<String> buildingIds;
//    private List<String> floorIds;
//    private List<String> organizationIds;
//
//    public OrganizationLocationSearchQuery(List<String> buildingIds, List<String> floorIds, List<String> organizationIds) {
//        this.buildingIds = buildingIds;
//        this.floorIds = floorIds;
//        this.organizationIds = organizationIds;
//    }

}
