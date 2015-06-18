import org.atilika.kuromoji.Tokenizer
import org.atilika.kuromoji.Token
import twitter4j._
import twitter4j.conf._

/**
 * Girls Senseのつぶやきを形態素解析し、どのワードを含むとリツイートされやすいか統計を取る
 */
object TwitterResearch {

  /**
   * Twiiterの設定
   */
  def twitterInfo() = {
    //
  }

  /**
   * Girls Senseのつぶやきを取得
   */
  def getTwitter(userName: String) = {
    val cb = new ConfigurationBuilder
    cb.setOAuthConsumerKey("wQ7lBe5gIQExkA98ocsIJa6ey")
      .setOAuthConsumerSecret("vbRfYoPcj8PYPV8V4K6AHfQevnSfQPb8OIR9flSLxEXp3lHcBt")
      .setOAuthAccessToken("198909960-oGoIqOLzytL3fWsTmALeLiCqL0OFHMOc5zwIV6Lo")
      .setOAuthAccessTokenSecret("OqeqcR2kEWHXakGPD6ykDxwJj0ybtz2xUaZfixdHxUewV")

    val twitterFactory = new TwitterFactory(cb.build)
    val tt = twitterFactory.getInstance

    val timeLine = tt.timelines
    timeLine.getUserTimeline(userName)
  }

  /**
   * つぶやきを形態素解析してサマる
   */
  def tweetAnalysis() = {

    val tokenizer = Tokenizer.builder.mode(Tokenizer.Mode.NORMAL).build

    val tokens = tokenizer.tokenize("すもももももももものうち").toArray

    tokens.foreach { t =>
      val token = t.asInstanceOf[Token]
      println(s"${token.getSurfaceForm} - ${token.getAllFeatures}")
    }
  }

  /**
   * 実行
   */
  def main (args: Array[String]) {

    val userName = "Girls_Sense"
    val gsTimeLine = getTwitter(userName)
    println(gsTimeLine)
//    tweetAnalysis()
  }
}
