package ge.utils.problem.object;

import ge.utils.bundle.ArgumentResource;
import ge.utils.bundle.Resources;
import ge.utils.problem.enums.ProblemType;

import javax.swing.Icon;
import java.util.List;

public class Problem extends ArgumentResource implements Comparable<Problem>
{
    private static final Resources resources =
            Resources.getInstance( "ge.utils.resources" );

    private ProblemType problemType;

    public Problem( Resources resources, ProblemType problemType, String... resourceName )
    {
        super( resources, resourceName );
        this.problemType = problemType;
    }

    public Problem( Resources resources, Class<? extends Object> clazz,
                    ProblemType problemType, String... resourceName )
    {
        super( resources, clazz, resourceName );
        this.problemType = problemType;
    }

    public ProblemType getProblemType()
    {
        return problemType;
    }

    public void setProblemType( ProblemType problemType )
    {
        this.problemType = problemType;
    }

    public Icon getResourceIcon()
    {
        Icon icon;

        if ( problemType != null )
        {
            if ( problemType == ProblemType.ERROR )
            {
                icon = resources.getResourceIcon( Problem.class, "icon", "error" );
            }
            else if ( problemType == ProblemType.WARNING )
            {
                icon = resources.getResourceIcon( Problem.class, "icon", "warn" );
            }
            else
            {
                icon = resources.getResourceIcon( Problem.class, "icon", "info" );
            }
        }
        else
        {
            icon = resources.getResourceIcon( Problem.class, "icon", "info" );
        }

        return icon;
    }

    @Override
    public int compareTo( Problem other )
    {
        return problemType.compareTo( other.getProblemType() );
    }

    public static ProblemType discoverHighest( List<Problem> problems )
    {
        ProblemType retVal = ProblemType.NONE;

        if ( ( problems != null ) && ( problems.size() != 0 ) )
        {
            for ( Problem problem : problems )
            {
                ProblemType problemType = problem.getProblemType();

                if ( problemType != null )
                {
                    if ( problemType == ProblemType.ERROR )
                    {
                        retVal = ProblemType.ERROR;
                    }
                    else if ( ( problemType == ProblemType.WARNING ) && ( retVal != ProblemType.ERROR ) )
                    {
                        retVal = ProblemType.WARNING;
                    }
                    else if ( ( problemType == ProblemType.INFORMATION ) && ( retVal != ProblemType.ERROR ) &&
                            ( retVal != ProblemType.WARNING ) )
                    {
                        retVal = ProblemType.INFORMATION;
                    }
                }
            }
        }

        return retVal;
    }
}
