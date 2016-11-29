package com.hana053.micropost.ui.components.followbtn;

public interface FollowBtnService {

    void onEvent(FollowBtnClickEvent event);

    void startObserving();

    void stopObserving();

}
