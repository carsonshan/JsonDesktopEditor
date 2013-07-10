package ge.utils.properties.object;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 18/02/2013
 * Time: 21:20
 */
public abstract class PropertyDialogObject
{
    private String lastPage = null;

    public String getLastPage()
    {
        return lastPage;
    }

    public void setLastPage( String lastPage )
    {
        this.lastPage = lastPage;
    }
}
