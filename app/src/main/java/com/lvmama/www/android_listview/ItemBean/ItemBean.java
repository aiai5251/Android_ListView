package com.lvmama.www.android_listview.ItemBean;

/**
 * Created by shiyaorong on 15/12/28.
 */
public class ItemBean {

    public String ItemImageUrl;

    public int ItemImageResid;
    public String ItemTitle;
    public String ItemContent;

    public ItemBean(int itemImageResid, String itemTitle, String itemContent) {
        ItemImageResid = itemImageResid;
        ItemTitle = itemTitle;
        ItemContent = itemContent;
    }
}
