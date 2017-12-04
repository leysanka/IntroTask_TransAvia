package com.epam.transavia.demo.tests.features.api.bo;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Gist {

    private Map<String, Map<String, String>> files;
    private String commits_url;
    private String truncated;
    private String git_push_url;
    private String url;
    private History[] history;
    private String html_url;
    private String id;
    private Forks[] forks;
    private String updated_at;
    private String description;
    private String comments_url;
    private String forks_url;
    private Owner owner;
    private String created_at;
    @SerializedName("public")
    private boolean isPublic;
    private String user;
    private String git_pull_url;
    private String comments;

    public Map<String, Map<String, String>> getFiles() {
        return files;
    }

    public void setFiles(Map<String, Map<String, String>> files) {
        this.files = files;
    }

    public String getCommits_url() {
        return commits_url;
    }

    public void setCommits_url(String commits_url) {
        this.commits_url = commits_url;
    }

    public String getTruncated() {
        return truncated;
    }

    public void setTruncated(String truncated) {
        this.truncated = truncated;
    }

    public String getGit_push_url() {
        return git_push_url;
    }

    public void setGit_push_url(String git_push_url) {
        this.git_push_url = git_push_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public History[] getHistory() {
        return history;
    }

    public void setHistory(History[] history) {
        this.history = history;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Forks[] getForks() {
        return forks;
    }

    public void setForks(Forks[] forks) {
        this.forks = forks;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public String getForks_url() {
        return forks_url;
    }

    public void setForks_url(String forks_url) {
        this.forks_url = forks_url;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGit_pull_url() {
        return git_pull_url;
    }

    public void setGit_pull_url(String git_pull_url) {
        this.git_pull_url = git_pull_url;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "FullGist [files = " + files + ", \ncommits_url = " + commits_url + ", \ntruncated = " + truncated + ", "
               + "\ngit_push_url = " + git_push_url + ", \nurl = " + url + ", \nhistory = " + history + ", \nhtml_url = " + html_url + ","
               + "\nid = " + id + ", \nforks = " + forks + ", \nupdated_at = " + updated_at + ", \ndescription = " + description + ", "
               + "\ncomments_url = " + comments_url + ", \nforks_url = " + forks_url + ", \nowner = " + owner + ", \ncreated_at = " + created_at + ", "
               + "\npublic = " + isPublic + ", \nuser = " + user + ", \ngit_pull_url = " + git_pull_url + ", \ncomments = " + comments + "]";
    }
}
