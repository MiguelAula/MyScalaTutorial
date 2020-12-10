package theory

/**
 *  Classes, abstract classes, traits, companion objects and inheritance
 */
object ClassTraitObj {
  trait Food
  object Vegetables extends Food
  object Meat extends Food
  object Fodder extends Food
  object Rabbit extends Food
  object Mouse extends Food

  trait Animal {
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
  object Dog {
    def apply(): Dog = new Dog
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
   *  We can make a class extend multiple traits. We cannot do this with abstract classes though!
   *  */
  class Ent extends Vegetal with Animal {
    override def sunAbsortion: Absortion = ???

    override def photosythesize(quantum: Absortion): Energy = ???

    override def sound: String = ???

    override def diet: List[Food] = ???

    override def digest(food: Food): Residue = ???
  }


  def main(args: Array[String]): Unit = {
    val kitty = new Cat()
    /** no need to do a "new" here because dog has a companion object with an apply method! */
    val sparkie = Dog()
  }
}