package com.hana053.micropost.ui.pages.relateduserlist;

import com.hana053.micropost.domain.RelatedUser;

import lombok.Value;

@Value
public class RelatedUserItemViewModel {

    public final RelatedUser user;
    public final boolean isMyself;

}
