
// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  ILoString sort();

  ILoString insertString(String s);

  String getFirst();

  boolean isSorted();

  boolean isSortedHelp(String acc);

  ILoString interleave(ILoString other);

  ILoString merge(ILoString other);

  ILoString reverse();

  ILoString reverseHelp(ILoString acc);
  
  boolean isDoubledList();
  
  boolean isPalindromeList();
  
  boolean isPalindromeListHelp(ILoString acc);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  @Override
  public ILoString sort() {
    return this;
  }

  @Override
  public ILoString insertString(String s) {
    return new ConsLoString(s, this);
  }

  @Override
  public String getFirst() {
    return "";
  }

  @Override
  public boolean isSorted() {
    return true;
  }

  @Override
  public boolean isSortedHelp(String acc) {
    return true;
  }

  @Override
  public ILoString interleave(ILoString other) {
    return other;
  }

  @Override
  public ILoString merge(ILoString other) {
    return other;
  }

  @Override
  public ILoString reverse() {
    return this;
  }

  @Override
  public ILoString reverseHelp(ILoString acc) {
    return acc;
  }

  @Override
  public boolean isDoubledList() {
    return true;
  }

  @Override
  public boolean isPalindromeList() {
    return true;
  }

  @Override
  public boolean isPalindromeListHelp(ILoString acc) {
    return true;
  }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE FIELDS: ... this.first ... -- String ... this.rest ... -- ILoString
   * 
   * METHODS ... this.combine() ... -- String
   * 
   * METHODS FOR FIELDS ... this.first.concat(String) ... -- String ...
   * this.first.compareTo(String) ... -- int ... this.rest.combine() ... -- String
   * 
   */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  @Override
  public ILoString sort() {
    return this.rest.sort().insertString(this.first);
  }

  @Override
  public ILoString insertString(String s) {
    if (s.compareToIgnoreCase(this.first) < 0) {
      return new ConsLoString(s, this);
    } else {
      return new ConsLoString(this.first, this.rest.insertString(s));
    }
  }

  @Override
  public String getFirst() {
    return this.first;
  }

  @Override
  public boolean isSorted() {
    return this.rest.isSortedHelp(this.first);
  }

  @Override
  public boolean isSortedHelp(String acc) {
    if (this.first.compareToIgnoreCase(acc) > 0) {
      return this.rest.isSortedHelp(this.first);
    } else {
      return false;
    }
  }

  @Override
  public ILoString interleave(ILoString other) {
    return new ConsLoString(this.first, other.interleave(this.rest));
  }

  @Override
  public ILoString merge(ILoString other) {
    return this.interleave(other).sort();
  }

  @Override
  public ILoString reverse() {
    return this.reverseHelp(new MtLoString());
  }

  @Override
  public ILoString reverseHelp(ILoString acc) {
    return this.rest.reverseHelp(new ConsLoString(this.first, acc));
  }

  @Override
  public boolean isDoubledList() {
    // TODO: implement
    return true;
  }

  @Override
  public boolean isPalindromeList() {
    return this.isPalindromeListHelp(this.reverse());
  }

  @Override
  public boolean isPalindromeListHelp(ILoString acc) {
    // TODO: implement
//    return this.first.equals(acc.getFirst()) && this.rest.isPalindromeListHelp(acc.)
  }
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));
  ILoString marySorted = new ConsLoString("a ", new ConsLoString("had ", new ConsLoString("lamb.",
      new ConsLoString("little ", new ConsLoString("Mary ", new MtLoString())))));

  ILoString interleave1 = new ConsLoString("Mary",
      new ConsLoString("a", new ConsLoString("lamb.", new MtLoString())));
  ILoString interleave2 = new ConsLoString("had",
      new ConsLoString("little", new ConsLoString("!", new MtLoString())));
  ILoString interleaveResult = new ConsLoString("Mary",
      new ConsLoString("had", new ConsLoString("a", new ConsLoString("little",
          new ConsLoString("lamb.", new ConsLoString("!", new MtLoString()))))));

  ILoString sorted1 = new ConsLoString("duckling", new ConsLoString("mary", new MtLoString()));
  ILoString sorted2 = new ConsLoString("apple", new ConsLoString("banana", new MtLoString()));
  ILoString mergeResult = new ConsLoString("apple", new ConsLoString("banana",
      new ConsLoString("duckling", new ConsLoString("mary", new MtLoString()))));

  ILoString reverseInterleave1 = new ConsLoString("lamb.",
      new ConsLoString("a", new ConsLoString("Mary", new MtLoString())));

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }

  boolean testSort(Tester t) {
    return t.checkExpect(mary.sort(), marySorted);
  }

  boolean testIsSorted1(Tester t) {
    return t.checkExpect(marySorted.isSorted(), true);
  }

  boolean testIsSorted2(Tester t) {
    return t.checkExpect(mary.isSorted(), false);
  }

  boolean testInterleave(Tester t) {
    return t.checkExpect(interleave1.interleave(interleave2), interleaveResult);
  }

  boolean testMerge(Tester t) {
    return t.checkExpect(sorted1.merge(sorted2), mergeResult);
  }
  
  boolean testReverse(Tester t) {
    return t.checkExpect(interleave1.reverse(), reverseInterleave1);
  }

}