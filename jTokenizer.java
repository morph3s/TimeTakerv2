import java.util.Enumeration;
import java.util.NoSuchElementException;

public final class jTokenizer
  implements Enumeration
{
  private int currentPosition = 0;
  private int maxPosition;
  private String str;
  private String delimiters;
  private boolean retTokens;

  public jTokenizer(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.str = paramString1;
    this.maxPosition = paramString1.length();
    this.delimiters = paramString2;
    this.retTokens = paramBoolean;
  }

  public jTokenizer(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, false);
  }

  public jTokenizer(String paramString)
  {
    this(paramString, " \t\n\r", false);
  }

  private void skipDelimiters()
  {
    while ((!this.retTokens) && (this.currentPosition < this.maxPosition) && (this.delimiters.indexOf(this.str.charAt(this.currentPosition)) >= 0))
      this.currentPosition += 1;
  }

  public boolean hasMoreTokens()
  {
    skipDelimiters();
    return this.currentPosition < this.maxPosition;
  }

  public void advance()
  {
    if (this.currentPosition >= this.maxPosition)
      throw new NoSuchElementException();
    this.currentPosition += 1;
  }

  public char currentChar()
  {
    return this.str.charAt(this.currentPosition);
  }

  public String nextToken()
  {
    skipDelimiters();
    if (this.currentPosition >= this.maxPosition)
      throw new NoSuchElementException();
    int i = this.currentPosition;
    while ((this.currentPosition < this.maxPosition) && (this.delimiters.indexOf(this.str.charAt(this.currentPosition)) < 0))
      this.currentPosition += 1;
    if ((this.retTokens) && (i == this.currentPosition) && (this.delimiters.indexOf(this.str.charAt(this.currentPosition)) >= 0))
      this.currentPosition += 1;
    return this.str.substring(i, this.currentPosition);
  }

  public String nextToken(String paramString)
  {
    this.delimiters = paramString;
    return nextToken();
  }

  public boolean hasMoreElements()
  {
    return hasMoreTokens();
  }

  public Object nextElement()
  {
    return nextToken();
  }

  public int countTokens()
  {
    int i = 0;
    int j = this.currentPosition;
    while (j < this.maxPosition)
    {
      while ((!this.retTokens) && (j < this.maxPosition) && (this.delimiters.indexOf(this.str.charAt(j)) >= 0))
        j++;
      if (j >= this.maxPosition)
        break;
      int k = j;
      while ((j < this.maxPosition) && (this.delimiters.indexOf(this.str.charAt(j)) < 0))
        j++;
      if ((this.retTokens) && (k == j) && (this.delimiters.indexOf(this.str.charAt(j)) >= 0))
        j++;
      i++;
    }
    return i;
  }

  public byte nextByte()
  {
    return (byte)nextInt();
  }

  public int nextInt()
  {
    skipDelimiters();
    if (this.currentPosition >= this.maxPosition)
      throw new NoSuchElementException();
    int i = this.currentPosition;
    while ((this.currentPosition < this.maxPosition) && (this.delimiters.indexOf(this.str.charAt(this.currentPosition)) < 0))
      this.currentPosition += 1;
    if ((this.retTokens) && (i == this.currentPosition) && (this.delimiters.indexOf(this.str.charAt(this.currentPosition)) >= 0))
      this.currentPosition += 1;
    int j = 0;
    int k = 0;
    while (i < this.currentPosition)
    {
      int m = this.str.charAt(i);
      if (m != 32)
        if (m == 45)
        {
          k = 1;
        }
        else
        {
          j *= 10;
          j += m - 48;
        }
      i++;
    }
    if (k != 0)
      j = -j;
    return j;
  }

  public long nextLong()
  {
    skipDelimiters();
    if (this.currentPosition >= this.maxPosition)
      throw new NoSuchElementException();
    int i = this.currentPosition;
    while ((this.currentPosition < this.maxPosition) && (this.delimiters.indexOf(this.str.charAt(this.currentPosition)) < 0))
      this.currentPosition += 1;
    if ((this.retTokens) && (i == this.currentPosition) && (this.delimiters.indexOf(this.str.charAt(this.currentPosition)) >= 0))
      this.currentPosition += 1;
    long l = 0L;
    int j = 0;
    while (i < this.currentPosition)
    {
      int k = this.str.charAt(i);
      if (k == 45)
      {
        j = 1;
      }
      else
      {
        l *= 10L;
        l += k - 48;
      }
      i++;
    }
    if (j != 0)
      l = -l;
    return l;
  }

  public void skipToken()
  {
    skipDelimiters();
    if (this.currentPosition >= this.maxPosition)
      throw new NoSuchElementException();
    int i = this.currentPosition;
    while ((this.currentPosition < this.maxPosition) && (this.delimiters.indexOf(this.str.charAt(this.currentPosition)) < 0))
      this.currentPosition += 1;
    if ((this.retTokens) && (i == this.currentPosition) && (this.delimiters.indexOf(this.str.charAt(this.currentPosition)) >= 0))
      this.currentPosition += 1;
  }

  public float nextFloat()
  {
    try
    {
      return Float.valueOf(nextToken()).floatValue();
    }
    catch (Exception localException)
    {
    }
    return 0.0F;
  }

  public double nextDouble()
  {
    try
    {
      return Double.valueOf(nextToken()).doubleValue();
    }
    catch (Exception localException)
    {
    }
    return 0.0D;
  }
}