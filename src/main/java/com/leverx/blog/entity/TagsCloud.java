package com.leverx.blog.entity;

public class TagsCloud {
    private String tagName;
    private int postCount;

    public TagsCloud(String tagName, int postCount) {
        this.tagName = tagName;
        this.postCount = postCount;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }
}
