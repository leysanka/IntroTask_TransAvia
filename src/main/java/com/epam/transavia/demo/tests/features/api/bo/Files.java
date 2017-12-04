package com.epam.transavia.demo.tests.features.api.bo;


public class Files {


  //  private Map<String, Map<String, String>> ;

    private MyFile file;

    public  MyFile getFile ()
    {
        return this.file;
    }

    public void setFile(MyFile file)
    {
        this.file = file;
    }

    @Override
    public String toString()
    {
        return "Files [className = "+file+"]";
    }

}
