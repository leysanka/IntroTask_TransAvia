package com.epam.transavia.demo.tests.features.api.bo;

public class Forks {

    private String id;
    private String updated_at;
    private String created_at;
    private User user;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "Forks [id = "+id+", updated_at = "+updated_at+", created_at = "+created_at+", user = "+user+", url = "+url+"]";
    }
}
