package ge.utils.layout;

import javax.swing.GroupLayout;
import java.awt.Component;
import java.awt.Container;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 22/05/13
 * Time: 15:13
 */
public class LabelFieldPairGroupLayout extends GroupLayout
{
    private GroupLayout.ParallelGroup labelGroup;

    private GroupLayout.ParallelGroup fieldGroup;

    private GroupLayout.Group verticalGroup;

    public LabelFieldPairGroupLayout( Container host )
    {
        super( host );

        host.setLayout( this );

        GroupLayout.SequentialGroup horizontalGroup = createSequentialGroup();

        labelGroup = createParallelGroup();
        fieldGroup = createParallelGroup();

        verticalGroup = createSequentialGroup();

        horizontalGroup.addGroup( labelGroup );
        horizontalGroup.addGroup( fieldGroup );

        setHorizontalGroup( horizontalGroup );
        setVerticalGroup( verticalGroup );
    }

    public void addLabelFieldPair( Component label, Component field )
    {
        labelGroup.addComponent( label, GroupLayout.Alignment.TRAILING );
        fieldGroup.addComponent( field );

        ParallelGroup parallelGroup = createParallelGroup();

        parallelGroup.addComponent( label );
        parallelGroup.addComponent( field );

        verticalGroup.addGroup( parallelGroup );
    }
}
