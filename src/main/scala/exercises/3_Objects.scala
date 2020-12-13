package exercises

object ObjectsExercises {
  /**
   *  1) Define an abstract class Country. All countries must have a name and a language.
   */

  /**
   *  2) Define two implementations of the Country trait (e.g: Spain and France).
   */

  /**
   *  3) Define an abstract class PieceOfLand. All pieces of land must have a size in m2.
   */

  /**
   *  4) Can you extend the your country implementations so that they are also pieces of land? Why? Make the necessary changes to make it possible.
   */



  /**
   *  5) Given the following code you may notice that some of the foods extending the Food trait are also animals.
   *  Change the code accordingly to reflect this behaviour.
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

  class Ent extends Vegetal with Animal {
    override def sunAbsortion: Absortion = 10

    override def photosythesize(quantum: Absortion): Energy = quantum/5

    override def sound: String = "rumble"

    override def diet: List[Food] = List(Mineral)

    override def digest(food: Food): Residue = food match {
      case Mineral => "seeds"
      case Vegetable => "sadness"
      case Rabbit => "rabbit hole"
      case Mouse => "tickles"
      case _ => "biomass"
    }
  }

  /**
   *  6) Imagine the following database domain
   *
   *  User(uuid,username)
   *  Task(uuid,userId,description)
   *
   *  Define its business model using classes.
   */
}



