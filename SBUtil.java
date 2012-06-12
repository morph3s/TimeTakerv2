public final class SBUtil
{
  public static final String[] htmlreplacers = { "<", "&lt;", ">", "&gt;", "&", "&amp;", "æ", "&aelig;", "ø", "&oslash;", "å", "&aring;", "Æ", "&AElig;", "Ø", "&Oslash;", "Å", "&Aring;", "é", "&eacute;", "ä", "&auml;", "ë", "&euml;", "ï", "&iuml;", "ü", "&uuml;", "ö", "&ouml;", "É", "&Eacute;", "Ä", "&Auml;", "Ë", "&Euml;", "�?", "&Iuml;", "Ü", "&Uuml;", "Ö", "&Ouml;" };

  public static StringBuffer replace(StringBuffer paramStringBuffer, String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return paramStringBuffer;
    if (paramString2 == null)
      return paramStringBuffer;
    if (paramString2.indexOf(paramString1) >= 0)
      return paramStringBuffer;
    int i = 0;
    do
    {
      
      if (paramString1.length() == paramString2.length())
      {
        setString(paramStringBuffer, i, paramString2);
      }
      else {
        makespace(paramStringBuffer, i, -paramString1.length());
        paramStringBuffer.insert(i, paramString2);
      }
    }
    while ((i = indexOf(paramStringBuffer, paramString1)) >= 0);

    return paramStringBuffer;
  }

  public static StringBuffer setString(StringBuffer paramStringBuffer, int paramInt, String paramString)
  {
    for (int i = 0; i < paramString.length(); i++)
      paramStringBuffer.setCharAt(paramInt + i, paramString.charAt(i));
    return paramStringBuffer;
  }

  public static StringBuffer makespace(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
  {
    try
    {
      if (paramInt2 == 0)
        return paramStringBuffer;
      int i = paramStringBuffer.length();

      if (paramInt2 < 0)
      {
        for (int j = paramInt1; j < i + paramInt2; j++)
          paramStringBuffer.setCharAt(j, paramStringBuffer.charAt(j - paramInt2));
        paramStringBuffer.setLength(i + paramInt2);
      }
      else
      {
        for (int j = 0; j < paramInt2; j++)
          paramStringBuffer.insert(33, paramInt1);
      }
    }
    catch (Exception localException)
    {
    }
    return paramStringBuffer;
  }

  public static int indexOf(StringBuffer paramStringBuffer, String paramString)
  {
    return indexOf(paramStringBuffer, 0, paramString);
  }

  public static int indexOf(StringBuffer paramStringBuffer, int paramInt, String paramString)
  {
    try
    {
      int i = paramString.charAt(0);
      int j = paramString.length();
      int k = paramStringBuffer.length() - j + 1;
      for (int m = paramInt; m < k; m++)
      {
        if (paramStringBuffer.charAt(m) != i) {
          continue;
        }
        int n;
        for (n = 1; (n < j) && (paramStringBuffer.charAt(m + n) == paramString.charAt(n)); n++);
        if (n >= j)
          return m;
      }
    }
    catch (Exception localException)
    {
    }
    return -1;
  }

  public static boolean equals(StringBuffer paramStringBuffer, String paramString)
  {
    if ((paramStringBuffer.length() == 0) || (paramStringBuffer.length() != paramString.length()))
      return false;
    for (int i = 0; i < paramStringBuffer.length(); i++)
      if (paramStringBuffer.charAt(i) != paramString.charAt(i))
        return false;
    return true;
  }

  public static boolean startsWith(StringBuffer paramStringBuffer, String paramString)
  {
    if (paramStringBuffer.length() < paramString.length())
      return false;
    for (int i = 0; i < paramString.length(); i++)
      if (paramStringBuffer.charAt(i) != paramString.charAt(i))
        return false;
    return true;
  }

  public static boolean endsWith(StringBuffer paramStringBuffer, String paramString)
  {
    int i = paramStringBuffer.length() - paramString.length();
    if (i < 0)
      return false;
    for (int j = 0; j < paramString.length(); j++)
      if (paramStringBuffer.charAt(j + i) != paramString.charAt(j))
        return false;
    return true;
  }

  public static String fixString(String paramString)
  {
    if (paramString == null)
      return null;
    try
    {
      QStringBuffer localQStringBuffer = new QStringBuffer(paramString.length() + 32);
      localQStringBuffer.htmlAppend(paramString);
      return localQStringBuffer.toString();
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static StringBuffer htmlAppend(StringBuffer paramStringBuffer, String paramString)
  {
    try
    {
      if (paramString == null)
        return paramStringBuffer;
      for (int i = 0; i < paramString.length(); i++)
      {
        char c = paramString.charAt(i);
        int j;
        for (j = 0; j < htmlreplacers.length; j += 2)
        {
          if (htmlreplacers[j].charAt(0) != c)
            continue;
          paramStringBuffer.append(htmlreplacers[(j + 1)]);
          break;
        }
        if (j < htmlreplacers.length)
          continue;
        paramStringBuffer.append(c);
      }
    }
    catch (Exception localException)
    {
    }
    return paramStringBuffer;
  }
}