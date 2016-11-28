package com.hana053.micropost.presentation.core.components.followbtn;

public interface FollowBtnService {

    void onEvent(FollowBtnClickEvent event);

    void startObserving();

    void stopObserving();

}
