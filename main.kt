import kotlin.random.Random

abstract class Player(val name: String) {
  abstract fun guess(): Int
  abstract fun receiveHint(hint: String)
  abstract fun thinkNumber(): Int
}

class HumanPlayer(name: String) : Player(name) {
  override fun guess(): Int {
    print("Введіть ваше число: ")
    return readLine()?.toIntOrNull() ?: 0
  }

  override fun receiveHint(hint: String) {
    println(hint)
  }

  override fun thinkNumber(): Int {
    print("Загадайте число від 0 до 100: ")
    return readLine()?.toIntOrNull() ?: 0
  }
}

class ComputerPlayer(name: String) : Player(name) {
  var min = 0
  var max = 101

  override fun guess(): Int {
    val guess = (min + max) / 2
    println("$name намагається вгадати число $guess")
    return guess
  }

  override fun receiveHint(hint: String) {
    when (hint) {
      "Більше" -> min = (min + max) / 2
      "Менше" -> max = (min + max) / 2
    }
  }

  override fun thinkNumber(): Int {
    return Random.nextInt(0, 101)
  }

  fun reset() {
    min = 0
    max = 101
  }
}

class Game(private var player1: Player, private var player2: Player) {
  private var secretNumber = 0

  fun start() {
    while (true) {
      secretNumber = player1.thinkNumber()
      var guess: Int
      do {
        guess = player2.guess()
        when {
          guess < secretNumber -> player2.receiveHint("Більше")
          guess > secretNumber -> player2.receiveHint("Менше")
          else -> println("${player2.name} вгадав число!")
        }
      } while (guess != secretNumber)
      (player2 as? ComputerPlayer)?.reset()
      player1 = player2.also { player2 = player1 }
    }
  }
}

fun main() {
  val game = Game(HumanPlayer("Гравець"), ComputerPlayer("Комп'ютер"))
  game.start()
}
