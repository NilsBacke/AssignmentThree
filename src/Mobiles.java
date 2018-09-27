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

  //Produces the image of this mobile, completely flat and shown as a two-dimensional drawing
  WorldImage drawMobile();
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
    if (((double) weight) / 10 % 1 == 0) {
      return (int) Math.floor(weight / 10);
    }
    return (int) Math.floor(weight / 10 + 1);
  }

  @Override
  public int curWidthHelp(int prevLength) {
    if (prevLength > 0) {
      return this.curWidth() / 2;
    }
    return this.curWidth();
  }

  @Override 
  public WorldImage drawMobile() {
    WorldImage simpleImage = new RectangleImage(this.weight/2, this.weight,
        OutlineMode.SOLID, this.color);

    WorldImage hieghtImage = new RectangleImage(2, this.length * 10,
        OutlineMode.SOLID, Color.black);

    OverlayOffsetImage simpleMobile = new OverlayOffsetImage(hieghtImage, 0, 
        (this.length * 10) / 2, simpleImage);

    return simpleMobile;        
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
    return left.totalWeight() * leftside == right.totalWeight() * rightside && left.isBalanced()
        && right.isBalanced();
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
      return this.leftside + this.left.curWidthHelp(this.leftside + this.rightside)
      + this.right.curWidthHelp(this.leftside + this.rightside);
    } else {
      return this.rightside + this.left.curWidthHelp(this.leftside + this.rightside)
      + this.right.curWidthHelp(this.leftside + this.rightside);
    }
  }
    public WorldImage drawMobile() {

      RectangleImage heightImage = new RectangleImage(2, this.length * 10, OutlineMode.SOLID, Color.black);

      RectangleImage left = new RectangleImage(this.leftside * 10, 2, OutlineMode.SOLID, Color.BLACK);

      RectangleImage right = new RectangleImage(this.rightside * 10, 2, OutlineMode.SOLID, Color.black);

      WorldImage mobileLeft = this.left.drawMobile()
          .movePinhole(0, - (this.left.totalHeight() * 5));

      WorldImage mobileRight = this.right.drawMobile()
          .movePinhole(0, - (this.right.totalHeight() * 5));

      WorldImage leftSide = new OverlayOffsetImage(left, - this.leftside * 5, 1, 
          mobileLeft).movePinhole((this.leftside * 5), - this.left.totalHeight() * 5);

      WorldImage rightSide = new VisiblePinholeImage( new OverlayOffsetImage(right, this.rightside * 5, 1, 
          mobileRight).movePinhole((this.leftside * -5), - this.right.totalHeight() * 5));

      WorldImage combined = new OverlayOffsetImage(leftSide, 0, 0, rightSide);

     return new OverlayOffsetImage(heightImage, 0, this.length * 5, combined);
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

  IMobile exampleCurWidth = new Complex(1, 8, 1, new Simple(1, 12, new Color(255, 0, 0)),
      new Simple(1, 36, new Color(255, 0, 0)));

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
    return t.checkExpect(exampleComplex.curWidth(), 21) && t.checkExpect(exampleCurWidth.curWidth(), 11) && t.checkExpect(exampleSimple.curWidth(), 2);
  }
}
