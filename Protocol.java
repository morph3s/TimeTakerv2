public class Protocol
{
  public static final String delimiter = "*";
  public static final char delimiterchar = '*';
  protected static final char quotechar = "'".charAt(0);
  public static final char playerpos = '!';
  public static final char listrooms = 'a';
  public static final char roomlist = 'b';
  public static final char timeis = 'c';
  public static final char addfriend = 'd';
  public static final char removefriend = 'e';
  public static final char newflags = 'f';
  public static final char alive = 'g';
  public static final char friendlist = 'h';
  public static final char addignore = 'i';
  public static final char removeignore = 'j';
  public static final char listonlineplayers = 'k';
  public static final char onlineplayerlist = 'l';
  public static final char listallplayers = 'm';
  public static final char roommode = 'n';
  public static final char newplayer = 'o';
  public static final char newpreferences = 'p';
  public static final char enterroom = 'q';
  public static final char getplayerinfosys = 'r';
  public static final char playerinfoonline = 's';
  public static final char getplayerinfoonline = 't';
  public static final char requestroom = 'u';
  public static final char playergone = 'v';
  public static final char thrownout = 'w';
  public static final char exit = 'x';
  public static final char playerlist = 'y';
  public static final char roomrefused = 'z';
  public static final char getroominfo = '1';
  public static final char roominfo = '2';
  public static final char command = '3';
  public static final char replytothis = '4';
  public static final char throwout = '-';
  public static final char remove = '_';
  public static final char servermessage = ':';
  public static final char whisper = '%';
  public static final char chatline = ' ';

  public static String string_time(long paramLong, boolean paramBoolean)
  {
    return "c," + paramLong + "," + (paramBoolean ? 1 : 0);
  }

  public static final String string_alive()
  {
    return "g";
  }

  public String string_exit(int paramInt)
  {
    return "x," + paramInt;
  }

  public static String string_command(String paramString1, String paramString2)
  {
    return "3," + paramString1 + "," + paramString2;
  }

  public static String string_replytothis(long paramLong)
  {
    return "4," + paramLong;
  }

  public static String string_throwout(int paramInt)
  {
    return "-," + paramInt;
  }

  public static String string_remove(int paramInt)
  {
    return "_," + paramInt;
  }

  public static String string_whisper(int paramInt1, int paramInt2, String paramString)
  {
    return "%," + paramInt1 + "," + paramInt2 + "," + paramString;
  }

  public static String string_chatline(int paramInt, String paramString)
  {
    return " ," + paramInt + "," + paramString;
  }

  public static String encodeString(String paramString)
  {
    return encodeString(new QStringBuffer(), paramString).toString();
  }

  public static QStringBuffer encodeString(QStringBuffer paramQStringBuffer, String paramString)
  {
    if (paramString == null)
      return paramQStringBuffer;
    for (int m = 0; m < paramString.length(); m++)
    {
      int n = paramString.charAt(m);
      if ((n < 32) || (n > 127) || (n == 42) || (n == 38) || (n == 44) || (n == quotechar))
      {
        paramQStringBuffer.append('&');
        int i = n;
        for (int i1 = 0; i1 < 4; i1++)
        {
          int k = (i & 0xF000) >> 12;
          paramQStringBuffer.append((char)(65 + k));

          i <<= 4;
        }
      }
      else
      {
        paramQStringBuffer.append(n);
      }
    }
    return paramQStringBuffer;
  }

  public static String decodeString(String paramString)
  {
    return decodeString(new QStringBuffer(), paramString).toString();
  }

  public static QStringBuffer decodeString(QStringBuffer paramQStringBuffer, String paramString)
  {
    if (paramString == null)
      return paramQStringBuffer;
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      if (c == '&')
      {
        if (i < j + 5)
          break;
        int k = 0;
        for (int m = 0; m < 4; m++)
        {
          j++;
          k *= 16;
          k += paramString.charAt(j) - 'A';
        }
        paramQStringBuffer.append((char)k);
      }
      else
      {
        paramQStringBuffer.append(c);
      }
    }
    return paramQStringBuffer;
  }
}