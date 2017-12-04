package com.epam.transavia.demo.tests.features.api.bo;

public class Change_status {

    private int total;

    private int additions;

    private int deletions;

    public int getTotal ()
    {
        return total;
    }

    public void setTotal (int total)
    {
        this.total = total;
    }

    public int getAdditions ()
    {
        return additions;
    }

    public void setAdditions (int additions)
    {
        this.additions = additions;
    }

    public int getDeletions ()
    {
        return deletions;
    }

    public void setDeletions (int deletions)
    {
        this.deletions = deletions;
    }

    @Override
    public String toString()
    {
        return "Change_status [total = "+total+", additions = "+additions+", deletions = "+deletions+"]";
    }
}
