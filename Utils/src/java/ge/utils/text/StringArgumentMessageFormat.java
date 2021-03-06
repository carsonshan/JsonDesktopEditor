package ge.utils.text;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class StringArgumentMessageFormat extends Format
{
    private static final long serialVersionUID = 6479157306784022952L;

    private static final int BUFSIZE = 255;

    /**
     * Locale region settings used for number and date formatting
     */
    private Locale locale = Locale.getDefault();

    /**
     * Left delimiter
     */
    private String ldel = "{"; // NOI18N

    /**
     * Right delimiter
     */
    private String rdel = "}"; // NOI18N

    /**
     * Used formatting map
     */
    private Map argmap;

    /**
     * Offsets to {} expressions
     */
    private int[] offsets;

    /**
     * Keys enclosed by {} brackets
     */
    private String[] arguments;

    /**
     * Max used offset
     */
    private int maxOffset;

    /**
     * Should be thrown an exception if key was not found?
     */
    private boolean throwex = false;

    /**
     * Exactly match brackets?
     */
    private boolean exactmatch = true;

    /**
     * Constructor.
     * For common work use  <code>format(pattern, arguments) </code>.
     *
     * @param arguments keys and values to use in the format
     */
    public StringArgumentMessageFormat( Map arguments )
    {
        super();
        setMap( arguments );
    }

    /**
     * Designated method. It gets the string, initializes HashFormat object
     * and returns converted string. It scans  <code>pattern</code>
     * for {} brackets, then parses enclosed string and replaces it
     * with argument's  <code>get()</code> value.
     *
     * @param pattern   String to be parsed.
     * @param arguments Map with key-value pairs to replace.
     *
     * @return Formatted string
     */
    public static String format( String pattern, Map arguments )
    {
        StringArgumentMessageFormat temp = new StringArgumentMessageFormat( arguments );

        return temp.format( pattern );
    }

    /**
     * Returns the value for given key. Subclass may define its own beahvior of
     * this method. For example, if key is not defined, subclass can return <not defined>
     * string.
     *
     * @param key Key.
     *
     * @return Value for this key.
     */
    protected Object processKey( String key )
    {
        return argmap.get( key );
    }

    /**
     * Scans the pattern and prepares internal variables.
     *
     * @param newPattern String to be parsed.
     *
     * @throws IllegalArgumentException if number of arguments exceeds BUFSIZE or
     *                                  parser found unmatched brackets (this exception should be switched off
     *                                  using setExactMatch(false)).
     */
    public String processPattern( String newPattern ) throws IllegalArgumentException
    {
        int idx = 0;
        int offnum = -1;
        StringBuffer outpat = new StringBuffer();
        offsets = new int[ BUFSIZE ];
        arguments = new String[ BUFSIZE ];
        maxOffset = -1;

        //skipped = new RangeList();
        // What was this for??
        //process(newPattern, "\"", "\""); // NOI18N
        while ( true )
        {
            int ridx = -1;
            int lidx = newPattern.indexOf( ldel, idx );

            if ( lidx >= 0 )
            {
                ridx = newPattern.indexOf( rdel, lidx + ldel.length() );
            }
            else
            {
                break;
            }

            if ( ++offnum >= BUFSIZE )
            {
                throw new IllegalArgumentException(
                        "TooManyArguments"
                );
            }

            if ( ridx < 0 )
            {
                if ( exactmatch )
                {
                    throw new IllegalArgumentException(
                            "UnmatchedBraces"
                    );
                }
                else
                {
                    break;
                }
            }

            outpat.append( newPattern.substring( idx, lidx ) );
            offsets[ offnum ] = outpat.length();
            arguments[ offnum ] = newPattern.substring( lidx + ldel.length(), ridx );
            idx = ridx + rdel.length();
            maxOffset++;
        }

        outpat.append( newPattern.substring( idx ) );

        return outpat.toString();
    }

    /**
     * Formats object.
     *
     * @param obj Object to be formatted into string
     *
     * @return Formatted object
     */
    private String formatObject( Object obj )
    {
        if ( obj == null )
        {
            return null;
        }

        if ( obj instanceof Number )
        {
            return NumberFormat.getInstance( locale ).format( obj ); // fix
        }
        else if ( obj instanceof Date )
        {
            return DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT, locale ).format( obj ); //fix
        }
        else if ( obj instanceof String )
        {
            return ( String ) obj;
        }

        return obj.toString();
    }

    /**
     * Formats the parsed string by inserting table's values.
     *
     * @param pat    a string pattern
     * @param result Buffer to be used for result.
     * @param fpos   position
     *
     * @return Formatted string
     */
    public StringBuffer format( Object pat, StringBuffer result, FieldPosition fpos )
    {
        String pattern = processPattern( ( String ) pat );
        int lastOffset = 0;

        for ( int i = 0; i <= maxOffset; ++i )
        {
            int offidx = offsets[ i ];
            result.append( pattern.substring( lastOffset, offsets[ i ] ) );
            lastOffset = offidx;

            String key = arguments[ i ];
            String obj;
            if ( key.length() > 0 )
            {
                obj = formatObject( processKey( key ) );
            }
            else
            {
                // else just copy the left and right braces
                result.append( this.ldel );
                result.append( this.rdel );
                continue;
            }

            if ( obj == null )
            {
                // try less-greedy match; useful for e.g. PROP___PROPNAME__ where
                // 'PROPNAME' is a key and delims are both '__'
                // this does not solve all possible cases, surely, but it should catch
                // the most common ones
                String lessgreedy = ldel + key;
                int fromright = lessgreedy.lastIndexOf( ldel );

                if ( fromright > 0 )
                {
                    String newkey = lessgreedy.substring( fromright + ldel.length() );
                    String newsubst = formatObject( processKey( newkey ) );

                    if ( newsubst != null )
                    {
                        obj = lessgreedy.substring( 0, fromright ) + newsubst;
                    }
                }
            }

            if ( obj == null )
            {
                if ( throwex )
                {
                    throw new IllegalArgumentException( "ObjectForKey" );
                }
                else
                {
                    obj = ldel + key + rdel;
                }
            }

            result.append( obj );
        }

        result.append( pattern.substring( lastOffset, pattern.length() ) );

        return result;
    }

    /**
     * Parses the string. Does not yet handle recursion (where
     * the substituted strings contain %n references.)
     */
    public Object parseObject( String text, ParsePosition status )
    {
        return parse( text );
    }

    /**
     * Parses the string. Does not yet handle recursion (where
     * the substituted strings contain {n} references.)
     *
     * @return New format.
     */
    public String parse( String source )
    {
        StringBuffer sbuf = new StringBuffer( source );
        Iterator key_it = argmap.keySet().iterator();

        while ( key_it.hasNext() )
        {
            String it_key = ( String ) key_it.next();
            String it_obj = formatObject( argmap.get( it_key ) );
            int it_idx = -1;

            do
            {
                it_idx = sbuf.toString().indexOf( it_obj, ++it_idx );

                if ( it_idx >= 0 )
                {
                    sbuf.replace( it_idx, it_idx + it_obj.length(), ldel + it_key + rdel );
                }
            } while ( it_idx != -1 );
        }

        return sbuf.toString();
    }

    /**
     * Test whether formatter will throw exception if object for key was not found.
     * If given map does not contain object for key specified, it could
     * throw an exception. Returns true if throws. If not, key is left unchanged.
     */
    public boolean willThrowExceptionIfKeyWasNotFound()
    {
        return throwex;
    }

    /**
     * Specify whether formatter will throw exception if object for key was not found.
     * If given map does not contain object for key specified, it could
     * throw an exception. If does not throw, key is left unchanged.
     *
     * @param flag If true, formatter throws IllegalArgumentException.
     */
    public void setThrowExceptionIfKeyWasNotFound( boolean flag )
    {
        throwex = flag;
    }

    /**
     * Test whether both brackets are required in the expression.
     * If not, use setExactMatch(false) and formatter will ignore missing right
     * bracket. Advanced feature.
     */
    public boolean isExactMatch()
    {
        return exactmatch;
    }

    /**
     * Specify whether both brackets are required in the expression.
     * If not, use setExactMatch(false) and formatter will ignore missing right
     * bracket. Advanced feature.
     *
     * @param flag If true, formatter will ignore missing right bracket (default = false)
     */
    public void setExactMatch( boolean flag )
    {
        exactmatch = flag;
    }

    /**
     * Returns string used as left brace
     */
    public String getLeftBrace()
    {
        return ldel;
    }

    /**
     * Sets string used as left brace
     *
     * @param delimiter Left brace.
     */
    public void setLeftBrace( String delimiter )
    {
        ldel = delimiter;
    }

    /**
     * Returns string used as right brace
     */
    public String getRightBrace()
    {
        return rdel;
    }

    /**
     * Sets string used as right brace
     *
     * @param delimiter Right brace.
     */
    public void setRightBrace( String delimiter )
    {
        rdel = delimiter;
    }

    /**
     * Returns argument map
     */
    public Map getMap()
    {
        return argmap;
    }

    /**
     * Sets argument map
     * This map should contain key-value pairs with key values used in
     * formatted string expression. If value for key was not found, formatter leave
     * key unchanged (except if you've set setThrowExceptionIfKeyWasNotFound(true),
     * then it fires IllegalArgumentException.
     *
     * @param map the argument map
     */
    public void setMap( Map map )
    {
        argmap = map;
    }
}
