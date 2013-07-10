package ge.utils.problem.table;

import com.jidesoft.grid.JideTable;
import ge.utils.problem.object.Problem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/02/13
 * Time: 09:20
 */
public class ProblemTable extends JideTable
{
    public ProblemTable( List<Problem> problems )
    {
        super( new ProblemModel( problems ) );

        setDefaultRenderer( Problem.class, new ProblemRenderer() );
        setCellSelectionEnabled( false );
        setRowSelectionAllowed( false );
        setColumnSelectionAllowed( false );
        setShowGrid( false );

        setOpaque( false );

        setTableHeader( null );
    }
}
