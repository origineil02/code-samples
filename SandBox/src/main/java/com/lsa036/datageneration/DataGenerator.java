package com.lsa036.datageneration;

public class DataGenerator implements Runnable
{

  private int plisns = 30000;

  @Override
  public void run()
  {
    generatePlisnBlocks(1, 1, generatePlisn(1));
  }

  private void generatePlisnBlocks(int plisn, int depth, final String nha)
  {
    if (plisn > plisns)
    {
      return;
    }

    if (depth > 5)
    {
      depth = 1;
    }

    for (int i = 1; i <= 5; i++)
    {

      final String cardA = generateCardA(plisn);
      final String cardC = generateCardC(plisn, generatePlisn(plisn - (i-1)));
      final String cardD = generateCardD(plisn);
      final String cardH = generateCardH(plisn);

      System.out.println(cardA);

      if (i > 1)
      {
        System.out.println(cardC);
      }

      System.out.println(cardD);

      System.out.println(cardH);
      plisn++;
    }

//    System.out.println("\nrecur\n");
    generatePlisnBlocks(plisn, ++depth, "");
  }

  private String generateCardA(int i)
  {
    final StringBuilder sb = new StringBuilder();
    insertCardCommonData(sb, i);
    if (1 == i)
    {
      sb.append("A");
      sb.append("ECAGE");
      sb.append("EIREFERENCENUMBER");
    }
    else
    {
      sb.append(" ");
      sb.append("CAGEC");
      sb.append("REF");
      sb.append(String.valueOf(i));
    }

    final String str = sb.toString();
    final String cardLine = String.format("%s% " + (80 - str.length()) + "d", str, i % 2);
    return cardLine.substring(0, 77) + "01A";
  }

  private String generateCardC(int i, final String nhaPlisn)
  {
    final StringBuilder sb = new StringBuilder();
    insertCardCommonData(sb, i);

    sb.append(nhaPlisn);

    final String str = sb.toString();
    final String cardLine = String.format("%s% " + (80 - str.length()) + "d", str, i % 2);
    return cardLine.substring(0, 77) + "01C";
  }

  private String generateCardD(int i)
  {
    final StringBuilder sb = new StringBuilder();
    insertCardCommonData(sb, i);
    sb.append("UOC,");
    final String str = sb.toString();
    final String cardLine = String.format("%s% " + (80 - str.length()) + "d", str, i % 2);
    return cardLine.substring(0, 77) + "01D";
  }

  private String generateCardH(int i)
  {
    final StringBuilder sb = new StringBuilder();
    insertCardCommonData(sb, i);
    if (1 == i)
    {
      sb.append("EILCN");
    }
    else
    {
      sb.append("LCN");
      sb.append(String.valueOf(i));
    }

    final String str = sb.toString();
    String card = String.format("%s% " + (30 - str.length()) + "d", str, i % 2);

    card = card.substring(0, 29) + " 00";

    final String cardLine = String.format("%s% " + (80 - card.length()) + "d", card, i % 2);
    return cardLine.substring(0, 77) + "01H";
  }

  private void insertCardCommonData(final StringBuilder sb, int i)
  {
    sb.append("PCCN56");
    sb.append(String.format("%0" + 4 + "d", i));
    sb.append("  ");
  }

  private String generatePlisn(int i)
  {
    return String.format("%0" + 4 + "d", i);
  }
}
