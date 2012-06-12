public final class QStringBuffer
{
  private char[] value;
  private int count;
  private int capacity = 16;

  public QStringBuffer()
  {
    this(16);
  }

  public QStringBuffer(int paramInt)
  {
    this.value = new char[paramInt];
    this.capacity = paramInt;
  }

  public QStringBuffer(String paramString)
  {
    this(paramString.length() + 16);
    append(paramString);
  }

  public int length()
  {
    return this.count;
  }

  public int capacity()
  {
    return this.capacity;
  }

  private void ensureCapacity(int paramInt)
  {
    if (paramInt > this.capacity)
    {
      this.capacity = ((this.capacity + 1) * 2);
      if (paramInt > this.capacity)
        this.capacity = paramInt;
      char[] arrayOfChar = new char[this.capacity];
      System.arraycopy(this.value, 0, arrayOfChar, 0, this.count);
      this.value = arrayOfChar;
    }
  }

  public void setLength(int paramInt)
  {
    if (paramInt < 0)
      throw new StringIndexOutOfBoundsException(paramInt);
    ensureCapacity(paramInt);
    if (this.count < paramInt)
      while (this.count < paramInt)
      {
        this.value[this.count] = '\000';
        this.count += 1;
      }
    this.count = paramInt;
  }

  public char charAt(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.count))
      throw new StringIndexOutOfBoundsException(paramInt);
    return this.value[paramInt];
  }

  public void getChars(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.count))
      throw new StringIndexOutOfBoundsException(paramInt1);
    if ((paramInt2 < 0) || (paramInt2 > this.count))
      throw new StringIndexOutOfBoundsException(paramInt2);
    if (paramInt1 < paramInt2)
      System.arraycopy(this.value, paramInt1, paramArrayOfChar, paramInt3, paramInt2 - paramInt1);
  }

  public void setCharAt(int paramInt, char paramChar)
  {
    if ((paramInt < 0) || (paramInt >= this.count))
      throw new StringIndexOutOfBoundsException(paramInt);
    this.value[paramInt] = paramChar;
  }

  public QStringBuffer append(Object paramObject)
  {
    return append(String.valueOf(paramObject));
  }

  public QStringBuffer append(String paramString)
  {
    if (paramString == null)
      paramString = String.valueOf(paramString);
    int i = paramString.length();
    ensureCapacity(this.count + i);
    paramString.getChars(0, i, this.value, this.count);
    this.count += i;
    return this;
  }

  public QStringBuffer append(char[] paramArrayOfChar)
  {
    int i = paramArrayOfChar.length;
    ensureCapacity(this.count + i);
    System.arraycopy(paramArrayOfChar, 0, this.value, this.count, i);
    this.count += i;
    return this;
  }

  public QStringBuffer append(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    ensureCapacity(this.count + paramInt2);
    System.arraycopy(paramArrayOfChar, paramInt1, this.value, this.count, paramInt2);
    this.count += paramInt2;
    return this;
  }

  public QStringBuffer append(boolean paramBoolean)
  {
    return append(String.valueOf(paramBoolean));
  }

  public QStringBuffer append(char paramChar)
  {
    ensureCapacity(this.count + 1);
    this.value[(this.count++)] = paramChar;
    return this;
  }

  public QStringBuffer append(int paramInt)
  {
    return append(String.valueOf(paramInt));
  }

  public QStringBuffer append(long paramLong)
  {
    return append(String.valueOf(paramLong));
  }

  public QStringBuffer append(float paramFloat)
  {
    return append(String.valueOf(paramFloat));
  }

  public QStringBuffer append(double paramDouble)
  {
    return append(String.valueOf(paramDouble));
  }

  public QStringBuffer insert(int paramInt, Object paramObject)
  {
    return insert(paramInt, String.valueOf(paramObject));
  }

  public QStringBuffer insert(int paramInt, String paramString)
  {
    if ((paramInt < 0) || (paramInt > this.count))
      throw new StringIndexOutOfBoundsException();
    int i = paramString.length();
    ensureCapacity(this.count + i);
    System.arraycopy(this.value, paramInt, this.value, paramInt + i, this.count - paramInt);
    paramString.getChars(0, i, this.value, paramInt);
    this.count += i;
    return this;
  }

  public QStringBuffer insert(int paramInt, char[] paramArrayOfChar)
  {
    if ((paramInt < 0) || (paramInt > this.count))
      throw new StringIndexOutOfBoundsException();
    int i = paramArrayOfChar.length;
    ensureCapacity(this.count + i);
    System.arraycopy(this.value, paramInt, this.value, paramInt + i, this.count - paramInt);
    System.arraycopy(paramArrayOfChar, 0, this.value, paramInt, i);
    this.count += i;
    return this;
  }

  public QStringBuffer insert(int paramInt, boolean paramBoolean)
  {
    return insert(paramInt, String.valueOf(paramBoolean));
  }

  public QStringBuffer insert(int paramInt, char paramChar)
  {
    ensureCapacity(this.count + 1);
    System.arraycopy(this.value, paramInt, this.value, paramInt + 1, this.count - paramInt);
    this.value[paramInt] = paramChar;
    this.count += 1;
    return this;
  }

  public QStringBuffer insert(int paramInt1, int paramInt2)
  {
    return insert(paramInt1, String.valueOf(paramInt2));
  }

  public QStringBuffer insert(int paramInt, long paramLong)
  {
    return insert(paramInt, String.valueOf(paramLong));
  }

  public QStringBuffer insert(int paramInt, float paramFloat)
  {
    return insert(paramInt, String.valueOf(paramFloat));
  }

  public QStringBuffer insert(int paramInt, double paramDouble)
  {
    return insert(paramInt, String.valueOf(paramDouble));
  }

  public QStringBuffer reverse()
  {
    int i = this.count - 1;
    for (int j = i - 1 >> 1; j >= 0; j--)
    {
      int k = this.value[j];
      this.value[j] = this.value[(i - j)];
      this.value[(i - j)] = (char)k;
    }
    return this;
  }

  public String toString()
  {
    return new String(this.value, 0, this.count);
  }

  final char[] getValue()
  {
    return this.value;
  }

  public int replace(String paramString1, String paramString2)
  {
    return replace(paramString1, paramString2, false);
  }

  public int replace(String paramString1, String paramString2, boolean paramBoolean)
  {
    int i = 0;
    try
    {
      if ((paramString1 == null) || (paramString1.length() == 0))
        return i;
      if (paramString2 == null)
        return i;
      int j = 0;
      int m = paramString1.length();
      int n = paramString2.length();
      int k;
      while ((k = indexOf(j, paramString1)) >= 0)
      {
        
        i++;
        if (m == n)
        {
          setString(k, paramString2);
        }
        else
        {
          makeSpace(k, n - m);
          setString(k, paramString2);
        }
        j = k + n;
        if (paramBoolean)
          continue;
      }
    }
    catch (Exception localException)
    {
    }
    return i;
  }

  public QStringBuffer setString(int paramInt, String paramString)
    throws Exception
  {
    paramString.getChars(0, paramString.length(), this.value, paramInt);
    return this;
  }

  public QStringBuffer makeSpace(int paramInt1, int paramInt2)
  {
    try
    {
      if (paramInt2 == 0)
        return this;
      int i = length();
      if (paramInt2 < 0)
      {
        System.arraycopy(this.value, paramInt1 - paramInt2, this.value, paramInt1, i - paramInt1 + paramInt2);
        setLength(i + paramInt2);
      }
      else
      {
        setLength(i + paramInt2);
        System.arraycopy(this.value, paramInt1, this.value, paramInt1 + paramInt2, i - paramInt1);
      }
    }
    catch (Exception localException)
    {
    }
    return this;
  }

  public int indexOf(String paramString)
  {
    return indexOf(0, paramString);
  }

  public int indexOf(int paramInt, String paramString)
  {
    try
    {
      int i = paramString.charAt(0);
      int j = paramString.length();
      int k = length() - j + 1;
      for (int m = paramInt; m < k; m++)
      {
        if (this.value[m] != i) {
          continue;
        }
        int n;
        for (n = 1; (n < j) && (this.value[(m + n)] == paramString.charAt(n)); n++);
        if (n >= j)
          return m;
      }
    }
    catch (Exception localException)
    {
    }
    return -1;
  }

  public QStringBuffer htmlAppend(String paramString)
  {
    try
    {
      int k = paramString.length();
      for (int i = 0; i < k; i++)
      {
        char c = paramString.charAt(i);
        if ((c == ' ') || ((c > '?') && (c < '')))
        {
          append(c);
        }
        else
        {	
        	int j;
          for (j = 0; j < SBUtil.htmlreplacers.length; j += 2)
          {
            if (SBUtil.htmlreplacers[j].charAt(0) != c)
              continue;
            append(SBUtil.htmlreplacers[(j + 1)]);
            break;
          }
          if (j < SBUtil.htmlreplacers.length)
            continue;
          append(c);
        }
      }
    }
    catch (Exception localException)
    {
    }
    return this;
  }

  public byte[] getBytes()
  {
    byte[] arrayOfByte = new byte[length()];
    for (int i = 0; i < arrayOfByte.length; i++)
      arrayOfByte[i] = (byte)this.value[i];
    return arrayOfByte;
  }
}