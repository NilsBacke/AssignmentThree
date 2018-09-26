import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import java.awt.Color; // general colors (as triples of red,green,blue values)
                       // and predefined colors (Red, Green, Yellow, Blue, Black, White)

interface IMobile {
  int totalWeight();

  int totalHeight();

  boolean isBalanced();
  
  IMobile buildMobile(IMobile other, int verticalLength, int strutLength);
  
  int curWidth();
  int curWidthHelp(int prevLength);
}

class Simple implements IMobile {
  int length;
  int weight;
  Color color;

  Simple(int length, int weight, Color color) {
    this.length = length;
    this.weight = weight;
    this.color = color;
  }

  @Override
  public int totalWeight() {
    return this.weight;
  }

  @Override
  public int totalHeight() {
    return this.length + this.weight / 10;
  }

  @Override
  public boolean isBalanced() {
    return true;
  }

  @Override
  public IMobile buildMobile(IMobile other, int verticalLength, int strutLength) {
    int leftWeight = this.totalWeight();
    int rightWeight = other.totalWeight();
    int rightside = leftWeight * strutLength / (leftWeight + rightWeight);
    int leftside = strutLength - rightside;
    return new Complex(verticalLength, leftside, rightside, this, other);
  }

  @Override
  public int curWidth() {
    return (int) Math.ceil(this.weight / 10);
  }

  @Override
  public int curWidthHelp(int prevLength) {
    return this.curWidth();
  }
}

class Complex implements IMobile {
  int length;
  int leftside;
  int rightside;
  IMobile left;
  IMobile right;

  Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
    this.length = length;
    this.leftside = leftside;
    this.rightside = rightside;
    this.left = left;
    this.right = right;
  }

  @Override
  public int totalWeight() {
    return this.left.totalWeight() + this.right.totalWeight();
  }

  @Override
  public int totalHeight() {
    return this.length + Math.max(this.left.totalHeight(), this.right.totalHeight());
  }

  @Override
  public boolean isBalanced() {
    return left.totalWeight() * leftside == right.totalWeight() * rightside && left.isBalanced() && right.isBalanced();
  }

  @Override
  public IMobile buildMobile(IMobile other, int verticalLength, int strutLength) {
    int leftWeight = this.totalWeight();
    int rightWeight = other.totalWeight();
    int rightside = leftWeight * strutLength / (leftWeight + rightWeight);
    int leftside = strutLength - rightside;
    return new Complex(verticalLength, leftside, rightside, this, other);
  }

  @Override
  public int curWidth() {
//    return this.leftside + this.rightside + left.curWidth() / 2 + right.curWidth() / 2;
    return this.curWidthHelp(0);
  }

  @Override
  public int curWidthHelp(int prevLength) {
    if (this.leftside + this.left.curWidthHelp(this.leftside + this.rightside) >= prevLength) {
      return this.leftside + this.left.curWidthHelp(this.leftside + this.rightside) + this.right.curWidthHelp(this.leftside + this.rightside);
    } else {
      return this.rightside + this.left.curWidthHelp(this.leftside + this.rightside) + this.right.curWidthHelp(this.leftside + this.rightside);
    }
  }
}

class ExamplesMobiles {
  IMobile exampleSimple = new Simple(2, 20, new Color(0, 0, 255));
  IMobile exampleComplex = new Complex(1, 9, 3, new Simple(1, 36, new Color(0, 0, 255)),
      new Complex(2, 8, 1, new Simple(1, 12, new Color(255, 0, 0)), new Complex(2, 5, 3,
          new Simple(2, 36, new Color(255, 0, 0)), new Simple(1, 60, new Color(0, 255, 0)))));
  IMobile example3 = new Complex(1, 9, 3, new Simple(1, 36, new Color(0, 0, 255)),
      new Complex(2, 8, 1, new Simple(1, 12, new Color(255, 0, 0)),
          new Complex(2, 5, 3, new Simple(2, 36, new Color(255, 0, 0)), new Complex(1, 5, 5,
              new Simple(1, 5, new Color(0, 255, 0)), new Simple(2, 5, new Color(255, 0, 0))))));

  boolean testTotalWeight(Tester t) {
    return t.checkExpect(exampleComplex.totalWeight(), 144);
  }

  boolean testTotalHeight(Tester t) {
    return t.checkExpect(exampleComplex.totalHeight(), 12);
  }

  boolean testIsBalanced(Tester t) {
    return t.checkExpect(exampleComplex.isBalanced(), true)
        && t.checkExpect(exampleSimple.isBalanced(), true)
        && t.checkExpect(example3.isBalanced(), false);
  }
  
  boolean testCurWidth(Tester t) {
    return t.checkExpect(exampleComplex.curWidth(), 26);
  }
}
