package com.epam.transavia.demo.tests.features.api.bo;

public class History {

    private ChangeStatus change_status;
    private String committed_at;
    private User user;
    private String url;
    private String version;

    public ChangeStatus getChange_status ()
    {
        return change_status;
    }

    public void setChange_status (ChangeStatus change_status)
    {
        this.change_status = change_status;
    }

    public String getCommitted_at ()
    {
        return committed_at;
    }

    public void setCommitted_at (String committed_at)
    {
        this.committed_at = committed_at;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "History [change_status = "+change_status+", committed_at = "+committed_at+", user = "+user+", url = "+url+", version = "+version+"]";
    }
}
