public class StrangeBase
{
  public static char startchar = 'a';

  public static String makeBase(int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramInt2;
    while (i < paramInt1)
      i *= paramInt2;
    do
    {
      int j = paramInt1 / i;
      char c = (char)(startchar + j);
      localStringBuffer.append(c);
      paramInt1 -= i * j;
      i /= paramInt2;
    }
    while (i > 0);
    return localStringBuffer.toString();
  }

  public static int parseBase(String paramString, int paramInt)
  {
    int i = 0;
    for (int j = 0; j < paramString.length(); j++)
    {
      i *= paramInt;
      i += paramString.charAt(j) - startchar;
    }
    return i;
  }
}