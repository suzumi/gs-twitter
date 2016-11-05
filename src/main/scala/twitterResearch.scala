import org.atilika.kuromoji.Tokenizer
import twitter4j._
import twitter4j.conf._
import scala.collection.JavaConversions._

/**
 * Girls Senseのつぶやきを形態素解析し、どのワードを含むとリツイートされやすいか統計を取る
 */
object TwitterResearch {

  /**
   * Girls Senseの公式リツイートされた記事を取得
   * @param userName String
   * @return ResponseList[Status]
   */
  def getTwitter(userName: String): ResponseList[Status] = {
    val cb = new ConfigurationBuilder
    // GSのAPIキー
    cb.setOAuthConsumerKey("your key")
      .setOAuthConsumerSecret("your key")
      .setOAuthAccessToken("your key")
      .setOAuthAccessTokenSecret("your key")

    val twitterFactory = new TwitterFactory(cb.build)
    val tt = twitterFactory.getInstance

    val timeLine = tt.timelines
    timeLine.getRetweetsOfMe
  }

  /**
   * つぶやきを形態素解析してサマる
   * @param gsRetweets ResponseList[Status]
   */
  def tweetAnalysis(gsRetweets: ResponseList[Status]) = {

    val tokenizer = Tokenizer.builder.mode(Tokenizer.Mode.NORMAL).build

    val tokens = for (rl <- gsRetweets) yield tokenizer.tokenize(rl.getText)

    val sorted = tokens.flatten.filter(x => x.getPartOfSpeech.startsWith("名詞")).groupBy(x => x.getBaseForm).values.toList.sortWith(_.length>_.length)

    sorted.foreach { x =>
      x match {
        case x if (x(0).getBaseForm() == null) =>
        case x => println("count: " +x.length+" "+x(0).getBaseForm())
      }
    }

  }

  /**
   * 実行
   */
  def main (args: Array[String]) {

    val userName = "Girls_Sense"
    val gsRetweets = getTwitter(userName)
    tweetAnalysis(gsRetweets)
  }
}
