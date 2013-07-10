package ge.utils.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class LockFile
{
    private File file;

    private File lockingFile;

    private RandomAccessFile randomAccessFile;

    private FileChannel fileChannel;

    private FileLock fileLock;

    public LockFile( File file )
    {
        this.file = file;
    }

    public boolean lockFile() throws IOException
    {
        if ( fileLock != null )
        {
            return fileLock.isValid();
        }

        if ( isFileLocked( file ) == true )
        {
            return false;
        }

        lockingFile = null;

        if ( file.isDirectory() == true )
        {
            lockingFile = new File( file, ".lock" );
        }
        else
        {
            File parent = file.getParentFile();
            String name = file.getName();
            lockingFile = new File( parent, name + ".lock" );
        }

        randomAccessFile = new RandomAccessFile( lockingFile, "rw" );
        fileChannel = randomAccessFile.getChannel();
        fileLock = fileChannel.tryLock();

        if ( fileLock != null )
        {
            return true;
        }
        else
        {
            randomAccessFile.close();
            randomAccessFile = null;
            fileChannel = null;
            lockingFile = null;
            return false;
        }
    }

    public boolean release() throws IOException
    {
        if ( fileLock != null )
        {
            fileLock.release();
            randomAccessFile.close();
            randomAccessFile = null;
            fileChannel = null;

            lockingFile.delete();
            lockingFile = null;

            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isFileLocked( File file ) throws IOException
    {
        if ( ( file == null ) || ( file.exists() == false ) )
        {
            return false;
        }

        File lockingFile = null;

        if ( file.isDirectory() == true )
        {
            lockingFile = new File( file, ".lock" );
        }
        else
        {
            File parent = file.getParentFile();
            String name = file.getName();
            lockingFile = new File( parent, name + ".lock" );
        }

        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        FileLock fileLock = null;

        try
        {
            randomAccessFile = new RandomAccessFile( lockingFile, "rw" );
            fileChannel = randomAccessFile.getChannel();
            fileLock = fileChannel.tryLock();

            if ( fileLock != null )
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch ( OverlappingFileLockException ofle )
        {
            return true;
        }
        catch ( IOException e )
        {
            return true;
        }
        finally
        {
            if ( fileLock != null )
            {
                fileLock.release();
                randomAccessFile.close();
                lockingFile.delete();
            }
            else
            {
                randomAccessFile.close();
            }
        }
    }
}
