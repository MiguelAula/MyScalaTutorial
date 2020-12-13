package theory

/**
 *  scala objects vs scala classes
 */
object ObjectsAndClasses extends App {
  /**
   * As we discussed in the "1_Basics" chapter, a class represents a data structure that can be instantiated multiple times.
   * It can also take a list of parameters and contain methods and values.
   */
  class Animal(_sound: String, _speed: Double) {
    val sound: String = _sound
    val speed: Double = _speed

    /** returns the time (in hours) he took to run the given distance */
    def travelTime(kms: Double): Double = kms / speed
  }

  val dog = new Animal("bark", 20)
  val wolf = new Animal("howl", 25)
  val cat = new Animal("meow", 22)

  println(dog.speed)
  println(dog.sound)
  println(dog.travelTime(10))

  /**
   *  On the other hand, a scala object represents a data structure that contains exactly one instance and it is instantiated at the moment of definition.
   *  This means there is no point in passing any parameters to the object since, at the time of definition, you already know all there is to know about it.
   */
  object PlanetEarth {
    /** km */
    val diameter = 12.742
    /** hours */
    val timeToRotate = 24
  }

  println(PlanetEarth.diameter)
  println(PlanetEarth.timeToRotate)


  /**
  NOTICE THAT...

  You can define the class values directly in the class header just by adding the "val" keyword in front of them
   */
  class Animal_v2(val sound: String, val speed: Double) {
    def travelTime(kms: Double): Double = kms / speed
  }
}

/**
 *  Scala supports class and object inheritance. To use this feature you need to use either traits or abstract classes:
 */
object Inheritance {

  /**
   * An abstract class contains the definition of how that class should be if you want to instantiate it.
   */
  abstract class Animal {
    val sound: String
    val avgSpeed: Double
    def travelTime(kms: Double): Double = kms/avgSpeed
  }

  /**  You can make an instance from an abstract class by overriding its NON-IMPLEMENTED values and methods */
  val myAnimal: Animal = new Animal {
    val sound: String = "meow"
    val avgSpeed: Double = 22
    /*
    Notice that you don't need to give an implementation for "travelTime" because the abstract class already contains it. You CAN nevertheless override it if
    you wish to:

    override def travelTime(kms: Double): Double = (kms/speed)/60
     */
  }

  /** You can create a more concrete class that inherits from the abstract class, and add any vals or methods you see fit*/
  class Cat(val name: String, val color: String) extends Animal {
    val sound: String = "meow"
    val avgSpeed: Double = 22
  }

  val myCat = new Cat("whiskers","black")

  /** You can also make an object that inherits from it, but remember, this will be a singleton instance! */
  object MySpecificCat extends Animal {
    val sound: String = "meow"
    val avgSpeed: Double = 22
  }
}

/**
 * The "final" keyword is used to precede something that cannot be changed through inheritance.
 */
object finalKeyword {
  abstract class AstralCorpse {
    val canHaveAtmosphere: Boolean
  }
  /**
   * In classes it is used to indicate that no other class can inherit from this one
   */
  final class Planet extends AstralCorpse {
    override val canHaveAtmosphere: Boolean = true
  }

  /**
  You canNOT make anything extend Planet now!
   This is especially useful if you want to use inheritance to define an enumeration-like structure. We will see this example in the next section.
   */
  //class Earth extends Planet


  /** DID YOU KNOW...
   *  The "final" keyword can also be used in methods and variables to prevent them from being overridden.
   */
}


/**
 *  A companion object is an object named after an already defined class.
 *  This can have many uses:
 *
 *  1) Holding static methods / variables for that class
 *
 *  2) A companion object’s apply method lets you create new instances of a class without using the new keyword
 *
 *  3) A companion object’s unapply method lets you de-construct an instance of a class into its individual components (used for pattern-matching)
 */
object CompanionObjects extends App {
  import scala.util.Random

  abstract class Wall {
    val height: Double
    val width: Double
  }
  object Wall {
    def apply(_size: Double, _shininess: Double): Wall = new Wall {
      val height: Double = _size
      val width: Double = _shininess
    }
    def unapply(wall: Wall): Option[(Double,Double)] = Some((wall.height,wall.width))

    def buildRandom: Wall = Wall(Random.nextDouble(),Random.nextDouble())
  }

  val s = Wall.buildRandom
  s match {
    case Wall(h, w) => println(s"this wall has dimensions ($h,$w)")
  }

  val s2 = Wall(10,10)
  s2 match {
    case Wall(d, d1) => println(s"this wall has dimensions ($d,$d1)")
  }
}


/**
 *  Scala case classes are just regular classes which are immutable by default and decomposable through pattern matching.
 *  It does not use new keyword to instantiate object. This is because a case class automatically adds the apply and unapply function to the
 *  companion object (if it doesn't exists it creates one for you in the background)
 */
object caseClasses extends App {
  abstract class Animal {
    val sound: String
    val avgSpeed: Double
    def travelTime(kms: Double): Double = kms/avgSpeed
  }
  /** case class */
  case class Cat(name: String, color: String) extends Animal {
    override val sound: String = "meow"
    override val avgSpeed: Double = 22
  }
  /** regular class */
  class Dog(val name: String, val color: String) extends Animal {
    override val sound: String = "bark"
    override val avgSpeed: Double = 20
  }

  val myCat: Cat = Cat("Whiskers","black")
  myCat match {
    case Cat(name, color) => println(s"my cat is called $name, and has color $color")
  }

  val myDog: Dog = new Dog("Tobby","brown")
  myDog match {
    case x: Dog => println(s"my dog is called ${x.name}, and has color ${x.color}")
  }

  /**
   *  It is a good practice to use case classes in scala instead of regular classes in most modeling cases.
   *  Nevertheless, keep in mind that there is a limitation: a case class CAN'T extend an other case class!
   */
  //case class Whatever() extends Cat
}

/**
 *  Scala supports multiple inheritance. To do this we must introduce scala traits.
 *  A trait is pretty much like an abstract class that ALLOWS MULTIPLE INHERITANCE. You can NOT use multiple inheritance with abstract classes.
 */
object MultipleInheritance {
  /**
   * You can use traits (or abstract classes) and inheritance to represent an Enumeration. Do not forget the SEALED keyword for this though! This prevents someone from
   * further extending your trait in some other file, which may cause problems due to pattern-matching on the "Enumeration" not being exhaustive anymore.
   *
   * In this example, this will let you match over a Food type so that you can implement different logics for each concrete case: Mineral, Vegetable, Meat, etc.
   */
  sealed trait Food
  object Mineral extends Food
  object Vegetable extends Food
  object Meat extends Food
  object Fodder extends Food
  object Rabbit extends Food
  object Mouse extends Food

  sealed trait Animal {
    type Residue = String
    def sound: String
    def diet: List[Food]
    def digest(food: Food): Residue
  }

  trait Vegetal {
    /** photons/mm3 */
    type Absortion = Int
    type Energy = Int
    def sunAbsortion: Absortion
    def photosythesize(quantum: Absortion): Energy
  }

  class Dog extends Animal {
    override def sound: String = "Bark"

    override def diet: List[Food] = List(Meat,Fodder)

    override def digest(food: Food): Residue = food match {
      case Fodder => "compact defecation"
      case Meat => "non compact defecation"
      case Rabbit => "non compact defecation"
      case _ => "diarrhea"
    }
  }


  class Cat extends Animal {
    override def sound: String = "Meow"

    override def diet: List[Food] = List(Meat,Fodder)

    override def digest(food: Food): Residue = food match {
      case Fodder => "compact defecation"
      case Meat => "non compact defecation"
      case Mouse => "non compact defecation"
      case _ => "diarrhea"
    }
  }

  /**
   *  We can make a class extend multiple traits with they "with" keyword. We can NOT do this with abstract classes!
   *
   *  Note: the only thing that needs to be a trait is the representation preceded by the "with" keyword, in this next example, Vegetal could be an abstract class
   *  and this would compile fine.
   *  */
  class Ent extends Vegetal with Animal {
    override def sunAbsortion: Absortion = 10

    override def photosythesize(quantum: Absortion): Energy = quantum/5

    override def sound: String = "rumble"

    override def diet: List[Food] = List(Mineral)

    override def digest(food: Food): Residue = food match {
      case Mineral => "seeds"
      case _ => "biomass"
    }
  }
}

/**
 *  You can limit the visibility of class and object methods / variables with the "private" or "protected" keyword.
 */
object Visibility {
  /**
   *  By default any value or method is public, if you want to change its visibility so it can only be accessed inside the class, you can use the
   *  "private" keyword.
   */
  class Animal(val sound: String, private val speed: Double) {
    /*
    YOU CAN ONLY ACCESS speed HERE!
     */
    /** returns the time (in hours) he took to run the given distance */
    def travelTime(kms: Double): Double = kms/speed
  }

  /**
   *  You can also make vals and methods available ONLY for subclasses with the "protected" keyword. This makes most sense for abstract classes and traits.
   */
  trait Vegetal {
    /** photons/mm3 */
    type Absortion = Int
    type Energy = Int
    protected def sunAbsortion: Absortion
    def photosythesize(quantum: Absortion): Energy = sunAbsortion/quantum
  }
  class Tree extends Vegetal {
    /*
    sunAbsortion is accessible here!
     */
    override protected def sunAbsortion: Absortion = 3
  }

  val aTree = new Tree
  /*
   sunAbsortion is NOT accessible here unless you remove the "protected" keyword
   */
  //aTree.sunAbsortion
}
