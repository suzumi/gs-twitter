import org.atilika.kuromoji.Tokenizer
import org.atilika.kuromoji.Token

object Main extends App{

  val tokenizer = Tokenizer.builder.mode(Tokenizer.Mode.NORMAL).build

  val tokens = tokenizer.tokenize("すもももももももものうち").toArray

  tokens.foreach { t =>
    val token = t.asInstanceOf[Token]
    println(s"${token.getSurfaceForm} - ${token.getAllFeatures}")
  }
}
