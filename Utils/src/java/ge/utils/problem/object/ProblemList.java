package ge.utils.problem.object;

import ge.utils.problem.enums.ProblemType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/02/13
 * Time: 11:19
 */
public class ProblemList extends ArrayList<Problem>
{
    public ProblemList()
    {
    }

    public ProblemList( Collection<? extends Problem> c )
    {
        super( c );
    }

    public ProblemList( int initialCapacity )
    {
        super( initialCapacity );
    }

    public ProblemType getHighestProblemType()
    {
        Problem retVal = null;

        for ( Problem problem : this )
        {
            if ( retVal == null )
            {
                retVal = problem;
            }
            else if ( retVal.compareTo( problem ) > 0 )
            {
                retVal = problem;
            }
        }

        if ( retVal == null )
        {
            return ProblemType.NONE;
        }
        else
        {
            return retVal.getProblemType();
        }
    }

    public void sort()
    {
        Collections.sort( this );
    }
}
