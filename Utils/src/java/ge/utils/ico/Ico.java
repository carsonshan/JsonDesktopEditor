package ge.utils.ico;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Ico
{
    private final static int FDE_OFFSET = 6; // first directory entry offset

    private final static int DE_LENGTH = 16; // directory entry length

    private final static int BMIH_LENGTH = 40; // BITMAPINFOHEADER length

    private final static int[] masks = { 128, 64, 32, 16, 8, 4, 2, 1 };

    private static int calculateScanLineBytes( int width, int bitCount )
    {
        return ( ( ( width * bitCount ) + 31 ) / 32 ) * 4;
    }

    private static Image[] parseICOImage( byte[] icoImage )
    {
        if ( icoImage[ 2 ] != 1 || icoImage[ 3 ] != 0 )
        {
            throw new IllegalArgumentException( "Not an ICO resource" );
        }

        int imageCount = calculateImageCount( icoImage[ 4 ], icoImage[ 5 ] );

        Image[] images = new Image[ imageCount ];

        for ( int i = 0; i < imageCount; i++ )
        {
            int imageOffset = calculateImageOffset( icoImage, i );

            if ( ( icoImage[ imageOffset ] == 40 ) &&
                    ( icoImage[ imageOffset + 1 ] == 0 ) &&
                    ( icoImage[ imageOffset + 2 ] == 0 ) &&
                    ( icoImage[ imageOffset + 3 ] == 0 ) )
            {
                int width = calculateWidth( icoImage, i, imageOffset );
                int height = calculateHeight( icoImage, i, imageOffset );
                int colorCount = calculateColorCount( icoImage, i, imageOffset );

                BufferedImage bi = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );

                images[ i ] = bi;

                // Parse image to image buffer.

                int colorTableOffset = imageOffset + BMIH_LENGTH;

                if ( colorCount != 0 )
                {
                    int xorImageOffset = calculateXorImageOffset( colorTableOffset, colorCount );

                    int scanLineBytes = calculateScanLineBytes( width, calculateImageBitCount( colorCount ) );

                    int andImageOffset = xorImageOffset + scanLineBytes * height;

                    for ( int row = 0; row < height; row++ )
                    {
                        for ( int col = 0; col < width; col++ )
                        {
                            int rgb = calculateRGB( colorCount,
                                                    icoImage,
                                                    colorTableOffset,
                                                    row,
                                                    scanLineBytes,
                                                    col,
                                                    xorImageOffset,
                                                    andImageOffset,
                                                    width );
                            bi.setRGB( col, height - 1 - row, rgb );
                        }
                    }
                }
                else
                {
                    int scanLineBytes = calculateScanLineBytes( width, calculateImageBitCount( colorCount ) );

                    for ( int row = 0; row < height; row++ )
                    {
                        for ( int col = 0; col < width; col++ )
                        {
                            int rgb = calculateRGB( colorCount,
                                                    icoImage,
                                                    colorTableOffset,
                                                    row,
                                                    scanLineBytes,
                                                    col,
                                                    0,
                                                    0,
                                                    width );
                            bi.setRGB( col, height - 1 - row, rgb );
                        }
                    }
                }
            }
            else if ( ( convertToUnsigned( icoImage[ imageOffset ] ) == 0x89 ) &&
                    ( icoImage[ imageOffset + 1 ] == 0x50 ) &&
                    ( icoImage[ imageOffset + 2 ] == 0x4e ) &&
                    ( icoImage[ imageOffset + 3 ] == 0x47 ) &&
                    ( icoImage[ imageOffset + 4 ] == 0x0d ) &&
                    ( icoImage[ imageOffset + 5 ] == 0x0a ) &&
                    ( icoImage[ imageOffset + 6 ] == 0x1a ) &&
                    ( icoImage[ imageOffset + 7 ] == 0x0a ) )
            {
                // PNG detected
                try
                {
                    int bytesInRes = calculateBytesInRes( icoImage, i );

                    ByteArrayInputStream byteArrayInputStream =
                            new ByteArrayInputStream( icoImage, imageOffset, bytesInRes );

                    images[ i ] = ImageIO.read( byteArrayInputStream );
                }
                catch ( IOException e )
                {
                    throw new IllegalArgumentException( e.getMessage(), e );
                }
            }
            else
            {
                throw new IllegalArgumentException( "BITMAPINFOHEADER or PNG expected" );
            }
        }

        return images;
    }

    private static int calculateRGB( int colorCount,
                                     byte[] icoImage,
                                     int colorTableOffset,
                                     int row,
                                     int scanLineBytes,
                                     int col,
                                     int xorImageOffset,
                                     int andImageOffset,
                                     int width )
    {
        int rgb = 0;
        if ( colorCount == 0 )
        {
            rgb = convertToUnsigned( icoImage[ colorTableOffset + row * scanLineBytes + col * 4 +
                    3 ] );
            rgb <<= 8;
            rgb |= convertToUnsigned( icoImage[ colorTableOffset + row * scanLineBytes + col * 4 +
                    2 ] );
            rgb <<= 8;
            rgb |= convertToUnsigned( icoImage[ colorTableOffset + row * scanLineBytes + col * 4 +
                    1 ] );
            rgb <<= 8;
            rgb |= convertToUnsigned( icoImage[ colorTableOffset + row * scanLineBytes + col * 4 ] );
        }
        else if ( colorCount == 2 )
        {
            int index;

            if ( ( convertToUnsigned( icoImage[ xorImageOffset + row * scanLineBytes + col / 8 ] ) &
                           masks[ col % 8 ] ) != 0 )
            {
                index = 1;
            }
            else
            {
                index = 0;
            }

            rgb |= ( convertToUnsigned( icoImage[ colorTableOffset + index * 4 + 2 ] ) );
            rgb <<= 8;
            rgb |= ( convertToUnsigned( icoImage[ colorTableOffset + index * 4 + 1 ] ) );
            rgb <<= 8;
            rgb |= ( convertToUnsigned( icoImage[ colorTableOffset + index * 4 ] ) );

            if ( ( convertToUnsigned( icoImage[ andImageOffset + row * scanLineBytes + col / 8 ] ) &
                           masks[ col % 8 ] ) == 0 )
            {
                rgb = 0xff000000 | rgb;
            }
        }
        else if ( colorCount == 16 )
        {
            int index;
            if ( ( col & 1 ) == 0 ) // even
            {
                index = convertToUnsigned( icoImage[ xorImageOffset + row * scanLineBytes + col / 2 ] );
                index >>= 4;
            }
            else
            {
                index = convertToUnsigned( icoImage[ xorImageOffset + row * scanLineBytes +
                        col / 2 ] ) & 15;
            }

            rgb |= ( convertToUnsigned( icoImage[ colorTableOffset + index * 4 + 2 ] ) );
            rgb <<= 8;
            rgb |= ( convertToUnsigned( icoImage[ colorTableOffset + index * 4 + 1 ] ) );
            rgb <<= 8;
            rgb |= ( convertToUnsigned( icoImage[ colorTableOffset + index * 4 ] ) );

            if ( ( convertToUnsigned( icoImage[ andImageOffset + row * calculateScanLineBytes( width, 1 ) +
                    col / 8 ] ) & masks[ col % 8 ] ) == 0 )
            {
                rgb = 0xff000000 | rgb;
            }
        }
        else if ( colorCount == 256 )
        {
            int index;
            index = convertToUnsigned( icoImage[ xorImageOffset + row * scanLineBytes + col ] );

            rgb |= ( convertToUnsigned( icoImage[ colorTableOffset + index * 4 + 2 ] ) );
            rgb <<= 8;
            rgb |= ( convertToUnsigned( icoImage[ colorTableOffset + index * 4 + 1 ] ) );
            rgb <<= 8;
            rgb |= ( convertToUnsigned( icoImage[ colorTableOffset + index * 4 ] ) );

            if ( ( convertToUnsigned( icoImage[ andImageOffset + row * calculateScanLineBytes( width, 1 ) +
                    col / 8 ] ) & masks[ col % 8 ] ) == 0 )
            {
                rgb = 0xff000000 | rgb;
            }
        }

        return rgb;
    }

    private static int calculateImageBitCount( int colorCount )
    {
        if ( colorCount == 2 )
        {
            return 1;
        }
        else if ( colorCount == 16 )
        {
            return 4;
        }
        else if ( colorCount == 256 )
        {
            return 8;
        }
        return 32;
    }

    private static int calculateXorImageOffset( int colorTableOffset, int colorCount )
    {
        return colorTableOffset + colorCount * 4;
    }

    private static int calculateImageCount( byte first, byte second )
    {
        int imageCount = convertToUnsigned( second );
        imageCount <<= 8;
        imageCount |= first;
        return imageCount;
    }

    private static int calculateColorCount( byte[] icoImage, int imageIndex, int imageOffset )
    {
        int colorCount = convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 2 ] );

        if ( colorCount == 0 )
        {
            int planes = calculatePlanes( icoImage, imageOffset );
            int bitCount = calculateBitCount( icoImage, imageOffset );

            if ( planes == 1 )
            {
                if ( bitCount == 1 )
                {
                    colorCount = 2;
                }
                else if ( bitCount == 4 )
                {
                    colorCount = 16;
                }
                else if ( bitCount == 8 )
                {
                    colorCount = 256;
                }
                else if ( bitCount != 32 )
                {
                    colorCount = ( int ) Math.pow( 2, bitCount );
                }
            }
            else
            {
                colorCount = ( int ) Math.pow( 2, bitCount * planes );
            }
        }

        return colorCount;
    }

    private static int calculateBitCount( byte[] icoImage, int imageOffset )
    {
        int bitCount = convertToUnsigned( icoImage[ imageOffset + 15 ] );
        bitCount <<= 8;
        bitCount |= convertToUnsigned( icoImage[ imageOffset + 14 ] );

        return bitCount;
    }

    private static int calculatePlanes( byte[] icoImage, int imageOffset )
    {
        int planes = convertToUnsigned( icoImage[ imageOffset + 13 ] );
        planes <<= 8;
        planes |= convertToUnsigned( icoImage[ imageOffset + 12 ] );

        return planes;
    }

    private static int calculateHeight( byte[] icoImage, int imageIndex, int imageOffset )
    {
        int height = convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 1 ] );

        if ( height == 0 )
        {
            height = convertToUnsigned( icoImage[ imageOffset + 11 ] );
            height <<= 8;
            height |= convertToUnsigned( icoImage[ imageOffset + 10 ] );
            height <<= 8;
            height |= convertToUnsigned( icoImage[ imageOffset + 9 ] );
            height <<= 8;
            height |= convertToUnsigned( icoImage[ imageOffset + 8 ] );
            height = height >> 1;
        }

        return height;
    }

    private static int calculateWidth( byte[] icoImage, int imageIndex, int imageOffset )
    {
        int width = convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH ] );

        if ( width == 0 )
        {
            width = convertToUnsigned( icoImage[ imageOffset + 7 ] );
            width <<= 8;
            width |= convertToUnsigned( icoImage[ imageOffset + 6 ] );
            width <<= 8;
            width |= convertToUnsigned( icoImage[ imageOffset + 5 ] );
            width <<= 8;
            width |= convertToUnsigned( icoImage[ imageOffset + 4 ] );
        }

        return width;
    }

    private static int calculateBytesInRes( byte[] icoImage, int imageIndex )
    {
        int bytesInRes = convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 11 ] );
        bytesInRes <<= 8;
        bytesInRes |= convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 10 ] );
        bytesInRes <<= 8;
        bytesInRes |= convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 9 ] );
        bytesInRes <<= 8;
        bytesInRes |= convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 8 ] );

        return bytesInRes;
    }

    private static int calculateImageOffset( byte[] icoImage, int imageIndex )
    {
        int imageOffset = convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 15 ] );
        imageOffset <<= 8;
        imageOffset |= convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 14 ] );
        imageOffset <<= 8;
        imageOffset |= convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 13 ] );
        imageOffset <<= 8;
        imageOffset |= convertToUnsigned( icoImage[ FDE_OFFSET + imageIndex * DE_LENGTH + 12 ] );

        return imageOffset;
    }

    private static int convertToUnsigned( byte b )
    {
        return ( b < 0 ) ? 256 + b : b; // Convert byte to unsigned byte.
    }

    public static Image getImage( byte[] bytes, int index )
    {
        Image[] images = parseICOImage( bytes );
        return images[ index ];
    }
}
