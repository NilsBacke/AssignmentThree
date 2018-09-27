import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import javalib.worldcanvas.*;
import java.awt.Color; // general colors (as triples of red,green,blue values)
// and predefined colors (Red, Green, Yellow, Blue, Black, White)

//to represent all types of mobiles
interface IMobile {

  //to compute the total weight of the mobiles
  int totalWeight();

  //to compute the total height of the mobiles
  int totalHeight();

  //to compute whether a mobile is balanced
  boolean isBalanced();

  //to compute whether a mobile is balanced
  IMobile buildMobile(IMobile other, int verticalLength, int strutLength);

  //to compute the total width of a mobile
  int curWidth();

  //helps compute the total width of a mobile
  int curWidthHelp(int prevLength);

  // Produces the image of this mobile, completely flat and shown as a
  // two-dimensional drawing
  WorldImage drawMobile();
  
  //returns length of IMobile
  int returnLength();
}

//to represent a Simple Mobile
class Simple implements IMobile {
  int length;
  int weight;
  Color color;

  //constructor for a simple mobile 
  Simple(int length, int weight, Color color) {
    this.length = length;
    this.weight = weight;
    this.color = color;
  }

  /* template for Simple
   * 
   * Fields:
   * this.length ... int
   * this.weight ... int
   * this.color ... Color
   * 
   * Methods:
   * 
   * totalWeight ... int
   * totalHeight ... int
   * isBalanced ... boolean
   * buildIMobile ... IMobile
   * curWidth ... int
   * curWeightHelper ... int
   * drawMobile() ... WorldImage
   * 
   * Methods for fields:
   */

  //computes the total weight of a complex mobile
  @Override
  public int totalWeight() {
    return this.weight;
  }

  //computes the total height of a complex mobile
  @Override
  public int totalHeight() {
    return this.length + this.weight / 10;
  }

  //returns true if mobile is balanced
  @Override
  public boolean isBalanced() {
    return true;
  }

  //Combines this balanced mobile with the given balanced mobile and produces a new mobile 
  @Override
  public IMobile buildMobile(IMobile other, int verticalLength, int strutLength) {
    int leftWeight = this.totalWeight();
    int rightWeight = other.totalWeight();
    int rightside = leftWeight * strutLength / (leftWeight + rightWeight);
    int leftside = strutLength - rightside;
    return new Complex(verticalLength, leftside, rightside, this, other);
  }

  //Calculated the width of the mobile
  @Override
  public int curWidth() {
    if (((double) weight) / 10 % 1 == 0) {
      return (int) Math.floor(weight / 10);
    }
    return (int) Math.floor(weight / 10 + 1);
  }

  //helps calculate the width of the mobile 
  @Override
  public int curWidthHelp(int prevLength) {
    if (prevLength > 0) {
      return this.curWidth() / 2;
    }
    return this.curWidth();
  }

  //draws simple mobile
  @Override
  public WorldImage drawMobile() {
    WorldImage simpleImage = new RectangleImage(this.weight / 2, this.weight, OutlineMode.SOLID,
        this.color);

    WorldImage hieghtImage = new RectangleImage(2, this.length * 10, OutlineMode.SOLID,
        Color.black);

    OverlayImage simpleMobile = new OverlayImage(hieghtImage.movePinhole(0, 0),
        simpleImage.movePinhole(0, -this.weight/2 - this.length * 5));

    return simpleMobile;
  }
  
  //returns length 
  @Override
  public int returnLength() {
    return this.length;
  }
}

//represents a complex mobile
class Complex implements IMobile {
  int length;
  int leftside;
  int rightside;
  IMobile left;
  IMobile right;

  //constructor for complex mobile
  Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
    this.length = length;
    this.leftside = leftside;
    this.rightside = rightside;
    this.left = left;
    this.right = right;
  }

  /* template for Simple
   * 
   * Fields:
   * this.length ... int
   * this.lefstide ... int
   * this.rightside ... int
   * this.right ... IMobile
   * this.left ... IMobile
   * 
   * Methods:
   * 
   * totalWeight ... int
   * totalHeight ... int
   * isBalanced ... boolean
   * buildIMobile ... IMobile
   * curWidth ... int
   * curWeightHelper ... int
   * drawMobile ... WorldImage
   * 
   * Methods for fields:
   * this.right.totalWeight ... int
   * this.right.totalHeight ... int
   * this.right.isBalanced ... boolean
   * this.right.buildIMobile ... IMobile
   * this.right.curWidth ... int
   * this.right.curWeightHelper ... int
   * this.right.drawMobile() ... WorldImage
   * this.left.totalWeight ... int
   * this.left.totalHeight ... int
   * this.left.sBalanced ... boolean
   * this.left.buildIMobile ... IMobile
   * this.left.curWidth ... int
   * this.left.curWeightHelper ... int
   * this.left.drawMobile() ... WorldImage
   * 
   */

  //calculates the total weight of a mobile
  @Override
  public int totalWeight() {
    return this.left.totalWeight() + this.right.totalWeight();
  }

  //calculates the total height of a mobile 
  @Override
  public int totalHeight() {
    return this.length + Math.max(this.left.totalHeight(), this.right.totalHeight());
  }

  //returns true of a mobile is balanced
  @Override
  public boolean isBalanced() {
    return left.totalWeight() * leftside == right.totalWeight() * rightside && left.isBalanced()
        && right.isBalanced();
  }

  //Combines this balanced mobile with the given balanced mobile and produces a new mobile
  @Override
  public IMobile buildMobile(IMobile other, int verticalLength, int strutLength) {
    int leftWeight = this.totalWeight();
    int rightWeight = other.totalWeight();
    int rightside = leftWeight * strutLength / (leftWeight + rightWeight);
    int leftside = strutLength - rightside;
    return new Complex(verticalLength, leftside, rightside, this, other);
  }

  //finds the total width of a mobile 
  @Override
  public int curWidth() {
    // return this.leftside + this.rightside + left.curWidth() / 2 +
    // right.curWidth() / 2;
    return this.curWidthHelp(0);
  }

  //helps find total width 
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

  @Override
  //draws mobile 
  public WorldImage drawMobile() {

    RectangleImage heightImage = new RectangleImage(2, this.length * 10, OutlineMode.SOLID,
        Color.black);

    RectangleImage left = new RectangleImage(this.leftside * 10, 2, OutlineMode.SOLID, Color.BLACK);

    RectangleImage right = new RectangleImage(this.rightside * 10, 2, OutlineMode.SOLID,
        Color.black);

    OverlayImage frame = new OverlayImage(heightImage,
        new OverlayImage(left.movePinhole(this.leftside * 5, -this.length * 5),
            new OverlayImage(right.movePinhole(-this.rightside * 5, -this.length * 5),
                new OverlayImage(this.left.drawMobile().movePinhole(this.leftside * 10, - (this.left.returnLength()*10)),
                    this.right.drawMobile().movePinhole(-this.rightside * 10,- (this.right.returnLength()*10))))));
 
    return frame;
  }
  
  @Override 
  //returns length
  public int returnLength() {
    return this.length;
  }
}

//examples of mobiles and methods 
class ExamplesMobiles {
  IMobile exampleSimple = new Simple(2, 20, new Color(0, 0, 255));
  IMobile exampleComplex = new Complex(1, 9, 3, new Simple(1, 36, new Color(0, 0, 255)),
      new Complex(2, 8, 1, new Simple(1, 12, new Color(255, 0, 0)), new Complex(2, 5, 3,
          new Simple(2, 36, new Color(255, 0, 0)), new Simple(1, 60, new Color(0, 255, 0)))));
  IMobile exampleComplex2 = new Complex(1,9,3,new Simple(1, 36, new Color(0, 0, 255)),new Simple(1, 36, new Color(0, 0, 255)));
  IMobile example3 = new Complex(1, 9, 3, new Simple(1, 36, new Color(0, 0, 255)),
      new Complex(2, 8, 1, new Simple(1, 12, new Color(255, 0, 0)),
          new Complex(2, 5, 3, new Simple(2, 36, new Color(255, 0, 0)), new Complex(1, 5, 5,
              new Simple(1, 5, new Color(0, 255, 0)), new Simple(2, 5, new Color(255, 0, 0))))));

  IMobile exampleCurWidth = new Complex(1, 8, 1, new Simple(1, 12, new Color(255, 0, 0)),
      new Simple(1, 36, new Color(255, 0, 0)));

  //test toatlWeight method
  boolean testTotalWeight(Tester t) {
    return t.checkExpect(exampleComplex.totalWeight(), 144);
  }

  //test totalHeight method
  boolean testTotalHeight(Tester t) {
    return t.checkExpect(exampleComplex.totalHeight(), 12);
  }

  //tests isBalanced method
  boolean testIsBalanced(Tester t) {
    return t.checkExpect(exampleComplex.isBalanced(), true)
        && t.checkExpect(exampleSimple.isBalanced(), true)
        && t.checkExpect(example3.isBalanced(), false);
  }

  //tests curWidth method 
  boolean testCurWidth(Tester t) {
    return t.checkExpect(exampleComplex.curWidth(), 21)
        && t.checkExpect(exampleCurWidth.curWidth(), 11)
        && t.checkExpect(exampleSimple.curWidth(), 2);
  }

  //test drawMobile method
  boolean testDrawMobile(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(exampleComplex.drawMobile(), 250, 250))
        && c.show();
  }
}