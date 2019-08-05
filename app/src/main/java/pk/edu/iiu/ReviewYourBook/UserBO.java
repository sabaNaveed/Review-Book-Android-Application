package pk.edu.iiu.ReviewYourBook;

public class UserBO
{
    private long id,pages;
    private String name, author,r1,r2;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getauthor()
    {
        return author;
    }

    public void setauthor(String author)
    {
        this.author = author;
    }
    public long getpages()
    {
        return pages;
    }

    public void setpages(long pages)
    {
        this.pages = pages;
    }
    public String getr1()
    {
        return r1;
    }

    public void setr1(String r1)
    {
        this.r1 = r1;
    }
    public String getr2()
    {
        return r2;
    }

    public void setr2(String r2)
    {
        this.r2 = r2;
    }

    public String toString()
    {
        return  name ;
    }
}